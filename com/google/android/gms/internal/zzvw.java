package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.common.util.zzw;
import com.google.android.gms.common.util.zzz;

public class zzvw {
    private static boolean DEBUG;
    private static String TAG;
    private static String auf;
    private final String AK;
    private final String AM;
    private WorkSource aaW;
    private final WakeLock aug;
    private final int auh;
    private final String aui;
    private boolean auj;
    private int auk;
    private int aul;
    private final Context mContext;

    static {
        TAG = "WakeLock";
        auf = "*gcore*:";
        DEBUG = false;
    }

    public zzvw(Context context, int i, String str) {
        this(context, i, str, null, context == null ? null : context.getPackageName());
    }

    @SuppressLint({"UnwrappedWakeLock"})
    public zzvw(Context context, int i, String str, String str2, String str3) {
        this(context, i, str, str2, str3, null);
    }

    @SuppressLint({"UnwrappedWakeLock"})
    public zzvw(Context context, int i, String str, String str2, String str3, String str4) {
        this.auj = true;
        zzab.zzh(str, "Wake lock name can NOT be empty");
        this.auh = i;
        this.aui = str2;
        this.AM = str4;
        this.mContext = context.getApplicationContext();
        if (GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE.equals(context.getPackageName())) {
            this.AK = str;
        } else {
            String valueOf = String.valueOf(auf);
            String valueOf2 = String.valueOf(str);
            this.AK = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        }
        this.aug = ((PowerManager) context.getSystemService("power")).newWakeLock(i, str);
        if (zzz.zzco(this.mContext)) {
            if (zzw.zzib(str3)) {
                str3 = context.getPackageName();
            }
            this.aaW = zzz.zzr(context, str3);
            zzc(this.aaW);
        }
    }

    private void zzd(WorkSource workSource) {
        try {
            this.aug.setWorkSource(workSource);
        } catch (IllegalArgumentException e) {
            Log.wtf(TAG, e.toString());
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void zzl(java.lang.String r13, long r14) {
        /*
        r12 = this;
        r0 = r12.zznz(r13);
        r6 = r12.zzp(r13, r0);
        monitor-enter(r12);
        r1 = r12.auj;	 Catch:{ all -> 0x0044 }
        if (r1 == 0) goto L_0x0017;
    L_0x000d:
        r1 = r12.auk;	 Catch:{ all -> 0x0044 }
        r2 = r1 + 1;
        r12.auk = r2;	 Catch:{ all -> 0x0044 }
        if (r1 == 0) goto L_0x001f;
    L_0x0015:
        if (r0 != 0) goto L_0x001f;
    L_0x0017:
        r0 = r12.auj;	 Catch:{ all -> 0x0044 }
        if (r0 != 0) goto L_0x0042;
    L_0x001b:
        r0 = r12.aul;	 Catch:{ all -> 0x0044 }
        if (r0 != 0) goto L_0x0042;
    L_0x001f:
        r1 = com.google.android.gms.common.stats.zzh.zzavi();	 Catch:{ all -> 0x0044 }
        r2 = r12.mContext;	 Catch:{ all -> 0x0044 }
        r0 = r12.aug;	 Catch:{ all -> 0x0044 }
        r3 = com.google.android.gms.common.stats.zzf.zza(r0, r6);	 Catch:{ all -> 0x0044 }
        r4 = 7;
        r5 = r12.AK;	 Catch:{ all -> 0x0044 }
        r7 = r12.AM;	 Catch:{ all -> 0x0044 }
        r8 = r12.auh;	 Catch:{ all -> 0x0044 }
        r0 = r12.aaW;	 Catch:{ all -> 0x0044 }
        r9 = com.google.android.gms.common.util.zzz.zzb(r0);	 Catch:{ all -> 0x0044 }
        r10 = r14;
        r1.zza(r2, r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ all -> 0x0044 }
        r0 = r12.aul;	 Catch:{ all -> 0x0044 }
        r0 = r0 + 1;
        r12.aul = r0;	 Catch:{ all -> 0x0044 }
    L_0x0042:
        monitor-exit(r12);	 Catch:{ all -> 0x0044 }
        return;
    L_0x0044:
        r0 = move-exception;
        monitor-exit(r12);	 Catch:{ all -> 0x0044 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzvw.zzl(java.lang.String, long):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void zzny(java.lang.String r10) {
        /*
        r9 = this;
        r0 = r9.zznz(r10);
        r5 = r9.zzp(r10, r0);
        monitor-enter(r9);
        r1 = r9.auj;	 Catch:{ all -> 0x0045 }
        if (r1 == 0) goto L_0x0017;
    L_0x000d:
        r1 = r9.auk;	 Catch:{ all -> 0x0045 }
        r1 = r1 + -1;
        r9.auk = r1;	 Catch:{ all -> 0x0045 }
        if (r1 == 0) goto L_0x0020;
    L_0x0015:
        if (r0 != 0) goto L_0x0020;
    L_0x0017:
        r0 = r9.auj;	 Catch:{ all -> 0x0045 }
        if (r0 != 0) goto L_0x0043;
    L_0x001b:
        r0 = r9.aul;	 Catch:{ all -> 0x0045 }
        r1 = 1;
        if (r0 != r1) goto L_0x0043;
    L_0x0020:
        r0 = com.google.android.gms.common.stats.zzh.zzavi();	 Catch:{ all -> 0x0045 }
        r1 = r9.mContext;	 Catch:{ all -> 0x0045 }
        r2 = r9.aug;	 Catch:{ all -> 0x0045 }
        r2 = com.google.android.gms.common.stats.zzf.zza(r2, r5);	 Catch:{ all -> 0x0045 }
        r3 = 8;
        r4 = r9.AK;	 Catch:{ all -> 0x0045 }
        r6 = r9.AM;	 Catch:{ all -> 0x0045 }
        r7 = r9.auh;	 Catch:{ all -> 0x0045 }
        r8 = r9.aaW;	 Catch:{ all -> 0x0045 }
        r8 = com.google.android.gms.common.util.zzz.zzb(r8);	 Catch:{ all -> 0x0045 }
        r0.zza(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ all -> 0x0045 }
        r0 = r9.aul;	 Catch:{ all -> 0x0045 }
        r0 = r0 + -1;
        r9.aul = r0;	 Catch:{ all -> 0x0045 }
    L_0x0043:
        monitor-exit(r9);	 Catch:{ all -> 0x0045 }
        return;
    L_0x0045:
        r0 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x0045 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzvw.zzny(java.lang.String):void");
    }

    private boolean zznz(String str) {
        return (TextUtils.isEmpty(str) || str.equals(this.aui)) ? false : true;
    }

    private String zzp(String str, boolean z) {
        return this.auj ? z ? str : this.aui : this.aui;
    }

    public void acquire(long j) {
        if (!zzs.zzavq() && this.auj) {
            String str = TAG;
            String str2 = "Do not acquire with timeout on reference counted WakeLocks before ICS. wakelock: ";
            String valueOf = String.valueOf(this.AK);
            Log.wtf(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
        zzl(null, j);
        this.aug.acquire(j);
    }

    public boolean isHeld() {
        return this.aug.isHeld();
    }

    public void release() {
        zzny(null);
        this.aug.release();
    }

    public void setReferenceCounted(boolean z) {
        this.aug.setReferenceCounted(z);
        this.auj = z;
    }

    public void zzc(WorkSource workSource) {
        if (workSource != null && zzz.zzco(this.mContext)) {
            if (this.aaW != null) {
                this.aaW.add(workSource);
            } else {
                this.aaW = workSource;
            }
            zzd(this.aaW);
        }
    }
}
