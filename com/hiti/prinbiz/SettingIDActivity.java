package com.hiti.prinbiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.utility.PrinterInfo;
import com.hiti.trace.GlobalVariable_PrintSettingInfo;
import com.hiti.ui.drawview.garnishitem.PathLoader.LoadFinishListener;
import com.hiti.ui.drawview.garnishitem.PathLoader.ThumbnailLoader;
import com.hiti.ui.drawview.garnishitem.security.GarnishSecurity;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.dialog.ShowMSGDialog;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class SettingIDActivity extends Activity {
    OnClickListener BackListener;
    LogManager LOG;
    OnClickListener OKListener;
    OnItemSelectedListener RegionItemListen;
    OnItemSelectedListener StyleItemListen;
    String TAG;
    OnItemSelectedListener UIItemListen;
    private String mCanadaReginFolderName;
    NFCInfo mNFCInfo;
    private String mRouteFrom;
    private String mSelectedItemLastPath;
    private String mSelectedItemRootPath;
    private ImageButton m_BackButton;
    private GarnishSecurity m_HCollageGarnishSecurity;
    private RadioButton m_HODItem;
    private RadioGroup m_HODRadioGroup;
    private IDLoader m_IDLoader;
    private ImageButton m_InfoHODImageView;
    private RelativeLayout m_MethodLayout;
    private LinearLayout m_NumberLinearLayout;
    private ImageButton m_OKButton;
    private ArrayAdapter<String> m_RegionAdapter;
    private Spinner m_RegionSpinner;
    private RelativeLayout m_SharpenLayout;
    private SeekBar m_SharpenSeekBar;
    private ShowMSGDialog m_ShowMSGDialog;
    private ArrayAdapter<String> m_StyleAdapter;
    private Spinner m_StyleSpinner;
    private ArrayAdapter<String> m_UIAdapter;
    private Spinner m_UISpinner;
    private ProgressBar m_WaitingCircle;
    private boolean m_bOK;
    private JumpPreferenceKey m_pref;
    private int m_prefMethod;
    private int m_prefSharpenValue;
    private ArrayList<String> m_strCollageFilePathList;
    private ArrayList<String> m_strIDstyleNameList;
    private String m_strIdStyle;
    private String m_strIdpath;
    private String m_strLastIdStyle;
    private String m_strLastRegion;
    private String m_strModel;
    private ArrayList<String> m_strReginFolderList;
    private ArrayList<String> m_strRegionNameList;
    private String m_strSelectedRegion;
    private String[] m_strUISizeList;
    private GlobalVariable_PrintSettingInfo pref;

    /* renamed from: com.hiti.prinbiz.SettingIDActivity.2 */
    class C03682 implements OnItemSelectedListener {
        C03682() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            SettingIDActivity.this.LOG.m383d(SettingIDActivity.this.TAG, "onItemSelected id=" + position);
            SettingIDActivity.this.m_strSelectedRegion = (String) SettingIDActivity.this.m_strReginFolderList.get(position);
            SettingIDActivity.this.GetIDStyleList();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingIDActivity.3 */
    class C03693 implements OnClickListener {
        C03693() {
        }

        public void onClick(View v) {
            SettingIDActivity.this.m_ShowMSGDialog.ShowHODInfoDialog();
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingIDActivity.4 */
    class C03704 implements OnCheckedChangeListener {
        C03704() {
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == C0349R.id.m_NormalItem) {
                SettingIDActivity.this.m_prefMethod = 0;
            } else {
                SettingIDActivity.this.m_prefMethod = 1;
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingIDActivity.5 */
    class C03715 implements OnSeekBarChangeListener {
        C03715() {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            SettingIDActivity.this.m_prefSharpenValue = progress;
            SettingIDActivity.this.SetNumberOnSharpenBar(progress);
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingIDActivity.6 */
    class C03726 implements OnItemSelectedListener {
        C03726() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            if (SettingIDActivity.this.m_strCollageFilePathList.size() > position) {
                SettingIDActivity.this.m_strIdpath = (String) SettingIDActivity.this.m_strCollageFilePathList.get(position);
                SettingIDActivity.this.pref.SetIDpath(SettingIDActivity.this.m_strIdpath);
                SettingIDActivity.this.m_strIdStyle = ((String) SettingIDActivity.this.m_strIDstyleNameList.get(position)).replace("/", "_");
                SettingIDActivity.this.pref.SetIDstyle(SettingIDActivity.this.m_strIdStyle);
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingIDActivity.7 */
    class C03737 implements OnItemSelectedListener {
        C03737() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            SettingIDActivity.this.pref.SetServerPaperType(PrinterInfo.GetPaperTypeByName(SettingIDActivity.this.m_strUISizeList[position]));
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingIDActivity.8 */
    class C03748 implements OnClickListener {
        C03748() {
        }

        public void onClick(View v) {
            SettingIDActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingIDActivity.9 */
    class C03759 implements OnClickListener {
        C03759() {
        }

        public void onClick(View v) {
            if (SettingIDActivity.this.m_bOK) {
                if (SettingIDActivity.this.m_pref == null) {
                    SettingIDActivity.this.m_pref = new JumpPreferenceKey(SettingIDActivity.this);
                }
                SettingIDActivity.this.pref.SetPrintMethod(SettingIDActivity.this.m_prefMethod);
                SettingIDActivity.this.pref.SetPrintSharpen(SettingIDActivity.this.m_prefSharpenValue);
                SettingIDActivity.this.pref.SaveGlobalVariable();
                if (SettingIDActivity.this.m_strIdStyle == null || SettingIDActivity.this.m_strIdStyle.isEmpty()) {
                    SettingIDActivity.this.pref.RestoreGlobalVariable();
                    SettingIDActivity.this.pref.SetIDregion(SettingIDActivity.this.m_strSelectedRegion);
                    SettingIDActivity.this.pref.SetIDpath((String) SettingIDActivity.this.m_strCollageFilePathList.get(0));
                    SettingIDActivity.this.pref.SetIDstyle(((String) SettingIDActivity.this.m_strIDstyleNameList.get(0)).replace("/", "_"));
                    SettingIDActivity.this.pref.SaveGlobalVariable();
                }
                SettingIDActivity.this.LOG.m383d("OKListener mRouteFrom", String.valueOf(SettingIDActivity.this.mRouteFrom));
                if (SettingIDActivity.this.mRouteFrom.equals(JumpPreferenceKey.ID_FROM_MAIN)) {
                    SettingIDActivity.this.LOG.m383d("OKListener ", "goto SourceActivity");
                    SettingIDActivity.this.startActivityForResult(new Intent(SettingIDActivity.this, SourceActivity.class), 0);
                    return;
                }
                SettingIDActivity.this.onBackPressed();
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingIDActivity.1 */
    class C07911 implements INfcPreview {
        C07911() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            SettingIDActivity.this.mNFCInfo = nfcInfo;
            if (SettingIDActivity.this.m_strModel.equals(WirelessType.TYPE_P310W)) {
                int iPos = SettingIDActivity.this.m_strReginFolderList.indexOf(SettingIDActivity.this.m_strSelectedRegion);
                SettingIDActivity.this.LOG.m383d(SettingIDActivity.this.TAG, "LastIdRegion: " + iPos);
                if (iPos == -1) {
                    SettingIDActivity.this.m_strSelectedRegion = (String) SettingIDActivity.this.m_strReginFolderList.get(0);
                    SettingIDActivity.this.m_RegionSpinner.setSelection(0);
                    return;
                }
                SettingIDActivity.this.m_RegionSpinner.setSelection(iPos);
            }
        }
    }

    private class IDLoader extends ThumbnailLoader {
        public IDLoader(Context context, GarnishSecurity security, LoadFinishListener loadFinishListener) {
            super(context, security, loadFinishListener);
        }

        public void BeforeLoadFinish() {
        }

        public void LoadFinish() {
            if (SettingIDActivity.this.m_strCollageFilePathList != null) {
                SettingIDActivity.this.m_strIDstyleNameList.clear();
                Iterator it = SettingIDActivity.this.m_strCollageFilePathList.iterator();
                while (it.hasNext()) {
                    String filename = FileUtility.GetFileNameWithoutExt((String) it.next());
                    if (filename.contains("_")) {
                        filename = filename.replace("_", "/");
                    }
                    SettingIDActivity.this.m_strIDstyleNameList.add(filename);
                }
                SettingIDActivity.this.LOG.m383d(SettingIDActivity.this.TAG, "ID loader LoadFinish");
                SettingIDActivity.this.InitStyleSpinner();
            }
        }
    }

    public SettingIDActivity() {
        this.m_StyleSpinner = null;
        this.m_UISpinner = null;
        this.m_RegionSpinner = null;
        this.m_BackButton = null;
        this.m_OKButton = null;
        this.m_InfoHODImageView = null;
        this.m_NumberLinearLayout = null;
        this.m_WaitingCircle = null;
        this.m_SharpenSeekBar = null;
        this.m_SharpenLayout = null;
        this.m_MethodLayout = null;
        this.m_HODRadioGroup = null;
        this.m_HODItem = null;
        this.m_StyleAdapter = null;
        this.m_UIAdapter = null;
        this.m_RegionAdapter = null;
        this.pref = null;
        this.m_pref = null;
        this.m_HCollageGarnishSecurity = null;
        this.m_IDLoader = null;
        this.m_strCollageFilePathList = null;
        this.m_strIDstyleNameList = null;
        this.m_strRegionNameList = null;
        this.m_strReginFolderList = null;
        this.m_strUISizeList = new String[]{"4x6"};
        this.m_strIdStyle = null;
        this.m_strSelectedRegion = null;
        this.m_strLastRegion = null;
        this.m_strLastIdStyle = null;
        this.m_strIdpath = null;
        this.m_strModel = null;
        this.m_prefSharpenValue = 1;
        this.m_prefMethod = 0;
        this.m_ShowMSGDialog = null;
        this.m_bOK = false;
        this.mCanadaReginFolderName = null;
        this.mSelectedItemRootPath = null;
        this.mSelectedItemLastPath = null;
        this.mRouteFrom = JumpPreferenceKey.ID_FROM_SETTING;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = null;
        this.RegionItemListen = new C03682();
        this.StyleItemListen = new C03726();
        this.UIItemListen = new C03737();
        this.BackListener = new C03748();
        this.OKListener = new C03759();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_setting_id);
        this.LOG = new LogManager(0);
        this.TAG = getClass().getSimpleName();
        this.LOG.m383d(this.TAG, "onCreate");
        GetPref();
        SetView();
        String itemPath = PringoConvenientConst.H_ID_PHOTO_PATH_4X6;
        this.mSelectedItemRootPath = itemPath.substring(0, itemPath.lastIndexOf("/") + 1);
        this.mSelectedItemLastPath = itemPath.substring(itemPath.lastIndexOf("/"));
        if (this.m_strModel.equals(WirelessType.TYPE_P310W)) {
            InitRegionSpinner();
            return;
        }
        this.m_strSelectedRegion = PringoConvenientConst.H_ID_PHOTO_PATH_COMM;
        GetIDStyleList();
    }

    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        this.LOG.m383d(this.TAG, "onResume");
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07911());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 50) {
            setResult(50);
            finish();
        }
    }

    protected void onPause() {
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    private void GetPref() {
        this.pref = new GlobalVariable_PrintSettingInfo(this, ControllerState.PLAY_COUNT_STATE);
        this.pref.RestoreGlobalVariable();
        this.m_strLastRegion = this.pref.GetIDregion();
        this.m_strLastIdStyle = this.pref.GetIDstyle().replace("_", "/");
        this.m_prefSharpenValue = this.pref.GetPrintSharpen();
        this.m_prefMethod = this.pref.GetPrintMethod();
        this.m_strSelectedRegion = this.m_strLastRegion;
        this.m_pref = new JumpPreferenceKey(this);
        this.m_strModel = this.m_pref.GetModelPreference();
        if (getIntent() != null) {
            String from = getIntent().getStringExtra(JumpBundleMessage.BUNDLE_MSG_ID_ROUTE_FROM);
            this.mRouteFrom = from != null ? from : this.mRouteFrom;
            this.LOG.m383d(this.TAG, "GetPref " + from);
        }
    }

    private void InitRegionSpinner() {
        this.LOG.m383d(this.TAG, "InitRegionSpinner");
        this.m_strRegionNameList = new ArrayList();
        this.m_strReginFolderList = new ArrayList();
        String[] strRegionNameList = getResources().getStringArray(C0349R.array.ID_REGION);
        String[] strReginFolderList = getResources().getStringArray(C0349R.array.REGION_FOLDER);
        for (String name : strRegionNameList) {
            this.m_strRegionNameList.add(name);
        }
        for (String folder : strReginFolderList) {
            this.m_strReginFolderList.add(folder);
        }
        this.mCanadaReginFolderName = (String) this.m_strReginFolderList.get(1);
        this.m_RegionSpinner.setVisibility(0);
        this.m_RegionAdapter = new ArrayAdapter(this, C0349R.layout.item_spinner, 16908308, this.m_strRegionNameList);
        this.m_RegionAdapter.setDropDownViewResource(17367050);
        this.m_RegionSpinner.setAdapter(this.m_RegionAdapter);
        this.m_RegionSpinner.setOnItemSelectedListener(this.RegionItemListen);
        this.LOG.m383d(this.TAG, "InitRegionSpinner end");
    }

    private void GetIDStyleList() {
        this.m_WaitingCircle.setVisibility(0);
        this.pref.SetIDregion(this.m_strSelectedRegion);
        this.m_HCollageGarnishSecurity = new GarnishSecurity(this);
        GarnishSecurity security = this.m_HCollageGarnishSecurity;
        String itemPath = XmlPullParser.NO_NAMESPACE;
        StringBuilder strBuilder = new StringBuilder(this.mSelectedItemRootPath);
        if (this.m_strModel.equals(WirelessType.TYPE_P310W)) {
            itemPath = strBuilder.append(this.m_strSelectedRegion.equals(this.mCanadaReginFolderName) ? (String) this.m_strReginFolderList.get(7) : this.m_strSelectedRegion).append(this.mSelectedItemLastPath).toString();
        } else {
            itemPath = this.m_strSelectedRegion;
        }
        if (this.m_IDLoader != null) {
            this.m_IDLoader.Stop();
            this.m_IDLoader = null;
        }
        if (this.m_strCollageFilePathList == null) {
            this.m_strCollageFilePathList = new ArrayList();
        }
        this.m_IDLoader = new IDLoader(this, security, null);
        this.m_IDLoader.LoadThumbnailFromAssets(itemPath + "/" + PringoConvenientConst.THUMB, this.m_strCollageFilePathList);
        this.LOG.m383d(this.TAG, "IDLoader start");
        this.m_strIDstyleNameList = new ArrayList();
        this.m_IDLoader.execute(new String[0]);
    }

    private void InitStyleSpinner() {
        this.LOG.m383d(this.TAG, "InitStyleSpinner");
        this.m_StyleAdapter = new ArrayAdapter(this, C0349R.layout.item_spinner, 16908308, this.m_strIDstyleNameList);
        this.m_StyleAdapter.setDropDownViewResource(17367050);
        this.m_StyleSpinner.setAdapter(this.m_StyleAdapter);
        this.m_StyleSpinner.setOnItemSelectedListener(this.StyleItemListen);
        int pos;
        if (!this.m_strModel.equals(WirelessType.TYPE_P310W)) {
            pos = this.m_strIDstyleNameList.indexOf(this.m_strLastIdStyle);
            if (pos != -1) {
                this.m_StyleSpinner.setSelection(pos);
            }
        } else if (this.m_strSelectedRegion.equals(this.m_strLastRegion)) {
            pos = this.m_strIDstyleNameList.indexOf(this.m_strLastIdStyle);
            if (pos != -1) {
                this.m_StyleSpinner.setSelection(pos);
            }
            this.m_strLastRegion = XmlPullParser.NO_NAMESPACE;
        } else {
            this.m_StyleSpinner.setSelection(0);
        }
        this.m_WaitingCircle.setVisibility(8);
        this.m_bOK = true;
        this.LOG.m383d(this.TAG, "InitStyleSpinner done");
    }

    private void SetView() {
        this.pref = new GlobalVariable_PrintSettingInfo(getBaseContext(), ControllerState.PLAY_COUNT_STATE);
        this.m_StyleSpinner = (Spinner) findViewById(C0349R.id.m_StyleSpinner);
        this.m_UISpinner = (Spinner) findViewById(C0349R.id.m_UISpinner);
        this.m_RegionSpinner = (Spinner) findViewById(C0349R.id.m_RegionSpinner);
        this.m_SharpenLayout = (RelativeLayout) findViewById(C0349R.id.m_SharpenLayout);
        this.m_SharpenSeekBar = (SeekBar) findViewById(C0349R.id.m_SharpenSeekBar);
        this.m_NumberLinearLayout = (LinearLayout) findViewById(C0349R.id.m_NumberLinearLayout);
        this.m_MethodLayout = (RelativeLayout) findViewById(C0349R.id.m_MethodLayout);
        this.m_HODRadioGroup = (RadioGroup) findViewById(C0349R.id.m_HODRadioGroup);
        this.m_HODItem = (RadioButton) findViewById(C0349R.id.m_HODItem);
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_OKButton = (ImageButton) findViewById(C0349R.id.m_IdSetOKImageButton);
        this.m_InfoHODImageView = (ImageButton) findViewById(C0349R.id.m_InfoHODImageView);
        this.m_WaitingCircle = (ProgressBar) findViewById(C0349R.id.m_WaitingProgressBar);
        GetPrintMethod();
        GetShapenValue();
        this.m_WaitingCircle.setVisibility(8);
        this.m_RegionSpinner.setVisibility(8);
        this.m_BackButton.setOnClickListener(this.BackListener);
        this.m_OKButton.setOnClickListener(this.OKListener);
        this.m_UIAdapter = new ArrayAdapter(this, C0349R.layout.item_spinner, 16908308, this.m_strUISizeList);
        this.m_UIAdapter.setDropDownViewResource(17367050);
        this.m_UISpinner.setAdapter(this.m_UIAdapter);
        this.m_UISpinner.setEnabled(false);
        this.m_UISpinner.setAlpha(0.6f);
        this.m_UISpinner.setOnItemSelectedListener(this.UIItemListen);
        if (this.m_strModel.equals(WirelessType.TYPE_P750L)) {
            this.m_InfoHODImageView.setVisibility(8);
        }
        this.m_InfoHODImageView.setOnClickListener(new C03693());
        this.m_ShowMSGDialog = new ShowMSGDialog(this, false);
    }

    private void GetPrintMethod() {
        if (this.m_strModel.equals(WirelessType.TYPE_P310W) || this.m_strModel.equals(WirelessType.TYPE_P750L)) {
            this.m_MethodLayout.setVisibility(0);
            if (this.m_strModel.equals(WirelessType.TYPE_P310W)) {
                this.m_HODItem.setText(getString(C0349R.string.QTY_HOD));
            } else {
                this.m_HODItem.setText(getString(C0349R.string.QTY_REFINEMENT));
            }
            if (this.m_prefMethod == 0) {
                this.m_HODRadioGroup.check(C0349R.id.m_NormalItem);
            } else {
                this.m_HODRadioGroup.check(C0349R.id.m_HODItem);
            }
            this.m_HODRadioGroup.setOnCheckedChangeListener(new C03704());
            return;
        }
        this.m_MethodLayout.setVisibility(8);
    }

    private void GetShapenValue() {
        if (this.m_strModel.equals(WirelessType.TYPE_P310W)) {
            this.m_SharpenLayout.setVisibility(0);
            int width = getResources().getDrawable(C0349R.drawable.scale).getIntrinsicWidth();
            this.m_SharpenSeekBar.setProgress(this.m_prefSharpenValue);
            this.m_SharpenSeekBar.getLayoutParams().width = width;
            this.m_NumberLinearLayout.getLayoutParams().width = (width * 9) / 8;
            this.m_SharpenSeekBar.setOnSeekBarChangeListener(new C03715());
            SetNumberOnSharpenBar(this.m_prefSharpenValue);
            return;
        }
        this.m_SharpenLayout.setVisibility(8);
    }

    private void SetNumberOnSharpenBar(int iSelected) {
        this.m_NumberLinearLayout.removeAllViews();
        for (int i = 0; i <= 8; i++) {
            TextView numTextView = new TextView(this);
            numTextView.setBackgroundColor(getResources().getColor(17170445));
            numTextView.setTextColor(getResources().getColor(C0349R.color.SETTING_GRAY));
            if (i == iSelected) {
                numTextView.setTextColor(getResources().getColor(C0349R.color.SETTING_ORANGE));
            } else {
                numTextView.setTextColor(getResources().getColor(C0349R.color.SETTING_GRAY));
            }
            numTextView.setLayoutParams(new LayoutParams(-1, -2, 1.0f));
            numTextView.setTextSize(13.0f);
            numTextView.setPadding(5, 0, 5, 0);
            numTextView.setGravity(3);
            numTextView.setText(String.valueOf(i - 1));
            this.m_NumberLinearLayout.addView(numTextView);
        }
        this.m_NumberLinearLayout.invalidate();
    }

    public void onBackPressed() {
        setResult(50);
        super.onBackPressed();
    }
}
