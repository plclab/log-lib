package nl.rp.loglib.impl.codesys.v2;

import nl.rp.loglib.impl.codesys.StructuredTextTemplateProcessor;

public class ExpTemplateProcessor extends StructuredTextTemplateProcessor {

	
	public ExpTemplateProcessor() {
		
	}
	
	@Override
	public String getTemplateDirectory() {
		return "codesys/v2/exp";
	}
	
	@Override
	public String getGlobalVariableListTemplate() {
		return "GlobalVariableList.ftl";
	}
	
	@Override
	public String getStructTemplate() {
		return "Struct.ftl";
	}
	
	@Override
	public String getFuctionTemplate() {
		return "Function.ftl";
	}
	
	@Override
	public String getOutputExtension() {
		return "EXP";
	}
	
}
