package org.apache.commons.net.tftp;

import android.support.v4.internal.view.SupportMenu;
import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.telnet.TelnetOption;

public final class TFTPDataPacket extends TFTPPacket {
    public static final int MAX_DATA_LENGTH = 512;
    public static final int MIN_DATA_LENGTH = 0;
    int _blockNumber;
    byte[] _data;
    int _length;
    int _offset;

    public TFTPDataPacket(InetAddress destination, int port, int blockNumber, byte[] data, int offset, int length) {
        super(3, destination, port);
        this._blockNumber = blockNumber;
        this._data = data;
        this._offset = offset;
        if (length > MAX_DATA_LENGTH) {
            this._length = MAX_DATA_LENGTH;
        } else {
            this._length = length;
        }
    }

    public TFTPDataPacket(InetAddress destination, int port, int blockNumber, byte[] data) {
        this(destination, port, blockNumber, data, 0, data.length);
    }

    TFTPDataPacket(DatagramPacket datagram) throws TFTPPacketException {
        super(3, datagram.getAddress(), datagram.getPort());
        this._data = datagram.getData();
        this._offset = 4;
        if (getType() != this._data[1]) {
            throw new TFTPPacketException("TFTP operator code does not match type.");
        }
        this._blockNumber = ((this._data[2] & TelnetOption.MAX_OPTION_VALUE) << 8) | (this._data[3] & TelnetOption.MAX_OPTION_VALUE);
        this._length = datagram.getLength() - 4;
        if (this._length > MAX_DATA_LENGTH) {
            this._length = MAX_DATA_LENGTH;
        }
    }

    DatagramPacket _newDatagram(DatagramPacket datagram, byte[] data) {
        data[0] = (byte) 0;
        data[1] = (byte) this._type;
        data[2] = (byte) ((this._blockNumber & SupportMenu.USER_MASK) >> 8);
        data[3] = (byte) (this._blockNumber & TelnetOption.MAX_OPTION_VALUE);
        if (data != this._data) {
            System.arraycopy(this._data, this._offset, data, 4, this._length);
        }
        datagram.setAddress(this._address);
        datagram.setPort(this._port);
        datagram.setData(data);
        datagram.setLength(this._length + 4);
        return datagram;
    }

    public DatagramPacket newDatagram() {
        byte[] data = new byte[(this._length + 4)];
        data[0] = (byte) 0;
        data[1] = (byte) this._type;
        data[2] = (byte) ((this._blockNumber & SupportMenu.USER_MASK) >> 8);
        data[3] = (byte) (this._blockNumber & TelnetOption.MAX_OPTION_VALUE);
        System.arraycopy(this._data, this._offset, data, 4, this._length);
        return new DatagramPacket(data, this._length + 4, this._address, this._port);
    }

    public int getBlockNumber() {
        return this._blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this._blockNumber = blockNumber;
    }

    public void setData(byte[] data, int offset, int length) {
        this._data = data;
        this._offset = offset;
        this._length = length;
        if (length > MAX_DATA_LENGTH) {
            this._length = MAX_DATA_LENGTH;
        } else {
            this._length = length;
        }
    }

    public int getDataLength() {
        return this._length;
    }

    public int getDataOffset() {
        return this._offset;
    }

    public byte[] getData() {
        return this._data;
    }
}
