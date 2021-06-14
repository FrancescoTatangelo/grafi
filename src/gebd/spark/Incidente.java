package gebd.spark;

import java.io.Serializable;

/*
 *c
 *i
 *d 
 *
 *i
 *de
 *a
 *s
 *-deve
 *-deve 
 *-per ogni v
 *in d
 *metodi getter e setter 
 */

public class Incidente implements Serializable {

	public Incidente(int idGruppo, String strada1, String strada2, int protocollo, int nrIllesi) {
		super();
		this.idGruppo = idGruppo;
		this.strada1 = strada1;
		this.strada2 = strada2;
		this.protocollo = protocollo;
		this.nrIllesi = nrIllesi;
	}
	public int getIdGruppo() {
		return idGruppo;
	}
	public void setIdGruppo(int idGruppo) {
		this.idGruppo = idGruppo;
	}
	public String getStrada1() {
		return strada1;
	}
	public void setStrada1(String strada1) {
		this.strada1 = strada1;
	}
	public String getStrada2() {
		return strada2;
	}
	public void setStrada2(String strada2) {
		this.strada2 = strada2;
	}
	public int getProtocollo() {
		return protocollo;
	}
	public void setProtocollo(int protocollo) {
		this.protocollo = protocollo;
	}
	public int getNrIllesi() {
		return nrIllesi;
	}
	public void setNrIllesi(int nrIllesi) {
		this.nrIllesi = nrIllesi;
	}
	Incidente(){
		
	}
	
	
	int idGruppo;
	String strada1;
	String strada2;
	int protocollo;
	int nrIllesi; 
}
