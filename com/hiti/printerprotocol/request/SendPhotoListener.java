package com.hiti.printerprotocol.request;

import com.hiti.utility.Verify.PrintMode;
import java.net.Socket;

public interface SendPhotoListener {
    boolean CheckJobIdIfEmpty();

    int GetCopies();

    void GetHitiPPR_SendPhotoPrinbiz(HitiPPR_SendPhotoPrinbiz hitiPPR_SendPhotoPrinbiz);

    void SendingPhoto(PrintMode printMode);

    void SetAskState(boolean z, boolean z2, boolean z3);

    void SkipToNextPhoto(Socket socket);

    void onCreateBitmapError(String str);
}
