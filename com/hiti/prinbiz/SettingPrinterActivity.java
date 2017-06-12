package com.hiti.prinbiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.media.TransportMediator;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.request.HitiPPR_GetAllSetting;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.printerprotocol.utility.BurnFWUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.Verify.ThreadMode;
import com.hiti.utility.dialog.DialogListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import java.util.LinkedList;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.ksoap2.SoapEnvelope;
import org.xmlpull.v1.XmlPullParser;

public class SettingPrinterActivity extends Activity {
    private static final int AP_SETTING_CHANGE = 18;
    private static final int INFRA_SETTING_CHANGE = 17;
    private static final int WIFI_SETTING_CHANGE = 19;
    String IP;
    LogManager LOG;
    String SETTING_ITEM_ABOUT_APP;
    String SETTING_ITEM_AUTO_POWER_OFF;
    String SETTING_ITEM_FIRMWARE_VERSION;
    String SETTING_ITEM_INFRA_WIFI_SETTINGS;
    String SETTING_ITEM_PRINTER_STATUS;
    String SETTING_ITEM_PRINTER_WIFI_SETTINGS;
    String TAG;
    int iItem_about_app;
    int iItem_ap_wifi_setting;
    int iItem_auto_power_off;
    int iItem_firmware_version;
    int iItem_infra_wifi_setting;
    int iItem_printer_status;
    NFCInfo mNFCInfo;
    ImageButton m_BackButton;
    AlertDialog m_CheckBurnFWAlertDialog;
    HitiPPR_GetAllSetting m_HitiPPR_GetAllSetting;
    String m_Password;
    ProgressBar m_ProgressBar;
    String m_SSID;
    SettingAdapter m_SettingAdapter;
    SettingHandler m_SettingHandler;
    ListView m_SettingListView;
    ShowMSGDialog m_ShowMSGDialog;
    TextView m_TitleTextView;
    boolean m_boNewFW;
    int m_iAutoSeconds;
    int m_iItemSize;
    int m_iPort;
    int m_iScreenHeight;
    int m_iScreenWidth;
    private int m_iTotalSetting;
    JumpPreferenceKey m_pref;
    String m_strConn;
    String m_strProductIDString;
    LinkedList<String> m_strSettingList;

    /* renamed from: com.hiti.prinbiz.SettingPrinterActivity.1 */
    class C03811 implements OnClickListener {
        C03811() {
        }

