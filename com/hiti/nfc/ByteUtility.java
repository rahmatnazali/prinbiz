package com.hiti.nfc;

import java.math.BigInteger;
import org.apache.commons.net.telnet.TelnetOption;

public class ByteUtility {
    public static String printHexString(byte[] data) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            s.append(String.format("%02x", new Object[]{Integer.valueOf(data[i] & TelnetOption.MAX_OPTION_VALUE)}));
        }
        return s.toString();
    }

    public static int byte2Int(byte[] data) {
        return new BigInteger(data).intValue();
    }

    public static int byte2Int(byte[] data, int srcPos, int length) {
        if (srcPos + length > data.length) {
            length = data.length - srcPos;
        }
        byte[] buf = new byte[length];
        System.arraycopy(data, srcPos, buf, 0, length);
        return new BigInteger(buf).intValue();
    }

    public static String Ascii2string(byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        StringBuffer s = new StringBuffer();
        for (byte b : data) {
            s.append((char) b);
        }
        return s.toString();
    }

    public static String Ascii2string(byte[] data, int srcPos, int length) {
        if (data == null || data.length == 0 || length == 0 || srcPos >= data.length) {
            return null;
        }
        if (srcPos + length > data.length) {
            length = data.length - srcPos;
        }
        StringBuffer s = new StringBuffer();
        for (int i = srcPos; i < length; i++) {
            s.append((char) data[i]);
        }
        return s.toString();
    }
}
