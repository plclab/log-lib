package nl.rp.loglib.java;

public class TickPacket extends Packet {

	private Long tick;


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
		return null;
	}

	@Override
	public Long getTick() {
		return tick;
	}

	@Override
	public void setTick(Long tick) {
		this.tick = tick;
	}

	@Override
	public Object getValue() {
		return null;
	}

}
