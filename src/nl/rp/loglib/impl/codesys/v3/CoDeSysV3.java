package nl.rp.loglib.impl.codesys.v3;

import nl.rp.loglib.impl.LogLibImpl;
import nl.rp.loglib.impl.TemplateProcessor;

public class CoDeSysV3 extends LogLibImpl {
	
	private final TemplateProcessor templateProcessor;
	
	
	public CoDeSysV3() {
		templateProcessor = new PlcOpenXmlTemplateProcessor();
	}
	
	@Override
	public TemplateProcessor getTemplateProcessor() {
		return templateProcessor;
	}
	
	@Override
	public String getOuputDirectory() {
		return "codesys/v3";
	}
	
	@Override
	public int getOutputMode() {
		return OUTPUT_MODE_MULTIPLE_FILES;
	}
	
}
