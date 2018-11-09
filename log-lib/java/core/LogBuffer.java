package nl.rp.loglib.java;

import java.util.Arrays;

public class LogBuffer {

    public static final byte START_FLAG = 121;
    public static final byte MAGIC_BYTE_V1_LITTLE_ENDIAN = 122;
    public static final byte MAGIC_BYTE_V1_BIG_ENDIAN = 124;
    public static final byte END_FLAG = 123;
    public static final byte EVT_NULL = 0;
    public static final byte EVT_CH8_BOOL8 = 8;
    public static final byte EVT_CH8_INT8 = 9;
    public static final byte EVT_CH8_UINT8 = 10;
    public static final byte EVT_CH8_INT16 = 11;
    public static final byte EVT_CH8_UINT16 = 12;
    public static final byte EVT_ID8_CH8_BOOL8 = 13;
    public static final byte EVT_ID8_CH8_INT8 = 14;
    public static final byte EVT_ID8_CH8_UINT8 = 15;
    public static final byte EVT_CH16_BOOL8 = 16;
    public static final byte EVT_CH16_INT8 = 17;
    public static final byte EVT_CH16_UINT8 = 18;
    public static final byte EVT_TICK32 = 19;
    public static final byte EVT_ID8_CH8_INT16 = 20;
    public static final byte EVT_ID8_CH8_UINT16 = 21;
    public static final byte EVT_GR8_ID8_CH8_BOOL8 = 22;
    public static final byte EVT_GR8_ID8_CH8_INT8 = 23;
    public static final byte EVT_GR8_ID8_CH8_UINT8 = 24;
    public static final byte EVT_ID8_CH16_BOOL8 = 25;
    public static final byte EVT_ID8_CH16_INT8 = 26;
    public static final byte EVT_ID8_CH16_UINT8 = 27;
    public static final byte EVT_CH16_INT16 = 28;
    public static final byte EVT_CH16_UINT16 = 29;
    public static final byte EVT_CH8_INT32 = 30;
    public static final byte EVT_CH8_UINT32 = 31;
    public static final byte EVT_CH8_REAL32 = 32;
    public static final byte EVT_GR8_ID8_CH8_INT16 = 33;
    public static final byte EVT_GR8_ID8_CH8_UINT16 = 34;
    public static final byte EVT_ID8_CH16_INT16 = 35;
    public static final byte EVT_ID8_CH16_UINT16 = 36;
    public static final byte EVT_GR8_ID8_CH16_BOOL8 = 37;
    public static final byte EVT_GR8_ID8_CH16_INT8 = 38;
    public static final byte EVT_GR8_ID8_CH16_UINT8 = 39;
    public static final byte EVT_ID8_CH8_INT32 = 40;
    public static final byte EVT_ID8_CH8_UINT32 = 41;
    public static final byte EVT_ID8_CH8_REAL32 = 42;
    public static final byte EVT_GR8_ID16_CH16_BOOL8 = 43;
    public static final byte EVT_GR8_ID16_CH16_INT8 = 44;
    public static final byte EVT_GR8_ID16_CH16_UINT8 = 45;
    public static final byte EVT_GR8_ID8_CH16_INT16 = 46;
    public static final byte EVT_GR8_ID8_CH16_UINT16 = 47;
    public static final byte EVT_CH16_INT32 = 48;
    public static final byte EVT_CH16_UINT32 = 49;
    public static final byte EVT_CH16_REAL32 = 50;
    public static final byte EVT_CH8_TICK32_BOOL8 = 51;
    public static final byte EVT_CH8_TICK32_INT8 = 52;
    public static final byte EVT_CH8_TICK32_UINT8 = 53;
    public static final byte EVT_GR8_ID8_CH8_INT32 = 54;
    public static final byte EVT_GR8_ID8_CH8_UINT32 = 55;
    public static final byte EVT_GR8_ID8_CH8_REAL32 = 56;
    public static final byte EVT_ID8_CH16_INT32 = 57;
    public static final byte EVT_ID8_CH16_UINT32 = 58;
    public static final byte EVT_ID8_CH16_REAL32 = 59;
    public static final byte EVT_GR8_ID16_CH16_INT16 = 60;
    public static final byte EVT_GR8_ID16_CH16_UINT16 = 61;
    public static final byte EVT_GR16_ID16_CH16_BOOL8 = 62;
    public static final byte EVT_GR16_ID16_CH16_INT8 = 63;
    public static final byte EVT_GR16_ID16_CH16_UINT8 = 64;
    public static final byte EVT_CH8_TICK32_INT16 = 65;
    public static final byte EVT_CH8_TICK32_UINT16 = 66;
    public static final byte EVT_ID8_CH8_TICK32_BOOL8 = 67;
    public static final byte EVT_ID8_CH8_TICK32_INT8 = 68;
    public static final byte EVT_ID8_CH8_TICK32_UINT8 = 69;
    public static final byte EVT_CH16_TICK32_BOOL8 = 70;
    public static final byte EVT_CH16_TICK32_INT8 = 71;
    public static final byte EVT_CH16_TICK32_UINT8 = 72;
    public static final byte EVT_TICK64 = 73;
    public static final byte EVT_GR16_ID16_CH16_INT16 = 74;
    public static final byte EVT_GR16_ID16_CH16_UINT16 = 75;
    public static final byte EVT_GR8_ID8_CH16_INT32 = 76;
    public static final byte EVT_GR8_ID8_CH16_UINT32 = 77;
    public static final byte EVT_GR8_ID8_CH16_REAL32 = 78;
    public static final byte EVT_ID8_CH8_TICK32_INT16 = 79;
    public static final byte EVT_ID8_CH8_TICK32_UINT16 = 80;
    public static final byte EVT_GR8_ID8_CH8_TICK32_BOOL8 = 81;
    public static final byte EVT_GR8_ID8_CH8_TICK32_INT8 = 82;
    public static final byte EVT_GR8_ID8_CH8_TICK32_UINT8 = 83;
    public static final byte EVT_ID8_CH16_TICK32_BOOL8 = 84;
    public static final byte EVT_ID8_CH16_TICK32_INT8 = 85;
    public static final byte EVT_ID8_CH16_TICK32_UINT8 = 86;
    public static final byte EVT_CH16_TICK32_INT16 = 87;
    public static final byte EVT_CH16_TICK32_UINT16 = 88;
    public static final byte EVT_CH8_REAL64 = 89;
    public static final byte EVT_GR8_ID16_CH16_INT32 = 90;
    public static final byte EVT_GR8_ID16_CH16_UINT32 = 91;
    public static final byte EVT_GR8_ID16_CH16_REAL32 = 92;
    public static final byte EVT_CH8_TICK32_INT32 = 93;
    public static final byte EVT_CH8_TICK32_UINT32 = 94;
    public static final byte EVT_CH8_TICK32_REAL32 = 95;
    public static final byte EVT_GR8_ID8_CH8_TICK32_INT16 = 96;
    public static final byte EVT_GR8_ID8_CH8_TICK32_UINT16 = 97;
    public static final byte EVT_ID8_CH16_TICK32_INT16 = 98;
    public static final byte EVT_ID8_CH16_TICK32_UINT16 = 99;
    public static final byte EVT_GR8_ID8_CH16_TICK32_BOOL8 = 100;
    public static final byte EVT_GR8_ID8_CH16_TICK32_INT8 = 101;
    public static final byte EVT_GR8_ID8_CH16_TICK32_UINT8 = 102;
    public static final byte EVT_ID16_CH16_TICK32_BOOL8 = 103;
    public static final byte EVT_ID16_CH16_TICK32_INT8 = 104;
    public static final byte EVT_ID16_CH16_TICK32_UINT8 = 105;
    public static final byte EVT_ID8_CH8_REAL64 = 106;
    public static final byte EVT_GR16_ID16_CH16_INT32 = 107;
    public static final byte EVT_GR16_ID16_CH16_UINT32 = 108;
    public static final byte EVT_GR16_ID16_CH16_REAL32 = 109;
    public static final byte EVT_CH16_REAL64 = 110;
    public static final byte EVT_ID8_CH8_TICK32_INT32 = 111;
    public static final byte EVT_ID8_CH8_TICK32_UINT32 = 112;
    public static final byte EVT_ID8_CH8_TICK32_REAL32 = 113;
    public static final byte EVT_GR8_ID16_CH16_TICK32_BOOL8 = 114;
    public static final byte EVT_GR8_ID16_CH16_TICK32_INT8 = 115;
    public static final byte EVT_GR8_ID16_CH16_TICK32_UINT8 = 116;
    public static final byte EVT_CH8_TICK64_BOOL8 = 117;
    public static final byte EVT_CH8_TICK64_INT8 = 118;
    public static final byte EVT_CH8_TICK64_UINT8 = 119;
    public static final byte EVT_GR8_ID8_CH16_TICK32_INT16 = 120;
    public static final byte EVT_GR8_ID8_CH16_TICK32_UINT16 = 121;
    public static final byte EVT_CH16_TICK32_INT32 = 122;
    public static final byte EVT_CH16_TICK32_UINT32 = 123;
    public static final byte EVT_CH16_TICK32_REAL32 = 124;
    public static final byte EVT_ID16_CH16_TICK32_INT16 = 125;
    public static final byte EVT_ID16_CH16_TICK32_UINT16 = 126;
    public static final byte EVT_GR8_ID8_CH8_REAL64 = 127;
    public static final byte EVT_ID8_CH16_REAL64 = -128;
    public static final byte EVT_GR8_ID8_CH8_TICK32_INT32 = -127;
    public static final byte EVT_GR8_ID8_CH8_TICK32_UINT32 = -126;
    public static final byte EVT_GR8_ID8_CH8_TICK32_REAL32 = -125;
    public static final byte EVT_ID8_CH16_TICK32_INT32 = -124;
    public static final byte EVT_ID8_CH16_TICK32_UINT32 = -123;
    public static final byte EVT_ID8_CH16_TICK32_REAL32 = -122;
    public static final byte EVT_GR8_ID16_CH16_TICK32_INT16 = -121;
    public static final byte EVT_GR8_ID16_CH16_TICK32_UINT16 = -120;
    public static final byte EVT_CH8_TICK64_INT16 = -119;
    public static final byte EVT_CH8_TICK64_UINT16 = -118;
    public static final byte EVT_ID8_CH8_TICK64_BOOL8 = -117;
    public static final byte EVT_ID8_CH8_TICK64_INT8 = -116;
    public static final byte EVT_ID8_CH8_TICK64_UINT8 = -115;
    public static final byte EVT_GR16_ID16_CH16_TICK32_BOOL8 = -114;
    public static final byte EVT_GR16_ID16_CH16_TICK32_INT8 = -113;
    public static final byte EVT_GR16_ID16_CH16_TICK32_UINT8 = -112;
    public static final byte EVT_GR8_ID8_CH16_REAL64 = -111;
    public static final byte EVT_ID8_CH8_TICK64_INT16 = -110;
    public static final byte EVT_ID8_CH8_TICK64_UINT16 = -109;
    public static final byte EVT_GR8_ID8_CH8_TICK64_BOOL8 = -108;
    public static final byte EVT_GR8_ID8_CH8_TICK64_INT8 = -107;
    public static final byte EVT_GR8_ID8_CH8_TICK64_UINT8 = -106;
    public static final byte EVT_ID8_CH16_TICK64_BOOL8 = -105;
    public static final byte EVT_ID8_CH16_TICK64_INT8 = -104;
    public static final byte EVT_ID8_CH16_TICK64_UINT8 = -103;
    public static final byte EVT_GR8_ID8_CH16_TICK32_INT32 = -102;
    public static final byte EVT_GR8_ID8_CH16_TICK32_UINT32 = -101;
    public static final byte EVT_GR8_ID8_CH16_TICK32_REAL32 = -100;
    public static final byte EVT_ID16_CH16_TICK32_INT32 = -99;
    public static final byte EVT_ID16_CH16_TICK32_UINT32 = -98;
    public static final byte EVT_ID16_CH16_TICK32_REAL32 = -97;
    public static final byte EVT_GR16_ID16_CH16_TICK32_INT16 = -96;
    public static final byte EVT_GR16_ID16_CH16_TICK32_UINT16 = -95;
    public static final byte EVT_GR8_ID16_CH16_REAL64 = -94;
    public static final byte EVT_CH8_TICK32_REAL64 = -93;
    public static final byte EVT_GR8_ID16_CH16_TICK32_INT32 = -92;
    public static final byte EVT_GR8_ID16_CH16_TICK32_UINT32 = -91;
    public static final byte EVT_GR8_ID16_CH16_TICK32_REAL32 = -90;
    public static final byte EVT_CH8_TICK64_INT32 = -89;
    public static final byte EVT_CH8_TICK64_UINT32 = -88;
    public static final byte EVT_CH8_TICK64_REAL32 = -87;
    public static final byte EVT_GR8_ID8_CH8_TICK64_INT16 = -86;
    public static final byte EVT_GR8_ID8_CH8_TICK64_UINT16 = -85;
    public static final byte EVT_ID8_CH16_TICK64_INT16 = -84;
    public static final byte EVT_ID8_CH16_TICK64_UINT16 = -83;
    public static final byte EVT_GR16_ID16_CH16_TICK32_INT32 = -82;
    public static final byte EVT_GR16_ID16_CH16_TICK32_UINT32 = -81;
    public static final byte EVT_GR16_ID16_CH16_TICK32_REAL32 = -80;
    public static final byte EVT_GR16_ID16_CH16_REAL64 = -79;
    public static final byte EVT_ID8_CH8_TICK32_REAL64 = -78;
    public static final byte EVT_ID8_CH8_TICK64_INT32 = -77;
    public static final byte EVT_ID8_CH8_TICK64_UINT32 = -76;
    public static final byte EVT_ID8_CH8_TICK64_REAL32 = -75;
    public static final byte EVT_GR8_ID16_CH16_TICK64_BOOL8 = -74;
    public static final byte EVT_GR8_ID16_CH16_TICK64_INT8 = -73;
    public static final byte EVT_GR8_ID16_CH16_TICK64_UINT8 = -72;
    public static final byte EVT_CH16_TICK32_REAL64 = -71;
    public static final byte EVT_CH16_TICK64_INT32 = -70;
    public static final byte EVT_CH16_TICK64_UINT32 = -69;
    public static final byte EVT_CH16_TICK64_REAL32 = -68;
    public static final byte EVT_GR8_ID8_CH8_TICK32_REAL64 = -67;
    public static final byte EVT_ID8_CH16_TICK32_REAL64 = -66;
    public static final byte EVT_GR8_ID8_CH8_TICK64_INT32 = -65;
    public static final byte EVT_GR8_ID8_CH8_TICK64_UINT32 = -64;
    public static final byte EVT_GR8_ID8_CH8_TICK64_REAL32 = -63;
    public static final byte EVT_ID8_CH16_TICK64_INT32 = -62;
    public static final byte EVT_ID8_CH16_TICK64_UINT32 = -61;
    public static final byte EVT_ID8_CH16_TICK64_REAL32 = -60;
    public static final byte EVT_GR8_ID16_CH16_TICK64_INT16 = -59;
    public static final byte EVT_GR8_ID16_CH16_TICK64_UINT16 = -58;
    public static final byte EVT_GR8_ID8_CH16_TICK32_REAL64 = -57;
    public static final byte EVT_ID16_CH16_TICK32_REAL64 = -56;
    public static final byte EVT_ID16_CH16_TICK64_INT32 = -55;
    public static final byte EVT_ID16_CH16_TICK64_UINT32 = -54;
    public static final byte EVT_ID16_CH16_TICK64_REAL32 = -53;
    public static final byte EVT_GR8_ID16_CH16_TICK32_REAL64 = -52;
    public static final byte EVT_CH8_TICK64_REAL64 = -51;
    public static final byte EVT_GR8_ID16_CH16_TICK64_INT32 = -50;
    public static final byte EVT_GR8_ID16_CH16_TICK64_UINT32 = -49;
    public static final byte EVT_GR8_ID16_CH16_TICK64_REAL32 = -48;
    public static final byte EVT_ID8_CH8_TICK64_REAL64 = -47;
    public static final byte EVT_GR16_ID16_CH16_TICK32_INT64 = -46;
    public static final byte EVT_GR16_ID16_CH16_TICK32_UINT64 = -45;
    public static final byte EVT_GR16_ID16_CH16_TICK32_REAL64 = -44;
    public static final byte EVT_GR16_ID16_CH16_TICK64_INT32 = -43;
    public static final byte EVT_GR16_ID16_CH16_TICK64_UINT32 = -42;
    public static final byte EVT_GR16_ID16_CH16_TICK64_REAL32 = -41;
    public static final byte EVT_CH16_TICK64_REAL64 = -40;
    public static final byte EVT_GR8_ID8_CH8_TICK64_REAL64 = -39;
    public static final byte EVT_ID8_CH16_TICK64_INT64 = -38;
    public static final byte EVT_ID8_CH16_TICK64_UINT64 = -37;
    public static final byte EVT_ID8_CH16_TICK64_REAL64 = -36;
    public static final byte EVT_ID16_CH16_TICK64_REAL64 = -35;
    public static final byte EVT_GR8_ID16_CH16_TICK64_REAL64 = -34;
    public static final byte EVT_GR16_ID16_CH16_TICK64_INT64 = -33;
    public static final byte EVT_GR16_ID16_CH16_TICK64_UINT64 = -32;
    public static final byte EVT_GR16_ID16_CH16_TICK64_REAL64 = -31;
    public static final byte EVT_CH8_STRING = -30;
    public static final byte EVT_CH8_BYTES = -29;
    public static final byte EVT_CH16_STRING = -28;
    public static final byte EVT_CH16_BYTES = -27;
    public static final byte EVT_GR8_ID8_CH8_STRING = -26;
    public static final byte EVT_GR8_ID8_CH8_BYTES = -25;
    public static final byte EVT_GR16_ID16_CH16_STRING = -24;
    public static final byte EVT_GR16_ID16_CH16_BYTES = -23;
    public static final byte EVT_CH8_TICK32_STRING = -22;
    public static final byte EVT_CH8_TICK32_BYTES = -21;
    public static final byte EVT_CH16_TICK32_STRING = -20;
    public static final byte EVT_CH16_TICK32_BYTES = -19;
    public static final byte EVT_GR8_ID8_CH8_TICK32_STRING = -18;
    public static final byte EVT_GR8_ID8_CH8_TICK32_BYTES = -17;
    public static final byte EVT_GR8_ID8_CH16_TICK32_STRING = -16;
    public static final byte EVT_GR8_ID8_CH16_TICK32_BYTES = -15;
    public static final byte EVT_GR16_ID16_CH16_TICK32_STRING = -14;
    public static final byte EVT_GR16_ID16_CH16_TICK32_BYTES = -13;
    public static final byte EVT_CH8_TICK64_STRING = -12;
    public static final byte EVT_CH8_TICK64_BYTES = -11;
    public static final byte EVT_CH16_TICK64_STRING = -10;
    public static final byte EVT_CH16_TICK64_BYTES = -9;
    public static final byte EVT_GR8_ID8_CH8_TICK64_STRING = -8;
    public static final byte EVT_GR8_ID8_CH8_TICK64_BYTES = -7;
    public static final byte EVT_GR8_ID8_CH16_TICK64_STRING = -6;
    public static final byte EVT_GR8_ID8_CH16_TICK64_BYTES = -5;
    public static final byte EVT_GR16_ID16_CH16_TICK64_STRING = -4;
    public static final byte EVT_GR16_ID16_CH16_TICK64_BYTES = -3;
    private byte[] buffer = new byte[4000];
    private int bufferSize = 4000;
    private int end = 4000 - 1;
    private int bufferWritePointer = 0;
    private int bufferReadPointer = 0;
    private int bufferOverflow = 0;
    private byte magicByte = MAGIC_BYTE_V1_LITTLE_ENDIAN;
    private int i = 0;
    
    
    public LogBuffer() {
         //Default constructor
    }

