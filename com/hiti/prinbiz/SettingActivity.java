package com.hiti.prinbiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.utility.PrinterInfo;
import com.hiti.service.upload.UploadService;
import com.hiti.trace.GlobalVariable_PrintSettingInfo;
import com.hiti.trace.GlobalVariable_WifiAutoConnectInfo;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.utility.ApModeConnection;
import com.hiti.utility.ApModeListener;
import com.hiti.utility.LogManager;
import com.hiti.utility.PrinterListListener;
import com.hiti.utility.ShowPrinterList;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.Verify.ThreadMode;
import com.hiti.utility.dialog.DialogListener;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import com.hiti.utility.wifi.WifiAutoConnect;
import java.util.LinkedList;
import org.apache.commons.net.telnet.TelnetOption;
import org.kxml2.wap.WbxmlParser;
import org.xmlpull.v1.XmlPullParser;

public class SettingActivity extends Activity {
    private static final int WIFI_SETTING_CHANGE = 19;
    private String ITEM_BORDER_SETTING;
    private String ITEM_CLEAN_MODE;
    private String ITEM_GENERAL_SETTING;
    private String ITEM_ID_SETTING;
    private String ITEM_MODEL_SWITCH;
    private String ITEM_PRINTER_SETTING_INFO;
    private String ITEM_QUICK_SETTING;
    LogManager LOG;
    String TAG;
    NFCInfo mNFCInfo;
    ApModeConnection m_ApModeConnection;
    private ImageButton m_BackButton;
    ProgressBar m_CloseListProgressBar;
    private TextView m_NowWifiTextView;
    private String m_Password;
    private String m_SSID;
    SettingAdapter m_SettingAdapter;
    ListView m_SettingIndexListView;
    private ShowMSGDialog m_ShowMSGDialog;
    private ShowPrinterList m_ShowPrinterList;
    private TextView m_VersionTextView;
    private GlobalVariable_WifiAutoConnectInfo m_WifiInfo;
    private boolean m_bBackStop;
    private boolean m_bChecked;
    private boolean m_bIsConnInfra;
    private boolean m_bNotMatchIsAsked;
    private boolean m_bNotShowWifi;
    LinkedList<Integer> m_iSettingImgList;
    private int m_iTotalSetting;
    private JumpPreferenceKey m_pref;
    private GlobalVariable_PrintSettingInfo m_prefPrintInfo;
    private String m_strCurrentSSID;
    private String m_strLastSSID;
    private String m_strModel;
    private String m_strSecurityKey;
    LinkedList<String> m_strSettingTextList;
    private String m_strTexture;
    private AutoWifiConnect m_wifiAutoConnect;

    /* renamed from: com.hiti.prinbiz.SettingActivity.2 */
    class C03542 implements OnClickListener {
        C03542() {
        }

        public void onClick(View v) {
            SettingActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingActivity.3 */
    class C03553 implements OnItemClickListener {
        C03553() {
        }

        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
            SettingActivity.this.LOG.m386v("position", String.valueOf(position));
            SettingActivity.this.onSettingListViewItemClicked(position);
        }
    }

    private class SettingAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        /* renamed from: com.hiti.prinbiz.SettingActivity.SettingAdapter.1 */
        class C03561 implements OnClickListener {
            C03561() {
            }

            public void onClick(View v) {
                SettingActivity.this.DispatchCommand(v.getId());
            }
        }

        class ViewHolder {
            int id;
            Button m_ItemImgBtn;
            TextView m_PrintInfoTextView;

            ViewHolder() {
            }
        }

        public SettingAdapter() {
            this.mInflater = (LayoutInflater) SettingActivity.this.getSystemService("layout_inflater");
        }

        public int getCount() {
            return SettingActivity.this.m_iTotalSetting;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = this.mInflater.inflate(C0349R.layout.item_setting_index, null);
                holder.m_ItemImgBtn = (Button) convertView.findViewById(C0349R.id.m_itemImageButton);
                holder.m_PrintInfoTextView = (TextView) convertView.findViewById(C0349R.id.m_PrintInfoTextView);
                holder.m_PrintInfoTextView.setVisibility(8);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.id = position;
            SettingTextAndImage(holder);
            return convertView;
        }

