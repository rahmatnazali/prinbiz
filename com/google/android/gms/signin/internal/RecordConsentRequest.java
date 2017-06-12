package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public class RecordConsentRequest extends AbstractSafeParcelable {
    public static final Creator<RecordConsentRequest> CREATOR;
    private final Account aL;
    private final Scope[] aua;
    private final String dR;
    final int mVersionCode;

    static {
        CREATOR = new zzf();
    }

    RecordConsentRequest(int i, Account account, Scope[] scopeArr, String str) {
        this.mVersionCode = i;
        this.aL = account;
        this.aua = scopeArr;
        this.dR = str;
    }

    public Account getAccount() {
        return this.aL;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzf.zza(this, parcel, i);
    }

    public String zzafu() {
        return this.dR;
    }

    public Scope[] zzbzw() {
        return this.aua;
    }
}
