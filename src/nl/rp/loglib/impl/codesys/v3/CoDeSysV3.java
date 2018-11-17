package nl.rp.loglib.impl.codesys.v3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import nl.rp.loglib.Constant;
import nl.rp.loglib.impl.LogLibImpl;
import nl.rp.loglib.impl.TemplateData;
import nl.rp.loglib.impl.codesys.StructuredTextTemplateFactory;

public class CoDeSysV3 extends LogLibImpl {

	public static final String TEMPLATE_DIR = "codesys/v3/plcopenxml";

	private final StructuredTextTemplateFactory templateFactory;


	public CoDeSysV3() {
		templateFactory = new StructuredTextTemplateFactory();
	}

	@Override
	protected String getOutputDirectory() {
		return "codesys/v3";
	}

	@Override
	public void generate(Configuration configuration) {

		//Use a fixed UUID pool for the project structure (otherwise each build results in diff's)
		final PlcOpenXmlUuidPool uuidPool = new PlcOpenXmlUuidPool();
		uuidPool.reset();
		
		generateCoreLib(configuration, uuidPool);
		generateBasicLib(configuration, uuidPool);

	}
	
	private void generateCoreLib(Configuration configuration, PlcOpenXmlUuidPool uuidPool) {
		
		try {

			final Template template = configuration.getTemplate(TEMPLATE_DIR + "/Project.ftlx");

			final TemplateData templateData = new TemplateData();
			templateData.setOutputFileName(OUTPUT_BASE_DIR + "/" + getOutputDirectory() +
					"/" + LIB_CORE_DIR + "/" + "LogLibCore.xml");

			final Map<String, Object> projectNode = new HashMap<>();
			templateData.addNode("project", projectNode);

			final List<Map<String, Object>> globalVarLists = new ArrayList<>();
			projectNode.put("globalVarLists", globalVarLists);

			final List<Map<String, Object>> dataTypes = new ArrayList<>();
			projectNode.put("dataTypes", dataTypes);

			final List<Map<String, Object>> pous = new ArrayList<>();
			projectNode.put("pous", pous);

			final PlcOpenXmlProjectFolder projectStructureRoot = new PlcOpenXmlProjectFolder(null);
			projectNode.put("projectStructure", projectStructureRoot);

			//Create global constants
			globalVarLists.add(templateFactory.getCoreConstantsTemplateData().getModel());

			//Create LogBufferHandle struct
			dataTypes.add(templateFactory.getLogBufferHandleStructTemplateData().getModel());

			//Create CreateBufferHandle function
			pous.add(templateFactory.getCreateBufferHandleFunctionTemplateData().getModel());

			//Create GetNextWritePointer function
			pous.add(templateFactory.getGetNextWritePointerFunctionTemplateData().getModel());

			//Create Evt functions
			TemplateData evtFunctionTemplateData;
			for (Constant constant : Constant.CORE_EVENTS_DEFAULT) {
				evtFunctionTemplateData = templateFactory.getEvtFunctionTemplateData(constant);
				if (evtFunctionTemplateData != null) {
					pous.add(evtFunctionTemplateData.getModel());
				}
			}

			//Create the project structure
			createProjectStructure(projectStructureRoot, uuidPool, globalVarLists);
			createProjectStructure(projectStructureRoot, uuidPool, dataTypes);
			createProjectStructure(projectStructureRoot, uuidPool, pous);

			//Process the template and write to output directory
			final File logLibCoreOutputFile = new File(templateData.getOutputFileName());
			final Writer writer = new OutputStreamWriter(new FileOutputStream(logLibCoreOutputFile));
			template.process(templateData.getModel(), writer);
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void generateBasicLib(Configuration configuration, PlcOpenXmlUuidPool uuidPool) {
		
		try {

			final Template template = configuration.getTemplate(TEMPLATE_DIR + "/Project.ftlx");

			final TemplateData templateData = new TemplateData();
			templateData.setOutputFileName(OUTPUT_BASE_DIR + "/" + getOutputDirectory() +
					"/" + LIB_BASIC_DIR + "/" + "LogLibBasic.xml");

			final Map<String, Object> projectNode = new HashMap<>();
			templateData.addNode("project", projectNode);

			final List<Map<String, Object>> globalVarLists = new ArrayList<>();
			projectNode.put("globalVarLists", globalVarLists);

			final List<Map<String, Object>> pous = new ArrayList<>();
			projectNode.put("pous", pous);

			final PlcOpenXmlProjectFolder projectStructureRoot = new PlcOpenXmlProjectFolder(null);
			projectNode.put("projectStructure", projectStructureRoot);

			//Create global variables
			globalVarLists.add(templateFactory.getBasicGlobalsTemplateData().getModel());

			//Create CreateBufferHandle function
			pous.add(templateFactory.getCreateGlobalBufferHandleFunctionTemplateData().getModel());

			//Create log-lib-basic functions
			pous.add(templateFactory.getLogBoolFunctionTemplateData().getModel());
			pous.add(templateFactory.getLogIntFunctionTemplateData().getModel());
			pous.add(templateFactory.getLogDIntFunctionTemplateData().getModel());
			pous.add(templateFactory.getLogUDIntFunctionTemplateData().getModel());
			pous.add(templateFactory.getLogRealFunctionTemplateData().getModel());
			pous.add(templateFactory.getLogLRealFunctionTemplateData().getModel());
			pous.add(templateFactory.getMonitorBoolFunctionTemplateData().getModel());
			pous.add(templateFactory.getMonitorIntFunctionTemplateData().getModel());
			pous.add(templateFactory.getMonitorDIntFunctionTemplateData().getModel());
			pous.add(templateFactory.getMonitorUDIntFunctionTemplateData().getModel());
			pous.add(templateFactory.getMonitorRealFunctionTemplateData().getModel());
			pous.add(templateFactory.getMonitorLRealFunctionTemplateData().getModel());

			//Create the project structure
			createProjectStructure(projectStructureRoot, uuidPool, globalVarLists);
			createProjectStructure(projectStructureRoot, uuidPool, pous);

			//Process the template and write to output directory
			final File logLibBasicOutputFile = new File(templateData.getOutputFileName());
			final Writer writer = new OutputStreamWriter(new FileOutputStream(logLibBasicOutputFile));
			template.process(templateData.getModel(), writer);
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void createProjectStructure(PlcOpenXmlProjectFolder projectStructureRoot, PlcOpenXmlUuidPool uuidPool, List<Map<String, Object>> projectObjects) {

		Object nameObject, pathObject;
		String name, objectId;
		String[] path;
		for (Map<String, Object> projectObject : projectObjects) {

			nameObject = projectObject.get("name");

			if (nameObject != null && nameObject instanceof String) {

				name = (String)nameObject;
				PlcOpenXmlProjectFolder parentFolder = projectStructureRoot;

				pathObject = projectObject.get("path");
				if (pathObject != null && pathObject instanceof String[]) {

					path = (String[])pathObject;
					String folderName = null;
					for (int i = 0; i < path.length; i++) {

						folderName = path[i];
						parentFolder = findOrCreateFolder(parentFolder, folderName);

					}

				}

				objectId = uuidPool.next();
				if (objectId != null) { //TODO: Handle exception?
					projectObject.put("objectId", objectId);
					parentFolder.objects.add(new PlcOpenXmlProjectObject(name, objectId));
				}

			}

		}

	}

	private PlcOpenXmlProjectFolder findOrCreateFolder(PlcOpenXmlProjectFolder parentFolder, String name) {

		for (PlcOpenXmlProjectFolder folder : parentFolder.folders) {
			if (name.equals(folder.name)) {
				return folder;
			}
		}

		final PlcOpenXmlProjectFolder folder = new PlcOpenXmlProjectFolder(name);
		parentFolder.folders.add(folder);
		return folder;

	}


	public class PlcOpenXmlProjectFolder {

		private final String name;
		private final List<PlcOpenXmlProjectObject> objects = new ArrayList<>();
		private final List<PlcOpenXmlProjectFolder> folders = new ArrayList<>();


		public PlcOpenXmlProjectFolder(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public List<PlcOpenXmlProjectObject> getObjects() {
			return objects;
		}

		public List<PlcOpenXmlProjectFolder> getFolders() {
			return folders;
		}

	}

	public class PlcOpenXmlProjectObject {

		private final String name;
		private final String objectId;

		public PlcOpenXmlProjectObject(String name, String objectId) {
			this.name = name;
			this.objectId = objectId;
		}

		public String getName() {
			return name;
		}

		public String getObjectId() {
			return objectId;
		}

	}

}
