package gebd.spark;

import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class TrovaMassimo implements PairFunction<Tuple2<String, String>, String, String> {

	/*
	 * Data in input una tupla nel formato
	 * K=p
	 * V=p1,f1,p2,f2,...,pn,fn
	 * restituisce in output la tupla
	 * K=p
	 * V=pi,fi
	 * dove fi è la frequenza massima
	 */
	@Override
	public Tuple2<String, String> call(Tuple2<String, String> t) throws Exception {
		String V = t._2;
		/*
		 * Le posizioni dispari all'interno di prodotti sono occupate
		 * dalle frequenze, le posizioni pari dai product id
		 * Nel mio caso, sono interessato ad analizzare le sole frequenze
		 * in circa di quella massima
		 */
		String[] prodotti = V.split(",");
		/*
		 * SOno certo del fatto che la sequenza di prodotti ne contenga almeno uno
		 * posso quindi assumere la frequenza del primo prodotto
		 * come frequenza massima in fase di inizializzazione 
		 */
		int maxFreq = Integer.parseInt(prodotti[1]);
		String maxProductId = prodotti[0];
		for (int i = 3; i < prodotti.length; i+=2) {
			if (Integer.parseInt(prodotti[i]) > maxFreq) {
				maxFreq = Integer.parseInt(prodotti[i]);
				maxProductId = prodotti[i-1];
			}
		}
		
		
		return new Tuple2<String, String>(t._1, maxProductId+","+maxFreq);
		
		
	}

}