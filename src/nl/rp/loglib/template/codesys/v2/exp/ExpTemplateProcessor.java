package nl.rp.loglib.template.codesys.v2.exp;

import nl.rp.loglib.template.codesys.StructuredTextTemplateProcessor;

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
	public String getFuctionTemplate() {
		return "Function.ftl";
	}
	
	@Override
	public String getOutputExtension() {
		return "EXP";
	}
	
}
