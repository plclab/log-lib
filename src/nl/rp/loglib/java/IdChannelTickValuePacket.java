package nl.rp.loglib.java;

public class IdChannelTickValuePacket extends ChannelTickValuePacket {

	private Integer id;


	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

}
