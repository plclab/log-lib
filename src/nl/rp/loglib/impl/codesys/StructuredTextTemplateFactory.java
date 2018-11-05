package nl.rp.loglib.impl.codesys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.rp.loglib.Constant;
import nl.rp.loglib.DataType;
import nl.rp.loglib.Key;
import nl.rp.loglib.Variable;
import nl.rp.loglib.impl.TemplateData;
import nl.rp.loglib.impl.TemplateFactory;

public class StructuredTextTemplateFactory extends TemplateFactory {


	public StructuredTextTemplateFactory() {

	}

	@Override
	public String getDataType(DataType dataType) {

		switch (dataType) {

		case BOOL8: return "BOOL";
		case INT8: return null; //Not supported
		case UINT8: return "BYTE";
		case INT16: return "INT";
		case UINT16: return "UINT";
		case INT32: return "DINT";
		case UINT32: return "UDINT";
		case REAL32: return "REAL";
		case INT64: return null; //Not supported
		case UINT64: return null; //Not supported
		case REAL64: return "LREAL"; //Not supported

		default: return null;

		}

	}

	public TemplateData getGlobalConstantsTemplateData() {

		final TemplateData templateData = new TemplateData();

		final String name = "LogLibCore";

		templateData.setOutputFileName(name);

		templateData.addNode("name", name);

		templateData.addNode("constant", true);

		final ArrayList<Variable> vars = new ArrayList<>();
		templateData.addNode("vars", vars);

		for (Constant constant : Constant.values()) {
			vars.add(new Variable(constant.name(), "BYTE", "" + constant.getValue()));
		}

		return templateData;

	}

	public TemplateData getLogBufferHandleStructTemplateData() {

		final TemplateData templateData = new TemplateData();

		final String name = "LogBufferHandle";

		templateData.setOutputFileName(name);

		templateData.addNode("path", new String[] {"core"});

		templateData.addNode("name", name);

		final ArrayList<Variable> vars = new ArrayList<>();
		templateData.addNode("vars", vars);

		vars.add(new Variable("BufferAddress", "DWORD"));
		vars.add(new Variable("BufferSize", "UDINT"));
		vars.add(new Variable("BufferEndAddress", "DWORD"));
		vars.add(new Variable("BufferWritePointer", "DINT"));
		vars.add(new Variable("BufferReadPointer", "DINT"));
		vars.add(new Variable("MagicByte", "BYTE", Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.name()));

		return templateData;

	}

	public TemplateData getCreateBufferHandleFunctionTemplateData() {

		final TemplateData templateData = new TemplateData();

		final String functionName = "CreateBufferHandle";

		templateData.setOutputFileName(functionName);

		final Map<String, Object> pouNode = new HashMap<>();
		templateData.addNode("pou", pouNode);

		final Map<String, Object> interfaceNode = new HashMap<>();
		templateData.addNode("interface", interfaceNode);

		final Map<String, Object> bodyNode = new HashMap<>();
		templateData.addNode("body", bodyNode);

		pouNode.put("path", new String[] {"core"});
		pouNode.put("name", functionName);

		interfaceNode.put("returnType", getDataType(DataType.BOOL8));

		final ArrayList<Variable> inputVars = new ArrayList<>();	
		interfaceNode.put("inputVars", inputVars);

		inputVars.add(new Variable("BufferAddress", "DWORD"));
		inputVars.add(new Variable("BufferSize", "DINT"));

		interfaceNode.put("inOutVars", new Variable[] {
				new Variable("Handle", "LogBufferHandle")
		});

		final ArrayList<Variable> vars = new ArrayList<>();
		interfaceNode.put("vars", vars);

		vars.add(new Variable("byteOrderInt", "INT"));
		vars.add(new Variable("byteOrderArray", "ARRAY[0..1] OF BYTE"));
		vars.add(new Variable("pInt", "POINTER TO INT"));

		final ArrayList<String> instructions = new ArrayList<>();
		bodyNode.put("instructions", instructions);

		instructions.add("");
		instructions.add("Handle.BufferAddress := BufferAddress;");
		instructions.add("Handle.BufferSize := BufferSize;");
		instructions.add("Handle.BufferEndAddress := BufferAddress + (BufferSize - 1);");
		instructions.add("");
		instructions.add("byteOrderInt := 1;");
		instructions.add("pInt := ADR(byteOrderArray[0]);");
		instructions.add("IF byteOrderArray[0] = 1 THEN");
		instructions.add("    Handle.MagicByte := " + Constant.MAGIC_BYTE_V1_BIG_ENDIAN.name() + ";");
		instructions.add("ELSE");
		instructions.add("    Handle.MagicByte := " + Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.name() + ";");
		instructions.add("END_IF");
		instructions.add("");

		return templateData;

	}

