package org.apache.commons.net.io;

import com.google.android.gms.common.ConnectionResult;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class FromNetASCIIOutputStream extends FilterOutputStream {
    private boolean __lastWasCR;

    public FromNetASCIIOutputStream(OutputStream output) {
        super(output);
        this.__lastWasCR = false;
    }

    private void __write(int ch) throws IOException {
        switch (ch) {
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                if (this.__lastWasCR) {
                    this.out.write(FromNetASCIIInputStream._lineSeparatorBytes);
                    this.__lastWasCR = false;
                    return;
                }
                this.__lastWasCR = false;
                this.out.write(10);
            case ConnectionResult.CANCELED /*13*/:
                this.__lastWasCR = true;
            default:
                if (this.__lastWasCR) {
                    this.out.write(13);
                    this.__lastWasCR = false;
                }
                this.out.write(ch);
        }
    }

    public synchronized void write(int ch) throws IOException {
        if (FromNetASCIIInputStream._noConversionRequired) {
            this.out.write(ch);
        } else {
            __write(ch);
        }
    }

    public synchronized void write(byte[] buffer) throws IOException {
        write(buffer, 0, buffer.length);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void write(byte[] r4, int r5, int r6) throws java.io.IOException {
        /*
        r3 = this;
        monitor-enter(r3);
        r2 = org.apache.commons.net.io.FromNetASCIIInputStream._noConversionRequired;	 Catch:{ all -> 0x001c }
        if (r2 == 0) goto L_0x001f;
    L_0x0005:
        r2 = r3.out;	 Catch:{ all -> 0x001c }
        r2.write(r4, r5, r6);	 Catch:{ all -> 0x001c }
    L_0x000a:
        monitor-exit(r3);
        return;
    L_0x000c:
        r6 = r0 + -1;
        if (r0 <= 0) goto L_0x001a;
    L_0x0010:
        r5 = r1 + 1;
        r2 = r4[r1];	 Catch:{ all -> 0x001c }
        r3.__write(r2);	 Catch:{ all -> 0x001c }
        r0 = r6;
        r1 = r5;
        goto L_0x000c;
    L_0x001a:
        r5 = r1;
        goto L_0x000a;
    L_0x001c:
        r2 = move-exception;
        monitor-exit(r3);
        throw r2;
    L_0x001f:
        r0 = r6;
        r1 = r5;
        goto L_0x000c;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.net.io.FromNetASCIIOutputStream.write(byte[], int, int):void");
    }

    public synchronized void close() throws IOException {
        if (FromNetASCIIInputStream._noConversionRequired) {
            super.close();
        } else {
            if (this.__lastWasCR) {
                this.out.write(13);
            }
            super.close();
        }
    }
}
