package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.telnet.TelnetCommand;

public final class zzapo {
    private final ByteBuffer bjw;

    public static class zza extends IOException {
        zza(int i, int i2) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + i + " limit " + i2 + ").");
        }
    }

    private zzapo(ByteBuffer byteBuffer) {
        this.bjw = byteBuffer;
        this.bjw.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzapo(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    private static int zza(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        int i3 = i;
        while (i3 < length) {
            char charAt = charSequence.charAt(i3);
            if (charAt < '\u0800') {
                i2 += (127 - charAt) >>> 31;
            } else {
                i2 += 2;
                if ('\ud800' <= charAt && charAt <= '\udfff') {
                    if (Character.codePointAt(charSequence, i3) < AccessibilityNodeInfoCompat.ACTION_CUT) {
                        throw new IllegalArgumentException("Unpaired surrogate at index " + i3);
                    }
                    i3++;
                }
            }
            i3++;
        }
        return i2;
    }

    private static int zza(CharSequence charSequence, byte[] bArr, int i, int i2) {
        int length = charSequence.length();
        int i3 = 0;
        int i4 = i + i2;
        while (i3 < length && i3 + i < i4) {
            char charAt = charSequence.charAt(i3);
            if (charAt >= '\u0080') {
                break;
            }
            bArr[i + i3] = (byte) charAt;
            i3++;
        }
        if (i3 == length) {
            return i + length;
        }
        int i5 = i + i3;
        while (i3 < length) {
            int i6;
            char charAt2 = charSequence.charAt(i3);
            if (charAt2 < '\u0080' && i5 < i4) {
                i6 = i5 + 1;
                bArr[i5] = (byte) charAt2;
            } else if (charAt2 < '\u0800' && i5 <= i4 - 2) {
                r6 = i5 + 1;
                bArr[i5] = (byte) ((charAt2 >>> 6) | 960);
                i6 = r6 + 1;
                bArr[r6] = (byte) ((charAt2 & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
            } else if ((charAt2 < '\ud800' || '\udfff' < charAt2) && i5 <= i4 - 3) {
                i6 = i5 + 1;
                bArr[i5] = (byte) ((charAt2 >>> 12) | NNTPReply.AUTHENTICATION_REQUIRED);
                i5 = i6 + 1;
                bArr[i6] = (byte) (((charAt2 >>> 6) & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
                i6 = i5 + 1;
                bArr[i5] = (byte) ((charAt2 & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
            } else if (i5 <= i4 - 4) {
                if (i3 + 1 != charSequence.length()) {
                    i3++;
                    charAt = charSequence.charAt(i3);
                    if (Character.isSurrogatePair(charAt2, charAt)) {
                        int toCodePoint = Character.toCodePoint(charAt2, charAt);
                        i6 = i5 + 1;
                        bArr[i5] = (byte) ((toCodePoint >>> 18) | TelnetCommand.SE);
                        i5 = i6 + 1;
                        bArr[i6] = (byte) (((toCodePoint >>> 12) & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
                        r6 = i5 + 1;
                        bArr[i5] = (byte) (((toCodePoint >>> 6) & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
                        i6 = r6 + 1;
                        bArr[r6] = (byte) ((toCodePoint & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    }
                }
                throw new IllegalArgumentException("Unpaired surrogate at index " + (i3 - 1));
            } else {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + i5);
            }
            i3++;
            i5 = i6;
        }
        return i5;
    }

    private static void zza(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(zza(charSequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
            } catch (Throwable e) {
                BufferOverflowException bufferOverflowException = new BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        } else {
            zzb(charSequence, byteBuffer);
        }
    }

    public static int zzafx(int i) {
        return i >= 0 ? zzagc(i) : 10;
    }

    public static int zzafy(int i) {
        return zzagc(zzage(i));
    }

    public static int zzag(int i, int i2) {
        return zzaga(i) + zzafx(i2);
    }

    public static int zzaga(int i) {
        return zzagc(zzapy.zzaj(i, 0));
    }

    public static int zzagc(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (-268435456 & i) == 0 ? 4 : 5;
    }

    public static int zzage(int i) {
        return (i << 1) ^ (i >> 31);
    }

    public static int zzah(int i, int i2) {
        return zzaga(i) + zzafy(i2);
    }

    public static int zzb(int i, double d) {
        return zzaga(i) + zzp(d);
    }

    public static int zzb(int i, zzapv com_google_android_gms_internal_zzapv) {
        return (zzaga(i) * 2) + zzd(com_google_android_gms_internal_zzapv);
    }

    public static int zzb(int i, byte[] bArr) {
        return zzaga(i) + zzbg(bArr);
    }

    private static void zzb(CharSequence charSequence, ByteBuffer byteBuffer) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < '\u0080') {
                byteBuffer.put((byte) charAt);
            } else if (charAt < '\u0800') {
                byteBuffer.put((byte) ((charAt >>> 6) | 960));
                byteBuffer.put((byte) ((charAt & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT));
            } else if (charAt < '\ud800' || '\udfff' < charAt) {
                byteBuffer.put((byte) ((charAt >>> 12) | NNTPReply.AUTHENTICATION_REQUIRED));
                byteBuffer.put((byte) (((charAt >>> 6) & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT));
                byteBuffer.put((byte) ((charAt & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT));
            } else {
                if (i + 1 != charSequence.length()) {
                    i++;
                    char charAt2 = charSequence.charAt(i);
                    if (Character.isSurrogatePair(charAt, charAt2)) {
                        int toCodePoint = Character.toCodePoint(charAt, charAt2);
                        byteBuffer.put((byte) ((toCodePoint >>> 18) | TelnetCommand.SE));
                        byteBuffer.put((byte) (((toCodePoint >>> 12) & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT));
                        byteBuffer.put((byte) (((toCodePoint >>> 6) & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT));
                        byteBuffer.put((byte) ((toCodePoint & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT));
                    }
                }
                throw new IllegalArgumentException("Unpaired surrogate at index " + (i - 1));
            }
            i++;
        }
    }

    public static zzapo zzbe(byte[] bArr) {
        return zzc(bArr, 0, bArr.length);
    }

    public static int zzbg(byte[] bArr) {
        return zzagc(bArr.length) + bArr.length;
    }

    public static int zzc(int i, zzapv com_google_android_gms_internal_zzapv) {
        return zzaga(i) + zze(com_google_android_gms_internal_zzapv);
    }

    public static zzapo zzc(byte[] bArr, int i, int i2) {
        return new zzapo(bArr, i, i2);
    }

    public static int zzcx(long j) {
        return zzdc(j);
    }

    public static int zzcy(long j) {
        return zzdc(j);
    }

    public static int zzcz(long j) {
        return 8;
    }

    public static int zzd(int i, float f) {
        return zzaga(i) + zzl(f);
    }

    public static int zzd(zzapv com_google_android_gms_internal_zzapv) {
        return com_google_android_gms_internal_zzapv.aM();
    }

    private static int zzd(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) < '\u0080') {
            i++;
        }
        int i2 = i;
        i = length;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            if (charAt >= '\u0800') {
                i += zza(charSequence, i2);
                break;
            }
            i2++;
            i = ((127 - charAt) >>> 31) + i;
        }
        if (i >= length) {
            return i;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (((long) i) + 4294967296L));
    }

    public static int zzda(long j) {
        return zzdc(zzde(j));
    }

    public static int zzdc(long j) {
        return (-128 & j) == 0 ? 1 : (-16384 & j) == 0 ? 2 : (-2097152 & j) == 0 ? 3 : (-268435456 & j) == 0 ? 4 : (-34359738368L & j) == 0 ? 5 : (-4398046511104L & j) == 0 ? 6 : (-562949953421312L & j) == 0 ? 7 : (-72057594037927936L & j) == 0 ? 8 : (Long.MIN_VALUE & j) == 0 ? 9 : 10;
    }

    public static long zzde(long j) {
        return (j << 1) ^ (j >> 63);
    }

    public static int zzdg(boolean z) {
        return 1;
    }

    public static int zze(int i, long j) {
        return zzaga(i) + zzcy(j);
    }

    public static int zze(zzapv com_google_android_gms_internal_zzapv) {
        int aM = com_google_android_gms_internal_zzapv.aM();
        return aM + zzagc(aM);
    }

    public static int zzf(int i, long j) {
        return zzaga(i) + zzcz(j);
    }

    public static int zzg(int i, long j) {
        return zzaga(i) + zzda(j);
    }

    public static int zzk(int i, boolean z) {
        return zzaga(i) + zzdg(z);
    }

    public static int zzl(float f) {
        return 4;
    }

    public static int zzp(double d) {
        return 8;
    }

    public static int zzs(int i, String str) {
        return zzaga(i) + zztx(str);
    }

    public static int zztx(String str) {
        int zzd = zzd((CharSequence) str);
        return zzd + zzagc(zzd);
    }

    public int ay() {
        return this.bjw.remaining();
    }

    public void az() {
        if (ay() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public void zza(int i, double d) throws IOException {
        zzai(i, 1);
        zzo(d);
    }

    public void zza(int i, long j) throws IOException {
        zzai(i, 0);
        zzct(j);
    }

    public void zza(int i, zzapv com_google_android_gms_internal_zzapv) throws IOException {
        zzai(i, 2);
        zzc(com_google_android_gms_internal_zzapv);
    }

    public void zza(int i, byte[] bArr) throws IOException {
        zzai(i, 2);
        zzbf(bArr);
    }

    public void zzae(int i, int i2) throws IOException {
        zzai(i, 0);
        zzafv(i2);
    }

    public void zzaf(int i, int i2) throws IOException {
        zzai(i, 0);
        zzafw(i2);
    }

    public void zzafv(int i) throws IOException {
        if (i >= 0) {
            zzagb(i);
        } else {
            zzdb((long) i);
        }
    }

    public void zzafw(int i) throws IOException {
        zzagb(zzage(i));
    }

    public void zzafz(int i) throws IOException {
        zzc((byte) i);
    }

    public void zzagb(int i) throws IOException {
        while ((i & -128) != 0) {
            zzafz((i & TransportMediator.KEYCODE_MEDIA_PAUSE) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
            i >>>= 7;
        }
        zzafz(i);
    }

    public void zzagd(int i) throws IOException {
        if (this.bjw.remaining() < 4) {
            throw new zza(this.bjw.position(), this.bjw.limit());
        }
        this.bjw.putInt(i);
    }

    public void zzai(int i, int i2) throws IOException {
        zzagb(zzapy.zzaj(i, i2));
    }

    public void zzb(int i, long j) throws IOException {
        zzai(i, 0);
        zzcu(j);
    }

    public void zzb(zzapv com_google_android_gms_internal_zzapv) throws IOException {
        com_google_android_gms_internal_zzapv.zza(this);
    }

    public void zzbf(byte[] bArr) throws IOException {
        zzagb(bArr.length);
        zzbh(bArr);
    }

    public void zzbh(byte[] bArr) throws IOException {
        zzd(bArr, 0, bArr.length);
    }

    public void zzc(byte b) throws IOException {
        if (this.bjw.hasRemaining()) {
            this.bjw.put(b);
            return;
        }
        throw new zza(this.bjw.position(), this.bjw.limit());
    }

    public void zzc(int i, float f) throws IOException {
        zzai(i, 5);
        zzk(f);
    }

    public void zzc(int i, long j) throws IOException {
        zzai(i, 1);
        zzcv(j);
    }

    public void zzc(zzapv com_google_android_gms_internal_zzapv) throws IOException {
        zzagb(com_google_android_gms_internal_zzapv.aL());
        com_google_android_gms_internal_zzapv.zza(this);
    }

    public void zzct(long j) throws IOException {
        zzdb(j);
    }

    public void zzcu(long j) throws IOException {
        zzdb(j);
    }

    public void zzcv(long j) throws IOException {
        zzdd(j);
    }

    public void zzcw(long j) throws IOException {
        zzdb(zzde(j));
    }

    public void zzd(int i, long j) throws IOException {
        zzai(i, 0);
        zzcw(j);
    }

    public void zzd(byte[] bArr, int i, int i2) throws IOException {
        if (this.bjw.remaining() >= i2) {
            this.bjw.put(bArr, i, i2);
            return;
        }
        throw new zza(this.bjw.position(), this.bjw.limit());
    }

    public void zzdb(long j) throws IOException {
        while ((-128 & j) != 0) {
            zzafz((((int) j) & TransportMediator.KEYCODE_MEDIA_PAUSE) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
            j >>>= 7;
        }
        zzafz((int) j);
    }

    public void zzdd(long j) throws IOException {
        if (this.bjw.remaining() < 8) {
            throw new zza(this.bjw.position(), this.bjw.limit());
        }
        this.bjw.putLong(j);
    }

    public void zzdf(boolean z) throws IOException {
        zzafz(z ? 1 : 0);
    }

    public void zzj(int i, boolean z) throws IOException {
        zzai(i, 0);
        zzdf(z);
    }

    public void zzk(float f) throws IOException {
        zzagd(Float.floatToIntBits(f));
    }

    public void zzo(double d) throws IOException {
        zzdd(Double.doubleToLongBits(d));
    }

    public void zzr(int i, String str) throws IOException {
        zzai(i, 2);
        zztw(str);
    }

    public void zztw(String str) throws IOException {
        try {
            int zzagc = zzagc(str.length());
            if (zzagc == zzagc(str.length() * 3)) {
                int position = this.bjw.position();
                if (this.bjw.remaining() < zzagc) {
                    throw new zza(zzagc + position, this.bjw.limit());
                }
                this.bjw.position(position + zzagc);
                zza((CharSequence) str, this.bjw);
                int position2 = this.bjw.position();
                this.bjw.position(position);
                zzagb((position2 - position) - zzagc);
                this.bjw.position(position2);
                return;
            }
            zzagb(zzd((CharSequence) str));
            zza((CharSequence) str, this.bjw);
        } catch (Throwable e) {
            zza com_google_android_gms_internal_zzapo_zza = new zza(this.bjw.position(), this.bjw.limit());
            com_google_android_gms_internal_zzapo_zza.initCause(e);
            throw com_google_android_gms_internal_zzapo_zza;
        }
    }
}
