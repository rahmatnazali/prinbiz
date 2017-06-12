package org.apache.commons.net.io;

import com.google.android.gms.common.ConnectionResult;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ToNetASCIIInputStream extends FilterInputStream {
    private static final int __LAST_WAS_CR = 1;
    private static final int __LAST_WAS_NL = 2;
    private static final int __NOTHING_SPECIAL = 0;
    private int __status;

    public ToNetASCIIInputStream(InputStream input) {
        super(input);
        this.__status = 0;
    }

    public int read() throws IOException {
        if (this.__status == __LAST_WAS_NL) {
            this.__status = 0;
            return 10;
        }
        int ch = this.in.read();
        switch (ch) {
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                if (this.__status != __LAST_WAS_CR) {
                    this.__status = __LAST_WAS_NL;
                    return 13;
                }
                break;
            case ConnectionResult.CANCELED /*13*/:
                this.__status = __LAST_WAS_CR;
                return 13;
        }
        this.__status = 0;
        return ch;
    }

    public int read(byte[] buffer) throws IOException {
        return read(buffer, 0, buffer.length);
    }

    public int read(byte[] buffer, int offset, int length) throws IOException {
        if (length < __LAST_WAS_CR) {
            return 0;
        }
        int ch = available();
        if (length > ch) {
            length = ch;
        }
        if (length < __LAST_WAS_CR) {
            length = __LAST_WAS_CR;
        }
        ch = read();
        if (ch == -1) {
            return -1;
        }
        int offset2;
        int off = offset;
        while (true) {
            offset2 = offset + __LAST_WAS_CR;
            buffer[offset] = (byte) ch;
            length--;
            if (length <= 0) {
                break;
            }
            ch = read();
            if (ch == -1) {
                break;
            }
            offset = offset2;
        }
        offset = offset2;
        return offset2 - off;
    }

    public boolean markSupported() {
        return false;
    }

    public int available() throws IOException {
        int result = this.in.available();
        if (this.__status == __LAST_WAS_NL) {
            return result + __LAST_WAS_CR;
        }
        return result;
    }
}
