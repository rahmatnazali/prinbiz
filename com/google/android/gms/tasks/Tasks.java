package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzab;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class Tasks {

    /* renamed from: com.google.android.gms.tasks.Tasks.1 */
    class C02241 implements Runnable {
        final /* synthetic */ zzh aDz;
        final /* synthetic */ Callable zzcky;

        C02241(zzh com_google_android_gms_tasks_zzh, Callable callable) {
            this.aDz = com_google_android_gms_tasks_zzh;
            this.zzcky = callable;
        }

        public void run() {
            try {
                this.aDz.setResult(this.zzcky.call());
            } catch (Exception e) {
                this.aDz.setException(e);
            }
        }
    }

    interface zzb extends OnFailureListener, OnSuccessListener<Object> {
    }

    private static final class zza implements zzb {
        private final CountDownLatch zzale;

        private zza() {
            this.zzale = new CountDownLatch(1);
        }

        public void await() throws InterruptedException {
            this.zzale.await();
        }

        public boolean await(long j, TimeUnit timeUnit) throws InterruptedException {
            return this.zzale.await(j, timeUnit);
        }

        public void onFailure(@NonNull Exception exception) {
            this.zzale.countDown();
        }

        public void onSuccess(Object obj) {
            this.zzale.countDown();
        }
    }

    private static final class zzc implements zzb {
        private final int aDA;
        private int aDB;
        private int aDC;
        private final zzh<Void> aDt;
        private Exception aDy;
        private final Object zzail;

        public zzc(int i, zzh<Void> com_google_android_gms_tasks_zzh_java_lang_Void) {
            this.zzail = new Object();
            this.aDA = i;
            this.aDt = com_google_android_gms_tasks_zzh_java_lang_Void;
        }

        private void zzchp() {
            if (this.aDB + this.aDC != this.aDA) {
                return;
            }
            if (this.aDy == null) {
                this.aDt.setResult(null);
                return;
            }
            zzh com_google_android_gms_tasks_zzh = this.aDt;
            int i = this.aDC;
            com_google_android_gms_tasks_zzh.setException(new ExecutionException(i + " out of " + this.aDA + " underlying tasks failed", this.aDy));
        }

        public void onFailure(@NonNull Exception exception) {
            synchronized (this.zzail) {
                this.aDC++;
                this.aDy = exception;
                zzchp();
            }
        }

        public void onSuccess(Object obj) {
            synchronized (this.zzail) {
                this.aDB++;
                zzchp();
            }
        }
    }

    private Tasks() {
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task) throws ExecutionException, InterruptedException {
        zzab.zzate();
        zzab.zzb((Object) task, (Object) "Task must not be null");
        if (task.isComplete()) {
            return zzb(task);
        }
        Object com_google_android_gms_tasks_Tasks_zza = new zza();
        zza(task, com_google_android_gms_tasks_Tasks_zza);
        com_google_android_gms_tasks_Tasks_zza.await();
        return zzb(task);
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task, long j, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        zzab.zzate();
        zzab.zzb((Object) task, (Object) "Task must not be null");
        zzab.zzb((Object) timeUnit, (Object) "TimeUnit must not be null");
        if (task.isComplete()) {
            return zzb(task);
        }
        Object com_google_android_gms_tasks_Tasks_zza = new zza();
        zza(task, com_google_android_gms_tasks_Tasks_zza);
        if (com_google_android_gms_tasks_Tasks_zza.await(j, timeUnit)) {
            return zzb(task);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }

    public static <TResult> Task<TResult> call(@NonNull Callable<TResult> callable) {
        return call(TaskExecutors.MAIN_THREAD, callable);
    }

    public static <TResult> Task<TResult> call(@NonNull Executor executor, @NonNull Callable<TResult> callable) {
        zzab.zzb((Object) executor, (Object) "Executor must not be null");
        zzab.zzb((Object) callable, (Object) "Callback must not be null");
        Task com_google_android_gms_tasks_zzh = new zzh();
        executor.execute(new C02241(com_google_android_gms_tasks_zzh, callable));
        return com_google_android_gms_tasks_zzh;
    }

    public static <TResult> Task<TResult> forException(@NonNull Exception exception) {
        Task com_google_android_gms_tasks_zzh = new zzh();
        com_google_android_gms_tasks_zzh.setException(exception);
        return com_google_android_gms_tasks_zzh;
    }

    public static <TResult> Task<TResult> forResult(TResult tResult) {
        Task com_google_android_gms_tasks_zzh = new zzh();
        com_google_android_gms_tasks_zzh.setResult(tResult);
        return com_google_android_gms_tasks_zzh;
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> collection) {
        if (collection.isEmpty()) {
            return forResult(null);
        }
        for (Task task : collection) {
            if (task == null) {
                throw new NullPointerException("null tasks are not accepted");
            }
        }
        Task com_google_android_gms_tasks_zzh = new zzh();
        zzb com_google_android_gms_tasks_Tasks_zzc = new zzc(collection.size(), com_google_android_gms_tasks_zzh);
        for (Task task2 : collection) {
            zza(task2, com_google_android_gms_tasks_Tasks_zzc);
        }
        return com_google_android_gms_tasks_zzh;
    }

    public static Task<Void> whenAll(Task<?>... taskArr) {
        return taskArr.length == 0 ? forResult(null) : whenAll(Arrays.asList(taskArr));
    }

    private static void zza(Task<?> task, zzb com_google_android_gms_tasks_Tasks_zzb) {
        task.addOnSuccessListener(TaskExecutors.aDu, (OnSuccessListener) com_google_android_gms_tasks_Tasks_zzb);
        task.addOnFailureListener(TaskExecutors.aDu, (OnFailureListener) com_google_android_gms_tasks_Tasks_zzb);
    }

    private static <TResult> TResult zzb(Task<TResult> task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        throw new ExecutionException(task.getException());
    }
}
