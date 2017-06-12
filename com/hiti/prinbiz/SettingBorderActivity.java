package com.hiti.prinbiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.jni.hello.Hello;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.request.HitiPPR_GetBorderFile;
import com.hiti.trace.GlobalVariable_BorderPath;
import com.hiti.trace.GlobalVariable_WifiAutoConnectInfo;
import com.hiti.utility.LogManager;
import com.hiti.utility.PrinterListListener;
import com.hiti.utility.ShowPrinterList;
import com.hiti.utility.UserInfo;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.wifi.WifiAutoConnect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.net.telnet.TelnetOption;
import org.apache.commons.net.tftp.TFTP;
import org.xmlpull.v1.XmlPullParser;

public class SettingBorderActivity extends Activity {
    private OnClickListener DelBorderListen;
    private OnClickListener ImportBorderListen;
    LogManager LOG;
    private OnClickListener TenClickForLoginListen;
    NFCInfo mNFCInfo;
    private ImageButton m_BackImageButton;
    private Button m_BorderDelButton;
    private Button m_BorderImportBtn;
    private View m_ConnectWifiDialogView;
    private AlertDialog m_ConnectWifiHintDialog;
    private Dialog m_GetBorderDialog;
    private HitiPPR_GetBorderFile m_GetBorderFile;
    private RelativeLayout m_PaTestLayout;
    private TextView m_PrintOutTextView;
    private Button m_ProgressOKButton;
    private RadioButton m_SelCurrentRadioButton;
    private RadioButton m_SelLastRadioButton;
    private ShowPrinterList m_ShowPrinterList;
    private TextView m_StatusTextView;
    private ProgressBar m_StepProgressBar;
    private Timer m_Timer;
    private TimeroutTask m_TimeroutTask;
    private TextView m_TitleTextView;
    private View m_WaitingDialogView;
    private AlertDialog m_WaitingHintDialog;
    private TextView m_WaitingMSGTextView;
    private ProgressBar m_WaitingProgressBar;
    private GlobalVariable_WifiAutoConnectInfo m_WifiInfo;
    private boolean m_bConnState;
    private GetBorderHandler m_handler;
    private int m_iTenClickForLogin;
    private int m_iTimeOut;
    GlobalVariable_BorderPath m_prefBorderPath;
    ArrayList<String> m_strBorderPathList;
    private String m_strCurrentSSID;
    private String m_strLastSSID;
    private String m_strSecurityKey;
    private AutoWifiConnect m_wifiAutoConnect;

    /* renamed from: com.hiti.prinbiz.SettingBorderActivity.2 */
    class C03572 implements OnClickListener {
        C03572() {
        }

