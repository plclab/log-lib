package nl.rp.loglib.java;

public class NullPacket extends Packet {


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
		return null;
	}

	@Override
	public Object getValue() {
		return null;
	}

}
