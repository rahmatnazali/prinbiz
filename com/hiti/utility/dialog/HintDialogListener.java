package com.hiti.utility.dialog;

import com.hiti.gcm.GCMInfo;
import com.hiti.prinbiz.C0349R;
import com.hiti.utility.Verify.HintType;
import com.hiti.utility.dialog.VerifySnap.HintListener;
import java.util.HashMap;
import org.apache.commons.net.ftp.FTPClient;

public class HintDialogListener implements HintListener {

    /* renamed from: com.hiti.utility.dialog.HintDialogListener.1 */
    static /* synthetic */ class C04591 {
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

    public void ContinueProcess() {
    }

    public HashMap<String, Integer> GetDialogViewId() {
        HashMap<String, Integer> map = new HashMap();
        map.put("style", Integer.valueOf(C0349R.style.Dialog_MSG));
        map.put("dialog", Integer.valueOf(C0349R.layout.dialog_no_verify));
        map.put("checkbox", Integer.valueOf(C0349R.id.m_NotShowAgainCheckBox));
        map.put("OKbtn", Integer.valueOf(C0349R.id.m_OKImageButton));
        map.put("NObtn", Integer.valueOf(C0349R.id.m_NoImageButton));
        map.put("title", Integer.valueOf(C0349R.id.m_VerifiedTitle));
        map.put(GCMInfo.EXTRA_MESSAGE, Integer.valueOf(C0349R.id.m_NoVerifiedMSGTextView));
        return map;
    }

    public HashMap<String, Integer> GetDialogMessage(HintType type) {
        HashMap<String, Integer> map = new HashMap();
        switch (C04591.$SwitchMap$com$hiti$utility$Verify$HintType[type.ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                map.put("title", Integer.valueOf(C0349R.string.HINT_TITLE));
                break;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                map.put("title", Integer.valueOf(C0349R.string.HINT_TITLE));
                break;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                map.put("title", Integer.valueOf(C0349R.string.SNAP_PRINT_TITLE));
                map.put(GCMInfo.EXTRA_MESSAGE, Integer.valueOf(C0349R.string.SNAP_PRINT_MSG));
                break;
        }
        return map;
    }
}
