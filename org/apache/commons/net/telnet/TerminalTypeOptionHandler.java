package org.apache.commons.net.telnet;

public class TerminalTypeOptionHandler extends TelnetOptionHandler {
    protected static final int TERMINAL_TYPE = 24;
    protected static final int TERMINAL_TYPE_IS = 0;
    protected static final int TERMINAL_TYPE_SEND = 1;
    private final String termType;

    public TerminalTypeOptionHandler(String termtype, boolean initlocal, boolean initremote, boolean acceptlocal, boolean acceptremote) {
        super(TERMINAL_TYPE, initlocal, initremote, acceptlocal, acceptremote);
        this.termType = termtype;
    }

    public TerminalTypeOptionHandler(String termtype) {
        super(TERMINAL_TYPE, false, false, false, false);
        this.termType = termtype;
    }

    public int[] answerSubnegotiation(int[] suboptionData, int suboptionLength) {
        if (suboptionData == null || suboptionLength <= TERMINAL_TYPE_SEND || this.termType == null || suboptionData[TERMINAL_TYPE_IS] != TERMINAL_TYPE || suboptionData[TERMINAL_TYPE_SEND] != TERMINAL_TYPE_SEND) {
            return null;
        }
        int[] iArr = new int[(this.termType.length() + 2)];
        iArr[TERMINAL_TYPE_IS] = TERMINAL_TYPE;
        iArr[TERMINAL_TYPE_SEND] = TERMINAL_TYPE_IS;
        for (int ii = TERMINAL_TYPE_IS; ii < this.termType.length(); ii += TERMINAL_TYPE_SEND) {
            iArr[ii + 2] = this.termType.charAt(ii);
        }
        return iArr;
    }

    public int[] startSubnegotiationLocal() {
        return null;
    }

    public int[] startSubnegotiationRemote() {
        return null;
    }
}
