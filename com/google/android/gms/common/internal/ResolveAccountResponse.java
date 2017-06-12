package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzq.zza;

public class ResolveAccountResponse extends AbstractSafeParcelable {
    public static final Creator<ResolveAccountResponse> CREATOR;
    final int mVersionCode;
    private ConnectionResult rF;
    private boolean tL;
    IBinder xj;
    private boolean zj;

    static {
        CREATOR = new zzad();
    }

    ResolveAccountResponse(int i, IBinder iBinder, ConnectionResult connectionResult, boolean z, boolean z2) {
        this.mVersionCode = i;
        this.xj = iBinder;
        this.rF = connectionResult;
        this.tL = z;
        this.zj = z2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResolveAccountResponse)) {
            return false;
        }
        ResolveAccountResponse resolveAccountResponse = (ResolveAccountResponse) obj;
        return this.rF.equals(resolveAccountResponse.rF) && zzatg().equals(resolveAccountResponse.zzatg());
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzad.zza(this, parcel, i);
    }

    public zzq zzatg() {
        return zza.zzdp(this.xj);
    }

    public ConnectionResult zzath() {
        return this.rF;
    }

    public boolean zzati() {
        return this.tL;
    }

    public boolean zzatj() {
        return this.zj;
    }
}
