package com.hiti.nfc.utility;

import android.nfc.NdefMessage;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.hiti.nfc.ByteUtility;
import com.hiti.nfc.DataParser;
import com.hiti.nfc.Nfc;
import java.util.ArrayList;
import java.util.HashMap;

public class NfcHandler extends Handler {
    private static final boolean localLOG = true;
    private static final String tag;
    private ArrayList<HashMap<String, String>> arrayInfo;
    private IhandlerListener listener;

    public interface IhandlerListener {
        void GetNfcData(ArrayList<HashMap<String, String>> arrayList);
    }

    static {
        tag = NfcHandler.class.getSimpleName();
    }

    public NfcHandler() {
        this.arrayInfo = new ArrayList();
    }

    public void SetHandlerListener(IhandlerListener listener) {
        this.listener = listener;
    }

    public void handleMessage(Message msg) {
        NdefMessage ndefMsg = (NdefMessage) msg.getData().getParcelable(Nfc.NFC_DATA);
        if (ndefMsg == null) {
            HashMap hashMap = new HashMap();
            HashMap<String, String> buf = new HashMap();
            buf.put("name", "error");
            buf.put("value", "not-support");
            this.arrayInfo.add(buf);
            if (this.listener != null) {
                this.listener.GetNfcData(this.arrayInfo);
                return;
            }
            return;
        }
        String password;
        String ssid = DataParser.getSSID(ndefMsg);
        Log.v(tag, "showSSIDData(): read data from NFC is " + ssid);
        if (ssid == null) {
            ssid = "null";
            password = "null";
        } else {
            byte[] passwordOffset = new byte[1];
            System.arraycopy(ssid.getBytes(), 0, passwordOffset, 0, 1);
            int offset = ByteUtility.byte2Int(passwordOffset);
            Log.v(tag, "showSSIDData(): offset = " + offset);
            ssid = ssid.substring(1);
            if (offset < ssid.length()) {
                password = ssid.substring(offset);
                ssid = ssid.substring(0, offset);
            } else {
                password = null;
            }
        }
        Log.v(tag, "showSSIDData(): ssid = " + ssid + ", pwd = " + password);
        hashMap = new HashMap();
        buf = new HashMap();
        buf.put("name", "SSID");
        buf.put("value", ssid);
        this.arrayInfo.add(buf);
        buf = new HashMap();
        buf.put("name", "password");
        buf.put("value", password);
        this.arrayInfo.add(buf);
        String printer = DataParser.getPrinter(ndefMsg);
        if (printer == null) {
            printer = "null";
        }
        buf = new HashMap();
        buf.put("name", "printer");
        buf.put("value", printer);
        this.arrayInfo.add(buf);
        String app = DataParser.getAppName(ndefMsg);
        if (app == null) {
            app = "null";
        }
        buf = new HashMap();
        buf.put("name", "app");
        buf.put("value", app);
        this.arrayInfo.add(buf);
        if (this.listener != null) {
            this.listener.GetNfcData(this.arrayInfo);
        }
    }
}
