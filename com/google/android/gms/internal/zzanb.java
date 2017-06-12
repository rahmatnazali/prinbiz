package com.google.android.gms.internal;

import java.math.BigInteger;

public final class zzanb extends zzamv {
    private static final Class<?>[] bek;
    private Object value;

    static {
        bek = new Class[]{Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    }

    public zzanb(Boolean bool) {
        setValue(bool);
    }

    public zzanb(Number number) {
        setValue(number);
    }

    public zzanb(String str) {
        setValue(str);
    }

    private static boolean zza(zzanb com_google_android_gms_internal_zzanb) {
        if (!(com_google_android_gms_internal_zzanb.value instanceof Number)) {
            return false;
        }
        Number number = (Number) com_google_android_gms_internal_zzanb.value;
        return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
    }

    private static boolean zzci(Object obj) {
        if (obj instanceof String) {
            return true;
        }
        Class cls = obj.getClass();
        for (Class isAssignableFrom : bek) {
            if (isAssignableFrom.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzanb com_google_android_gms_internal_zzanb = (zzanb) obj;
        if (this.value == null) {
            return com_google_android_gms_internal_zzanb.value == null;
        } else {
            if (zza(this) && zza(com_google_android_gms_internal_zzanb)) {
                return zzcze().longValue() == com_google_android_gms_internal_zzanb.zzcze().longValue();
            } else {
                if (!(this.value instanceof Number) || !(com_google_android_gms_internal_zzanb.value instanceof Number)) {
                    return this.value.equals(com_google_android_gms_internal_zzanb.value);
                }
                double doubleValue = zzcze().doubleValue();
                double doubleValue2 = com_google_android_gms_internal_zzanb.zzcze().doubleValue();
                if (doubleValue == doubleValue2 || (Double.isNaN(doubleValue) && Double.isNaN(doubleValue2))) {
                    z = true;
                }
                return z;
            }
        }
    }

    public boolean getAsBoolean() {
        return zzczo() ? zzczn().booleanValue() : Boolean.parseBoolean(zzczf());
    }

    public double getAsDouble() {
        return zzczp() ? zzcze().doubleValue() : Double.parseDouble(zzczf());
    }

    public int getAsInt() {
        return zzczp() ? zzcze().intValue() : Integer.parseInt(zzczf());
    }

    public long getAsLong() {
        return zzczp() ? zzcze().longValue() : Long.parseLong(zzczf());
    }

    public int hashCode() {
        if (this.value == null) {
            return 31;
        }
        long longValue;
        if (zza(this)) {
            longValue = zzcze().longValue();
            return (int) (longValue ^ (longValue >>> 32));
        } else if (!(this.value instanceof Number)) {
            return this.value.hashCode();
        } else {
            longValue = Double.doubleToLongBits(zzcze().doubleValue());
            return (int) (longValue ^ (longValue >>> 32));
        }
    }

    void setValue(Object obj) {
        if (obj instanceof Character) {
            this.value = String.valueOf(((Character) obj).charValue());
            return;
        }
        boolean z = (obj instanceof Number) || zzci(obj);
        zzann.zzbo(z);
        this.value = obj;
    }

    public Number zzcze() {
        return this.value instanceof String ? new zzans((String) this.value) : (Number) this.value;
    }

    public String zzczf() {
        return zzczp() ? zzcze().toString() : zzczo() ? zzczn().toString() : (String) this.value;
    }

    Boolean zzczn() {
        return (Boolean) this.value;
    }

    public boolean zzczo() {
        return this.value instanceof Boolean;
    }

    public boolean zzczp() {
        return this.value instanceof Number;
    }

    public boolean zzczq() {
        return this.value instanceof String;
    }
}
