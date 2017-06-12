package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

@TargetApi(11)
public final class zzql extends Fragment implements zzqk {
    private static WeakHashMap<Activity, WeakReference<zzql>> vn;
    private Map<String, zzqj> vo;
    private Bundle vp;
    private int zzblv;

    /* renamed from: com.google.android.gms.internal.zzql.1 */
    class C02141 implements Runnable {
        final /* synthetic */ zzqj vq;
        final /* synthetic */ zzql vr;
        final /* synthetic */ String zzap;

        C02141(zzql com_google_android_gms_internal_zzql, zzqj com_google_android_gms_internal_zzqj, String str) {
            this.vr = com_google_android_gms_internal_zzql;
            this.vq = com_google_android_gms_internal_zzqj;
            this.zzap = str;
        }

        public void run() {
            if (this.vr.zzblv >= 1) {
                this.vq.onCreate(this.vr.vp != null ? this.vr.vp.getBundle(this.zzap) : null);
            }
            if (this.vr.zzblv >= 2) {
                this.vq.onStart();
            }
            if (this.vr.zzblv >= 3) {
                this.vq.onStop();
            }
        }
    }

    static {
        vn = new WeakHashMap();
    }

    public zzql() {
        this.vo = new ArrayMap();
        this.zzblv = 0;
    }

    private void zzb(String str, @NonNull zzqj com_google_android_gms_internal_zzqj) {
        if (this.zzblv > 0) {
            new Handler(Looper.getMainLooper()).post(new C02141(this, com_google_android_gms_internal_zzqj, str));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.internal.zzql zzt(android.app.Activity r3) {
        /*
        r0 = vn;
        r0 = r0.get(r3);
        r0 = (java.lang.ref.WeakReference) r0;
        if (r0 == 0) goto L_0x0013;
    L_0x000a:
        r0 = r0.get();
        r0 = (com.google.android.gms.internal.zzql) r0;
        if (r0 == 0) goto L_0x0013;
    L_0x0012:
        return r0;
    L_0x0013:
        r0 = r3.getFragmentManager();	 Catch:{ ClassCastException -> 0x0048 }
        r1 = "LifecycleFragmentImpl";
        r0 = r0.findFragmentByTag(r1);	 Catch:{ ClassCastException -> 0x0048 }
        r0 = (com.google.android.gms.internal.zzql) r0;	 Catch:{ ClassCastException -> 0x0048 }
        if (r0 == 0) goto L_0x0027;
    L_0x0021:
        r1 = r0.isRemoving();
        if (r1 == 0) goto L_0x003d;
    L_0x0027:
        r0 = new com.google.android.gms.internal.zzql;
        r0.<init>();
        r1 = r3.getFragmentManager();
        r1 = r1.beginTransaction();
        r2 = "LifecycleFragmentImpl";
        r1 = r1.add(r0, r2);
        r1.commitAllowingStateLoss();
    L_0x003d:
        r1 = vn;
        r2 = new java.lang.ref.WeakReference;
        r2.<init>(r0);
        r1.put(r3, r2);
        goto L_0x0012;
    L_0x0048:
        r0 = move-exception;
        r1 = new java.lang.IllegalStateException;
        r2 = "Fragment with tag LifecycleFragmentImpl is not a LifecycleFragmentImpl";
        r1.<init>(r2, r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzql.zzt(android.app.Activity):com.google.android.gms.internal.zzql");
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        for (zzqj dump : this.vo.values()) {
            dump.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        for (zzqj onActivityResult : this.vo.values()) {
            onActivityResult.onActivityResult(i, i2, intent);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzblv = 1;
        this.vp = bundle;
        for (Entry entry : this.vo.entrySet()) {
            ((zzqj) entry.getValue()).onCreate(bundle != null ? bundle.getBundle((String) entry.getKey()) : null);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            for (Entry entry : this.vo.entrySet()) {
                Bundle bundle2 = new Bundle();
                ((zzqj) entry.getValue()).onSaveInstanceState(bundle2);
                bundle.putBundle((String) entry.getKey(), bundle2);
            }
        }
    }

    public void onStart() {
        super.onStop();
        this.zzblv = 2;
        for (zzqj onStart : this.vo.values()) {
            onStart.onStart();
        }
    }

    public void onStop() {
        super.onStop();
        this.zzblv = 3;
        for (zzqj onStop : this.vo.values()) {
            onStop.onStop();
        }
    }

    public <T extends zzqj> T zza(String str, Class<T> cls) {
        return (zzqj) cls.cast(this.vo.get(str));
    }

    public void zza(String str, @NonNull zzqj com_google_android_gms_internal_zzqj) {
        if (this.vo.containsKey(str)) {
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 59).append("LifecycleCallback with tag ").append(str).append(" already added to this fragment.").toString());
        }
        this.vo.put(str, com_google_android_gms_internal_zzqj);
        zzb(str, com_google_android_gms_internal_zzqj);
    }

    public Activity zzaqt() {
        return getActivity();
    }
}
