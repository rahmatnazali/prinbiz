package com.hiti.prinbiz;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.Flurry.FlurryLogString;
import com.hiti.Flurry.FlurryUtility;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.jumpinfo.FlowMode;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.jumpinfo.JumpInfo;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.utility.PrinterInfo;
import com.hiti.printerprotocol.utility.PrinterInfo.Callback;
import com.hiti.service.upload.UploadService;
import com.hiti.trace.GlobalVariable_AlbumSelInfo;
import com.hiti.trace.GlobalVariable_PrintSettingInfo;
import com.hiti.trace.GlobalVariable_SaveLoadedMeta;
import com.hiti.ui.drawview.garnishitem.utility.EditMetaUtility;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.MediaUtil;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.dialog.ShowMSGDialog;
import com.hiti.utility.dialog.ShowMSGDialog.HintDialog;
import com.hiti.utility.permissionRequest.PermissionAsk;
import com.hiti.utility.permissionRequest.PermissionAsk.IPermission;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.telnet.TelnetOption;
import org.xmlpull.v1.XmlPullParser;

public class SourceActivity extends Activity {
    private OnClickListener GoToAlbumFromPrinterListen;
    private OnClickListener GoToListAlbumListen;
    private OnClickListener GoToListCameraListen;
    LogManager LOG;
    String TAG;
    FlowMode mFlowMode;
    NFCInfo mNFCInfo;
    PermissionAsk mPermissionAsk;
    private PrintMode mPrintMode;
    private ImageButton m_BackButton;
    private Button m_CameraSourceButton;
    private Bundle m_CollageBundle;
    private ShowMSGDialog m_CropMSG;
    private EditMetaUtility m_EditMetaUtility;
    private Button m_MobileOriButton;
    private TextView m_NowWifiTextView;
    private TextView m_PrintModelTextView;
    private TextView m_PrintOutTextView;
    private TextView m_RouteTextView;
    private Button m_SDcardOriButton;
    private GlobalVariable_AlbumSelInfo m_SetAlbumName;
    private long m_TmpFileID;
    private String m_TmpFilePath;
    private Uri m_TmpFileUri;
    private boolean m_boProtected;
    private int m_iAlbumFrom;
    private int m_iPathRoute;
    private JumpPreferenceKey m_pathPref;
    private GlobalVariable_PrintSettingInfo m_prefPrintOutInfo;

    /* renamed from: com.hiti.prinbiz.SourceActivity.2 */
    class C03872 implements OnClickListener {
        C03872() {
        }

