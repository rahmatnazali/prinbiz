package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.signin.internal.SignInResponse;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import org.apache.commons.net.ftp.FTPClient;

public class zzpw implements zzpz {
    private final Context mContext;
    private final com.google.android.gms.common.api.Api.zza<? extends zzvu, zzvv> si;
    private ConnectionResult tA;
    private int tB;
    private int tC;
    private int tD;
    private final Bundle tE;
    private final Set<com.google.android.gms.common.api.Api.zzc> tF;
    private zzvu tG;
    private int tH;
    private boolean tI;
    private boolean tJ;
    private zzq tK;
    private boolean tL;
    private boolean tM;
    private final zzg tN;
    private final Map<Api<?>, Integer> tO;
    private ArrayList<Future<?>> tP;
    private final Lock tr;
    private final zzqa tw;
    private final com.google.android.gms.common.zzc tz;

    /* renamed from: com.google.android.gms.internal.zzpw.1 */
    class C02131 implements Runnable {
        final /* synthetic */ zzpw tQ;

        C02131(zzpw com_google_android_gms_internal_zzpw) {
            this.tQ = com_google_android_gms_internal_zzpw;
        }

        public void run() {
            this.tQ.tz.zzbp(this.tQ.mContext);
        }
    }

    private abstract class zzf implements Runnable {
        final /* synthetic */ zzpw tQ;

        private zzf(zzpw com_google_android_gms_internal_zzpw) {
            this.tQ = com_google_android_gms_internal_zzpw;
        }

        @WorkerThread
        public void run() {
            this.tQ.tr.lock();
            try {
                if (!Thread.interrupted()) {
                    zzapl();
                    this.tQ.tr.unlock();
                }
            } catch (RuntimeException e) {
                this.tQ.tw.zza(e);
            } finally {
                this.tQ.tr.unlock();
            }
        }

        @WorkerThread
        protected abstract void zzapl();
    }

    private static class zza implements com.google.android.gms.common.internal.zzd.zzf {
        private final Api<?> pN;
        private final WeakReference<zzpw> tR;
        private final int tf;

        public zza(zzpw com_google_android_gms_internal_zzpw, Api<?> api, int i) {
            this.tR = new WeakReference(com_google_android_gms_internal_zzpw);
            this.pN = api;
            this.tf = i;
        }

        public void zzh(@NonNull ConnectionResult connectionResult) {
            boolean z = false;
            zzpw com_google_android_gms_internal_zzpw = (zzpw) this.tR.get();
            if (com_google_android_gms_internal_zzpw != null) {
                if (Looper.myLooper() == com_google_android_gms_internal_zzpw.tw.th.getLooper()) {
                    z = true;
                }
                zzab.zza(z, (Object) "onReportServiceBinding must be called on the GoogleApiClient handler thread");
                com_google_android_gms_internal_zzpw.tr.lock();
                try {
                    if (com_google_android_gms_internal_zzpw.zzfi(0)) {
                        if (!connectionResult.isSuccess()) {
                            com_google_android_gms_internal_zzpw.zzb(connectionResult, this.pN, this.tf);
                        }
                        if (com_google_android_gms_internal_zzpw.zzapm()) {
                            com_google_android_gms_internal_zzpw.zzapn();
                        }
                        com_google_android_gms_internal_zzpw.tr.unlock();
                    }
                } finally {
                    com_google_android_gms_internal_zzpw.tr.unlock();
                }
            }
        }
    }

    private class zzb extends zzf {
        final /* synthetic */ zzpw tQ;
        private final Map<com.google.android.gms.common.api.Api.zze, zza> tS;

        /* renamed from: com.google.android.gms.internal.zzpw.zzb.1 */
        class C07091 extends zza {
            final /* synthetic */ ConnectionResult tT;
            final /* synthetic */ zzb tU;

            C07091(zzb com_google_android_gms_internal_zzpw_zzb, zzpz com_google_android_gms_internal_zzpz, ConnectionResult connectionResult) {
                this.tU = com_google_android_gms_internal_zzpw_zzb;
                this.tT = connectionResult;
                super(com_google_android_gms_internal_zzpz);
            }

