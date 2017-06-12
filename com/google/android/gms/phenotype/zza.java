package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import org.apache.commons.net.ftp.FTPClient;

public class zza implements Creator<Configuration> {
    static void zza(Configuration configuration, Parcel parcel, int i) {
        int zzcn = zzb.zzcn(parcel);
        zzb.zzc(parcel, 1, configuration.mVersionCode);
        zzb.zzc(parcel, 2, configuration.arh);
        zzb.zza(parcel, 3, configuration.ari, i, false);
        zzb.zza(parcel, 4, configuration.arj, false);
        zzb.zzaj(parcel, zzcn);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzqx(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzye(i);
    }

    public Configuration zzqx(Parcel parcel) {
        String[] strArr = null;
        int i = 0;
        int zzcm = com.google.android.gms.common.internal.safeparcel.zza.zzcm(parcel);
        Flag[] flagArr = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzcm) {
            Flag[] flagArr2;
            int i3;
            String[] strArr2;
            int zzcl = com.google.android.gms.common.internal.safeparcel.zza.zzcl(parcel);
            String[] strArr3;
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzgm(zzcl)) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    strArr3 = strArr;
                    flagArr2 = flagArr;
                    i3 = i;
                    i = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, zzcl);
                    strArr2 = strArr3;
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    i = i2;
                    Flag[] flagArr3 = flagArr;
                    i3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, zzcl);
                    strArr2 = strArr;
                    flagArr2 = flagArr3;
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    i3 = i;
                    i = i2;
                    strArr3 = strArr;
                    flagArr2 = (Flag[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, zzcl, Flag.CREATOR);
                    strArr2 = strArr3;
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    strArr2 = com.google.android.gms.common.internal.safeparcel.zza.zzac(parcel, zzcl);
                    flagArr2 = flagArr;
                    i3 = i;
                    i = i2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, zzcl);
                    strArr2 = strArr;
                    flagArr2 = flagArr;
                    i3 = i;
                    i = i2;
                    break;
            }
            i2 = i;
            i = i3;
            flagArr = flagArr2;
            strArr = strArr2;
        }
        if (parcel.dataPosition() == zzcm) {
            return new Configuration(i2, i, flagArr, strArr);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + zzcm, parcel);
    }

    public Configuration[] zzye(int i) {
        return new Configuration[i];
    }
}