        public void onClick(View v) {
            SourceActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.SourceActivity.4 */
    class C03884 implements OnClickListener {
        C03884() {
        }

        public void onClick(View v) {
            SourceActivity.this.onCameraClick();
        }
    }

    /* renamed from: com.hiti.prinbiz.SourceActivity.5 */
    class C03895 implements OnClickListener {
        C03895() {
        }

        public void onClick(View v) {
            switch (SourceActivity.this.m_iPathRoute) {
                case ControllerState.PLAY_PHOTO /*101*/:
                    FlurryUtility.logEvent(FlurryLogString.UI_PAGE_source_TARGET_rapid_photo_phone_album);
                    break;
                case ControllerState.NO_PLAY_ITEM /*102*/:
                    FlurryUtility.logEvent(FlurryLogString.UI_PAGE_source_TARGET_general_photo_phone_album);
                    break;
                case ControllerState.PLAY_COUNT_STATE /*103*/:
                    FlurryUtility.logEvent(FlurryLogString.UI_PAGE_source_TARGET_id_photo_phone_album);
                    break;
            }
            SourceActivity.this.ClearMultiSelContainerPref(1);
            SourceActivity.this.EndThisPage(31, null);
        }
    }

    /* renamed from: com.hiti.prinbiz.SourceActivity.6 */
    class C03906 implements OnClickListener {
        C03906() {
        }

        public void onClick(View v) {
            switch (SourceActivity.this.m_iPathRoute) {
                case ControllerState.PLAY_PHOTO /*101*/:
                    FlurryUtility.logEvent(FlurryLogString.UI_PAGE_source_TARGET_rapid_photo_sdcard);
                    break;
                case ControllerState.NO_PLAY_ITEM /*102*/:
                    FlurryUtility.logEvent(FlurryLogString.UI_PAGE_source_TARGET_general_photo_sdcard);
                    break;
                case ControllerState.PLAY_COUNT_STATE /*103*/:
                    FlurryUtility.logEvent(FlurryLogString.UI_PAGE_source_TARGET_id_photo_sdcard);
                    break;
            }
            UploadService.StopUploadService(SourceActivity.this, UploadService.class);
            SourceActivity.this.ClearMultiSelContainerPref(2);
            SourceActivity.this.EndThisPage(54, null);
        }
    }

    /* renamed from: com.hiti.prinbiz.SourceActivity.7 */
    class C03917 implements DialogInterface.OnClickListener {
        C03917() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    /* renamed from: com.hiti.prinbiz.SourceActivity.1 */
    class C07951 implements INfcPreview {
        C07951() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            SourceActivity.this.mNFCInfo = nfcInfo;
        }
    }

    /* renamed from: com.hiti.prinbiz.SourceActivity.3 */
    class C07963 implements Callback {
        C07963() {
        }

        public String setModelTextP310W() {
            return SourceActivity.this.getString(C0349R.string.P310W);
        }

        public String setModelTextP520L() {
            return SourceActivity.this.getString(C0349R.string.P520L);
        }

        public String setModelTextP750L() {
            return SourceActivity.this.getString(C0349R.string.P750L);
        }

        public String setModelTextP530D() {
            return SourceActivity.this.getString(C0349R.string.P530D);
        }
    }

    class AskStateCallback implements IPermission {
        AskStateCallback() {
        }

        public void GetGrantState(int iPermissionCode, int askState) {
            SourceActivity.this.LOG.m383d(SourceActivity.this.TAG, "GetGrantState: " + askState);
            if (iPermissionCode == 3 && askState == 3) {
                SourceActivity.this.onCameraClick();
            }
        }

        public int SetLatterState(int iPermissionCode, int askState) {
            return 1;
        }
    }

    public SourceActivity() {
        this.m_boProtected = false;
        this.m_TmpFileUri = null;
        this.m_TmpFilePath = null;
        this.m_TmpFileID = -1;
        this.m_SetAlbumName = null;
        this.m_prefPrintOutInfo = null;
        this.m_pathPref = null;
        this.m_iPathRoute = 0;
        this.m_iAlbumFrom = 0;
        this.m_CollageBundle = null;
        this.m_EditMetaUtility = null;
        this.m_CropMSG = null;
        this.mPrintMode = PrintMode.NormalPrint;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = null;
        this.mFlowMode = FlowMode.Normal;
        this.mPermissionAsk = null;
        this.GoToListCameraListen = new C03884();
        this.GoToListAlbumListen = new C03895();
        this.GoToAlbumFromPrinterListen = new C03906();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_source);
        this.LOG = new LogManager(0);
        this.TAG = getClass().getSimpleName();
        this.LOG.m385i(this.TAG, "onCreate()");
        this.m_pathPref = new JumpPreferenceKey(this);
        this.mPermissionAsk = new PermissionAsk();
        if (savedInstanceState != null) {
            this.m_TmpFilePath = savedInstanceState.getString(JumpBundleMessage520.BUNDLE_MSG_CAMERRA_PHOTO_PATH);
            this.m_TmpFileID = savedInstanceState.getLong(JumpBundleMessage.BUNDLE_MSG_CAMERRA_PHOTO_ID);
            String flowMode = savedInstanceState.getString(JumpBundleMessage.BUNDLE_MSG_COLLAGE_MODE);
            String printMode = savedInstanceState.getString(JumpBundleMessage.BUNDLE_MSG_PRINT_MODE);
            this.mFlowMode = flowMode != null ? FlowMode.valueOf(flowMode) : this.mFlowMode;
            this.mPrintMode = printMode != null ? PrintMode.valueOf(printMode) : this.mPrintMode;
            this.LOG.m385i(this.TAG, "onCreate()_savedInstanceState " + this.mFlowMode);
        } else {
            GetBundle();
        }
        if (this.mFlowMode == FlowMode.CollageStart) {
            GoToGalleryForCollage();
            return;
        }
        if (savedInstanceState == null) {
            CleanPhotoPathPref();
        }
        if (!this.mPrintMode.equals(PrintMode.Snap)) {
            if (savedInstanceState == null) {
                ShowCropHint();
            }
            SetView();
            ShowPathRoute();
            ShowPrintOutSetting();
            ShowPrinterModel();
            FlurryUtility.init(this, FlurryUtility.FLURRY_API_KEY_PRINBIZ);
        }
    }

