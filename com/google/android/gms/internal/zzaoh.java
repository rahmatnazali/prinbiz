package com.google.android.gms.internal;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class zzaoh extends zzanh<Date> {
    public static final zzani bfu;
    private final DateFormat bfU;

    /* renamed from: com.google.android.gms.internal.zzaoh.1 */
    static class C06931 implements zzani {
        C06931() {
        }

        public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
            return com_google_android_gms_internal_zzaol_T.m359m() == Date.class ? new zzaoh() : null;
        }
    }

    static {
        bfu = new C06931();
    }

    public zzaoh() {
        this.bfU = new SimpleDateFormat("MMM d, yyyy");
    }

    public synchronized void zza(zzaoo com_google_android_gms_internal_zzaoo, Date date) throws IOException {
        com_google_android_gms_internal_zzaoo.zzts(date == null ? null : this.bfU.format(date));
    }

    public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
        return zzm(com_google_android_gms_internal_zzaom);
    }

    public synchronized Date zzm(zzaom com_google_android_gms_internal_zzaom) throws IOException {
        Date date;
        if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
            com_google_android_gms_internal_zzaom.nextNull();
            date = null;
        } else {
            try {
                date = new Date(this.bfU.parse(com_google_android_gms_internal_zzaom.nextString()).getTime());
            } catch (Throwable e) {
                throw new zzane(e);
            }
        }
        return date;
    }
}
