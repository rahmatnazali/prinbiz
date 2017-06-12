package com.hiti.prinbiz;

import android.app.Activity;
import android.os.Bundle;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.utility.LogManager;

public class SettingGeneralPrintActivity extends SettingPrintAttrActivity {

    /* renamed from: com.hiti.prinbiz.SettingGeneralPrintActivity.1 */
    class C07901 implements INfcPreview {
        C07901() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            SettingGeneralPrintActivity.this.mNFCInfo = nfcInfo;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.LOG = new LogManager(0);
        SetPref(ControllerState.NO_PLAY_ITEM);
        super.onCreate(savedInstanceState);
    }

    protected void onResume() {
        super.onResume();
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07901());
    }

    protected void onPause() {
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }
}
