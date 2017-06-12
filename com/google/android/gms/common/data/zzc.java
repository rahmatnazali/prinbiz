package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzab;

public abstract class zzc {
    protected final DataHolder tu;
    protected int vX;
    private int vY;

    public zzc(DataHolder dataHolder, int i) {
        this.tu = (DataHolder) zzab.zzy(dataHolder);
        zzfq(i);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        zzc com_google_android_gms_common_data_zzc = (zzc) obj;
        return zzaa.equal(Integer.valueOf(com_google_android_gms_common_data_zzc.vX), Integer.valueOf(this.vX)) && zzaa.equal(Integer.valueOf(com_google_android_gms_common_data_zzc.vY), Integer.valueOf(this.vY)) && com_google_android_gms_common_data_zzc.tu == this.tu;
    }

    protected boolean getBoolean(String str) {
        return this.tu.zze(str, this.vX, this.vY);
    }

    protected byte[] getByteArray(String str) {
        return this.tu.zzg(str, this.vX, this.vY);
    }

    protected float getFloat(String str) {
        return this.tu.zzf(str, this.vX, this.vY);
    }

    protected int getInteger(String str) {
        return this.tu.zzc(str, this.vX, this.vY);
    }

    protected long getLong(String str) {
        return this.tu.zzb(str, this.vX, this.vY);
    }

    protected String getString(String str) {
        return this.tu.zzd(str, this.vX, this.vY);
    }

    public int hashCode() {
        return zzaa.hashCode(Integer.valueOf(this.vX), Integer.valueOf(this.vY), this.tu);
    }

    public boolean isDataValid() {
        return !this.tu.isClosed();
    }

    protected void zza(String str, CharArrayBuffer charArrayBuffer) {
        this.tu.zza(str, this.vX, this.vY, charArrayBuffer);
    }

    protected int zzarf() {
        return this.vX;
    }

    protected void zzfq(int i) {
        boolean z = i >= 0 && i < this.tu.getCount();
        zzab.zzbn(z);
        this.vX = i;
        this.vY = this.tu.zzfs(this.vX);
    }

    public boolean zzhe(String str) {
        return this.tu.zzhe(str);
    }

    protected Uri zzhf(String str) {
        return this.tu.zzh(str, this.vX, this.vY);
    }

    protected boolean zzhg(String str) {
        return this.tu.zzi(str, this.vX, this.vY);
    }
}