            public void zzapl() {
                this.tU.tQ.zzg(this.tT);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzpw.zzb.2 */
        class C07102 extends zza {
            final /* synthetic */ zzb tU;
            final /* synthetic */ com.google.android.gms.common.internal.zzd.zzf tV;

            C07102(zzb com_google_android_gms_internal_zzpw_zzb, zzpz com_google_android_gms_internal_zzpz, com.google.android.gms.common.internal.zzd.zzf com_google_android_gms_common_internal_zzd_zzf) {
                this.tU = com_google_android_gms_internal_zzpw_zzb;
                this.tV = com_google_android_gms_common_internal_zzd_zzf;
                super(com_google_android_gms_internal_zzpz);
            }

            public void zzapl() {
                this.tV.zzh(new ConnectionResult(16, null));
            }
        }

        public zzb(zzpw com_google_android_gms_internal_zzpw, Map<com.google.android.gms.common.api.Api.zze, zza> map) {
            this.tQ = com_google_android_gms_internal_zzpw;
            super(null);
            this.tS = map;
        }

        @WorkerThread
        public void zzapl() {
            int i;
            int i2 = 1;
            int i3 = 0;
            int i4 = 1;
            int i5 = 0;
            for (com.google.android.gms.common.api.Api.zze com_google_android_gms_common_api_Api_zze : this.tS.keySet()) {
                if (!com_google_android_gms_common_api_Api_zze.zzanu()) {
                    i = 0;
                    i4 = i5;
                } else if (((zza) this.tS.get(com_google_android_gms_common_api_Api_zze)).tf == 0) {
                    i = 1;
                    break;
                } else {
                    i = i4;
                    i4 = 1;
                }
                i5 = i4;
                i4 = i;
            }
            i2 = i5;
            i = 0;
            if (i2 != 0) {
                i3 = this.tQ.tz.isGooglePlayServicesAvailable(this.tQ.mContext);
            }
            if (i3 == 0 || (r0 == 0 && i4 == 0)) {
                if (this.tQ.tI) {
                    this.tQ.tG.connect();
                }
                for (com.google.android.gms.common.api.Api.zze com_google_android_gms_common_api_Api_zze2 : this.tS.keySet()) {
                    com.google.android.gms.common.internal.zzd.zzf com_google_android_gms_common_internal_zzd_zzf = (com.google.android.gms.common.internal.zzd.zzf) this.tS.get(com_google_android_gms_common_api_Api_zze2);
                    if (!com_google_android_gms_common_api_Api_zze2.zzanu() || i3 == 0) {
                        com_google_android_gms_common_api_Api_zze2.zza(com_google_android_gms_common_internal_zzd_zzf);
                    } else {
                        this.tQ.tw.zza(new C07102(this, this.tQ, com_google_android_gms_common_internal_zzd_zzf));
                    }
                }
                return;
            }
            this.tQ.tw.zza(new C07091(this, this.tQ, new ConnectionResult(i3, null)));
        }
    }

    private class zzc extends zzf {
        final /* synthetic */ zzpw tQ;
        private final ArrayList<com.google.android.gms.common.api.Api.zze> tW;

        public zzc(zzpw com_google_android_gms_internal_zzpw, ArrayList<com.google.android.gms.common.api.Api.zze> arrayList) {
            this.tQ = com_google_android_gms_internal_zzpw;
            super(null);
            this.tW = arrayList;
        }

        @WorkerThread
        public void zzapl() {
            this.tQ.tw.th.uj = this.tQ.zzaps();
            Iterator it = this.tW.iterator();
            while (it.hasNext()) {
                ((com.google.android.gms.common.api.Api.zze) it.next()).zza(this.tQ.tK, this.tQ.tw.th.uj);
            }
        }
    }

    private class zze implements ConnectionCallbacks, OnConnectionFailedListener {
        final /* synthetic */ zzpw tQ;

