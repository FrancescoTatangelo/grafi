package gebd;

/*
 * Classe utilizzata per ospitare i dettagli circa
 * ciascun singolo esame verbalizzato
 */
public class Esame implements Autodescrivente{
	
	/*
	 * Variabili membro della classe esame
	 * costituiscono lo stato di questa classe.
	 * 
	 * Sono anche dette variabili 'istanza', perché
	 * ogni oggetto di questo tipo possiede la sua copia
	 * di queste variabili, copia indipendente da quella
	 * posseduta dagli altri oggetti dello stesso tipo
	 */
	private int voto;
	private String ins;
	private int cfu;
	
	/*
	 * E' possibile poi definire delle variabili statiche,
	 * che non esistono in copie diverse all'interno di ciascun
	 * oggetto, ma esistono in un'unica copia a livello di classe
	 */
	
	private static int numeroEsami = 0;
	
	/*
	 * Identificativo univoco usato per contrassegnare ciascun
	 * esame
	 */
	private int id;
	

	public int getId() {
		return id;
	}

	public Esame(int voto, String ins, int cfu) {
		super();
		this.voto = voto;
		this.ins = ins;
		this.cfu = cfu;
		this.id = numeroEsami++;

		
		// ...

}

	public void setVoto(int voto) {
		if (voto > 31) {
			return;
		}
		
		this.voto = voto;
	}

	public void setIns(String ins) {
		this.ins = ins;
	}

	public void setCfu(int cfu) {
		this.cfu = cfu;
	}
	
	public int getVoto() {
		return voto;
	}
	
	public String getIns() {
		return ins;
	}
	
	public int getCfu() {
		return cfu;
	}
	
	
	public String getDescrizione() {
		return "id: " + this.id + " ins: " + this.ins + " voto: " + this.voto + " cfu: " + cfu;
		
	}
	

}