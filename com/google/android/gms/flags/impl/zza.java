package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import com.google.android.gms.internal.zzua;
import java.util.concurrent.Callable;

public abstract class zza<T> {

    public static class zza extends zza<Boolean> {

        /* renamed from: com.google.android.gms.flags.impl.zza.zza.1 */
        class C01841 implements Callable<Boolean> {
            final /* synthetic */ SharedPreferences OW;
            final /* synthetic */ String OX;
            final /* synthetic */ Boolean OY;

            C01841(SharedPreferences sharedPreferences, String str, Boolean bool) {
                this.OW = sharedPreferences;
                this.OX = str;
                this.OY = bool;
            }

            public /* synthetic */ Object call() throws Exception {
                return zztn();
            }

            public Boolean zztn() {
                return Boolean.valueOf(this.OW.getBoolean(this.OX, this.OY.booleanValue()));
            }
        }

        public static Boolean zza(SharedPreferences sharedPreferences, String str, Boolean bool) {
            return (Boolean) zzua.zzb(new C01841(sharedPreferences, str, bool));
        }
    }

    public static class zzb extends zza<Integer> {

        /* renamed from: com.google.android.gms.flags.impl.zza.zzb.1 */
        class C01851 implements Callable<Integer> {
            final /* synthetic */ SharedPreferences OW;
            final /* synthetic */ String OX;
            final /* synthetic */ Integer OZ;

            C01851(SharedPreferences sharedPreferences, String str, Integer num) {
                this.OW = sharedPreferences;
                this.OX = str;
                this.OZ = num;
            }

            public /* synthetic */ Object call() throws Exception {
                return zzbev();
            }

            public Integer zzbev() {
                return Integer.valueOf(this.OW.getInt(this.OX, this.OZ.intValue()));
            }
        }

        public static Integer zza(SharedPreferences sharedPreferences, String str, Integer num) {
            return (Integer) zzua.zzb(new C01851(sharedPreferences, str, num));
        }
    }

    public static class zzc extends zza<Long> {

        /* renamed from: com.google.android.gms.flags.impl.zza.zzc.1 */
        class C01861 implements Callable<Long> {
            final /* synthetic */ SharedPreferences OW;
            final /* synthetic */ String OX;
            final /* synthetic */ Long Pa;

            C01861(SharedPreferences sharedPreferences, String str, Long l) {
                this.OW = sharedPreferences;
                this.OX = str;
                this.Pa = l;
            }

            public /* synthetic */ Object call() throws Exception {
                return zzbew();
            }

            public Long zzbew() {
                return Long.valueOf(this.OW.getLong(this.OX, this.Pa.longValue()));
            }
        }

        public static Long zza(SharedPreferences sharedPreferences, String str, Long l) {
            return (Long) zzua.zzb(new C01861(sharedPreferences, str, l));
        }
    }

    public static class zzd extends zza<String> {

        /* renamed from: com.google.android.gms.flags.impl.zza.zzd.1 */
        class C01871 implements Callable<String> {
            final /* synthetic */ SharedPreferences OW;
            final /* synthetic */ String OX;
            final /* synthetic */ String Pb;

            C01871(SharedPreferences sharedPreferences, String str, String str2) {
                this.OW = sharedPreferences;
                this.OX = str;
                this.Pb = str2;
            }

            public /* synthetic */ Object call() throws Exception {
                return zzaba();
            }

            public String zzaba() {
                return this.OW.getString(this.OX, this.Pb);
            }
        }

        public static String zza(SharedPreferences sharedPreferences, String str, String str2) {
            return (String) zzua.zzb(new C01871(sharedPreferences, str, str2));
        }
    }
}
