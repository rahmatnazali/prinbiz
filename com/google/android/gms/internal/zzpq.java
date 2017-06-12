package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzg;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import org.apache.commons.net.ftp.FTPClient;

final class zzpq implements zzqh {
    private final Context mContext;
    private final zzpy th;
    private final zzqa ti;
    private final zzqa tj;
    private final Map<zzc<?>, zzqa> tk;
    private final Set<zzqt> tl;
    private final zze tm;
    private Bundle tn;
    private ConnectionResult to;
    private ConnectionResult tp;
    private boolean tq;
    private final Lock tr;
    private int ts;
    private final Looper zzahv;

    /* renamed from: com.google.android.gms.internal.zzpq.1 */
    class C02121 implements Runnable {
        final /* synthetic */ zzpq tt;

        C02121(zzpq com_google_android_gms_internal_zzpq) {
            this.tt = com_google_android_gms_internal_zzpq;
        }

        public void run() {
            this.tt.tr.lock();
            try {
                this.tt.zzape();
            } finally {
                this.tt.tr.unlock();
            }
        }
    }

    private class zza implements com.google.android.gms.internal.zzqh.zza {
        final /* synthetic */ zzpq tt;

        private zza(zzpq com_google_android_gms_internal_zzpq) {
            this.tt = com_google_android_gms_internal_zzpq;
        }

        public void zzc(int i, boolean z) {
            this.tt.tr.lock();
            try {
                if (this.tt.tq || this.tt.tp == null || !this.tt.tp.isSuccess()) {
                    this.tt.tq = false;
                    this.tt.zzb(i, z);
                    return;
                }
                this.tt.tq = true;
                this.tt.tj.onConnectionSuspended(i);
                this.tt.tr.unlock();
            } finally {
                this.tt.tr.unlock();
            }
        }

        public void zzd(@NonNull ConnectionResult connectionResult) {
            this.tt.tr.lock();
            try {
                this.tt.to = connectionResult;
                this.tt.zzape();
            } finally {
                this.tt.tr.unlock();
            }
        }

        public void zzm(@Nullable Bundle bundle) {
            this.tt.tr.lock();
            try {
                this.tt.zzl(bundle);
                this.tt.to = ConnectionResult.rb;
                this.tt.zzape();
            } finally {
                this.tt.tr.unlock();
            }
        }
    }

    private class zzb implements com.google.android.gms.internal.zzqh.zza {
        final /* synthetic */ zzpq tt;

        private zzb(zzpq com_google_android_gms_internal_zzpq) {
            this.tt = com_google_android_gms_internal_zzpq;
        }

        public void zzc(int i, boolean z) {
            this.tt.tr.lock();
            try {
                if (this.tt.tq) {
                    this.tt.tq = false;
                    this.tt.zzb(i, z);
                    return;
                }
                this.tt.tq = true;
                this.tt.ti.onConnectionSuspended(i);
                this.tt.tr.unlock();
            } finally {
                this.tt.tr.unlock();
            }
        }

        public void zzd(@NonNull ConnectionResult connectionResult) {
            this.tt.tr.lock();
            try {
                this.tt.tp = connectionResult;
                this.tt.zzape();
            } finally {
                this.tt.tr.unlock();
            }
        }

        public void zzm(@Nullable Bundle bundle) {
            this.tt.tr.lock();
            try {
                this.tt.tp = ConnectionResult.rb;
                this.tt.zzape();
            } finally {
                this.tt.tr.unlock();
            }
        }
    }

    private zzpq(Context context, zzpy com_google_android_gms_internal_zzpy, Lock lock, Looper looper, com.google.android.gms.common.zzc com_google_android_gms_common_zzc, Map<zzc<?>, zze> map, Map<zzc<?>, zze> map2, zzg com_google_android_gms_common_internal_zzg, com.google.android.gms.common.api.Api.zza<? extends zzvu, zzvv> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzvu__com_google_android_gms_internal_zzvv, zze com_google_android_gms_common_api_Api_zze, ArrayList<zzpp> arrayList, ArrayList<zzpp> arrayList2, Map<Api<?>, Integer> map3, Map<Api<?>, Integer> map4) {
        this.tl = Collections.newSetFromMap(new WeakHashMap());
        this.to = null;
        this.tp = null;
        this.tq = false;
        this.ts = 0;
        this.mContext = context;
        this.th = com_google_android_gms_internal_zzpy;
        this.tr = lock;
        this.zzahv = looper;
        this.tm = com_google_android_gms_common_api_Api_zze;
        this.ti = new zzqa(context, this.th, lock, looper, com_google_android_gms_common_zzc, map2, null, map4, null, arrayList2, new zza());
        this.tj = new zzqa(context, this.th, lock, looper, com_google_android_gms_common_zzc, map, com_google_android_gms_common_internal_zzg, map3, com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzvu__com_google_android_gms_internal_zzvv, arrayList, new zzb());
        Map arrayMap = new ArrayMap();
        for (zzc put : map2.keySet()) {
            arrayMap.put(put, this.ti);
        }
        for (zzc put2 : map.keySet()) {
            arrayMap.put(put2, this.tj);
        }
        this.tk = Collections.unmodifiableMap(arrayMap);
    }

