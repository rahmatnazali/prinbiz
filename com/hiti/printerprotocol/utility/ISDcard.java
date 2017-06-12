package com.hiti.printerprotocol.utility;

import java.net.Socket;
import java.util.ArrayList;

public interface ISDcard {
    void CancelPrinting();

    void EndCheckPrintInfo(String str, String str2);

    void ErrorOccur(String str);

    void ErrorOccurDuetoPrinter(String str);

    void ErrorTimeOut(String str);

    void OnPrintSendedNum(String str);

    void OnPrintingCountChange(String str, int i);

    void OnPrintingStatusChange(String str);

    void PaperJamAgain(String str);

    void PaperJamDone();

    void PrintDone(int i, int i2);

    void RecoveryDone();

    void SetPrintout(String str);

    void SizeNoMatch(String str);

    void StartBurnFW(Socket socket);

    void StartCheckPrintInfo();

    void StartPrintFromSDcard(int i, int i2);

    ArrayList<Integer> StorageError(String str);
}
