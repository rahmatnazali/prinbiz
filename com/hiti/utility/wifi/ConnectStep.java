package com.hiti.utility.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.hiti.utility.LogManager;
import org.xmlpull.v1.XmlPullParser;

public class ConnectStep {
    public static final int CONNECTED_ENDURANCE = 10;
    public static final int DISCONNECT_ENDURANCE = 10;
    public static final int TIMEOUT_ENDURANCE = 30;
    LogManager LOG;
    Context mContext;
    Type mType;
    private String m_Password;
    private String m_SSID;
    WifiConnect m_WifiConnect;
    boolean m_boStop;

    /* renamed from: com.hiti.utility.wifi.ConnectStep.1 */
    class C04861 implements Runnable {
        C04861() {
        }

        public void run() {
            boolean bRet = ConnectStep.this.WifiCheck(ConnectStep.this.m_SSID, ConnectStep.this.m_Password);
            if (ConnectStep.this.m_boStop) {
                ConnectStep.this.ConnectionStop();
            } else if (bRet) {
                ConnectStep.this.ConnectionSuccess();
            } else {
                ConnectStep.this.ConnectionFail();
            }
        }
    }

    enum Type {
        T_asynkTask,
        T_Thread
    }

    public ConnectStep(Context context, WifiConnect mWifiConnect) {
        this.mContext = null;
        this.m_WifiConnect = null;
        this.m_boStop = false;
        this.m_SSID = XmlPullParser.NO_NAMESPACE;
        this.m_Password = XmlPullParser.NO_NAMESPACE;
        this.mType = Type.T_asynkTask;
        this.LOG = null;
        this.mContext = context;
        this.m_WifiConnect = mWifiConnect;
        this.LOG = new LogManager(0);
    }

    public ConnectStep(Context context, String strSSID, String strPassword) {
        this.mContext = null;
        this.m_WifiConnect = null;
        this.m_boStop = false;
        this.m_SSID = XmlPullParser.NO_NAMESPACE;
        this.m_Password = XmlPullParser.NO_NAMESPACE;
        this.mType = Type.T_asynkTask;
        this.LOG = null;
        this.mType = Type.T_Thread;
        this.mContext = context;
        this.m_SSID = strSSID;
        this.m_Password = strPassword;
        if (this.m_Password == null) {
            this.m_Password = XmlPullParser.NO_NAMESPACE;
        }
        this.m_WifiConnect = new WifiConnect(context, (WifiManager) this.mContext.getSystemService("wifi"));
        this.LOG = new LogManager(0);
    }

    public void Start() {
        if (this.mType == Type.T_Thread) {
            this.LOG.m386v("ConnectStep", "Start");
            new Thread(new C04861()).start();
        }
    }

    public void Stop() {
        this.m_boStop = true;
    }

    public void ConnectionSuccess() {
    }

    public void ConnectionFail() {
    }

    public void ConnectionStop() {
    }

    boolean WifiCheck(String m_SSID, String m_Password) {
        boolean boRet;
        this.LOG.m385i("WifiCheck", m_SSID);
        if (m_Password.length() == 0) {
            boRet = this.m_WifiConnect.Connect(m_SSID, null, WifiCipherType.WIFICIPHER_NOPASS);
            this.LOG.m385i("Wait Connect", "No password");
        } else {
            boRet = this.m_WifiConnect.Connect(m_SSID, m_Password, WifiCipherType.WIFICIPHER_WEP);
            this.LOG.m385i("Wait Connect", "m_Password=" + m_Password);
        }
        int iDisconnectedCount = DISCONNECT_ENDURANCE;
        int iConnectedCount = DISCONNECT_ENDURANCE;
        long lStart = System.currentTimeMillis();
        long lEnd = System.currentTimeMillis();
        this.LOG.m385i("Wait Connect", m_SSID);
        this.LOG.m385i("Wait Connect " + boRet, m_SSID);
        if (!boRet) {
            return boRet;
        }
        while (!this.m_boStop) {
            NetworkInfo mWifi = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getNetworkInfo(1);
            if (mWifi != null) {
                WifiInfo wifiInfo = ((WifiManager) this.mContext.getSystemService("wifi")).getConnectionInfo();
                this.LOG.m385i("mWifi.getState()", mWifi.getState().toString());
                State NetworkInfoState = mWifi.getState();
                if (NetworkInfoState == State.DISCONNECTED) {
                    iDisconnectedCount--;
                    if (iDisconnectedCount < 0) {
                        this.LOG.m385i("No network", "WifiAutoConnect");
                        return false;
                    }
                }
                if (!(NetworkInfoState != State.CONNECTED || wifiInfo.getSSID() == null || m_SSID == null)) {
                    this.LOG.m385i("Find network", wifiInfo.getSSID());
                    this.LOG.m385i("Compare network", m_SSID);
                    if (wifiInfo.getSSID().contains(m_SSID)) {
                        this.LOG.m385i("Get network", "WifiAutoConnect");
                        return true;
                    }
                    iConnectedCount--;
                    if (iConnectedCount < 0) {
                        this.LOG.m385i("Get network due to user change", "WifiAutoConnect");
                        return true;
                    }
                }
                if ((System.currentTimeMillis() - lStart) / 1000 > 30) {
                    this.LOG.m385i("No network due to time out", "WifiAutoConnect");
                    return false;
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return boRet;
    }
}
