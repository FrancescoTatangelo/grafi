
package gebd.jdbc;

// Importo tutte le classi del pacchetto di JDBC
import java.sql.*;

public class TestConnection {

	/*
	 * Classe per introdurre le istruzioni necessarie
	 * ad interagire con una base dati MySQL.
	 * 
	 * Java introduce uno standard universale per l'interazione
	 * con basi dati relazionali utilizzando il linguaggio
	 * SQL. Questo standard prende il nome di JDBC (Java DataBase
	 * Connectivity). Siccome tutti i database relazionali
	 * si assomigliano un po' tra loro pur non implementando
	 * esattamente la stessa versione del linguaggio SQL,
	 * attraverso l'uso di JDBC è possibile da Java interfacciarsi
	 * con qualsiasi database relazionale allo stesso modo. 
	 * In questo senso, JDBC implementa una architettura
	 * stratificata in cui è presente uno strato superiore
	 * identico per tutti i database ed utilizzabile per interagire
	 * con loro, ed uno strato inferiore, che cambia da database
	 * a database. In questo strato è presente una predisposizione
	 * per un modulo (driver) di volta in volta aggiunto dal programma
	 * sulla base del DBMS che si intende utilizzare.
	 * Nella lezione di oggi, caricheremo ed useremo
	 * il modulo necessario per interagire con MySQL.
	 * 
	 * Le istruzioni che andremo a descrivere e la sequenza di
	 * operazioni necessarie per interagire con una base dati
	 * ricordano quelle che un operatore umano seguirebbe
	 * usando, e.g., MySQLWorkbench
	 */
	public static void main(String[] args) throws Exception{
		/*
		 * Per esigenze di semplicità, svilupperemo il nostro
		 * primo programma interamente all'interno del metodo
		 * main.
		 */
		
		/*
		 * 1. Apro il programma
		 * 2. Stabilisco la connessione
		 * 3. Apro una finestra per le query
		 * 4. Formulo la query SQL
		 * 5. La eseguo
		 * 6. Attendo il risultato e lo processo
		 * 7. Chiudo tutto
		 */
		
		/* 
		 * 1. Apro il programma
		 * 
		 * Per poter stabilire una connessione con MySQL
		 * occorre innanzitutto caricare in memoria il driver
		 * specializzato per interagire con quel tipo di
		 * base dati.
		 * 
		 * Non necessariamente conosco in partenza il tipo di
		 * driver che intendo utilizzare, anche perché JDBC
		 * mi dà la possibilità di sceglierlo al volo. 
		 * La classe che mi dà accesso a questo driver non
		 * è quindi necessariamente già presente in memoria,
		 * e la posso richiamare utilizzando la seguente istruzione
		 */
		
		Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		
		/*
		 * 2. Stabilisco la connessione
		 * 
		 * Creo una nuova connessione verso un certo DBMS indicandone
		 * la posizione nella mia "stringa di connessione". Indicherò
		 * inoltre le credenziali con cui mi intendo collegare 
		 * (username e password) ed il nome specifico del database
		 * che intendo utilizzare. In questo frangente indichiamo
		 * anche il particolare tipo di DBMS che intendiamo utilizzare.
		 * Se la connessione ha successo, un riferimento ad essa viene
		 * restituito sotto forma di oggetto di tipo Connection
		 */
		Connection conn =DriverManager.getConnection("jdbc:mysql://90.147.166.209:3306/db170?" +
				"user=utente170&password=d1r0mp3nt3");
		
		
		/*
		 * 3. Apro una finestra per le query
		 * E' possibile eseguire delle query SQL in JDBC attraverso degli statement.
		 * Ciascuno statement può essere usato per eseguire una query alla volta.
		 * Gli statement si creano a partire dalle connessioni attraverso il 
		 * metodo createStatement ricevendo, come risultato, un oggetto di tipo
		 * Statement
		 */
		System.out.println("Connessione stabilita");
		
		Statement st = conn.createStatement();
		
		/*
		 * 4. Formulo la query SQL
		 * 
		 * Utilizzo gli operatori di Java per le stringhe allo scopo di
		 * assemblare la stringa contenente la query da eseguire
		 */

		int meseNascita = 122;
		
		String sql = "SELECT COGNOME, NOME, GIORNONASCITA, MESENASCITA, ANNONASCITA FROM Anagrafe WHERE MESENASCITA = "+meseNascita+ " ORDER BY COGNOME";
		
//		String userName = "foo UNION SELECT * FROM credentials";
//		sql = "SELECT firstName, lastName FROM users WHERE userId = " + userName + ";";
//		"SELECT firstName, lastName FROM users WHERE userId = foo UNION SELECT * FROM credentials"
		
		
		System.out.println("sql: " + sql);
		/*
		 * 5. Eseguo la query SQL
		 * Utilizzo lo statement precedentemente creato per richiedere
		 * l'esecuzione di una certa query SQL. L'eventuale risultato della query
		 * sarà incapsulato in un oggetto del tipo ResultSet.
		 * 
		 * Le operazioni di interrogazione (i.e., SELECT) si eseguono attraverso
		 * il metodo executeQuery e restituiscono un ResultSet che incapsula l'insieme
		 * dei record corrispondenti alla query di input.
		 * Tutte le altre operazioni, che hanno come effetto quello di modificare la struttura
		 * o il contenuto di una base dati, sono eseguibili attraverso il metodo executeUpdate
		 * e, tipicamente, restituiscono il numero di record interessati dalla modifica (se ha senso)
		 * 
		 */
		
		
		
		ResultSet rs = st.executeQuery(sql);
		
		/*
		 * 6. Attendo il risultato e lo processo
		 * 
		 * Per motivi di performance, quando si esegue una query di selezione
		 * su tabelle molto voluminose, JDBC non restituisce immediatamente l'intero
		 * recordset selezionato ma solo una sua parte (di default, 100 record).
		 * Dopodiché, man mano che l'applicazione processa questi 100 record, attraverso
		 * un meccanismo automatico e trasparente, vengo richiesti al DBMS i successivi 100
		 * record, e così via, sino a che non si esaurisce l'intero resultset. 
		 */
		
		while(rs.next()) {
			/*
			 * Nel fare il parsing degli argomenti, posso utilizzare
			 * sia una sintassi posizionale (i.e., fornisco l'indice dell'attributo selezionato)
			 * oppure una sintassi nominale (i.e., fornisco il nome dell'attributo selezionato)
			 */
			System.out.println("Soggetto: " + rs.getString(1) + " " + rs.getString(2));
			System.out.println("Soggetto: " + rs.getString("COGNOME") + " " + rs.getString("NOME"));
			System.out.println("Data nascita:" + rs.getInt("GIORNONASCITA") + "/"+rs.getInt("MESENASCITA")+"/"+rs.getInt("ANNONASCITA"));
			System.out.println();
		}
		
		int idSoggetto = 7;
		String nomeSoggetto = "Giulio";
		sql = "update Anagrafe set NOME = '" + nomeSoggetto + "' WHERE ID = "+idSoggetto;
		System.out.println("sql: " + sql);
		int nRows = st.executeUpdate(sql);
		System.out.println("Righe modificate: " + nRows);
		
		String cognome = "Verdi";
		String nome = "Gianni";
		int giornoNascita = 2;
		 meseNascita = 1;
		int annoNascita = 1980;
		String sesso = "Maschio";
		
		sql = "INSERT INTO Anagrafe (COGNOME, NOME, GIORNONASCITA, MESENASCITA, ANNONASCITA, SESSO)"
				+ " VALUES ('" + cognome + "','" + nome + "'," + giornoNascita + ","+meseNascita + 
				","+annoNascita +",'"+sesso+"')";
		System.out.println("sql:" + sql);
		nRows = st.executeUpdate(sql);
		System.out.println("Righe inserite: " + nRows);
		
		/*
		 * 7. Chiudo tutto
		 * 
		 * Una volta terminata la sessione di lavoro devo chiudere la connessione
		 * precedentemente creata per liberare delle risorse del DBMS
		 */
		
		conn.close();

		System.out.println("Connessione chiusa");

	}

}