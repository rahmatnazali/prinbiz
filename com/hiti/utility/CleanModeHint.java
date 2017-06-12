package com.hiti.utility;

import android.content.Context;
import com.hiti.printerprotocol.RequestState;
import com.hiti.trace.GlobalVariable_PrinterInfo;
import org.apache.commons.net.telnet.TelnetCommand;
import org.ksoap2.SoapEnvelope;

public class CleanModeHint {
    public static boolean CleanNuberCheck(Context context, int iNumber) {
        GlobalVariable_PrinterInfo mPrinterInfo = new GlobalVariable_PrinterInfo(context);
        mPrinterInfo.RestoreGlobalVariable();
        int iHintTimes = mPrinterInfo.GetCleanNumber();
        if (iNumber >= SoapEnvelope.VER12) {
            if (iNumber < TelnetCommand.SE && iHintTimes < 3) {
                mPrinterInfo.SetCleanNumber(iHintTimes + 1);
                mPrinterInfo.SaveGlobalVariable();
                return true;
            } else if (iNumber >= TelnetCommand.SE && iNumber < RequestState.REQUEST_CREATE_BITMAP_SCALE_HINT && iHintTimes <= 3) {
                mPrinterInfo.SetCleanNumber(4);
                mPrinterInfo.SaveGlobalVariable();
                return true;
            } else if (iNumber >= RequestState.REQUEST_CREATE_BITMAP_SCALE_HINT && iHintTimes <= 4) {
                mPrinterInfo.SetCleanNumber(5);
                mPrinterInfo.SaveGlobalVariable();
                return true;
            }
        } else if (iHintTimes > 0) {
            mPrinterInfo.SetCleanNumber(0);
            mPrinterInfo.SaveGlobalVariable();
        }
        return false;
    }

    public void ResetCleanNumberRecord(Context context) {
        GlobalVariable_PrinterInfo mPrinterInfo = new GlobalVariable_PrinterInfo(context);
        mPrinterInfo.RestoreGlobalVariable();
        mPrinterInfo.SetCleanNumber(0);
        mPrinterInfo.SaveGlobalVariable();
    }
}
