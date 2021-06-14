package gebd.spark;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;
import java.util.Scanner;

public class InstacartAnalyzer {

	public static void main(String[] args) {
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);
		SparkConf sc = new SparkConf();
		sc.setAppName("Instacart Analyzer");
		sc.setMaster("local[*]");
		JavaSparkContext jsc = new JavaSparkContext(sc);
		
		JavaRDD<String> dOrders1 = jsc.textFile("data/orders.csv");
		JavaRDD<String> dOrders2 = dOrders1.filter(x ->x.indexOf("moved")==-1);
		
		JavaPairRDD<String, Integer> dProducts1 = dOrders2.mapToPair(x->new Tuple2<String,Integer>(x.split(",")[1],1));
		JavaPairRDD<String, Integer> dProducts2 = dProducts1.reduceByKey((x,y)->x+y);
		
//		System.out.println("Prodotto più spesso venduto: " + dProducts2.max(new ProductComparator()));
		
		JavaPairRDD<String, String> dOrders3 = dOrders2.mapToPair(x->new Tuple2<String, String>(x.split(",")[0],x.split(",")[1]));
		JavaPairRDD<String, String> dOrders4 = dOrders3.reduceByKey((x,y)->x+","+y);
		JavaRDD<String> dOrders5 = dOrders4.values();
		JavaPairRDD<String, Integer> dOrders6 = dOrders5.flatMapToPair(new EstraiCoppie());
		JavaPairRDD<String, Integer> dOrders7 = dOrders6.reduceByKey((x,y)->x+y);
		System.out.println("Coppia/e prodotti più spesso acquistati assieme : " +dOrders7.max(new ProductComparator()));
	
		JavaPairRDD<String, String> dOrders8 = dOrders7.flatMapToPair(new DisaccoppiaProdotti());
		JavaPairRDD<String, String> dOrders9 = dOrders8.reduceByKey((x,y)->x+","+y);
		JavaPairRDD<String, String> dOrders10 = dOrders9.mapToPair(new TrovaMassimo());
		
		Scanner scan;
		System.out.println("Premi invio per concludere l'esecuzione");
		scan = new Scanner(System.in);
		scan.next(); 
		
		System.out.println(dOrders10.take(20));

		
	}

}