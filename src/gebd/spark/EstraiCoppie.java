package gebd.spark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.api.java.function.PairFlatMapFunction;

import scala.Tuple2;

/*
 * Data in input una stringa nella forma p1,p2,p3,4
 * restituisce in output tutte le possibile coppie pi,pj
 * dove i<>j, senza ripetizioni, accompagnate dal valore 1
 */
public class EstraiCoppie implements PairFlatMapFunction<String, String, Integer> {

	
	public Iterator<Tuple2<String,Integer>> call(String carrello){
		/*
		 * "p1,p2,p3,p4"
		 */
		List<Tuple2<String, Integer>> output = new ArrayList<Tuple2<String, Integer>>();
		
		String[] prodotti = carrello.split(",");
		for (int i = 0; i < prodotti.length-1; i++) {
			for (int j = i+1; j < prodotti.length; j++) {
				String p1 = prodotti[i];
				String p2 = prodotti[j];
				/*
				 * Normalizziamo la struttura delle tuple in modo che al primo
				 * posto compaia sempre il prodotto minore
				 */
				if (p1.compareTo(p2) > 0) {
					String xx = p1;
					p1 = p2;
					p2 = xx;
				}
				
				output.add(new Tuple2<String, Integer>(p1+","+p2,1));
			}
		}
		
		return output.iterator();
			
		}
		
	}
	
