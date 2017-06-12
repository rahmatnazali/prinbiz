package com.google.android.gms.internal;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;

public abstract class zzpn extends zzqj implements OnCancelListener {
    protected boolean mStarted;
    protected boolean sL;
    private ConnectionResult sM;
    private int sN;
    private final Handler sO;
    protected final GoogleApiAvailability sh;

    private class zza implements Runnable {
        final /* synthetic */ zzpn sP;

        /* renamed from: com.google.android.gms.internal.zzpn.zza.1 */
        class C07061 extends com.google.android.gms.internal.zzqe.zza {
            final /* synthetic */ Dialog sQ;
            final /* synthetic */ zza sR;

            C07061(zza com_google_android_gms_internal_zzpn_zza, Dialog dialog) {
                this.sR = com_google_android_gms_internal_zzpn_zza;
                this.sQ = dialog;
            }

            public void zzaou() {
                this.sR.sP.zzaot();
                if (this.sQ.isShowing()) {
                    this.sQ.dismiss();
                }
            }
        }

        private zza(zzpn com_google_android_gms_internal_zzpn) {
            this.sP = com_google_android_gms_internal_zzpn;
        }

        @MainThread
        public void run() {
            if (!this.sP.mStarted) {
                return;
            }
            if (this.sP.sM.hasResolution()) {
                this.sP.vm.startActivityForResult(GoogleApiActivity.zzb(this.sP.getActivity(), this.sP.sM.getResolution(), this.sP.sN, false), 1);
            } else if (this.sP.sh.isUserResolvableError(this.sP.sM.getErrorCode())) {
                this.sP.sh.zza(this.sP.getActivity(), this.sP.vm, this.sP.sM.getErrorCode(), 2, this.sP);
            } else if (this.sP.sM.getErrorCode() == 18) {
                this.sP.sh.zza(this.sP.getActivity().getApplicationContext(), new C07061(this, this.sP.sh.zza(this.sP.getActivity(), this.sP)));
            } else {
                this.sP.zza(this.sP.sM, this.sP.sN);
            }
        }
    }

    protected zzpn(zzqk com_google_android_gms_internal_zzqk) {
        this(com_google_android_gms_internal_zzqk, GoogleApiAvailability.getInstance());
    }

    zzpn(zzqk com_google_android_gms_internal_zzqk, GoogleApiAvailability googleApiAvailability) {
        super(com_google_android_gms_internal_zzqk);
        this.sN = -1;
        this.sO = new Handler(Looper.getMainLooper());
        this.sh = googleApiAvailability;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r6, int r7, android.content.Intent r8) {
        /*
        r5 = this;
        r4 = 18;
        r2 = 13;
        r0 = 1;
        r1 = 0;
        switch(r6) {
            case 1: goto L_0x0027;
            case 2: goto L_0x0010;
            default: goto L_0x0009;
        };
    L_0x0009:
        r0 = r1;
    L_0x000a:
        if (r0 == 0) goto L_0x003d;
    L_0x000c:
        r5.zzaot();
    L_0x000f:
        return;
    L_0x0010:
        r2 = r5.sh;
        r3 = r5.getActivity();
        r2 = r2.isGooglePlayServicesAvailable(r3);
        if (r2 != 0) goto L_0x0047;
    L_0x001c:
        r1 = r5.sM;
        r1 = r1.getErrorCode();
        if (r1 != r4) goto L_0x000a;
    L_0x0024:
        if (r2 != r4) goto L_0x000a;
    L_0x0026:
        goto L_0x000f;
    L_0x0027:
        r3 = -1;
        if (r7 == r3) goto L_0x000a;
    L_0x002a:
        if (r7 != 0) goto L_0x0009;
    L_0x002c:
        if (r8 == 0) goto L_0x0045;
    L_0x002e:
        r0 = "<<ResolutionFailureErrorDetail>>";
        r0 = r8.getIntExtra(r0, r2);
    L_0x0034:
        r2 = new com.google.android.gms.common.ConnectionResult;
        r3 = 0;
        r2.<init>(r0, r3);
        r5.sM = r2;
        goto L_0x0009;
    L_0x003d:
        r0 = r5.sM;
        r1 = r5.sN;
        r5.zza(r0, r1);
        goto L_0x000f;
    L_0x0045:
        r0 = r2;
        goto L_0x0034;
    L_0x0047:
        r0 = r1;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzpn.onActivityResult(int, int, android.content.Intent):void");
    }

    public void onCancel(DialogInterface dialogInterface) {
        zza(new ConnectionResult(13, null), this.sN);
        zzaot();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.sL = bundle.getBoolean("resolving_error", false);
            if (this.sL) {
                this.sN = bundle.getInt("failed_client_id", -1);
                this.sM = new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution"));
            }
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("resolving_error", this.sL);
        if (this.sL) {
            bundle.putInt("failed_client_id", this.sN);
            bundle.putInt("failed_status", this.sM.getErrorCode());
            bundle.putParcelable("failed_resolution", this.sM.getResolution());
        }
    }

    public void onStart() {
        super.onStart();
        this.mStarted = true;
    }

    public void onStop() {
        super.onStop();
        this.mStarted = false;
    }

    protected abstract void zza(ConnectionResult connectionResult, int i);

    protected abstract void zzaoo();

    protected void zzaot() {
        this.sN = -1;
        this.sL = false;
        this.sM = null;
        zzaoo();
    }

    public void zzb(ConnectionResult connectionResult, int i) {
        if (!this.sL) {
            this.sL = true;
            this.sN = i;
            this.sM = connectionResult;
            this.sO.post(new zza());
        }
    }
}
