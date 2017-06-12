package com.hiti.prinbiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.media.TransportMediator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.request.HitiPPR_BurnFirmware;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.printerprotocol.request.HitiPPR_RecoveryPrinter;
import com.hiti.printerprotocol.utility.BurnFWUtility;
import com.hiti.sql.oadc.OADCItem;
import com.hiti.utility.FileUtility;
import com.hiti.utility.PringoConvenientConst;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;

public class BurnFirmwareActivity extends Activity {
    String IP;
    NFCInfo mNFCInfo;
    Button m_ApplyToPrinterButton;
    AssetManager m_AssetManager;
    ImageButton m_BackButton;
    View m_BurnFirmwareDialogView;
    TextView m_BurnFirmwareFeatureDescriptionTextView;
    BurnFirmwareHandler m_BurnFirmwareHandler;
    AlertDialog m_BurnFirmwareHintDialog;
    TextView m_BurnFirmwareMSGTextView;
    ProgressBar m_BurnFirmwareProgressBar;
    TextView m_BurnFirmwareVersionTextView;
    HitiPPR_BurnFirmware m_HitiPPR_BurnFirmware;
    HitiPPR_RecoveryPrinter m_HitiPPR_RecoveryPrinter;
    ProgressBar m_ProgressBar;
    ImageView m_TitleImageView;
    TextView m_TitleTextView;
    int m_iItemSize;
    int m_iPort;
    int m_iScreenHeight;
    int m_iScreenWidth;
    String m_strProductIDString;

    /* renamed from: com.hiti.prinbiz.BurnFirmwareActivity.1 */
    class C02651 implements OnClickListener {
        C02651() {
        }

