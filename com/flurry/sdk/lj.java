package com.flurry.sdk;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class lj {
    private Map<String, Object> f403a;
    private Map<String, List<C0175a>> f404b;

    /* renamed from: com.flurry.sdk.lj.a */
    public interface C0175a {
        void m271a(String str, Object obj);
    }

    public lj() {
        this.f403a = new HashMap();
        this.f404b = new HashMap();
    }

    public final synchronized void m274a(String str, Object obj) {
        if (!TextUtils.isEmpty(str)) {
            Object obj2 = this.f403a.get(str);
            if (obj == obj2 || (obj != null && obj.equals(obj2))) {
                obj2 = 1;
            } else {
                obj2 = null;
            }
            if (obj2 == null) {
                if (obj == null) {
                    this.f403a.remove(str);
                } else {
                    this.f403a.put(str, obj);
                }
                if (this.f404b.get(str) != null) {
                    for (C0175a a : (List) this.f404b.get(str)) {
                        a.m271a(str, obj);
                    }
                }
            }
        }
    }

    public final synchronized Object m272a(String str) {
        return this.f403a.get(str);
    }

    public final synchronized void m273a(String str, C0175a c0175a) {
        if (!(TextUtils.isEmpty(str) || c0175a == null)) {
            List list = (List) this.f404b.get(str);
            if (list == null) {
                list = new LinkedList();
            }
            list.add(c0175a);
            this.f404b.put(str, list);
        }
    }

    public final synchronized boolean m275b(String str, C0175a c0175a) {
        boolean z;
        if (TextUtils.isEmpty(str)) {
            z = false;
        } else if (c0175a == null) {
            z = false;
        } else {
            List list = (List) this.f404b.get(str);
            z = list == null ? false : list.remove(c0175a);
        }
        return z;
    }

    public final synchronized void m276c() {
        this.f404b.clear();
    }
}
