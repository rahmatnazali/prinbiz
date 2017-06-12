package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zzc<TResult> implements zzf<TResult> {
    private OnCompleteListener<TResult> aDl;
    private final Executor avv;
    private final Object zzail;

    /* renamed from: com.google.android.gms.tasks.zzc.1 */
    class C02271 implements Runnable {
        final /* synthetic */ Task aDi;
        final /* synthetic */ zzc aDm;

        C02271(zzc com_google_android_gms_tasks_zzc, Task task) {
            this.aDm = com_google_android_gms_tasks_zzc;
            this.aDi = task;
        }

        public void run() {
            synchronized (this.aDm.zzail) {
                if (this.aDm.aDl != null) {
                    this.aDm.aDl.onComplete(this.aDi);
                }
            }
        }
    }

    public zzc(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzail = new Object();
        this.avv = executor;
        this.aDl = onCompleteListener;
    }

    public void cancel() {
        synchronized (this.zzail) {
            this.aDl = null;
        }
    }

    public void onComplete(@NonNull Task<TResult> task) {
        synchronized (this.zzail) {
            if (this.aDl == null) {
                return;
            }
            this.avv.execute(new C02271(this, task));
        }
    }
}
