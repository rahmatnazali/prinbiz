package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FieldMappingDictionary extends AbstractSafeParcelable {
    public static final zzc CREATOR;
    private final int mVersionCode;
    private final HashMap<String, Map<String, Field<?, ?>>> zP;
    private final ArrayList<Entry> zQ;
    private final String zR;

    public static class Entry extends AbstractSafeParcelable {
        public static final zzd CREATOR;
        final String className;
        final int versionCode;
        final ArrayList<FieldMapPair> zS;

        static {
            CREATOR = new zzd();
        }

        Entry(int i, String str, ArrayList<FieldMapPair> arrayList) {
            this.versionCode = i;
            this.className = str;
            this.zS = arrayList;
        }

        Entry(String str, Map<String, Field<?, ?>> map) {
            this.versionCode = 1;
            this.className = str;
            this.zS = zzat(map);
        }

        private static ArrayList<FieldMapPair> zzat(Map<String, Field<?, ?>> map) {
            if (map == null) {
                return null;
            }
            ArrayList<FieldMapPair> arrayList = new ArrayList();
            for (String str : map.keySet()) {
                arrayList.add(new FieldMapPair(str, (Field) map.get(str)));
            }
            return arrayList;
        }

        public void writeToParcel(Parcel parcel, int i) {
            zzd com_google_android_gms_common_server_response_zzd = CREATOR;
            zzd.zza(this, parcel, i);
        }

        HashMap<String, Field<?, ?>> zzauk() {
            HashMap<String, Field<?, ?>> hashMap = new HashMap();
            int size = this.zS.size();
            for (int i = 0; i < size; i++) {
                FieldMapPair fieldMapPair = (FieldMapPair) this.zS.get(i);
                hashMap.put(fieldMapPair.zzcb, fieldMapPair.zT);
            }
            return hashMap;
        }
    }

    public static class FieldMapPair extends AbstractSafeParcelable {
        public static final zzb CREATOR;
        final int versionCode;
        final Field<?, ?> zT;
        final String zzcb;

        static {
            CREATOR = new zzb();
        }

        FieldMapPair(int i, String str, Field<?, ?> field) {
            this.versionCode = i;
            this.zzcb = str;
            this.zT = field;
        }

        FieldMapPair(String str, Field<?, ?> field) {
            this.versionCode = 1;
            this.zzcb = str;
            this.zT = field;
        }

        public void writeToParcel(Parcel parcel, int i) {
            zzb com_google_android_gms_common_server_response_zzb = CREATOR;
            zzb.zza(this, parcel, i);
        }
    }

    static {
        CREATOR = new zzc();
    }

    FieldMappingDictionary(int i, ArrayList<Entry> arrayList, String str) {
        this.mVersionCode = i;
        this.zQ = null;
        this.zP = zzi(arrayList);
        this.zR = (String) zzab.zzy(str);
        zzauh();
    }

    private static HashMap<String, Map<String, Field<?, ?>>> zzi(ArrayList<Entry> arrayList) {
        HashMap<String, Map<String, Field<?, ?>>> hashMap = new HashMap();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Entry entry = (Entry) arrayList.get(i);
            hashMap.put(entry.className, entry.zzauk());
        }
        return hashMap;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : this.zP.keySet()) {
            stringBuilder.append(str).append(":\n");
            Map map = (Map) this.zP.get(str);
            for (String str2 : map.keySet()) {
                stringBuilder.append("  ").append(str2).append(": ");
                stringBuilder.append(map.get(str2));
            }
        }
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc com_google_android_gms_common_server_response_zzc = CREATOR;
        zzc.zza(this, parcel, i);
    }

    public void zzauh() {
        for (String str : this.zP.keySet()) {
            Map map = (Map) this.zP.get(str);
            for (String str2 : map.keySet()) {
                ((Field) map.get(str2)).zza(this);
            }
        }
    }

    ArrayList<Entry> zzaui() {
        ArrayList<Entry> arrayList = new ArrayList();
        for (String str : this.zP.keySet()) {
            arrayList.add(new Entry(str, (Map) this.zP.get(str)));
        }
        return arrayList;
    }

    public String zzauj() {
        return this.zR;
    }

    public Map<String, Field<?, ?>> zzhw(String str) {
        return (Map) this.zP.get(str);
    }
}
