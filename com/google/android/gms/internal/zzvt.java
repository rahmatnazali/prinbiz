package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.signin.internal.zzg;

public final class zzvt {
    public static final Api<zzvv> API;
    public static final Api<zza> Dz;
    public static final zzf<zzg> atP;
    static final com.google.android.gms.common.api.Api.zza<zzg, zza> atQ;
    public static final zzf<zzg> bJ;
    public static final com.google.android.gms.common.api.Api.zza<zzg, zzvv> bK;
    public static final Scope dK;
    public static final Scope dL;

    /* renamed from: com.google.android.gms.internal.zzvt.1 */
    class C08291 extends com.google.android.gms.common.api.Api.zza<zzg, zzvv> {
        C08291() {
        }

        public zzg zza(Context context, Looper looper, com.google.android.gms.common.internal.zzg com_google_android_gms_common_internal_zzg, zzvv com_google_android_gms_internal_zzvv, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzg(context, looper, true, com_google_android_gms_common_internal_zzg, com_google_android_gms_internal_zzvv == null ? zzvv.atR : com_google_android_gms_internal_zzvv, connectionCallbacks, onConnectionFailedListener);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzvt.2 */
    class C08302 extends com.google.android.gms.common.api.Api.zza<zzg, zza> {
        C08302() {
        }

        public zzg zza(Context context, Looper looper, com.google.android.gms.common.internal.zzg com_google_android_gms_common_internal_zzg, zza com_google_android_gms_internal_zzvt_zza, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzg(context, looper, false, com_google_android_gms_common_internal_zzg, com_google_android_gms_internal_zzvt_zza.zzbzn(), connectionCallbacks, onConnectionFailedListener);
        }
    }

    public static class zza implements HasOptions {
        public Bundle zzbzn() {
            return null;
        }
    }

    static {
        bJ = new zzf();
        atP = new zzf();
        bK = new C08291();
        atQ = new C08302();
        dK = new Scope(Scopes.PROFILE);
        dL = new Scope(Scopes.EMAIL);
        API = new Api("SignIn.API", bK, bJ);
        Dz = new Api("SignIn.INTERNAL_API", atQ, atP);
    }
}
