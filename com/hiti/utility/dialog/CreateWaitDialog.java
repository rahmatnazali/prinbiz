package com.hiti.utility.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.media.TransportMediator;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public class CreateWaitDialog {
    private int R_ID_RATIO_TEXTVIEW;
    private int R_ID_WAITING_PROGRESS;
    private int R_ID_WAITING_TEXTVIEW;
    private int R_LAYOUT_DIALOG_WAITING;
    private int R_STYLE_DIALOG_MSG;
    private TextView m_RatioTextView;
    private View m_WaitingDialogView;
    private Dialog m_WaitingHintDialog;
    private TextView m_WaitingMSGTextView;
    private ProgressBar m_WaitingProgressBar;
    private Context m_context;

    public CreateWaitDialog(Context context) {
        this.m_context = null;
        this.m_WaitingHintDialog = null;
        this.m_WaitingDialogView = null;
        this.m_WaitingProgressBar = null;
        this.m_WaitingMSGTextView = null;
        this.m_RatioTextView = null;
        this.R_STYLE_DIALOG_MSG = 0;
        this.R_LAYOUT_DIALOG_WAITING = 0;
        this.R_ID_WAITING_PROGRESS = 0;
        this.R_ID_WAITING_TEXTVIEW = 0;
        this.R_ID_RATIO_TEXTVIEW = 0;
        this.m_context = context;
        GetResourceID();
    }

    void GetResourceID() {
        this.R_STYLE_DIALOG_MSG = ResourceSearcher.getId(this.m_context, RS_TYPE.STYLE, "Dialog_MSG");
        this.R_LAYOUT_DIALOG_WAITING = ResourceSearcher.getId(this.m_context, RS_TYPE.LAYOUT, "dialog_waiting");
        this.R_ID_WAITING_PROGRESS = ResourceSearcher.getId(this.m_context, RS_TYPE.ID, "m_WaitingProgressBar");
        this.R_ID_WAITING_TEXTVIEW = ResourceSearcher.getId(this.m_context, RS_TYPE.ID, "m_WaitingMSGTextView");
        this.R_ID_RATIO_TEXTVIEW = ResourceSearcher.getId(this.m_context, RS_TYPE.ID, "m_RatioTextView");
    }

    public void ShowDialog(String strMSG) {
        CreateWaitingHintDialog(null, strMSG);
    }

    public void ShowDialog(String strRatio, String strMSG) {
        CreateWaitingHintDialog(strRatio, strMSG);
    }

    public void DismissDialog() {
        if (this.m_WaitingHintDialog != null) {
            this.m_WaitingHintDialog.dismiss();
            this.m_WaitingHintDialog.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        }
    }

    public boolean IsShowing() {
        if (this.m_WaitingHintDialog != null) {
            return this.m_WaitingHintDialog.isShowing();
        }
        return false;
    }

    private void CreateWaitingHintDialog(String ratio, String strMSG) {
        if (this.m_WaitingHintDialog == null) {
            this.m_WaitingHintDialog = new Dialog(this.m_context, this.R_STYLE_DIALOG_MSG);
            this.m_WaitingHintDialog.requestWindowFeature(1);
            this.m_WaitingHintDialog.setCanceledOnTouchOutside(false);
            this.m_WaitingHintDialog.setCancelable(false);
            this.m_WaitingDialogView = this.m_WaitingHintDialog.getLayoutInflater().inflate(this.R_LAYOUT_DIALOG_WAITING, null);
            this.m_WaitingProgressBar = (ProgressBar) this.m_WaitingDialogView.findViewById(this.R_ID_WAITING_PROGRESS);
            this.m_WaitingProgressBar.setVisibility(0);
            this.m_WaitingProgressBar.setIndeterminate(true);
            this.m_WaitingMSGTextView = (TextView) this.m_WaitingDialogView.findViewById(this.R_ID_WAITING_TEXTVIEW);
            this.m_RatioTextView = (TextView) this.m_WaitingDialogView.findViewById(this.R_ID_RATIO_TEXTVIEW);
            this.m_WaitingHintDialog.setContentView(this.m_WaitingDialogView);
        }
        this.m_WaitingHintDialog.getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        this.m_WaitingMSGTextView.setText(String.valueOf(strMSG));
        if (ratio != null) {
            this.m_RatioTextView.setVisibility(0);
            this.m_RatioTextView.setText(ratio);
        } else {
            this.m_RatioTextView.setVisibility(8);
        }
        this.m_WaitingHintDialog.show();
    }
}
