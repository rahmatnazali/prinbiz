package com.hiti.nfc.utility;

import android.content.Context;
import com.hiti.nfc.utility.NFCInfo.NFCMode;

public interface NFCInerface {
    void PasswordRuleError();

    void ReadNFCFail(Context context, NFCInfo nFCInfo);

    void ReadNFCSuccess(Context context, NFCInfo nFCInfo);

    void SSIDEmpty();

    void SetSSID(NFCMode nFCMode);

    void WriteDialogClose();
}
