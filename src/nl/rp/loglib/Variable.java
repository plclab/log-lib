package nl.rp.loglib;

public class Variable {

	private final String name;
	private final String type;
	
	
	public Variable(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
}
