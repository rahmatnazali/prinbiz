package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.internal.zzua;
import java.util.concurrent.Callable;

public class zzb {
    private static SharedPreferences Pc;

    /* renamed from: com.google.android.gms.flags.impl.zzb.1 */
    class C01881 implements Callable<SharedPreferences> {
        final /* synthetic */ Context zzala;

        C01881(Context context) {
            this.zzala = context;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzbex();
        }

        public SharedPreferences zzbex() {
            return this.zzala.getSharedPreferences("google_sdk_flags", 1);
        }
    }

    static {
        Pc = null;
    }

    public static SharedPreferences zzn(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (SharedPreferences.class) {
            if (Pc == null) {
                Pc = (SharedPreferences) zzua.zzb(new C01881(context));
            }
            sharedPreferences = Pc;
        }
        return sharedPreferences;
    }
}
