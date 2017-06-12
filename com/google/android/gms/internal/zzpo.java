package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.commons.net.ftp.FTPClient;

public abstract class zzpo<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> sS;
    private final Object sT;
    protected final zza<R> sU;
    protected final WeakReference<GoogleApiClient> sV;
    private final ArrayList<com.google.android.gms.common.api.PendingResult.zza> sW;
    private ResultCallback<? super R> sX;
    private zzb sY;
    private volatile boolean sZ;
    private R sm;
    private boolean ta;
    private zzr tb;
    private volatile zzqx<R> tc;
    private boolean td;
    private boolean zzak;
    private final CountDownLatch zzale;

    /* renamed from: com.google.android.gms.internal.zzpo.1 */
    class C02111 extends ThreadLocal<Boolean> {
        C02111() {
        }

        protected /* synthetic */ Object initialValue() {
            return zzaoy();
        }

        protected Boolean zzaoy() {
            return Boolean.valueOf(false);
        }
    }

    public static class zza<R extends Result> extends Handler {
        public zza() {
            this(Looper.getMainLooper());
        }

        public zza(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    Pair pair = (Pair) message.obj;
                    zzb((ResultCallback) pair.first, (Result) pair.second);
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    ((zzpo) message.obj).zzaa(Status.st);
                default:
                    Log.wtf("BasePendingResult", "Don't know how to handle message: " + message.what, new Exception());
            }
        }

        public void zza(ResultCallback<? super R> resultCallback, R r) {
            sendMessage(obtainMessage(1, new Pair(resultCallback, r)));
        }

        public void zza(zzpo<R> com_google_android_gms_internal_zzpo_R, long j) {
            sendMessageDelayed(obtainMessage(2, com_google_android_gms_internal_zzpo_R), j);
        }

        public void zzaoz() {
            removeMessages(2);
        }

        protected void zzb(ResultCallback<? super R> resultCallback, R r) {
            try {
                resultCallback.onResult(r);
            } catch (RuntimeException e) {
                zzpo.zze(r);
                throw e;
            }
        }
    }

    private final class zzb {
        final /* synthetic */ zzpo te;

        private zzb(zzpo com_google_android_gms_internal_zzpo) {
            this.te = com_google_android_gms_internal_zzpo;
        }

        protected void finalize() throws Throwable {
            zzpo.zze(this.te.sm);
            super.finalize();
        }
    }

    static {
        sS = new C02111();
    }

    @Deprecated
    zzpo() {
        this.sT = new Object();
        this.zzale = new CountDownLatch(1);
        this.sW = new ArrayList();
        this.td = false;
        this.sU = new zza(Looper.getMainLooper());
        this.sV = new WeakReference(null);
    }

    @Deprecated
    protected zzpo(Looper looper) {
        this.sT = new Object();
        this.zzale = new CountDownLatch(1);
        this.sW = new ArrayList();
        this.td = false;
        this.sU = new zza(looper);
        this.sV = new WeakReference(null);
    }

    protected zzpo(GoogleApiClient googleApiClient) {
        this.sT = new Object();
        this.zzale = new CountDownLatch(1);
        this.sW = new ArrayList();
        this.td = false;
        this.sU = new zza(googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
        this.sV = new WeakReference(googleApiClient);
    }

    private R get() {
        R r;
        boolean z = true;
        synchronized (this.sT) {
            if (this.sZ) {
                z = false;
            }
            zzab.zza(z, (Object) "Result has already been consumed.");
            zzab.zza(isReady(), (Object) "Result is not ready.");
            r = this.sm;
            this.sm = null;
            this.sX = null;
            this.sZ = true;
        }
        zzaos();
        return r;
    }

    private void zzd(R r) {
        this.sm = r;
        this.tb = null;
        this.zzale.countDown();
        Status status = this.sm.getStatus();
        if (this.zzak) {
            this.sX = null;
        } else if (this.sX != null) {
            this.sU.zzaoz();
            this.sU.zza(this.sX, get());
        } else if (this.sm instanceof Releasable) {
            this.sY = new zzb();
        }
        Iterator it = this.sW.iterator();
        while (it.hasNext()) {
            ((com.google.android.gms.common.api.PendingResult.zza) it.next()).zzv(status);
        }
        this.sW.clear();
    }

    public static void zze(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (Throwable e) {
                String valueOf = String.valueOf(result);
                Log.w("BasePendingResult", new StringBuilder(String.valueOf(valueOf).length() + 18).append("Unable to release ").append(valueOf).toString(), e);
            }
        }
    }

    public final R await() {
        boolean z = true;
        zzab.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "await must not be called on the UI thread");
        zzab.zza(!this.sZ, (Object) "Result has already been consumed");
        if (this.tc != null) {
            z = false;
        }
        zzab.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            this.zzale.await();
        } catch (InterruptedException e) {
            zzaa(Status.sr);
        }
        zzab.zza(isReady(), (Object) "Result is not ready.");
        return get();
    }

    public final R await(long j, TimeUnit timeUnit) {
        boolean z = true;
        boolean z2 = j <= 0 || Looper.myLooper() != Looper.getMainLooper();
        zzab.zza(z2, (Object) "await must not be called on the UI thread when time is greater than zero.");
        zzab.zza(!this.sZ, (Object) "Result has already been consumed.");
        if (this.tc != null) {
            z = false;
        }
        zzab.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            if (!this.zzale.await(j, timeUnit)) {
                zzaa(Status.st);
            }
        } catch (InterruptedException e) {
            zzaa(Status.sr);
        }
        zzab.zza(isReady(), (Object) "Result is not ready.");
        return get();
    }

    public void cancel() {
        synchronized (this.sT) {
            if (this.zzak || this.sZ) {
                return;
            }
            if (this.tb != null) {
                try {
                    this.tb.cancel();
                } catch (RemoteException e) {
                }
            }
            zze(this.sm);
            this.zzak = true;
            zzd(zzc(Status.su));
        }
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.sT) {
            z = this.zzak;
        }
        return z;
    }

    public final boolean isReady() {
        return this.zzale.getCount() == 0;
    }

    public final void setResultCallback(ResultCallback<? super R> resultCallback) {
        boolean z = true;
        synchronized (this.sT) {
            if (resultCallback == null) {
                this.sX = null;
                return;
            }
            zzab.zza(!this.sZ, (Object) "Result has already been consumed.");
            if (this.tc != null) {
                z = false;
            }
            zzab.zza(z, (Object) "Cannot set callbacks if then() has been called.");
            if (isCanceled()) {
                return;
            }
            if (isReady()) {
                this.sU.zza((ResultCallback) resultCallback, get());
            } else {
                this.sX = resultCallback;
            }
            return;
        }
    }

    public final void setResultCallback(ResultCallback<? super R> resultCallback, long j, TimeUnit timeUnit) {
        boolean z = true;
        synchronized (this.sT) {
            if (resultCallback == null) {
                this.sX = null;
                return;
            }
            zzab.zza(!this.sZ, (Object) "Result has already been consumed.");
            if (this.tc != null) {
                z = false;
            }
            zzab.zza(z, (Object) "Cannot set callbacks if then() has been called.");
            if (isCanceled()) {
                return;
            }
            if (isReady()) {
                this.sU.zza((ResultCallback) resultCallback, get());
            } else {
                this.sX = resultCallback;
                this.sU.zza(this, timeUnit.toMillis(j));
            }
            return;
        }
    }

    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult<S> then;
        boolean z = true;
        zzab.zza(!this.sZ, (Object) "Result has already been consumed.");
        synchronized (this.sT) {
            zzab.zza(this.tc == null, (Object) "Cannot call then() twice.");
            if (this.sX != null) {
                z = false;
            }
            zzab.zza(z, (Object) "Cannot call then() if callbacks are set.");
            this.td = true;
            this.tc = new zzqx(this.sV);
            then = this.tc.then(resultTransform);
            if (isReady()) {
                this.sU.zza(this.tc, get());
            } else {
                this.sX = this.tc;
            }
        }
        return then;
    }

    public final void zza(com.google.android.gms.common.api.PendingResult.zza com_google_android_gms_common_api_PendingResult_zza) {
        boolean z = true;
        zzab.zza(!this.sZ, (Object) "Result has already been consumed.");
        if (com_google_android_gms_common_api_PendingResult_zza == null) {
            z = false;
        }
        zzab.zzb(z, (Object) "Callback cannot be null.");
        synchronized (this.sT) {
            if (isReady()) {
                com_google_android_gms_common_api_PendingResult_zza.zzv(this.sm.getStatus());
            } else {
                this.sW.add(com_google_android_gms_common_api_PendingResult_zza);
            }
        }
    }

    protected final void zza(zzr com_google_android_gms_common_internal_zzr) {
        synchronized (this.sT) {
            this.tb = com_google_android_gms_common_internal_zzr;
        }
    }

    public final void zzaa(Status status) {
        synchronized (this.sT) {
            if (!isReady()) {
                zzc(zzc(status));
                this.ta = true;
            }
        }
    }

    public Integer zzaoj() {
        return null;
    }

    protected void zzaos() {
    }

    public boolean zzaov() {
        boolean isCanceled;
        synchronized (this.sT) {
            if (((GoogleApiClient) this.sV.get()) == null || !this.td) {
                cancel();
            }
            isCanceled = isCanceled();
        }
        return isCanceled;
    }

    public void zzaow() {
        boolean z = this.td || ((Boolean) sS.get()).booleanValue();
        this.td = z;
    }

    boolean zzaox() {
        return false;
    }

    protected abstract R zzc(Status status);

    public final void zzc(R r) {
        boolean z = true;
        synchronized (this.sT) {
            if (this.ta || this.zzak || (isReady() && zzaox())) {
                zze(r);
                return;
            }
            zzab.zza(!isReady(), (Object) "Results have already been set");
            if (this.sZ) {
                z = false;
            }
            zzab.zza(z, (Object) "Result has already been consumed");
            zzd(r);
        }
    }
}
