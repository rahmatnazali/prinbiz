package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzqj;
import com.google.android.gms.internal.zzqk;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

final class zzh<TResult> extends Task<TResult> {
    private final zzg<TResult> aDv;
    private boolean aDw;
    private TResult aDx;
    private Exception aDy;
    private final Object zzail;

    private static class zza extends zzqj {
        private final List<WeakReference<zzf<?>>> mListeners;

        private zza(zzqk com_google_android_gms_internal_zzqk) {
            super(com_google_android_gms_internal_zzqk);
            this.mListeners = new ArrayList();
            this.vm.zza("TaskOnStopCallback", (zzqj) this);
        }

        public static zza zzv(Activity activity) {
            zzqk zzs = zzqj.zzs(activity);
            zza com_google_android_gms_tasks_zzh_zza = (zza) zzs.zza("TaskOnStopCallback", zza.class);
            return com_google_android_gms_tasks_zzh_zza == null ? new zza(zzs) : com_google_android_gms_tasks_zzh_zza;
        }

        @MainThread
        public void onStop() {
            synchronized (this.mListeners) {
                for (WeakReference weakReference : this.mListeners) {
                    zzf com_google_android_gms_tasks_zzf = (zzf) weakReference.get();
                    if (com_google_android_gms_tasks_zzf != null) {
                        com_google_android_gms_tasks_zzf.cancel();
                    }
                }
                this.mListeners.clear();
            }
        }

        public <T> void zzb(zzf<T> com_google_android_gms_tasks_zzf_T) {
            synchronized (this.mListeners) {
                this.mListeners.add(new WeakReference(com_google_android_gms_tasks_zzf_T));
            }
        }
    }

    zzh() {
        this.zzail = new Object();
        this.aDv = new zzg();
    }

    private void zzchm() {
        zzab.zza(this.aDw, (Object) "Task is not yet complete");
    }

    private void zzchn() {
        zzab.zza(!this.aDw, (Object) "Task is already complete");
    }

    private void zzcho() {
        synchronized (this.zzail) {
            if (this.aDw) {
                this.aDv.zza((Task) this);
                return;
            }
        }
    }

    @NonNull
    public Task<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zzf com_google_android_gms_tasks_zzc = new zzc(TaskExecutors.MAIN_THREAD, onCompleteListener);
        this.aDv.zza(com_google_android_gms_tasks_zzc);
        zza.zzv(activity).zzb(com_google_android_gms_tasks_zzc);
        zzcho();
        return this;
    }

    @NonNull
    public Task<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        return addOnCompleteListener(TaskExecutors.MAIN_THREAD, (OnCompleteListener) onCompleteListener);
    }

    @NonNull
    public Task<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.aDv.zza(new zzc(executor, onCompleteListener));
        zzcho();
        return this;
    }

    @NonNull
    public Task<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        zzf com_google_android_gms_tasks_zzd = new zzd(TaskExecutors.MAIN_THREAD, onFailureListener);
        this.aDv.zza(com_google_android_gms_tasks_zzd);
        zza.zzv(activity).zzb(com_google_android_gms_tasks_zzd);
        zzcho();
        return this;
    }

    @NonNull
    public Task<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @NonNull
    public Task<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.aDv.zza(new zzd(executor, onFailureListener));
        zzcho();
        return this;
    }

    @NonNull
    public Task<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzf com_google_android_gms_tasks_zze = new zze(TaskExecutors.MAIN_THREAD, onSuccessListener);
        this.aDv.zza(com_google_android_gms_tasks_zze);
        zza.zzv(activity).zzb(com_google_android_gms_tasks_zze);
        zzcho();
        return this;
    }

    @NonNull
    public Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        return addOnSuccessListener(TaskExecutors.MAIN_THREAD, (OnSuccessListener) onSuccessListener);
    }

    @NonNull
    public Task<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.aDv.zza(new zze(executor, onSuccessListener));
        zzcho();
        return this;
    }

    @NonNull
    public <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        Task com_google_android_gms_tasks_zzh = new zzh();
        this.aDv.zza(new zza(executor, continuation, com_google_android_gms_tasks_zzh));
        zzcho();
        return com_google_android_gms_tasks_zzh;
    }

    @NonNull
    public <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        Task com_google_android_gms_tasks_zzh = new zzh();
        this.aDv.zza(new zzb(executor, continuation, com_google_android_gms_tasks_zzh));
        zzcho();
        return com_google_android_gms_tasks_zzh;
    }

    @Nullable
    public Exception getException() {
        Exception exception;
        synchronized (this.zzail) {
            exception = this.aDy;
        }
        return exception;
    }

    public TResult getResult() {
        TResult tResult;
        synchronized (this.zzail) {
            zzchm();
            if (this.aDy != null) {
                throw new RuntimeExecutionException(this.aDy);
            }
            tResult = this.aDx;
        }
        return tResult;
    }

    public <X extends Throwable> TResult getResult(@NonNull Class<X> cls) throws Throwable {
        TResult tResult;
        synchronized (this.zzail) {
            zzchm();
            if (cls.isInstance(this.aDy)) {
                throw ((Throwable) cls.cast(this.aDy));
            } else if (this.aDy != null) {
                throw new RuntimeExecutionException(this.aDy);
            } else {
                tResult = this.aDx;
            }
        }
        return tResult;
    }

    public boolean isComplete() {
        boolean z;
        synchronized (this.zzail) {
            z = this.aDw;
        }
        return z;
    }

    public boolean isSuccessful() {
        boolean z;
        synchronized (this.zzail) {
            z = this.aDw && this.aDy == null;
        }
        return z;
    }

    public void setException(@NonNull Exception exception) {
        zzab.zzb((Object) exception, (Object) "Exception must not be null");
        synchronized (this.zzail) {
            zzchn();
            this.aDw = true;
            this.aDy = exception;
        }
        this.aDv.zza((Task) this);
    }

    public void setResult(TResult tResult) {
        synchronized (this.zzail) {
            zzchn();
            this.aDw = true;
            this.aDx = tResult;
        }
        this.aDv.zza((Task) this);
    }
}
