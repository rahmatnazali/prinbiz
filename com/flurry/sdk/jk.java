package com.flurry.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.common.ConnectionResult;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public final class jk extends BroadcastReceiver {
    private static jk f256c;
    boolean f257a;
    public boolean f258b;
    private boolean f259d;

    /* renamed from: com.flurry.sdk.jk.a */
    public enum C0150a {
        NONE_OR_UNKNOWN(0),
        NETWORK_AVAILABLE(1),
        WIFI(2),
        CELL(3);
        
        public int f255e;

        private C0150a(int i) {
            this.f255e = i;
        }
    }

    public static synchronized jk m98a() {
        jk jkVar;
        synchronized (jk.class) {
            if (f256c == null) {
                f256c = new jk();
            }
            jkVar = f256c;
        }
        return jkVar;
    }

    public static synchronized void m100b() {
        synchronized (jk.class) {
            if (f256c != null) {
                f256c.m102e();
            }
            f256c = null;
        }
    }

    private jk() {
        boolean z = false;
        this.f259d = false;
        Context context = jr.m120a().f284a;
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0) {
            z = true;
        }
        this.f259d = z;
        this.f258b = m99a(context);
        if (this.f259d) {
            m101d();
        }
    }

    private synchronized void m101d() {
        if (!this.f257a) {
            Context context = jr.m120a().f284a;
            this.f258b = m99a(context);
            context.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this.f257a = true;
        }
    }

    private synchronized void m102e() {
        if (this.f257a) {
            jr.m120a().f284a.unregisterReceiver(this);
            this.f257a = false;
        }
    }

    public final void onReceive(Context context, Intent intent) {
        boolean a = m99a(context);
        if (this.f258b != a) {
            this.f258b = a;
            jz jjVar = new jj();
            jjVar.f612a = a;
            jjVar.f613b = m104c();
            kb.m157a().m161a(jjVar);
        }
    }

    private boolean m99a(Context context) {
        if (!this.f259d || context == null) {
            return true;
        }
        NetworkInfo activeNetworkInfo = m103f().getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public final C0150a m104c() {
        if (!this.f259d) {
            return C0150a.NONE_OR_UNKNOWN;
        }
        NetworkInfo activeNetworkInfo = m103f().getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return C0150a.NONE_OR_UNKNOWN;
        }
        switch (activeNetworkInfo.getType()) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                return C0150a.CELL;
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return C0150a.WIFI;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                return C0150a.NONE_OR_UNKNOWN;
            default:
                if (activeNetworkInfo.isConnected()) {
                    return C0150a.NETWORK_AVAILABLE;
                }
                return C0150a.NONE_OR_UNKNOWN;
        }
    }

    private static ConnectivityManager m103f() {
        return (ConnectivityManager) jr.m120a().f284a.getSystemService("connectivity");
    }
}
