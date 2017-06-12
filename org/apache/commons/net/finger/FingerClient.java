package org.apache.commons.net.finger;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.util.Charsets;
import org.xmlpull.v1.XmlPullParser;

public class FingerClient extends SocketClient {
    public static final int DEFAULT_PORT = 79;
    private static final String __LONG_FLAG = "/W ";
    private transient char[] __buffer;

    public FingerClient() {
        this.__buffer = new char[Util.DEFAULT_COPY_BUFFER_SIZE];
        setDefaultPort(DEFAULT_PORT);
    }

    public String query(boolean longOutput, String username) throws IOException {
        StringBuilder result = new StringBuilder(this.__buffer.length);
        BufferedReader input = new BufferedReader(new InputStreamReader(getInputStream(longOutput, username), getCharsetName()));
        while (true) {
            try {
                int read = input.read(this.__buffer, 0, this.__buffer.length);
                if (read <= 0) {
                    break;
                }
                result.append(this.__buffer, 0, read);
            } finally {
                input.close();
            }
        }
        return result.toString();
    }

    public String query(boolean longOutput) throws IOException {
        return query(longOutput, XmlPullParser.NO_NAMESPACE);
    }

    public InputStream getInputStream(boolean longOutput, String username) throws IOException {
        return getInputStream(longOutput, username, null);
    }

    public InputStream getInputStream(boolean longOutput, String username, String encoding) throws IOException {
        StringBuilder buffer = new StringBuilder(64);
        if (longOutput) {
            buffer.append(__LONG_FLAG);
        }
        buffer.append(username);
        buffer.append(SocketClient.NETASCII_EOL);
        byte[] encodedQuery = buffer.toString().getBytes(Charsets.toCharset(encoding).name());
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(this._output_, Util.DEFAULT_COPY_BUFFER_SIZE));
        output.write(encodedQuery, 0, encodedQuery.length);
        output.flush();
        return this._input_;
    }

    public InputStream getInputStream(boolean longOutput) throws IOException {
        return getInputStream(longOutput, XmlPullParser.NO_NAMESPACE);
    }
}
