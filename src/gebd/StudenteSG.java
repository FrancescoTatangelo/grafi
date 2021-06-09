package gebd;

import java.util.Scanner;

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
 */
public final class StudenteSG extends Studente{
	
	public StudenteSG(String nome, String cognome) {
		/*
		 * Attraverso l'uso di super, sto richiamando il costruttore della classe padre
		 * 
		 */
		super(nome, cognome, "SG");
	}

	/*
	 * Calcola la media aritmetica di una serie di voti codificata come array di
	 * interi.
	 * Da modificare utilizzando stavolta l'array 'esami'
	 */
	public double calcolaMedia() {
		System.out.println("Pippo");
	
		int somma = 0;
		for(Esame e : esami) {
			somma += e.getVoto();
		}

		return (double) somma / esami.size();

	}

}