        private zze(zzpw com_google_android_gms_internal_zzpw) {
            this.tQ = com_google_android_gms_internal_zzpw;
        }

        public void onConnected(Bundle bundle) {
            this.tQ.tG.zza(new zzd(this.tQ));
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            this.tQ.tr.lock();
            try {
                if (this.tQ.zzf(connectionResult)) {
                    this.tQ.zzapq();
                    this.tQ.zzapn();
                } else {
                    this.tQ.zzg(connectionResult);
                }
                this.tQ.tr.unlock();
            } catch (Throwable th) {
                this.tQ.tr.unlock();
            }
        }

        public void onConnectionSuspended(int i) {
        }
    }

    private static class zzd extends com.google.android.gms.signin.internal.zzb {
        private final WeakReference<zzpw> tR;

        /* renamed from: com.google.android.gms.internal.zzpw.zzd.1 */
        class C07111 extends zza {
            final /* synthetic */ zzpw tX;
            final /* synthetic */ SignInResponse tY;
            final /* synthetic */ zzd tZ;

            C07111(zzd com_google_android_gms_internal_zzpw_zzd, zzpz com_google_android_gms_internal_zzpz, zzpw com_google_android_gms_internal_zzpw, SignInResponse signInResponse) {
                this.tZ = com_google_android_gms_internal_zzpw_zzd;
                this.tX = com_google_android_gms_internal_zzpw;
                this.tY = signInResponse;
                super(com_google_android_gms_internal_zzpz);
            }

            public void zzapl() {
                this.tX.zza(this.tY);
            }
        }

        zzd(zzpw com_google_android_gms_internal_zzpw) {
            this.tR = new WeakReference(com_google_android_gms_internal_zzpw);
        }

        @BinderThread
        public void zzb(SignInResponse signInResponse) {
            zzpw com_google_android_gms_internal_zzpw = (zzpw) this.tR.get();
            if (com_google_android_gms_internal_zzpw != null) {
                com_google_android_gms_internal_zzpw.tw.zza(new C07111(this, com_google_android_gms_internal_zzpw, com_google_android_gms_internal_zzpw, signInResponse));
            }
        }
    }

    public zzpw(zzqa com_google_android_gms_internal_zzqa, zzg com_google_android_gms_common_internal_zzg, Map<Api<?>, Integer> map, com.google.android.gms.common.zzc com_google_android_gms_common_zzc, com.google.android.gms.common.api.Api.zza<? extends zzvu, zzvv> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzvu__com_google_android_gms_internal_zzvv, Lock lock, Context context) {
        this.tC = 0;
        this.tE = new Bundle();
        this.tF = new HashSet();
        this.tP = new ArrayList();
        this.tw = com_google_android_gms_internal_zzqa;
        this.tN = com_google_android_gms_common_internal_zzg;
        this.tO = map;
        this.tz = com_google_android_gms_common_zzc;
        this.si = com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzvu__com_google_android_gms_internal_zzvv;
        this.tr = lock;
        this.mContext = context;
    }

    private void zza(SignInResponse signInResponse) {
        if (zzfi(0)) {
            ConnectionResult zzath = signInResponse.zzath();
            if (zzath.isSuccess()) {
                ResolveAccountResponse zzbzz = signInResponse.zzbzz();
                ConnectionResult zzath2 = zzbzz.zzath();
                if (zzath2.isSuccess()) {
                    this.tJ = true;
                    this.tK = zzbzz.zzatg();
                    this.tL = zzbzz.zzati();
                    this.tM = zzbzz.zzatj();
                    zzapn();
                    return;
                }
                String valueOf = String.valueOf(zzath2);
                Log.wtf("GoogleApiClientConnecting", new StringBuilder(String.valueOf(valueOf).length() + 48).append("Sign-in succeeded with resolve account failure: ").append(valueOf).toString(), new Exception());
                zzg(zzath2);
            } else if (zzf(zzath)) {
                zzapq();
                zzapn();
            } else {
                zzg(zzath);
            }
        }
    }

