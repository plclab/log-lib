package nl.rp.loglib;

public class Variable {

	private final String name;
	private final String type;
	private final String value;
	private final String[] modifiers;
	
	
	public Variable(String name, String type) {
		this(name, type, null, null);
	}
	
	public Variable(String name, String type, String value) {
		this(name, type, value, null);
	}
	
	public Variable(String name, String type, String value, String[] modifiers) {
		this.name = name;
		this.type = type;
		this.value = value;
		this.modifiers = modifiers;
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
	
	public String[] getModifiers() {
		return modifiers;
	}
	
}
