package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Api.zzh;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.common.internal.zzd.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public class zzqc implements Callback {
    private static zzqc uG;
    private static final Object zzamr;
    private final Context mContext;
    private final Handler mHandler;
    private final GoogleApiAvailability sh;
    private long uF;
    private int uH;
    private final AtomicInteger uI;
    private final SparseArray<zzc<?>> uJ;
    private final Map<zzpj<?>, zzc<?>> uK;
    private zzpr uL;
    private final Set<zzpj<?>> uM;
    private final ReferenceQueue<com.google.android.gms.common.api.zzc<?>> uN;
    private final SparseArray<zza> uO;
    private zzb uP;
    private long ue;
    private long uf;

    private final class zza extends PhantomReference<com.google.android.gms.common.api.zzc<?>> {
        private final int sx;
        final /* synthetic */ zzqc uQ;

        public zza(zzqc com_google_android_gms_internal_zzqc, com.google.android.gms.common.api.zzc com_google_android_gms_common_api_zzc, int i, ReferenceQueue<com.google.android.gms.common.api.zzc<?>> referenceQueue) {
            this.uQ = com_google_android_gms_internal_zzqc;
            super(com_google_android_gms_common_api_zzc, referenceQueue);
            this.sx = i;
        }

        public void zzaqg() {
            this.uQ.mHandler.sendMessage(this.uQ.mHandler.obtainMessage(2, this.sx, 2));
        }
    }

    private static final class zzb extends Thread {
        private final ReferenceQueue<com.google.android.gms.common.api.zzc<?>> uN;
        private final SparseArray<zza> uO;
        private final AtomicBoolean uR;

        public zzb(ReferenceQueue<com.google.android.gms.common.api.zzc<?>> referenceQueue, SparseArray<zza> sparseArray) {
            super("GoogleApiCleanup");
            this.uR = new AtomicBoolean();
            this.uN = referenceQueue;
            this.uO = sparseArray;
        }

        public void run() {
            this.uR.set(true);
            Process.setThreadPriority(10);
            while (this.uR.get()) {
                try {
                    zza com_google_android_gms_internal_zzqc_zza = (zza) this.uN.remove();
                    this.uO.remove(com_google_android_gms_internal_zzqc_zza.sx);
                    com_google_android_gms_internal_zzqc_zza.zzaqg();
                } catch (InterruptedException e) {
                } finally {
                    this.uR.set(false);
                }
            }
        }
    }

    private class zzc<O extends ApiOptions> implements ConnectionCallbacks, OnConnectionFailedListener {
        private final zzpj<O> rQ;
        final /* synthetic */ zzqc uQ;
        private final Queue<zzpi> uS;
        private final zze uT;
        private final com.google.android.gms.common.api.Api.zzb uU;
        private final SparseArray<zzqy> uV;
        private final Set<zzpl> uW;
        private final SparseArray<Map<Object, com.google.android.gms.internal.zzpm.zza>> uX;
        private ConnectionResult uY;
        private boolean ud;

        /* renamed from: com.google.android.gms.internal.zzqc.zzc.1 */
        class C07161 implements zzc {
            final /* synthetic */ int uZ;
            final /* synthetic */ zzc va;

            C07161(zzc com_google_android_gms_internal_zzqc_zzc, int i) {
                this.va = com_google_android_gms_internal_zzqc_zzc;
                this.uZ = i;
            }

            public void zzaqn() {
                if (this.va.uS.isEmpty()) {
                    this.va.zzf(this.uZ, false);
                }
            }
        }

        @WorkerThread
        public zzc(zzqc com_google_android_gms_internal_zzqc, com.google.android.gms.common.api.zzc<O> com_google_android_gms_common_api_zzc_O) {
            this.uQ = com_google_android_gms_internal_zzqc;
            this.uS = new LinkedList();
            this.uV = new SparseArray();
            this.uW = new HashSet();
            this.uX = new SparseArray();
            this.uY = null;
            this.uT = zzb((com.google.android.gms.common.api.zzc) com_google_android_gms_common_api_zzc_O);
            if (this.uT instanceof zzah) {
                this.uU = ((zzah) this.uT).zzatn();
            } else {
                this.uU = this.uT;
            }
            this.rQ = com_google_android_gms_common_api_zzc_O.zzaob();
        }

        @WorkerThread
        private void connect() {
            if (!this.uT.isConnected() && !this.uT.isConnecting()) {
                if (this.uT.zzanu() && this.uQ.uH != 0) {
                    this.uQ.uH = this.uQ.sh.isGooglePlayServicesAvailable(this.uQ.mContext);
                    if (this.uQ.uH != 0) {
                        onConnectionFailed(new ConnectionResult(this.uQ.uH, null));
                        return;
                    }
                }
                this.uT.zza(new zzd(this.uQ, this.uT, this.rQ));
            }
        }

        @WorkerThread
        private void resume() {
            if (this.ud) {
                connect();
            }
        }

        @WorkerThread
        private void zzab(Status status) {
            for (zzpi zzx : this.uS) {
                zzx.zzx(status);
            }
            this.uS.clear();
        }

        @WorkerThread
        private void zzapu() {
            if (this.ud) {
                zzaqk();
                zzab(this.uQ.sh.isGooglePlayServicesAvailable(this.uQ.mContext) == 18 ? new Status(8, "Connection timed out while waiting for Google Play services update to complete.") : new Status(8, "API failed to connect while resuming due to an unknown error."));
                this.uT.disconnect();
            }
        }

        @WorkerThread
        private void zzaqk() {
            if (this.ud) {
                this.uQ.mHandler.removeMessages(9, this.rQ);
                this.uQ.mHandler.removeMessages(8, this.rQ);
                this.ud = false;
            }
        }

        private void zzaql() {
            this.uQ.mHandler.removeMessages(10, this.rQ);
            this.uQ.mHandler.sendMessageDelayed(this.uQ.mHandler.obtainMessage(10, this.rQ), this.uQ.uF);
        }

        private void zzaqm() {
            if (this.uT.isConnected() && this.uX.size() == 0) {
                for (int i = 0; i < this.uV.size(); i++) {
                    if (((zzqy) this.uV.get(this.uV.keyAt(i))).zzara()) {
                        zzaql();
                        return;
                    }
                }
                this.uT.disconnect();
            }
        }

        @WorkerThread
        private zze zzb(com.google.android.gms.common.api.zzc com_google_android_gms_common_api_zzc) {
            Api zzanz = com_google_android_gms_common_api_zzc.zzanz();
            if (!zzanz.zzant()) {
                return com_google_android_gms_common_api_zzc.zzanz().zzanq().zza(com_google_android_gms_common_api_zzc.getApplicationContext(), this.uQ.mHandler.getLooper(), zzg.zzcd(com_google_android_gms_common_api_zzc.getApplicationContext()), com_google_android_gms_common_api_zzc.zzaoa(), this, this);
            }
            zzh zzanr = zzanz.zzanr();
            return new zzah(com_google_android_gms_common_api_zzc.getApplicationContext(), this.uQ.mHandler.getLooper(), zzanr.zzanw(), this, this, zzg.zzcd(com_google_android_gms_common_api_zzc.getApplicationContext()), zzanr.zzr(com_google_android_gms_common_api_zzc.zzaoa()));
        }

        @WorkerThread
        private void zzc(zzpi com_google_android_gms_internal_zzpi) {
            com_google_android_gms_internal_zzpi.zza(this.uV);
            Map map;
            if (com_google_android_gms_internal_zzpi.iq == 3) {
                try {
                    Map map2;
                    map = (Map) this.uX.get(com_google_android_gms_internal_zzpi.sx);
                    if (map == null) {
                        ArrayMap arrayMap = new ArrayMap(1);
                        this.uX.put(com_google_android_gms_internal_zzpi.sx, arrayMap);
                        map2 = arrayMap;
                    } else {
                        map2 = map;
                    }
                    com.google.android.gms.internal.zzpm.zza com_google_android_gms_internal_zzpm_zza = ((com.google.android.gms.internal.zzpi.zza) com_google_android_gms_internal_zzpi).sy;
                    map2.put(((zzqm) com_google_android_gms_internal_zzpm_zza).zzaqu(), com_google_android_gms_internal_zzpm_zza);
                } catch (ClassCastException e) {
                    throw new IllegalStateException("Listener registration methods must implement ListenerApiMethod");
                }
            } else if (com_google_android_gms_internal_zzpi.iq == 4) {
                try {
                    map = (Map) this.uX.get(com_google_android_gms_internal_zzpi.sx);
                    zzqm com_google_android_gms_internal_zzqm = (zzqm) ((com.google.android.gms.internal.zzpi.zza) com_google_android_gms_internal_zzpi).sy;
                    if (map != null) {
                        map.remove(com_google_android_gms_internal_zzqm.zzaqu());
                    } else {
                        Log.w("GoogleApiManager", "Received call to unregister a listener without a matching registration call.");
                    }
                } catch (ClassCastException e2) {
                    throw new IllegalStateException("Listener unregistration methods must implement ListenerApiMethod");
                }
            }
            try {
                com_google_android_gms_internal_zzpi.zzb(this.uU);
            } catch (DeadObjectException e3) {
                this.uT.disconnect();
                onConnectionSuspended(1);
            }
        }

        @WorkerThread
        private void zzj(ConnectionResult connectionResult) {
            for (zzpl zza : this.uW) {
                zza.zza(this.rQ, connectionResult);
            }
            this.uW.clear();
        }

        boolean isConnected() {
            return this.uT.isConnected();
        }

        @WorkerThread
        public void onConnected(@Nullable Bundle bundle) {
            zzaqi();
            zzj(ConnectionResult.rb);
            zzaqk();
            for (int i = 0; i < this.uX.size(); i++) {
                for (com.google.android.gms.internal.zzpm.zza zzb : ((Map) this.uX.get(this.uX.keyAt(i))).values()) {
                    try {
                        zzb.zzb(this.uU);
                    } catch (DeadObjectException e) {
                        this.uT.disconnect();
                        onConnectionSuspended(1);
                    }
                }
            }
            zzaqh();
            zzaql();
        }

        @WorkerThread
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            zzaqi();
            this.uQ.uH = -1;
            zzj(connectionResult);
            int keyAt = this.uV.keyAt(0);
            if (this.uS.isEmpty()) {
                this.uY = connectionResult;
                return;
            }
            synchronized (zzqc.zzamr) {
                if (null == null || !this.uQ.uM.contains(this.rQ)) {
                    if (!this.uQ.zzc(connectionResult, keyAt)) {
                        if (connectionResult.getErrorCode() == 18) {
                            this.ud = true;
                        }
                        if (this.ud) {
                            this.uQ.mHandler.sendMessageDelayed(Message.obtain(this.uQ.mHandler, 8, this.rQ), this.uQ.uf);
                            return;
                        }
                        String valueOf = String.valueOf(this.rQ.zzaon());
                        zzab(new Status(17, new StringBuilder(String.valueOf(valueOf).length() + 38).append("API: ").append(valueOf).append(" is not available on this device.").toString()));
                        return;
                    }
                    return;
                }
                null.zzb(connectionResult, keyAt);
            }
        }

        @WorkerThread
        public void onConnectionSuspended(int i) {
            zzaqi();
            this.ud = true;
            this.uQ.mHandler.sendMessageDelayed(Message.obtain(this.uQ.mHandler, 8, this.rQ), this.uQ.uf);
            this.uQ.mHandler.sendMessageDelayed(Message.obtain(this.uQ.mHandler, 9, this.rQ), this.uQ.ue);
            this.uQ.uH = -1;
        }

        @WorkerThread
        public void zzaqh() {
            while (this.uT.isConnected() && !this.uS.isEmpty()) {
                zzc((zzpi) this.uS.remove());
            }
        }

        @WorkerThread
        public void zzaqi() {
            this.uY = null;
        }

        ConnectionResult zzaqj() {
            return this.uY;
        }

        @WorkerThread
        public void zzb(zzpi com_google_android_gms_internal_zzpi) {
            if (this.uT.isConnected()) {
                zzc(com_google_android_gms_internal_zzpi);
                zzaql();
                return;
            }
            this.uS.add(com_google_android_gms_internal_zzpi);
            if (this.uY == null || !this.uY.hasResolution()) {
                connect();
            } else {
                onConnectionFailed(this.uY);
            }
        }

        @WorkerThread
        public void zzb(zzpl com_google_android_gms_internal_zzpl) {
            this.uW.add(com_google_android_gms_internal_zzpl);
        }

        @WorkerThread
        public void zzf(int i, boolean z) {
            Iterator it = this.uS.iterator();
            while (it.hasNext()) {
                zzpi com_google_android_gms_internal_zzpi = (zzpi) it.next();
                if (com_google_android_gms_internal_zzpi.sx == i && com_google_android_gms_internal_zzpi.iq != 1 && com_google_android_gms_internal_zzpi.cancel()) {
                    it.remove();
                }
            }
            ((zzqy) this.uV.get(i)).release();
            this.uX.delete(i);
            if (!z) {
                this.uV.remove(i);
                this.uQ.uO.remove(i);
                if (this.uV.size() == 0 && this.uS.isEmpty()) {
                    zzaqk();
                    this.uT.disconnect();
                    this.uQ.uK.remove(this.rQ);
                    synchronized (zzqc.zzamr) {
                        this.uQ.uM.remove(this.rQ);
                    }
                }
            }
        }

        @WorkerThread
        public void zzfn(int i) {
            this.uV.put(i, new zzqy(this.rQ.zzans(), this.uT));
        }

        @WorkerThread
        public void zzfo(int i) {
            ((zzqy) this.uV.get(i)).zza(new C07161(this, i));
        }
    }

    private class zzd implements zzf {
        private final zzpj<?> rQ;
        final /* synthetic */ zzqc uQ;
        private final zze uT;

        public zzd(zzqc com_google_android_gms_internal_zzqc, zze com_google_android_gms_common_api_Api_zze, zzpj<?> com_google_android_gms_internal_zzpj_) {
            this.uQ = com_google_android_gms_internal_zzqc;
            this.uT = com_google_android_gms_common_api_Api_zze;
            this.rQ = com_google_android_gms_internal_zzpj_;
        }

        @WorkerThread
        public void zzh(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                this.uT.zza(null, Collections.emptySet());
            } else {
                ((zzc) this.uQ.uK.get(this.rQ)).onConnectionFailed(connectionResult);
            }
        }
    }

    static {
        zzamr = new Object();
    }

    private zzqc(Context context) {
        this(context, GoogleApiAvailability.getInstance());
    }

    private zzqc(Context context, GoogleApiAvailability googleApiAvailability) {
        this.uf = DNSConstants.CLOSE_TIMEOUT;
        this.ue = 120000;
        this.uF = 10000;
        this.uH = -1;
        this.uI = new AtomicInteger(1);
        this.uJ = new SparseArray();
        this.uK = new ConcurrentHashMap(5, 0.75f, 1);
        this.uL = null;
        this.uM = new com.google.android.gms.common.util.zza();
        this.uN = new ReferenceQueue();
        this.uO = new SparseArray();
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper(), this);
        this.sh = googleApiAvailability;
    }

    private int zza(com.google.android.gms.common.api.zzc<?> com_google_android_gms_common_api_zzc_) {
        int andIncrement = this.uI.getAndIncrement();
        this.mHandler.sendMessage(this.mHandler.obtainMessage(6, andIncrement, 0, com_google_android_gms_common_api_zzc_));
        return andIncrement;
    }

    public static Pair<zzqc, Integer> zza(Context context, com.google.android.gms.common.api.zzc<?> com_google_android_gms_common_api_zzc_) {
        Pair<zzqc, Integer> create;
        synchronized (zzamr) {
            if (uG == null) {
                uG = new zzqc(context.getApplicationContext());
            }
            create = Pair.create(uG, Integer.valueOf(uG.zza((com.google.android.gms.common.api.zzc) com_google_android_gms_common_api_zzc_)));
        }
        return create;
    }

    @WorkerThread
    private void zza(com.google.android.gms.common.api.zzc<?> com_google_android_gms_common_api_zzc_, int i) {
        zzpj zzaob = com_google_android_gms_common_api_zzc_.zzaob();
        if (!this.uK.containsKey(zzaob)) {
            this.uK.put(zzaob, new zzc(this, com_google_android_gms_common_api_zzc_));
        }
        zzc com_google_android_gms_internal_zzqc_zzc = (zzc) this.uK.get(zzaob);
        com_google_android_gms_internal_zzqc_zzc.zzfn(i);
        this.uJ.put(i, com_google_android_gms_internal_zzqc_zzc);
        com_google_android_gms_internal_zzqc_zzc.connect();
        this.uO.put(i, new zza(this, com_google_android_gms_common_api_zzc_, i, this.uN));
        if (this.uP == null || !this.uP.uR.get()) {
            this.uP = new zzb(this.uN, this.uO);
            this.uP.start();
        }
    }

    @WorkerThread
    private void zza(zzpi com_google_android_gms_internal_zzpi) {
        ((zzc) this.uJ.get(com_google_android_gms_internal_zzpi.sx)).zzb(com_google_android_gms_internal_zzpi);
    }

    public static zzqc zzaqd() {
        zzqc com_google_android_gms_internal_zzqc;
        synchronized (zzamr) {
            com_google_android_gms_internal_zzqc = uG;
        }
        return com_google_android_gms_internal_zzqc;
    }

    @WorkerThread
    private void zzaqe() {
        for (zzc com_google_android_gms_internal_zzqc_zzc : this.uK.values()) {
            com_google_android_gms_internal_zzqc_zzc.zzaqi();
            com_google_android_gms_internal_zzqc_zzc.connect();
        }
    }

    @WorkerThread
    private void zze(int i, boolean z) {
        zzc com_google_android_gms_internal_zzqc_zzc = (zzc) this.uJ.get(i);
        if (com_google_android_gms_internal_zzqc_zzc != null) {
            if (!z) {
                this.uJ.delete(i);
            }
            com_google_android_gms_internal_zzqc_zzc.zzf(i, z);
            return;
        }
        Log.wtf("GoogleApiManager", "onRelease received for unknown instance: " + i, new Exception());
    }

    @WorkerThread
    private void zzfm(int i) {
        zzc com_google_android_gms_internal_zzqc_zzc = (zzc) this.uJ.get(i);
        if (com_google_android_gms_internal_zzqc_zzc != null) {
            this.uJ.delete(i);
            com_google_android_gms_internal_zzqc_zzc.zzfo(i);
            return;
        }
        Log.wtf("GoogleApiManager", "onCleanupLeakInternal received for unknown instance: " + i, new Exception());
    }

    @WorkerThread
    public boolean handleMessage(Message message) {
        boolean z = false;
        switch (message.what) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                zza((zzpl) message.obj);
                break;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                zzfm(message.arg1);
                break;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                zzaqe();
                break;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                zza((zzpi) message.obj);
                break;
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                if (this.uJ.get(message.arg1) != null) {
                    ((zzc) this.uJ.get(message.arg1)).zzab(new Status(17, "Error resolution was canceled by the user."));
                    break;
                }
                break;
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                zza((com.google.android.gms.common.api.zzc) message.obj, message.arg1);
                break;
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                int i = message.arg1;
                if (message.arg2 == 1) {
                    z = true;
                }
                zze(i, z);
                break;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                if (this.uK.containsKey(message.obj)) {
                    ((zzc) this.uK.get(message.obj)).resume();
                    break;
                }
                break;
            case ConnectionResult.SERVICE_INVALID /*9*/:
                if (this.uK.containsKey(message.obj)) {
                    ((zzc) this.uK.get(message.obj)).zzapu();
                    break;
                }
                break;
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                if (this.uK.containsKey(message.obj)) {
                    ((zzc) this.uK.get(message.obj)).zzaqm();
                    break;
                }
                break;
            default:
                Log.w("GoogleApiManager", "Unknown message id: " + message.what);
                return false;
        }
        return true;
    }

    public void zza(ConnectionResult connectionResult, int i) {
        if (!zzc(connectionResult, i)) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(5, i, 0));
        }
    }

    public <O extends ApiOptions> void zza(com.google.android.gms.common.api.zzc<O> com_google_android_gms_common_api_zzc_O, int i, com.google.android.gms.internal.zzpm.zza<? extends Result, com.google.android.gms.common.api.Api.zzb> com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__com_google_android_gms_common_api_Api_zzb) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, new com.google.android.gms.internal.zzpi.zza(com_google_android_gms_common_api_zzc_O.getInstanceId(), i, com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__com_google_android_gms_common_api_Api_zzb)));
    }

    public <O extends ApiOptions, TResult> void zza(com.google.android.gms.common.api.zzc<O> com_google_android_gms_common_api_zzc_O, int i, zzqw<com.google.android.gms.common.api.Api.zzb, TResult> com_google_android_gms_internal_zzqw_com_google_android_gms_common_api_Api_zzb__TResult, TaskCompletionSource<TResult> taskCompletionSource) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, new com.google.android.gms.internal.zzpi.zzb(com_google_android_gms_common_api_zzc_O.getInstanceId(), i, com_google_android_gms_internal_zzqw_com_google_android_gms_common_api_Api_zzb__TResult, taskCompletionSource)));
    }

    @WorkerThread
    public void zza(zzpl com_google_android_gms_internal_zzpl) {
        for (zzpj com_google_android_gms_internal_zzpj : com_google_android_gms_internal_zzpl.zzaoq()) {
            zzc com_google_android_gms_internal_zzqc_zzc = (zzc) this.uK.get(com_google_android_gms_internal_zzpj);
            if (com_google_android_gms_internal_zzqc_zzc == null) {
                com_google_android_gms_internal_zzpl.cancel();
                return;
            } else if (com_google_android_gms_internal_zzqc_zzc.isConnected()) {
                com_google_android_gms_internal_zzpl.zza(com_google_android_gms_internal_zzpj, ConnectionResult.rb);
            } else if (com_google_android_gms_internal_zzqc_zzc.zzaqj() != null) {
                com_google_android_gms_internal_zzpl.zza(com_google_android_gms_internal_zzpj, com_google_android_gms_internal_zzqc_zzc.zzaqj());
            } else {
                com_google_android_gms_internal_zzqc_zzc.zzb(com_google_android_gms_internal_zzpl);
            }
        }
    }

    public void zza(zzpr com_google_android_gms_internal_zzpr) {
        synchronized (zzamr) {
            if (com_google_android_gms_internal_zzpr == null) {
                this.uL = null;
                this.uM.clear();
            }
        }
    }

    public void zzaoo() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
    }

    boolean zzc(ConnectionResult connectionResult, int i) {
        if (!connectionResult.hasResolution() && !this.sh.isUserResolvableError(connectionResult.getErrorCode())) {
            return false;
        }
        this.sh.zza(this.mContext, connectionResult, i);
        return true;
    }

    public void zzd(int i, boolean z) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7, i, z ? 1 : 2));
    }
}
