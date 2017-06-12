package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

public final class WakeLockEvent extends StatsEvent {
    public static final Creator<WakeLockEvent> CREATOR;
    private final String AK;
    private final String AL;
    private final String AM;
    private final int AN;
    private final List<String> AO;
    private final String AP;
    private int AQ;
    private final String AR;
    private final float AS;
    private final long Ag;
    private long Ai;
    private final long mTimeout;
    final int mVersionCode;
    private final long zY;
    private int zZ;

    static {
        CREATOR = new zzg();
    }

    WakeLockEvent(int i, long j, int i2, String str, int i3, List<String> list, String str2, long j2, int i4, String str3, String str4, float f, long j3, String str5) {
        this.mVersionCode = i;
        this.zY = j;
        this.zZ = i2;
        this.AK = str;
        this.AL = str3;
        this.AM = str5;
        this.AN = i3;
        this.Ai = -1;
        this.AO = list;
        this.AP = str2;
        this.Ag = j2;
        this.AQ = i4;
        this.AR = str4;
        this.AS = f;
        this.mTimeout = j3;
    }

    public WakeLockEvent(long j, int i, String str, int i2, List<String> list, String str2, long j2, int i3, String str3, String str4, float f, long j3, String str5) {
        this(2, j, i, str, i2, list, str2, j2, i3, str3, str4, f, j3, str5);
    }

    public int getEventType() {
        return this.zZ;
    }

    public long getTimeMillis() {
        return this.zY;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzg.zza(this, parcel, i);
    }

    public String zzaus() {
        return this.AP;
    }

    public long zzaut() {
        return this.Ai;
    }

    public long zzauv() {
        return this.Ag;
    }

    public String zzauw() {
        String valueOf = String.valueOf("\t");
        String valueOf2 = String.valueOf(zzauz());
        String valueOf3 = String.valueOf("\t");
        int zzavc = zzavc();
        String valueOf4 = String.valueOf("\t");
        String join = zzavd() == null ? XmlPullParser.NO_NAMESPACE : TextUtils.join(",", zzavd());
        String valueOf5 = String.valueOf("\t");
        int zzave = zzave();
        String valueOf6 = String.valueOf("\t");
        String zzava = zzava() == null ? XmlPullParser.NO_NAMESPACE : zzava();
        String valueOf7 = String.valueOf("\t");
        String zzavf = zzavf() == null ? XmlPullParser.NO_NAMESPACE : zzavf();
        String valueOf8 = String.valueOf("\t");
        float zzavg = zzavg();
        String valueOf9 = String.valueOf("\t");
        String zzavb = zzavb() == null ? XmlPullParser.NO_NAMESPACE : zzavb();
        return new StringBuilder(((((((((((((String.valueOf(valueOf).length() + 37) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()) + String.valueOf(valueOf4).length()) + String.valueOf(join).length()) + String.valueOf(valueOf5).length()) + String.valueOf(valueOf6).length()) + String.valueOf(zzava).length()) + String.valueOf(valueOf7).length()) + String.valueOf(zzavf).length()) + String.valueOf(valueOf8).length()) + String.valueOf(valueOf9).length()) + String.valueOf(zzavb).length()).append(valueOf).append(valueOf2).append(valueOf3).append(zzavc).append(valueOf4).append(join).append(valueOf5).append(zzave).append(valueOf6).append(zzava).append(valueOf7).append(zzavf).append(valueOf8).append(zzavg).append(valueOf9).append(zzavb).toString();
    }

    public String zzauz() {
        return this.AK;
    }

    public String zzava() {
        return this.AL;
    }

    public String zzavb() {
        return this.AM;
    }

    public int zzavc() {
        return this.AN;
    }

    public List<String> zzavd() {
        return this.AO;
    }

    public int zzave() {
        return this.AQ;
    }

    public String zzavf() {
        return this.AR;
    }

    public float zzavg() {
        return this.AS;
    }

    public long zzavh() {
        return this.mTimeout;
    }
}
