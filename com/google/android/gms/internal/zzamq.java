package com.google.android.gms.internal;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public final class zzamq {
    private final List<zzani> bdQ;
    private zzanq bea;
    private zzanf beb;
    private zzamo bec;
    private final Map<Type, zzamr<?>> bed;
    private final List<zzani> bee;
    private int bef;
    private int beg;
    private boolean beh;

    public zzamq() {
        this.bea = zzanq.beK;
        this.beb = zzanf.DEFAULT;
        this.bec = zzamn.IDENTITY;
        this.bed = new HashMap();
        this.bdQ = new ArrayList();
        this.bee = new ArrayList();
        this.bef = 2;
        this.beg = 2;
        this.beh = true;
    }

    private void zza(String str, int i, int i2, List<zzani> list) {
        Object com_google_android_gms_internal_zzamk;
        if (str != null && !XmlPullParser.NO_NAMESPACE.equals(str.trim())) {
            com_google_android_gms_internal_zzamk = new zzamk(str);
        } else if (i != 2 && i2 != 2) {
            com_google_android_gms_internal_zzamk = new zzamk(i, i2);
        } else {
            return;
        }
        list.add(zzang.zza(zzaol.zzr(Date.class), com_google_android_gms_internal_zzamk));
        list.add(zzang.zza(zzaol.zzr(Timestamp.class), com_google_android_gms_internal_zzamk));
        list.add(zzang.zza(zzaol.zzr(java.sql.Date.class), com_google_android_gms_internal_zzamk));
    }

    public zzamq zza(Type type, Object obj) {
        boolean z = (obj instanceof zzand) || (obj instanceof zzamu) || (obj instanceof zzamr) || (obj instanceof zzanh);
        zzann.zzbo(z);
        if (obj instanceof zzamr) {
            this.bed.put(type, (zzamr) obj);
        }
        if ((obj instanceof zzand) || (obj instanceof zzamu)) {
            this.bdQ.add(zzang.zzb(zzaol.zzl(type), obj));
        }
        if (obj instanceof zzanh) {
            this.bdQ.add(zzaok.zza(zzaol.zzl(type), (zzanh) obj));
        }
        return this;
    }

    public zzamq zza(zzaml... com_google_android_gms_internal_zzamlArr) {
        for (zzaml zza : com_google_android_gms_internal_zzamlArr) {
            this.bea = this.bea.zza(zza, true, true);
        }
        return this;
    }

    public zzamq zzczc() {
        this.beh = false;
        return this;
    }

    public zzamp zzczd() {
        List arrayList = new ArrayList();
        arrayList.addAll(this.bdQ);
        Collections.reverse(arrayList);
        arrayList.addAll(this.bee);
        zza(null, this.bef, this.beg, arrayList);
        return new zzamp(this.bea, this.bec, this.bed, false, false, false, this.beh, false, false, this.beb, arrayList);
    }

    public zzamq zzf(int... iArr) {
        this.bea = this.bea.zzg(iArr);
        return this;
    }
}
