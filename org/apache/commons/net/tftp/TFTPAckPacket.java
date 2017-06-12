package org.apache.commons.net.tftp;

import android.support.v4.internal.view.SupportMenu;
import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.telnet.TelnetOption;

public final class TFTPAckPacket extends TFTPPacket {
    int _blockNumber;

    public TFTPAckPacket(InetAddress destination, int port, int blockNumber) {
        super(4, destination, port);
        this._blockNumber = blockNumber;
    }

    TFTPAckPacket(DatagramPacket datagram) throws TFTPPacketException {
        super(4, datagram.getAddress(), datagram.getPort());
        byte[] data = datagram.getData();
        if (getType() != data[1]) {
            throw new TFTPPacketException("TFTP operator code does not match type.");
        }
        this._blockNumber = ((data[2] & TelnetOption.MAX_OPTION_VALUE) << 8) | (data[3] & TelnetOption.MAX_OPTION_VALUE);
    }

    DatagramPacket _newDatagram(DatagramPacket datagram, byte[] data) {
        data[0] = (byte) 0;
        data[1] = (byte) this._type;
        data[2] = (byte) ((this._blockNumber & SupportMenu.USER_MASK) >> 8);
        data[3] = (byte) (this._blockNumber & TelnetOption.MAX_OPTION_VALUE);
        datagram.setAddress(this._address);
        datagram.setPort(this._port);
        datagram.setData(data);
        datagram.setLength(4);
        return datagram;
    }

    public DatagramPacket newDatagram() {
        byte[] data = new byte[]{(byte) 0, (byte) this._type, (byte) ((this._blockNumber & SupportMenu.USER_MASK) >> 8), (byte) (this._blockNumber & TelnetOption.MAX_OPTION_VALUE)};
        return new DatagramPacket(data, data.length, this._address, this._port);
    }

    public int getBlockNumber() {
        return this._blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this._blockNumber = blockNumber;
    }
}
