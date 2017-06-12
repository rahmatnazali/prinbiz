package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.internal.zzpm.zza;

public class zzpv implements zzpz {
    private final zzqa tw;
    private boolean tx;

    /* renamed from: com.google.android.gms.internal.zzpv.1 */
    class C07071 extends zza {
        final /* synthetic */ zzpv ty;

        C07071(zzpv com_google_android_gms_internal_zzpv, zzpz com_google_android_gms_internal_zzpz) {
            this.ty = com_google_android_gms_internal_zzpv;
            super(com_google_android_gms_internal_zzpz);
        }

        public void zzapl() {
            this.ty.onConnectionSuspended(1);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpv.2 */
    class C07082 extends zza {
        final /* synthetic */ zzpv ty;

        C07082(zzpv com_google_android_gms_internal_zzpv, zzpz com_google_android_gms_internal_zzpz) {
            this.ty = com_google_android_gms_internal_zzpv;
            super(com_google_android_gms_internal_zzpz);
        }

        public void zzapl() {
            this.ty.tw.uB.zzm(null);
        }
    }

    public zzpv(zzqa com_google_android_gms_internal_zzqa) {
        this.tx = false;
        this.tw = com_google_android_gms_internal_zzqa;
    }

    private <A extends zzb> void zzf(zza<? extends Result, A> com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__A) throws DeadObjectException {
        this.tw.th.uo.zzg(com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__A);
        zzb zzb = this.tw.th.zzb(com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__A.zzans());
        if (zzb.isConnected() || !this.tw.ux.containsKey(com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__A.zzans())) {
            if (zzb instanceof zzah) {
                zzb = ((zzah) zzb).zzatn();
            }
            com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__A.zzb(zzb);
            return;
        }
        com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__A.zzz(new Status(17));
    }

    public void begin() {
    }

    public void connect() {
        if (this.tx) {
            this.tx = false;
            this.tw.zza(new C07082(this, this));
        }
    }

    public boolean disconnect() {
        if (this.tx) {
            return false;
        }
        if (this.tw.th.zzapx()) {
            this.tx = true;
            for (zzqx zzaqx : this.tw.th.un) {
                zzaqx.zzaqx();
            }
            return false;
        }
        this.tw.zzi(null);
        return true;
    }

    public void onConnected(Bundle bundle) {
    }

    public void onConnectionSuspended(int i) {
        this.tw.zzi(null);
        this.tw.uB.zzc(i, this.tx);
    }

    public void zza(ConnectionResult connectionResult, Api<?> api, int i) {
    }

    void zzapk() {
        if (this.tx) {
            this.tx = false;
            this.tw.th.uo.release();
            disconnect();
        }
    }

    public <A extends zzb, R extends Result, T extends zza<R, A>> T zzc(T t) {
        return zzd(t);
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzd(T t) {
        try {
            zzf(t);
        } catch (DeadObjectException e) {
            this.tw.zza(new C07071(this, this));
        }
        return t;
    }
}
