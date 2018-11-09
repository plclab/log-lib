package nl.rp.loglib.java;

import nl.rp.loglib.Constant;

public class LogLibJavaTester {

	private final LogBuffer logBuffer;
	private int bufferReadInterval = 1000;

	private boolean exit = false;


	public LogLibJavaTester(int capacity) {

		logBuffer = new LogBuffer(capacity);

		createBackgroundThreads();

	}

	public LogBuffer getLogBuffer() {
		return logBuffer;
	}

	public int getBufferReadInterval() {
		return bufferReadInterval;
	}

	public void setBufferReadInterval(int bufferReadInterval) {
		this.bufferReadInterval = bufferReadInterval;
	}

	public void exit() {
		exit = true;
	}

	private void createBackgroundThreads() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				while (!exit) {

					try {
						Thread.sleep(bufferReadInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					final byte[] bytes = logBuffer.readBytes();
					if (bytes != null) {

						//System.out.println("Number of bytes read: " + bytes.length);

					}

				}

			}
		}).start();

	}

	public void createEvent(Constant evtConstant) {

		switch (evtConstant) {

		case EVT_NULL:
			logBuffer.evtNull();
			break;

		default:
			break;

		}

	}

}
