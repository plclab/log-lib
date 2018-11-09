package nl.rp.loglib.impl.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import nl.rp.loglib.Constant;
import nl.rp.loglib.Variable;
import nl.rp.loglib.impl.LogLibImpl;
import nl.rp.loglib.impl.TemplateData;

public class Java extends LogLibImpl {

	public static final String TEMPLATE_DIR = "java";

	private final JavaTemplateFactory templateFactory;


	public Java() {
		templateFactory = new JavaTemplateFactory();
	}

	@Override
	protected String getOutputDirectory() {
		return "java";
	}

	@Override
	public void generate(Configuration configuration) {

		try {

			final Template template = configuration.getTemplate(TEMPLATE_DIR + "/Class.ftl");

			final TemplateData templateData = new TemplateData();
			templateData.setOutputFileName(OUTPUT_BASE_DIR + "/" + getOutputDirectory() +
					"/" + LIB_CORE_DIR + "/" + "LogBuffer.java");

			final Map<String, Object> classNode = new HashMap<>();
			templateData.addNode("class", classNode);
			classNode.put("package", "nl.rp.loglib.java");
			classNode.put("name", "LogBuffer");
			classNode.put("modifiers", new String[] {"public"});
			classNode.put("imports", new String[] {"java.util.Arrays"});

			final ArrayList<Variable> constants = new ArrayList<>();
			classNode.put("constants", constants);

			final ArrayList<Variable> vars = new ArrayList<>();
			classNode.put("vars", vars);

			final ArrayList<Map<String, Object>> constructors = new ArrayList<>();
			classNode.put("constructors", constructors);
			
			final ArrayList<Map<String, Object>> methods = new ArrayList<>();
			classNode.put("methods", methods);

			//Create constants
			constants.addAll(templateFactory.getConstants());

			//Create buffer members
			vars.addAll(templateFactory.getMembers());

			//Create default constructor
			constructors.add(templateFactory.getDefaultConstructor());

			//Create getBufferWritePointer() method
			methods.add(templateFactory.getGetBufferWritePointerMethod());

			//Create getBufferReadPointer() method
			methods.add(templateFactory.getGetBufferReadPointerMethod());

			//Create getBufferOverflow() method
			methods.add(templateFactory.getGetBufferOverflowMethod());

			//Create getNextWritePointer() method
			methods.add(templateFactory.getGetNextWritePointerMethod());

			//Create next() method (next index with overflow check)
			//TODO
			//methods.add(templateFactory.getNextMethod());

			//Create Evt methods
			Map<String, Object> method;
			for (Constant constant : Constant.CORE_EVENTS_DEFAULT) {
				method = templateFactory.getEvtMethod(constant.name());
				if (method != null) {
					methods.add(method);							
				}
			}
			
			//Create readBytes() method
			methods.add(templateFactory.getReadBytesMethod());

			//Process the template and write to output directory
			final File logBufferOutputFile = new File(templateData.getOutputFileName());

			final Writer out = new OutputStreamWriter(
					new FileOutputStream(logBufferOutputFile));

			template.process(templateData.getModel(), out);

			//Copy the file to this project so we can use it for testing
			Files.copy(logBufferOutputFile.toPath(),
					new File("src/nl/rp/loglib/java/LogBuffer.java").toPath(),
					StandardCopyOption.REPLACE_EXISTING);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}