package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse.zza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class StringToIntConverter extends AbstractSafeParcelable implements zza<String, Integer> {
    public static final zzb CREATOR;
    private final int mVersionCode;
    private final HashMap<String, Integer> zA;
    private final SparseArray<String> zB;
    private final ArrayList<Entry> zC;

    public static final class Entry extends AbstractSafeParcelable {
        public static final zzc CREATOR;
        final int versionCode;
        final String zD;
        final int zE;

        static {
            CREATOR = new zzc();
        }

        Entry(int i, String str, int i2) {
            this.versionCode = i;
            this.zD = str;
            this.zE = i2;
        }

        Entry(String str, int i) {
            this.versionCode = 1;
            this.zD = str;
            this.zE = i;
        }

        public void writeToParcel(Parcel parcel, int i) {
            zzc com_google_android_gms_common_server_converter_zzc = CREATOR;
            zzc.zza(this, parcel, i);
        }
    }

    static {
        CREATOR = new zzb();
    }

    public StringToIntConverter() {
        this.mVersionCode = 1;
        this.zA = new HashMap();
        this.zB = new SparseArray();
        this.zC = null;
    }

    StringToIntConverter(int i, ArrayList<Entry> arrayList) {
        this.mVersionCode = i;
        this.zA = new HashMap();
        this.zB = new SparseArray();
        this.zC = null;
        zzh(arrayList);
    }

    private void zzh(ArrayList<Entry> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzi(entry.zD, entry.zE);
        }
    }

    public /* synthetic */ Object convertBack(Object obj) {
        return zzd((Integer) obj);
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb com_google_android_gms_common_server_converter_zzb = CREATOR;
        zzb.zza(this, parcel, i);
    }

    ArrayList<Entry> zzats() {
        ArrayList<Entry> arrayList = new ArrayList();
        for (String str : this.zA.keySet()) {
            arrayList.add(new Entry(str, ((Integer) this.zA.get(str)).intValue()));
        }
        return arrayList;
    }

    public int zzatt() {
        return 7;
    }

    public int zzatu() {
        return 0;
    }

    public String zzd(Integer num) {
        String str = (String) this.zB.get(num.intValue());
        return (str == null && this.zA.containsKey("gms_unknown")) ? "gms_unknown" : str;
    }

    public StringToIntConverter zzi(String str, int i) {
        this.zA.put(str, Integer.valueOf(i));
        this.zB.put(i, str);
        return this;
    }
}
