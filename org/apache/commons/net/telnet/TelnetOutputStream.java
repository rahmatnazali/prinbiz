package org.apache.commons.net.telnet;

import com.google.android.gms.common.ConnectionResult;
import java.io.IOException;
import java.io.OutputStream;

final class TelnetOutputStream extends OutputStream {
    private final TelnetClient __client;
    private final boolean __convertCRtoCRLF;
    private boolean __lastWasCR;

    TelnetOutputStream(TelnetClient client) {
        this.__convertCRtoCRLF = true;
        this.__lastWasCR = false;
        this.__client = client;
    }

    public void write(int ch) throws IOException {
        synchronized (this.__client) {
            ch &= TelnetOption.MAX_OPTION_VALUE;
            if (this.__client._requestedWont(0)) {
                if (this.__lastWasCR) {
                    this.__client._sendByte(10);
                    if (ch == 10) {
                        this.__lastWasCR = false;
                        return;
                    }
                }
                this.__lastWasCR = false;
                switch (ch) {
                    case ConnectionResult.CANCELED /*13*/:
                        this.__client._sendByte(13);
                        this.__lastWasCR = true;
                        break;
                    case TelnetOption.MAX_OPTION_VALUE /*255*/:
                        this.__client._sendByte(TelnetOption.MAX_OPTION_VALUE);
                        this.__client._sendByte(TelnetOption.MAX_OPTION_VALUE);
                        break;
                    default:
                        this.__client._sendByte(ch);
                        break;
                }
            } else if (ch == TelnetOption.MAX_OPTION_VALUE) {
                this.__client._sendByte(ch);
                this.__client._sendByte(TelnetOption.MAX_OPTION_VALUE);
            } else {
                this.__client._sendByte(ch);
            }
        }
    }

    public void write(byte[] buffer) throws IOException {
        write(buffer, 0, buffer.length);
    }

    public void write(byte[] buffer, int offset, int length) throws IOException {
        Throwable th;
        synchronized (this.__client) {
            int length2 = length;
            int offset2 = offset;
            while (true) {
                length = length2 - 1;
                if (length2 > 0) {
                    offset = offset2 + 1;
                    try {
                        write(buffer[offset2]);
                        length2 = length;
                        offset2 = offset;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } else {
                    try {
                        return;
                    } catch (Throwable th3) {
                        th = th3;
                        offset = offset2;
                        throw th;
                    }
                }
            }
        }
    }

    public void flush() throws IOException {
        this.__client._flushOutputStream();
    }

    public void close() throws IOException {
        this.__client._closeOutputStream();
    }
}
