package com.flurry.sdk;

import android.text.TextUtils;

public abstract class jz {
    protected String f312g;

    public jz(String str) {
        this.f312g = "com.flurry.android.sdk.ReplaceMeWithAProperEventName";
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Event must have a name!");
        }
        this.f312g = str;
    }

    public final String m154a() {
        return this.f312g;
    }

    public final void m155b() {
        kb.m157a().m161a(this);
    }
}
