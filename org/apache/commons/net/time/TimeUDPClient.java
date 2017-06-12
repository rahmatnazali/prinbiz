package org.apache.commons.net.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Date;
import org.apache.commons.net.DatagramSocketClient;
import org.apache.commons.net.telnet.TelnetOption;

public final class TimeUDPClient extends DatagramSocketClient {
    public static final int DEFAULT_PORT = 37;
    public static final long SECONDS_1900_TO_1970 = 2208988800L;
    private final byte[] __dummyData;
    private final byte[] __timeData;

    public TimeUDPClient() {
        this.__dummyData = new byte[1];
        this.__timeData = new byte[4];
    }

    public long getTime(InetAddress host, int port) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(this.__dummyData, this.__dummyData.length, host, port);
        DatagramPacket receivePacket = new DatagramPacket(this.__timeData, this.__timeData.length);
        this._socket_.send(sendPacket);
        this._socket_.receive(receivePacket);
        return (((0 | (((long) ((this.__timeData[0] & TelnetOption.MAX_OPTION_VALUE) << 24)) & 4294967295L)) | (((long) ((this.__timeData[1] & TelnetOption.MAX_OPTION_VALUE) << 16)) & 4294967295L)) | (((long) ((this.__timeData[2] & TelnetOption.MAX_OPTION_VALUE) << 8)) & 4294967295L)) | (((long) (this.__timeData[3] & TelnetOption.MAX_OPTION_VALUE)) & 4294967295L);
    }

    public long getTime(InetAddress host) throws IOException {
        return getTime(host, DEFAULT_PORT);
    }

    public Date getDate(InetAddress host, int port) throws IOException {
        return new Date((getTime(host, port) - SECONDS_1900_TO_1970) * 1000);
    }

    public Date getDate(InetAddress host) throws IOException {
        return new Date((getTime(host, DEFAULT_PORT) - SECONDS_1900_TO_1970) * 1000);
    }
}
