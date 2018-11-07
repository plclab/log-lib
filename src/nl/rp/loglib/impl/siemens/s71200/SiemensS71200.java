package nl.rp.loglib.impl.siemens.s71200;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import nl.rp.loglib.Constant;
import nl.rp.loglib.impl.LogLibImpl;
import nl.rp.loglib.impl.TemplateData;
import nl.rp.loglib.impl.siemens.SclTemplateFactory;

public class SiemensS71200 extends LogLibImpl {

	public static final String TEMPLATE_DIR = "siemens";

	private final SclTemplateFactory templateFactory;
	
	
	public SiemensS71200() {
		templateFactory = new SclTemplateFactory();
	}
	
	@Override
	protected String getOutputDirectory() {
		return "siemens/s7-1200";
	}
	
	@Override
	public void generate(Configuration configuration) {
		
		try {

			Template template;
			TemplateData templateData;

			//All UDT's are written to a single data-types (.udt) file
			final String logLibCoreDataTypesOutputFileName = OUTPUT_BASE_DIR + "/" + getOutputDirectory() + "/" +
					LIB_CORE_DIR + "/" + "LogLibCoreDataTypes.udt";
			
			final File logLibCoreDataTypesOutputFile = new File(logLibCoreDataTypesOutputFileName);
			
			//Clear the contents of the output file if it already exists
			if (logLibCoreDataTypesOutputFile.exists()) {
				final PrintWriter printWriter = new PrintWriter(logLibCoreDataTypesOutputFile);
				printWriter.print("");
				printWriter.close();
			}
			
			//Create a FileWriter which appends each processed template
			final Writer logLibCoreDataTypesOutputWriter = new FileWriter(logLibCoreDataTypesOutputFile, true);

			//Create LogBuffer struct
			template = configuration.getTemplate(TEMPLATE_DIR + "/udt/Struct.ftl");
			templateData = templateFactory.getLogBufferStructTemplateData();
			template.process(templateData.getModel(), logLibCoreDataTypesOutputWriter);
			
			//All FC's, FB's and DB's are written to a single .scl file
			final String logLibCoreOutputFileName = OUTPUT_BASE_DIR + "/" + getOutputDirectory() + "/" +
					LIB_CORE_DIR + "/" + "LogLibCore.scl";
			
			final File logLibCoreOutputFile = new File(logLibCoreOutputFileName);
			
			//Clear the contents of the output file if it already exists
			if (logLibCoreOutputFile.exists()) {
				final PrintWriter printWriter = new PrintWriter(logLibCoreOutputFile);
				printWriter.print("");
				printWriter.close();
			}
			
			//Create a FileWriter which appends each processed template
			final Writer logLibCoreOutputWriter = new FileWriter(logLibCoreOutputFile, true);
			
			//Create CreateBufferHandle function
			template = configuration.getTemplate(TEMPLATE_DIR + "/scl/Function.ftl");
			templateData = templateFactory.getCreateBufferHandleFunctionTemplateData();
			template.process(templateData.getModel(), logLibCoreOutputWriter);

			//Create GetNextWritePointer function
			templateData = templateFactory.getGetNextWritePointerFunctionTemplateData();
			template.process(templateData.getModel(), logLibCoreOutputWriter);

			//Create Evt functions
			for (Constant constant : Constant.CORE_EVENTS) {
				templateData = templateFactory.getEvtFunctionTemplateData(constant);
				template.process(templateData.getModel(), logLibCoreOutputWriter);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
