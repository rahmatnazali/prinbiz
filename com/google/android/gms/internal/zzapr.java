package com.google.android.gms.internal;

public final class zzapr implements Cloneable {
    private static final zzaps bjz;
    private boolean bjA;
    private int[] bjB;
    private zzaps[] bjC;
    private int mSize;

    static {
        bjz = new zzaps();
    }

    zzapr() {
        this(10);
    }

    zzapr(int i) {
        this.bjA = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.bjB = new int[idealIntArraySize];
        this.bjC = new zzaps[idealIntArraySize];
        this.mSize = 0;
    }

    private int idealByteArraySize(int i) {
        for (int i2 = 4; i2 < 32; i2++) {
            if (i <= (1 << i2) - 12) {
                return (1 << i2) - 12;
            }
        }
        return i;
    }

    private int idealIntArraySize(int i) {
        return idealByteArraySize(i * 4) / 4;
    }

    private boolean zza(int[] iArr, int[] iArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    private boolean zza(zzaps[] com_google_android_gms_internal_zzapsArr, zzaps[] com_google_android_gms_internal_zzapsArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (!com_google_android_gms_internal_zzapsArr[i2].equals(com_google_android_gms_internal_zzapsArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    private int zzagh(int i) {
        int i2 = 0;
        int i3 = this.mSize - 1;
        while (i2 <= i3) {
            int i4 = (i2 + i3) >>> 1;
            int i5 = this.bjB[i4];
            if (i5 < i) {
                i2 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i3 = i4 - 1;
            }
        }
        return i2 ^ -1;
    }

    public final zzapr aC() {
        int size = size();
        zzapr com_google_android_gms_internal_zzapr = new zzapr(size);
        System.arraycopy(this.bjB, 0, com_google_android_gms_internal_zzapr.bjB, 0, size);
        for (int i = 0; i < size; i++) {
            if (this.bjC[i] != null) {
                com_google_android_gms_internal_zzapr.bjC[i] = (zzaps) this.bjC[i].clone();
            }
        }
        com_google_android_gms_internal_zzapr.mSize = size;
        return com_google_android_gms_internal_zzapr;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return aC();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzapr)) {
            return false;
        }
        zzapr com_google_android_gms_internal_zzapr = (zzapr) obj;
        return size() != com_google_android_gms_internal_zzapr.size() ? false : zza(this.bjB, com_google_android_gms_internal_zzapr.bjB, this.mSize) && zza(this.bjC, com_google_android_gms_internal_zzapr.bjC, this.mSize);
    }

    public int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.bjB[i2]) * 31) + this.bjC[i2].hashCode();
        }
        return i;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    int size() {
        return this.mSize;
    }

    void zza(int i, zzaps com_google_android_gms_internal_zzaps) {
        int zzagh = zzagh(i);
        if (zzagh >= 0) {
            this.bjC[zzagh] = com_google_android_gms_internal_zzaps;
            return;
        }
        zzagh ^= -1;
        if (zzagh >= this.mSize || this.bjC[zzagh] != bjz) {
            if (this.mSize >= this.bjB.length) {
                int idealIntArraySize = idealIntArraySize(this.mSize + 1);
                Object obj = new int[idealIntArraySize];
                Object obj2 = new zzaps[idealIntArraySize];
                System.arraycopy(this.bjB, 0, obj, 0, this.bjB.length);
                System.arraycopy(this.bjC, 0, obj2, 0, this.bjC.length);
                this.bjB = obj;
                this.bjC = obj2;
            }
            if (this.mSize - zzagh != 0) {
                System.arraycopy(this.bjB, zzagh, this.bjB, zzagh + 1, this.mSize - zzagh);
                System.arraycopy(this.bjC, zzagh, this.bjC, zzagh + 1, this.mSize - zzagh);
            }
            this.bjB[zzagh] = i;
            this.bjC[zzagh] = com_google_android_gms_internal_zzaps;
            this.mSize++;
            return;
        }
        this.bjB[zzagh] = i;
        this.bjC[zzagh] = com_google_android_gms_internal_zzaps;
    }

    zzaps zzagf(int i) {
        int zzagh = zzagh(i);
        return (zzagh < 0 || this.bjC[zzagh] == bjz) ? null : this.bjC[zzagh];
    }

    zzaps zzagg(int i) {
        return this.bjC[i];
    }
}
