package org.kobjects.base64;

import android.support.v4.view.MotionEventCompat;
import com.hiti.jumpinfo.JumpInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.telnet.TelnetOption;
import org.apache.commons.net.whois.WhoisClient;

public class Base64 {
    static final char[] charTab;

    static {
        charTab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    }

    public static String encode(byte[] data) {
        return encode(data, 0, data.length, null).toString();
    }

    public static StringBuffer encode(byte[] data, int start, int len, StringBuffer buf) {
        if (buf == null) {
            buf = new StringBuffer((data.length * 3) / 2);
        }
        int end = len - 3;
        int i = start;
        int n = 0;
        while (i <= end) {
            int d = (((data[i] & TelnetOption.MAX_OPTION_VALUE) << 16) | ((data[i + 1] & TelnetOption.MAX_OPTION_VALUE) << 8)) | (data[i + 2] & TelnetOption.MAX_OPTION_VALUE);
            buf.append(charTab[(d >> 18) & 63]);
            buf.append(charTab[(d >> 12) & 63]);
            buf.append(charTab[(d >> 6) & 63]);
            buf.append(charTab[d & 63]);
            i += 3;
            int n2 = n + 1;
            if (n >= 14) {
                n2 = 0;
                buf.append(SocketClient.NETASCII_EOL);
            }
            n = n2;
        }
        if (i == (start + len) - 2) {
            d = ((data[i] & TelnetOption.MAX_OPTION_VALUE) << 16) | ((data[i + 1] & TelnetOption.MAX_OPTION_VALUE) << 8);
            buf.append(charTab[(d >> 18) & 63]);
            buf.append(charTab[(d >> 12) & 63]);
            buf.append(charTab[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == (start + len) - 1) {
            d = (data[i] & TelnetOption.MAX_OPTION_VALUE) << 16;
            buf.append(charTab[(d >> 18) & 63]);
            buf.append(charTab[(d >> 12) & 63]);
            buf.append("==");
        }
        return buf;
    }

    static int decode(char c) {
        if (c >= 'A' && c <= 'Z') {
            return c - 65;
        }
        if (c >= 'a' && c <= 'z') {
            return (c - 97) + 26;
        }
        if (c >= '0' && c <= '9') {
            return ((c - 48) + 26) + 26;
        }
        switch (c) {
            case WhoisClient.DEFAULT_PORT /*43*/:
                return 62;
            case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
                return 63;
            case JumpInfo.RESULT_END /*61*/:
                return 0;
            default:
                throw new RuntimeException("unexpected code: " + c);
        }
    }

    public static byte[] decode(String s) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            decode(s, bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void decode(String s, OutputStream os) throws IOException {
        int i = 0;
        int len = s.length();
        while (true) {
            if (i < len && s.charAt(i) <= ' ') {
                i++;
            } else if (i != len) {
                int tri = (((decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12)) + (decode(s.charAt(i + 2)) << 6)) + decode(s.charAt(i + 3));
                os.write((tri >> 16) & TelnetOption.MAX_OPTION_VALUE);
                if (s.charAt(i + 2) != '=') {
                    os.write((tri >> 8) & TelnetOption.MAX_OPTION_VALUE);
                    if (s.charAt(i + 3) != '=') {
                        os.write(tri & TelnetOption.MAX_OPTION_VALUE);
                        i += 4;
                    } else {
                        return;
                    }
                }
                return;
            } else {
                return;
            }
        }
    }
}
