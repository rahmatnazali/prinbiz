package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zzd;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class zzqy {
    private static final com.google.android.gms.internal.zzpm.zza<?, ?>[] vF;
    private final Map<com.google.android.gms.common.api.Api.zzc<?>, zze> ui;
    final Set<com.google.android.gms.internal.zzpm.zza<?, ?>> vG;
    private final zzb vH;
    private zzc vI;

    interface zzb {
        void zzh(com.google.android.gms.internal.zzpm.zza<?, ?> com_google_android_gms_internal_zzpm_zza___);
    }

    interface zzc {
        void zzaqn();
    }

    /* renamed from: com.google.android.gms.internal.zzqy.1 */
    class C07171 implements zzb {
        final /* synthetic */ zzqy vJ;

        C07171(zzqy com_google_android_gms_internal_zzqy) {
            this.vJ = com_google_android_gms_internal_zzqy;
        }

        public void zzh(com.google.android.gms.internal.zzpm.zza<?, ?> com_google_android_gms_internal_zzpm_zza___) {
            this.vJ.vG.remove(com_google_android_gms_internal_zzpm_zza___);
            if (!(com_google_android_gms_internal_zzpm_zza___.zzaoj() == null || null == null)) {
                null.remove(com_google_android_gms_internal_zzpm_zza___.zzaoj().intValue());
            }
            if (this.vJ.vI != null && this.vJ.vG.isEmpty()) {
                this.vJ.vI.zzaqn();
            }
        }
    }

    private static class zza implements DeathRecipient, zzb {
        private final WeakReference<com.google.android.gms.internal.zzpm.zza<?, ?>> vK;
        private final WeakReference<zzd> vL;
        private final WeakReference<IBinder> vM;

        private zza(com.google.android.gms.internal.zzpm.zza<?, ?> com_google_android_gms_internal_zzpm_zza___, zzd com_google_android_gms_common_api_zzd, IBinder iBinder) {
            this.vL = new WeakReference(com_google_android_gms_common_api_zzd);
            this.vK = new WeakReference(com_google_android_gms_internal_zzpm_zza___);
            this.vM = new WeakReference(iBinder);
        }

        private void zzaqg() {
            com.google.android.gms.internal.zzpm.zza com_google_android_gms_internal_zzpm_zza = (com.google.android.gms.internal.zzpm.zza) this.vK.get();
            zzd com_google_android_gms_common_api_zzd = (zzd) this.vL.get();
            if (!(com_google_android_gms_common_api_zzd == null || com_google_android_gms_internal_zzpm_zza == null)) {
                com_google_android_gms_common_api_zzd.remove(com_google_android_gms_internal_zzpm_zza.zzaoj().intValue());
            }
            IBinder iBinder = (IBinder) this.vM.get();
            if (this.vM != null) {
                iBinder.unlinkToDeath(this, 0);
            }
        }

        public void binderDied() {
            zzaqg();
        }

        public void zzh(com.google.android.gms.internal.zzpm.zza<?, ?> com_google_android_gms_internal_zzpm_zza___) {
            zzaqg();
        }
    }

    static {
        vF = new com.google.android.gms.internal.zzpm.zza[0];
    }

    public zzqy(com.google.android.gms.common.api.Api.zzc<?> com_google_android_gms_common_api_Api_zzc_, zze com_google_android_gms_common_api_Api_zze) {
        this.vG = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
        this.vH = new C07171(this);
        this.vI = null;
        this.ui = new ArrayMap();
        this.ui.put(com_google_android_gms_common_api_Api_zzc_, com_google_android_gms_common_api_Api_zze);
    }

    public zzqy(Map<com.google.android.gms.common.api.Api.zzc<?>, zze> map) {
        this.vG = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
        this.vH = new C07171(this);
        this.vI = null;
        this.ui = map;
    }

    private static void zza(com.google.android.gms.internal.zzpm.zza<?, ?> com_google_android_gms_internal_zzpm_zza___, zzd com_google_android_gms_common_api_zzd, IBinder iBinder) {
        if (com_google_android_gms_internal_zzpm_zza___.isReady()) {
            com_google_android_gms_internal_zzpm_zza___.zza(new zza(com_google_android_gms_common_api_zzd, iBinder, null));
        } else if (iBinder == null || !iBinder.isBinderAlive()) {
            com_google_android_gms_internal_zzpm_zza___.zza(null);
            com_google_android_gms_internal_zzpm_zza___.cancel();
            com_google_android_gms_common_api_zzd.remove(com_google_android_gms_internal_zzpm_zza___.zzaoj().intValue());
        } else {
            zzb com_google_android_gms_internal_zzqy_zza = new zza(com_google_android_gms_common_api_zzd, iBinder, null);
            com_google_android_gms_internal_zzpm_zza___.zza(com_google_android_gms_internal_zzqy_zza);
            try {
                iBinder.linkToDeath(com_google_android_gms_internal_zzqy_zza, 0);
            } catch (RemoteException e) {
                com_google_android_gms_internal_zzpm_zza___.cancel();
                com_google_android_gms_common_api_zzd.remove(com_google_android_gms_internal_zzpm_zza___.zzaoj().intValue());
            }
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.vG.size());
    }

    public void release() {
        for (com.google.android.gms.internal.zzpm.zza com_google_android_gms_internal_zzpm_zza : (com.google.android.gms.internal.zzpm.zza[]) this.vG.toArray(vF)) {
            com_google_android_gms_internal_zzpm_zza.zza(null);
            if (com_google_android_gms_internal_zzpm_zza.zzaoj() != null) {
                com_google_android_gms_internal_zzpm_zza.zzaor();
                zza(com_google_android_gms_internal_zzpm_zza, null, ((zze) this.ui.get(com_google_android_gms_internal_zzpm_zza.zzans())).zzanv());
                this.vG.remove(com_google_android_gms_internal_zzpm_zza);
            } else if (com_google_android_gms_internal_zzpm_zza.zzaov()) {
                this.vG.remove(com_google_android_gms_internal_zzpm_zza);
            }
        }
    }

    public void zza(zzc com_google_android_gms_internal_zzqy_zzc) {
        if (this.vG.isEmpty()) {
            com_google_android_gms_internal_zzqy_zzc.zzaqn();
        }
        this.vI = com_google_android_gms_internal_zzqy_zzc;
    }

    public void zzaqz() {
        for (com.google.android.gms.internal.zzpm.zza zzaa : (com.google.android.gms.internal.zzpm.zza[]) this.vG.toArray(vF)) {
            zzaa.zzaa(new Status(8, "The connection to Google Play services was lost"));
        }
    }

    public boolean zzara() {
        for (com.google.android.gms.internal.zzpm.zza isReady : (com.google.android.gms.internal.zzpm.zza[]) this.vG.toArray(vF)) {
            if (!isReady.isReady()) {
                return true;
            }
        }
        return false;
    }

    <A extends com.google.android.gms.common.api.Api.zzb> void zzg(com.google.android.gms.internal.zzpm.zza<? extends Result, A> com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__A) {
        this.vG.add(com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__A);
        com_google_android_gms_internal_zzpm_zza__extends_com_google_android_gms_common_api_Result__A.zza(this.vH);
    }
}
