package nl.rp.loglib.java;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import nl.rp.loglib.Constant;

public class TestServer {

	public static final int RESTART_INTERVAL = 1000;

	private final int port;
	private final LogBuffer logBuffer;
	private int bufferReadInterval = 1000;
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private DataInputStream dataInputStream = null;
	private DataOutputStream dataOutputStream = null;

	private final List<TestServerListener> listeners = new ArrayList<>();

	private boolean stop = false;


	public TestServer(int port, int capacity) {
		this.port = port;
		logBuffer = new LogBuffer(capacity);
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

	public void addServerListener(TestServerListener listener) {
		listeners.add(listener);
	}

	public void removeServerListener(TestServerListener listener) {
		listeners.remove(listener);
	}

	private void fireStarted() {
		for (TestServerListener listener : listeners) {
			listener.started();
		}
	}

	private void fireClientConnected() {
		for (TestServerListener listener : listeners) {
			listener.clientConnected();
		}
	}

	private void fireClientDisconnected() {
		for (TestServerListener listener : listeners) {
			listener.clientDisconnected();
		}
	}

	private void fireStopped() {
		for (TestServerListener listener : listeners) {
			listener.stopped();
		}
	}

	public void start() {

		stop = false;

		new Thread(new Runnable() {

			@Override
			public void run() {

				fireStarted();

				while (!stop) {

					try {

						serverSocket = new ServerSocket(port);
						socket = serverSocket.accept();

						fireClientConnected();

						dataInputStream = new DataInputStream(socket.getInputStream());
						dataOutputStream = new DataOutputStream(socket.getOutputStream());

						while (socket.isConnected() && !stop) {

							Thread.sleep(bufferReadInterval);

							//Check if the socket is connected
							dataInputStream.available();

							final byte[] bytes = logBuffer.readBytes();
							if (bytes != null) {
								dataOutputStream.write(bytes);
							}

						}

						closeSockets();
						fireClientDisconnected();

					} catch (Exception e) {

						closeSockets();
						fireClientDisconnected();

						e.printStackTrace();

					}

					if (!stop) {
						try {
							Thread.sleep(RESTART_INTERVAL);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}

				fireStopped();

			}
		}).start();

	}

	public void stop() {
		closeSockets();
		stop = true;
	}

	private void closeSockets() {

		if (socket != null) {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void createEvent(Constant evtConstant, Integer group, Integer id, Integer channel, Long tick, Object value) {

		switch (evtConstant) {

		case EVT_NULL:
			logBuffer.evtNull();
			break;

		case EVT_FULL:
			logBuffer.evtFull();
			break;

		case EVT_TICK32:
			logBuffer.evtTick32(tick.intValue());
			break;

		case EVT_CH8_TICK32_BOOL8:
			logBuffer.evtCh8Tick32Bool8(channel.byteValue(), tick.intValue(), (Boolean)value);
			break;

		case EVT_CH8_TICK32_INT16:
			logBuffer.evtCh8Tick32Int16(channel.byteValue(), tick.intValue(), ((Number)value).shortValue());
			break;

		case EVT_CH8_TICK32_INT32:
			logBuffer.evtCh8Tick32Int32(channel.byteValue(), tick.intValue(), ((Number)value).intValue());
			break;

		case EVT_CH8_TICK32_UINT32:
			logBuffer.evtCh8Tick32UInt32(channel.byteValue(), tick.intValue(), ((Number)value).intValue()); //TODO
			break;

		case EVT_CH8_TICK32_REAL32:
			//logBuffer.evtCh8Tick32Real32(channel.byteValue(), tick.intValue(), (Float)value); //TODO
			break;

		case EVT_CH8_TICK32_REAL64:
			//logBuffer.evtCh8Tick32Real64(channel.byteValue(), tick.intValue(), (Double)value); //TODO
			break;

		case EVT_CH8_TICK32_STRING:
			//logBuffer.evtCh8Tick32String(channel.byteValue(), tick.intValue(), (String)value); //TODO
			break;

		case EVT_CH16_TICK32_BOOL8:
			logBuffer.evtCh16Tick32Bool8(channel.shortValue(), tick.intValue(), (Boolean)value);
			break;

		case EVT_CH16_TICK32_INT16:
			logBuffer.evtCh16Tick32Int16(channel.shortValue(), tick.intValue(), ((Number)value).shortValue());
			break;

		case EVT_CH16_TICK32_INT32:
			logBuffer.evtCh16Tick32Int32(channel.shortValue(), tick.intValue(), ((Number)value).intValue());
			break;

		case EVT_CH16_TICK32_UINT32:
			logBuffer.evtCh16Tick32UInt32(channel.shortValue(), tick.intValue(), ((Number)value).intValue()); //TODO
			break;

		case EVT_CH16_TICK32_REAL32:
			//logBuffer.evtCh16Tick32Real32(channel.shortValue(), tick.intValue(), (Float)value); //TODO
			break;

		case EVT_CH16_TICK32_REAL64:
			//logBuffer.evtCh16Tick32Real64(channel.shortValue(), tick.intValue(), (Double)value); //TODO
			break;

		case EVT_CH16_TICK32_STRING:
			//logBuffer.evtCh16Tick32String(channel.shortValue(), tick.intValue(), (String)value); //TODO
			break;

		case EVT_GR8_ID8_CH8_TICK32_BOOL8:
			logBuffer.evtGr8Id8Ch8Tick32Bool8(group.byteValue(), id.byteValue(), channel.byteValue(), tick.intValue(), (Boolean)value);
			break;

		case EVT_GR8_ID8_CH8_TICK32_INT16:
			logBuffer.evtGr8Id8Ch8Tick32Int16(group.byteValue(), id.byteValue(), channel.byteValue(), tick.intValue(), ((Number)value).shortValue());
			break;

		case EVT_GR8_ID8_CH8_TICK32_INT32:
			logBuffer.evtGr8Id8Ch8Tick32Int32(group.byteValue(), id.byteValue(), channel.byteValue(), tick.intValue(), ((Number)value).intValue());
			break;

		case EVT_GR8_ID8_CH8_TICK32_UINT32:
			logBuffer.evtGr8Id8Ch8Tick32UInt32(group.byteValue(), id.byteValue(), channel.byteValue(), tick.intValue(), ((Number)value).intValue());
			break;

		case EVT_GR8_ID8_CH8_TICK32_REAL32:
			//logBuffer.evtGr8Id8Ch8Tick32Real32(group.byteValue(), id.byteValue(), channel.byteValue(), tick.intValue(), (Float)value); //TODO
			break;

		case EVT_GR8_ID8_CH8_TICK32_REAL64:
			//logBuffer.evtGr8Id8Ch8Tick32Real64(group.byteValue(), id.byteValue(), channel.byteValue(), tick.intValue(), (Double)value); //TODO
			break;

		case EVT_GR8_ID8_CH8_TICK32_STRING:
			//logBuffer.evtGr8Id8Ch8Tick32String(group.byteValue(), id.byteValue(), channel.byteValue(), tick.intValue(), (String)value); //TODO
			break;

		case EVT_GR8_ID8_CH16_TICK32_BOOL8:
			logBuffer.evtGr8Id8Ch16Tick32Bool8(group.byteValue(), id.byteValue(), channel.shortValue(), tick.intValue(), (Boolean)value);
			break;

		case EVT_GR8_ID8_CH16_TICK32_INT16:
			logBuffer.evtGr8Id8Ch16Tick32Int16(group.byteValue(), id.byteValue(), channel.shortValue(), tick.intValue(), ((Number)value).shortValue());
			break;

		case EVT_GR8_ID8_CH16_TICK32_INT32:
			logBuffer.evtGr8Id8Ch16Tick32Int32(group.byteValue(), id.byteValue(), channel.shortValue(), tick.intValue(), ((Number)value).intValue());
			break;

		case EVT_GR8_ID8_CH16_TICK32_UINT32:
			logBuffer.evtGr8Id8Ch16Tick32UInt32(group.byteValue(), id.byteValue(), channel.shortValue(), tick.intValue(), ((Number)value).intValue()); //TODO
			break;

		case EVT_GR8_ID8_CH16_TICK32_REAL32:
			//logBuffer.evtGr8Id8Ch16Tick32Real32(group.byteValue(), id.byteValue(), channel.shortValue(), tick.intValue(), (Float)value); //TODO
			break;

		case EVT_GR8_ID8_CH16_TICK32_REAL64:
			//logBuffer.evtGr8Id8Ch16Tick32Real64(group.byteValue(), id.byteValue(), channel.shortValue(), tick.intValue(), (Double)value); //TODO
			break;

		case EVT_GR8_ID8_CH16_TICK32_STRING:
			//logBuffer.evtGr8Id8Ch16Tick32String(group.byteValue(), id.byteValue(), channel.shortValue(), tick.intValue(), (String)value); //TODO
			break;

		default:
			break;

		}

	}

	public interface TestServerListener {

		public void started();
		public void clientConnected();
		public void clientDisconnected();
		public void stopped();

	}

}
