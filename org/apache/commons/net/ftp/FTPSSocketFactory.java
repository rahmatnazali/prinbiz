package org.apache.commons.net.ftp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;

public class FTPSSocketFactory extends SocketFactory {
    private final SSLContext context;

    public FTPSSocketFactory(SSLContext context) {
        this.context = context;
    }

    public Socket createSocket() throws IOException {
        return this.context.getSocketFactory().createSocket();
    }

    public Socket createSocket(String address, int port) throws UnknownHostException, IOException {
        return this.context.getSocketFactory().createSocket(address, port);
    }

    public Socket createSocket(InetAddress address, int port) throws IOException {
        return this.context.getSocketFactory().createSocket(address, port);
    }

    public Socket createSocket(String address, int port, InetAddress localAddress, int localPort) throws UnknownHostException, IOException {
        return this.context.getSocketFactory().createSocket(address, port, localAddress, localPort);
    }

    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return this.context.getSocketFactory().createSocket(address, port, localAddress, localPort);
    }

    @Deprecated
    public ServerSocket createServerSocket(int port) throws IOException {
        return init(this.context.getServerSocketFactory().createServerSocket(port));
    }

    @Deprecated
    public ServerSocket createServerSocket(int port, int backlog) throws IOException {
        return init(this.context.getServerSocketFactory().createServerSocket(port, backlog));
    }

    @Deprecated
    public ServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException {
        return init(this.context.getServerSocketFactory().createServerSocket(port, backlog, ifAddress));
    }

    @Deprecated
    public ServerSocket init(ServerSocket socket) throws IOException {
        ((SSLServerSocket) socket).setUseClientMode(true);
        return socket;
    }
}
