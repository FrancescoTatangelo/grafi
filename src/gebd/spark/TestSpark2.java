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

public class TestSpark2 {

	/*
	 * Applicazione di prova di Spark Faremo un po' di text processing
	 */
	public static void main(String[] args) {
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);
		SparkConf sc = new SparkConf();
		sc.setAppName("La mia prima applicazione Spark");
		sc.setMaster("local[*]");
		JavaSparkContext jsc = new JavaSparkContext(sc);

		JavaRDD<String> dLines1 = jsc.textFile("data/testo1.txt");
		JavaRDD<String> dLines2 = jsc.textFile("data/testo2.txt");
		
		/*
		 * Necessario a valutare la somma delle frequenze di
		 * ciascuna parola in ciascuno dei due file
		 */
		dLines1 = dLines1.union(dLines2);//per la seconda richiesta del secondo es
		
		JavaPairRDD<String, Integer> dWords4 = dLines1.flatMapToPair(new EstrapolaParole2());
		JavaPairRDD<String, Integer> dWords5 = dWords4.reduceByKey((x, y) -> x + y);
		//le due righe che seguono si levano per la seconda richiesta del secondo es
		JavaPairRDD<String, Integer> dWords6 = dLines2.flatMapToPair(new EstrapolaParole2());
		JavaPairRDD<String, Integer> dWords7 = dWords6.reduceByKey((x, y) -> x + y);
		
		
		//System.out.println("Output:" + dWords5.keys().take(10)); //per la seconda richiesta del secondo es
		//le due righe che seguono si levano per la seconda richiesta del secondo es
		System.out.println("Output:" + dWords5.join(dWords7).keys().take(10));
		dWords5.saveAsTextFile("frequenze2");

	}

}