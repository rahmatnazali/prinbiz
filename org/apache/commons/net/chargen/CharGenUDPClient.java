package org.apache.commons.net.chargen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.DatagramSocketClient;
import org.apache.commons.net.bsd.RCommandClient;

public final class CharGenUDPClient extends DatagramSocketClient {
    public static final int CHARGEN_PORT = 19;
    public static final int DEFAULT_PORT = 19;
    public static final int NETSTAT_PORT = 15;
    public static final int QUOTE_OF_DAY_PORT = 17;
    public static final int SYSTAT_PORT = 11;
    private final byte[] __receiveData;
    private final DatagramPacket __receivePacket;
    private final DatagramPacket __sendPacket;

    public CharGenUDPClient() {
        this.__receiveData = new byte[RCommandClient.MIN_CLIENT_PORT];
        this.__receivePacket = new DatagramPacket(this.__receiveData, this.__receiveData.length);
        this.__sendPacket = new DatagramPacket(new byte[0], 0);
    }

    public void send(InetAddress host, int port) throws IOException {
        this.__sendPacket.setAddress(host);
        this.__sendPacket.setPort(port);
        this._socket_.send(this.__sendPacket);
    }

    public void send(InetAddress host) throws IOException {
        send(host, DEFAULT_PORT);
    }

    public byte[] receive() throws IOException {
        this._socket_.receive(this.__receivePacket);
        int length = this.__receivePacket.getLength();
        byte[] result = new byte[length];
        System.arraycopy(this.__receiveData, 0, result, 0, length);
        return result;
    }
}
