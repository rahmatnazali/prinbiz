package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Api.zzh;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzg.zza;
import com.google.android.gms.internal.zzpk;
import com.google.android.gms.internal.zzpm;
import com.google.android.gms.internal.zzpp;
import com.google.android.gms.internal.zzpy;
import com.google.android.gms.internal.zzqi;
import com.google.android.gms.internal.zzqn;
import com.google.android.gms.internal.zzqt;
import com.google.android.gms.internal.zzqx;
import com.google.android.gms.internal.zzvt;
import com.google.android.gms.internal.zzvu;
import com.google.android.gms.internal.zzvv;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GoogleApiClient {
    public static final int SIGN_IN_MODE_OPTIONAL = 2;
    public static final int SIGN_IN_MODE_REQUIRED = 1;
    private static final Set<GoogleApiClient> rW;

    public static final class Builder {
        private Account aL;
        private String bX;
        private final Context mContext;
        private final Set<Scope> rX;
        private final Set<Scope> rY;
        private int rZ;
        private View sa;
        private String sb;
        private final Map<Api<?>, zza> sc;
        private final Map<Api<?>, ApiOptions> sd;
        private zzqi se;
        private int sf;
        private OnConnectionFailedListener sg;
        private GoogleApiAvailability sh;
        private Api.zza<? extends zzvu, zzvv> si;
        private final ArrayList<ConnectionCallbacks> sj;
        private final ArrayList<OnConnectionFailedListener> sk;
        private Looper zzahv;

        public Builder(@NonNull Context context) {
            this.rX = new HashSet();
            this.rY = new HashSet();
            this.sc = new ArrayMap();
            this.sd = new ArrayMap();
            this.sf = -1;
            this.sh = GoogleApiAvailability.getInstance();
            this.si = zzvt.bK;
            this.sj = new ArrayList();
            this.sk = new ArrayList();
            this.mContext = context;
            this.zzahv = context.getMainLooper();
            this.bX = context.getPackageName();
            this.sb = context.getClass().getName();
        }

        public Builder(@NonNull Context context, @NonNull ConnectionCallbacks connectionCallbacks, @NonNull OnConnectionFailedListener onConnectionFailedListener) {
            this(context);
            zzab.zzb((Object) connectionCallbacks, (Object) "Must provide a connected listener");
            this.sj.add(connectionCallbacks);
            zzab.zzb((Object) onConnectionFailedListener, (Object) "Must provide a connection failed listener");
            this.sk.add(onConnectionFailedListener);
        }

        private static <C extends zze, O> C zza(Api.zza<C, O> com_google_android_gms_common_api_Api_zza_C__O, Object obj, Context context, Looper looper, zzg com_google_android_gms_common_internal_zzg, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return com_google_android_gms_common_api_Api_zza_C__O.zza(context, looper, com_google_android_gms_common_internal_zzg, obj, connectionCallbacks, onConnectionFailedListener);
        }

        private Builder zza(@NonNull zzqi com_google_android_gms_internal_zzqi, int i, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            zzab.zzb(i >= 0, (Object) "clientId must be non-negative");
            this.sf = i;
            this.sg = onConnectionFailedListener;
            this.se = com_google_android_gms_internal_zzqi;
            return this;
        }

        private static <C extends Api.zzg, O> zzah zza(zzh<C, O> com_google_android_gms_common_api_Api_zzh_C__O, Object obj, Context context, Looper looper, zzg com_google_android_gms_common_internal_zzg, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzah(context, looper, com_google_android_gms_common_api_Api_zzh_C__O.zzanw(), connectionCallbacks, onConnectionFailedListener, com_google_android_gms_common_internal_zzg, com_google_android_gms_common_api_Api_zzh_C__O.zzr(obj));
        }

        private <O extends ApiOptions> void zza(Api<O> api, O o, int i, Scope... scopeArr) {
            boolean z = true;
            int i2 = 0;
            if (i != GoogleApiClient.SIGN_IN_MODE_REQUIRED) {
                if (i == GoogleApiClient.SIGN_IN_MODE_OPTIONAL) {
                    z = false;
                } else {
                    throw new IllegalArgumentException("Invalid resolution mode: '" + i + "', use a constant from GoogleApiClient.ResolutionMode");
                }
            }
            Set hashSet = new HashSet(api.zzanp().zzp(o));
            int length = scopeArr.length;
            while (i2 < length) {
                hashSet.add(scopeArr[i2]);
                i2 += GoogleApiClient.SIGN_IN_MODE_REQUIRED;
            }
            this.sc.put(api, new zza(hashSet, z));
        }

        private GoogleApiClient zzaoi() {
            zzg zzaoh = zzaoh();
            Api api = null;
            Map zzasl = zzaoh.zzasl();
            Map arrayMap = new ArrayMap();
            Map arrayMap2 = new ArrayMap();
            ArrayList arrayList = new ArrayList();
            Api api2 = null;
            for (Api api3 : this.sd.keySet()) {
                Api api32;
                zze zza;
                Api api4;
                Object obj = this.sd.get(api32);
                int i = 0;
                if (zzasl.get(api32) != null) {
                    i = ((zza) zzasl.get(api32)).yn ? GoogleApiClient.SIGN_IN_MODE_REQUIRED : GoogleApiClient.SIGN_IN_MODE_OPTIONAL;
                }
                arrayMap.put(api32, Integer.valueOf(i));
                ConnectionCallbacks com_google_android_gms_internal_zzpp = new zzpp(api32, i);
                arrayList.add(com_google_android_gms_internal_zzpp);
                Api api5;
                if (api32.zzant()) {
                    zzh zzanr = api32.zzanr();
                    api5 = zzanr.getPriority() == GoogleApiClient.SIGN_IN_MODE_REQUIRED ? api32 : api2;
                    zza = zza(zzanr, obj, this.mContext, this.zzahv, zzaoh, com_google_android_gms_internal_zzpp, (OnConnectionFailedListener) com_google_android_gms_internal_zzpp);
                    api4 = api5;
                } else {
                    Api.zza zzanq = api32.zzanq();
                    api5 = zzanq.getPriority() == GoogleApiClient.SIGN_IN_MODE_REQUIRED ? api32 : api2;
                    zza = zza(zzanq, obj, this.mContext, this.zzahv, zzaoh, com_google_android_gms_internal_zzpp, (OnConnectionFailedListener) com_google_android_gms_internal_zzpp);
                    api4 = api5;
                }
                arrayMap2.put(api32.zzans(), zza);
                if (!zza.zzafz()) {
                    api32 = api;
                } else if (api != null) {
                    String valueOf = String.valueOf(api32.getName());
                    String valueOf2 = String.valueOf(api.getName());
                    throw new IllegalStateException(new StringBuilder((String.valueOf(valueOf).length() + 21) + String.valueOf(valueOf2).length()).append(valueOf).append(" cannot be used with ").append(valueOf2).toString());
                }
                api2 = api4;
                api = api32;
            }
            if (api != null) {
                if (api2 != null) {
                    valueOf = String.valueOf(api.getName());
                    valueOf2 = String.valueOf(api2.getName());
                    throw new IllegalStateException(new StringBuilder((String.valueOf(valueOf).length() + 21) + String.valueOf(valueOf2).length()).append(valueOf).append(" cannot be used with ").append(valueOf2).toString());
                }
                boolean z = this.aL == null;
                Object[] objArr = new Object[GoogleApiClient.SIGN_IN_MODE_REQUIRED];
                objArr[0] = api.getName();
                zzab.zza(z, "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead", objArr);
                objArr = new Object[GoogleApiClient.SIGN_IN_MODE_REQUIRED];
                objArr[0] = api.getName();
                zzab.zza(this.rX.equals(this.rY), "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.", objArr);
            }
            return new zzpy(this.mContext, new ReentrantLock(), this.zzahv, zzaoh, this.sh, this.si, arrayMap, this.sj, this.sk, arrayMap2, this.sf, zzpy.zza(arrayMap2.values(), true), arrayList);
        }

        private void zzf(GoogleApiClient googleApiClient) {
            zzpk.zza(this.se).zza(this.sf, googleApiClient, this.sg);
        }

        public Builder addApi(@NonNull Api<? extends NotRequiredOptions> api) {
            zzab.zzb((Object) api, (Object) "Api must not be null");
            this.sd.put(api, null);
            Collection zzp = api.zzanp().zzp(null);
            this.rY.addAll(zzp);
            this.rX.addAll(zzp);
            return this;
        }

        public <O extends HasOptions> Builder addApi(@NonNull Api<O> api, @NonNull O o) {
            zzab.zzb((Object) api, (Object) "Api must not be null");
            zzab.zzb((Object) o, (Object) "Null options are not permitted for this Api");
            this.sd.put(api, o);
            Collection zzp = api.zzanp().zzp(o);
            this.rY.addAll(zzp);
            this.rX.addAll(zzp);
            return this;
        }

        public <O extends HasOptions> Builder addApiIfAvailable(@NonNull Api<O> api, @NonNull O o, Scope... scopeArr) {
            zzab.zzb((Object) api, (Object) "Api must not be null");
            zzab.zzb((Object) o, (Object) "Null options are not permitted for this Api");
            this.sd.put(api, o);
            zza(api, o, GoogleApiClient.SIGN_IN_MODE_REQUIRED, scopeArr);
            return this;
        }

        public Builder addApiIfAvailable(@NonNull Api<? extends NotRequiredOptions> api, Scope... scopeArr) {
            zzab.zzb((Object) api, (Object) "Api must not be null");
            this.sd.put(api, null);
            zza(api, null, GoogleApiClient.SIGN_IN_MODE_REQUIRED, scopeArr);
            return this;
        }

        public Builder addConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
            zzab.zzb((Object) connectionCallbacks, (Object) "Listener must not be null");
            this.sj.add(connectionCallbacks);
            return this;
        }

        public Builder addOnConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
            zzab.zzb((Object) onConnectionFailedListener, (Object) "Listener must not be null");
            this.sk.add(onConnectionFailedListener);
            return this;
        }

        public Builder addScope(@NonNull Scope scope) {
            zzab.zzb((Object) scope, (Object) "Scope must not be null");
            this.rX.add(scope);
            return this;
        }

        public GoogleApiClient build() {
            zzab.zzb(!this.sd.isEmpty(), (Object) "must call addApi() to add at least one API");
            GoogleApiClient zzaoi = zzaoi();
            synchronized (GoogleApiClient.rW) {
                GoogleApiClient.rW.add(zzaoi);
            }
            if (this.sf >= 0) {
                zzf(zzaoi);
            }
            return zzaoi;
        }

        public Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, int i, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            return zza(new zzqi(fragmentActivity), i, onConnectionFailedListener);
        }

        public Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            return enableAutoManage(fragmentActivity, 0, onConnectionFailedListener);
        }

        public Builder setAccountName(String str) {
            this.aL = str == null ? null : new Account(str, "com.google");
            return this;
        }

        public Builder setGravityForPopups(int i) {
            this.rZ = i;
            return this;
        }

        public Builder setHandler(@NonNull Handler handler) {
            zzab.zzb((Object) handler, (Object) "Handler must not be null");
            this.zzahv = handler.getLooper();
            return this;
        }

        public Builder setViewForPopups(@NonNull View view) {
            zzab.zzb((Object) view, (Object) "View must not be null");
            this.sa = view;
            return this;
        }

        public Builder useDefaultAccount() {
            return setAccountName("<<default account>>");
        }

        public zzg zzaoh() {
            zzvv com_google_android_gms_internal_zzvv = zzvv.atR;
            if (this.sd.containsKey(zzvt.API)) {
                com_google_android_gms_internal_zzvv = (zzvv) this.sd.get(zzvt.API);
            }
            return new zzg(this.aL, this.rX, this.sc, this.rZ, this.sa, this.bX, this.sb, com_google_android_gms_internal_zzvv);
        }
    }

    public interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        void onConnected(@Nullable Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface OnConnectionFailedListener {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    static {
        rW = Collections.newSetFromMap(new WeakHashMap());
    }

    public static void dumpAll(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (rW) {
            String concat = String.valueOf(str).concat("  ");
            int i = 0;
            for (GoogleApiClient googleApiClient : rW) {
                int i2 = i + SIGN_IN_MODE_REQUIRED;
                printWriter.append(str).append("GoogleApiClient#").println(i);
                googleApiClient.dump(concat, fileDescriptor, printWriter, strArr);
                i = i2;
            }
        }
    }

    public static Set<GoogleApiClient> zzaoe() {
        Set<GoogleApiClient> set;
        synchronized (rW) {
            set = rW;
        }
        return set;
    }

    public abstract ConnectionResult blockingConnect();

    public abstract ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit);

    public abstract PendingResult<Status> clearDefaultAccountAndReconnect();

    public abstract void connect();

    public void connect(int i) {
        throw new UnsupportedOperationException();
    }

    public abstract void disconnect();

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    @NonNull
    public abstract ConnectionResult getConnectionResult(@NonNull Api<?> api);

    public Context getContext() {
        throw new UnsupportedOperationException();
    }

    public Looper getLooper() {
        throw new UnsupportedOperationException();
    }

    public abstract boolean hasConnectedApi(@NonNull Api<?> api);

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public abstract void reconnect();

    public abstract void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public abstract void stopAutoManage(@NonNull FragmentActivity fragmentActivity);

    public abstract void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    @NonNull
    public <C extends zze> C zza(@NonNull zzc<C> com_google_android_gms_common_api_Api_zzc_C) {
        throw new UnsupportedOperationException();
    }

    public void zza(zzqx com_google_android_gms_internal_zzqx) {
        throw new UnsupportedOperationException();
    }

    public boolean zza(@NonNull Api<?> api) {
        throw new UnsupportedOperationException();
    }

    public boolean zza(zzqt com_google_android_gms_internal_zzqt) {
        throw new UnsupportedOperationException();
    }

    public void zzaof() {
        throw new UnsupportedOperationException();
    }

    public void zzb(zzqx com_google_android_gms_internal_zzqx) {
        throw new UnsupportedOperationException();
    }

    public <A extends zzb, R extends Result, T extends zzpm.zza<R, A>> T zzc(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    public <A extends zzb, T extends zzpm.zza<? extends Result, A>> T zzd(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    public <L> zzqn<L> zzs(@NonNull L l) {
        throw new UnsupportedOperationException();
    }
}
