package javax.jmdns.impl.constants;

import android.support.v4.internal.view.SupportMenu;

public enum DNSOptionCode {
    Unknown("Unknown", SupportMenu.USER_MASK),
    LLQ("LLQ", 1),
    UL("UL", 2),
    NSID("NSID", 3),
    Owner("Owner", 4);
    
    private final String _externalName;
    private final int _index;

    private DNSOptionCode(String name, int index) {
        this._externalName = name;
        this._index = index;
    }

    public String externalName() {
        return this._externalName;
    }

    public int indexValue() {
        return this._index;
    }

    public static DNSOptionCode resultCodeForFlags(int optioncode) {
        int maskedIndex = optioncode;
        for (DNSOptionCode aCode : values()) {
            if (aCode._index == maskedIndex) {
                return aCode;
            }
        }
        return Unknown;
    }

    public String toString() {
        return name() + " index " + indexValue();
    }
}
