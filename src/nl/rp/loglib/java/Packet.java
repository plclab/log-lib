package nl.rp.loglib.java;

public abstract class Packet {

	private long time;
	private byte type;


	public Packet() {

	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public abstract Integer getGroup();

	public void setGroup(Integer group) {

	}

	public abstract Integer getId();

	public void setId(Integer id) {

	}

	public abstract Integer getChannel();

	public void setChannel(Integer channel) {

	}

	public abstract Long getTick();

	public void setTick(Long tick) {

	}

	public abstract Object getValue();

	public void setValue(Object value) {

	}

}
