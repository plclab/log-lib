package nl.rp.loglib.test;

import java.io.File;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import nl.rp.loglib.impl.LogLibImpl;
import nl.rp.loglib.impl.codesys.v2.CoDeSysV2;
import nl.rp.loglib.impl.codesys.v3.CoDeSysV3;

public class LogLibTemplateTests {


	public LogLibTemplateTests() {

		try {

			// Create your Configuration instance, and specify if up to what FreeMarker
			// version (here 2.3.28) do you want to apply the fixes that are not 100%
			// backward-compatible. See the Configuration JavaDoc for details.
			final Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

			// Specify the source where the template files come from. Here I set a
			// plain directory for it, but non-file-system sources are possible too:
			configuration.setDirectoryForTemplateLoading(new File("templates"));

			// Set the preferred charset template files are stored in. UTF-8 is
			// a good choice in most applications:
			configuration.setDefaultEncoding("UTF-8");

			// Sets how errors will appear.
			// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

			// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
			configuration.setLogTemplateExceptions(false);

			// Wrap unchecked exceptions thrown during template processing into TemplateException-s.
			configuration.setWrapUncheckedExceptions(true);

			LogLibImpl logLibImpl;

			logLibImpl = new CoDeSysV2();
			logLibImpl.generateLogLibCore(configuration);

			logLibImpl = new CoDeSysV3();
			logLibImpl.generateLogLibCore(configuration);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new LogLibTemplateTests();
	}

}
