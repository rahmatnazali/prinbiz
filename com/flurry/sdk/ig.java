package com.flurry.sdk;

public enum ig {
    HW_MACHINE(0),
    MODEL(1),
    BRAND(2),
    ID(3),
    DEVICE(4),
    PRODUCT(5),
    VERSION_RELEASE(6);
    
    final int f102h;

    private ig(int i) {
        this.f102h = i;
    }
}
