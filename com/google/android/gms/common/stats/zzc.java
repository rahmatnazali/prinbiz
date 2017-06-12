package com.google.android.gms.common.stats;

import com.google.android.gms.internal.zzqz;
import org.xmlpull.v1.XmlPullParser;

public final class zzc {
    public static zzqz<Integer> Ar;
    public static zzqz<Integer> As;

    public static final class zza {
        public static zzqz<Integer> At;
        public static zzqz<String> Au;
        public static zzqz<String> Av;
        public static zzqz<String> Aw;
        public static zzqz<String> Ax;
        public static zzqz<Long> Ay;

        static {
            At = zzqz.zza("gms:common:stats:connections:level", Integer.valueOf(zzd.LOG_LEVEL_OFF));
            Au = zzqz.zzab("gms:common:stats:connections:ignored_calling_processes", XmlPullParser.NO_NAMESPACE);
            Av = zzqz.zzab("gms:common:stats:connections:ignored_calling_services", XmlPullParser.NO_NAMESPACE);
            Aw = zzqz.zzab("gms:common:stats:connections:ignored_target_processes", XmlPullParser.NO_NAMESPACE);
            Ax = zzqz.zzab("gms:common:stats:connections:ignored_target_services", "com.google.android.gms.auth.GetToken");
            Ay = zzqz.zza("gms:common:stats:connections:time_out_duration", Long.valueOf(600000));
        }
    }

    public static final class zzb {
        public static zzqz<Integer> At;
        public static zzqz<Long> Ay;

        static {
            At = zzqz.zza("gms:common:stats:wakeLocks:level", Integer.valueOf(zzd.LOG_LEVEL_OFF));
            Ay = zzqz.zza("gms:common:stats:wakelocks:time_out_duration", Long.valueOf(600000));
        }
    }

    static {
        Ar = zzqz.zza("gms:common:stats:max_num_of_events", Integer.valueOf(100));
        As = zzqz.zza("gms:common:stats:max_chunk_size", Integer.valueOf(100));
    }
}
