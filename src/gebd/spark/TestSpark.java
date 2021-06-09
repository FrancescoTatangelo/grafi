package gebd.spark;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class TestSpark {

	/*
	 * Applicazione di prova di Spark
	 * Faremo un po' di text processing
	 */
	public static void main(String[] args) {

		/*
		 * Per default, le esecuzioni di applicazioni
		 * Spark possono generare numerosi messaggi a schermo,
		 * solitamente descriventi il buon andamento o meno
		 * dell'esecuzione.
		 * Per rendere leggibile l'output dell'applicazione,
		 * decidiamo di sopprimere tutti questi messaggi,
		 * tranne che nel caso di errori
		 * 
		 */
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);
		
		/*
		 * Per poter iniziare a lavorare con Spark,
		 * dobbiamo dapprima definirne la configurazione
		 * attraverso un nuovo oggetto di tipo SparkConf
		 */
		SparkConf sc = new SparkConf();
		/*
		 * Definiamo alcune proprietà di base:
		 * - come si chiama la nostra applicazione
		 */
		sc.setAppName("La mia prima applicazione Spark");
		/*
		 * - il contesto di esecuzione dell'applicazione,
		 * attraverso il metodo setMaster. Questi può
		 * assumere tre diversi valori: local, cluster, yarn.
		 * Nella modalità local, è possibile indicare il
		 * numero di core da riservare a Spark, indicandolo
		 * tra parentesi quadra. * significa: usali tutti.
		 */
		sc.setMaster("local[*]");
		
		/*
		 * Una volta definita la configurazione iniziale,
		 * posso avviare Spark attraverso l'oggetto 
		 * JavaSparkContext
		 */
		JavaSparkContext jsc = new JavaSparkContext(sc);
		
		/*
		 * Di base, le applicazioni Spark creano e processano
		 * delle RDD. Queste possono essere viste come dei
		 * DataFrame ...distribuiti.
		 * Esistono diversi modi per creare una RDD,
		 * il più semplice è attraverso il metodo parallelize()
		 */
		
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(5);
		lista.add(12);
		lista.add(4);
		
		/*
		 * Attraverso il metodo parallelize, è possibile
		 * trasformare una collection in una RDD.
		 * La variabile JavaRDD appena creata non punta ad
		 * una struttura dati in memoria che mantiene quei dati.
		 * Piuttosto, essa è una sorta di stub contenente al
		 * suo interno dei puntatori ai nodi del sistema distribuito
		 * che ospitano le varie parti di quella struttura dati.
		 * 
		 * Da questo momento in poi, ogni operazione eseguita
		 * su una RDD, non sarà eseguita localmente all'applicazione
		 * Driver, ma sarà richiesta a tutti i nodi che detengono
		 * una porzione di quella struttura dati.
		 * 
		 * E' possibile interagire con una RDD
		 * per mezzo di:
		 * - azioni: tipicamente, processa parte o tutta una RDD,
		 * e restituisce uno o più valori scalari
		 * - trasformazioni: tipicamente, processa parte, tutta o
		 * più di una RDD, e restituisce altre RDD
		 */
		
		JavaRDD<Integer> dLista = jsc.parallelize(lista);
		
		/*
		 * Alcuni esempi di azioni
		 */
		
		System.out.println("Dimensione della lista: " + dLista.count());
		
		System.out.println("Primo elemento della lista: " + dLista.first());

		System.out.println("Tot elementi della lista: " + dLista.take(2));

		/*
		 * E' possibile trasferire il contenuto di una RDD in una collezione
		 * in memoria nel driver attraverso l'azione collect()
		 */
		
		List<Integer> lista2 = dLista.collect();
		
		/*
		 * Risolviamo, per tappe, il problema del word count
		 * introducendo, per l'occasione, alcune delle trasformazioni
		 * (e sono tante) supportate da Spark
		 */
		
		List<String> lines = new ArrayList<String>();
		lines.add("sopra la panca");
		lines.add("la capra campa");
		lines.add("sotto la panca");
		lines.add("la capra crepa");
		
		JavaRDD<String> dLines = jsc.parallelize(lines);
		
		/*
		 * Per iniziare, risolviamo una versione semplificata del problema,
		 * nella quale vogliamo misurare la taglia complessiva di queste stringhe.
		 * 
		 * Da risolvere in due step:
		 * - map step: conto la taglia di ogni riga
		 * - reduce step: sommo i conteggi
		 */
		
		
		/*
		 * Eseguiamo una istruzione di map su tutte le stringhe
		 * presenti in dLines. Con questa istruzione, chiediamo di 
		 * valutare e restituire la taglia di ciascuna di queste stringhe.
		 * In output, avremo una JavaRDD con l'elenco delle taglie.
		 * 
		 * Come argomento del metodo map dobbiamo fornire la descrizione
		 * dell'istruzione che vogliamo ciascun executor vada ad eseguire
		 * sulla porzione di dati in proprio possesso.
		 * Esistono due diversi modi di farlo con Java/Spark:
		 * - le funzioni lambda
		 * - l'implementazione di interfacce
		 * 
		 * 1. Funzioni lambda
		 * una caratteristica del linguaggio Java (ma presente anche altrove)
		 * che consente di definire in modo estremamente sintetico una certa funzione
		 * sugli elementi di una collezione.
		 * Introduco una variabile che descrive il generico elemento di quella collezione,
		 * seguita dalla sequenza '->' e poi dal tipo di elaborazione cui voglio sottoporlo
		 */
		 JavaRDD<Integer> dSize= dLines.map(x -> x.length());
		 System.out.println(dSize.collect());
		
		 /*
		  * Reduce step: sommiamo la taglia di tutte le stringhe attraverso
		  * una funzione di reduce
		  */
		 Integer size =	dSize.reduce((x,y)->x+y);
		 System.out.println("Il numero di caratteri complessivo è: "+ size);
		
		 /*
		  * Risolviamo adesso la versione originale del problema, di nuovo in
		  * due step:
		  * - map step: estriamo tutte le parole presenti in una linea,
		  *   associandovi la frequenza 1
		  * - reduce step: aggreghiamo tutte le occorrenze di una stessa 
		  *   parola, sommandone le frequenze
		  *   
		  *   Nel primo passo, processiamo ciascuna stringa estrapolando
		  *   tutte le parole presenti al suo interno. Poiché, in questo caso,
		  *   una stringa può dar vita a numerose stringhe, usiamo la variante
		  *   di map dal nome flatMap.
		  *   
		  *   In casi come questo, potrebbe essere difficile, se non impossibile,
		  *   descrivere il tipo di elaborazione che abbiamo in mente
		  *   attraverso una funzione lambda.
		  *   Per questo, dobbiamo utilizzare una strada alternativa.
		  *   
		  *   Innanzitutto, dal momento che per ogni stringa in input
		  *   l'output potrebbe contenere 0, 1 o n stringhe, non usiamo
		  *   più la trasformazione "map" bensì la trasformazione "flatMap".
		  *   Sappiamo dell'esistenza di questa trasformazione non perché
		  *   ce l'ha detto il docente ma perché abbiamo consultato la documentazione
		  *   . Nella documentazione c'è scritto cosa fa una certa trasformazione
		  *   ed anche come sia possibile specificarne il comportamento.
		  */
		 
		 JavaRDD<String> dWords = dLines.flatMap(new EstrapolaParole());
		 
		 /*
		  * Utilizzo una trasformazione mapToPair per convertire la JavaRDD<String>
		  * in JavaPairRDD<String, Integer>, accostando a ciascuna parola la frequenza 1
		  * Per confezionare la coppia (chiave, valore) utilizziamo la classe Tuple2 
		  * (da importare)
		  */
		 JavaPairRDD<String, Integer> dWords2 = dWords.mapToPair(x -> new Tuple2(x,1));

		 JavaPairRDD<String, Integer> dWords3 = dWords2.reduceByKey((x,y)->x+y);
		 System.out.println(dWords3.collect());		
		 
		 
		 /*
		  * Di seguito una implementazione alternativa a quella appena vista
		  * dove utilizziamo un minor numero di trasformazioni per ottenere
		  * lo stesso risultato
		  */
		 
		 /*
		  * Nella realtà, è estremamente improbabile che i dati
		  * da processare si trovino già in memoria. Più spesso,
		  * inizialmente si trovano su disco o presso un database remoto
		  * o sul web.
		  * 
		  * Negli scenari d'uso di Spark, sovente, i file di input
		  * sono molto, ma molto, voluminosi. In questi casi, si adoperano
		  * sistemi concepiti per Big Data, come Hadoop/HDFS.
		  * In casi più semplici, è possibile assumere che un file di input
		  * si trovi sul nostro pc sotto forma di file di testo.
		  * E' il caso che esploriamo ora.
		  */

		 /*
		  * Il metodo textFile di JavaSparkContext carica il contenuto
		  * di un file di testo *direttamente* in una JavaRDD
		  */
		 JavaRDD<String> dLines1 = jsc.textFile("data/testo.txt");

		 System.out.println(dLines1.take(2));

		 String pattern = "la";//"abracadabra".indexOf("bra");

		 
		 JavaPairRDD<String, Integer> dWords4 = dLines1.flatMapToPair(new EstrapolaParole2());
		 //dWords4 =dWords4.filter(x->x._1.indexOf(pattern)>-1);
		 
		 JavaPairRDD<String, Integer> dWords5 = dWords4.reduceByKey((x,y)->x+y);
		 System.out.println(dWords5.collect());
		 
		 dWords5 =dWords5.filter(x->x._1.indexOf(pattern)>-1);

		 
		 /*
		  * Una volta conclusa la nostra elaborazione,
		  * occorre fissare il risultato su disco o su un database.
		  * Esploriamo la prima possibilità.
		  * Anche in questo nella realtà si utilizzano tecnologie
		  * specializzate per i Big data, ma è comunque possibile
		  * salvare un risultato sotto forma di file di testo
		  */
		 
		 /*
		  * Il metodo saveAsTextFile mi consente di salvare su
		  * disco il contenuto di una RDD
		  */
		 
		 System.out.println("Output:" + dWords.take(10)); 
		 //commentando dopo dWords4 e mettendo 4 in dWords di qui risolvo il primo esercizio del testSpark
		 //dWords5.saveAsTextFile("frequenze");
		 
		 
	}

}