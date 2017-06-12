package com.hiti.prinbiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.hiti.Flurry.FlurryLogString;
import com.hiti.Flurry.FlurryUtility;
import com.hiti.jumpinfo.FlowMode;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpInfo;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.service.upload.UploadService;
import com.hiti.trace.GlobalVariable_SaveLoadedMeta;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.permissionRequest.PermissionAsk;
import com.hiti.utility.permissionRequest.PermissionAsk.IPermission;
import java.util.ArrayList;
import java.util.Iterator;
import org.kxml2.wap.WbxmlParser;
import org.xmlpull.v1.XmlPullParser;

@SuppressLint({"NewApi"})
public class ModelActivity extends Activity {
    LogManager LOG;
    Runnable RunClose;
    String TAG;
    private Handler handler;
    int iPermissionButton;
    FlowMode mFlowMode;
    NFCInfo mNFCInfo;
    PermissionAsk mPermissionAsk;
    private Animation m_AnimFade;
    private RelativeLayout m_CoverRelativeLayout;
    private RelativeLayout m_MainRelativeLayout;
    private CheckBox m_NotAskAgainCheckBox;
    private ImageButton m_P310WButton;
    private ImageButton m_P520LButton;
    private ImageButton m_P530DButton;
    private ImageButton m_P750LButton;
    private Toast m_Toast;
    private boolean m_bAnimateDone;
    private boolean m_bFromSetting;
    private long m_lExitTime;
    JumpPreferenceKey pref;

    /* renamed from: com.hiti.prinbiz.ModelActivity.2 */
    class C03192 implements OnClickListener {
        C03192() {
        }

