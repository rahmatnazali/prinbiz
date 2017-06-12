package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zza<TResult, TContinuationResult> implements zzf<TResult> {
    private final Continuation<TResult, TContinuationResult> aDg;
    private final zzh<TContinuationResult> aDh;
    private final Executor avv;

    /* renamed from: com.google.android.gms.tasks.zza.1 */
    class C02251 implements Runnable {
        final /* synthetic */ Task aDi;
        final /* synthetic */ zza aDj;

        C02251(zza com_google_android_gms_tasks_zza, Task task) {
            this.aDj = com_google_android_gms_tasks_zza;
            this.aDi = task;
        }

        public void run() {
            try {
                this.aDj.aDh.setResult(this.aDj.aDg.then(this.aDi));
            } catch (Exception e) {
                if (e.getCause() instanceof Exception) {
                    this.aDj.aDh.setException((Exception) e.getCause());
                } else {
                    this.aDj.aDh.setException(e);
                }
            } catch (Exception e2) {
                this.aDj.aDh.setException(e2);
            }
        }
    }

    public zza(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzh<TContinuationResult> com_google_android_gms_tasks_zzh_TContinuationResult) {
        this.avv = executor;
        this.aDg = continuation;
        this.aDh = com_google_android_gms_tasks_zzh_TContinuationResult;
    }

    public void cancel() {
        throw new UnsupportedOperationException();
    }

    public void onComplete(@NonNull Task<TResult> task) {
        this.avv.execute(new C02251(this, task));
    }
}
