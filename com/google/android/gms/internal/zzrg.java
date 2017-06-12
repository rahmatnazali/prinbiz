package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzpm.zzb;

public final class zzrg implements zzrf {

    private static class zza extends zzrd {
        private final zzb<Status> zv;

        public zza(zzb<Status> com_google_android_gms_internal_zzpm_zzb_com_google_android_gms_common_api_Status) {
            this.zv = com_google_android_gms_internal_zzpm_zzb_com_google_android_gms_common_api_Status;
        }

        public void zzgn(int i) throws RemoteException {
            this.zv.setResult(new Status(i));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzrg.1 */
    class C08391 extends zza {
        final /* synthetic */ zzrg zu;

        C08391(zzrg com_google_android_gms_internal_zzrg, GoogleApiClient googleApiClient) {
            this.zu = com_google_android_gms_internal_zzrg;
            super(googleApiClient);
        }

        protected void zza(zzri com_google_android_gms_internal_zzri) throws RemoteException {
            ((zzrk) com_google_android_gms_internal_zzri.zzasa()).zza(new zza(this));
        }
    }

    public PendingResult<Status> zzg(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new C08391(this, googleApiClient));
    }
}
