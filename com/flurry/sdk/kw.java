package com.flurry.sdk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class kw<ObjectType> implements kz<ObjectType> {
    protected final kz<ObjectType> f666a;

    public kw(kz<ObjectType> kzVar) {
        this.f666a = kzVar;
    }

    public void m638a(OutputStream outputStream, ObjectType objectType) throws IOException {
        if (this.f666a != null && outputStream != null && objectType != null) {
            this.f666a.m263a(outputStream, objectType);
        }
    }

    public ObjectType m637a(InputStream inputStream) throws IOException {
        if (this.f666a == null || inputStream == null) {
            return null;
        }
        return this.f666a.m262a(inputStream);
    }
}
