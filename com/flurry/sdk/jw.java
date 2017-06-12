package com.flurry.sdk;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class jw<K, V> {
    public final Map<K, List<V>> f306a;
    private int f307b;

    public jw() {
        this.f306a = new HashMap();
    }

    public jw(Map<K, List<V>> map) {
        this.f306a = map;
    }

    public final void m142a() {
        this.f306a.clear();
    }

    public final List<V> m140a(K k) {
        if (k == null) {
            return Collections.emptyList();
        }
        List<V> a = m141a((Object) k, false);
        if (a == null) {
            return Collections.emptyList();
        }
        return a;
    }

    public final void m144a(K k, V v) {
        if (k != null) {
            m141a((Object) k, true).add(v);
        }
    }

    public final void m143a(jw<K, V> jwVar) {
        if (jwVar != null) {
            for (Entry entry : jwVar.f306a.entrySet()) {
                m141a(entry.getKey(), true).addAll((Collection) entry.getValue());
            }
        }
    }

    public final boolean m147b(K k, V v) {
        boolean z = false;
        if (k != null) {
            List a = m141a((Object) k, false);
            if (a != null) {
                z = a.remove(v);
                if (a.size() == 0) {
                    this.f306a.remove(k);
                }
            }
        }
        return z;
    }

    public final boolean m146b(K k) {
        if (k == null) {
            return false;
        }
        return ((List) this.f306a.remove(k)) != null;
    }

    public final Collection<Entry<K, V>> m145b() {
        Collection arrayList = new ArrayList();
        for (Entry entry : this.f306a.entrySet()) {
            for (Object simpleImmutableEntry : (List) entry.getValue()) {
                arrayList.add(new SimpleImmutableEntry(entry.getKey(), simpleImmutableEntry));
            }
        }
        return arrayList;
    }

    public final Collection<V> m148c() {
        Collection arrayList = new ArrayList();
        for (Entry value : this.f306a.entrySet()) {
            arrayList.addAll((Collection) value.getValue());
        }
        return arrayList;
    }

    public final List<V> m141a(K k, boolean z) {
        List<V> list = (List) this.f306a.get(k);
        if (z && list == null) {
            if (this.f307b > 0) {
                list = new ArrayList(this.f307b);
            } else {
                list = new ArrayList();
            }
            this.f306a.put(k, list);
        }
        return list;
    }
}
