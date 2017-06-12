package com.google.android.gms.internal;

import java.io.IOException;

public class zzapu extends IOException {
    public zzapu(String str) {
        super(str);
    }

    static zzapu aE() {
        return new zzapu("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzapu aF() {
        return new zzapu("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzapu aG() {
        return new zzapu("CodedInputStream encountered a malformed varint.");
    }

    static zzapu aH() {
        return new zzapu("Protocol message contained an invalid tag (zero).");
    }

    static zzapu aI() {
        return new zzapu("Protocol message end-group tag did not match expected tag.");
    }

    static zzapu aJ() {
        return new zzapu("Protocol message tag had invalid wire type.");
    }

    static zzapu aK() {
        return new zzapu("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }
}