    public static zzpq zza(Context context, zzpy com_google_android_gms_internal_zzpy, Lock lock, Looper looper, com.google.android.gms.common.zzc com_google_android_gms_common_zzc, Map<zzc<?>, zze> map, zzg com_google_android_gms_common_internal_zzg, Map<Api<?>, Integer> map2, com.google.android.gms.common.api.Api.zza<? extends zzvu, zzvv> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzvu__com_google_android_gms_internal_zzvv, ArrayList<zzpp> arrayList) {
        zze com_google_android_gms_common_api_Api_zze = null;
        Map arrayMap = new ArrayMap();
        Map arrayMap2 = new ArrayMap();
        for (Entry entry : map.entrySet()) {
            zze com_google_android_gms_common_api_Api_zze2 = (zze) entry.getValue();
            if (com_google_android_gms_common_api_Api_zze2.zzafz()) {
                com_google_android_gms_common_api_Api_zze = com_google_android_gms_common_api_Api_zze2;
            }
            if (com_google_android_gms_common_api_Api_zze2.zzafk()) {
                arrayMap.put((zzc) entry.getKey(), com_google_android_gms_common_api_Api_zze2);
            } else {
                arrayMap2.put((zzc) entry.getKey(), com_google_android_gms_common_api_Api_zze2);
            }
        }
        zzab.zza(!arrayMap.isEmpty(), (Object) "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        Map arrayMap3 = new ArrayMap();
        Map arrayMap4 = new ArrayMap();
        for (Api api : map2.keySet()) {
            zzc zzans = api.zzans();
            if (arrayMap.containsKey(zzans)) {
                arrayMap3.put(api, (Integer) map2.get(api));
            } else if (arrayMap2.containsKey(zzans)) {
                arrayMap4.put(api, (Integer) map2.get(api));
            } else {
                throw new IllegalStateException("Each API in the apiTypeMap must have a corresponding client in the clients map.");
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            zzpp com_google_android_gms_internal_zzpp = (zzpp) it.next();
            if (arrayMap3.containsKey(com_google_android_gms_internal_zzpp.pN)) {
                arrayList2.add(com_google_android_gms_internal_zzpp);
            } else if (arrayMap4.containsKey(com_google_android_gms_internal_zzpp.pN)) {
                arrayList3.add(com_google_android_gms_internal_zzpp);
            } else {
                throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the apiTypeMap");
            }
        }
        return new zzpq(context, com_google_android_gms_internal_zzpy, lock, looper, com_google_android_gms_common_zzc, arrayMap, arrayMap2, com_google_android_gms_common_internal_zzg, com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzvu__com_google_android_gms_internal_zzvv, com_google_android_gms_common_api_Api_zze, arrayList2, arrayList3, arrayMap3, arrayMap4);
    }

    private void zzapd() {
        this.tp = null;
        this.to = null;
        this.ti.connect();
        this.tj.connect();
    }

    private void zzape() {
        if (zzc(this.to)) {
            if (zzc(this.tp) || zzaph()) {
                zzapf();
            } else if (this.tp == null) {
            } else {
                if (this.ts == 1) {
                    zzapg();
                    return;
                }
                zzb(this.tp);
                this.ti.disconnect();
            }
        } else if (this.to != null && zzc(this.tp)) {
            this.tj.disconnect();
            zzb(this.to);
        } else if (this.to != null && this.tp != null) {
            ConnectionResult connectionResult = this.to;
            if (this.tj.uA < this.ti.uA) {
                connectionResult = this.tp;
            }
            zzb(connectionResult);
        }
    }

    private void zzapf() {
        switch (this.ts) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                break;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                this.th.zzm(this.tn);
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                break;
        }
        zzapg();
        this.ts = 0;
    }

    private void zzapg() {
        for (zzqt zzafy : this.tl) {
            zzafy.zzafy();
        }
        this.tl.clear();
    }

    private boolean zzaph() {
        return this.tp != null && this.tp.getErrorCode() == 4;
    }

    @Nullable
    private PendingIntent zzapi() {
        return this.tm == null ? null : PendingIntent.getActivity(this.mContext, this.th.getSessionId(), this.tm.zzaga(), 134217728);
    }

    private void zzb(int i, boolean z) {
        this.th.zzc(i, z);
        this.tp = null;
        this.to = null;
    }

    private void zzb(ConnectionResult connectionResult) {
        switch (this.ts) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                break;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                this.th.zzd(connectionResult);
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                break;
        }
        zzapg();
        this.ts = 0;
    }

