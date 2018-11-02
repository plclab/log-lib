package nl.rp.loglib.impl.codesys.v3;

import nl.rp.loglib.impl.codesys.StructuredTextTemplateProcessor;

public class PlcOpenXmlTemplateProcessor extends StructuredTextTemplateProcessor {

	
	public PlcOpenXmlTemplateProcessor() {
		
	}
	
	@Override
	public String getTemplateDirectory() {
		return "codesys/v3/plcopenxml";
	}
	
	@Override
	public String getGlobalVariableListTemplate() {
		return "GlobalVariableList.ftlx";
	}
	
	@Override
	public String getStructTemplate() {
		return null; //TODO
	}
	
	@Override
	public String getFuctionTemplate() {
		return "Function.ftlx";
	}
	
	@Override
	public String getOutputExtension() {
		return "xml";
	}
	
}
