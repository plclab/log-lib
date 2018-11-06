package nl.rp.loglib.impl;

import freemarker.template.Configuration;

public abstract class LogLibImpl {

	public static final String OUTPUT_BASE_DIR = "log-lib";
	public static final String LIB_CORE_DIR = "core";
	public static final String LIB_BASIC_DIR = "basic";


	public LogLibImpl() {

	}

	protected abstract String getOutputDirectory();
	
	public abstract void generate(Configuration configuration);

}
