package com.hiti.nfc;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;

public class DataParser {
    private static final boolean localLOG = true;
    private static final String tag;

    static {
        tag = DataParser.class.getSimpleName();
    }

    public static String getSSID(NdefMessage msg) {
        Log.v(tag, "getSSID(): begin");
        if (msg == null) {
            return null;
        }
        for (NdefRecord record : msg.getRecords()) {
            String type = ByteUtility.Ascii2string(record.getType());
            String payload = ByteUtility.Ascii2string(record.getPayload());
            if (type.contains("application/hiti.")) {
                Log.v(tag, "getSSID(): SSID = " + (payload != null ? payload : "null"));
                return payload;
            }
        }
        return null;
    }

    public static String getAppName(NdefMessage msg) {
        Log.v(tag, "getAppName(): begin");
        if (msg == null) {
            return null;
        }
        for (NdefRecord record : msg.getRecords()) {
            String type = ByteUtility.Ascii2string(record.getType());
            if (type.contains("application/hiti.")) {
                String appName = type.replace("application/hiti.", XmlPullParser.NO_NAMESPACE);
                Log.v(tag, "getAppName(): appName = " + (appName != null ? appName : "null"));
                return appName;
            }
        }
        return null;
    }

    public static String getPrinter(NdefMessage msg) {
        Log.v(tag, "getPrinter(): begin");
        if (msg == null) {
            return null;
        }
        for (NdefRecord record : msg.getRecords()) {
            String type = ByteUtility.Ascii2string(record.getType());
            if (type.contains("printer/")) {
                String printer = type.replace("printer/", XmlPullParser.NO_NAMESPACE);
                Log.v(tag, "getPrinter(): printer = " + (printer != null ? printer : "null"));
                return printer;
            }
        }
        return null;
    }

    static String getUUID(byte[] ID) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < ID.length; index++) {
            builder.append(String.format("%02x", new Object[]{Byte.valueOf(ID[index])}));
            if (index < ID.length - 1) {
                builder.append(":");
            }
        }
        return builder.toString();
    }

    static String getTechArray(String[] techArray) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < techArray.length; index++) {
            builder.append(techArray[index]);
            if (index < techArray.length - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }
}
