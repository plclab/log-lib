package nl.rp.loglib.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import nl.rp.loglib.Constant;
import nl.rp.loglib.Key;
import nl.rp.loglib.template.TemplateData;
import nl.rp.loglib.template.TemplateProcessor;

public abstract class LogLibImpl {

	public static final int OUTPUT_MODE_UNKNOWN = 0;
	public static final int OUTPUT_MODE_SINGLE_FILE = 1;
	public static final int OUTPUT_MODE_MULTIPLE_FILES = 2;
	
	
	public LogLibImpl() {
		
	}
	
	public abstract TemplateProcessor getTemplateProcessor();
	
	public abstract String getOuputDirectory();
	
	public abstract int getOutputMode();

	public void generateLogLibCore(Configuration configuration) {

		try {

			final int outputMode = getOutputMode();
			
			Key[] keys;
			Template template;
			TemplateData templateData;
			String outputFile;

			final TemplateProcessor templateProcessor = getTemplateProcessor();

			for (Constant constant : Constant.values()) {

				template = null;
				templateData = null;
				
				keys = TemplateProcessor.stringToKeys(constant.name());
				if (keys != null && keys.length > 0 && keys[0] != null) {

					System.out.println(constant.name() + ", " + constant.getValue());

					switch (keys[0]) {

					case EVT:

						template = configuration.getTemplate(
								templateProcessor.getTemplateDirectory()
								+ "/" + templateProcessor.getFuctionTemplate());
						
						templateData = templateProcessor.getEvtFunctionTemplateData(keys);

						break;

					default:
						break;
					}

				}
				
				if (template != null && templateData != null) {

					switch (outputMode) {
					
					case OUTPUT_MODE_MULTIPLE_FILES:
						
						outputFile = "log-lib/" + getOuputDirectory() + "/core/" + templateData.getOutputFileName();

						final Writer out = new OutputStreamWriter(
								new FileOutputStream(new File(outputFile)));
						
						template.process(templateData.getModel(), out);
						
						break;

					default:
						break;
					}

				}


			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
