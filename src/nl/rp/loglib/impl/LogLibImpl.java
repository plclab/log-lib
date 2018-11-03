package nl.rp.loglib.impl;

import freemarker.template.Configuration;

public abstract class LogLibImpl {

	public static final String OUTPUT_BASE_DIR = "log-lib";


	public LogLibImpl() {

	}

	protected abstract String getOutputDirectory();
	
	public abstract void generate(Configuration configuration);

}
