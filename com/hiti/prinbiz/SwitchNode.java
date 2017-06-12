package com.hiti.prinbiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.hiti.jumpinfo.JumpInfo;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.service.upload.UploadService;
import com.hiti.trace.GlobalVariable_AlbumSelInfo;
import com.hiti.trace.GlobalVariable_SaveLoadedMeta;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.telnet.TelnetOption;
import org.kxml2.wap.Wbxml;
import org.kxml2.wap.WbxmlParser;

public class SwitchNode extends Activity {
    LogManager LOG;
    String TAG;
    NFCInfo mNFCInfo;
    boolean m_bChangePage;
    boolean m_bID_Collage;
    private GlobalVariable_AlbumSelInfo m_prefAlbumInfo;
    JumpPreferenceKey pref;

    /* renamed from: com.hiti.prinbiz.SwitchNode.1 */
    class C07971 implements INfcPreview {
        C07971() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            SwitchNode.this.mNFCInfo = nfcInfo;
            if (NFCInfo.IsNFCForHitiApp(nfcInfo)) {
                SwitchNode.this.LOG.m383d(SwitchNode.this.TAG, "onResume SwitchPage");
                SwitchNode.this.SwitchPage(64, NFCInfo.PackNFCData(SwitchNode.this.mNFCInfo));
                return;
            }
            SwitchNode.this.finish();
        }
    }

    public SwitchNode() {
        this.pref = null;
        this.m_bID_Collage = false;
        this.m_bChangePage = false;
        this.m_prefAlbumInfo = null;
        this.mNFCInfo = null;
        this.TAG = null;
        this.LOG = new LogManager(0);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = getClass().getSimpleName();
        this.LOG.m386v("onCreate", String.valueOf("SwitchNode"));
        if (!GetBundle()) {
            this.LOG.m386v(this.TAG, String.valueOf("onCreate savedInstanceState: ") + savedInstanceState);
            if (savedInstanceState == null) {
                this.pref.SetPreference(JumpPreferenceKey.ANIMATION_DONE, false);
            } else {
                this.pref.SetPreference(JumpPreferenceKey.ANIMATION_DONE, true);
            }
        }
    }

    private boolean GetBundle() {
        this.pref = new JumpPreferenceKey(this);
        this.m_bID_Collage = this.pref.GetStatePreference("BackID_Collage");
        if (this.m_bID_Collage) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                Intent intent;
                if (GetPref() == 1) {
                    intent = new Intent(this, GalleryActivity.class);
                } else {
                    intent = new Intent(this, GalleryFromPrinterActivity.class);
                }
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
                return true;
            }
            this.pref.SetPreference("BackID_Collage", false);
            this.pref.SetPreference("m_bFromCollageBegin", false);
        }
        return false;
    }

    private int GetPref() {
        this.m_prefAlbumInfo = new GlobalVariable_AlbumSelInfo(this, this.m_bID_Collage);
        this.m_prefAlbumInfo.RestoreGlobalVariable();
        return this.m_prefAlbumInfo.GetAlbumRoute();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.LOG.m386v("switch_onActivityResult", String.valueOf(resultCode));
        this.m_bChangePage = true;
        Bundle bundle = null;
        if (data != null) {
            bundle = data.getExtras();
        }
        SwitchPage(resultCode, bundle);
    }

    private void SwitchPage(int resultCode, Bundle data) {
        this.LOG.m386v("SwitchPage", String.valueOf(resultCode));
        Intent intent = new Intent();
        if (data != null) {
            intent.putExtras(data);
        }
        switch (resultCode) {
            case TelnetOption.REGIME_3270 /*29*/:
                if (this.pref == null) {
                    this.pref = new JumpPreferenceKey(this);
                }
                this.pref.SetPreference("BackID_Collage", false);
                setResult(29, intent);
                finish();
                return;
            case TelnetOption.WINDOW_SIZE /*31*/:
                intent.setClass(this, AlbumActivity.class);
                break;
            case JumpInfo.RESULT_MAIN_ACTIVITY /*50*/:
                intent.setClass(this, MainActivity.class);
                break;
            case JumpInfo.RESULT_SOURCE_ACTIVITY /*51*/:
                intent.setClass(this, SourceActivity.class);
                break;
            case JumpInfo.RESULT_ALBUM_FROM_PRINTER_ACTIVITY /*54*/:
                intent.setClass(this, AlbumFromPrinterActivity.class);
                break;
            case JumpInfo.RESULT_PHOTO_ACTIVITY /*55*/:
                intent.setClass(this, GalleryActivity.class);
                break;
            case JumpInfo.RESULT_PHOTO_FROM_PRINTER_ACTIVITY /*56*/:
                intent.setClass(this, GalleryFromPrinterActivity.class);
                break;
            case JumpInfo.RESULT_POOL_ACTIVITY /*57*/:
                intent.setClass(this, PoolActivity.class);
                break;
            case JumpInfo.RESULT_POOL_ID_ACTIVITY /*58*/:
                intent.putExtra("TYPE", 58);
                intent.setClass(this, CollageActivity.class);
                break;
            case JumpInfo.RESULT_EDIT_GENREAL_ACTIVITY /*59*/:
                intent.setClass(getApplicationContext(), EditGeneralActivity.class);
                break;
            case JumpInfo.RESULT_EDIT_ID_ACTIVITY /*60*/:
                intent.setClass(this, EditIdActivity.class);
                break;
            case JumpInfo.RESULT_END /*61*/:
                onDestroy();
                break;
            case JumpInfo.RESULT_PRINT_VIEW_ACTIVITY /*62*/:
                intent.setClass(this, PrintViewActivity.class);
                break;
            case JumpInfo.RESULT_ID_SET_ACTIVITY /*63*/:
                intent.setClass(this, SettingIDActivity.class);
                break;
            case WbxmlParser.WAP_EXTENSION /*64*/:
                intent.setClass(this, ModelActivity.class);
                break;
            case Wbxml.EXT_I_1 /*65*/:
                intent.setClass(this, SettingActivity.class);
                break;
            default:
                intent.setClass(getApplicationContext(), MainActivity.class);
                break;
        }
        if (resultCode != 61) {
            startActivityForResult(intent, 0);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        this.LOG.m386v(this.TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        this.LOG.m386v("onResume", String.valueOf("SwitchNode"));
        if (!this.m_bChangePage && !this.m_bID_Collage) {
            NFCInfo.PreviewNFCData(this, new C07971(), new NfcListener());
        }
    }

    protected void onPause() {
        this.LOG.m386v("onPause", String.valueOf("SwitchNode"));
        if (!(this.m_bChangePage || this.m_bID_Collage || this.mNFCInfo.mNfcAdapter == null)) {
            this.mNFCInfo = NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    protected void onStop() {
        this.LOG.m386v("onStop", String.valueOf("SwitchNode"));
        super.onStop();
    }

    protected void onDestroy() {
        this.LOG.m386v("onDestroy", String.valueOf("SwitchNode"));
        UploadService.StopUploadService(this, UploadService.class);
        CleanTempFolder();
        finish();
        super.onDestroy();
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
