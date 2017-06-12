package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.server.converter.ConverterWrapper;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzp;
import com.google.android.gms.common.util.zzq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class FastJsonResponse {

    public interface zza<I, O> {
        I convertBack(O o);

        int zzatt();

        int zzatu();
    }

    public static class Field<I, O> extends AbstractSafeParcelable {
        public static final zza CREATOR;
        private final int mVersionCode;
        protected final int zF;
        protected final boolean zG;
        protected final int zH;
        protected final boolean zI;
        protected final String zJ;
        protected final int zK;
        protected final Class<? extends FastJsonResponse> zL;
        protected final String zM;
        private FieldMappingDictionary zN;
        private zza<I, O> zO;

        static {
            CREATOR = new zza();
        }

        Field(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, ConverterWrapper converterWrapper) {
            this.mVersionCode = i;
            this.zF = i2;
            this.zG = z;
            this.zH = i3;
            this.zI = z2;
            this.zJ = str;
            this.zK = i4;
            if (str2 == null) {
                this.zL = null;
                this.zM = null;
            } else {
                this.zL = SafeParcelResponse.class;
                this.zM = str2;
            }
            if (converterWrapper == null) {
                this.zO = null;
            } else {
                this.zO = converterWrapper.zzatr();
            }
        }

        protected Field(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends FastJsonResponse> cls, zza<I, O> com_google_android_gms_common_server_response_FastJsonResponse_zza_I__O) {
            this.mVersionCode = 1;
            this.zF = i;
            this.zG = z;
            this.zH = i2;
            this.zI = z2;
            this.zJ = str;
            this.zK = i3;
            this.zL = cls;
            if (cls == null) {
                this.zM = null;
            } else {
                this.zM = cls.getCanonicalName();
            }
            this.zO = com_google_android_gms_common_server_response_FastJsonResponse_zza_I__O;
        }

        public static Field zza(String str, int i, zza<?, ?> com_google_android_gms_common_server_response_FastJsonResponse_zza___, boolean z) {
            return new Field(com_google_android_gms_common_server_response_FastJsonResponse_zza___.zzatt(), z, com_google_android_gms_common_server_response_FastJsonResponse_zza___.zzatu(), false, str, i, null, com_google_android_gms_common_server_response_FastJsonResponse_zza___);
        }

        public static <T extends FastJsonResponse> Field<T, T> zza(String str, int i, Class<T> cls) {
            return new Field(11, false, 11, false, str, i, cls, null);
        }

        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> zzb(String str, int i, Class<T> cls) {
            return new Field(11, true, 11, true, str, i, cls, null);
        }

        public static Field<Integer, Integer> zzj(String str, int i) {
            return new Field(0, false, 0, false, str, i, null, null);
        }

        public static Field<Boolean, Boolean> zzk(String str, int i) {
            return new Field(6, false, 6, false, str, i, null, null);
        }

        public static Field<String, String> zzl(String str, int i) {
            return new Field(7, false, 7, false, str, i, null, null);
        }

        public I convertBack(O o) {
            return this.zO.convertBack(o);
        }

        public int getVersionCode() {
            return this.mVersionCode;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Field\n");
            stringBuilder.append("            versionCode=").append(this.mVersionCode).append('\n');
            stringBuilder.append("                 typeIn=").append(this.zF).append('\n');
            stringBuilder.append("            typeInArray=").append(this.zG).append('\n');
            stringBuilder.append("                typeOut=").append(this.zH).append('\n');
            stringBuilder.append("           typeOutArray=").append(this.zI).append('\n');
            stringBuilder.append("        outputFieldName=").append(this.zJ).append('\n');
            stringBuilder.append("      safeParcelFieldId=").append(this.zK).append('\n');
            stringBuilder.append("       concreteTypeName=").append(zzaud()).append('\n');
            if (zzauc() != null) {
                stringBuilder.append("     concreteType.class=").append(zzauc().getCanonicalName()).append('\n');
            }
            stringBuilder.append("          converterName=").append(this.zO == null ? "null" : this.zO.getClass().getCanonicalName()).append('\n');
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            zza com_google_android_gms_common_server_response_zza = CREATOR;
            zza.zza(this, parcel, i);
        }

        public void zza(FieldMappingDictionary fieldMappingDictionary) {
            this.zN = fieldMappingDictionary;
        }

        public int zzatt() {
            return this.zF;
        }

        public int zzatu() {
            return this.zH;
        }

        public boolean zzaty() {
            return this.zG;
        }

        public boolean zzatz() {
            return this.zI;
        }

        public String zzaua() {
            return this.zJ;
        }

        public int zzaub() {
            return this.zK;
        }

        public Class<? extends FastJsonResponse> zzauc() {
            return this.zL;
        }

        String zzaud() {
            return this.zM == null ? null : this.zM;
        }

        public boolean zzaue() {
            return this.zO != null;
        }

        ConverterWrapper zzauf() {
            return this.zO == null ? null : ConverterWrapper.zza(this.zO);
        }

        public Map<String, Field<?, ?>> zzaug() {
            zzab.zzy(this.zM);
            zzab.zzy(this.zN);
            return this.zN.zzhw(this.zM);
        }
    }

    private void zza(StringBuilder stringBuilder, Field field, Object obj) {
        if (field.zzatt() == 11) {
            stringBuilder.append(((FastJsonResponse) field.zzauc().cast(obj)).toString());
        } else if (field.zzatt() == 7) {
            stringBuilder.append("\"");
            stringBuilder.append(zzp.zzia((String) obj));
            stringBuilder.append("\"");
        } else {
            stringBuilder.append(obj);
        }
    }

    private void zza(StringBuilder stringBuilder, Field field, ArrayList<Object> arrayList) {
        stringBuilder.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                zza(stringBuilder, field, obj);
            }
        }
        stringBuilder.append("]");
    }

    public String toString() {
        Map zzatv = zzatv();
        StringBuilder stringBuilder = new StringBuilder(100);
        for (String str : zzatv.keySet()) {
            Field field = (Field) zzatv.get(str);
            if (zza(field)) {
                Object zza = zza(field, zzb(field));
                if (stringBuilder.length() == 0) {
                    stringBuilder.append("{");
                } else {
                    stringBuilder.append(",");
                }
                stringBuilder.append("\"").append(str).append("\":");
                if (zza != null) {
                    switch (field.zzatu()) {
                        case ConnectionResult.INTERNAL_ERROR /*8*/:
                            stringBuilder.append("\"").append(zzc.zzp((byte[]) zza)).append("\"");
                            break;
                        case ConnectionResult.SERVICE_INVALID /*9*/:
                            stringBuilder.append("\"").append(zzc.zzq((byte[]) zza)).append("\"");
                            break;
                        case ConnectionResult.DEVELOPER_ERROR /*10*/:
                            zzq.zza(stringBuilder, (HashMap) zza);
                            break;
                        default:
                            if (!field.zzaty()) {
                                zza(stringBuilder, field, zza);
                                break;
                            }
                            zza(stringBuilder, field, (ArrayList) zza);
                            break;
                    }
                }
                stringBuilder.append("null");
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append("}");
        } else {
            stringBuilder.append("{}");
        }
        return stringBuilder.toString();
    }

    protected <O, I> I zza(Field<I, O> field, Object obj) {
        return field.zO != null ? field.convertBack(obj) : obj;
    }

    protected boolean zza(Field field) {
        return field.zzatu() == 11 ? field.zzatz() ? zzhv(field.zzaua()) : zzhu(field.zzaua()) : zzht(field.zzaua());
    }

    public abstract Map<String, Field<?, ?>> zzatv();

    public HashMap<String, Object> zzatw() {
        return null;
    }

    public HashMap<String, Object> zzatx() {
        return null;
    }

    protected Object zzb(Field field) {
        String zzaua = field.zzaua();
        if (field.zzauc() == null) {
            return zzhs(field.zzaua());
        }
        zzab.zza(zzhs(field.zzaua()) == null, "Concrete field shouldn't be value object: %s", field.zzaua());
        Map zzatx = field.zzatz() ? zzatx() : zzatw();
        if (zzatx != null) {
            return zzatx.get(zzaua);
        }
        try {
            char toUpperCase = Character.toUpperCase(zzaua.charAt(0));
            String valueOf = String.valueOf(zzaua.substring(1));
            return getClass().getMethod(new StringBuilder(String.valueOf(valueOf).length() + 4).append("get").append(toUpperCase).append(valueOf).toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Object zzhs(String str);

    protected abstract boolean zzht(String str);

    protected boolean zzhu(String str) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected boolean zzhv(String str) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }
}