    void GoToGalleryForCollage() {
        Intent intent = this.m_iAlbumFrom == 1 ? new Intent(this, GalleryActivity.class) : this.m_iAlbumFrom == 2 ? new Intent(this, GalleryFromPrinterActivity.class) : null;
        if (!(intent == null || this.m_CollageBundle == null)) {
            intent.putExtras(this.m_CollageBundle);
        }
        if (intent != null) {
            startActivityForResult(intent, 18);
        }
    }

    private void CleanPhotoPathPref() {
        CleanTempFolderAndPref();
        CleanEditMetaPref();
    }

    private void ShowCropHint() {
        if (this.m_iPathRoute == ControllerState.PLAY_PHOTO) {
            this.m_CropMSG = new ShowMSGDialog(this, false);
            if (this.m_CropMSG.IsNeedHintAgain(HintDialog.Crop, this.m_iPathRoute)) {
                this.m_CropMSG.ShowCropWaringDialog(this.m_iPathRoute, getString(C0349R.string.HINT_TITLE));
            }
        }
    }

    private void GetBundle() {
        Bundle bundle = null;
        if (getIntent() != null) {
            bundle = getIntent().getExtras();
        }
        this.m_iPathRoute = this.m_pathPref.GetPathSelectedPref();
        this.LOG.m383d(this.TAG, "GetBundle m_iPathRoute: " + this.m_iPathRoute);
        if (bundle != null) {
            if (this.m_iPathRoute == ControllerState.PLAY_COUNT_STATE) {
                this.mFlowMode = bundle.getBoolean(JumpBundleMessage.BUNDLE_MSG_COLLAGE_FOLLOW) ? FlowMode.CollageStart : this.mFlowMode;
                if (this.mFlowMode == FlowMode.CollageStart) {
                    this.m_iAlbumFrom = bundle.getInt(JumpBundleMessage520.BUNDLE_NSG_SEL_ROUTE);
                    this.m_CollageBundle = bundle;
                }
            }
            String printMode = bundle.getString(JumpBundleMessage.BUNDLE_MSG_PRINT_MODE);
            this.mPrintMode = printMode == null ? this.mPrintMode : PrintMode.valueOf(printMode);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mNFCInfo != null) {
            this.mNFCInfo.mPrintMode = PrintMode.NormalMain;
        }
        this.LOG.m383d(this.TAG, "onActivityResult requestCode: " + requestCode);
        this.LOG.m383d(this.TAG, "onActivityResult resultCode: " + resultCode);
        switch (requestCode) {
            case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                if (this.mPrintMode.equals(PrintMode.Snap)) {
                    this.mPrintMode = PrintMode.camera;
                }
                this.m_boProtected = false;
                if (resultCode == 0) {
                    DeleteTmpFile();
                    if (this.mPrintMode.equals(PrintMode.camera)) {
                        finish();
                    }
                } else if (resultCode == -1) {
                    GetCameraPhoto();
                }
            case ConnectionResult.SERVICE_UPDATING /*18*/:
                EndThisPage(resultCode, data != null ? data.getExtras() : null);
            case TelnetOption.WINDOW_SIZE /*31*/:
            case JumpInfo.RESULT_ALBUM_FROM_PRINTER_ACTIVITY /*54*/:
                if (resultCode == 50) {
                    this.mFlowMode = FlowMode.BackMainPage;
                    setResult(50);
                }
            case JumpInfo.REQUEST_SNAP_PRINT /*45*/:
                if (data != null) {
                    this.m_TmpFilePath = data.getStringExtra(JumpBundleMessage.BUNDLE_MSG_SNAP_PRINT_PICTURE_PATH);
                }
                DeleteTmpFile();
                this.mPrintMode = PrintMode.camera;
                finish();
            default:
        }
    }

