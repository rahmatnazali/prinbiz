package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import java.lang.reflect.Method;

public class ProviderInstaller {
    public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
    private static final zzc atM;
    private static Method atN;
    private static final Object zzamr;

    /* renamed from: com.google.android.gms.security.ProviderInstaller.1 */
    class C02221 extends AsyncTask<Void, Void, Integer> {
        final /* synthetic */ ProviderInstallListener atO;
        final /* synthetic */ Context zzala;

        C02221(Context context, ProviderInstallListener providerInstallListener) {
            this.zzala = context;
            this.atO = providerInstallListener;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return zzb((Void[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            zzg((Integer) obj);
        }

        protected Integer zzb(Void... voidArr) {
            try {
                ProviderInstaller.installIfNeeded(this.zzala);
                return Integer.valueOf(0);
            } catch (GooglePlayServicesRepairableException e) {
                return Integer.valueOf(e.getConnectionStatusCode());
            } catch (GooglePlayServicesNotAvailableException e2) {
                return Integer.valueOf(e2.errorCode);
            }
        }

        protected void zzg(Integer num) {
            if (num.intValue() == 0) {
                this.atO.onProviderInstalled();
                return;
            }
            this.atO.onProviderInstallFailed(num.intValue(), ProviderInstaller.atM.zza(this.zzala, num.intValue(), "pi"));
        }
    }

    public interface ProviderInstallListener {
        void onProviderInstallFailed(int i, Intent intent);

        void onProviderInstalled();
    }

    static {
        atM = zzc.zzang();
        zzamr = new Object();
        atN = null;
    }

    public static void installIfNeeded(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzab.zzb((Object) context, (Object) "Context must not be null");
        atM.zzbo(context);
        Context remoteContext = zze.getRemoteContext(context);
        if (remoteContext == null) {
            Log.e("ProviderInstaller", "Failed to get remote context");
            throw new GooglePlayServicesNotAvailableException(8);
        }
        synchronized (zzamr) {
            try {
                if (atN == null) {
                    zzdt(remoteContext);
                }
                atN.invoke(null, new Object[]{remoteContext});
            } catch (Exception e) {
                String str = "ProviderInstaller";
                String str2 = "Failed to install provider: ";
                String valueOf = String.valueOf(e.getMessage());
                Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                throw new GooglePlayServicesNotAvailableException(8);
            }
        }
    }

    public static void installIfNeededAsync(Context context, ProviderInstallListener providerInstallListener) {
        zzab.zzb((Object) context, (Object) "Context must not be null");
        zzab.zzb((Object) providerInstallListener, (Object) "Listener must not be null");
        zzab.zzhi("Must be called on the UI thread");
        new C02221(context, providerInstallListener).execute(new Void[0]);
    }

    private static void zzdt(Context context) throws ClassNotFoundException, NoSuchMethodException {
        atN = context.getClassLoader().loadClass("com.google.android.gms.common.security.ProviderInstallerImpl").getMethod("insertProvider", new Class[]{Context.class});
    }
}
