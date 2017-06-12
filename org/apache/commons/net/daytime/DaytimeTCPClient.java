package org.apache.commons.net.daytime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.net.SocketClient;

public final class DaytimeTCPClient extends SocketClient {
    public static final int DEFAULT_PORT = 13;
    private final char[] __buffer;

    public DaytimeTCPClient() {
        this.__buffer = new char[64];
        setDefaultPort(DEFAULT_PORT);
    }

    public String getTime() throws IOException {
        StringBuilder result = new StringBuilder(this.__buffer.length);
        BufferedReader reader = new BufferedReader(new InputStreamReader(this._input_, getCharsetName()));
        while (true) {
            int read = reader.read(this.__buffer, 0, this.__buffer.length);
            if (read <= 0) {
                return result.toString();
            }
            result.append(this.__buffer, 0, read);
        }
    }
}
