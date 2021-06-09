package gebd;

/*
 * In questa classe faremo uso di altre classi presenti 
 * nel pacchetto java.util
 */
import java.util.*;
/*
 * Classe utilizzata per incapsulare gli attributi
 * relativi al concetto di Studente e per poterli manipolare
 * in accordo alle esigenze applicative. 
 * Nella nostra idea, attraverso questa classe, sarà possibile
 * eseguire tutte le operazioni che riguardano il ciclo di vita
 * delle variabili che descrivono la carriera di uno studente.
 * 
 * All'interno di questa classe saranno presenti delle variabili
 * che prendono il nome di *attributi* (fields) o *variabili membro* della classe.
 * Descrivono in pratica le diverse sfaccettature di quel concetto (e.g., lo Studente)
 * 
 * In questo modo mi consentono di utilizzare una classe come contenitore di dati,
 * tenendo assieme valori/variabili che altrimenti sarebbero stati/e indipendenti.
 * 
 * L'idea è che nella mia applicazione piuttosto che gestire separatamente 
 * ed isolatamente le diverse variabili, le gestisco all'interno di queste classi.
 * 
 * 
 * Da questo momento in poi, la classe Studente diventa una sorta di classe prototipo
 * (la chiamerò classe *padre*) da utilizzare per fissare tutti gli aspetti, in termini
 * di variabili membro e di metodi, comuni alle diverse classi specializzate per gestire
 * gli iscritti ai diversi corsi di laurea. Per questo motivo, non voglio che le
 * applicazioni possano direttamente creare oggetti di questo tipo, pretenderò piuttosto
 * che passino sempre attraverso una delle classi figlie. Per imporre questo vincolo
 * definisco questa classe come classe astratta (*abstract*). Non è possibile
 * creare oggetti di un tipo astratto.
 * 
 * In questo frangente elenco anche le eventuali interfacce implementate
 * da questa classe
 */
public abstract class Studente implements Autodescrivente {

