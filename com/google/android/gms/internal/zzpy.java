package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.auth.api.signin.internal.zzk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzl;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;

public final class zzpy extends GoogleApiClient implements com.google.android.gms.internal.zzqh.zza {
    private final Context mContext;
    private final int sf;
    private final GoogleApiAvailability sh;
    final com.google.android.gms.common.api.Api.zza<? extends zzvu, zzvv> si;
    final zzg tN;
    final Map<Api<?>, Integer> tO;
    private final Lock tr;
    private final zzl ua;
    private zzqh ub;
    final Queue<com.google.android.gms.internal.zzpm.zza<?, ?>> uc;
    private volatile boolean ud;
    private long ue;
    private long uf;
    private final zza ug;
    zzqe uh;
    final Map<zzc<?>, zze> ui;
    Set<Scope> uj;
    private final zzqo uk;
    private final ArrayList<zzpp> ul;
    private Integer um;
    Set<zzqx> un;
    final zzqy uo;
    private final com.google.android.gms.common.internal.zzl.zza up;
    private final Looper zzahv;

    final class zza extends Handler {
        final /* synthetic */ zzpy uq;

        zza(zzpy com_google_android_gms_internal_zzpy, Looper looper) {
            this.uq = com_google_android_gms_internal_zzpy;
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    this.uq.zzapu();
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    this.uq.resume();
                default:
                    Log.w("GoogleApiClientImpl", "Unknown message id: " + message.what);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpy.1 */
    class C07121 implements com.google.android.gms.common.internal.zzl.zza {
        final /* synthetic */ zzpy uq;

        C07121(zzpy com_google_android_gms_internal_zzpy) {
            this.uq = com_google_android_gms_internal_zzpy;
        }

        public boolean isConnected() {
            return this.uq.isConnected();
        }

        public Bundle zzamh() {
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpy.2 */
    class C07132 implements ConnectionCallbacks {
        final /* synthetic */ zzpy uq;
        final /* synthetic */ AtomicReference ur;
        final /* synthetic */ zzqu us;

        C07132(zzpy com_google_android_gms_internal_zzpy, AtomicReference atomicReference, zzqu com_google_android_gms_internal_zzqu) {
            this.uq = com_google_android_gms_internal_zzpy;
            this.ur = atomicReference;
            this.us = com_google_android_gms_internal_zzqu;
        }

        public void onConnected(Bundle bundle) {
            this.uq.zza((GoogleApiClient) this.ur.get(), this.us, true);
        }

        public void onConnectionSuspended(int i) {
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpy.3 */
    class C07143 implements OnConnectionFailedListener {
        final /* synthetic */ zzpy uq;
        final /* synthetic */ zzqu us;

        C07143(zzpy com_google_android_gms_internal_zzpy, zzqu com_google_android_gms_internal_zzqu) {
            this.uq = com_google_android_gms_internal_zzpy;
            this.us = com_google_android_gms_internal_zzqu;
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            this.us.zzc(new Status(8));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpy.4 */
    class C07154 implements ResultCallback<Status> {
        final /* synthetic */ GoogleApiClient iA;
        final /* synthetic */ zzpy uq;
        final /* synthetic */ zzqu us;
        final /* synthetic */ boolean ut;

        C07154(zzpy com_google_android_gms_internal_zzpy, zzqu com_google_android_gms_internal_zzqu, boolean z, GoogleApiClient googleApiClient) {
            this.uq = com_google_android_gms_internal_zzpy;
            this.us = com_google_android_gms_internal_zzqu;
            this.ut = z;
            this.iA = googleApiClient;
        }

        public /* synthetic */ void onResult(@NonNull Result result) {
            zzp((Status) result);
        }

        public void zzp(@NonNull Status status) {
            zzk.zzbc(this.uq.mContext).zzagl();
            if (status.isSuccess() && this.uq.isConnected()) {
                this.uq.reconnect();
            }
            this.us.zzc((Result) status);
            if (this.ut) {
                this.iA.disconnect();
            }
        }
    }

    static class zzb extends com.google.android.gms.internal.zzqe.zza {
        private WeakReference<zzpy> uu;

        zzb(zzpy com_google_android_gms_internal_zzpy) {
            this.uu = new WeakReference(com_google_android_gms_internal_zzpy);
        }

        public void zzaou() {
            zzpy com_google_android_gms_internal_zzpy = (zzpy) this.uu.get();
            if (com_google_android_gms_internal_zzpy != null) {
                com_google_android_gms_internal_zzpy.resume();
            }
        }
    }

    public zzpy(Context context, Lock lock, Looper looper, zzg com_google_android_gms_common_internal_zzg, GoogleApiAvailability googleApiAvailability, com.google.android.gms.common.api.Api.zza<? extends zzvu, zzvv> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzvu__com_google_android_gms_internal_zzvv, Map<Api<?>, Integer> map, List<ConnectionCallbacks> list, List<OnConnectionFailedListener> list2, Map<zzc<?>, zze> map2, int i, int i2, ArrayList<zzpp> arrayList) {
        this.ub = null;
        this.uc = new LinkedList();
        this.ue = 120000;
        this.uf = DNSConstants.CLOSE_TIMEOUT;
        this.uj = new HashSet();
        this.uk = new zzqo();
        this.um = null;
        this.un = null;
        this.up = new C07121(this);
        this.mContext = context;
        this.tr = lock;
        this.ua = new zzl(looper, this.up);
        this.zzahv = looper;
        this.ug = new zza(this, looper);
        this.sh = googleApiAvailability;
        this.sf = i;
        if (this.sf >= 0) {
            this.um = Integer.valueOf(i2);
        }
        this.tO = map;
        this.ui = map2;
        this.ul = arrayList;
        this.uo = new zzqy(this.ui);
        for (ConnectionCallbacks registerConnectionCallbacks : list) {
            this.ua.registerConnectionCallbacks(registerConnectionCallbacks);
        }
        for (OnConnectionFailedListener registerConnectionFailedListener : list2) {
            this.ua.registerConnectionFailedListener(registerConnectionFailedListener);
        }
        this.tN = com_google_android_gms_common_internal_zzg;
        this.si = com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzvu__com_google_android_gms_internal_zzvv;
    }

    private void resume() {
        this.tr.lock();
        try {
            if (isResuming()) {
                zzapt();
            }
            this.tr.unlock();
        } catch (Throwable th) {
            this.tr.unlock();
        }
    }

    public static int zza(Iterable<zze> iterable, boolean z) {
        int i = 0;
        int i2 = 0;
        for (zze com_google_android_gms_common_api_Api_zze : iterable) {
            if (com_google_android_gms_common_api_Api_zze.zzafk()) {
                i2 = 1;
            }
            i = com_google_android_gms_common_api_Api_zze.zzafz() ? 1 : i;
        }
        return i2 != 0 ? (i == 0 || !z) ? 1 : 2 : 3;
    }

    private void zza(GoogleApiClient googleApiClient, zzqu com_google_android_gms_internal_zzqu, boolean z) {
        zzre.zt.zzg(googleApiClient).setResultCallback(new C07154(this, com_google_android_gms_internal_zzqu, z, googleApiClient));
    }

    private void zzapt() {
        this.ua.zzasx();
        this.ub.connect();
    }

    private void zzapu() {
        this.tr.lock();
        try {
            if (zzapw()) {
                zzapt();
            }
            this.tr.unlock();
        } catch (Throwable th) {
            this.tr.unlock();
        }
    }

    private void zzb(@NonNull zzqi com_google_android_gms_internal_zzqi) {
        if (this.sf >= 0) {
            zzpk.zza(com_google_android_gms_internal_zzqi).zzfh(this.sf);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    private void zzfk(int i) {
        if (this.um == null) {
            this.um = Integer.valueOf(i);
        } else if (this.um.intValue() != i) {
            String valueOf = String.valueOf(zzfl(i));
            String valueOf2 = String.valueOf(zzfl(this.um.intValue()));
            throw new IllegalStateException(new StringBuilder((String.valueOf(valueOf).length() + 51) + String.valueOf(valueOf2).length()).append("Cannot use sign-in mode: ").append(valueOf).append(". Mode was already set to ").append(valueOf2).toString());
        }
        if (this.ub == null) {
            Object obj = null;
            Object obj2 = null;
            for (zze com_google_android_gms_common_api_Api_zze : this.ui.values()) {
                if (com_google_android_gms_common_api_Api_zze.zzafk()) {
                    obj2 = 1;
                }
                obj = com_google_android_gms_common_api_Api_zze.zzafz() ? 1 : obj;
            }
            switch (this.um.intValue()) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    if (obj2 == null) {
                        throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                    } else if (obj != null) {
                        throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
                    }
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    if (obj2 != null) {
                        this.ub = zzpq.zza(this.mContext, this, this.tr, this.zzahv, this.sh, this.ui, this.tN, this.tO, this.si, this.ul);
                        return;
                    }
                    break;
            }
            this.ub = new zzqa(this.mContext, this, this.tr, this.zzahv, this.sh, this.ui, this.tN, this.tO, this.si, this.ul, this);
        }
    }

    static String zzfl(int i) {
        switch (i) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return "SIGN_IN_MODE_REQUIRED";
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return "SIGN_IN_MODE_OPTIONAL";
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return "SIGN_IN_MODE_NONE";
            default:
                return "UNKNOWN";
        }
    }

    public ConnectionResult blockingConnect() {
        boolean z = true;
        zzab.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "blockingConnect must not be called on the UI thread");
        this.tr.lock();
        try {
            if (this.sf >= 0) {
                if (this.um == null) {
                    z = false;
                }
                zzab.zza(z, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.um == null) {
                this.um = Integer.valueOf(zza(this.ui.values(), false));
            } else if (this.um.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zzfk(this.um.intValue());
            this.ua.zzasx();
            ConnectionResult blockingConnect = this.ub.blockingConnect();
            return blockingConnect;
        } finally {
            this.tr.unlock();
        }
    }

    public ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        boolean z = false;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            z = true;
        }
        zzab.zza(z, (Object) "blockingConnect must not be called on the UI thread");
        zzab.zzb((Object) timeUnit, (Object) "TimeUnit must not be null");
        this.tr.lock();
        try {
            if (this.um == null) {
                this.um = Integer.valueOf(zza(this.ui.values(), false));
            } else if (this.um.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zzfk(this.um.intValue());
            this.ua.zzasx();
            ConnectionResult blockingConnect = this.ub.blockingConnect(j, timeUnit);
            return blockingConnect;
        } finally {
            this.tr.unlock();
        }
    }

    public PendingResult<Status> clearDefaultAccountAndReconnect() {
        zzab.zza(isConnected(), (Object) "GoogleApiClient is not connected yet.");
        zzab.zza(this.um.intValue() != 2, (Object) "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        PendingResult com_google_android_gms_internal_zzqu = new zzqu((GoogleApiClient) this);
        if (this.ui.containsKey(zzre.bJ)) {
            zza(this, com_google_android_gms_internal_zzqu, false);
        } else {
            AtomicReference atomicReference = new AtomicReference();
            GoogleApiClient build = new Builder(this.mContext).addApi(zzre.API).addConnectionCallbacks(new C07132(this, atomicReference, com_google_android_gms_internal_zzqu)).addOnConnectionFailedListener(new C07143(this, com_google_android_gms_internal_zzqu)).setHandler(this.ug).build();
            atomicReference.set(build);
            build.connect();
        }
        return com_google_android_gms_internal_zzqu;
    }

    public void connect() {
        boolean z = false;
        this.tr.lock();
        try {
            if (this.sf >= 0) {
                if (this.um != null) {
                    z = true;
                }
                zzab.zza(z, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.um == null) {
                this.um = Integer.valueOf(zza(this.ui.values(), false));
            } else if (this.um.intValue() == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            connect(this.um.intValue());
        } finally {
            this.tr.unlock();
        }
    }

    public void connect(int i) {
        boolean z = true;
        this.tr.lock();
        if (!(i == 3 || i == 1 || i == 2)) {
            z = false;
        }
        try {
            zzab.zzb(z, "Illegal sign-in mode: " + i);
            zzfk(i);
            zzapt();
        } finally {
            this.tr.unlock();
        }
    }

    public void disconnect() {
        this.tr.lock();
        try {
            this.uo.release();
            if (this.ub != null) {
                this.ub.disconnect();
            }
            this.uk.release();
            for (com.google.android.gms.internal.zzpm.zza com_google_android_gms_internal_zzpm_zza : this.uc) {
                com_google_android_gms_internal_zzpm_zza.zza(null);
                com_google_android_gms_internal_zzpm_zza.cancel();
            }
            this.uc.clear();
            if (this.ub != null) {
                zzapw();
                this.ua.zzasw();
                this.tr.unlock();
            }
        } finally {
            this.tr.unlock();
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("mContext=").println(this.mContext);
        printWriter.append(str).append("mResuming=").print(this.ud);
        printWriter.append(" mWorkQueue.size()=").print(this.uc.size());
        this.uo.dump(printWriter);
        if (this.ub != null) {
            this.ub.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    @NonNull
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        this.tr.lock();
        try {
            if (!isConnected() && !isResuming()) {
                throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
            } else if (this.ui.containsKey(api.zzans())) {
                ConnectionResult connectionResult = this.ub.getConnectionResult(api);
                if (connectionResult != null) {
                    this.tr.unlock();
                } else if (isResuming()) {
                    connectionResult = ConnectionResult.rb;
                } else {
                    Log.w("GoogleApiClientImpl", zzapy());
                    Log.wtf("GoogleApiClientImpl", String.valueOf(api.getName()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
                    connectionResult = new ConnectionResult(8, null);
                    this.tr.unlock();
                }
                return connectionResult;
            } else {
                throw new IllegalArgumentException(String.valueOf(api.getName()).concat(" was never registered with GoogleApiClient"));
            }
        } finally {
            this.tr.unlock();
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public Looper getLooper() {
        return this.zzahv;
    }

    public int getSessionId() {
        return System.identityHashCode(this);
    }

    public boolean hasConnectedApi(@NonNull Api<?> api) {
        zze com_google_android_gms_common_api_Api_zze = (zze) this.ui.get(api.zzans());
        return com_google_android_gms_common_api_Api_zze != null && com_google_android_gms_common_api_Api_zze.isConnected();
    }

    public boolean isConnected() {
        return this.ub != null && this.ub.isConnected();
    }

    public boolean isConnecting() {
        return this.ub != null && this.ub.isConnecting();
    }

    public boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks) {
        return this.ua.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    public boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        return this.ua.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    boolean isResuming() {
        return this.ud;
    }

    public void reconnect() {
        disconnect();
        connect();
    }

    public void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        this.ua.registerConnectionCallbacks(connectionCallbacks);
    }

    public void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this.ua.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        zzb(new zzqi(fragmentActivity));
    }

    public void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        this.ua.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this.ua.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @NonNull
    public <C extends zze> C zza(@NonNull zzc<C> com_google_android_gms_common_api_Api_zzc_C) {
        Object obj = (zze) this.ui.get(com_google_android_gms_common_api_Api_zzc_C);
        zzab.zzb(obj, (Object) "Appropriate Api was not requested.");
        return obj;
    }

    public void zza(zzqx com_google_android_gms_internal_zzqx) {
        this.tr.lock();
        try {
            if (this.un == null) {
                this.un = new HashSet();
            }
            this.un.add(com_google_android_gms_internal_zzqx);
        } finally {
            this.tr.unlock();
        }
    }

    public boolean zza(@NonNull Api<?> api) {
        return this.ui.containsKey(api.zzans());
    }

    public boolean zza(zzqt com_google_android_gms_internal_zzqt) {
        return this.ub != null && this.ub.zza(com_google_android_gms_internal_zzqt);
    }

    public void zzaof() {
        if (this.ub != null) {
            this.ub.zzaof();
        }
    }

    void zzapv() {
        if (!isResuming()) {
            this.ud = true;
            if (this.uh == null) {
                this.uh = this.sh.zza(this.mContext.getApplicationContext(), new zzb(this));
            }
            this.ug.sendMessageDelayed(this.ug.obtainMessage(1), this.ue);
            this.ug.sendMessageDelayed(this.ug.obtainMessage(2), this.uf);
        }
    }

    boolean zzapw() {
        if (!isResuming()) {
            return false;
        }
        this.ud = false;
        this.ug.removeMessages(2);
        this.ug.removeMessages(1);
        if (this.uh != null) {
            this.uh.unregister();
            this.uh = null;
        }
        return true;
    }

    boolean zzapx() {
        boolean z = false;
        this.tr.lock();
        try {
            if (this.un != null) {
                if (!this.un.isEmpty()) {
                    z = true;
                }
                this.tr.unlock();
            }
            return z;
        } finally {
            this.tr.unlock();
        }
    }

    String zzapy() {
        Writer stringWriter = new StringWriter();
        dump(XmlPullParser.NO_NAMESPACE, null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }

    <C extends zze> C zzb(zzc<?> com_google_android_gms_common_api_Api_zzc_) {
        Object obj = (zze) this.ui.get(com_google_android_gms_common_api_Api_zzc_);
        zzab.zzb(obj, (Object) "Appropriate Api was not requested.");
        return obj;
    }

    public void zzb(zzqx com_google_android_gms_internal_zzqx) {
        this.tr.lock();
        try {
            if (this.un == null) {
                Log.wtf("GoogleApiClientImpl", "Attempted to remove pending transform when no transforms are registered.", new Exception());
            } else if (!this.un.remove(com_google_android_gms_internal_zzqx)) {
                Log.wtf("GoogleApiClientImpl", "Failed to remove pending transform - this may lead to memory leaks!", new Exception());
            } else if (!zzapx()) {
                this.ub.zzapb();
            }
            this.tr.unlock();
        } catch (Throwable th) {
            this.tr.unlock();
        }
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.internal.zzpm.zza<R, A>> T zzc(@NonNull T t) {
        zzab.zzb(t.zzans() != null, (Object) "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean containsKey = this.ui.containsKey(t.zzans());
        String name = t.zzanz() != null ? t.zzanz().getName() : "the API";
        zzab.zzb(containsKey, new StringBuilder(String.valueOf(name).length() + 65).append("GoogleApiClient is not configured to use ").append(name).append(" required for this call.").toString());
        this.tr.lock();
        try {
            if (this.ub == null) {
                this.uc.add(t);
            } else {
                t = this.ub.zzc(t);
                this.tr.unlock();
            }
            return t;
        } finally {
            this.tr.unlock();
        }
    }

    public void zzc(int i, boolean z) {
        if (i == 1 && !z) {
            zzapv();
        }
        this.uo.zzaqz();
        this.ua.zzgf(i);
        this.ua.zzasw();
        if (i == 2) {
            zzapt();
        }
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.internal.zzpm.zza<? extends Result, A>> T zzd(@NonNull T t) {
        zzab.zzb(t.zzans() != null, (Object) "This task can not be executed (it's probably a Batch or malformed)");
        boolean containsKey = this.ui.containsKey(t.zzans());
        String name = t.zzanz() != null ? t.zzanz().getName() : "the API";
        zzab.zzb(containsKey, new StringBuilder(String.valueOf(name).length() + 65).append("GoogleApiClient is not configured to use ").append(name).append(" required for this call.").toString());
        this.tr.lock();
        try {
            if (this.ub == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            }
            if (isResuming()) {
                this.uc.add(t);
                while (!this.uc.isEmpty()) {
                    com.google.android.gms.internal.zzpm.zza com_google_android_gms_internal_zzpm_zza = (com.google.android.gms.internal.zzpm.zza) this.uc.remove();
                    this.uo.zzg(com_google_android_gms_internal_zzpm_zza);
                    com_google_android_gms_internal_zzpm_zza.zzz(Status.ss);
                }
            } else {
                t = this.ub.zzd(t);
                this.tr.unlock();
            }
            return t;
        } finally {
            this.tr.unlock();
        }
    }

    public void zzd(ConnectionResult connectionResult) {
        if (!this.sh.zzc(this.mContext, connectionResult.getErrorCode())) {
            zzapw();
        }
        if (!isResuming()) {
            this.ua.zzm(connectionResult);
            this.ua.zzasw();
        }
    }

    public void zzm(Bundle bundle) {
        while (!this.uc.isEmpty()) {
            zzd((com.google.android.gms.internal.zzpm.zza) this.uc.remove());
        }
        this.ua.zzo(bundle);
    }

    public <L> zzqn<L> zzs(@NonNull L l) {
        this.tr.lock();
        try {
            zzqn<L> zzb = this.uk.zzb(l, this.zzahv);
            return zzb;
        } finally {
            this.tr.unlock();
        }
    }
}
