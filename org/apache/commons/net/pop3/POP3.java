package org.apache.commons.net.pop3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.CRLFLineReader;

public class POP3 extends SocketClient {
    public static final int AUTHORIZATION_STATE = 0;
    public static final int DEFAULT_PORT = 110;
    public static final int DISCONNECTED_STATE = -1;
    public static final int TRANSACTION_STATE = 1;
    public static final int UPDATE_STATE = 2;
    static final String _DEFAULT_ENCODING = "ISO-8859-1";
    static final String _ERROR = "-ERR";
    static final String _OK = "+OK";
    static final String _OK_INT = "+ ";
    private int __popState;
    protected ProtocolCommandSupport _commandSupport_;
    String _lastReplyLine;
    BufferedReader _reader;
    int _replyCode;
    List<String> _replyLines;
    BufferedWriter _writer;

    public POP3() {
        setDefaultPort(DEFAULT_PORT);
        this.__popState = DISCONNECTED_STATE;
        this._reader = null;
        this._writer = null;
        this._replyLines = new ArrayList();
        this._commandSupport_ = new ProtocolCommandSupport(this);
    }

    private void __getReply() throws IOException {
        this._replyLines.clear();
        String line = this._reader.readLine();
        if (line == null) {
            throw new EOFException("Connection closed without indication.");
        }
        if (line.startsWith(_OK)) {
            this._replyCode = AUTHORIZATION_STATE;
        } else if (line.startsWith(_ERROR)) {
            this._replyCode = TRANSACTION_STATE;
        } else if (line.startsWith(_OK_INT)) {
            this._replyCode = UPDATE_STATE;
        } else {
            throw new MalformedServerReplyException("Received invalid POP3 protocol response from server." + line);
        }
        this._replyLines.add(line);
        this._lastReplyLine = line;
        fireReplyReceived(this._replyCode, getReplyString());
    }

    protected void _connectAction_() throws IOException {
        super._connectAction_();
        this._reader = new CRLFLineReader(new InputStreamReader(this._input_, _DEFAULT_ENCODING));
        this._writer = new BufferedWriter(new OutputStreamWriter(this._output_, _DEFAULT_ENCODING));
        __getReply();
        setState(AUTHORIZATION_STATE);
    }

    public void setState(int state) {
        this.__popState = state;
    }

    public int getState() {
        return this.__popState;
    }

    public void getAdditionalReply() throws IOException {
        String line = this._reader.readLine();
        while (line != null) {
            this._replyLines.add(line);
            if (!line.equals(".")) {
                line = this._reader.readLine();
            } else {
                return;
            }
        }
    }

    public void disconnect() throws IOException {
        super.disconnect();
        this._reader = null;
        this._writer = null;
        this._lastReplyLine = null;
        this._replyLines.clear();
        setState(DISCONNECTED_STATE);
    }

    public int sendCommand(String command, String args) throws IOException {
        if (this._writer == null) {
            throw new IllegalStateException("Socket is not connected");
        }
        StringBuilder __commandBuffer = new StringBuilder();
        __commandBuffer.append(command);
        if (args != null) {
            __commandBuffer.append(' ');
            __commandBuffer.append(args);
        }
        __commandBuffer.append(SocketClient.NETASCII_EOL);
        String message = __commandBuffer.toString();
        this._writer.write(message);
        this._writer.flush();
        fireCommandSent(command, message);
        __getReply();
        return this._replyCode;
    }

    public int sendCommand(String command) throws IOException {
        return sendCommand(command, null);
    }

    public int sendCommand(int command, String args) throws IOException {
        return sendCommand(POP3Command._commands[command], args);
    }

    public int sendCommand(int command) throws IOException {
        return sendCommand(POP3Command._commands[command], null);
    }

    public String[] getReplyStrings() {
        return (String[]) this._replyLines.toArray(new String[this._replyLines.size()]);
    }

    public String getReplyString() {
        StringBuilder buffer = new StringBuilder(DNSConstants.FLAGS_RD);
        for (String entry : this._replyLines) {
            buffer.append(entry);
            buffer.append(SocketClient.NETASCII_EOL);
        }
        return buffer.toString();
    }

    public void removeProtocolCommandistener(ProtocolCommandListener listener) {
        removeProtocolCommandListener(listener);
    }

    protected ProtocolCommandSupport getCommandSupport() {
        return this._commandSupport_;
    }
}
