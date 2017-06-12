package com.hiti.prinbiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.media.TransportMediator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.request.HitiPPR_GetWifiConfig;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.printerprotocol.request.HitiPPR_SetWifiModeSetting;
import com.hiti.utility.LogManager;
import com.hiti.utility.dialog.CreateWaitDialog;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import org.xmlpull.v1.XmlPullParser;

public class InfraWifiSettingActivity extends Activity {
    private static final int AP_MODE = 0;
    private static final int INFRA_MODE = 1;
    private static final int INFRA_SETTING_CHANGE = 17;
    private String IP;
    LogManager LOG;
    private OnCheckedChangeListener ListenInfraOpenCheck;
    String TAG;
    NFCInfo mNFCInfo;
    private ImageButton m_BackImageButton;
    private ImageButton m_ConfirmButton;
    HitiPPR_GetWifiConfig m_GetWifiSetting;
    private RadioGroup m_InfraOpenRadioGroup;
    private RelativeLayout m_InfraSetLayout;
    CreateWaitDialog m_InfraSettingDialog;
    private TextView m_PrinterNameTextView;
    private EditText m_PwdEditText;
    private EditText m_SSIDEditText;
    HitiPPR_SetWifiModeSetting m_SetWifiInfraSetting;
    ShowMSGDialog m_ShowMSGDialog;
    private WifiInfraSettingHandler m_WifiInfraHandler;
    private int m_iConnMode;
    private int m_iPort;
    private String m_strApPwd;
    private String m_strApSSID;
    private String m_strInfraPwd;
    private String m_strInfraSSID;

    /* renamed from: com.hiti.prinbiz.InfraWifiSettingActivity.2 */
    class C03092 implements OnClickListener {
        C03092() {
        }

