package nl.rp.loglib;

public class Variable {

	private final String name;
	private final String type;
	private final String value;
	private final String[] modifiers;
	private final boolean derived;


	public Variable(String name, String type) {
		this(name, type, null, null, false);
	}

	public Variable(String name, String type, String value) {
		this(name, type, value, null, false);
	}

	public Variable(String name, String type, String value, String[] modifiers) {
		this(name, type, value, modifiers, false);
	}

	public Variable(String name, String type, String value, String[] modifiers, boolean derived) {
		this.name = name;
		this.type = type;
		this.value = value;
		this.modifiers = modifiers;
		this.derived = derived;
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

	public boolean isDerived() {
		return derived;
	}

}
