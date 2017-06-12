package com.hiti.service.mdns;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.hiti.utility.LogManager;
import com.hiti.utility.wifi.ShowPrinterList.Scan;

public class MdnsHandler extends Handler {
    public static final int MDNS_SCAN_STATE = 6;
    LogManager LOG;
    public IMdnsListener mListener;

    public interface IMdnsListener {
        void StartAnimation();

        void getScanState(Scan scan, String str, String str2, String str3, int i);
    }

    public MdnsHandler() {
        this.LOG = null;
    }

    public void SetMdnsListener(IMdnsListener listener) {
        this.mListener = listener;
        this.LOG = new LogManager(0);
    }

    public void handleMessage(Message msg) {
        this.LOG.m385i("MdnsHandler ", String.valueOf(msg.what));
        if (this.mListener != null) {
            switch (msg.what) {
                case MDNS_SCAN_STATE /*6*/:
                    Bundle bundle = msg.getData();
                    if (bundle != null) {
                        String state = bundle.getString("ScanState");
                        this.LOG.m385i("MDNS_SCAN_STATE ", "state: " + state);
                        if (state != null) {
                            this.mListener.getScanState(Scan.valueOf(bundle.getString("ScanState")), bundle.getString("PID"), bundle.getString("SSID"), bundle.getString("IP"), bundle.getInt("Port"));
                        }
                    }
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
