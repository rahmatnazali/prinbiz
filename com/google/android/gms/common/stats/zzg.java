package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public class zzg implements Creator<WakeLockEvent> {
    static void zza(WakeLockEvent wakeLockEvent, Parcel parcel, int i) {
        int zzcn = zzb.zzcn(parcel);
        zzb.zzc(parcel, 1, wakeLockEvent.mVersionCode);
        zzb.zza(parcel, 2, wakeLockEvent.getTimeMillis());
        zzb.zza(parcel, 4, wakeLockEvent.zzauz(), false);
        zzb.zzc(parcel, 5, wakeLockEvent.zzavc());
        zzb.zzb(parcel, 6, wakeLockEvent.zzavd(), false);
        zzb.zza(parcel, 8, wakeLockEvent.zzauv());
        zzb.zza(parcel, 10, wakeLockEvent.zzava(), false);
        zzb.zzc(parcel, 11, wakeLockEvent.getEventType());
        zzb.zza(parcel, 12, wakeLockEvent.zzaus(), false);
        zzb.zza(parcel, 13, wakeLockEvent.zzavf(), false);
        zzb.zzc(parcel, 14, wakeLockEvent.zzave());
        zzb.zza(parcel, 15, wakeLockEvent.zzavg());
        zzb.zza(parcel, 16, wakeLockEvent.zzavh());
        zzb.zza(parcel, 17, wakeLockEvent.zzavb(), false);
        zzb.zzaj(parcel, zzcn);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzcy(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzgy(i);
    }

    public WakeLockEvent zzcy(Parcel parcel) {
        int zzcm = zza.zzcm(parcel);
        int i = 0;
        long j = 0;
        int i2 = 0;
        String str = null;
        int i3 = 0;
        List list = null;
        String str2 = null;
        long j2 = 0;
        int i4 = 0;
        String str3 = null;
        String str4 = null;
        float f = 0.0f;
        long j3 = 0;
        String str5 = null;
        while (parcel.dataPosition() < zzcm) {
            int zzcl = zza.zzcl(parcel);
            switch (zza.zzgm(zzcl)) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    i = zza.zzg(parcel, zzcl);
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    j = zza.zzi(parcel, zzcl);
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    str = zza.zzq(parcel, zzcl);
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    i3 = zza.zzg(parcel, zzcl);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    list = zza.zzae(parcel, zzcl);
                    break;
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    j2 = zza.zzi(parcel, zzcl);
                    break;
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    str3 = zza.zzq(parcel, zzcl);
                    break;
                case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                    i2 = zza.zzg(parcel, zzcl);
                    break;
                case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                    str2 = zza.zzq(parcel, zzcl);
                    break;
                case ConnectionResult.CANCELED /*13*/:
                    str4 = zza.zzq(parcel, zzcl);
                    break;
                case ConnectionResult.TIMEOUT /*14*/:
                    i4 = zza.zzg(parcel, zzcl);
                    break;
                case ConnectionResult.INTERRUPTED /*15*/:
                    f = zza.zzl(parcel, zzcl);
                    break;
                case ConnectionResult.API_UNAVAILABLE /*16*/:
                    j3 = zza.zzi(parcel, zzcl);
                    break;
                case ConnectionResult.SIGN_IN_FAILED /*17*/:
                    str5 = zza.zzq(parcel, zzcl);
                    break;
                default:
                    zza.zzb(parcel, zzcl);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcm) {
            return new WakeLockEvent(i, j, i2, str, i3, list, str2, j2, i4, str3, str4, f, j3, str5);
        }
        throw new zza.zza("Overread allowed size end=" + zzcm, parcel);
    }

    public WakeLockEvent[] zzgy(int i) {
        return new WakeLockEvent[i];
    }
}
