package nl.rp.loglib.impl.codesys.v2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import nl.rp.loglib.Constant;
import nl.rp.loglib.Key;
import nl.rp.loglib.impl.LogLibImpl;
import nl.rp.loglib.impl.TemplateData;
import nl.rp.loglib.impl.TemplateProcessor;
import nl.rp.loglib.impl.codesys.StructuredTextTemplateProcessor;

public class CoDeSysV2 extends LogLibImpl {

	public static final String TEMPLATE_DIR = "codesys/v2/exp";
	public static final String OUTPUT_FILE_EXTENSION = ".EXP";

	private final StructuredTextTemplateProcessor templateProcessor;


	public CoDeSysV2() {
		templateProcessor = new StructuredTextTemplateProcessor();
	}

	@Override
	protected String getOutputDirectory() {
		return "codesys/v2";
	}

	@Override
	public void generate(Configuration configuration) {

		try {

			Template template;
			TemplateData templateData;

			//Create global constants
			template = configuration.getTemplate(TEMPLATE_DIR + "/GlobalVariableList.ftl");

			templateData = templateProcessor.getGlobalConstantsTemplateData();

			processTemplate(template, templateData);

			//Create LogBufferHandle struct
			template = configuration.getTemplate(TEMPLATE_DIR + "/Struct.ftl");

			templateData = templateProcessor.getLogBufferHandleStructTemplateData();

			processTemplate(template, templateData);			

			//Create Evt functions
			Key[] keys;
			for (Constant constant : Constant.CORE_EVENTS) {

				template = null;
				templateData = null;

				keys = TemplateProcessor.stringToKeys(constant.name());
				if (keys != null && keys.length > 0 && keys[0] != null) {

					System.out.println(constant.name() + ", " + constant.getValue());

					switch (keys[0]) {

					case EVT:

						template = configuration.getTemplate(
								TEMPLATE_DIR + "/Function.ftl");

						templateData = templateProcessor.getEvtFunctionTemplateData(keys);

						break;

					default:
						break;
					}

				}

				processTemplate(template, templateData);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void processTemplate(Template template, TemplateData templateData) {

		if (template != null && templateData != null) {

			try {

				final String outputFile = OUTPUT_BASE_DIR + "/" + getOutputDirectory() +
						"/core/" + templateData.getOutputFileName() + OUTPUT_FILE_EXTENSION;

				final Writer out = new OutputStreamWriter(
						new FileOutputStream(new File(outputFile)));

				template.process(templateData.getModel(), out);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
