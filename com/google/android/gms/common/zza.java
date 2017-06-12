package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzab;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class zza implements ServiceConnection {
    boolean qZ;
    private final BlockingQueue<IBinder> ra;

    public zza() {
        this.qZ = false;
        this.ra = new LinkedBlockingQueue();
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.ra.add(iBinder);
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }

    public IBinder zza(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        zzab.zzhj("BlockingServiceConnection.getServiceWithTimeout() called on main thread");
        if (this.qZ) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.qZ = true;
        IBinder iBinder = (IBinder) this.ra.poll(j, timeUnit);
        if (iBinder != null) {
            return iBinder;
        }
        throw new TimeoutException("Timed out waiting for the service connection");
    }

    public IBinder zzanf() throws InterruptedException {
        zzab.zzhj("BlockingServiceConnection.getService() called on main thread");
        if (this.qZ) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.qZ = true;
        return (IBinder) this.ra.take();
    }
}
