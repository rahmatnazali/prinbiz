package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzd.zzb;
import com.google.android.gms.common.internal.zzd.zzc;
import com.google.android.gms.common.internal.zzl.zza;
import java.util.Set;

public abstract class zzk<T extends IInterface> extends zzd<T> implements zze, zza {
    private final Account aL;
    private final Set<Scope> dT;
    private final zzg tN;

    /* renamed from: com.google.android.gms.common.internal.zzk.1 */
    class C06581 implements zzb {
        final /* synthetic */ ConnectionCallbacks yC;

        C06581(ConnectionCallbacks connectionCallbacks) {
            this.yC = connectionCallbacks;
        }

        public void onConnected(@Nullable Bundle bundle) {
            this.yC.onConnected(bundle);
        }

        public void onConnectionSuspended(int i) {
            this.yC.onConnectionSuspended(i);
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zzk.2 */
    class C06592 implements zzc {
        final /* synthetic */ OnConnectionFailedListener yD;

        C06592(OnConnectionFailedListener onConnectionFailedListener) {
            this.yD = onConnectionFailedListener;
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            this.yD.onConnectionFailed(connectionResult);
        }
    }

    protected zzk(Context context, Looper looper, int i, zzg com_google_android_gms_common_internal_zzg, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, zzm.zzce(context), GoogleApiAvailability.getInstance(), i, com_google_android_gms_common_internal_zzg, (ConnectionCallbacks) zzab.zzy(connectionCallbacks), (OnConnectionFailedListener) zzab.zzy(onConnectionFailedListener));
    }

    protected zzk(Context context, Looper looper, zzm com_google_android_gms_common_internal_zzm, GoogleApiAvailability googleApiAvailability, int i, zzg com_google_android_gms_common_internal_zzg, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, com_google_android_gms_common_internal_zzm, googleApiAvailability, i, zza(connectionCallbacks), zza(onConnectionFailedListener), com_google_android_gms_common_internal_zzg.zzasn());
        this.tN = com_google_android_gms_common_internal_zzg;
        this.aL = com_google_android_gms_common_internal_zzg.getAccount();
        this.dT = zzb(com_google_android_gms_common_internal_zzg.zzask());
    }

    @Nullable
    private static zzb zza(ConnectionCallbacks connectionCallbacks) {
        return connectionCallbacks == null ? null : new C06581(connectionCallbacks);
    }

    @Nullable
    private static zzc zza(OnConnectionFailedListener onConnectionFailedListener) {
        return onConnectionFailedListener == null ? null : new C06592(onConnectionFailedListener);
    }

    private Set<Scope> zzb(@NonNull Set<Scope> set) {
        Set<Scope> zzc = zzc(set);
        for (Scope contains : zzc) {
            if (!set.contains(contains)) {
                throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        return zzc;
    }

    public final Account getAccount() {
        return this.aL;
    }

    protected final Set<Scope> zzasc() {
        return this.dT;
    }

    protected final zzg zzasv() {
        return this.tN;
    }

    @NonNull
    protected Set<Scope> zzc(@NonNull Set<Scope> set) {
        return set;
    }
}
