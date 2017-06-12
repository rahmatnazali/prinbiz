package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public class SignInButtonConfig extends AbstractSafeParcelable {
    public static final Creator<SignInButtonConfig> CREATOR;
    final int mVersionCode;
    private final Scope[] ry;
    private final int zk;
    private final int zl;

    static {
        CREATOR = new zzae();
    }

    SignInButtonConfig(int i, int i2, int i3, Scope[] scopeArr) {
        this.mVersionCode = i;
        this.zk = i2;
        this.zl = i3;
        this.ry = scopeArr;
    }

    public SignInButtonConfig(int i, int i2, Scope[] scopeArr) {
        this(1, i, i2, scopeArr);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzae.zza(this, parcel, i);
    }

    public int zzatk() {
        return this.zk;
    }

    public int zzatl() {
        return this.zl;
    }

    public Scope[] zzatm() {
        return this.ry;
    }
}
