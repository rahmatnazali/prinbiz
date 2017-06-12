package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.jumpinfo.JumpInfo;
import com.hiti.prinbiz.BorderDelFragment;
import com.hiti.printerprotocol.request.HitiPPR_BurnFirmware;
import com.hiti.sql.oadc.OADCItem;
import com.hiti.ui.drawview.DrawView;
import com.hiti.utility.grid.ImageAdapter;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.telnet.TelnetOption;
import org.kxml2.wap.Wbxml;
import org.kxml2.wap.WbxmlParser;
import org.xmlpull.v1.XmlPullParser;

public interface zzah {

    public static final class zza extends zzapp<zza> {
        public int level;
        public int zzun;
        public int zzuo;

        public zza() {
            zzaa();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_internal_zzah_zza = (zza) obj;
            return (this.level == com_google_android_gms_internal_zzah_zza.level && this.zzun == com_google_android_gms_internal_zzah_zza.zzun && this.zzuo == com_google_android_gms_internal_zzah_zza.zzuo) ? (this.bjx == null || this.bjx.isEmpty()) ? com_google_android_gms_internal_zzah_zza.bjx == null || com_google_android_gms_internal_zzah_zza.bjx.isEmpty() : this.bjx.equals(com_google_android_gms_internal_zzah_zza.bjx) : false;
        }

        public int hashCode() {
            int hashCode = (((((((getClass().getName().hashCode() + 527) * 31) + this.level) * 31) + this.zzun) * 31) + this.zzuo) * 31;
            int hashCode2 = (this.bjx == null || this.bjx.isEmpty()) ? 0 : this.bjx.hashCode();
            return hashCode2 + hashCode;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.level != 1) {
                com_google_android_gms_internal_zzapo.zzae(1, this.level);
            }
            if (this.zzun != 0) {
                com_google_android_gms_internal_zzapo.zzae(2, this.zzun);
            }
            if (this.zzuo != 0) {
                com_google_android_gms_internal_zzapo.zzae(3, this.zzuo);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zza zzaa() {
            this.level = 1;
            this.zzun = 0;
            this.zzuo = 0;
            this.bjx = null;
            this.bjG = -1;
            return this;
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzj(com_google_android_gms_internal_zzapn);
        }

        public zza zzj(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        ah = com_google_android_gms_internal_zzapn.al();
                        switch (ah) {
                            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                                this.level = ah;
                                break;
                            default:
                                continue;
                        }
                    case ConnectionResult.API_UNAVAILABLE /*16*/:
                        this.zzun = com_google_android_gms_internal_zzapn.al();
                        continue;
                    case TelnetOption.TERMINAL_TYPE /*24*/:
                        this.zzuo = com_google_android_gms_internal_zzapn.al();
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

        protected int zzx() {
            int zzx = super.zzx();
            if (this.level != 1) {
                zzx += zzapo.zzag(1, this.level);
            }
            if (this.zzun != 0) {
                zzx += zzapo.zzag(2, this.zzun);
            }
            return this.zzuo != 0 ? zzx + zzapo.zzag(3, this.zzuo) : zzx;
        }
    }

    public static final class zzb extends zzapp<zzb> {
        private static volatile zzb[] zzup;
        public int name;
        public int[] zzuq;
        public int zzur;
        public boolean zzus;
        public boolean zzut;

        public zzb() {
            zzac();
        }

        public static zzb[] zzab() {
            if (zzup == null) {
                synchronized (zzapt.bjF) {
                    if (zzup == null) {
                        zzup = new zzb[0];
                    }
                }
            }
            return zzup;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzb)) {
                return false;
            }
            zzb com_google_android_gms_internal_zzah_zzb = (zzb) obj;
            return (zzapt.equals(this.zzuq, com_google_android_gms_internal_zzah_zzb.zzuq) && this.zzur == com_google_android_gms_internal_zzah_zzb.zzur && this.name == com_google_android_gms_internal_zzah_zzb.name && this.zzus == com_google_android_gms_internal_zzah_zzb.zzus && this.zzut == com_google_android_gms_internal_zzah_zzb.zzut) ? (this.bjx == null || this.bjx.isEmpty()) ? com_google_android_gms_internal_zzah_zzb.bjx == null || com_google_android_gms_internal_zzah_zzb.bjx.isEmpty() : this.bjx.equals(com_google_android_gms_internal_zzah_zzb.bjx) : false;
        }