	/*
	 * Le variabili membro di una classe sono dotate di un indicatore di
	 * *visibilità* che stabilisce se una certa variabile è leggibile anche al di
	 * fuori della classe.
	 * 
	 * Le variabili membro di una classe possono avere diversi livelli di visibilità:
	 * public, protected, private, package. 
	 * Per default, se non diversamente indicato, una variabile membro è visibile
	 * a livello package. Questo significa che qualsiasi classe appartenente
	 * allo stesso pacchetto può accedere in lettura ed in scrittura a quella variabile.
	 * 
	 * A livello private, una variabile è visibile esclusivamente ai metodi di quella
	 * classe
	 * 
	 * A livello protected, una variabile è visibile ai metodi di quella classe
	 * ed ai suoi discendenti.
	 */
	 private String nome;
	 private String cognome;
	 private String cdl;
	 
	 
	 /*
	  * La scelta di gestire l'insieme degli esami sostenuti
	  * da un particolare soggetto utilizzando un array è discutibile,
	  * soprattutto in merito alla dinamicità della struttura dati.
	  * Come fare quando l'array è pieno? come fare quando è quasi del tutto inutilizzato?
	  * 
	  * Nella realtà, quando abbiamo bisogno di gestire collezioni di valori di diverso tipo
	  * in Java, piuttosto che farlo utilizzando gli array, utilizziamo delle classi 'Collection'.
	  * Questo termine indica una libreria di classi presenti nell'installazione di Java
	  * che consentono di memorizzare, mantenere e processare delle collezioni di valori,
	  * ottimizzate rispetto a certe operazioni.
	  * 
	  * Esistono diversi tipi di collections. Il primo che approfondiremo è di tipo
	  * List. Con questo termine intendiamo una struttura dati lineare che tiene
	  * traccia dell'ordine con cui gli elementi sono inseriti al suo interno
	  * e rende possibile l'accesso ad essi mediante un indice (per certi versi,
	  * una sorta di super-array).
	  * Esistono in Java diverse implementazioni di List, noi useremo ArrayList
	  */
//	 protected Esame[] esami;
	 /*
	  * L'elenco degli esami sarà mantenuto attraverso una istanza della classe
	  * ArrayList. Questa classe imlementa una collection di tipo List.
	  * Quando si implementano queste classi non si conosce a priori il tipo di
	  * dato con cui si andrà a lavorare. Una lista potrebbe essere usata
	  * per mantenere un elenco di stringhe, un elenco di numeri, un elenco di esami.
	  * Neanche posso pensare di avere una diversa implementazione per ogni diverso
	  * tipo. Per gestire casi come questi è possibile definire in Java delle classi
	  * che fanno uso di tipi 'Generics'. Questo significa che il codice di quelle
	  * classi non è specializzato per gestire un particolare tipo di dato ma funziona
	  * con un tipo di dato variabile, indicato da input nel momento in cui si crea
	  * un oggetto di quel tipo.
	  * 
	  * Nel nostro caso, se vogliamo creare un ArrayList che conterrà oggetti di tipo
	  * Esame, dichiariamo una variabile di tipo ArrayList<Esame> dove, tra parentesi
	  * angolari indichiamo il tipo desiderato
	  * 
	  */
	 protected ArrayList<Esame> esami;
	 
	 
	 /*
	  * I contenitori di tipo Collection contengono anche altre classi
	  * ottimizzate per l'accesso ai dati con la semantica di un dizionario.
	  * Ci riferiamo a strutture dati nelle quali i nostri dati sono organizzati
	  * sotto forma di coppie (chiave,valore).
	  * Su queste strutture dati sono presenti principalmente le seguenti operazioni:
	  * - insert(K,V)
	  * - search(K)
	  * - delete(K)
	  * Assumiamo quindi che i nostri valori V siano contrassegnati con delle chiavi
	  * K, attraverso le quali possiamo indicizzare quei valori inserendoli
	  * nel dizionario e richiamarli efficientemente. La particolarità dei dizionari
	  * è che la loro implementazione interna è particolarmente efficiente nell'esecuzione 
	  * delle operazioni sopra descritte.
	  * Java offre diverse implementazioni di dizionari, noi utilizziamo HashMap.
	  * Questi prende in input, come parametri, due tipi corrispondenti, rispettivamente,
	  * al tipo usato per codificare la chiave ed al tipo usato per codificare il valore.
	  * Nel nostro caso, le chiavi saranno numeri (i codici identificativi degli
	  * esami) ed i valori saranno oggetti di tipo Esame. Nel caso in cui una delle due
	  * classi corrisponda ad un tipo primitivo, non posso indicare direttamente quel
	  * tipo perché la classe HashMap si aspetta di ricevere una classe.
	  * Per ovviare a queste situazioni, Java mette a disposizioni delle classi
	  * (dette classi Wrapper) che sono contenitori di tipi primitivi:
	  * Integer
	  * Long
	  * Double
	  * Boolean
	  * Float
	  * Short
	  */
	 protected HashMap<Integer,Esame> idToEsame;
	 
	 
//	 protected int numeroMassimoEsami = 30;
//	 protected int numeroEsami = 0;
	 
	 /*
	  * Nella mia idea, nel momento in cui si crea un oggetto di tipo Studente
	  * bisogna specificarne da subito nome e cognome. Questo potrebbe essere
	  * fatto ricordandosi di eseguire tempestivamente i metodi setNome e setCognome.
	  * Tuttavia, questo potrebbe non avvenire. 
	  * In Java è possibile definire dei blocchi di istruzioni la cui esecuzione
	  * è contestuale alla creazione di nuovi oggetti di un certo tipo. Questi
	  * blocchi di istruzioni portano il nome di *costruttori*. Sono dei particolari
	  * metodi che, per definizione, sono eseguiti all'atto della creazione
	  * dell'oggetto del tipo in cui sono presenti ed hanno le seguenti particolarità:
	  * - il nome del metodo costruttore deve coincidere con il nome della classe
	  * che lo ospita
	  * - non presentano alcun valore di ritorno
	  * 
	  * Se in una classe Java è presente un costruttore, occorre necessariamente
	  * passare attraverso di esso per creare oggetti di quel tipo. 
	  */
	 
	 public Studente(String cognome, String nome) {
		 /*
		  * In una classe Java, possono coesistere numerosi costruttori
		  * Spesso i diversi costruttori sono l'uno una variante degli altri.
		  * PIuttosto che portare avanti numerose implementazioni di costruttori
		  * tutti simili tra loro, ne posso individuare uno sufficientemente generale
		  * da includere anche gli altri. Per gli altri, piuttosto che 
		  * ripetere la definizione di ciascun costruttore, posso poi richiamare
		  * il costruttore più generale mediante la keyword this.
		  */
		 this(cognome, nome, "na");
	 }
	 
