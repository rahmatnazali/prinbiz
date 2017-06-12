package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import com.google.android.gms.common.ConnectionResult;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.telnet.TelnetOption;
import org.apache.commons.net.tftp.TFTPClient;

public final class zzapn {
    private int bjn;
    private int bjo;
    private int bjp;
    private int bjq;
    private int bjr;
    private int bjs;
    private int bjt;
    private int bju;
    private int bjv;
    private final byte[] buffer;

    private zzapn(byte[] bArr, int i, int i2) {
        this.bjs = Integer.MAX_VALUE;
        this.bju = 64;
        this.bjv = 67108864;
        this.buffer = bArr;
        this.bjn = i;
        this.bjo = i + i2;
        this.bjq = i;
    }

    private void au() {
        this.bjo += this.bjp;
        int i = this.bjo;
        if (i > this.bjs) {
            this.bjp = i - this.bjs;
            this.bjo -= this.bjp;
            return;
        }
        this.bjp = 0;
    }

    public static int zzafq(int i) {
        return (i >>> 1) ^ (-(i & 1));
    }

    public static zzapn zzb(byte[] bArr, int i, int i2) {
        return new zzapn(bArr, i, i2);
    }

    public static zzapn zzbd(byte[] bArr) {
        return zzb(bArr, 0, bArr.length);
    }

    public static long zzcs(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }

    public int ah() throws IOException {
        if (aw()) {
            this.bjr = 0;
            return 0;
        }
        this.bjr = aq();
        if (this.bjr != 0) {
            return this.bjr;
        }
        throw zzapu.aH();
    }

    public void ai() throws IOException {
        int ah;
        do {
            ah = ah();
            if (ah == 0) {
                return;
            }
        } while (zzafp(ah));
    }

    public long aj() throws IOException {
        return ar();
    }

    public long ak() throws IOException {
        return ar();
    }

    public int al() throws IOException {
        return aq();
    }

    public long am() throws IOException {
        return at();
    }

    public boolean an() throws IOException {
        return aq() != 0;
    }

    public int ao() throws IOException {
        return zzafq(aq());
    }

    public long ap() throws IOException {
        return zzcs(ar());
    }