        public int hashCode() {
            int i = 1231;
            int hashCode = ((this.zzus ? 1231 : 1237) + ((((((((getClass().getName().hashCode() + 527) * 31) + zzapt.hashCode(this.zzuq)) * 31) + this.zzur) * 31) + this.name) * 31)) * 31;
            if (!this.zzut) {
                i = 1237;
            }
            i = (hashCode + i) * 31;
            hashCode = (this.bjx == null || this.bjx.isEmpty()) ? 0 : this.bjx.hashCode();
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.zzut) {
                com_google_android_gms_internal_zzapo.zzj(1, this.zzut);
            }
            com_google_android_gms_internal_zzapo.zzae(2, this.zzur);
            if (this.zzuq != null && this.zzuq.length > 0) {
                for (int zzae : this.zzuq) {
                    com_google_android_gms_internal_zzapo.zzae(3, zzae);
                }
            }
            if (this.name != 0) {
                com_google_android_gms_internal_zzapo.zzae(4, this.name);
            }
            if (this.zzus) {
                com_google_android_gms_internal_zzapo.zzj(6, this.zzus);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzb zzac() {
            this.zzuq = zzapy.bjH;
            this.zzur = 0;
            this.name = 0;
            this.zzus = false;
            this.zzut = false;
            this.bjx = null;
            this.bjG = -1;
            return this;
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzk(com_google_android_gms_internal_zzapn);
        }

        public zzb zzk(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                int zzc;
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        this.zzut = com_google_android_gms_internal_zzapn.an();
                        continue;
                    case ConnectionResult.API_UNAVAILABLE /*16*/:
                        this.zzur = com_google_android_gms_internal_zzapn.al();
                        continue;
                    case TelnetOption.TERMINAL_TYPE /*24*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 24);
                        ah = this.zzuq == null ? 0 : this.zzuq.length;
                        Object obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzuq, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzuq = obj;
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        int zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzuq == null ? 0 : this.zzuq.length;
                        Object obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzuq, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzuq = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case BorderDelFragment.PENDDING_SIZE /*32*/:
                        this.name = com_google_android_gms_internal_zzapn.al();
                        continue;
                    case 48:
                        this.zzus = com_google_android_gms_internal_zzapn.an();
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

        protected int zzx() {
            int i = 0;
            int zzx = super.zzx();
            if (this.zzut) {
                zzx += zzapo.zzk(1, this.zzut);
            }
            int zzag = zzapo.zzag(2, this.zzur) + zzx;
            if (this.zzuq == null || this.zzuq.length <= 0) {
                zzx = zzag;
            } else {
                for (int zzafx : this.zzuq) {
                    i += zzapo.zzafx(zzafx);
                }
                zzx = (zzag + i) + (this.zzuq.length * 1);
            }
            if (this.name != 0) {
                zzx += zzapo.zzag(4, this.name);
            }
            return this.zzus ? zzx + zzapo.zzk(6, this.zzus) : zzx;
        }
    }

    public static final class zzc extends zzapp<zzc> {
        private static volatile zzc[] zzuu;
        public String zzcb;
        public long zzuv;
        public long zzuw;
        public boolean zzux;
        public long zzuy;

        public zzc() {
            zzae();
        }

        public static zzc[] zzad() {
            if (zzuu == null) {
                synchronized (zzapt.bjF) {
                    if (zzuu == null) {
                        zzuu = new zzc[0];
                    }
                }
            }
            return zzuu;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzc)) {
                return false;
            }
            zzc com_google_android_gms_internal_zzah_zzc = (zzc) obj;
            if (this.zzcb == null) {
                if (com_google_android_gms_internal_zzah_zzc.zzcb != null) {
                    return false;
                }
            } else if (!this.zzcb.equals(com_google_android_gms_internal_zzah_zzc.zzcb)) {
                return false;
            }
            return (this.zzuv == com_google_android_gms_internal_zzah_zzc.zzuv && this.zzuw == com_google_android_gms_internal_zzah_zzc.zzuw && this.zzux == com_google_android_gms_internal_zzah_zzc.zzux && this.zzuy == com_google_android_gms_internal_zzah_zzc.zzuy) ? (this.bjx == null || this.bjx.isEmpty()) ? com_google_android_gms_internal_zzah_zzc.bjx == null || com_google_android_gms_internal_zzah_zzc.bjx.isEmpty() : this.bjx.equals(com_google_android_gms_internal_zzah_zzc.bjx) : false;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((((this.zzux ? 1231 : 1237) + (((((((this.zzcb == null ? 0 : this.zzcb.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + ((int) (this.zzuv ^ (this.zzuv >>> 32)))) * 31) + ((int) (this.zzuw ^ (this.zzuw >>> 32)))) * 31)) * 31) + ((int) (this.zzuy ^ (this.zzuy >>> 32)))) * 31;
            if (!(this.bjx == null || this.bjx.isEmpty())) {
                i = this.bjx.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (!this.zzcb.equals(XmlPullParser.NO_NAMESPACE)) {
                com_google_android_gms_internal_zzapo.zzr(1, this.zzcb);
            }
            if (this.zzuv != 0) {
                com_google_android_gms_internal_zzapo.zzb(2, this.zzuv);
            }
            if (this.zzuw != 2147483647L) {
                com_google_android_gms_internal_zzapo.zzb(3, this.zzuw);
            }
            if (this.zzux) {
                com_google_android_gms_internal_zzapo.zzj(4, this.zzux);
            }
            if (this.zzuy != 0) {
                com_google_android_gms_internal_zzapo.zzb(5, this.zzuy);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzc zzae() {
            this.zzcb = XmlPullParser.NO_NAMESPACE;
            this.zzuv = 0;
            this.zzuw = 2147483647L;
            this.zzux = false;
            this.zzuy = 0;
            this.bjx = null;
            this.bjG = -1;
            return this;
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzl(com_google_android_gms_internal_zzapn);
        }

        public zzc zzl(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        this.zzcb = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case ConnectionResult.API_UNAVAILABLE /*16*/:
                        this.zzuv = com_google_android_gms_internal_zzapn.ak();
                        continue;
                    case TelnetOption.TERMINAL_TYPE /*24*/:
                        this.zzuw = com_google_android_gms_internal_zzapn.ak();
                        continue;
                    case BorderDelFragment.PENDDING_SIZE /*32*/:
                        this.zzux = com_google_android_gms_internal_zzapn.an();
                        continue;
                    case ImageAdapter.PENDDING_SIZE /*40*/:
                        this.zzuy = com_google_android_gms_internal_zzapn.ak();
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

        protected int zzx() {
            int zzx = super.zzx();
            if (!this.zzcb.equals(XmlPullParser.NO_NAMESPACE)) {
                zzx += zzapo.zzs(1, this.zzcb);
            }
            if (this.zzuv != 0) {
                zzx += zzapo.zze(2, this.zzuv);
            }
            if (this.zzuw != 2147483647L) {
                zzx += zzapo.zze(3, this.zzuw);
            }
            if (this.zzux) {
                zzx += zzapo.zzk(4, this.zzux);
            }
            return this.zzuy != 0 ? zzx + zzapo.zze(5, this.zzuy) : zzx;
        }
    }

    public static final class zzd extends zzapp<zzd> {
        public com.google.android.gms.internal.zzai.zza[] zzuz;
        public com.google.android.gms.internal.zzai.zza[] zzva;
        public zzc[] zzvb;

        public zzd() {
            zzaf();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzd)) {
                return false;
            }
            zzd com_google_android_gms_internal_zzah_zzd = (zzd) obj;
            return (zzapt.equals(this.zzuz, com_google_android_gms_internal_zzah_zzd.zzuz) && zzapt.equals(this.zzva, com_google_android_gms_internal_zzah_zzd.zzva) && zzapt.equals(this.zzvb, com_google_android_gms_internal_zzah_zzd.zzvb)) ? (this.bjx == null || this.bjx.isEmpty()) ? com_google_android_gms_internal_zzah_zzd.bjx == null || com_google_android_gms_internal_zzah_zzd.bjx.isEmpty() : this.bjx.equals(com_google_android_gms_internal_zzah_zzd.bjx) : false;
        }

        public int hashCode() {
            int hashCode = (((((((getClass().getName().hashCode() + 527) * 31) + zzapt.hashCode(this.zzuz)) * 31) + zzapt.hashCode(this.zzva)) * 31) + zzapt.hashCode(this.zzvb)) * 31;
            int hashCode2 = (this.bjx == null || this.bjx.isEmpty()) ? 0 : this.bjx.hashCode();
            return hashCode2 + hashCode;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            int i = 0;
            if (this.zzuz != null && this.zzuz.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv : this.zzuz) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        com_google_android_gms_internal_zzapo.zza(1, com_google_android_gms_internal_zzapv);
                    }
                }
            }
            if (this.zzva != null && this.zzva.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv2 : this.zzva) {
                    if (com_google_android_gms_internal_zzapv2 != null) {
                        com_google_android_gms_internal_zzapo.zza(2, com_google_android_gms_internal_zzapv2);
                    }
                }
            }
            if (this.zzvb != null && this.zzvb.length > 0) {
                while (i < this.zzvb.length) {
                    zzapv com_google_android_gms_internal_zzapv3 = this.zzvb[i];
                    if (com_google_android_gms_internal_zzapv3 != null) {
                        com_google_android_gms_internal_zzapo.zza(3, com_google_android_gms_internal_zzapv3);
                    }
                    i++;
                }
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzd zzaf() {
            this.zzuz = com.google.android.gms.internal.zzai.zza.zzap();
            this.zzva = com.google.android.gms.internal.zzai.zza.zzap();
            this.zzvb = zzc.zzad();
            this.bjx = null;
            this.bjG = -1;
            return this;
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzm(com_google_android_gms_internal_zzapn);
        }

        public zzd zzm(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                int zzc;
                Object obj;
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 10);
                        ah = this.zzuz == null ? 0 : this.zzuz.length;
                        obj = new com.google.android.gms.internal.zzai.zza[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzuz, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new com.google.android.gms.internal.zzai.zza();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new com.google.android.gms.internal.zzai.zza();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.zzuz = obj;
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 18);
                        ah = this.zzva == null ? 0 : this.zzva.length;
                        obj = new com.google.android.gms.internal.zzai.zza[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzva, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new com.google.android.gms.internal.zzai.zza();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new com.google.android.gms.internal.zzai.zza();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.zzva = obj;
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 26);
                        ah = this.zzvb == null ? 0 : this.zzvb.length;
                        obj = new zzc[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvb, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zzc();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zzc();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.zzvb = obj;
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

        protected int zzx() {
            int i;
            int i2 = 0;
            int zzx = super.zzx();
            if (this.zzuz != null && this.zzuz.length > 0) {
                i = zzx;
                for (zzapv com_google_android_gms_internal_zzapv : this.zzuz) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        i += zzapo.zzc(1, com_google_android_gms_internal_zzapv);
                    }
                }
                zzx = i;
            }
            if (this.zzva != null && this.zzva.length > 0) {
                i = zzx;
                for (zzapv com_google_android_gms_internal_zzapv2 : this.zzva) {
                    if (com_google_android_gms_internal_zzapv2 != null) {
                        i += zzapo.zzc(2, com_google_android_gms_internal_zzapv2);
                    }
                }
                zzx = i;
            }
            if (this.zzvb != null && this.zzvb.length > 0) {
                while (i2 < this.zzvb.length) {
                    zzapv com_google_android_gms_internal_zzapv3 = this.zzvb[i2];
                    if (com_google_android_gms_internal_zzapv3 != null) {
                        zzx += zzapo.zzc(3, com_google_android_gms_internal_zzapv3);
                    }
                    i2++;
                }
            }
            return zzx;
        }
    }

    public static final class zze extends zzapp<zze> {
        private static volatile zze[] zzvc;
        public int key;
        public int value;

        public zze() {
            zzah();
        }

        public static zze[] zzag() {
            if (zzvc == null) {
                synchronized (zzapt.bjF) {
                    if (zzvc == null) {
                        zzvc = new zze[0];
                    }
                }
            }
            return zzvc;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zze)) {
                return false;
            }
            zze com_google_android_gms_internal_zzah_zze = (zze) obj;
            return (this.key == com_google_android_gms_internal_zzah_zze.key && this.value == com_google_android_gms_internal_zzah_zze.value) ? (this.bjx == null || this.bjx.isEmpty()) ? com_google_android_gms_internal_zzah_zze.bjx == null || com_google_android_gms_internal_zzah_zze.bjx.isEmpty() : this.bjx.equals(com_google_android_gms_internal_zzah_zze.bjx) : false;
        }

