package com.hiti.utility;

public abstract class PrinterListListener {
    public abstract void BackFinish();

    public abstract void BackStart();

    public abstract void IsBackStateOnMDNS(boolean z);

    public abstract void PrinterListFinish(String str, String str2, int i, String str3);
}
