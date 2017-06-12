package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.internal.zzvu;
import com.google.android.gms.internal.zzvv;
import com.google.android.gms.signin.internal.zze.zza;

public class zzg extends zzk<zze> implements zzvu {
    private final boolean aub;
    private final Bundle auc;
    private final com.google.android.gms.common.internal.zzg tN;
    private Integer ym;

    public zzg(Context context, Looper looper, boolean z, com.google.android.gms.common.internal.zzg com_google_android_gms_common_internal_zzg, Bundle bundle, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, com_google_android_gms_common_internal_zzg, connectionCallbacks, onConnectionFailedListener);
        this.aub = z;
        this.tN = com_google_android_gms_common_internal_zzg;
        this.auc = bundle;
        this.ym = com_google_android_gms_common_internal_zzg.zzasq();
    }

    public zzg(Context context, Looper looper, boolean z, com.google.android.gms.common.internal.zzg com_google_android_gms_common_internal_zzg, zzvv com_google_android_gms_internal_zzvv, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, z, com_google_android_gms_common_internal_zzg, zza(com_google_android_gms_common_internal_zzg), connectionCallbacks, onConnectionFailedListener);
    }

    public static Bundle zza(com.google.android.gms.common.internal.zzg com_google_android_gms_common_internal_zzg) {
        zzvv zzasp = com_google_android_gms_common_internal_zzg.zzasp();
        Integer zzasq = com_google_android_gms_common_internal_zzg.zzasq();
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", com_google_android_gms_common_internal_zzg.getAccount());
        if (zzasq != null) {
            bundle.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", zzasq.intValue());
        }
        if (zzasp != null) {
            bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", zzasp.zzbzp());
            bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", zzasp.zzafr());
            bundle.putString("com.google.android.gms.signin.internal.serverClientId", zzasp.zzafu());
            bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
            bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", zzasp.zzaft());
            bundle.putString("com.google.android.gms.signin.internal.hostedDomain", zzasp.zzafv());
            bundle.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", zzasp.zzbzq());
            if (zzasp.zzbzr() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.authApiSignInModuleVersion", zzasp.zzbzr().longValue());
            }
            if (zzasp.zzbzs() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.realClientLibraryVersion", zzasp.zzbzs().longValue());
            }
        }
        return bundle;
    }

    private ResolveAccountRequest zzbzx() {
        Account zzary = this.tN.zzary();
        GoogleSignInAccount googleSignInAccount = null;
        if ("<<default account>>".equals(zzary.name)) {
            googleSignInAccount = com.google.android.gms.auth.api.signin.internal.zzk.zzbc(getContext()).zzagj();
        }
        return new ResolveAccountRequest(zzary, this.ym.intValue(), googleSignInAccount);
    }

    public void connect() {
        zza(new zzi(this));
    }

    public void zza(zzq com_google_android_gms_common_internal_zzq, boolean z) {
        try {
            ((zze) zzasa()).zza(com_google_android_gms_common_internal_zzq, this.ym.intValue(), z);
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
        }
    }

    public void zza(zzd com_google_android_gms_signin_internal_zzd) {
        zzab.zzb((Object) com_google_android_gms_signin_internal_zzd, (Object) "Expecting a valid ISignInCallbacks");
        try {
            ((zze) zzasa()).zza(new SignInRequest(zzbzx()), com_google_android_gms_signin_internal_zzd);
        } catch (Throwable e) {
            Log.w("SignInClientImpl", "Remote service probably died when signIn is called");
            try {
                com_google_android_gms_signin_internal_zzd.zzb(new SignInResponse(8));
            } catch (RemoteException e2) {
                Log.wtf("SignInClientImpl", "ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", e);
            }
        }
    }

    protected Bundle zzaeu() {
        if (!getContext().getPackageName().equals(this.tN.zzasm())) {
            this.auc.putString("com.google.android.gms.signin.internal.realClientPackageName", this.tN.zzasm());
        }
        return this.auc;
    }

    public boolean zzafk() {
        return this.aub;
    }

    protected /* synthetic */ IInterface zzbb(IBinder iBinder) {
        return zzkw(iBinder);
    }

    public void zzbzo() {
        try {
            ((zze) zzasa()).zzza(this.ym.intValue());
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
        }
    }

    protected zze zzkw(IBinder iBinder) {
        return zza.zzkv(iBinder);
    }

    protected String zzqz() {
        return "com.google.android.gms.signin.service.START";
    }

    protected String zzra() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }
}
