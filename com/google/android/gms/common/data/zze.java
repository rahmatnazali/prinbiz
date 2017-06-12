package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.ftp.FTPClient;

public class zze implements Creator<DataHolder> {
    static void zza(DataHolder dataHolder, Parcel parcel, int i) {
        int zzcn = zzb.zzcn(parcel);
        zzb.zza(parcel, 1, dataHolder.zzari(), false);
        zzb.zza(parcel, 2, dataHolder.zzarj(), i, false);
        zzb.zzc(parcel, 3, dataHolder.getStatusCode());
        zzb.zza(parcel, 4, dataHolder.zzarc(), false);
        zzb.zzc(parcel, DNSConstants.PROBE_CONFLICT_INTERVAL, dataHolder.getVersionCode());
        zzb.zzaj(parcel, zzcn);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzcc(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzfv(i);
    }

    public DataHolder zzcc(Parcel parcel) {
        int i = 0;
        Bundle bundle = null;
        int zzcm = zza.zzcm(parcel);
        CursorWindow[] cursorWindowArr = null;
        String[] strArr = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzcm) {
            int zzcl = zza.zzcl(parcel);
            switch (zza.zzgm(zzcl)) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    strArr = zza.zzac(parcel, zzcl);
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    cursorWindowArr = (CursorWindow[]) zza.zzb(parcel, zzcl, CursorWindow.CREATOR);
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    i = zza.zzg(parcel, zzcl);
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    bundle = zza.zzs(parcel, zzcl);
                    break;
                case DNSConstants.PROBE_CONFLICT_INTERVAL /*1000*/:
                    i2 = zza.zzg(parcel, zzcl);
                    break;
                default:
                    zza.zzb(parcel, zzcl);
                    break;
            }
        }
        if (parcel.dataPosition() != zzcm) {
            throw new zza.zza("Overread allowed size end=" + zzcm, parcel);
        }
        DataHolder dataHolder = new DataHolder(i2, strArr, cursorWindowArr, i, bundle);
        dataHolder.zzarh();
        return dataHolder;
    }

    public DataHolder[] zzfv(int i) {
        return new DataHolder[i];
    }
}