	public TemplateData getEvtFunctionTemplateData(String evtConstant) {

		final Key[] keys = Key.stringToKeys(evtConstant);
		final int length = Key.getDataLength(keys) + 4;

		String functionName = "";

		final ArrayList<Variable> inputVars = new ArrayList<>();	

		final ArrayList<String> instructions1 = new ArrayList<String>();	
		final ArrayList<String> instructions2 = new ArrayList<String>();	

		addEvtP1Instruction(instructions1, instructions2, Constant.START_FLAG.name());
		addEvtP1Instruction(instructions1, instructions2, "Handle.MagicByte");
		addEvtP1Instruction(instructions1, instructions2, evtConstant);

		boolean createP2Var = false;
		DataType p3DataType = null;
		DataType p4DataType = null;
		int nextP1Step = 1;

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
				addEvtP1Instruction(instructions1, instructions2, key.fullName);
				nextP1Step = 1;
				break;

			case GR16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(instructions1, instructions2, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(instructions1, instructions2, "p2 + 1");
				nextP1Step = 1;
				createP2Var = true;
				break;

			case ID8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP1Instruction(instructions1, instructions2, key.fullName);
				nextP1Step = 1;
				break;

			case ID16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(instructions1, instructions2, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(instructions1, instructions2, "p2 + 1");
				nextP1Step = 1;
				createP2Var = true;
				break;

			case CH8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP1Instruction(instructions1, instructions2, key.fullName);
				nextP1Step = 1;
				break;

			case CH16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(instructions1, instructions2, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(instructions1, instructions2, "p2 + 1");
				nextP1Step = 1;
				createP2Var = true;
				break;

			case BOOL8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP1Instruction(instructions1, instructions2, "BOOL_TO_BYTE(" + key.fullName + ")", nextP1Step);
				nextP1Step = 1;
				break;

			case INT8:
				return null; //Not supported on CoDeSys V2, V3

			case UINT8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP1Instruction(instructions1, instructions2, key.fullName, nextP1Step);
				nextP1Step = 1;
				break;

			case INT16:
			case UINT16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(instructions1, instructions2, "ADR(" + key.fullName + ")", nextP1Step);
				addEvtP2Instruction(instructions1, instructions2, "p2 + 1");
				nextP1Step = 1;
				createP2Var = true;
				break;

			case TICK32:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				p3DataType = key.dataType;
				addEvtP3Instruction(instructions1, key.fullName, nextP1Step);
				addEvtP2Instruction(null, instructions2, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				nextP1Step += p3DataType.getLength();
				createP2Var = true;
				break;

			case INT32:
			case UINT32:
			case REAL32:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				p4DataType = key.dataType;
				addEvtP4Instruction(instructions1, key.fullName, nextP1Step);
				addEvtP2Instruction(null, instructions2, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				nextP1Step += p4DataType.getLength();
				createP2Var = true;
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
				addEvtP4Instruction(instructions1, key.fullName, nextP1Step);
				addEvtP2Instruction(null, instructions2, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				addEvtP2Instruction(null, instructions2, "p2 + 1");
				nextP1Step += p4DataType.getLength();
				createP2Var = true;
				break;

			case STRING:
			case BYTES:
				return null; //TODO

			default:
				break;
			}

		}

		addEvtP1Instruction(instructions1, instructions2, Constant.END_FLAG.name(), nextP1Step);

		final TemplateData templateData = new TemplateData();
		templateData.setOutputFileName(functionName);

		final Map<String, Object> pouNode = new HashMap<>();
		templateData.addNode("pou", pouNode);

		final Map<String, Object> interfaceNode = new HashMap<>();
		templateData.addNode("interface", interfaceNode);

		final Map<String, Object> bodyNode = new HashMap<>();
		templateData.addNode("body", bodyNode);

		pouNode.put("path", new String[] {"core", "evt"});
		pouNode.put("name", functionName);

		interfaceNode.put("returnType", getDataType(DataType.BOOL8));

		interfaceNode.put("inputVars", inputVars);

		interfaceNode.put("inOutVars", new Variable[] {
				new Variable("Handle", "LogBufferHandle")
		});

		final ArrayList<Variable> vars = new ArrayList<>();
		interfaceNode.put("vars", vars);

		vars.add(new Variable("full", "BOOL"));
		vars.add(new Variable("start", "DWORD"));
		vars.add(new Variable("end", "DWORD"));
		vars.add(new Variable("p1", "POINTER TO BYTE"));
		if (createP2Var) {
			vars.add(new Variable("p2", "POINTER TO BYTE"));
		}
		if (p3DataType != null) {
			vars.add(new Variable("p3", "POINTER TO " + getDataType(p3DataType)));
		}
		if (p4DataType != null) {
			vars.add(new Variable("p4", "POINTER TO " + getDataType(p4DataType)));
		}

		final ArrayList<String> instructions = new ArrayList<>();
		bodyNode.put("instructions", instructions);

		instructions.add("IF Handle.BufferWritePointer >= Handle.BufferReadPointer THEN");
		instructions.add(SPC4 + "full := Handle.BufferSize - (Handle.BufferWritePointer - Handle.BufferReadPointer) < " + length + ";");
		instructions.add("ELSE");
		instructions.add(SPC4 + "full := Handle.BufferSize - (Handle.BufferSize + (Handle.BufferWritePointer - Handle.BufferReadPointer)) < " + length + ";");
		instructions.add("END_IF");
		instructions.add("");
		instructions.add("IF NOT full THEN");
		instructions.add("");
		instructions.add(SPC4 + "start := Handle.BufferAddress;");
		instructions.add(SPC4 + "end := Handle.BufferEndAddress;");
		instructions.add(SPC4 + "p1 := start + Handle.BufferWritePointer;");
		instructions.add("");
		instructions.add(SPC4 + "IF p1 + " + length + " < end THEN");
		instructions.add("");
		instructions.addAll(instructions1);
		instructions.add(SPC4 + "ELSE");
		instructions.add("");
		instructions.addAll(instructions2);
		instructions.add(SPC4 + "END_IF");
		instructions.add("");
		instructions.add(SPC4 + "Handle.BufferWritePointer := DWORD_TO_DINT(p1 - start);");
		instructions.add("");
		instructions.add("END_IF");

		return templateData;

	}

	private void addEvtP1Instruction(ArrayList<String> instructions1, ArrayList<String> instructions2, String p1Instruction) {
		addEvtP1Instruction(instructions1, instructions2, p1Instruction, 1);
	}

	private void addEvtP1Instruction(ArrayList<String> instructions1, ArrayList<String> instructions2, String p1Instruction, int p1Step) {

		if (instructions1 != null) {
			instructions1.add(SPC8 + "p1 := p1 + " + p1Step + ";");
			instructions1.add(SPC8 + "p1^ := " + p1Instruction + ";");
			instructions1.add("");			
		}

		instructions2.add(SPC8 + "IF p1 < end THEN p1 := p1 + 1; ELSE p1 := start; END_IF");
		instructions2.add(SPC8 + "p1^ := " + p1Instruction + ";");
		instructions2.add("");

	}

	private void addEvtP2Instruction(ArrayList<String> instructions1, ArrayList<String> instructions2, String p2Instruction) {
		addEvtP2Instruction(instructions1, instructions2, p2Instruction, 1);
	}

	private void addEvtP2Instruction(ArrayList<String> instructions1, ArrayList<String> instructions2, String p2Instruction, int p2Step) {

		if (instructions1 != null) {
			instructions1.add(SPC8 + "p1 := p1 + " + p2Step + ";");
			instructions1.add(SPC8 + "p2 := " + p2Instruction + ";");
			instructions1.add(SPC8 + "p1^ := p2^;");
			instructions1.add("");
		}

		instructions2.add(SPC8 + "IF p1 < end THEN p1 := p1 + 1; ELSE p1 := start; END_IF");
		instructions2.add(SPC8 + "p2 := " + p2Instruction + ";");
		instructions2.add(SPC8 + "p1^ := p2^;");
		instructions2.add("");

	}

	private void addEvtP3Instruction(ArrayList<String> instructions, String p3Instruction, int p1Step) {
		instructions.add(SPC8 + "p3 := p1 + " + p1Step + ";");
		instructions.add(SPC8 + "p3^ := " + p3Instruction + ";");
		instructions.add("");
	}

	private void addEvtP4Instruction(ArrayList<String> instructions, String p4Instruction, int p1Step) {
		instructions.add(SPC8 + "p4 := p1 + " + p1Step + ";");
		instructions.add(SPC8 + "p4^ := " + p4Instruction + ";");
		instructions.add("");
	}

}
