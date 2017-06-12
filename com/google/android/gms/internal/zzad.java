package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.hiti.jumpinfo.JumpInfo;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.telnet.TelnetOption;
import org.kxml2.wap.Wbxml;

public interface zzad {

    public static final class zza extends zzapp<zza> {
        public String stackTrace;
        public String zzck;
        public Long zzcl;
        public String zzcm;
        public String zzcn;
        public Long zzco;
        public Long zzcp;
        public String zzcq;
        public Long zzcr;
        public String zzcs;

        public zza() {
            this.zzck = null;
            this.zzcl = null;
            this.stackTrace = null;
            this.zzcm = null;
            this.zzcn = null;
            this.zzco = null;
            this.zzcp = null;
            this.zzcq = null;
            this.zzcr = null;
            this.zzcs = null;
            this.bjG = -1;
        }

        public zza zza(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        this.zzck = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case ConnectionResult.API_UNAVAILABLE /*16*/:
                        this.zzcl = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        this.stackTrace = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case TelnetOption.LINEMODE /*34*/:
                        this.zzcm = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case JumpInfo.RESULT_FORCE_VERIFY_ACTIVITY /*42*/:
                        this.zzcn = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case 48:
                        this.zzco = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case JumpInfo.RESULT_PHOTO_FROM_PRINTER_ACTIVITY /*56*/:
                        this.zzcp = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case Wbxml.EXT_I_2 /*66*/:
                        this.zzcq = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case 72:
                        this.zzcr = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case 82:
                        this.zzcs = com_google_android_gms_internal_zzapn.readString();
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

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.zzck != null) {
                com_google_android_gms_internal_zzapo.zzr(1, this.zzck);
            }
            if (this.zzcl != null) {
                com_google_android_gms_internal_zzapo.zzb(2, this.zzcl.longValue());
            }
            if (this.stackTrace != null) {
                com_google_android_gms_internal_zzapo.zzr(3, this.stackTrace);
            }
            if (this.zzcm != null) {
                com_google_android_gms_internal_zzapo.zzr(4, this.zzcm);
            }
            if (this.zzcn != null) {
                com_google_android_gms_internal_zzapo.zzr(5, this.zzcn);
            }
            if (this.zzco != null) {
                com_google_android_gms_internal_zzapo.zzb(6, this.zzco.longValue());
            }
            if (this.zzcp != null) {
                com_google_android_gms_internal_zzapo.zzb(7, this.zzcp.longValue());
            }
            if (this.zzcq != null) {
                com_google_android_gms_internal_zzapo.zzr(8, this.zzcq);
            }
            if (this.zzcr != null) {
                com_google_android_gms_internal_zzapo.zzb(9, this.zzcr.longValue());
            }
            if (this.zzcs != null) {
                com_google_android_gms_internal_zzapo.zzr(10, this.zzcs);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zza(com_google_android_gms_internal_zzapn);
        }

        protected int zzx() {
            int zzx = super.zzx();
            if (this.zzck != null) {
                zzx += zzapo.zzs(1, this.zzck);
            }
            if (this.zzcl != null) {
                zzx += zzapo.zze(2, this.zzcl.longValue());
            }
            if (this.stackTrace != null) {
                zzx += zzapo.zzs(3, this.stackTrace);
            }
            if (this.zzcm != null) {
                zzx += zzapo.zzs(4, this.zzcm);
            }
            if (this.zzcn != null) {
                zzx += zzapo.zzs(5, this.zzcn);
            }
            if (this.zzco != null) {
                zzx += zzapo.zze(6, this.zzco.longValue());
            }
            if (this.zzcp != null) {
                zzx += zzapo.zze(7, this.zzcp.longValue());
            }
            if (this.zzcq != null) {
                zzx += zzapo.zzs(8, this.zzcq);
            }
            if (this.zzcr != null) {
                zzx += zzapo.zze(9, this.zzcr.longValue());
            }
            return this.zzcs != null ? zzx + zzapo.zzs(10, this.zzcs) : zzx;
        }
    }
}
