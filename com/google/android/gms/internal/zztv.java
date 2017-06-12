package com.google.android.gms.internal;

import android.os.RemoteException;

public abstract class zztv<T> {
    private final int zzaxo;
    private final String zzaxp;
    private final T zzaxq;

    public static class zza extends zztv<Boolean> {
        public zza(int i, String str, Boolean bool) {
            super(str, bool, null);
        }

        public /* synthetic */ Object zza(zzty com_google_android_gms_internal_zzty) {
            return zzb(com_google_android_gms_internal_zzty);
        }

        public Boolean zzb(zzty com_google_android_gms_internal_zzty) {
            try {
                return Boolean.valueOf(com_google_android_gms_internal_zzty.getBooleanFlagValue(getKey(), ((Boolean) zzjw()).booleanValue(), getSource()));
            } catch (RemoteException e) {
                return (Boolean) zzjw();
            }
        }
    }

    public static class zzb extends zztv<Integer> {
        public zzb(int i, String str, Integer num) {
            super(str, num, null);
        }

        public /* synthetic */ Object zza(zzty com_google_android_gms_internal_zzty) {
            return zzc(com_google_android_gms_internal_zzty);
        }

        public Integer zzc(zzty com_google_android_gms_internal_zzty) {
            try {
                return Integer.valueOf(com_google_android_gms_internal_zzty.getIntFlagValue(getKey(), ((Integer) zzjw()).intValue(), getSource()));
            } catch (RemoteException e) {
                return (Integer) zzjw();
            }
        }
    }

    public static class zzc extends zztv<Long> {
        public zzc(int i, String str, Long l) {
            super(str, l, null);
        }

        public /* synthetic */ Object zza(zzty com_google_android_gms_internal_zzty) {
            return zzd(com_google_android_gms_internal_zzty);
        }

        public Long zzd(zzty com_google_android_gms_internal_zzty) {
            try {
                return Long.valueOf(com_google_android_gms_internal_zzty.getLongFlagValue(getKey(), ((Long) zzjw()).longValue(), getSource()));
            } catch (RemoteException e) {
                return (Long) zzjw();
            }
        }
    }

    public static class zzd extends zztv<String> {
        public zzd(int i, String str, String str2) {
            super(str, str2, null);
        }

        public /* synthetic */ Object zza(zzty com_google_android_gms_internal_zzty) {
            return zze(com_google_android_gms_internal_zzty);
        }

        public String zze(zzty com_google_android_gms_internal_zzty) {
            try {
                return com_google_android_gms_internal_zzty.getStringFlagValue(getKey(), (String) zzjw(), getSource());
            } catch (RemoteException e) {
                return (String) zzjw();
            }
        }
    }

    private zztv(int i, String str, T t) {
        this.zzaxo = i;
        this.zzaxp = str;
        this.zzaxq = t;
        zztz.zzbet().zza(this);
    }

    public static zza zzb(int i, String str, Boolean bool) {
        return new zza(i, str, bool);
    }

    public static zzb zzb(int i, String str, int i2) {
        return new zzb(i, str, Integer.valueOf(i2));
    }

    public static zzc zzb(int i, String str, long j) {
        return new zzc(i, str, Long.valueOf(j));
    }

    public static zzd zzc(int i, String str, String str2) {
        return new zzd(i, str, str2);
    }

    public T get() {
        return zztz.zzbeu().zzb(this);
    }

    public String getKey() {
        return this.zzaxp;
    }

    public int getSource() {
        return this.zzaxo;
    }

    protected abstract T zza(zzty com_google_android_gms_internal_zzty);

    public T zzjw() {
        return this.zzaxq;
    }
}