    protected void onStart() {
        this.LOG.m385i("SourceActivity", "onStart()");
        if (this.mFlowMode == FlowMode.Normal && !this.mPrintMode.equals(PrintMode.Snap)) {
            FlurryUtility.onStartSession(this);
        }
        super.onStart();
    }

    protected void onNewIntent(Intent intent) {
        this.LOG.m386v("onNewIntent ", XmlPullParser.NO_NAMESPACE + intent);
        setIntent(intent);
        this.mNFCInfo.mPrintMode = PrintMode.NormalMain;
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        if (this.mFlowMode != FlowMode.CollageStart && this.mFlowMode != FlowMode.ActivityResult && !this.mPermissionAsk.JustAfterCheck(3, new AskStateCallback())) {
            if (this.mFlowMode == FlowMode.BackMainPage) {
                finish();
            } else if (this.mPrintMode == PrintMode.camera) {
            } else {
                if (this.mPrintMode == PrintMode.Snap) {
                    onCameraClick();
                } else {
                    NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07951());
                }
            }
        }
    }

    protected void onPause() {
        if (!(this.mFlowMode != FlowMode.Normal || this.mPrintMode == PrintMode.Snap || this.mPrintMode == PrintMode.camera || this.mNFCInfo.mNfcAdapter == null)) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        if (this.mFlowMode == FlowMode.CollageStart) {
            this.mFlowMode = FlowMode.CollagePeriod;
        }
        super.onPause();
    }

    protected void onSaveInstanceState(Bundle outState) {
        this.LOG.m385i(this.TAG, "onSaveInstanceState() " + this.m_TmpFilePath);
        this.LOG.m385i(this.TAG, "onSaveInstanceState() " + this.m_TmpFileID);
        outState.remove(JumpBundleMessage520.BUNDLE_MSG_CAMERRA_PHOTO_PATH);
        outState.remove(JumpBundleMessage.BUNDLE_MSG_COLLAGE_MODE);
        outState.remove(JumpBundleMessage.BUNDLE_MSG_PRINT_MODE);
        if (this.m_TmpFilePath != null) {
            outState.putString(JumpBundleMessage520.BUNDLE_MSG_CAMERRA_PHOTO_PATH, this.m_TmpFilePath);
        }
        if (this.m_TmpFileID != -1) {
            outState.putLong(JumpBundleMessage.BUNDLE_MSG_CAMERRA_PHOTO_ID, this.m_TmpFileID);
        }
        outState.putString(JumpBundleMessage.BUNDLE_MSG_COLLAGE_MODE, this.mFlowMode.toString());
        outState.putString(JumpBundleMessage.BUNDLE_MSG_PRINT_MODE, this.mPrintMode.toString());
        this.LOG.m385i(this.TAG, "onSaveInstanceState() " + this.mFlowMode);
        super.onSaveInstanceState(outState);
    }

    protected void onStop() {
        if (this.mFlowMode == FlowMode.Normal) {
            FlurryUtility.onEndSession(this);
        }
        this.LOG.m385i(this.TAG, "onStop() " + this.mFlowMode);
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.LOG.m385i(this.TAG, "onDestroy() " + this.mFlowMode);
    }

    void ClearMultiSelContainerPref(int type) {
        GlobalVariable_AlbumSelInfo m_prefAlbumInfo = new GlobalVariable_AlbumSelInfo(this, false);
        m_prefAlbumInfo.RestoreGlobalVariable();
        m_prefAlbumInfo.SetAlbumRoute(type);
        m_prefAlbumInfo.SaveGlobalVariable();
        CleanEditMetaPref();
    }