    private boolean getNextWritePointer(int length) {
        i = -1;
        if (bufferWritePointer >= bufferReadPointer) {
            if (bufferWritePointer + length >= end) {
                if (bufferReadPointer >= length) {
                    bufferOverflow = bufferWritePointer;
                    buffer[0] = START_FLAG;
                    buffer[1] = magicByte;
                    i = 2;
                    return true;
                }
            } else if (bufferSize - (bufferWritePointer - bufferReadPointer) >= length) {
                i = bufferWritePointer + 1;
                buffer[i] = START_FLAG;
                buffer[++i] = magicByte;
                return true;
            }
        } else if (bufferReadPointer - bufferWritePointer >= length) {
            i = bufferWritePointer + 1;
            buffer[i] = START_FLAG;
            buffer[++i] = magicByte;
            return true;
        }
        return false;
    }

    protected void evtNull() {
        if (getNextWritePointer(4)) {
            buffer[++i] = EVT_NULL;
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtTick32(int tick) {
        if (getNextWritePointer(8)) {
            buffer[++i] = EVT_TICK32;
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtCh8Tick32Bool8(byte channel, int tick, boolean value) {
        if (getNextWritePointer(10)) {
            buffer[++i] = EVT_CH8_TICK32_BOOL8;
            buffer[++i] = channel;
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value? 1:0);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtCh8Tick32Int16(byte channel, int tick, short value) {
        if (getNextWritePointer(11)) {
            buffer[++i] = EVT_CH8_TICK32_INT16;
            buffer[++i] = channel;
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value & 0xff);
            buffer[++i] = (byte)((value >> 8) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtCh8Tick32Int32(byte channel, int tick, int value) {
        if (getNextWritePointer(13)) {
            buffer[++i] = EVT_CH8_TICK32_INT32;
            buffer[++i] = channel;
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value & 0xff);
            buffer[++i] = (byte)((value >> 8) & 0xff);
            buffer[++i] = (byte)((value >> 16) & 0xff);
            buffer[++i] = (byte)((value >> 24) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtCh8Tick32UInt32(byte channel, int tick, int value) {
        if (getNextWritePointer(13)) {
            buffer[++i] = EVT_CH8_TICK32_UINT32;
            buffer[++i] = channel;
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value & 0xff);
            buffer[++i] = (byte)((value >> 8) & 0xff);
            buffer[++i] = (byte)((value >> 16) & 0xff);
            buffer[++i] = (byte)((value >> 24) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtCh16Tick32Bool8(short channel, int tick, boolean value) {
        if (getNextWritePointer(11)) {
            buffer[++i] = EVT_CH16_TICK32_BOOL8;
            buffer[++i] = (byte)(channel & 0xff);
            buffer[++i] = (byte)((channel >> 8) & 0xff);
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value? 1:0);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtCh16Tick32Int16(short channel, int tick, short value) {
        if (getNextWritePointer(12)) {
            buffer[++i] = EVT_CH16_TICK32_INT16;
            buffer[++i] = (byte)(channel & 0xff);
            buffer[++i] = (byte)((channel >> 8) & 0xff);
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value & 0xff);
            buffer[++i] = (byte)((value >> 8) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtCh16Tick32Int32(short channel, int tick, int value) {
        if (getNextWritePointer(14)) {
            buffer[++i] = EVT_CH16_TICK32_INT32;
            buffer[++i] = (byte)(channel & 0xff);
            buffer[++i] = (byte)((channel >> 8) & 0xff);
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value & 0xff);
            buffer[++i] = (byte)((value >> 8) & 0xff);
            buffer[++i] = (byte)((value >> 16) & 0xff);
            buffer[++i] = (byte)((value >> 24) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtCh16Tick32UInt32(short channel, int tick, int value) {
        if (getNextWritePointer(14)) {
            buffer[++i] = EVT_CH16_TICK32_UINT32;
            buffer[++i] = (byte)(channel & 0xff);
            buffer[++i] = (byte)((channel >> 8) & 0xff);
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value & 0xff);
            buffer[++i] = (byte)((value >> 8) & 0xff);
            buffer[++i] = (byte)((value >> 16) & 0xff);
            buffer[++i] = (byte)((value >> 24) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtGr8Id8Ch8Tick32Bool8(byte group, byte id, byte channel, int tick, boolean value) {
        if (getNextWritePointer(12)) {
            buffer[++i] = EVT_GR8_ID8_CH8_TICK32_BOOL8;
            buffer[++i] = group;
            buffer[++i] = id;
            buffer[++i] = channel;
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value? 1:0);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtGr8Id8Ch8Tick32Int16(byte group, byte id, byte channel, int tick, short value) {
        if (getNextWritePointer(13)) {
            buffer[++i] = EVT_GR8_ID8_CH8_TICK32_INT16;
            buffer[++i] = group;
            buffer[++i] = id;
            buffer[++i] = channel;
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value & 0xff);
            buffer[++i] = (byte)((value >> 8) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtGr8Id8Ch8Tick32Int32(byte group, byte id, byte channel, int tick, int value) {
        if (getNextWritePointer(15)) {
            buffer[++i] = EVT_GR8_ID8_CH8_TICK32_INT32;
            buffer[++i] = group;
            buffer[++i] = id;
            buffer[++i] = channel;
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value & 0xff);
            buffer[++i] = (byte)((value >> 8) & 0xff);
            buffer[++i] = (byte)((value >> 16) & 0xff);
            buffer[++i] = (byte)((value >> 24) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtGr8Id8Ch8Tick32UInt32(byte group, byte id, byte channel, int tick, int value) {
        if (getNextWritePointer(15)) {
            buffer[++i] = EVT_GR8_ID8_CH8_TICK32_UINT32;
            buffer[++i] = group;
            buffer[++i] = id;
            buffer[++i] = channel;
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value & 0xff);
            buffer[++i] = (byte)((value >> 8) & 0xff);
            buffer[++i] = (byte)((value >> 16) & 0xff);
            buffer[++i] = (byte)((value >> 24) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtGr8Id8Ch16Tick32Bool8(byte group, byte id, short channel, int tick, boolean value) {
        if (getNextWritePointer(13)) {
            buffer[++i] = EVT_GR8_ID8_CH16_TICK32_BOOL8;
            buffer[++i] = group;
            buffer[++i] = id;
            buffer[++i] = (byte)(channel & 0xff);
            buffer[++i] = (byte)((channel >> 8) & 0xff);
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value? 1:0);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtGr8Id8Ch16Tick32Int16(byte group, byte id, short channel, int tick, short value) {
        if (getNextWritePointer(14)) {
            buffer[++i] = EVT_GR8_ID8_CH16_TICK32_INT16;
            buffer[++i] = group;
            buffer[++i] = id;
            buffer[++i] = (byte)(channel & 0xff);
            buffer[++i] = (byte)((channel >> 8) & 0xff);
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value & 0xff);
            buffer[++i] = (byte)((value >> 8) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtGr8Id8Ch16Tick32Int32(byte group, byte id, short channel, int tick, int value) {
        if (getNextWritePointer(16)) {
            buffer[++i] = EVT_GR8_ID8_CH16_TICK32_INT32;
            buffer[++i] = group;
            buffer[++i] = id;
            buffer[++i] = (byte)(channel & 0xff);
            buffer[++i] = (byte)((channel >> 8) & 0xff);
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value & 0xff);
            buffer[++i] = (byte)((value >> 8) & 0xff);
            buffer[++i] = (byte)((value >> 16) & 0xff);
            buffer[++i] = (byte)((value >> 24) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    protected void evtGr8Id8Ch16Tick32UInt32(byte group, byte id, short channel, int tick, int value) {
        if (getNextWritePointer(16)) {
            buffer[++i] = EVT_GR8_ID8_CH16_TICK32_UINT32;
            buffer[++i] = group;
            buffer[++i] = id;
            buffer[++i] = (byte)(channel & 0xff);
            buffer[++i] = (byte)((channel >> 8) & 0xff);
            buffer[++i] = (byte)(tick & 0xff);
            buffer[++i] = (byte)((tick >> 8) & 0xff);
            buffer[++i] = (byte)((tick >> 16) & 0xff);
            buffer[++i] = (byte)((tick >> 24) & 0xff);
            buffer[++i] = (byte)(value & 0xff);
            buffer[++i] = (byte)((value >> 8) & 0xff);
            buffer[++i] = (byte)((value >> 16) & 0xff);
            buffer[++i] = (byte)((value >> 24) & 0xff);
            buffer[++i] = END_FLAG;
            bufferWritePointer = i;
        }
    }

    public byte[] readBytes() {
        if (bufferWritePointer > bufferReadPointer) {
            final byte[] bytes = Arrays.copyOfRange(buffer, bufferReadPointer + 1, bufferWritePointer + 1);
            bufferReadPointer = bufferWritePointer;
            return bytes;
        } else if (bufferWritePointer < bufferReadPointer) {
            if (bufferReadPointer < bufferOverflow) {
                final byte[] bytes = Arrays.copyOfRange(buffer, bufferReadPointer + 1, bufferOverflow + 1);
                bufferReadPointer = bufferOverflow;
                return bytes;
            } else if (bufferWritePointer > 0) {
                final byte[] bytes = Arrays.copyOfRange(buffer, 0, bufferWritePointer + 1);
                bufferReadPointer = bufferWritePointer;
                return bytes;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
