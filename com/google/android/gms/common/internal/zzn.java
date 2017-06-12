package com.google.android.gms.common.internal;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.kxml2.wap.Wbxml;

final class zzn extends zzm implements Callback {
    private final Handler mHandler;
    private final HashMap<zza, zzb> yN;
    private final com.google.android.gms.common.stats.zzb yO;
    private final long yP;
    private final Context zzaql;

    private static final class zza {
        private final String yQ;
        private final ComponentName yR;
        private final String zzcvc;

        public zza(ComponentName componentName) {
            this.zzcvc = null;
            this.yQ = null;
            this.yR = (ComponentName) zzab.zzy(componentName);
        }

        public zza(String str, String str2) {
            this.zzcvc = zzab.zzhr(str);
            this.yQ = zzab.zzhr(str2);
            this.yR = null;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_common_internal_zzn_zza = (zza) obj;
            return zzaa.equal(this.zzcvc, com_google_android_gms_common_internal_zzn_zza.zzcvc) && zzaa.equal(this.yR, com_google_android_gms_common_internal_zzn_zza.yR);
        }

        public int hashCode() {
            return zzaa.hashCode(this.zzcvc, this.yR);
        }

        public String toString() {
            return this.zzcvc == null ? this.yR.flattenToString() : this.zzcvc;
        }

        public Intent zzasy() {
            return this.zzcvc != null ? new Intent(this.zzcvc).setPackage(this.yQ) : new Intent().setComponent(this.yR);
        }
    }

    private final class zzb {
        private int mState;
        private IBinder xL;
        private ComponentName yR;
        private final zza yS;
        private final Set<ServiceConnection> yT;
        private boolean yU;
        private final zza yV;
        final /* synthetic */ zzn yW;

        public class zza implements ServiceConnection {
            final /* synthetic */ zzb yX;

            public zza(zzb com_google_android_gms_common_internal_zzn_zzb) {
                this.yX = com_google_android_gms_common_internal_zzn_zzb;
            }

            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                synchronized (this.yX.yW.yN) {
                    this.yX.xL = iBinder;
                    this.yX.yR = componentName;
                    for (ServiceConnection onServiceConnected : this.yX.yT) {
                        onServiceConnected.onServiceConnected(componentName, iBinder);
                    }
                    this.yX.mState = 1;
                }
            }

            public void onServiceDisconnected(ComponentName componentName) {
                synchronized (this.yX.yW.yN) {
                    this.yX.xL = null;
                    this.yX.yR = componentName;
                    for (ServiceConnection onServiceDisconnected : this.yX.yT) {
                        onServiceDisconnected.onServiceDisconnected(componentName);
                    }
                    this.yX.mState = 2;
                }
            }
        }

        public zzb(zzn com_google_android_gms_common_internal_zzn, zza com_google_android_gms_common_internal_zzn_zza) {
            this.yW = com_google_android_gms_common_internal_zzn;
            this.yV = com_google_android_gms_common_internal_zzn_zza;
            this.yS = new zza(this);
            this.yT = new HashSet();
            this.mState = 2;
        }

        public IBinder getBinder() {
            return this.xL;
        }

        public ComponentName getComponentName() {
            return this.yR;
        }

        public int getState() {
            return this.mState;
        }

        public boolean isBound() {
            return this.yU;
        }

        public void zza(ServiceConnection serviceConnection, String str) {
            this.yW.yO.zza(this.yW.zzaql, serviceConnection, str, this.yV.zzasy());
            this.yT.add(serviceConnection);
        }

        public boolean zza(ServiceConnection serviceConnection) {
            return this.yT.contains(serviceConnection);
        }

        public boolean zzasz() {
            return this.yT.isEmpty();
        }

        public void zzb(ServiceConnection serviceConnection, String str) {
            this.yW.yO.zzb(this.yW.zzaql, serviceConnection);
            this.yT.remove(serviceConnection);
        }

        @TargetApi(14)
        public void zzhm(String str) {
            this.mState = 3;
            this.yU = this.yW.yO.zza(this.yW.zzaql, str, this.yV.zzasy(), this.yS, (int) Wbxml.EXT_T_1);
            if (!this.yU) {
                this.mState = 2;
                try {
                    this.yW.yO.zza(this.yW.zzaql, this.yS);
                } catch (IllegalArgumentException e) {
                }
            }
        }

