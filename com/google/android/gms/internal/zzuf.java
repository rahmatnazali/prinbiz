package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.hiti.jumpinfo.JumpInfo;
import com.hiti.prinbiz.BorderDelFragment;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.telnet.TelnetOption;
import org.apache.commons.net.tftp.TFTPClient;

public interface zzuf {

    public static final class zza extends zzapv {
        private static volatile zza[] amy;
        public zze[] amA;
        public zzb[] amB;
        public Integer amz;

        public zza() {
            zzbve();
        }

        public static zza[] zzbvd() {
            if (amy == null) {
                synchronized (zzapt.bjF) {
                    if (amy == null) {
                        amy = new zza[0];
                    }
                }
            }
            return amy;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_internal_zzuf_zza = (zza) obj;
            if (this.amz == null) {
                if (com_google_android_gms_internal_zzuf_zza.amz != null) {
                    return false;
                }
            } else if (!this.amz.equals(com_google_android_gms_internal_zzuf_zza.amz)) {
                return false;
            }
            return !zzapt.equals(this.amA, com_google_android_gms_internal_zzuf_zza.amA) ? false : zzapt.equals(this.amB, com_google_android_gms_internal_zzuf_zza.amB);
        }

        public int hashCode() {
            return (((((this.amz == null ? 0 : this.amz.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + zzapt.hashCode(this.amA)) * 31) + zzapt.hashCode(this.amB);
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            int i = 0;
            if (this.amz != null) {
                com_google_android_gms_internal_zzapo.zzae(1, this.amz.intValue());
            }
            if (this.amA != null && this.amA.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv : this.amA) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        com_google_android_gms_internal_zzapo.zza(2, com_google_android_gms_internal_zzapv);
                    }
                }
            }
            if (this.amB != null && this.amB.length > 0) {
                while (i < this.amB.length) {
                    zzapv com_google_android_gms_internal_zzapv2 = this.amB[i];
                    if (com_google_android_gms_internal_zzapv2 != null) {
                        com_google_android_gms_internal_zzapo.zza(3, com_google_android_gms_internal_zzapv2);
                    }
                    i++;
                }
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zza zzac(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                int zzc;
                Object obj;
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        this.amz = Integer.valueOf(com_google_android_gms_internal_zzapn.al());
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 18);
                        ah = this.amA == null ? 0 : this.amA.length;
                        obj = new zze[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.amA, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zze();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zze();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.amA = obj;
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 26);
                        ah = this.amB == null ? 0 : this.amB.length;
                        obj = new zzb[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.amB, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zzb();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zzb();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.amB = obj;
                        continue;
                    default:
                        if (!zzapy.zzb(com_google_android_gms_internal_zzapn, ah)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzac(com_google_android_gms_internal_zzapn);
        }

        public zza zzbve() {
            this.amz = null;
            this.amA = zze.zzbvk();
            this.amB = zzb.zzbvf();
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int i = 0;
            int zzx = super.zzx();
            if (this.amz != null) {
                zzx += zzapo.zzag(1, this.amz.intValue());
            }
            if (this.amA != null && this.amA.length > 0) {
                int i2 = zzx;
                for (zzapv com_google_android_gms_internal_zzapv : this.amA) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        i2 += zzapo.zzc(2, com_google_android_gms_internal_zzapv);
                    }
                }
                zzx = i2;
            }
            if (this.amB != null && this.amB.length > 0) {
                while (i < this.amB.length) {
                    zzapv com_google_android_gms_internal_zzapv2 = this.amB[i];
                    if (com_google_android_gms_internal_zzapv2 != null) {
                        zzx += zzapo.zzc(3, com_google_android_gms_internal_zzapv2);
                    }
                    i++;
                }
            }
            return zzx;
        }
    }

    public static final class zzb extends zzapv {
        private static volatile zzb[] amC;
        public Integer amD;
        public String amE;
        public zzc[] amF;
        public Boolean amG;
        public zzd amH;

        public zzb() {
            zzbvg();
        }

