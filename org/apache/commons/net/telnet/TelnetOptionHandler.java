package org.apache.commons.net.telnet;

public abstract class TelnetOptionHandler {
    private boolean acceptLocal;
    private boolean acceptRemote;
    private boolean doFlag;
    private boolean initialLocal;
    private boolean initialRemote;
    private int optionCode;
    private boolean willFlag;

    public abstract int[] answerSubnegotiation(int[] iArr, int i);

    public abstract int[] startSubnegotiationLocal();

    public abstract int[] startSubnegotiationRemote();

    public TelnetOptionHandler(int optcode, boolean initlocal, boolean initremote, boolean acceptlocal, boolean acceptremote) {
        this.optionCode = -1;
        this.initialLocal = false;
        this.initialRemote = false;
        this.acceptLocal = false;
        this.acceptRemote = false;
        this.doFlag = false;
        this.willFlag = false;
        this.optionCode = optcode;
        this.initialLocal = initlocal;
        this.initialRemote = initremote;
        this.acceptLocal = acceptlocal;
        this.acceptRemote = acceptremote;
    }

    public int getOptionCode() {
        return this.optionCode;
    }

    public boolean getAcceptLocal() {
        return this.acceptLocal;
    }

    public boolean getAcceptRemote() {
        return this.acceptRemote;
    }

    public void setAcceptLocal(boolean accept) {
        this.acceptLocal = accept;
    }

    public void setAcceptRemote(boolean accept) {
        this.acceptRemote = accept;
    }

    public boolean getInitLocal() {
        return this.initialLocal;
    }

    public boolean getInitRemote() {
        return this.initialRemote;
    }

    public void setInitLocal(boolean init) {
        this.initialLocal = init;
    }

    public void setInitRemote(boolean init) {
        this.initialRemote = init;
    }

    boolean getWill() {
        return this.willFlag;
    }

    void setWill(boolean state) {
        this.willFlag = state;
    }

    boolean getDo() {
        return this.doFlag;
    }

    void setDo(boolean state) {
        this.doFlag = state;
    }
}
