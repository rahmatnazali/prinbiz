package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public class SignInRequest extends AbstractSafeParcelable {
    public static final Creator<SignInRequest> CREATOR;
    final ResolveAccountRequest aud;
    final int mVersionCode;

    static {
        CREATOR = new zzh();
    }

    SignInRequest(int i, ResolveAccountRequest resolveAccountRequest) {
        this.mVersionCode = i;
        this.aud = resolveAccountRequest;
    }

    public SignInRequest(ResolveAccountRequest resolveAccountRequest) {
        this(1, resolveAccountRequest);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzh.zza(this, parcel, i);
    }

    public ResolveAccountRequest zzbzy() {
        return this.aud;
    }
}
