package com.hiti.utility.wifi;

import com.hiti.utility.wifi.ShowPrinterList.Scan;

public abstract class PrinterListListener {
    public abstract void BackFinish();

    public abstract void BackStart();

    public abstract void IsMdnsState(boolean z);

    public abstract void PrinterListFinish(String str, String str2, int i, String str3);

    public abstract void ScanState(Scan scan, String str, String str2, String str3, int i);
}
