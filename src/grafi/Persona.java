package grafi;

public class Persona {
	private String id, name;
	private int age;
	
	/*
	 * @param Id id del nodo Persona
	 * @param name nome della persona 
	 * @param age età della persona 
	 */
	
	public Persona(String id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	public Persona() {}
	
	public int getAge() {
		return age;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}