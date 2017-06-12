package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepName;

@KeepName
public final class BinderWrapper implements Parcelable {
    public static final Creator<BinderWrapper> CREATOR;
    private IBinder xL;

    /* renamed from: com.google.android.gms.common.internal.BinderWrapper.1 */
    class C01811 implements Creator<BinderWrapper> {
        C01811() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return zzcf(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return zzgd(i);
        }

        public BinderWrapper zzcf(Parcel parcel) {
            return new BinderWrapper(null);
        }

        public BinderWrapper[] zzgd(int i) {
            return new BinderWrapper[i];
        }
    }

    static {
        CREATOR = new C01811();
    }

    public BinderWrapper() {
        this.xL = null;
    }

    public BinderWrapper(IBinder iBinder) {
        this.xL = null;
        this.xL = iBinder;
    }

    private BinderWrapper(Parcel parcel) {
        this.xL = null;
        this.xL = parcel.readStrongBinder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.xL);
    }
}
