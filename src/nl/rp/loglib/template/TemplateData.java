package nl.rp.loglib.template;

import java.util.HashMap;
import java.util.Map;

public class TemplateData {

	private final Map<String, Object> model;
	private String outputFileName = null;
	
	
	public TemplateData() {
		model = new HashMap<>();
	}
	
	public Map<String, Object> getModel() {
		return model;
	}
	
	public void addNode(String key, Object value) {
		model.put(key, value);
	}
	
	public String getOutputFileName() {
		return outputFileName;
	}
	
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
	
}
