package org.apache.commons.net.tftp;

import android.support.v4.internal.view.SupportMenu;
import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.telnet.TelnetOption;

public final class TFTPErrorPacket extends TFTPPacket {
    public static final int ACCESS_VIOLATION = 2;
    public static final int FILE_EXISTS = 6;
    public static final int FILE_NOT_FOUND = 1;
    public static final int ILLEGAL_OPERATION = 4;
    public static final int NO_SUCH_USER = 7;
    public static final int OUT_OF_SPACE = 3;
    public static final int UNDEFINED = 0;
    public static final int UNKNOWN_TID = 5;
    int _error;
    String _message;

    public TFTPErrorPacket(InetAddress destination, int port, int error, String message) {
        super(UNKNOWN_TID, destination, port);
        this._error = error;
        this._message = message;
    }

    TFTPErrorPacket(DatagramPacket datagram) throws TFTPPacketException {
        super(UNKNOWN_TID, datagram.getAddress(), datagram.getPort());
        byte[] data = datagram.getData();
        int length = datagram.getLength();
        if (getType() != data[FILE_NOT_FOUND]) {
            throw new TFTPPacketException("TFTP operator code does not match type.");
        }
        this._error = ((data[ACCESS_VIOLATION] & TelnetOption.MAX_OPTION_VALUE) << 8) | (data[OUT_OF_SPACE] & TelnetOption.MAX_OPTION_VALUE);
        if (length < UNKNOWN_TID) {
            throw new TFTPPacketException("Bad error packet. No message.");
        }
        int index = ILLEGAL_OPERATION;
        StringBuilder buffer = new StringBuilder();
        while (index < length && data[index] != null) {
            buffer.append((char) data[index]);
            index += FILE_NOT_FOUND;
        }
        this._message = buffer.toString();
    }

    DatagramPacket _newDatagram(DatagramPacket datagram, byte[] data) {
        int length = this._message.length();
        data[UNDEFINED] = (byte) 0;
        data[FILE_NOT_FOUND] = (byte) this._type;
        data[ACCESS_VIOLATION] = (byte) ((this._error & SupportMenu.USER_MASK) >> 8);
        data[OUT_OF_SPACE] = (byte) (this._error & TelnetOption.MAX_OPTION_VALUE);
        System.arraycopy(this._message.getBytes(), UNDEFINED, data, ILLEGAL_OPERATION, length);
        data[length + ILLEGAL_OPERATION] = (byte) 0;
        datagram.setAddress(this._address);
        datagram.setPort(this._port);
        datagram.setData(data);
        datagram.setLength(length + ILLEGAL_OPERATION);
        return datagram;
    }

    public DatagramPacket newDatagram() {
        int length = this._message.length();
        byte[] data = new byte[(length + UNKNOWN_TID)];
        data[UNDEFINED] = (byte) 0;
        data[FILE_NOT_FOUND] = (byte) this._type;
        data[ACCESS_VIOLATION] = (byte) ((this._error & SupportMenu.USER_MASK) >> 8);
        data[OUT_OF_SPACE] = (byte) (this._error & TelnetOption.MAX_OPTION_VALUE);
        System.arraycopy(this._message.getBytes(), UNDEFINED, data, ILLEGAL_OPERATION, length);
        data[length + ILLEGAL_OPERATION] = (byte) 0;
        return new DatagramPacket(data, data.length, this._address, this._port);
    }

    public int getError() {
        return this._error;
    }

    public String getMessage() {
        return this._message;
    }
}
