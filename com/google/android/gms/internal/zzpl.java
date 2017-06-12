package com.google.android.gms.internal;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.common.api.zzb;
import java.util.Set;

public final class zzpl extends zzpo<zzb> {
    private int sH;
    private boolean sI;

    private void zza(ConnectionResult connectionResult) {
        ArrayMap arrayMap = null;
        for (int i = 0; i < arrayMap.size(); i++) {
            zza((zzpj) arrayMap.keyAt(i), connectionResult);
        }
    }

    public void zza(zzpj<?> com_google_android_gms_internal_zzpj_, ConnectionResult connectionResult) {
        synchronized (null) {
            ArrayMap arrayMap = null;
            try {
                arrayMap.put(com_google_android_gms_internal_zzpj_, connectionResult);
                this.sH--;
                boolean isSuccess = connectionResult.isSuccess();
                if (!isSuccess) {
                    this.sI = isSuccess;
                }
                if (this.sH == 0) {
                    Status status = this.sI ? new Status(13) : Status.sq;
                    arrayMap = null;
                    zzc(arrayMap.size() == 1 ? new zza(status, null) : new zzb(status, null));
                }
            } finally {
            }
        }
    }

    public Set<zzpj<?>> zzaoq() {
        ArrayMap arrayMap = null;
        return arrayMap.keySet();
    }

    protected /* synthetic */ Result zzc(Status status) {
        return zzy(status);
    }

    protected zzb zzy(Status status) {
        zzb com_google_android_gms_common_api_zzb;
        synchronized (null) {
            try {
                zza(new ConnectionResult(8));
                ArrayMap arrayMap = null;
                if (arrayMap.size() != 1) {
                    com_google_android_gms_common_api_zzb = new zzb(status, null);
                }
            } finally {
            }
        }
        return com_google_android_gms_common_api_zzb;
    }
}
