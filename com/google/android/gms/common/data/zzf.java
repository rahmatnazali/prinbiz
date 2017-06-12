package com.google.android.gms.common.data;

import java.util.ArrayList;

public abstract class zzf<T> extends AbstractDataBuffer<T> {
    private boolean wo;
    private ArrayList<Integer> wp;

    protected zzf(DataHolder dataHolder) {
        super(dataHolder);
        this.wo = false;
    }

    private void zzarl() {
        synchronized (this) {
            if (!this.wo) {
                int count = this.tu.getCount();
                this.wp = new ArrayList();
                if (count > 0) {
                    this.wp.add(Integer.valueOf(0));
                    String zzark = zzark();
                    String zzd = this.tu.zzd(zzark, 0, this.tu.zzfs(0));
                    int i = 1;
                    while (i < count) {
                        int zzfs = this.tu.zzfs(i);
                        String zzd2 = this.tu.zzd(zzark, i, zzfs);
                        if (zzd2 == null) {
                            throw new NullPointerException(new StringBuilder(String.valueOf(zzark).length() + 78).append("Missing value for markerColumn: ").append(zzark).append(", at row: ").append(i).append(", for window: ").append(zzfs).toString());
                        }
                        if (zzd2.equals(zzd)) {
                            zzd2 = zzd;
                        } else {
                            this.wp.add(Integer.valueOf(i));
                        }
                        i++;
                        zzd = zzd2;
                    }
                }
                this.wo = true;
            }
        }
    }

    public final T get(int i) {
        zzarl();
        return zzl(zzfw(i), zzfx(i));
    }

    public int getCount() {
        zzarl();
        return this.wp.size();
    }

    protected abstract String zzark();

    protected String zzarm() {
        return null;
    }

    int zzfw(int i) {
        if (i >= 0 && i < this.wp.size()) {
            return ((Integer) this.wp.get(i)).intValue();
        }
        throw new IllegalArgumentException("Position " + i + " is out of bounds for this buffer");
    }

    protected int zzfx(int i) {
        if (i < 0 || i == this.wp.size()) {
            return 0;
        }
        int count = i == this.wp.size() + -1 ? this.tu.getCount() - ((Integer) this.wp.get(i)).intValue() : ((Integer) this.wp.get(i + 1)).intValue() - ((Integer) this.wp.get(i)).intValue();
        if (count != 1) {
            return count;
        }
        int zzfw = zzfw(i);
        int zzfs = this.tu.zzfs(zzfw);
        String zzarm = zzarm();
        return (zzarm == null || this.tu.zzd(zzarm, zzfw, zzfs) != null) ? count : 0;
    }

    protected abstract T zzl(int i, int i2);
}
