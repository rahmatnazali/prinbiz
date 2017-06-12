package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public class Flag extends AbstractSafeParcelable implements Comparable<Flag> {
    public static final Creator<Flag> CREATOR;
    private static final Charset UTF_8;
    public static final zza arp;
    final boolean ZV;
    final double ZX;
    final long arl;
    final byte[] arm;
    public final int arn;
    public final int aro;
    final int mVersionCode;
    public final String name;
    final String zD;

    public static class zza implements Comparator<Flag> {
        public /* synthetic */ int compare(Object obj, Object obj2) {
            return zza((Flag) obj, (Flag) obj2);
        }

        public int zza(Flag flag, Flag flag2) {
            return flag.aro == flag2.aro ? flag.name.compareTo(flag2.name) : flag.aro - flag2.aro;
        }
    }

    static {
        CREATOR = new zzb();
        UTF_8 = Charset.forName("UTF-8");
        arp = new zza();
    }

    Flag(int i, String str, long j, boolean z, double d, String str2, byte[] bArr, int i2, int i3) {
        this.mVersionCode = i;
        this.name = str;
        this.arl = j;
        this.ZV = z;
        this.ZX = d;
        this.zD = str2;
        this.arm = bArr;
        this.arn = i2;
        this.aro = i3;
    }

    private static int compare(byte b, byte b2) {
        return b - b2;
    }

    private static int compare(int i, int i2) {
        return i < i2 ? -1 : i == i2 ? 0 : 1;
    }

    private static int compare(long j, long j2) {
        return j < j2 ? -1 : j == j2 ? 0 : 1;
    }

    private static int compare(String str, String str2) {
        return str == str2 ? 0 : str == null ? -1 : str2 == null ? 1 : str.compareTo(str2);
    }

    private static int compare(boolean z, boolean z2) {
        return z == z2 ? 0 : z ? 1 : -1;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return zza((Flag) obj);
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Flag)) {
            return false;
        }
        Flag flag = (Flag) obj;
        if (this.mVersionCode != flag.mVersionCode || !zzaa.equal(this.name, flag.name) || this.arn != flag.arn || this.aro != flag.aro) {
            return false;
        }
        switch (this.arn) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return this.arl == flag.arl;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return this.ZV == flag.ZV;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return this.ZX == flag.ZX;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return zzaa.equal(this.zD, flag.zD);
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                return Arrays.equals(this.arm, flag.arm);
            default:
                throw new AssertionError("Invalid enum value: " + this.arn);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Flag(");
        stringBuilder.append(this.mVersionCode);
        stringBuilder.append(", ");
        stringBuilder.append(this.name);
        stringBuilder.append(", ");
        switch (this.arn) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                stringBuilder.append(this.arl);
                break;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                stringBuilder.append(this.ZV);
                break;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                stringBuilder.append(this.ZX);
                break;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                stringBuilder.append("'");
                stringBuilder.append(this.zD);
                stringBuilder.append("'");
                break;
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                if (this.arm != null) {
                    stringBuilder.append("'");
                    stringBuilder.append(new String(this.arm, UTF_8));
                    stringBuilder.append("'");
                    break;
                }
                stringBuilder.append("null");
                break;
            default:
                throw new AssertionError("Invalid enum value: " + this.arn);
        }
        stringBuilder.append(", ");
        stringBuilder.append(this.arn);
        stringBuilder.append(", ");
        stringBuilder.append(this.aro);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.zza(this, parcel, i);
    }

    public int zza(Flag flag) {
        int i = 0;
        int compareTo = this.name.compareTo(flag.name);
        if (compareTo != 0) {
            return compareTo;
        }
        compareTo = compare(this.arn, flag.arn);
        if (compareTo != 0) {
            return compareTo;
        }
        switch (this.arn) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return compare(this.arl, flag.arl);
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return compare(this.ZV, flag.ZV);
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return Double.compare(this.ZX, flag.ZX);
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return compare(this.zD, flag.zD);
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                if (this.arm == flag.arm) {
                    return 0;
                }
                if (this.arm == null) {
                    return -1;
                }
                if (flag.arm == null) {
                    return 1;
                }
                while (i < Math.min(this.arm.length, flag.arm.length)) {
                    compareTo = compare(this.arm[i], flag.arm[i]);
                    if (compareTo != 0) {
                        return compareTo;
                    }
                    i++;
                }
                return compare(this.arm.length, flag.arm.length);
            default:
                throw new AssertionError("Invalid enum value: " + this.arn);
        }
    }
}
