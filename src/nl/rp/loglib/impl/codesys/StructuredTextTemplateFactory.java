package nl.rp.loglib.impl.codesys;

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
		case REAL64: return "LREAL";

		default: return null;

		}

	}

	public TemplateData getCoreConstantsTemplateData() {

		final String name = "LogLibCore";

		final List<Variable> vars = new ArrayList<>();
		for (Constant constant : Constant.values()) {
			vars.add(new Variable(constant.name(), "BYTE", "" + constant.getValue()));
		}

		final TemplateData templateData = new TemplateData();
		templateData.setOutputFileName(name);
		templateData.addNode("name", name);
		templateData.addNode("constant", true);
		templateData.addNode("vars", vars);

		return templateData;

	}

	public TemplateData getBasicGlobalsTemplateData() {

		final String name = "LogLibBasic";

		final List<Variable> vars = new ArrayList<>();
		vars.add(new Variable("GlobalLogBufferHandle", "LogBufferHandle"));
		vars.add(new Variable("GlobalTick", "UDINT"));

		final TemplateData templateData = new TemplateData();
		templateData.setOutputFileName(name);
		templateData.addNode("name", name);
		templateData.addNode("vars", vars);

		return templateData;

	}

	public TemplateData getLogBufferHandleStructTemplateData() {

		final String name = "LogBufferHandle";

		final List<Variable> vars = new ArrayList<>();
		vars.add(new Variable("BufferAddress", "DWORD"));
		vars.add(new Variable("BufferSize", "DINT"));
		vars.add(new Variable("BufferEndAddress", "DWORD"));
		vars.add(new Variable("BufferWritePointer", "DINT"));
		vars.add(new Variable("BufferReadPointer", "DINT"));
		vars.add(new Variable("BufferOverflow", "DINT"));
		vars.add(new Variable("MagicByte", "BYTE", Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.name()));

		final TemplateData templateData = new TemplateData();
		templateData.setOutputFileName(name);
		templateData.addNode("path", new String[] {"core"});
		templateData.addNode("name", name);
		templateData.addNode("vars", vars);

		return templateData;

	}

	public TemplateData getCreateBufferHandleFunctionTemplateData() {

		final List<Variable> inputVars = new ArrayList<>();	
		inputVars.add(new Variable("BufferAddress", "DWORD"));
		inputVars.add(new Variable("BufferSize", "DINT"));

		final List<Variable> inOutVars = new ArrayList<>();	
		inOutVars.add(new Variable("Handle", "LogBufferHandle"));

		final List<Variable> vars = new ArrayList<>();
		vars.add(new Variable("byteOrderInt", "INT"));
		vars.add(new Variable("byteOrderArray", "ARRAY[0..1] OF BYTE"));
		vars.add(new Variable("pInt", "POINTER TO INT"));

		final List<String> instructions = new ArrayList<>();
		instructions.add("");
		instructions.add("Handle.BufferAddress := BufferAddress;");
		instructions.add("Handle.BufferSize := BufferSize;");
		instructions.add("Handle.BufferEndAddress := BufferAddress + (BufferSize - 1);");
		instructions.add("");
		instructions.add("byteOrderInt := 1;");
		instructions.add("pInt := ADR(byteOrderArray[0]);");
		instructions.add("pInt^ := byteOrderInt;");
		instructions.add("IF byteOrderArray[0] = 1 THEN");
		instructions.add(TAB + "Handle.MagicByte := " + Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.name() + ";");
		instructions.add("ELSE");
		instructions.add(TAB + "Handle.MagicByte := " + Constant.MAGIC_BYTE_V1_BIG_ENDIAN.name() + ";");
		instructions.add("END_IF");
		instructions.add("");

		return createFunction("CreateBufferHandle", "core", inputVars, inOutVars, vars, instructions);

	}

	public TemplateData getCreateGlobalBufferHandleFunctionTemplateData() {

		final List<Variable> inputVars = new ArrayList<>();	
		inputVars.add(new Variable("BufferAddress", "DWORD"));
		inputVars.add(new Variable("BufferSize", "DINT"));

		final List<String> instructions = new ArrayList<>();
		instructions.add("CreateBufferHandle(BufferAddress, BufferSize, GlobalLogBufferHandle);");

		return createFunction("CreateGlobalBufferHandle", "basic", inputVars, null, null, instructions);

	}

	public TemplateData getLogBoolFunctionTemplateData() {
		return createBasicLogFunction("LogBool", Key.BOOL8);
	}

	public TemplateData getLogIntFunctionTemplateData() {
		return createBasicLogFunction("LogInt", Key.INT16);
	}

	public TemplateData getLogDIntFunctionTemplateData() {
		return createBasicLogFunction("LogDInt", Key.INT32);
	}

	public TemplateData getLogUDIntFunctionTemplateData() {
		return createBasicLogFunction("LogUDInt", Key.UINT32);
	}

	public TemplateData getLogRealFunctionTemplateData() {
		return createBasicLogFunction("LogReal", Key.REAL32);
	}

	public TemplateData getLogLRealFunctionTemplateData() {
		return createBasicLogFunction("LogLReal", Key.REAL64);
	}

	public TemplateData getMonitorBoolFunctionTemplateData() {
		return createBasicMonitorFunction("MonitorBool", Key.BOOL8);
	}

	public TemplateData getMonitorIntFunctionTemplateData() {
		return createBasicMonitorFunction("MonitorInt", Key.INT16);
	}

	public TemplateData getMonitorDIntFunctionTemplateData() {
		return createBasicMonitorFunction("MonitorDInt", Key.INT32);
	}

	public TemplateData getMonitorUDIntFunctionTemplateData() {
		return createBasicMonitorFunction("MonitorUDInt", Key.UINT32);
	}

	public TemplateData getMonitorRealFunctionTemplateData() {
		return createBasicMonitorFunction("MonitorReal", Key.REAL32);
	}

	public TemplateData getMonitorLRealFunctionTemplateData() {
		return createBasicMonitorFunction("MonitorLReal", Key.REAL64);
	}

	private TemplateData createBasicLogFunction(String name, Key valueKey) {

		final List<Variable> inputVars = new ArrayList<>();	
		inputVars.add(new Variable("Group", "BYTE"));
		inputVars.add(new Variable("Id", "BYTE"));
		inputVars.add(new Variable("Channel", "UINT"));
		inputVars.add(new Variable("Value", getDataType(valueKey.dataType)));

		final List<String> instructions = new ArrayList<>();
		addBasicLogInstructions(instructions, valueKey, "");

		return createFunction(name, "basic", inputVars, null, null, instructions);

	}

	private TemplateData createBasicMonitorFunction(String name, Key valueKey) {

		final List<Variable> inputVars = new ArrayList<>();	
		inputVars.add(new Variable("Group", "BYTE"));
		inputVars.add(new Variable("Id", "BYTE"));
		inputVars.add(new Variable("Channel", "UINT"));
		inputVars.add(new Variable("Value", getDataType(valueKey.dataType)));

		final List<Variable> inOutVars = new ArrayList<>();	
		inOutVars.add(new Variable("Data", getDataType(valueKey.dataType)));

		final List<String> instructions = new ArrayList<>();
		instructions.add("IF Value <> Data THEN");
		addBasicLogInstructions(instructions, valueKey, TAB);
		instructions.add(TAB + "Data := Value;");
		instructions.add("END_IF");

		return createFunction(name, "basic", inputVars, inOutVars, null, instructions);

	}

	private void addBasicLogInstructions(List<String> instructions, Key valueKey, String tab) {
		instructions.add(tab + "IF Group > 0 OR Id > 0 THEN");
		instructions.add(tab + TAB + "IF Channel < 256 THEN");
		instructions.add(tab + TABTAB + "EvtGr8Id8Ch8Tick32" + valueKey.shortName + "(Group, Id, UINT_TO_BYTE(Channel), GlobalTick, Value, GlobalLogBufferHandle);");
		instructions.add(tab + TAB + "ELSE");
		instructions.add(tab + TABTAB + "EvtGr8Id8Ch16Tick32" + valueKey.shortName + "(Group, Id, Channel, GlobalTick, Value, GlobalLogBufferHandle);");
		instructions.add(tab + TAB + "END_IF");
		instructions.add(tab + "ELSIF Channel < 256 THEN");
		instructions.add(tab + TAB + "EvtCh8Tick32" + valueKey.shortName + "(UINT_TO_BYTE(Channel), GlobalTick, Value, GlobalLogBufferHandle);");
		instructions.add(tab + "ELSE");
		instructions.add(tab + TAB + "EvtCh16Tick32" + valueKey.shortName + "(Channel, GlobalTick, Value, GlobalLogBufferHandle);");
		instructions.add(tab + "END_IF");
	}

	public TemplateData getGetNextWritePointerFunctionTemplateData() {

		final List<Variable> inputVars = new ArrayList<>();	
		inputVars.add(new Variable("Length", "DINT"));
		inputVars.add(new Variable("WritePointer", "DINT"));
		inputVars.add(new Variable("ReadPointer", "DINT"));
		inputVars.add(new Variable("BufferSize", "DINT"));

		final List<Variable> inOutVars = new ArrayList<>();	
		inOutVars.add(new Variable("BufferOverflow", "DINT"));

		final List<String> instructions = new ArrayList<>();
		instructions.add("GetNextWritePointer := -1;");
		instructions.add("IF WritePointer >= ReadPointer THEN");
		instructions.add(TAB + "IF WritePointer + Length + 1 >= BufferSize THEN");
		instructions.add(TABTAB + "IF ReadPointer >= Length THEN");
		instructions.add(TABTABTAB + "BufferOverflow := WritePointer;");
		instructions.add(TABTABTAB + "GetNextWritePointer := 0;");
		instructions.add(TABTAB + "END_IF");
		instructions.add(TAB + "ELSIF BufferSize - (WritePointer - ReadPointer) >= Length THEN");
		instructions.add(TABTAB + "GetNextWritePointer := WritePointer + 1;");
		instructions.add(TAB + "END_IF");
		instructions.add("ELSIF ReadPointer - WritePointer >= Length THEN");
		instructions.add(TAB + "GetNextWritePointer := WritePointer + 1;");
		instructions.add("END_IF");

		return createFunction("GetNextWritePointer", new String[] {"core", "evt"}, DataType.INT32, inputVars, inOutVars, null, instructions);

	}

	public TemplateData getEvtFunctionTemplateData(Constant evtConstant) {

		String functionName = "";

		final Key[] keys = Key.stringToKeys(evtConstant.name());
		final int length = Key.getDataLength(keys) + 4;

		final List<Variable> inputVars = new ArrayList<>();	

		final List<String> evtInstructions = new ArrayList<String>();	

		addEvtP1Instruction(evtInstructions, Constant.START_FLAG.name(), 0);
		addEvtP1Instruction(evtInstructions, "Handle.MagicByte");
		addEvtP1Instruction(evtInstructions, evtConstant.name());

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
				addEvtP1Instruction(evtInstructions, key.fullName);
				nextP1Step = 1;
				break;

			case GR16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(evtInstructions, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(evtInstructions, "p2 + 1");
				nextP1Step = 1;
				createP2Var = true;
				break;

			case ID8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP1Instruction(evtInstructions, key.fullName);
				nextP1Step = 1;
				break;

			case ID16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(evtInstructions, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(evtInstructions, "p2 + 1");
				nextP1Step = 1;
				createP2Var = true;
				break;

			case CH8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP1Instruction(evtInstructions, key.fullName);
				nextP1Step = 1;
				break;

			case CH16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(evtInstructions, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(evtInstructions, "p2 + 1");
				nextP1Step = 1;
				createP2Var = true;
				break;

			case BOOL8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP1Instruction(evtInstructions, "BOOL_TO_BYTE(" + key.fullName + ")", nextP1Step);
				nextP1Step = 1;
				break;

			case INT8:
				return null; //Not supported on CoDeSys V2, V3

			case UINT8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP1Instruction(evtInstructions, key.fullName, nextP1Step);
				nextP1Step = 1;
				break;

			case INT16:
			case UINT16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(evtInstructions, "ADR(" + key.fullName + ")", nextP1Step);
				addEvtP2Instruction(evtInstructions, "p2 + 1");
				nextP1Step = 1;
				createP2Var = true;
				break;

			case TICK32:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				p3DataType = key.dataType;
				addEvtP3Instruction(evtInstructions, key.fullName, nextP1Step);
				nextP1Step += p3DataType.getLength();
				createP2Var = true;
				break;

			case INT32:
			case UINT32:
			case REAL32:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				p4DataType = key.dataType;
				addEvtP4Instruction(evtInstructions, key.fullName, nextP1Step);
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
				addEvtP4Instruction(evtInstructions, key.fullName, nextP1Step);
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

		addEvtP1Instruction(evtInstructions, Constant.END_FLAG.name(), nextP1Step);

		final TemplateData templateData = new TemplateData();
		templateData.setOutputFileName(functionName);

		final Map<String, Object> pouNode = new HashMap<>();
		templateData.addNode("pou", pouNode);
		pouNode.put("path", new String[] {"core", "evt"});
		pouNode.put("name", functionName);

		final Map<String, Object> interfaceNode = new HashMap<>();
		templateData.addNode("interface", interfaceNode);
		interfaceNode.put("returnType", getDataType(DataType.BOOL8));
		interfaceNode.put("inputVars", inputVars);
		interfaceNode.put("inOutVars", new Variable[] {
				new Variable("Handle", "LogBufferHandle")
		});

		final List<Variable> vars = new ArrayList<>();
		interfaceNode.put("vars", vars);
		vars.add(new Variable("i", "DINT"));
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

		final Map<String, Object> bodyNode = new HashMap<>();
		templateData.addNode("body", bodyNode);

		final List<String> instructions = new ArrayList<>();
		bodyNode.put("instructions", instructions);
		instructions.add("i := GetNextWritePointer(" + length + ", Handle.BufferWritePointer, Handle.BufferReadPointer, Handle.BufferSize, Handle.BufferOverflow);");
		instructions.add("IF i >= 0 THEN");
		instructions.add("");
		instructions.add(TAB + "p1 := Handle.BufferAddress + i;");
		instructions.add("");
		instructions.addAll(evtInstructions);
		instructions.add(TAB + "Handle.BufferWritePointer := DWORD_TO_DINT(p1 - Handle.BufferAddress);");
		instructions.add("");
		instructions.add("END_IF");

		return templateData;

	}

	private void addEvtP1Instruction(List<String> instructions, String p1Instruction) {
		addEvtP1Instruction(instructions, p1Instruction, 1);
	}

	private void addEvtP1Instruction(List<String> instructions, String p1Instruction, int p1Step) {
		if (p1Step > 0) {
			instructions.add(TAB + "p1 := p1 + " + p1Step + ";");
		}
		instructions.add(TAB + "p1^ := " + p1Instruction + ";");
		instructions.add("");			
	}

	private void addEvtP2Instruction(List<String> instructions, String p2Instruction) {
		addEvtP2Instruction(instructions, p2Instruction, 1);
	}

	private void addEvtP2Instruction(List<String> instructions, String p2Instruction, int p2Step) {
		if (p2Step > 0) {
			instructions.add(TAB + "p1 := p1 + " + p2Step + ";");
		}
		instructions.add(TAB + "p2 := " + p2Instruction + ";");
		instructions.add(TAB + "p1^ := p2^;");
		instructions.add("");
	}

	private void addEvtP3Instruction(List<String> instructions, String p3Instruction, int p1Step) {
		if (p1Step > 0) {
			instructions.add(TAB + "p3 := p1 + " + p1Step + ";");
		}
		instructions.add(TAB + "p3^ := " + p3Instruction + ";");
		instructions.add("");
	}

	private void addEvtP4Instruction(List<String> instructions, String p4Instruction, int p1Step) {
		if (p1Step > 0) {
			instructions.add(TAB + "p4 := p1 + " + p1Step + ";");
		}
		instructions.add(TAB + "p4^ := " + p4Instruction + ";");
		instructions.add("");
	}

	private TemplateData createFunction(String name, String path,
			List<Variable> inputVars, List<Variable> inOutVars,
			List<Variable> vars, List<String> instructions) {
		return createFunction(name, new String[] {path}, null, inputVars, inOutVars, vars, instructions);
	}

	private TemplateData createFunction(String name, String[] path, DataType returnType,
			List<Variable> inputVars, List<Variable> inOutVars,
			List<Variable> vars, List<String> instructions) {

		final TemplateData templateData = new TemplateData();
		templateData.setOutputFileName(name);

		final Map<String, Object> pouNode = new HashMap<>();
		templateData.addNode("pou", pouNode);
		pouNode.put("name", name);
		if (path != null) {
			pouNode.put("path", path);
		}

		final Map<String, Object> interfaceNode = new HashMap<>();
		templateData.addNode("interface", interfaceNode);

		if (returnType != null) {
			interfaceNode.put("returnType", getDataType(returnType));
		} else {			
			interfaceNode.put("returnType", getDataType(DataType.BOOL8));
		}

		if (inputVars != null) {
			interfaceNode.put("inputVars", inputVars);
		}
		if (inOutVars != null) {
			interfaceNode.put("inOutVars", inOutVars);
		}
		if (vars != null) {
			interfaceNode.put("vars", vars);
		}

		final Map<String, Object> bodyNode = new HashMap<>();
		templateData.addNode("body", bodyNode);

		if (instructions != null) {			
			bodyNode.put("instructions", instructions);
		}

		return templateData;

	}

}
