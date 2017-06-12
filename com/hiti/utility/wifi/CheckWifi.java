package com.hiti.utility.wifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.os.EnvironmentCompat;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.trace.GlobalVariable_WifiAutoConnectInfo;
import com.hiti.utility.LogManager;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.Verify.ThreadMode;
import com.hiti.utility.dialog.DialogListener;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import com.hiti.utility.resource.ResourceId;
import com.hiti.utility.resource.ResourceId.Page;
import com.hiti.utility.wifi.ShowPrinterList.MDNS;
import com.hiti.utility.wifi.ShowPrinterList.Scan;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;

public class CheckWifi {
    LogManager LOG;
    String TAG;
    boolean mAutoWifi;
    Context mContext;
    String mIP;
    ICheckWifiListener mListener;
    IMdnsWifi mMdnsWifi;
    int mPort;
    PrintMode mPrintMode;
    ResourceId mRID;
    ShowMSGDialog m_ShowMSGDialog;
    ShowPrinterList m_ShowPrinterList;
    GlobalVariable_WifiAutoConnectInfo m_WifiInfo;
    boolean m_bOnMDNS;
    boolean m_bThreadStop;
    String m_strConnectedSSID;
    AutoWifiConnect m_wifiAutoConnect;

    /* renamed from: com.hiti.utility.wifi.CheckWifi.5 */
    static /* synthetic */ class C04855 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$utility$Verify$ThreadMode;

