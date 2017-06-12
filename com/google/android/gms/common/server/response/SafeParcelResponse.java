package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzp;
import com.google.android.gms.common.util.zzq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public class SafeParcelResponse extends FastSafeParcelableJsonResponse {
    public static final zze CREATOR;
    private final String mClassName;
    private final int mVersionCode;
    private final FieldMappingDictionary zN;
    private final Parcel zU;
    private final int zV;
    private int zW;
    private int zX;

    static {
        CREATOR = new zze();
    }

    SafeParcelResponse(int i, Parcel parcel, FieldMappingDictionary fieldMappingDictionary) {
        this.mVersionCode = i;
        this.zU = (Parcel) zzab.zzy(parcel);
        this.zV = 2;
        this.zN = fieldMappingDictionary;
        if (this.zN == null) {
            this.mClassName = null;
        } else {
            this.mClassName = this.zN.zzauj();
        }
        this.zW = 2;
    }

    private void zza(StringBuilder stringBuilder, int i, Object obj) {
        switch (i) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                stringBuilder.append(obj);
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                stringBuilder.append("\"").append(zzp.zzia(obj.toString())).append("\"");
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                stringBuilder.append("\"").append(zzc.zzp((byte[]) obj)).append("\"");
            case ConnectionResult.SERVICE_INVALID /*9*/:
                stringBuilder.append("\"").append(zzc.zzq((byte[]) obj));
                stringBuilder.append("\"");
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                zzq.zza(stringBuilder, (HashMap) obj);
            case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown type = " + i);
        }
    }

    private void zza(StringBuilder stringBuilder, Field<?, ?> field, Parcel parcel, int i) {
        switch (field.zzatu()) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                zzb(stringBuilder, (Field) field, zza(field, Integer.valueOf(zza.zzg(parcel, i))));
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                zzb(stringBuilder, (Field) field, zza(field, zza.zzk(parcel, i)));
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                zzb(stringBuilder, (Field) field, zza(field, Long.valueOf(zza.zzi(parcel, i))));
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                zzb(stringBuilder, (Field) field, zza(field, Float.valueOf(zza.zzl(parcel, i))));
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                zzb(stringBuilder, (Field) field, zza(field, Double.valueOf(zza.zzn(parcel, i))));
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                zzb(stringBuilder, (Field) field, zza(field, zza.zzp(parcel, i)));
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                zzb(stringBuilder, (Field) field, zza(field, Boolean.valueOf(zza.zzc(parcel, i))));
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                zzb(stringBuilder, (Field) field, zza(field, zza.zzq(parcel, i)));
            case ConnectionResult.INTERNAL_ERROR /*8*/:
            case ConnectionResult.SERVICE_INVALID /*9*/:
                zzb(stringBuilder, (Field) field, zza(field, zza.zzt(parcel, i)));
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                zzb(stringBuilder, (Field) field, zza(field, zzp(zza.zzs(parcel, i))));
            case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown field out type = " + field.zzatu());
        }
    }

    private void zza(StringBuilder stringBuilder, String str, Field<?, ?> field, Parcel parcel, int i) {
        stringBuilder.append("\"").append(str).append("\":");
        if (field.zzaue()) {
            zza(stringBuilder, field, parcel, i);
        } else {
            zzb(stringBuilder, field, parcel, i);
        }
    }

    private void zza(StringBuilder stringBuilder, Map<String, Field<?, ?>> map, Parcel parcel) {
        SparseArray zzau = zzau(map);
        stringBuilder.append('{');
        int zzcm = zza.zzcm(parcel);
        Object obj = null;
        while (parcel.dataPosition() < zzcm) {
            int zzcl = zza.zzcl(parcel);
            Entry entry = (Entry) zzau.get(zza.zzgm(zzcl));
            if (entry != null) {
                if (obj != null) {
                    stringBuilder.append(",");
                }
                zza(stringBuilder, (String) entry.getKey(), (Field) entry.getValue(), parcel, zzcl);
                obj = 1;
            }
        }
        if (parcel.dataPosition() != zzcm) {
            throw new zza.zza("Overread allowed size end=" + zzcm, parcel);
        }
        stringBuilder.append('}');
    }

    private static SparseArray<Entry<String, Field<?, ?>>> zzau(Map<String, Field<?, ?>> map) {
        SparseArray<Entry<String, Field<?, ?>>> sparseArray = new SparseArray();
        for (Entry entry : map.entrySet()) {
            sparseArray.put(((Field) entry.getValue()).zzaub(), entry);
        }
        return sparseArray;
    }

    private void zzb(StringBuilder stringBuilder, Field<?, ?> field, Parcel parcel, int i) {
        if (field.zzatz()) {
            stringBuilder.append("[");
            switch (field.zzatu()) {
                case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                    zzb.zza(stringBuilder, zza.zzw(parcel, i));
                    break;
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    zzb.zza(stringBuilder, zza.zzy(parcel, i));
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    zzb.zza(stringBuilder, zza.zzx(parcel, i));
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    zzb.zza(stringBuilder, zza.zzz(parcel, i));
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    zzb.zza(stringBuilder, zza.zzaa(parcel, i));
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    zzb.zza(stringBuilder, zza.zzab(parcel, i));
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    zzb.zza(stringBuilder, zza.zzv(parcel, i));
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    zzb.zza(stringBuilder, zza.zzac(parcel, i));
                    break;
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                case ConnectionResult.SERVICE_INVALID /*9*/:
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                    Parcel[] zzag = zza.zzag(parcel, i);
                    int length = zzag.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        if (i2 > 0) {
                            stringBuilder.append(",");
                        }
                        zzag[i2].setDataPosition(0);
                        zza(stringBuilder, field.zzaug(), zzag[i2]);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown field type out.");
            }
            stringBuilder.append("]");
            return;
        }
        switch (field.zzatu()) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                stringBuilder.append(zza.zzg(parcel, i));
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                stringBuilder.append(zza.zzk(parcel, i));
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                stringBuilder.append(zza.zzi(parcel, i));
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                stringBuilder.append(zza.zzl(parcel, i));
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                stringBuilder.append(zza.zzn(parcel, i));
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                stringBuilder.append(zza.zzp(parcel, i));
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                stringBuilder.append(zza.zzc(parcel, i));
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                stringBuilder.append("\"").append(zzp.zzia(zza.zzq(parcel, i))).append("\"");
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                stringBuilder.append("\"").append(zzc.zzp(zza.zzt(parcel, i))).append("\"");
            case ConnectionResult.SERVICE_INVALID /*9*/:
                stringBuilder.append("\"").append(zzc.zzq(zza.zzt(parcel, i)));
                stringBuilder.append("\"");
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                Bundle zzs = zza.zzs(parcel, i);
                Set<String> keySet = zzs.keySet();
                keySet.size();
                stringBuilder.append("{");
                int i3 = 1;
                for (String str : keySet) {
                    if (i3 == 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append("\"").append(str).append("\"");
                    stringBuilder.append(":");
                    stringBuilder.append("\"").append(zzp.zzia(zzs.getString(str))).append("\"");
                    i3 = 0;
                }
                stringBuilder.append("}");
            case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                Parcel zzaf = zza.zzaf(parcel, i);
                zzaf.setDataPosition(0);
                zza(stringBuilder, field.zzaug(), zzaf);
            default:
                throw new IllegalStateException("Unknown field type out");
        }
    }

    private void zzb(StringBuilder stringBuilder, Field<?, ?> field, Object obj) {
        if (field.zzaty()) {
            zzb(stringBuilder, (Field) field, (ArrayList) obj);
        } else {
            zza(stringBuilder, field.zzatt(), obj);
        }
    }

    private void zzb(StringBuilder stringBuilder, Field<?, ?> field, ArrayList<?> arrayList) {
        stringBuilder.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            zza(stringBuilder, field.zzatt(), arrayList.get(i));
        }
        stringBuilder.append("]");
    }

    public static HashMap<String, String> zzp(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap();
        for (String str : bundle.keySet()) {
            hashMap.put(str, bundle.getString(str));
        }
        return hashMap;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public String toString() {
        zzab.zzb(this.zN, (Object) "Cannot convert to JSON on client side.");
        Parcel zzaul = zzaul();
        zzaul.setDataPosition(0);
        StringBuilder stringBuilder = new StringBuilder(100);
        zza(stringBuilder, this.zN.zzhw(this.mClassName), zzaul);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zze com_google_android_gms_common_server_response_zze = CREATOR;
        zze.zza(this, parcel, i);
    }

    public Map<String, Field<?, ?>> zzatv() {
        return this.zN == null ? null : this.zN.zzhw(this.mClassName);
    }

    public Parcel zzaul() {
        switch (this.zW) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                this.zX = com.google.android.gms.common.internal.safeparcel.zzb.zzcn(this.zU);
                com.google.android.gms.common.internal.safeparcel.zzb.zzaj(this.zU, this.zX);
                this.zW = 2;
                break;
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                com.google.android.gms.common.internal.safeparcel.zzb.zzaj(this.zU, this.zX);
                this.zW = 2;
                break;
        }
        return this.zU;
    }

    FieldMappingDictionary zzaum() {
        switch (this.zV) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                return null;
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return this.zN;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return this.zN;
            default:
                throw new IllegalStateException("Invalid creation type: " + this.zV);
        }
    }

    public Object zzhs(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public boolean zzht(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }
}
