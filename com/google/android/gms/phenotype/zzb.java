package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public class zzb implements Creator<Flag> {
    static void zza(Flag flag, Parcel parcel, int i) {
        int zzcn = com.google.android.gms.common.internal.safeparcel.zzb.zzcn(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, flag.mVersionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, flag.name, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, flag.arl);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, flag.ZV);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, flag.ZX);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, flag.zD, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, flag.arm, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 8, flag.arn);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 9, flag.aro);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj(parcel, zzcn);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzqy(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzyf(i);
    }

    public Flag zzqy(Parcel parcel) {
        byte[] bArr = null;
        int i = 0;
        int zzcm = zza.zzcm(parcel);
        long j = 0;
        double d = 0.0d;
        int i2 = 0;
        String str = null;
        boolean z = false;
        String str2 = null;
        int i3 = 0;
        while (parcel.dataPosition() < zzcm) {
            int zzcl = zza.zzcl(parcel);
            switch (zza.zzgm(zzcl)) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    i3 = zza.zzg(parcel, zzcl);
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    str2 = zza.zzq(parcel, zzcl);
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    j = zza.zzi(parcel, zzcl);
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    z = zza.zzc(parcel, zzcl);
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    d = zza.zzn(parcel, zzcl);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    str = zza.zzq(parcel, zzcl);
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    bArr = zza.zzt(parcel, zzcl);
                    break;
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    i2 = zza.zzg(parcel, zzcl);
                    break;
                case ConnectionResult.SERVICE_INVALID /*9*/:
                    i = zza.zzg(parcel, zzcl);
                    break;
                default:
                    zza.zzb(parcel, zzcl);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcm) {
            return new Flag(i3, str2, j, z, d, str, bArr, i2, i);
        }
        throw new zza.zza("Overread allowed size end=" + zzcm, parcel);
    }

    public Flag[] zzyf(int i) {
        return new Flag[i];
    }
}
