package org.kobjects.mime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.jmdns.impl.constants.DNSConstants;

public class Decoder {
    String boundary;
    char[] buf;
    String characterEncoding;
    boolean consumed;
    boolean eof;
    Hashtable header;
    InputStream is;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.String readLine() throws java.io.IOException {
        /*
        r8 = this;
        r7 = -1;
        r6 = 0;
        r0 = 0;
    L_0x0003:
        r4 = r8.is;
        r2 = r4.read();
        if (r2 != r7) goto L_0x000f;
    L_0x000b:
        if (r0 != 0) goto L_0x000f;
    L_0x000d:
        r4 = 0;
    L_0x000e:
        return r4;
    L_0x000f:
        if (r2 == r7) goto L_0x0015;
    L_0x0011:
        r4 = 10;
        if (r2 != r4) goto L_0x001d;
    L_0x0015:
        r4 = new java.lang.String;
        r5 = r8.buf;
        r4.<init>(r5, r6, r0);
        goto L_0x000e;
    L_0x001d:
        r4 = 13;
        if (r2 == r4) goto L_0x0003;
    L_0x0021:
        r4 = r8.buf;
        r4 = r4.length;
        if (r0 < r4) goto L_0x0039;
    L_0x0026:
        r4 = r8.buf;
        r4 = r4.length;
        r4 = r4 * 3;
        r4 = r4 / 2;
        r3 = new char[r4];
        r4 = r8.buf;
        r5 = r8.buf;
        r5 = r5.length;
        java.lang.System.arraycopy(r4, r6, r3, r6, r5);
        r8.buf = r3;
    L_0x0039:
        r4 = r8.buf;
        r1 = r0 + 1;
        r5 = (char) r2;
        r4[r0] = r5;
        r0 = r1;
        goto L_0x0003;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kobjects.mime.Decoder.readLine():java.lang.String");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Hashtable getHeaderElements(java.lang.String r10) {
        /*
        r9 = 59;
        r8 = 34;
        r7 = -1;
        r1 = "";
        r3 = 0;
        r4 = new java.util.Hashtable;
        r4.<init>();
        r2 = r10.length();
    L_0x0011:
        if (r3 >= r2) goto L_0x001e;
    L_0x0013:
        r5 = r10.charAt(r3);
        r6 = 32;
        if (r5 > r6) goto L_0x001e;
    L_0x001b:
        r3 = r3 + 1;
        goto L_0x0011;
    L_0x001e:
        if (r3 < r2) goto L_0x0021;
    L_0x0020:
        return r4;
    L_0x0021:
        r5 = r10.charAt(r3);
        if (r5 != r8) goto L_0x0074;
    L_0x0027:
        r3 = r3 + 1;
        r0 = r10.indexOf(r8, r3);
        if (r0 != r7) goto L_0x0048;
    L_0x002f:
        r5 = new java.lang.RuntimeException;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "End quote expected in ";
        r6 = r6.append(r7);
        r6 = r6.append(r10);
        r6 = r6.toString();
        r5.<init>(r6);
        throw r5;
    L_0x0048:
        r5 = r10.substring(r3, r0);
        r4.put(r1, r5);
        r3 = r0 + 2;
        if (r3 >= r2) goto L_0x0020;
    L_0x0053:
        r5 = r3 + -1;
        r5 = r10.charAt(r5);
        if (r5 == r9) goto L_0x008b;
    L_0x005b:
        r5 = new java.lang.RuntimeException;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "; expected in ";
        r6 = r6.append(r7);
        r6 = r6.append(r10);
        r6 = r6.toString();
        r5.<init>(r6);
        throw r5;
    L_0x0074:
        r0 = r10.indexOf(r9, r3);
        if (r0 != r7) goto L_0x0082;
    L_0x007a:
        r5 = r10.substring(r3);
        r4.put(r1, r5);
        goto L_0x0020;
    L_0x0082:
        r5 = r10.substring(r3, r0);
        r4.put(r1, r5);
        r3 = r0 + 1;
    L_0x008b:
        r5 = 61;
        r0 = r10.indexOf(r5, r3);
        if (r0 == r7) goto L_0x0020;
    L_0x0093:
        r5 = r10.substring(r3, r0);
        r5 = r5.toLowerCase();
        r1 = r5.trim();
        r3 = r0 + 1;
        goto L_0x0011;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kobjects.mime.Decoder.getHeaderElements(java.lang.String):java.util.Hashtable");
    }

    public Decoder(InputStream is, String _bound) throws IOException {
        this(is, _bound, null);
    }

    public Decoder(InputStream is, String _bound, String characterEncoding) throws IOException {
        this.buf = new char[DNSConstants.FLAGS_RD];
        this.characterEncoding = characterEncoding;
        this.is = is;
        this.boundary = "--" + _bound;
        String line;
        do {
            line = readLine();
            if (line == null) {
                throw new IOException("Unexpected EOF");
            }
        } while (!line.startsWith(this.boundary));
        if (line.endsWith("--")) {
            this.eof = true;
            is.close();
        }
        this.consumed = true;
    }

    public Enumeration getHeaderNames() {
        return this.header.keys();
    }

    public String getHeader(String key) {
        return (String) this.header.get(key.toLowerCase());
    }

    public String readContent() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        readContent(bos);
        String result = this.characterEncoding == null ? new String(bos.toByteArray()) : new String(bos.toByteArray(), this.characterEncoding);
        System.out.println("Field content: '" + result + "'");
        return result;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void readContent(java.io.OutputStream r12) throws java.io.IOException {
        /*
        r11 = this;
        r8 = 0;
        r7 = 1;
        r9 = r11.consumed;
        if (r9 == 0) goto L_0x000e;
    L_0x0006:
        r7 = new java.lang.RuntimeException;
        r8 = "Content already consumed!";
        r7.<init>(r8);
        throw r7;
    L_0x000e:
        r5 = "";
        r9 = "Content-Type";
        r1 = r11.getHeader(r9);
        r9 = "base64";
        r10 = "Content-Transfer-Encoding";
        r10 = r11.getHeader(r10);
        r9 = r9.equals(r10);
        if (r9 == 0) goto L_0x0050;
    L_0x0024:
        r0 = new java.io.ByteArrayOutputStream;
        r0.<init>();
    L_0x0029:
        r5 = r11.readLine();
        if (r5 != 0) goto L_0x0037;
    L_0x002f:
        r7 = new java.io.IOException;
        r8 = "Unexpected EOF";
        r7.<init>(r8);
        throw r7;
    L_0x0037:
        r8 = r11.boundary;
        r8 = r5.startsWith(r8);
        if (r8 == 0) goto L_0x004c;
    L_0x003f:
        r8 = "--";
        r8 = r5.endsWith(r8);
        if (r8 == 0) goto L_0x0049;
    L_0x0047:
        r11.eof = r7;
    L_0x0049:
        r11.consumed = r7;
        return;
    L_0x004c:
        org.kobjects.base64.Base64.decode(r5, r12);
        goto L_0x0029;
    L_0x0050:
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "\r\n";
        r9 = r9.append(r10);
        r10 = r11.boundary;
        r9 = r9.append(r10);
        r2 = r9.toString();
        r6 = 0;
    L_0x0066:
        r9 = r11.is;
        r3 = r9.read();
        r9 = -1;
        if (r3 != r9) goto L_0x0077;
    L_0x006f:
        r7 = new java.lang.RuntimeException;
        r8 = "Unexpected EOF";
        r7.<init>(r8);
        throw r7;
    L_0x0077:
        r9 = (char) r3;
        r10 = r2.charAt(r6);
        if (r9 != r10) goto L_0x008b;
    L_0x007e:
        r6 = r6 + 1;
        r9 = r2.length();
        if (r6 != r9) goto L_0x0066;
    L_0x0086:
        r5 = r11.readLine();
        goto L_0x003f;
    L_0x008b:
        if (r6 <= 0) goto L_0x00a3;
    L_0x008d:
        r4 = 0;
    L_0x008e:
        if (r4 >= r6) goto L_0x009b;
    L_0x0090:
        r9 = r2.charAt(r4);
        r9 = (byte) r9;
        r12.write(r9);
        r4 = r4 + 1;
        goto L_0x008e;
    L_0x009b:
        r9 = (char) r3;
        r10 = r2.charAt(r8);
        if (r9 != r10) goto L_0x00aa;
    L_0x00a2:
        r6 = r7;
    L_0x00a3:
        if (r6 != 0) goto L_0x0066;
    L_0x00a5:
        r9 = (byte) r3;
        r12.write(r9);
        goto L_0x0066;
    L_0x00aa:
        r6 = r8;
        goto L_0x00a3;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kobjects.mime.Decoder.readContent(java.io.OutputStream):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean next() throws java.io.IOException {
        /*
        r6 = this;
        r2 = 0;
        r3 = r6.consumed;
        if (r3 != 0) goto L_0x0009;
    L_0x0005:
        r3 = 0;
        r6.readContent(r3);
    L_0x0009:
        r3 = r6.eof;
        if (r3 == 0) goto L_0x000e;
    L_0x000d:
        return r2;
    L_0x000e:
        r3 = new java.util.Hashtable;
        r3.<init>();
        r6.header = r3;
    L_0x0015:
        r1 = r6.readLine();
        if (r1 == 0) goto L_0x0023;
    L_0x001b:
        r3 = "";
        r3 = r1.equals(r3);
        if (r3 == 0) goto L_0x0027;
    L_0x0023:
        r6.consumed = r2;
        r2 = 1;
        goto L_0x000d;
    L_0x0027:
        r3 = 58;
        r0 = r1.indexOf(r3);
        r3 = -1;
        if (r0 != r3) goto L_0x0049;
    L_0x0030:
        r2 = new java.io.IOException;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "colon missing in multipart header line: ";
        r3 = r3.append(r4);
        r3 = r3.append(r1);
        r3 = r3.toString();
        r2.<init>(r3);
        throw r2;
    L_0x0049:
        r3 = r6.header;
        r4 = r1.substring(r2, r0);
        r4 = r4.trim();
        r4 = r4.toLowerCase();
        r5 = r0 + 1;
        r5 = r1.substring(r5);
        r5 = r5.trim();
        r3.put(r4, r5);
        goto L_0x0015;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kobjects.mime.Decoder.next():boolean");
    }
}
