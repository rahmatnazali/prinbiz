package org.apache.commons.net.telnet;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import javax.jmdns.impl.constants.DNSConstants;

public class WindowSizeOptionHandler extends TelnetOptionHandler {
    protected static final int WINDOW_SIZE = 31;
    private int m_nHeight;
    private int m_nWidth;

    public WindowSizeOptionHandler(int nWidth, int nHeight, boolean initlocal, boolean initremote, boolean acceptlocal, boolean acceptremote) {
        super(WINDOW_SIZE, initlocal, initremote, acceptlocal, acceptremote);
        this.m_nWidth = 80;
        this.m_nHeight = 24;
        this.m_nWidth = nWidth;
        this.m_nHeight = nHeight;
    }

    public WindowSizeOptionHandler(int nWidth, int nHeight) {
        super(WINDOW_SIZE, false, false, false, false);
        this.m_nWidth = 80;
        this.m_nHeight = 24;
        this.m_nWidth = nWidth;
        this.m_nHeight = nHeight;
    }

    public int[] answerSubnegotiation(int[] suboptionData, int suboptionLength) {
        return null;
    }

    public int[] startSubnegotiationLocal() {
        int nCompoundWindowSize = (this.m_nWidth * AccessibilityNodeInfoCompat.ACTION_CUT) + this.m_nHeight;
        int nResponseSize = 5;
        if (this.m_nWidth % DNSConstants.FLAGS_RD == TelnetOption.MAX_OPTION_VALUE) {
            nResponseSize = 5 + 1;
        }
        if (this.m_nWidth / DNSConstants.FLAGS_RD == TelnetOption.MAX_OPTION_VALUE) {
            nResponseSize++;
        }
        if (this.m_nHeight % DNSConstants.FLAGS_RD == TelnetOption.MAX_OPTION_VALUE) {
            nResponseSize++;
        }
        if (this.m_nHeight / DNSConstants.FLAGS_RD == TelnetOption.MAX_OPTION_VALUE) {
            nResponseSize++;
        }
        int[] response = new int[nResponseSize];
        response[0] = WINDOW_SIZE;
        int nIndex = 1;
        int nShift = 24;
        while (nIndex < nResponseSize) {
            response[nIndex] = (nCompoundWindowSize & (TelnetOption.MAX_OPTION_VALUE << nShift)) >>> nShift;
            if (response[nIndex] == 255) {
                nIndex++;
                response[nIndex] = TelnetOption.MAX_OPTION_VALUE;
            }
            nIndex++;
            nShift -= 8;
        }
        return response;
    }

    public int[] startSubnegotiationRemote() {
        return null;
    }
}
