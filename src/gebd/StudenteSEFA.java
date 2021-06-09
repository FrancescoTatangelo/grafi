package gebd;

import java.util.Scanner;

/*
 * Classe usata per descrivere e per gestire gli studenti iscritti a SEFA.
 * E' otttenuta derivando la classe padre *Studente*. Una classe che deriva un'altra
 * classe ne eredita tutti gli attributi e tutti i metodi. La nuova classe,
 * detta classe *figlia* avrà accesso a metodi ed attributi della classe padre, purché
 * questi siano pubblici o protetti.
 * Nel linguaggio Java non è ammessa l'ereditarietà multipla. Questo implica che:
 * - una stessa classe può essere derivata numerose volte (e.g., Studente)
 * - una stessa classe può essere derivata *solo* da un'altra classe
 * - una classe che deriva da un'altra classe, viene a sua volta derivata
 * 
 * 
 * E' possibile impedire che una classe venga derivata definendola come final
 */
public final class StudenteSEFA extends Studente{

	/*
	 * Dal momento che la classe padre possiede un costruttore con degli
	 * argomenti, lo debbo possedere anche io.
	 * Nel costruttore che aggiungo, prevedo come argomenti di input
	 * gli stessi già presenti nella classe padre.
	 * Vorrei evitare di ripetere in questo costruttore le stesse istruzioni
	 * già presenti nel costruttore della classe padre. Piuttosto,
	 * vorrei richiamare *quel* costruttore. Come faccio? Con l'istruzione *super*
	 */

	public StudenteSEFA(String nome, String cognome) {
		/*
		 * Attraverso l'uso di super, sto richiamando il costruttore della classe padre
		 * 
		 */
		super(nome, cognome, "SEFA");
	}

	/*
	 * Calcola la media aritmetica di una serie di voti codificata come array di
	 * interi.
	 * Da modificare utilizzando stavolta l'array 'esami'
	 */
	public double calcolaMedia() {
		int somma = 0;
		for(Esame e : esami) {
			somma += e.getVoto();
		}

		return (double) somma / esami.size();

	}

	/*
	 * L'implementazione che segue sovrascrive quella già presente nella classe padre
	 * Perchè la struttura del metodo è identica, il contenuto è diverso
	 */
	public void mostraInformazioni() {
		System.out.println("Mi chiamo " + this.getCognome() + " " + this.getNome());
		System.out.println("Sono iscritto a SEFA");
		System.out.println("Questi sono i miei voti");
		//mostraVoti();
	}


}