package com.hiti.utility;

import android.util.Log;
import org.apache.commons.net.telnet.TelnetOption;

public class ByteConvertUtility_520 extends ByteConvertUtility {
    public static void EdienChange(byte[] array, int iStart, int iLength, byte[] byName) {
        for (int i = 0; i < iLength; i++) {
            if (i % 2 == 0) {
                byName[i] = array[(iStart + i) + 1];
            } else {
                byName[i] = array[(iStart + i) - 1];
            }
        }
    }

    public static short ByteToShort(byte[] array, int iStart, int iLength) {
        short iRet = (short) 0;
        for (int i = 0; i < iLength; i++) {
            iRet = (short) ((((short) (array[i + iStart] & TelnetOption.MAX_OPTION_VALUE)) << (8 - (i * 8))) | iRet);
        }
        Log.e("ByteToInt", Integer.toHexString(iRet));
        return iRet;
    }
}
