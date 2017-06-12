package com.hiti.prinbiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.media.TransportMediator;
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
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.request.HitiPPR_SetAutoPowerOff;
import com.hiti.utility.LogManager;
import java.util.LinkedList;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.nntp.NNTPReply;
import org.xmlpull.v1.XmlPullParser;

public class SetAutoPowerOffActivity extends Activity {
    String AUTO_POWER_OFF_20_MINUTES;
    String AUTO_POWER_OFF_60_MINUTES;
    String AUTO_POWER_OFF_OFF;
    String IP;
    LogManager LOG;
    NFCInfo mNFCInfo;
    ImageButton m_ApplyToPrinterButton;
    AutoPowerOffAdapter m_AutoPowerOffAdapter;
    View m_AutoPowerOffDialogView;
    AlertDialog m_AutoPowerOffHintDialog;
    ListView m_AutoPowerOffListView;
    TextView m_AutoPowerOffMSGTextView;
    ProgressBar m_AutoPowerOffProgressBar;
    ImageButton m_BackButton;
    int m_CurrentSelectItem;
    HitiPPR_SetAutoPowerOff m_HitiPPR_SetAutoPowerOff;
    ProgressBar m_ProgressBar;
    SetAutoPowerOffHandler m_SetAutoPowerOffHandler;
    ImageView m_TitleImageView;
    TextView m_TitleTextView;
    int m_iItemSize;
    int m_iPort;
    int m_iScreenHeight;
    int m_iScreenWidth;
    private int m_iTotalAutoPowerOff;
    LinkedList<String> m_strAutoPowerOffList;
    private String m_strPrinterModel;

    /* renamed from: com.hiti.prinbiz.SetAutoPowerOffActivity.1 */
    class C03501 implements OnClickListener {
        C03501() {
        }

        public void onClick(View v) {
            SetAutoPowerOffActivity.this.onApplyToPrinterButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.prinbiz.SetAutoPowerOffActivity.2 */
    class C03512 implements OnClickListener {
        C03512() {
        }

        public void onClick(View v) {
            SetAutoPowerOffActivity.this.onBackButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.prinbiz.SetAutoPowerOffActivity.3 */
    class C03523 implements OnItemClickListener {
        C03523() {
        }

        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            SetAutoPowerOffActivity.this.onSettingListViewItemClicked(a, v, position, id);
        }
    }

    /* renamed from: com.hiti.prinbiz.SetAutoPowerOffActivity.5 */
    class C03535 implements DialogInterface.OnClickListener {
        C03535() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            SetAutoPowerOffActivity.this.finish();
        }
    }

    public class AutoPowerOffAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        class ViewHolder {
            ImageView checkView;
            int id;
            TextView textView;

            ViewHolder() {
            }
        }

        public AutoPowerOffAdapter() {
            this.mInflater = (LayoutInflater) SetAutoPowerOffActivity.this.getSystemService("layout_inflater");
        }

        public int getCount() {
            return SetAutoPowerOffActivity.this.m_iTotalAutoPowerOff;
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
                convertView = this.mInflater.inflate(C0349R.layout.item_auto_power_off, null);
                holder.textView = (TextView) convertView.findViewById(C0349R.id.txtAutoPowerOffName);
                holder.textView.setHeight(SetAutoPowerOffActivity.this.m_iItemSize);
                holder.textView.setWidth(SetAutoPowerOffActivity.this.m_iScreenWidth / 3);
                holder.checkView = (ImageView) convertView.findViewById(C0349R.id.CheckView);
                holder.checkView.setImageResource(0);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.checkView.setImageResource(C0349R.drawable.check_button);
            if (SetAutoPowerOffActivity.this.m_CurrentSelectItem == position) {
                holder.checkView.setImageResource(C0349R.drawable.check_button_clicked);
            }
            holder.checkView.setId(position);
            holder.textView.setId(position);
            holder.id = position;
            holder.textView.setText((CharSequence) SetAutoPowerOffActivity.this.m_strAutoPowerOffList.get(position));
            return convertView;
        }
    }

    /* renamed from: com.hiti.prinbiz.SetAutoPowerOffActivity.4 */
    class C07804 implements INfcPreview {
        C07804() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            SetAutoPowerOffActivity.this.mNFCInfo = nfcInfo;
        }
    }

    class SetAutoPowerOffHandler extends MSGHandler {
        SetAutoPowerOffHandler() {
        }

