package nl.rp.loglib.impl.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	public List<Variable> getConstants() {

		final String[] modifiers = new String[] {"public", "static", "final"};

		final List<Variable> vars = new ArrayList<Variable>();
		for (Constant constant : Constant.values()) {
			vars.add(new Variable(constant.name(), "byte", "" + (byte)constant.getValue(), modifiers));
		}

		return vars;

	}

	public List<Variable> getMembers() {

		final String[] modifiers = new String[] {"private"};

		final List<Variable> vars = new ArrayList<Variable>();
		vars.add(new Variable("buffer", "byte[]", "new byte[4000]", modifiers));
		vars.add(new Variable("bufferWritePointer", "int", "0", modifiers));
		vars.add(new Variable("bufferReadPointer", "int", "0", modifiers));
		vars.add(new Variable("magicByte", "byte", Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.name(), modifiers));
		vars.add(new Variable("i", "int", "0", modifiers));
		vars.add(new Variable("last", "int", "4000 - 1", modifiers));

		return vars;

	}

	public Map<String, Object> getBeginMethod() {

		final List<String> instructions = new ArrayList<String>();	
		instructions.add("i = bufferWritePointer;");
		addNextInstruction(instructions, Constant.START_FLAG.name(), true);
		addNextInstruction(instructions, "magicByte", true);

		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"private"});
		method.put("returnType", "void");
		method.put("name", "begin");
		method.put("instructions", instructions);

		return method;

	}

	public Map<String, Object> getNextAMethod() {

		final List<String> instructions = new ArrayList<String>();
		instructions.add("i += 1;");
		instructions.add("buffer[i] = value;");

		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"private"});
		method.put("returnType", "void");
		method.put("name", "nextA");
		method.put("args", new Variable[] {new Variable("value", "byte")});
		method.put("instructions", instructions);

		return method;

	}

	public Map<String, Object> getNextBMethod() {

		final List<String> instructions = new ArrayList<String>();
		instructions.add("if (i == last) {");
		instructions.add("    i = 0;");
		instructions.add("} else {");
		instructions.add("    i += 1;");
		instructions.add("}");
		instructions.add("buffer[i] = value;");

		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"private"});
		method.put("returnType", "void");
		method.put("name", "nextB");
		method.put("args", new Variable[] {new Variable("value", "byte")});
		method.put("instructions", instructions);

		return method;

	}

	public Map<String, Object> getEndMethod() {

		final List<String> instructions = new ArrayList<String>();	
		addNextInstruction(instructions, Constant.END_FLAG.name(), true);
		instructions.add("bufferWritePointer = i;");

		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"private"});
		method.put("returnType", "void");
		method.put("name", "end");
		method.put("instructions", instructions);

		return method;

	}

	public Map<String, Object> getEvtMethod(String evtConstant) {

		String methodName = "";

		final Key[] keys = Key.stringToKeys(evtConstant);

		final List<Variable> args = new ArrayList<Variable>();	

		final List<String> instructions = new ArrayList<String>();	
		instructions.add("begin();");
		addNextInstruction(instructions, evtConstant, true);

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
				addNextInstruction(instructions, fullName, true);
				break;

			case GR16:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, true, 0);
				addNextInstruction(instructions, fullName, true, 1);
				break;

			case ID8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, true);
				break;

			case ID16:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, true, 0);
				addNextInstruction(instructions, fullName, true, 1);
				break;

			case CH8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, true);
				break;

			case CH16:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, true, 0);
				addNextInstruction(instructions, fullName, true, 1);
				break;

			case BOOL8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, "(byte)(" + fullName + "? 1:0)", true);
				break;

			case INT8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, true);
				break;

			case UINT8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, true);
				break;

			case INT16:
			case UINT16:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, true, 0);
				addNextInstruction(instructions, fullName, true, 1);
				break;

			case TICK32:
			case INT32:
			case UINT32:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addNextInstruction(instructions, fullName, true, 0);
				addNextInstruction(instructions, fullName, true, 1);
				addNextInstruction(instructions, fullName, true, 2);
				addNextInstruction(instructions, fullName, true, 3);
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

		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"protected"});
		method.put("returnType", "void");
		method.put("name", methodName);
		method.put("args", args);
		method.put("instructions", instructions);

		return method;

	}

	private void addNextInstruction(List<String> instructions, String value, boolean withOverflow) {
		instructions.add("next" + (withOverflow? "B":"A") + "(" + value + ");");
	}

	private void addNextInstruction(List<String> instructions, String value, boolean withOverflow, int byteIndex) {
		if (byteIndex > 0) {
			instructions.add("next" + (withOverflow? "B":"A") + "((byte)((" + value + " >> " + (byteIndex * 8) + ") & 0xff));");
		} else {
			instructions.add("next" + (withOverflow? "B":"A") + "((byte)(" + value + " & 0xff));");
		}
	}

}
