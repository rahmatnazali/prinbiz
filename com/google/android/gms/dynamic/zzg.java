package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.zze;

public abstract class zzg<T> {
    private final String KD;
    private T KE;

    public static class zza extends Exception {
        public zza(String str) {
            super(str);
        }

        public zza(String str, Throwable th) {
            super(str, th);
        }
    }

    protected zzg(String str) {
        this.KD = str;
    }

    protected abstract T zzc(IBinder iBinder);

    protected final T zzcr(Context context) throws zza {
        if (this.KE == null) {
            zzab.zzy(context);
            Context remoteContext = zze.getRemoteContext(context);
            if (remoteContext == null) {
                throw new zza("Could not get remote context.");
            }
            try {
                this.KE = zzc((IBinder) remoteContext.getClassLoader().loadClass(this.KD).newInstance());
            } catch (Throwable e) {
                throw new zza("Could not load creator class.", e);
            } catch (Throwable e2) {
                throw new zza("Could not instantiate creator.", e2);
            } catch (Throwable e22) {
                throw new zza("Could not access creator.", e22);
            }
        }
        return this.KE;
    }
}
