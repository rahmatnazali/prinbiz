package com.google.android.gms.internal;

import java.io.Reader;
import java.io.StringReader;

public final class zzana {
    public zzamv zza(Reader reader) throws zzamw, zzane {
        try {
            zzaom com_google_android_gms_internal_zzaom = new zzaom(reader);
            zzamv zzh = zzh(com_google_android_gms_internal_zzaom);
            if (zzh.zzczj() || com_google_android_gms_internal_zzaom.m370b() == zzaon.END_DOCUMENT) {
                return zzh;
            }
            throw new zzane("Did not consume the entire document.");
        } catch (Throwable e) {
            throw new zzane(e);
        } catch (Throwable e2) {
            throw new zzamw(e2);
        } catch (Throwable e22) {
            throw new zzane(e22);
        }
    }

    public zzamv zzh(zzaom com_google_android_gms_internal_zzaom) throws zzamw, zzane {
        String valueOf;
        boolean isLenient = com_google_android_gms_internal_zzaom.isLenient();
        com_google_android_gms_internal_zzaom.setLenient(true);
        try {
            zzamv zzh = zzanw.zzh(com_google_android_gms_internal_zzaom);
            com_google_android_gms_internal_zzaom.setLenient(isLenient);
            return zzh;
        } catch (Throwable e) {
            valueOf = String.valueOf(com_google_android_gms_internal_zzaom);
            throw new zzamz(new StringBuilder(String.valueOf(valueOf).length() + 36).append("Failed parsing JSON source: ").append(valueOf).append(" to Json").toString(), e);
        } catch (Throwable e2) {
            valueOf = String.valueOf(com_google_android_gms_internal_zzaom);
            throw new zzamz(new StringBuilder(String.valueOf(valueOf).length() + 36).append("Failed parsing JSON source: ").append(valueOf).append(" to Json").toString(), e2);
        } catch (Throwable th) {
            com_google_android_gms_internal_zzaom.setLenient(isLenient);
        }
    }

    public zzamv zztp(String str) throws zzane {
        return zza(new StringReader(str));
    }
}
