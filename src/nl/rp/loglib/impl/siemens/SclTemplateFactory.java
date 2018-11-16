package nl.rp.loglib.impl.siemens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.rp.loglib.Constant;
import nl.rp.loglib.DataType;
import nl.rp.loglib.Key;
import nl.rp.loglib.Variable;
import nl.rp.loglib.impl.TemplateData;
import nl.rp.loglib.impl.TemplateFactory;

public class SclTemplateFactory extends TemplateFactory {

	public static final int BUFFER_FULL_HANDLING_CALL_EVENT = 0;
	public static final int BUFFER_FULL_HANDLING_SET_BOOL = 1;
	
	private int bufferFullHandling = BUFFER_FULL_HANDLING_CALL_EVENT;
	

	public SclTemplateFactory() {

	}
	
	public int getBufferFullHandling() {
		return bufferFullHandling;
	}
	
	public void setBufferFullHandling(int bufferFullHandling) {
		this.bufferFullHandling = bufferFullHandling;
	}

	@Override
	public String getDataType(DataType dataType) {

		switch (dataType) {

		case BOOL8: return "Bool";
		case INT8: return null; //Not supported
		case UINT8: return "Byte";
		case INT16: return "Int";
		case UINT16: return "UInt";
		case INT32: return "Dint";
		case UINT32: return "UDInt";
		case REAL32: return "Real";
		case INT64: return null; //Not supported
		case UINT64: return null; //Not supported
		case REAL64: return "LReal";

		default: return null;

		}

	}

	public TemplateData getLogBufferStructTemplateData() {

		final String name = "LogBuffer";

		final List<Variable> vars = new ArrayList<>();
		vars.add(new Variable("Buffer", "Array[0..3999] of Byte"));
		vars.add(new Variable("BufferSize", "DInt", "4000"));
		vars.add(new Variable("BufferEnd", "DInt", "3999"));
		vars.add(new Variable("BufferWritePointer", "DInt", "0"));
		vars.add(new Variable("BufferReadPointer", "DInt", "0"));
		vars.add(new Variable("BufferOverflow", "DInt", "-1"));
		vars.add(new Variable("MagicByte", "Byte", "" + Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.getValue()));

		if (bufferFullHandling == BUFFER_FULL_HANDLING_SET_BOOL) {
			vars.add(new Variable("BufferFull", "Bool", "FALSE"));
		}
		
		final TemplateData templateData = new TemplateData();
		templateData.setOutputFileName(name);
		templateData.addNode("name", name);
		templateData.addNode("vars", vars);

		return templateData;

	}

	public TemplateData getCreateBufferHandleFunctionTemplateData() {

		final List<Variable> inputVars = new ArrayList<>();	
		inputVars.add(new Variable("BufferSize", "DInt"));

		final List<Variable> inOutVars = new ArrayList<>();	
		inOutVars.add(new Variable("Handle", "\"LogBuffer\""));

		final List<Variable> tempVars = new ArrayList<>();
		tempVars.add(new Variable("byteOrderInt", "Int"));
		tempVars.add(new Variable("byteOrderArray AT byteOrderInt", "Array[0..1] OF Byte"));

		final List<String> instructions = new ArrayList<>();
		instructions.add("#Handle.BufferSize := #BufferSize;");
		instructions.add("#Handle.BufferEnd := #BufferSize - 1;");
		instructions.add("");
		instructions.add("#byteOrderInt := 1;");
		instructions.add("IF #byteOrderArray[0] = 1 THEN");
		instructions.add(TAB + "#Handle.MagicByte := " + Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.getValue() + ";");
		instructions.add("ELSE");
		instructions.add(TAB + "#Handle.MagicByte := " + Constant.MAGIC_BYTE_V1_BIG_ENDIAN.getValue() + ";");
		instructions.add("END_IF;");

		if (bufferFullHandling == BUFFER_FULL_HANDLING_SET_BOOL) {
			
			tempVars.add(new Variable("i", "DInt"));
			
			instructions.add("");
			instructions.add("IF #Handle.BufferFull THEN");
			instructions.add("");
			//TODO: TAB's
			instructions.add("#i := \"GetNextWritePointer\"(Length := 4, WritePointer := #Handle.BufferWritePointer, ReadPointer := #Handle.BufferReadPointer, BufferSize := #Handle.BufferSize, Overflow := #Handle.BufferOverflow);");
			instructions.add("IF #i >= 0 THEN");
			instructions.add("");
			instructions.add(TAB + "#Handle.Buffer[#i] := " + Constant.START_FLAG.getValue() + ";");
			instructions.add("");
			addEvtNextIndexInstruction(instructions, "#Handle.MagicByte");
			addEvtNextIndexInstruction(instructions, "" + Constant.EVT_FULL.getValue());
			addEvtNextIndexInstruction(instructions, "" + Constant.END_FLAG.getValue());
			instructions.add(TAB + "#Handle.BufferWritePointer := #i;");
			instructions.add("");
			instructions.add(TAB + "#Handle.BufferFull := FALSE;");
			instructions.add("");
			instructions.add("END_IF;");
			instructions.add("");
			instructions.add("END_IF;");
			
		}
		
		return createFunction("CreateBufferHandle", inputVars, inOutVars, tempVars, instructions);

	}
	
