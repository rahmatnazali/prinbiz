package com.hiti.prinbiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PointerIconCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hiti.AppInfo.AppInfo.APP_MODE;
import com.hiti.Flurry.FlurryLogString;
import com.hiti.Flurry.FlurryUtility;
import com.hiti.jni.hello.Hello;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpInfo;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.mdns.MdnsAnimation;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NFCInfo.NfcState;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.service.mdns.MdnsHandler.IMdnsListener;
import com.hiti.service.mdns.MdnsUtility;
import com.hiti.service.upload.UploadService;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.utility.LogManager;
import com.hiti.utility.UserInfo;
import com.hiti.utility.Verify.HintType;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.dialog.HintDialogListener;
import com.hiti.utility.dialog.VerifySnap;
import com.hiti.utility.permissionRequest.PermissionAsk;
import com.hiti.utility.permissionRequest.PermissionAsk.IPermission;
import com.hiti.utility.wifi.ShowPrinterList.Scan;
import com.hiti.webhiti.CheckIfVerify;
import org.kxml2.wap.Wbxml;
import org.xmlpull.v1.XmlPullParser;

public class MainActivity extends Activity {
    LogManager LOG;
    Runnable RunClose;
    String TAG;
    private Handler handler;
    MdnsUtility mMdns;
    MdnsAnimation mMdnsAnimation;
    NFCInfo mNFCInfo;
    PermissionAsk mPermissionAsk;
    private VerifySnap mSnapHintDialog;
    private Animation m_AnimFade;
    CheckIfVerify m_CheckIfVerify;
    private RelativeLayout m_CoverRelativeLayout;
    private Button m_IDImageButton;
    private ImageButton m_InfoButton;
    private Button m_LivePhotoImageButton;
    private ImageButton m_MDNSHintImageButton;
    private Button m_MDNSMessageImageButton;
    private TextView m_MDNSTitleTextView;
    private RelativeLayout m_MainRelativeLayout;
    private Button m_QuickFlashImageButton;
    private ImageButton m_SettingButton;
    private Button m_SnappicsButton;
    private Toast m_Toast;
    private long m_lExitTime;
    JumpPreferenceKey pref;

    /* renamed from: com.hiti.prinbiz.MainActivity.2 */
    class C03122 implements OnClickListener {
        C03122() {
        }

        public void onClick(View v) {
            FlurryUtility.logEvent(FlurryLogString.UI_PAGE_main_TARGET_go_to_quick_album);
            MainActivity.this.SelectPrintFlow(v.getId());
        }
    }

    /* renamed from: com.hiti.prinbiz.MainActivity.3 */
    class C03133 implements OnClickListener {
        C03133() {
        }

        public void onClick(View v) {
            FlurryUtility.logEvent(FlurryLogString.UI_PAGE_main_TARGET_go_to_edit_album);
            MainActivity.this.SelectPrintFlow(v.getId());
        }
    }

    /* renamed from: com.hiti.prinbiz.MainActivity.4 */
    class C03144 implements OnClickListener {
        C03144() {
        }

        public void onClick(View v) {
            FlurryUtility.logEvent(FlurryLogString.UI_PAGE_main_TARGET_go_to_Id_album);
            MainActivity.this.SelectPrintFlow(v.getId());
        }
    }

    /* renamed from: com.hiti.prinbiz.MainActivity.5 */
    class C03155 implements OnClickListener {
        C03155() {
        }

