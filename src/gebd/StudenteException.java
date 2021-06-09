package gebd;

/*
 * Eccezione utilizzata per descrivere i casi in cui
 * lo studente cercato non esiste con un certo codice identificativo.
 * Di norma, le exception obbligano chi usa un certo  codice 
 * a gestirle attraverso un blocco try catch o attraverso un throws.
 * Questo potrebbe rendere il codice complesso, intricato, lento,
 * ingestibile.
 * E' tuttavia possibile introdurre un diverso tipo di eccezione, meno vincolante
 * e meno rigido: le runtime exceptions. La differenza rispetto alle altre
 * è che l'applicazione non è obbligata gestirle.
 */
public class StudenteException extends RuntimeException {

}