        public void onClick(View v) {
            BurnFirmwareActivity.this.onApplyToPrinterButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.prinbiz.BurnFirmwareActivity.2 */
    class C02662 implements OnClickListener {
        C02662() {
        }

        public void onClick(View v) {
            BurnFirmwareActivity.this.onBackButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.prinbiz.BurnFirmwareActivity.4 */
    class C02674 implements DialogInterface.OnClickListener {
        C02674() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    /* renamed from: com.hiti.prinbiz.BurnFirmwareActivity.5 */
    class C02685 implements DialogInterface.OnClickListener {
        C02685() {
        }

        public void onClick(DialogInterface dialog, int which) {
            BurnFirmwareActivity.this.RecoveryPrinter();
        }
    }

    /* renamed from: com.hiti.prinbiz.BurnFirmwareActivity.3 */
    class C07543 implements INfcPreview {
        C07543() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            BurnFirmwareActivity.this.mNFCInfo = nfcInfo;
        }
    }

    class BurnFirmwareHandler extends MSGHandler {
        BurnFirmwareHandler() {
        }

        public void handleMessage(Message msg) {
            Bundle buddle;
            String strTitle;
            String strMSG;
            switch (msg.what) {
                case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                    BurnFirmwareActivity.this.m_BurnFirmwareHintDialog.dismiss();
                    BurnFirmwareActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    buddle = msg.getData();
                    strTitle = BurnFirmwareActivity.this.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    BurnFirmwareActivity.this.ShowAlertDialog(strMSG, strTitle, 17301569);
                case RequestState.REQUEST_COMPLETE_DUETO_SAME_VERSION /*313*/:
                    BurnFirmwareActivity.this.m_BurnFirmwareHintDialog.dismiss();
                    BurnFirmwareActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    BurnFirmwareActivity.this.ShowAlertDialog(BurnFirmwareActivity.this.getString(C0349R.string.BURN_FIRMWARE_SAME_VERSION), BurnFirmwareActivity.this.getString(C0349R.string.COMPLETE), 17301569);
                case RequestState.REQUEST_COMPLETE_DUETO_CHECK_SUM_ERROR /*314*/:
                    BurnFirmwareActivity.this.m_BurnFirmwareHintDialog.dismiss();
                    BurnFirmwareActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    strTitle = BurnFirmwareActivity.this.getString(C0349R.string.ERROR);
                    BurnFirmwareActivity.this.ShowCancelAlertDialog(BurnFirmwareActivity.this.getString(C0349R.string.ERROR_PRINTER_FIRMWARE_UPDATE), strTitle, 17301569);
                case RequestState.REQUEST_BURN_FIRMWARE /*315*/:
                    BurnFirmwareActivity.this.m_BurnFirmwareHintDialog.dismiss();
                    BurnFirmwareActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    BurnFirmwareActivity.this.ShowAlertDialog(BurnFirmwareActivity.this.getString(C0349R.string.BURN_FIRMWARE_SUCCESS), BurnFirmwareActivity.this.getString(C0349R.string.COMPLETE), 17301569);
                case RequestState.REQUEST_BURN_FIRMWARE_PROGRESS /*316*/:
                    buddle = msg.getData();
                    strMSG = OADCItem.WATCH_TYPE_NON;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    BurnFirmwareActivity.this.m_BurnFirmwareProgressBar.incrementProgressBy(Integer.parseInt(strMSG));
                    if (BurnFirmwareActivity.this.m_BurnFirmwareProgressBar.getProgress() == 80) {
                        BurnFirmwareActivity.this.m_BurnFirmwareHintDialog.setTitle(C0349R.string.BURN_FIRMWARE_CHECK_DATA);
                    }
                case RequestState.REQUEST_BURN_FIRMWARE_ERROR /*317*/:
                    BurnFirmwareActivity.this.m_BurnFirmwareHintDialog.dismiss();
                    BurnFirmwareActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    buddle = msg.getData();
                    strTitle = BurnFirmwareActivity.this.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    BurnFirmwareActivity.this.ShowAlertDialog(strMSG, strTitle, 17301560);
                default:
            }
        }
    }

    public BurnFirmwareActivity() {
        this.m_BurnFirmwareHintDialog = null;
        this.m_BurnFirmwareDialogView = null;
        this.m_BurnFirmwareProgressBar = null;
        this.m_BurnFirmwareMSGTextView = null;
        this.m_BurnFirmwareHandler = null;
        this.m_ApplyToPrinterButton = null;
        this.m_TitleImageView = null;
        this.m_BackButton = null;
        this.m_TitleTextView = null;
        this.m_ProgressBar = null;
        this.m_iScreenWidth = 0;
        this.m_iScreenHeight = 0;
        this.m_iItemSize = 0;
        this.m_HitiPPR_BurnFirmware = null;
        this.m_HitiPPR_RecoveryPrinter = null;
        this.m_AssetManager = null;
        this.m_strProductIDString = WirelessType.TYPE_P520L;
        this.m_BurnFirmwareFeatureDescriptionTextView = null;
        this.m_BurnFirmwareVersionTextView = null;
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        this.mNFCInfo = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0349R.layout.activity_burn_firmware);
        GetBundle();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.m_iScreenWidth = dm.widthPixels;
        this.m_iScreenHeight = dm.heightPixels;
        this.m_iItemSize = this.m_iScreenWidth / 6;
        this.m_TitleTextView = (TextView) findViewById(C0349R.id.m_TitleTextView);
        this.m_TitleTextView.setText((String) getResources().getText(C0349R.string.BurnFirmwareActivity_m_TitleTextView));
        this.m_ApplyToPrinterButton = (Button) findViewById(C0349R.id.m_ApplyToPrinterButton);
        this.m_ApplyToPrinterButton.setOnClickListener(new C02651());
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_BackButton.setOnClickListener(new C02662());
        this.m_ProgressBar = (ProgressBar) findViewById(C0349R.id.loadThumbnailProgressBar);
        this.m_ProgressBar.setVisibility(0);
        this.m_ProgressBar.setVisibility(8);
        this.m_ProgressBar.setIndeterminate(true);
        this.m_BurnFirmwareHandler = new BurnFirmwareHandler();
        this.m_AssetManager = getAssets();
        this.m_BurnFirmwareFeatureDescriptionTextView = (TextView) findViewById(C0349R.id.m_BurnFirmwareFeatureDescriptionTextView);
        this.m_BurnFirmwareVersionTextView = (TextView) findViewById(C0349R.id.m_BurnFirmwareVersionTextView);
        String strDescriptionTextView = XmlPullParser.NO_NAMESPACE;
        int iElementID = 0;
        if (this.m_strProductIDString.equals(WirelessType.TYPE_P520L)) {
            strDescriptionTextView = getString(C0349R.string.descriptions_p520l);
            iElementID = 7;
        } else if (this.m_strProductIDString.equals(WirelessType.TYPE_P310W)) {
            strDescriptionTextView = getString(C0349R.string.descriptions_p310w);
            iElementID = 9;
        } else if (this.m_strProductIDString.equals(WirelessType.TYPE_P750L)) {
            strDescriptionTextView = getString(C0349R.string.descriptions_p750l);
            iElementID = 8;
        } else if (this.m_strProductIDString.equals(WirelessType.TYPE_P530D)) {
            strDescriptionTextView = getString(C0349R.string.descriptions_p530D);
            iElementID = 11;
        }
        this.m_BurnFirmwareFeatureDescriptionTextView.setText(strDescriptionTextView);
        this.m_BurnFirmwareVersionTextView.setText(getString(C0349R.string.THE_LAST_FIRMWARE) + ((String) BurnFWUtility.GetTheNewestFWVersion((Context) this, iElementID, false).second));
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07543());
    }

    protected void onPause() {
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackButtonClicked(View v) {
        setResult(0, new Intent());
        finish();
    }

    void GetBundle() {
        Bundle Bundle = getIntent().getExtras();
        if (Bundle != null) {
            this.m_strProductIDString = Bundle.getString(JumpBundleMessage520.BUNDLE_MSG_WIRELESS_TYPE);
            this.IP = Bundle.getString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP);
            this.m_iPort = Bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT);
        }
    }

