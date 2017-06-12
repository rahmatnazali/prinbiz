package org.apache.commons.net.echo;

import java.io.InputStream;
import org.apache.commons.net.discard.DiscardTCPClient;

public final class EchoTCPClient extends DiscardTCPClient {
    public static final int DEFAULT_PORT = 7;

    public EchoTCPClient() {
        setDefaultPort(DEFAULT_PORT);
    }

    public InputStream getInputStream() {
        return this._input_;
    }
}
