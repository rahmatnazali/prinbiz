package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Pair;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzpj;
import com.google.android.gms.internal.zzpm.zza;
import com.google.android.gms.internal.zzqc;
import com.google.android.gms.internal.zzqd;
import com.google.android.gms.internal.zzqo;
import com.google.android.gms.internal.zzqw;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class zzc<O extends ApiOptions> {
    private final Context mContext;
    private final int mId;
    private final Api<O> pN;
    private final zzqo rO;
    private final O rP;
    private final zzpj<O> rQ;
    private final zzqc rR;
    private final GoogleApiClient rS;
    private final AtomicBoolean rT;
    private final AtomicInteger rU;
    private final Looper zzahv;

    public zzc(@NonNull Context context, Api<O> api, O o) {
        this(context, api, o, Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper());
    }

    public zzc(@NonNull Context context, Api<O> api, O o, Looper looper) {
        this.rT = new AtomicBoolean(false);
        this.rU = new AtomicInteger(0);
        zzab.zzb((Object) context, (Object) "Null context is not permitted.");
        zzab.zzb((Object) api, (Object) "Api must not be null.");
        zzab.zzb((Object) looper, (Object) "Looper must not be null.");
        this.mContext = context.getApplicationContext();
        this.pN = api;
        this.rP = o;
        this.zzahv = looper;
        this.rO = new zzqo();
        this.rQ = new zzpj(this.pN, this.rP);
        this.rS = new zzqd(this);
        Pair zza = zzqc.zza(this.mContext, this);
        this.rR = (zzqc) zza.first;
        this.mId = ((Integer) zza.second).intValue();
    }

    private <A extends zzb, T extends zza<? extends Result, A>> T zza(int i, @NonNull T t) {
        t.zzaow();
        this.rR.zza(this, i, t);
        return t;
    }

    private <TResult, A extends zzb> Task<TResult> zza(int i, @NonNull zzqw<A, TResult> com_google_android_gms_internal_zzqw_A__TResult) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.rR.zza(this, i, com_google_android_gms_internal_zzqw_A__TResult, taskCompletionSource);
        return taskCompletionSource.getTask();
    }

    public Context getApplicationContext() {
        return this.mContext;
    }

    public int getInstanceId() {
        return this.mId;
    }

    public Looper getLooper() {
        return this.zzahv;
    }

    public void release() {
        boolean z = true;
        if (!this.rT.getAndSet(true)) {
            this.rO.release();
            zzqc com_google_android_gms_internal_zzqc = this.rR;
            int i = this.mId;
            if (this.rU.get() <= 0) {
                z = false;
            }
            com_google_android_gms_internal_zzqc.zzd(i, z);
        }
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zza(@NonNull T t) {
        return zza(0, (zza) t);
    }

    public <TResult, A extends zzb> Task<TResult> zza(zzqw<A, TResult> com_google_android_gms_internal_zzqw_A__TResult) {
        return zza(0, (zzqw) com_google_android_gms_internal_zzqw_A__TResult);
    }

    public void zzanx() {
        this.rU.incrementAndGet();
    }

    public void zzany() {
        if (this.rU.decrementAndGet() == 0 && this.rT.get()) {
            this.rR.zzd(this.mId, false);
        }
    }

    public Api<O> zzanz() {
        return this.pN;
    }

    public O zzaoa() {
        return this.rP;
    }

    public zzpj<O> zzaob() {
        return this.rQ;
    }

    public GoogleApiClient zzaoc() {
        return this.rS;
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzb(@NonNull T t) {
        return zza(1, (zza) t);
    }

    public <TResult, A extends zzb> Task<TResult> zzb(zzqw<A, TResult> com_google_android_gms_internal_zzqw_A__TResult) {
        return zza(1, (zzqw) com_google_android_gms_internal_zzqw_A__TResult);
    }
}
