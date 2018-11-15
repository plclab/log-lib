package nl.rp.loglib.java;

public class IdChannelValuePacket extends ChannelValuePacket {

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