        public void onClick(View v) {
            InfraWifiSettingActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.InfraWifiSettingActivity.3 */
    class C03103 implements OnClickListener {
        C03103() {
        }

        public void onClick(View v) {
            if (InfraWifiSettingActivity.this.m_iConnMode == InfraWifiSettingActivity.INFRA_MODE) {
                InfraWifiSettingActivity.this.ChangeToInfraMode();
            } else {
                InfraWifiSettingActivity.this.ChangeToApMode();
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.InfraWifiSettingActivity.4 */
    class C03114 implements OnCheckedChangeListener {
        C03114() {
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case C0349R.id.m_InfraCloseRadioButton /*2131427453*/:
                    InfraWifiSettingActivity.this.m_iConnMode = InfraWifiSettingActivity.AP_MODE;
                    InfraWifiSettingActivity.this.m_InfraSetLayout.setVisibility(8);
                case C0349R.id.m_InfraOpenRadioButton /*2131427454*/:
                    InfraWifiSettingActivity.this.m_iConnMode = InfraWifiSettingActivity.INFRA_MODE;
                    InfraWifiSettingActivity.this.m_InfraSetLayout.setVisibility(InfraWifiSettingActivity.AP_MODE);
                default:
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.InfraWifiSettingActivity.1 */
    class C07681 implements INfcPreview {
        C07681() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            InfraWifiSettingActivity.this.mNFCInfo = nfcInfo;
        }
    }

    /* renamed from: com.hiti.prinbiz.InfraWifiSettingActivity.5 */
    class C07695 implements MSGListener {
        final /* synthetic */ Intent val$data;

        C07695(Intent intent) {
            this.val$data = intent;
        }

        public void Close() {
        }

        public void OKClick() {
            InfraWifiSettingActivity.this.setResult(InfraWifiSettingActivity.INFRA_SETTING_CHANGE, this.val$data);
            InfraWifiSettingActivity.this.finish();
        }

        public void CancelClick() {
        }
    }

    class WifiInfraSettingHandler extends MSGHandler {
        WifiInfraSettingHandler() {
        }

        public void handleMessage(Message msg) {
            InfraWifiSettingActivity.this.LOG.m383d(InfraWifiSettingActivity.this.TAG, "handler " + msg.what);
            Bundle buddle;
            String strTitle;
            String strMSG;
            switch (msg.what) {
                case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                    InfraWifiSettingActivity.this.m_InfraSettingDialog.DismissDialog();
                    InfraWifiSettingActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    buddle = msg.getData();
                    strTitle = InfraWifiSettingActivity.this.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    InfraWifiSettingActivity.this.ShowMSGAlert(strTitle, strMSG, null);
                case RequestState.REQUEST_GET_WIFI_SETTING /*325*/:
                    InfraWifiSettingActivity.this.m_InfraSettingDialog.DismissDialog();
                    InfraWifiSettingActivity.this.m_iConnMode = InfraWifiSettingActivity.this.m_GetWifiSetting.GetAttrWifiMode();
                    InfraWifiSettingActivity.this.SetRadioGroupOpenInfra(InfraWifiSettingActivity.this.m_iConnMode, InfraWifiSettingActivity.this.m_GetWifiSetting.GetAttrSSID());
                case RequestState.REQUEST_GET_WIFI_SETTING_ERROR /*326*/:
                    InfraWifiSettingActivity.this.m_InfraSettingDialog.DismissDialog();
                    InfraWifiSettingActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    buddle = msg.getData();
                    strTitle = InfraWifiSettingActivity.this.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    InfraWifiSettingActivity.this.ShowMSGAlert(strTitle, strMSG, null);
                case RequestState.REQUEST_SET_WIFI_MODE_SETTING /*398*/:
                    InfraWifiSettingActivity.this.m_InfraSettingDialog.DismissDialog();
                    strTitle = XmlPullParser.NO_NAMESPACE;
                    strMSG = XmlPullParser.NO_NAMESPACE;
                    Bundle bundle = new Bundle();
                    Intent data = new Intent();
                    if (InfraWifiSettingActivity.this.m_iConnMode == InfraWifiSettingActivity.INFRA_MODE) {
                        strTitle = InfraWifiSettingActivity.this.getString(C0349R.string.INFRA_CHANGE_DONE);
                        strMSG = InfraWifiSettingActivity.this.getString(C0349R.string.CHANGE_WIFI_MODE);
                        bundle.putString(JumpBundleMessage.BUNDLE_MSG_SSID, InfraWifiSettingActivity.this.m_strInfraSSID);
                        bundle.putString(JumpBundleMessage.BUNDLE_MSG_PASSWORD, InfraWifiSettingActivity.this.m_strInfraPwd);
                    } else {
                        strTitle = InfraWifiSettingActivity.this.getString(C0349R.string.AP_CHANGE_DONE);
                        strMSG = InfraWifiSettingActivity.this.getString(C0349R.string.CHANGE_WIFI_MODE);
                        bundle.putString(JumpBundleMessage.BUNDLE_MSG_SSID, InfraWifiSettingActivity.this.m_strApSSID);
                        bundle.putString(JumpBundleMessage.BUNDLE_MSG_PASSWORD, InfraWifiSettingActivity.this.m_strApPwd);
                    }
                    data.putExtras(bundle);
                    InfraWifiSettingActivity.this.ShowMSGAlert(strTitle, strMSG, data);
                case RequestState.REQUEST_SET_WIFI_MODE_SETTING_ERROR /*399*/:
                    InfraWifiSettingActivity.this.m_InfraSettingDialog.DismissDialog();
                    InfraWifiSettingActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    buddle = msg.getData();
                    strTitle = InfraWifiSettingActivity.this.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    InfraWifiSettingActivity.this.ShowMSGAlert(strTitle, strMSG, null);
                    Toast.makeText(InfraWifiSettingActivity.this.getBaseContext(), InfraWifiSettingActivity.this.getString(C0349R.string.SET_SSID_AND_PASSWORD_FAIL), InfraWifiSettingActivity.AP_MODE).show();
                default:
            }
        }
    }

    public InfraWifiSettingActivity() {
        this.m_InfraOpenRadioGroup = null;
        this.m_SSIDEditText = null;
        this.m_PwdEditText = null;
        this.m_PrinterNameTextView = null;
        this.m_ConfirmButton = null;
        this.m_BackImageButton = null;
        this.m_InfraSetLayout = null;
        this.m_InfraSettingDialog = null;
        this.m_ShowMSGDialog = null;
        this.m_GetWifiSetting = null;
        this.m_SetWifiInfraSetting = null;
        this.m_WifiInfraHandler = null;
        this.IP = XmlPullParser.NO_NAMESPACE;
        this.m_iPort = AP_MODE;
        this.m_iConnMode = AP_MODE;
        this.m_strApSSID = null;
        this.m_strApPwd = null;
        this.m_strInfraSSID = null;
        this.m_strInfraPwd = null;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = getClass().getSimpleName();
        this.ListenInfraOpenCheck = new C03114();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_infra_setting);
        this.LOG = new LogManager(AP_MODE);
        GetBundle();
        SetView();
        this.m_InfraSettingDialog.ShowDialog(getString(C0349R.string.PLEASE_WAIT));
        this.m_WifiInfraHandler = new WifiInfraSettingHandler();
        this.m_GetWifiSetting = new HitiPPR_GetWifiConfig(this, this.IP, this.m_iPort, this.m_WifiInfraHandler);
        this.m_GetWifiSetting.start();
    }

    protected void onResume() {
        super.onResume();
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07681());
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
            this.m_strApSSID = bundle.getString(JumpBundleMessage.BUNDLE_MSG_SSID);
            this.m_strApPwd = bundle.getString(JumpBundleMessage.BUNDLE_MSG_PASSWORD);
            if (this.m_strApSSID == null) {
                this.m_strApSSID = XmlPullParser.NO_NAMESPACE;
            }
            if (this.m_strApPwd == null) {
                this.m_strApPwd = XmlPullParser.NO_NAMESPACE;
                return;
            }
            return;
        }
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
    }

    private void SetView() {
        this.m_InfraOpenRadioGroup = (RadioGroup) findViewById(C0349R.id.m_OpenInfraRadioGroup);
        this.m_SSIDEditText = (EditText) findViewById(C0349R.id.m_SSIDEditText);
        this.m_PwdEditText = (EditText) findViewById(C0349R.id.m_PwdEditText);
        this.m_ConfirmButton = (ImageButton) findViewById(C0349R.id.m_SetComfirmButton);
        this.m_InfraSetLayout = (RelativeLayout) findViewById(C0349R.id.m_InfraSetLayout);
        this.m_PrinterNameTextView = (TextView) findViewById(C0349R.id.m_PrinterNameTextView);
        this.m_BackImageButton = (ImageButton) findViewById(C0349R.id.m_BackImageButton);
        this.m_InfraSetLayout.setVisibility(8);
        this.m_InfraOpenRadioGroup.setOnCheckedChangeListener(this.ListenInfraOpenCheck);
        this.m_BackImageButton.setOnClickListener(new C03092());
        this.m_ConfirmButton.setOnClickListener(new C03103());
        this.m_InfraSettingDialog = new CreateWaitDialog(this);
    }

    private void SetRadioGroupOpenInfra(int iConnMode, String wifiSSID) {
        if (iConnMode == INFRA_MODE) {
            this.m_InfraOpenRadioGroup.check(C0349R.id.m_InfraOpenRadioButton);
            this.m_InfraSetLayout.setVisibility(AP_MODE);
            this.m_SSIDEditText.setText(wifiSSID);
        } else {
            this.m_InfraOpenRadioGroup.check(C0349R.id.m_InfraCloseRadioButton);
            this.m_InfraSetLayout.setVisibility(8);
        }
        this.m_PrinterNameTextView.setText(this.m_strApSSID);
    }

    private void ChangeToInfraMode() {
        this.m_strInfraSSID = this.m_SSIDEditText.getText().toString();
        this.m_strInfraPwd = this.m_PwdEditText.getText().toString();
        if (this.m_strInfraSSID.isEmpty()) {
            Toast.makeText(getBaseContext(), getString(C0349R.string.INFRA_NO_SSID), AP_MODE).show();
            return;
        }
        this.m_InfraSettingDialog.ShowDialog(getString(C0349R.string.INFRA_SETTING));
        this.m_iConnMode = INFRA_MODE;
        this.m_SetWifiInfraSetting = new HitiPPR_SetWifiModeSetting(getBaseContext(), this.IP, this.m_iPort, this.m_iConnMode, this.m_WifiInfraHandler);
        this.m_SetWifiInfraSetting.SetSSID(this.m_strInfraSSID);
        this.m_SetWifiInfraSetting.SetPassword(this.m_strInfraPwd);
        this.m_SetWifiInfraSetting.start();
    }

    private void ChangeToApMode() {
        this.m_InfraSettingDialog.ShowDialog(getString(C0349R.string.INFRA_SETTING));
        this.m_iConnMode = AP_MODE;
        this.m_SetWifiInfraSetting = new HitiPPR_SetWifiModeSetting(getBaseContext(), this.IP, this.m_iPort, this.m_iConnMode, this.m_WifiInfraHandler);
        this.m_SetWifiInfraSetting.start();
    }

    private void ShowMSGAlert(String strTitle, String strMSG, Intent data) {
        if (this.m_ShowMSGDialog == null) {
            this.m_ShowMSGDialog = new ShowMSGDialog(this, false);
            this.m_ShowMSGDialog.SetMSGListener(new C07695(data));
        }
        this.m_ShowMSGDialog.ShowSimpleMSGDialog(strMSG, strTitle);
    }
}
