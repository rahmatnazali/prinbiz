package com.google.android.gms.gcm;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.CallSuper;
import android.util.Log;
import java.util.HashSet;
import java.util.Set;

public abstract class GcmTaskService extends Service {
    public static final String SERVICE_ACTION_EXECUTE_TASK = "com.google.android.gms.gcm.ACTION_TASK_READY";
    public static final String SERVICE_ACTION_INITIALIZE = "com.google.android.gms.gcm.SERVICE_ACTION_INITIALIZE";
    public static final String SERVICE_PERMISSION = "com.google.android.gms.permission.BIND_NETWORK_TASK_SERVICE";
    private final Set<String> Zi;
    private int Zj;

    private class zza extends Thread {
        private final zzb Zk;
        final /* synthetic */ GcmTaskService Zl;
        private final Bundle mExtras;
        private final String mTag;

        zza(GcmTaskService gcmTaskService, String str, IBinder iBinder, Bundle bundle) {
            this.Zl = gcmTaskService;
            super(String.valueOf(str).concat(" GCM Task"));
            this.mTag = str;
            this.Zk = com.google.android.gms.gcm.zzb.zza.zzgm(iBinder);
            this.mExtras = bundle;
        }

        public void run() {
            Process.setThreadPriority(10);
            try {
                this.Zk.zzsn(this.Zl.onRunTask(new TaskParams(this.mTag, this.mExtras)));
            } catch (RemoteException e) {
                String str = "GcmTaskService";
                String str2 = "Error reporting result of operation to scheduler for ";
                String valueOf = String.valueOf(this.mTag);
                Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            } finally {
                this.Zl.zzkc(this.mTag);
            }
        }
    }

    public GcmTaskService() {
        this.Zi = new HashSet();
    }

    private void zzkc(String str) {
        synchronized (this.Zi) {
            this.Zi.remove(str);
            if (this.Zi.size() == 0) {
                stopSelf(this.Zj);
            }
        }
    }

    private void zzsm(int i) {
        synchronized (this.Zi) {
            this.Zj = i;
            if (this.Zi.size() == 0) {
                stopSelf(this.Zj);
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onInitializeTasks() {
    }

    public abstract int onRunTask(TaskParams taskParams);

    @CallSuper
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            zzsm(i2);
        } else {
            try {
                intent.setExtrasClassLoader(PendingCallback.class.getClassLoader());
                String action = intent.getAction();
                if (SERVICE_ACTION_EXECUTE_TASK.equals(action)) {
                    String stringExtra = intent.getStringExtra("tag");
                    Parcelable parcelableExtra = intent.getParcelableExtra("callback");
                    Bundle bundle = (Bundle) intent.getParcelableExtra("extras");
                    if (parcelableExtra == null || !(parcelableExtra instanceof PendingCallback)) {
                        String valueOf = String.valueOf(getPackageName());
                        Log.e("GcmTaskService", new StringBuilder((String.valueOf(valueOf).length() + 47) + String.valueOf(stringExtra).length()).append(valueOf).append(" ").append(stringExtra).append(": Could not process request, invalid callback.").toString());
                    } else {
                        synchronized (this.Zi) {
                            this.Zi.add(stringExtra);
                        }
                        new zza(this, stringExtra, ((PendingCallback) parcelableExtra).getIBinder(), bundle).start();
                    }
                } else if (SERVICE_ACTION_INITIALIZE.equals(action)) {
                    onInitializeTasks();
                } else {
                    Log.e("GcmTaskService", new StringBuilder(String.valueOf(action).length() + 37).append("Unknown action received ").append(action).append(", terminating").toString());
                }
                zzsm(i2);
            } finally {
                zzsm(i2);
            }
        }
        return 2;
    }
}
