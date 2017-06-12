package com.google.android.gms.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public final class zzamm {
    private final Field bdH;

    public zzamm(Field field) {
        zzann.zzy(field);
        this.bdH = field;
    }

    public <T extends Annotation> T getAnnotation(Class<T> cls) {
        return this.bdH.getAnnotation(cls);
    }
}
