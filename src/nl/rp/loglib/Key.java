package nl.rp.loglib;

public enum Key {

	EVT("Evt", "Event", DataType.NONE),
	GR8("Gr8", "Group", DataType.UINT8),
	GR16("Gr16", "Group", DataType.UINT16),
	ID8("Id8", "Id", DataType.UINT8),
	ID16("Id16", "Id", DataType.UINT16),
	CH8("Ch8", "Channel", DataType.UINT8),
	CH16("Ch16", "Channel", DataType.UINT16),
	TICK32("Tick32", "Tick", DataType.UINT32),
	TICK64("Tick64", "Tick", DataType.UINT64),
	BOOL8("Bool8", "Value", DataType.BOOL8),
	INT8("Int8", "Value", DataType.INT8),
	UINT8("UInt8", "Value", DataType.UINT8),
	INT16("Int16", "Value", DataType.INT16),
	UINT16("UInt16", "Value", DataType.UINT16),
	INT32("Int32", "Value", DataType.INT32),
	UINT32("UInt32", "Value", DataType.UINT32),
	REAL32("Real32", "Value", DataType.REAL32),
	INT64("Int64", "Value", DataType.INT64),
	UINT64("UInt64", "Value", DataType.UINT64),
	REAL64("Real64", "Value", DataType.REAL64),
	
	//TODO: These keys need to be handled differently..
	STRING("String", "Value", DataType.UINT32),
	BYTES("Bytes", "Value", DataType.UINT32);
	
	
	public final String shortName;
	public final String fullName;
	public final DataType dataType;
	
	
	Key(String shortName, String fullName, DataType dataType) {
		this.shortName = shortName;
		this.fullName = fullName;
		this.dataType = dataType;
	}
	
}
