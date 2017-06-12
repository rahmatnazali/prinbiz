package com.flurry.android;

public enum FlurrySyndicationEventName {
    REBLOG("Reblog"),
    FAST_REBLOG("FastReblog"),
    SOURCE_LINK("SourceClick"),
    LIKE("Like");
    
    private String f21a;

    private FlurrySyndicationEventName(String str) {
        this.f21a = str;
    }

    public final String toString() {
        return this.f21a;
    }
}
