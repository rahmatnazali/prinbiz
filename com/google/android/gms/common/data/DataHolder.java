package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@KeepName
public final class DataHolder extends AbstractSafeParcelable implements Closeable {
    public static final Creator<DataHolder> CREATOR;
    private static final zza wi;
    boolean mClosed;
    private final int mVersionCode;
    private final int ok;
    private final String[] wb;
    Bundle wc;
    private final CursorWindow[] wd;
    private final Bundle we;
    int[] wf;
    int wg;
    private boolean wh;

    public static class zza {
        private final String[] wb;
        private final ArrayList<HashMap<String, Object>> wj;
        private final String wk;
        private final HashMap<Object, Integer> wl;
        private boolean wm;
        private String wn;

        private zza(String[] strArr, String str) {
            this.wb = (String[]) zzab.zzy(strArr);
            this.wj = new ArrayList();
            this.wk = str;
            this.wl = new HashMap();
            this.wm = false;
            this.wn = null;
        }

        private int zzb(HashMap<String, Object> hashMap) {
            if (this.wk == null) {
                return -1;
            }
            Object obj = hashMap.get(this.wk);
            if (obj == null) {
                return -1;
            }
            Integer num = (Integer) this.wl.get(obj);
            if (num != null) {
                return num.intValue();
            }
            this.wl.put(obj, Integer.valueOf(this.wj.size()));
            return -1;
        }

        public zza zza(ContentValues contentValues) {
            com.google.android.gms.common.internal.zzb.zzu(contentValues);
            HashMap hashMap = new HashMap(contentValues.size());
            for (Entry entry : contentValues.valueSet()) {
                hashMap.put((String) entry.getKey(), entry.getValue());
            }
            return zza(hashMap);
        }

        public zza zza(HashMap<String, Object> hashMap) {
            com.google.android.gms.common.internal.zzb.zzu(hashMap);
            int zzb = zzb((HashMap) hashMap);
            if (zzb == -1) {
                this.wj.add(hashMap);
            } else {
                this.wj.remove(zzb);
                this.wj.add(zzb, hashMap);
            }
            this.wm = false;
            return this;
        }

        public DataHolder zzfu(int i) {
            return new DataHolder(i, null, null);
        }
    }

    public static class zzb extends RuntimeException {
        public zzb(String str) {
            super(str);
        }
    }

    /* renamed from: com.google.android.gms.common.data.DataHolder.1 */
    class C06451 extends zza {
        C06451(String[] strArr, String str) {
            super(str, null);
        }

        public zza zza(ContentValues contentValues) {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }

        public zza zza(HashMap<String, Object> hashMap) {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }
    }

    static {
        CREATOR = new zze();
        wi = new C06451(new String[0], null);
    }

    DataHolder(int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.mClosed = false;
        this.wh = true;
        this.mVersionCode = i;
        this.wb = strArr;
        this.wd = cursorWindowArr;
        this.ok = i2;
        this.we = bundle;
    }

    private DataHolder(zza com_google_android_gms_common_data_DataHolder_zza, int i, Bundle bundle) {
        this(com_google_android_gms_common_data_DataHolder_zza.wb, zza(com_google_android_gms_common_data_DataHolder_zza, -1), i, bundle);
    }

