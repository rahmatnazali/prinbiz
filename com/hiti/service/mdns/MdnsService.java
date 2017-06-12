package com.hiti.service.mdns;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import com.hiti.service.mdns.MdnsHandler.IMdnsListener;
import com.hiti.utility.wifi.ShowPrinterList.MDNS;

public class MdnsService extends Service {
    public final IBinder mBinder;
    private MdnsHandler mHandler;
    private MdnsThread mMdnsThread;

    public class LocalBinder extends Binder {
        MdnsService getService() {
            return MdnsService.this;
        }
    }

    public MdnsService() {
        this.mMdnsThread = null;
        this.mHandler = null;
        this.mBinder = new LocalBinder();
    }

    public void SetListener(IMdnsListener listener) {
        this.mHandler.SetMdnsListener(listener);
    }

    public void onCreate() {
        super.onCreate();
        this.mHandler = new MdnsHandler();
        this.mMdnsThread = new MdnsThread(getApplicationContext(), MDNS.Check);
        this.mMdnsThread.SetHandler(this.mHandler);
        this.mMdnsThread.start();
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public void SendMessage(int iWhat, int iArr) {
        Message message = new Message();
        message.what = iWhat;
        if (iArr != -1) {
            message.arg1 = iArr;
        }
        if (this.mHandler != null) {
            this.mHandler.sendMessage(message);
        }
    }

    public void Cancel() {
        if (this.mMdnsThread != null) {
            this.mMdnsThread.Cancel();
        }
    }

    public void Stop() {
        if (this.mMdnsThread != null) {
            this.mMdnsThread.Stop();
        }
    }
}
