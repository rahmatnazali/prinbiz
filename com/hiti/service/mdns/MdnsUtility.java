package com.hiti.service.mdns;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.hiti.service.mdns.MdnsHandler.IMdnsListener;
import com.hiti.service.mdns.MdnsService.LocalBinder;
import com.hiti.utility.LogManager;
import org.xmlpull.v1.XmlPullParser;

public class MdnsUtility {
    LogManager LOG;
    boolean mBound;
    ServiceConnection mConnection;
    Context mContext;
    IMdnsListener mMainListener;
    MdnsThread mMdnsThread;
    MdnsService mService;

    /* renamed from: com.hiti.service.mdns.MdnsUtility.1 */
    class C04181 implements ServiceConnection {
        C04181() {
        }

        public void onServiceDisconnected(ComponentName name) {
            MdnsUtility.this.mBound = false;
            MdnsUtility.this.LOG.m384e("onServiceDisconnected:", String.valueOf(MdnsUtility.this.mBound));
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalBinder mBinder = (LocalBinder) service;
            MdnsUtility.this.mService = mBinder.getService();
            MdnsUtility.this.mBound = true;
            MdnsUtility.this.mService.SetListener(MdnsUtility.this.mMainListener);
            MdnsUtility.this.LOG.m386v("onServiceConnected:", "mMainListener: " + String.valueOf(MdnsUtility.this.mMainListener));
        }
    }

    public MdnsUtility(Context context) {
        this.mContext = null;
        this.mMdnsThread = null;
        this.mService = null;
        this.mBound = false;
        this.mMainListener = null;
        this.LOG = null;
        this.mConnection = new C04181();
        this.LOG = new LogManager(0);
        this.mContext = context;
    }

    public void BindService() {
        this.mContext.bindService(new Intent(this.mContext, MdnsService.class), this.mConnection, 1);
    }

    public void UnbindService() {
        if (this.mBound) {
            this.mContext.unbindService(this.mConnection);
            this.mBound = false;
            this.mService.Cancel();
        }
    }

    public void Stop() {
        if (this.mService != null) {
            this.mService.Stop();
        }
    }

    public void SetAnimationListener(IMdnsListener listener) {
        this.LOG.m385i("SetAnimationListener:", XmlPullParser.NO_NAMESPACE + String.valueOf(listener));
        this.mMainListener = listener;
        listener.StartAnimation();
    }
}
