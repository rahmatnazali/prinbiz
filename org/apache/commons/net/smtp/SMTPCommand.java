package org.apache.commons.net.smtp;

public final class SMTPCommand {
    public static final int AUTH = 14;
    public static final int DATA = 3;
    public static final int EHLO = 15;
    public static final int EXPAND = 9;
    public static final int EXPN = 9;
    public static final int HELLO = 0;
    public static final int HELO = 0;
    public static final int HELP = 10;
    public static final int LOGIN = 0;
    public static final int LOGOUT = 13;
    public static final int MAIL = 1;
    public static final int MAIL_FROM = 1;
    public static final int NOOP = 11;
    public static final int QUIT = 13;
    public static final int RCPT = 2;
    public static final int RECIPIENT = 2;
    public static final int RESET = 7;
    public static final int RSET = 7;
    public static final int SAML = 6;
    public static final int SEND = 4;
    public static final int SEND_AND_MAIL_FROM = 6;
    public static final int SEND_FROM = 4;
    public static final int SEND_MESSAGE_DATA = 3;
    public static final int SEND_OR_MAIL_FROM = 5;
    public static final int SOML = 5;
    public static final int TURN = 12;
    public static final int VERIFY = 8;
    public static final int VRFY = 8;
    private static final int _NEXT_ = 16;
    private static final String[] _commands;

    private SMTPCommand() {
    }

    static {
        String[] strArr = new String[_NEXT_];
        strArr[LOGIN] = "HELO";
        strArr[MAIL_FROM] = "MAIL FROM:";
        strArr[RECIPIENT] = "RCPT TO:";
        strArr[SEND_MESSAGE_DATA] = "DATA";
        strArr[SEND_FROM] = "SEND FROM:";
        strArr[SOML] = "SOML FROM:";
        strArr[SEND_AND_MAIL_FROM] = "SAML FROM:";
        strArr[RSET] = "RSET";
        strArr[VRFY] = "VRFY";
        strArr[EXPN] = "EXPN";
        strArr[HELP] = "HELP";
        strArr[NOOP] = "NOOP";
        strArr[TURN] = "TURN";
        strArr[QUIT] = "QUIT";
        strArr[AUTH] = "AUTH";
        strArr[EHLO] = "EHLO";
        _commands = strArr;
        if (_commands.length != _NEXT_) {
            throw new RuntimeException("Error in array definition");
        }
    }

    public static final String getCommand(int command) {
        return _commands[command];
    }
}
