package org.apache.commons.net.bsd;

import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.net.io.SocketInputStream;

public class RCommandClient extends RExecClient {
    public static final int DEFAULT_PORT = 514;
    public static final int MAX_CLIENT_PORT = 1023;
    public static final int MIN_CLIENT_PORT = 512;

    InputStream _createErrorStream() throws IOException {
        ServerSocket server = null;
        int localPort = MAX_CLIENT_PORT;
        while (localPort >= MIN_CLIENT_PORT) {
            try {
                server = this._serverSocketFactory_.createServerSocket(localPort, 1, getLocalAddress());
                break;
            } catch (SocketException e) {
                localPort--;
            }
        }
        if (server == null) {
            throw new BindException("All ports in use.");
        }
        this._output_.write(Integer.toString(server.getLocalPort()).getBytes("UTF-8"));
        this._output_.write(0);
        this._output_.flush();
        Socket socket = server.accept();
        server.close();
        if (!isRemoteVerificationEnabled() || verifyRemote(socket)) {
            return new SocketInputStream(socket, socket.getInputStream());
        }
        socket.close();
        throw new IOException("Security violation: unexpected connection attempt by " + socket.getInetAddress().getHostAddress());
    }

    public RCommandClient() {
        setDefaultPort(DEFAULT_PORT);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(java.net.InetAddress r6, int r7, java.net.InetAddress r8) throws java.net.SocketException, java.net.BindException, java.io.IOException {
        /*
        r5 = this;
        r4 = 512; // 0x200 float:7.175E-43 double:2.53E-321;
        r2 = 1023; // 0x3ff float:1.434E-42 double:5.054E-321;
        r2 = 1023; // 0x3ff float:1.434E-42 double:5.054E-321;
    L_0x0006:
        if (r2 < r4) goto L_0x0010;
    L_0x0008:
        r3 = r5._socketFactory_;	 Catch:{ BindException -> 0x001a, SocketException -> 0x001e }
        r3 = r3.createSocket(r6, r7, r8, r2);	 Catch:{ BindException -> 0x001a, SocketException -> 0x001e }
        r5._socket_ = r3;	 Catch:{ BindException -> 0x001a, SocketException -> 0x001e }
    L_0x0010:
        if (r2 >= r4) goto L_0x0020;
    L_0x0012:
        r3 = new java.net.BindException;
        r4 = "All ports in use or insufficient permssion.";
        r3.<init>(r4);
        throw r3;
    L_0x001a:
        r0 = move-exception;
    L_0x001b:
        r2 = r2 + -1;
        goto L_0x0006;
    L_0x001e:
        r1 = move-exception;
        goto L_0x001b;
    L_0x0020:
        r5._connectAction_();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.net.bsd.RCommandClient.connect(java.net.InetAddress, int, java.net.InetAddress):void");
    }

    public void connect(InetAddress host, int port) throws SocketException, IOException {
        connect(host, port, InetAddress.getLocalHost());
    }

    public void connect(String hostname, int port) throws SocketException, IOException, UnknownHostException {
        connect(InetAddress.getByName(hostname), port, InetAddress.getLocalHost());
    }

    public void connect(String hostname, int port, InetAddress localAddr) throws SocketException, IOException {
        connect(InetAddress.getByName(hostname), port, localAddr);
    }

    public void connect(InetAddress host, int port, InetAddress localAddr, int localPort) throws SocketException, IOException, IllegalArgumentException {
        if (localPort < MIN_CLIENT_PORT || localPort > MAX_CLIENT_PORT) {
            throw new IllegalArgumentException("Invalid port number " + localPort);
        }
        super.connect(host, port, localAddr, localPort);
    }

    public void connect(String hostname, int port, InetAddress localAddr, int localPort) throws SocketException, IOException, IllegalArgumentException, UnknownHostException {
        if (localPort < MIN_CLIENT_PORT || localPort > MAX_CLIENT_PORT) {
            throw new IllegalArgumentException("Invalid port number " + localPort);
        }
        super.connect(hostname, port, localAddr, localPort);
    }

    public void rcommand(String localUsername, String remoteUsername, String command, boolean separateErrorStream) throws IOException {
        rexec(localUsername, remoteUsername, command, separateErrorStream);
    }

    public void rcommand(String localUsername, String remoteUsername, String command) throws IOException {
        rcommand(localUsername, remoteUsername, command, false);
    }
}
