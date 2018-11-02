package nl.rp.loglib;

public class Variable {

	private final String name;
	private final String type;
	private final String value;
	
	
	public Variable(String name, String type) {
		this(name, type, null);
	}
	
	public Variable(String name, String type, String value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
}
