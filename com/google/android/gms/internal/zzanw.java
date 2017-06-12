package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Writer;

public final class zzanw {

    private static final class zza extends Writer {
        private final Appendable bfn;
        private final zza bfo;

        static class zza implements CharSequence {
            char[] bfp;

            zza() {
            }

            public char charAt(int i) {
                return this.bfp[i];
            }

            public int length() {
                return this.bfp.length;
            }

            public CharSequence subSequence(int i, int i2) {
                return new String(this.bfp, i, i2 - i);
            }
        }

        private zza(Appendable appendable) {
            this.bfo = new zza();
            this.bfn = appendable;
        }

        public void close() {
        }

        public void flush() {
        }

        public void write(int i) throws IOException {
            this.bfn.append((char) i);
        }

        public void write(char[] cArr, int i, int i2) throws IOException {
            this.bfo.bfp = cArr;
            this.bfn.append(this.bfo, i, i + i2);
        }
    }

    public static Writer zza(Appendable appendable) {
        return appendable instanceof Writer ? (Writer) appendable : new zza(null);
    }

    public static void zzb(zzamv com_google_android_gms_internal_zzamv, zzaoo com_google_android_gms_internal_zzaoo) throws IOException {
        zzaok.bgM.zza(com_google_android_gms_internal_zzaoo, com_google_android_gms_internal_zzamv);
    }

    public static zzamv zzh(zzaom com_google_android_gms_internal_zzaom) throws zzamz {
        Object obj = 1;
        try {
            com_google_android_gms_internal_zzaom.m370b();
            obj = null;
            return (zzamv) zzaok.bgM.zzb(com_google_android_gms_internal_zzaom);
        } catch (Throwable e) {
            if (obj != null) {
                return zzamx.bei;
            }
            throw new zzane(e);
        } catch (Throwable e2) {
            throw new zzane(e2);
        } catch (Throwable e22) {
            throw new zzamw(e22);
        } catch (Throwable e222) {
            throw new zzane(e222);
        }
    }
}
