package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import org.xmlpull.v1.XmlPullParser;

public final class ConnectionEvent extends StatsEvent {
    public static final Creator<ConnectionEvent> CREATOR;
    private final String Aa;
    private final String Ab;
    private final String Ac;
    private final String Ad;
    private final String Ae;
    private final String Af;
    private final long Ag;
    private final long Ah;
    private long Ai;
    final int mVersionCode;
    private final long zY;
    private int zZ;

    static {
        CREATOR = new zza();
    }

    ConnectionEvent(int i, long j, int i2, String str, String str2, String str3, String str4, String str5, String str6, long j2, long j3) {
        this.mVersionCode = i;
        this.zY = j;
        this.zZ = i2;
        this.Aa = str;
        this.Ab = str2;
        this.Ac = str3;
        this.Ad = str4;
        this.Ai = -1;
        this.Ae = str5;
        this.Af = str6;
        this.Ag = j2;
        this.Ah = j3;
    }

    public ConnectionEvent(long j, int i, String str, String str2, String str3, String str4, String str5, String str6, long j2, long j3) {
        this(1, j, i, str, str2, str3, str4, str5, str6, j2, j3);
    }

    public int getEventType() {
        return this.zZ;
    }

    public long getTimeMillis() {
        return this.zY;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zza.zza(this, parcel, i);
    }

    public String zzaun() {
        return this.Aa;
    }

    public String zzauo() {
        return this.Ab;
    }

    public String zzaup() {
        return this.Ac;
    }

    public String zzauq() {
        return this.Ad;
    }

    public String zzaur() {
        return this.Ae;
    }

    public String zzaus() {
        return this.Af;
    }

    public long zzaut() {
        return this.Ai;
    }

    public long zzauu() {
        return this.Ah;
    }

    public long zzauv() {
        return this.Ag;
    }

    public String zzauw() {
        String valueOf = String.valueOf("\t");
        String valueOf2 = String.valueOf(zzaun());
        String valueOf3 = String.valueOf(zzauo());
        String valueOf4 = String.valueOf("\t");
        String valueOf5 = String.valueOf(zzaup());
        String valueOf6 = String.valueOf(zzauq());
        String valueOf7 = String.valueOf("\t");
        String str = this.Ae == null ? XmlPullParser.NO_NAMESPACE : this.Ae;
        String valueOf8 = String.valueOf("\t");
        return new StringBuilder(((((((((String.valueOf(valueOf).length() + 22) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()) + String.valueOf(valueOf4).length()) + String.valueOf(valueOf5).length()) + String.valueOf(valueOf6).length()) + String.valueOf(valueOf7).length()) + String.valueOf(str).length()) + String.valueOf(valueOf8).length()).append(valueOf).append(valueOf2).append("/").append(valueOf3).append(valueOf4).append(valueOf5).append("/").append(valueOf6).append(valueOf7).append(str).append(valueOf8).append(zzauu()).toString();
    }
}
