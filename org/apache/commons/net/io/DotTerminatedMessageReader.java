package org.apache.commons.net.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public final class DotTerminatedMessageReader extends BufferedReader {
    private static final char CR = '\r';
    private static final int DOT = 46;
    private static final char LF = '\n';
    private boolean atBeginning;
    private boolean eof;
    private boolean seenCR;

    public DotTerminatedMessageReader(Reader reader) {
        super(reader);
        this.atBeginning = true;
        this.eof = false;
    }

    public int read() throws IOException {
        synchronized (this.lock) {
            if (this.eof) {
                return -1;
            }
            int chint = super.read();
            if (chint == -1) {
                this.eof = true;
                return -1;
            }
            if (this.atBeginning) {
                this.atBeginning = false;
                if (chint == DOT) {
                    mark(2);
                    chint = super.read();
                    if (chint == -1) {
                        this.eof = true;
                        return DOT;
                    } else if (chint == DOT) {
                        return chint;
                    } else {
                        if (chint == 13) {
                            chint = super.read();
                            if (chint == -1) {
                                reset();
                                return DOT;
                            } else if (chint == 10) {
                                this.atBeginning = true;
                                this.eof = true;
                                return -1;
                            }
                        }
                        reset();
                        return DOT;
                    }
                }
            }
            if (this.seenCR) {
                this.seenCR = false;
                if (chint == 10) {
                    this.atBeginning = true;
                }
            }
            if (chint == 13) {
                this.seenCR = true;
            }
            return chint;
        }
    }

    public int read(char[] buffer) throws IOException {
        return read(buffer, 0, buffer.length);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(char[] r7, int r8, int r9) throws java.io.IOException {
        /*
        r6 = this;
        r3 = -1;
        r4 = 1;
        if (r9 >= r4) goto L_0x0006;
    L_0x0004:
        r3 = 0;
    L_0x0005:
        return r3;
    L_0x0006:
        r4 = r6.lock;
        monitor-enter(r4);
        r0 = r6.read();	 Catch:{ all -> 0x0011 }
        if (r0 != r3) goto L_0x0014;
    L_0x000f:
        monitor-exit(r4);	 Catch:{ all -> 0x0011 }
        goto L_0x0005;
    L_0x0011:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0011 }
        throw r3;
    L_0x0014:
        r1 = r8;
        r2 = r8;
    L_0x0016:
        r8 = r2 + 1;
        r5 = (char) r0;
        r7[r2] = r5;	 Catch:{ all -> 0x0011 }
        r9 = r9 + -1;
        if (r9 <= 0) goto L_0x0025;
    L_0x001f:
        r0 = r6.read();	 Catch:{ all -> 0x0011 }
        if (r0 != r3) goto L_0x0029;
    L_0x0025:
        r3 = r8 - r1;
        monitor-exit(r4);	 Catch:{ all -> 0x0011 }
        goto L_0x0005;
    L_0x0029:
        r2 = r8;
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.net.io.DotTerminatedMessageReader.read(char[], int, int):int");
    }

    public void close() throws IOException {
        synchronized (this.lock) {
            if (!this.eof) {
                do {
                } while (read() != -1);
            }
            this.eof = true;
            this.atBeginning = false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String readLine() throws java.io.IOException {
        /*
        r6 = this;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r4 = r6.lock;
        monitor-enter(r4);
    L_0x0008:
        r0 = r6.read();	 Catch:{ all -> 0x0029 }
        r3 = -1;
        if (r0 == r3) goto L_0x002c;
    L_0x000f:
        r3 = 10;
        if (r0 != r3) goto L_0x0024;
    L_0x0013:
        r3 = r6.atBeginning;	 Catch:{ all -> 0x0029 }
        if (r3 == 0) goto L_0x0024;
    L_0x0017:
        r3 = 0;
        r5 = r1.length();	 Catch:{ all -> 0x0029 }
        r5 = r5 + -1;
        r2 = r1.substring(r3, r5);	 Catch:{ all -> 0x0029 }
        monitor-exit(r4);	 Catch:{ all -> 0x0029 }
    L_0x0023:
        return r2;
    L_0x0024:
        r3 = (char) r0;	 Catch:{ all -> 0x0029 }
        r1.append(r3);	 Catch:{ all -> 0x0029 }
        goto L_0x0008;
    L_0x0029:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0029 }
        throw r3;
    L_0x002c:
        monitor-exit(r4);	 Catch:{ all -> 0x0029 }
        r2 = r1.toString();
        r3 = r2.length();
        if (r3 != 0) goto L_0x0023;
    L_0x0037:
        r2 = 0;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.net.io.DotTerminatedMessageReader.readLine():java.lang.String");
    }
}
