package com.hiti.prinbiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.ui.hitiwebview.HITI_WEB_VIEW_STATUS;
import com.hiti.utility.LogManager;
import org.apache.commons.net.pop3.POP3;

public class InfoActivity extends Activity {
    private static final String INFO_HTML_PATH = "file:///android_asset/INFO_PRINGO/info.html";
    private static final String INFO_HTML_PATH_310W = "file:///android_asset/INFO_PRINGO/info-310w.html";
    String HOME_PATH;
    LogManager LOG;
    NFCInfo mNFCInfo;
    ImageButton m_BackButton;
    TextView m_TitleTextView;
    WebView m_WebView;
    ProgressBar m_WebViewLoadProgressBar;
    TextView m_WebViewLoadTextView;

    /* renamed from: com.hiti.prinbiz.InfoActivity.1 */
    class C03081 implements OnClickListener {
        C03081() {
        }

        public void onClick(View v) {
            InfoActivity.this.onBackButtonClicked(v);
        }
    }

    private class WatchWebViewClient extends WebViewClient {
        private WatchWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            InfoActivity.this.WebViewProgress(true);
            InfoActivity.this.LOG.m385i("shouldOverrideUrlLoading", url.toString());
            return super.shouldOverrideUrlLoading(view, url);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            InfoActivity.this.WebViewProgress(true);
            InfoActivity.this.LOG.m385i("onPageStarted", url.toString());
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            InfoActivity.this.WebViewProgress(false);
            InfoActivity.this.LOG.m385i("onPageFinished", url.toString());
            super.onPageFinished(view, url);
        }

        public void onLoadResource(WebView view, String url) {
            InfoActivity.this.LOG.m385i("onLoadResource", url.toString());
            super.onLoadResource(view, url);
        }

        public void onReceivedError(WebView view, int errorCod, String description, String failingUrl) {
            String strError;
            InfoActivity.this.WebViewProgress(false);
            switch (errorCod) {
                case HITI_WEB_VIEW_STATUS.ERROR_TOO_MANY_REQUESTS /*-15*/:
                    strError = "ERROR_TOO_MANY_REQUESTS";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_FILE_NOT_FOUND /*-14*/:
                    strError = "ERROR_FILE_NOT_FOUND";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_FILE /*-13*/:
                    strError = "ERROR_FILE";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_BAD_URL /*-12*/:
                    strError = "ERROR_BAD_URL";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_FAILED_SSL_HANDSHAKE /*-11*/:
                    strError = "ERROR_FAILED_SSL_HANDSHAKE";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_UNSUPPORTED_SCHEME /*-10*/:
                    strError = "ERROR_UNSUPPORTED_SCHEME";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_REDIRECT_LOOP /*-9*/:
                    strError = "ERROR_REDIRECT_LOOP";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_TIMEOUT /*-8*/:
                    strError = "ERROR_TIMEOUT";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_IO /*-7*/:
                    strError = "ERROR_IO";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_CONNECT /*-6*/:
                    strError = "ERROR_CONNECT";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_PROXY_AUTHENTICATION /*-5*/:
                    strError = "ERROR_PROXY_AUTHENTICATION";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_AUTHENTICATION /*-4*/:
                    strError = "ERROR_AUTHENTICATION";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_UNSUPPORTED_AUTH_SCHEME /*-3*/:
                    strError = "ERROR_UNSUPPORTED_AUTH_SCHEME";
                    break;
                case HITI_WEB_VIEW_STATUS.ERROR_HOST_LOOKUP /*-2*/:
                    strError = "ERROR_HOST_LOOKUP";
                    InfoActivity.this.ClearWebView();
                    break;
                case POP3.DISCONNECTED_STATE /*-1*/:
                    strError = "ERROR_UNKNOWN";
                    InfoActivity.this.ClearWebView();
                    break;
                default:
                    strError = "UNKNOWN";
                    break;
            }
            Log.e("onReceivedError", strError + String.valueOf(errorCod));
            InfoActivity.this.LOG.m385i("onReceivedError", String.valueOf(errorCod));
        }
    }

    /* renamed from: com.hiti.prinbiz.InfoActivity.2 */
    class C07672 implements INfcPreview {
        C07672() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            InfoActivity.this.mNFCInfo = nfcInfo;
        }
    }

    public InfoActivity() {
        this.HOME_PATH = INFO_HTML_PATH;
        this.m_WebView = null;
        this.m_WebViewLoadTextView = null;
        this.m_WebViewLoadProgressBar = null;
        this.m_TitleTextView = null;
        this.m_BackButton = null;
        this.mNFCInfo = null;
        this.LOG = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0349R.layout.activity_info);
        this.LOG = new LogManager(0);
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_BackButton.setOnClickListener(new C03081());
        SetupWebView();
        this.LOG.m385i("onCreate()", "InfoActivity");
    }

    protected void onStart() {
        this.LOG.m385i("onStart()", "InfoActivity");
        super.onStart();
    }

    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void onResume() {
        this.LOG.m385i("onResure()", "InfoActivity");
        super.onResume();
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07672());
    }

    protected void onPause() {
        this.LOG.m385i("onPause()", "InfoActivity");
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    protected void onStop() {
        this.LOG.m385i("onStop()", "InfoActivity");
        super.onStop();
    }

    public void onDestroy() {
        this.LOG.m385i("onDestroy", "InfoActivity");
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackButtonClicked(View v) {
        setResult(0, new Intent());
        try {
            this.m_WebView.stopLoading();
            ((ViewGroup) this.m_WebView.getParent()).removeView(this.m_WebView);
            this.m_WebView.destroy();
            this.m_WebViewLoadTextView.setVisibility(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    public void onBackPressed() {
        onBackButtonClicked(null);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void SetupWebView() {
        this.m_WebView = (WebView) findViewById(C0349R.id.m_WebView);
        this.m_WebViewLoadProgressBar = (ProgressBar) findViewById(C0349R.id.m_WebViewLoadProgressBar);
        this.m_WebViewLoadTextView = (TextView) findViewById(C0349R.id.m_WebViewLoadTextView);
        WebSettings websettings = this.m_WebView.getSettings();
        websettings.setSupportZoom(true);
        websettings.setBuiltInZoomControls(true);
        websettings.setJavaScriptEnabled(true);
        websettings.setDomStorageEnabled(true);
        String strPrinterModel = new JumpPreferenceKey(this).GetModelPreference();
        this.LOG.m385i(getClass().getSimpleName(), "PrinterModel: " + strPrinterModel);
        String str = strPrinterModel == null ? this.HOME_PATH : strPrinterModel.equals(WirelessType.TYPE_P310W) ? INFO_HTML_PATH_310W : this.HOME_PATH;
        this.HOME_PATH = str;
        this.m_WebView.setWebViewClient(new WatchWebViewClient());
        this.m_WebView.loadUrl(this.HOME_PATH);
    }

    void WebViewProgress(boolean boRun) {
        if (boRun) {
            this.m_WebViewLoadProgressBar.setVisibility(0);
            this.m_WebViewLoadProgressBar.setIndeterminate(true);
            return;
        }
        this.m_WebViewLoadProgressBar.setVisibility(8);
    }

    void ClearWebView() {
        this.m_WebView.loadUrl("about:blank");
        this.m_WebView.setVisibility(8);
        this.m_WebViewLoadTextView.setVisibility(0);
    }
}
