package org.apache.commons.net.pop3;

public final class POP3Command {
    public static final int APOP = 9;
    public static final int AUTH = 13;
    public static final int CAPA = 12;
    public static final int DELE = 6;
    public static final int LIST = 4;
    public static final int NOOP = 7;
    public static final int PASS = 1;
    public static final int QUIT = 2;
    public static final int RETR = 5;
    public static final int RSET = 8;
    public static final int STAT = 3;
    public static final int TOP = 10;
    public static final int UIDL = 11;
    public static final int USER = 0;
    private static final int _NEXT_ = 14;
    static final String[] _commands;

    static {
        String[] strArr = new String[_NEXT_];
        strArr[USER] = "USER";
        strArr[PASS] = "PASS";
        strArr[QUIT] = "QUIT";
        strArr[STAT] = "STAT";
        strArr[LIST] = "LIST";
        strArr[RETR] = "RETR";
        strArr[DELE] = "DELE";
        strArr[NOOP] = "NOOP";
        strArr[RSET] = "RSET";
        strArr[APOP] = "APOP";
        strArr[TOP] = "TOP";
        strArr[UIDL] = "UIDL";
        strArr[CAPA] = "CAPA";
        strArr[AUTH] = "AUTH";
        _commands = strArr;
        if (_commands.length != _NEXT_) {
            throw new RuntimeException("Error in array definition");
        }
    }

    private POP3Command() {
    }

    public static final String getCommand(int command) {
        return _commands[command];
    }
}