    public DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.mClosed = false;
        this.wh = true;
        this.mVersionCode = 1;
        this.wb = (String[]) zzab.zzy(strArr);
        this.wd = (CursorWindow[]) zzab.zzy(cursorWindowArr);
        this.ok = i;
        this.we = bundle;
        zzarh();
    }

    public static DataHolder zza(int i, Bundle bundle) {
        return new DataHolder(wi, i, bundle);
    }

    private static CursorWindow[] zza(zza com_google_android_gms_common_data_DataHolder_zza, int i) {
        int i2 = 0;
        if (com_google_android_gms_common_data_DataHolder_zza.wb.length == 0) {
            return new CursorWindow[0];
        }
        List zzb = (i < 0 || i >= com_google_android_gms_common_data_DataHolder_zza.wj.size()) ? com_google_android_gms_common_data_DataHolder_zza.wj : com_google_android_gms_common_data_DataHolder_zza.wj.subList(0, i);
        int size = zzb.size();
        CursorWindow cursorWindow = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow);
        cursorWindow.setNumColumns(com_google_android_gms_common_data_DataHolder_zza.wb.length);
        int i3 = 0;
        int i4 = 0;
        while (i3 < size) {
            try {
                int i5;
                int i6;
                CursorWindow cursorWindow2;
                if (!cursorWindow.allocRow()) {
                    Log.d("DataHolder", "Allocating additional cursor window for large data set (row " + i3 + ")");
                    cursorWindow = new CursorWindow(false);
                    cursorWindow.setStartPosition(i3);
                    cursorWindow.setNumColumns(com_google_android_gms_common_data_DataHolder_zza.wb.length);
                    arrayList.add(cursorWindow);
                    if (!cursorWindow.allocRow()) {
                        Log.e("DataHolder", "Unable to allocate row to hold data.");
                        arrayList.remove(cursorWindow);
                        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                    }
                }
                Map map = (Map) zzb.get(i3);
                boolean z = true;
                for (int i7 = 0; i7 < com_google_android_gms_common_data_DataHolder_zza.wb.length && z; i7++) {
                    String str = com_google_android_gms_common_data_DataHolder_zza.wb[i7];
                    Object obj = map.get(str);
                    if (obj == null) {
                        z = cursorWindow.putNull(i3, i7);
                    } else if (obj instanceof String) {
                        z = cursorWindow.putString((String) obj, i3, i7);
                    } else if (obj instanceof Long) {
                        z = cursorWindow.putLong(((Long) obj).longValue(), i3, i7);
                    } else if (obj instanceof Integer) {
                        z = cursorWindow.putLong((long) ((Integer) obj).intValue(), i3, i7);
                    } else if (obj instanceof Boolean) {
                        z = cursorWindow.putLong(((Boolean) obj).booleanValue() ? 1 : 0, i3, i7);
                    } else if (obj instanceof byte[]) {
                        z = cursorWindow.putBlob((byte[]) obj, i3, i7);
                    } else if (obj instanceof Double) {
                        z = cursorWindow.putDouble(((Double) obj).doubleValue(), i3, i7);
                    } else if (obj instanceof Float) {
                        z = cursorWindow.putDouble((double) ((Float) obj).floatValue(), i3, i7);
                    } else {
                        String valueOf = String.valueOf(obj);
                        throw new IllegalArgumentException(new StringBuilder((String.valueOf(str).length() + 32) + String.valueOf(valueOf).length()).append("Unsupported object for column ").append(str).append(": ").append(valueOf).toString());
                    }
                }
                if (z) {
                    i5 = i3;
                    i6 = 0;
                    cursorWindow2 = cursorWindow;
                } else if (i4 != 0) {
                    throw new zzb("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                } else {
                    Log.d("DataHolder", "Couldn't populate window data for row " + i3 + " - allocating new window.");
                    cursorWindow.freeLastRow();
                    CursorWindow cursorWindow3 = new CursorWindow(false);
                    cursorWindow3.setStartPosition(i3);
                    cursorWindow3.setNumColumns(com_google_android_gms_common_data_DataHolder_zza.wb.length);
                    arrayList.add(cursorWindow3);
                    i5 = i3 - 1;
                    cursorWindow2 = cursorWindow3;
                    i6 = 1;
                }
                i4 = i6;
                cursorWindow = cursorWindow2;
                i3 = i5 + 1;
            } catch (RuntimeException e) {
                RuntimeException runtimeException = e;
                int size2 = arrayList.size();
                while (i2 < size2) {
                    ((CursorWindow) arrayList.get(i2)).close();
                    i2++;
                }
                throw runtimeException;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    public static zza zzb(String[] strArr) {
        return new zza(null, null);
    }

    public static DataHolder zzft(int i) {
        return zza(i, null);
    }

    private void zzh(String str, int i) {
        if (this.wc == null || !this.wc.containsKey(str)) {
            String str2 = "No such column: ";
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i < 0 || i >= this.wg) {
            throw new CursorIndexOutOfBoundsException(i, this.wg);
        }
    }

    public void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (CursorWindow close : this.wd) {
                    close.close();
                }
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.wh && this.wd.length > 0 && !isClosed()) {
                close();
                String valueOf = String.valueOf(toString());
                Log.e("DataBuffer", new StringBuilder(String.valueOf(valueOf).length() + 178).append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ").append(valueOf).append(")").toString());
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public int getCount() {
        return this.wg;
    }

    public int getStatusCode() {
        return this.ok;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zze.zza(this, parcel, i);
    }

    public void zza(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        zzh(str, i);
        this.wd[i2].copyStringToBuffer(i, this.wc.getInt(str), charArrayBuffer);
    }

    public Bundle zzarc() {
        return this.we;
    }

    public void zzarh() {
        int i;
        int i2 = 0;
        this.wc = new Bundle();
        for (i = 0; i < this.wb.length; i++) {
            this.wc.putInt(this.wb[i], i);
        }
        this.wf = new int[this.wd.length];
        i = 0;
        while (i2 < this.wd.length) {
            this.wf[i2] = i;
            i += this.wd[i2].getNumRows() - (i - this.wd[i2].getStartPosition());
            i2++;
        }
        this.wg = i;
    }

    String[] zzari() {
        return this.wb;
    }

    CursorWindow[] zzarj() {
        return this.wd;
    }

    public long zzb(String str, int i, int i2) {
        zzh(str, i);
        return this.wd[i2].getLong(i, this.wc.getInt(str));
    }

    public int zzc(String str, int i, int i2) {
        zzh(str, i);
        return this.wd[i2].getInt(i, this.wc.getInt(str));
    }

    public String zzd(String str, int i, int i2) {
        zzh(str, i);
        return this.wd[i2].getString(i, this.wc.getInt(str));
    }

    public boolean zze(String str, int i, int i2) {
        zzh(str, i);
        return Long.valueOf(this.wd[i2].getLong(i, this.wc.getInt(str))).longValue() == 1;
    }

    public float zzf(String str, int i, int i2) {
        zzh(str, i);
        return this.wd[i2].getFloat(i, this.wc.getInt(str));
    }

    public int zzfs(int i) {
        int i2 = 0;
        boolean z = i >= 0 && i < this.wg;
        zzab.zzbn(z);
        while (i2 < this.wf.length) {
            if (i < this.wf[i2]) {
                i2--;
                break;
            }
            i2++;
        }
        return i2 == this.wf.length ? i2 - 1 : i2;
    }

    public byte[] zzg(String str, int i, int i2) {
        zzh(str, i);
        return this.wd[i2].getBlob(i, this.wc.getInt(str));
    }

    public Uri zzh(String str, int i, int i2) {
        String zzd = zzd(str, i, i2);
        return zzd == null ? null : Uri.parse(zzd);
    }

    public boolean zzhe(String str) {
        return this.wc.containsKey(str);
    }

    public boolean zzi(String str, int i, int i2) {
        zzh(str, i);
        return this.wd[i2].isNull(i, this.wc.getInt(str));
    }
}