    private void SetView() {
        this.m_CameraSourceButton = (Button) findViewById(C0349R.id.m_CameraSourceButton);
        this.m_MobileOriButton = (Button) findViewById(C0349R.id.m_AlbumSourceButton);
        this.m_SDcardOriButton = (Button) findViewById(C0349R.id.m_SDcardSourceButton);
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_PrintOutTextView = (TextView) findViewById(C0349R.id.m_PrintOutTextView);
        this.m_PrintModelTextView = (TextView) findViewById(C0349R.id.m_PrintModelTextView);
        this.m_NowWifiTextView = (TextView) findViewById(C0349R.id.m_WifiTextView);
        this.m_RouteTextView = (TextView) findViewById(C0349R.id.m_RouteTextView);
        this.m_CameraSourceButton.setOnClickListener(this.GoToListCameraListen);
        this.m_MobileOriButton.setOnClickListener(this.GoToListAlbumListen);
        this.m_SDcardOriButton.setOnClickListener(this.GoToAlbumFromPrinterListen);
        GetNowSSID();
        this.m_BackButton.setOnClickListener(new C03872());
    }

    private void GetNowSSID() {
        String strSSID;
        WifiInfo wifiInfo = ((WifiManager) getSystemService("wifi")).getConnectionInfo();
        if (wifiInfo.getSSID() == null) {
            strSSID = XmlPullParser.NO_NAMESPACE;
        } else {
            strSSID = wifiInfo.getSSID();
        }
        if (strSSID.contains("\"")) {
            strSSID = strSSID.replace("\"", XmlPullParser.NO_NAMESPACE);
        }
        if (strSSID.isEmpty() || strSSID.equals("0x")) {
            this.m_NowWifiTextView.setVisibility(8);
            return;
        }
        this.m_NowWifiTextView.append(" " + strSSID);
        this.m_NowWifiTextView.setVisibility(0);
    }

    private void ShowPathRoute() {
        String strRoute = XmlPullParser.NO_NAMESPACE;
        if (this.m_iPathRoute == ControllerState.PLAY_PHOTO) {
            strRoute = getString(C0349R.string.QUICKTXT);
        } else if (this.m_iPathRoute == ControllerState.NO_PLAY_ITEM) {
            strRoute = getString(C0349R.string.LIFETEXT);
        } else if (this.m_iPathRoute == ControllerState.PLAY_COUNT_STATE) {
            strRoute = getString(C0349R.string.IDTXT);
        }
        this.m_RouteTextView.setText(strRoute);
    }

    private void ShowPrintOutSetting() {
        this.m_prefPrintOutInfo = new GlobalVariable_PrintSettingInfo(this, this.m_iPathRoute);
        this.m_prefPrintOutInfo.RestoreGlobalVariable();
        this.m_PrintOutTextView.setText(":" + String.valueOf(PrinterInfo.GetPrintoutItem(this, this.m_prefPrintOutInfo.GetServerPaperType())));
    }

    private void ShowPrinterModel() {
        this.m_PrintModelTextView.setText(":" + String.valueOf(PrinterInfo.getPrinterModelName(this, new C07963())));
    }

    private void onCameraClick() {
        if (!PermissionAsk.Request(this, 3)) {
            switch (this.m_iPathRoute) {
                case ControllerState.PLAY_PHOTO /*101*/:
                    FlurryUtility.logEvent(FlurryLogString.UI_PAGE_source_TARGET_rapid_photo_open_camera);
                    break;
                case ControllerState.NO_PLAY_ITEM /*102*/:
                    FlurryUtility.logEvent(FlurryLogString.UI_PAGE_source_TARGET_general_photo_open_camera);
                    break;
                case ControllerState.PLAY_COUNT_STATE /*103*/:
                    FlurryUtility.logEvent(FlurryLogString.UI_PAGE_source_TARGET_id_photo_open_camera);
                    break;
            }
            ClearMultiSelContainerPref(1);
            onCameraImageButtonClicked();
        }
    }

    private void EndThisPage(int iResult, Bundle bundle) {
        this.LOG.m383d(this.TAG, "EndThisPage iResult: " + iResult);
        this.LOG.m383d(this.TAG, "EndThisPage mFlowMode: " + this.mFlowMode);
        this.mFlowMode = FlowMode.ActivityResult;
        Intent intent = null;
        switch (iResult) {
            case TelnetOption.REGIME_3270 /*29*/:
            case JumpInfo.RESULT_ID_SET_ACTIVITY /*63*/:
                if (bundle != null) {
                    intent = new Intent();
                }
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                setResult(iResult, intent);
                finish();
                return;
            case TelnetOption.WINDOW_SIZE /*31*/:
                intent = new Intent(this, AlbumActivity.class);
                iResult = 18;
                this.mFlowMode = FlowMode.CollageStart;
                break;
            case JumpInfo.RESULT_MAIN_ACTIVITY /*50*/:
                setResult(iResult, null);
                finish();
                return;
            case JumpInfo.RESULT_ALBUM_FROM_PRINTER_ACTIVITY /*54*/:
                intent = new Intent(this, AlbumFromPrinterActivity.class);
                break;
            case JumpInfo.RESULT_PHOTO_ACTIVITY /*55*/:
                intent = new Intent(this, GalleryActivity.class);
                iResult = 18;
                this.mFlowMode = FlowMode.CollageStart;
                break;
        }
        if (intent != null) {
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivityForResult(intent, iResult);
        }
    }

