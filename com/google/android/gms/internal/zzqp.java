package com.google.android.gms.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

public class zzqp extends zzpn {
    private TaskCompletionSource<Void> sA;

    private zzqp(zzqk com_google_android_gms_internal_zzqk) {
        super(com_google_android_gms_internal_zzqk);
        this.sA = new TaskCompletionSource();
        this.vm.zza("GmsAvailabilityHelper", (zzqj) this);
    }

    public static zzqp zzu(Activity activity) {
        zzqk zzs = zzqj.zzs(activity);
        zzqp com_google_android_gms_internal_zzqp = (zzqp) zzs.zza("GmsAvailabilityHelper", zzqp.class);
        if (com_google_android_gms_internal_zzqp == null) {
            return new zzqp(zzs);
        }
        if (!com_google_android_gms_internal_zzqp.sA.getTask().isComplete()) {
            return com_google_android_gms_internal_zzqp;
        }
        com_google_android_gms_internal_zzqp.sA = new TaskCompletionSource();
        return com_google_android_gms_internal_zzqp;
    }

    public Task<Void> getTask() {
        return this.sA.getTask();
    }

    public void onStop() {
        super.onStop();
        this.sA.setException(new CancellationException());
    }

    protected void zza(ConnectionResult connectionResult, int i) {
        this.sA.setException(new Exception());
    }

    protected void zzaoo() {
        int isGooglePlayServicesAvailable = this.sh.isGooglePlayServicesAvailable(this.vm.zzaqt());
        if (isGooglePlayServicesAvailable == 0) {
            this.sA.setResult(null);
        } else {
            zzk(new ConnectionResult(isGooglePlayServicesAvailable, null));
        }
    }

    public void zzk(ConnectionResult connectionResult) {
        zzb(connectionResult, 0);
    }
}
