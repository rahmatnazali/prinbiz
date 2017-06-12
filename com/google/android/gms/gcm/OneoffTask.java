package com.google.android.gms.gcm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;

public class OneoffTask extends Task {
    public static final Creator<OneoffTask> CREATOR;
    private final long Zw;
    private final long Zx;

    /* renamed from: com.google.android.gms.gcm.OneoffTask.1 */
    class C01921 implements Creator<OneoffTask> {
        C01921() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return zzmi(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return zzso(i);
        }

        public OneoffTask zzmi(Parcel parcel) {
            return new OneoffTask(null);
        }

        public OneoffTask[] zzso(int i) {
            return new OneoffTask[i];
        }
    }

    public static class Builder extends com.google.android.gms.gcm.Task.Builder {
        private long Zy;
        private long Zz;

        public Builder() {
            this.Zy = -1;
            this.Zz = -1;
            this.isPersisted = false;
        }

        public OneoffTask build() {
            checkConditions();
            return new OneoffTask();
        }

        protected void checkConditions() {
            super.checkConditions();
            if (this.Zy == -1 || this.Zz == -1) {
                throw new IllegalArgumentException("Must specify an execution window using setExecutionWindow.");
            } else if (this.Zy >= this.Zz) {
                throw new IllegalArgumentException("Window start must be shorter than window end.");
            }
        }

        public Builder setExecutionWindow(long j, long j2) {
            this.Zy = j;
            this.Zz = j2;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.extras = bundle;
            return this;
        }

        public Builder setPersisted(boolean z) {
            this.isPersisted = z;
            return this;
        }

        public Builder setRequiredNetwork(int i) {
            this.requiredNetworkState = i;
            return this;
        }

        public Builder setRequiresCharging(boolean z) {
            this.requiresCharging = z;
            return this;
        }

        public Builder setService(Class<? extends GcmTaskService> cls) {
            this.gcmTaskService = cls.getName();
            return this;
        }

        public Builder setTag(String str) {
            this.tag = str;
            return this;
        }

        public Builder setUpdateCurrent(boolean z) {
            this.updateCurrent = z;
            return this;
        }
    }

    static {
        CREATOR = new C01921();
    }

    @Deprecated
    private OneoffTask(Parcel parcel) {
        super(parcel);
        this.Zw = parcel.readLong();
        this.Zx = parcel.readLong();
    }

    private OneoffTask(Builder builder) {
        super((com.google.android.gms.gcm.Task.Builder) builder);
        this.Zw = builder.Zy;
        this.Zx = builder.Zz;
    }

    public long getWindowEnd() {
        return this.Zx;
    }

    public long getWindowStart() {
        return this.Zw;
    }

    public void toBundle(Bundle bundle) {
        super.toBundle(bundle);
        bundle.putLong("window_start", this.Zw);
        bundle.putLong("window_end", this.Zx);
    }

    public String toString() {
        String valueOf = String.valueOf(super.toString());
        long windowStart = getWindowStart();
        return new StringBuilder(String.valueOf(valueOf).length() + 64).append(valueOf).append(" windowStart=").append(windowStart).append(" windowEnd=").append(getWindowEnd()).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeLong(this.Zw);
        parcel.writeLong(this.Zx);
    }
}
