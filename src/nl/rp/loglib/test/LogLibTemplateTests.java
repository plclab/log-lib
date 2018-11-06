package nl.rp.loglib.test;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import nl.rp.loglib.impl.LogLibImpl;
import nl.rp.loglib.impl.bachmann.mplc.BachmannMPlc;
import nl.rp.loglib.impl.beckhoff.tc2.BeckhoffTc2;
import nl.rp.loglib.impl.codesys.v2.CoDeSysV2;

public class LogLibTemplateTests {


	public LogLibTemplateTests() {

		try {

			createOutputDirectories();
			
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

			/*
			logLibImpl = new CoDeSysV3();
			logLibImpl.generate(configuration);

			logLibImpl = new Java();
			logLibImpl.generate(configuration);
			*/

			logLibImpl = new CoDeSysV2();
			logLibImpl.generate(configuration);

			logLibImpl = new BeckhoffTc2();
			logLibImpl.generate(configuration);

			logLibImpl = new BachmannMPlc();
			logLibImpl.generate(configuration);

			printByteOrders();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void createOutputDirectories() {
		
		new File("log-lib").mkdirs();
		
		//CoDeSys V2
		new File("log-lib/codesys/v2/basic").mkdirs();
		new File("log-lib/codesys/v2/comm/tcp").mkdirs();
		new File("log-lib/codesys/v2/core").mkdirs();

		//CoDeSys V3
		new File("log-lib/codesys/v3/basic").mkdirs();
		new File("log-lib/codesys/v3/comm/tcp").mkdirs();
		new File("log-lib/codesys/v3/core").mkdirs();

		//Bachmann M-Plc
		new File("log-lib/bachmann/mplc/basic").mkdirs();
		new File("log-lib/bachmann/mplc/comm/tcp").mkdirs();
		new File("log-lib/bachmann/mplc/core").mkdirs();

		//Beckhoff TwinCAT 2
		new File("log-lib/beckhoff/tc2/basic").mkdirs();
		new File("log-lib/beckhoff/tc2/comm/tcp").mkdirs();
		new File("log-lib/beckhoff/tc2/core").mkdirs();

		//Beckhoff TwinCAT 3
		new File("log-lib/beckhoff/tc3/basic").mkdirs();
		new File("log-lib/beckhoff/tc3/comm/tcp").mkdirs();
		new File("log-lib/beckhoff/tc3/core").mkdirs();

		//Java
		new File("log-lib/java/basic").mkdirs();
		new File("log-lib/java/comm/tcp").mkdirs();
		new File("log-lib/java/core").mkdirs();

	}
	
	private void printByteOrders() {
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(2);
		byteBuffer.order(ByteOrder.BIG_ENDIAN);
		byteBuffer.putShort((short)1);
		byte[] bytes = byteBuffer.array();
		System.out.println("Big endian: [" + bytes[0] + ", " + bytes[1] + "]");

		byteBuffer = ByteBuffer.allocate(2);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putShort((short)1);
		bytes = byteBuffer.array();
		System.out.println("Little endian: [" + bytes[0] + ", " + bytes[1] + "]");

	}

	public static void main(String[] args) {
		new LogLibTemplateTests();
	}

}
