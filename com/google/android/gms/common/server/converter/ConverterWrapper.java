package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse.zza;

public class ConverterWrapper extends AbstractSafeParcelable {
    public static final zza CREATOR;
    private final int mVersionCode;
    private final StringToIntConverter zz;

    static {
        CREATOR = new zza();
    }

    ConverterWrapper(int i, StringToIntConverter stringToIntConverter) {
        this.mVersionCode = i;
        this.zz = stringToIntConverter;
    }

    private ConverterWrapper(StringToIntConverter stringToIntConverter) {
        this.mVersionCode = 1;
        this.zz = stringToIntConverter;
    }

    public static ConverterWrapper zza(zza<?, ?> com_google_android_gms_common_server_response_FastJsonResponse_zza___) {
        if (com_google_android_gms_common_server_response_FastJsonResponse_zza___ instanceof StringToIntConverter) {
            return new ConverterWrapper((StringToIntConverter) com_google_android_gms_common_server_response_FastJsonResponse_zza___);
        }
        throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zza com_google_android_gms_common_server_converter_zza = CREATOR;
        zza.zza(this, parcel, i);
    }

    StringToIntConverter zzatq() {
        return this.zz;
    }

    public zza<?, ?> zzatr() {
        if (this.zz != null) {
            return this.zz;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }
}