        private void SettingTextAndImage(ViewHolder holder) {
            int position = holder.id;
            String strItem = (String) SettingActivity.this.m_strSettingTextList.get(position);
            holder.m_PrintInfoTextView.setVisibility(8);
            holder.m_ItemImgBtn.setText((CharSequence) SettingActivity.this.m_strSettingTextList.get(position));
            holder.m_ItemImgBtn.setId(position);
            holder.m_ItemImgBtn.setBackgroundResource(((Integer) SettingActivity.this.m_iSettingImgList.get(position)).intValue());
            holder.m_ItemImgBtn.setOnClickListener(new C03561());
            if (strItem.equals(SettingActivity.this.ITEM_ID_SETTING)) {
                SettingActivity.this.GetPrinterInfo(ControllerState.PLAY_COUNT_STATE, holder);
            } else if (strItem.equals(SettingActivity.this.ITEM_QUICK_SETTING)) {
                SettingActivity.this.GetPrinterInfo(ControllerState.PLAY_PHOTO, holder);
            } else if (strItem.equals(SettingActivity.this.ITEM_GENERAL_SETTING)) {
                SettingActivity.this.GetPrinterInfo(ControllerState.NO_PLAY_ITEM, holder);
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingActivity.1 */
    class C07811 extends DialogListener {
        C07811() {
        }

        public void SetNowConnSSID(String strNowSSID) {
            SettingActivity.this.ShowPrinterListDialog();
        }

        public void SetLastConnSSID(String strLastSSID) {
            SettingActivity.this.CreateWaitingHintDialog();
            SettingActivity.this.m_wifiAutoConnect = new AutoWifiConnect(SettingActivity.this.getBaseContext(), SettingActivity.this.m_strLastSSID, SettingActivity.this.m_strSecurityKey);
            SettingActivity.this.m_wifiAutoConnect.execute(new Void[0]);
        }

        public void LeaveConfirm() {
        }

        public void LeaveCancel() {
        }

        public void LeaveClose() {
        }

        public void CancelConnetion(ThreadMode strSSID) {
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingActivity.4 */
    class C07824 implements INfcPreview {
        C07824() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            SettingActivity.this.mNFCInfo = nfcInfo;
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingActivity.5 */
    class C07835 implements MSGListener {
        C07835() {
        }

        public void OKClick() {
            SettingActivity.this.OpenWifi();
        }

        public void Close() {
        }

        public void CancelClick() {
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingActivity.6 */
    class C07846 implements MSGListener {
        C07846() {
        }

        public void OKClick() {
            SettingActivity.this.m_WifiInfo.RestoreGlobalVariable();
            SettingActivity.this.m_WifiInfo.SetSSID(SettingActivity.this.m_SSID);
            SettingActivity.this.m_WifiInfo.SetPassword(SettingActivity.this.m_Password);
            SettingActivity.this.m_WifiInfo.SaveGlobalVariableForever();
            Log.i("Set Default SSID ", SettingActivity.this.m_WifiInfo.GetSSID());
        }

        public void Close() {
        }

        public void CancelClick() {
            SettingActivity.this.m_SSID = XmlPullParser.NO_NAMESPACE;
            SettingActivity.this.m_Password = XmlPullParser.NO_NAMESPACE;
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingActivity.7 */
    class C07857 extends PrinterListListener {
        C07857() {
        }

        public void PrinterListFinish(String strPrinterSSID, String IP, int iPort, String strConn) {
            UploadService.StopUploadService(SettingActivity.this, UploadService.class);
            SettingActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
            GetPrinterInfoToList(strPrinterSSID, IP, iPort, strConn);
        }

        public void IsBackStateOnMDNS(boolean bMDNS) {
        }

        public void BackFinish() {
            SettingActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
            SettingActivity.this.m_CloseListProgressBar.setVisibility(8);
            SettingActivity.this.SetAllViewEnable(true);
        }

        public void BackStart() {
            SettingActivity.this.getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
            SettingActivity.this.m_CloseListProgressBar.setVisibility(0);
            SettingActivity.this.SetAllViewEnable(false);
            if (SettingActivity.this.m_ShowPrinterList.IsShowing()) {
                SettingActivity.this.m_ShowPrinterList.ListClose();
            }
        }

        private void GetPrinterInfoToList(String strPrinterSSID, String IP, int iPort, String strConn) {
            Log.e("PrinterListFinish", "strConn=" + strConn);
            if (strConn.equals("Infra")) {
                SettingActivity.this.m_bIsConnInfra = true;
            } else {
                SettingActivity.this.m_bIsConnInfra = false;
            }
            Intent intent = new Intent(SettingActivity.this.getBaseContext(), SettingPrinterActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, IP);
            bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, iPort);
            bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_SSID, strPrinterSSID);
            bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_CONN_MODE, strConn);
            intent.putExtras(bundle);
            SettingActivity.this.startActivityForResult(intent, 24);
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingActivity.8 */
    class C07868 extends ApModeListener {
        C07868() {
        }

        private void GetPrinterInfoToList(String IP, int iPort) {
            Intent intent = new Intent(SettingActivity.this.getBaseContext(), SettingPrinterActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, IP);
            bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, iPort);
            intent.putExtras(bundle);
            SettingActivity.this.startActivityForResult(intent, 24);
        }

        public void ScanIpFinish(String ip, int port) {
            GetPrinterInfoToList(ip, port);
        }

        public void SocketTest(int num) {
        }
    }

    class AutoWifiConnect extends WifiAutoConnect {
        public AutoWifiConnect(Context context, String strSSID, String strPassword) {
            super(context, strSSID, strPassword);
        }

        public void ConnectionSuccess() {
            SettingActivity.this.LOG.m384e("ConnectionSuccess", "ConnectionSuccess");
            ConnectionStop();
            SettingActivity.this.ShowPrinterListDialog();
        }

        public void ConnectionFail() {
            SettingActivity.this.LOG.m384e("ConnectionFail", "ConnectionFail");
            ConnectionStop();
            SettingActivity.this.ShowNoWiFiDialog();
        }

        public void ConnectionStop() {
            SettingActivity.this.LOG.m384e("autowifi", "stop");
            SettingActivity.this.m_ShowMSGDialog.StopWaitingDialog();
            SetStop(true);
        }
    }

    public SettingActivity() {
        this.m_BackButton = null;
        this.m_VersionTextView = null;
        this.m_NowWifiTextView = null;
        this.m_strLastSSID = null;
        this.m_strCurrentSSID = null;
        this.m_strSecurityKey = null;
        this.m_wifiAutoConnect = null;
        this.m_CloseListProgressBar = null;
        this.m_ShowMSGDialog = null;
        this.m_WifiInfo = null;
        this.m_prefPrintInfo = null;
        this.m_pref = null;
        this.m_SSID = XmlPullParser.NO_NAMESPACE;
        this.m_Password = XmlPullParser.NO_NAMESPACE;
        this.m_ShowPrinterList = null;
        this.m_bChecked = false;
        this.m_bBackStop = false;
        this.m_bNotMatchIsAsked = false;
        this.m_bNotShowWifi = false;
        this.m_bIsConnInfra = false;
        this.m_ApModeConnection = null;
        this.m_iSettingImgList = null;
        this.m_strSettingTextList = null;
        this.m_SettingAdapter = null;
        this.m_SettingIndexListView = null;
        this.m_iTotalSetting = 0;
        this.m_strTexture = null;
        this.m_strModel = null;
        this.ITEM_PRINTER_SETTING_INFO = XmlPullParser.NO_NAMESPACE;
        this.ITEM_ID_SETTING = XmlPullParser.NO_NAMESPACE;
        this.ITEM_QUICK_SETTING = XmlPullParser.NO_NAMESPACE;
        this.ITEM_GENERAL_SETTING = XmlPullParser.NO_NAMESPACE;
        this.ITEM_CLEAN_MODE = XmlPullParser.NO_NAMESPACE;
        this.ITEM_BORDER_SETTING = XmlPullParser.NO_NAMESPACE;
        this.ITEM_MODEL_SWITCH = XmlPullParser.NO_NAMESPACE;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = getClass().getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_setting);
        this.LOG = new LogManager(0);
        this.m_pref = new JumpPreferenceKey(this);
        this.m_strModel = this.m_pref.GetModelPreference();
        SetMSGDialog();
        this.ITEM_PRINTER_SETTING_INFO = getString(C0349R.string.SET_PRINTER);
        this.ITEM_ID_SETTING = getString(C0349R.string.SET_ID);
        this.ITEM_QUICK_SETTING = getString(C0349R.string.SET_QUICK);
        this.ITEM_GENERAL_SETTING = getString(C0349R.string.SET_LIFE);
        this.ITEM_CLEAN_MODE = getString(C0349R.string.CLEAN_MODE_TITLE);
        this.ITEM_BORDER_SETTING = getString(C0349R.string.SET_COLLAGE);
        this.ITEM_MODEL_SWITCH = getString(C0349R.string.SWITCH_PRINTER_MODEL);
        SetView();
    }

    protected void onNewIntent(Intent intent) {
        this.LOG.m386v("onNewIntent ", XmlPullParser.NO_NAMESPACE + intent);
        setIntent(intent);
        if (this.mNFCInfo != null) {
            this.mNFCInfo.mPrintMode = PrintMode.NormalMain;
        }
        super.onNewIntent(intent);
    }

    private void SetMSGDialog() {
        this.m_ShowMSGDialog = new ShowMSGDialog(this, false);
        this.m_ShowMSGDialog.SetDialogListener(new C07811());
    }

    private void SetView() {
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_CloseListProgressBar = (ProgressBar) findViewById(C0349R.id.m_CloseListprogressBar);
        this.m_CloseListProgressBar.setVisibility(8);
        this.m_NowWifiTextView = (TextView) findViewById(C0349R.id.m_NowWifiTextView);
        this.m_NowWifiTextView.setVisibility(8);
        this.m_WifiInfo = new GlobalVariable_WifiAutoConnectInfo(this);
        this.m_BackButton.setOnClickListener(new C03542());
        this.m_VersionTextView = (TextView) findViewById(C0349R.id.m_VersionTextView);
        try {
            String strtVersion = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
            SpannableString textSpannedVersion = new SpannableString(strtVersion);
            textSpannedVersion.setSpan(new ForegroundColorSpan(-12303292), 0, strtVersion.length(), 33);
            this.m_VersionTextView.setText(getString(C0349R.string.APP_VERSION) + String.valueOf(textSpannedVersion));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        this.m_iSettingImgList = new LinkedList();
        this.m_strSettingTextList = new LinkedList();
        SetSettingItem();
        this.m_SettingIndexListView = (ListView) findViewById(C0349R.id.m_SettingIndexListView);
        this.m_SettingIndexListView.setCacheColorHint(17170443);
        this.m_SettingIndexListView.setOnItemClickListener(new C03553());
        if (this.m_SettingAdapter == null) {
            this.m_SettingAdapter = new SettingAdapter();
        }
        this.m_SettingIndexListView.setAdapter(this.m_SettingAdapter);
        this.m_SettingIndexListView.setFocusableInTouchMode(true);
        this.m_SettingAdapter.notifyDataSetChanged();
    }

    void SetSettingItem() {
        this.m_strSettingTextList.add(this.ITEM_PRINTER_SETTING_INFO);
        if (!this.m_strModel.equals(WirelessType.TYPE_P530D)) {
            this.m_strSettingTextList.add(this.ITEM_ID_SETTING);
        }
        this.m_strSettingTextList.add(this.ITEM_QUICK_SETTING);
        if (!this.m_strModel.equals(WirelessType.TYPE_P530D)) {
            this.m_strSettingTextList.add(this.ITEM_GENERAL_SETTING);
            if (this.m_strModel.equals(WirelessType.TYPE_P310W)) {
                this.m_strSettingTextList.add(this.ITEM_CLEAN_MODE);
            }
            this.m_strSettingTextList.add(this.ITEM_BORDER_SETTING);
        }
        this.m_strSettingTextList.add(this.ITEM_MODEL_SWITCH);
        this.m_iTotalSetting = this.m_strSettingTextList.size();
        this.m_iSettingImgList.add(Integer.valueOf(C0349R.drawable.button_setting_printer));
        if (!this.m_strModel.equals(WirelessType.TYPE_P530D)) {
            this.m_iSettingImgList.add(Integer.valueOf(C0349R.drawable.button_setting_id));
        }
        this.m_iSettingImgList.add(Integer.valueOf(C0349R.drawable.button_setting_quick));
        if (!this.m_strModel.equals(WirelessType.TYPE_P530D)) {
            this.m_iSettingImgList.add(Integer.valueOf(C0349R.drawable.button_setting_general));
            if (this.m_strModel.equals(WirelessType.TYPE_P310W)) {
                this.m_iSettingImgList.add(Integer.valueOf(C0349R.drawable.button_setting_clean_mode));
            }
            this.m_iSettingImgList.add(Integer.valueOf(C0349R.drawable.button_setting_frame));
        }
        this.m_iSettingImgList.add(Integer.valueOf(C0349R.drawable.button_setting_switch));
    }

    public void onSettingListViewItemClicked(int position) {
        DispatchCommand(position);
    }

    private void GetPrinterInfo(int iRoute, ViewHolder holder) {
        String strMethod;
        this.m_prefPrintInfo = new GlobalVariable_PrintSettingInfo(this, iRoute);
        this.m_prefPrintInfo.RestoreGlobalVariable();
        if (this.m_prefPrintInfo.GetPrintTexture() == 1) {
            this.m_strTexture = getString(C0349R.string.SHADOWFACE);
        } else {
            this.m_strTexture = getString(C0349R.string.BRIGHTFACE);
        }
        int iPaperType = this.m_prefPrintInfo.GetServerPaperType();
        String strPrintoutItem = PrinterInfo.GetPrintoutItem(this, iPaperType);
        if (iPaperType == 6) {
            strPrintoutItem = "4x6";
        }
        this.LOG.m383d(this.TAG, "GetPrinterInfo iPaperType: " + iPaperType);
        this.LOG.m383d(this.TAG, "GetPrinterInfo strPrintoutItem: " + strPrintoutItem);
        String strSplit = getString(C0349R.string.SPLITE);
        if (this.m_strModel.equals(WirelessType.TYPE_P530D)) {
            if (this.m_prefPrintInfo.GetPrintDuplex() == 1) {
                strMethod = getString(C0349R.string.PAGE_DUPLEX);
            } else {
                strMethod = getString(C0349R.string.PAGE_SINGLE);
            }
        } else if (this.m_prefPrintInfo.GetPrintMethod() != 1) {
            strMethod = getString(C0349R.string.QTY_NORMAL);
        } else if (this.m_strModel.equals(WirelessType.TYPE_P310W)) {
            strMethod = getString(C0349R.string.QTY_HOD);
        } else {
            strMethod = getString(C0349R.string.QTY_REFINEMENT);
        }
        holder.m_PrintInfoTextView.setText(this.m_strTexture + strSplit + strPrintoutItem + strSplit + strMethod);
        holder.m_PrintInfoTextView.setVisibility(0);
    }

    private void DispatchCommand(int position) {
        String strItem = (String) this.m_strSettingTextList.get(position);
        if (strItem.equals(this.ITEM_PRINTER_SETTING_INFO)) {
            GetPrintInfo();
        } else if (strItem.equals(this.ITEM_ID_SETTING)) {
            IDSetting();
        } else if (strItem.equals(this.ITEM_QUICK_SETTING)) {
            QuickSetting();
        } else if (strItem.equals(this.ITEM_GENERAL_SETTING)) {
            GeneralSetting();
        } else if (strItem.equals(this.ITEM_CLEAN_MODE)) {
            CleanMode();
        } else if (strItem.equals(this.ITEM_BORDER_SETTING)) {
            BorderSetting();
        } else if (strItem.equals(this.ITEM_MODEL_SWITCH)) {
            ModelSwitch();
        }
    }

    private void GetPrintInfo() {
        CheckWifi();
    }

    private void IDSetting() {
        Intent intent = new Intent(this, SettingIDActivity.class);
        intent.putExtra(JumpBundleMessage.BUNDLE_MSG_ID_ROUTE_FROM, JumpPreferenceKey.ID_FROM_SETTING);
        startActivityForResult(intent, 24);
    }

    private void QuickSetting() {
        startActivityForResult(new Intent(this, SettingQuickPrintActivity.class), 24);
    }

    private void GeneralSetting() {
        startActivityForResult(new Intent(this, SettingGeneralPrintActivity.class), 24);
    }

    private void CleanMode() {
        startActivityForResult(new Intent(this, SettingCleanModeActivity.class), 74);
    }

    private void BorderSetting() {
        startActivityForResult(new Intent(this, SettingBorderActivity.class), 24);
    }

    private void ModelSwitch() {
        Intent intent = new Intent(this, ModelActivity.class);
        intent.putExtra(JumpBundleMessage.BUNDLE_MSG_SWITCH_MODEL, true);
        startActivityForResult(intent, 64);
    }

    private void CheckWifi() {
        this.m_bChecked = true;
        NetworkInfo mWifi = ((ConnectivityManager) getSystemService("connectivity")).getNetworkInfo(1);
        this.m_WifiInfo.RestoreGlobalVariable();
        this.m_strLastSSID = this.m_WifiInfo.GetSSID();
        this.m_strSecurityKey = this.m_WifiInfo.GetPassword();
        this.m_strCurrentSSID = GetNowSSID();
        if (mWifi.isConnected()) {
            this.LOG.m384e("isConnected", "isConnected");
            if (this.m_strCurrentSSID.contains(this.m_strLastSSID)) {
                this.LOG.m384e("isConnected", "Match");
                ShowPrinterListDialog();
                return;
            }
            this.LOG.m384e("isConnected", "notMatch");
            if (this.m_bNotMatchIsAsked) {
                ShowPrinterListDialog();
                return;
            } else {
                this.m_ShowMSGDialog.CreateConnectWifiHintDialog(this.m_strCurrentSSID, this.m_strLastSSID);
                return;
            }
        }
        this.LOG.m384e("not isConnected", "not isConnected");
        if (this.m_strLastSSID.length() != 0) {
            this.LOG.m384e("LastSSID_connecting", "auto scan");
            CreateWaitingHintDialog();
            this.m_wifiAutoConnect = new AutoWifiConnect(getBaseContext(), this.m_strLastSSID, this.m_strSecurityKey);
            this.m_wifiAutoConnect.execute(new Void[0]);
            return;
        }
        ShowNoWiFiDialog();
    }

    private void ShowNowWifi() {
        String strWifi = GetNowSSID();
        if (!((ConnectivityManager) getSystemService("connectivity")).getNetworkInfo(1).isConnected()) {
            this.m_NowWifiTextView.setVisibility(8);
        } else if (strWifi.isEmpty() || strWifi.equals("0x")) {
            this.m_NowWifiTextView.setVisibility(8);
        } else {
            if (strWifi.contains("\"")) {
                strWifi = strWifi.replace("\"", XmlPullParser.NO_NAMESPACE);
            }
            this.m_NowWifiTextView.setVisibility(0);
            this.m_NowWifiTextView.setText(getString(C0349R.string.NOW_WIFI) + " " + strWifi);
        }
    }

    private String GetNowSSID() {
        String strSSID;
        WifiInfo wifiInfo = ((WifiManager) getSystemService("wifi")).getConnectionInfo();
        if (wifiInfo.getSSID() == null) {
            strSSID = XmlPullParser.NO_NAMESPACE;
        } else {
            strSSID = wifiInfo.getSSID();
        }
        Log.e("GetNowSSID", strSSID);
        return strSSID;
    }

    void OpenWifi() {
        startActivityForResult(new Intent("android.settings.WIFI_SETTINGS"), 10);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mNFCInfo != null) {
            this.mNFCInfo.mPrintMode = PrintMode.NormalMain;
        }
        if (this.m_SettingAdapter != null) {
            this.m_SettingAdapter.notifyDataSetChanged();
        }
        switch (requestCode) {
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                this.m_ShowMSGDialog.StopWaitingDialog();
                if (this.m_ShowPrinterList != null && this.m_ShowPrinterList.IsShowing()) {
                    ShowPrinterListDialog();
                }
            case TelnetOption.TERMINAL_TYPE /*24*/:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        this.m_SSID = bundle.getString(JumpBundleMessage.BUNDLE_MSG_SSID);
                        this.m_Password = bundle.getString(JumpBundleMessage.BUNDLE_MSG_PASSWORD);
                    }
                }
                if (resultCode == WIFI_SETTING_CHANGE) {
                    this.m_bNotShowWifi = true;
                    this.m_NowWifiTextView.setVisibility(8);
                }
            case WbxmlParser.WAP_EXTENSION /*64*/:
                if (resultCode == -1) {
                    this.m_strModel = this.m_pref.GetModelPreference();
                    SetView();
                }
            default:
        }
    }

    protected void onResume() {
        super.onResume();
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07824());
        this.LOG.m386v("SelectSettingActivity", "onResume()");
        if (this.mNFCInfo.mPrintMode == PrintMode.NormalMain) {
            if (this.m_SSID.length() > 0) {
                this.m_WifiInfo.RestoreGlobalVariable();
                if (this.m_WifiInfo.GetSSID() == XmlPullParser.NO_NAMESPACE) {
                    this.m_WifiInfo.SetSSID(this.m_SSID);
                    this.m_WifiInfo.SetPassword(this.m_Password);
                    this.m_WifiInfo.SaveGlobalVariableForever();
                    this.LOG.m385i("Set Default SSID ", this.m_WifiInfo.GetSSID());
                } else if (this.m_WifiInfo.GetSSID().contains(this.m_SSID)) {
                    if (!(!this.m_WifiInfo.GetSSID().contains(this.m_SSID) || this.m_WifiInfo.GetPassword() == null || this.m_WifiInfo.GetPassword().equals(this.m_Password))) {
                        ShowModifyDefaultWifiAlertDialog(getString(C0349R.string.MODIFY_DEFAULT_CONNECTION_1) + this.m_SSID + getString(C0349R.string.MODIFY_DEFAULT_CONNECTION_2), getString(C0349R.string.WARNNING));
                    }
                } else if (!this.m_bIsConnInfra) {
                    ShowModifyDefaultWifiAlertDialog(getString(C0349R.string.MODIFY_DEFAULT_CONNECTION_1) + this.m_SSID + getString(C0349R.string.MODIFY_DEFAULT_CONNECTION_2), getString(C0349R.string.WARNNING));
                } else {
                    return;
                }
            }
            if (this.m_bNotShowWifi) {
                this.m_bNotShowWifi = false;
            } else {
                ShowNowWifi();
            }
            if (!this.m_bChecked && this.m_ShowPrinterList != null && this.m_ShowPrinterList.IsShowing()) {
                CheckWifi();
            }
        }
    }

    protected void onPause() {
        this.m_bChecked = false;
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    public void ShowNoWiFiDialog() {
        this.m_ShowMSGDialog.SetMSGListener(new C07835());
        String strTitle = getString(C0349R.string.UNABLE_TO_CONNECT_TO_PRINTER);
        this.m_ShowMSGDialog.ShowMessageDialog(getString(C0349R.string.PLEASE_SELECT_NETWORK), strTitle);
    }

    private void CreateWaitingHintDialog() {
        this.m_ShowMSGDialog.ShowWaitingHintDialog(null, getString(C0349R.string.CONNECTING));
    }

    public void ShowModifyDefaultWifiAlertDialog(String strMessage, String strTitle) {
        this.m_ShowMSGDialog.SetMSGListener(new C07846());
        this.m_ShowMSGDialog.ShowMessageDialog(strMessage, strTitle);
    }

    public void onBackPressed() {
        if (!this.m_bBackStop) {
            setResult(50);
            finish();
        }
    }

    private void ShowPrinterListDialog() {
        if (this.m_ShowPrinterList == null) {
            this.m_ShowPrinterList = new ShowPrinterList(this);
            this.m_ShowPrinterList.SetPrinterListListener(new C07857());
        }
        ShowNowWifi();
        getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        this.m_ShowPrinterList.Show();
    }

    private void SetAllViewEnable(boolean bEnable) {
        this.m_bBackStop = !bEnable;
        this.m_BackButton.setEnabled(bEnable);
        this.m_SettingIndexListView.setEnabled(bEnable);
    }

    private void ShowApModeConnection() {
        Log.e("ShowApModeConnection", "Click!!");
        if (this.m_ApModeConnection == null) {
            this.m_ApModeConnection = new ApModeConnection(this);
            this.m_ApModeConnection.SetApModeListener(new C07868());
        }
        this.m_ApModeConnection.Scan();
        getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        this.m_ApModeConnection.Show();
    }
}
