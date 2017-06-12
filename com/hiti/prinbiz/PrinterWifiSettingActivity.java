package com.hiti.prinbiz;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NFCInfo.NFCMode;
import com.hiti.nfc.utility.NFCWrite;
import com.hiti.nfc.utility.NFCWrite.CTextWatcher;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.request.HitiPPR_GetWifiSetting;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.printerprotocol.request.HitiPPR_SetWifiSetting;
import com.hiti.trace.GlobalVariable_WifiAutoConnectInfo;
import com.hiti.utility.LogManager;
import com.hiti.utility.Verify.ThreadMode;
import com.hiti.utility.dialog.DialogListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;

public class PrinterWifiSettingActivity extends Activity {
    private static final int AP_SETTING_CHANGE = 18;
    private String IP;
    LogManager LOG;
    NFCInfo mNFCInfo;
    NFCListener mNFCListener;
    NFCWrite mNFCWritePrinter;
    NFCWrite mNFCWriteTag;
    ImageButton m_BackImageButton;
    Button m_ConfirmButton;
    ImageView m_DividerLine;
    private HitiPPR_GetWifiSetting m_GetWifiSetting;
    ImageButton m_NFCInfoButton;
    EditText m_NFCPwdEditText;
    EditText m_NFCSSIDEditText;
    LinearLayout m_NFCTagLayout;
    LinearLayout m_NFCWifiLayout;
    Button m_NFCWriteButton;
    private String m_PrinterPassword;
    EditText m_PwdEditText;
    EditText m_SSIDEditText;
    private HitiPPR_SetWifiSetting m_SetWifiSetting;
    View m_SettingDialogView;
    TextView m_SettingMSGTextView;
    ProgressBar m_SettingProgressBar;
    ShowMSGDialog m_ShowMSGDialog;
    private GlobalVariable_WifiAutoConnectInfo m_WifiAutoSet;
    private WifiSettingHandler m_WifiSettingHandler;
    private int m_iPort;
    private String m_strPrinterSSID;
    private String m_strProductIDString;
    private boolean mbSSIDchanged;

    /* renamed from: com.hiti.prinbiz.PrinterWifiSettingActivity.2 */
    class C03402 implements OnClickListener {
        C03402() {
        }

