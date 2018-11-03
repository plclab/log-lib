package nl.rp.loglib.impl.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.rp.loglib.Constant;
import nl.rp.loglib.DataType;
import nl.rp.loglib.Key;
import nl.rp.loglib.Variable;
import nl.rp.loglib.impl.TemplateProcessor;

public class JavaTemplateProcessor extends TemplateProcessor {


	public JavaTemplateProcessor() {

	}
	
	@Override
	public String getDataType(DataType dataType) {

		switch (dataType) {

		case BOOL8: return "boolean";
		case INT8: return "byte";
		case UINT8: return "short";
		case INT16: return "short";
		case UINT16: return "int";
		case INT32: return "int";
		case UINT32: return "long";
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

	public Map<String, Object> getEvtMethod(Key[] keys) {

		final Map<String, Object> method = new HashMap<>();
		
		String methodName = "";

		final ArrayList<Variable> args = new ArrayList<Variable>();	

		final ArrayList<String> instructions = new ArrayList<String>();	
		instructions.add("");
		addEvtInstruction(instructions, Constant.START_FLAG.name());
		addEvtInstruction(instructions, "magicByte");
		addEvtInstruction(instructions, Key.keysToString(keys));

		for (Key key : keys) {

			if (key == null) {
				return null;
			}

			switch (key) {

			case EVT:
				methodName += key.shortName.toLowerCase();
				break;

			case NULL:
				methodName += key.shortName;
				break;

			case GR8:
				methodName += key.shortName;
				args.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtInstruction(instructions, key.fullName);
				break;

			case GR16:
				methodName += key.shortName;
				args.add(new Variable(key.fullName, getDataType(key.dataType)));
				instructions.add("//TODO");
				break;

			case ID8:
				methodName += key.shortName;
				args.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtInstruction(instructions, key.fullName);
				break;

			case ID16:
				methodName += key.shortName;
				args.add(new Variable(key.fullName, getDataType(key.dataType)));
				instructions.add("//TODO: " + key.fullName);
				break;

			case CH8:
				methodName += key.shortName;
				args.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtInstruction(instructions, key.fullName);
				break;

			case CH16:
				methodName += key.shortName;
				args.add(new Variable(key.fullName, getDataType(key.dataType)));
				instructions.add("//TODO: " + key.fullName);
				break;

			case BOOL8:
				methodName += key.shortName;
				args.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtInstruction(instructions, "BOOL_TO_BYTE(" + key.fullName + ")");
				break;

			case INT8:
				return null; //Not supported on CoDeSys V2, V3

			case UINT8:
				methodName += key.shortName;
				args.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtInstruction(instructions, key.fullName);
				break;

			case INT16:
			case UINT16:
				methodName += key.shortName;
				args.add(new Variable(key.fullName, getDataType(key.dataType)));
				instructions.add("//TODO: " + key.fullName);
				break;

			case TICK32:
			case INT32:
			case UINT32:
			case REAL32:
				methodName += key.shortName;
				args.add(new Variable(key.fullName, getDataType(key.dataType)));
				instructions.add("//TODO: " + key.fullName);
				break;

			case INT64:
			case UINT64:
				return null; //Not supported on CoDeSys V2, V3

			case TICK64:
				return null; //TODO

			case REAL64:
				methodName += key.shortName;
				args.add(new Variable(key.fullName, getDataType(key.dataType)));
				instructions.add("//TODO: " + key.fullName);
				break;

			case STRING:
			case BYTES:
				return null; //TODO

			default:
				break;
			}

		}

		addEvtInstruction(instructions, Constant.END_FLAG.name());

		instructions.add("bufferWritePointer = i;");
		instructions.add("");

		method.put("modifiers", new String[] {"protected"});
		method.put("returnType", "void");
		method.put("name", methodName);
		method.put("args", args);
		method.put("instructions", instructions);

		return method;

	}

	private void addEvtInstruction(ArrayList<String> instructions, String evtInstruction) {
		instructions.add("if (i == last) { i = 0; } else { i += 1; }");
		instructions.add("buffer[i] = " + evtInstruction + ";");
		instructions.add("");
	}

}
