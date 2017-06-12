package com.hiti.utility.wifi;

import android.app.Activity;
import android.content.Context;
import android.support.v4.media.TransportMediator;
import com.hiti.trace.GlobalVariable_WifiAutoConnectInfo;
import com.hiti.utility.LogManager;
import com.hiti.utility.dialog.ShowMSGDialog;

public abstract class ConnectWifi {
    LogManager LOG;
    boolean mConnecting;
    private Context mContext;
    protected IConnect mIConnect;
    protected ConnectPrinter m_ConnectPrinter;
    protected ShowMSGDialog m_ShowMSGDialog;
    private GlobalVariable_WifiAutoConnectInfo m_WifiAutoConnectInfo;

    public interface IConnect {
        void AfterWifiConnected();
    }

    public class ConnectPrinter extends WifiAutoConnect {
        ConnectPrinter(Context context, String strSSID, String strPassword) {
            super(context, strSSID, strPassword);
        }

        public void ConnectionSuccess() {
            ConnectWifi.this.LOG.m385i("ConnectWifi ConnectPrinter", "ConnectionSuccess now SSID: " + WifiSetting.GetSSID(ConnectWifi.this.mContext));
            if (ConnectWifi.this.mConnecting) {
                ConnectWifi.this.mConnecting = false;
                if (ConnectWifi.this.m_ShowMSGDialog != null) {
                    ConnectWifi.this.m_ShowMSGDialog.StopMSGDialog();
                }
                if (ConnectWifi.this.mIConnect != null) {
                    ConnectWifi.this.mIConnect.AfterWifiConnected();
                }
            }
        }

        public void ConnectionFail() {
            ConnectWifi.this.LOG.m385i("ConnectWifi ConnectionFail", "ConnectionSuccess now SSID: " + WifiSetting.GetSSID(ConnectWifi.this.mContext));
            if (ConnectWifi.this.mConnecting) {
                ConnectWifi.this.mConnecting = false;
                if (ConnectWifi.this.m_ShowMSGDialog != null) {
                    ConnectWifi.this.m_ShowMSGDialog.StopMSGDialog();
                }
                ((Activity) ConnectWifi.this.mContext).getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                ConnectWifi.this.ShowNoWiFiDialog();
            }
        }
    }

    public abstract ShowMSGDialog CreateConnectPrinterHintDialog();

    public abstract void CreateConnectWifiHintDialog(String str, String str2);

    public abstract void ShowNoWiFiDialog();

    public ConnectWifi(Context context, IConnect mIConnect) {
        this.mContext = null;
        this.m_WifiAutoConnectInfo = null;
        this.m_ShowMSGDialog = null;
        this.m_ConnectPrinter = null;
        this.mIConnect = null;
        this.mConnecting = false;
        this.LOG = null;
        this.LOG = new LogManager(0);
        this.mContext = context;
        this.mIConnect = mIConnect;
        this.m_WifiAutoConnectInfo = new GlobalVariable_WifiAutoConnectInfo(context);
    }

    public boolean IsConnecting() {
        return this.mConnecting;
    }

    public void Stop() {
        this.LOG.m385i("ConnectWifi", "Stop");
        if (this.mConnecting) {
            this.mConnecting = false;
            if (this.m_ConnectPrinter != null) {
                this.m_ConnectPrinter.SetStop(true);
                this.m_ConnectPrinter.cancel(true);
            }
            ((Activity) this.mContext).getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
            if (this.m_ShowMSGDialog != null) {
                this.m_ShowMSGDialog.StopMSGDialog();
            }
        }
    }

    public void Connect(boolean bDirect) {
        ((Activity) this.mContext).getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        this.m_WifiAutoConnectInfo.RestoreGlobalVariable();
        this.mConnecting = true;
        this.LOG.m385i("ConnectWifi Connect " + bDirect, "default SSID: " + this.m_WifiAutoConnectInfo.GetSSID());
        if (!WifiSetting.IsWifiConnected(this.mContext)) {
            ConnectDefaultSSID(bDirect);
        } else if (WifiSetting.GetSSID(this.mContext).contains(this.m_WifiAutoConnectInfo.GetSSID())) {
            this.mConnecting = false;
            if (this.mIConnect != null) {
                this.mIConnect.AfterWifiConnected();
            }
        } else if (bDirect) {
            ConnectDefaultSSID(bDirect);
        } else {
            CreateConnectWifiHintDialog(WifiSetting.GetSSID(this.mContext), this.m_WifiAutoConnectInfo.GetSSID());
        }
    }

    protected void ConnectDefaultSSID(boolean bDirect) {
        this.LOG.m385i("ConnectWifi ConnectDefaultSSID", "default SSID: " + this.m_WifiAutoConnectInfo.GetSSID());
        this.LOG.m385i("ConnectWifi ConnectDefaultSSID", "default pwd: " + this.m_WifiAutoConnectInfo.GetPassword());
        if (this.m_WifiAutoConnectInfo.GetSSID().length() != 0) {
            if (!bDirect) {
                this.m_ShowMSGDialog = CreateConnectPrinterHintDialog();
            }
            this.m_ConnectPrinter = new ConnectPrinter(this.mContext, this.m_WifiAutoConnectInfo.GetSSID(), this.m_WifiAutoConnectInfo.GetPassword());
            this.m_ConnectPrinter.execute(new Void[0]);
        } else if (this.mConnecting) {
            ShowNoWiFiDialog();
        }
    }
}