    private boolean zza(int i, int i2, ConnectionResult connectionResult) {
        return (i2 != 1 || zze(connectionResult)) ? this.tA == null || i < this.tB : false;
    }

    private boolean zzapm() {
        this.tD--;
        if (this.tD > 0) {
            return false;
        }
        if (this.tD < 0) {
            Log.w("GoogleApiClientConnecting", this.tw.th.zzapy());
            Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            zzg(new ConnectionResult(8, null));
            return false;
        } else if (this.tA == null) {
            return true;
        } else {
            this.tw.uA = this.tB;
            zzg(this.tA);
            return false;
        }
    }

    private void zzapn() {
        if (this.tD == 0) {
            if (!this.tI || this.tJ) {
                zzapo();
            }
        }
    }

    private void zzapo() {
        ArrayList arrayList = new ArrayList();
        this.tC = 1;
        this.tD = this.tw.ui.size();
        for (com.google.android.gms.common.api.Api.zzc com_google_android_gms_common_api_Api_zzc : this.tw.ui.keySet()) {
            if (!this.tw.ux.containsKey(com_google_android_gms_common_api_Api_zzc)) {
                arrayList.add((com.google.android.gms.common.api.Api.zze) this.tw.ui.get(com_google_android_gms_common_api_Api_zzc));
            } else if (zzapm()) {
                zzapp();
            }
        }
        if (!arrayList.isEmpty()) {
            this.tP.add(zzqb.zzaqc().submit(new zzc(this, arrayList)));
        }
    }

    private void zzapp() {
        this.tw.zzaqa();
        zzqb.zzaqc().execute(new C02131(this));
        if (this.tG != null) {
            if (this.tL) {
                this.tG.zza(this.tK, this.tM);
            }
            zzbm(false);
        }
        for (com.google.android.gms.common.api.Api.zzc com_google_android_gms_common_api_Api_zzc : this.tw.ux.keySet()) {
            ((com.google.android.gms.common.api.Api.zze) this.tw.ui.get(com_google_android_gms_common_api_Api_zzc)).disconnect();
        }
        this.tw.uB.zzm(this.tE.isEmpty() ? null : this.tE);
    }

    private void zzapq() {
        this.tI = false;
        this.tw.th.uj = Collections.emptySet();
        for (com.google.android.gms.common.api.Api.zzc com_google_android_gms_common_api_Api_zzc : this.tF) {
            if (!this.tw.ux.containsKey(com_google_android_gms_common_api_Api_zzc)) {
                this.tw.ux.put(com_google_android_gms_common_api_Api_zzc, new ConnectionResult(17, null));
            }
        }
    }

    private void zzapr() {
        Iterator it = this.tP.iterator();
        while (it.hasNext()) {
            ((Future) it.next()).cancel(true);
        }
        this.tP.clear();
    }

    private Set<Scope> zzaps() {
        if (this.tN == null) {
            return Collections.emptySet();
        }
        Set<Scope> hashSet = new HashSet(this.tN.zzasj());
        Map zzasl = this.tN.zzasl();
        for (Api api : zzasl.keySet()) {
            if (!this.tw.ux.containsKey(api.zzans())) {
                hashSet.addAll(((com.google.android.gms.common.internal.zzg.zza) zzasl.get(api)).dT);
            }
        }
        return hashSet;
    }

    private void zzb(ConnectionResult connectionResult, Api<?> api, int i) {
        if (i != 2) {
            int priority = api.zzanp().getPriority();
            if (zza(priority, i, connectionResult)) {
                this.tA = connectionResult;
                this.tB = priority;
            }
        }
        this.tw.ux.put(api.zzans(), connectionResult);
    }

    private void zzbm(boolean z) {
        if (this.tG != null) {
            if (this.tG.isConnected() && z) {
                this.tG.zzbzo();
            }
            this.tG.disconnect();
            this.tK = null;
        }
    }

    private boolean zze(ConnectionResult connectionResult) {
        return connectionResult.hasResolution() || this.tz.zzfc(connectionResult.getErrorCode()) != null;
    }

