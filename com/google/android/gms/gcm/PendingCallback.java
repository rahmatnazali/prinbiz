package com.google.android.gms.gcm;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;

public class PendingCallback implements Parcelable, ReflectedParcelable {
    public static final Creator<PendingCallback> CREATOR;
    final IBinder xL;

    /* renamed from: com.google.android.gms.gcm.PendingCallback.1 */
    class C01931 implements Creator<PendingCallback> {
        C01931() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return zzmj(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return zzsp(i);
        }

        public PendingCallback zzmj(Parcel parcel) {
            return new PendingCallback(parcel);
        }

        public PendingCallback[] zzsp(int i) {
            return new PendingCallback[i];
        }
    }

    static {
        CREATOR = new C01931();
    }

    public PendingCallback(Parcel parcel) {
        this.xL = parcel.readStrongBinder();
    }

    public int describeContents() {
        return 0;
    }

    public IBinder getIBinder() {
        return this.xL;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.xL);
    }
}
