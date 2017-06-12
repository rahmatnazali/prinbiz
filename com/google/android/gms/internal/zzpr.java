package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.zzc;
import java.util.Iterator;

public class zzpr extends zzpn {
    public void onStop() {
        Object obj = null;
        super.onStop();
        Iterator it = obj.iterator();
        while (it.hasNext()) {
            ((zzc) it.next()).release();
        }
        obj.clear();
        obj.zza(this);
    }

    protected void zza(ConnectionResult connectionResult, int i) {
        zzqc com_google_android_gms_internal_zzqc = null;
        com_google_android_gms_internal_zzqc.zza(connectionResult, i);
    }

    protected void zzaoo() {
        zzqc com_google_android_gms_internal_zzqc = null;
        com_google_android_gms_internal_zzqc.zzaoo();
    }
}
