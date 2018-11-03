package nl.rp.loglib.impl.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.rp.loglib.Constant;
import nl.rp.loglib.DataType;
import nl.rp.loglib.Key;
import nl.rp.loglib.Variable;
import nl.rp.loglib.impl.TemplateFactory;

public class JavaTemplateFactory extends TemplateFactory {


	public JavaTemplateFactory() {

	}
	
	@Override
	public String getDataType(DataType dataType) {

		switch (dataType) {

		case BOOL8: return "boolean";
		case INT8: return "byte";
		case UINT8: return "byte";
		case INT16: return "short";
		case UINT16: return "short";
		case INT32: return "int";
		case UINT32: return "int";
		case REAL32: return "float";
		case INT64: return "long";
		case UINT64: return "long";
		case REAL64: return "double";

		default: return null;

		}

	}

	public ArrayList<Variable> getConstants() {
		
		final ArrayList<Variable> vars = new ArrayList<Variable>();
		
		final String[] modifiers = new String[] {"public", "static", "final"};
		for (Constant constant : Constant.values()) {
			vars.add(new Variable(constant.name(), "byte", "" + (byte)constant.getValue(), modifiers));
		}
		
		return vars;
		
	}
	
	public ArrayList<Variable> getMembers() {

		final ArrayList<Variable> vars = new ArrayList<Variable>();

		final String[] modifiers = new String[] {"private"};
		
		vars.add(new Variable("buffer", "byte[]", "new byte[4000]", modifiers));
		vars.add(new Variable("bufferWritePointer", "int", "0", modifiers));
		vars.add(new Variable("bufferReadPointer", "int", "0", modifiers));
		vars.add(new Variable("magicByte", "byte", Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.name(), modifiers));
		vars.add(new Variable("i", "int", "0", modifiers));
		vars.add(new Variable("last", "int", "4000 - 1", modifiers));
		
		return vars;
		
	}
	
	public Map<String, Object> getBeginMethod() {
		
		final Map<String, Object> method = new HashMap<>();
		
		final ArrayList<String> instructions = new ArrayList<String>();	
		instructions.add("i = bufferWritePointer;");
		addNextInstruction(instructions, Constant.START_FLAG.name());
		addNextInstruction(instructions, "magicByte");

		method.put("modifiers", new String[] {"private"});
		method.put("returnType", "void");
		method.put("name", "begin");
		method.put("instructions", instructions);
		
		return method;
		
	}
	
	public Map<String, Object> getNextMethod() {
		
		final Map<String, Object> method = new HashMap<>();
		
		final ArrayList<String> instructions = new ArrayList<String>();
		instructions.add("if (i == last) {");
		instructions.add("    i = 0;");
		instructions.add("} else {");
		instructions.add("    i += 1;");
		instructions.add("}");
		instructions.add("buffer[i] = value;");

		method.put("modifiers", new String[] {"private"});
		method.put("returnType", "void");
		method.put("name", "next");
		method.put("args", new Variable[] {new Variable("value", "byte")});
		method.put("instructions", instructions);
		
		return method;
		
	}
	
	public Map<String, Object> getEndMethod() {
		
		final Map<String, Object> method = new HashMap<>();
		
		final ArrayList<String> instructions = new ArrayList<String>();	
		addNextInstruction(instructions, Constant.END_FLAG.name());
		instructions.add("bufferWritePointer = i;");

		method.put("modifiers", new String[] {"private"});
		method.put("returnType", "void");
		method.put("name", "end");
		method.put("instructions", instructions);
		
		return method;
		
	}

	public Map<String, Object> getEvtMethod(Key[] keys) {

		final Map<String, Object> method = new HashMap<>();
		
		String methodName = "";

		final ArrayList<Variable> args = new ArrayList<Variable>();	

		final ArrayList<String> instructions = new ArrayList<String>();	
		instructions.add("begin();");
		addNextInstruction(instructions, Key.keysToString(keys));

		String fullName;
		for (Key key : keys) {

			if (key == null) {
				return null;
			}

			fullName = key.fullName.toLowerCase();
			
			switch (key) {

			case EVT:
				methodName += key.shortName.toLowerCase();
				break;

			case NULL:
				methodName += key.shortName;
				break;

			case GR8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName);
				break;

			case GR16:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, 0);
				addNextInstruction(instructions, fullName, 1);
				break;

			case ID8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName);
				break;

			case ID16:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, 0);
				addNextInstruction(instructions, fullName, 1);
				break;

			case CH8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName);
				break;

			case CH16:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, 0);
				addNextInstruction(instructions, fullName, 1);
				break;

			case BOOL8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, "(byte)(" + fullName + "? 1:0)");
				break;

			case INT8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName);
				break;
				
			case UINT8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName);
				break;

			case INT16:
			case UINT16:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, 0);
				addNextInstruction(instructions, fullName, 1);
				break;

			case TICK32:
			case INT32:
			case UINT32:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, 0);
				addNextInstruction(instructions, fullName, 1);
				addNextInstruction(instructions, fullName, 2);
				addNextInstruction(instructions, fullName, 3);
				break;

			case REAL32:
				return null; //TODO

			case INT64:
			case UINT64:
				return null; //TODO

			case TICK64:
				return null; //TODO

			case REAL64:
				return null; //TODO

			case STRING:
			case BYTES:
				return null; //TODO

			default:
				break;
			}

		}

		instructions.add("end();");

		method.put("modifiers", new String[] {"protected"});
		method.put("returnType", "void");
		method.put("name", methodName);
		method.put("args", args);
		method.put("instructions", instructions);

		return method;

	}
	
	private void addNextInstruction(ArrayList<String> instructions, String value) {
		instructions.add("next(" + value + ");");
	}
	
	private void addNextInstruction(ArrayList<String> instructions, String value, int byteIndex) {
		if (byteIndex > 0) {
			instructions.add("next((byte)((" + value + " >> " + (byteIndex * 8) + ") & 0xff));");
		} else {
			instructions.add("next((byte)(" + value + " & 0xff));");
		}
	}

}
