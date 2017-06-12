package org.kobjects.util;

import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.telnet.TelnetCommand;
import org.kxml2.wap.Wbxml;

public class Strings {
    public static String replace(String src, String what, String by) {
        int i0 = src.indexOf(what);
        if (i0 == -1) {
            return src;
        }
        StringBuffer buf = new StringBuffer(src.substring(0, i0));
        while (true) {
            buf.append(by);
            i0 += what.length();
            int i1 = src.indexOf(what, i0);
            if (i1 == -1) {
                buf.append(src.substring(i0));
                return buf.toString();
            }
            buf.append(src.substring(i0, i1));
            i0 = i1;
        }
    }

    public static String toAscii(String src) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (c > ' ') {
                if (c >= '\u007f') {
                    switch (c) {
                        case Wbxml.LITERAL_AC /*196*/:
                            buf.append("Ae");
                            break;
                        case SMTPReply.HELP_MESSAGE /*214*/:
                            buf.append("Oe");
                            break;
                        case SMTPReply.SERVICE_READY /*220*/:
                            buf.append("Ue");
                            break;
                        case NNTPReply.ARTICLE_RETRIEVED_REQUEST_TEXT_SEPARATELY /*223*/:
                            buf.append("ss");
                            break;
                        case '\u00e4':
                            buf.append("ae");
                            break;
                        case TelnetCommand.AYT /*246*/:
                            buf.append("oe");
                            break;
                        case TelnetCommand.WONT /*252*/:
                            buf.append("ue");
                            break;
                        default:
                            buf.append('?');
                            break;
                    }
                }
                buf.append(c);
            } else {
                buf.append(' ');
            }
        }
        return buf.toString();
    }

    public static String fill(String s, int len, char c) {
        boolean left = len < 0;
        len = Math.abs(len);
        if (s.length() >= len) {
            return s;
        }
        StringBuffer buf = new StringBuffer();
        for (len -= s.length(); len > 0; len--) {
            buf.append(c);
        }
        if (!left) {
            return s + buf.toString();
        }
        buf.append(s);
        return buf.toString();
    }

    public static String beautify(String s) {
        StringBuffer buf = new StringBuffer();
        if (s.length() > 0) {
            buf.append(Character.toUpperCase(s.charAt(0)));
            int i = 1;
            while (i < s.length() - 1) {
                char c = s.charAt(i);
                if (Character.isUpperCase(c) && Character.isLowerCase(s.charAt(i - 1)) && Character.isLowerCase(s.charAt(i + 1))) {
                    buf.append(" ");
                }
                buf.append(c);
                i++;
            }
            if (s.length() > 1) {
                buf.append(s.charAt(s.length() - 1));
            }
        }
        return buf.toString();
    }

    public static String lTrim(String s, String chars) {
        int i = 0;
        int len = s.length();
        while (i < len) {
            if (chars == null) {
                if (s.charAt(i) > ' ') {
                    break;
                }
            } else if (chars.indexOf(s.charAt(i)) == -1) {
                break;
            }
            i++;
        }
        return i == 0 ? s : s.substring(i);
    }

    public static String rTrim(String s, String chars) {
        int i = s.length() - 1;
        while (i >= 0) {
            if (chars == null) {
                if (s.charAt(i) > ' ') {
                    break;
                }
            } else if (chars.indexOf(s.charAt(i)) == -1) {
                break;
            }
            i--;
        }
        return i == s.length() + -1 ? s : s.substring(0, i + 1);
    }
}
