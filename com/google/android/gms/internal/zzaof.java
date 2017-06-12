package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public final class zzaof extends zzanh<Object> {
    public static final zzani bfu;
    private final zzamp beq;

    /* renamed from: com.google.android.gms.internal.zzaof.2 */
    static /* synthetic */ class C02062 {
        static final /* synthetic */ int[] bfK;

        static {
            bfK = new int[zzaon.values().length];
            try {
                bfK[zzaon.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                bfK[zzaon.BEGIN_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                bfK[zzaon.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                bfK[zzaon.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                bfK[zzaon.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                bfK[zzaon.NULL.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaof.1 */
    static class C06911 implements zzani {
        C06911() {
        }

        public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
            return com_google_android_gms_internal_zzaol_T.m359m() == Object.class ? new zzaof(null) : null;
        }
    }

    static {
        bfu = new C06911();
    }

    private zzaof(zzamp com_google_android_gms_internal_zzamp) {
        this.beq = com_google_android_gms_internal_zzamp;
    }

    public void zza(zzaoo com_google_android_gms_internal_zzaoo, Object obj) throws IOException {
        if (obj == null) {
            com_google_android_gms_internal_zzaoo.m379l();
            return;
        }
        zzanh zzk = this.beq.zzk(obj.getClass());
        if (zzk instanceof zzaof) {
            com_google_android_gms_internal_zzaoo.m377j();
            com_google_android_gms_internal_zzaoo.m378k();
            return;
        }
        zzk.zza(com_google_android_gms_internal_zzaoo, obj);
    }

    public Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
        switch (C02062.bfK[com_google_android_gms_internal_zzaom.m370b().ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                List arrayList = new ArrayList();
                com_google_android_gms_internal_zzaom.beginArray();
                while (com_google_android_gms_internal_zzaom.hasNext()) {
                    arrayList.add(zzb(com_google_android_gms_internal_zzaom));
                }
                com_google_android_gms_internal_zzaom.endArray();
                return arrayList;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                Map com_google_android_gms_internal_zzant = new zzant();
                com_google_android_gms_internal_zzaom.beginObject();
                while (com_google_android_gms_internal_zzaom.hasNext()) {
                    com_google_android_gms_internal_zzant.put(com_google_android_gms_internal_zzaom.nextName(), zzb(com_google_android_gms_internal_zzaom));
                }
                com_google_android_gms_internal_zzaom.endObject();
                return com_google_android_gms_internal_zzant;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return com_google_android_gms_internal_zzaom.nextString();
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return Double.valueOf(com_google_android_gms_internal_zzaom.nextDouble());
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                return Boolean.valueOf(com_google_android_gms_internal_zzaom.nextBoolean());
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            default:
                throw new IllegalStateException();
        }
    }
}
