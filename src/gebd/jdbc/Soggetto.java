package gebd.jdbc;

/*
 * Classe utilizza per custodire le generalità
 * di un singolo soggetto
 */
public final class Soggetto {
	
	private int id;
	private String nome;
	private String cognome;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public int getId() {
		return id;
	}

	public Soggetto(int id, String nome, String cognome) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
	}

	
	
}