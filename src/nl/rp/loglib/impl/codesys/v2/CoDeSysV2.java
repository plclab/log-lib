package nl.rp.loglib.impl.codesys.v2;

import nl.rp.loglib.impl.LogLibImpl;
import nl.rp.loglib.impl.TemplateProcessor;

public class CoDeSysV2 extends LogLibImpl {
	
	private final TemplateProcessor templateProcessor;
	
	
	public CoDeSysV2() {
		templateProcessor = new ExpTemplateProcessor();
	}
	
	@Override
	public TemplateProcessor getTemplateProcessor() {
		return templateProcessor;
	}
	
	@Override
	public String getOuputDirectory() {
		return "codesys/v2";
	}
	
	@Override
	public int getOutputMode() {
		return OUTPUT_MODE_MULTIPLE_FILES;
	}
	
}
