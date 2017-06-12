package org.kxml2.io;

import android.support.v4.media.TransportMediator;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.jumpinfo.JumpInfo;
import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.pop3.POP3;
import org.apache.commons.net.telnet.TelnetOption;
import org.ksoap2.SoapEnvelope;
import org.kxml2.wap.Wbxml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class KXmlParser implements XmlPullParser {
    private static final String ILLEGAL_TYPE = "Wrong event type";
    private static final int LEGACY = 999;
    private static final String UNEXPECTED_EOF = "Unexpected EOF";
    private static final int XML_DECL = 998;
    private int attributeCount;
    private String[] attributes;
    private int column;
    private boolean degenerated;
    private int depth;
    private String[] elementStack;
    private String encoding;
    private Hashtable entityMap;
    private String error;
    private boolean isWhitespace;
    private int line;
    private Object location;
    private String name;
    private String namespace;
    private int[] nspCounts;
    private String[] nspStack;
    private int[] peek;
    private int peekCount;
    private String prefix;
    private boolean processNsp;
    private Reader reader;
    private boolean relaxed;
    private char[] srcBuf;
    private int srcCount;
    private int srcPos;
    private Boolean standalone;
    private boolean token;
    private char[] txtBuf;
    private int txtPos;
    private int type;
    private boolean unresolved;
    private String version;
    private boolean wasCR;

    public KXmlParser() {
        int i = TransportMediator.FLAG_KEY_MEDIA_NEXT;
        this.elementStack = new String[16];
        this.nspStack = new String[8];
        this.nspCounts = new int[4];
        this.txtBuf = new char[TransportMediator.FLAG_KEY_MEDIA_NEXT];
        this.attributes = new String[16];
        this.peek = new int[2];
        if (Runtime.getRuntime().freeMemory() >= 1048576) {
            i = AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD;
        }
        this.srcBuf = new char[i];
    }

    private final boolean isProp(String n1, boolean prop, String n2) {
        if (!n1.startsWith("http://xmlpull.org/v1/doc/")) {
            return false;
        }
        if (prop) {
            return n1.substring(42).equals(n2);
        }
        return n1.substring(40).equals(n2);
    }

    private final boolean adjustNsp() throws XmlPullParserException {
        int cut;
        boolean any = false;
        int i = 0;
        while (i < (this.attributeCount << 2)) {
            String prefix;
            String attrName = this.attributes[i + 2];
            cut = attrName.indexOf(58);
            if (cut != -1) {
                prefix = attrName.substring(0, cut);
                attrName = attrName.substring(cut + 1);
            } else if (attrName.equals("xmlns")) {
                prefix = attrName;
                attrName = null;
            } else {
                i += 4;
            }
            if (prefix.equals("xmlns")) {
                int[] iArr = this.nspCounts;
                int i2 = this.depth;
                int i3 = iArr[i2];
                iArr[i2] = i3 + 1;
                int j = i3 << 1;
                this.nspStack = ensureCapacity(this.nspStack, j + 2);
                this.nspStack[j] = attrName;
                this.nspStack[j + 1] = this.attributes[i + 3];
                if (attrName != null && this.attributes[i + 3].equals(XmlPullParser.NO_NAMESPACE)) {
                    error("illegal empty namespace");
                }
                Object obj = this.attributes;
                i2 = i + 4;
                Object obj2 = this.attributes;
                int i4 = this.attributeCount - 1;
                this.attributeCount = i4;
                System.arraycopy(obj, i2, obj2, i, (i4 << 2) - i);
                i -= 4;
            } else {
                any = true;
            }
            i += 4;
        }
        if (any) {
            i = (this.attributeCount << 2) - 4;
            while (i >= 0) {
                attrName = this.attributes[i + 2];
                cut = attrName.indexOf(58);
                if (cut != 0 || this.relaxed) {
                    if (cut != -1) {
                        String attrPrefix = attrName.substring(0, cut);
                        attrName = attrName.substring(cut + 1);
                        String attrNs = getNamespace(attrPrefix);
                        if (attrNs != null || this.relaxed) {
                            this.attributes[i] = attrNs;
                            this.attributes[i + 1] = attrPrefix;
                            this.attributes[i + 2] = attrName;
                        } else {
                            throw new RuntimeException("Undefined Prefix: " + attrPrefix + " in " + this);
                        }
                    }
                    i -= 4;
                } else {
                    throw new RuntimeException("illegal attribute name: " + attrName + " at " + this);
                }
            }
        }
        cut = this.name.indexOf(58);
        if (cut == 0) {
            error("illegal tag name: " + this.name);
        }
        if (cut != -1) {
            this.prefix = this.name.substring(0, cut);
            this.name = this.name.substring(cut + 1);
        }
        this.namespace = getNamespace(this.prefix);
        if (this.namespace == null) {
            if (this.prefix != null) {
                error("undefined prefix: " + this.prefix);
            }
            this.namespace = XmlPullParser.NO_NAMESPACE;
        }
        return any;
    }

    private final String[] ensureCapacity(String[] arr, int required) {
        if (arr.length >= required) {
            return arr;
        }
        String[] bigger = new String[(required + 16)];
        System.arraycopy(arr, 0, bigger, 0, arr.length);
        return bigger;
    }

    private final void error(String desc) throws XmlPullParserException {
        if (!this.relaxed) {
            exception(desc);
        } else if (this.error == null) {
            this.error = "ERR: " + desc;
        }
    }

    private final void exception(String desc) throws XmlPullParserException {
        if (desc.length() >= 100) {
            desc = desc.substring(0, 100) + "\n";
        }
        throw new XmlPullParserException(desc, this, null);
    }

    private final void nextImpl() throws IOException, XmlPullParserException {
        boolean z = false;
        if (this.reader == null) {
            exception("No Input specified");
        }
        if (this.type == 3) {
            this.depth--;
        }
        do {
            this.attributeCount = -1;
            if (!this.degenerated) {
                if (this.error == null) {
                    this.prefix = null;
                    this.name = null;
                    this.namespace = null;
                    this.type = peekType();
                    switch (this.type) {
                        case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                            return;
                        case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                            parseStartTag(false);
                            return;
                        case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                            parseEndTag();
                            return;
                        case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                            if (!this.token) {
                                z = true;
                            }
                            pushText(60, z);
                            if (this.depth == 0 && this.isWhitespace) {
                                this.type = 7;
                                return;
                            }
                            return;
                        case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                            pushEntity();
                            return;
                        default:
                            this.type = parseLegacy(this.token);
                            break;
                    }
                }
                for (int i = 0; i < this.error.length(); i++) {
                    push(this.error.charAt(i));
                }
                this.error = null;
                this.type = 9;
                return;
            }
            this.degenerated = false;
            this.type = 3;
            return;
        } while (this.type == XML_DECL);
    }

    private final int parseLegacy(boolean push) throws IOException, XmlPullParserException {
        int term;
        int result;
        String req = XmlPullParser.NO_NAMESPACE;
        int prev = 0;
        read();
        int c = read();
        if (c == 63) {
            if ((peek(0) == SoapEnvelope.VER12 || peek(0) == 88) && (peek(1) == 109 || peek(1) == 77)) {
                if (push) {
                    push(peek(0));
                    push(peek(1));
                }
                read();
                read();
                if ((peek(0) == 108 || peek(0) == 76) && peek(1) <= 32) {
                    if (this.line != 1 || this.column > 4) {
                        error("PI must not start with xml");
                    }
                    parseStartTag(true);
                    if (this.attributeCount < 1 || !"version".equals(this.attributes[2])) {
                        error("version expected");
                    }
                    this.version = this.attributes[3];
                    int pos = 1;
                    if (1 < this.attributeCount && "encoding".equals(this.attributes[6])) {
                        this.encoding = this.attributes[7];
                        pos = 1 + 1;
                    }
                    if (pos < this.attributeCount && "standalone".equals(this.attributes[(pos * 4) + 2])) {
                        String st = this.attributes[(pos * 4) + 3];
                        if ("yes".equals(st)) {
                            this.standalone = new Boolean(true);
                        } else if ("no".equals(st)) {
                            this.standalone = new Boolean(false);
                        } else {
                            error("illegal standalone value: " + st);
                        }
                        pos++;
                    }
                    if (pos != this.attributeCount) {
                        error("illegal xmldecl");
                    }
                    this.isWhitespace = true;
                    this.txtPos = 0;
                    return XML_DECL;
                }
            }
            term = 63;
            result = 8;
        } else if (c != 33) {
            error("illegal: <" + c);
            return 9;
        } else if (peek(0) == 45) {
            result = 9;
            req = "--";
            term = 45;
        } else if (peek(0) == 91) {
            result = 5;
            req = "[CDATA[";
            term = 93;
            push = true;
        } else {
            result = 10;
            req = "DOCTYPE";
            term = -1;
        }
        for (int i = 0; i < req.length(); i++) {
            read(req.charAt(i));
        }
        if (result == 10) {
            parseDoctype(push);
            return result;
        }
        while (true) {
            c = read();
            if (c == -1) {
                error(UNEXPECTED_EOF);
                return 9;
            }
            if (push) {
                push(c);
            }
            if ((term == 63 || c == term) && peek(0) == term && peek(1) == 62) {
                break;
            }
            prev = c;
        }
        if (term == 45 && prev == 45 && !this.relaxed) {
            error("illegal comment delimiter: --->");
        }
        read();
        read();
        if (!push || term == 63) {
            return result;
        }
        this.txtPos--;
        return result;
    }

    private final void parseDoctype(boolean push) throws IOException, XmlPullParserException {
        int nesting = 1;
        boolean quoted = false;
        while (true) {
            int i = read();
            switch (i) {
                case POP3.DISCONNECTED_STATE /*-1*/:
                    error(UNEXPECTED_EOF);
                    return;
                case TelnetOption.NEW_ENVIRONMENT_VARIABLES /*39*/:
                    quoted = !quoted;
                    break;
                case JumpInfo.RESULT_EDIT_ID_ACTIVITY /*60*/:
                    if (!quoted) {
                        nesting++;
                        break;
                    }
                    break;
                case JumpInfo.RESULT_PRINT_VIEW_ACTIVITY /*62*/:
                    if (!quoted) {
                        nesting--;
                        if (nesting == 0) {
                            return;
                        }
                    }
                    break;
            }
            if (push) {
                push(i);
            }
        }
    }

    private final void parseEndTag() throws IOException, XmlPullParserException {
        read();
        read();
        this.name = readName();
        skip();
        read('>');
        int sp = (this.depth - 1) << 2;
        if (this.depth == 0) {
            error("element stack empty");
            this.type = 9;
        } else if (!this.relaxed) {
            if (!this.name.equals(this.elementStack[sp + 3])) {
                error("expected: /" + this.elementStack[sp + 3] + " read: " + this.name);
            }
            this.namespace = this.elementStack[sp];
            this.prefix = this.elementStack[sp + 1];
            this.name = this.elementStack[sp + 2];
        }
    }

    private final int peekType() throws IOException {
        switch (peek(0)) {
            case POP3.DISCONNECTED_STATE /*-1*/:
                return 1;
            case TelnetOption.ENCRYPTION /*38*/:
                return 6;
            case JumpInfo.RESULT_EDIT_ID_ACTIVITY /*60*/:
                switch (peek(1)) {
                    case TelnetOption.REMOTE_FLOW_CONTROL /*33*/:
                    case JumpInfo.RESULT_ID_SET_ACTIVITY /*63*/:
                        return LEGACY;
                    case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
                        return 3;
                    default:
                        return 2;
                }
            default:
                return 4;
        }
    }

    private final String get(int pos) {
        return new String(this.txtBuf, pos, this.txtPos - pos);
    }

    private final void push(int c) {
        this.isWhitespace = (c <= 32 ? 1 : 0) & this.isWhitespace;
        if (this.txtPos == this.txtBuf.length) {
            char[] bigger = new char[(((this.txtPos * 4) / 3) + 4)];
            System.arraycopy(this.txtBuf, 0, bigger, 0, this.txtPos);
            this.txtBuf = bigger;
        }
        char[] cArr = this.txtBuf;
        int i = this.txtPos;
        this.txtPos = i + 1;
        cArr[i] = (char) c;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void parseStartTag(boolean r15) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
        r14 = this;
        r13 = 61;
        r12 = 1;
        r10 = 62;
        r11 = 0;
        if (r15 != 0) goto L_0x000b;
    L_0x0008:
        r14.read();
    L_0x000b:
        r8 = r14.readName();
        r14.name = r8;
        r14.attributeCount = r11;
    L_0x0013:
        r14.skip();
        r2 = r14.peek(r11);
        if (r15 == 0) goto L_0x0027;
    L_0x001c:
        r8 = 63;
        if (r2 != r8) goto L_0x009b;
    L_0x0020:
        r14.read();
        r14.read(r10);
    L_0x0026:
        return;
    L_0x0027:
        r8 = 47;
        if (r2 != r8) goto L_0x0093;
    L_0x002b:
        r14.degenerated = r12;
        r14.read();
        r14.skip();
        r14.read(r10);
    L_0x0036:
        r8 = r14.depth;
        r9 = r8 + 1;
        r14.depth = r9;
        r7 = r8 << 2;
        r8 = r14.elementStack;
        r9 = r7 + 4;
        r8 = r14.ensureCapacity(r8, r9);
        r14.elementStack = r8;
        r8 = r14.elementStack;
        r9 = r7 + 3;
        r10 = r14.name;
        r8[r9] = r10;
        r8 = r14.depth;
        r9 = r14.nspCounts;
        r9 = r9.length;
        if (r8 < r9) goto L_0x0067;
    L_0x0057:
        r8 = r14.depth;
        r8 = r8 + 4;
        r1 = new int[r8];
        r8 = r14.nspCounts;
        r9 = r14.nspCounts;
        r9 = r9.length;
        java.lang.System.arraycopy(r8, r11, r1, r11, r9);
        r14.nspCounts = r1;
    L_0x0067:
        r8 = r14.nspCounts;
        r9 = r14.depth;
        r10 = r14.nspCounts;
        r11 = r14.depth;
        r11 = r11 + -1;
        r10 = r10[r11];
        r8[r9] = r10;
        r8 = r14.processNsp;
        if (r8 == 0) goto L_0x013d;
    L_0x0079:
        r14.adjustNsp();
    L_0x007c:
        r8 = r14.elementStack;
        r9 = r14.namespace;
        r8[r7] = r9;
        r8 = r14.elementStack;
        r9 = r7 + 1;
        r10 = r14.prefix;
        r8[r9] = r10;
        r8 = r14.elementStack;
        r9 = r7 + 2;
        r10 = r14.name;
        r8[r9] = r10;
        goto L_0x0026;
    L_0x0093:
        if (r2 != r10) goto L_0x009b;
    L_0x0095:
        if (r15 != 0) goto L_0x009b;
    L_0x0097:
        r14.read();
        goto L_0x0036;
    L_0x009b:
        r8 = -1;
        if (r2 != r8) goto L_0x00a4;
    L_0x009e:
        r8 = "Unexpected EOF";
        r14.error(r8);
        goto L_0x0026;
    L_0x00a4:
        r0 = r14.readName();
        r8 = r0.length();
        if (r8 != 0) goto L_0x00b4;
    L_0x00ae:
        r8 = "attr name expected";
        r14.error(r8);
        goto L_0x0036;
    L_0x00b4:
        r8 = r14.attributeCount;
        r9 = r8 + 1;
        r14.attributeCount = r9;
        r4 = r8 << 2;
        r8 = r14.attributes;
        r9 = r4 + 4;
        r8 = r14.ensureCapacity(r8, r9);
        r14.attributes = r8;
        r8 = r14.attributes;
        r5 = r4 + 1;
        r9 = "";
        r8[r4] = r9;
        r8 = r14.attributes;
        r4 = r5 + 1;
        r9 = 0;
        r8[r5] = r9;
        r8 = r14.attributes;
        r5 = r4 + 1;
        r8[r4] = r0;
        r14.skip();
        r8 = r14.peek(r11);
        if (r8 == r13) goto L_0x0104;
    L_0x00e4:
        r8 = r14.relaxed;
        if (r8 != 0) goto L_0x00fe;
    L_0x00e8:
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "Attr.value missing f. ";
        r8 = r8.append(r9);
        r8 = r8.append(r0);
        r8 = r8.toString();
        r14.error(r8);
    L_0x00fe:
        r8 = r14.attributes;
        r8[r5] = r0;
        goto L_0x0013;
    L_0x0104:
        r14.read(r13);
        r14.skip();
        r3 = r14.peek(r11);
        r8 = 39;
        if (r3 == r8) goto L_0x0139;
    L_0x0112:
        r8 = 34;
        if (r3 == r8) goto L_0x0139;
    L_0x0116:
        r8 = r14.relaxed;
        if (r8 != 0) goto L_0x011f;
    L_0x011a:
        r8 = "attr value delimiter missing!";
        r14.error(r8);
    L_0x011f:
        r3 = 32;
    L_0x0121:
        r6 = r14.txtPos;
        r14.pushText(r3, r12);
        r8 = r14.attributes;
        r9 = r14.get(r6);
        r8[r5] = r9;
        r14.txtPos = r6;
        r8 = 32;
        if (r3 == r8) goto L_0x0013;
    L_0x0134:
        r14.read();
        goto L_0x0013;
    L_0x0139:
        r14.read();
        goto L_0x0121;
    L_0x013d:
        r8 = "";
        r14.namespace = r8;
        goto L_0x007c;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kxml2.io.KXmlParser.parseStartTag(boolean):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void pushEntity() throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
        r10 = this;
        r9 = 35;
        r5 = 1;
        r6 = 0;
        r7 = r10.read();
        r10.push(r7);
        r3 = r10.txtPos;
    L_0x000d:
        r0 = r10.peek(r6);
        r7 = 59;
        if (r0 != r7) goto L_0x0048;
    L_0x0015:
        r10.read();
        r1 = r10.get(r3);
        r7 = r3 + -1;
        r10.txtPos = r7;
        r7 = r10.token;
        if (r7 == 0) goto L_0x002b;
    L_0x0024:
        r7 = r10.type;
        r8 = 6;
        if (r7 != r8) goto L_0x002b;
    L_0x0029:
        r10.name = r1;
    L_0x002b:
        r7 = r1.charAt(r6);
        if (r7 != r9) goto L_0x00a8;
    L_0x0031:
        r6 = r1.charAt(r5);
        r7 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        if (r6 != r7) goto L_0x009f;
    L_0x0039:
        r5 = 2;
        r5 = r1.substring(r5);
        r6 = 16;
        r0 = java.lang.Integer.parseInt(r5, r6);
    L_0x0044:
        r10.push(r0);
    L_0x0047:
        return;
    L_0x0048:
        r7 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        if (r0 >= r7) goto L_0x0096;
    L_0x004c:
        r7 = 48;
        if (r0 < r7) goto L_0x0054;
    L_0x0050:
        r7 = 57;
        if (r0 <= r7) goto L_0x0096;
    L_0x0054:
        r7 = 97;
        if (r0 < r7) goto L_0x005c;
    L_0x0058:
        r7 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        if (r0 <= r7) goto L_0x0096;
    L_0x005c:
        r7 = 65;
        if (r0 < r7) goto L_0x0064;
    L_0x0060:
        r7 = 90;
        if (r0 <= r7) goto L_0x0096;
    L_0x0064:
        r7 = 95;
        if (r0 == r7) goto L_0x0096;
    L_0x0068:
        r7 = 45;
        if (r0 == r7) goto L_0x0096;
    L_0x006c:
        if (r0 == r9) goto L_0x0096;
    L_0x006e:
        r5 = r10.relaxed;
        if (r5 != 0) goto L_0x0077;
    L_0x0072:
        r5 = "unterminated entity ref";
        r10.error(r5);
    L_0x0077:
        r5 = java.lang.System.out;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "broken entitiy: ";
        r6 = r6.append(r7);
        r7 = r3 + -1;
        r7 = r10.get(r7);
        r6 = r6.append(r7);
        r6 = r6.toString();
        r5.println(r6);
        goto L_0x0047;
    L_0x0096:
        r7 = r10.read();
        r10.push(r7);
        goto L_0x000d;
    L_0x009f:
        r5 = r1.substring(r5);
        r0 = java.lang.Integer.parseInt(r5);
        goto L_0x0044;
    L_0x00a8:
        r7 = r10.entityMap;
        r4 = r7.get(r1);
        r4 = (java.lang.String) r4;
        if (r4 != 0) goto L_0x00da;
    L_0x00b2:
        r10.unresolved = r5;
        r5 = r10.unresolved;
        if (r5 == 0) goto L_0x00dc;
    L_0x00b8:
        r5 = r10.token;
        if (r5 != 0) goto L_0x0047;
    L_0x00bc:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "unresolved: &";
        r5 = r5.append(r6);
        r5 = r5.append(r1);
        r6 = ";";
        r5 = r5.append(r6);
        r5 = r5.toString();
        r10.error(r5);
        goto L_0x0047;
    L_0x00da:
        r5 = r6;
        goto L_0x00b2;
    L_0x00dc:
        r2 = 0;
    L_0x00dd:
        r5 = r4.length();
        if (r2 >= r5) goto L_0x0047;
    L_0x00e3:
        r5 = r4.charAt(r2);
        r10.push(r5);
        r2 = r2 + 1;
        goto L_0x00dd;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kxml2.io.KXmlParser.pushEntity():void");
    }

    private final void pushText(int delimiter, boolean resolveEntities) throws IOException, XmlPullParserException {
        int next = peek(0);
        int cbrCount = 0;
        while (next != -1 && next != delimiter) {
            if (delimiter != 32 || (next > 32 && next != 62)) {
                if (next == 38) {
                    if (resolveEntities) {
                        pushEntity();
                    } else {
                        return;
                    }
                } else if (next == 10 && this.type == 2) {
                    read();
                    push(32);
                } else {
                    push(read());
                }
                if (next == 62 && cbrCount >= 2 && delimiter != 93) {
                    error("Illegal: ]]>");
                }
                if (next == 93) {
                    cbrCount++;
                } else {
                    cbrCount = 0;
                }
                next = peek(0);
            } else {
                return;
            }
        }
    }

    private final void read(char c) throws IOException, XmlPullParserException {
        char a = read();
        if (a != c) {
            error("expected: '" + c + "' actual: '" + ((char) a) + "'");
        }
    }

    private final int read() throws IOException {
        int result;
        if (this.peekCount == 0) {
            result = peek(0);
        } else {
            result = this.peek[0];
            this.peek[0] = this.peek[1];
        }
        this.peekCount--;
        this.column++;
        if (result == 10) {
            this.line++;
            this.column = 1;
        }
        return result;
    }

    private final int peek(int pos) throws IOException {
        while (pos >= this.peekCount) {
            int nw;
            if (this.srcBuf.length <= 1) {
                nw = this.reader.read();
            } else if (this.srcPos < this.srcCount) {
                char[] cArr = this.srcBuf;
                int i = this.srcPos;
                this.srcPos = i + 1;
                nw = cArr[i];
            } else {
                this.srcCount = this.reader.read(this.srcBuf, 0, this.srcBuf.length);
                if (this.srcCount <= 0) {
                    nw = -1;
                } else {
                    nw = this.srcBuf[0];
                }
                this.srcPos = 1;
            }
            int[] iArr;
            if (nw == 13) {
                this.wasCR = true;
                iArr = this.peek;
                i = this.peekCount;
                this.peekCount = i + 1;
                iArr[i] = 10;
            } else {
                if (nw != 10) {
                    iArr = this.peek;
                    i = this.peekCount;
                    this.peekCount = i + 1;
                    iArr[i] = nw;
                } else if (!this.wasCR) {
                    iArr = this.peek;
                    i = this.peekCount;
                    this.peekCount = i + 1;
                    iArr[i] = 10;
                }
                this.wasCR = false;
            }
        }
        return this.peek[pos];
    }

    private final String readName() throws IOException, XmlPullParserException {
        int pos = this.txtPos;
        int c = peek(0);
        if ((c < 97 || c > 122) && !((c >= 65 && c <= 90) || c == 95 || c == 58 || c >= Wbxml.EXT_0 || this.relaxed)) {
            error("name expected");
        }
        while (true) {
            push(read());
            c = peek(0);
            if ((c < 97 || c > 122) && ((c < 65 || c > 90) && !((c >= 48 && c <= 57) || c == 95 || c == 45 || c == 58 || c == 46 || c >= 183))) {
                String result = get(pos);
                this.txtPos = pos;
                return result;
            }
        }
    }

    private final void skip() throws IOException {
        while (true) {
            int c = peek(0);
            if (c <= 32 && c != -1) {
                read();
            } else {
                return;
            }
        }
    }

    public void setInput(Reader reader) throws XmlPullParserException {
        this.reader = reader;
        this.line = 1;
        this.column = 0;
        this.type = 0;
        this.name = null;
        this.namespace = null;
        this.degenerated = false;
        this.attributeCount = -1;
        this.encoding = null;
        this.version = null;
        this.standalone = null;
        if (reader != null) {
            this.srcPos = 0;
            this.srcCount = 0;
            this.peekCount = 0;
            this.depth = 0;
            this.entityMap = new Hashtable();
            this.entityMap.put("amp", "&");
            this.entityMap.put("apos", "'");
            this.entityMap.put("gt", ">");
            this.entityMap.put("lt", "<");
            this.entityMap.put("quot", "\"");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setInput(java.io.InputStream r18, java.lang.String r19) throws org.xmlpull.v1.XmlPullParserException {
        /*
        r17 = this;
        r12 = 0;
        r0 = r17;
        r0.srcPos = r12;
        r12 = 0;
        r0 = r17;
        r0.srcCount = r12;
        r5 = r19;
        if (r18 != 0) goto L_0x0014;
    L_0x000e:
        r12 = new java.lang.IllegalArgumentException;
        r12.<init>();
        throw r12;
    L_0x0014:
        if (r5 != 0) goto L_0x0057;
    L_0x0016:
        r2 = 0;
    L_0x0017:
        r0 = r17;
        r12 = r0.srcCount;	 Catch:{ Exception -> 0x008c }
        r13 = 4;
        if (r12 >= r13) goto L_0x0025;
    L_0x001e:
        r6 = r18.read();	 Catch:{ Exception -> 0x008c }
        r12 = -1;
        if (r6 != r12) goto L_0x0076;
    L_0x0025:
        r0 = r17;
        r12 = r0.srcCount;	 Catch:{ Exception -> 0x008c }
        r13 = 4;
        if (r12 != r13) goto L_0x0057;
    L_0x002c:
        switch(r2) {
            case -131072: goto L_0x00b4;
            case 60: goto L_0x00bc;
            case 65279: goto L_0x00ac;
            case 3932223: goto L_0x00df;
            case 1006632960: goto L_0x00cd;
            case 1006649088: goto L_0x00fa;
            case 1010792557: goto L_0x0115;
            default: goto L_0x002f;
        };	 Catch:{ Exception -> 0x008c }
    L_0x002f:
        r12 = -65536; // 0xffffffffffff0000 float:NaN double:NaN;
        r12 = r12 & r2;
        r13 = -16842752; // 0xfffffffffeff0000 float:-1.6947657E38 double:NaN;
        if (r12 != r13) goto L_0x016d;
    L_0x0036:
        r5 = "UTF-16BE";
        r0 = r17;
        r12 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r13 = 0;
        r0 = r17;
        r14 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r15 = 2;
        r14 = r14[r15];	 Catch:{ Exception -> 0x008c }
        r14 = r14 << 8;
        r0 = r17;
        r15 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r16 = 3;
        r15 = r15[r16];	 Catch:{ Exception -> 0x008c }
        r14 = r14 | r15;
        r14 = (char) r14;	 Catch:{ Exception -> 0x008c }
        r12[r13] = r14;	 Catch:{ Exception -> 0x008c }
        r12 = 1;
        r0 = r17;
        r0.srcCount = r12;	 Catch:{ Exception -> 0x008c }
    L_0x0057:
        if (r5 != 0) goto L_0x005b;
    L_0x0059:
        r5 = "UTF-8";
    L_0x005b:
        r0 = r17;
        r11 = r0.srcCount;	 Catch:{ Exception -> 0x008c }
        r12 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x008c }
        r0 = r18;
        r12.<init>(r0, r5);	 Catch:{ Exception -> 0x008c }
        r0 = r17;
        r0.setInput(r12);	 Catch:{ Exception -> 0x008c }
        r0 = r19;
        r1 = r17;
        r1.encoding = r0;	 Catch:{ Exception -> 0x008c }
        r0 = r17;
        r0.srcCount = r11;	 Catch:{ Exception -> 0x008c }
        return;
    L_0x0076:
        r12 = r2 << 8;
        r2 = r12 | r6;
        r0 = r17;
        r12 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r0 = r17;
        r13 = r0.srcCount;	 Catch:{ Exception -> 0x008c }
        r14 = r13 + 1;
        r0 = r17;
        r0.srcCount = r14;	 Catch:{ Exception -> 0x008c }
        r14 = (char) r6;	 Catch:{ Exception -> 0x008c }
        r12[r13] = r14;	 Catch:{ Exception -> 0x008c }
        goto L_0x0017;
    L_0x008c:
        r4 = move-exception;
        r12 = new org.xmlpull.v1.XmlPullParserException;
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "Invalid stream or encoding: ";
        r13 = r13.append(r14);
        r14 = r4.toString();
        r13 = r13.append(r14);
        r13 = r13.toString();
        r0 = r17;
        r12.<init>(r13, r0, r4);
        throw r12;
    L_0x00ac:
        r5 = "UTF-32BE";
        r12 = 0;
        r0 = r17;
        r0.srcCount = r12;	 Catch:{ Exception -> 0x008c }
        goto L_0x0057;
    L_0x00b4:
        r5 = "UTF-32LE";
        r12 = 0;
        r0 = r17;
        r0.srcCount = r12;	 Catch:{ Exception -> 0x008c }
        goto L_0x0057;
    L_0x00bc:
        r5 = "UTF-32BE";
        r0 = r17;
        r12 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r13 = 0;
        r14 = 60;
        r12[r13] = r14;	 Catch:{ Exception -> 0x008c }
        r12 = 1;
        r0 = r17;
        r0.srcCount = r12;	 Catch:{ Exception -> 0x008c }
        goto L_0x0057;
    L_0x00cd:
        r5 = "UTF-32LE";
        r0 = r17;
        r12 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r13 = 0;
        r14 = 60;
        r12[r13] = r14;	 Catch:{ Exception -> 0x008c }
        r12 = 1;
        r0 = r17;
        r0.srcCount = r12;	 Catch:{ Exception -> 0x008c }
        goto L_0x0057;
    L_0x00df:
        r5 = "UTF-16BE";
        r0 = r17;
        r12 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r13 = 0;
        r14 = 60;
        r12[r13] = r14;	 Catch:{ Exception -> 0x008c }
        r0 = r17;
        r12 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r13 = 1;
        r14 = 63;
        r12[r13] = r14;	 Catch:{ Exception -> 0x008c }
        r12 = 2;
        r0 = r17;
        r0.srcCount = r12;	 Catch:{ Exception -> 0x008c }
        goto L_0x0057;
    L_0x00fa:
        r5 = "UTF-16LE";
        r0 = r17;
        r12 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r13 = 0;
        r14 = 60;
        r12[r13] = r14;	 Catch:{ Exception -> 0x008c }
        r0 = r17;
        r12 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r13 = 1;
        r14 = 63;
        r12[r13] = r14;	 Catch:{ Exception -> 0x008c }
        r12 = 2;
        r0 = r17;
        r0.srcCount = r12;	 Catch:{ Exception -> 0x008c }
        goto L_0x0057;
    L_0x0115:
        r6 = r18.read();	 Catch:{ Exception -> 0x008c }
        r12 = -1;
        if (r6 == r12) goto L_0x002f;
    L_0x011c:
        r0 = r17;
        r12 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r0 = r17;
        r13 = r0.srcCount;	 Catch:{ Exception -> 0x008c }
        r14 = r13 + 1;
        r0 = r17;
        r0.srcCount = r14;	 Catch:{ Exception -> 0x008c }
        r14 = (char) r6;	 Catch:{ Exception -> 0x008c }
        r12[r13] = r14;	 Catch:{ Exception -> 0x008c }
        r12 = 62;
        if (r6 != r12) goto L_0x0115;
    L_0x0131:
        r10 = new java.lang.String;	 Catch:{ Exception -> 0x008c }
        r0 = r17;
        r12 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r13 = 0;
        r0 = r17;
        r14 = r0.srcCount;	 Catch:{ Exception -> 0x008c }
        r10.<init>(r12, r13, r14);	 Catch:{ Exception -> 0x008c }
        r12 = "encoding";
        r7 = r10.indexOf(r12);	 Catch:{ Exception -> 0x008c }
        r12 = -1;
        if (r7 == r12) goto L_0x002f;
    L_0x0148:
        r8 = r7;
    L_0x0149:
        r12 = r10.charAt(r8);	 Catch:{ Exception -> 0x008c }
        r13 = 34;
        if (r12 == r13) goto L_0x015d;
    L_0x0151:
        r12 = r10.charAt(r8);	 Catch:{ Exception -> 0x008c }
        r13 = 39;
        if (r12 == r13) goto L_0x015d;
    L_0x0159:
        r7 = r8 + 1;
        r8 = r7;
        goto L_0x0149;
    L_0x015d:
        r7 = r8 + 1;
        r3 = r10.charAt(r8);	 Catch:{ Exception -> 0x008c }
        r9 = r10.indexOf(r3, r7);	 Catch:{ Exception -> 0x008c }
        r5 = r10.substring(r7, r9);	 Catch:{ Exception -> 0x008c }
        goto L_0x002f;
    L_0x016d:
        r12 = -65536; // 0xffffffffffff0000 float:NaN double:NaN;
        r12 = r12 & r2;
        r13 = -131072; // 0xfffffffffffe0000 float:NaN double:NaN;
        if (r12 != r13) goto L_0x0197;
    L_0x0174:
        r5 = "UTF-16LE";
        r0 = r17;
        r12 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r13 = 0;
        r0 = r17;
        r14 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r15 = 3;
        r14 = r14[r15];	 Catch:{ Exception -> 0x008c }
        r14 = r14 << 8;
        r0 = r17;
        r15 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r16 = 2;
        r15 = r15[r16];	 Catch:{ Exception -> 0x008c }
        r14 = r14 | r15;
        r14 = (char) r14;	 Catch:{ Exception -> 0x008c }
        r12[r13] = r14;	 Catch:{ Exception -> 0x008c }
        r12 = 1;
        r0 = r17;
        r0.srcCount = r12;	 Catch:{ Exception -> 0x008c }
        goto L_0x0057;
    L_0x0197:
        r12 = r2 & -256;
        r13 = -272908544; // 0xffffffffefbbbf00 float:-1.162092E29 double:NaN;
        if (r12 != r13) goto L_0x0057;
    L_0x019e:
        r5 = "UTF-8";
        r0 = r17;
        r12 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r13 = 0;
        r0 = r17;
        r14 = r0.srcBuf;	 Catch:{ Exception -> 0x008c }
        r15 = 3;
        r14 = r14[r15];	 Catch:{ Exception -> 0x008c }
        r12[r13] = r14;	 Catch:{ Exception -> 0x008c }
        r12 = 1;
        r0 = r17;
        r0.srcCount = r12;	 Catch:{ Exception -> 0x008c }
        goto L_0x0057;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kxml2.io.KXmlParser.setInput(java.io.InputStream, java.lang.String):void");
    }

    public boolean getFeature(String feature) {
        if (XmlPullParser.FEATURE_PROCESS_NAMESPACES.equals(feature)) {
            return this.processNsp;
        }
        if (isProp(feature, false, "relaxed")) {
            return this.relaxed;
        }
        return false;
    }

    public String getInputEncoding() {
        return this.encoding;
    }

    public void defineEntityReplacementText(String entity, String value) throws XmlPullParserException {
        if (this.entityMap == null) {
            throw new RuntimeException("entity replacement text must be defined after setInput!");
        }
        this.entityMap.put(entity, value);
    }

    public Object getProperty(String property) {
        if (isProp(property, true, "xmldecl-version")) {
            return this.version;
        }
        if (isProp(property, true, "xmldecl-standalone")) {
            return this.standalone;
        }
        if (isProp(property, true, "location")) {
            return this.location != null ? this.location : this.reader.toString();
        } else {
            return null;
        }
    }

    public int getNamespaceCount(int depth) {
        if (depth <= this.depth) {
            return this.nspCounts[depth];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getNamespacePrefix(int pos) {
        return this.nspStack[pos << 1];
    }

    public String getNamespaceUri(int pos) {
        return this.nspStack[(pos << 1) + 1];
    }

    public String getNamespace(String prefix) {
        if ("xml".equals(prefix)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        if ("xmlns".equals(prefix)) {
            return "http://www.w3.org/2000/xmlns/";
        }
        for (int i = (getNamespaceCount(this.depth) << 1) - 2; i >= 0; i -= 2) {
            if (prefix == null) {
                if (this.nspStack[i] == null) {
                    return this.nspStack[i + 1];
                }
            } else if (prefix.equals(this.nspStack[i])) {
                return this.nspStack[i + 1];
            }
        }
        return null;
    }

    public int getDepth() {
        return this.depth;
    }

    public String getPositionDescription() {
        StringBuffer buf = new StringBuffer(this.type < TYPES.length ? TYPES[this.type] : EnvironmentCompat.MEDIA_UNKNOWN);
        buf.append(' ');
        if (this.type == 2 || this.type == 3) {
            if (this.degenerated) {
                buf.append("(empty) ");
            }
            buf.append('<');
            if (this.type == 3) {
                buf.append('/');
            }
            if (this.prefix != null) {
                buf.append("{" + this.namespace + "}" + this.prefix + ":");
            }
            buf.append(this.name);
            int cnt = this.attributeCount << 2;
            for (int i = 0; i < cnt; i += 4) {
                buf.append(' ');
                if (this.attributes[i + 1] != null) {
                    buf.append("{" + this.attributes[i] + "}" + this.attributes[i + 1] + ":");
                }
                buf.append(this.attributes[i + 2] + "='" + this.attributes[i + 3] + "'");
            }
            buf.append('>');
        } else if (this.type != 7) {
            if (this.type != 4) {
                buf.append(getText());
            } else if (this.isWhitespace) {
                buf.append("(whitespace)");
            } else {
                String text = getText();
                if (text.length() > 16) {
                    text = text.substring(0, 16) + "...";
                }
                buf.append(text);
            }
        }
        buf.append("@" + this.line + ":" + this.column);
        if (this.location != null) {
            buf.append(" in ");
            buf.append(this.location);
        } else if (this.reader != null) {
            buf.append(" in ");
            buf.append(this.reader.toString());
        }
        return buf.toString();
    }

    public int getLineNumber() {
        return this.line;
    }

    public int getColumnNumber() {
        return this.column;
    }

    public boolean isWhitespace() throws XmlPullParserException {
        if (!(this.type == 4 || this.type == 7 || this.type == 5)) {
            exception(ILLEGAL_TYPE);
        }
        return this.isWhitespace;
    }

    public String getText() {
        return (this.type < 4 || (this.type == 6 && this.unresolved)) ? null : get(0);
    }

    public char[] getTextCharacters(int[] poslen) {
        if (this.type < 4) {
            poslen[0] = -1;
            poslen[1] = -1;
            return null;
        } else if (this.type == 6) {
            poslen[0] = 0;
            poslen[1] = this.name.length();
            return this.name.toCharArray();
        } else {
            poslen[0] = 0;
            poslen[1] = this.txtPos;
            return this.txtBuf;
        }
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getName() {
        return this.name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public boolean isEmptyElementTag() throws XmlPullParserException {
        if (this.type != 2) {
            exception(ILLEGAL_TYPE);
        }
        return this.degenerated;
    }

    public int getAttributeCount() {
        return this.attributeCount;
    }

    public String getAttributeType(int index) {
        return "CDATA";
    }

    public boolean isAttributeDefault(int index) {
        return false;
    }

    public String getAttributeNamespace(int index) {
        if (index < this.attributeCount) {
            return this.attributes[index << 2];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getAttributeName(int index) {
        if (index < this.attributeCount) {
            return this.attributes[(index << 2) + 2];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getAttributePrefix(int index) {
        if (index < this.attributeCount) {
            return this.attributes[(index << 2) + 1];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getAttributeValue(int index) {
        if (index < this.attributeCount) {
            return this.attributes[(index << 2) + 3];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getAttributeValue(String namespace, String name) {
        int i = (this.attributeCount << 2) - 4;
        while (i >= 0) {
            if (this.attributes[i + 2].equals(name) && (namespace == null || this.attributes[i].equals(namespace))) {
                return this.attributes[i + 3];
            }
            i -= 4;
        }
        return null;
    }

    public int getEventType() throws XmlPullParserException {
        return this.type;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int next() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
        r4 = this;
        r3 = 0;
        r2 = 4;
        r4.txtPos = r3;
        r1 = 1;
        r4.isWhitespace = r1;
        r0 = 9999; // 0x270f float:1.4012E-41 double:4.94E-320;
        r4.token = r3;
    L_0x000b:
        r4.nextImpl();
        r1 = r4.type;
        if (r1 >= r0) goto L_0x0014;
    L_0x0012:
        r0 = r4.type;
    L_0x0014:
        r1 = 6;
        if (r0 > r1) goto L_0x000b;
    L_0x0017:
        if (r0 < r2) goto L_0x001f;
    L_0x0019:
        r1 = r4.peekType();
        if (r1 >= r2) goto L_0x000b;
    L_0x001f:
        r4.type = r0;
        r1 = r4.type;
        if (r1 <= r2) goto L_0x0027;
    L_0x0025:
        r4.type = r2;
    L_0x0027:
        r1 = r4.type;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kxml2.io.KXmlParser.next():int");
    }

    public int nextToken() throws XmlPullParserException, IOException {
        this.isWhitespace = true;
        this.txtPos = 0;
        this.token = true;
        nextImpl();
        return this.type;
    }

    public int nextTag() throws XmlPullParserException, IOException {
        next();
        if (this.type == 4 && this.isWhitespace) {
            next();
        }
        if (!(this.type == 3 || this.type == 2)) {
            exception("unexpected type");
        }
        return this.type;
    }

    public void require(int type, String namespace, String name) throws XmlPullParserException, IOException {
        if (type != this.type || ((namespace != null && !namespace.equals(getNamespace())) || (name != null && !name.equals(getName())))) {
            exception("expected: " + TYPES[type] + " {" + namespace + "}" + name);
        }
    }

    public String nextText() throws XmlPullParserException, IOException {
        String result;
        if (this.type != 2) {
            exception("precondition: START_TAG");
        }
        next();
        if (this.type == 4) {
            result = getText();
            next();
        } else {
            result = XmlPullParser.NO_NAMESPACE;
        }
        if (this.type != 3) {
            exception("END_TAG expected");
        }
        return result;
    }

    public void setFeature(String feature, boolean value) throws XmlPullParserException {
        if (XmlPullParser.FEATURE_PROCESS_NAMESPACES.equals(feature)) {
            this.processNsp = value;
        } else if (isProp(feature, false, "relaxed")) {
            this.relaxed = value;
        } else {
            exception("unsupported feature: " + feature);
        }
    }

    public void setProperty(String property, Object value) throws XmlPullParserException {
        if (isProp(property, true, "location")) {
            this.location = value;
            return;
        }
        throw new XmlPullParserException("unsupported property: " + property);
    }

    public void skipSubTree() throws XmlPullParserException, IOException {
        require(2, null, null);
        int level = 1;
        while (level > 0) {
            int eventType = next();
            if (eventType == 3) {
                level--;
            } else if (eventType == 2) {
                level++;
            }
        }
    }
}
