package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class zzaeo {
    public static final Uri CONTENT_URI;
    public static final Uri aLH;
    public static final Pattern aLI;
    public static final Pattern aLJ;
    static HashMap<String, String> aLK;
    private static Object aLL;
    static HashSet<String> aLM;

    /* renamed from: com.google.android.gms.internal.zzaeo.1 */
    class C02001 extends Thread {
        final /* synthetic */ ContentResolver aLN;

        /* renamed from: com.google.android.gms.internal.zzaeo.1.1 */
        class C01991 extends ContentObserver {
            final /* synthetic */ C02001 aLO;

            C01991(C02001 c02001, Handler handler) {
                this.aLO = c02001;
                super(handler);
            }

            public void onChange(boolean z) {
                synchronized (zzaeo.class) {
                    zzaeo.aLK.clear();
                    zzaeo.aLL = new Object();
                    if (!zzaeo.aLM.isEmpty()) {
                        zzaeo.zzb(this.aLO.aLN, (String[]) zzaeo.aLM.toArray(new String[zzaeo.aLM.size()]));
                    }
                }
            }
        }

        C02001(String str, ContentResolver contentResolver) {
            this.aLN = contentResolver;
            super(str);
        }

        public void run() {
            Looper.prepare();
            this.aLN.registerContentObserver(zzaeo.CONTENT_URI, true, new C01991(this, new Handler(Looper.myLooper())));
            Looper.loop();
        }
    }

    static {
        CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
        aLH = Uri.parse("content://com.google.android.gsf.gservices/prefix");
        aLI = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
        aLJ = Pattern.compile("^(0|false|f|off|no|n)$", 2);
        aLM = new HashSet();
    }

    public static long getLong(ContentResolver contentResolver, String str, long j) {
        String string = getString(contentResolver, str);
        if (string != null) {
            try {
                j = Long.parseLong(string);
            } catch (NumberFormatException e) {
            }
        }
        return j;
    }

    public static String getString(ContentResolver contentResolver, String str) {
        return zza(contentResolver, str, null);
    }

    public static String zza(ContentResolver contentResolver, String str, String str2) {
        synchronized (zzaeo.class) {
            zza(contentResolver);
            Object obj = aLL;
            String str3;
            if (aLK.containsKey(str)) {
                str3 = (String) aLK.get(str);
                if (str3 != null) {
                    str2 = str3;
                }
            } else {
                Iterator it = aLM.iterator();
                while (it.hasNext()) {
                    if (str.startsWith((String) it.next())) {
                        break;
                    }
                }
                Cursor query = contentResolver.query(CONTENT_URI, null, null, new String[]{str}, null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            str3 = query.getString(1);
                            synchronized (zzaeo.class) {
                                if (obj == aLL) {
                                    aLK.put(str, str3);
                                }
                            }
                            if (str3 != null) {
                                str2 = str3;
                            }
                            if (query != null) {
                                query.close();
                            }
                        }
                    } catch (Throwable th) {
                        if (query != null) {
                            query.close();
                        }
                    }
                }
                aLK.put(str, null);
                if (query != null) {
                    query.close();
                }
            }
        }
        return str2;
    }

    public static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor query = contentResolver.query(aLH, null, null, strArr, null);
        Map<String, String> treeMap = new TreeMap();
        if (query != null) {
            while (query.moveToNext()) {
                try {
                    treeMap.put(query.getString(0), query.getString(1));
                } finally {
                    query.close();
                }
            }
        }
        return treeMap;
    }

    private static void zza(ContentResolver contentResolver) {
        if (aLK == null) {
            aLK = new HashMap();
            aLL = new Object();
            new C02001("Gservices", contentResolver).start();
        }
    }

    public static void zzb(ContentResolver contentResolver, String... strArr) {
        Map zza = zza(contentResolver, strArr);
        synchronized (zzaeo.class) {
            zza(contentResolver);
            aLM.addAll(Arrays.asList(strArr));
            for (Entry entry : zza.entrySet()) {
                aLK.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }
}
