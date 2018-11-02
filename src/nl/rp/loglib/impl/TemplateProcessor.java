package nl.rp.loglib.impl;

import nl.rp.loglib.DataType;
import nl.rp.loglib.Key;

public abstract class TemplateProcessor {
	
	
	public TemplateProcessor() {
		
	}
	
	public abstract String getTemplateDirectory();

	public abstract String getGlobalVariableListTemplate();

	public abstract String getStructTemplate();

	public abstract String getFuctionTemplate();

	public abstract String getDataType(DataType dataType);
	
	public abstract TemplateData getGlobalConstantsTemplateData();

	public abstract TemplateData getLogBufferHandleStructTemplateData();

	public abstract TemplateData getEvtFunctionTemplateData(Key[] keys);
	
	public abstract String getOutputExtension();
	
	public static Key[] stringToKeys(String keysString) {
		
		final String[] keyStrings = keysString.split("_");
		final Key[] keys = new Key[keyStrings.length];
		
		for (int i = 0; i < keyStrings.length; i++) {
			for (Key key : Key.values()) {
				if (key.name().equals(keyStrings[i])) {
					keys[i] = key;
					break;
				}
			}
		}
		
		return keys;
		
	}
	
	public static String keysToString(Key[] keys) {
		
		String keyString = "";
		
		for (int i = 0; i < keys.length; i++) {
			keyString += keys[i].name() + (i < keys.length - 1? "_":"");
		}
		
		return keyString;
		
	}
	
}
