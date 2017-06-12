package com.hiti.prinbiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import java.util.ArrayList;

public class BorderDelMainFragmentActivity extends FragmentActivity {
    NFCInfo mNFCInfo;
    ImageButton m_BackImageButton;
    ImageButton m_OKImageButton;
    ArrayList<String> m_strSelBorderList;
    FragmentTabHost tabHost;

    /* renamed from: com.hiti.prinbiz.BorderDelMainFragmentActivity.1 */
    class C07531 implements INfcPreview {
        C07531() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            BorderDelMainFragmentActivity.this.mNFCInfo = nfcInfo;
        }
    }

    public BorderDelMainFragmentActivity() {
        this.tabHost = null;
        this.m_BackImageButton = null;
        this.m_OKImageButton = null;
        this.m_strSelBorderList = null;
        this.mNFCInfo = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_border_del);
        this.tabHost = (FragmentTabHost) findViewById(16908306);
        this.tabHost.setup(this, getSupportFragmentManager(), C0349R.id.realtabcontent);
        Bundle m_bundle4x6 = new Bundle();
        Bundle m_bundle5x7 = new Bundle();
        Bundle m_bundle6x8 = new Bundle();
        m_bundle4x6.putString("tab", "4x6");
        m_bundle5x7.putString("tab", "5x7");
        m_bundle6x8.putString("tab", "6x8");
        this.tabHost.addTab(this.tabHost.newTabSpec("border4x6").setIndicator(SetTab("4x6")), BorderDelFragment.class, m_bundle4x6);
        this.tabHost.addTab(this.tabHost.newTabSpec("border5x7").setIndicator(SetTab("5x7")), BorderDelFragment.class, m_bundle5x7);
        this.tabHost.addTab(this.tabHost.newTabSpec("border6x8").setIndicator(SetTab("6x8")), BorderDelFragment.class, m_bundle6x8);
    }

    private View SetTab(String tabName) {
        View m_TabView = getLayoutInflater().inflate(C0349R.layout.fragment_border_label, null);
        ((TextView) m_TabView.findViewById(C0349R.id.m_BorderLabelTextView)).setText(tabName);
        return m_TabView;
    }

    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07531());
    }

    protected void onPause() {
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }
}