	public TemplateData getGetNextWritePointerFunctionTemplateData() {

		final List<Variable> inputVars = new ArrayList<>();	
		inputVars.add(new Variable("Length", "DInt"));
		inputVars.add(new Variable("WritePointer", "DInt"));
		inputVars.add(new Variable("ReadPointer", "DInt"));
		inputVars.add(new Variable("BufferSize", "DInt"));

		final List<Variable> inOutVars = new ArrayList<>();	
		inOutVars.add(new Variable("Overflow", "DInt"));

		final List<String> instructions = new ArrayList<>();
		instructions.add("#GetNextWritePointer := -1;");
		instructions.add("IF #WritePointer >= #ReadPointer THEN");
		instructions.add(TAB + "IF #WritePointer + #Length >= #BufferSize THEN");
		instructions.add(TABTAB + "IF #ReadPointer >= #Length THEN");
		instructions.add(TABTABTAB + "#Overflow := #WritePointer;");
		instructions.add(TABTABTAB + "#GetNextWritePointer := 0;");
		instructions.add(TABTAB + "END_IF;");
		instructions.add(TAB + "ELSE");
		instructions.add(TABTAB + "#GetNextWritePointer := #WritePointer + 1;");
		instructions.add(TAB + "END_IF;");
		instructions.add("ELSIF #ReadPointer - #WritePointer > #Length THEN");
		instructions.add(TAB + "#GetNextWritePointer := #WritePointer + 1;");
		instructions.add("END_IF;");

		return createFunction("GetNextWritePointer", DataType.INT32, inputVars, inOutVars, null, instructions);

	}

