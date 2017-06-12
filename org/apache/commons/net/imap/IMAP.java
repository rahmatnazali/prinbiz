package org.apache.commons.net.imap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.CRLFLineReader;

public class IMAP extends SocketClient {
    public static final int DEFAULT_PORT = 143;
    protected static final String __DEFAULT_ENCODING = "ISO-8859-1";
    private IMAPState __state;
    protected BufferedWriter __writer;
    private final char[] _initialID;
    protected BufferedReader _reader;
    private int _replyCode;
    private final List<String> _replyLines;

    public enum IMAPState {
        DISCONNECTED_STATE,
        NOT_AUTH_STATE,
        AUTH_STATE,
        LOGOUT_STATE
    }

    public IMAP() {
        this._initialID = new char[]{'A', 'A', 'A', 'A'};
        setDefaultPort(DEFAULT_PORT);
        this.__state = IMAPState.DISCONNECTED_STATE;
        this._reader = null;
        this.__writer = null;
        this._replyLines = new ArrayList();
        createCommandSupport();
    }

    private void __getReply() throws IOException {
        __getReply(true);
    }

    private void __getReply(boolean wantTag) throws IOException {
        this._replyLines.clear();
        String line = this._reader.readLine();
        if (line == null) {
            throw new EOFException("Connection closed without indication.");
        }
        this._replyLines.add(line);
        if (wantTag) {
            while (IMAPReply.isUntagged(line)) {
                int literalCount = IMAPReply.literalCount(line);
                while (literalCount >= 0) {
                    line = this._reader.readLine();
                    if (line == null) {
                        throw new EOFException("Connection closed without indication.");
                    }
                    this._replyLines.add(line);
                    literalCount -= line.length() + 2;
                }
                line = this._reader.readLine();
                if (line == null) {
                    throw new EOFException("Connection closed without indication.");
                }
                this._replyLines.add(line);
            }
            this._replyCode = IMAPReply.getReplyCode(line);
        } else {
            this._replyCode = IMAPReply.getUntaggedReplyCode(line);
        }
        fireReplyReceived(this._replyCode, getReplyString());
    }

    protected void _connectAction_() throws IOException {
        super._connectAction_();
        this._reader = new CRLFLineReader(new InputStreamReader(this._input_, __DEFAULT_ENCODING));
        this.__writer = new BufferedWriter(new OutputStreamWriter(this._output_, __DEFAULT_ENCODING));
        int tmo = getSoTimeout();
        if (tmo <= 0) {
            setSoTimeout(this.connectTimeout);
        }
        __getReply(false);
        if (tmo <= 0) {
            setSoTimeout(tmo);
        }
        setState(IMAPState.NOT_AUTH_STATE);
    }

    protected void setState(IMAPState state) {
        this.__state = state;
    }

    public IMAPState getState() {
        return this.__state;
    }

    public void disconnect() throws IOException {
        super.disconnect();
        this._reader = null;
        this.__writer = null;
        this._replyLines.clear();
        setState(IMAPState.DISCONNECTED_STATE);
    }

    private int sendCommandWithID(String commandID, String command, String args) throws IOException {
        StringBuilder __commandBuffer = new StringBuilder();
        if (commandID != null) {
            __commandBuffer.append(commandID);
            __commandBuffer.append(' ');
        }
        __commandBuffer.append(command);
        if (args != null) {
            __commandBuffer.append(' ');
            __commandBuffer.append(args);
        }
        __commandBuffer.append(SocketClient.NETASCII_EOL);
        String message = __commandBuffer.toString();
        this.__writer.write(message);
        this.__writer.flush();
        fireCommandSent(command, message);
        __getReply();
        return this._replyCode;
    }

    public int sendCommand(String command, String args) throws IOException {
        return sendCommandWithID(generateCommandID(), command, args);
    }

    public int sendCommand(String command) throws IOException {
        return sendCommand(command, null);
    }

    public int sendCommand(IMAPCommand command, String args) throws IOException {
        return sendCommand(command.getIMAPCommand(), args);
    }

    public boolean doCommand(IMAPCommand command, String args) throws IOException {
        return IMAPReply.isSuccess(sendCommand(command, args));
    }

    public int sendCommand(IMAPCommand command) throws IOException {
        return sendCommand(command, null);
    }

    public boolean doCommand(IMAPCommand command) throws IOException {
        return IMAPReply.isSuccess(sendCommand(command));
    }

    public int sendData(String command) throws IOException {
        return sendCommandWithID(null, command, null);
    }

    public String[] getReplyStrings() {
        return (String[]) this._replyLines.toArray(new String[this._replyLines.size()]);
    }

    public String getReplyString() {
        StringBuilder buffer = new StringBuilder(DNSConstants.FLAGS_RD);
        for (String s : this._replyLines) {
            buffer.append(s);
            buffer.append(SocketClient.NETASCII_EOL);
        }
        return buffer.toString();
    }

    protected String generateCommandID() {
        String res = new String(this._initialID);
        boolean carry = true;
        int i = this._initialID.length - 1;
        while (carry && i >= 0) {
            if (this._initialID[i] == 'Z') {
                this._initialID[i] = 'A';
            } else {
                char[] cArr = this._initialID;
                cArr[i] = (char) (cArr[i] + 1);
                carry = false;
            }
            i--;
        }
        return res;
    }
}
