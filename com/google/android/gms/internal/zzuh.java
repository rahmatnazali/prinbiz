package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.jumpinfo.JumpInfo;
import com.hiti.prinbiz.BorderDelFragment;
import com.hiti.ui.drawview.DrawView;
import com.hiti.utility.grid.ImageAdapter;
import java.io.IOException;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.telnet.TelnetCommand;
import org.apache.commons.net.telnet.TelnetOption;
import org.apache.commons.net.time.TimeUDPClient;
import org.kxml2.wap.Wbxml;

public interface zzuh {

    public static final class zza extends zzapv {
        private static volatile zza[] ani;
        public Integer amz;
        public zzf anj;
        public zzf ank;
        public Boolean anl;

        public zza() {
            zzbvt();
        }

        public static zza[] zzbvs() {
            if (ani == null) {
                synchronized (zzapt.bjF) {
                    if (ani == null) {
                        ani = new zza[0];
                    }
                }
            }
            return ani;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_internal_zzuh_zza = (zza) obj;
            if (this.amz == null) {
                if (com_google_android_gms_internal_zzuh_zza.amz != null) {
                    return false;
                }
            } else if (!this.amz.equals(com_google_android_gms_internal_zzuh_zza.amz)) {
                return false;
            }
            if (this.anj == null) {
                if (com_google_android_gms_internal_zzuh_zza.anj != null) {
                    return false;
                }
            } else if (!this.anj.equals(com_google_android_gms_internal_zzuh_zza.anj)) {
                return false;
            }
            if (this.ank == null) {
                if (com_google_android_gms_internal_zzuh_zza.ank != null) {
                    return false;
                }
            } else if (!this.ank.equals(com_google_android_gms_internal_zzuh_zza.ank)) {
                return false;
            }
            return this.anl == null ? com_google_android_gms_internal_zzuh_zza.anl == null : this.anl.equals(com_google_android_gms_internal_zzuh_zza.anl);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.ank == null ? 0 : this.ank.hashCode()) + (((this.anj == null ? 0 : this.anj.hashCode()) + (((this.amz == null ? 0 : this.amz.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31;
            if (this.anl != null) {
                i = this.anl.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.amz != null) {
                com_google_android_gms_internal_zzapo.zzae(1, this.amz.intValue());
            }
            if (this.anj != null) {
                com_google_android_gms_internal_zzapo.zza(2, this.anj);
            }
            if (this.ank != null) {
                com_google_android_gms_internal_zzapo.zza(3, this.ank);
            }
            if (this.anl != null) {
                com_google_android_gms_internal_zzapo.zzj(4, this.anl.booleanValue());
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zza zzal(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        this.amz = Integer.valueOf(com_google_android_gms_internal_zzapn.al());
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        if (this.anj == null) {
                            this.anj = new zzf();
                        }
                        com_google_android_gms_internal_zzapn.zza(this.anj);
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        if (this.ank == null) {
                            this.ank = new zzf();
                        }
                        com_google_android_gms_internal_zzapn.zza(this.ank);
                        continue;
                    case BorderDelFragment.PENDDING_SIZE /*32*/:
                        this.anl = Boolean.valueOf(com_google_android_gms_internal_zzapn.an());
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
            return zzal(com_google_android_gms_internal_zzapn);
        }

        public zza zzbvt() {
            this.amz = null;
            this.anj = null;
            this.ank = null;
            this.anl = null;
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int zzx = super.zzx();
            if (this.amz != null) {
                zzx += zzapo.zzag(1, this.amz.intValue());
            }
            if (this.anj != null) {
                zzx += zzapo.zzc(2, this.anj);
            }
            if (this.ank != null) {
                zzx += zzapo.zzc(3, this.ank);
            }
            return this.anl != null ? zzx + zzapo.zzk(4, this.anl.booleanValue()) : zzx;
        }
    }

    public static final class zzb extends zzapv {
        private static volatile zzb[] anm;
        public zzc[] ann;
        public Long ano;
        public Long anp;
        public Integer count;
        public String name;

        public zzb() {
            zzbvv();
        }

        public static zzb[] zzbvu() {
            if (anm == null) {
                synchronized (zzapt.bjF) {
                    if (anm == null) {
                        anm = new zzb[0];
                    }
                }
            }
            return anm;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzb)) {
                return false;
            }
            zzb com_google_android_gms_internal_zzuh_zzb = (zzb) obj;
            if (!zzapt.equals(this.ann, com_google_android_gms_internal_zzuh_zzb.ann)) {
                return false;
            }
            if (this.name == null) {
                if (com_google_android_gms_internal_zzuh_zzb.name != null) {
                    return false;
                }
            } else if (!this.name.equals(com_google_android_gms_internal_zzuh_zzb.name)) {
                return false;
            }
            if (this.ano == null) {
                if (com_google_android_gms_internal_zzuh_zzb.ano != null) {
                    return false;
                }
            } else if (!this.ano.equals(com_google_android_gms_internal_zzuh_zzb.ano)) {
                return false;
            }
            if (this.anp == null) {
                if (com_google_android_gms_internal_zzuh_zzb.anp != null) {
                    return false;
                }
            } else if (!this.anp.equals(com_google_android_gms_internal_zzuh_zzb.anp)) {
                return false;
            }
            return this.count == null ? com_google_android_gms_internal_zzuh_zzb.count == null : this.count.equals(com_google_android_gms_internal_zzuh_zzb.count);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.anp == null ? 0 : this.anp.hashCode()) + (((this.ano == null ? 0 : this.ano.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + zzapt.hashCode(this.ann)) * 31)) * 31)) * 31)) * 31;
            if (this.count != null) {
                i = this.count.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.ann != null && this.ann.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv : this.ann) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        com_google_android_gms_internal_zzapo.zza(1, com_google_android_gms_internal_zzapv);
                    }
                }
            }
            if (this.name != null) {
                com_google_android_gms_internal_zzapo.zzr(2, this.name);
            }
            if (this.ano != null) {
                com_google_android_gms_internal_zzapo.zzb(3, this.ano.longValue());
            }
            if (this.anp != null) {
                com_google_android_gms_internal_zzapo.zzb(4, this.anp.longValue());
            }
            if (this.count != null) {
                com_google_android_gms_internal_zzapo.zzae(5, this.count.intValue());
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzb zzam(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        int zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 10);
                        ah = this.ann == null ? 0 : this.ann.length;
                        Object obj = new zzc[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.ann, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zzc();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zzc();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.ann = obj;
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        this.name = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case TelnetOption.TERMINAL_TYPE /*24*/:
                        this.ano = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case BorderDelFragment.PENDDING_SIZE /*32*/:
                        this.anp = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case ImageAdapter.PENDDING_SIZE /*40*/:
                        this.count = Integer.valueOf(com_google_android_gms_internal_zzapn.al());
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
            return zzam(com_google_android_gms_internal_zzapn);
        }

        public zzb zzbvv() {
            this.ann = zzc.zzbvw();
            this.name = null;
            this.ano = null;
            this.anp = null;
            this.count = null;
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int zzx = super.zzx();
            if (this.ann != null && this.ann.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv : this.ann) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        zzx += zzapo.zzc(1, com_google_android_gms_internal_zzapv);
                    }
                }
            }
            if (this.name != null) {
                zzx += zzapo.zzs(2, this.name);
            }
            if (this.ano != null) {
                zzx += zzapo.zze(3, this.ano.longValue());
            }
            if (this.anp != null) {
                zzx += zzapo.zze(4, this.anp.longValue());
            }
            return this.count != null ? zzx + zzapo.zzag(5, this.count.intValue()) : zzx;
        }
    }

    public static final class zzc extends zzapv {
        private static volatile zzc[] anq;
        public Float amv;
        public Double amw;
        public Long anr;
        public String name;
        public String zD;

        public zzc() {
            zzbvx();
        }

        public static zzc[] zzbvw() {
            if (anq == null) {
                synchronized (zzapt.bjF) {
                    if (anq == null) {
                        anq = new zzc[0];
                    }
                }
            }
            return anq;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzc)) {
                return false;
            }
            zzc com_google_android_gms_internal_zzuh_zzc = (zzc) obj;
            if (this.name == null) {
                if (com_google_android_gms_internal_zzuh_zzc.name != null) {
                    return false;
                }
            } else if (!this.name.equals(com_google_android_gms_internal_zzuh_zzc.name)) {
                return false;
            }
            if (this.zD == null) {
                if (com_google_android_gms_internal_zzuh_zzc.zD != null) {
                    return false;
                }
            } else if (!this.zD.equals(com_google_android_gms_internal_zzuh_zzc.zD)) {
                return false;
            }
            if (this.anr == null) {
                if (com_google_android_gms_internal_zzuh_zzc.anr != null) {
                    return false;
                }
            } else if (!this.anr.equals(com_google_android_gms_internal_zzuh_zzc.anr)) {
                return false;
            }
            if (this.amv == null) {
                if (com_google_android_gms_internal_zzuh_zzc.amv != null) {
                    return false;
                }
            } else if (!this.amv.equals(com_google_android_gms_internal_zzuh_zzc.amv)) {
                return false;
            }
            return this.amw == null ? com_google_android_gms_internal_zzuh_zzc.amw == null : this.amw.equals(com_google_android_gms_internal_zzuh_zzc.amw);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.amv == null ? 0 : this.amv.hashCode()) + (((this.anr == null ? 0 : this.anr.hashCode()) + (((this.zD == null ? 0 : this.zD.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31;
            if (this.amw != null) {
                i = this.amw.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.name != null) {
                com_google_android_gms_internal_zzapo.zzr(1, this.name);
            }
            if (this.zD != null) {
                com_google_android_gms_internal_zzapo.zzr(2, this.zD);
            }
            if (this.anr != null) {
                com_google_android_gms_internal_zzapo.zzb(3, this.anr.longValue());
            }
            if (this.amv != null) {
                com_google_android_gms_internal_zzapo.zzc(4, this.amv.floatValue());
            }
            if (this.amw != null) {
                com_google_android_gms_internal_zzapo.zza(5, this.amw.doubleValue());
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzc zzan(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        this.name = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        this.zD = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case TelnetOption.TERMINAL_TYPE /*24*/:
                        this.anr = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case TimeUDPClient.DEFAULT_PORT /*37*/:
                        this.amv = Float.valueOf(com_google_android_gms_internal_zzapn.readFloat());
                        continue;
                    case JumpInfo.RESULT_UPLOAD_PHOTO_BACK_GALLERY /*41*/:
                        this.amw = Double.valueOf(com_google_android_gms_internal_zzapn.readDouble());
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
            return zzan(com_google_android_gms_internal_zzapn);
        }

        public zzc zzbvx() {
            this.name = null;
            this.zD = null;
            this.anr = null;
            this.amv = null;
            this.amw = null;
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int zzx = super.zzx();
            if (this.name != null) {
                zzx += zzapo.zzs(1, this.name);
            }
            if (this.zD != null) {
                zzx += zzapo.zzs(2, this.zD);
            }
            if (this.anr != null) {
                zzx += zzapo.zze(3, this.anr.longValue());
            }
            if (this.amv != null) {
                zzx += zzapo.zzd(4, this.amv.floatValue());
            }
            return this.amw != null ? zzx + zzapo.zzb(5, this.amw.doubleValue()) : zzx;
        }
    }

    public static final class zzd extends zzapv {
        public zze[] ans;

        public zzd() {
            zzbvy();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzd)) {
                return false;
            }
            return zzapt.equals(this.ans, ((zzd) obj).ans);
        }

        public int hashCode() {
            return ((getClass().getName().hashCode() + 527) * 31) + zzapt.hashCode(this.ans);
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.ans != null && this.ans.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv : this.ans) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        com_google_android_gms_internal_zzapo.zza(1, com_google_android_gms_internal_zzapv);
                    }
                }
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzd zzao(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        int zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 10);
                        ah = this.ans == null ? 0 : this.ans.length;
                        Object obj = new zze[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.ans, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zze();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zze();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.ans = obj;
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
            return zzao(com_google_android_gms_internal_zzapn);
        }

        public zzd zzbvy() {
            this.ans = zze.zzbvz();
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int zzx = super.zzx();
            if (this.ans != null && this.ans.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv : this.ans) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        zzx += zzapo.zzc(1, com_google_android_gms_internal_zzapv);
                    }
                }
            }
            return zzx;
        }
    }

    public static final class zze extends zzapv {
        private static volatile zze[] ant;
        public String aav;
        public String aic;
        public String aid;
        public String aig;
        public String aik;
        public Long anA;
        public Long anB;
        public String anC;
        public String anD;
        public String anE;
        public Integer anF;
        public Long anG;
        public Long anH;
        public String anI;
        public Boolean anJ;
        public String anK;
        public Long anL;
        public Integer anM;
        public Boolean anN;
        public zza[] anO;
        public Integer anP;
        public Integer anQ;
        public Integer anR;
        public String anS;
        public Integer anu;
        public zzb[] anv;
        public zzg[] anw;
        public Long anx;
        public Long any;
        public Long anz;
        public String zzck;
        public String zzct;

        public zze() {
            zzbwa();
        }

        public static zze[] zzbvz() {
            if (ant == null) {
                synchronized (zzapt.bjF) {
                    if (ant == null) {
                        ant = new zze[0];
                    }
                }
            }
            return ant;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zze)) {
                return false;
            }
            zze com_google_android_gms_internal_zzuh_zze = (zze) obj;
            if (this.anu == null) {
                if (com_google_android_gms_internal_zzuh_zze.anu != null) {
                    return false;
                }
            } else if (!this.anu.equals(com_google_android_gms_internal_zzuh_zze.anu)) {
                return false;
            }
            if (!zzapt.equals(this.anv, com_google_android_gms_internal_zzuh_zze.anv)) {
                return false;
            }
            if (!zzapt.equals(this.anw, com_google_android_gms_internal_zzuh_zze.anw)) {
                return false;
            }
            if (this.anx == null) {
                if (com_google_android_gms_internal_zzuh_zze.anx != null) {
                    return false;
                }
            } else if (!this.anx.equals(com_google_android_gms_internal_zzuh_zze.anx)) {
                return false;
            }
            if (this.any == null) {
                if (com_google_android_gms_internal_zzuh_zze.any != null) {
                    return false;
                }
            } else if (!this.any.equals(com_google_android_gms_internal_zzuh_zze.any)) {
                return false;
            }
            if (this.anz == null) {
                if (com_google_android_gms_internal_zzuh_zze.anz != null) {
                    return false;
                }
            } else if (!this.anz.equals(com_google_android_gms_internal_zzuh_zze.anz)) {
                return false;
            }
            if (this.anA == null) {
                if (com_google_android_gms_internal_zzuh_zze.anA != null) {
                    return false;
                }
            } else if (!this.anA.equals(com_google_android_gms_internal_zzuh_zze.anA)) {
                return false;
            }
            if (this.anB == null) {
                if (com_google_android_gms_internal_zzuh_zze.anB != null) {
                    return false;
                }
            } else if (!this.anB.equals(com_google_android_gms_internal_zzuh_zze.anB)) {
                return false;
            }
            if (this.anC == null) {
                if (com_google_android_gms_internal_zzuh_zze.anC != null) {
                    return false;
                }
            } else if (!this.anC.equals(com_google_android_gms_internal_zzuh_zze.anC)) {
                return false;
            }
            if (this.zzct == null) {
                if (com_google_android_gms_internal_zzuh_zze.zzct != null) {
                    return false;
                }
            } else if (!this.zzct.equals(com_google_android_gms_internal_zzuh_zze.zzct)) {
                return false;
            }
            if (this.anD == null) {
                if (com_google_android_gms_internal_zzuh_zze.anD != null) {
                    return false;
                }
            } else if (!this.anD.equals(com_google_android_gms_internal_zzuh_zze.anD)) {
                return false;
            }
            if (this.anE == null) {
                if (com_google_android_gms_internal_zzuh_zze.anE != null) {
                    return false;
                }
            } else if (!this.anE.equals(com_google_android_gms_internal_zzuh_zze.anE)) {
                return false;
            }
            if (this.anF == null) {
                if (com_google_android_gms_internal_zzuh_zze.anF != null) {
                    return false;
                }
            } else if (!this.anF.equals(com_google_android_gms_internal_zzuh_zze.anF)) {
                return false;
            }
            if (this.aid == null) {
                if (com_google_android_gms_internal_zzuh_zze.aid != null) {
                    return false;
                }
            } else if (!this.aid.equals(com_google_android_gms_internal_zzuh_zze.aid)) {
                return false;
            }
            if (this.zzck == null) {
                if (com_google_android_gms_internal_zzuh_zze.zzck != null) {
                    return false;
                }
            } else if (!this.zzck.equals(com_google_android_gms_internal_zzuh_zze.zzck)) {
                return false;
            }
            if (this.aav == null) {
                if (com_google_android_gms_internal_zzuh_zze.aav != null) {
                    return false;
                }
            } else if (!this.aav.equals(com_google_android_gms_internal_zzuh_zze.aav)) {
                return false;
            }
            if (this.anG == null) {
                if (com_google_android_gms_internal_zzuh_zze.anG != null) {
                    return false;
                }
            } else if (!this.anG.equals(com_google_android_gms_internal_zzuh_zze.anG)) {
                return false;
            }
            if (this.anH == null) {
                if (com_google_android_gms_internal_zzuh_zze.anH != null) {
                    return false;
                }
            } else if (!this.anH.equals(com_google_android_gms_internal_zzuh_zze.anH)) {
                return false;
            }
            if (this.anI == null) {
                if (com_google_android_gms_internal_zzuh_zze.anI != null) {
                    return false;
                }
            } else if (!this.anI.equals(com_google_android_gms_internal_zzuh_zze.anI)) {
                return false;
            }
            if (this.anJ == null) {
                if (com_google_android_gms_internal_zzuh_zze.anJ != null) {
                    return false;
                }
            } else if (!this.anJ.equals(com_google_android_gms_internal_zzuh_zze.anJ)) {
                return false;
            }
            if (this.anK == null) {
                if (com_google_android_gms_internal_zzuh_zze.anK != null) {
                    return false;
                }
            } else if (!this.anK.equals(com_google_android_gms_internal_zzuh_zze.anK)) {
                return false;
            }
            if (this.anL == null) {
                if (com_google_android_gms_internal_zzuh_zze.anL != null) {
                    return false;
                }
            } else if (!this.anL.equals(com_google_android_gms_internal_zzuh_zze.anL)) {
                return false;
            }
            if (this.anM == null) {
                if (com_google_android_gms_internal_zzuh_zze.anM != null) {
                    return false;
                }
            } else if (!this.anM.equals(com_google_android_gms_internal_zzuh_zze.anM)) {
                return false;
            }
            if (this.aig == null) {
                if (com_google_android_gms_internal_zzuh_zze.aig != null) {
                    return false;
                }
            } else if (!this.aig.equals(com_google_android_gms_internal_zzuh_zze.aig)) {
                return false;
            }
            if (this.aic == null) {
                if (com_google_android_gms_internal_zzuh_zze.aic != null) {
                    return false;
                }
            } else if (!this.aic.equals(com_google_android_gms_internal_zzuh_zze.aic)) {
                return false;
            }
            if (this.anN == null) {
                if (com_google_android_gms_internal_zzuh_zze.anN != null) {
                    return false;
                }
            } else if (!this.anN.equals(com_google_android_gms_internal_zzuh_zze.anN)) {
                return false;
            }
            if (!zzapt.equals(this.anO, com_google_android_gms_internal_zzuh_zze.anO)) {
                return false;
            }
            if (this.aik == null) {
                if (com_google_android_gms_internal_zzuh_zze.aik != null) {
                    return false;
                }
            } else if (!this.aik.equals(com_google_android_gms_internal_zzuh_zze.aik)) {
                return false;
            }
            if (this.anP == null) {
                if (com_google_android_gms_internal_zzuh_zze.anP != null) {
                    return false;
                }
            } else if (!this.anP.equals(com_google_android_gms_internal_zzuh_zze.anP)) {
                return false;
            }
            if (this.anQ == null) {
                if (com_google_android_gms_internal_zzuh_zze.anQ != null) {
                    return false;
                }
            } else if (!this.anQ.equals(com_google_android_gms_internal_zzuh_zze.anQ)) {
                return false;
            }
            if (this.anR == null) {
                if (com_google_android_gms_internal_zzuh_zze.anR != null) {
                    return false;
                }
            } else if (!this.anR.equals(com_google_android_gms_internal_zzuh_zze.anR)) {
                return false;
            }
            return this.anS == null ? com_google_android_gms_internal_zzuh_zze.anS == null : this.anS.equals(com_google_android_gms_internal_zzuh_zze.anS);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.anR == null ? 0 : this.anR.hashCode()) + (((this.anQ == null ? 0 : this.anQ.hashCode()) + (((this.anP == null ? 0 : this.anP.hashCode()) + (((this.aik == null ? 0 : this.aik.hashCode()) + (((((this.anN == null ? 0 : this.anN.hashCode()) + (((this.aic == null ? 0 : this.aic.hashCode()) + (((this.aig == null ? 0 : this.aig.hashCode()) + (((this.anM == null ? 0 : this.anM.hashCode()) + (((this.anL == null ? 0 : this.anL.hashCode()) + (((this.anK == null ? 0 : this.anK.hashCode()) + (((this.anJ == null ? 0 : this.anJ.hashCode()) + (((this.anI == null ? 0 : this.anI.hashCode()) + (((this.anH == null ? 0 : this.anH.hashCode()) + (((this.anG == null ? 0 : this.anG.hashCode()) + (((this.aav == null ? 0 : this.aav.hashCode()) + (((this.zzck == null ? 0 : this.zzck.hashCode()) + (((this.aid == null ? 0 : this.aid.hashCode()) + (((this.anF == null ? 0 : this.anF.hashCode()) + (((this.anE == null ? 0 : this.anE.hashCode()) + (((this.anD == null ? 0 : this.anD.hashCode()) + (((this.zzct == null ? 0 : this.zzct.hashCode()) + (((this.anC == null ? 0 : this.anC.hashCode()) + (((this.anB == null ? 0 : this.anB.hashCode()) + (((this.anA == null ? 0 : this.anA.hashCode()) + (((this.anz == null ? 0 : this.anz.hashCode()) + (((this.any == null ? 0 : this.any.hashCode()) + (((this.anx == null ? 0 : this.anx.hashCode()) + (((((((this.anu == null ? 0 : this.anu.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + zzapt.hashCode(this.anv)) * 31) + zzapt.hashCode(this.anw)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31) + zzapt.hashCode(this.anO)) * 31)) * 31)) * 31)) * 31)) * 31;
            if (this.anS != null) {
                i = this.anS.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            int i = 0;
            if (this.anu != null) {
                com_google_android_gms_internal_zzapo.zzae(1, this.anu.intValue());
            }
            if (this.anv != null && this.anv.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv : this.anv) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        com_google_android_gms_internal_zzapo.zza(2, com_google_android_gms_internal_zzapv);
                    }
                }
            }
            if (this.anw != null && this.anw.length > 0) {
                for (zzapv com_google_android_gms_internal_zzapv2 : this.anw) {
                    if (com_google_android_gms_internal_zzapv2 != null) {
                        com_google_android_gms_internal_zzapo.zza(3, com_google_android_gms_internal_zzapv2);
                    }
                }
            }
            if (this.anx != null) {
                com_google_android_gms_internal_zzapo.zzb(4, this.anx.longValue());
            }
            if (this.any != null) {
                com_google_android_gms_internal_zzapo.zzb(5, this.any.longValue());
            }
            if (this.anz != null) {
                com_google_android_gms_internal_zzapo.zzb(6, this.anz.longValue());
            }
            if (this.anB != null) {
                com_google_android_gms_internal_zzapo.zzb(7, this.anB.longValue());
            }
            if (this.anC != null) {
                com_google_android_gms_internal_zzapo.zzr(8, this.anC);
            }
            if (this.zzct != null) {
                com_google_android_gms_internal_zzapo.zzr(9, this.zzct);
            }
            if (this.anD != null) {
                com_google_android_gms_internal_zzapo.zzr(10, this.anD);
            }
            if (this.anE != null) {
                com_google_android_gms_internal_zzapo.zzr(11, this.anE);
            }
            if (this.anF != null) {
                com_google_android_gms_internal_zzapo.zzae(12, this.anF.intValue());
            }
            if (this.aid != null) {
                com_google_android_gms_internal_zzapo.zzr(13, this.aid);
            }
            if (this.zzck != null) {
                com_google_android_gms_internal_zzapo.zzr(14, this.zzck);
            }
            if (this.aav != null) {
                com_google_android_gms_internal_zzapo.zzr(16, this.aav);
            }
            if (this.anG != null) {
                com_google_android_gms_internal_zzapo.zzb(17, this.anG.longValue());
            }
            if (this.anH != null) {
                com_google_android_gms_internal_zzapo.zzb(18, this.anH.longValue());
            }
            if (this.anI != null) {
                com_google_android_gms_internal_zzapo.zzr(19, this.anI);
            }
            if (this.anJ != null) {
                com_google_android_gms_internal_zzapo.zzj(20, this.anJ.booleanValue());
            }
            if (this.anK != null) {
                com_google_android_gms_internal_zzapo.zzr(21, this.anK);
            }
            if (this.anL != null) {
                com_google_android_gms_internal_zzapo.zzb(22, this.anL.longValue());
            }
            if (this.anM != null) {
                com_google_android_gms_internal_zzapo.zzae(23, this.anM.intValue());
            }
            if (this.aig != null) {
                com_google_android_gms_internal_zzapo.zzr(24, this.aig);
            }
            if (this.aic != null) {
                com_google_android_gms_internal_zzapo.zzr(25, this.aic);
            }
            if (this.anA != null) {
                com_google_android_gms_internal_zzapo.zzb(26, this.anA.longValue());
            }
            if (this.anN != null) {
                com_google_android_gms_internal_zzapo.zzj(28, this.anN.booleanValue());
            }
            if (this.anO != null && this.anO.length > 0) {
                while (i < this.anO.length) {
                    zzapv com_google_android_gms_internal_zzapv3 = this.anO[i];
                    if (com_google_android_gms_internal_zzapv3 != null) {
                        com_google_android_gms_internal_zzapo.zza(29, com_google_android_gms_internal_zzapv3);
                    }
                    i++;
                }
            }
            if (this.aik != null) {
                com_google_android_gms_internal_zzapo.zzr(30, this.aik);
            }
            if (this.anP != null) {
                com_google_android_gms_internal_zzapo.zzae(31, this.anP.intValue());
            }
            if (this.anQ != null) {
                com_google_android_gms_internal_zzapo.zzae(32, this.anQ.intValue());
            }
            if (this.anR != null) {
                com_google_android_gms_internal_zzapo.zzae(33, this.anR.intValue());
            }
            if (this.anS != null) {
                com_google_android_gms_internal_zzapo.zzr(34, this.anS);
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zze zzap(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                int zzc;
                Object obj;
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        this.anu = Integer.valueOf(com_google_android_gms_internal_zzapn.al());
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 18);
                        ah = this.anv == null ? 0 : this.anv.length;
                        obj = new zzb[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.anv, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zzb();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zzb();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.anv = obj;
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 26);
                        ah = this.anw == null ? 0 : this.anw.length;
                        obj = new zzg[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.anw, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zzg();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zzg();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.anw = obj;
                        continue;
                    case BorderDelFragment.PENDDING_SIZE /*32*/:
                        this.anx = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case ImageAdapter.PENDDING_SIZE /*40*/:
                        this.any = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case 48:
                        this.anz = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case JumpInfo.RESULT_PHOTO_FROM_PRINTER_ACTIVITY /*56*/:
                        this.anB = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case Wbxml.EXT_I_2 /*66*/:
                        this.anC = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case JumpInfo.REQUEST_SETTING_CLEAN_MODE /*74*/:
                        this.zzct = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case 82:
                        this.anD = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case 90:
                        this.anE = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case DrawView.BRUSH_STATE /*96*/:
                        this.anF = Integer.valueOf(com_google_android_gms_internal_zzapn.al());
                        continue;
                    case 106:
                        this.aid = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case 114:
                        this.zzck = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                        this.aav = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case 136:
                        this.anG = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case 144:
                        this.anH = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case 154:
                        this.anI = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case 160:
                        this.anJ = Boolean.valueOf(com_google_android_gms_internal_zzapn.an());
                        continue;
                    case 170:
                        this.anK = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case 176:
                        this.anL = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case 184:
                        this.anM = Integer.valueOf(com_google_android_gms_internal_zzapn.al());
                        continue;
                    case Wbxml.EXT_2 /*194*/:
                        this.aig = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case NNTPReply.SLAVE_STATUS_NOTED /*202*/:
                        this.aic = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case 208:
                        this.anA = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case 224:
                        this.anN = Boolean.valueOf(com_google_android_gms_internal_zzapn.an());
                        continue;
                    case FTPReply.SECURITY_DATA_EXCHANGE_COMPLETE /*234*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, FTPReply.SECURITY_DATA_EXCHANGE_COMPLETE);
                        ah = this.anO == null ? 0 : this.anO.length;
                        obj = new zza[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.anO, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = new zza();
                            com_google_android_gms_internal_zzapn.zza(obj[ah]);
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = new zza();
                        com_google_android_gms_internal_zzapn.zza(obj[ah]);
                        this.anO = obj;
                        continue;
                    case TelnetCommand.SYNCH /*242*/:
                        this.aik = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case TelnetCommand.EL /*248*/:
                        this.anP = Integer.valueOf(com_google_android_gms_internal_zzapn.al());
                        continue;
                    case DNSConstants.FLAGS_RD /*256*/:
                        this.anQ = Integer.valueOf(com_google_android_gms_internal_zzapn.al());
                        continue;
                    case 264:
                        this.anR = Integer.valueOf(com_google_android_gms_internal_zzapn.al());
                        continue;
                    case 274:
                        this.anS = com_google_android_gms_internal_zzapn.readString();
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
            return zzap(com_google_android_gms_internal_zzapn);
        }

        public zze zzbwa() {
            this.anu = null;
            this.anv = zzb.zzbvu();
            this.anw = zzg.zzbwc();
            this.anx = null;
            this.any = null;
            this.anz = null;
            this.anA = null;
            this.anB = null;
            this.anC = null;
            this.zzct = null;
            this.anD = null;
            this.anE = null;
            this.anF = null;
            this.aid = null;
            this.zzck = null;
            this.aav = null;
            this.anG = null;
            this.anH = null;
            this.anI = null;
            this.anJ = null;
            this.anK = null;
            this.anL = null;
            this.anM = null;
            this.aig = null;
            this.aic = null;
            this.anN = null;
            this.anO = zza.zzbvs();
            this.aik = null;
            this.anP = null;
            this.anQ = null;
            this.anR = null;
            this.anS = null;
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int i;
            int i2 = 0;
            int zzx = super.zzx();
            if (this.anu != null) {
                zzx += zzapo.zzag(1, this.anu.intValue());
            }
            if (this.anv != null && this.anv.length > 0) {
                i = zzx;
                for (zzapv com_google_android_gms_internal_zzapv : this.anv) {
                    if (com_google_android_gms_internal_zzapv != null) {
                        i += zzapo.zzc(2, com_google_android_gms_internal_zzapv);
                    }
                }
                zzx = i;
            }
            if (this.anw != null && this.anw.length > 0) {
                i = zzx;
                for (zzapv com_google_android_gms_internal_zzapv2 : this.anw) {
                    if (com_google_android_gms_internal_zzapv2 != null) {
                        i += zzapo.zzc(3, com_google_android_gms_internal_zzapv2);
                    }
                }
                zzx = i;
            }
            if (this.anx != null) {
                zzx += zzapo.zze(4, this.anx.longValue());
            }
            if (this.any != null) {
                zzx += zzapo.zze(5, this.any.longValue());
            }
            if (this.anz != null) {
                zzx += zzapo.zze(6, this.anz.longValue());
            }
            if (this.anB != null) {
                zzx += zzapo.zze(7, this.anB.longValue());
            }
            if (this.anC != null) {
                zzx += zzapo.zzs(8, this.anC);
            }
            if (this.zzct != null) {
                zzx += zzapo.zzs(9, this.zzct);
            }
            if (this.anD != null) {
                zzx += zzapo.zzs(10, this.anD);
            }
            if (this.anE != null) {
                zzx += zzapo.zzs(11, this.anE);
            }
            if (this.anF != null) {
                zzx += zzapo.zzag(12, this.anF.intValue());
            }
            if (this.aid != null) {
                zzx += zzapo.zzs(13, this.aid);
            }
            if (this.zzck != null) {
                zzx += zzapo.zzs(14, this.zzck);
            }
            if (this.aav != null) {
                zzx += zzapo.zzs(16, this.aav);
            }
            if (this.anG != null) {
                zzx += zzapo.zze(17, this.anG.longValue());
            }
            if (this.anH != null) {
                zzx += zzapo.zze(18, this.anH.longValue());
            }
            if (this.anI != null) {
                zzx += zzapo.zzs(19, this.anI);
            }
            if (this.anJ != null) {
                zzx += zzapo.zzk(20, this.anJ.booleanValue());
            }
            if (this.anK != null) {
                zzx += zzapo.zzs(21, this.anK);
            }
            if (this.anL != null) {
                zzx += zzapo.zze(22, this.anL.longValue());
            }
            if (this.anM != null) {
                zzx += zzapo.zzag(23, this.anM.intValue());
            }
            if (this.aig != null) {
                zzx += zzapo.zzs(24, this.aig);
            }
            if (this.aic != null) {
                zzx += zzapo.zzs(25, this.aic);
            }
            if (this.anA != null) {
                zzx += zzapo.zze(26, this.anA.longValue());
            }
            if (this.anN != null) {
                zzx += zzapo.zzk(28, this.anN.booleanValue());
            }
            if (this.anO != null && this.anO.length > 0) {
                while (i2 < this.anO.length) {
                    zzapv com_google_android_gms_internal_zzapv3 = this.anO[i2];
                    if (com_google_android_gms_internal_zzapv3 != null) {
                        zzx += zzapo.zzc(29, com_google_android_gms_internal_zzapv3);
                    }
                    i2++;
                }
            }
            if (this.aik != null) {
                zzx += zzapo.zzs(30, this.aik);
            }
            if (this.anP != null) {
                zzx += zzapo.zzag(31, this.anP.intValue());
            }
            if (this.anQ != null) {
                zzx += zzapo.zzag(32, this.anQ.intValue());
            }
            if (this.anR != null) {
                zzx += zzapo.zzag(33, this.anR.intValue());
            }
            return this.anS != null ? zzx + zzapo.zzs(34, this.anS) : zzx;
        }
    }

    public static final class zzf extends zzapv {
        public long[] anT;
        public long[] anU;

        public zzf() {
            zzbwb();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzf)) {
                return false;
            }
            zzf com_google_android_gms_internal_zzuh_zzf = (zzf) obj;
            return !zzapt.equals(this.anT, com_google_android_gms_internal_zzuh_zzf.anT) ? false : zzapt.equals(this.anU, com_google_android_gms_internal_zzuh_zzf.anU);
        }

        public int hashCode() {
            return ((((getClass().getName().hashCode() + 527) * 31) + zzapt.hashCode(this.anT)) * 31) + zzapt.hashCode(this.anU);
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            int i = 0;
            if (this.anT != null && this.anT.length > 0) {
                for (long zza : this.anT) {
                    com_google_android_gms_internal_zzapo.zza(1, zza);
                }
            }
            if (this.anU != null && this.anU.length > 0) {
                while (i < this.anU.length) {
                    com_google_android_gms_internal_zzapo.zza(2, this.anU[i]);
                    i++;
                }
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzf zzaq(zzapn com_google_android_gms_internal_zzapn) throws IOException {
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
                        ah = this.anT == null ? 0 : this.anT.length;
                        obj = new long[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.anT, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.aj();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.aj();
                        this.anT = obj;
                        continue;
                    case ConnectionResult.DEVELOPER_ERROR /*10*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.aj();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.anT == null ? 0 : this.anT.length;
                        obj2 = new long[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.anT, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.aj();
                            zzc++;
                        }
                        this.anT = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
                        continue;
                    case ConnectionResult.API_UNAVAILABLE /*16*/:
                        zzc = zzapy.zzc(com_google_android_gms_internal_zzapn, 16);
                        ah = this.anU == null ? 0 : this.anU.length;
                        obj = new long[(zzc + ah)];
                        if (ah != 0) {
                            System.arraycopy(this.anU, 0, obj, 0, ah);
                        }
                        while (ah < obj.length - 1) {
                            obj[ah] = com_google_android_gms_internal_zzapn.aj();
                            com_google_android_gms_internal_zzapn.ah();
                            ah++;
                        }
                        obj[ah] = com_google_android_gms_internal_zzapn.aj();
                        this.anU = obj;
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        zzafr = com_google_android_gms_internal_zzapn.zzafr(com_google_android_gms_internal_zzapn.aq());
                        zzc = com_google_android_gms_internal_zzapn.getPosition();
                        ah = 0;
                        while (com_google_android_gms_internal_zzapn.av() > 0) {
                            com_google_android_gms_internal_zzapn.aj();
                            ah++;
                        }
                        com_google_android_gms_internal_zzapn.zzaft(zzc);
                        zzc = this.anU == null ? 0 : this.anU.length;
                        obj2 = new long[(ah + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.anU, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzapn.aj();
                            zzc++;
                        }
                        this.anU = obj2;
                        com_google_android_gms_internal_zzapn.zzafs(zzafr);
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
            return zzaq(com_google_android_gms_internal_zzapn);
        }

        public zzf zzbwb() {
            this.anT = zzapy.bjI;
            this.anU = zzapy.bjI;
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int i;
            int i2;
            int i3 = 0;
            int zzx = super.zzx();
            if (this.anT == null || this.anT.length <= 0) {
                i = zzx;
            } else {
                i2 = 0;
                for (long zzcx : this.anT) {
                    i2 += zzapo.zzcx(zzcx);
                }
                i = (zzx + i2) + (this.anT.length * 1);
            }
            if (this.anU == null || this.anU.length <= 0) {
                return i;
            }
            i2 = 0;
            while (i3 < this.anU.length) {
                i2 += zzapo.zzcx(this.anU[i3]);
                i3++;
            }
            return (i + i2) + (this.anU.length * 1);
        }
    }

    public static final class zzg extends zzapv {
        private static volatile zzg[] anV;
        public Float amv;
        public Double amw;
        public Long anW;
        public Long anr;
        public String name;
        public String zD;

        public zzg() {
            zzbwd();
        }

        public static zzg[] zzbwc() {
            if (anV == null) {
                synchronized (zzapt.bjF) {
                    if (anV == null) {
                        anV = new zzg[0];
                    }
                }
            }
            return anV;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzg)) {
                return false;
            }
            zzg com_google_android_gms_internal_zzuh_zzg = (zzg) obj;
            if (this.anW == null) {
                if (com_google_android_gms_internal_zzuh_zzg.anW != null) {
                    return false;
                }
            } else if (!this.anW.equals(com_google_android_gms_internal_zzuh_zzg.anW)) {
                return false;
            }
            if (this.name == null) {
                if (com_google_android_gms_internal_zzuh_zzg.name != null) {
                    return false;
                }
            } else if (!this.name.equals(com_google_android_gms_internal_zzuh_zzg.name)) {
                return false;
            }
            if (this.zD == null) {
                if (com_google_android_gms_internal_zzuh_zzg.zD != null) {
                    return false;
                }
            } else if (!this.zD.equals(com_google_android_gms_internal_zzuh_zzg.zD)) {
                return false;
            }
            if (this.anr == null) {
                if (com_google_android_gms_internal_zzuh_zzg.anr != null) {
                    return false;
                }
            } else if (!this.anr.equals(com_google_android_gms_internal_zzuh_zzg.anr)) {
                return false;
            }
            if (this.amv == null) {
                if (com_google_android_gms_internal_zzuh_zzg.amv != null) {
                    return false;
                }
            } else if (!this.amv.equals(com_google_android_gms_internal_zzuh_zzg.amv)) {
                return false;
            }
            return this.amw == null ? com_google_android_gms_internal_zzuh_zzg.amw == null : this.amw.equals(com_google_android_gms_internal_zzuh_zzg.amw);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.amv == null ? 0 : this.amv.hashCode()) + (((this.anr == null ? 0 : this.anr.hashCode()) + (((this.zD == null ? 0 : this.zD.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + (((this.anW == null ? 0 : this.anW.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
            if (this.amw != null) {
                i = this.amw.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
            if (this.anW != null) {
                com_google_android_gms_internal_zzapo.zzb(1, this.anW.longValue());
            }
            if (this.name != null) {
                com_google_android_gms_internal_zzapo.zzr(2, this.name);
            }
            if (this.zD != null) {
                com_google_android_gms_internal_zzapo.zzr(3, this.zD);
            }
            if (this.anr != null) {
                com_google_android_gms_internal_zzapo.zzb(4, this.anr.longValue());
            }
            if (this.amv != null) {
                com_google_android_gms_internal_zzapo.zzc(5, this.amv.floatValue());
            }
            if (this.amw != null) {
                com_google_android_gms_internal_zzapo.zza(6, this.amw.doubleValue());
            }
            super.zza(com_google_android_gms_internal_zzapo);
        }

        public zzg zzar(zzapn com_google_android_gms_internal_zzapn) throws IOException {
            while (true) {
                int ah = com_google_android_gms_internal_zzapn.ah();
                switch (ah) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                        break;
                    case ConnectionResult.INTERNAL_ERROR /*8*/:
                        this.anW = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case ConnectionResult.SERVICE_UPDATING /*18*/:
                        this.name = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                        this.zD = com_google_android_gms_internal_zzapn.readString();
                        continue;
                    case BorderDelFragment.PENDDING_SIZE /*32*/:
                        this.anr = Long.valueOf(com_google_android_gms_internal_zzapn.ak());
                        continue;
                    case JumpInfo.REQUEST_SNAP_PRINT /*45*/:
                        this.amv = Float.valueOf(com_google_android_gms_internal_zzapn.readFloat());
                        continue;
                    case 49:
                        this.amw = Double.valueOf(com_google_android_gms_internal_zzapn.readDouble());
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
            return zzar(com_google_android_gms_internal_zzapn);
        }

        public zzg zzbwd() {
            this.anW = null;
            this.name = null;
            this.zD = null;
            this.anr = null;
            this.amv = null;
            this.amw = null;
            this.bjG = -1;
            return this;
        }

        protected int zzx() {
            int zzx = super.zzx();
            if (this.anW != null) {
                zzx += zzapo.zze(1, this.anW.longValue());
            }
            if (this.name != null) {
                zzx += zzapo.zzs(2, this.name);
            }
            if (this.zD != null) {
                zzx += zzapo.zzs(3, this.zD);
            }
            if (this.anr != null) {
                zzx += zzapo.zze(4, this.anr.longValue());
            }
            if (this.amv != null) {
                zzx += zzapo.zzd(5, this.amv.floatValue());
            }
            return this.amw != null ? zzx + zzapo.zzb(6, this.amw.doubleValue()) : zzx;
        }
    }
}
