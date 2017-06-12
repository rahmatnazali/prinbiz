package org.apache.commons.net.tftp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import org.apache.commons.net.DatagramSocketClient;

public class TFTP extends DatagramSocketClient {
    public static final int ASCII_MODE = 0;
    public static final int BINARY_MODE = 1;
    public static final int DEFAULT_PORT = 69;
    public static final int DEFAULT_TIMEOUT = 5000;
    public static final int IMAGE_MODE = 1;
    public static final int NETASCII_MODE = 0;
    public static final int OCTET_MODE = 1;
    static final int PACKET_SIZE = 516;
    private byte[] __receiveBuffer;
    private DatagramPacket __receiveDatagram;
    private DatagramPacket __sendDatagram;
    byte[] _sendBuffer;

    public static final String getModeName(int mode) {
        return TFTPRequestPacket._modeStrings[mode];
    }

    public TFTP() {
        setDefaultTimeout(DEFAULT_TIMEOUT);
        this.__receiveBuffer = null;
        this.__receiveDatagram = null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void discardPackets() throws java.io.IOException {
        /*
        r4 = this;
        r3 = 516; // 0x204 float:7.23E-43 double:2.55E-321;
        r0 = new java.net.DatagramPacket;
        r2 = new byte[r3];
        r0.<init>(r2, r3);
        r1 = r4.getSoTimeout();
        r2 = 1;
        r4.setSoTimeout(r2);
    L_0x0011:
        r2 = r4._socket_;	 Catch:{ SocketException -> 0x0017, InterruptedIOException -> 0x001c }
        r2.receive(r0);	 Catch:{ SocketException -> 0x0017, InterruptedIOException -> 0x001c }
        goto L_0x0011;
    L_0x0017:
        r2 = move-exception;
    L_0x0018:
        r4.setSoTimeout(r1);
        return;
    L_0x001c:
        r2 = move-exception;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.net.tftp.TFTP.discardPackets():void");
    }

    public final TFTPPacket bufferedReceive() throws IOException, InterruptedIOException, SocketException, TFTPPacketException {
        this.__receiveDatagram.setData(this.__receiveBuffer);
        this.__receiveDatagram.setLength(this.__receiveBuffer.length);
        this._socket_.receive(this.__receiveDatagram);
        return TFTPPacket.newTFTPPacket(this.__receiveDatagram);
    }

    public final void bufferedSend(TFTPPacket packet) throws IOException {
        this._socket_.send(packet._newDatagram(this.__sendDatagram, this._sendBuffer));
    }

    public final void beginBufferedOps() {
        this.__receiveBuffer = new byte[PACKET_SIZE];
        this.__receiveDatagram = new DatagramPacket(this.__receiveBuffer, this.__receiveBuffer.length);
        this._sendBuffer = new byte[PACKET_SIZE];
        this.__sendDatagram = new DatagramPacket(this._sendBuffer, this._sendBuffer.length);
    }

    public final void endBufferedOps() {
        this.__receiveBuffer = null;
        this.__receiveDatagram = null;
        this._sendBuffer = null;
        this.__sendDatagram = null;
    }

    public final void send(TFTPPacket packet) throws IOException {
        this._socket_.send(packet.newDatagram());
    }

    public final TFTPPacket receive() throws IOException, InterruptedIOException, SocketException, TFTPPacketException {
        DatagramPacket packet = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
        this._socket_.receive(packet);
        return TFTPPacket.newTFTPPacket(packet);
    }
}
