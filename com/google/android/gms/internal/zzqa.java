package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.zzc;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.apache.commons.net.ftp.FTPClient;

public class zzqa implements zzqh {
    private final Context mContext;
    final com.google.android.gms.common.api.Api.zza<? extends zzvu, zzvv> si;
    final zzg tN;
    final Map<Api<?>, Integer> tO;
    final zzpy th;
    private final Lock tr;
    private final zzc tz;
    int uA;
    final com.google.android.gms.internal.zzqh.zza uB;
    final Map<Api.zzc<?>, zze> ui;
    private final Condition uv;
    private final zzb uw;
    final Map<Api.zzc<?>, ConnectionResult> ux;
    private volatile zzpz uy;
    private ConnectionResult uz;

    static abstract class zza {
        private final zzpz uC;

        protected zza(zzpz com_google_android_gms_internal_zzpz) {
            this.uC = com_google_android_gms_internal_zzpz;
        }

        protected abstract void zzapl();

        public final void zzd(zzqa com_google_android_gms_internal_zzqa) {
            com_google_android_gms_internal_zzqa.tr.lock();
            try {
                if (com_google_android_gms_internal_zzqa.uy == this.uC) {
                    zzapl();
                    com_google_android_gms_internal_zzqa.tr.unlock();
                }
            } finally {
                com_google_android_gms_internal_zzqa.tr.unlock();
            }
        }
    }

    final class zzb extends Handler {
        final /* synthetic */ zzqa uD;

        zzb(zzqa com_google_android_gms_internal_zzqa, Looper looper) {
            this.uD = com_google_android_gms_internal_zzqa;
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    ((zza) message.obj).zzd(this.uD);
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    throw ((RuntimeException) message.obj);
                default:
                    Log.w("GACStateManager", "Unknown message id: " + message.what);
            }
        }
    }

    public zzqa(Context context, zzpy com_google_android_gms_internal_zzpy, Lock lock, Looper looper, zzc com_google_android_gms_common_zzc, Map<Api.zzc<?>, zze> map, zzg com_google_android_gms_common_internal_zzg, Map<Api<?>, Integer> map2, com.google.android.gms.common.api.Api.zza<? extends zzvu, zzvv> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzvu__com_google_android_gms_internal_zzvv, ArrayList<zzpp> arrayList, com.google.android.gms.internal.zzqh.zza com_google_android_gms_internal_zzqh_zza) {
        this.ux = new HashMap();
        this.uz = null;
        this.mContext = context;
        this.tr = lock;
        this.tz = com_google_android_gms_common_zzc;
        this.ui = map;
        this.tN = com_google_android_gms_common_internal_zzg;
        this.tO = map2;
        this.si = com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzvu__com_google_android_gms_internal_zzvv;
        this.th = com_google_android_gms_internal_zzpy;
        this.uB = com_google_android_gms_internal_zzqh_zza;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((zzpp) it.next()).zza(this);
        }
        this.uw = new zzb(this, looper);
        this.uv = lock.newCondition();
        this.uy = new zzpx(this);
    }

    public ConnectionResult blockingConnect() {
        connect();
        while (isConnecting()) {
            try {
                this.uv.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        return isConnected() ? ConnectionResult.rb : this.uz != null ? this.uz : new ConnectionResult(13, null);
    }

    public ConnectionResult blockingConnect(long j, TimeUnit timeUnit) {
        connect();
        long toNanos = timeUnit.toNanos(j);
        while (isConnecting()) {
            if (toNanos <= 0) {
                try {
                    disconnect();
                    return new ConnectionResult(14, null);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return new ConnectionResult(15, null);
                }
            }
            toNanos = this.uv.awaitNanos(toNanos);
        }
        return isConnected() ? ConnectionResult.rb : this.uz != null ? this.uz : new ConnectionResult(13, null);
    }

    public void connect() {
        this.uy.connect();
    }

    public void disconnect() {
        if (this.uy.disconnect()) {
            this.ux.clear();
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String concat = String.valueOf(str).concat("  ");
        printWriter.append(str).append("mState=").println(this.uy);
        for (Api api : this.tO.keySet()) {
            printWriter.append(str).append(api.getName()).println(":");
            ((zze) this.ui.get(api.zzans())).dump(concat, fileDescriptor, printWriter, strArr);
        }
    }

    @Nullable
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        Api.zzc zzans = api.zzans();
        if (this.ui.containsKey(zzans)) {
            if (((zze) this.ui.get(zzans)).isConnected()) {
                return ConnectionResult.rb;
            }
            if (this.ux.containsKey(zzans)) {
                return (ConnectionResult) this.ux.get(zzans);
            }
        }
        return null;
    }

    public boolean isConnected() {
        return this.uy instanceof zzpv;
    }

    public boolean isConnecting() {
        return this.uy instanceof zzpw;
    }

    public void onConnected(@Nullable Bundle bundle) {
        this.tr.lock();
        try {
            this.uy.onConnected(bundle);
        } finally {
            this.tr.unlock();
        }
    }

    public void onConnectionSuspended(int i) {
        this.tr.lock();
        try {
            this.uy.onConnectionSuspended(i);
        } finally {
            this.tr.unlock();
        }
    }

    public void zza(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, int i) {
        this.tr.lock();
        try {
            this.uy.zza(connectionResult, api, i);
        } finally {
            this.tr.unlock();
        }
    }

    void zza(zza com_google_android_gms_internal_zzqa_zza) {
        this.uw.sendMessage(this.uw.obtainMessage(1, com_google_android_gms_internal_zzqa_zza));
    }

    void zza(RuntimeException runtimeException) {
        this.uw.sendMessage(this.uw.obtainMessage(2, runtimeException));
    }

    public boolean zza(zzqt com_google_android_gms_internal_zzqt) {
        return false;
    }

    public void zzaof() {
    }

    public void zzapb() {
        if (isConnected()) {
            ((zzpv) this.uy).zzapk();
        }
    }

    void zzapz() {
        this.tr.lock();
        try {
            this.uy = new zzpw(this, this.tN, this.tO, this.tz, this.si, this.tr, this.mContext);
            this.uy.begin();
            this.uv.signalAll();
        } finally {
            this.tr.unlock();
        }
    }

    void zzaqa() {
        this.tr.lock();
        try {
            this.th.zzapw();
            this.uy = new zzpv(this);
            this.uy.begin();
            this.uv.signalAll();
        } finally {
            this.tr.unlock();
        }
    }

    void zzaqb() {
        for (zze disconnect : this.ui.values()) {
            disconnect.disconnect();
        }
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.internal.zzpm.zza<R, A>> T zzc(@NonNull T t) {
        t.zzaow();
        return this.uy.zzc(t);
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.internal.zzpm.zza<? extends Result, A>> T zzd(@NonNull T t) {
        t.zzaow();
        return this.uy.zzd(t);
    }

    void zzi(ConnectionResult connectionResult) {
        this.tr.lock();
        try {
            this.uz = connectionResult;
            this.uy = new zzpx(this);
            this.uy.begin();
            this.uv.signalAll();
        } finally {
            this.tr.unlock();
        }
    }
}
