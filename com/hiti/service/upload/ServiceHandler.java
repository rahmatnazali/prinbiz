package com.hiti.service.upload;

import android.app.Service;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.apache.commons.net.pop3.POP3;
import org.ksoap2.SoapEnvelope;

public class ServiceHandler extends Handler {
    private static final String MSG = "MSG";
    private IPrinterListner mFWListener;
    private Service m_Service;
    private boolean m_boStop;

    public class ServiceState {
        public static final int FW_Version = 110;
        public static final int STOP = 100;
    }

    public ServiceHandler(Service service) {
        this.m_Service = null;
        this.m_boStop = false;
        this.mFWListener = null;
        this.m_Service = service;
    }

    public ServiceHandler() {
        this.m_Service = null;
        this.m_boStop = false;
        this.mFWListener = null;
    }

    public void SetPrinterListener(IPrinterListner listener) {
        this.mFWListener = listener;
    }

    void SetStop(boolean boStop) {
        this.m_boStop = boStop;
    }

    public void handleMessage(Message msg) {
        Log.i("ServiceState.STOP", "m_boStop=" + String.valueOf(this.m_boStop));
        if (!this.m_boStop) {
            switch (msg.what) {
                case SoapEnvelope.VER10 /*100*/:
                    Log.e("ServiceState.STOP", String.valueOf("ServiceState.STOP"));
                    if (this.mFWListener != null) {
                        this.mFWListener.onServiceStop();
                    }
                    SetStop(true);
                    if (this.m_Service != null) {
                        this.m_Service.stopSelf();
                    }
                    Log.e("STOP Service", "STOP");
                    break;
                case POP3.DEFAULT_PORT /*110*/:
                    Bundle bundle = msg.getData();
                    this.mFWListener.onNewstVersion(bundle.getString("ElemntID"), bundle.getString("FWversion"), Integer.parseInt(bundle.getString("XMLversion")));
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public void SendMessage(int iMsg, String strMsg) {
        Message msg = Message.obtain();
        msg.what = iMsg;
        if (strMsg != null) {
            Bundle buddle = new Bundle();
            buddle.putString(MSG, strMsg);
            msg.setData(buddle);
        }
        sendMessage(msg);
    }
}
