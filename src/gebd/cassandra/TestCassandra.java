package gebd.cassandra;

import java.util.List;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;

public class TestCassandra {

	/*
	 * Connessione al cluster Cassandra
	 */
	public Cluster cluster = null;

	public void connect() {

		final Cluster.Builder clusterBuilder = Cluster.builder()
				.addContactPoints("104.197.92.103", "35.223.90.172", "34.121.29.137" // GCP_us-central1 (Google Cloud
																						// Platform)
				).withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().withLocalDc("GCP_us-central1").build()) // your
																													// local
																													// data
																													// centre
				.withPort(9042)
				.withAuthProvider(new PlainTextAuthProvider("iccassandra", "32344a7961decda777142b6ea386ed61"));

		cluster = clusterBuilder.build();
		final Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());

		for (final Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(),
					host.getRack());
		}
	}

	public void init(int ksNumber, boolean drop) {
		Session session = cluster.connect();
		for (int i = 0; i < ksNumber; i++) {
			String ks = "ks" + i;

			String cql;
			if (drop) {
				cql = "drop keyspace " + ks + ";";
				session.execute(cql);
			}

			cql = "create keyspace " + ks + " WITH REPLICATION = {'class':'SimpleStrategy','replication_factor':'3'};";
			session.execute(cql);

			System.out.println("Key space " + i + " created");

//			cql  = "use " + ks + ";";
//			session.execute(cql);

		}

	}

	/*
	 * Crea le tabelle desiderate su tutti i keyspaces da 0 a ksNumber (escluso)
	 */
	public void create(int ksNumber) {
		Session session = cluster.newSession();

		/*
		 * Seleziono a turno ciascun keyspace, mi sposto al suo interno ed eseguo tutte
		 * le query di creazione
		 */
		String cql;
		for (int i = 13; i < ksNumber; i++) {
			cql = "use ks" + i + ";";
			System.out.println("cql: " + cql);
			session.execute(cql);

			cql = "CREATE TABLE tracks(id int, titolo text, durata int, anno int, id_album int, riproduzioni bigint, generi list<text>, PRIMARY KEY (id))";
			session.execute(cql);

			cql = "CREATE TABLE track_by_genere(genere text, id_author int, titolo text, durata int, anno int, id_track int, PRIMARY KEY ((genere), id_author, id_track))";
			session.execute(cql);

			cql = "CREATE TABLE track_by_author(id_author int, titolo text, durata int, anno int, id_track int, riproduzioni bigint, PRIMARY KEY((id_author),id_track))";
			session.execute(cql);

			cql = "CREATE TABLE composizione(id_track int, id_playlist int, id_user int, nome text, cognome text, titolo text, durata int, position int, PRIMARY KEY((id_track), id_user, id_playlist))";
			session.execute(cql);

			cql = "CREATE TABLE ranking(id_track int, titolo text, genere text, anno int, settimana int, posizione int, PRIMARY KEY((genere), anno, settimana, posizione))";
			session.execute(cql);

			cql = "INSERT INTO tracks (id, anno, durata, generi, id_album, riproduzioni, titolo) VALUES (1, 2020, 114, ['rock','pop'], 5, 387, 'nonso');";
			session.execute(cql);

			cql = "INSERT INTO composizione (id_track, id_user, id_playlist, cognome, durata, nome, position, titolo) VALUES (1, 422, 766, 'Rossi', 114, 'Giulio', 1, 'favoriti');";
			session.execute(cql);

			cql = "INSERT INTO ranking (genere, anno, settimana, posizione, id_track, titolo) VALUES ('rock', 2021, 1, 5, 1, 'nonso');";
			session.execute(cql);
			
			cql = "INSERT INTO ranking (genere, anno, settimana, posizione, id_track, titolo) VALUES ('pop', 2021, 1, 1, 1, 'nonso');";
			session.execute(cql);

			cql = "INSERT INTO track_by_author (id_author, id_track, anno, durata, riproduzioni, titolo) VALUES (11, 1, 2020, 114, 387, 'nonso');";
			session.execute(cql);

			cql = "INSERT INTO track_by_genere (genere, id_author, id_track, anno, durata, titolo) VALUES ('rock', 11, 1, 2020, 114, 'nonso');";
			session.execute(cql);

			cql = "INSERT INTO track_by_genere (genere, id_author, id_track, anno, durata, titolo) VALUES ('pop', 11, 1, 2020, 114, 'nonso');";
			session.execute(cql);

		}
	}

	
	/*
	 * Esegue una batteria di query di selezione sulle tabelle presenti in un certo keyspace
	 */
	public void select(int ksNumber) {
		
		String cql = "use ks"+ksNumber+";";
		Session session = cluster.connect();
		session.execute(cql);
		
		/*
		 * Esempio di esecuzione di una query parametrizzata, dove il parametro viene
		 * fornito utilizzando gli operatori su stringhe
		 */
		cql = "select * from tracks where id = 1";
		ResultSet rs = session.execute(cql);
		for(Row r : rs) {
			String titolo = r.getString("titolo");
			int durata = r.getInt("durata");
			/*
			 * I tipi collection di Cassandra si mappano nei corrispondenti tipi collection di Java
			 * List -> List
			 * Set -> List
			 * Map -> HashMap
			 * quando leggo il contenuto di una variabile collection debbo indicare il nome della classe
			 * Java corrispondente al tipo del contenuto di quella collection.
			 * Esempio, se la collection contiene variabili text, la classe Java corrispondente è String.class
			 * Se la collection contiene variabili int, la classe Java corrispondente è Integer.class
			 */
			List<String> generi = r.getList("generi", String.class);
			System.out.println("titolo: " + titolo);
			System.out.println("durata: " + durata);
			for(String genere : generi) {
				System.out.println("genere: " + genere);
			}
			
		}
		
		/*
		 * Esempio di esecuzione di una query parametrizzata, dove il parametro viene
		 * fornito utilizzando un prepared statement.
		 * 
		 * Il prepared statement va definito a priori, precompilato ed utilizzato laddove ce n'è bisogno
		 */
		cql = "select * from tracks where id = ?";
		PreparedStatement psSelectTrackById = session.prepare(cql);
		
		BoundStatement bsSelectTrackById = new BoundStatement(psSelectTrackById);
		
		int trackId = 1;
		

		rs = session.execute(bsSelectTrackById.bind(trackId));
		for(Row r : rs) {
			String titolo = r.getString("titolo");
			int durata = r.getInt("durata");
			/*
			 * I tipi collection di Cassandra si mappano nei corrispondenti tipi collection di Java
			 * List -> List
			 * Set -> List
			 * Map -> HashMap
			 * quando leggo il contenuto di una variabile collection debbo indicare il nome della classe
			 * Java corrispondente al tipo del contenuto di quella collection.
			 * Esempio, se la collection contiene variabili text, la classe Java corrispondente è String.class
			 * Se la collection contiene variabili int, la classe Java corrispondente è Integer.class
			 */
			List<String> generi = r.getList("generi", String.class);
			System.out.println("titolo: " + titolo);
			System.out.println("durata: " + durata);
			for(String genere : generi) {
				System.out.println("genere: " + genere);
			}
			
		}

		
		
	}
	
	public static void main(String[] args) throws Exception {

		TestCassandra tc = new TestCassandra();
		tc.connect();
//		tc.init(51, true);
		//tc.create(51);
		tc.select(33);

	}

}







