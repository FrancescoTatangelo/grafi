package grafi;

import java.util.ArrayList;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.graphframes.GraphFrame;

public class Main {
	public static void main(String[] args) {
		ArrayList<Persona> persone = new ArrayList<>();
		persone.add(new Persona("a", "Alice", 34));
		persone.add(new Persona("b", "Bob", 36));
		persone.add(new Persona("c", "Charlie", 30));
		persone.add(new Persona("d", "David", 29));
		persone.add(new Persona("e", "Esther", 32));
		persone.add(new Persona("f", "Fanny", 36));
		
		SparkSession spark = SparkSession.
				builder().
				master("local[*]").
				appName("Test grafi").
				getOrCreate();
		
		spark.sparkContext().setLogLevel("ERROR");
		
		Dataset<Row> vertici = spark.createDataFrame(persone, Persona.class);
		
		ArrayList<Relazione> relazioni = new ArrayList<>();
		relazioni.add(new Relazione("a", "e", "friend"));
		relazioni.add(new Relazione("f", "b", "follow"));
		relazioni.add(new Relazione("c", "e", "friend"));
		relazioni.add(new Relazione("a", "b", "friend"));
		relazioni.add(new Relazione("b", "c", "follow"));
		relazioni.add(new Relazione("c", "b", "follow"));
		relazioni.add(new Relazione("f", "c", "follow"));
		relazioni.add(new Relazione("e", "f", "follow"));
		relazioni.add(new Relazione("e", "d", "friend"));
		relazioni.add(new Relazione("d", "a", "friend"));
		
		Dataset<Row> archi = spark.createDataFrame(relazioni, Relazione.class);
		
		GraphFrame grafo = new GraphFrame(vertici, archi);
		
		spark.sparkContext().setCheckpointDir("checkpoint_grafi");
		
		// grafo.degrees().show();
		// grafo.inDegrees().show();
		// grafo.connectedComponents().run().show();
		
		GraphFrame amicizie = grafo.filterEdges("relationship = 'friend'");
		// amicizie.edges().show();
		// amicizie.connectedComponents().run().show();
		
		// grafo.find("(src)-[e]->(dst); (dst)-[e2]->(src)").show();
		
		// TODO: rivedere caso "Alice"
		// grafo.bfs().fromExpr("age > 30").toExpr("age < 31").run().show();
		
		ArrayList<Object> id_landmarks = new ArrayList<>();
		id_landmarks.add("f");
		id_landmarks.add("c");
	
		grafo.shortestPaths().landmarks(id_landmarks).run().show();
		
		
		grafo.degrees().show();
	}
}
