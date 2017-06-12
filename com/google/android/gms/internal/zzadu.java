package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzah.zzf;
import com.google.android.gms.internal.zzah.zzj;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.telnet.TelnetOption;

public interface zzadu {

    public static final class zza extends zzapp<zza> {
        public long aCV;
        public zzj aCW;
        public zzf zzwr;

        public zza() {
            zzcgx();
        }

        public static zza zzao(byte[] bArr) throws zzapu {
            return (zza) zzapv.zza(new zza(), bArr);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_internal_zzadu_zza = (zza) obj;
            if (this.aCV != com_google_android_gms_internal_zzadu_zza.aCV) {
                return false;
            }
            if (this.zzwr == null) {
                if (com_google_android_gms_internal_zzadu_zza.zzwr != null) {
                    return false;
                }
            } else if (!this.zzwr.equals(com_google_android_gms_internal_zzadu_zza.zzwr)) {
                return false;
            }
            if (this.aCW == null) {
                if (com_google_android_gms_internal_zzadu_zza.aCW != null) {
                    return false;
                }
            } else if (!this.aCW.equals(com_google_android_gms_internal_zzadu_zza.aCW)) {
                return false;
            }
            return (this.bjx == null || this.bjx.isEmpty()) ? com_google_android_gms_internal_zzadu_zza.bjx == null || com_google_android_gms_internal_zzadu_zza.bjx.isEmpty() : this.bjx.equals(com_google_android_gms_internal_zzadu_zza.bjx);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.aCW == null ? 0 : this.aCW.hashCode()) + (((this.zzwr == null ? 0 : this.zzwr.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + ((int) (this.aCV ^ (this.aCV >>> 32)))) * 31)) * 31)) * 31;
            if (!(this.bjx == null || this.bjx.isEmpty())) {
                i = this.bjx.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            com_google_android_gms_internal_zzapo.zzb(1, this.aCV);
            if (this.zzwr != null) {
                com_google_android_gms_internal_zzapo.zza(2, this.zzwr);
            }
            if (this.aCW != null) {
                com_google_android_gms_internal_zzapo.zza(3, this.aCW);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zza zzas(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        this.aCV = com_google_android_gms_internal_zzapn.ak();
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        if (this.zzwr == null) {
                            this.zzwr = new zzf();
                        }
                        com_google_android_gms_internal_zzapn.zza(this.zzwr);
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        if (this.aCW == null) {
                            this.aCW = new zzj();
                        }
                        com_google_android_gms_internal_zzapn.zza(this.aCW);
                        continue;
                    default:
                        if (!super.zza(com_google_android_gms_internal_zzapn, ah)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzas(com_google_android_gms_internal_zzapn);
        }

        public zza zzcgx() {
            this.aCV = 0;
            this.zzwr = null;
            this.aCW = null;
            this.bjx = null;
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int zzx = super.zzx() + zzapo.zze(1, this.aCV);
            if (this.zzwr != null) {
                zzx += zzapo.zzc(2, this.zzwr);
            }
            return this.aCW != null ? zzx + zzapo.zzc(3, this.aCW) : zzx;
        }
    }
}
