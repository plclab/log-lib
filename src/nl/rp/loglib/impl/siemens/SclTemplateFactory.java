package nl.rp.loglib.impl.siemens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.rp.loglib.Constant;
import nl.rp.loglib.DataType;
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
		instructions.add("Handle.BufferSize := BufferSize;");
		instructions.add("Handle.BufferEnd := BufferSize - 1;");
		instructions.add("");
		instructions.add("byteOrderInt := 1;");
		instructions.add("IF byteOrderArray[0] = 1 THEN");
		instructions.add(SPC4 + "Handle.MagicByte := " + Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.getValue() + ";");
		instructions.add("ELSE");
		instructions.add(SPC4 + "Handle.MagicByte := " + Constant.MAGIC_BYTE_V1_BIG_ENDIAN.getValue() + ";");
		instructions.add("END_IF;");

		return createFunction("CreateBufferHandle", inputVars, inOutVars, vars, instructions);

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
