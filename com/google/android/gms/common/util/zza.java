package com.google.android.gms.common.util;

import android.support.v4.util.ArrayMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

public class zza<E> extends AbstractSet<E> {
    private final ArrayMap<E, E> AV;

    public zza() {
        this.AV = new ArrayMap();
    }

    public zza(int i) {
        this.AV = new ArrayMap(i);
    }

    public zza(Collection<E> collection) {
        this(collection.size());
        addAll(collection);
    }

    public boolean add(E e) {
        if (this.AV.containsKey(e)) {
            return false;
        }
        this.AV.put(e, e);
        return true;
    }

    public boolean addAll(Collection<? extends E> collection) {
        return collection instanceof zza ? zza((zza) collection) : super.addAll(collection);
    }

    public void clear() {
        this.AV.clear();
    }

    public boolean contains(Object obj) {
        return this.AV.containsKey(obj);
    }

    public Iterator<E> iterator() {
        return this.AV.keySet().iterator();
    }

    public boolean remove(Object obj) {
        if (!this.AV.containsKey(obj)) {
            return false;
        }
        this.AV.remove(obj);
        return true;
    }

    public int size() {
        return this.AV.size();
    }

    public boolean zza(zza<? extends E> com_google_android_gms_common_util_zza__extends_E) {
        int size = size();
        this.AV.putAll(com_google_android_gms_common_util_zza__extends_E.AV);
        return size() > size;
    }
}
