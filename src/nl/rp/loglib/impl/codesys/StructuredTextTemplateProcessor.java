package nl.rp.loglib.impl.codesys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.rp.loglib.Constant;
import nl.rp.loglib.DataType;
import nl.rp.loglib.Key;
import nl.rp.loglib.Variable;
import nl.rp.loglib.impl.TemplateData;
import nl.rp.loglib.impl.TemplateProcessor;

public abstract class StructuredTextTemplateProcessor extends TemplateProcessor {


	public StructuredTextTemplateProcessor() {

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
	
	@Override
	public TemplateData getGlobalConstantsTemplateData() {
		
		final TemplateData templateData = new TemplateData();
		
		final String name = "LogLibCore";
		
		templateData.setOutputFileName(name + "." + getOutputExtension());
		
		templateData.addNode("name", name);
		
		templateData.addNode("constant", true);
		
		final ArrayList<Variable> vars = new ArrayList<Variable>();
		templateData.addNode("vars", vars);
		
		for (Constant constant : Constant.values()) {
			vars.add(new Variable(constant.name(), "BYTE", "" + constant.getValue()));
		}
		
		return templateData;
		
	}
	
	@Override
	public TemplateData getLogBufferHandleStructTemplateData() {

		final TemplateData templateData = new TemplateData();
		
		final String name = "LogBufferHandle";

		templateData.setOutputFileName(name + "." + getOutputExtension());

		templateData.addNode("path", new String[] {"core"});
		
		templateData.addNode("name", name);

		final ArrayList<Variable> vars = new ArrayList<Variable>();
		templateData.addNode("vars", vars);

		vars.add(new Variable("BufferAddress", "DWORD"));
		vars.add(new Variable("BufferSize", "UDINT"));
		vars.add(new Variable("BufferWritePointer", "DINT"));
		vars.add(new Variable("BufferReadPointer", "DINT"));
		vars.add(new Variable("MagicByte", "BYTE", Constant.MAGIC_BYTE_V1_LITTLE_ENDIAN.name()));
		
		return templateData;
		
	}

	@Override
	public TemplateData getEvtFunctionTemplateData(Key[] keys) {

		String functionName = "";

		final ArrayList<Variable> inputVars = new ArrayList<Variable>();	

		final ArrayList<String> mainInstructions = new ArrayList<String>();	
		addEvtP1Instruction(mainInstructions, Constant.START_FLAG.name());
		addEvtP1Instruction(mainInstructions, "Handle.MagicByte");
		addEvtP1Instruction(mainInstructions, keysToString(keys));

		boolean createP2Var = false;

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
				addEvtP1Instruction(mainInstructions, key.fullName);
				break;

			case GR16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(mainInstructions, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				createP2Var = true;
				break;

			case ID8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP1Instruction(mainInstructions, key.fullName);
				break;

			case ID16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(mainInstructions, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				createP2Var = true;
				break;

			case CH8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP1Instruction(mainInstructions, key.fullName);
				break;

			case CH16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(mainInstructions, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				createP2Var = true;
				break;

			case BOOL8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP1Instruction(mainInstructions, "BOOL_TO_BYTE(" + key.fullName + ")");
				break;

			case INT8:
				return null; //Not supported on CoDeSys V2, V3

			case UINT8:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP1Instruction(mainInstructions, key.fullName);
				break;

			case INT16:
			case UINT16:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(mainInstructions, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				createP2Var = true;
				break;

			case TICK32:
			case INT32:
			case UINT32:
			case REAL32:
				functionName += key.shortName;
				inputVars.add(new Variable(key.fullName, getDataType(key.dataType)));
				addEvtP2Instruction(mainInstructions, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
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
				addEvtP2Instruction(mainInstructions, "ADR(" + key.fullName + ")");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				addEvtP2Instruction(mainInstructions, "p2 + 1");
				createP2Var = true;
				break;

			case STRING:
			case BYTES:
				return null; //TODO

			default:
				break;
			}

		}

		addEvtP1Instruction(mainInstructions, Constant.END_FLAG.name());

		final TemplateData templateData = new TemplateData();
		templateData.setOutputFileName(functionName + "." + getOutputExtension());

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

		final ArrayList<Variable> vars = new ArrayList<Variable>();
		interfaceNode.put("vars", vars);

		vars.add(new Variable("start", "DWORD"));
		vars.add(new Variable("end", "DWORD"));
		vars.add(new Variable("p1", "POINTER TO BYTE"));
		if (createP2Var) {
			vars.add(new Variable("p2", "POINTER TO BYTE"));
		}

		final ArrayList<String> instructions = new ArrayList<String>();
		bodyNode.put("instructions", instructions);

		instructions.add("");
		instructions.add("start := Handle.BufferAddress;");
		instructions.add("end := start + (Handle.BufferSize - 1);");
		instructions.add("p1 := Handle.BufferAddress + Handle.BufferWritePointer;");
		instructions.add("");
		instructions.addAll(mainInstructions);
		instructions.add("Handle.BufferWritePointer := DWORD_TO_DINT(p1 - start);");
		instructions.add("");

		return templateData;

	}

	private void addEvtP1Instruction(ArrayList<String> instructions, String p1Instruction) {
		instructions.add("IF p1 = end THEN p1 := start; ELSE p1 := p1 + 1; END_IF");
		instructions.add("p1^ := " + p1Instruction + ";");
		instructions.add("");
	}

	private void addEvtP2Instruction(ArrayList<String> instructions, String p2Instruction) {
		instructions.add("IF p1 = end THEN p1 := start; ELSE p1 := p1 + 1; END_IF");
		instructions.add("p2 := " + p2Instruction + ";");
		instructions.add("p1^ := p2^;");
		instructions.add("");
	}

}
