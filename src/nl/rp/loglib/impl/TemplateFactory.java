package nl.rp.loglib.impl;

import nl.rp.loglib.DataType;

public abstract class TemplateFactory {

	public static final String SPC4 = "    ";
	public static final String SPC8 = "        ";


	public TemplateFactory() {

	}

	public abstract String getDataType(DataType dataType);

}
