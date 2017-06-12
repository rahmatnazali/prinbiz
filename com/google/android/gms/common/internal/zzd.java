package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.BinderThread;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.net.ftp.FTPClient;

public abstract class zzd<T extends IInterface> {
    public static final String[] xE;
    private final Context mContext;
    final Handler mHandler;
    private final com.google.android.gms.common.zzc tz;
    private final zzc xA;
    private final int xB;
    private final String xC;
    protected AtomicInteger xD;
    private int xm;
    private long xn;
    private long xo;
    private int xp;
    private long xq;
    private final zzm xr;
    private final Object xs;
    private zzu xt;
    private zzf xu;
    private T xv;
    private final ArrayList<zze<?>> xw;
    private zzh xx;
    private int xy;
    private final zzb xz;
    private final Looper zzahv;
    private final Object zzail;

    public interface zzb {
        void onConnected(@Nullable Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface zzc {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    final class zzd extends Handler {
        final /* synthetic */ zzd xG;

        public zzd(zzd com_google_android_gms_common_internal_zzd, Looper looper) {
            this.xG = com_google_android_gms_common_internal_zzd;
            super(looper);
        }

        private void zza(Message message) {
            zze com_google_android_gms_common_internal_zzd_zze = (zze) message.obj;
            com_google_android_gms_common_internal_zzd_zze.zzase();
            com_google_android_gms_common_internal_zzd_zze.unregister();
        }

        private boolean zzb(Message message) {
            return message.what == 2 || message.what == 1 || message.what == 5;
        }

        public void handleMessage(Message message) {
            PendingIntent pendingIntent = null;
            if (this.xG.xD.get() != message.arg1) {
                if (zzb(message)) {
                    zza(message);
                }
            } else if ((message.what == 1 || message.what == 5) && !this.xG.isConnecting()) {
                zza(message);
            } else if (message.what == 3) {
                if (message.obj instanceof PendingIntent) {
                    pendingIntent = (PendingIntent) message.obj;
                }
                ConnectionResult connectionResult = new ConnectionResult(message.arg2, pendingIntent);
                this.xG.xu.zzh(connectionResult);
                this.xG.onConnectionFailed(connectionResult);
            } else if (message.what == 4) {
                this.xG.zzb(4, null);
                if (this.xG.xz != null) {
                    this.xG.xz.onConnectionSuspended(message.arg2);
                }
                this.xG.onConnectionSuspended(message.arg2);
                this.xG.zza(4, 1, null);
            } else if (message.what == 2 && !this.xG.isConnected()) {
                zza(message);
            } else if (zzb(message)) {
                ((zze) message.obj).zzasf();
            } else {
                Log.wtf("GmsClient", "Don't know how to handle message: " + message.what, new Exception());
            }
        }
    }

    protected abstract class zze<TListener> {
        private TListener mListener;
        final /* synthetic */ zzd xG;
        private boolean xH;

        public zze(zzd com_google_android_gms_common_internal_zzd, TListener tListener) {
            this.xG = com_google_android_gms_common_internal_zzd;
            this.mListener = tListener;
            this.xH = false;
        }

        public void unregister() {
            zzasg();
            synchronized (this.xG.xw) {
                this.xG.xw.remove(this);
            }
        }

        protected abstract void zzase();

        public void zzasf() {
            synchronized (this) {
                Object obj = this.mListener;
                if (this.xH) {
                    String valueOf = String.valueOf(this);
                    Log.w("GmsClient", new StringBuilder(String.valueOf(valueOf).length() + 47).append("Callback proxy ").append(valueOf).append(" being reused. This is not safe.").toString());
                }
            }
            if (obj != null) {
                try {
                    zzv(obj);
                } catch (RuntimeException e) {
                    zzase();
                    throw e;
                }
            }
            zzase();
            synchronized (this) {
                this.xH = true;
            }
            unregister();
        }

        public void zzasg() {
            synchronized (this) {
                this.mListener = null;
            }
        }

        protected abstract void zzv(TListener tListener);
    }

    public interface zzf {
        void zzh(@NonNull ConnectionResult connectionResult);
    }

    public final class zzh implements ServiceConnection {
        final /* synthetic */ zzd xG;
        private final int xJ;

        public zzh(zzd com_google_android_gms_common_internal_zzd, int i) {
            this.xG = com_google_android_gms_common_internal_zzd;
            this.xJ = i;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            zzab.zzb((Object) iBinder, (Object) "Expecting a valid IBinder");
            synchronized (this.xG.xs) {
                this.xG.xt = com.google.android.gms.common.internal.zzu.zza.zzdt(iBinder);
            }
            this.xG.zza(0, null, this.xJ);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            synchronized (this.xG.xs) {
                this.xG.xt = null;
            }
            this.xG.mHandler.sendMessage(this.xG.mHandler.obtainMessage(4, this.xJ, 1));
        }
    }

    private abstract class zza extends zze<Boolean> {
        public final int statusCode;
        public final Bundle xF;
        final /* synthetic */ zzd xG;

        @BinderThread
        protected zza(zzd com_google_android_gms_common_internal_zzd, int i, Bundle bundle) {
            this.xG = com_google_android_gms_common_internal_zzd;
            super(com_google_android_gms_common_internal_zzd, Boolean.valueOf(true));
            this.statusCode = i;
            this.xF = bundle;
        }

        protected abstract boolean zzasd();

        protected void zzase() {
        }

        protected void zzc(Boolean bool) {
            PendingIntent pendingIntent = null;
            if (bool == null) {
                this.xG.zzb(1, null);
                return;
            }
            switch (this.statusCode) {
                case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                    if (!zzasd()) {
                        this.xG.zzb(1, null);
                        zzl(new ConnectionResult(8, null));
                    }
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    this.xG.zzb(1, null);
                    throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
                default:
                    this.xG.zzb(1, null);
                    if (this.xF != null) {
                        pendingIntent = (PendingIntent) this.xF.getParcelable("pendingIntent");
                    }
                    zzl(new ConnectionResult(this.statusCode, pendingIntent));
            }
        }

        protected abstract void zzl(ConnectionResult connectionResult);

        protected /* synthetic */ void zzv(Object obj) {
            zzc((Boolean) obj);
        }
    }

    protected class zzi implements zzf {
        final /* synthetic */ zzd xG;

        public zzi(zzd com_google_android_gms_common_internal_zzd) {
            this.xG = com_google_android_gms_common_internal_zzd;
        }

        public void zzh(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                this.xG.zza(null, this.xG.zzasc());
            } else if (this.xG.xA != null) {
                this.xG.xA.onConnectionFailed(connectionResult);
            }
        }
    }

    public static final class zzg extends com.google.android.gms.common.internal.zzt.zza {
        private zzd xI;
        private final int xJ;

        public zzg(@NonNull zzd com_google_android_gms_common_internal_zzd, int i) {
            this.xI = com_google_android_gms_common_internal_zzd;
            this.xJ = i;
        }

        private void zzash() {
            this.xI = null;
        }

        @BinderThread
        public void zza(int i, @NonNull IBinder iBinder, @Nullable Bundle bundle) {
            zzab.zzb(this.xI, (Object) "onPostInitComplete can be called only once per call to getRemoteService");
            this.xI.zza(i, iBinder, bundle, this.xJ);
            zzash();
        }

        @BinderThread
        public void zzb(int i, @Nullable Bundle bundle) {
            Log.wtf("GmsClient", "received deprecated onAccountValidationComplete callback, ignoring", new Exception());
        }
    }

    protected final class zzj extends zza {
        final /* synthetic */ zzd xG;
        public final IBinder xK;

        @BinderThread
        public zzj(zzd com_google_android_gms_common_internal_zzd, int i, IBinder iBinder, Bundle bundle) {
            this.xG = com_google_android_gms_common_internal_zzd;
            super(com_google_android_gms_common_internal_zzd, i, bundle);
            this.xK = iBinder;
        }

        protected boolean zzasd() {
            try {
                String interfaceDescriptor = this.xK.getInterfaceDescriptor();
                if (this.xG.zzra().equals(interfaceDescriptor)) {
                    IInterface zzbb = this.xG.zzbb(this.xK);
                    if (zzbb == null || !this.xG.zza(2, 3, zzbb)) {
                        return false;
                    }
                    Bundle zzamh = this.xG.zzamh();
                    if (this.xG.xz != null) {
                        this.xG.xz.onConnected(zzamh);
                    }
                    return true;
                }
                String valueOf = String.valueOf(this.xG.zzra());
                Log.e("GmsClient", new StringBuilder((String.valueOf(valueOf).length() + 34) + String.valueOf(interfaceDescriptor).length()).append("service descriptor mismatch: ").append(valueOf).append(" vs. ").append(interfaceDescriptor).toString());
                return false;
            } catch (RemoteException e) {
                Log.w("GmsClient", "service probably died");
                return false;
            }
        }

        protected void zzl(ConnectionResult connectionResult) {
            if (this.xG.xA != null) {
                this.xG.xA.onConnectionFailed(connectionResult);
            }
            this.xG.onConnectionFailed(connectionResult);
        }
    }

    protected final class zzk extends zza {
        final /* synthetic */ zzd xG;

        @BinderThread
        public zzk(zzd com_google_android_gms_common_internal_zzd, int i, @Nullable Bundle bundle) {
            this.xG = com_google_android_gms_common_internal_zzd;
            super(com_google_android_gms_common_internal_zzd, i, bundle);
        }

        protected boolean zzasd() {
            this.xG.xu.zzh(ConnectionResult.rb);
            return true;
        }

        protected void zzl(ConnectionResult connectionResult) {
            this.xG.xu.zzh(connectionResult);
            this.xG.onConnectionFailed(connectionResult);
        }
    }

    static {
        xE = new String[]{"service_esmobile", "service_googleme"};
    }

    protected zzd(Context context, Looper looper, int i, zzb com_google_android_gms_common_internal_zzd_zzb, zzc com_google_android_gms_common_internal_zzd_zzc, String str) {
        this(context, looper, zzm.zzce(context), com.google.android.gms.common.zzc.zzang(), i, (zzb) zzab.zzy(com_google_android_gms_common_internal_zzd_zzb), (zzc) zzab.zzy(com_google_android_gms_common_internal_zzd_zzc), str);
    }

    protected zzd(Context context, Looper looper, zzm com_google_android_gms_common_internal_zzm, com.google.android.gms.common.zzc com_google_android_gms_common_zzc, int i, zzb com_google_android_gms_common_internal_zzd_zzb, zzc com_google_android_gms_common_internal_zzd_zzc, String str) {
        this.zzail = new Object();
        this.xs = new Object();
        this.xw = new ArrayList();
        this.xy = 1;
        this.xD = new AtomicInteger(0);
        this.mContext = (Context) zzab.zzb((Object) context, (Object) "Context must not be null");
        this.zzahv = (Looper) zzab.zzb((Object) looper, (Object) "Looper must not be null");
        this.xr = (zzm) zzab.zzb((Object) com_google_android_gms_common_internal_zzm, (Object) "Supervisor must not be null");
        this.tz = (com.google.android.gms.common.zzc) zzab.zzb((Object) com_google_android_gms_common_zzc, (Object) "API availability must not be null");
        this.mHandler = new zzd(this, looper);
        this.xB = i;
        this.xz = com_google_android_gms_common_internal_zzd_zzb;
        this.xA = com_google_android_gms_common_internal_zzd_zzc;
        this.xC = str;
    }

    private boolean zza(int i, int i2, T t) {
        boolean z;
        synchronized (this.zzail) {
            if (this.xy != i) {
                z = false;
            } else {
                zzb(i2, t);
                z = true;
            }
        }
        return z;
    }

    private void zzarv() {
        if (this.xx != null) {
            String valueOf = String.valueOf(zzqz());
            String valueOf2 = String.valueOf(zzart());
            Log.e("GmsClient", new StringBuilder((String.valueOf(valueOf).length() + 70) + String.valueOf(valueOf2).length()).append("Calling connect() while still connected, missing disconnect() for ").append(valueOf).append(" on ").append(valueOf2).toString());
            this.xr.zzb(zzqz(), zzart(), this.xx, zzaru());
            this.xD.incrementAndGet();
        }
        this.xx = new zzh(this, this.xD.get());
        if (!this.xr.zza(zzqz(), zzart(), this.xx, zzaru())) {
            valueOf = String.valueOf(zzqz());
            valueOf2 = String.valueOf(zzart());
            Log.e("GmsClient", new StringBuilder((String.valueOf(valueOf).length() + 34) + String.valueOf(valueOf2).length()).append("unable to connect to service: ").append(valueOf).append(" on ").append(valueOf2).toString());
            zza(16, null, this.xD.get());
        }
    }

    private void zzarw() {
        if (this.xx != null) {
            this.xr.zzb(zzqz(), zzart(), this.xx, zzaru());
            this.xx = null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void zzb(int r5, T r6) {
        /*
        r4 = this;
        r0 = 1;
        r1 = 0;
        r2 = 3;
        if (r5 != r2) goto L_0x001d;
    L_0x0005:
        r3 = r0;
    L_0x0006:
        if (r6 == 0) goto L_0x001f;
    L_0x0008:
        r2 = r0;
    L_0x0009:
        if (r3 != r2) goto L_0x0021;
    L_0x000b:
        com.google.android.gms.common.internal.zzab.zzbo(r0);
        r1 = r4.zzail;
        monitor-enter(r1);
        r4.xy = r5;	 Catch:{ all -> 0x0027 }
        r4.xv = r6;	 Catch:{ all -> 0x0027 }
        r4.zzc(r5, r6);	 Catch:{ all -> 0x0027 }
        switch(r5) {
            case 1: goto L_0x002e;
            case 2: goto L_0x0023;
            case 3: goto L_0x002a;
            default: goto L_0x001b;
        };	 Catch:{ all -> 0x0027 }
    L_0x001b:
        monitor-exit(r1);	 Catch:{ all -> 0x0027 }
        return;
    L_0x001d:
        r3 = r1;
        goto L_0x0006;
    L_0x001f:
        r2 = r1;
        goto L_0x0009;
    L_0x0021:
        r0 = r1;
        goto L_0x000b;
    L_0x0023:
        r4.zzarv();	 Catch:{ all -> 0x0027 }
        goto L_0x001b;
    L_0x0027:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0027 }
        throw r0;
    L_0x002a:
        r4.zza(r6);	 Catch:{ all -> 0x0027 }
        goto L_0x001b;
    L_0x002e:
        r4.zzarw();	 Catch:{ all -> 0x0027 }
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzd.zzb(int, android.os.IInterface):void");
    }

    public void disconnect() {
        this.xD.incrementAndGet();
        synchronized (this.xw) {
            int size = this.xw.size();
            for (int i = 0; i < size; i++) {
                ((zze) this.xw.get(i)).zzasg();
            }
            this.xw.clear();
        }
        synchronized (this.xs) {
            this.xt = null;
        }
        zzb(1, null);
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (this.zzail) {
            int i = this.xy;
            IInterface iInterface = this.xv;
        }
        printWriter.append(str).append("mConnectState=");
        switch (i) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                printWriter.print("DISCONNECTED");
                break;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                printWriter.print("CONNECTING");
                break;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                printWriter.print("CONNECTED");
                break;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                printWriter.print("DISCONNECTING");
                break;
            default:
                printWriter.print("UNKNOWN");
                break;
        }
        printWriter.append(" mService=");
        if (iInterface == null) {
            printWriter.println("null");
        } else {
            printWriter.append(zzra()).append("@").println(Integer.toHexString(System.identityHashCode(iInterface.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.xo > 0) {
            PrintWriter append = printWriter.append(str).append("lastConnectedTime=");
            long j = this.xo;
            String valueOf = String.valueOf(simpleDateFormat.format(new Date(this.xo)));
            append.println(new StringBuilder(String.valueOf(valueOf).length() + 21).append(j).append(" ").append(valueOf).toString());
        }
        if (this.xn > 0) {
            printWriter.append(str).append("lastSuspendedCause=");
            switch (this.xm) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    printWriter.append("CAUSE_SERVICE_DISCONNECTED");
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    printWriter.append("CAUSE_NETWORK_LOST");
                    break;
                default:
                    printWriter.append(String.valueOf(this.xm));
                    break;
            }
            append = printWriter.append(" lastSuspendedTime=");
            j = this.xn;
            valueOf = String.valueOf(simpleDateFormat.format(new Date(this.xn)));
            append.println(new StringBuilder(String.valueOf(valueOf).length() + 21).append(j).append(" ").append(valueOf).toString());
        }
        if (this.xq > 0) {
            printWriter.append(str).append("lastFailedStatus=").append(CommonStatusCodes.getStatusCodeString(this.xp));
            append = printWriter.append(" lastFailedTime=");
            j = this.xq;
            String valueOf2 = String.valueOf(simpleDateFormat.format(new Date(this.xq)));
            append.println(new StringBuilder(String.valueOf(valueOf2).length() + 21).append(j).append(" ").append(valueOf2).toString());
        }
    }

    public Account getAccount() {
        return null;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.zzahv;
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.zzail) {
            z = this.xy == 3;
        }
        return z;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this.zzail) {
            z = this.xy == 2;
        }
        return z;
    }

    @CallSuper
    protected void onConnectionFailed(ConnectionResult connectionResult) {
        this.xp = connectionResult.getErrorCode();
        this.xq = System.currentTimeMillis();
    }

    @CallSuper
    protected void onConnectionSuspended(int i) {
        this.xm = i;
        this.xn = System.currentTimeMillis();
    }

    protected void zza(int i, @Nullable Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(5, i2, -1, new zzk(this, i, bundle)));
    }

    @BinderThread
    protected void zza(int i, IBinder iBinder, Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, i2, -1, new zzj(this, i, iBinder, bundle)));
    }

    @CallSuper
    protected void zza(@NonNull T t) {
        this.xo = System.currentTimeMillis();
    }

    public void zza(@NonNull zzf com_google_android_gms_common_internal_zzd_zzf) {
        this.xu = (zzf) zzab.zzb((Object) com_google_android_gms_common_internal_zzd_zzf, (Object) "Connection progress callbacks cannot be null.");
        zzb(2, null);
    }

    public void zza(zzf com_google_android_gms_common_internal_zzd_zzf, ConnectionResult connectionResult) {
        this.xu = (zzf) zzab.zzb((Object) com_google_android_gms_common_internal_zzd_zzf, (Object) "Connection progress callbacks cannot be null.");
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.xD.get(), connectionResult.getErrorCode(), connectionResult.getResolution()));
    }

    @WorkerThread
    public void zza(zzq com_google_android_gms_common_internal_zzq, Set<Scope> set) {
        try {
            GetServiceRequest zzn = new GetServiceRequest(this.xB).zzhl(this.mContext.getPackageName()).zzn(zzaeu());
            if (set != null) {
                zzn.zzf(set);
            }
            if (zzafk()) {
                zzn.zzd(zzary()).zzb(com_google_android_gms_common_internal_zzq);
            } else if (zzasb()) {
                zzn.zzd(getAccount());
            }
            synchronized (this.xs) {
                if (this.xt != null) {
                    this.xt.zza(new zzg(this, this.xD.get()), zzn);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (DeadObjectException e) {
            Log.w("GmsClient", "service died");
            zzgc(1);
        } catch (Throwable e2) {
            Log.w("GmsClient", "Remote exception occurred", e2);
        }
    }

    protected Bundle zzaeu() {
        return new Bundle();
    }

    public boolean zzafk() {
        return false;
    }

    public boolean zzafz() {
        return false;
    }

    public Intent zzaga() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    public Bundle zzamh() {
        return null;
    }

    public boolean zzanu() {
        return true;
    }

    @Nullable
    public IBinder zzanv() {
        IBinder iBinder;
        synchronized (this.xs) {
            if (this.xt == null) {
                iBinder = null;
            } else {
                iBinder = this.xt.asBinder();
            }
        }
        return iBinder;
    }

    protected String zzart() {
        return GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE;
    }

    @Nullable
    protected final String zzaru() {
        return this.xC == null ? this.mContext.getClass().getName() : this.xC;
    }

    public void zzarx() {
        int isGooglePlayServicesAvailable = this.tz.isGooglePlayServicesAvailable(this.mContext);
        if (isGooglePlayServicesAvailable != 0) {
            zzb(1, null);
            this.xu = new zzi(this);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.xD.get(), isGooglePlayServicesAvailable));
            return;
        }
        zza(new zzi(this));
    }

    public final Account zzary() {
        return getAccount() != null ? getAccount() : new Account("<<default account>>", "com.google");
    }

    protected final void zzarz() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public final T zzasa() throws DeadObjectException {
        T t;
        synchronized (this.zzail) {
            if (this.xy == 4) {
                throw new DeadObjectException();
            }
            zzarz();
            zzab.zza(this.xv != null, (Object) "Client is connected but service is null");
            t = this.xv;
        }
        return t;
    }

    public boolean zzasb() {
        return false;
    }

    protected Set<Scope> zzasc() {
        return Collections.EMPTY_SET;
    }

    @Nullable
    protected abstract T zzbb(IBinder iBinder);

    void zzc(int i, T t) {
    }

    public void zzgc(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, this.xD.get(), i));
    }

    @NonNull
    protected abstract String zzqz();

    @NonNull
    protected abstract String zzra();
}
