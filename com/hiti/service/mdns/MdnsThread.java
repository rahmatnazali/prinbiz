package com.hiti.service.mdns;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import com.hiti.utility.LogManager;
import com.hiti.utility.wifi.PrinterListListener;
import com.hiti.utility.wifi.ShowPrinterList;
import com.hiti.utility.wifi.ShowPrinterList.MDNS;
import com.hiti.utility.wifi.ShowPrinterList.Scan;

public class MdnsThread extends Thread {
    LogManager LOG;
    private MdnsHandler mMdnsHandler;
    private ShowPrinterList mShowPrinterList;

    /* renamed from: com.hiti.service.mdns.MdnsThread.1 */
    class C07991 extends PrinterListListener {
        C07991() {
        }

        public void ScanState(Scan state, String strPID, String strSSID, String IP, int iPort) {
            MdnsThread.this.LOG.m385i("MdnsThread", "ScanState:" + state);
            MdnsThread.this.LOG.m385i("MdnsThread", "strPID:" + strPID);
            MdnsThread.this.LOG.m385i("MdnsThread", "strSSID:" + strSSID);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("ScanState", state.toString());
            bundle.putString("PID", String.valueOf(strPID));
            bundle.putString("SSID", String.valueOf(strSSID));
            bundle.putString("IP", String.valueOf(IP));
            bundle.putInt("Port", iPort);
            message.setData(bundle);
            message.what = 6;
            MdnsThread.this.mMdnsHandler.sendMessage(message);
        }

        public void PrinterListFinish(String strPrinterSSID, String IP, int iPort, String strConn) {
        }

        public void IsMdnsState(boolean bMDNS) {
        }

        public void BackStart() {
        }

        public void BackFinish() {
        }
    }

    public MdnsThread(Context context, MDNS type) {
        this.mShowPrinterList = null;
        this.mMdnsHandler = null;
        this.LOG = null;
        this.mShowPrinterList = new ShowPrinterList(context, type);
        this.LOG = new LogManager(0);
    }

    public void SetHandler(MdnsHandler handler) {
        this.mMdnsHandler = handler;
    }

    public void Cancel() {
        if (this.mShowPrinterList != null) {
            this.mShowPrinterList.Cancel();
        }
    }

    public void Stop() {
        if (this.mShowPrinterList != null) {
            this.mShowPrinterList.Stop();
        }
    }

    public void run() {
        ScanMdns();
    }

    void ScanMdns() {
        this.mShowPrinterList.SetPrinterListListener(new C07991());
        this.mShowPrinterList.Show();
    }
}
