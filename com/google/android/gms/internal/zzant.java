package com.google.android.gms.internal;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public final class zzant<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Comparator<Comparable> beW;
    Comparator<? super K> aPZ;
    zzd<K, V> beX;
    final zzd<K, V> beY;
    private zza beZ;
    private zzb bfa;
    int modCount;
    int size;

    /* renamed from: com.google.android.gms.internal.zzant.1 */
    static class C02021 implements Comparator<Comparable> {
        C02021() {
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return zza((Comparable) obj, (Comparable) obj2);
        }

        public int zza(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }
    }

    class zza extends AbstractSet<Entry<K, V>> {
        final /* synthetic */ zzant bfb;

        /* renamed from: com.google.android.gms.internal.zzant.zza.1 */
        class C06831 extends zzc<Entry<K, V>> {
            final /* synthetic */ zza bfc;

            C06831(zza com_google_android_gms_internal_zzant_zza) {
                this.bfc = com_google_android_gms_internal_zzant_zza;
                super(null);
            }

            public Entry<K, V> next() {
                return zzczw();
            }
        }

        zza(zzant com_google_android_gms_internal_zzant) {
            this.bfb = com_google_android_gms_internal_zzant;
        }

        public void clear() {
            this.bfb.clear();
        }

        public boolean contains(Object obj) {
            return (obj instanceof Entry) && this.bfb.zzc((Entry) obj) != null;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new C06831(this);
        }

        public boolean remove(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            zzd zzc = this.bfb.zzc((Entry) obj);
            if (zzc == null) {
                return false;
            }
            this.bfb.zza(zzc, true);
            return true;
        }

        public int size() {
            return this.bfb.size;
        }
    }

    final class zzb extends AbstractSet<K> {
        final /* synthetic */ zzant bfb;

        /* renamed from: com.google.android.gms.internal.zzant.zzb.1 */
        class C06841 extends zzc<K> {
            final /* synthetic */ zzb bfd;

            C06841(zzb com_google_android_gms_internal_zzant_zzb) {
                this.bfd = com_google_android_gms_internal_zzant_zzb;
                super(null);
            }

            public K next() {
                return zzczw().aQn;
            }
        }

        zzb(zzant com_google_android_gms_internal_zzant) {
            this.bfb = com_google_android_gms_internal_zzant;
        }

        public void clear() {
            this.bfb.clear();
        }

        public boolean contains(Object obj) {
            return this.bfb.containsKey(obj);
        }

        public Iterator<K> iterator() {
            return new C06841(this);
        }

        public boolean remove(Object obj) {
            return this.bfb.zzcn(obj) != null;
        }

        public int size() {
            return this.bfb.size;
        }
    }

    private abstract class zzc<T> implements Iterator<T> {
        final /* synthetic */ zzant bfb;
        zzd<K, V> bfe;
        zzd<K, V> bff;
        int bfg;

        private zzc(zzant com_google_android_gms_internal_zzant) {
            this.bfb = com_google_android_gms_internal_zzant;
            this.bfe = this.bfb.beY.bfe;
            this.bff = null;
            this.bfg = this.bfb.modCount;
        }

        public final boolean hasNext() {
            return this.bfe != this.bfb.beY;
        }

        public final void remove() {
            if (this.bff == null) {
                throw new IllegalStateException();
            }
            this.bfb.zza(this.bff, true);
            this.bff = null;
            this.bfg = this.bfb.modCount;
        }

        final zzd<K, V> zzczw() {
            zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V = this.bfe;
            if (com_google_android_gms_internal_zzant_zzd_K__V == this.bfb.beY) {
                throw new NoSuchElementException();
            } else if (this.bfb.modCount != this.bfg) {
                throw new ConcurrentModificationException();
            } else {
                this.bfe = com_google_android_gms_internal_zzant_zzd_K__V.bfe;
                this.bff = com_google_android_gms_internal_zzant_zzd_K__V;
                return com_google_android_gms_internal_zzant_zzd_K__V;
            }
        }
    }

    static final class zzd<K, V> implements Entry<K, V> {
        final K aQn;
        zzd<K, V> bfe;
        zzd<K, V> bfh;
        zzd<K, V> bfi;
        zzd<K, V> bfj;
        zzd<K, V> bfk;
        int height;
        V value;

        zzd() {
            this.aQn = null;
            this.bfk = this;
            this.bfe = this;
        }

        zzd(zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V, K k, zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V2, zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V3) {
            this.bfh = com_google_android_gms_internal_zzant_zzd_K__V;
            this.aQn = k;
            this.height = 1;
            this.bfe = com_google_android_gms_internal_zzant_zzd_K__V2;
            this.bfk = com_google_android_gms_internal_zzant_zzd_K__V3;
            com_google_android_gms_internal_zzant_zzd_K__V3.bfe = this;
            com_google_android_gms_internal_zzant_zzd_K__V2.bfk = this;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (this.aQn == null) {
                if (entry.getKey() != null) {
                    return false;
                }
            } else if (!this.aQn.equals(entry.getKey())) {
                return false;
            }
            if (this.value == null) {
                if (entry.getValue() != null) {
                    return false;
                }
            } else if (!this.value.equals(entry.getValue())) {
                return false;
            }
            return true;
        }

        public K getKey() {
            return this.aQn;
        }

        public V getValue() {
            return this.value;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = this.aQn == null ? 0 : this.aQn.hashCode();
            if (this.value != null) {
                i = this.value.hashCode();
            }
            return hashCode ^ i;
        }

        public V setValue(V v) {
            V v2 = this.value;
            this.value = v;
            return v2;
        }

        public String toString() {
            String valueOf = String.valueOf(this.aQn);
            String valueOf2 = String.valueOf(this.value);
            return new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(valueOf2).length()).append(valueOf).append("=").append(valueOf2).toString();
        }

        public zzd<K, V> zzczx() {
            zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V;
            for (zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V2 = this.bfi; com_google_android_gms_internal_zzant_zzd_K__V2 != null; com_google_android_gms_internal_zzant_zzd_K__V2 = com_google_android_gms_internal_zzant_zzd_K__V2.bfi) {
                com_google_android_gms_internal_zzant_zzd_K__V = com_google_android_gms_internal_zzant_zzd_K__V2;
            }
            return com_google_android_gms_internal_zzant_zzd_K__V;
        }

        public zzd<K, V> zzczy() {
            zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V;
            for (zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V2 = this.bfj; com_google_android_gms_internal_zzant_zzd_K__V2 != null; com_google_android_gms_internal_zzant_zzd_K__V2 = com_google_android_gms_internal_zzant_zzd_K__V2.bfj) {
                com_google_android_gms_internal_zzant_zzd_K__V = com_google_android_gms_internal_zzant_zzd_K__V2;
            }
            return com_google_android_gms_internal_zzant_zzd_K__V;
        }
    }

    static {
        $assertionsDisabled = !zzant.class.desiredAssertionStatus();
        beW = new C02021();
    }

    public zzant() {
        this(beW);
    }

    public zzant(Comparator<? super K> comparator) {
        Comparator comparator2;
        this.size = 0;
        this.modCount = 0;
        this.beY = new zzd();
        if (comparator == null) {
            comparator2 = beW;
        }
        this.aPZ = comparator2;
    }

    private boolean equal(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    private void zza(zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V) {
        int i = 0;
        zzd com_google_android_gms_internal_zzant_zzd = com_google_android_gms_internal_zzant_zzd_K__V.bfi;
        zzd com_google_android_gms_internal_zzant_zzd2 = com_google_android_gms_internal_zzant_zzd_K__V.bfj;
        zzd com_google_android_gms_internal_zzant_zzd3 = com_google_android_gms_internal_zzant_zzd2.bfi;
        zzd com_google_android_gms_internal_zzant_zzd4 = com_google_android_gms_internal_zzant_zzd2.bfj;
        com_google_android_gms_internal_zzant_zzd_K__V.bfj = com_google_android_gms_internal_zzant_zzd3;
        if (com_google_android_gms_internal_zzant_zzd3 != null) {
            com_google_android_gms_internal_zzant_zzd3.bfh = com_google_android_gms_internal_zzant_zzd_K__V;
        }
        zza((zzd) com_google_android_gms_internal_zzant_zzd_K__V, com_google_android_gms_internal_zzant_zzd2);
        com_google_android_gms_internal_zzant_zzd2.bfi = com_google_android_gms_internal_zzant_zzd_K__V;
        com_google_android_gms_internal_zzant_zzd_K__V.bfh = com_google_android_gms_internal_zzant_zzd2;
        com_google_android_gms_internal_zzant_zzd_K__V.height = Math.max(com_google_android_gms_internal_zzant_zzd != null ? com_google_android_gms_internal_zzant_zzd.height : 0, com_google_android_gms_internal_zzant_zzd3 != null ? com_google_android_gms_internal_zzant_zzd3.height : 0) + 1;
        int i2 = com_google_android_gms_internal_zzant_zzd_K__V.height;
        if (com_google_android_gms_internal_zzant_zzd4 != null) {
            i = com_google_android_gms_internal_zzant_zzd4.height;
        }
        com_google_android_gms_internal_zzant_zzd2.height = Math.max(i2, i) + 1;
    }

    private void zza(zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V, zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V2) {
        zzd com_google_android_gms_internal_zzant_zzd = com_google_android_gms_internal_zzant_zzd_K__V.bfh;
        com_google_android_gms_internal_zzant_zzd_K__V.bfh = null;
        if (com_google_android_gms_internal_zzant_zzd_K__V2 != null) {
            com_google_android_gms_internal_zzant_zzd_K__V2.bfh = com_google_android_gms_internal_zzant_zzd;
        }
        if (com_google_android_gms_internal_zzant_zzd == null) {
            this.beX = com_google_android_gms_internal_zzant_zzd_K__V2;
        } else if (com_google_android_gms_internal_zzant_zzd.bfi == com_google_android_gms_internal_zzant_zzd_K__V) {
            com_google_android_gms_internal_zzant_zzd.bfi = com_google_android_gms_internal_zzant_zzd_K__V2;
        } else if ($assertionsDisabled || com_google_android_gms_internal_zzant_zzd.bfj == com_google_android_gms_internal_zzant_zzd_K__V) {
            com_google_android_gms_internal_zzant_zzd.bfj = com_google_android_gms_internal_zzant_zzd_K__V2;
        } else {
            throw new AssertionError();
        }
    }

    private void zzb(zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V) {
        int i = 0;
        zzd com_google_android_gms_internal_zzant_zzd = com_google_android_gms_internal_zzant_zzd_K__V.bfi;
        zzd com_google_android_gms_internal_zzant_zzd2 = com_google_android_gms_internal_zzant_zzd_K__V.bfj;
        zzd com_google_android_gms_internal_zzant_zzd3 = com_google_android_gms_internal_zzant_zzd.bfi;
        zzd com_google_android_gms_internal_zzant_zzd4 = com_google_android_gms_internal_zzant_zzd.bfj;
        com_google_android_gms_internal_zzant_zzd_K__V.bfi = com_google_android_gms_internal_zzant_zzd4;
        if (com_google_android_gms_internal_zzant_zzd4 != null) {
            com_google_android_gms_internal_zzant_zzd4.bfh = com_google_android_gms_internal_zzant_zzd_K__V;
        }
        zza((zzd) com_google_android_gms_internal_zzant_zzd_K__V, com_google_android_gms_internal_zzant_zzd);
        com_google_android_gms_internal_zzant_zzd.bfj = com_google_android_gms_internal_zzant_zzd_K__V;
        com_google_android_gms_internal_zzant_zzd_K__V.bfh = com_google_android_gms_internal_zzant_zzd;
        com_google_android_gms_internal_zzant_zzd_K__V.height = Math.max(com_google_android_gms_internal_zzant_zzd2 != null ? com_google_android_gms_internal_zzant_zzd2.height : 0, com_google_android_gms_internal_zzant_zzd4 != null ? com_google_android_gms_internal_zzant_zzd4.height : 0) + 1;
        int i2 = com_google_android_gms_internal_zzant_zzd_K__V.height;
        if (com_google_android_gms_internal_zzant_zzd3 != null) {
            i = com_google_android_gms_internal_zzant_zzd3.height;
        }
        com_google_android_gms_internal_zzant_zzd.height = Math.max(i2, i) + 1;
    }

    private void zzb(zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V, boolean z) {
        zzd com_google_android_gms_internal_zzant_zzd;
        while (com_google_android_gms_internal_zzant_zzd != null) {
            zzd com_google_android_gms_internal_zzant_zzd2 = com_google_android_gms_internal_zzant_zzd.bfi;
            zzd com_google_android_gms_internal_zzant_zzd3 = com_google_android_gms_internal_zzant_zzd.bfj;
            int i = com_google_android_gms_internal_zzant_zzd2 != null ? com_google_android_gms_internal_zzant_zzd2.height : 0;
            int i2 = com_google_android_gms_internal_zzant_zzd3 != null ? com_google_android_gms_internal_zzant_zzd3.height : 0;
            int i3 = i - i2;
            zzd com_google_android_gms_internal_zzant_zzd4;
            if (i3 == -2) {
                com_google_android_gms_internal_zzant_zzd2 = com_google_android_gms_internal_zzant_zzd3.bfi;
                com_google_android_gms_internal_zzant_zzd4 = com_google_android_gms_internal_zzant_zzd3.bfj;
                i2 = (com_google_android_gms_internal_zzant_zzd2 != null ? com_google_android_gms_internal_zzant_zzd2.height : 0) - (com_google_android_gms_internal_zzant_zzd4 != null ? com_google_android_gms_internal_zzant_zzd4.height : 0);
                if (i2 == -1 || (i2 == 0 && !z)) {
                    zza(com_google_android_gms_internal_zzant_zzd);
                } else if ($assertionsDisabled || i2 == 1) {
                    zzb(com_google_android_gms_internal_zzant_zzd3);
                    zza(com_google_android_gms_internal_zzant_zzd);
                } else {
                    throw new AssertionError();
                }
                if (z) {
                    return;
                }
            } else if (i3 == 2) {
                com_google_android_gms_internal_zzant_zzd3 = com_google_android_gms_internal_zzant_zzd2.bfi;
                com_google_android_gms_internal_zzant_zzd4 = com_google_android_gms_internal_zzant_zzd2.bfj;
                i2 = (com_google_android_gms_internal_zzant_zzd3 != null ? com_google_android_gms_internal_zzant_zzd3.height : 0) - (com_google_android_gms_internal_zzant_zzd4 != null ? com_google_android_gms_internal_zzant_zzd4.height : 0);
                if (i2 == 1 || (i2 == 0 && !z)) {
                    zzb(com_google_android_gms_internal_zzant_zzd);
                } else if ($assertionsDisabled || i2 == -1) {
                    zza(com_google_android_gms_internal_zzant_zzd2);
                    zzb(com_google_android_gms_internal_zzant_zzd);
                } else {
                    throw new AssertionError();
                }
                if (z) {
                    return;
                }
            } else if (i3 == 0) {
                com_google_android_gms_internal_zzant_zzd.height = i + 1;
                if (z) {
                    return;
                }
            } else if ($assertionsDisabled || i3 == -1 || i3 == 1) {
                com_google_android_gms_internal_zzant_zzd.height = Math.max(i, i2) + 1;
                if (!z) {
                    return;
                }
            } else {
                throw new AssertionError();
            }
            com_google_android_gms_internal_zzant_zzd = com_google_android_gms_internal_zzant_zzd.bfh;
        }
    }

    public void clear() {
        this.beX = null;
        this.size = 0;
        this.modCount++;
        zzd com_google_android_gms_internal_zzant_zzd = this.beY;
        com_google_android_gms_internal_zzant_zzd.bfk = com_google_android_gms_internal_zzant_zzd;
        com_google_android_gms_internal_zzant_zzd.bfe = com_google_android_gms_internal_zzant_zzd;
    }

    public boolean containsKey(Object obj) {
        return zzcm(obj) != null;
    }

    public Set<Entry<K, V>> entrySet() {
        Set set = this.beZ;
        if (set != null) {
            return set;
        }
        set = new zza(this);
        this.beZ = set;
        return set;
    }

    public V get(Object obj) {
        zzd zzcm = zzcm(obj);
        return zzcm != null ? zzcm.value : null;
    }

    public Set<K> keySet() {
        Set set = this.bfa;
        if (set != null) {
            return set;
        }
        set = new zzb(this);
        this.bfa = set;
        return set;
    }

    public V put(K k, V v) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        zzd zza = zza((Object) k, true);
        V v2 = zza.value;
        zza.value = v;
        return v2;
    }

    public V remove(Object obj) {
        zzd zzcn = zzcn(obj);
        return zzcn != null ? zzcn.value : null;
    }

    public int size() {
        return this.size;
    }

    zzd<K, V> zza(K k, boolean z) {
        int i;
        Comparator comparator = this.aPZ;
        zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V = this.beX;
        if (com_google_android_gms_internal_zzant_zzd_K__V != null) {
            int compareTo;
            Comparable comparable = comparator == beW ? (Comparable) k : null;
            while (true) {
                compareTo = comparable != null ? comparable.compareTo(com_google_android_gms_internal_zzant_zzd_K__V.aQn) : comparator.compare(k, com_google_android_gms_internal_zzant_zzd_K__V.aQn);
                if (compareTo == 0) {
                    return com_google_android_gms_internal_zzant_zzd_K__V;
                }
                zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V2 = compareTo < 0 ? com_google_android_gms_internal_zzant_zzd_K__V.bfi : com_google_android_gms_internal_zzant_zzd_K__V.bfj;
                if (com_google_android_gms_internal_zzant_zzd_K__V2 == null) {
                    break;
                }
                com_google_android_gms_internal_zzant_zzd_K__V = com_google_android_gms_internal_zzant_zzd_K__V2;
            }
            int i2 = compareTo;
            zzd com_google_android_gms_internal_zzant_zzd = com_google_android_gms_internal_zzant_zzd_K__V;
            i = i2;
        } else {
            zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V3 = com_google_android_gms_internal_zzant_zzd_K__V;
            i = 0;
        }
        if (!z) {
            return null;
        }
        zzd<K, V> com_google_android_gms_internal_zzant_zzd2;
        zzd com_google_android_gms_internal_zzant_zzd3 = this.beY;
        if (com_google_android_gms_internal_zzant_zzd != null) {
            com_google_android_gms_internal_zzant_zzd2 = new zzd(com_google_android_gms_internal_zzant_zzd, k, com_google_android_gms_internal_zzant_zzd3, com_google_android_gms_internal_zzant_zzd3.bfk);
            if (i < 0) {
                com_google_android_gms_internal_zzant_zzd.bfi = com_google_android_gms_internal_zzant_zzd2;
            } else {
                com_google_android_gms_internal_zzant_zzd.bfj = com_google_android_gms_internal_zzant_zzd2;
            }
            zzb(com_google_android_gms_internal_zzant_zzd, true);
        } else if (comparator != beW || (k instanceof Comparable)) {
            com_google_android_gms_internal_zzant_zzd2 = new zzd(com_google_android_gms_internal_zzant_zzd, k, com_google_android_gms_internal_zzant_zzd3, com_google_android_gms_internal_zzant_zzd3.bfk);
            this.beX = com_google_android_gms_internal_zzant_zzd2;
        } else {
            throw new ClassCastException(String.valueOf(k.getClass().getName()).concat(" is not Comparable"));
        }
        this.size++;
        this.modCount++;
        return com_google_android_gms_internal_zzant_zzd2;
    }

    void zza(zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V, boolean z) {
        int i = 0;
        if (z) {
            com_google_android_gms_internal_zzant_zzd_K__V.bfk.bfe = com_google_android_gms_internal_zzant_zzd_K__V.bfe;
            com_google_android_gms_internal_zzant_zzd_K__V.bfe.bfk = com_google_android_gms_internal_zzant_zzd_K__V.bfk;
        }
        zzd com_google_android_gms_internal_zzant_zzd = com_google_android_gms_internal_zzant_zzd_K__V.bfi;
        zzd com_google_android_gms_internal_zzant_zzd2 = com_google_android_gms_internal_zzant_zzd_K__V.bfj;
        zzd com_google_android_gms_internal_zzant_zzd3 = com_google_android_gms_internal_zzant_zzd_K__V.bfh;
        if (com_google_android_gms_internal_zzant_zzd == null || com_google_android_gms_internal_zzant_zzd2 == null) {
            if (com_google_android_gms_internal_zzant_zzd != null) {
                zza((zzd) com_google_android_gms_internal_zzant_zzd_K__V, com_google_android_gms_internal_zzant_zzd);
                com_google_android_gms_internal_zzant_zzd_K__V.bfi = null;
            } else if (com_google_android_gms_internal_zzant_zzd2 != null) {
                zza((zzd) com_google_android_gms_internal_zzant_zzd_K__V, com_google_android_gms_internal_zzant_zzd2);
                com_google_android_gms_internal_zzant_zzd_K__V.bfj = null;
            } else {
                zza((zzd) com_google_android_gms_internal_zzant_zzd_K__V, null);
            }
            zzb(com_google_android_gms_internal_zzant_zzd3, false);
            this.size--;
            this.modCount++;
            return;
        }
        int i2;
        com_google_android_gms_internal_zzant_zzd = com_google_android_gms_internal_zzant_zzd.height > com_google_android_gms_internal_zzant_zzd2.height ? com_google_android_gms_internal_zzant_zzd.zzczy() : com_google_android_gms_internal_zzant_zzd2.zzczx();
        zza(com_google_android_gms_internal_zzant_zzd, false);
        com_google_android_gms_internal_zzant_zzd3 = com_google_android_gms_internal_zzant_zzd_K__V.bfi;
        if (com_google_android_gms_internal_zzant_zzd3 != null) {
            i2 = com_google_android_gms_internal_zzant_zzd3.height;
            com_google_android_gms_internal_zzant_zzd.bfi = com_google_android_gms_internal_zzant_zzd3;
            com_google_android_gms_internal_zzant_zzd3.bfh = com_google_android_gms_internal_zzant_zzd;
            com_google_android_gms_internal_zzant_zzd_K__V.bfi = null;
        } else {
            i2 = 0;
        }
        com_google_android_gms_internal_zzant_zzd3 = com_google_android_gms_internal_zzant_zzd_K__V.bfj;
        if (com_google_android_gms_internal_zzant_zzd3 != null) {
            i = com_google_android_gms_internal_zzant_zzd3.height;
            com_google_android_gms_internal_zzant_zzd.bfj = com_google_android_gms_internal_zzant_zzd3;
            com_google_android_gms_internal_zzant_zzd3.bfh = com_google_android_gms_internal_zzant_zzd;
            com_google_android_gms_internal_zzant_zzd_K__V.bfj = null;
        }
        com_google_android_gms_internal_zzant_zzd.height = Math.max(i2, i) + 1;
        zza((zzd) com_google_android_gms_internal_zzant_zzd_K__V, com_google_android_gms_internal_zzant_zzd);
    }

    zzd<K, V> zzc(Entry<?, ?> entry) {
        zzd<K, V> zzcm = zzcm(entry.getKey());
        Object obj = (zzcm == null || !equal(zzcm.value, entry.getValue())) ? null : 1;
        return obj != null ? zzcm : null;
    }

    zzd<K, V> zzcm(Object obj) {
        zzd<K, V> com_google_android_gms_internal_zzant_zzd_K__V = null;
        if (obj != null) {
            try {
                com_google_android_gms_internal_zzant_zzd_K__V = zza(obj, false);
            } catch (ClassCastException e) {
            }
        }
        return com_google_android_gms_internal_zzant_zzd_K__V;
    }

    zzd<K, V> zzcn(Object obj) {
        zzd zzcm = zzcm(obj);
        if (zzcm != null) {
            zza(zzcm, true);
        }
        return zzcm;
    }
}
