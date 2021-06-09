package gebd.neo4j;

import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;//import org.neo4j.driver.Values.parameters;

public class TestNeo4J {
	
	public static void main (String[] args) {
		/*
		 * La procedura da utilizzare per collegarsi a Neo4J
		 * da java ricorda quella già vista per JDBC.
		 * Concettualmente, i passi sono gli stessi.
		 * La sintassi esatta e le classi utilizzate. purtroppo,
		 * sono diverse.
		 * - si istanzia il driver
		 * - si crea una connessione 
		 * - si crea una sessione 
		 * - si definisce il contenuto di una query e la si esegue
		 * -- se ne interpreta il risultato
		 */
		
		/*
		 * Definiamo la stringa di connessione a Neo4j
		 * Utilizziamo il protocollo 'bolt' per collegarsi
		 * al nostro stesso computer sulla porta 7687
		 */
	String uri = "bolt://localhost:7687";

		/*
		 * Forniamo le nostre credenziali di acesso
		 */
	AuthToken token = AuthTokens.basic("neo4j", "InfoStud=merda9");

		/*
		 * Istanziamo il driver e creiamo la prima connessione
		 */
	Driver driver = GraphDatabase.driver(uri, token);

		/*
		 * Aprimao una sessione di lavoro
		 */
	Session s = driver.session(); 
	
	System.out.println("Connessione stabilita!");
	
	//Session s = driver.session(); 
	String query="match (n:studente) return n.nome as nome, n.cognome as cognome limit 20";
	
	/*
	 * L'esecuzione di una query comporto la restituzione 
	 * di un result set incapsulato in un oggetto Result
	 */
	Result result = s.run(query);
	
	/*
	 * Per poter consumare il contenuyo di un result set
	 * debbo scorrerlo una riga alla volta
	 */
	while(result.hasNext()) {
		Record r = result.next();
		/*
		 * Gli oggwetti contenti i valori estrapolati dalla 
		 * query sono amorfi, gli attribuisco un tipo 
		 * e li converto al volo utilizzanfo metodi della forma
		 * asX, dove X è il tipo desiderato
		 */
		String nome = r.get("nome").asString();
		String cognome = r.get("cognome").asString();
		System.out.println("Nome: " + nome + "Cognome:" + cognome); 
	}	
	
	query = "match (n_studente)-[e:esame]-(n:insegnamento) where n.matricola = $matricola return m.nome as titolo, e.voto as voto";
	String matricola = "1506877653";
	
	Result r=s.run(query, Values.parameters("matricola", matricola));
	while(r.hasNext()) {
		Record r1 = result.next();
		System.out.println("Titolo: " + r1.get("titolo").asString());
		System.out.println("Voto: " + r1.get("voto").asInt());

	}
	
	s.close();
	}
}