    public void onApplyToPrinterButtonClicked(View v) {
        CreateBurnFirmwareHintDialog();
        this.m_BurnFirmwareHintDialog.show();
        getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        this.m_HitiPPR_BurnFirmware = new HitiPPR_BurnFirmware(this, this.IP, this.m_iPort, this.m_BurnFirmwareHandler);
        if (this.m_strProductIDString.equals(WirelessType.TYPE_P520L)) {
            PrePareFirmwareFile(7, PringoConvenientConst.FIRMWARE_PATH_P520L);
        } else if (this.m_strProductIDString.equals(WirelessType.TYPE_P310W)) {
            PrePareFirmwareFile(9, PringoConvenientConst.FIRMWARE_PATH_P310W);
        } else if (this.m_strProductIDString.equals(WirelessType.TYPE_P750L)) {
            PrePareFirmwareFile(8, PringoConvenientConst.FIRMWARE_PATH_P750L);
        } else if (this.m_strProductIDString.equals(WirelessType.TYPE_P461)) {
            PrePareFirmwareFile(10, PringoConvenientConst.FIRMWARE_PATH_P461);
        } else if (this.m_strProductIDString.equals(WirelessType.TYPE_P530D)) {
            PrePareFirmwareFile(11, PringoConvenientConst.FIRMWARE_PATH_P530D);
        }
        this.m_HitiPPR_BurnFirmware.start();
    }

    private void PrePareFirmwareFile(int iModel, String strFWpath) {
        IOException e;
        try {
            if (((Integer) BurnFWUtility.GetTheNewestFWVersion((Context) this, iModel, true).first).intValue() == 0) {
                this.m_HitiPPR_BurnFirmware.SetFirmwareInputStream(this.m_AssetManager.open(strFWpath));
                return;
            }
            try {
                File file = new File(FileUtility.GetSDAppRootPath(this) + "/" + strFWpath);
                Log.e("BurnFW", file.getPath());
                InputStream is = new BufferedInputStream(new FileInputStream(file));
                try {
                    this.m_HitiPPR_BurnFirmware.SetFirmwareInputStream(is);
                } catch (IOException e2) {
                    e = e2;
                    InputStream inputStream = is;
                    e.printStackTrace();
                }
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

    void CreateBurnFirmwareHintDialog() {
        if (this.m_BurnFirmwareHintDialog == null) {
            this.m_BurnFirmwareHintDialog = new Builder(this).create();
            this.m_BurnFirmwareHintDialog.setCanceledOnTouchOutside(false);
            this.m_BurnFirmwareHintDialog.setCancelable(false);
            this.m_BurnFirmwareDialogView = getLayoutInflater().inflate(C0349R.layout.dialog_burn_firmware, null);
            this.m_BurnFirmwareProgressBar = (ProgressBar) this.m_BurnFirmwareDialogView.findViewById(C0349R.id.m_BurnFirmwareProgressBar);
            this.m_BurnFirmwareProgressBar.setVisibility(0);
            this.m_BurnFirmwareMSGTextView = (TextView) this.m_BurnFirmwareDialogView.findViewById(C0349R.id.m_BurnFirmwareMSGTextView);
            this.m_BurnFirmwareMSGTextView.setText("\n" + getString(C0349R.string.PLEASE_WAIT) + "\n");
            this.m_BurnFirmwareHintDialog.setView(this.m_BurnFirmwareDialogView);
        }
        this.m_BurnFirmwareProgressBar.incrementProgressBy(-100);
        this.m_BurnFirmwareHintDialog.setTitle(C0349R.string.SETTING_DATA_TO_PRINTER);
    }

    public void ShowAlertDialog(String strMessage, String strTitle, int iResId) {
        Builder alertDialog = new Builder(this);
        alertDialog.setIcon(iResId);
        alertDialog.setTitle(strTitle);
        alertDialog.setMessage(strMessage);
        alertDialog.setPositiveButton(getString(C0349R.string.OK), new C02674());
        alertDialog.show();
    }

    public void ShowCancelAlertDialog(String strMessage, String strTitle, int iResId) {
        AlertDialog alertDialog = new Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setIcon(iResId);
        alertDialog.setTitle(strTitle);
        alertDialog.setMessage(strMessage);
        alertDialog.setButton(-1, getString(C0349R.string.OK), new C02685());
        alertDialog.show();
    }

    void RecoveryPrinter() {
        this.m_HitiPPR_RecoveryPrinter = new HitiPPR_RecoveryPrinter(this, this.IP, this.m_iPort, this.m_BurnFirmwareHandler);
        this.m_HitiPPR_RecoveryPrinter.SetSilentMode();
        this.m_HitiPPR_RecoveryPrinter.start();
    }
}
