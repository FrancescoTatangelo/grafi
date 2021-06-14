package gebd.spark;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import scala.Tuple2;

/*
 * Carica il contenuto di un file csv
 * contenente il report degli incidenti stradali a Roma
 * sotto forma di RDD e vi applica alcune operazioni
 */
public class AnalizzaIncidentiStradali {
	
	public static void main(String[] args) throws AnalysisException {
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);
		SparkConf sc = new SparkConf();
		sc.setAppName("Analizza Incidenti Stradali");
		sc.setMaster("local[*]");
		JavaSparkContext jsc = new JavaSparkContext(sc);
	
		JavaRDD<String> dLines = jsc.textFile("data/incidenti_roma_2020.csv");
		dLines = dLines.filter(x -> x.indexOf("Protocollo") == -1);
		JavaRDD<Incidente> dIncidente = dLines.map(x -> new Incidente(Integer.parseInt(x.split(";")[1]),x.split(";")[4],x.split(";")[6],Integer.parseInt(x.split(";")[0]),Integer.parseInt(x.split(";")[23])));

/*		List<Incidente> x = dIncidente.take(10);
		for (Incidente i : x) {
			System.out.println(i.getStrada1());
			System.out.println(i.getStrada2());
		}
		
		JavaPairRDD<String, Integer> dIncidente1 = dIncidente.mapToPair(i -> new Tuple2<String, Integer>(i.getStrada1(),1));
		JavaPairRDD<String, Integer> dIncidente2 = dIncidente1.reduceByKey((y,z)->y+z);
		System.out.println(dIncidente2.collect());
*/
		
		/*
		 * Spark supporta diverse modalità di utilizzo. 
		 * La più elementare e di più basso livello è quella basata
		 * sull'uso delle RDD. E' quella più complessa da utilizzare
		 * perché l'utente deve poi esprimere sotto forma di trasformazioni
		 * il tipo di elaborazione che in mente.
		 * 
		 * Molte delle operazioni eseguite su Spark e che abbiamo
		 * visto sinora sono assimilabili a delle query nel linguaggio
		 * SQL. Alla luce di questo, Spark mette a disposizione
		 * una modalità di funzionamento alternativa nella quale
		 * le nostre RDD possono essere direttamente interrogate
		 * usando una variante di SQL. Questa modalità
		 * si basa sull'uso di una differente struttura dati,
		 * di nome DataFrame/Dataset. Questa nuova struttura dati
		 * è più ricca di funzioni e di più alto livello rispetto
		 * alle RDD. Consente di ospitare un set di variabili
		 * tipizzate, abbandonando l'impostazione chiave valore.
		 * Un DataFrame è assimilabile ad una tabella. 
		 * 
		 * Di seguito, introduciamo brevemente le istruzioni necessarie
		 * per fare uso di questa modalità, utilizzando come esempio
		 * quello degli incidenti
		 * 
		 */
		
		/*
		 * Come primo passo, occorre inizializzare Spark
		 * daccapo per usare quest'altra modalità.
		 * Nel nostro caso, tenendo conto del fatto che Spark
		 * è stato già inizializzato.
		 */
		
		SparkSession spark = SparkSession.builder().appName("Esempio uso Dataframe").config(jsc.getConf()).getOrCreate();
	
		/*
		 * Ho creato una nuova istanza di Spark da utilizzare
		 * per la manipolazione di Dataframe. Come posso creare un
		 * Dataframe da zero? Ho due possibilità: 
		 * - convertire una RDD in un Dataframe
		 * - caricare il contenuto di un file CSV in memoria come Dataframe
		 */
		
		/*
		 * Converto il contenuto della RDD dIncidente in un
		 * Dataframe/Dataset DFIncidente. Nel fare la conversione,
		 * devo indicare a Spark la classe con cui sono fatti
		 * gli elementi della RDD dIncidente
		 */
/*
		Dataset<Row> DFIncidente = spark.createDataFrame(dIncidente, Incidente.class);
		DFIncidente.createTempView("incidenti");
		
		String sql = "SELECT * FROM incidenti where idGruppo = 5";
		Dataset<Row> result = spark.sql("SELECT * FROM incidenti where idGruppo = 5");
		result.write().format("csv").csv("incidenti5");
*/		
		/*
		 * Carico in memoria il contenuto di un file csv
		 * direttamente sotto forma di Dataset/Dataframe
		 * 
		 */
		
		Dataset<Row> DFIncidente = spark.read().option("header", "true").option("inferSchema", "true").option("delimiter", ";").csv("data/incidenti_roma_2020.csv");
		DFIncidente.createTempView("incidenti");
		
		Dataset<Row> result = spark.sql("SELECT * FROM incidenti where Gruppo = 1");
		result.write().mode("append").format("csv").csv("incidenti5");

	}
	

	
	
}