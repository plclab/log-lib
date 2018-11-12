package nl.rp.loglib.java;

import java.io.DataInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestClient {

	public static final int CONNECT_TIMEOUT = 1000;

	private enum ScannerState {
		WAIT_FOR_START_FLAG,
		WAIT_FOR_MAGIC_BYTE,
		WAIT_FOR_TYPE_1,
		WAIT_FOR_TYPE_2,
		WAIT_FOR_TYPE_3,
		WAIT_FOR_LENGTH,
		WAIT_FOR_END_FLAG
	}

	private final int port;
	private String ip = "127.0.0.1";
	private int socketReadInterval = 1000;
	private Socket socket;
	private DataInputStream dataInputStream;

	private ScannerState scannerState = ScannerState.WAIT_FOR_START_FLAG;
	private ByteBuffer byteBuffer = ByteBuffer.allocate(300); //TODO: Define max packet size
	private int bytesSinceStartFlag = 0;
	private int expectedLengthIndex = 0;
	private int expectedLength = 0;

	private final List<TestClientListener> listeners = new ArrayList<>();
	
	private boolean exit = false;


	public TestClient(int port) {

		this.port = port;

		createBackgroundThreads();

	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getSocketReadInterval() {
		return socketReadInterval;
	}

	public void setSocketReadInterval(int socketReadInterval) {
		this.socketReadInterval = socketReadInterval;
	}
	
	public void addClientListener(TestClientListener listener) {
		listeners.add(listener);
	}
	
	public void removeClientListener(TestClientListener listener) {
		listeners.remove(listener);
	}
	
	private void firePacketReceived(Packet packet) {
		for (TestClientListener listener : listeners) {
			listener.packetReceived(packet);
		}
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

						socket = new Socket();
						socket.setKeepAlive(true);
						socket.connect(new InetSocketAddress(ip, port), CONNECT_TIMEOUT);

						dataInputStream = new DataInputStream(socket.getInputStream());

						while (socket.isConnected() && !exit) {

							try {
								Thread.sleep(socketReadInterval);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							if (dataInputStream.available() > 0) {

								final byte[] bytes = new byte[dataInputStream.available()];
								dataInputStream.read(bytes);

								scan(bytes);

							}

						}

						try {
							socket.close();
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

	private void scan(byte[] bytes) {

		for (byte b : bytes) {

			bytesSinceStartFlag++;

			switch (scannerState) {

			case WAIT_FOR_START_FLAG:
				checkForStartFlag(b);
				break;

			case WAIT_FOR_MAGIC_BYTE:

				if (bytesSinceStartFlag == 1) {

					if (b == LogBuffer.MAGIC_BYTE_V1_LITTLE_ENDIAN
							|| b == LogBuffer.MAGIC_BYTE_V1_BIG_ENDIAN) {
						byteBuffer.put(b);
						scannerState = ScannerState.WAIT_FOR_TYPE_1;
					} else {
						scannerState = ScannerState.WAIT_FOR_START_FLAG;
						checkForStartFlag(b);
					}

				}

				break;

			case WAIT_FOR_TYPE_1:

				byteBuffer.put(b);

				if (bytesSinceStartFlag == 2) {

					if (b == 255) {
						scannerState = ScannerState.WAIT_FOR_TYPE_2;
					} else {

						expectedLength = getLengthForType1(b);
						
						if (expectedLength < 0) {
							expectedLengthIndex = bytesSinceStartFlag + 1;
							scannerState = ScannerState.WAIT_FOR_LENGTH;
						} else {
							scannerState = ScannerState.WAIT_FOR_END_FLAG;
						}

					}

				}

				break;

			case WAIT_FOR_TYPE_2:

				byteBuffer.put(b);

				if (bytesSinceStartFlag == 3) {

					if (b == 255) {
						scannerState = ScannerState.WAIT_FOR_TYPE_3;
					} else {

						expectedLength = 0; //TODO
						
						if (expectedLength < 0) {
							expectedLengthIndex = bytesSinceStartFlag + 1;
							scannerState = ScannerState.WAIT_FOR_LENGTH;
						} else {
							scannerState = ScannerState.WAIT_FOR_END_FLAG;
						}

					}

				}

				break;

			case WAIT_FOR_TYPE_3:

				byteBuffer.put(b);

				if (bytesSinceStartFlag == 4) {

					expectedLength = 0; //TODO
					
					if (expectedLength < 0) {
						expectedLengthIndex = bytesSinceStartFlag + 1;
						scannerState = ScannerState.WAIT_FOR_LENGTH;
					} else {
						scannerState = ScannerState.WAIT_FOR_END_FLAG;
					}

				}

				break;

			case WAIT_FOR_LENGTH:

				byteBuffer.put(b);

				if (bytesSinceStartFlag == expectedLengthIndex) {
					expectedLength = b + 5; //TODO
					scannerState = ScannerState.WAIT_FOR_END_FLAG;
				}

				break;

			case WAIT_FOR_END_FLAG:

				byteBuffer.put(b);

				if (bytesSinceStartFlag == expectedLength - 1) {

					if (b == LogBuffer.END_FLAG) {
						readPacketFromByteBuffer();
						scannerState = ScannerState.WAIT_FOR_START_FLAG;
					} else {
						scannerState = ScannerState.WAIT_FOR_START_FLAG;
						checkForStartFlag(b);
					}

				}

				break;

			default:
				break;

			}

		}

	}

	private void checkForStartFlag(byte b) {

		if (b == LogBuffer.START_FLAG) {

			byteBuffer.position(0);
			byteBuffer.put(b);
			bytesSinceStartFlag = 0;
			expectedLength = 0;
			scannerState = ScannerState.WAIT_FOR_MAGIC_BYTE;

		}

	}

	//TODO: Should we return packet length or payload length?
	private int getLengthForType1(byte type) {

		if (type < LogBuffer.EVT_CH8_BOOL8) {
			return 4;
		} else if (type < LogBuffer.EVT_CH8_INT16) {
			return 6;
		} else if (type < LogBuffer.EVT_TICK32) {
			return 7;
		} else if (type < LogBuffer.EVT_CH8_INT32) {
			return 8;
		} else if (type < LogBuffer.EVT_ID8_CH8_INT32) {
			return 9;
		} else if (type < LogBuffer.EVT_GR8_ID8_CH8_INT32) {
			return 10;
		} else if (type < LogBuffer.EVT_TICK64) {
			return 11;
		} else if (type < LogBuffer.EVT_CH8_REAL64) {
			return 12;
		} else if (type < LogBuffer.EVT_ID8_CH8_REAL64) {
			return 13;
		} else if (type < LogBuffer.EVT_GR8_ID8_CH8_REAL64) {
			return 14;
		} else if (type < LogBuffer.EVT_GR8_ID8_CH16_REAL64) {
			return 15;
		} else if (type < LogBuffer.EVT_GR8_ID16_CH16_REAL64) {
			return 16;
		} else if (type < LogBuffer.EVT_GR16_ID16_CH16_TICK32_INT32) {
			return 17;
		} else if (type < LogBuffer.EVT_GR8_ID8_CH8_TICK32_REAL64) {
			return 18;
		} else if (type < LogBuffer.EVT_GR8_ID8_CH16_TICK32_REAL64) {
			return 19;
		} else if (type < LogBuffer.EVT_GR8_ID16_CH16_TICK32_REAL64) {
			return 20;
		} else if (type < LogBuffer.EVT_ID8_CH8_TICK64_REAL64) {
			return 21;
		} else if (type < LogBuffer.EVT_GR8_ID8_CH8_TICK64_REAL64) {
			return 22;
		} else if (type < LogBuffer.EVT_ID16_CH16_TICK64_REAL64) {
			return 23;
		} else if (type < LogBuffer.EVT_GR8_ID16_CH16_TICK64_REAL64) {
			return 24;
		} else if (type < LogBuffer.EVT_GR16_ID16_CH16_TICK64_INT64) {
			return 25;
		} else if (type < LogBuffer.EVT_CH8_STRING) {
			return 26;
		} else {
			return -1;
		}

	}

	private void readPacketFromByteBuffer() {

		System.out.println("Packet received, length = " + expectedLength);

		final Packet packet = new Packet();
		
		packet.setTime(new Date().getTime());
		
		//TODO: Read packet from bytes
		
		firePacketReceived(packet);
		
	}
	
	public interface TestClientListener {
		
		public void packetReceived(Packet packet);
		
	}

}
