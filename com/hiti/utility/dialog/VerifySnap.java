package com.hiti.utility.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.hiti.gcm.GCMInfo;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.service.upload.UploadUtility;
import com.hiti.trace.GlobalVariable_OtherSetting;
import com.hiti.trace.GlobalVariable_UserInfo;
import com.hiti.utility.Verify;
import com.hiti.utility.Verify.HintType;
import com.hiti.utility.Verify.VERIFFY_PROCESS;
import com.hiti.utility.Verify.VerifyToWhere;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;

public class VerifySnap implements Verify {
    private Context m_Context;
    private GlobalVariable_OtherSetting m_GVOtherInfo;
    private GlobalVariable_UserInfo m_GVUserInfo;
    private Dialog m_VerifyHintDialog;
    private Dialog m_VerifyLoginAlertDialog;
    private TextView m_VerifyMSGTextView;
    private TextView m_VerifyTitleTextView;
    private boolean m_bCheckClicked;

    /* renamed from: com.hiti.utility.dialog.VerifySnap.1 */
    class C04691 implements OnCheckedChangeListener {
        final /* synthetic */ HintType val$type;

        C04691(HintType hintType) {
            this.val$type = hintType;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            VerifySnap.this.CheckNotAskAgain(this.val$type, isChecked);
        }
    }

    /* renamed from: com.hiti.utility.dialog.VerifySnap.2 */
    class C04702 implements OnClickListener {
        final /* synthetic */ HintListener val$listener;
        final /* synthetic */ HintType val$type;

        C04702(HintType hintType, HintListener hintListener) {
            this.val$type = hintType;
            this.val$listener = hintListener;
        }

        public void onClick(View v) {
            if (VerifySnap.this.m_bCheckClicked) {
                VerifySnap.this.m_GVOtherInfo.SaveGlobalVariable();
            }
            VerifySnap.this.m_VerifyHintDialog.dismiss();
            if (this.val$type != HintType.CloudBack) {
                this.val$listener.ContinueProcess();
            }
        }
    }

    /* renamed from: com.hiti.utility.dialog.VerifySnap.3 */
    class C04713 implements OnClickListener {
        C04713() {
        }

        public void onClick(View v) {
            if (VerifySnap.this.m_bCheckClicked) {
                VerifySnap.this.m_GVOtherInfo.SaveGlobalVariable();
            }
            VerifySnap.this.m_VerifyHintDialog.dismiss();
        }
    }

    /* renamed from: com.hiti.utility.dialog.VerifySnap.4 */
    class C04724 implements OnClickListener {
        final /* synthetic */ VerifyToWhere val$ToWhere;
        final /* synthetic */ VerifyListener val$listener;

        C04724(VerifyToWhere verifyToWhere, VerifyListener verifyListener) {
            this.val$ToWhere = verifyToWhere;
            this.val$listener = verifyListener;
        }

        public void onClick(View v) {
            VerifySnap.this.VerifyProcess(false, this.val$ToWhere, this.val$listener);
            VerifySnap.this.m_VerifyLoginAlertDialog.dismiss();
        }
    }

    /* renamed from: com.hiti.utility.dialog.VerifySnap.5 */
    class C04735 implements OnClickListener {
        final /* synthetic */ VerifyToWhere val$ToWhere;
        final /* synthetic */ VerifyListener val$listener;

        C04735(VerifyToWhere verifyToWhere, VerifyListener verifyListener) {
            this.val$ToWhere = verifyToWhere;
            this.val$listener = verifyListener;
        }

        public void onClick(View v) {
            VerifySnap.this.VerifyProcess(true, this.val$ToWhere, this.val$listener);
            VerifySnap.this.m_VerifyLoginAlertDialog.dismiss();
        }
    }

    /* renamed from: com.hiti.utility.dialog.VerifySnap.6 */
    class C04746 implements OnClickListener {
        C04746() {
        }

        public void onClick(View v) {
            VerifySnap.this.m_VerifyLoginAlertDialog.dismiss();
        }
    }

    /* renamed from: com.hiti.utility.dialog.VerifySnap.7 */
    static /* synthetic */ class C04757 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$utility$Verify$HintType;

