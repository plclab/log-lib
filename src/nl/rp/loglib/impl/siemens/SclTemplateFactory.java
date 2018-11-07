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


	public SclTemplateFactory() {

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
		vars.add(new Variable("MagicByte", "Byte", "" + Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.getValue()));

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

		final List<Variable> vars = new ArrayList<>();
		vars.add(new Variable("byteOrderInt", "Int"));
		vars.add(new Variable("byteOrderArray AT byteOrderInt", "Array[0..1] OF Byte"));

		final List<String> instructions = new ArrayList<>();
		instructions.add("#Handle.BufferSize := #BufferSize;");
		instructions.add("#Handle.BufferEnd := #BufferSize - 1;");
		instructions.add("");
		instructions.add("#byteOrderInt := 1;");
		instructions.add("IF #byteOrderArray[0] = 1 THEN");
		instructions.add(SPC4 + "#Handle.MagicByte := " + Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.getValue() + ";");
		instructions.add("ELSE");
		instructions.add(SPC4 + "#Handle.MagicByte := " + Constant.MAGIC_BYTE_V1_BIG_ENDIAN.getValue() + ";");
		instructions.add("END_IF;");

		return createFunction("CreateBufferHandle", inputVars, inOutVars, vars, instructions);

	}

	public TemplateData getEvtFunctionTemplateData(Constant evtConstant) {

		String functionName = "";

		final Key[] keys = Key.stringToKeys(evtConstant.name());
		final int length = Key.getDataLength(keys) + 4;

		final List<Variable> inputVars = new ArrayList<>();	

		final List<String> instructions1 = new ArrayList<String>();	
		final List<String> instructions2 = new ArrayList<String>();	

		addEvtNextIndexInstruction(instructions1, instructions2, "" + Constant.START_FLAG.getValue());
		addEvtNextIndexInstruction(instructions1, instructions2, "#Handle.MagicByte");
		addEvtNextIndexInstruction(instructions1, instructions2, "" + evtConstant.getValue());

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

			case GR8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtNextIndexInstruction(instructions1, instructions2, "#" + key.fullName);
				break;

			case GR16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtAssignPxInstruction(instructions1, instructions2, 2, "#" + key.fullName);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 2, 0);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 2, 1);
				createP2Var = true;
				break;

			case ID8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtNextIndexInstruction(instructions1, instructions2, key.fullName);
				break;

			case ID16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtAssignPxInstruction(instructions1, instructions2, 2, "#" + key.fullName);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 2, 0);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 2, 1);
				createP2Var = true;
				break;

			case CH8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtNextIndexInstruction(instructions1, instructions2, key.fullName);
				break;

			case CH16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtAssignPxInstruction(instructions1, instructions2, 2, "#" + key.fullName);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 2, 0);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 2, 1);
				createP2Var = true;
				break;

			case BOOL8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtNextIndexInstruction(instructions1, instructions2, "BOOL_TO_BYTE(#" + key.fullName + ")");
				break;

			case INT8:
				return null; //Not supported on CoDeSys V2, V3

			case UINT8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtNextIndexInstruction(instructions1, instructions2, key.fullName);
				break;

			case INT16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				p4DataType = key.dataType;
				addEvtAssignPxInstruction(instructions1, instructions2, 4, "#" + key.fullName);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 0);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 1);
				break;

			case UINT16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtAssignPxInstruction(instructions1, instructions2, 2, "#" + key.fullName);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 2, 0);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 2, 1);
				createP2Var = true;
				break;

			case TICK32:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				p3DataType = key.dataType;
				addEvtAssignPxInstruction(instructions1, instructions2, 3, "#" + key.fullName);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 3, 0);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 3, 1);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 3, 2);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 3, 3);
				break;

			case INT32:
			case UINT32:
			case REAL32:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				p4DataType = key.dataType;
				addEvtAssignPxInstruction(instructions1, instructions2, 4, "#" + key.fullName);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 0);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 1);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 2);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 3);
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
				addEvtAssignPxInstruction(instructions1, instructions2, 4, "#" + key.fullName);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 0);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 1);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 2);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 3);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 4);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 5);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 6);
				addEvtNextIndexPxInstruction(instructions1, instructions2, 4, 7);
				break;

			case STRING:
			case BYTES:
				return null; //TODO

			default:
				break;
			}

		}

		addEvtNextIndexInstruction(instructions1, instructions2, "" + Constant.END_FLAG.getValue());

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

		final List<Variable> vars = new ArrayList<>();
		interfaceNode.put("tempVars", vars);
		vars.add(new Variable("full", "Bool"));
		vars.add(new Variable("end", "DInt"));
		vars.add(new Variable("i", "DInt"));
		if (createP2Var) {
			vars.add(new Variable("p2", "UInt"));
			vars.add(new Variable("p2Bytes AT p2", "Array[0..1] of Byte"));
		}
		if (p3DataType != null) {
			vars.add(new Variable("p3", getDataType(p3DataType)));
			vars.add(new Variable("p3Bytes AT p3", "Array[0.." + (p3DataType.getLength() - 1) + "] of Byte"));
		}
		if (p4DataType != null) {
			vars.add(new Variable("p4", getDataType(p4DataType)));
			vars.add(new Variable("p4Bytes AT p4", "Array[0.." + (p4DataType.getLength() - 1) + "] of Byte"));
		}

		final Map<String, Object> bodyNode = new HashMap<>();
		templateData.addNode("body", bodyNode);

		final List<String> instructions = new ArrayList<>();
		bodyNode.put("instructions", instructions);
		instructions.add("IF #Handle.BufferWritePointer >= #Handle.BufferReadPointer THEN");
		instructions.add(SPC4 + "#full := #Handle.BufferSize - (#Handle.BufferWritePointer - #Handle.BufferReadPointer) < " + length + ";");
		instructions.add("ELSE");
		instructions.add(SPC4 + "#full := #Handle.BufferSize - (#Handle.BufferSize + (#Handle.BufferWritePointer - #Handle.BufferReadPointer)) < " + length + ";");
		instructions.add("END_IF;");
		instructions.add("");
		instructions.add("IF NOT #full THEN");
		instructions.add("");
		instructions.add(SPC4 + "#end := #Handle.BufferEnd;");
		instructions.add(SPC4 + "#i := #Handle.BufferWritePointer;");
		instructions.add("");
		instructions.add(SPC4 + "IF #i + " + length + " < #end THEN");
		instructions.add("");
		instructions.addAll(instructions1);
		instructions.add(SPC4 + "ELSE");
		instructions.add("");
		instructions.addAll(instructions2);
		instructions.add(SPC4 + "END_IF;");
		instructions.add("");
		instructions.add(SPC4 + "#Handle.BufferWritePointer := #i;");
		instructions.add("");
		instructions.add("END_IF;");

		return templateData;

	}

	private void addEvtNextIndexInstruction(List<String> instructions1, List<String> instructions2, String indexInstruction) {

		instructions1.add(SPC8 + "#i := #i + 1;");
		instructions1.add(SPC8 + "#Handle.Buffer[#i] := " + indexInstruction + ";");
		instructions1.add("");			

		instructions2.add(SPC8 + "IF #i < end THEN #i := #i + 1; ELSE #i := 0; END_IF;");
		instructions2.add(SPC8 + "#Handle.Buffer[#i] := " + indexInstruction + ";");
		instructions2.add("");

	}

	private void addEvtAssignPxInstruction(List<String> instructions1, List<String> instructions2, int pIndex, String pInstruction) {
		instructions1.add(SPC8 + "#p" + pIndex + " := " + pInstruction + ";");
		instructions2.add(SPC8 + "#p" + pIndex + " := " + pInstruction + ";");
	}
	
	private void addEvtNextIndexPxInstruction(List<String> instructions1, List<String> instructions2, int pIndex, int pBytesIndex) {

		instructions1.add(SPC8 + "#i := #i + 1;");
		instructions1.add(SPC8 + "#Handle.Buffer[#i] := #p" + pIndex + "Bytes[" + pBytesIndex + "];");
		instructions1.add("");

		instructions2.add(SPC8 + "IF #i < end THEN #i := #i + 1; ELSE #i := 0; END_IF;");
		instructions2.add(SPC8 + "#Handle.Buffer[#i] := #p" + pIndex + "Bytes[" + pBytesIndex + "];");
		instructions2.add("");

	}

	private TemplateData createFunction(String name, List<Variable> inputVars,
			List<Variable> inOutVars, List<Variable> tempVars, List<String> instructions) {
		return createFunction(name, null, null, inputVars, inOutVars, tempVars, instructions);
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
