import java.util.Scanner;
public class AnalisiVoti {
			public static void main(String[] args) {
				int voto;
				int[] voti;
				int[] cfu;
				int votoMax;
				int votoMin;
				int index_min=0;
				int cfu_app;
				int nrEsami = 0;
				int cfutot =0;
				
				int votoMinimo = 999;
				int votoMassimo = 0;
				int somma = 0;
				
				Scanner sc = new Scanner(System.in);

				System.out.println("inserisci il numero di esami da processare");
				nrEsami=sc.nextInt();
				
				
				
				voti = new int[nrEsami];
				cfu = new int[nrEsami];
			
				
				for(int i=0; i<nrEsami; i++) {
					System.out.println("inserisci il prossimo voto");
					voto=sc.nextInt();
					voti[i]=voto;
					
					System.out.println("inserisci i crediti dell'esame");
					cfu_app=sc.nextInt();
					cfu[i]=cfu_app;
					
			
					if (voto > votoMassimo) { 
						votoMassimo = voto;
					}
				
					if (voto < votoMinimo) {
						votoMinimo = voto;
						//System.out.println("un nuovo minimo");
						index_min=i;
					} else if (voto == votoMinimo) {
						//System.out.println("SONO NEL ELSE IF");

						if (cfu[i]>cfu[index_min]) {
							//System.out.println("SONO NEL IF");
							index_min=i;
						}
					}
					//System.out.println("indice voto minimo " + index_min);

					}
					
				
				
				
				//System.out.println("numero esami: " + (nrEsami));
				System.out.println("voto minimo: " + votoMinimo);
				System.out.println("voto massimo: " + votoMassimo);
				
				for(int i=0; i<nrEsami;i++) {
					somma += voti[i];
				}
				System.out.println("voto medio: " + (float)somma/nrEsami);
				
				somma=0;
				
				for (int i=0; i<nrEsami; i++) {
					somma += (voti[i]*cfu[i]);
					cfutot += cfu[i];
				}
				System.out.println("media pesata:" + (float)somma/cfutot);
				
				somma=0;
				
				for(int i=0; i<nrEsami; i++) {
					if (i!=index_min) {
					somma+=voti[i];
					}}
				System.out.println("media senza considerare il voto peggiore:" + (float)somma/(nrEsami-1));
				//System.out.println("indice voto minimo " + index_min);
				
				somma=0;
				cfutot=0;

				for(int i=0; i<nrEsami; i++) {
				/*come la media pesata di prima, ma ogni volta verifichiamo se non sia il voto peggiore 
				 * a cfu massimo, in caso si scarta. Forse un if per ogni esame è un po' impegnativo...
				 */
					if (i!=index_min) {
						somma+= (voti[i]*cfu[i]);
					    cfutot += cfu[i];
					}
					
				}
				System.out.println("media pesata senza considerare il voto peggiore:" + (float)somma/cfutot);

				votoMin=999;
				for (int i=0; i<nrEsami; i++) {
					if (voti[i]<votoMin) {
						votoMin=voti[i];
					}
				}
				
		}
}