        public void zzhn(String str) {
            this.yW.yO.zza(this.yW.zzaql, this.yS);
            this.yU = false;
            this.mState = 2;
        }
    }

    zzn(Context context) {
        this.yN = new HashMap();
        this.zzaql = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), this);
        this.yO = com.google.android.gms.common.stats.zzb.zzaux();
        this.yP = DNSConstants.CLOSE_TIMEOUT;
    }

    private boolean zza(zza com_google_android_gms_common_internal_zzn_zza, ServiceConnection serviceConnection, String str) {
        boolean isBound;
        zzab.zzb((Object) serviceConnection, (Object) "ServiceConnection must not be null");
        synchronized (this.yN) {
            zzb com_google_android_gms_common_internal_zzn_zzb = (zzb) this.yN.get(com_google_android_gms_common_internal_zzn_zza);
            if (com_google_android_gms_common_internal_zzn_zzb != null) {
                this.mHandler.removeMessages(0, com_google_android_gms_common_internal_zzn_zzb);
                if (!com_google_android_gms_common_internal_zzn_zzb.zza(serviceConnection)) {
                    com_google_android_gms_common_internal_zzn_zzb.zza(serviceConnection, str);
                    switch (com_google_android_gms_common_internal_zzn_zzb.getState()) {
                        case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                            serviceConnection.onServiceConnected(com_google_android_gms_common_internal_zzn_zzb.getComponentName(), com_google_android_gms_common_internal_zzn_zzb.getBinder());
                            break;
                        case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                            com_google_android_gms_common_internal_zzn_zzb.zzhm(str);
                            break;
                        default:
                            break;
                    }
                }
                String valueOf = String.valueOf(com_google_android_gms_common_internal_zzn_zza);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 81).append("Trying to bind a GmsServiceConnection that was already connected before.  config=").append(valueOf).toString());
            }
            com_google_android_gms_common_internal_zzn_zzb = new zzb(this, com_google_android_gms_common_internal_zzn_zza);
            com_google_android_gms_common_internal_zzn_zzb.zza(serviceConnection, str);
            com_google_android_gms_common_internal_zzn_zzb.zzhm(str);
            this.yN.put(com_google_android_gms_common_internal_zzn_zza, com_google_android_gms_common_internal_zzn_zzb);
            isBound = com_google_android_gms_common_internal_zzn_zzb.isBound();
        }
        return isBound;
    }

    private void zzb(zza com_google_android_gms_common_internal_zzn_zza, ServiceConnection serviceConnection, String str) {
        zzab.zzb((Object) serviceConnection, (Object) "ServiceConnection must not be null");
        synchronized (this.yN) {
            zzb com_google_android_gms_common_internal_zzn_zzb = (zzb) this.yN.get(com_google_android_gms_common_internal_zzn_zza);
            String valueOf;
            if (com_google_android_gms_common_internal_zzn_zzb == null) {
                valueOf = String.valueOf(com_google_android_gms_common_internal_zzn_zza);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 50).append("Nonexistent connection status for service config: ").append(valueOf).toString());
            } else if (com_google_android_gms_common_internal_zzn_zzb.zza(serviceConnection)) {
                com_google_android_gms_common_internal_zzn_zzb.zzb(serviceConnection, str);
                if (com_google_android_gms_common_internal_zzn_zzb.zzasz()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, com_google_android_gms_common_internal_zzn_zzb), this.yP);
                }
            } else {
                valueOf = String.valueOf(com_google_android_gms_common_internal_zzn_zza);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 76).append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=").append(valueOf).toString());
            }
        }
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                zzb com_google_android_gms_common_internal_zzn_zzb = (zzb) message.obj;
                synchronized (this.yN) {
                    if (com_google_android_gms_common_internal_zzn_zzb.zzasz()) {
                        if (com_google_android_gms_common_internal_zzn_zzb.isBound()) {
                            com_google_android_gms_common_internal_zzn_zzb.zzhn("GmsClientSupervisor");
                        }
                        this.yN.remove(com_google_android_gms_common_internal_zzn_zzb.yV);
                    }
                    break;
                }
                return true;
            default:
                return false;
        }
    }

    public boolean zza(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        return zza(new zza(componentName), serviceConnection, str);
    }

    public boolean zza(String str, String str2, ServiceConnection serviceConnection, String str3) {
        return zza(new zza(str, str2), serviceConnection, str3);
    }

    public void zzb(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        zzb(new zza(componentName), serviceConnection, str);
    }

    public void zzb(String str, String str2, ServiceConnection serviceConnection, String str3) {
        zzb(new zza(str, str2), serviceConnection, str3);
    }
}