	 /*
	  * E' possibile avere in una stessa classe Java numerosi costruttori,
	  * con lo stesso nome (overloading), a patto che differiscano
	  * nell'elenco degli argomenti
	  * Nell'esempio sottostante, un costruttore alternativo al primo
	  * in cui si indica anche il corso di laurea
	  */
	 public Studente(String cognome, String nome, String cdl) {
		 this.cognome = cognome;
		 this.nome = nome;
		 this.cdl = cdl;
		 
		 /*
		  * Quando creiamo un nuovo arraylist non abbiamo bisogno
		  * di specificarne la taglia. Si tratta di una struttura dati dinamica,
		  * per cui appena creata possiede una certa taglia. Dopodiché, se necessario
		  * la sua taglia sarà automaticamente incrementata o decrementata sulla
		  * base del suo fattore di carico.
		  */
		 esami = new ArrayList<Esame>();
		 //		 esami = new Esame[numeroMassimoEsami];
		 idToEsame = new HashMap<Integer, Esame>();

	 }
	 
	 
	 /*
	  * Non potendo e non dovendo fornire accesso diretto
	  * alle variabili membro di una classe, mi invento dei metodi
	  * utilizzabili per regolamentare questo accesso
	  */
	 public void setNome(String nome) {
		 /*
		  * Il fatto di convogliare tutte le richieste di accesso (in lettura
		  * o in scrittura) attraverso un unico metodo mi consente di codificare
		  * in quel metodo la logica per cui le richieste possono essere accolte o meno
		  * 
		  */
		 if (nome.length() == 0) {
			 return;
		 }
		 
		 /*
		  * Se in un metodo è presente una variabile il cui nome
		  * coincide con quello di una variabile membro, la variabile membro
		  * (in quel metodo) viene "oscurata" dalla variabile locale
		  * 
		  * E' possibile far riferimento ad una variabile membro di una classe
		  * attraverso la keyword this. Questa rappresenta un puntatore implicito
		  * al particolare oggetto in cui ci troviamo
		  */
		 this.nome = nome;
	 }
	 
	 /*
	  * Mi consente di accedere al contenuto della variabile nome in sola lettura
	  */
	 public String getNome() {
		 return nome;
	 }
	 
	 
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/*
	 * 
	 * Definiamo un metodo istanza con nome "leggiVoti" che acquisisce una sequenza
	 * di voti sotto forma di array di interi e vi fa riferimento attraverso la
	 * variabile voti. Il metodo è in grado in completa autonomia di acquisire da
	 * input una serie di valori interi corrispondenti ai voti conseguiti da un
	 * certo studente. Una volta che il risultato sarà pronto, verrà agganciato
	 * mediante la variabile voti. Nota: questo metodo non restituisce alcunché
	 */


	/*
	 * Implementiamo un metodo che data in input una serie di voti codificata sotto
	 * forma di array di interi, ne mostra a schermo il contenuto, senza restituire
	 * alcunché
	 * 
	 * Nel definire il contratto del metodo, devo specificare per ciascun argomento
	 * il tipo ed un identificatore che userò per manipolare quel valore all'interno
	 * del metodo
	 * 
	 * 	 * Da modificare utilizzando stavolta l'array 'esami'

	 */
	public String getVoti() {

		/*
		 * Posso sfogliare il contenuto di una lista quasi come se fosse un array
		 * unico accorgimento: utilizzo il metodo get() piuttosto che []
		 */
		/*
		for (int i = 0; i < esami.size(); i++) {
			System.out.println(esami.get(i).getVoto());
		}*/
		/*
		 * Noi non siamo interessati a conoscere la struttura dati usata internamente
		 * da queste classi per conservare certi valori. In un caso come questo,
		 * siamo esclusivamente interessati ad accedere ad ogni valore presente nella
		 * lista, tenendo conto dell'ordine di inserimento.
		 */
		
		/*
		 * Java mette a disposizione una variante del ciclo for utilizzabile
		 * per passare in rassegna il contenuto di una collection
		 */
		String voti = "";
		for (Esame e : esami) {
			voti += e.getVoto() + "\n";
		}
		return voti;

	}
	
	
	public String getDescrizione() {
		String descrizione = "Mi chiamo " + this.cognome + " " + this.nome+"\n";
		descrizione += "Questi sono i miei voti" + "\n";
		descrizione += getVoti();
		return descrizione;
	}