        public void handleMessage(Message msg) {
            Bundle buddle;
            String strTitle;
            String strMSG;
            switch (msg.what) {
                case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                    SetAutoPowerOffActivity.this.m_AutoPowerOffHintDialog.dismiss();
                    SetAutoPowerOffActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    buddle = msg.getData();
                    strTitle = SetAutoPowerOffActivity.this.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    SetAutoPowerOffActivity.this.ShowAlertDialog(strMSG, strTitle, 17301569);
                case NNTPReply.SEND_ARTICLE_TO_POST /*340*/:
                    SetAutoPowerOffActivity.this.m_AutoPowerOffHintDialog.dismiss();
                    SetAutoPowerOffActivity.this.ShowAlertDialog(SetAutoPowerOffActivity.this.getString(C0349R.string.CURRENT_AUTO_POWER_OFF_TIME) + ((String) SetAutoPowerOffActivity.this.m_strAutoPowerOffList.get(SetAutoPowerOffActivity.this.m_CurrentSelectItem)), SetAutoPowerOffActivity.this.getString(C0349R.string.SET_AUTO_POWER_SUCCESS), 17301569);
                    SetAutoPowerOffActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                case RequestState.REQUEST_AUTO_POWER_OFF_ERROR /*341*/:
                    SetAutoPowerOffActivity.this.m_AutoPowerOffHintDialog.dismiss();
                    buddle = msg.getData();
                    strTitle = SetAutoPowerOffActivity.this.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    SetAutoPowerOffActivity.this.ShowAlertDialog(strMSG, strTitle, 17301560);
                    SetAutoPowerOffActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                default:
            }
        }
    }