        public void onClick(View v) {
            SettingBorderActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingBorderActivity.3 */
    class C03583 implements OnClickListener {
        C03583() {
        }

        public void onClick(View v) {
            SettingBorderActivity.this.PATestLogin();
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingBorderActivity.4 */
    class C03594 implements OnClickListener {
        C03594() {
        }

        public void onClick(View v) {
            SettingBorderActivity.this.m_handler = new GetBorderHandler();
            if (SettingBorderActivity.this.m_bConnState) {
                SettingBorderActivity.this.ShowPrinterListDialog();
            } else {
                SettingBorderActivity.this.CheckWifi();
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingBorderActivity.5 */
    class C03605 implements OnClickListener {
        C03605() {
        }

        public void onClick(View v) {
            SettingBorderActivity.this.startActivity(new Intent(SettingBorderActivity.this.getBaseContext(), BorderDelMainFragmentActivity.class));
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingBorderActivity.6 */
    class C03616 implements DialogInterface.OnClickListener {
        C03616() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingBorderActivity.7 */
    class C03627 implements DialogInterface.OnClickListener {
        C03627() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingBorderActivity.8 */
    class C03638 implements OnClickListener {
        C03638() {
        }

        public void onClick(View v) {
            SettingBorderActivity.this.m_GetBorderDialog.dismiss();
            SettingBorderActivity.this.m_ProgressOKButton.setVisibility(8);
            SettingBorderActivity.this.m_StatusTextView.setVisibility(0);
            SettingBorderActivity.this.m_BorderImportBtn.setEnabled(true);
            SettingBorderActivity.this.m_BorderImportBtn.setAlpha(1.0f);
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingBorderActivity.9 */
    class C03649 implements OnClickListener {
        C03649() {
        }

        public void onClick(View v) {
            SettingBorderActivity.this.m_ConnectWifiHintDialog.dismiss();
        }
    }

    private class TimeroutTask extends TimerTask {
        private TimeroutTask() {
        }

        public void run() {
            Log.e("RunTimeOut", "RunTimeOut");
            SettingBorderActivity.this.m_iTenClickForLogin = 0;
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingBorderActivity.1 */
    class C07871 implements INfcPreview {
        C07871() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            SettingBorderActivity.this.mNFCInfo = nfcInfo;
        }
    }

    class AutoWifiConnect extends WifiAutoConnect {
        public AutoWifiConnect(Context context, String strSSID, String strPassword) {
            super(context, strSSID, strPassword);
        }

        public void ConnectionSuccess() {
            ConnectionStop();
            SettingBorderActivity.this.m_bConnState = true;
            SettingBorderActivity.this.ShowPrinterListDialog();
        }

        public void ConnectionFail() {
            ConnectionStop();
            SettingBorderActivity.this.m_bConnState = false;
            SettingBorderActivity.this.ShowNoWiFiDialog();
        }

        public void ConnectionStop() {
            SettingBorderActivity.this.m_WaitingHintDialog.dismiss();
            SettingBorderActivity.this.m_WaitingHintDialog = null;
            SetStop(true);
        }
    }

    class GetBorderHandler extends MSGHandler {
        GetBorderHandler() {
        }

        public void handleMessage(Message msg) {
            SettingBorderActivity.this.LOG.m385i("GetBorderHandler", "Step: " + msg.what);
            switch (msg.what) {
                case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                    SettingBorderActivity.this.m_BorderImportBtn.setEnabled(true);
                    SettingBorderActivity.this.m_BorderImportBtn.setAlpha(1.0f);
                    SettingBorderActivity.this.ShowErrorDialog(msg.getData().getString(MSGHandler.MSG), SettingBorderActivity.this.getString(C0349R.string.ERROR), 17301569);
                case RequestState.REQUEST_GET_BORDER_FOLDER /*364*/:
                    SettingBorderActivity.this.ProgressStepDialog(XmlPullParser.NO_NAMESPACE, SettingBorderActivity.this.getString(C0349R.string.IMPORT_DONE), 100);
                    SettingBorderActivity.this.m_ProgressOKButton.setVisibility(0);
                    SettingBorderActivity.this.m_StatusTextView.setVisibility(8);
                    SettingBorderActivity.this.m_strBorderPathList = new ArrayList();
                    SettingBorderActivity.this.m_GetBorderFile.GetBorderPath(SettingBorderActivity.this.m_strBorderPathList);
                    SettingBorderActivity.this.SavePrefBorderPath(SettingBorderActivity.this.m_strBorderPathList);
                case RequestState.REQUEST_GET_BORDER_FOLDER_ERROR /*365*/:
                    SettingBorderActivity.this.m_BorderImportBtn.setEnabled(true);
                    SettingBorderActivity.this.m_BorderImportBtn.setAlpha(1.0f);
                    SettingBorderActivity.this.ShowErrorDialog(msg.getData().getString(MSGHandler.MSG), SettingBorderActivity.this.getString(C0349R.string.ERROR), 17301569);
                case RequestState.REQUEST_SEARCH_THUMB_PRINTOUT /*366*/:
                    SettingBorderActivity.this.ProgressStepDialog(msg.getData().getString(MSGHandler.MSG) + ": ", SettingBorderActivity.this.getString(C0349R.string.SEARCH_BORDER), 3);
                case RequestState.REQUEST_SEARCH_THUMB_BORDER /*367*/:
                    SettingBorderActivity.this.ProgressStepDialog(null, SettingBorderActivity.this.getString(C0349R.string.SEARCH_BORDER), Integer.parseInt(msg.getData().getString(MSGHandler.MSG)));
                case RequestState.REQUEST_COMPARE_BORDER_NAME /*368*/:
                    SettingBorderActivity.this.ProgressStepDialog(null, SettingBorderActivity.this.getString(C0349R.string.COMPARE_BORDER), (int) Float.parseFloat(msg.getData().getString(MSGHandler.MSG)));
                case RequestState.REQUEST_IMPORT_BORDER_FILE /*369*/:
                    SettingBorderActivity.this.ProgressStepDialog(null, SettingBorderActivity.this.getString(C0349R.string.IMPORT_BORDER), (int) Float.parseFloat(msg.getData().getString(MSGHandler.MSG)));
                default:
            }
        }
    }

    public SettingBorderActivity() {
        this.m_BorderDelButton = null;
        this.m_BorderImportBtn = null;
        this.m_BackImageButton = null;
        this.m_PaTestLayout = null;
        this.m_GetBorderFile = null;
        this.m_handler = null;
        this.m_WifiInfo = null;
        this.m_strLastSSID = null;
        this.m_strCurrentSSID = null;
        this.m_strSecurityKey = null;
        this.m_wifiAutoConnect = null;
        this.m_bConnState = false;
        this.m_ConnectWifiHintDialog = null;
        this.m_WaitingHintDialog = null;
        this.m_GetBorderDialog = null;
        this.m_ConnectWifiDialogView = null;
        this.m_WaitingDialogView = null;
        this.m_SelCurrentRadioButton = null;
        this.m_SelLastRadioButton = null;
        this.m_StepProgressBar = null;
        this.m_WaitingProgressBar = null;
        this.m_WaitingMSGTextView = null;
        this.m_StatusTextView = null;
        this.m_TitleTextView = null;
        this.m_PrintOutTextView = null;
        this.m_ProgressOKButton = null;
        this.m_strBorderPathList = null;
        this.m_prefBorderPath = null;
        this.m_ShowPrinterList = null;
        this.m_iTenClickForLogin = 0;
        this.m_Timer = null;
        this.m_TimeroutTask = null;
        this.m_iTimeOut = TFTP.DEFAULT_TIMEOUT;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TenClickForLoginListen = new C03583();
        this.ImportBorderListen = new C03594();
        this.DelBorderListen = new C03605();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_border_set);
        this.LOG = new LogManager(0);
        SetView();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mNFCInfo != null) {
            this.mNFCInfo.mPrintMode = PrintMode.NormalMain;
        }
        switch (requestCode) {
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                this.m_bConnState = true;
                CheckWifi();
            case TelnetOption.TERMINAL_TYPE /*24*/:
                finish();
            default:
        }
    }

    protected void onNewIntent(Intent intent) {
        this.LOG.m386v("onNewIntent ", XmlPullParser.NO_NAMESPACE + intent);
        setIntent(intent);
        if (this.mNFCInfo != null) {
            this.mNFCInfo.mPrintMode = PrintMode.NormalMain;
        }
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07871());
    }

    protected void onPause() {
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    private void SetView() {
        this.m_BorderImportBtn = (Button) findViewById(C0349R.id.m_ImportCollageButton);
        this.m_BorderDelButton = (Button) findViewById(C0349R.id.m_DelCollageButton);
        this.m_BackImageButton = (ImageButton) findViewById(C0349R.id.m_BackImageButton);
        this.m_PaTestLayout = (RelativeLayout) findViewById(C0349R.id.m_PaTestLayout);
        SetOnClickListen();
    }

    private void SetOnClickListen() {
        this.m_PaTestLayout.setOnClickListener(this.TenClickForLoginListen);
        this.m_BorderImportBtn.setOnClickListener(this.ImportBorderListen);
        this.m_BorderDelButton.setOnClickListener(this.DelBorderListen);
        this.m_BackImageButton.setOnClickListener(new C03572());
    }

    private void PATestLogin() {
        if (this.m_iTenClickForLogin == 0) {
            StartTimeOut();
        }
        this.m_iTenClickForLogin++;
        Log.e("num", "-" + String.valueOf(this.m_iTenClickForLogin));
        if (this.m_iTenClickForLogin >= 10) {
            this.m_iTenClickForLogin = 0;
            StopTimeOut();
            if (!UserInfo.GetUser(this).equals(Hello.SayHello(this, 10014))) {
                startActivityForResult(new Intent(this, FirmwareTestLogin.class), 24);
            }
        }
    }

    private void StartTimeOut() {
        StopTimeOut();
        if (this.m_Timer == null) {
            Log.e("StartTimeOut", "StartTimeOut");
            this.m_Timer = new Timer(true);
            this.m_TimeroutTask = new TimeroutTask();
            this.m_Timer.schedule(this.m_TimeroutTask, (long) this.m_iTimeOut);
        }
    }

    private void StopTimeOut() {
        Log.e("StopTimeOut", "StopTimeOut");
        if (this.m_Timer != null) {
            this.m_Timer.cancel();
            this.m_Timer = null;
        }
        if (this.m_TimeroutTask != null) {
            this.m_TimeroutTask.cancel();
            this.m_TimeroutTask = null;
        }
    }

    private void CheckBorderFile(String IP, int iPort) {
        this.m_BorderImportBtn.setEnabled(false);
        this.m_BorderImportBtn.setAlpha(0.6f);
        ProgressStepDialog("4x6: ", getString(C0349R.string.SEARCH_BORDER), 0);
        this.m_StepProgressBar.setProgress(0);
        this.LOG.m385i("CheckBorderFile", "IP: " + IP);
        this.LOG.m385i("CheckBorderFile", "iPort: " + iPort);
        this.m_GetBorderFile = new HitiPPR_GetBorderFile(this, IP, iPort, this.m_handler);
        this.m_GetBorderFile.start();
    }

    private void SavePrefBorderPath(ArrayList<String> arrayList) {
        ArrayList<String> strOldPathList = new ArrayList();
        this.m_prefBorderPath = new GlobalVariable_BorderPath(this);
        this.m_prefBorderPath.RestoreGlobalVariable();
        if (!this.m_prefBorderPath.IsNoData()) {
            this.m_prefBorderPath.GetBorderPathList(strOldPathList);
        }
        Iterator it = strOldPathList.iterator();
        while (it.hasNext()) {
            String strOldPath = (String) it.next();
            if (!this.m_strBorderPathList.contains(strOldPath)) {
                this.m_strBorderPathList.add(strOldPath);
            }
        }
        this.m_prefBorderPath.ClearGlobalVariable();
        this.m_prefBorderPath.ClearRestorePrefBorderPath();
        this.m_prefBorderPath.SetBorderPathList(this.m_strBorderPathList);
        this.m_prefBorderPath.SaveGlobalVariable();
    }

    public void ShowErrorDialog(String strMessage, String strTitle, int iResId) {
        Builder errorDialog = new Builder(this);
        errorDialog.setCancelable(false);
        errorDialog.setIcon(iResId);
        errorDialog.setTitle(strTitle);
        errorDialog.setMessage(strMessage);
        errorDialog.setPositiveButton(getString(C0349R.string.OK), new C03616());
        errorDialog.show();
        this.m_GetBorderDialog.dismiss();
    }

    public void ShowAlertDialog(String strMessage, String strTitle, int iResId) {
        Builder alertDialog = new Builder(this);
        alertDialog.setIcon(iResId);
        alertDialog.setTitle(strTitle);
        alertDialog.setMessage(strMessage);
        alertDialog.setPositiveButton(getString(C0349R.string.OK), new C03627());
        alertDialog.show();
    }

    private String GetNowSSID() {
        WifiInfo wifiInfo = ((WifiManager) getSystemService("wifi")).getConnectionInfo();
        if (wifiInfo.getSSID() == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        return wifiInfo.getSSID();
    }

    private void CheckWifi() {
        NetworkInfo mWifi = ((ConnectivityManager) getSystemService("connectivity")).getNetworkInfo(1);
        this.m_WifiInfo = new GlobalVariable_WifiAutoConnectInfo(this);
        this.m_WifiInfo.RestoreGlobalVariable();
        this.m_strLastSSID = this.m_WifiInfo.GetSSID();
        this.m_strSecurityKey = this.m_WifiInfo.GetPassword();
        this.m_strCurrentSSID = GetNowSSID();
        if (mWifi.isConnected()) {
            if (this.m_strCurrentSSID.contains(this.m_strLastSSID)) {
                this.m_bConnState = true;
                ShowPrinterListDialog();
                return;
            }
            CreateConnectWifiHintDialog(this.m_strCurrentSSID, this.m_strLastSSID);
        } else if (this.m_strLastSSID.length() != 0) {
            CreateWaitingHintDialog(getString(C0349R.string.CONN_SEARCHING));
            this.m_wifiAutoConnect = new AutoWifiConnect(getBaseContext(), this.m_strLastSSID, this.m_strSecurityKey);
            this.m_wifiAutoConnect.execute(new Void[0]);
        } else {
            ShowNoWiFiDialog();
        }
    }

    private void ProgressStepDialog(String strPrintOut, String strMSG, int iProgress) {
        if (this.m_GetBorderDialog == null) {
            this.m_GetBorderDialog = new Dialog(this, C0349R.style.Dialog_MSG);
            this.m_GetBorderDialog.setContentView(C0349R.layout.dialog_progressbar);
            this.m_GetBorderDialog.setCancelable(false);
            this.m_GetBorderDialog.setCanceledOnTouchOutside(false);
            this.m_TitleTextView = (TextView) this.m_GetBorderDialog.findViewById(C0349R.id.m_TitleTextView);
            this.m_StatusTextView = (TextView) this.m_GetBorderDialog.findViewById(C0349R.id.m_StausTextView);
            this.m_PrintOutTextView = (TextView) this.m_GetBorderDialog.findViewById(C0349R.id.m_PreintOutTextView);
            this.m_StepProgressBar = (ProgressBar) this.m_GetBorderDialog.findViewById(C0349R.id.m_ProgressStepProgressBar);
            this.m_StepProgressBar.setMax(100);
            this.m_ProgressOKButton = (Button) this.m_GetBorderDialog.findViewById(C0349R.id.m_OKButton);
            this.m_ProgressOKButton.setOnClickListener(new C03638());
        }
        if (strMSG != null) {
            if (strMSG.equals(getString(C0349R.string.IMPORT_DONE))) {
                this.m_TitleTextView.setText(strMSG);
            } else {
                this.m_StatusTextView.setText(strMSG);
                this.m_TitleTextView.setText(getString(C0349R.string.BORDER_TITLE));
            }
        }
        if (strPrintOut != null) {
            this.m_PrintOutTextView.setText(strPrintOut);
        }
        if (iProgress >= 0) {
            this.m_StepProgressBar.setProgress(iProgress);
        }
        this.m_GetBorderDialog.show();
    }

    private void CreateWaitingHintDialog(String strMSG) {
        if (this.m_WaitingHintDialog == null) {
            this.m_WaitingHintDialog = new Builder(this).create();
            this.m_WaitingHintDialog.requestWindowFeature(1);
            this.m_WaitingHintDialog.setCanceledOnTouchOutside(false);
            this.m_WaitingHintDialog.setCancelable(false);
            this.m_WaitingDialogView = getLayoutInflater().inflate(C0349R.layout.dialog_waiting, null);
            this.m_WaitingProgressBar = (ProgressBar) this.m_WaitingDialogView.findViewById(C0349R.id.m_WaitingProgressBar);
            this.m_WaitingProgressBar.setVisibility(0);
            this.m_WaitingProgressBar.setIndeterminate(true);
            this.m_WaitingMSGTextView = (TextView) this.m_WaitingDialogView.findViewById(C0349R.id.m_WaitingMSGTextView);
            this.m_WaitingMSGTextView.setText("\n" + strMSG + "\n");
            this.m_WaitingHintDialog.setView(this.m_WaitingDialogView);
        }
        this.m_WaitingHintDialog.show();
    }

    void CreateConnectWifiHintDialog(String strCurrentSSID, String strLastSSID) {
        this.m_handler.SetStop(true);
        if (this.m_ConnectWifiHintDialog == null) {
            this.m_ConnectWifiHintDialog = new Builder(this).create();
            this.m_ConnectWifiHintDialog.setCanceledOnTouchOutside(false);
            this.m_ConnectWifiHintDialog.setCancelable(false);
            this.m_ConnectWifiDialogView = getLayoutInflater().inflate(C0349R.layout.dialog_sel_conn_wifi, null);
            this.m_SelCurrentRadioButton = (RadioButton) this.m_ConnectWifiDialogView.findViewById(C0349R.id.m_SelCurrentRadioButton);
            this.m_SelLastRadioButton = (RadioButton) this.m_ConnectWifiDialogView.findViewById(C0349R.id.m_SelLastRadioButton);
            ImageButton m_OKButton = (ImageButton) this.m_ConnectWifiDialogView.findViewById(C0349R.id.m_SelConnOKButton);
            ((ImageButton) this.m_ConnectWifiDialogView.findViewById(C0349R.id.m_SelConnCancelButton)).setOnClickListener(new C03649());
            m_OKButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (SettingBorderActivity.this.m_SelCurrentRadioButton.isChecked()) {
                        SettingBorderActivity.this.m_bConnState = true;
                        SettingBorderActivity.this.ShowPrinterListDialog();
                    }
                    if (SettingBorderActivity.this.m_SelLastRadioButton.isChecked()) {
                        SettingBorderActivity.this.CreateWaitingHintDialog(SettingBorderActivity.this.getString(C0349R.string.CONN_SEARCHING));
                        SettingBorderActivity.this.m_wifiAutoConnect = new AutoWifiConnect(SettingBorderActivity.this.getBaseContext(), SettingBorderActivity.this.m_strLastSSID, SettingBorderActivity.this.m_strSecurityKey);
                        SettingBorderActivity.this.m_wifiAutoConnect.execute(new Void[0]);
                    }
                    SettingBorderActivity.this.m_handler.SetStop(false);
                    SettingBorderActivity.this.m_ConnectWifiHintDialog.dismiss();
                }
            });
            this.m_ConnectWifiHintDialog.setView(this.m_ConnectWifiDialogView);
        }
        this.m_SelCurrentRadioButton.setText(getString(C0349R.string.DIALOG_CONN_WIFI_SEL_CURRENT) + strCurrentSSID);
        this.m_SelLastRadioButton.setText(getString(C0349R.string.DIALOG_CONN_WIFI_SEL_LAST) + strLastSSID);
        this.m_ConnectWifiHintDialog.show();
    }

    void OpenWifi() {
        startActivityForResult(new Intent("android.settings.WIFI_SETTINGS"), 10);
    }

    public void ShowNoWiFiDialog() {
        if (this.m_handler != null) {
            this.m_handler.SetStop(true);
        }
        Builder alertDialog = new Builder(this);
        alertDialog.setTitle(getString(C0349R.string.UNABLE_TO_CONNECT_TO_PRINTER));
        alertDialog.setMessage(getString(C0349R.string.PLEASE_SELECT_NETWORK));
        alertDialog.setPositiveButton(getString(C0349R.string.OK), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (SettingBorderActivity.this.m_handler != null) {
                    SettingBorderActivity.this.m_handler.SetStop(false);
                }
                SettingBorderActivity.this.OpenWifi();
            }
        });
        alertDialog.setNegativeButton(getString(C0349R.string.CANCEL), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SettingBorderActivity.this.onBackPressed();
            }
        });
        alertDialog.show();
    }

    public void onBackPressed() {
        if (this.m_handler != null) {
            this.m_handler.SetStop(true);
        }
        if (this.m_GetBorderFile != null) {
            this.m_GetBorderFile.Stop();
        }
        super.onBackPressed();
    }

    private void ShowPrinterListDialog() {
        if (this.m_ShowPrinterList == null) {
            this.m_ShowPrinterList = new ShowPrinterList(this);
            this.m_ShowPrinterList.SetPrinterListListener(new PrinterListListener() {
                public void PrinterListFinish(String strPrinterSSID, String IP, int iPort, String strConn) {
                    SettingBorderActivity.this.CheckBorderFile(IP, iPort);
                }

                public void IsBackStateOnMDNS(boolean bMDNS) {
                }

                public void BackFinish() {
                }

                public void BackStart() {
                }
            });
        }
        this.m_ShowPrinterList.Show();
    }
}
