package nl.rp.loglib.java;

public class Packet {

	private long time;
	private Integer group = null;
	private Integer id = null;
	private Integer channel = null;
	private Long tick = null;
	private Object value = null;


	public Packet() {

	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Long getTick() {
		return tick;
	}

	public void setTick(Long tick) {
		this.tick = tick;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