        public void onClick(View v) {
            if (!PermissionAsk.Request(MainActivity.this, 3)) {
                MainActivity.this.CameraVerify();
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.MainActivity.6 */
    class C03166 implements OnClickListener {
        C03166() {
        }

        public void onClick(View v) {
            FlurryUtility.logEvent(FlurryLogString.UI_PAGE_main_TARGET_go_to_setting);
            MainActivity.this.SelectPrintFlow(v.getId());
        }
    }

    /* renamed from: com.hiti.prinbiz.MainActivity.7 */
    class C03177 implements OnClickListener {
        C03177() {
        }

        public void onClick(View v) {
            FlurryUtility.logEvent(FlurryLogString.UI_PAGE_main_TARGET_go_to_user_info);
            MainActivity.this.SelectPrintFlow(v.getId());
        }
    }

    /* renamed from: com.hiti.prinbiz.MainActivity.8 */
    class C03188 implements Runnable {
        C03188() {
        }

        public void run() {
            MainActivity.this.AnimationDone();
            MainActivity.this.m_AnimFade = AnimationUtils.loadAnimation(MainActivity.this, C0349R.anim.fade_in);
            MainActivity.this.m_MainRelativeLayout.startAnimation(MainActivity.this.m_AnimFade);
        }
    }

    /* renamed from: com.hiti.prinbiz.MainActivity.1 */
    class C07701 implements INfcPreview {
        C07701() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            MainActivity.this.mNFCInfo = nfcInfo;
            MainActivity.this.LoadAnimation();
        }
    }

    /* renamed from: com.hiti.prinbiz.MainActivity.9 */
    class C07719 implements IMdnsListener {
        C07719() {
        }

        public void getScanState(Scan state, String strPID, String strSSID, String IP, int iPort) {
            MainActivity.this.mMdnsAnimation.GetState(state, strSSID);
        }

        public void StartAnimation() {
            MainActivity.this.mMdnsAnimation.Initial();
        }
    }

    class AskStateCallback implements IPermission {
        AskStateCallback() {
        }

        public void GetGrantState(int iPermissionCode, int askState) {
            MainActivity.this.LOG.m383d(MainActivity.this.TAG, "GetGrantState: " + askState);
            if (iPermissionCode == 3 && askState == 3) {
                MainActivity.this.CameraVerify();
            }
        }

        public int SetLatterState(int iPermissionCode, int askState) {
            return 1;
        }
    }

    public MainActivity() {
        this.m_Toast = null;
        this.m_lExitTime = 0;
        this.m_CheckIfVerify = null;
        this.mNFCInfo = null;
        this.mMdns = null;
        this.mMdnsAnimation = null;
        this.LOG = null;
        this.TAG = null;
        this.mSnapHintDialog = null;
        this.mPermissionAsk = null;
        this.RunClose = new C03188();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_main);
        this.LOG = new LogManager(0);
        this.TAG = getClass().getSimpleName();
        InitPref();
        SetMdnsAnimation();
        SetView();
        FakeLogin();
        FlurryUtility.init(this, FlurryUtility.FLURRY_API_KEY_PRINBIZ);
        this.LOG.m386v("onCreate ", this.TAG);
        this.mNFCInfo = NFCInfo.InitNFC(getIntent(), null);
        this.mPermissionAsk = new PermissionAsk();
    }

    protected void onStart() {
        FlurryUtility.onStartSession(this);
        this.mMdns.BindService();
        super.onStart();
    }

    protected void onNewIntent(Intent intent) {
        this.LOG.m386v("onNewIntent ", XmlPullParser.NO_NAMESPACE + intent);
        setIntent(intent);
        this.mNFCInfo.mPrintMode = PrintMode.NormalMain;
        super.onNewIntent(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            this.mNFCInfo.mPrintMode = PrintMode.NormalMain;
        }
        viewCheck();
    }

    protected void onResume() {
        super.onResume();
        this.LOG.m386v("onResume mPrintMode ", XmlPullParser.NO_NAMESPACE + this.mNFCInfo.mPrintMode);
        if (!this.mPermissionAsk.JustAfterCheck(3, new AskStateCallback())) {
            if (this.mNFCInfo.mPrintMode == PrintMode.NormalMain) {
                NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07701());
            } else if (NFCInfo.IsSupportWifiDefault(this.mNFCInfo)) {
                EndMainPage(51, NFCInfo.PackNFCData(this.mNFCInfo));
                AnimationDone();
            } else {
                this.mNFCInfo.mNFCListener = new NfcListener();
                this.mNFCInfo = NFCInfo.NfcEnableNdefExchangeMode(this.mNFCInfo, this);
                AnimationDone();
            }
        }
    }

