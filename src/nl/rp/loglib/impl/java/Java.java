package nl.rp.loglib.impl.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import nl.rp.loglib.Constant;
import nl.rp.loglib.Key;
import nl.rp.loglib.Variable;
import nl.rp.loglib.impl.LogLibImpl;
import nl.rp.loglib.impl.TemplateData;

public class Java extends LogLibImpl {
	
	public static final String TEMPLATE_DIR = "java";
	public static final String OUTPUT_FILE_EXTENSION = ".java";

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
					"/core/" + "LogBuffer" + OUTPUT_FILE_EXTENSION);

			final Map<String, Object> classNode = new HashMap<>();
			templateData.addNode("class", classNode);
			
			classNode.put("package", "nl.rp.loglib.java");
			classNode.put("name", "LogBuffer");
			classNode.put("modifiers", new String[] {"public"});
			
			final ArrayList<Variable> vars = new ArrayList<>();
			classNode.put("vars", vars);
			
			final ArrayList<Map<String, Object>> methods = new ArrayList<>();
			classNode.put("methods", methods);

			//Create constants
			vars.addAll(templateFactory.getConstants());

			//Create buffer members
			vars.addAll(templateFactory.getMembers());

			//Create Evt methods
			Key[] keys;
			for (Constant constant : Constant.CORE_EVENTS) {

				keys = Key.stringToKeys(constant.name());
				if (keys != null && keys.length > 0 && keys[0] != null) {

					System.out.println(constant.name() + ", " + constant.getValue());

					switch (keys[0]) {

					case EVT:
						methods.add(templateFactory.getEvtMethod(keys));
						break;

					default:
						break;
					}

				}

			}
			
			//Process the template and write to output directory
			final Writer out = new OutputStreamWriter(
					new FileOutputStream(new File(templateData.getOutputFileName())));

			template.process(templateData.getModel(), out);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}