        static {
            $SwitchMap$com$hiti$utility$Verify$HintType = new int[HintType.values().length];
            try {
                $SwitchMap$com$hiti$utility$Verify$HintType[HintType.GeneralPrint.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$utility$Verify$HintType[HintType.CloudBack.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$utility$Verify$HintType[HintType.SnapPrint.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    class HintInfo {
        boolean bNoAskAgain;
        String strMessage;
        String strTitle;

        public HintInfo(String title, String message, boolean bNotAskAgain) {
            this.strTitle = XmlPullParser.NO_NAMESPACE;
            this.strMessage = XmlPullParser.NO_NAMESPACE;
            this.bNoAskAgain = false;
            this.strTitle = title;
            this.strMessage = message;
            this.bNoAskAgain = bNotAskAgain;
        }

        public String GetTitle() {
            return this.strTitle;
        }

        public String GetMessage() {
            return this.strMessage;
        }

        public boolean IsNotAskAgain() {
            return this.bNoAskAgain;
        }
    }

    public interface HintListener {
        void ContinueProcess();

        HashMap<String, Integer> GetDialogMessage(HintType hintType);

        HashMap<String, Integer> GetDialogViewId();
    }

    public interface VerifyListener {
        HashMap<String, Integer> GetDialogViewId();

        void GoToVerifyPage(Bundle bundle);

        void HaveVerified(VerifyToWhere verifyToWhere);
    }

    public VerifySnap(Context context) {
        this.m_VerifyLoginAlertDialog = null;
        this.m_VerifyHintDialog = null;
        this.m_VerifyTitleTextView = null;
        this.m_VerifyMSGTextView = null;
        this.m_GVUserInfo = null;
        this.m_GVOtherInfo = null;
        this.m_Context = null;
        this.m_bCheckClicked = false;
        this.m_Context = context;
    }

    public void ShowVerifyLoginDialog(VerifyToWhere ToWhere, VerifyListener listener) {
        if (this.m_GVUserInfo == null) {
            this.m_GVUserInfo = new GlobalVariable_UserInfo(this.m_Context);
        }
        this.m_GVUserInfo.RestoreGlobalVariable();
        if (!UploadUtility.HaveVerify(this.m_GVUserInfo)) {
            CreateVerifyLoginAlertDialog(ToWhere, listener);
        } else if (listener != null) {
            listener.HaveVerified(ToWhere);
        }
    }

    public void ShowHintDialog(HintType type, HintListener listener) {
        if (listener != null) {
            if (this.m_GVOtherInfo == null) {
                this.m_GVOtherInfo = new GlobalVariable_OtherSetting(this.m_Context);
            }
            HintInfo hint = SetDialogMessage(type, listener);
            if (hint.IsNotAskAgain()) {
                listener.ContinueProcess();
            } else {
                CreateHintDialog(hint.GetTitle(), hint.GetMessage(), type, listener);
            }
        }
    }

    private HintInfo SetDialogMessage(HintType type, HintListener listener) {
        this.m_GVOtherInfo.RestoreGlobalVariable();
        HashMap<String, Integer> map = listener.GetDialogMessage(type);
        String strTitle = this.m_Context.getString(((Integer) map.get("title")).intValue());
        String strMessage = this.m_Context.getString(((Integer) map.get(GCMInfo.EXTRA_MESSAGE)).intValue());
        boolean bNotAskAgain = false;
        switch (C04757.$SwitchMap$com$hiti$utility$Verify$HintType[type.ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                bNotAskAgain = this.m_GVOtherInfo.GetEditPrintNotAskAgain();
                break;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                bNotAskAgain = this.m_GVOtherInfo.GetSnapPrintNotAskAgain();
                break;
        }
        return new HintInfo(strTitle, strMessage, bNotAskAgain);
    }

    public boolean IsSnapHintShowing() {
        if (this.m_VerifyHintDialog != null) {
            return this.m_VerifyHintDialog.isShowing();
        }
        return false;
    }

    public void CloseSnapHintDialog() {
        if (this.m_VerifyHintDialog != null) {
            this.m_VerifyHintDialog.cancel();
        }
    }

    private void CreateHintDialog(String strTitle, String strMSG, HintType type, HintListener listener) {
        if (this.m_VerifyHintDialog == null && listener != null) {
            Map<String, Integer> map = listener.GetDialogViewId();
            this.m_VerifyHintDialog = new Dialog(this.m_Context, ((Integer) map.get("style")).intValue());
            this.m_VerifyHintDialog.requestWindowFeature(1);
            View view = View.inflate(this.m_Context, ((Integer) map.get("dialog")).intValue(), null);
            CheckBox mNotAgainCheckBox = (CheckBox) view.findViewById(((Integer) map.get("checkbox")).intValue());
            ImageButton mVerifyHintOKButton = (ImageButton) view.findViewById(((Integer) map.get("OKbtn")).intValue());
            ImageButton mVerifyHintNoButton = (ImageButton) view.findViewById(((Integer) map.get("NObtn")).intValue());
            this.m_VerifyTitleTextView = (TextView) view.findViewById(((Integer) map.get("title")).intValue());
            this.m_VerifyMSGTextView = (TextView) view.findViewById(((Integer) map.get(GCMInfo.EXTRA_MESSAGE)).intValue());
            this.m_VerifyHintDialog.setContentView(view);
            if (type == HintType.CloudBack) {
                mNotAgainCheckBox.setVisibility(8);
                mVerifyHintNoButton.setVisibility(8);
            }
            mNotAgainCheckBox.setOnCheckedChangeListener(new C04691(type));
            mVerifyHintOKButton.setOnClickListener(new C04702(type, listener));
            mVerifyHintNoButton.setOnClickListener(new C04713());
        }
        this.m_VerifyTitleTextView.setText(String.valueOf(strTitle));
        this.m_VerifyMSGTextView.setText(String.valueOf(strMSG));
        this.m_VerifyHintDialog.show();
    }

    private void CheckNotAskAgain(HintType type, boolean bIsChecked) {
        this.m_bCheckClicked = true;
        this.m_GVOtherInfo.RestoreGlobalVariable();
        switch (C04757.$SwitchMap$com$hiti$utility$Verify$HintType[type.ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                this.m_GVOtherInfo.SetEditPrintNotAskAgain(bIsChecked);
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                this.m_GVOtherInfo.SetSnapPrintNotAskAgain(bIsChecked);
            default:
        }
    }

    private void CreateVerifyLoginAlertDialog(VerifyToWhere ToWhere, VerifyListener listener) {
        if (this.m_VerifyLoginAlertDialog == null && listener != null) {
            Map<String, Integer> map = listener.GetDialogViewId();
            this.m_VerifyLoginAlertDialog = new Dialog(this.m_Context, ((Integer) map.get("style")).intValue());
            this.m_VerifyLoginAlertDialog.requestWindowFeature(1);
            View view = View.inflate(this.m_Context, ((Integer) map.get("dialog")).intValue(), null);
            Button mVerifyLoginOKButton = (Button) view.findViewById(((Integer) map.get("OKbtn")).intValue());
            Button mVerifyLoginRegisterButton = (Button) view.findViewById(((Integer) map.get("regist")).intValue());
            ImageButton mVerifyLoginNoButton = (ImageButton) view.findViewById(((Integer) map.get("NObtn")).intValue());
            this.m_VerifyLoginAlertDialog.setContentView(view);
            mVerifyLoginOKButton.setOnClickListener(new C04724(ToWhere, listener));
            mVerifyLoginRegisterButton.setOnClickListener(new C04735(ToWhere, listener));
            mVerifyLoginNoButton.setOnClickListener(new C04746());
        }
        this.m_VerifyLoginAlertDialog.show();
    }

    private void VerifyProcess(boolean boRegister, VerifyToWhere bButtn, VerifyListener listener) {
        Bundle bundle = new Bundle();
        if (boRegister) {
            bundle.putString(JumpBundleMessage.BUNDLE_MSG_VERIFY_PROCESS, VERIFFY_PROCESS.REGIST.toString());
        } else {
            bundle.putString(JumpBundleMessage.BUNDLE_MSG_VERIFY_PROCESS, VERIFFY_PROCESS.LOGIN.toString());
        }
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_VERIFY_TRIGGER_BUTTON, bButtn.toString());
        if (listener != null) {
            listener.GoToVerifyPage(bundle);
        }
    }
}
