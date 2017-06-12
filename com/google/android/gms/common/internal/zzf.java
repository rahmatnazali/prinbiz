package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;

public abstract class zzf {
    public static final zzf xN;
    public static final zzf xO;
    public static final zzf xP;
    public static final zzf xQ;
    public static final zzf xR;
    public static final zzf xS;
    public static final zzf xT;
    public static final zzf xU;
    public static final zzf xV;
    public static final zzf xW;
    public static final zzf xX;
    public static final zzf xY;
    public static final zzf xZ;
    public static final zzf ya;
    public static final zzf yb;

    /* renamed from: com.google.android.gms.common.internal.zzf.11 */
    class AnonymousClass11 extends zzf {
        final /* synthetic */ char yh;

        AnonymousClass11(char c) {
            this.yh = c;
        }

        public zzf zza(zzf com_google_android_gms_common_internal_zzf) {
            return com_google_android_gms_common_internal_zzf.zzd(this.yh) ? com_google_android_gms_common_internal_zzf : super.zza(com_google_android_gms_common_internal_zzf);
        }

        public boolean zzd(char c) {
            return c == this.yh;
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zzf.1 */
    class C06461 extends zzf {
        C06461() {
        }

        public boolean zzd(char c) {
            return Character.isDigit(c);
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zzf.2 */
    class C06472 extends zzf {
        final /* synthetic */ char yc;
        final /* synthetic */ char yd;

        C06472(char c, char c2) {
            this.yc = c;
            this.yd = c2;
        }

        public boolean zzd(char c) {
            return c == this.yc || c == this.yd;
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zzf.3 */
    class C06483 extends zzf {
        final /* synthetic */ char[] ye;

        C06483(char[] cArr) {
            this.ye = cArr;
        }

        public boolean zzd(char c) {
            return Arrays.binarySearch(this.ye, c) >= 0;
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zzf.4 */
    class C06494 extends zzf {
        final /* synthetic */ char yf;
        final /* synthetic */ char yg;

        C06494(char c, char c2) {
            this.yf = c;
            this.yg = c2;
        }

        public boolean zzd(char c) {
            return this.yf <= c && c <= this.yg;
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zzf.5 */
    class C06505 extends zzf {
        C06505() {
        }

        public boolean zzd(char c) {
            return Character.isLetter(c);
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zzf.6 */
    class C06516 extends zzf {
        C06516() {
        }

        public boolean zzd(char c) {
            return Character.isLetterOrDigit(c);
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zzf.7 */
    class C06527 extends zzf {
        C06527() {
        }

        public boolean zzd(char c) {
            return Character.isUpperCase(c);
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zzf.8 */
    class C06538 extends zzf {
        C06538() {
        }

        public boolean zzd(char c) {
            return Character.isLowerCase(c);
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zzf.9 */
    class C06549 extends zzf {
        C06549() {
        }

        public zzf zza(zzf com_google_android_gms_common_internal_zzf) {
            zzab.zzy(com_google_android_gms_common_internal_zzf);
            return this;
        }

        public boolean zzb(CharSequence charSequence) {
            zzab.zzy(charSequence);
            return true;
        }

        public boolean zzd(char c) {
            return true;
        }
    }

    private static class zza extends zzf {
        List<zzf> yi;

        zza(List<zzf> list) {
            this.yi = list;
        }

        public zzf zza(zzf com_google_android_gms_common_internal_zzf) {
            List arrayList = new ArrayList(this.yi);
            arrayList.add((zzf) zzab.zzy(com_google_android_gms_common_internal_zzf));
            return new zza(arrayList);
        }

        public boolean zzd(char c) {
            for (zzf zzd : this.yi) {
                if (zzd.zzd(c)) {
                    return true;
                }
            }
            return false;
        }
    }

    static {
        xN = zza((CharSequence) "\t\n\u000b\f\r \u0085\u1680\u2028\u2029\u205f\u3000\u00a0\u180e\u202f").zza(zza('\u2000', '\u200a'));
        xO = zza((CharSequence) "\t\n\u000b\f\r \u0085\u1680\u2028\u2029\u205f\u3000").zza(zza('\u2000', '\u2006')).zza(zza('\u2008', '\u200a'));
        xP = zza('\u0000', '\u007f');
        zzf zza = zza('0', '9');
        zzf com_google_android_gms_common_internal_zzf = zza;
        for (char c : "\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10".toCharArray()) {
            com_google_android_gms_common_internal_zzf = com_google_android_gms_common_internal_zzf.zza(zza(c, (char) (c + 9)));
        }
        xQ = com_google_android_gms_common_internal_zzf;
        xR = zza('\t', '\r').zza(zza('\u001c', ' ')).zza(zzc('\u1680')).zza(zzc('\u180e')).zza(zza('\u2000', '\u2006')).zza(zza('\u2008', '\u200b')).zza(zza('\u2028', '\u2029')).zza(zzc('\u205f')).zza(zzc('\u3000'));
        xS = new C06461();
        xT = new C06505();
        xU = new C06516();
        xV = new C06527();
        xW = new C06538();
        xX = zza('\u0000', '\u001f').zza(zza('\u007f', '\u009f'));
        xY = zza('\u0000', ' ').zza(zza('\u007f', '\u00a0')).zza(zzc('\u00ad')).zza(zza('\u0600', '\u0603')).zza(zza((CharSequence) "\u06dd\u070f\u1680\u17b4\u17b5\u180e")).zza(zza('\u2000', '\u200f')).zza(zza('\u2028', '\u202f')).zza(zza('\u205f', '\u2064')).zza(zza('\u206a', '\u206f')).zza(zzc('\u3000')).zza(zza('\ud800', '\uf8ff')).zza(zza((CharSequence) "\ufeff\ufff9\ufffa\ufffb"));
        xZ = zza('\u0000', '\u04f9').zza(zzc('\u05be')).zza(zza('\u05d0', '\u05ea')).zza(zzc('\u05f3')).zza(zzc('\u05f4')).zza(zza('\u0600', '\u06ff')).zza(zza('\u0750', '\u077f')).zza(zza('\u0e00', '\u0e7f')).zza(zza('\u1e00', '\u20af')).zza(zza('\u2100', '\u213a')).zza(zza('\ufb50', '\ufdff')).zza(zza('\ufe70', '\ufeff')).zza(zza('\uff61', '\uffdc'));
        ya = new C06549();
        yb = new zzf() {
            public zzf zza(zzf com_google_android_gms_common_internal_zzf) {
                return (zzf) zzab.zzy(com_google_android_gms_common_internal_zzf);
            }

            public boolean zzb(CharSequence charSequence) {
                return charSequence.length() == 0;
            }

            public boolean zzd(char c) {
                return false;
            }
        };
    }

    public static zzf zza(char c, char c2) {
        zzab.zzbo(c2 >= c);
        return new C06494(c, c2);
    }

    public static zzf zza(CharSequence charSequence) {
        switch (charSequence.length()) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                return yb;
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return zzc(charSequence.charAt(0));
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return new C06472(charSequence.charAt(0), charSequence.charAt(1));
            default:
                char[] toCharArray = charSequence.toString().toCharArray();
                Arrays.sort(toCharArray);
                return new C06483(toCharArray);
        }
    }

    public static zzf zzc(char c) {
        return new AnonymousClass11(c);
    }

    public zzf zza(zzf com_google_android_gms_common_internal_zzf) {
        return new zza(Arrays.asList(new zzf[]{this, (zzf) zzab.zzy(com_google_android_gms_common_internal_zzf)}));
    }

    public boolean zzb(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (!zzd(charSequence.charAt(length))) {
                return false;
            }
        }
        return true;
    }

    public abstract boolean zzd(char c);
}
