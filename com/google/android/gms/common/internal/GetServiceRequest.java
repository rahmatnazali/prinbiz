package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzq.zza;
import com.google.android.gms.common.zzc;
import java.util.Collection;

public class GetServiceRequest extends AbstractSafeParcelable {
    public static final Creator<GetServiceRequest> CREATOR;
    final int version;
    Account yA;
    long yB;
    final int yu;
    int yv;
    String yw;
    IBinder yx;
    Scope[] yy;
    Bundle yz;

    static {
        CREATOR = new zzj();
    }

    public GetServiceRequest(int i) {
        this.version = 3;
        this.yv = zzc.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.yu = i;
    }

    GetServiceRequest(int i, int i2, int i3, String str, IBinder iBinder, Scope[] scopeArr, Bundle bundle, Account account, long j) {
        this.version = i;
        this.yu = i2;
        this.yv = i3;
        this.yw = str;
        if (i < 2) {
            this.yA = zzdo(iBinder);
        } else {
            this.yx = iBinder;
            this.yA = account;
        }
        this.yy = scopeArr;
        this.yz = bundle;
        this.yB = j;
    }

    private Account zzdo(IBinder iBinder) {
        return iBinder != null ? zza.zza(zza.zzdp(iBinder)) : null;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzj.zza(this, parcel, i);
    }

    public GetServiceRequest zzb(zzq com_google_android_gms_common_internal_zzq) {
        if (com_google_android_gms_common_internal_zzq != null) {
            this.yx = com_google_android_gms_common_internal_zzq.asBinder();
        }
        return this;
    }

    public GetServiceRequest zzd(Account account) {
        this.yA = account;
        return this;
    }

    public GetServiceRequest zzf(Collection<Scope> collection) {
        this.yy = (Scope[]) collection.toArray(new Scope[collection.size()]);
        return this;
    }

    public GetServiceRequest zzhl(String str) {
        this.yw = str;
        return this;
    }

    public GetServiceRequest zzn(Bundle bundle) {
        this.yz = bundle;
        return this;
    }
}
