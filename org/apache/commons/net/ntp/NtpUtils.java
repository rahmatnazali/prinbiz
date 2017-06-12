package org.apache.commons.net.ntp;

import com.google.android.gms.common.ConnectionResult;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.telnet.TelnetOption;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public final class NtpUtils {
    public static String getHostAddress(int address) {
        return ((address >>> 24) & TelnetOption.MAX_OPTION_VALUE) + "." + ((address >>> 16) & TelnetOption.MAX_OPTION_VALUE) + "." + ((address >>> 8) & TelnetOption.MAX_OPTION_VALUE) + "." + ((address >>> 0) & TelnetOption.MAX_OPTION_VALUE);
    }

    public static String getRefAddress(NtpV3Packet packet) {
        return getHostAddress(packet == null ? 0 : packet.getReferenceId());
    }

    public static String getReferenceClock(NtpV3Packet message) {
        if (message == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        int refId = message.getReferenceId();
        if (refId == 0) {
            return XmlPullParser.NO_NAMESPACE;
        }
        StringBuilder buf = new StringBuilder(4);
        int shiftBits = 24;
        while (shiftBits >= 0) {
            char c = (char) ((refId >>> shiftBits) & TelnetOption.MAX_OPTION_VALUE);
            if (c == '\u0000') {
                break;
            } else if (!Character.isLetterOrDigit(c)) {
                return XmlPullParser.NO_NAMESPACE;
            } else {
                buf.append(c);
                shiftBits -= 8;
            }
        }
        return buf.toString();
    }

    public static String getModeName(int mode) {
        switch (mode) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                return "Reserved";
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return "Symmetric Active";
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return "Symmetric Passive";
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return "Client";
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return "Server";
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                return "Broadcast";
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                return "Control";
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                return "Private";
            default:
                return "Unknown";
        }
    }
}