    private static boolean zzc(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    private boolean zze(com.google.android.gms.internal.zzpm.zza<? extends Result, ? extends com.google.android.gms.common.api.Api.zzb> com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result___extends_com_google_android_gms_common_api_Api_zzb) {
        zzc zzans = com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result___extends_com_google_android_gms_common_api_Api_zzb.zzans();
        zzab.zzb(this.tk.containsKey(zzans), (Object) "GoogleApiClient is not configured to use the API required for this call.");
        return ((zzqa) this.tk.get(zzans)).equals(this.tj);
    }

    private void zzl(Bundle bundle) {
        if (this.tn == null) {
            this.tn = bundle;
        } else if (bundle != null) {
            this.tn.putAll(bundle);
        }
    }

    public ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException();
    }

    public ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public void connect() {
        this.ts = 2;
        this.tq = false;
        zzapd();
    }

    public void disconnect() {
        this.tp = null;
        this.to = null;
        this.ts = 0;
        this.ti.disconnect();
        this.tj.disconnect();
        zzapg();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("authClient").println(":");
        this.tj.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        printWriter.append(str).append("anonClient").println(":");
        this.ti.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
    }

    @Nullable
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return ((zzqa) this.tk.get(api.zzans())).equals(this.tj) ? zzaph() ? new ConnectionResult(4, zzapi()) : this.tj.getConnectionResult(api) : this.ti.getConnectionResult(api);
    }

    public boolean isConnected() {
        boolean z = true;
        this.tr.lock();
        try {
            if (!(this.ti.isConnected() && (zzapc() || zzaph() || this.ts == 1))) {
                z = false;
            }
            this.tr.unlock();
            return z;
        } catch (Throwable th) {
            this.tr.unlock();
        }
    }

    public boolean isConnecting() {
        this.tr.lock();
        try {
            boolean z = this.ts == 2;
            this.tr.unlock();
            return z;
        } catch (Throwable th) {
            this.tr.unlock();
        }
    }

    public boolean zza(zzqt com_google_android_gms_internal_zzqt) {
        this.tr.lock();
        try {
            if ((isConnecting() || isConnected()) && !zzapc()) {
                this.tl.add(com_google_android_gms_internal_zzqt);
                if (this.ts == 0) {
                    this.ts = 1;
                }
                this.tp = null;
                this.tj.connect();
                return true;
            }
            this.tr.unlock();
            return false;
        } finally {
            this.tr.unlock();
        }
    }

    public void zzaof() {
        this.tr.lock();
        try {
            boolean isConnecting = isConnecting();
            this.tj.disconnect();
            this.tp = new ConnectionResult(4);
            if (isConnecting) {
                new Handler(this.zzahv).post(new C02121(this));
            } else {
                zzapg();
            }
            this.tr.unlock();
        } catch (Throwable th) {
            this.tr.unlock();
        }
    }

    public void zzapb() {
        this.ti.zzapb();
        this.tj.zzapb();
    }

    public boolean zzapc() {
        return this.tj.isConnected();
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.internal.zzpm.zza<R, A>> T zzc(@NonNull T t) {
        if (!zze((com.google.android.gms.internal.zzpm.zza) t)) {
            return this.ti.zzc((com.google.android.gms.internal.zzpm.zza) t);
        }
        if (!zzaph()) {
            return this.tj.zzc((com.google.android.gms.internal.zzpm.zza) t);
        }
        t.zzz(new Status(4, null, zzapi()));
        return t;
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.internal.zzpm.zza<? extends Result, A>> T zzd(@NonNull T t) {
        if (!zze((com.google.android.gms.internal.zzpm.zza) t)) {
            return this.ti.zzd(t);
        }
        if (!zzaph()) {
            return this.tj.zzd(t);
        }
        t.zzz(new Status(4, null, zzapi()));
        return t;
    }
}
