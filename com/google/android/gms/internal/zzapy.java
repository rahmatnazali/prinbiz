package com.google.android.gms.internal;

import java.io.IOException;

public final class zzapy {
    public static final int[] bjH;
    public static final long[] bjI;
    public static final float[] bjJ;
    public static final double[] bjK;
    public static final boolean[] bjL;
    public static final String[] bjM;
    public static final byte[][] bjN;
    public static final byte[] bjO;

    static {
        bjH = new int[0];
        bjI = new long[0];
        bjJ = new float[0];
        bjK = new double[0];
        bjL = new boolean[0];
        bjM = new String[0];
        bjN = new byte[0][];
        bjO = new byte[0];
    }

    static int zzagi(int i) {
        return i & 7;
    }

    public static int zzagj(int i) {
        return i >>> 3;
    }

    public static int zzaj(int i, int i2) {
        return (i << 3) | i2;
    }

    public static boolean zzb(zzapn com_google_android_gms_internal_zzapn, int i) throws IOException {
        return com_google_android_gms_internal_zzapn.zzafp(i);
    }

    public static final int zzc(zzapn com_google_android_gms_internal_zzapn, int i) throws IOException {
        int i2 = 1;
        int position = com_google_android_gms_internal_zzapn.getPosition();
        com_google_android_gms_internal_zzapn.zzafp(i);
        while (com_google_android_gms_internal_zzapn.ah() == i) {
            com_google_android_gms_internal_zzapn.zzafp(i);
            i2++;
        }
        com_google_android_gms_internal_zzapn.zzaft(position);
        return i2;
    }
}
