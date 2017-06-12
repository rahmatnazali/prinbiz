package org.apache.commons.net.imap;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.io.CRLFLineReader;
import org.apache.commons.net.util.SSLContextUtils;

public class IMAPSClient extends IMAPClient {
    public static final int DEFAULT_IMAPS_PORT = 993;
    public static final String DEFAULT_PROTOCOL = "TLS";
    private SSLContext context;
    private final boolean isImplicit;
    private KeyManager keyManager;
    private final String protocol;
    private String[] protocols;
    private String[] suites;
    private TrustManager trustManager;

    public IMAPSClient() {
        this(DEFAULT_PROTOCOL, false);
    }

    public IMAPSClient(boolean implicit) {
        this(DEFAULT_PROTOCOL, implicit);
    }

    public IMAPSClient(String proto) {
        this(proto, false);
    }

    public IMAPSClient(String proto, boolean implicit) {
        this(proto, implicit, null);
    }

    public IMAPSClient(String proto, boolean implicit, SSLContext ctx) {
        this.context = null;
        this.suites = null;
        this.protocols = null;
        this.trustManager = null;
        this.keyManager = null;
        setDefaultPort(DEFAULT_IMAPS_PORT);
        this.protocol = proto;
        this.isImplicit = implicit;
        this.context = ctx;
    }

    public IMAPSClient(boolean implicit, SSLContext ctx) {
        this(DEFAULT_PROTOCOL, implicit, ctx);
    }

    public IMAPSClient(SSLContext context) {
        this(false, context);
    }

    protected void _connectAction_() throws IOException {
        if (this.isImplicit) {
            performSSLNegotiation();
        }
        super._connectAction_();
    }

    private void initSSLContext() throws IOException {
        if (this.context == null) {
            this.context = SSLContextUtils.createSSLContext(this.protocol, getKeyManager(), getTrustManager());
        }
    }

    private void performSSLNegotiation() throws IOException {
        initSSLContext();
        SSLSocket socket = (SSLSocket) this.context.getSocketFactory().createSocket(this._socket_, getRemoteAddress().getHostAddress(), getRemotePort(), true);
        socket.setEnableSessionCreation(true);
        socket.setUseClientMode(true);
        if (this.protocols != null) {
            socket.setEnabledProtocols(this.protocols);
        }
        if (this.suites != null) {
            socket.setEnabledCipherSuites(this.suites);
        }
        socket.startHandshake();
        this._socket_ = socket;
        this._input_ = socket.getInputStream();
        this._output_ = socket.getOutputStream();
        this._reader = new CRLFLineReader(new InputStreamReader(this._input_, FTP.DEFAULT_CONTROL_ENCODING));
        this.__writer = new BufferedWriter(new OutputStreamWriter(this._output_, FTP.DEFAULT_CONTROL_ENCODING));
    }

    private KeyManager getKeyManager() {
        return this.keyManager;
    }

    public void setKeyManager(KeyManager newKeyManager) {
        this.keyManager = newKeyManager;
    }

    public void setEnabledCipherSuites(String[] cipherSuites) {
        this.suites = new String[cipherSuites.length];
        System.arraycopy(cipherSuites, 0, this.suites, 0, cipherSuites.length);
    }

    public String[] getEnabledCipherSuites() {
        if (this._socket_ instanceof SSLSocket) {
            return ((SSLSocket) this._socket_).getEnabledCipherSuites();
        }
        return null;
    }

    public void setEnabledProtocols(String[] protocolVersions) {
        this.protocols = new String[protocolVersions.length];
        System.arraycopy(protocolVersions, 0, this.protocols, 0, protocolVersions.length);
    }

    public String[] getEnabledProtocols() {
        if (this._socket_ instanceof SSLSocket) {
            return ((SSLSocket) this._socket_).getEnabledProtocols();
        }
        return null;
    }

    public boolean execTLS() throws SSLException, IOException {
        if (sendCommand(IMAPCommand.getCommand(IMAPCommand.STARTTLS)) != 0) {
            return false;
        }
        performSSLNegotiation();
        return true;
    }

    public TrustManager getTrustManager() {
        return this.trustManager;
    }

    public void setTrustManager(TrustManager newTrustManager) {
        this.trustManager = newTrustManager;
    }
}
