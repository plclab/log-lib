package nl.rp.loglib.java;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

import nl.rp.loglib.Constant;

public class PacketFactory {

	public Packet createPacket(ByteBuffer byteBuffer, int length) {

		if (byteBuffer != null && byteBuffer.capacity() >= 4 && byteBuffer.capacity() >= length) {

			byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
			byteBuffer.position(3);

			Constant eventType = Constant.getEventConstantForType(byteBuffer.get(2));

			Packet packet = null;

			switch (eventType) {

			case EVT_NULL:
				packet = new NullPacket();
				break;

			case EVT_FULL:
				packet = new BufferFullPacket();
				break;

			case EVT_TICK32:
				packet = new TickPacket();
				packet.setTick((long)byteBuffer.getInt());
				break;

			case EVT_CH8_TICK32_BOOL8:
				packet = new ChannelTickValuePacket();
				packet.setChannel((int)(byteBuffer.get() & 0xFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.get() != 0? Boolean.TRUE:Boolean.FALSE);
				break;

			case EVT_CH8_TICK32_INT16:
				packet = new ChannelTickValuePacket();
				packet.setChannel((int)(byteBuffer.get() & 0xFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getShort());
				break;

			case EVT_CH8_TICK32_INT32:
				packet = new ChannelTickValuePacket();
				packet.setChannel((int)(byteBuffer.get() & 0xFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getInt());
				break;

			case EVT_CH8_TICK32_UINT32:
				packet = new ChannelTickValuePacket();
				packet.setChannel((int)(byteBuffer.get() & 0xFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getInt() & 0x00000000FFFFFFFFL);
				break;

			case EVT_CH8_TICK32_REAL32:
				packet = new ChannelTickValuePacket();
				packet.setChannel((int)(byteBuffer.get() & 0xFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getFloat());
				break;	

			case EVT_CH8_TICK32_REAL64:
				packet = new ChannelTickValuePacket();
				packet.setChannel((int)(byteBuffer.get() & 0xFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getDouble());
				break;					

			case EVT_CH8_TICK32_STRING:
				//TODO
				break;

			case EVT_CH16_TICK32_BOOL8:
				packet = new ChannelTickValuePacket();
				packet.setChannel((int)(byteBuffer.getShort() & 0xFFFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.get() != 0? Boolean.TRUE:Boolean.FALSE);
				break;

			case EVT_CH16_TICK32_INT16:
				packet = new ChannelTickValuePacket();
				packet.setChannel((int)(byteBuffer.getShort() & 0xFFFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getShort());
				break;

			case EVT_CH16_TICK32_INT32:
				packet = new ChannelTickValuePacket();
				packet.setChannel((int)(byteBuffer.getShort() & 0xFFFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getInt());
				break;

			case EVT_CH16_TICK32_UINT32:
				packet = new ChannelTickValuePacket();
				packet.setChannel((int)(byteBuffer.getShort() & 0xFFFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getInt() & 0x00000000FFFFFFFFL);
				break;

			case EVT_CH16_TICK32_REAL32:
				packet = new ChannelTickValuePacket();
				packet.setChannel((int)(byteBuffer.getShort() & 0xFFFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getFloat());
				break;	

			case EVT_CH16_TICK32_REAL64:
				packet = new ChannelTickValuePacket();
				packet.setChannel((int)(byteBuffer.getShort() & 0xFFFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getDouble());
				break;					

			case EVT_CH16_TICK32_STRING:
				//TODO
				break;

			case EVT_GR8_ID8_CH8_TICK32_BOOL8:
				packet = new GroupIdChannelTickValuePacket();
				packet.setGroup((int)(byteBuffer.get() & 0xFF));
				packet.setId((int)(byteBuffer.get() & 0xFF));
				packet.setChannel((int)(byteBuffer.get() & 0xFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.get() != 0? Boolean.TRUE:Boolean.FALSE);
				break;

			case EVT_GR8_ID8_CH8_TICK32_INT16:
				packet = new GroupIdChannelTickValuePacket();
				packet.setGroup((int)(byteBuffer.get() & 0xFF));
				packet.setId((int)(byteBuffer.get() & 0xFF));
				packet.setChannel((int)(byteBuffer.get() & 0xFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getShort());
				break;

			case EVT_GR8_ID8_CH8_TICK32_INT32:
				packet = new GroupIdChannelTickValuePacket();
				packet.setGroup((int)(byteBuffer.get() & 0xFF));
				packet.setId((int)(byteBuffer.get() & 0xFF));
				packet.setChannel((int)(byteBuffer.get() & 0xFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getInt());
				break;

			case EVT_GR8_ID8_CH8_TICK32_UINT32:
				packet = new GroupIdChannelTickValuePacket();
				packet.setGroup((int)(byteBuffer.get() & 0xFF));
				packet.setId((int)(byteBuffer.get() & 0xFF));
				packet.setChannel((int)(byteBuffer.get() & 0xFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getInt() & 0x00000000FFFFFFFFL);
				break;

			case EVT_GR8_ID8_CH8_TICK32_REAL32:
				packet = new GroupIdChannelTickValuePacket();
				packet.setGroup((int)(byteBuffer.get() & 0xFF));
				packet.setId((int)(byteBuffer.get() & 0xFF));
				packet.setChannel((int)(byteBuffer.get() & 0xFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getFloat());
				break;	

			case EVT_GR8_ID8_CH8_TICK32_REAL64:
				packet = new GroupIdChannelTickValuePacket();
				packet.setGroup((int)(byteBuffer.get() & 0xFF));
				packet.setId((int)(byteBuffer.get() & 0xFF));
				packet.setChannel((int)(byteBuffer.get() & 0xFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getDouble());
				break;					

			case EVT_GR8_ID8_CH8_TICK32_STRING:
				//TODO
				break;

			case EVT_GR8_ID8_CH16_TICK32_BOOL8:
				packet = new GroupIdChannelTickValuePacket();
				packet.setGroup((int)(byteBuffer.get() & 0xFF));
				packet.setId((int)(byteBuffer.get() & 0xFF));
				packet.setChannel((int)(byteBuffer.getShort() & 0xFFFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.get() != 0? Boolean.TRUE:Boolean.FALSE);
				break;

			case EVT_GR8_ID8_CH16_TICK32_INT16:
				packet = new GroupIdChannelTickValuePacket();
				packet.setGroup((int)(byteBuffer.get() & 0xFF));
				packet.setId((int)(byteBuffer.get() & 0xFF));
				packet.setChannel((int)(byteBuffer.getShort() & 0xFFFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getShort());
				break;

			case EVT_GR8_ID8_CH16_TICK32_INT32:
				packet = new GroupIdChannelTickValuePacket();
				packet.setGroup((int)(byteBuffer.get() & 0xFF));
				packet.setId((int)(byteBuffer.get() & 0xFF));
				packet.setChannel((int)(byteBuffer.getShort() & 0xFFFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getInt());
				break;

			case EVT_GR8_ID8_CH16_TICK32_UINT32:
				packet = new GroupIdChannelTickValuePacket();
				packet.setGroup((int)(byteBuffer.get() & 0xFF));
				packet.setId((int)(byteBuffer.get() & 0xFF));
				packet.setChannel((int)(byteBuffer.getShort() & 0xFFFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getInt() & 0x00000000FFFFFFFFL);
				break;

			case EVT_GR8_ID8_CH16_TICK32_REAL32:
				packet = new GroupIdChannelTickValuePacket();
				packet.setGroup((int)(byteBuffer.get() & 0xFF));
				packet.setId((int)(byteBuffer.get() & 0xFF));
				packet.setChannel((int)(byteBuffer.getShort() & 0xFFFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getFloat());
				break;	

			case EVT_GR8_ID8_CH16_TICK32_REAL64:
				packet = new GroupIdChannelTickValuePacket();
				packet.setGroup((int)(byteBuffer.get() & 0xFF));
				packet.setId((int)(byteBuffer.get() & 0xFF));
				packet.setChannel((int)(byteBuffer.getShort() & 0xFFFF));
				packet.setTick((long)byteBuffer.getInt());
				packet.setValue(byteBuffer.getDouble());
				break;					

			case EVT_GR8_ID8_CH16_TICK32_STRING:
				//TODO
				break;

			default:
				break;
			}

			if (packet != null) {
				packet.setTime(new Date().getTime());
				packet.setType(byteBuffer.get(2));
			}

			return packet;

		}

		return null;

	}

}
