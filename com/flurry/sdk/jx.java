package com.flurry.sdk;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.zip.CRC32;

public final class jx extends MessageDigest {
    private CRC32 f308a;

    public jx() {
        super("CRC");
        this.f308a = new CRC32();
    }

    protected final void engineReset() {
        this.f308a.reset();
    }

    protected final void engineUpdate(byte b) {
        this.f308a.update(b);
    }

    protected final void engineUpdate(byte[] bArr, int i, int i2) {
        this.f308a.update(bArr, i, i2);
    }

    protected final byte[] engineDigest() {
        long value = this.f308a.getValue();
        return new byte[]{(byte) ((int) ((-16777216 & value) >> 24)), (byte) ((int) ((16711680 & value) >> 16)), (byte) ((int) ((65280 & value) >> 8)), (byte) ((int) ((value & 255) >> 0))};
    }

    public final byte[] m149a() {
        return engineDigest();
    }

    public final int m150b() {
        return ByteBuffer.wrap(engineDigest()).getInt();
    }
}
