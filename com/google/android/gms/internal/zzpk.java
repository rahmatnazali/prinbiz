package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzab;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzpk extends zzpn {
    private final SparseArray<zza> sC;

    private class zza implements OnConnectionFailedListener {
        public final int sD;
        public final GoogleApiClient sE;
        public final OnConnectionFailedListener sF;
        final /* synthetic */ zzpk sG;

        public zza(zzpk com_google_android_gms_internal_zzpk, int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
            this.sG = com_google_android_gms_internal_zzpk;
            this.sD = i;
            this.sE = googleApiClient;
            this.sF = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.append(str).append("GoogleApiClient #").print(this.sD);
            printWriter.println(":");
            this.sE.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            String valueOf = String.valueOf(connectionResult);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 27).append("beginFailureResolution for ").append(valueOf).toString());
            this.sG.zzb(connectionResult, this.sD);
        }

        public void zzaop() {
            this.sE.unregisterConnectionFailedListener(this);
            this.sE.disconnect();
        }
    }

    private zzpk(zzqk com_google_android_gms_internal_zzqk) {
        super(com_google_android_gms_internal_zzqk);
        this.sC = new SparseArray();
        this.vm.zza("AutoManageHelper", (zzqj) this);
    }

    public static zzpk zza(zzqi com_google_android_gms_internal_zzqi) {
        zzqk zzc = zzqj.zzc(com_google_android_gms_internal_zzqi);
        zzpk com_google_android_gms_internal_zzpk = (zzpk) zzc.zza("AutoManageHelper", zzpk.class);
        return com_google_android_gms_internal_zzpk != null ? com_google_android_gms_internal_zzpk : new zzpk(zzc);
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.sC.size(); i++) {
            ((zza) this.sC.valueAt(i)).dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    public void onStart() {
        super.onStart();
        boolean z = this.mStarted;
        String valueOf = String.valueOf(this.sC);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 14).append("onStart ").append(z).append(" ").append(valueOf).toString());
        if (!this.sL) {
            for (int i = 0; i < this.sC.size(); i++) {
                ((zza) this.sC.valueAt(i)).sE.connect();
            }
        }
    }

    public void onStop() {
        super.onStop();
        for (int i = 0; i < this.sC.size(); i++) {
            ((zza) this.sC.valueAt(i)).sE.disconnect();
        }
    }

    public void zza(int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
        zzab.zzb((Object) googleApiClient, (Object) "GoogleApiClient instance cannot be null");
        zzab.zza(this.sC.indexOfKey(i) < 0, "Already managing a GoogleApiClient with id " + i);
        Log.d("AutoManageHelper", "starting AutoManage for client " + i + " " + this.mStarted + " " + this.sL);
        this.sC.put(i, new zza(this, i, googleApiClient, onConnectionFailedListener));
        if (this.mStarted && !this.sL) {
            String valueOf = String.valueOf(googleApiClient);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 11).append("connecting ").append(valueOf).toString());
            googleApiClient.connect();
        }
    }

    protected void zza(ConnectionResult connectionResult, int i) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zza com_google_android_gms_internal_zzpk_zza = (zza) this.sC.get(i);
        if (com_google_android_gms_internal_zzpk_zza != null) {
            zzfh(i);
            OnConnectionFailedListener onConnectionFailedListener = com_google_android_gms_internal_zzpk_zza.sF;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }

    protected void zzaoo() {
        for (int i = 0; i < this.sC.size(); i++) {
            ((zza) this.sC.valueAt(i)).sE.connect();
        }
    }

    public void zzfh(int i) {
        zza com_google_android_gms_internal_zzpk_zza = (zza) this.sC.get(i);
        this.sC.remove(i);
        if (com_google_android_gms_internal_zzpk_zza != null) {
            com_google_android_gms_internal_zzpk_zza.zzaop();
        }
    }
}