        public void onClick(View v) {
            SettingPrinterActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingPrinterActivity.2 */
    class C03822 implements OnItemClickListener {
        C03822() {
        }

        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            SettingPrinterActivity.this.onSettingListViewItemClicked(a, v, position, id);
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingPrinterActivity.5 */
    class C03835 implements DialogInterface.OnClickListener {
        C03835() {
        }

        public void onClick(DialogInterface dialog, int which) {
            SettingPrinterActivity.this.onBackButtonClicked(null);
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingPrinterActivity.6 */
    class C03846 implements DialogInterface.OnClickListener {
        C03846() {
        }

        public void onClick(DialogInterface dialog, int which) {
            SettingPrinterActivity.this.DispatchCommand(SettingPrinterActivity.this.iItem_firmware_version);
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingPrinterActivity.7 */
    class C03857 implements DialogInterface.OnClickListener {
        C03857() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingPrinterActivity.8 */
    static /* synthetic */ class C03868 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$utility$Verify$ThreadMode;

        static {
            $SwitchMap$com$hiti$utility$Verify$ThreadMode = new int[ThreadMode.values().length];
            try {
                $SwitchMap$com$hiti$utility$Verify$ThreadMode[ThreadMode.GetInfo.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    public class SettingAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        class ViewHolder {
            ImageView checkView;
            int id;
            TextView textView;
            ImageView updateView;

            ViewHolder() {
            }
        }

        public SettingAdapter() {
            this.mInflater = (LayoutInflater) SettingPrinterActivity.this.getSystemService("layout_inflater");
        }

        public int getCount() {
            return SettingPrinterActivity.this.m_iTotalSetting;
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
                convertView = this.mInflater.inflate(C0349R.layout.item_setting, null);
                holder.textView = (TextView) convertView.findViewById(C0349R.id.txtSettingName);
                holder.textView.setHeight(SettingPrinterActivity.this.m_iItemSize);
                holder.textView.setGravity(SettingPrinterActivity.WIFI_SETTING_CHANGE);
                holder.checkView = (ImageView) convertView.findViewById(C0349R.id.CheckView);
                holder.checkView.setImageResource(0);
                holder.updateView = (ImageView) convertView.findViewById(C0349R.id.UpdateView);
                holder.updateView.setImageResource(0);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.updateView.setId(position);
            holder.checkView.setId(position);
            holder.textView.setId(position);
            holder.id = position;
            SetItemText(holder);
            SetNextStepPhoto(holder);
            SetItemHeight(holder);
            return convertView;
        }

        void SetItemText(ViewHolder holder) {
            int iSelect = SettingPrinterActivity.this.GetWhatSettingItem(holder.id);
            SettingPrinterActivity.this.LOG.m385i("iSelect", "=" + iSelect);
            if (iSelect == SettingPrinterActivity.this.iItem_about_app) {
                SpannableString textSpannedTitle = new SpannableString(SettingPrinterActivity.this.getString(C0349R.string.ABOUT_APP));
                textSpannedTitle.setSpan(new StyleSpan(0), 0, SettingPrinterActivity.this.getString(C0349R.string.ABOUT_APP).length(), 33);
                holder.textView.setText(textSpannedTitle);
                try {
                    String strtVersion = SettingPrinterActivity.this.getString(C0349R.string.ABOUT_APP_VERSION) + String.valueOf(SettingPrinterActivity.this.getPackageManager().getPackageInfo(SettingPrinterActivity.this.getPackageName(), 0).versionName);
                    SettingPrinterActivity.this.LOG.m385i("strtVersion.length()", String.valueOf(strtVersion.length()));
                    SpannableString textSpannedVersion = new SpannableString(strtVersion);
                    textSpannedVersion.setSpan(new ForegroundColorSpan(-7829368), 0, strtVersion.length(), 33);
                    holder.textView.append(textSpannedVersion);
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
                SpannableString textSpannedContent = new SpannableString(SettingPrinterActivity.this.getString(C0349R.string.ABOUT_APP_CONTENT));
                textSpannedContent.setSpan(new ForegroundColorSpan(-7829368), 0, SettingPrinterActivity.this.getString(C0349R.string.ABOUT_APP_CONTENT).length(), 33);
                holder.textView.append(textSpannedContent);
                return;
            }
            holder.textView.setText((CharSequence) SettingPrinterActivity.this.m_strSettingList.get(holder.id));
        }

        void SetNextStepPhoto(ViewHolder holder) {
            int iSelect = SettingPrinterActivity.this.GetWhatSettingItem(holder.id);
            holder.checkView.setImageResource(0);
            holder.updateView.setImageResource(0);
            if (iSelect == SettingPrinterActivity.this.iItem_ap_wifi_setting || iSelect == SettingPrinterActivity.this.iItem_infra_wifi_setting) {
                holder.checkView.setImageResource(C0349R.drawable.button_arrow_album);
            }
            if (iSelect == SettingPrinterActivity.this.iItem_firmware_version && SettingPrinterActivity.this.m_boNewFW) {
                holder.checkView.setImageResource(C0349R.drawable.button_arrow_album);
                holder.updateView.setImageResource(C0349R.drawable.one_update);
                holder.updateView.getLayoutParams().width = SettingPrinterActivity.this.m_iItemSize;
            }
            if (iSelect == SettingPrinterActivity.this.iItem_auto_power_off && SettingPrinterActivity.this.m_strProductIDString.equals(WirelessType.TYPE_P310W)) {
                holder.checkView.setImageResource(C0349R.drawable.button_arrow_album);
            }
        }

        void SetItemHeight(ViewHolder holder) {
            int iSelect = SettingPrinterActivity.this.GetWhatSettingItem(holder.id);
            holder.textView.setHeight(SettingPrinterActivity.this.m_iItemSize);
            if (iSelect == SettingPrinterActivity.this.iItem_about_app) {
                holder.textView.setHeight(SettingPrinterActivity.this.m_iItemSize * 2);
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingPrinterActivity.3 */
    class C07923 implements INfcPreview {
        C07923() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            SettingPrinterActivity.this.mNFCInfo = nfcInfo;
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingPrinterActivity.4 */
    class C07934 extends DialogListener {
        C07934() {
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
            switch (C03868.$SwitchMap$com$hiti$utility$Verify$ThreadMode[mode.ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    if (SettingPrinterActivity.this.m_HitiPPR_GetAllSetting != null) {
                        SettingPrinterActivity.this.m_HitiPPR_GetAllSetting.Stop();
                    }
                    SettingPrinterActivity.this.onBackPressed();
                default:
            }
        }
    }

    class SettingHandler extends MSGHandler {
        SettingHandler() {
        }

        public void handleMessage(Message msg) {
            Bundle buddle;
            String strTitle;
            String strMSG;
            switch (msg.what) {
                case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                    SettingPrinterActivity.this.LOG.m385i(SettingPrinterActivity.this.TAG + " Get All setting", "timeout");
                    SettingPrinterActivity.this.m_ShowMSGDialog.StopWaitingDialog();
                    SettingPrinterActivity.this.m_BackButton.setEnabled(true);
                    SettingPrinterActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    buddle = msg.getData();
                    strTitle = SettingPrinterActivity.this.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    SettingPrinterActivity.this.ShowErrorDialog(strMSG, strTitle, 17301569);
                case RequestState.REQUEST_SETTING_ITEM_ALL /*338*/:
                    SettingPrinterActivity.this.LOG.m386v("Get All setting", "success");
                    SettingPrinterActivity.this.m_ShowMSGDialog.StopWaitingDialog();
                    SettingPrinterActivity.this.m_BackButton.setEnabled(true);
                    String strTemp = SettingPrinterActivity.this.getString(C0349R.string.FIRMWARE_VERSION) + " " + SettingPrinterActivity.this.m_HitiPPR_GetAllSetting.GetAttrFirmwareVersionString();
                    SettingPrinterActivity.this.m_strSettingList.remove(SettingPrinterActivity.this.iItem_firmware_version);
                    SettingPrinterActivity.this.m_strSettingList.add(SettingPrinterActivity.this.iItem_firmware_version, strTemp);
                    SettingPrinterActivity.this.m_strProductIDString = SettingPrinterActivity.this.m_HitiPPR_GetAllSetting.GetAttrProductIDString();
                    if (SettingPrinterActivity.this.m_pref == null) {
                        SettingPrinterActivity.this.m_pref = new JumpPreferenceKey(SettingPrinterActivity.this);
                    }
                    String strDefaultProductID = SettingPrinterActivity.this.m_pref.GetModelPreference();
                    SettingPrinterActivity.this.LOG.m386v("m_strProductIDString", "=" + SettingPrinterActivity.this.m_strProductIDString);
                    SettingPrinterActivity.this.LOG.m386v("strDefaultProductID", "=" + strDefaultProductID);
                    if (SettingPrinterActivity.this.m_strProductIDString.equals(strDefaultProductID)) {
                        if ((SettingPrinterActivity.this.m_strProductIDString.equals(WirelessType.TYPE_P520L) || SettingPrinterActivity.this.m_strProductIDString.equals(WirelessType.TYPE_P750L) || SettingPrinterActivity.this.m_strProductIDString.equals(WirelessType.TYPE_P530D)) && SettingPrinterActivity.this.m_iTotalSetting > 5) {
                            SettingPrinterActivity.this.m_strSettingList.remove(SettingPrinterActivity.this.iItem_auto_power_off);
                            SettingPrinterActivity.this.iItem_about_app = SettingPrinterActivity.this.iItem_infra_wifi_setting;
                            SettingPrinterActivity.this.iItem_infra_wifi_setting = SettingPrinterActivity.this.iItem_ap_wifi_setting;
                            SettingPrinterActivity.this.iItem_ap_wifi_setting = SettingPrinterActivity.this.iItem_auto_power_off;
                            SettingPrinterActivity.this.iItem_auto_power_off = -1;
                            SettingPrinterActivity.this.m_iTotalSetting = SettingPrinterActivity.this.m_strSettingList.size();
                            SettingPrinterActivity.this.LOG.m386v("iItem_about_app", "=" + SettingPrinterActivity.this.iItem_about_app);
                        }
                        String strGetVersion = SettingPrinterActivity.this.m_HitiPPR_GetAllSetting.GetAttrFirmwareVersionString().replace(".", XmlPullParser.NO_NAMESPACE).substring(0, 3);
                        SettingPrinterActivity.this.LOG.m386v("strGetVersion", strGetVersion);
                        String strUpdateVersion = BurnFWUtility.GetTheNewestFWVersion(SettingPrinterActivity.this.getBaseContext(), SettingPrinterActivity.this.m_strProductIDString, true).second.substring(0, 3);
                        SettingPrinterActivity.this.LOG.m386v("strUpdateVersion", strUpdateVersion);
                        SettingPrinterActivity.this.m_boNewFW = false;
                        if (Integer.parseInt(strGetVersion) < Integer.parseInt(strUpdateVersion)) {
                            SettingPrinterActivity.this.m_boNewFW = true;
                        }
                        strTemp = SettingPrinterActivity.this.getString(C0349R.string.PRINTER_STATUS);
                        if (SettingPrinterActivity.this.m_HitiPPR_GetAllSetting.GetAttrErrorDescription() != null) {
                            String strError = SettingPrinterActivity.this.m_HitiPPR_GetAllSetting.GetErrorMSG(SettingPrinterActivity.this.m_HitiPPR_GetAllSetting.GetAttrErrorDescription());
                            if (SettingPrinterActivity.this.m_strProductIDString.contains(WirelessType.TYPE_P310W)) {
                                strError = SettingPrinterActivity.this.ErrorMessageCheck(strError);
                            }
                            strTemp = strTemp + " " + strError;
                        } else {
                            strTemp = strTemp + " " + SettingPrinterActivity.this.GetPrinterStatus(SettingPrinterActivity.this.m_HitiPPR_GetAllSetting.GetAttrPrinterStatus());
                        }
                        SettingPrinterActivity.this.m_strSettingList.remove(SettingPrinterActivity.this.iItem_printer_status);
                        SettingPrinterActivity.this.m_strSettingList.add(SettingPrinterActivity.this.iItem_printer_status, strTemp);
                        SettingPrinterActivity.this.m_SettingAdapter.notifyDataSetChanged();
                        if (SettingPrinterActivity.this.m_strProductIDString.equals(WirelessType.TYPE_P310W)) {
                            strTemp = SettingPrinterActivity.this.getString(C0349R.string.AUTO_POWER_OFF) + " " + SettingPrinterActivity.this.GetAutoPowerOff(SettingPrinterActivity.this.m_HitiPPR_GetAllSetting.GetAttrAutoPowerOffSeconds());
                            SettingPrinterActivity.this.m_strSettingList.remove(SettingPrinterActivity.this.iItem_auto_power_off);
                            SettingPrinterActivity.this.m_strSettingList.add(SettingPrinterActivity.this.iItem_auto_power_off, strTemp);
                            SettingPrinterActivity.this.m_SettingAdapter.notifyDataSetChanged();
                        }
                        if (SettingPrinterActivity.this.m_boNewFW) {
                            SettingPrinterActivity.this.ShowBurnFWDialog(SettingPrinterActivity.this.getString(C0349R.string.SUGGEST_UPDATE_FIRMWARE));
                        }
                        if (SettingPrinterActivity.this.m_HitiPPR_GetAllSetting != null) {
                            SettingPrinterActivity.this.m_SSID = SettingPrinterActivity.this.m_HitiPPR_GetAllSetting.GetAttrSSID();
                            Log.e("m_SSID", String.valueOf(SettingPrinterActivity.this.m_SSID));
                            SettingPrinterActivity.this.m_Password = SettingPrinterActivity.this.m_HitiPPR_GetAllSetting.GetAttrSecurityKey();
                            if (SettingPrinterActivity.this.m_Password == null) {
                                SettingPrinterActivity.this.m_Password = XmlPullParser.NO_NAMESPACE;
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    strTitle = SettingPrinterActivity.this.getString(C0349R.string.ERROR);
                    strMSG = SettingPrinterActivity.this.getString(C0349R.string.ERROR_MODEL);
                    SettingPrinterActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    SettingPrinterActivity.this.ShowErrorDialog(strMSG, strTitle, 17301569);
                case RequestState.REQUEST_SETTING_ITEM_ALL_ERROR /*339*/:
                    SettingPrinterActivity.this.LOG.m385i(SettingPrinterActivity.this.TAG + " Get All setting", "conn error");
                    SettingPrinterActivity.this.m_ShowMSGDialog.StopWaitingDialog();
                    SettingPrinterActivity.this.m_BackButton.setEnabled(true);
                    buddle = msg.getData();
                    strTitle = SettingPrinterActivity.this.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    SettingPrinterActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    SettingPrinterActivity.this.ShowErrorDialog(strMSG, strTitle, 17301569);
                default:
            }
        }
    }

    public SettingPrinterActivity() {
        this.iItem_firmware_version = 0;
        this.iItem_printer_status = 1;
        this.iItem_auto_power_off = 2;
        this.iItem_ap_wifi_setting = 3;
        this.iItem_infra_wifi_setting = 4;
        this.iItem_about_app = 5;
        this.SETTING_ITEM_FIRMWARE_VERSION = null;
        this.SETTING_ITEM_PRINTER_STATUS = null;
        this.SETTING_ITEM_AUTO_POWER_OFF = null;
        this.SETTING_ITEM_PRINTER_WIFI_SETTINGS = null;
        this.SETTING_ITEM_INFRA_WIFI_SETTINGS = null;
        this.SETTING_ITEM_ABOUT_APP = null;
        this.m_strSettingList = null;
        this.m_SettingAdapter = null;
        this.m_SettingListView = null;
        this.m_iTotalSetting = 0;
        this.m_SettingHandler = null;
        this.m_CheckBurnFWAlertDialog = null;
        this.m_BackButton = null;
        this.m_TitleTextView = null;
        this.m_ProgressBar = null;
        this.m_ShowMSGDialog = null;
        this.m_iScreenWidth = 0;
        this.m_iScreenHeight = 0;
        this.m_iItemSize = 0;
        this.m_boNewFW = false;
        this.m_HitiPPR_GetAllSetting = null;
        this.m_strProductIDString = WirelessType.TYPE_P520L;
        this.m_SSID = XmlPullParser.NO_NAMESPACE;
        this.m_Password = XmlPullParser.NO_NAMESPACE;
        this.m_strConn = XmlPullParser.NO_NAMESPACE;
        this.IP = XmlPullParser.NO_NAMESPACE;
        this.m_iPort = 0;
        this.m_iAutoSeconds = 0;
        this.m_pref = null;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0349R.layout.activity_setting_printer);
        this.LOG = new LogManager(Integer.parseInt(getString(C0349R.string.SettingPrinterActivity_DEBUG_LOG)));
        this.TAG = getClass().getSimpleName();
        this.LOG.m385i(this.TAG, "onCreate");
        GetBundle();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.m_iScreenWidth = dm.widthPixels;
        this.m_iScreenHeight = dm.heightPixels;
        this.m_iItemSize = this.m_iScreenWidth / 6;
        this.m_TitleTextView = (TextView) findViewById(C0349R.id.m_TitleTextView);
        this.m_TitleTextView.setText(getString(C0349R.string.SET_PRINTER));
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_BackButton.setOnClickListener(new C03811());
        this.m_ProgressBar = (ProgressBar) findViewById(C0349R.id.loadThumbnailProgressBar);
        this.m_ProgressBar.setVisibility(0);
        this.m_ProgressBar.setVisibility(8);
        this.m_ProgressBar.setIndeterminate(true);
        this.m_strSettingList = new LinkedList();
        SetSettingItem();
        this.m_SettingListView = (ListView) findViewById(C0349R.id.m_SettingListView);
        this.m_SettingListView.setOnItemClickListener(new C03822());
        this.m_SettingListView.setCacheColorHint(0);
        if (this.m_SettingAdapter == null) {
            this.m_SettingAdapter = new SettingAdapter();
        }
        this.m_SettingListView.setAdapter(this.m_SettingAdapter);
        this.m_SettingAdapter.notifyDataSetChanged();
        this.m_SettingHandler = new SettingHandler();
    }

    private void GetBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.IP = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP);
            this.m_iPort = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT);
            this.m_strConn = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_WIFI_CONN_MODE);
        } else {
            this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
            this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        }
        this.LOG.m385i(this.TAG + "--IP", String.valueOf(this.IP));
        this.LOG.m385i(this.TAG + "--m_iPort", String.valueOf(this.m_iPort));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.LOG.m384e("onActivity for result", "onActivity for result");
        if (requestCode == this.iItem_ap_wifi_setting) {
            if (resultCode == AP_SETTING_CHANGE) {
                setResult(WIFI_SETTING_CHANGE, data);
                finish();
            }
        } else if (requestCode == this.iItem_infra_wifi_setting) {
            if (resultCode == INFRA_SETTING_CHANGE) {
                setResult(WIFI_SETTING_CHANGE, data);
                finish();
            }
        } else if (requestCode == this.iItem_firmware_version) {
            finish();
        }
    }

    protected void onStart() {
        this.LOG.m385i("onStart()", "SettingActivity");
        super.onStart();
        getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        if (this.m_HitiPPR_GetAllSetting != null) {
            this.m_HitiPPR_GetAllSetting.Stop();
        }
        this.m_HitiPPR_GetAllSetting = new HitiPPR_GetAllSetting(this, this.IP, this.m_iPort, this.m_SettingHandler);
        this.m_HitiPPR_GetAllSetting.start();
        if (this.m_CheckBurnFWAlertDialog != null && this.m_CheckBurnFWAlertDialog.isShowing()) {
            this.m_CheckBurnFWAlertDialog.cancel();
        }
        CreateSettingHintDialog();
        this.m_BackButton.setEnabled(false);
    }

    protected void onResume() {
        this.LOG.m385i(this.TAG, "onResume");
        super.onResume();
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07923());
    }

    protected void onPause() {
        this.LOG.m385i("onPause()", "SettingActivity");
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
        if (this.m_HitiPPR_GetAllSetting != null) {
            this.m_HitiPPR_GetAllSetting.Stop();
            this.m_HitiPPR_GetAllSetting.CloseSocket();
        }
    }

    protected void onStop() {
        super.onStop();
        this.LOG.m385i("onStop()", "SettingActivity");
    }

    public void onDestroy() {
        super.onDestroy();
        this.LOG.m385i("onDestroy", "SettingActivity");
    }

    public void onBackButtonClicked(View v) {
        this.LOG.m385i(this.TAG, "onBackButtonClicked");
        Intent intent = new Intent();
        if (this.m_SSID.length() > 0) {
            Bundle bundle = new Bundle();
            bundle.putString(JumpBundleMessage.BUNDLE_MSG_SSID, this.m_SSID);
            bundle.putString(JumpBundleMessage.BUNDLE_MSG_PASSWORD, this.m_Password);
            intent.putExtras(bundle);
        }
        setResult(24, intent);
        finish();
    }

    public void onBackPressed() {
        this.LOG.m385i("onBackPressed", "SettingActivity");
        onBackButtonClicked(null);
    }

    void SetSettingItem() {
        this.SETTING_ITEM_FIRMWARE_VERSION = getString(C0349R.string.FIRMWARE_VERSION);
        this.SETTING_ITEM_PRINTER_STATUS = getString(C0349R.string.PRINTER_STATUS);
        this.SETTING_ITEM_AUTO_POWER_OFF = getString(C0349R.string.AUTO_POWER_OFF);
        this.SETTING_ITEM_PRINTER_WIFI_SETTINGS = getString(C0349R.string.PRINTER_WIFI_SETTINGS);
        this.SETTING_ITEM_INFRA_WIFI_SETTINGS = getString(C0349R.string.INFRA_WIFI_SETTINGS);
        this.SETTING_ITEM_ABOUT_APP = getString(C0349R.string.ABOUT_APP);
        this.m_strSettingList.add(this.SETTING_ITEM_FIRMWARE_VERSION);
        this.m_strSettingList.add(this.SETTING_ITEM_PRINTER_STATUS);
        this.m_strSettingList.add(this.SETTING_ITEM_AUTO_POWER_OFF);
        this.m_strSettingList.add(this.SETTING_ITEM_PRINTER_WIFI_SETTINGS);
        this.m_strSettingList.add(this.SETTING_ITEM_INFRA_WIFI_SETTINGS);
        this.m_strSettingList.add(this.SETTING_ITEM_ABOUT_APP);
        this.m_iTotalSetting = this.m_strSettingList.size();
    }

    public void onSettingListViewItemClicked(AdapterView<?> adapterView, View v, int position, long id) {
        this.LOG.m385i(this.TAG + " SettingName", (String) this.m_strSettingList.get(position));
        DispatchCommand(position);
    }

    int GetWhatSettingItem(int id) {
        if (id == this.iItem_firmware_version) {
            return this.iItem_firmware_version;
        }
        if (id == this.iItem_printer_status) {
            return this.iItem_printer_status;
        }
        if (id == this.iItem_auto_power_off) {
            return this.iItem_auto_power_off;
        }
        if (id == this.iItem_ap_wifi_setting) {
            return this.iItem_ap_wifi_setting;
        }
        if (id == this.iItem_infra_wifi_setting) {
            return this.iItem_infra_wifi_setting;
        }
        if (id == this.iItem_about_app) {
            return this.iItem_about_app;
        }
        return -1;
    }

    void DispatchCommand(int position) {
        Intent intent;
        Bundle bundle;
        if (position == this.iItem_firmware_version) {
            if (this.m_boNewFW) {
                intent = new Intent(getApplicationContext(), BurnFirmwareActivity.class);
                bundle = new Bundle();
                bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIRELESS_TYPE, this.m_strProductIDString);
                bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
                bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
                intent.putExtras(bundle);
                startActivityForResult(intent, this.iItem_firmware_version);
            }
        } else if (position == this.iItem_printer_status) {
        } else {
            if (this.iItem_auto_power_off != -1 && position == this.iItem_auto_power_off) {
                this.LOG.m384e(this.TAG + " DispatchCommand", "m_strProductIDString = " + String.valueOf(this.m_strProductIDString));
                intent = new Intent(getApplicationContext(), SetAutoPowerOffActivity.class);
                bundle = new Bundle();
                bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIRELESS_TYPE, this.m_strProductIDString);
                bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_AUTO_SECONDS, this.m_iAutoSeconds);
                bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
                bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
                intent.putExtras(bundle);
                startActivityForResult(intent, this.iItem_auto_power_off);
            } else if (position == this.iItem_ap_wifi_setting) {
                if (this.m_strConn.equals("Infra")) {
                    Toast.makeText(this, getString(C0349R.string.MSG_MODE_WARNING), 0).show();
                    return;
                }
                intent = new Intent(getApplicationContext(), PrinterWifiSettingActivity.class);
                bundle = new Bundle();
                bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
                bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
                bundle.putString(JumpBundleMessage.BUNDLE_MSG_PRINTER_PRODUCT_ID, this.m_strProductIDString);
                intent.putExtras(bundle);
                startActivityForResult(intent, this.iItem_ap_wifi_setting);
            } else if (position == this.iItem_infra_wifi_setting) {
                if (this.m_boNewFW) {
                    ShowBurnFWDialog(getString(C0349R.string.MUST_UPDATE_FIRMWARE));
                    return;
                }
                intent = new Intent(getApplicationContext(), InfraWifiSettingActivity.class);
                bundle = new Bundle();
                bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
                bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
                bundle.putString(JumpBundleMessage.BUNDLE_MSG_SSID, this.m_SSID);
                bundle.putString(JumpBundleMessage.BUNDLE_MSG_PASSWORD, this.m_Password);
                intent.putExtras(bundle);
                startActivityForResult(intent, this.iItem_infra_wifi_setting);
            } else if (position != this.iItem_about_app) {
            }
        }
    }

    void CreateSettingHintDialog() {
        if (this.m_ShowMSGDialog == null) {
            this.m_ShowMSGDialog = new ShowMSGDialog(this, true);
            this.m_ShowMSGDialog.SetDialogListener(new C07934());
        }
        this.m_ShowMSGDialog.ShowWaitingHintDialog(ThreadMode.GetInfo, getString(C0349R.string.GETTING_DATA_FROM_PRINTER));
    }

    byte CheckBit(byte bCheck, byte bMask) {
        return (byte) (bCheck & bMask);
    }

    void SendMessage(int iMsg, String strMsg) {
        Message msg = Message.obtain();
        msg.what = iMsg;
        if (strMsg != null) {
            Bundle buddle = new Bundle();
            buddle.putString(MSGHandler.MSG, strMsg);
            msg.setData(buddle);
        }
        this.m_SettingHandler.sendMessage(msg);
    }

    public void ShowErrorDialog(String strMessage, String strTitle, int iResId) {
        Builder errorDialog = new Builder(this);
        errorDialog.setCancelable(false);
        errorDialog.setIcon(iResId);
        errorDialog.setTitle(strTitle);
        errorDialog.setMessage(strMessage);
        errorDialog.setPositiveButton(getString(C0349R.string.OK), new C03835());
        errorDialog.show();
    }

    String GetPrinterStatus(Byte bStatus) {
        if (bStatus.byteValue() == null) {
            return getString(C0349R.string.PRINTER_STATUS_INITIALIZING);
        }
        if (bStatus.byteValue() == 1) {
            return getString(C0349R.string.PRINTER_STATUS_IDLE);
        }
        if (bStatus.byteValue() == 2) {
            return getString(C0349R.string.PRINTER_STATUS_PRINTING);
        }
        if (bStatus.byteValue() == 3) {
            return getString(C0349R.string.PRINTER_STATUS_PAUSED);
        }
        return null;
    }

    String GetAutoPowerOff(int iSeconds) {
        Log.e("GetAutoPowerOff", String.valueOf(iSeconds));
        this.m_iAutoSeconds = iSeconds;
        if (iSeconds == 30) {
            return " 30 " + getString(C0349R.string.SECS);
        }
        if (iSeconds == 60) {
            return " 1 " + getString(C0349R.string.MIN);
        }
        if (iSeconds == SoapEnvelope.VER12) {
            return " 2 " + getString(C0349R.string.MINS);
        }
        if (iSeconds == 180) {
            return " 3 " + getString(C0349R.string.MINS);
        }
        if (iSeconds == 1200) {
            return " 20 " + getString(C0349R.string.MINS);
        }
        if (iSeconds == DNSConstants.DNS_TTL) {
            return " 60 " + getString(C0349R.string.MINS);
        }
        if (iSeconds == 0) {
            return " " + getString(C0349R.string.OFF);
        }
        return null;
    }

    public void ShowBurnFWDialog(String strMessage) {
        String strTitle = getString(C0349R.string.BurnFirmwareActivity_m_TitleTextView);
        if (this.m_CheckBurnFWAlertDialog == null) {
            this.m_CheckBurnFWAlertDialog = new Builder(this).create();
            this.m_CheckBurnFWAlertDialog.setCancelable(false);
            this.m_CheckBurnFWAlertDialog.setCanceledOnTouchOutside(false);
            this.m_CheckBurnFWAlertDialog.setIcon(17301569);
            this.m_CheckBurnFWAlertDialog.setTitle(strTitle);
            this.m_CheckBurnFWAlertDialog.setButton(-1, getString(C0349R.string.OK), new C03846());
            this.m_CheckBurnFWAlertDialog.setButton(-2, getString(C0349R.string.CANCEL), new C03857());
        }
        this.m_CheckBurnFWAlertDialog.setMessage(strMessage);
        this.m_CheckBurnFWAlertDialog.show();
    }

    private String ErrorMessageCheck(String strMessage) {
        if (strMessage.contains("010")) {
            return getString(C0349R.string.ERROR_PRINTER_0100_P310W);
        }
        return strMessage;
    }
}
