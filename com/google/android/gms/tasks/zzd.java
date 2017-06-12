package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zzd<TResult> implements zzf<TResult> {
    private OnFailureListener aDn;
    private final Executor avv;
    private final Object zzail;

    /* renamed from: com.google.android.gms.tasks.zzd.1 */
    class C02281 implements Runnable {
        final /* synthetic */ Task aDi;
        final /* synthetic */ zzd aDo;

        C02281(zzd com_google_android_gms_tasks_zzd, Task task) {
            this.aDo = com_google_android_gms_tasks_zzd;
            this.aDi = task;
        }

        public void run() {
            synchronized (this.aDo.zzail) {
                if (this.aDo.aDn != null) {
                    this.aDo.aDn.onFailure(this.aDi.getException());
                }
            }
        }
    }

    public zzd(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzail = new Object();
        this.avv = executor;
        this.aDn = onFailureListener;
    }

    public void cancel() {
        synchronized (this.zzail) {
            this.aDn = null;
        }
    }

    public void onComplete(@NonNull Task<TResult> task) {
        if (!task.isSuccessful()) {
            synchronized (this.zzail) {
                if (this.aDn == null) {
                    return;
                }
                this.avv.execute(new C02281(this, task));
            }
        }
    }
}