    private void onCameraImageButtonClicked() {
        if (!this.m_boProtected) {
            this.m_boProtected = true;
            if (FileUtility.SDCardState()) {
                File sdFile = Environment.getExternalStorageDirectory();
                if (sdFile != null) {
                    FileUtility.CreateFolder(sdFile.getPath() + File.separator + PringoConvenientConst.PRINBIZ_FOLDER);
                    OpenCamera();
                    return;
                }
                ShowAlertDialog(getString(C0349R.string.SD_CARD_NOT_READYING), getString(C0349R.string.WARNNING), 17301569);
                this.m_boProtected = false;
                return;
            }
            ShowAlertDialog(getString(C0349R.string.SD_CARD_NOT_READYING), getString(C0349R.string.WARNNING), 17301569);
            this.m_boProtected = false;
        }
    }

    private void OpenCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Pair<Uri, String> pair = MediaUtil.PrepareMedia(this, PringoConvenientConst.PRINBIZ_FOLDER);
        this.m_TmpFileUri = (Uri) pair.first;
        this.m_TmpFilePath = (String) pair.second;
        this.m_TmpFileID = ContentUris.parseId(this.m_TmpFileUri);
        intent.putExtra("output", this.m_TmpFileUri);
        startActivityForResult(intent, 11);
    }

    private void DeleteTmpFile() {
        if (this.m_TmpFilePath != null) {
            FileUtility.DeleteFile(this.m_TmpFilePath);
        }
    }

    private void GetCameraPhoto() {
        this.LOG.m385i("GetCameraPhoto", "=" + String.valueOf(this.m_TmpFilePath));
        if (this.m_TmpFilePath != null) {
            if (BitmapMonitor.IsLegalRatio(this, Uri.parse("file://" + this.m_TmpFilePath)).GetResult() == 0) {
                this.LOG.m385i("UpdateMedia", "ID=" + String.valueOf(this.m_TmpFileID));
                MediaUtil.UpdateMedia(this, this.m_TmpFilePath, this.m_TmpFileID);
                if (this.mPrintMode == PrintMode.camera) {
                    ToSnapPrint(this.m_TmpFilePath);
                    return;
                } else {
                    GoToCameraStoreAlbum();
                    return;
                }
            }
            ShowAlertDialog(getString(C0349R.string.PLEASE_SELECT_OTHER_CAMERA_APP), getString(C0349R.string.WARNNING), 17301569);
            DeleteTmpFile();
        }
    }

    private void ToSnapPrint(String strPhotoPath) {
        Intent intent = new Intent(this, PrintViewActivity.class);
        intent.putExtra(JumpBundleMessage.BUNDLE_MSG_PRINT_MODE, PrintMode.Snap.toString());
        intent.putExtra(JumpBundleMessage.BUNDLE_MSG_SNAP_PRINT_PICTURE_PATH, String.valueOf(strPhotoPath));
        startActivityForResult(intent, 45);
    }

    private void GoToCameraStoreAlbum() {
        boolean z;
        String[] projection = new String[]{"bucket_display_name", "bucket_id"};
        Cursor cursor = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, projection, null, null, "bucket_display_name");
        int bucketNameColumnIndex = cursor.getColumnIndex("bucket_display_name");
        int bucketIDColumnIndex = cursor.getColumnIndex("bucket_id");
        String strAlbumName = XmlPullParser.NO_NAMESPACE;
        String strAlbumID = XmlPullParser.NO_NAMESPACE;
        if (cursor.moveToFirst()) {
            do {
                strAlbumName = cursor.getString(bucketNameColumnIndex);
                strAlbumID = cursor.getString(bucketIDColumnIndex);
                if (strAlbumName.contains(PringoConvenientConst.PRINBIZ_FOLDER)) {
                    cursor.close();
                    break;
                }
            } while (cursor.moveToNext());
        }
        if (this.mFlowMode == FlowMode.CollagePeriod) {
            z = true;
        } else {
            z = false;
        }
        this.m_SetAlbumName = new GlobalVariable_AlbumSelInfo(this, z);
        this.m_SetAlbumName.ClearGlobalVariable();
        this.m_SetAlbumName.SetAlbumRoute(1);
        this.m_SetAlbumName.SaveGlobalVariable();
        this.m_SetAlbumName.SetMobileAlbumId(Integer.parseInt(strAlbumID));
        this.m_SetAlbumName.SetAlbumName(strAlbumName);
        this.m_SetAlbumName.SaveGlobalVariable();
        EndThisPage(55, null);
    }

    private void ShowAlertDialog(String strMessage, String strTitle, int iResId) {
        Builder alertDialog = new Builder(this);
        alertDialog.setIcon(iResId);
        alertDialog.setTitle(strTitle);
        alertDialog.setMessage(strMessage);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getString(C0349R.string.OK), new C03917());
        alertDialog.show();
    }

    private void CleanTempFolderAndPref() {
        GlobalVariable_SaveLoadedMeta prefLoadedAlbumID = new GlobalVariable_SaveLoadedMeta(this, null);
        prefLoadedAlbumID.RestoreGlobalVariable();
        ArrayList<Integer> strLoadedIDList = new ArrayList();
        if (!prefLoadedAlbumID.IsNoData()) {
            prefLoadedAlbumID.GetIdAndSIDList(strLoadedIDList, null);
        }
        prefLoadedAlbumID.ClearAlbumMeta();
        prefLoadedAlbumID.ClearGlobalVariable();
        ArrayList<String> m_strThumbPathList = new ArrayList();
        Iterator it = strLoadedIDList.iterator();
        while (it.hasNext()) {
            GlobalVariable_SaveLoadedMeta prefLoadedPath = new GlobalVariable_SaveLoadedMeta(this, String.valueOf(((Integer) it.next()).intValue()));
            prefLoadedPath.RestoreGlobalVariable();
            if (!prefLoadedPath.IsNoData()) {
                prefLoadedPath.GetIdAndLoadedPathList(null, m_strThumbPathList);
            }
            Iterator it2 = m_strThumbPathList.iterator();
            while (it2.hasNext()) {
                String strThumbPath = (String) it2.next();
                if (strThumbPath != null && FileUtility.FileExist(strThumbPath)) {
                    FileUtility.DeleteFile(strThumbPath);
                }
            }
            prefLoadedPath.ClearPhotoMeta();
            prefLoadedPath.ClearGlobalVariable();
        }
        if (getExternalFilesDir(null) != null) {
            FileUtility.DeleteALLFolder(getExternalFilesDir(null).getAbsolutePath() + "/" + PringoConvenientConst.PRINGO_TEMP_FOLDER);
        }
    }

    private void CleanEditMetaPref() {
        this.m_EditMetaUtility = new EditMetaUtility(this);
        this.m_EditMetaUtility.ClearPoolEditMeta();
        this.m_EditMetaUtility.CleanFilterValuePref();
        this.m_EditMetaUtility.ClearEditMeta();
        this.m_EditMetaUtility.ClearMultiSelMeta();
    }

    public void onBackPressed() {
        this.LOG.m383d(this.TAG, "onBackPressed " + this.mFlowMode);
        if (this.m_iPathRoute != ControllerState.PLAY_COUNT_STATE) {
            EndThisPage(50, null);
        } else if (this.mFlowMode != FlowMode.CollagePeriod) {
            EndThisPage(63, null);
        } else {
            EndThisPage(29, null);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (this.mPermissionAsk != null) {
            for (int i = 0; i < permissions.length; i++) {
                this.mPermissionAsk.SetAskState(permissions[i], grantResults[i]);
            }
        }
    }
}
