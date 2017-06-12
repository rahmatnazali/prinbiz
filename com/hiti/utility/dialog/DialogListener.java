package com.hiti.utility.dialog;

import com.hiti.utility.Verify.ThreadMode;

public abstract class DialogListener {
    public abstract void CancelConnetion(ThreadMode threadMode);

    public abstract void LeaveCancel();

    public abstract void LeaveClose();

    public abstract void LeaveConfirm();

    public abstract void SetLastConnSSID(String str);

    public abstract void SetNowConnSSID(String str);
}
