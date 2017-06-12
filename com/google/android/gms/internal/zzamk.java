package com.google.android.gms.internal;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

final class zzamk implements zzamu<Date>, zzand<Date> {
    private final DateFormat bdE;
    private final DateFormat bdF;
    private final DateFormat bdG;

    zzamk() {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    public zzamk(int i, int i2) {
        this(DateFormat.getDateTimeInstance(i, i2, Locale.US), DateFormat.getDateTimeInstance(i, i2));
    }

    zzamk(String str) {
        this(new SimpleDateFormat(str, Locale.US), new SimpleDateFormat(str));
    }

    zzamk(DateFormat dateFormat, DateFormat dateFormat2) {
        this.bdE = dateFormat;
        this.bdF = dateFormat2;
        this.bdG = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        this.bdG.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private Date zza(zzamv com_google_android_gms_internal_zzamv) {
        Date parse;
        synchronized (this.bdF) {
            try {
                parse = this.bdF.parse(com_google_android_gms_internal_zzamv.zzczf());
            } catch (ParseException e) {
                try {
                    parse = this.bdE.parse(com_google_android_gms_internal_zzamv.zzczf());
                } catch (ParseException e2) {
                    try {
                        parse = this.bdG.parse(com_google_android_gms_internal_zzamv.zzczf());
                    } catch (Throwable e3) {
                        throw new zzane(com_google_android_gms_internal_zzamv.zzczf(), e3);
                    }
                }
            }
        }
        return parse;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(zzamk.class.getSimpleName());
        stringBuilder.append('(').append(this.bdF.getClass().getSimpleName()).append(')');
        return stringBuilder.toString();
    }

    public zzamv zza(Date date, Type type, zzanc com_google_android_gms_internal_zzanc) {
        zzamv com_google_android_gms_internal_zzanb;
        synchronized (this.bdF) {
            com_google_android_gms_internal_zzanb = new zzanb(this.bdE.format(date));
        }
        return com_google_android_gms_internal_zzanb;
    }

    public Date zza(zzamv com_google_android_gms_internal_zzamv, Type type, zzamt com_google_android_gms_internal_zzamt) throws zzamz {
        if (com_google_android_gms_internal_zzamv instanceof zzanb) {
            Date zza = zza(com_google_android_gms_internal_zzamv);
            if (type == Date.class) {
                return zza;
            }
            if (type == Timestamp.class) {
                return new Timestamp(zza.getTime());
            }
            if (type == java.sql.Date.class) {
                return new java.sql.Date(zza.getTime());
            }
            String valueOf = String.valueOf(getClass());
            String valueOf2 = String.valueOf(type);
            throw new IllegalArgumentException(new StringBuilder((String.valueOf(valueOf).length() + 23) + String.valueOf(valueOf2).length()).append(valueOf).append(" cannot deserialize to ").append(valueOf2).toString());
        }
        throw new zzamz("The date should be a string value");
    }

    public /* synthetic */ Object zzb(zzamv com_google_android_gms_internal_zzamv, Type type, zzamt com_google_android_gms_internal_zzamt) throws zzamz {
        return zza(com_google_android_gms_internal_zzamv, type, com_google_android_gms_internal_zzamt);
    }
}
