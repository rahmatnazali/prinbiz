package com.google.android.gms.internal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class zzqb {
    private static final ExecutorService uE;

    static {
        uE = Executors.newFixedThreadPool(2, new zzrm("GAC_Executor"));
    }

    public static ExecutorService zzaqc() {
        return uE;
    }
}