        public void onClick(View v) {
            PrinterWifiSettingActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.PrinterWifiSettingActivity.3 */
    class C03413 implements OnClickListener {
        C03413() {
        }

        public void onClick(View v) {
            NFCInfo.ChangeSSID(NFCMode.NFCWritePrinter, PrinterWifiSettingActivity.this.mNFCListener, PrinterWifiSettingActivity.this.m_SSIDEditText.getText().toString(), PrinterWifiSettingActivity.this.m_PwdEditText.getText().toString());
        }
    }

    /* renamed from: com.hiti.prinbiz.PrinterWifiSettingActivity.4 */
    class C03424 implements OnClickListener {
        C03424() {
        }

        public void onClick(View v) {
            NFCInfo.ChangeSSID(NFCMode.NFCWriteTag, PrinterWifiSettingActivity.this.mNFCListener, PrinterWifiSettingActivity.this.m_NFCSSIDEditText.getText().toString(), PrinterWifiSettingActivity.this.m_NFCPwdEditText.getText().toString());
        }
    }

    /* renamed from: com.hiti.prinbiz.PrinterWifiSettingActivity.5 */
    class C03435 implements OnClickListener {
        C03435() {
        }

        public void onClick(View v) {
            PrinterWifiSettingActivity.this.m_ShowMSGDialog.ShowSimpleMSGDialog(PrinterWifiSettingActivity.this.getString(C0349R.string.NFC_READ_INFO), PrinterWifiSettingActivity.this.getString(C0349R.string.NFC_APPLY_BUTTON));
        }
    }

    /* renamed from: com.hiti.prinbiz.PrinterWifiSettingActivity.6 */
    class C03446 implements DialogInterface.OnClickListener {
        C03446() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            if (PrinterWifiSettingActivity.this.mbSSIDchanged) {
                PrinterWifiSettingActivity.this.onBackPressed();
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.PrinterWifiSettingActivity.8 */
    class C03458 implements DialogInterface.OnClickListener {
        C03458() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            PrinterWifiSettingActivity.this.finish();
        }
    }

    /* renamed from: com.hiti.prinbiz.PrinterWifiSettingActivity.9 */
    static /* synthetic */ class C03469 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$utility$Verify$ThreadMode;

        static {
            $SwitchMap$com$hiti$utility$Verify$ThreadMode = new int[ThreadMode.values().length];
            try {
                $SwitchMap$com$hiti$utility$Verify$ThreadMode[ThreadMode.AutoWifi.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.PrinterWifiSettingActivity.1 */
    class C07781 implements INfcPreview {
        C07781() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            PrinterWifiSettingActivity.this.mNFCInfo = nfcInfo;
        }
    }

    /* renamed from: com.hiti.prinbiz.PrinterWifiSettingActivity.7 */
    class C07797 extends DialogListener {
        C07797() {
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
            PrinterWifiSettingActivity.this.mNFCListener.SetWriteStaus(NFCMode.NFCRead);
            switch (C03469.$SwitchMap$com$hiti$utility$Verify$ThreadMode[mode.ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    if (PrinterWifiSettingActivity.this.m_SetWifiSetting != null) {
                        PrinterWifiSettingActivity.this.m_SetWifiSetting.Stop();
                    }
                    PrinterWifiSettingActivity.this.m_BackImageButton.setEnabled(true);
                default:
            }
        }
    }

    class WifiSettingHandler extends MSGHandler {
        WifiSettingHandler() {
        }

        public void handleMessage(Message msg) {
            Bundle buddle;
            String strTitle;
            String strMSG;
            switch (msg.what) {
                case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                    PrinterWifiSettingActivity.this.m_ShowMSGDialog.StopWaitingDialog();
                    PrinterWifiSettingActivity.this.m_BackImageButton.setEnabled(true);
                    buddle = msg.getData();
                    strTitle = PrinterWifiSettingActivity.this.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    PrinterWifiSettingActivity.this.ShowAlertDialog(strMSG, strTitle, 17301569);
                case RequestState.REQUEST_GET_WIFI_SETTING /*325*/:
                    PrinterWifiSettingActivity.this.m_ShowMSGDialog.StopWaitingDialog();
                    PrinterWifiSettingActivity.this.m_BackImageButton.setEnabled(true);
                    PrinterWifiSettingActivity.this.m_strPrinterSSID = PrinterWifiSettingActivity.this.m_GetWifiSetting.GetAttrSSID();
                    PrinterWifiSettingActivity.this.m_PrinterPassword = PrinterWifiSettingActivity.this.m_GetWifiSetting.GetAttrSecurityKey();
                    PrinterWifiSettingActivity.this.m_SSIDEditText.setText(PrinterWifiSettingActivity.this.m_strPrinterSSID == null ? XmlPullParser.NO_NAMESPACE : PrinterWifiSettingActivity.this.m_strPrinterSSID);
                    PrinterWifiSettingActivity.this.m_PwdEditText.setText(PrinterWifiSettingActivity.this.m_PrinterPassword == null ? XmlPullParser.NO_NAMESPACE : PrinterWifiSettingActivity.this.m_PrinterPassword);
                case RequestState.REQUEST_GET_WIFI_SETTING_ERROR /*326*/:
                    PrinterWifiSettingActivity.this.m_ShowMSGDialog.StopWaitingDialog();
                    PrinterWifiSettingActivity.this.m_BackImageButton.setEnabled(true);
                    buddle = msg.getData();
                    strTitle = PrinterWifiSettingActivity.this.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    PrinterWifiSettingActivity.this.ShowErrorDialog(strMSG, strTitle, 17301569);
                case RequestState.REQUEST_SET_WIFI_SETTING /*327*/:
                    PrinterWifiSettingActivity.this.m_ShowMSGDialog.StopWaitingDialog();
                    PrinterWifiSettingActivity.this.m_BackImageButton.setEnabled(true);
                    strTitle = PrinterWifiSettingActivity.this.getString(C0349R.string.COMPLETE);
                    strMSG = PrinterWifiSettingActivity.this.getString(C0349R.string.SET_SSID_AND_PASSWORD_SUCCESS);
                    if (PrinterWifiSettingActivity.this.mNFCInfo.mNfcAdapter != null) {
                        strMSG = strMSG + "\n" + PrinterWifiSettingActivity.this.getString(C0349R.string.NFC_SET_SSID_AND_PASSWORD_MSG);
                    }
                    PrinterWifiSettingActivity.this.SetDefaultWifiInfo(PrinterWifiSettingActivity.this.m_SetWifiSetting.GetSSID(), PrinterWifiSettingActivity.this.m_SetWifiSetting.GetPassword());
                    PrinterWifiSettingActivity.this.mNFCListener.ShowNFCWriteDialog(strMSG, strTitle, NFCMode.NFCWritePrinter);
                case RequestState.REQUEST_SET_WIFI_SETTING_ERROR /*328*/:
                    PrinterWifiSettingActivity.this.m_ShowMSGDialog.StopWaitingDialog();
                    PrinterWifiSettingActivity.this.m_BackImageButton.setEnabled(true);
                    PrinterWifiSettingActivity.this.mNFCListener.SetWriteStaus(NFCMode.NFCRead);
                    buddle = msg.getData();
                    strTitle = PrinterWifiSettingActivity.this.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    PrinterWifiSettingActivity.this.ShowErrorDialog(strMSG, strTitle, 17301569);
                    Toast.makeText(PrinterWifiSettingActivity.this.getBaseContext(), PrinterWifiSettingActivity.this.getString(C0349R.string.SET_SSID_AND_PASSWORD_FAIL), 0).show();
                default:
                    PrinterWifiSettingActivity.this.m_ShowMSGDialog.StopWaitingDialog();
            }
        }
    }

    class NFCListener extends NfcListener {

        /* renamed from: com.hiti.prinbiz.PrinterWifiSettingActivity.NFCListener.1 */
        class C03471 implements DialogInterface.OnClickListener {
            final /* synthetic */ NFCMode val$nfcMode;

            C03471(NFCMode nFCMode) {
                this.val$nfcMode = nFCMode;
            }

            public void onClick(DialogInterface dialog, int which) {
                if (PrinterWifiSettingActivity.this.mNFCInfo.mNfcAdapter != null) {
                    NFCListener.this.SetWriteStaus(this.val$nfcMode);
                }
                dialog.dismiss();
            }
        }

        /* renamed from: com.hiti.prinbiz.PrinterWifiSettingActivity.NFCListener.2 */
        class C03482 implements DialogInterface.OnClickListener {
            C03482() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }

        NFCListener() {
        }

        void SetWriteStaus(NFCMode mNfcMode) {
            PrinterWifiSettingActivity.this.mNFCInfo.mNFCTag = mNfcMode;
            if (mNfcMode == NFCMode.NFCWritePrinter) {
                RunNFCWrite(PrinterWifiSettingActivity.this.mNFCWritePrinter, PrinterWifiSettingActivity.this.m_SSIDEditText.getText().toString(), PrinterWifiSettingActivity.this.m_PwdEditText.getText().toString());
            } else if (mNfcMode == NFCMode.NFCWriteTag) {
                RunNFCWrite(PrinterWifiSettingActivity.this.mNFCWriteTag, PrinterWifiSettingActivity.this.m_NFCSSIDEditText.getText().toString(), PrinterWifiSettingActivity.this.m_NFCPwdEditText.getText().toString());
            }
        }

        void RunNFCWrite(NFCWrite mNFCWriter, String strSSID, String strPassword) {
            if (strPassword == XmlPullParser.NO_NAMESPACE) {
                strPassword = null;
            }
            mNFCWriter.SetWriteInfo(strSSID, strPassword, NFCInfo.GetPrinterModelForNFC(PrinterWifiSettingActivity.this.m_strProductIDString));
            mNFCWriter.ShowWriteDialog();
            mNFCWriter.WriteTagProcess(PrinterWifiSettingActivity.this.mNFCInfo);
        }

        public void ShowNFCWriteDialog(String strMessage, String strTitle, NFCMode nfcMode) {
            Builder alertDialog = new Builder(PrinterWifiSettingActivity.this);
            if (strTitle != null) {
                alertDialog.setIcon(17301569);
                alertDialog.setTitle(strTitle);
            }
            alertDialog.setMessage(strMessage);
            alertDialog.setPositiveButton(PrinterWifiSettingActivity.this.getString(C0349R.string.OK), new C03471(nfcMode));
            if (PrinterWifiSettingActivity.this.mNFCInfo.mNfcAdapter != null) {
                alertDialog.setNegativeButton(PrinterWifiSettingActivity.this.getString(C0349R.string.CANCEL), new C03482());
            }
            alertDialog.show();
        }

        public void SSIDEmpty() {
            PrinterWifiSettingActivity.this.ShowErrorDialog(PrinterWifiSettingActivity.this.getString(C0349R.string.PLEASE_INPUT_PRINTER_WIFI_SSID), XmlPullParser.NO_NAMESPACE, 17301569);
        }

        public void PasswordRuleError() {
            PrinterWifiSettingActivity.this.ShowErrorDialog(PrinterWifiSettingActivity.this.getString(C0349R.string.PASSWORD_SETTING_ALERT_MSG), XmlPullParser.NO_NAMESPACE, 17301569);
        }

        public void SetSSID(NFCMode mNFCMode) {
            if (mNFCMode == NFCMode.NFCWritePrinter) {
                if (PrinterWifiSettingActivity.this.mbSSIDchanged) {
                    PrinterWifiSettingActivity.this.ShowErrorDialog(PrinterWifiSettingActivity.this.getString(C0349R.string.WiFiChanged), XmlPullParser.NO_NAMESPACE, 17301569);
                    return;
                }
                PrinterWifiSettingActivity.this.CreateSettingHintDialog();
                PrinterWifiSettingActivity.this.m_BackImageButton.setEnabled(false);
                PrinterWifiSettingActivity.this.m_SetWifiSetting = new HitiPPR_SetWifiSetting(PrinterWifiSettingActivity.this.getBaseContext(), PrinterWifiSettingActivity.this.IP, PrinterWifiSettingActivity.this.m_iPort, PrinterWifiSettingActivity.this.m_WifiSettingHandler);
                PrinterWifiSettingActivity.this.m_SetWifiSetting.SetSSID(PrinterWifiSettingActivity.this.m_SSIDEditText.getText().toString());
                PrinterWifiSettingActivity.this.m_SetWifiSetting.SetPassword(PrinterWifiSettingActivity.this.m_PwdEditText.getText().toString());
                PrinterWifiSettingActivity.this.m_SetWifiSetting.start();
            } else if (mNFCMode == NFCMode.NFCWriteTag) {
                ShowNFCWriteDialog(PrinterWifiSettingActivity.this.getString(C0349R.string.NFC_WRITE_DIALOG), PrinterWifiSettingActivity.this.getString(C0349R.string.HINT), NFCMode.NFCWriteTag);
            }
        }

        public void WriteDialogClose() {
            SetWriteStaus(NFCMode.NFCRead);
        }

        public void ReadNFCSuccess(Context context, NFCInfo mNFCInfo) {
            PrinterWifiSettingActivity.this.m_NFCSSIDEditText.setText(mNFCInfo.mSSID == null ? XmlPullParser.NO_NAMESPACE : mNFCInfo.mSSID);
        }
    }

    public PrinterWifiSettingActivity() {
        this.m_SSIDEditText = null;
        this.m_PwdEditText = null;
        this.m_NFCSSIDEditText = null;
        this.m_NFCPwdEditText = null;
        this.m_ConfirmButton = null;
        this.m_NFCWriteButton = null;
        this.m_BackImageButton = null;
        this.m_NFCInfoButton = null;
        this.m_DividerLine = null;
        this.m_NFCTagLayout = null;
        this.m_NFCWifiLayout = null;
        this.m_SettingDialogView = null;
        this.m_SettingProgressBar = null;
        this.m_SettingMSGTextView = null;
        this.m_ShowMSGDialog = null;
        this.mbSSIDchanged = false;
        this.m_WifiSettingHandler = null;
        this.m_GetWifiSetting = null;
        this.m_SetWifiSetting = null;
        this.m_WifiAutoSet = null;
        this.m_strPrinterSSID = XmlPullParser.NO_NAMESPACE;
        this.m_PrinterPassword = XmlPullParser.NO_NAMESPACE;
        this.IP = XmlPullParser.NO_NAMESPACE;
        this.m_iPort = 0;
        this.m_strProductIDString = null;
        this.mNFCInfo = null;
        this.mNFCWritePrinter = null;
        this.mNFCWriteTag = null;
        this.mNFCListener = null;
        this.LOG = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_wifisetting);
        this.LOG = new LogManager(0);
        GetBundle();
        SetView();
        this.mNFCInfo = NFCInfo.NewNFCInfo(this.mNFCInfo);
        CreateSettingHintDialog();
        this.m_BackImageButton.setEnabled(false);
        this.m_WifiSettingHandler = new WifiSettingHandler();
        this.m_GetWifiSetting = new HitiPPR_GetWifiSetting(this, this.IP, this.m_iPort, this.m_WifiSettingHandler);
        this.m_GetWifiSetting.start();
    }

    protected void onNewIntent(Intent intent) {
        this.LOG.m386v("onNewIntent ", XmlPullParser.NO_NAMESPACE + intent);
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        if (this.mNFCInfo.mNFCTag != NFCMode.NFCRead) {
            this.mNFCInfo = NFCInfo.WriteNFC(this.mNFCInfo, this);
            this.mNFCListener.SetWriteStaus(this.mNFCInfo.mNFCTag);
        } else {
            NFCInfo.CheckNFC(this.mNFCListener, (Activity) this, new C07781());
        }
        if (this.mNFCInfo.mNfcAdapter == null) {
            this.m_NFCTagLayout.setVisibility(4);
            this.m_NFCWifiLayout.setVisibility(4);
            this.m_DividerLine.setVisibility(8);
            this.m_NFCWriteButton.setVisibility(8);
        }
    }

    protected void onPause() {
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    private void GetBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.IP = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP);
            this.m_iPort = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT);
            this.m_strProductIDString = bundle.getString(JumpBundleMessage.BUNDLE_MSG_PRINTER_PRODUCT_ID);
            return;
        }
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
    }

    private void SetView() {
        this.m_SSIDEditText = (EditText) findViewById(C0349R.id.m_SSIDEditText);
        this.m_PwdEditText = (EditText) findViewById(C0349R.id.m_PwdEditText);
        this.m_NFCSSIDEditText = (EditText) findViewById(C0349R.id.m_NFCSSIDEditText);
        this.m_NFCPwdEditText = (EditText) findViewById(C0349R.id.m_NFCPwdEditText);
        this.m_ConfirmButton = (Button) findViewById(C0349R.id.m_SetComfirmButton);
        this.m_NFCWriteButton = (Button) findViewById(C0349R.id.m_NFCWriteButton);
        this.m_NFCInfoButton = (ImageButton) findViewById(C0349R.id.m_NFCInfoButton);
        this.m_DividerLine = (ImageView) findViewById(C0349R.id.m_DividerLine);
        this.m_NFCTagLayout = (LinearLayout) findViewById(C0349R.id.m_NFCTagLayout);
        this.m_NFCWifiLayout = (LinearLayout) findViewById(C0349R.id.m_NFCWifiLayout);
        this.m_NFCPwdEditText.setEnabled(false);
        this.m_BackImageButton = (ImageButton) findViewById(C0349R.id.m_BackImageButton);
        this.m_BackImageButton.setOnClickListener(new C03402());
        this.m_ConfirmButton.setOnClickListener(new C03413());
        this.m_NFCWriteButton.setOnClickListener(new C03424());
        this.m_NFCInfoButton.setOnClickListener(new C03435());
        this.mNFCListener = new NFCListener();
        this.mNFCWritePrinter = new NFCWrite(this, this.mNFCListener);
        this.mNFCWriteTag = new NFCWrite(this, this.mNFCListener);
        EditText editText = this.m_SSIDEditText;
        NFCWrite nFCWrite = this.mNFCWritePrinter;
        nFCWrite.getClass();
        editText.addTextChangedListener(new CTextWatcher(this.m_PwdEditText));
        editText = this.m_NFCSSIDEditText;
        nFCWrite = this.mNFCWriteTag;
        nFCWrite.getClass();
        editText.addTextChangedListener(new CTextWatcher(this.m_NFCPwdEditText));
        this.m_ShowMSGDialog = new ShowMSGDialog(this, false);
    }

    public void onBackPressed() {
        if (!this.mbSSIDchanged) {
            setResult(0);
        }
        super.onBackPressed();
    }

    public void ShowErrorDialog(String strMessage, String strTitle, int iResId) {
        this.mNFCListener.SetWriteStaus(NFCMode.NFCRead);
        Builder errorDialog = new Builder(this);
        errorDialog.setCancelable(false);
        errorDialog.setIcon(iResId);
        errorDialog.setTitle(strTitle);
        errorDialog.setMessage(strMessage);
        errorDialog.setPositiveButton(getString(C0349R.string.OK), new C03446());
        errorDialog.show();
    }

    void CreateSettingHintDialog() {
        if (this.m_ShowMSGDialog == null) {
            this.m_ShowMSGDialog = new ShowMSGDialog(this, true);
            this.m_ShowMSGDialog.SetDialogListener(new C07797());
        }
        this.m_ShowMSGDialog.ShowWaitingHintDialog(ThreadMode.AutoWifi, getString(C0349R.string.PLEASE_WAIT));
    }

    private void SetDefaultWifiInfo(String strSSID, String strPassword) {
        this.mbSSIDchanged = true;
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_SSID, strSSID);
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_PASSWORD, strPassword);
        intent.putExtras(bundle);
        setResult(AP_SETTING_CHANGE, intent);
    }

    public void ShowAlertDialog(String strMessage, String strTitle, int iResId) {
        Builder alertDialog = new Builder(this);
        alertDialog.setIcon(iResId);
        alertDialog.setTitle(strTitle);
        alertDialog.setMessage(strMessage);
        alertDialog.setPositiveButton(getString(C0349R.string.OK), new C03458());
        alertDialog.show();
    }
}