	public TemplateData getEvtFunctionTemplateData(Constant evtConstant) {

		String functionName = "";

		final Key[] keys = Key.stringToKeys(evtConstant.name());
		final int length = Key.getDataLength(keys) + 4;

		final List<Variable> inputVars = new ArrayList<>();	

		final List<String> evtInstructions = new ArrayList<String>();	

		evtInstructions.add(TAB + "#Handle.Buffer[#i] := " + Constant.START_FLAG.getValue() + ";");
		evtInstructions.add("");
		addEvtNextIndexInstruction(evtInstructions, "#Handle.MagicByte");
		addEvtNextIndexInstruction(evtInstructions, "" + evtConstant.getValue());
		
		boolean createP2Var = false;
		DataType p3DataType = null;
		DataType p4DataType = null;

		for (Key key : keys) {

			if (key == null) {
				return null;
			}

			switch (key) {

			case EVT:
				functionName += key.shortName;
				break;

			case NULL:
				functionName += key.shortName;
				break;

			case FULL:
				if (bufferFullHandling == BUFFER_FULL_HANDLING_SET_BOOL) {
					return null;
				} else {
					functionName += key.shortName;
				}
				break;

			case GR8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtNextIndexInstruction(evtInstructions, "#" + key.fullName);
				break;

			case GR16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtAssignPxInstruction(evtInstructions, 2, "#" + key.fullName);
				addEvtNextIndexPxInstruction(evtInstructions, 2, 0);
				addEvtNextIndexPxInstruction(evtInstructions, 2, 1);
				createP2Var = true;
				break;

			case ID8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtNextIndexInstruction(evtInstructions, key.fullName);
				break;

			case ID16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtAssignPxInstruction(evtInstructions, 2, "#" + key.fullName);
				addEvtNextIndexPxInstruction(evtInstructions, 2, 0);
				addEvtNextIndexPxInstruction(evtInstructions, 2, 1);
				createP2Var = true;
				break;

			case CH8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtNextIndexInstruction(evtInstructions, key.fullName);
				break;

			case CH16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtAssignPxInstruction(evtInstructions, 2, "#" + key.fullName);
				addEvtNextIndexPxInstruction(evtInstructions, 2, 0);
				addEvtNextIndexPxInstruction(evtInstructions, 2, 1);
				createP2Var = true;
				break;

			case BOOL8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtNextIndexInstruction(evtInstructions, "BOOL_TO_BYTE(#" + key.fullName + ")");
				break;

			case INT8:
				return null; //Not supported on CoDeSys V2, V3

			case UINT8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtNextIndexInstruction(evtInstructions, key.fullName);
				break;

			case INT16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				p4DataType = key.dataType;
				addEvtAssignPxInstruction(evtInstructions, 4, "#" + key.fullName);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 0);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 1);
				break;

			case UINT16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtAssignPxInstruction(evtInstructions, 2, "#" + key.fullName);
				addEvtNextIndexPxInstruction(evtInstructions, 2, 0);
				addEvtNextIndexPxInstruction(evtInstructions, 2, 1);
				createP2Var = true;
				break;

			case TICK32:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				p3DataType = key.dataType;
				addEvtAssignPxInstruction(evtInstructions, 3, "#" + key.fullName);
				addEvtNextIndexPxInstruction(evtInstructions, 3, 0);
				addEvtNextIndexPxInstruction(evtInstructions, 3, 1);
				addEvtNextIndexPxInstruction(evtInstructions, 3, 2);
				addEvtNextIndexPxInstruction(evtInstructions, 3, 3);
				break;

			case INT32:
			case UINT32:
			case REAL32:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				p4DataType = key.dataType;
				addEvtAssignPxInstruction(evtInstructions, 4, "#" + key.fullName);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 0);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 1);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 2);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 3);
				break;

			case INT64:
			case UINT64:
				return null; //Not supported on CoDeSys V2, V3

			case TICK64:
				return null; //TODO

			case REAL64:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				p4DataType = key.dataType;
				addEvtAssignPxInstruction(evtInstructions, 4, "#" + key.fullName);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 0);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 1);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 2);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 3);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 4);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 5);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 6);
				addEvtNextIndexPxInstruction(evtInstructions, 4, 7);
				break;

			case STRING:
			case BYTES:
				return null; //TODO

			default:
				return null;
			}

		}

		addEvtNextIndexInstruction(evtInstructions, "" + Constant.END_FLAG.getValue());

		final TemplateData templateData = new TemplateData();
		templateData.setOutputFileName(functionName);

		final Map<String, Object> blockNode = new HashMap<>();
		templateData.addNode("block", blockNode);
		blockNode.put("name", functionName);

		final Map<String, Object> interfaceNode = new HashMap<>();
		templateData.addNode("interface", interfaceNode);
		interfaceNode.put("returnType", "Void");
		interfaceNode.put("inputVars", inputVars);
		interfaceNode.put("inOutVars", new Variable[] {
				new Variable("Handle", "\"LogBuffer\"")
		});

		final List<Variable> tempVars = new ArrayList<>();
		interfaceNode.put("tempVars", tempVars);
		tempVars.add(new Variable("i", "DInt"));
		if (createP2Var) {
			tempVars.add(new Variable("p2", getDataType(DataType.UINT16)));
			tempVars.add(new Variable("p2Bytes AT p2", "Array[0..1] of Byte"));
		}
		if (p3DataType != null) {
			tempVars.add(new Variable("p3", getDataType(p3DataType)));
			tempVars.add(new Variable("p3Bytes AT p3", "Array[0.." + (p3DataType.getLength() - 1) + "] of Byte"));
		}
		if (p4DataType != null) {
			tempVars.add(new Variable("p4", getDataType(p4DataType)));
			tempVars.add(new Variable("p4Bytes AT p4", "Array[0.." + (p4DataType.getLength() - 1) + "] of Byte"));
		}

		final Map<String, Object> bodyNode = new HashMap<>();
		templateData.addNode("body", bodyNode);

		final List<String> instructions = new ArrayList<>();
		bodyNode.put("instructions", instructions);
		if (evtConstant == Constant.EVT_FULL) {
			instructions.add("#i := \"GetNextWritePointer\"(Length := " + length + ", WritePointer := #Handle.BufferWritePointer, ReadPointer := #Handle.BufferReadPointer, BufferSize := #Handle.BufferSize, Overflow := #Handle.BufferOverflow);");
			instructions.add("IF #i >= 0 THEN");
			instructions.add("");
			instructions.addAll(evtInstructions);
			instructions.add(TAB + "#Handle.BufferWritePointer := #i;");
			instructions.add("");
			instructions.add("END_IF;");

		} else {
			instructions.add("#i := \"GetNextWritePointer\"(Length := " + (length + 4) + ", WritePointer := #Handle.BufferWritePointer, ReadPointer := #Handle.BufferReadPointer, BufferSize := #Handle.BufferSize, Overflow := #Handle.BufferOverflow);");
			instructions.add("IF #i >= 0 THEN");
			instructions.add("");
			instructions.addAll(evtInstructions);
			instructions.add(TAB + "#Handle.BufferWritePointer := #i;");
			instructions.add("");
			instructions.add("ELSE");
			if (bufferFullHandling == BUFFER_FULL_HANDLING_SET_BOOL) {
				instructions.add(TAB + "#Handle.BufferFull := TRUE;");
			} else {
				instructions.add(TAB + "\"EvtFull\"(Handle := #Handle);");
			}
			instructions.add("END_IF;");
		}

		return templateData;

	}

	private void addEvtNextIndexInstruction(List<String> instructions, String indexInstruction) {
		instructions.add(TAB + "#i := #i + 1;");
		instructions.add(TAB + "#Handle.Buffer[#i] := " + indexInstruction + ";");
		instructions.add("");			
	}

	private void addEvtAssignPxInstruction(List<String> instructions, int pIndex, String pInstruction) {
		instructions.add(TAB + "#p" + pIndex + " := " + pInstruction + ";");
		instructions.add("");			
	}
	
	private void addEvtNextIndexPxInstruction(List<String> instructions, int pIndex, int pBytesIndex) {
		instructions.add(TAB + "#i := #i + 1;");
		instructions.add(TAB + "#Handle.Buffer[#i] := #p" + pIndex + "Bytes[" + pBytesIndex + "];");
		instructions.add("");
	}

	private TemplateData createFunction(String name, List<Variable> inputVars,
			List<Variable> inOutVars, List<Variable> tempVars, List<String> instructions) {
		return createFunction(name, null, null, inputVars, inOutVars, tempVars, instructions);
	}
	
	private TemplateData createFunction(String name, DataType returnType, List<Variable> inputVars,
			List<Variable> inOutVars, List<Variable> tempVars, List<String> instructions) {
		return createFunction(name, null, returnType, inputVars, inOutVars, tempVars, instructions);
	}

	private TemplateData createFunction(String name, String[] path, DataType returnType,
			List<Variable> inputVars, List<Variable> inOutVars,
			List<Variable> tempVars, List<String> instructions) {

		final TemplateData templateData = new TemplateData();
		templateData.setOutputFileName(name);

		final Map<String, Object> blockNode = new HashMap<>();
		templateData.addNode("block", blockNode);
		blockNode.put("name", name);
		if (path != null) {
			blockNode.put("path", path); //TODO: Not support by scl
		}

		final Map<String, Object> interfaceNode = new HashMap<>();
		templateData.addNode("interface", interfaceNode);

		if (returnType != null) {
			interfaceNode.put("returnType", getDataType(returnType));
		} else {			
			interfaceNode.put("returnType", "Void");
		}

		if (inputVars != null) {
			interfaceNode.put("inputVars", inputVars);
		}
		if (inOutVars != null) {
			interfaceNode.put("inOutVars", inOutVars);
		}
		if (tempVars != null) {
			interfaceNode.put("tempVars", tempVars);
		}

		final Map<String, Object> bodyNode = new HashMap<>();
		templateData.addNode("body", bodyNode);

		if (instructions != null) {			
			bodyNode.put("instructions", instructions);
		}

		return templateData;

	}

}