    public int aq() throws IOException {
        byte ax = ax();
        if (ax >= null) {
            return ax;
        }
        int i = ax & TransportMediator.KEYCODE_MEDIA_PAUSE;
        byte ax2 = ax();
        if (ax2 >= null) {
            return i | (ax2 << 7);
        }
        i |= (ax2 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 7;
        ax2 = ax();
        if (ax2 >= null) {
            return i | (ax2 << 14);
        }
        i |= (ax2 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 14;
        ax2 = ax();
        if (ax2 >= null) {
            return i | (ax2 << 21);
        }
        i |= (ax2 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 21;
        ax2 = ax();
        i |= ax2 << 28;
        if (ax2 >= null) {
            return i;
        }
        for (int i2 = 0; i2 < 5; i2++) {
            if (ax() >= null) {
                return i;
            }
        }
        throw zzapu.aG();
    }

    public long ar() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte ax = ax();
            j |= ((long) (ax & TransportMediator.KEYCODE_MEDIA_PAUSE)) << i;
            if ((ax & TransportMediator.FLAG_KEY_MEDIA_NEXT) == 0) {
                return j;
            }
        }
        throw zzapu.aG();
    }

    public int as() throws IOException {
        return (((ax() & TelnetOption.MAX_OPTION_VALUE) | ((ax() & TelnetOption.MAX_OPTION_VALUE) << 8)) | ((ax() & TelnetOption.MAX_OPTION_VALUE) << 16)) | ((ax() & TelnetOption.MAX_OPTION_VALUE) << 24);
    }

    public long at() throws IOException {
        byte ax = ax();
        byte ax2 = ax();
        return ((((((((((long) ax2) & 255) << 8) | (((long) ax) & 255)) | ((((long) ax()) & 255) << 16)) | ((((long) ax()) & 255) << 24)) | ((((long) ax()) & 255) << 32)) | ((((long) ax()) & 255) << 40)) | ((((long) ax()) & 255) << 48)) | ((((long) ax()) & 255) << 56);
    }

    public int av() {
        if (this.bjs == Integer.MAX_VALUE) {
            return -1;
        }
        return this.bjs - this.bjq;
    }

    public boolean aw() {
        return this.bjq == this.bjo;
    }

    public byte ax() throws IOException {
        if (this.bjq == this.bjo) {
            throw zzapu.aE();
        }
        byte[] bArr = this.buffer;
        int i = this.bjq;
        this.bjq = i + 1;
        return bArr[i];
    }

    public int getPosition() {
        return this.bjq - this.bjn;
    }

    public byte[] readBytes() throws IOException {
        int aq = aq();
        if (aq < 0) {
            throw zzapu.aF();
        } else if (aq == 0) {
            return zzapy.bjO;
        } else {
            if (aq > this.bjo - this.bjq) {
                throw zzapu.aE();
            }
            Object obj = new byte[aq];
            System.arraycopy(this.buffer, this.bjq, obj, 0, aq);
            this.bjq = aq + this.bjq;
            return obj;
        }
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(at());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(as());
    }

    public String readString() throws IOException {
        int aq = aq();
        if (aq < 0) {
            throw zzapu.aF();
        } else if (aq > this.bjo - this.bjq) {
            throw zzapu.aE();
        } else {
            String str = new String(this.buffer, this.bjq, aq, zzapt.UTF_8);
            this.bjq = aq + this.bjq;
            return str;
        }
    }

    public void zza(zzapv com_google_android_gms_internal_zzapv) throws IOException {
        int aq = aq();
        if (this.bjt >= this.bju) {
            throw zzapu.aK();
        }
        aq = zzafr(aq);
        this.bjt++;
        com_google_android_gms_internal_zzapv.zzb(this);
        zzafo(0);
        this.bjt--;
        zzafs(aq);
    }

    public void zza(zzapv com_google_android_gms_internal_zzapv, int i) throws IOException {
        if (this.bjt >= this.bju) {
            throw zzapu.aK();
        }
        this.bjt++;
        com_google_android_gms_internal_zzapv.zzb(this);
        zzafo(zzapy.zzaj(i, 4));
        this.bjt--;
    }

    public byte[] zzad(int i, int i2) {
        if (i2 == 0) {
            return zzapy.bjO;
        }
        Object obj = new byte[i2];
        System.arraycopy(this.buffer, this.bjn + i, obj, 0, i2);
        return obj;
    }

    public void zzafo(int i) throws zzapu {
        if (this.bjr != i) {
            throw zzapu.aI();
        }
    }

    public boolean zzafp(int i) throws IOException {
        switch (zzapy.zzagi(i)) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                al();
                return true;
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                at();
                return true;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                zzafu(aq());
                return true;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                ai();
                zzafo(zzapy.zzaj(zzapy.zzagj(i), 4));
                return true;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return false;
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                as();
                return true;
            default:
                throw zzapu.aJ();
        }
    }

    public int zzafr(int i) throws zzapu {
        if (i < 0) {
            throw zzapu.aF();
        }
        int i2 = this.bjq + i;
        int i3 = this.bjs;
        if (i2 > i3) {
            throw zzapu.aE();
        }
        this.bjs = i2;
        au();
        return i3;
    }

    public void zzafs(int i) {
        this.bjs = i;
        au();
    }

    public void zzaft(int i) {
        if (i > this.bjq - this.bjn) {
            throw new IllegalArgumentException("Position " + i + " is beyond current " + (this.bjq - this.bjn));
        } else if (i < 0) {
            throw new IllegalArgumentException("Bad position " + i);
        } else {
            this.bjq = this.bjn + i;
        }
    }

    public void zzafu(int i) throws IOException {
        if (i < 0) {
            throw zzapu.aF();
        } else if (this.bjq + i > this.bjs) {
            zzafu(this.bjs - this.bjq);
            throw zzapu.aE();
        } else if (i <= this.bjo - this.bjq) {
            this.bjq += i;
        } else {
            throw zzapu.aE();
        }
    }
}
