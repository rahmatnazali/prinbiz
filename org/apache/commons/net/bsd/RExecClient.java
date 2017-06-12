package org.apache.commons.net.bsd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.SocketInputStream;

public class RExecClient extends SocketClient {
    public static final int DEFAULT_PORT = 512;
    protected static final char NULL_CHAR = '\u0000';
    private boolean __remoteVerificationEnabled;
    protected InputStream _errorStream_;

    InputStream _createErrorStream() throws IOException {
        ServerSocket server = this._serverSocketFactory_.createServerSocket(0, 1, getLocalAddress());
        this._output_.write(Integer.toString(server.getLocalPort()).getBytes("UTF-8"));
        this._output_.write(0);
        this._output_.flush();
        Socket socket = server.accept();
        server.close();
        if (!this.__remoteVerificationEnabled || verifyRemote(socket)) {
            return new SocketInputStream(socket, socket.getInputStream());
        }
        socket.close();
        throw new IOException("Security violation: unexpected connection attempt by " + socket.getInetAddress().getHostAddress());
    }

    public RExecClient() {
        this._errorStream_ = null;
        setDefaultPort(DEFAULT_PORT);
    }

    public InputStream getInputStream() {
        return this._input_;
    }

    public OutputStream getOutputStream() {
        return this._output_;
    }

    public InputStream getErrorStream() {
        return this._errorStream_;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rexec(java.lang.String r6, java.lang.String r7, java.lang.String r8, boolean r9) throws java.io.IOException {
        /*
        r5 = this;
        r4 = 0;
        if (r9 == 0) goto L_0x0063;
    L_0x0003:
        r2 = r5._createErrorStream();
        r5._errorStream_ = r2;
    L_0x0009:
        r2 = r5._output_;
        r3 = r5.getCharsetName();
        r3 = r6.getBytes(r3);
        r2.write(r3);
        r2 = r5._output_;
        r2.write(r4);
        r2 = r5._output_;
        r3 = r5.getCharsetName();
        r3 = r7.getBytes(r3);
        r2.write(r3);
        r2 = r5._output_;
        r2.write(r4);
        r2 = r5._output_;
        r3 = r5.getCharsetName();
        r3 = r8.getBytes(r3);
        r2.write(r3);
        r2 = r5._output_;
        r2.write(r4);
        r2 = r5._output_;
        r2.flush();
        r2 = r5._input_;
        r1 = r2.read();
        if (r1 <= 0) goto L_0x0073;
    L_0x004c:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
    L_0x0051:
        r2 = r5._input_;
        r1 = r2.read();
        r2 = -1;
        if (r1 == r2) goto L_0x0069;
    L_0x005a:
        r2 = 10;
        if (r1 == r2) goto L_0x0069;
    L_0x005e:
        r2 = (char) r1;
        r0.append(r2);
        goto L_0x0051;
    L_0x0063:
        r2 = r5._output_;
        r2.write(r4);
        goto L_0x0009;
    L_0x0069:
        r2 = new java.io.IOException;
        r3 = r0.toString();
        r2.<init>(r3);
        throw r2;
    L_0x0073:
        if (r1 >= 0) goto L_0x007d;
    L_0x0075:
        r2 = new java.io.IOException;
        r3 = "Server closed connection.";
        r2.<init>(r3);
        throw r2;
    L_0x007d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.net.bsd.RExecClient.rexec(java.lang.String, java.lang.String, java.lang.String, boolean):void");
    }

    public void rexec(String username, String password, String command) throws IOException {
        rexec(username, password, command, false);
    }

    public void disconnect() throws IOException {
        if (this._errorStream_ != null) {
            this._errorStream_.close();
        }
        this._errorStream_ = null;
        super.disconnect();
    }

    public final void setRemoteVerificationEnabled(boolean enable) {
        this.__remoteVerificationEnabled = enable;
    }

    public final boolean isRemoteVerificationEnabled() {
        return this.__remoteVerificationEnabled;
    }
}