        public static zzb[] zzbvf() {
            if (amC == null) {
                synchronized (zzapt.bjF) {
                    if (amC == null) {
                        amC = new zzb[0];
                    }
                }
            }
            return amC;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzb)) {
                return false;
            }
            zzb com_google_android_gms_internal_zzuf_zzb = (zzb) obj;
            if (this.amD == null) {
                if (com_google_android_gms_internal_zzuf_zzb.amD != null) {
                    return false;
                }
            } else if (!this.amD.equals(com_google_android_gms_internal_zzuf_zzb.amD)) {
                return false;
            }
            if (this.amE == null) {
                if (com_google_android_gms_internal_zzuf_zzb.amE != null) {
                    return false;
                }
            } else if (!this.amE.equals(com_google_android_gms_internal_zzuf_zzb.amE)) {
                return false;
            }
            if (!zzapt.equals(this.amF, com_google_android_gms_internal_zzuf_zzb.amF)) {
                return false;
            }
            if (this.amG == null) {
                if (com_google_android_gms_internal_zzuf_zzb.amG != null) {
                    return false;
                }
            } else if (!this.amG.equals(com_google_android_gms_internal_zzuf_zzb.amG)) {
                return false;
            }
            return this.amH == null ? com_google_android_gms_internal_zzuf_zzb.amH == null : this.amH.equals(com_google_android_gms_internal_zzuf_zzb.amH);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.amG == null ? 0 : this.amG.hashCode()) + (((((this.amE == null ? 0 : this.amE.hashCode()) + (((this.amD == null ? 0 : this.amD.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31) + zzapt.hashCode(this.amF)) * 31)) * 31;
            if (this.amH != null) {
                i = this.amH.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.amD != null) {
                com_google_android_gms_internal_zzapo.zzae(1, this.amD.intValue());
            }
            if (this.amE != null) {
                com_google_android_gms_internal_zzapo.zzr(2, this.amE);
            }
            if (this.amF != null && this.amF.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv : this.amF) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        com_google_android_gms_internal_zzapo.zza(3, com_google_android_gms_internal_zzapv);
                    }
                }
            }
            if (this.amG != null) {
                com_google_android_gms_internal_zzapo.zzj(4, this.amG.booleanValue());
            }
            if (this.amH != null) {
                com_google_android_gms_internal_zzapo.zza(5, this.amH);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzb zzad(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        this.amD = Integer.valueOf(com_google_android_gms_internal_zzapn.al());
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        this.amE = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        int zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 26);
                        ah = this.amF == null ? 0 : this.amF.length;
                        Object obj = new zzc[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.amF, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zzc();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zzc();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.amF = obj;
                        continue;
                    case BorderDelFragment.PENDDING_SIZE /*32*/:
                        this.amG = Boolean.valueOf(com_google_android_gms_internal_zzapn.an());
                        continue;
                    case JumpInfo.RESULT_FORCE_VERIFY_ACTIVITY /*42*/:
                        if (this.amH == null) {
                            this.amH = new zzd();
                        }
                        com_google_android_gms_internal_zzapn.zza(this.amH);
                        continue;
                    default:
                        if (!zzapy.zzb(com_google_android_gms_internal_zzapn, ah)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzad(com_google_android_gms_internal_zzapn);
        }

        public zzb zzbvg() {
            this.amD = null;
            this.amE = null;
            this.amF = zzc.zzbvh();
            this.amG = null;
            this.amH = null;
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int zzx = super.zzx();
            if (this.amD != null) {
                zzx += zzapo.zzag(1, this.amD.intValue());
            }
            if (this.amE != null) {
                zzx += zzapo.zzs(2, this.amE);
            }
            if (this.amF != null && this.amF.length > 0) {
                int i = zzx;
                for (zzapv com_google_android_gms_internal_zzapv : this.amF) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        i += zzapo.zzc(3, com_google_android_gms_internal_zzapv);
                    }
                }
                zzx = i;
            }
            if (this.amG != null) {
                zzx += zzapo.zzk(4, this.amG.booleanValue());
            }
            return this.amH != null ? zzx + zzapo.zzc(5, this.amH) : zzx;
        }
    }

    public static final class zzc extends zzapv {
        private static volatile zzc[] amI;
        public zzf amJ;
        public zzd amK;
        public Boolean amL;
        public String amM;

        public zzc() {
            zzbvi();
        }

        public static zzc[] zzbvh() {
            if (amI == null) {
                synchronized (zzapt.bjF) {
                    if (amI == null) {
                        amI = new zzc[0];
                    }
                }
            }
            return amI;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzc)) {
                return false;
            }
            zzc com_google_android_gms_internal_zzuf_zzc = (zzc) obj;
            if (this.amJ == null) {
                if (com_google_android_gms_internal_zzuf_zzc.amJ != null) {
                    return false;
                }
            } else if (!this.amJ.equals(com_google_android_gms_internal_zzuf_zzc.amJ)) {
                return false;
            }
            if (this.amK == null) {
                if (com_google_android_gms_internal_zzuf_zzc.amK != null) {
                    return false;
                }
            } else if (!this.amK.equals(com_google_android_gms_internal_zzuf_zzc.amK)) {
                return false;
            }
            if (this.amL == null) {
                if (com_google_android_gms_internal_zzuf_zzc.amL != null) {
                    return false;
                }
            } else if (!this.amL.equals(com_google_android_gms_internal_zzuf_zzc.amL)) {
                return false;
            }
            return this.amM == null ? com_google_android_gms_internal_zzuf_zzc.amM == null : this.amM.equals(com_google_android_gms_internal_zzuf_zzc.amM);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.amL == null ? 0 : this.amL.hashCode()) + (((this.amK == null ? 0 : this.amK.hashCode()) + (((this.amJ == null ? 0 : this.amJ.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31;
            if (this.amM != null) {
                i = this.amM.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.amJ != null) {
                com_google_android_gms_internal_zzapo.zza(1, this.amJ);
            }
            if (this.amK != null) {
                com_google_android_gms_internal_zzapo.zza(2, this.amK);
            }
            if (this.amL != null) {
                com_google_android_gms_internal_zzapo.zzj(3, this.amL.booleanValue());
            }
            if (this.amM != null) {
                com_google_android_gms_internal_zzapo.zzr(4, this.amM);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzc zzae(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        if (this.amJ == null) {
                            this.amJ = new zzf();
                        }
                        com_google_android_gms_internal_zzapn.zza(this.amJ);
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        if (this.amK == null) {
                            this.amK = new zzd();
                        }
                        com_google_android_gms_internal_zzapn.zza(this.amK);
                        continue;
                    case TelnetOption.TERMINAL_TYPE /*24*/:
                        this.amL = Boolean.valueOf(com_google_android_gms_internal_zzapn.an());
                        continue;
                    case TelnetOption.LINEMODE /*34*/:
                        this.amM = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    default:
                        if (!zzapy.zzb(com_google_android_gms_internal_zzapn, ah)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzae(com_google_android_gms_internal_zzapn);
        }

        public zzc zzbvi() {
            this.amJ = null;
            this.amK = null;
            this.amL = null;
            this.amM = null;
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int zzx = super.zzx();
            if (this.amJ != null) {
                zzx += zzapo.zzc(1, this.amJ);
            }
            if (this.amK != null) {
                zzx += zzapo.zzc(2, this.amK);
            }
            if (this.amL != null) {
                zzx += zzapo.zzk(3, this.amL.booleanValue());
            }
            return this.amM != null ? zzx + zzapo.zzs(4, this.amM) : zzx;
        }
    }

    public static final class zzd extends zzapv {
        public Integer amN;
        public Boolean amO;
        public String amP;
        public String amQ;
        public String amR;

        public zzd() {
            zzbvj();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzd)) {
                return false;
            }
            zzd com_google_android_gms_internal_zzuf_zzd = (zzd) obj;
            if (this.amN == null) {
                if (com_google_android_gms_internal_zzuf_zzd.amN != null) {
                    return false;
                }
            } else if (!this.amN.equals(com_google_android_gms_internal_zzuf_zzd.amN)) {
                return false;
            }
            if (this.amO == null) {
                if (com_google_android_gms_internal_zzuf_zzd.amO != null) {
                    return false;
                }
            } else if (!this.amO.equals(com_google_android_gms_internal_zzuf_zzd.amO)) {
                return false;
            }
            if (this.amP == null) {
                if (com_google_android_gms_internal_zzuf_zzd.amP != null) {
                    return false;
                }
            } else if (!this.amP.equals(com_google_android_gms_internal_zzuf_zzd.amP)) {
                return false;
            }
            if (this.amQ == null) {
                if (com_google_android_gms_internal_zzuf_zzd.amQ != null) {
                    return false;
                }
            } else if (!this.amQ.equals(com_google_android_gms_internal_zzuf_zzd.amQ)) {
                return false;
            }
            return this.amR == null ? com_google_android_gms_internal_zzuf_zzd.amR == null : this.amR.equals(com_google_android_gms_internal_zzuf_zzd.amR);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.amQ == null ? 0 : this.amQ.hashCode()) + (((this.amP == null ? 0 : this.amP.hashCode()) + (((this.amO == null ? 0 : this.amO.hashCode()) + (((this.amN == null ? 0 : this.amN.intValue()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31;
            if (this.amR != null) {
                i = this.amR.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.amN != null) {
                com_google_android_gms_internal_zzapo.zzae(1, this.amN.intValue());
            }
            if (this.amO != null) {
                com_google_android_gms_internal_zzapo.zzj(2, this.amO.booleanValue());
            }
            if (this.amP != null) {
                com_google_android_gms_internal_zzapo.zzr(3, this.amP);
            }
            if (this.amQ != null) {
                com_google_android_gms_internal_zzapo.zzr(4, this.amQ);
            }
            if (this.amR != null) {
                com_google_android_gms_internal_zzapo.zzr(5, this.amR);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzd zzaf(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        ah = com_google_android_gms_internal_zzapn.al();
                        switch (ah) {
                            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                                this.amN = Integer.valueOf(ah);
                                break;
                            default:
                                continue;
                        }
                    case ConnectionResult.API_UNAVAILABLE /*16*/:
                        this.amO = Boolean.valueOf(com_google_android_gms_internal_zzapn.an());
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        this.amP = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case TelnetOption.LINEMODE /*34*/:
                        this.amQ = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case JumpInfo.RESULT_FORCE_VERIFY_ACTIVITY /*42*/:
                        this.amR = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    default:
                        if (!zzapy.zzb(com_google_android_gms_internal_zzapn, ah)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzaf(com_google_android_gms_internal_zzapn);
        }

        public zzd zzbvj() {
            this.amO = null;
            this.amP = null;
            this.amQ = null;
            this.amR = null;
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int zzx = super.zzx();
            if (this.amN != null) {
                zzx += zzapo.zzag(1, this.amN.intValue());
            }
            if (this.amO != null) {
                zzx += zzapo.zzk(2, this.amO.booleanValue());
            }
            if (this.amP != null) {
                zzx += zzapo.zzs(3, this.amP);
            }
            if (this.amQ != null) {
                zzx += zzapo.zzs(4, this.amQ);
            }
            return this.amR != null ? zzx + zzapo.zzs(5, this.amR) : zzx;
        }
    }

    public static final class zze extends zzapv {
        private static volatile zze[] amS;
        public Integer amD;
        public String amT;
        public zzc amU;

        public zze() {
            zzbvl();
        }

        public static zze[] zzbvk() {
            if (amS == null) {
                synchronized (zzapt.bjF) {
                    if (amS == null) {
                        amS = new zze[0];
                    }
                }
            }
            return amS;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zze)) {
                return false;
            }
            zze com_google_android_gms_internal_zzuf_zze = (zze) obj;
            if (this.amD == null) {
                if (com_google_android_gms_internal_zzuf_zze.amD != null) {
                    return false;
                }
            } else if (!this.amD.equals(com_google_android_gms_internal_zzuf_zze.amD)) {
                return false;
            }
            if (this.amT == null) {
                if (com_google_android_gms_internal_zzuf_zze.amT != null) {
                    return false;
                }
            } else if (!this.amT.equals(com_google_android_gms_internal_zzuf_zze.amT)) {
                return false;
            }
            return this.amU == null ? com_google_android_gms_internal_zzuf_zze.amU == null : this.amU.equals(com_google_android_gms_internal_zzuf_zze.amU);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.amT == null ? 0 : this.amT.hashCode()) + (((this.amD == null ? 0 : this.amD.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31;
            if (this.amU != null) {
                i = this.amU.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.amD != null) {
                com_google_android_gms_internal_zzapo.zzae(1, this.amD.intValue());
            }
            if (this.amT != null) {
                com_google_android_gms_internal_zzapo.zzr(2, this.amT);
            }
            if (this.amU != null) {
                com_google_android_gms_internal_zzapo.zza(3, this.amU);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zze zzag(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        this.amD = Integer.valueOf(com_google_android_gms_internal_zzapn.al());
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        this.amT = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        if (this.amU == null) {
                            this.amU = new zzc();
                        }
                        com_google_android_gms_internal_zzapn.zza(this.amU);
                        continue;
                    default:
                        if (!zzapy.zzb(com_google_android_gms_internal_zzapn, ah)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzag(com_google_android_gms_internal_zzapn);
        }

        public zze zzbvl() {
            this.amD = null;
            this.amT = null;
            this.amU = null;
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int zzx = super.zzx();
            if (this.amD != null) {
                zzx += zzapo.zzag(1, this.amD.intValue());
            }
            if (this.amT != null) {
                zzx += zzapo.zzs(2, this.amT);
            }
            return this.amU != null ? zzx + zzapo.zzc(3, this.amU) : zzx;
        }
    }

    public static final class zzf extends zzapv {
        public Integer amV;
        public String amW;
        public Boolean amX;
        public String[] amY;

        public zzf() {
            zzbvm();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzf)) {
                return false;
            }
            zzf com_google_android_gms_internal_zzuf_zzf = (zzf) obj;
            if (this.amV == null) {
                if (com_google_android_gms_internal_zzuf_zzf.amV != null) {
                    return false;
                }
            } else if (!this.amV.equals(com_google_android_gms_internal_zzuf_zzf.amV)) {
                return false;
            }
            if (this.amW == null) {
                if (com_google_android_gms_internal_zzuf_zzf.amW != null) {
                    return false;
                }
            } else if (!this.amW.equals(com_google_android_gms_internal_zzuf_zzf.amW)) {
                return false;
            }
            if (this.amX == null) {
                if (com_google_android_gms_internal_zzuf_zzf.amX != null) {
                    return false;
                }
            } else if (!this.amX.equals(com_google_android_gms_internal_zzuf_zzf.amX)) {
                return false;
            }
            return zzapt.equals(this.amY, com_google_android_gms_internal_zzuf_zzf.amY);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.amW == null ? 0 : this.amW.hashCode()) + (((this.amV == null ? 0 : this.amV.intValue()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31;
            if (this.amX != null) {
                i = this.amX.hashCode();
            }
            return ((hashCode + i) * 31) + zzapt.hashCode(this.amY);
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.amV != null) {
                com_google_android_gms_internal_zzapo.zzae(1, this.amV.intValue());
            }
            if (this.amW != null) {
                com_google_android_gms_internal_zzapo.zzr(2, this.amW);
            }
            if (this.amX != null) {
                com_google_android_gms_internal_zzapo.zzj(3, this.amX.booleanValue());
            }
            if (this.amY != null && this.amY.length > 0) {
                for (String str : this.amY) {
                    if (str != null) {
                        com_google_android_gms_internal_zzapo.zzr(4, str);
                    }
                }
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzf zzah(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        ah = com_google_android_gms_internal_zzapn.al();
                        switch (ah) {
                            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                                this.amV = Integer.valueOf(ah);
                                break;
                            default:
                                continue;
                        }
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        this.amW = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case TelnetOption.TERMINAL_TYPE /*24*/:
                        this.amX = Boolean.valueOf(com_google_android_gms_internal_zzapn.an());
                        continue;
                    case TelnetOption.LINEMODE /*34*/:
                        int zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 34);
                        ah = this.amY == null ? 0 : this.amY.length;
                        Object obj = new String[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.amY, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.readString();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.readString();
                        this.amY = obj;
                        continue;
                    default:
                        if (!zzapy.zzb(com_google_android_gms_internal_zzapn, ah)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public /* synthetic */ zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            return zzah(com_google_android_gms_internal_zzapn);
        }

        public zzf zzbvm() {
            this.amW = null;
            this.amX = null;
            this.amY = zzapy.bjM;
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int i = 0;
            int zzx = super.zzx();
            if (this.amV != null) {
                zzx += zzapo.zzag(1, this.amV.intValue());
            }
            if (this.amW != null) {
                zzx += zzapo.zzs(2, this.amW);
            }
            if (this.amX != null) {
                zzx += zzapo.zzk(3, this.amX.booleanValue());
            }
            if (this.amY == null || this.amY.length <= 0) {
                return zzx;
            }
            int i2 = 0;
            int i3 = 0;
            while (i < this.amY.length) {
                String str = this.amY[i];
                if (str != null) {
                    i3++;
                    i2 += zzapo.zztx(str);
                }
                i++;
            }
            return (zzx + i2) + (i3 * 1);
        }
    }
}
