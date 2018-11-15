package nl.rp.loglib.java;

public class GroupIdChannelTickValuePacket extends IdChannelTickValuePacket {

	private Integer group;


	@Override
	public Integer getGroup() {
		return group;
	}

	@Override
	public void setGroup(Integer group) {
		this.group = group;
	}

}
