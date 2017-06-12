package org.apache.commons.net.ftp;

import com.hiti.sql.oadc.OADCItem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.CRLFLineReader;
import org.apache.commons.net.telnet.TelnetOption;

public class FTP extends SocketClient {
    public static final int ASCII_FILE_TYPE = 0;
    public static final int BINARY_FILE_TYPE = 2;
    public static final int BLOCK_TRANSFER_MODE = 11;
    public static final int CARRIAGE_CONTROL_TEXT_FORMAT = 6;
    public static final int COMPRESSED_TRANSFER_MODE = 12;
    public static final String DEFAULT_CONTROL_ENCODING = "ISO-8859-1";
    public static final int DEFAULT_DATA_PORT = 20;
    public static final int DEFAULT_PORT = 21;
    public static final int EBCDIC_FILE_TYPE = 1;
    public static final int FILE_STRUCTURE = 7;
    public static final int LOCAL_FILE_TYPE = 3;
    public static final int NON_PRINT_TEXT_FORMAT = 4;
    public static final int PAGE_STRUCTURE = 9;
    public static final int RECORD_STRUCTURE = 8;
    public static final int REPLY_CODE_LEN = 3;
    public static final int STREAM_TRANSFER_MODE = 10;
    public static final int TELNET_TEXT_FORMAT = 5;
    private static final String __modes = "AEILNTCFRPSBC";
    protected ProtocolCommandSupport _commandSupport_;
    protected String _controlEncoding;
    protected BufferedReader _controlInput_;
    protected BufferedWriter _controlOutput_;
    protected boolean _newReplyString;
    protected int _replyCode;
    protected ArrayList<String> _replyLines;
    protected String _replyString;
    protected boolean strictMultilineParsing;

    public FTP() {
        this.strictMultilineParsing = false;
        setDefaultPort(DEFAULT_PORT);
        this._replyLines = new ArrayList();
        this._newReplyString = false;
        this._replyString = null;
        this._controlEncoding = DEFAULT_CONTROL_ENCODING;
        this._commandSupport_ = new ProtocolCommandSupport(this);
    }

    private boolean __strictCheck(String line, String code) {
        return (line.startsWith(code) && line.charAt(REPLY_CODE_LEN) == ' ') ? false : true;
    }

    private boolean __lenientCheck(String line) {
        return line.length() <= REPLY_CODE_LEN || line.charAt(REPLY_CODE_LEN) == '-' || !Character.isDigit(line.charAt(ASCII_FILE_TYPE));
    }

    private void __getReply() throws IOException {
        __getReply(true);
    }

