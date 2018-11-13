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

		final List<Variable> constants = new ArrayList<Variable>();
		constants.add(new Variable("DEFAULT_BUFFER_LENGTH", "int", "4000", modifiers));

		for (Constant constant : Constant.values()) {
			constants.add(new Variable(constant.name(), "byte", "" + (byte)constant.getValue(), modifiers));
		}

		return constants;

	}

	public List<Variable> getMembers() {

		final String[] modifiers = new String[] {"private"};

		final List<Variable> vars = new ArrayList<Variable>();
		vars.add(new Variable("buffer", "byte[]", "new byte[DEFAULT_BUFFER_LENGTH]", modifiers));
		vars.add(new Variable("bufferLength", "int", "DEFAULT_BUFFER_LENGTH", modifiers));
		//vars.add(new Variable("end", "int", "DEFAULT_BUFFER_LENGTH - 1", modifiers)); //TODO
		vars.add(new Variable("bufferWritePointer", "int", "0", modifiers));
		vars.add(new Variable("bufferReadPointer", "int", "0", modifiers));
		vars.add(new Variable("bufferOverflow", "int", "0", modifiers));
		vars.add(new Variable("magicByte", "byte", Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.name(), modifiers));
		vars.add(new Variable("i", "int", "0", modifiers));

		return vars;

	}

	public Map<String, Object> getDefaultConstructor() {

		final List<String> instructions = new ArrayList<String>();	
		instructions.add("buffer = new byte[length];");
		instructions.add("bufferLength = length;");
		//instructions.add("end = length - 1;"); //TODO
		instructions.add("bufferWritePointer = 0;");
		instructions.add("bufferReadPointer = 0;");
		instructions.add("bufferOverflow = 0;");
		instructions.add("i = 0;");

		//TODO: Initialize magic byte

		final Map<String, Object> constructor = new HashMap<>();
		constructor.put("modifiers", new String[] {"public"});
		constructor.put("args", new Variable[] {new Variable("length", "int")});
		constructor.put("instructions", instructions);

		return constructor;

	}

	public Map<String, Object> getGetBufferLengthMethod() {

		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"public"});
		method.put("returnType", "int");
		method.put("name", "getBufferLength");
		method.put("instructions", new String[] {"return bufferLength;"});

		return method;

	}

	public Map<String, Object> getGetBufferWritePointerMethod() {

		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"public"});
		method.put("returnType", "int");
		method.put("name", "getBufferWritePointer");
		method.put("instructions", new String[] {"return bufferWritePointer;"});

		return method;

	}

	public Map<String, Object> getGetBufferReadPointerMethod() {

		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"public"});
		method.put("returnType", "int");
		method.put("name", "getBufferReadPointer");
		method.put("instructions", new String[] {"return bufferReadPointer;"});

		return method;

	}

	public Map<String, Object> getGetBufferOverflowMethod() {

		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"public"});
		method.put("returnType", "int");
		method.put("name", "getBufferOverflow");
		method.put("instructions", new String[] {"return bufferOverflow;"});

		return method;

	}

	public Map<String, Object> getGetNextWritePointerMethod() {

		final List<String> instructions = new ArrayList<String>();	
		instructions.add("i = -1;");
		instructions.add("if (bufferWritePointer >= bufferReadPointer) {");
		instructions.add(TAB + "if (bufferWritePointer + length >= bufferLength) {");
		instructions.add(TABTAB + "if (bufferReadPointer >= length) {");
		instructions.add(TABTABTAB + "bufferOverflow = bufferWritePointer;");
		instructions.add(TABTABTAB + "buffer[0] = " + Constant.START_FLAG.name() + ";");
		instructions.add(TABTABTAB + "buffer[1] = magicByte;");
		instructions.add(TABTABTAB + "i = 2;");
		instructions.add(TABTABTAB + "return true;");
		instructions.add(TABTAB + "}");
		instructions.add(TAB + "} else {");
		instructions.add(TABTAB + "i = bufferWritePointer + 1;");
		instructions.add(TABTAB + "buffer[i++] = " + Constant.START_FLAG.name() + ";");
		instructions.add(TABTAB + "buffer[i++] = magicByte;");
		instructions.add(TABTAB + "return true;");
		instructions.add(TAB + "}");
		instructions.add("} else if (bufferReadPointer - bufferWritePointer > length) {");
		instructions.add(TAB + "i = bufferWritePointer + 1;");
		instructions.add(TAB + "buffer[i++] = " + Constant.START_FLAG.name() + ";");
		instructions.add(TAB + "buffer[i++] = magicByte;");
		instructions.add(TAB + "return true;");
		instructions.add("}");
		instructions.add("return false;");

		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"private"});
		method.put("returnType", "boolean");
		method.put("name", "getNextWritePointer");
		method.put("args", new Variable[] {new Variable("length", "int")});
		method.put("instructions", instructions);

		return method;

	}

	public Map<String, Object> getNextMethod() {

		final List<String> instructions = new ArrayList<String>();
		instructions.add("i = (i == end)? 0 : i + 1;");
		instructions.add("buffer[i] = value;");

		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"private"});
		method.put("returnType", "void");
		method.put("name", "next");
		method.put("args", new Variable[] {new Variable("value", "byte")});
		method.put("instructions", instructions);

		return method;

	}

	public Map<String, Object> getReadBytesMethod() {

		final List<String> instructions = new ArrayList<String>();
		instructions.add("if (bufferWritePointer > bufferReadPointer) {");
		instructions.add(TAB + "final byte[] bytes = Arrays.copyOfRange(buffer, bufferReadPointer + 1, bufferWritePointer + 1);");
		instructions.add(TAB + "bufferReadPointer = bufferWritePointer;");
		instructions.add(TAB + "return bytes;");
		instructions.add("} else if (bufferWritePointer < bufferReadPointer) {");
		instructions.add(TAB + "if (bufferReadPointer < bufferOverflow) {");
		instructions.add(TABTAB + "final byte[] bytes = Arrays.copyOfRange(buffer, bufferReadPointer + 1, bufferOverflow + 1);");
		instructions.add(TABTAB + "bufferReadPointer = bufferOverflow;");
		instructions.add(TABTAB + "return bytes;");
		instructions.add(TAB + "} else if (bufferWritePointer > 0) {");
		instructions.add(TABTAB + "final byte[] bytes = Arrays.copyOfRange(buffer, 0, bufferWritePointer + 1);");
		instructions.add(TABTAB + "bufferReadPointer = bufferWritePointer;");
		instructions.add(TABTAB + "return bytes;");
		instructions.add(TAB + "} else {");
		instructions.add(TABTAB + "return null;");
		instructions.add(TAB + "}");
		instructions.add("} else {");
		instructions.add(TAB + "return null;");
		instructions.add("}");
		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"public"});
		method.put("returnType", "byte[]");
		method.put("name", "readBytes");
		method.put("instructions", instructions);

		return method;

	}

	public Map<String, Object> getEvtMethod(String evtConstant) {

		String methodName = "";

		final Key[] keys = Key.stringToKeys(evtConstant);
		final int length = Key.getDataLength(keys) + 4;

		final List<Variable> args = new ArrayList<Variable>();	

		final List<String> evtInstructions = new ArrayList<String>();
		addEvtInstruction(evtInstructions, evtConstant, false, false);

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
				addEvtInstruction(evtInstructions, fullName, false);
				break;

			case GR16:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addEvtInstruction(evtInstructions, fullName, false, 0);
				addEvtInstruction(evtInstructions, fullName, false, 1);
				break;

			case ID8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addEvtInstruction(evtInstructions, fullName, false);
				break;

			case ID16:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addEvtInstruction(evtInstructions, fullName, false, 0);
				addEvtInstruction(evtInstructions, fullName, false, 1);
				break;

			case CH8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addEvtInstruction(evtInstructions, fullName, false);
				break;

			case CH16:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addEvtInstruction(evtInstructions, fullName, false, 0);
				addEvtInstruction(evtInstructions, fullName, false, 1);
				break;

			case BOOL8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addEvtInstruction(evtInstructions, "(byte)(" + fullName + "? 1:0)", false);
				break;

			case INT8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addEvtInstruction(evtInstructions, fullName, false);
				break;

			case UINT8:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addEvtInstruction(evtInstructions, fullName, false);
				break;

			case INT16:
			case UINT16:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addEvtInstruction(evtInstructions, fullName, false, 0);
				addEvtInstruction(evtInstructions, fullName, false, 1);
				break;

			case TICK32:
			case INT32:
			case UINT32:
				methodName += key.shortName;
				args.add(new Variable(fullName, getDataType(key.dataType)));
				addEvtInstruction(evtInstructions, fullName, false, 0);
				addEvtInstruction(evtInstructions, fullName, false, 1);
				addEvtInstruction(evtInstructions, fullName, false, 2);
				addEvtInstruction(evtInstructions, fullName, false, 3);
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

		addEvtInstruction(evtInstructions, Constant.END_FLAG.name(), false);
		evtInstructions.add(TAB + "bufferWritePointer = i;");

		final Map<String, Object> method = new HashMap<>();
		method.put("modifiers", new String[] {"protected"});
		method.put("returnType", "void");
		method.put("name", methodName);
		method.put("args", args);

		final List<String> instructions = new ArrayList<>();
		method.put("instructions", instructions);
		instructions.add("if (getNextWritePointer(" + length + ")) {");
		instructions.addAll(evtInstructions);
		instructions.add("}");

		return method;

	}

	private void addEvtInstruction(List<String> instructions, String value, boolean withOverflow) {
		addEvtInstruction(instructions, value, true, withOverflow);
	}

	private void addEvtInstruction(List<String> instructions, String value, boolean increase, boolean withOverflow) {
		if (increase) {
			if (withOverflow) {
				instructions.add(TAB + "next(" + value + ");");
			} else {
				instructions.add(TAB + "buffer[++i] = " + value + ";");
			}
		} else {
			instructions.add(TAB + "buffer[i] = " + value + ";");
		}
	}

	private void addEvtInstruction(List<String> instructions, String value, boolean withOverflow, int byteIndex) {
		if (byteIndex > 0) {
			if (withOverflow) {
				instructions.add(TAB + "next((byte)((" + value + " >> " + (byteIndex * 8) + ") & 0xff));");
			} else {
				instructions.add(TAB + "buffer[++i] = (byte)((" + value + " >> " + (byteIndex * 8) + ") & 0xff);");
			}
		} else {
			if (withOverflow) {
				instructions.add(TAB + "next((byte)(" + value + " & 0xff));");
			} else {
				instructions.add(TAB + "buffer[++i] = (byte)(" + value + " & 0xff);");
			}
		}
	}

}
