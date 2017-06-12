package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.os.EnvironmentCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzaa.zza;
import com.google.android.gms.common.internal.zzab;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public class PlaceReport extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<PlaceReport> CREATOR;
    private final String adn;
    private final String mTag;
    final int mVersionCode;
    private final String zzcup;

    static {
        CREATOR = new zzi();
    }

    PlaceReport(int i, String str, String str2, String str3) {
        this.mVersionCode = i;
        this.adn = str;
        this.mTag = str2;
        this.zzcup = str3;
    }

    public static PlaceReport create(String str, String str2) {
        return zzk(str, str2, EnvironmentCompat.MEDIA_UNKNOWN);
    }

    public static PlaceReport zzk(String str, String str2, String str3) {
        zzab.zzy(str);
        zzab.zzhr(str2);
        zzab.zzhr(str3);
        zzab.zzb(zzkp(str3), (Object) "Invalid source");
        return new PlaceReport(1, str, str2, str3);
    }

    private static boolean zzkp(String str) {
        boolean z = true;
        switch (str.hashCode()) {
            case -1436706272:
                if (str.equals("inferredGeofencing")) {
                    z = true;
                    break;
                }
                break;
            case -1194968642:
                if (str.equals("userReported")) {
                    z = true;
                    break;
                }
                break;
            case -284840886:
                if (str.equals(EnvironmentCompat.MEDIA_UNKNOWN)) {
                    z = false;
                    break;
                }
                break;
            case -262743844:
                if (str.equals("inferredReverseGeocoding")) {
                    z = true;
                    break;
                }
                break;
            case 1164924125:
                if (str.equals("inferredSnappedToRoad")) {
                    z = true;
                    break;
                }
                break;
            case 1287171955:
                if (str.equals("inferredRadioSignals")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                return true;
            default:
                return false;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PlaceReport)) {
            return false;
        }
        PlaceReport placeReport = (PlaceReport) obj;
        return zzaa.equal(this.adn, placeReport.adn) && zzaa.equal(this.mTag, placeReport.mTag) && zzaa.equal(this.zzcup, placeReport.zzcup);
    }

    public String getPlaceId() {
        return this.adn;
    }

    public String getSource() {
        return this.zzcup;
    }

    public String getTag() {
        return this.mTag;
    }

    public int hashCode() {
        return zzaa.hashCode(this.adn, this.mTag, this.zzcup);
    }

    public String toString() {
        zza zzx = zzaa.zzx(this);
        zzx.zzg("placeId", this.adn);
        zzx.zzg("tag", this.mTag);
        if (!EnvironmentCompat.MEDIA_UNKNOWN.equals(this.zzcup)) {
            zzx.zzg("source", this.zzcup);
        }
        return zzx.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }
}
