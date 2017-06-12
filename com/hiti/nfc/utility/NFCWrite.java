package com.hiti.nfc.utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.jni.hello.Hello;
import com.hiti.nfc.ByteUtility;
import com.hiti.nfc.utility.NFCInfo.NFCMode;
import com.hiti.utility.LogManager;
import com.hiti.utility.resource.ResourceId;
import com.hiti.utility.resource.ResourceId.Page;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public class NFCWrite {
    LogManager LOG;
    Button mCloseButton;
    Context mContext;
    Handler mNFCWriteHandler;
    NfcListener mNfcListener;
    Button mOKButton;
    String mPassword;
    String mPrinterModel;
    ResourceId mRID;
    String mSSID;
    Dialog mWriteDialog;
    TextView mWriteNfcMessageTextView;

    /* renamed from: com.hiti.nfc.utility.NFCWrite.1 */
    class C02401 implements OnClickListener {
        C02401() {
        }

        public void onClick(View v) {
            NFCWrite.this.mWriteDialog.dismiss();
            if (NFCWrite.this.mNfcListener != null) {
                NFCWrite.this.mNfcListener.WriteDialogClose();
            }
        }
    }

    /* renamed from: com.hiti.nfc.utility.NFCWrite.2 */
    class C02412 extends Handler {
        C02412() {
        }

        public void handleMessage(Message msg) {
            NFCWrite.this.showNfcWriteDialog(msg.what);
        }
    }

    public class CTextWatcher implements TextWatcher {
        View vOkView;
        View vPasswordView;

        public CTextWatcher(View view) {
            this.vPasswordView = view;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            NFCWrite.this.LOG.m386v("onTextChanged", "s= " + s + " , start= " + start + " , before= " + before + " , count= " + count);
            if (s.length() == 0) {
                this.vPasswordView.setEnabled(false);
            } else {
                this.vPasswordView.setEnabled(true);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    public NFCWrite(Context context, NfcListener listener) {
        this.mContext = null;
        this.mNfcListener = null;
        this.mWriteDialog = null;
        this.mWriteNfcMessageTextView = null;
        this.mOKButton = null;
        this.mCloseButton = null;
        this.mRID = null;
        this.LOG = null;
        this.mSSID = null;
        this.mPassword = null;
        this.mPrinterModel = null;
        this.mNFCWriteHandler = new C02412();
        this.mContext = context;
        this.mNfcListener = listener;
        this.mRID = new ResourceId(context, Page.NfcWrite);
        this.LOG = new LogManager(0);
        InitWriteDialog();
    }

    private void InitWriteDialog() {
        if (this.mWriteDialog == null) {
            this.mWriteDialog = new Dialog(this.mContext, this.mRID.R_STYLE_Dialog_MSG);
            View dialogView = this.mWriteDialog.getLayoutInflater().inflate(this.mRID.R_LAYOUT_NFC_WRITE_DIALOG, null);
            this.mWriteNfcMessageTextView = (TextView) dialogView.findViewById(this.mRID.R_ID_m_NfcDialogTextMessage);
            this.mOKButton = (Button) dialogView.findViewById(this.mRID.R_ID_m_NfcOKButton);
            this.mOKButton.setOnClickListener(new C02401());
            this.mWriteDialog.setContentView(dialogView);
            this.mWriteDialog.setCancelable(false);
            this.mWriteDialog.setCanceledOnTouchOutside(false);
        }
    }

    public void ShowWriteDialog() {
        this.mWriteDialog.show();
    }

    public void showNfcWriteDialog(int status) {
        switch (status) {
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                this.mWriteNfcMessageTextView.setText(this.mContext.getString(this.mRID.R_STRING_NFC_WRITE_FAIL));
                this.mWriteNfcMessageTextView.append(this.mContext.getString(this.mRID.R_STRING_NFC_APPROACH_TAG));
                this.mOKButton.setVisibility(0);
                this.mOKButton.setText(this.mContext.getString(this.mRID.R_STRING_CANCEL));
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                this.mWriteNfcMessageTextView.setText(this.mContext.getString(this.mRID.R_STRING_NFC_WRITE_SUCCESS));
                this.mOKButton.setVisibility(0);
                this.mOKButton.setText(this.mContext.getString(this.mRID.R_STRING_OK));
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                this.mWriteNfcMessageTextView.setText(this.mContext.getString(this.mRID.R_STRING_NFC_IS_WRITING));
                this.mOKButton.setVisibility(4);
            default:
        }
    }

    public void SetWriteInfo(String SSID, String password, String printerModel) {
        this.mSSID = SSID;
        this.mPassword = password;
        this.mPrinterModel = printerModel;
    }

    public void WriteTagProcess(NFCInfo mNFCInfo) {
        if (mNFCInfo.mNFCTag != NFCMode.NFCRead) {
            if (mNFCInfo.mNfc != null) {
                showNfcWriteDialog(5);
                String SSID = this.mSSID;
                String password = this.mPassword;
                String newString = null;
                if (SSID != null) {
                    StringBuilder builder = new StringBuilder(ByteUtility.Ascii2string(new byte[]{Integer.valueOf(SSID.length()).byteValue()})).append(SSID);
                    if (password != null) {
                        this.LOG.m386v("password", "=" + password);
                        builder.append(NFCInfo.xorEncrypt(password, Hello.SayGoodBye(this.mContext, 1119)));
                    }
                    newString = builder.toString();
                }
                this.LOG.m386v("write info", "=" + newString);
                mNFCInfo.mNfc.writeNFC(newString, this.mPrinterModel, this.mContext.getPackageName(), this.mNFCWriteHandler);
                return;
            }
            showNfcWriteDialog(3);
        }
    }
}
