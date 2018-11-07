package nl.rp.loglib.impl;

import nl.rp.loglib.DataType;

public abstract class TemplateFactory {

	public static final String TAB = "    ";
	public static final String TABTAB = "        ";
	public static final String TABTABTAB = "            ";


	public TemplateFactory() {

	}

	public abstract String getDataType(DataType dataType);

}
