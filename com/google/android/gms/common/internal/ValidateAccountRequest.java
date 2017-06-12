package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

@Deprecated
public class ValidateAccountRequest extends AbstractSafeParcelable {
    public static final Creator<ValidateAccountRequest> CREATOR;
    final int mVersionCode;
    private final Scope[] ry;
    final IBinder xj;
    private final int zq;
    private final Bundle zr;
    private final String zs;

    static {
        CREATOR = new zzaj();
    }

    ValidateAccountRequest(int i, int i2, IBinder iBinder, Scope[] scopeArr, Bundle bundle, String str) {
        this.mVersionCode = i;
        this.zq = i2;
        this.xj = iBinder;
        this.ry = scopeArr;
        this.zr = bundle;
        this.zs = str;
    }

    public String getCallingPackage() {
        return this.zs;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzaj.zza(this, parcel, i);
    }

    public Scope[] zzatm() {
        return this.ry;
    }

    public int zzato() {
        return this.zq;
    }

    public Bundle zzatp() {
        return this.zr;
    }
}
