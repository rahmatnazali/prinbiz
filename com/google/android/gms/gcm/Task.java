package com.google.android.gms.gcm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.util.Log;
import com.google.android.gms.common.internal.zzab;

public abstract class Task implements Parcelable {
    public static final int EXTRAS_LIMIT_BYTES = 10240;
    public static final int NETWORK_STATE_ANY = 2;
    public static final int NETWORK_STATE_CONNECTED = 0;
    public static final int NETWORK_STATE_UNMETERED = 1;
    protected static final long UNINITIALIZED = -1;
    private final String ZH;
    private final boolean ZI;
    private final boolean ZJ;
    private final int ZK;
    private final boolean ZL;
    private final zzc ZM;
    private final Bundle mExtras;
    private final String mTag;

    public static abstract class Builder {
        protected zzc ZN;
        protected Bundle extras;
        protected String gcmTaskService;
        protected boolean isPersisted;
        protected int requiredNetworkState;
        protected boolean requiresCharging;
        protected String tag;
        protected boolean updateCurrent;

        public Builder() {
            this.ZN = zzc.ZC;
        }

        public abstract Task build();

        @CallSuper
        protected void checkConditions() {
            zzab.zzb(this.gcmTaskService != null, (Object) "Must provide an endpoint for this task by calling setService(ComponentName).");
            GcmNetworkManager.zzjx(this.tag);
            Task.zza(this.ZN);
            if (this.isPersisted) {
                Task.zzaj(this.extras);
            }
        }

        public abstract Builder setExtras(Bundle bundle);

        public abstract Builder setPersisted(boolean z);

        public abstract Builder setRequiredNetwork(int i);

        public abstract Builder setRequiresCharging(boolean z);

        public abstract Builder setService(Class<? extends GcmTaskService> cls);

        public abstract Builder setTag(String str);

        public abstract Builder setUpdateCurrent(boolean z);
    }

    @Deprecated
    Task(Parcel parcel) {
        boolean z = true;
        Log.e("Task", "Constructing a Task object using a parcel.");
        this.ZH = parcel.readString();
        this.mTag = parcel.readString();
        this.ZI = parcel.readInt() == NETWORK_STATE_UNMETERED;
        if (parcel.readInt() != NETWORK_STATE_UNMETERED) {
            z = false;
        }
        this.ZJ = z;
        this.ZK = NETWORK_STATE_ANY;
        this.ZL = false;
        this.ZM = zzc.ZC;
        this.mExtras = null;
    }

    Task(Builder builder) {
        this.ZH = builder.gcmTaskService;
        this.mTag = builder.tag;
        this.ZI = builder.updateCurrent;
        this.ZJ = builder.isPersisted;
        this.ZK = builder.requiredNetworkState;
        this.ZL = builder.requiresCharging;
        this.mExtras = builder.extras;
        this.ZM = builder.ZN != null ? builder.ZN : zzc.ZC;
    }

    public static void zza(zzc com_google_android_gms_gcm_zzc) {
        if (com_google_android_gms_gcm_zzc != null) {
            int zzblj = com_google_android_gms_gcm_zzc.zzblj();
            if (zzblj == NETWORK_STATE_UNMETERED || zzblj == 0) {
                int zzblk = com_google_android_gms_gcm_zzc.zzblk();
                int zzbll = com_google_android_gms_gcm_zzc.zzbll();
                if (zzblj == 0 && zzblk < 0) {
                    throw new IllegalArgumentException("InitialBackoffSeconds can't be negative: " + zzblk);
                } else if (zzblj == NETWORK_STATE_UNMETERED && zzblk < 10) {
                    throw new IllegalArgumentException("RETRY_POLICY_LINEAR must have an initial backoff at least 10 seconds.");
                } else if (zzbll < zzblk) {
                    throw new IllegalArgumentException("MaximumBackoffSeconds must be greater than InitialBackoffSeconds: " + com_google_android_gms_gcm_zzc.zzbll());
                } else {
                    return;
                }
            }
            throw new IllegalArgumentException("Must provide a valid RetryPolicy: " + zzblj);
        }
    }

    private static boolean zzad(Object obj) {
        return (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Double) || (obj instanceof String) || (obj instanceof Boolean);
    }

    public static void zzaj(Bundle bundle) {
        if (bundle != null) {
            Parcel obtain = Parcel.obtain();
            bundle.writeToParcel(obtain, NETWORK_STATE_CONNECTED);
            int dataSize = obtain.dataSize();
            if (dataSize > EXTRAS_LIMIT_BYTES) {
                obtain.recycle();
                String valueOf = String.valueOf("Extras exceeding maximum size(10240 bytes): ");
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 11).append(valueOf).append(dataSize).toString());
            }
            obtain.recycle();
            for (String str : bundle.keySet()) {
                if (!zzad(bundle.get(str))) {
                    throw new IllegalArgumentException("Only the following extra parameter types are supported: Integer, Long, Double, String, and Boolean. ");
                }
            }
        }
    }

    public int describeContents() {
        return NETWORK_STATE_CONNECTED;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public int getRequiredNetwork() {
        return this.ZK;
    }

    public boolean getRequiresCharging() {
        return this.ZL;
    }

    public String getServiceName() {
        return this.ZH;
    }

    public String getTag() {
        return this.mTag;
    }

    public boolean isPersisted() {
        return this.ZJ;
    }

    public boolean isUpdateCurrent() {
        return this.ZI;
    }

    public void toBundle(Bundle bundle) {
        bundle.putString("tag", this.mTag);
        bundle.putBoolean("update_current", this.ZI);
        bundle.putBoolean("persisted", this.ZJ);
        bundle.putString(NotificationCompatApi24.CATEGORY_SERVICE, this.ZH);
        bundle.putInt("requiredNetwork", this.ZK);
        bundle.putBoolean("requiresCharging", this.ZL);
        bundle.putBundle("retryStrategy", this.ZM.zzai(new Bundle()));
        bundle.putBundle("extras", this.mExtras);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = NETWORK_STATE_UNMETERED;
        parcel.writeString(this.ZH);
        parcel.writeString(this.mTag);
        parcel.writeInt(this.ZI ? NETWORK_STATE_UNMETERED : NETWORK_STATE_CONNECTED);
        if (!this.ZJ) {
            i2 = NETWORK_STATE_CONNECTED;
        }
        parcel.writeInt(i2);
    }
}