    protected void __getReplyNoReport() throws IOException {
        __getReply(false);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void __getReply(boolean r8) throws java.io.IOException {
        /*
        r7 = this;
        r6 = 3;
        r4 = 1;
        r7._newReplyString = r4;
        r4 = r7._replyLines;
        r4.clear();
        r4 = r7._controlInput_;
        r3 = r4.readLine();
        if (r3 != 0) goto L_0x0019;
    L_0x0011:
        r4 = new org.apache.commons.net.ftp.FTPConnectionClosedException;
        r5 = "Connection closed without indication.";
        r4.<init>(r5);
        throw r4;
    L_0x0019:
        r2 = r3.length();
        if (r2 >= r6) goto L_0x0038;
    L_0x001f:
        r4 = new org.apache.commons.net.MalformedServerReplyException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "Truncated server reply: ";
        r5 = r5.append(r6);
        r5 = r5.append(r3);
        r5 = r5.toString();
        r4.<init>(r5);
        throw r4;
    L_0x0038:
        r0 = 0;
        r4 = 0;
        r5 = 3;
        r0 = r3.substring(r4, r5);	 Catch:{ NumberFormatException -> 0x0064 }
        r4 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x0064 }
        r7._replyCode = r4;	 Catch:{ NumberFormatException -> 0x0064 }
        r4 = r7._replyLines;
        r4.add(r3);
        if (r2 <= r6) goto L_0x008f;
    L_0x004c:
        r4 = r3.charAt(r6);
        r5 = 45;
        if (r4 != r5) goto L_0x008f;
    L_0x0054:
        r4 = r7._controlInput_;
        r3 = r4.readLine();
        if (r3 != 0) goto L_0x007e;
    L_0x005c:
        r4 = new org.apache.commons.net.ftp.FTPConnectionClosedException;
        r5 = "Connection closed without indication.";
        r4.<init>(r5);
        throw r4;
    L_0x0064:
        r1 = move-exception;
        r4 = new org.apache.commons.net.MalformedServerReplyException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "Could not parse response code.\nServer Reply: ";
        r5 = r5.append(r6);
        r5 = r5.append(r3);
        r5 = r5.toString();
        r4.<init>(r5);
        throw r4;
    L_0x007e:
        r4 = r7._replyLines;
        r4.add(r3);
        r4 = r7.isStrictMultilineParsing();
        if (r4 == 0) goto L_0x00a6;
    L_0x0089:
        r4 = r7.__strictCheck(r3, r0);
        if (r4 != 0) goto L_0x0054;
    L_0x008f:
        r4 = r7._replyCode;
        r5 = r7.getReplyString();
        r7.fireReplyReceived(r4, r5);
        r4 = r7._replyCode;
        r5 = 421; // 0x1a5 float:5.9E-43 double:2.08E-321;
        if (r4 != r5) goto L_0x00ad;
    L_0x009e:
        r4 = new org.apache.commons.net.ftp.FTPConnectionClosedException;
        r5 = "FTP response 421 received.  Server closed connection.";
        r4.<init>(r5);
        throw r4;
    L_0x00a6:
        r4 = r7.__lenientCheck(r3);
        if (r4 != 0) goto L_0x0054;
    L_0x00ac:
        goto L_0x008f;
    L_0x00ad:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.net.ftp.FTP.__getReply(boolean):void");
    }

    protected void _connectAction_() throws IOException {
        super._connectAction_();
        this._controlInput_ = new CRLFLineReader(new InputStreamReader(this._input_, getControlEncoding()));
        this._controlOutput_ = new BufferedWriter(new OutputStreamWriter(this._output_, getControlEncoding()));
        if (this.connectTimeout > 0) {
            int original = this._socket_.getSoTimeout();
            this._socket_.setSoTimeout(this.connectTimeout);
            try {
                __getReply();
                if (FTPReply.isPositivePreliminary(this._replyCode)) {
                    __getReply();
                }
                this._socket_.setSoTimeout(original);
            } catch (SocketTimeoutException e) {
                IOException ioe = new IOException("Timed out waiting for initial connect reply");
                ioe.initCause(e);
                throw ioe;
            } catch (Throwable th) {
                this._socket_.setSoTimeout(original);
            }
        } else {
            __getReply();
            if (FTPReply.isPositivePreliminary(this._replyCode)) {
                __getReply();
            }
        }
    }

    public void setControlEncoding(String encoding) {
        this._controlEncoding = encoding;
    }

    public String getControlEncoding() {
        return this._controlEncoding;
    }

    public void disconnect() throws IOException {
        super.disconnect();
        this._controlInput_ = null;
        this._controlOutput_ = null;
        this._newReplyString = false;
        this._replyString = null;
    }

    public int sendCommand(String command, String args) throws IOException {
        if (this._controlOutput_ == null) {
            throw new IOException("Connection is not open");
        }
        String message = __buildMessage(command, args);
        __send(message);
        fireCommandSent(command, message);
        __getReply();
        return this._replyCode;
    }

    private String __buildMessage(String command, String args) {
        StringBuilder __commandBuffer = new StringBuilder();
        __commandBuffer.append(command);
        if (args != null) {
            __commandBuffer.append(' ');
            __commandBuffer.append(args);
        }
        __commandBuffer.append(SocketClient.NETASCII_EOL);
        return __commandBuffer.toString();
    }

    private void __send(String message) throws IOException, FTPConnectionClosedException, SocketException {
        try {
            this._controlOutput_.write(message);
            this._controlOutput_.flush();
        } catch (SocketException e) {
            if (isConnected()) {
                throw e;
            }
            throw new FTPConnectionClosedException("Connection unexpectedly closed.");
        }
    }

    protected void __noop() throws IOException {
        __send(__buildMessage(FTPCmd.NOOP.getCommand(), null));
        __getReplyNoReport();
    }

    @Deprecated
    public int sendCommand(int command, String args) throws IOException {
        return sendCommand(FTPCommand.getCommand(command), args);
    }

    public int sendCommand(FTPCmd command) throws IOException {
        return sendCommand(command, null);
    }

    public int sendCommand(FTPCmd command, String args) throws IOException {
        return sendCommand(command.getCommand(), args);
    }

    public int sendCommand(String command) throws IOException {
        return sendCommand(command, null);
    }

    public int sendCommand(int command) throws IOException {
        return sendCommand(command, null);
    }

    public int getReplyCode() {
        return this._replyCode;
    }

    public int getReply() throws IOException {
        __getReply();
        return this._replyCode;
    }

    public String[] getReplyStrings() {
        return (String[]) this._replyLines.toArray(new String[this._replyLines.size()]);
    }

    public String getReplyString() {
        if (!this._newReplyString) {
            return this._replyString;
        }
        StringBuilder buffer = new StringBuilder(DNSConstants.FLAGS_RD);
        Iterator i$ = this._replyLines.iterator();
        while (i$.hasNext()) {
            buffer.append((String) i$.next());
            buffer.append(SocketClient.NETASCII_EOL);
        }
        this._newReplyString = false;
        String stringBuilder = buffer.toString();
        this._replyString = stringBuilder;
        return stringBuilder;
    }

    public int user(String username) throws IOException {
        return sendCommand(FTPCmd.USER, username);
    }

    public int pass(String password) throws IOException {
        return sendCommand(FTPCmd.PASS, password);
    }

    public int acct(String account) throws IOException {
        return sendCommand(FTPCmd.ACCT, account);
    }

    public int abor() throws IOException {
        return sendCommand(FTPCmd.ABOR);
    }

    public int cwd(String directory) throws IOException {
        return sendCommand(FTPCmd.CWD, directory);
    }

    public int cdup() throws IOException {
        return sendCommand(FTPCmd.CDUP);
    }

    public int quit() throws IOException {
        return sendCommand(FTPCmd.QUIT);
    }

    public int rein() throws IOException {
        return sendCommand(FTPCmd.REIN);
    }

    public int smnt(String dir) throws IOException {
        return sendCommand(FTPCmd.SMNT, dir);
    }

    public int port(InetAddress host, int port) throws IOException {
        StringBuilder info = new StringBuilder(24);
        info.append(host.getHostAddress().replace('.', ','));
        int num = port >>> RECORD_STRUCTURE;
        info.append(',');
        info.append(num);
        info.append(',');
        info.append(port & TelnetOption.MAX_OPTION_VALUE);
        return sendCommand(FTPCmd.PORT, info.toString());
    }

    public int eprt(InetAddress host, int port) throws IOException {
        StringBuilder info = new StringBuilder();
        String h = host.getHostAddress();
        int num = h.indexOf("%");
        if (num > 0) {
            h = h.substring(ASCII_FILE_TYPE, num);
        }
        info.append("|");
        if (host instanceof Inet4Address) {
            info.append(OADCItem.WATCH_TYPE_WATCH);
        } else if (host instanceof Inet6Address) {
            info.append("2");
        }
        info.append("|");
        info.append(h);
        info.append("|");
        info.append(port);
        info.append("|");
        return sendCommand(FTPCmd.EPRT, info.toString());
    }

    public int pasv() throws IOException {
        return sendCommand(FTPCmd.PASV);
    }

    public int epsv() throws IOException {
        return sendCommand(FTPCmd.EPSV);
    }

    public int type(int fileType, int formatOrByteSize) throws IOException {
        StringBuilder arg = new StringBuilder();
        arg.append(__modes.charAt(fileType));
        arg.append(' ');
        if (fileType == REPLY_CODE_LEN) {
            arg.append(formatOrByteSize);
        } else {
            arg.append(__modes.charAt(formatOrByteSize));
        }
        return sendCommand(FTPCmd.TYPE, arg.toString());
    }

    public int type(int fileType) throws IOException {
        return sendCommand(FTPCmd.TYPE, __modes.substring(fileType, fileType + EBCDIC_FILE_TYPE));
    }

    public int stru(int structure) throws IOException {
        return sendCommand(FTPCmd.STRU, __modes.substring(structure, structure + EBCDIC_FILE_TYPE));
    }

    public int mode(int mode) throws IOException {
        return sendCommand(FTPCmd.MODE, __modes.substring(mode, mode + EBCDIC_FILE_TYPE));
    }

    public int retr(String pathname) throws IOException {
        return sendCommand(FTPCmd.RETR, pathname);
    }

    public int stor(String pathname) throws IOException {
        return sendCommand(FTPCmd.STOR, pathname);
    }

    public int stou() throws IOException {
        return sendCommand(FTPCmd.STOU);
    }

    public int stou(String pathname) throws IOException {
        return sendCommand(FTPCmd.STOU, pathname);
    }

    public int appe(String pathname) throws IOException {
        return sendCommand(FTPCmd.APPE, pathname);
    }

    public int allo(int bytes) throws IOException {
        return sendCommand(FTPCmd.ALLO, Integer.toString(bytes));
    }

    public int feat() throws IOException {
        return sendCommand(FTPCmd.FEAT);
    }

    public int allo(int bytes, int recordSize) throws IOException {
        return sendCommand(FTPCmd.ALLO, Integer.toString(bytes) + " R " + Integer.toString(recordSize));
    }

    public int rest(String marker) throws IOException {
        return sendCommand(FTPCmd.REST, marker);
    }

    public int mdtm(String file) throws IOException {
        return sendCommand(FTPCmd.MDTM, file);
    }

    public int mfmt(String pathname, String timeval) throws IOException {
        return sendCommand(FTPCmd.MFMT, timeval + " " + pathname);
    }

    public int rnfr(String pathname) throws IOException {
        return sendCommand(FTPCmd.RNFR, pathname);
    }

    public int rnto(String pathname) throws IOException {
        return sendCommand(FTPCmd.RNTO, pathname);
    }

    public int dele(String pathname) throws IOException {
        return sendCommand(FTPCmd.DELE, pathname);
    }

    public int rmd(String pathname) throws IOException {
        return sendCommand(FTPCmd.RMD, pathname);
    }

    public int mkd(String pathname) throws IOException {
        return sendCommand(FTPCmd.MKD, pathname);
    }

    public int pwd() throws IOException {
        return sendCommand(FTPCmd.PWD);
    }

    public int list() throws IOException {
        return sendCommand(FTPCmd.LIST);
    }

    public int list(String pathname) throws IOException {
        return sendCommand(FTPCmd.LIST, pathname);
    }

    public int mlsd() throws IOException {
        return sendCommand(FTPCmd.MLSD);
    }

    public int mlsd(String path) throws IOException {
        return sendCommand(FTPCmd.MLSD, path);
    }

    public int mlst() throws IOException {
        return sendCommand(FTPCmd.MLST);
    }

    public int mlst(String path) throws IOException {
        return sendCommand(FTPCmd.MLST, path);
    }

    public int nlst() throws IOException {
        return sendCommand(FTPCmd.NLST);
    }

    public int nlst(String pathname) throws IOException {
        return sendCommand(FTPCmd.NLST, pathname);
    }

    public int site(String parameters) throws IOException {
        return sendCommand(FTPCmd.SITE, parameters);
    }

    public int syst() throws IOException {
        return sendCommand(FTPCmd.SYST);
    }

    public int stat() throws IOException {
        return sendCommand(FTPCmd.STAT);
    }

    public int stat(String pathname) throws IOException {
        return sendCommand(FTPCmd.STAT, pathname);
    }

    public int help() throws IOException {
        return sendCommand(FTPCmd.HELP);
    }

    public int help(String command) throws IOException {
        return sendCommand(FTPCmd.HELP, command);
    }

    public int noop() throws IOException {
        return sendCommand(FTPCmd.NOOP);
    }

    public boolean isStrictMultilineParsing() {
        return this.strictMultilineParsing;
    }

    public void setStrictMultilineParsing(boolean strictMultilineParsing) {
        this.strictMultilineParsing = strictMultilineParsing;
    }

    protected ProtocolCommandSupport getCommandSupport() {
        return this._commandSupport_;
    }
}