        public int hashCode() {
            int hashCode = (((((getClass().getName().hashCode() + 527) * 31) + this.key) * 31) + this.value) * 31;
            int hashCode2 = (this.bjx == null || this.bjx.isEmpty()) ? 0 : this.bjx.hashCode();
            return hashCode2 + hashCode;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            com_google_android_gms_internal_zzapo.zzae(1, this.key);
            com_google_android_gms_internal_zzapo.zzae(2, this.value);
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zze zzah() {
            this.key = 0;
            this.value = 0;
            this.bjx = null;
            this.bjG = -1;
            return this;
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzn(com_google_android_gms_internal_zzapn);
        }

        public zze zzn(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        this.key = com_google_android_gms_internal_zzapn.al();
                        continue;
                    case ConnectionResult.API_UNAVAILABLE /*16*/:
                        this.value = com_google_android_gms_internal_zzapn.al();
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

        protected int zzx() {
            return (super.zzx() + zzapo.zzag(1, this.key)) + zzapo.zzag(2, this.value);
        }
    }

    public static final class zzf extends zzapp<zzf> {
        public String version;
        public String[] zzvd;
        public String[] zzve;
        public com.google.android.gms.internal.zzai.zza[] zzvf;
        public zze[] zzvg;
        public zzb[] zzvh;
        public zzb[] zzvi;
        public zzb[] zzvj;
        public zzg[] zzvk;
        public String zzvl;
        public String zzvm;
        public String zzvn;
        public zza zzvo;
        public float zzvp;
        public boolean zzvq;
        public String[] zzvr;
        public int zzvs;

        public zzf() {
            zzai();
        }

        public static zzf zze(byte[] bArr) throws zzapu {
            return (zzf) zzapv.zza(new zzf(), bArr);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzf)) {
                return false;
            }
            zzf com_google_android_gms_internal_zzah_zzf = (zzf) obj;
            if (!zzapt.equals(this.zzvd, com_google_android_gms_internal_zzah_zzf.zzvd) || !zzapt.equals(this.zzve, com_google_android_gms_internal_zzah_zzf.zzve) || !zzapt.equals(this.zzvf, com_google_android_gms_internal_zzah_zzf.zzvf) || !zzapt.equals(this.zzvg, com_google_android_gms_internal_zzah_zzf.zzvg) || !zzapt.equals(this.zzvh, com_google_android_gms_internal_zzah_zzf.zzvh) || !zzapt.equals(this.zzvi, com_google_android_gms_internal_zzah_zzf.zzvi) || !zzapt.equals(this.zzvj, com_google_android_gms_internal_zzah_zzf.zzvj) || !zzapt.equals(this.zzvk, com_google_android_gms_internal_zzah_zzf.zzvk)) {
                return false;
            }
            if (this.zzvl == null) {
                if (com_google_android_gms_internal_zzah_zzf.zzvl != null) {
                    return false;
                }
            } else if (!this.zzvl.equals(com_google_android_gms_internal_zzah_zzf.zzvl)) {
                return false;
            }
            if (this.zzvm == null) {
                if (com_google_android_gms_internal_zzah_zzf.zzvm != null) {
                    return false;
                }
            } else if (!this.zzvm.equals(com_google_android_gms_internal_zzah_zzf.zzvm)) {
                return false;
            }
            if (this.zzvn == null) {
                if (com_google_android_gms_internal_zzah_zzf.zzvn != null) {
                    return false;
                }
            } else if (!this.zzvn.equals(com_google_android_gms_internal_zzah_zzf.zzvn)) {
                return false;
            }
            if (this.version == null) {
                if (com_google_android_gms_internal_zzah_zzf.version != null) {
                    return false;
                }
            } else if (!this.version.equals(com_google_android_gms_internal_zzah_zzf.version)) {
                return false;
            }
            if (this.zzvo == null) {
                if (com_google_android_gms_internal_zzah_zzf.zzvo != null) {
                    return false;
                }
            } else if (!this.zzvo.equals(com_google_android_gms_internal_zzah_zzf.zzvo)) {
                return false;
            }
            return (Float.floatToIntBits(this.zzvp) == Float.floatToIntBits(com_google_android_gms_internal_zzah_zzf.zzvp) && this.zzvq == com_google_android_gms_internal_zzah_zzf.zzvq && zzapt.equals(this.zzvr, com_google_android_gms_internal_zzah_zzf.zzvr) && this.zzvs == com_google_android_gms_internal_zzah_zzf.zzvs) ? (this.bjx == null || this.bjx.isEmpty()) ? com_google_android_gms_internal_zzah_zzf.bjx == null || com_google_android_gms_internal_zzah_zzf.bjx.isEmpty() : this.bjx.equals(com_google_android_gms_internal_zzah_zzf.bjx) : false;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((((((this.zzvq ? 1231 : 1237) + (((((this.zzvo == null ? 0 : this.zzvo.hashCode()) + (((this.version == null ? 0 : this.version.hashCode()) + (((this.zzvn == null ? 0 : this.zzvn.hashCode()) + (((this.zzvm == null ? 0 : this.zzvm.hashCode()) + (((this.zzvl == null ? 0 : this.zzvl.hashCode()) + ((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + zzapt.hashCode(this.zzvd)) * 31) + zzapt.hashCode(this.zzve)) * 31) + zzapt.hashCode(this.zzvf)) * 31) + zzapt.hashCode(this.zzvg)) * 31) + zzapt.hashCode(this.zzvh)) * 31) + zzapt.hashCode(this.zzvi)) * 31) + zzapt.hashCode(this.zzvj)) * 31) + zzapt.hashCode(this.zzvk)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31) + Float.floatToIntBits(this.zzvp)) * 31)) * 31) + zzapt.hashCode(this.zzvr)) * 31) + this.zzvs) * 31;
            if (!(this.bjx == null || this.bjx.isEmpty())) {
                i = this.bjx.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            int i = 0;
            if (this.zzve != null && this.zzve.length > 0) {
                for (String str : this.zzve) {
                    if (str != null) {
                        com_google_android_gms_internal_zzapo.zzr(1, str);
                    }
                }
            }
            if (this.zzvf != null && this.zzvf.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv : this.zzvf) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        com_google_android_gms_internal_zzapo.zza(2, com_google_android_gms_internal_zzapv);
                    }
                }
            }
            if (this.zzvg != null && this.zzvg.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv2 : this.zzvg) {
                    if (com_google_android_gms_internal_zzapv2 != null) {
                        com_google_android_gms_internal_zzapo.zza(3, com_google_android_gms_internal_zzapv2);
                    }
                }
            }
            if (this.zzvh != null && this.zzvh.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv22 : this.zzvh) {
                    if (com_google_android_gms_internal_zzapv22 != null) {
                        com_google_android_gms_internal_zzapo.zza(4, com_google_android_gms_internal_zzapv22);
                    }
                }
            }
            if (this.zzvi != null && this.zzvi.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv222 : this.zzvi) {
                    if (com_google_android_gms_internal_zzapv222 != null) {
                        com_google_android_gms_internal_zzapo.zza(5, com_google_android_gms_internal_zzapv222);
                    }
                }
            }
            if (this.zzvj != null && this.zzvj.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv2222 : this.zzvj) {
                    if (com_google_android_gms_internal_zzapv2222 != null) {
                        com_google_android_gms_internal_zzapo.zza(6, com_google_android_gms_internal_zzapv2222);
                    }
                }
            }
            if (this.zzvk != null && this.zzvk.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv22222 : this.zzvk) {
                    if (com_google_android_gms_internal_zzapv22222 != null) {
                        com_google_android_gms_internal_zzapo.zza(7, com_google_android_gms_internal_zzapv22222);
                    }
                }
            }
            if (!this.zzvl.equals(XmlPullParser.NO_NAMESPACE)) {
                com_google_android_gms_internal_zzapo.zzr(9, this.zzvl);
            }
            if (!this.zzvm.equals(XmlPullParser.NO_NAMESPACE)) {
                com_google_android_gms_internal_zzapo.zzr(10, this.zzvm);
            }
            if (!this.zzvn.equals(OADCItem.WATCH_TYPE_NON)) {
                com_google_android_gms_internal_zzapo.zzr(12, this.zzvn);
            }
            if (!this.version.equals(XmlPullParser.NO_NAMESPACE)) {
                com_google_android_gms_internal_zzapo.zzr(13, this.version);
            }
            if (this.zzvo != null) {
                com_google_android_gms_internal_zzapo.zza(14, this.zzvo);
            }
            if (Float.floatToIntBits(this.zzvp) != Float.floatToIntBits(0.0f)) {
                com_google_android_gms_internal_zzapo.zzc(15, this.zzvp);
            }
            if (this.zzvr != null && this.zzvr.length > 0) {
                for (String str2 : this.zzvr) {
                    if (str2 != null) {
                        com_google_android_gms_internal_zzapo.zzr(16, str2);
                    }
                }
            }
            if (this.zzvs != 0) {
                com_google_android_gms_internal_zzapo.zzae(17, this.zzvs);
            }
            if (this.zzvq) {
                com_google_android_gms_internal_zzapo.zzj(18, this.zzvq);
            }
            if (this.zzvd != null && this.zzvd.length > 0) {
                while (i < this.zzvd.length) {
                    String str3 = this.zzvd[i];
                    if (str3 != null) {
                        com_google_android_gms_internal_zzapo.zzr(19, str3);
                    }
                    i++;
                }
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzf zzai() {
            this.zzvd = zzapy.bjM;
            this.zzve = zzapy.bjM;
            this.zzvf = com.google.android.gms.internal.zzai.zza.zzap();
            this.zzvg = zze.zzag();
            this.zzvh = zzb.zzab();
            this.zzvi = zzb.zzab();
            this.zzvj = zzb.zzab();
            this.zzvk = zzg.zzaj();
            this.zzvl = XmlPullParser.NO_NAMESPACE;
            this.zzvm = XmlPullParser.NO_NAMESPACE;
            this.zzvn = OADCItem.WATCH_TYPE_NON;
            this.version = XmlPullParser.NO_NAMESPACE;
            this.zzvo = null;
            this.zzvp = 0.0f;
            this.zzvq = false;
            this.zzvr = zzapy.bjM;
            this.zzvs = 0;
            this.bjx = null;
            this.bjG = -1;
            return this;
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzo(com_google_android_gms_internal_zzapn);
        }

        public zzf zzo(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                int zzc;
                Object obj;
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 10);
                        ah = this.zzve == null ? 0 : this.zzve.length;
                        obj = new String[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzve, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.readString();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.readString();
                        this.zzve = obj;
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 18);
                        ah = this.zzvf == null ? 0 : this.zzvf.length;
                        obj = new com.google.android.gms.internal.zzai.zza[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvf, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new com.google.android.gms.internal.zzai.zza();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new com.google.android.gms.internal.zzai.zza();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.zzvf = obj;
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 26);
                        ah = this.zzvg == null ? 0 : this.zzvg.length;
                        obj = new zze[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvg, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zze();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zze();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.zzvg = obj;
                        continue;
                    case TelnetOption.LINEMODE /*34*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 34);
                        ah = this.zzvh == null ? 0 : this.zzvh.length;
                        obj = new zzb[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvh, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zzb();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zzb();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.zzvh = obj;
                        continue;
                    case JumpInfo.RESULT_FORCE_VERIFY_ACTIVITY /*42*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 42);
                        ah = this.zzvi == null ? 0 : this.zzvi.length;
                        obj = new zzb[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvi, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zzb();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zzb();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.zzvi = obj;
                        continue;
                    case JumpInfo.RESULT_MAIN_ACTIVITY /*50*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 50);
                        ah = this.zzvj == null ? 0 : this.zzvj.length;
                        obj = new zzb[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvj, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zzb();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zzb();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.zzvj = obj;
                        continue;
                    case JumpInfo.RESULT_POOL_ID_ACTIVITY /*58*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 58);
                        ah = this.zzvk == null ? 0 : this.zzvk.length;
                        obj = new zzg[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvk, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zzg();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zzg();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.zzvk = obj;
                        continue;
                    case JumpInfo.REQUEST_SETTING_CLEAN_MODE /*74*/:
                        this.zzvl = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case 82:
                        this.zzvm = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case DrawView.GS_GARNISH_STATE /*98*/:
                        this.zzvn = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case 106:
                        this.version = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case 114:
                        if (this.zzvo == null) {
                            this.zzvo = new zza();
                        }
                        com_google_android_gms_internal_zzapn.zza(this.zzvo);
                        continue;
                    case FTPReply.DATA_CONNECTION_ALREADY_OPEN /*125*/:
                        this.zzvp = com_google_android_gms_internal_zzapn.readFloat();
                        continue;
                    case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, TransportMediator.KEYCODE_MEDIA_RECORD);
                        ah = this.zzvr == null ? 0 : this.zzvr.length;
                        obj = new String[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvr, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.readString();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.readString();
                        this.zzvr = obj;
                        continue;
                    case 136:
                        this.zzvs = com_google_android_gms_internal_zzapn.al();
                        continue;
                    case 144:
                        this.zzvq = com_google_android_gms_internal_zzapn.an();
                        continue;
                    case 154:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 154);
                        ah = this.zzvd == null ? 0 : this.zzvd.length;
                        obj = new String[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvd, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.readString();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.readString();
                        this.zzvd = obj;
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

        protected int zzx() {
            int i;
            int i2;
            int i3;
            int i4 = 0;
            int zzx = super.zzx();
            if (this.zzve == null || this.zzve.length <= 0) {
                i = zzx;
            } else {
                i2 = 0;
                i3 = 0;
                for (String str : this.zzve) {
                    if (str != null) {
                        i3++;
                        i2 += zzapo.zztx(str);
                    }
                }
                i = (zzx + i2) + (i3 * 1);
            }
            if (this.zzvf != null && this.zzvf.length > 0) {
                i2 = i;
                for (zzapv com_google_android_gms_internal_zzapv : this.zzvf) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        i2 += zzapo.zzc(2, com_google_android_gms_internal_zzapv);
                    }
                }
                i = i2;
            }
            if (this.zzvg != null && this.zzvg.length > 0) {
                i2 = i;
                for (zzapv com_google_android_gms_internal_zzapv2 : this.zzvg) {
                    if (com_google_android_gms_internal_zzapv2 != null) {
                        i2 += zzapo.zzc(3, com_google_android_gms_internal_zzapv2);
                    }
                }
                i = i2;
            }
            if (this.zzvh != null && this.zzvh.length > 0) {
                i2 = i;
                for (zzapv com_google_android_gms_internal_zzapv22 : this.zzvh) {
                    if (com_google_android_gms_internal_zzapv22 != null) {
                        i2 += zzapo.zzc(4, com_google_android_gms_internal_zzapv22);
                    }
                }
                i = i2;
            }
            if (this.zzvi != null && this.zzvi.length > 0) {
                i2 = i;
                for (zzapv com_google_android_gms_internal_zzapv222 : this.zzvi) {
                    if (com_google_android_gms_internal_zzapv222 != null) {
                        i2 += zzapo.zzc(5, com_google_android_gms_internal_zzapv222);
                    }
                }
                i = i2;
            }
            if (this.zzvj != null && this.zzvj.length > 0) {
                i2 = i;
                for (zzapv com_google_android_gms_internal_zzapv2222 : this.zzvj) {
                    if (com_google_android_gms_internal_zzapv2222 != null) {
                        i2 += zzapo.zzc(6, com_google_android_gms_internal_zzapv2222);
                    }
                }
                i = i2;
            }
            if (this.zzvk != null && this.zzvk.length > 0) {
                i2 = i;
                for (zzapv com_google_android_gms_internal_zzapv22222 : this.zzvk) {
                    if (com_google_android_gms_internal_zzapv22222 != null) {
                        i2 += zzapo.zzc(7, com_google_android_gms_internal_zzapv22222);
                    }
                }
                i = i2;
            }
            if (!this.zzvl.equals(XmlPullParser.NO_NAMESPACE)) {
                i += zzapo.zzs(9, this.zzvl);
            }
            if (!this.zzvm.equals(XmlPullParser.NO_NAMESPACE)) {
                i += zzapo.zzs(10, this.zzvm);
            }
            if (!this.zzvn.equals(OADCItem.WATCH_TYPE_NON)) {
                i += zzapo.zzs(12, this.zzvn);
            }
            if (!this.version.equals(XmlPullParser.NO_NAMESPACE)) {
                i += zzapo.zzs(13, this.version);
            }
            if (this.zzvo != null) {
                i += zzapo.zzc(14, this.zzvo);
            }
            if (Float.floatToIntBits(this.zzvp) != Float.floatToIntBits(0.0f)) {
                i += zzapo.zzd(15, this.zzvp);
            }
            if (this.zzvr != null && this.zzvr.length > 0) {
                i3 = 0;
                zzx = 0;
                for (String str2 : this.zzvr) {
                    if (str2 != null) {
                        zzx++;
                        i3 += zzapo.zztx(str2);
                    }
                }
                i = (i + i3) + (zzx * 2);
            }
            if (this.zzvs != 0) {
                i += zzapo.zzag(17, this.zzvs);
            }
            if (this.zzvq) {
                i += zzapo.zzk(18, this.zzvq);
            }
            if (this.zzvd == null || this.zzvd.length <= 0) {
                return i;
            }
            i2 = 0;
            i3 = 0;
            while (i4 < this.zzvd.length) {
                String str3 = this.zzvd[i4];
                if (str3 != null) {
                    i3++;
                    i2 += zzapo.zztx(str3);
                }
                i4++;
            }
            return (i + i2) + (i3 * 2);
        }
    }

    public static final class zzg extends zzapp<zzg> {
        private static volatile zzg[] zzvt;
        public int[] zzvu;
        public int[] zzvv;
        public int[] zzvw;
        public int[] zzvx;
        public int[] zzvy;
        public int[] zzvz;
        public int[] zzwa;
        public int[] zzwb;
        public int[] zzwc;
        public int[] zzwd;

        public zzg() {
            zzak();
        }

        public static zzg[] zzaj() {
            if (zzvt == null) {
                synchronized (zzapt.bjF) {
                    if (zzvt == null) {
                        zzvt = new zzg[0];
                    }
                }
            }
            return zzvt;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzg)) {
                return false;
            }
            zzg com_google_android_gms_internal_zzah_zzg = (zzg) obj;
            return (zzapt.equals(this.zzvu, com_google_android_gms_internal_zzah_zzg.zzvu) && zzapt.equals(this.zzvv, com_google_android_gms_internal_zzah_zzg.zzvv) && zzapt.equals(this.zzvw, com_google_android_gms_internal_zzah_zzg.zzvw) && zzapt.equals(this.zzvx, com_google_android_gms_internal_zzah_zzg.zzvx) && zzapt.equals(this.zzvy, com_google_android_gms_internal_zzah_zzg.zzvy) && zzapt.equals(this.zzvz, com_google_android_gms_internal_zzah_zzg.zzvz) && zzapt.equals(this.zzwa, com_google_android_gms_internal_zzah_zzg.zzwa) && zzapt.equals(this.zzwb, com_google_android_gms_internal_zzah_zzg.zzwb) && zzapt.equals(this.zzwc, com_google_android_gms_internal_zzah_zzg.zzwc) && zzapt.equals(this.zzwd, com_google_android_gms_internal_zzah_zzg.zzwd)) ? (this.bjx == null || this.bjx.isEmpty()) ? com_google_android_gms_internal_zzah_zzg.bjx == null || com_google_android_gms_internal_zzah_zzg.bjx.isEmpty() : this.bjx.equals(com_google_android_gms_internal_zzah_zzg.bjx) : false;
        }

        public int hashCode() {
            int hashCode = (((((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + zzapt.hashCode(this.zzvu)) * 31) + zzapt.hashCode(this.zzvv)) * 31) + zzapt.hashCode(this.zzvw)) * 31) + zzapt.hashCode(this.zzvx)) * 31) + zzapt.hashCode(this.zzvy)) * 31) + zzapt.hashCode(this.zzvz)) * 31) + zzapt.hashCode(this.zzwa)) * 31) + zzapt.hashCode(this.zzwb)) * 31) + zzapt.hashCode(this.zzwc)) * 31) + zzapt.hashCode(this.zzwd)) * 31;
            int hashCode2 = (this.bjx == null || this.bjx.isEmpty()) ? 0 : this.bjx.hashCode();
            return hashCode2 + hashCode;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            int i = 0;
            if (this.zzvu != null && this.zzvu.length > 0) {
                for (int zzae : this.zzvu) {
                    com_google_android_gms_internal_zzapo.zzae(1, zzae);
                }
            }
            if (this.zzvv != null && this.zzvv.length > 0) {
                for (int zzae2 : this.zzvv) {
                    com_google_android_gms_internal_zzapo.zzae(2, zzae2);
                }
            }
            if (this.zzvw != null && this.zzvw.length > 0) {
                for (int zzae22 : this.zzvw) {
                    com_google_android_gms_internal_zzapo.zzae(3, zzae22);
                }
            }
            if (this.zzvx != null && this.zzvx.length > 0) {
                for (int zzae222 : this.zzvx) {
                    com_google_android_gms_internal_zzapo.zzae(4, zzae222);
                }
            }
            if (this.zzvy != null && this.zzvy.length > 0) {
                for (int zzae2222 : this.zzvy) {
                    com_google_android_gms_internal_zzapo.zzae(5, zzae2222);
                }
            }
            if (this.zzvz != null && this.zzvz.length > 0) {
                for (int zzae22222 : this.zzvz) {
                    com_google_android_gms_internal_zzapo.zzae(6, zzae22222);
                }
            }
            if (this.zzwa != null && this.zzwa.length > 0) {
                for (int zzae222222 : this.zzwa) {
                    com_google_android_gms_internal_zzapo.zzae(7, zzae222222);
                }
            }
            if (this.zzwb != null && this.zzwb.length > 0) {
                for (int zzae2222222 : this.zzwb) {
                    com_google_android_gms_internal_zzapo.zzae(8, zzae2222222);
                }
            }
            if (this.zzwc != null && this.zzwc.length > 0) {
                for (int zzae22222222 : this.zzwc) {
                    com_google_android_gms_internal_zzapo.zzae(9, zzae22222222);
                }
            }
            if (this.zzwd != null && this.zzwd.length > 0) {
                while (i < this.zzwd.length) {
                    com_google_android_gms_internal_zzapo.zzae(10, this.zzwd[i]);
                    i++;
                }
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzg zzak() {
            this.zzvu = zzapy.bjH;
            this.zzvv = zzapy.bjH;
            this.zzvw = zzapy.bjH;
            this.zzvx = zzapy.bjH;
            this.zzvy = zzapy.bjH;
            this.zzvz = zzapy.bjH;
            this.zzwa = zzapy.bjH;
            this.zzwb = zzapy.bjH;
            this.zzwc = zzapy.bjH;
            this.zzwd = zzapy.bjH;
            this.bjx = null;
            this.bjG = -1;
            return this;
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzp(com_google_android_gms_internal_zzapn);
        }

        public zzg zzp(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                int zzc;
                Object obj;
                int zzafr;
                Object obj2;
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 8);
                        ah = this.zzvu == null ? 0 : this.zzvu.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvu, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzvu = obj;
                        continue;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzvu == null ? 0 : this.zzvu.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzvu, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzvu = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case ConnectionResult.API_UNAVAILABLE /*16*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 16);
                        ah = this.zzvv == null ? 0 : this.zzvv.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvv, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzvv = obj;
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzvv == null ? 0 : this.zzvv.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzvv, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzvv = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case TelnetOption.TERMINAL_TYPE /*24*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 24);
                        ah = this.zzvw == null ? 0 : this.zzvw.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvw, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzvw = obj;
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzvw == null ? 0 : this.zzvw.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzvw, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzvw = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case BorderDelFragment.PENDDING_SIZE /*32*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 32);
                        ah = this.zzvx == null ? 0 : this.zzvx.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvx, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzvx = obj;
                        continue;
                    case TelnetOption.LINEMODE /*34*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzvx == null ? 0 : this.zzvx.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzvx, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzvx = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case ImageAdapter.PENDDING_SIZE /*40*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 40);
                        ah = this.zzvy == null ? 0 : this.zzvy.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvy, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzvy = obj;
                        continue;
                    case JumpInfo.RESULT_FORCE_VERIFY_ACTIVITY /*42*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzvy == null ? 0 : this.zzvy.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzvy, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzvy = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case 48:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 48);
                        ah = this.zzvz == null ? 0 : this.zzvz.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzvz, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzvz = obj;
                        continue;
                    case JumpInfo.RESULT_MAIN_ACTIVITY /*50*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzvz == null ? 0 : this.zzvz.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzvz, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzvz = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case JumpInfo.RESULT_PHOTO_FROM_PRINTER_ACTIVITY /*56*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 56);
                        ah = this.zzwa == null ? 0 : this.zzwa.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzwa, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzwa = obj;
                        continue;
                    case JumpInfo.RESULT_POOL_ID_ACTIVITY /*58*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzwa == null ? 0 : this.zzwa.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzwa, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzwa = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case WbxmlParser.WAP_EXTENSION /*64*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 64);
                        ah = this.zzwb == null ? 0 : this.zzwb.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzwb, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzwb = obj;
                        continue;
                    case Wbxml.EXT_I_2 /*66*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzwb == null ? 0 : this.zzwb.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzwb, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzwb = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case 72:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 72);
                        ah = this.zzwc == null ? 0 : this.zzwc.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzwc, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzwc = obj;
                        continue;
                    case JumpInfo.REQUEST_SETTING_CLEAN_MODE /*74*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzwc == null ? 0 : this.zzwc.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzwc, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzwc = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case HitiPPR_BurnFirmware.BURN_FIRMWARE_PROGRESS_SIZE /*80*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 80);
                        ah = this.zzwd == null ? 0 : this.zzwd.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzwd, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzwd = obj;
                        continue;
                    case 82:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzwd == null ? 0 : this.zzwd.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzwd, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzwd = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
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

        protected int zzx() {
            int i;
            int i2;
            int i3 = 0;
            int zzx = super.zzx();
            if (this.zzvu == null || this.zzvu.length <= 0) {
                i = zzx;
            } else {
                i2 = 0;
                for (int zzafx : this.zzvu) {
                    i2 += zzapo.zzafx(zzafx);
                }
                i = (zzx + i2) + (this.zzvu.length * 1);
            }
            if (this.zzvv != null && this.zzvv.length > 0) {
                zzx = 0;
                for (int zzafx2 : this.zzvv) {
                    zzx += zzapo.zzafx(zzafx2);
                }
                i = (i + zzx) + (this.zzvv.length * 1);
            }
            if (this.zzvw != null && this.zzvw.length > 0) {
                zzx = 0;
                for (int zzafx22 : this.zzvw) {
                    zzx += zzapo.zzafx(zzafx22);
                }
                i = (i + zzx) + (this.zzvw.length * 1);
            }
            if (this.zzvx != null && this.zzvx.length > 0) {
                zzx = 0;
                for (int zzafx222 : this.zzvx) {
                    zzx += zzapo.zzafx(zzafx222);
                }
                i = (i + zzx) + (this.zzvx.length * 1);
            }
            if (this.zzvy != null && this.zzvy.length > 0) {
                zzx = 0;
                for (int zzafx2222 : this.zzvy) {
                    zzx += zzapo.zzafx(zzafx2222);
                }
                i = (i + zzx) + (this.zzvy.length * 1);
            }
            if (this.zzvz != null && this.zzvz.length > 0) {
                zzx = 0;
                for (int zzafx22222 : this.zzvz) {
                    zzx += zzapo.zzafx(zzafx22222);
                }
                i = (i + zzx) + (this.zzvz.length * 1);
            }
            if (this.zzwa != null && this.zzwa.length > 0) {
                zzx = 0;
                for (int zzafx222222 : this.zzwa) {
                    zzx += zzapo.zzafx(zzafx222222);
                }
                i = (i + zzx) + (this.zzwa.length * 1);
            }
            if (this.zzwb != null && this.zzwb.length > 0) {
                zzx = 0;
                for (int zzafx2222222 : this.zzwb) {
                    zzx += zzapo.zzafx(zzafx2222222);
                }
                i = (i + zzx) + (this.zzwb.length * 1);
            }
            if (this.zzwc != null && this.zzwc.length > 0) {
                zzx = 0;
                for (int zzafx22222222 : this.zzwc) {
                    zzx += zzapo.zzafx(zzafx22222222);
                }
                i = (i + zzx) + (this.zzwc.length * 1);
            }
            if (this.zzwd == null || this.zzwd.length <= 0) {
                return i;
            }
            i2 = 0;
            while (i3 < this.zzwd.length) {
                i2 += zzapo.zzafx(this.zzwd[i3]);
                i3++;
            }
            return (i + i2) + (this.zzwd.length * 1);
        }
    }

    public static final class zzh extends zzapp<zzh> {
        public static final zzapq<com.google.android.gms.internal.zzai.zza, zzh> zzwe;
        private static final zzh[] zzwf;
        public int[] zzwg;
        public int[] zzwh;
        public int[] zzwi;
        public int zzwj;
        public int[] zzwk;
        public int zzwl;
        public int zzwm;

        static {
            zzwe = zzapq.zza(11, zzh.class, 810);
            zzwf = new zzh[0];
        }

        public zzh() {
            zzal();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzh)) {
                return false;
            }
            zzh com_google_android_gms_internal_zzah_zzh = (zzh) obj;
            return (zzapt.equals(this.zzwg, com_google_android_gms_internal_zzah_zzh.zzwg) && zzapt.equals(this.zzwh, com_google_android_gms_internal_zzah_zzh.zzwh) && zzapt.equals(this.zzwi, com_google_android_gms_internal_zzah_zzh.zzwi) && this.zzwj == com_google_android_gms_internal_zzah_zzh.zzwj && zzapt.equals(this.zzwk, com_google_android_gms_internal_zzah_zzh.zzwk) && this.zzwl == com_google_android_gms_internal_zzah_zzh.zzwl && this.zzwm == com_google_android_gms_internal_zzah_zzh.zzwm) ? (this.bjx == null || this.bjx.isEmpty()) ? com_google_android_gms_internal_zzah_zzh.bjx == null || com_google_android_gms_internal_zzah_zzh.bjx.isEmpty() : this.bjx.equals(com_google_android_gms_internal_zzah_zzh.bjx) : false;
        }

        public int hashCode() {
            int hashCode = (((((((((((((((getClass().getName().hashCode() + 527) * 31) + zzapt.hashCode(this.zzwg)) * 31) + zzapt.hashCode(this.zzwh)) * 31) + zzapt.hashCode(this.zzwi)) * 31) + this.zzwj) * 31) + zzapt.hashCode(this.zzwk)) * 31) + this.zzwl) * 31) + this.zzwm) * 31;
            int hashCode2 = (this.bjx == null || this.bjx.isEmpty()) ? 0 : this.bjx.hashCode();
            return hashCode2 + hashCode;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            int i = 0;
            if (this.zzwg != null && this.zzwg.length > 0) {
                for (int zzae : this.zzwg) {
                    com_google_android_gms_internal_zzapo.zzae(1, zzae);
                }
            }
            if (this.zzwh != null && this.zzwh.length > 0) {
                for (int zzae2 : this.zzwh) {
                    com_google_android_gms_internal_zzapo.zzae(2, zzae2);
                }
            }
            if (this.zzwi != null && this.zzwi.length > 0) {
                for (int zzae22 : this.zzwi) {
                    com_google_android_gms_internal_zzapo.zzae(3, zzae22);
                }
            }
            if (this.zzwj != 0) {
                com_google_android_gms_internal_zzapo.zzae(4, this.zzwj);
            }
            if (this.zzwk != null && this.zzwk.length > 0) {
                while (i < this.zzwk.length) {
                    com_google_android_gms_internal_zzapo.zzae(5, this.zzwk[i]);
                    i++;
                }
            }
            if (this.zzwl != 0) {
                com_google_android_gms_internal_zzapo.zzae(6, this.zzwl);
            }
            if (this.zzwm != 0) {
                com_google_android_gms_internal_zzapo.zzae(7, this.zzwm);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzh zzal() {
            this.zzwg = zzapy.bjH;
            this.zzwh = zzapy.bjH;
            this.zzwi = zzapy.bjH;
            this.zzwj = 0;
            this.zzwk = zzapy.bjH;
            this.zzwl = 0;
            this.zzwm = 0;
            this.bjx = null;
            this.bjG = -1;
            return this;
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzq(com_google_android_gms_internal_zzapn);
        }

        public zzh zzq(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                int zzc;
                Object obj;
                int zzafr;
                Object obj2;
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 8);
                        ah = this.zzwg == null ? 0 : this.zzwg.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzwg, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzwg = obj;
                        continue;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzwg == null ? 0 : this.zzwg.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzwg, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzwg = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case ConnectionResult.API_UNAVAILABLE /*16*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 16);
                        ah = this.zzwh == null ? 0 : this.zzwh.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzwh, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzwh = obj;
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzwh == null ? 0 : this.zzwh.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzwh, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzwh = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case TelnetOption.TERMINAL_TYPE /*24*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 24);
                        ah = this.zzwi == null ? 0 : this.zzwi.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzwi, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzwi = obj;
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzwi == null ? 0 : this.zzwi.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzwi, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzwi = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case BorderDelFragment.PENDDING_SIZE /*32*/:
                        this.zzwj = com_google_android_gms_internal_zzapn.al();
                        continue;
                    case ImageAdapter.PENDDING_SIZE /*40*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 40);
                        ah = this.zzwk == null ? 0 : this.zzwk.length;
                        obj = new int[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzwk, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.al();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.al();
                        this.zzwk = obj;
                        continue;
                    case JumpInfo.RESULT_FORCE_VERIFY_ACTIVITY /*42*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.al();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.zzwk == null ? 0 : this.zzwk.length;
                        obj2 = new int[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzwk, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.al();
                            zzc++;
                        }
                        this.zzwk = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case 48:
                        this.zzwl = com_google_android_gms_internal_zzapn.al();
                        continue;
                    case JumpInfo.RESULT_PHOTO_FROM_PRINTER_ACTIVITY /*56*/:
                        this.zzwm = com_google_android_gms_internal_zzapn.al();
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

        protected int zzx() {
            int i;
            int i2;
            int i3 = 0;
            int zzx = super.zzx();
            if (this.zzwg == null || this.zzwg.length <= 0) {
                i = zzx;
            } else {
                i2 = 0;
                for (int zzafx : this.zzwg) {
                    i2 += zzapo.zzafx(zzafx);
                }
                i = (zzx + i2) + (this.zzwg.length * 1);
            }
            if (this.zzwh != null && this.zzwh.length > 0) {
                zzx = 0;
                for (int zzafx2 : this.zzwh) {
                    zzx += zzapo.zzafx(zzafx2);
                }
                i = (i + zzx) + (this.zzwh.length * 1);
            }
            if (this.zzwi != null && this.zzwi.length > 0) {
                zzx = 0;
                for (int zzafx22 : this.zzwi) {
                    zzx += zzapo.zzafx(zzafx22);
                }
                i = (i + zzx) + (this.zzwi.length * 1);
            }
            if (this.zzwj != 0) {
                i += zzapo.zzag(4, this.zzwj);
            }
            if (this.zzwk != null && this.zzwk.length > 0) {
                i2 = 0;
                while (i3 < this.zzwk.length) {
                    i2 += zzapo.zzafx(this.zzwk[i3]);
                    i3++;
                }
                i = (i + i2) + (this.zzwk.length * 1);
            }
            if (this.zzwl != 0) {
                i += zzapo.zzag(6, this.zzwl);
            }
            return this.zzwm != 0 ? i + zzapo.zzag(7, this.zzwm) : i;
        }
    }

    public static final class zzi extends zzapp<zzi> {
        private static volatile zzi[] zzwn;
        public String name;
        public com.google.android.gms.internal.zzai.zza zzwo;
        public zzd zzwp;

        public zzi() {
            zzan();
        }

        public static zzi[] zzam() {
            if (zzwn == null) {
                synchronized (zzapt.bjF) {
                    if (zzwn == null) {
                        zzwn = new zzi[0];
                    }
                }
            }
            return zzwn;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzi)) {
                return false;
            }
            zzi com_google_android_gms_internal_zzah_zzi = (zzi) obj;
            if (this.name == null) {
                if (com_google_android_gms_internal_zzah_zzi.name != null) {
                    return false;
                }
            } else if (!this.name.equals(com_google_android_gms_internal_zzah_zzi.name)) {
                return false;
            }
            if (this.zzwo == null) {
                if (com_google_android_gms_internal_zzah_zzi.zzwo != null) {
                    return false;
                }
            } else if (!this.zzwo.equals(com_google_android_gms_internal_zzah_zzi.zzwo)) {
                return false;
            }
            if (this.zzwp == null) {
                if (com_google_android_gms_internal_zzah_zzi.zzwp != null) {
                    return false;
                }
            } else if (!this.zzwp.equals(com_google_android_gms_internal_zzah_zzi.zzwp)) {
                return false;
            }
            return (this.bjx == null || this.bjx.isEmpty()) ? com_google_android_gms_internal_zzah_zzi.bjx == null || com_google_android_gms_internal_zzah_zzi.bjx.isEmpty() : this.bjx.equals(com_google_android_gms_internal_zzah_zzi.bjx);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.zzwp == null ? 0 : this.zzwp.hashCode()) + (((this.zzwo == null ? 0 : this.zzwo.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31;
            if (!(this.bjx == null || this.bjx.isEmpty())) {
                i = this.bjx.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (!this.name.equals(XmlPullParser.NO_NAMESPACE)) {
                com_google_android_gms_internal_zzapo.zzr(1, this.name);
            }
            if (this.zzwo != null) {
                com_google_android_gms_internal_zzapo.zza(2, this.zzwo);
            }
            if (this.zzwp != null) {
                com_google_android_gms_internal_zzapo.zza(3, this.zzwp);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzi zzan() {
            this.name = XmlPullParser.NO_NAMESPACE;
            this.zzwo = null;
            this.zzwp = null;
            this.bjx = null;
            this.bjG = -1;
            return this;
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzr(com_google_android_gms_internal_zzapn);
        }

        public zzi zzr(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        this.name = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        if (this.zzwo == null) {
                            this.zzwo = new com.google.android.gms.internal.zzai.zza();
                        }
                        com_google_android_gms_internal_zzapn.zza(this.zzwo);
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        if (this.zzwp == null) {
                            this.zzwp = new zzd();
                        }
                        com_google_android_gms_internal_zzapn.zza(this.zzwp);
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

        protected int zzx() {
            int zzx = super.zzx();
            if (!this.name.equals(XmlPullParser.NO_NAMESPACE)) {
                zzx += zzapo.zzs(1, this.name);
            }
            if (this.zzwo != null) {
                zzx += zzapo.zzc(2, this.zzwo);
            }
            return this.zzwp != null ? zzx + zzapo.zzc(3, this.zzwp) : zzx;
        }
    }

    public static final class zzj extends zzapp<zzj> {
        public zzi[] zzwq;
        public zzf zzwr;
        public String zzws;

        public zzj() {
            zzao();
        }

        public static zzj zzf(byte[] bArr) throws zzapu {
            return (zzj) zzapv.zza(new zzj(), bArr);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzj)) {
                return false;
            }
            zzj com_google_android_gms_internal_zzah_zzj = (zzj) obj;
            if (!zzapt.equals(this.zzwq, com_google_android_gms_internal_zzah_zzj.zzwq)) {
                return false;
            }
            if (this.zzwr == null) {
                if (com_google_android_gms_internal_zzah_zzj.zzwr != null) {
                    return false;
                }
            } else if (!this.zzwr.equals(com_google_android_gms_internal_zzah_zzj.zzwr)) {
                return false;
            }
            if (this.zzws == null) {
                if (com_google_android_gms_internal_zzah_zzj.zzws != null) {
                    return false;
                }
            } else if (!this.zzws.equals(com_google_android_gms_internal_zzah_zzj.zzws)) {
                return false;
            }
            return (this.bjx == null || this.bjx.isEmpty()) ? com_google_android_gms_internal_zzah_zzj.bjx == null || com_google_android_gms_internal_zzah_zzj.bjx.isEmpty() : this.bjx.equals(com_google_android_gms_internal_zzah_zzj.bjx);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.zzws == null ? 0 : this.zzws.hashCode()) + (((this.zzwr == null ? 0 : this.zzwr.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + zzapt.hashCode(this.zzwq)) * 31)) * 31)) * 31;
            if (!(this.bjx == null || this.bjx.isEmpty())) {
                i = this.bjx.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.zzwq != null && this.zzwq.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv : this.zzwq) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        com_google_android_gms_internal_zzapo.zza(1, com_google_android_gms_internal_zzapv);
                    }
                }
            }
            if (this.zzwr != null) {
                com_google_android_gms_internal_zzapo.zza(2, this.zzwr);
            }
            if (!this.zzws.equals(XmlPullParser.NO_NAMESPACE)) {
                com_google_android_gms_internal_zzapo.zzr(3, this.zzws);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzj zzao() {
            this.zzwq = zzi.zzam();
            this.zzwr = null;
            this.zzws = XmlPullParser.NO_NAMESPACE;
            this.bjx = null;
            this.bjG = -1;
            return this;
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzs(com_google_android_gms_internal_zzapn);
        }

        public zzj zzs(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        int zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 10);
                        ah = this.zzwq == null ? 0 : this.zzwq.length;
                        Object obj = new zzi[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.zzwq, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zzi();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zzi();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.zzwq = obj;
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        if (this.zzwr == null) {
                            this.zzwr = new zzf();
                        }
                        com_google_android_gms_internal_zzapn.zza(this.zzwr);
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        this.zzws = com_google_android_gms_internal_zzapn.readString();
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

        protected int zzx() {
            int zzx = super.zzx();
            if (this.zzwq != null && this.zzwq.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv : this.zzwq) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        zzx += zzapo.zzc(1, com_google_android_gms_internal_zzapv);
                    }
                }
            }
            if (this.zzwr != null) {
                zzx += zzapo.zzc(2, this.zzwr);
            }
            return !this.zzws.equals(XmlPullParser.NO_NAMESPACE) ? zzx + zzapo.zzs(3, this.zzws) : zzx;
        }
    }
}
