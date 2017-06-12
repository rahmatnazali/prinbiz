package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzab;

public final class zzqn<L> {
    private volatile L mListener;
    private final zza vs;

    private final class zza extends Handler {
        final /* synthetic */ zzqn vt;

        public zza(zzqn com_google_android_gms_internal_zzqn, Looper looper) {
            this.vt = com_google_android_gms_internal_zzqn;
            super(looper);
        }

        public void handleMessage(Message message) {
            boolean z = true;
            if (message.what != 1) {
                z = false;
            }
            zzab.zzbo(z);
            this.vt.zzb((zzb) message.obj);
        }
    }

    public interface zzb<L> {
        void zzapj();

        void zzt(L l);
    }

    zzqn(Looper looper, L l) {
        this.vs = new zza(this, looper);
        this.mListener = zzab.zzb((Object) l, (Object) "Listener must not be null");
    }

    public void clear() {
        this.mListener = null;
    }

    public void zza(zzb<? super L> com_google_android_gms_internal_zzqn_zzb__super_L) {
        zzab.zzb((Object) com_google_android_gms_internal_zzqn_zzb__super_L, (Object) "Notifier must not be null");
        this.vs.sendMessage(this.vs.obtainMessage(1, com_google_android_gms_internal_zzqn_zzb__super_L));
    }

    void zzb(zzb<? super L> com_google_android_gms_internal_zzqn_zzb__super_L) {
        Object obj = this.mListener;
        if (obj == null) {
            com_google_android_gms_internal_zzqn_zzb__super_L.zzapj();
            return;
        }
        try {
            com_google_android_gms_internal_zzqn_zzb__super_L.zzt(obj);
        } catch (RuntimeException e) {
            com_google_android_gms_internal_zzqn_zzb__super_L.zzapj();
            throw e;
        }
    }
}
