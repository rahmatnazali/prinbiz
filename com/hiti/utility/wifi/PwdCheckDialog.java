package com.hiti.utility.wifi;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.media.TransportMediator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.resource.ResourceId;
import com.hiti.utility.resource.ResourceId.Page;

public class PwdCheckDialog {
    private ResourceId mRID;
    private Dialog m_AskPwdCheckDialog;
    private Button m_CancelButton;
    private MSGListener m_MSGListener;
    private Button m_OKButton;
    private String m_Password;
    private EditText m_PwdEditText;
    private Context m_context;

    /* renamed from: com.hiti.utility.wifi.PwdCheckDialog.1 */
    class C04871 implements OnClickListener {
        C04871() {
        }

        public void onClick(View v) {
            if (PwdCheckDialog.this.m_Password.equals(PwdCheckDialog.this.m_PwdEditText.getText().toString())) {
                PwdCheckDialog.this.m_AskPwdCheckDialog.dismiss();
                if (PwdCheckDialog.this.m_MSGListener != null) {
                    PwdCheckDialog.this.m_MSGListener.Close();
                    return;
                }
                return;
            }
            PwdCheckDialog.this.ShowErrorDialog();
        }
    }

    /* renamed from: com.hiti.utility.wifi.PwdCheckDialog.2 */
    class C04882 implements OnClickListener {
        C04882() {
        }

        public void onClick(View v) {
            PwdCheckDialog.this.m_AskPwdCheckDialog.dismiss();
        }
    }

    /* renamed from: com.hiti.utility.wifi.PwdCheckDialog.3 */
    class C04893 implements DialogInterface.OnClickListener {
        C04893() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    public PwdCheckDialog(Context context) {
        this.m_context = null;
        this.m_AskPwdCheckDialog = null;
        this.m_PwdEditText = null;
        this.m_OKButton = null;
        this.m_CancelButton = null;
        this.m_Password = null;
        this.m_MSGListener = null;
        this.mRID = null;
        this.m_context = context;
        this.mRID = new ResourceId(context, Page.PwdCheck);
    }

    public void ShowDialog(String strPwd) {
        this.m_Password = strPwd;
        AskPwdCheckDialog();
    }

    public void SetMSGListener(MSGListener listener) {
        this.m_MSGListener = listener;
    }

    public void DismissDialog() {
        if (this.m_AskPwdCheckDialog != null) {
            this.m_AskPwdCheckDialog.dismiss();
            this.m_AskPwdCheckDialog.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        }
    }

    public boolean IsShowing() {
        if (this.m_AskPwdCheckDialog != null) {
            return this.m_AskPwdCheckDialog.isShowing();
        }
        return false;
    }

    private void AskPwdCheckDialog() {
        if (this.m_AskPwdCheckDialog == null) {
            this.m_AskPwdCheckDialog = new Dialog(this.m_context, this.mRID.R_STYLE_Dialog_MSG);
            this.m_AskPwdCheckDialog.setContentView(this.mRID.R_LAYOUT_dialog_pwd_check);
            this.m_AskPwdCheckDialog.setCanceledOnTouchOutside(false);
            this.m_AskPwdCheckDialog.setCancelable(false);
            this.m_PwdEditText = (EditText) this.m_AskPwdCheckDialog.findViewById(this.mRID.R_ID_m_PwdPasswordEditText);
            this.m_OKButton = (Button) this.m_AskPwdCheckDialog.findViewById(this.mRID.R_ID_m_PwdOKButton);
            this.m_CancelButton = (Button) this.m_AskPwdCheckDialog.findViewById(this.mRID.R_ID_m_PwdCancelButton);
            this.m_OKButton.setOnClickListener(new C04871());
            this.m_CancelButton.setOnClickListener(new C04882());
        }
        this.m_AskPwdCheckDialog.getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        this.m_AskPwdCheckDialog.show();
    }

    public void ShowErrorDialog() {
        Builder errorDialog = new Builder(this.m_context);
        errorDialog.setCancelable(false);
        errorDialog.setIcon(17301569);
        errorDialog.setTitle(this.m_context.getString(this.mRID.R_STRING_ERROR_TITLT));
        errorDialog.setMessage(this.m_context.getString(this.mRID.R_STRING_MSG_PWD_WRONG));
        errorDialog.setPositiveButton(this.m_context.getString(this.mRID.R_STRING_OK), new C04893());
        errorDialog.show();
    }
}
