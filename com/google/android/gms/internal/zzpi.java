package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseException;

public abstract class zzpi {
    public final int iq;
    public final int sx;

    public static final class zza extends zzpi {
        public final com.google.android.gms.internal.zzpm.zza<? extends Result, com.google.android.gms.common.api.Api.zzb> sy;

        public zza(int i, int i2, com.google.android.gms.internal.zzpm.zza<? extends Result, com.google.android.gms.common.api.Api.zzb> com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__com_google_android_gms_common_api_Api_zzb) {
            super(i, i2);
            this.sy = com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__com_google_android_gms_common_api_Api_zzb;
        }

        public boolean cancel() {
            return this.sy.zzaov();
        }

        public void zza(SparseArray<zzqy> sparseArray) {
            zzqy com_google_android_gms_internal_zzqy = (zzqy) sparseArray.get(this.sx);
            if (com_google_android_gms_internal_zzqy != null) {
                com_google_android_gms_internal_zzqy.zzg(this.sy);
            }
        }

        public void zzb(com.google.android.gms.common.api.Api.zzb com_google_android_gms_common_api_Api_zzb) throws DeadObjectException {
            this.sy.zzb(com_google_android_gms_common_api_Api_zzb);
        }

        public void zzx(@NonNull Status status) {
            this.sy.zzz(status);
        }
    }

    public static final class zzb<TResult> extends zzpi {
        private static final Status sB;
        private final TaskCompletionSource<TResult> sA;
        private final zzqw<com.google.android.gms.common.api.Api.zzb, TResult> sz;

        static {
            sB = new Status(8, "Connection to Google Play services was lost while executing the API call.");
        }

        public zzb(int i, int i2, zzqw<com.google.android.gms.common.api.Api.zzb, TResult> com_google_android_gms_internal_zzqw_com_google_android_gms_common_api_Api_zzb__TResult, TaskCompletionSource<TResult> taskCompletionSource) {
            super(i, i2);
            this.sA = taskCompletionSource;
            this.sz = com_google_android_gms_internal_zzqw_com_google_android_gms_common_api_Api_zzb__TResult;
        }

        public void zzb(com.google.android.gms.common.api.Api.zzb com_google_android_gms_common_api_Api_zzb) throws DeadObjectException {
            try {
                this.sz.zza(com_google_android_gms_common_api_Api_zzb, this.sA);
            } catch (DeadObjectException e) {
                zzx(sB);
                throw e;
            } catch (RemoteException e2) {
                zzx(sB);
            }
        }

        public void zzx(@NonNull Status status) {
            if (status.getStatusCode() == 8) {
                this.sA.setException(new FirebaseException(status.getStatusMessage()));
            } else {
                this.sA.setException(new FirebaseApiNotAvailableException(status.getStatusMessage()));
            }
        }
    }

    public zzpi(int i, int i2) {
        this.sx = i;
        this.iq = i2;
    }

    public boolean cancel() {
        return true;
    }

    public void zza(SparseArray<zzqy> sparseArray) {
    }

    public abstract void zzb(com.google.android.gms.common.api.Api.zzb com_google_android_gms_common_api_Api_zzb) throws DeadObjectException;

    public abstract void zzx(@NonNull Status status);
}
