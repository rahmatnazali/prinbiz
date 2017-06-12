package com.google.android.gms.internal;

import android.os.Process;

class zzrn implements Runnable {
    private final int mPriority;
    private final Runnable zzw;

    public zzrn(Runnable runnable, int i) {
        this.zzw = runnable;
        this.mPriority = i;
    }

    public void run() {
        Process.setThreadPriority(this.mPriority);
        this.zzw.run();
    }
}
