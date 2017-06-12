package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zztv.zzd;
import java.util.ArrayList;
import java.util.Collection;

public class zztw {
    private final Collection<zztv> zzaxr;
    private final Collection<zzd> zzaxs;
    private final Collection<zzd> zzaxt;

    public zztw() {
        this.zzaxr = new ArrayList();
        this.zzaxs = new ArrayList();
        this.zzaxt = new ArrayList();
    }

    public static void initialize(Context context) {
        zztz.zzbeu().initialize(context);
    }

    public void zza(zztv com_google_android_gms_internal_zztv) {
        this.zzaxr.add(com_google_android_gms_internal_zztv);
    }
}
