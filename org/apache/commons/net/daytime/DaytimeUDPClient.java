package org.apache.commons.net.daytime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.DatagramSocketClient;

public final class DaytimeUDPClient extends DatagramSocketClient {
    public static final int DEFAULT_PORT = 13;
    private final byte[] __dummyData;
    private final byte[] __timeData;

    public DaytimeUDPClient() {
        this.__dummyData = new byte[1];
        this.__timeData = new byte[DNSConstants.FLAGS_RD];
    }

    public String getTime(InetAddress host, int port) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(this.__dummyData, this.__dummyData.length, host, port);
        DatagramPacket receivePacket = new DatagramPacket(this.__timeData, this.__timeData.length);
        this._socket_.send(sendPacket);
        this._socket_.receive(receivePacket);
        return new String(receivePacket.getData(), 0, receivePacket.getLength(), getCharsetName());
    }

    public String getTime(InetAddress host) throws IOException {
        return getTime(host, DEFAULT_PORT);
    }
}
