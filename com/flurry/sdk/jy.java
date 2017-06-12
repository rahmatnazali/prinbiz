package com.flurry.sdk;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class jy<T> {
    private static final String f309a;
    private final File f310b;
    private final kz<T> f311c;

    static {
        f309a = jy.class.getSimpleName();
    }

    public jy(File file, String str, int i, lc<T> lcVar) {
        this.f310b = file;
        this.f311c = new kx(new lb(str, i, lcVar));
    }

    public final T m151a() {
        Closeable fileInputStream;
        Throwable e;
        Throwable th;
        T t = null;
        if (this.f310b != null) {
            if (this.f310b.exists()) {
                Object obj = null;
                try {
                    fileInputStream = new FileInputStream(this.f310b);
                    try {
                        t = this.f311c.m262a(fileInputStream);
                        lr.m311a(fileInputStream);
                    } catch (Exception e2) {
                        e = e2;
                        try {
                            kf.m183a(3, f309a, "Error reading data file:" + this.f310b.getName(), e);
                            obj = 1;
                            lr.m311a(fileInputStream);
                            if (obj != null) {
                                kf.m182a(3, f309a, "Deleting data file:" + this.f310b.getName());
                                this.f310b.delete();
                            }
                            return t;
                        } catch (Throwable th2) {
                            th = th2;
                            lr.m311a(fileInputStream);
                            throw th;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    fileInputStream = t;
                    kf.m183a(3, f309a, "Error reading data file:" + this.f310b.getName(), e);
                    obj = 1;
                    lr.m311a(fileInputStream);
                    if (obj != null) {
                        kf.m182a(3, f309a, "Deleting data file:" + this.f310b.getName());
                        this.f310b.delete();
                    }
                    return t;
                } catch (Throwable e4) {
                    fileInputStream = t;
                    th = e4;
                    lr.m311a(fileInputStream);
                    throw th;
                }
                if (obj != null) {
                    kf.m182a(3, f309a, "Deleting data file:" + this.f310b.getName());
                    this.f310b.delete();
                }
            } else {
                kf.m182a(5, f309a, "No data to read for file:" + this.f310b.getName());
            }
        }
        return t;
    }

    public final void m152a(T t) {
        Throwable e;
        int i;
        Object obj = null;
        Closeable closeable = null;
        if (t == null) {
            kf.m182a(3, f309a, "No data to write for file:" + this.f310b.getName());
            obj = 1;
        } else {
            try {
                if (lq.m302a(this.f310b)) {
                    Closeable fileOutputStream = new FileOutputStream(this.f310b);
                    try {
                        this.f311c.m263a(fileOutputStream, t);
                        lr.m311a(fileOutputStream);
                    } catch (Exception e2) {
                        e = e2;
                        closeable = fileOutputStream;
                        try {
                            kf.m183a(3, f309a, "Error writing data file:" + this.f310b.getName(), e);
                            lr.m311a(closeable);
                            i = 1;
                            if (obj == null) {
                                kf.m182a(3, f309a, "Deleting data file:" + this.f310b.getName());
                                this.f310b.delete();
                            }
                        } catch (Throwable th) {
                            e = th;
                            lr.m311a(closeable);
                            throw e;
                        }
                    } catch (Throwable th2) {
                        e = th2;
                        closeable = fileOutputStream;
                        lr.m311a(closeable);
                        throw e;
                    }
                }
                throw new IOException("Cannot create parent directory!");
            } catch (Exception e3) {
                e = e3;
                kf.m183a(3, f309a, "Error writing data file:" + this.f310b.getName(), e);
                lr.m311a(closeable);
                i = 1;
                if (obj == null) {
                    kf.m182a(3, f309a, "Deleting data file:" + this.f310b.getName());
                    this.f310b.delete();
                }
            }
        }
        if (obj == null) {
            kf.m182a(3, f309a, "Deleting data file:" + this.f310b.getName());
            this.f310b.delete();
        }
    }

    public final boolean m153b() {
        if (this.f310b == null) {
            return false;
        }
        return this.f310b.delete();
    }
}
