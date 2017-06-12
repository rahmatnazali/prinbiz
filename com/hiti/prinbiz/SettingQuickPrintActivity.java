package com.hiti.prinbiz;

import android.app.Activity;
import android.os.Bundle;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.utility.LogManager;

public class SettingQuickPrintActivity extends SettingPrintAttrActivity {

    /* renamed from: com.hiti.prinbiz.SettingQuickPrintActivity.1 */
    class C07941 implements INfcPreview {
        C07941() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            SettingQuickPrintActivity.this.mNFCInfo = nfcInfo;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.LOG = new LogManager(0);
        SetPref(ControllerState.PLAY_PHOTO);
        super.onCreate(savedInstanceState);
    }

    protected void onResume() {
        super.onResume();
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07941());
    }

    protected void onPause() {
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }
}
