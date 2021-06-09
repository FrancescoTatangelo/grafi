package progetto;

public class TestArray {

	public static void main(String[] args) {  
		
		
		
		
		int voti[];
		
		
		
		voti=new int[10];
		
		
		voti[0]=28;
	
	int[] voti2= {21,24,23};
	
	System.out.println(voti[0]);
	
	voti2=voti;
	
	System.out.println(voti2[0]);
	
	int[] voticopia = new int[10];
	
	
	//voti.length
	for (int i=0; i<voti.length; i++) {
		voticopia[i]=voti[i];
		
		
		
	}
	
	System.out.println(voticopia[0]);
	
	System.arraycopy(voti, 0, voticopia, 0,voti.length);
	
	
	}
	
	
	
	}
