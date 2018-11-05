package nl.rp.loglib;

public enum DataType {

	NONE(0),
	BOOL8(1),
	INT8(1),
	UINT8(1),
	INT16(2),
	UINT16(2),
	INT32(4),
	UINT32(4),
	REAL32(4),
	INT64(8),
	UINT64(8),
	REAL64(8),
	STRING(0);
	
	final int length;

	DataType(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}

}
