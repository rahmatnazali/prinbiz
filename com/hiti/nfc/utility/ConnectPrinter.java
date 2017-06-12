package com.hiti.nfc.utility;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.hiti.utility.wifi.ConnectStep;
import org.xmlpull.v1.XmlPullParser;

public class ConnectPrinter extends ConnectStep {
    private WiFiListener m_WiFiListener;

    public interface WiFiListener {
        void onConnectFail();

        void onConnectSuccess();
    }

    public ConnectPrinter(Context context, String strSSID, String strPassword) {
        super(context, strSSID, strPassword);
        this.m_WiFiListener = null;
    }

    public void SetListener(WiFiListener listner) {
        this.m_WiFiListener = listner;
    }

    public static void NfcConnect(NFCInfo mNFCInfo, Context context, String strSuccess, String strFail) {
        Log.v("ConnectPrinter", "mSSID:" + mNFCInfo.mSSID);
        Connect(context, strSuccess, strFail, mNFCInfo.mSSID, mNFCInfo.mPassword);
    }

    public static void BLEConnect(Context context, String strSuccess, String strFail, String strSSID, String strPassword) {
        Log.v("ConnectPrinter", "mSSID:" + strSSID);
        Connect(context, strSuccess, strFail, strSSID, strPassword);
    }

    private static void Connect(Context context, String strSuccess, String strFail, String strSSID, String strPassword) {
        ConnectPrinter m_WifiConnect = new ConnectPrinter(context, strSSID, strPassword);
        m_WifiConnect.SetListener(null);
        m_WifiConnect.Start();
    }

    public static String GetSSID(Context context) {
        String strSSID = XmlPullParser.NO_NAMESPACE;
        WifiInfo wifiInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        if (wifiInfo.getSSID() == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        return wifiInfo.getSSID();
    }

    public void ConnectionSuccess() {
        Log.v("ConnectionSuccess", "OK");
        Stop();
        if (this.m_WiFiListener != null) {
            this.m_WiFiListener.onConnectSuccess();
        }
    }

    public void ConnectionFail() {
        Stop();
        Log.v("ConnectionFail", "Fail");
        if (this.m_WiFiListener != null) {
            this.m_WiFiListener.onConnectFail();
        }
    }
}
