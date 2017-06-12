package com.google.android.gms.common.images;

import org.ksoap2.SoapEnvelope;

public final class Size {
    private final int zzaie;
    private final int zzaif;

    public Size(int i, int i2) {
        this.zzaie = i;
        this.zzaif = i2;
    }

    public static Size parseSize(String str) throws NumberFormatException {
        if (str == null) {
            throw new IllegalArgumentException("string must not be null");
        }
        int indexOf = str.indexOf(42);
        if (indexOf < 0) {
            indexOf = str.indexOf(SoapEnvelope.VER12);
        }
        if (indexOf < 0) {
            throw zzhh(str);
        }
        try {
            return new Size(Integer.parseInt(str.substring(0, indexOf)), Integer.parseInt(str.substring(indexOf + 1)));
        } catch (NumberFormatException e) {
            throw zzhh(str);
        }
    }

    private static NumberFormatException zzhh(String str) {
        throw new NumberFormatException(new StringBuilder(String.valueOf(str).length() + 16).append("Invalid Size: \"").append(str).append("\"").toString());
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Size)) {
            return false;
        }
        Size size = (Size) obj;
        if (!(this.zzaie == size.zzaie && this.zzaif == size.zzaif)) {
            z = false;
        }
        return z;
    }

    public int getHeight() {
        return this.zzaif;
    }

    public int getWidth() {
        return this.zzaie;
    }

    public int hashCode() {
        return this.zzaif ^ ((this.zzaie << 16) | (this.zzaie >>> 16));
    }

    public String toString() {
        int i = this.zzaie;
        return i + "x" + this.zzaif;
    }
}