    protected void onPause() {
        this.LOG.m386v(this.TAG, "onPause");
        StopMdnsAnimation();
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    protected void onStop() {
        this.LOG.m386v(this.TAG, "onStop");
        FlurryUtility.endTimedEvent(FlurryLogString.UI_PAGE_main_TARGET_stay_time);
        FlurryUtility.onEndSession(this);
        this.mMdns.UnbindService();
        this.mMdns.Stop();
        super.onStop();
    }

    protected void onDestroy() {
        this.LOG.m386v(this.TAG, "onDestroy!!!!");
        super.onDestroy();
    }

    private void InitPref() {
        this.pref = new JumpPreferenceKey(this);
        this.pref.SetPreference(JumpPreferenceKey.PATHSELECTED, (int) ControllerState.PLAY_PHOTO);
    }

    private void FakeLogin() {
        String strU = Hello.SayHello(this, PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW);
        String strP = Hello.SayGoodBye(this, PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW);
        String strUser = UserInfo.GetUser(this);
        if (strUser == null) {
            UserInfo.FakeUserLogin(this, strU, strP);
        } else if (strUser.length() == 0) {
            UserInfo.FakeUserLogin(this, strU, strP);
        }
    }

    private void LoadAnimation() {
        this.pref = new JumpPreferenceKey(this);
        if (Boolean.valueOf(this.pref.GetStatePreference(JumpPreferenceKey.ANIMATION_DONE)).booleanValue()) {
            AnimationDone();
            return;
        }
        this.m_MainRelativeLayout.setVisibility(4);
        this.m_AnimFade = AnimationUtils.loadAnimation(getApplicationContext(), C0349R.anim.fade);
        this.m_CoverRelativeLayout.startAnimation(this.m_AnimFade);
        this.handler = new Handler();
        this.handler.postDelayed(this.RunClose, 2600);
    }

    void viewCheck() {
        int i;
        int i2 = 8;
        boolean bRet = this.pref.GetModelPreference().equals(WirelessType.TYPE_P530D);
        Button button = this.m_LivePhotoImageButton;
        if (bRet) {
            i = 8;
        } else {
            i = 0;
        }
        button.setVisibility(i);
        Button button2 = this.m_IDImageButton;
        if (!bRet) {
            i2 = 0;
        }
        button2.setVisibility(i2);
    }

    private void SetView() {
        this.mSnapHintDialog = new VerifySnap(this);
        this.m_CoverRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_LoadingRelativeLayout);
        this.m_MainRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_MainRelativeLayout);
        this.m_QuickFlashImageButton = (Button) findViewById(C0349R.id.m_QuickFlashButton);
        this.m_LivePhotoImageButton = (Button) findViewById(C0349R.id.m_LifePhotoButton);
        this.m_IDImageButton = (Button) findViewById(C0349R.id.m_IDpicsButton);
        this.m_SnappicsButton = (Button) findViewById(C0349R.id.m_SnappicsButton);
        this.m_SettingButton = (ImageButton) findViewById(C0349R.id.m_SettingButton);
        this.m_InfoButton = (ImageButton) findViewById(C0349R.id.m_InfoButton);
        viewCheck();
        this.m_QuickFlashImageButton.setOnClickListener(new C03122());
        this.m_LivePhotoImageButton.setOnClickListener(new C03133());
        this.m_IDImageButton.setOnClickListener(new C03144());
        this.m_SnappicsButton.setOnClickListener(new C03155());
        this.m_SettingButton.setOnClickListener(new C03166());
        this.m_InfoButton.setOnClickListener(new C03177());
    }

    private void AnimationDone() {
        this.m_CoverRelativeLayout.setVisibility(8);
        this.m_MainRelativeLayout.setVisibility(0);
        this.pref.SetPreference(JumpPreferenceKey.ANIMATION_DONE, true);
        StartService();
    }

    private void StartService() {
        String strU = UserInfo.GetUser(this);
        if (strU == null) {
            strU = UserInfo.GetPublicPrintUP(this).first;
        }
        FlurryUtility.logEvent(FlurryLogString.UI_PAGE_main_TARGET_stay_time, FlurryLogString.KEY_USER, strU);
        UploadService.StartUploadService(this, UploadService.class, APP_MODE.PRINBIZ);
    }

    private void SelectPrintFlow(int iPath) {
        switch (iPath) {
            case C0349R.id.m_QuickFlashButton /*2131427481*/:
                this.pref.SetPreference(JumpPreferenceKey.PATHSELECTED, (int) ControllerState.PLAY_PHOTO);
                EndMainPage(51, null);
            case C0349R.id.m_LifePhotoButton /*2131427482*/:
                this.pref.SetPreference(JumpPreferenceKey.PATHSELECTED, (int) ControllerState.NO_PLAY_ITEM);
                EndMainPage(51, null);
            case C0349R.id.m_IDpicsButton /*2131427483*/:
                this.pref.SetPreference(JumpPreferenceKey.PATHSELECTED, (int) ControllerState.PLAY_COUNT_STATE);
                EndMainPage(63, null);
            case C0349R.id.m_InfoButton /*2131427486*/:
                startActivity(new Intent(this, InfoActivity.class));
            case C0349R.id.m_SettingButton /*2131427487*/:
                EndMainPage(65, null);
            default:
        }
    }

    public void onBackPressed() {
        this.LOG.m383d(this.TAG, "onBackPressed ");
        this.pref.SetPreference(JumpPreferenceKey.FROM_SETTING, false);
        Exit();
    }

    private void EndMainPage(int iResult, Bundle bundle) {
        CloseAnimation();
        this.mMdns.UnbindService();
        this.mMdns.Stop();
        this.LOG.m383d(this.TAG, "EndMainPage " + iResult);
        Intent intent = null;
        switch (iResult) {
            case JumpInfo.RESULT_SOURCE_ACTIVITY /*51*/:
                intent = new Intent(this, SourceActivity.class);
                break;
            case JumpInfo.RESULT_ID_SET_ACTIVITY /*63*/:
                intent = new Intent(this, SettingIDActivity.class);
                intent.putExtra(JumpBundleMessage.BUNDLE_MSG_ID_ROUTE_FROM, JumpPreferenceKey.ID_FROM_MAIN);
                break;
            case Wbxml.EXT_I_1 /*65*/:
                intent = new Intent(this, SettingActivity.class);
                break;
        }
        if (iResult == 64) {
            finish();
        } else if (intent != null) {
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivityForResult(intent, iResult);
        }
    }

    public void Exit() {
        this.mMdns.UnbindService();
        this.mMdns.Stop();
        CloseAnimation();
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
        setResult(61);
        this.pref.SetPreference(JumpPreferenceKey.ANIMATION_DONE, true);
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        finish();
    }

    private void CloseAnimation() {
        this.LOG.m383d(this.TAG, "CloseAnimation ");
        if (this.m_AnimFade != null) {
            this.m_AnimFade.cancel();
        }
        this.m_AnimFade = null;
        if (this.RunClose != null && this.handler != null) {
            this.handler.removeCallbacks(this.RunClose);
            this.RunClose = null;
        }
    }

    void SetMdnsAnimation() {
        this.m_MDNSHintImageButton = (ImageButton) findViewById(C0349R.id.m_MDNSHintImageButton);
        this.m_MDNSMessageImageButton = (Button) findViewById(C0349R.id.m_MDNSMessageImageButton);
        this.m_MDNSTitleTextView = (TextView) findViewById(C0349R.id.m_MDNSTitleTextView);
        if (NFCInfo.GetNfcDeviceState(this) != NfcState.not_support) {
            this.m_MDNSMessageImageButton.setText(getString(C0349R.string.NFC_WIFI_UNDETECT));
        }
        this.mMdnsAnimation = new MdnsAnimation(this, this.m_MDNSHintImageButton, this.m_MDNSMessageImageButton, this.m_MDNSTitleTextView);
        this.mMdns = new MdnsUtility(this);
        this.mMdns.SetAnimationListener(new C07719());
    }

    void StopMdnsAnimation() {
        this.mMdnsAnimation.StopAnimation();
    }

    void CameraVerify() {
        this.mSnapHintDialog.ShowHintDialog(HintType.SnapPrint, new HintDialogListener() {
            public void ContinueProcess() {
                FlurryUtility.logEvent(FlurryLogString.UI_PAGE_main_TARGET_go_to_snapprint);
                Intent intent = new Intent(MainActivity.this, SourceActivity.class);
                intent.putExtra(JumpBundleMessage.BUNDLE_MSG_PRINT_MODE, PrintMode.Snap.toString());
                MainActivity.this.startActivityForResult(intent, 45);
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (this.mPermissionAsk != null) {
            for (int i = 0; i < permissions.length; i++) {
                this.mPermissionAsk.SetAskState(permissions[i], grantResults[i]);
            }
        }
    }
}
