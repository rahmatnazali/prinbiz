package com.hiti.printerprotocol.utility;

import android.os.Message;

public abstract class PrintingPhotoListener {
    public abstract void EndCheckPrintInfo(String str, String str2);

    public abstract void EndCheckPrinting();

    public abstract void EndSendingPhoto(String str, String str2);

    public abstract void ErrorCheckPrintInfo(Message message);

    public abstract void ErrorCheckPrinting(Message message);

    public abstract void ErrorCheckPrintingDuetoPrinter(Message message);

    public abstract void ErrorPreparePhoto(int i);

    public abstract void ErrorRecoveryPrinter(Message message);

    public abstract void ErrorSendingPhoto(Message message);

    public abstract void ErrorSendingPhotoDuetoPrinter(Message message);

    public abstract void ErrorTimeOut(Message message);

    public abstract void OnPrintingCountChange(int i, int i2);

    public abstract void OnPrintingStatusChange(Message message);

    public abstract void StartBurnFW(String str, String str2);

    public abstract void StartCheckPrintInfo();

    public abstract void StartCheckPrinting();

    public abstract void StartSendingPhoto(int i, int i2);
}