        static {
            $SwitchMap$com$hiti$utility$Verify$ThreadMode = new int[ThreadMode.values().length];
            try {
                $SwitchMap$com$hiti$utility$Verify$ThreadMode[ThreadMode.AutoWifi.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    public enum Connection {
        AutoWifi,
        Mdns,
        all
    }

    public interface ICheckWifiListener {
        Activity OpenWifi();

        void onAutoConntectCancel();

        void onAutoConntectionSuccess(String str);

        void onCheckWifiConnected(String str);

        void onCheckWifiStart();

        void onConnectWifiStart();

        void onMdnsBackFinish();

        void onMdnsBackStart();

        void onMdnsPrinterListFinish(String str, String str2, int i);

        void onMdnsScanState(Scan scan, String str, String str2, String str3, int i);

        void onNoWifiDialaogCancelClicked();

        void onSelectionSSIDCancelConnetion();

        void onSelectionSSIDSetLastConnSSID(String str);

        void onSelectionSSIDSetNowConnSSID(String str);
    }

    public interface IMdnsWifi {
        void wifiNotConnected();
    }

    /* renamed from: com.hiti.utility.wifi.CheckWifi.1 */
    class C08081 implements MSGListener {
        C08081() {
        }

        public void OKClick() {
            if (CheckWifi.this.HaveListener()) {
                CheckWifi.OpenWifi(CheckWifi.this.mListener.OpenWifi());
            }
        }

        public void CancelClick() {
            if (CheckWifi.this.HaveListener()) {
                CheckWifi.this.mListener.onNoWifiDialaogCancelClicked();
            }
        }

        public void Close() {
        }
    }

    /* renamed from: com.hiti.utility.wifi.CheckWifi.2 */
    class C08092 extends DialogListener {
        final /* synthetic */ String val$strLastPassword;

        C08092(String str) {
            this.val$strLastPassword = str;
        }

        public void SetNowConnSSID(String strNowSSID) {
            if (CheckWifi.this.HaveListener()) {
                CheckWifi.this.mListener.onSelectionSSIDSetNowConnSSID(strNowSSID);
            }
            if (!CheckWifi.this.m_bThreadStop) {
                CheckWifi.this.ShowPrinterListDialog();
            }
        }

        public void SetLastConnSSID(String strLastSSID) {
            if (CheckWifi.this.HaveListener()) {
                CheckWifi.this.mListener.onSelectionSSIDSetLastConnSSID(strLastSSID);
            }
            CheckWifi.this.m_wifiAutoConnect = new AutoWifiConnect(CheckWifi.this.mContext, strLastSSID, this.val$strLastPassword);
            CheckWifi.this.m_wifiAutoConnect.execute(new Void[0]);
        }

        public void LeaveConfirm() {
        }

        public void LeaveClose() {
        }

        public void LeaveCancel() {
        }

        public void CancelConnetion(ThreadMode strSSID) {
            if (CheckWifi.this.HaveListener()) {
                CheckWifi.this.mListener.onSelectionSSIDCancelConnetion();
            }
        }
    }

    /* renamed from: com.hiti.utility.wifi.CheckWifi.3 */
    class C08103 extends PrinterListListener {
        C08103() {
        }

        public void PrinterListFinish(String strPrinterSSID, String IP, int iPort, String strConn) {
            if (!CheckWifi.this.m_bThreadStop) {
                if (strConn == "AP" && iPort == 0) {
                    iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
                }
                if (CheckWifi.this.HaveListener()) {
                    CheckWifi.this.mListener.onMdnsPrinterListFinish(strPrinterSSID, IP, iPort);
                }
            }
        }

        public void IsMdnsState(boolean bMDNS) {
            CheckWifi.this.m_bOnMDNS = bMDNS;
        }

        public void BackFinish() {
            if (CheckWifi.this.HaveListener()) {
                CheckWifi.this.mListener.onMdnsBackFinish();
            }
            CheckWifi.this.m_bOnMDNS = false;
        }

        public void BackStart() {
            if (CheckWifi.this.HaveListener()) {
                CheckWifi.this.mListener.onMdnsBackStart();
            }
            if (CheckWifi.this.m_ShowPrinterList.IsShowing()) {
                CheckWifi.this.m_ShowPrinterList.ListClose();
            }
        }

        public void ScanState(Scan state, String strPID, String strSSID, String IP, int iPort) {
            CheckWifi.this.LOG.m385i(CheckWifi.this.TAG, "state: " + state);
            if (CheckWifi.this.HaveListener()) {
                CheckWifi.this.mListener.onMdnsScanState(state, strPID, strSSID, IP, iPort);
            }
        }
    }

    /* renamed from: com.hiti.utility.wifi.CheckWifi.4 */
    class C08114 extends DialogListener {
        C08114() {
        }

        public void SetNowConnSSID(String strNowSSID) {
        }

        public void SetLastConnSSID(String strLastSSID) {
        }

        public void LeaveConfirm() {
        }

        public void LeaveClose() {
        }

        public void LeaveCancel() {
        }

        public void CancelConnetion(ThreadMode mode) {
            CheckWifi.this.LOG.m385i(CheckWifi.this.TAG, "CancelConnetion mode: " + mode);
            CheckWifi.this.m_bThreadStop = true;
            switch (C04855.$SwitchMap$com$hiti$utility$Verify$ThreadMode[mode.ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    if (CheckWifi.this.m_wifiAutoConnect != null) {
                        CheckWifi.this.m_wifiAutoConnect.ConnectionStop();
                    }
                    if (CheckWifi.this.HaveListener()) {
                        CheckWifi.this.mListener.onAutoConntectCancel();
                    }
                default:
                    CheckWifi.this.m_bThreadStop = false;
            }
        }
    }

    class AutoWifiConnect extends WifiAutoConnect {
        public AutoWifiConnect(Context context, String strSSID, String strPassword) {
            super(context, strSSID, strPassword);
        }

        public void ConnectionSuccess() {
            ConnectionStop();
            if (!CheckWifi.this.m_bThreadStop) {
                if (CheckWifi.this.HaveListener()) {
                    CheckWifi.this.mListener.onAutoConntectionSuccess(CheckWifi.GetNowSSID(CheckWifi.this.mContext));
                }
                CheckWifi.this.ShowPrinterListDialog();
            }
        }

        public void ConnectionFail() {
            ConnectionStop();
            if (!CheckWifi.this.m_bThreadStop) {
                CheckWifi.this.ShowNoWiFiDialog();
            }
        }

        public void ConnectionStop() {
            CheckWifi.this.m_ShowMSGDialog.StopWaitingDialog();
            SetStop(true);
        }
    }

    public CheckWifi(Context context) {
        this.mContext = null;
        this.m_WifiInfo = null;
        this.m_ShowPrinterList = null;
        this.m_ShowMSGDialog = null;
        this.mPrintMode = PrintMode.NormalMain;
        this.mListener = null;
        this.m_wifiAutoConnect = null;
        this.m_bThreadStop = false;
        this.m_bOnMDNS = false;
        this.mRID = null;
        this.m_strConnectedSSID = null;
        this.mIP = null;
        this.mPort = 0;
        this.mAutoWifi = true;
        this.LOG = null;
        this.TAG = null;
        this.mMdnsWifi = null;
        this.mContext = context;
        this.LOG = new LogManager(0);
        this.TAG = this.mContext.getClass().getSimpleName();
        this.mRID = new ResourceId(context, Page.CheckWifi);
        this.m_ShowMSGDialog = new ShowMSGDialog(this.mContext, true);
    }

    public void SetListener(ICheckWifiListener listener) {
        this.mListener = listener;
    }

    private boolean HaveListener() {
        if (this.mListener != null) {
            return true;
        }
        return false;
    }

    public void SetPrintMode(PrintMode printMode) {
        this.mPrintMode = printMode;
    }

    public boolean Stop(Connection conn) {
        if (conn == Connection.AutoWifi || conn == Connection.all) {
            if (this.m_wifiAutoConnect == null) {
                return false;
            }
            this.m_wifiAutoConnect.SetStop(true);
            this.m_wifiAutoConnect = null;
            return true;
        } else if ((conn != Connection.Mdns && conn != Connection.all) || this.m_ShowPrinterList == null || !this.m_bOnMDNS) {
            return false;
        } else {
            this.m_ShowPrinterList.Stop();
            return true;
        }
    }

    public void CloseMdnsList() {
        if (this.m_ShowPrinterList != null && this.m_ShowPrinterList.IsShowing()) {
            this.m_ShowPrinterList.ListClose();
        }
    }

    public void SetConnectedPrinterSSID(String strNowPrinterSSID, String IP, int iPort) {
        this.m_strConnectedSSID = strNowPrinterSSID;
        this.mIP = IP;
        this.mPort = iPort;
    }

    public void seAutoWifi(boolean autoWifi, IMdnsWifi mdnsWifi) {
        this.mAutoWifi = autoWifi;
        this.mMdnsWifi = mdnsWifi;
    }

    public void Check() {
        NetworkInfo mWifi = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getNetworkInfo(1);
        String strCurrentSSID = GetNowSSID(this.mContext);
        this.m_WifiInfo = new GlobalVariable_WifiAutoConnectInfo(this.mContext);
        this.m_WifiInfo.RestoreGlobalVariable();
        String strLastSSID = this.m_WifiInfo.GetSSID();
        String strSecurityKey = this.m_WifiInfo.GetPassword();
        if (HaveListener()) {
            this.mListener.onCheckWifiStart();
        }
        strLastSSID = CleanSSID(strLastSSID);
        strCurrentSSID = CleanSSID(strCurrentSSID);
        if (mWifi.isConnected()) {
            this.LOG.m385i(this.TAG, "m_strConnectedSSID: " + this.m_strConnectedSSID);
            if (this.m_strConnectedSSID == null || !strCurrentSSID.contains(this.m_strConnectedSSID)) {
                if (strCurrentSSID.contains(strLastSSID)) {
                    this.LOG.m385i(this.TAG, "onCheckWifiConnected: " + strCurrentSSID);
                    if (HaveListener()) {
                        this.mListener.onCheckWifiConnected(strCurrentSSID);
                    }
                    if (this.mAutoWifi) {
                        ShowPrinterListDialog();
                    }
                } else {
                    this.LOG.m385i(this.TAG, "mPrintMode: " + this.mPrintMode);
                    if (this.mPrintMode == PrintMode.NFC) {
                        AutoConnectionWiFi(strLastSSID, strSecurityKey);
                    } else if (this.mAutoWifi) {
                        SelectionSSID(strCurrentSSID, strLastSSID, strSecurityKey);
                    } else if (this.mMdnsWifi != null) {
                        this.mMdnsWifi.wifiNotConnected();
                    }
                }
            } else if (HaveListener()) {
                this.mListener.onMdnsPrinterListFinish(this.m_strConnectedSSID, this.mIP, this.mPort);
            }
            this.LOG.m385i(this.TAG, "After isConnected");
        } else if (strLastSSID.length() == 0 || strLastSSID.contains(EnvironmentCompat.MEDIA_UNKNOWN)) {
            ShowNoWiFiDialog();
        } else {
            AutoConnectionWiFi(strLastSSID, strSecurityKey);
        }
    }

    public static String GetNowSSID(Context context) {
        String strSSID;
        WifiInfo wifiInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        if (wifiInfo.getSSID() == null) {
            strSSID = XmlPullParser.NO_NAMESPACE;
        } else {
            strSSID = wifiInfo.getSSID();
        }
        return CleanSSID(strSSID);
    }

    private static String CleanSSID(String strSSID) {
        if (strSSID.contains("\"")) {
            return strSSID.split("\"")[1];
        }
        return strSSID;
    }

    public void ShowNoWiFiDialog() {
        this.m_ShowMSGDialog.StopMSGDialog();
        this.m_ShowMSGDialog.SetMSGListener(new C08081());
        this.m_ShowMSGDialog.ShowMessageDialog(this.mContext.getString(this.mRID.R_STRING_PLEASE_SELECT_NETWORK), this.mContext.getString(this.mRID.R_STRING_UNABLE_TO_CONNECT_TO_PRINTER));
    }

    public static void OpenWifi(Activity activity) {
        activity.startActivityForResult(new Intent("android.settings.WIFI_SETTINGS"), 10);
    }

    private void SelectionSSID(String strCurrentSSID, String strLastSSID, String strLastPassword) {
        this.LOG.m386v("SelectionOfSSID", "SSID=" + strCurrentSSID);
        this.m_bThreadStop = false;
        this.m_ShowMSGDialog.SetDialogListener(new C08092(strLastPassword));
        this.m_ShowMSGDialog.CreateConnectWifiHintDialog(strCurrentSSID, strLastSSID);
    }

    private void AutoConnectionWiFi(String strSSID, String strPassword) {
        CreateWaitingDialog(ThreadMode.AutoWifi, this.mContext.getString(this.mRID.R_STRING_CONN_SEARCHING));
        if (HaveListener()) {
            this.mListener.onConnectWifiStart();
        }
        this.m_wifiAutoConnect = new AutoWifiConnect(this.mContext, strSSID, strPassword);
        this.m_wifiAutoConnect.execute(new Void[0]);
    }

    private void ShowPrinterListDialog() {
        if (this.m_ShowPrinterList == null) {
            this.m_ShowPrinterList = new ShowPrinterList(this.mContext, MDNS.List);
            this.m_ShowPrinterList.SetPrinterListListener(new C08103());
        }
        this.m_ShowPrinterList.Show();
    }

    private void CreateWaitingDialog(ThreadMode mode, String strMessage) {
        this.m_bThreadStop = false;
        this.m_ShowMSGDialog.SetDialogListener(new C08114());
        this.m_ShowMSGDialog.ShowWaitingHintDialog(mode, strMessage);
    }
}