/*package gebd.cassandra;

import java.util.List;-

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;

public class TestCassandra {

	/*
	 * Connessione al cluster Cassandra
	 *
	public Cluster cluster = null;

	public void connect() {

		final Cluster.Builder clusterBuilder = Cluster.builder()
				.addContactPoints("104.197.92.103", "35.223.90.172", "34.121.29.137" // GCP_us-central1 (Google Cloud
																						// Platform)
				).withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().withLocalDc("GCP_us-central1").build()) // your
																													// local
																													// data
																													// centre
				.withPort(9042)
				.withAuthProvider(new PlainTextAuthProvider("iccassandra", "32344a7961decda777142b6ea386ed61"));

		cluster = clusterBuilder.build();
		final Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());

		for (final Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(),
					host.getRack());
		}
	}

	public void init(int ksNumber, boolean drop) {
		Session session = cluster.connect();
		for (int i = 0; i < ksNumber; i++) {
			String ks = "ks" + i;

			String cql;
			if (drop) {
				cql = "drop keyspace " + ks + ";";
				session.execute(cql);
			}

			cql = "create keyspace " + ks + " WITH REPLICATION = {'class':'SimpleStrategy','replication_factor':'3'};";
			session.execute(cql);

			System.out.println("Key space " + i + " created");

//			cql  = "use " + ks + ";";
//			session.execute(cql);

		}

	}

	/*
	 * Crea le tabelle desiderate su tutti i keyspaces da 0 a ksNumber (escluso)
	 *
	public void create(int ksNumber) {
		Session session = cluster.newSession();

		/*
		 * Seleziono a turno ciascun keyspace, mi sposto al suo interno ed eseguo tutte
		 * le query di creazione
		 *
		String cql;
		for (int i = 0; i < ksNumber; i++) {
			cql = "use ks" + i + ";";
			System.out.println("cql: " + cql);
			session.execute(cql);

			cql = "CREATE TABLE tracks(id int, titolo text, durata int, anno int, id_album int, riproduzioni bigint, generi list<text>, PRIMARY KEY (id))";
			session.execute(cql);

			cql = "CREATE TABLE track_by_genere(genere text, id_author int, titolo text, durata int, anno int, id_track int, PRIMARY KEY ((genere), id_author, id_track))";
			session.execute(cql);

			cql = "CREATE TABLE track_by_author(id_author int, titolo text, durata int, anno int, id_track int, riproduzioni bigint, PRIMARY KEY((id_author),id_track))";
			session.execute(cql);

			cql = "CREATE TABLE composizione(id_track int, id_playlist int, id_user int, nome text, cognome text, titolo text, durata int, position int, PRIMARY KEY((id_track), id_user, id_playlist))";
			session.execute(cql);

			cql = "CREATE TABLE ranking(id_track int, titolo text, genere text, anno int, settimana int, posizione int, PRIMARY KEY((genere), anno, settimana, posizione))";
			session.execute(cql);

			cql = "INSERT INTO tracks (id, anno, durata, generi, id_album, riproduzioni, titolo) VALUES (1, 2020, 114, ['rock','pop'], 5, 387, 'nonso');";
			session.execute(cql);

			cql = "INSERT INTO composizione (id_track, id_user, id_playlist, cognome, durata, nome, position, titolo) VALUES (1, 422, 766, 'Rossi', 114, 'Giulio', 1, 'favoriti');";
			session.execute(cql);

			cql = "INSERT INTO ranking (genere, anno, settimana, posizione, id_track, titolo) VALUES ('rock', 2021, 1, 5, 1, 'nonso');";
			session.execute(cql);
			
			cql = "INSERT INTO ranking (genere, anno, settimana, posizione, id_track, titolo) VALUES ('pop', 2021, 1, 1, 1, 'nonso');";
			session.execute(cql);

			cql = "INSERT INTO track_by_author (id_author, id_track, anno, durata, riproduzioni, titolo) VALUES (11, 1, 2020, 114, 387, 'nonso');";
			session.execute(cql);

			cql = "INSERT INTO track_by_genere (genere, id_author, id_track, anno, durata, titolo) VALUES ('rock', 11, 1, 2020, 114, 'nonso');";
			session.execute(cql);

			cql = "INSERT INTO track_by_genere (genere, id_author, id_track, anno, durata, titolo) VALUES ('pop', 11, 1, 2020, 114, 'nonso');";
			session.execute(cql);

		}
	}

	
	/*
	 * Esegue una batteria di query di selezione sulle tabelle presenti in un certo keyspace
	 *
	public void select(int ksNumber) {
		
		String cql = "use ks"+ksNumber+";";
		Session session = cluster.connect();
		session.execute(cql);
		
		/*
		 * Esempio di esecuzione di una query parametrizzata, dove il parametro viene
		 * fornito utilizzando gli operatori su stringhe
		 *
		cql = "select * from tracks where id = 1";
		ResultSet rs = session.execute(cql);
		for(Row r : rs) {
			String titolo = r.getString("titolo");
			int durata = r.getInt("durata");
			/*
			 * I tipi collection di Cassandra si mappano nei corrispondenti tipi collection di Java
			 * List -> List
			 * Set -> List
			 * Map -> HashMap
			 * quando leggo il contenuto di una variabile collection debbo indicare il nome della classe
			 * Java corrispondente al tipo del contenuto di quella collection.
			 * Esempio, se la collection contiene variabili text, la classe Java corrispondente è String.class
			 * Se la collection contiene variabili int, la classe Java corrispondente è Integer.class
			 *
			List<String> generi = r.getList("generi", String.class);
			System.out.println("titolo: " + titolo);
			System.out.println("durata: " + durata);
			for(String genere : generi) {
				System.out.println("genere: " + genere);
			}
			
		}
		
		/*
		 * Esempio di esecuzione di una query parametrizzata, dove il parametro viene
		 * fornito utilizzando un prepared statement.
		 * 
		 * Il prepared statement va definito a priori, precompilato ed utilizzato laddove ce n'è bisogno
		 *
		cql = "select * from tracks where id = ?";
		PreparedStatement psSelectTrackById = session.prepare(cql);
		
		BoundStatement bsSelectTrackById = new BoundStatement(psSelectTrackById);
		
		int trackId = 1;
		

		rs = session.execute(bsSelectTrackById.bind(trackId));
		for(Row r : rs) {
			String titolo = r.getString("titolo");
			int durata = r.getInt("durata");
			/*
			 * I tipi collection di Cassandra si mappano nei corrispondenti tipi collection di Java
			 * List -> List
			 * Set -> List
			 * Map -> HashMap
			 * quando leggo il contenuto di una variabile collection debbo indicare il nome della classe
			 * Java corrispondente al tipo del contenuto di quella collection.
			 * Esempio, se la collection contiene variabili text, la classe Java corrispondente è String.class
			 * Se la collection contiene variabili int, la classe Java corrispondente è Integer.class
			 *
			List<String> generi = r.getList("generi", String.class);
			System.out.println("titolo: " + titolo);
			System.out.println("durata: " + durata);
			for(String genere : generi) {
				System.out.println("genere: " + genere);
			}
			
		}

		
		
	}
	
	public static void main(String[] args) throws Exception {

		TestCassandra tc = new TestCassandra();
		tc.connect();
		//tc.init(51, true);
		//tc.create(51);
		tc.select(33);

	}

} */