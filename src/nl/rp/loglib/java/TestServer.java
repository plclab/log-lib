package nl.rp.loglib.java;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import nl.rp.loglib.Constant;

public class TestServer {

	private final int port;
	private final LogBuffer logBuffer;
	private int bufferReadInterval = 1000;
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private DataOutputStream dataOutputStream = null;

	private boolean exit = false;


	public TestServer(int port, int capacity) {

		this.port = port;
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
		
		//The server socket might still be waiting for a connection (accept() is blocking) 
		try {
			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		exit = true;
		
	}

	private void createBackgroundThreads() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				while (!exit) {

					try {

						serverSocket = new ServerSocket(port);
						socket = serverSocket.accept();
						dataOutputStream = new DataOutputStream(socket.getOutputStream());

						while (socket.isConnected() && !exit) {

							try {
								Thread.sleep(bufferReadInterval);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							final byte[] bytes = logBuffer.readBytes();
							if (bytes != null) {
								dataOutputStream.write(bytes);
							}

						}
						
						try {
							socket.close();
							serverSocket.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					} catch (Exception e) {
						e.printStackTrace();
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
