package nl.rp.loglib.impl.codesys.v2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import nl.rp.loglib.Constant;
import nl.rp.loglib.impl.LogLibImpl;
import nl.rp.loglib.impl.TemplateData;
import nl.rp.loglib.impl.codesys.StructuredTextTemplateFactory;

public class CoDeSysV2 extends LogLibImpl {

	public static final String TEMPLATE_DIR = "codesys/v2/exp";
	public static final String OUTPUT_FILE_EXTENSION = ".EXP";

	private final StructuredTextTemplateFactory templateFactory;


	public CoDeSysV2() {
		templateFactory = new StructuredTextTemplateFactory();
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
			templateData = templateFactory.getCoreConstantsTemplateData();
			processTemplate(template, templateData, LIB_CORE_DIR);

			//Create LogBufferHandle struct
			template = configuration.getTemplate(TEMPLATE_DIR + "/Struct.ftl");
			templateData = templateFactory.getLogBufferHandleStructTemplateData();
			processTemplate(template, templateData, LIB_CORE_DIR);			

			//Create CreateBufferHandle function
			template = configuration.getTemplate(TEMPLATE_DIR + "/Function.ftl");
			templateData = templateFactory.getCreateBufferHandleFunctionTemplateData();
			processTemplate(template, templateData, LIB_CORE_DIR);			

			//Create Evt functions
			for (Constant constant : Constant.CORE_EVENTS) {
				templateData = templateFactory.getEvtFunctionTemplateData(constant.name());
				processTemplate(template, templateData, LIB_CORE_DIR);
			}

			//Create global variables
			template = configuration.getTemplate(TEMPLATE_DIR + "/GlobalVariableList.ftl");
			templateData = templateFactory.getBasicGlobalsTemplateData();
			processTemplate(template, templateData, LIB_BASIC_DIR);

			//Create log-lib-basic functions
			template = configuration.getTemplate(TEMPLATE_DIR + "/Function.ftl");

			processTemplate(template, templateFactory.getCreateGlobalBufferHandleFunctionTemplateData(), LIB_BASIC_DIR);			
			processTemplate(template, templateFactory.getLogBoolFunctionTemplateData(), LIB_BASIC_DIR);			
			processTemplate(template, templateFactory.getLogDintFunctionTemplateData(), LIB_BASIC_DIR);			
			processTemplate(template, templateFactory.getLogRealFunctionTemplateData(), LIB_BASIC_DIR);			
			processTemplate(template, templateFactory.getMonitorBoolFunctionTemplateData(), LIB_BASIC_DIR);			
			processTemplate(template, templateFactory.getMonitorDintFunctionTemplateData(), LIB_BASIC_DIR);			
			processTemplate(template, templateFactory.getMonitorRealFunctionTemplateData(), LIB_BASIC_DIR);			


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void processTemplate(Template template, TemplateData templateData, String libraryDir) {

		if (template != null && templateData != null) {

			try {

				final String outputFile = OUTPUT_BASE_DIR + "/" + getOutputDirectory() + "/" +
						libraryDir + "/" + templateData.getOutputFileName() + OUTPUT_FILE_EXTENSION;

				final Writer out = new OutputStreamWriter(
						new FileOutputStream(new File(outputFile)));

				template.process(templateData.getModel(), out);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