    public SetAutoPowerOffActivity() {
        this.AUTO_POWER_OFF_OFF = null;
        this.AUTO_POWER_OFF_20_MINUTES = null;
        this.AUTO_POWER_OFF_60_MINUTES = null;
        this.m_AutoPowerOffHintDialog = null;
        this.m_AutoPowerOffDialogView = null;
        this.m_AutoPowerOffProgressBar = null;
        this.m_AutoPowerOffMSGTextView = null;
        this.m_SetAutoPowerOffHandler = null;
        this.m_strAutoPowerOffList = null;
        this.m_AutoPowerOffAdapter = null;
        this.m_AutoPowerOffListView = null;
        this.m_iTotalAutoPowerOff = 0;
        this.m_TitleImageView = null;
        this.m_BackButton = null;
        this.m_ApplyToPrinterButton = null;
        this.m_TitleTextView = null;
        this.m_ProgressBar = null;
        this.m_iScreenWidth = 0;
        this.m_iScreenHeight = 0;
        this.m_iItemSize = 0;
        this.m_CurrentSelectItem = -1;
        this.m_HitiPPR_SetAutoPowerOff = null;
        this.m_strPrinterModel = null;
        this.IP = XmlPullParser.NO_NAMESPACE;
        this.m_iPort = 0;
        this.mNFCInfo = null;
        this.LOG = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0349R.layout.activity_auto_power_off);
        this.LOG = new LogManager(0);
        this.LOG.m385i("onCreate SettingActivity", "onCreate SettingActivity");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.m_iScreenWidth = dm.widthPixels;
        this.m_iScreenHeight = dm.heightPixels;
        this.m_iItemSize = this.m_iScreenWidth / 6;
        GetBundle();
        this.m_TitleTextView = (TextView) findViewById(C0349R.id.m_TitleTextView);
        this.m_TitleTextView.setText((String) getResources().getText(C0349R.string.AutoPowerOff_Title));
        this.m_ApplyToPrinterButton = (ImageButton) findViewById(C0349R.id.m_ApplyToPrinterButton);
        this.m_ApplyToPrinterButton.setOnClickListener(new C03501());
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_BackButton.setOnClickListener(new C03512());
        this.m_ProgressBar = (ProgressBar) findViewById(C0349R.id.loadThumbnailProgressBar);
        this.m_ProgressBar.setVisibility(0);
        this.m_ProgressBar.setVisibility(8);
        this.m_ProgressBar.setIndeterminate(true);
        this.m_strAutoPowerOffList = new LinkedList();
        SetSettingItem();
        this.m_AutoPowerOffListView = (ListView) findViewById(C0349R.id.m_AutoPowerOffListView);
        this.m_AutoPowerOffListView.setOnItemClickListener(new C03523());
        this.m_AutoPowerOffListView.setCacheColorHint(0);
        if (this.m_AutoPowerOffAdapter == null) {
            this.m_AutoPowerOffAdapter = new AutoPowerOffAdapter();
        }
        this.m_AutoPowerOffListView.setAdapter(this.m_AutoPowerOffAdapter);
        this.m_AutoPowerOffAdapter.notifyDataSetChanged();
        this.m_SetAutoPowerOffHandler = new SetAutoPowerOffHandler();
    }

    protected void onStart() {
        super.onStart();
        this.LOG.m385i("onStart()", "AutoPowerOffActivity");
    }

    protected void onResume() {
        this.LOG.m385i("onResume()", "AutoPowerOffActivity");
        super.onResume();
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07804());
    }

    protected void onPause() {
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        this.LOG.m385i("onStop()", "AutoPowerOffActivity");
    }

    public void onDestroy() {
        super.onDestroy();
        this.LOG.m385i("onDestroy", "AutoPowerOffActivity");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void GetBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.m_strPrinterModel = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_WIRELESS_TYPE);
            int iAutoSeconds = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_AUTO_SECONDS);
            this.IP = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP);
            this.m_iPort = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT);
            switch (iAutoSeconds) {
                case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                    this.m_CurrentSelectItem = 0;
                case 1200:
                    this.m_CurrentSelectItem = 1;
                case DNSConstants.DNS_TTL /*3600*/:
                    this.m_CurrentSelectItem = 2;
                default:
            }
        }
    }

    public void onBackButtonClicked(View v) {
        setResult(0, new Intent());
        finish();
    }

    public void onApplyToPrinterButtonClicked(View v) {
        if (this.m_CurrentSelectItem != -1) {
            CreateAutoPowerOffHintDialog();
            this.m_AutoPowerOffHintDialog.show();
            getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
            this.m_HitiPPR_SetAutoPowerOff = new HitiPPR_SetAutoPowerOff(this, this.IP, this.m_iPort, this.m_SetAutoPowerOffHandler);
            int iSelect = 0;
            if (this.m_strPrinterModel.equals(WirelessType.TYPE_P310W) && this.m_CurrentSelectItem != 0) {
                iSelect = this.m_CurrentSelectItem + 4;
            }
            Log.e("iSelect", String.valueOf(iSelect));
            this.m_HitiPPR_SetAutoPowerOff.SetAutoPowerOffSeconds(HitiPPR_SetAutoPowerOff.IntToAUTO_POWER_OFF(iSelect));
            this.m_HitiPPR_SetAutoPowerOff.start();
        }
    }

    void SetSettingItem() {
        this.AUTO_POWER_OFF_OFF = getString(C0349R.string.OFF);
        this.AUTO_POWER_OFF_20_MINUTES = getString(C0349R.string.TWENTY_MINUTES);
        this.AUTO_POWER_OFF_60_MINUTES = getString(C0349R.string.SIXTY_MINUTES);
        this.m_strAutoPowerOffList.add(this.AUTO_POWER_OFF_OFF);
        this.m_strAutoPowerOffList.add(this.AUTO_POWER_OFF_20_MINUTES);
        this.m_strAutoPowerOffList.add(this.AUTO_POWER_OFF_60_MINUTES);
        this.m_iTotalAutoPowerOff = this.m_strAutoPowerOffList.size();
    }

    public void onSettingListViewItemClicked(AdapterView<?> adapterView, View v, int position, long id) {
        this.LOG.m385i("m_strAutoPowerOffList", (String) this.m_strAutoPowerOffList.get(position));
        this.m_CurrentSelectItem = position;
        this.m_AutoPowerOffAdapter.notifyDataSetChanged();
    }

    void CreateAutoPowerOffHintDialog() {
        if (this.m_AutoPowerOffHintDialog == null) {
            this.m_AutoPowerOffHintDialog = new Builder(this).create();
            this.m_AutoPowerOffHintDialog.setCanceledOnTouchOutside(false);
            this.m_AutoPowerOffHintDialog.setCancelable(false);
            this.m_AutoPowerOffHintDialog.setTitle(C0349R.string.SETTING_DATA_TO_PRINTER);
            this.m_AutoPowerOffDialogView = getLayoutInflater().inflate(C0349R.layout.dialog_auto_power_off, null);
            this.m_AutoPowerOffProgressBar = (ProgressBar) this.m_AutoPowerOffDialogView.findViewById(C0349R.id.m_AutoPowerOffProgressBar);
            this.m_AutoPowerOffProgressBar.setVisibility(0);
            this.m_AutoPowerOffProgressBar.setIndeterminate(true);
            this.m_AutoPowerOffMSGTextView = (TextView) this.m_AutoPowerOffDialogView.findViewById(C0349R.id.m_AutoPowerOffMSGTextView);
            this.m_AutoPowerOffMSGTextView.setText("\n" + getString(C0349R.string.PLEASE_WAIT) + "\n");
            this.m_AutoPowerOffHintDialog.setView(this.m_AutoPowerOffDialogView);
        }
    }

    public void ShowAlertDialog(String strMessage, String strTitle, int iResId) {
        Builder alertDialog = new Builder(this);
        alertDialog.setIcon(iResId);
        alertDialog.setTitle(strTitle);
        alertDialog.setMessage(strMessage);
        alertDialog.setPositiveButton(getString(C0349R.string.OK), new C03535());
        alertDialog.show();
    }
}