    private boolean zzf(ConnectionResult connectionResult) {
        return this.tH != 2 ? this.tH == 1 && !connectionResult.hasResolution() : true;
    }

    private boolean zzfi(int i) {
        if (this.tC == i) {
            return true;
        }
        Log.w("GoogleApiClientConnecting", this.tw.th.zzapy());
        String valueOf = String.valueOf(this);
        Log.w("GoogleApiClientConnecting", new StringBuilder(String.valueOf(valueOf).length() + 23).append("Unexpected callback in ").append(valueOf).toString());
        Log.w("GoogleApiClientConnecting", "mRemainingConnections=" + this.tD);
        valueOf = String.valueOf(zzfj(this.tC));
        String valueOf2 = String.valueOf(zzfj(i));
        Log.wtf("GoogleApiClientConnecting", new StringBuilder((String.valueOf(valueOf).length() + 70) + String.valueOf(valueOf2).length()).append("GoogleApiClient connecting is in step ").append(valueOf).append(" but received callback for step ").append(valueOf2).toString(), new Exception());
        zzg(new ConnectionResult(8, null));
        return false;
    }

    private String zzfj(int i) {
        switch (i) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return "STEP_GETTING_REMOTE_SERVICE";
            default:
                return "UNKNOWN";
        }
    }

    private void zzg(ConnectionResult connectionResult) {
        zzapr();
        zzbm(!connectionResult.hasResolution());
        this.tw.zzi(connectionResult);
        this.tw.uB.zzd(connectionResult);
    }

    public void begin() {
        this.tw.ux.clear();
        this.tI = false;
        this.tA = null;
        this.tC = 0;
        this.tH = 2;
        this.tJ = false;
        this.tL = false;
        Map hashMap = new HashMap();
        int i = 0;
        for (Api api : this.tO.keySet()) {
            com.google.android.gms.common.api.Api.zze com_google_android_gms_common_api_Api_zze = (com.google.android.gms.common.api.Api.zze) this.tw.ui.get(api.zzans());
            int intValue = ((Integer) this.tO.get(api)).intValue();
            int i2 = (api.zzanp().getPriority() == 1 ? 1 : 0) | i;
            if (com_google_android_gms_common_api_Api_zze.zzafk()) {
                this.tI = true;
                if (intValue < this.tH) {
                    this.tH = intValue;
                }
                if (intValue != 0) {
                    this.tF.add(api.zzans());
                }
            }
            hashMap.put(com_google_android_gms_common_api_Api_zze, new zza(this, api, intValue));
            i = i2;
        }
        if (i != 0) {
            this.tI = false;
        }
        if (this.tI) {
            this.tN.zzc(Integer.valueOf(this.tw.th.getSessionId()));
            ConnectionCallbacks com_google_android_gms_internal_zzpw_zze = new zze();
            this.tG = (zzvu) this.si.zza(this.mContext, this.tw.th.getLooper(), this.tN, this.tN.zzasp(), com_google_android_gms_internal_zzpw_zze, com_google_android_gms_internal_zzpw_zze);
        }
        this.tD = this.tw.ui.size();
        this.tP.add(zzqb.zzaqc().submit(new zzb(this, hashMap)));
    }

    public void connect() {
    }

    public boolean disconnect() {
        zzapr();
        zzbm(true);
        this.tw.zzi(null);
        return true;
    }

    public void onConnected(Bundle bundle) {
        if (zzfi(1)) {
            if (bundle != null) {
                this.tE.putAll(bundle);
            }
            if (zzapm()) {
                zzapp();
            }
        }
    }

    public void onConnectionSuspended(int i) {
        zzg(new ConnectionResult(8, null));
    }

    public void zza(ConnectionResult connectionResult, Api<?> api, int i) {
        if (zzfi(1)) {
            zzb(connectionResult, api, i);
            if (zzapm()) {
                zzapp();
            }
        }
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.internal.zzpm.zza<R, A>> T zzc(T t) {
        this.tw.th.uc.add(t);
        return t;
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.internal.zzpm.zza<? extends Result, A>> T zzd(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}
