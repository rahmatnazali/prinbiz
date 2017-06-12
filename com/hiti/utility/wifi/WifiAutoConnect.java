package com.hiti.utility.wifi;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import org.xmlpull.v1.XmlPullParser;

public class WifiAutoConnect extends AsyncTask<Void, Integer, Boolean> {
    public static final int CONNECTED_ENDURANCE = 10;
    public static final int DISCONNECT_ENDURANCE = 10;
    public static final int TIMEOUT_ENDURANCE = 30;
    ConnectStep mConn;
    private Context m_Context;
    private String m_Password;
    private String m_SSID;
    private WifiConnect m_WifiConnect;
    private boolean m_boStop;

    public WifiAutoConnect(Context context, String strSSID, String strPassword) {
        this.m_boStop = false;
        this.m_Context = null;
        this.m_SSID = XmlPullParser.NO_NAMESPACE;
        this.m_Password = XmlPullParser.NO_NAMESPACE;
        this.m_WifiConnect = null;
        this.mConn = null;
        this.m_Context = context;
        GetResourceID(context);
        this.m_SSID = strSSID;
        this.m_Password = strPassword;
        if (this.m_Password == null) {
            this.m_Password = XmlPullParser.NO_NAMESPACE;
        }
        this.m_WifiConnect = new WifiConnect(context, (WifiManager) this.m_Context.getSystemService("wifi"));
    }

    private void GetResourceID(Context context) {
    }

    public void SetStop(boolean boStop) {
        this.m_boStop = boStop;
        if (this.mConn != null) {
            this.mConn.Stop();
        }
    }

    protected Boolean doInBackground(Void... params) {
        this.mConn = new ConnectStep(this.m_Context, this.m_WifiConnect);
        return Boolean.valueOf(this.mConn.WifiCheck(this.m_SSID, this.m_Password));
    }

    public void onProgressUpdate(Integer... params) {
    }

    protected void onPostExecute(Boolean boResult) {
        if (this.m_boStop) {
            ConnectionStop();
        } else if (boResult.booleanValue()) {
            ConnectionSuccess();
        } else {
            ConnectionFail();
        }
    }

    public void ConnectionSuccess() {
    }

    public void ConnectionFail() {
    }

    public void ConnectionStop() {
    }
}