	/*
	 * Esempio di metodo istanza, processa il contenuto dell'array voti presente
	 * nella copia corrente della classe Studente. Per default, ciascun metodo
	 * istanza di una classe ha accesso alle variabili membro di quella classe senza
	 * dover utilizzare alcuno strumenti di accesso (tradotto, è come se le
	 * variabili in questione fossero definite all'interno di quel metodo)
	 * 
	 * Scorre la sequenza dei voti determinando e restituendo il voto più basso. Il
	 * voto minimo così individuato viene restituito all'applicazione che ha
	 * richiesto l'esecuzione di quel metodo. Nel caso in cui l'array sia vuoto,
	 * restituisce 0.
	 * Da modificare utilizzando stavolta l'array 'esami'

	 */
	public int cercaMinimo() {
		if (esami.size() == 0) {
			return 0;
		}

		if (esami.size() == 1) {
			return esami.get(0).getVoto();
		}

		int votoMinimo = 999;
		for (Esame e : esami) {
			if (e.getVoto() < votoMinimo) {
				votoMinimo = e.getVoto();
			}
		}

		return votoMinimo;

	}


	/*
	 * Crea un nuovo oggetto di tipo esame utilizzando le informazioni
	 * acquisite da input e lo aggiunge all'array esami.
	 * In seguito alla verbalizzazione dell'esame,
	 * viene restituito all'applicazione che ne ha fatto richiesta
	 * un numero identificativo univoco
	 * 
	 * Il valore di ritorno indica l'eventuale insorgenza di situazioni di errore,
	 * secondo la seguente codifica:
	 * -1 voto non valido
	 * -2 cfu non validi
	 * -3 insegnamento non valido ...
	 * 
	 * La soluzione appena descritta, per cui le diverse situazioni di errore
	 * vengono codificate senza interrompere il normale flusso di esecuzione dell'applicazione
	 * ma riciclando valori inizialmente pensati per altro è una pessima soluzione
	 * Se chi richiama questo metodo non è a conoscenza di questa codifica o se ne
	 * disinteressa, il codice di errore restituito come se fosse un valore legittimo
	 * inquina e corrompe il funzionamento del sistema circostante
	 * 
	 * Durante l'esecuzione di questo metodo potrebbero avvenire degli errori
	 * dovuto all'uso di valori non validi per la variabile voto.
	 * Laddove questo dovesse accadere, il metodo terminerà immediatamente 
	 * e restituirà una eccezione di tipo VotoException. Per indicare la presenza
	 * di una possibile eccezione si adopera la keyword throws nella firma del
	 * metodo
	 * 
	 */
	public int verbalizzaEsame(int voto, int cfu, String insegnamento) throws VotoException{
		if ((voto < 18) || (voto > 31)) {
			/*
			 * Se ricado in una delle situazioni per cui il voto viene
			 * ritenuto non valido, confeziono una nuova eccezione di tipo
			 * VotoException e la restituisco al chiamante
			 */
			throw new VotoException();
		}
		
		
		/*
		 * Creo un nuovo oggetto di tipo Esame per ospitare i dati 
		 * forniti da input
		 */
		Esame e = new Esame(voto, insegnamento, cfu);
		esami.add(e);
		idToEsame.put(e.getId(), e);
		
		/*
		 * Restituiamo, come valore di ritorno,
		 * il codice identificativo dell'esame appena creato
		 */
		return e.getId();
	}
	
	/*
	 * Consente di rimuovere un esame precedentemente
	 * verbalizzato
	 */
	public void eliminaEsame(int  id) {
		Esame e = cercaEsame(id);
		if (e != null) {
			esami.remove(e);
		}
	}
	
	/*
	 * Dato in input il codice identificativo di un
	 * certo esame, restituisce il riferimento a quell'esame,
	 * se esistente. Altrimenti, null
	 */
	public Esame cercaEsame(int id) {
		return idToEsame.get(id);
		
/*		for(Esame e:esami) {
			if (e.getId() == id) {
				return e;
			}
		}
		
		return null;*/
		
	}
	
	/*
	 * Il metodo per il calcolo della media non è presente sulla classe Studente.
	 * Piuttosto, ciascuna classe che andrà a derivare questa classe, dovrà implementarlo
	 * seguendo il seguente schema. Altrimenti, non sarà possibile istanziarla.
	 */
	public abstract double calcolaMedia();

}