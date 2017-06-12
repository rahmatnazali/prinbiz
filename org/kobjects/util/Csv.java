package org.kobjects.util;

public class Csv {
    public static String encode(String value, char quote) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == quote || c == '^') {
                buf.append(c);
                buf.append(c);
            } else if (c < ' ') {
                buf.append('^');
                buf.append((char) (c + 64));
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    public static String encode(Object[] values) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < values.length; i++) {
            if (i != 0) {
                buf.append(',');
            }
            Object v = values[i];
            if ((v instanceof Number) || (v instanceof Boolean)) {
                buf.append(v.toString());
            } else {
                buf.append('\"');
                buf.append(encode(v.toString(), '\"'));
                buf.append('\"');
            }
        }
        return buf.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String[] decode(java.lang.String r15) {
        /*
        r14 = 94;
        r13 = 44;
        r12 = 32;
        r11 = 34;
        r9 = new java.util.Vector;
        r9.<init>();
        r5 = 0;
        r4 = r15.length();
    L_0x0012:
        if (r5 >= r4) goto L_0x001d;
    L_0x0014:
        r10 = r15.charAt(r5);
        if (r10 > r12) goto L_0x001d;
    L_0x001a:
        r5 = r5 + 1;
        goto L_0x0012;
    L_0x001d:
        if (r5 < r4) goto L_0x0034;
    L_0x001f:
        r10 = r9.size();
        r8 = new java.lang.String[r10];
        r3 = 0;
    L_0x0026:
        r10 = r8.length;
        if (r3 >= r10) goto L_0x00d0;
    L_0x0029:
        r10 = r9.elementAt(r3);
        r10 = (java.lang.String) r10;
        r8[r3] = r10;
        r3 = r3 + 1;
        goto L_0x0026;
    L_0x0034:
        r10 = r15.charAt(r5);
        if (r10 != r11) goto L_0x00ad;
    L_0x003a:
        r5 = r5 + 1;
        r0 = new java.lang.StringBuffer;
        r0.<init>();
    L_0x0041:
        r6 = r5 + 1;
        r1 = r15.charAt(r5);
        if (r1 != r14) goto L_0x005b;
    L_0x0049:
        if (r6 >= r4) goto L_0x005b;
    L_0x004b:
        r5 = r6 + 1;
        r2 = r15.charAt(r6);
        if (r2 != r14) goto L_0x0057;
    L_0x0053:
        r0.append(r2);
        goto L_0x0041;
    L_0x0057:
        r10 = r2 + -64;
        r2 = (char) r10;
        goto L_0x0053;
    L_0x005b:
        if (r1 != r11) goto L_0x00d1;
    L_0x005d:
        if (r6 == r4) goto L_0x0065;
    L_0x005f:
        r10 = r15.charAt(r6);
        if (r10 == r11) goto L_0x0078;
    L_0x0065:
        r10 = r0.toString();
        r9.addElement(r10);
        r5 = r6;
    L_0x006d:
        if (r5 >= r4) goto L_0x007e;
    L_0x006f:
        r10 = r15.charAt(r5);
        if (r10 > r12) goto L_0x007e;
    L_0x0075:
        r5 = r5 + 1;
        goto L_0x006d;
    L_0x0078:
        r5 = r6 + 1;
    L_0x007a:
        r0.append(r1);
        goto L_0x0041;
    L_0x007e:
        if (r5 >= r4) goto L_0x001f;
    L_0x0080:
        r10 = r15.charAt(r5);
        if (r10 == r13) goto L_0x00a9;
    L_0x0086:
        r10 = new java.lang.RuntimeException;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "Comma expected at ";
        r11 = r11.append(r12);
        r11 = r11.append(r5);
        r12 = " line: ";
        r11 = r11.append(r12);
        r11 = r11.append(r15);
        r11 = r11.toString();
        r10.<init>(r11);
        throw r10;
    L_0x00a9:
        r5 = r5 + 1;
        goto L_0x0012;
    L_0x00ad:
        r7 = r15.indexOf(r13, r5);
        r10 = -1;
        if (r7 != r10) goto L_0x00c1;
    L_0x00b4:
        r10 = r15.substring(r5);
        r10 = r10.trim();
        r9.addElement(r10);
        goto L_0x001f;
    L_0x00c1:
        r10 = r15.substring(r5, r7);
        r10 = r10.trim();
        r9.addElement(r10);
        r5 = r7 + 1;
        goto L_0x0012;
    L_0x00d0:
        return r8;
    L_0x00d1:
        r5 = r6;
        goto L_0x007a;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kobjects.util.Csv.decode(java.lang.String):java.lang.String[]");
    }
}
