package nl.rp.loglib.impl.bachmann.mplc;

import nl.rp.loglib.impl.LogLibImpl;
import nl.rp.loglib.impl.TemplateProcessor;
import nl.rp.loglib.impl.codesys.v2.ExpTemplateProcessor;

public class BachmannMPlc extends LogLibImpl {
	
	private final TemplateProcessor templateProcessor;
	
	
	public BachmannMPlc() {
		templateProcessor = new ExpTemplateProcessor();
	}
	
	@Override
	public TemplateProcessor getTemplateProcessor() {
		return templateProcessor;
	}
	
	@Override
	public String getOuputDirectory() {
		return "bachmann/mplc";
	}
	
	@Override
	public int getOutputMode() {
		return OUTPUT_MODE_MULTIPLE_FILES;
	}
	
}
