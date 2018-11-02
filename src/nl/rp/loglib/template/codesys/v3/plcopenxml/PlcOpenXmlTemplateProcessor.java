package nl.rp.loglib.template.codesys.v3.plcopenxml;

import nl.rp.loglib.template.codesys.StructuredTextTemplateProcessor;

public class PlcOpenXmlTemplateProcessor extends StructuredTextTemplateProcessor {

	
	public PlcOpenXmlTemplateProcessor() {
		
	}
	
	@Override
	public String getTemplateDirectory() {
		return "codesys/v3/plcopenxml";
	}
	
	@Override
	public String getGlobalVariableListTemplate() {
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
