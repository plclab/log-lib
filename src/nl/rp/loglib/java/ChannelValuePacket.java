package nl.rp.loglib.java;

public class ChannelValuePacket extends Packet {

	private Integer channel;
	private Object value;


	@Override
	public Integer getGroup() {
		return null;
	}

	@Override
	public Integer getId() {
		return null;
	}

	@Override
	public Integer getChannel() {
		return channel;
	}

	@Override
	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	@Override
	public Long getTick() {
		return null;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

}