        public void onClick(View v) {
            ModelActivity.this.iPermissionButton = v.getId();
            if (!ModelActivity.this.PermissionCheck(1)) {
                ModelActivity.this.ButtonClickToModelFlow(v.getId());
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.ModelActivity.3 */
    class C03203 implements OnClickListener {
        C03203() {
        }

        public void onClick(View v) {
            ModelActivity.this.iPermissionButton = v.getId();
            if (!ModelActivity.this.PermissionCheck(1)) {
                ModelActivity.this.ButtonClickToModelFlow(v.getId());
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.ModelActivity.4 */
    class C03214 implements OnClickListener {
        C03214() {
        }

        public void onClick(View v) {
            ModelActivity.this.iPermissionButton = v.getId();
            if (!ModelActivity.this.PermissionCheck(1)) {
                ModelActivity.this.ButtonClickToModelFlow(v.getId());
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.ModelActivity.5 */
    class C03225 implements OnClickListener {
        C03225() {
        }

        public void onClick(View v) {
            ModelActivity.this.iPermissionButton = v.getId();
            if (!ModelActivity.this.PermissionCheck(1)) {
                ModelActivity.this.ButtonClickToModelFlow(v.getId());
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.ModelActivity.6 */
    class C03236 implements OnCheckedChangeListener {
        C03236() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                ModelActivity.this.pref.SetPreference(JumpPreferenceKey.MODEL_DEFAULT, true);
            } else {
                ModelActivity.this.pref.SetPreference(JumpPreferenceKey.MODEL_DEFAULT, false);
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.ModelActivity.7 */
    class C03247 implements Runnable {
        C03247() {
        }

        public void run() {
            ModelActivity.this.m_CoverRelativeLayout.setVisibility(8);
            ModelActivity.this.m_MainRelativeLayout.setVisibility(0);
            ModelActivity.this.m_AnimFade = AnimationUtils.loadAnimation(ModelActivity.this.getApplicationContext(), C0349R.anim.fade_in);
            ModelActivity.this.m_MainRelativeLayout.startAnimation(ModelActivity.this.m_AnimFade);
            ModelActivity.this.pref.SetPreference(JumpPreferenceKey.ANIMATION_DONE, true);
            ModelActivity.this.m_bAnimateDone = true;
            ModelActivity.this.EndAnimation();
        }
    }

    /* renamed from: com.hiti.prinbiz.ModelActivity.1 */
    class C07721 implements INfcPreview {
        C07721() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            ModelActivity.this.mNFCInfo = nfcInfo;
            ModelActivity.this.CheckNFC();
        }
    }

    class AskSataCallback implements IPermission {
        AskSataCallback() {
        }

        public void GetGrantState(int iPermissionCode, int askState) {
            if (iPermissionCode == 1 && askState == 3) {
                ModelActivity.this.ButtonClickToModelFlow(ModelActivity.this.iPermissionButton);
            }
        }

        public int SetLatterState(int iPermissionCode, int askState) {
            if (iPermissionCode == 1) {
                return 1;
            }
            return askState;
        }
    }

    public ModelActivity() {
        this.m_NotAskAgainCheckBox = null;
        this.m_Toast = null;
        this.m_lExitTime = 0;
        this.m_bFromSetting = false;
        this.m_bAnimateDone = false;
        this.mFlowMode = FlowMode.Normal;
        this.mNFCInfo = null;
        this.TAG = null;
        this.LOG = null;
        this.mPermissionAsk = null;
        this.iPermissionButton = 0;
        this.RunClose = new C03247();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_model);
        this.TAG = getClass().getSimpleName();
        this.LOG = new LogManager(0);
        this.pref = new JumpPreferenceKey(this);
        GetBundle();
        SetView();
        FlurryUtility.init(this, FlurryUtility.FLURRY_API_KEY_PRINBIZ);
        this.LOG.m386v(this.TAG, "onCreate " + savedInstanceState);
        this.mNFCInfo = NFCInfo.InitNFC(getIntent(), null);
        if (VERSION.SDK_INT >= 23) {
            this.mPermissionAsk = new PermissionAsk();
        }
    }

    protected void onNewIntent(Intent intent) {
        this.LOG.m386v("onNewIntent ", XmlPullParser.NO_NAMESPACE + intent);
        setIntent(intent);
        this.mNFCInfo.mPrintMode = PrintMode.NormalMain;
        super.onNewIntent(intent);
    }

    protected void onStart() {
        if (this.mFlowMode != FlowMode.BackMainPage) {
            FlurryUtility.onStartSession(this);
        }
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
        this.LOG.m383d(this.TAG, "onResume " + this.mFlowMode);
        this.LOG.m383d(this.TAG, "onResume " + this.mNFCInfo.mPrintMode);
        if (this.mFlowMode == FlowMode.BackMainPage) {
            EndApp();
        } else if (this.mNFCInfo.mPrintMode == PrintMode.NormalMain) {
            NFCInfo.CheckNFC(this.mNFCInfo, new NfcListener(), this, new C07721());
        } else {
            CheckNFC();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.LOG.m383d(this.TAG, "onActivityResult " + resultCode);
        switch (resultCode) {
            case JumpInfo.RESULT_MAIN_ACTIVITY /*50*/:
                this.mFlowMode = FlowMode.BackMainPage;
            case JumpInfo.RESULT_END /*61*/:
            case WbxmlParser.WAP_EXTENSION /*64*/:
                this.mFlowMode = FlowMode.BackMainPage;
            default:
        }
    }

    void CheckNFC() {
        this.LOG.m383d(this.TAG, "CheckNFC " + this.mNFCInfo.mPrintMode);
        if (this.mNFCInfo.mPrintMode != PrintMode.NormalMain) {
            if (NFCInfo.IsNFCForHitiApp(this.mNFCInfo)) {
                this.LOG.m383d(this.TAG, "Is NFC For Hiti App");
                FlurryUtility.logEvent(FlurryLogString.UI_PAGE_main_TARGET_go_to_NFCsnapprint);
                this.pref.SetPreference(JumpPreferenceKey.ANIMATION_DONE, true);
                SelectPrinterModel(NFCInfo.GetPrinterModelFromNFC(this.mNFCInfo.mPrinterModel), this.mNFCInfo);
            }
        } else if (IsNoNeedAskModel()) {
            this.LOG.m383d(this.TAG, "Is No Need Ask Model");
            startActivityForResult(new Intent(this, MainActivity.class), 77);
        } else {
            LoadAnimation();
        }
    }

    protected void onPause() {
        this.LOG.m386v(this.TAG, "onPause");
        if (!(this.mFlowMode == FlowMode.BackMainPage || this.mNFCInfo.mNfcAdapter == null)) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    protected void onStop() {
        this.LOG.m383d(this.TAG, "onStop");
        if (this.mFlowMode != FlowMode.BackMainPage) {
            FlurryUtility.onEndSession(this);
        }
        super.onStop();
    }

    void GetBundle() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                this.m_bFromSetting = bundle.getBoolean(JumpBundleMessage.BUNDLE_MSG_SWITCH_MODEL);
            }
        }
    }

    private boolean IsNoNeedAskModel() {
        if (this.m_bFromSetting) {
            return false;
        }
        return this.pref.GetStatePreference(JumpPreferenceKey.MODEL_DEFAULT);
    }

    private void LoadAnimation() {
        this.m_CoverRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_LoadingRelativeLayout);
        this.m_MainRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_MainRelativeLayout);
        this.m_bAnimateDone = this.pref.GetStatePreference(JumpPreferenceKey.ANIMATION_DONE);
        if (this.m_bAnimateDone) {
            this.m_MainRelativeLayout.setVisibility(0);
            this.m_CoverRelativeLayout.setVisibility(8);
            EndAnimation();
            return;
        }
        this.m_MainRelativeLayout.setVisibility(4);
        this.m_AnimFade = AnimationUtils.loadAnimation(getApplicationContext(), C0349R.anim.fade);
        this.m_CoverRelativeLayout.setVisibility(0);
        this.m_CoverRelativeLayout.startAnimation(this.m_AnimFade);
        this.handler = new Handler();
        this.handler.postDelayed(this.RunClose, 2600);
    }

    private void SetView() {
        this.m_NotAskAgainCheckBox = (CheckBox) findViewById(C0349R.id.m_m_NotAskAgainCheckBox);
        this.m_P310WButton = (ImageButton) findViewById(C0349R.id.m_P310WButton);
        this.m_P520LButton = (ImageButton) findViewById(C0349R.id.m_P520LButton);
        this.m_P750LButton = (ImageButton) findViewById(C0349R.id.m_P750LButton);
        this.m_P530DButton = (ImageButton) findViewById(C0349R.id.m_P530DLButton);
        if (VERSION.SDK_INT <= 16) {
            this.m_NotAskAgainCheckBox.setPadding(35, 0, 12, 0);
        }
        this.m_P310WButton.setOnClickListener(new C03192());
        this.m_P520LButton.setOnClickListener(new C03203());
        this.m_P750LButton.setOnClickListener(new C03214());
        this.m_P530DButton.setOnClickListener(new C03225());
        this.m_NotAskAgainCheckBox.setOnCheckedChangeListener(new C03236());
        if (this.pref.GetStatePreference(JumpPreferenceKey.MODEL_DEFAULT)) {
            this.m_NotAskAgainCheckBox.setChecked(true);
        }
    }

    private void SelectPrinterModel(String strPrinterModel, NFCInfo mNFCInfo) {
        this.LOG.m383d(this.TAG, "SelectPrinterModel " + strPrinterModel);
        CloseAnimation();
        this.pref.SetPreference(JumpPreferenceKey.PRINTER_MODEL, strPrinterModel);
        if (this.m_bFromSetting) {
            setResult(-1);
            finish();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        if (mNFCInfo != null) {
            intent.putExtras(NFCInfo.PackNFCData(mNFCInfo));
        }
        this.LOG.m383d(this.TAG, "Go To Main page");
        startActivityForResult(intent, 50);
    }

    public void onBackPressed() {
        if (this.m_bFromSetting) {
            setResult(0);
            finish();
            return;
        }
        Exit();
    }

    protected void onDestroy() {
        this.LOG.m383d(this.TAG, "onStop");
        super.onDestroy();
    }

    public void Exit() {
        if (System.currentTimeMillis() - this.m_lExitTime > 2000) {
            if (this.m_Toast == null) {
                this.m_Toast = Toast.makeText(getApplicationContext(), getString(C0349R.string.BACK_HINT), 0);
            }
            this.m_Toast.show();
            this.m_lExitTime = System.currentTimeMillis();
            return;
        }
        if (this.m_Toast != null) {
            this.m_Toast.cancel();
        }
        EndApp();
    }

    void EndApp() {
        this.LOG.m383d(this.TAG, "EndApp");
        CloseAnimation();
        UploadService.StopUploadService(this, UploadService.class);
        this.pref.SetPreference(JumpPreferenceKey.ANIMATION_DONE, true);
        CleanTempFolder();
        finish();
    }

    private void CloseAnimation() {
        if (this.m_AnimFade != null) {
            this.m_AnimFade.cancel();
        }
        this.m_AnimFade = null;
        if (this.RunClose != null && this.handler != null) {
            this.handler.removeCallbacks(this.RunClose);
            this.RunClose = null;
        }
    }

    void EndAnimation() {
        this.LOG.m383d(this.TAG, "EndAnimation");
        if (VERSION.SDK_INT >= 23) {
            this.mPermissionAsk.JustAfterCheck(1, new AskSataCallback());
            if (!this.mPermissionAsk.JustAfterCheck(2, new AskSataCallback())) {
                PermissionCheck(2);
            }
        }
    }

    boolean PermissionCheck(int requestCode) {
        if (VERSION.SDK_INT < 23) {
            return false;
        }
        return PermissionAsk.Request(this, requestCode);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (this.mPermissionAsk != null) {
            for (int i = 0; i < permissions.length; i++) {
                this.mPermissionAsk.SetAskState(permissions[i], grantResults[i]);
            }
        }
    }

    void ButtonClickToModelFlow(int id) {
        switch (id) {
            case C0349R.id.m_P310WButton /*2131427492*/:
                FlurryUtility.logEvent(FlurryLogString.UI_PAGE_model_TARGET_printer310w);
                SelectPrinterModel(WirelessType.TYPE_P310W, null);
            case C0349R.id.m_P520LButton /*2131427493*/:
                FlurryUtility.logEvent(FlurryLogString.UI_PAGE_model_TARGET_printer520l);
                SelectPrinterModel(WirelessType.TYPE_P520L, null);
            case C0349R.id.m_P750LButton /*2131427494*/:
                FlurryUtility.logEvent(FlurryLogString.UI_PAGE_model_TARGET_printer750l);
                SelectPrinterModel(WirelessType.TYPE_P750L, null);
            case C0349R.id.m_P530DLButton /*2131427495*/:
                FlurryUtility.logEvent(FlurryLogString.UI_PAGE_model_TARGET_printer530l);
                SelectPrinterModel(WirelessType.TYPE_P530D, null);
            default:
        }
    }

    private void CleanTempFolder() {
        GlobalVariable_SaveLoadedMeta prefLoadedPath = new GlobalVariable_SaveLoadedMeta(this, "gallery");
        ArrayList<String> m_strThumbPathList = new ArrayList();
        prefLoadedPath.RestoreGlobalVariable();
        if (!prefLoadedPath.IsNoData()) {
            prefLoadedPath.GetIdAndLoadedPathList(null, m_strThumbPathList);
        }
        Iterator it = m_strThumbPathList.iterator();
        while (it.hasNext()) {
            String strThumbPath = (String) it.next();
            if (strThumbPath != null && FileUtility.FileExist(strThumbPath)) {
                FileUtility.DeleteFile(strThumbPath);
            }
        }
        prefLoadedPath.ClearGlobalVariable();
    }
}
