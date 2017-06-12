package com.flurry.sdk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.telnet.TelnetCommand;
import org.apache.commons.net.telnet.TelnetOption;
import org.xmlpull.v1.XmlPullParser;

public final class lr {
    private static final String f417a;

    static {
        f417a = lr.class.getSimpleName();
    }

    public static void m310a() {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Must be called from the main thread!");
        }
    }

    public static void m318b() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            throw new IllegalStateException("Must be called from a background thread!");
        }
    }

    public static String m308a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        Uri parse = Uri.parse(str);
        if (parse == null || parse.getScheme() != null) {
            return str;
        }
        return "http://" + str;
    }

    public static String m316b(String str) {
        if (str == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        if (str.length() > TelnetOption.MAX_OPTION_VALUE) {
            return str.substring(0, TelnetOption.MAX_OPTION_VALUE);
        }
        return str;
    }

    public static String m319c(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            kf.m182a(5, f417a, "Cannot encode '" + str + "'");
            return XmlPullParser.NO_NAMESPACE;
        }
    }

    public static String m320d(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            kf.m182a(5, f417a, "Cannot decode '" + str + "'");
            return XmlPullParser.NO_NAMESPACE;
        }
    }

    public static void m311a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
            }
        }
    }

    public static byte[] m321e(String str) {
        byte[] bArr = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                bArr = str.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                kf.m182a(5, f417a, "Unsupported UTF-8: " + e.getMessage());
            }
        }
        return bArr;
    }

    public static byte[] m322f(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(str.getBytes(), 0, str.length());
            return instance.digest();
        } catch (NoSuchAlgorithmException e) {
            kf.m182a(6, f417a, "Unsupported SHA1: " + e.getMessage());
            return null;
        }
    }

    public static String m309a(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
        char[] cArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (byte b : bArr) {
            byte b2 = (byte) (b & 15);
            stringBuilder.append(cArr[(byte) ((b & TelnetCommand.SE) >> 4)]);
            stringBuilder.append(cArr[b2]);
        }
        return stringBuilder.toString();
    }

    public static String m317b(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            return new String(bArr, FTP.DEFAULT_CONTROL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            kf.m182a(5, f417a, "Unsupported ISO-8859-1:" + e.getMessage());
            return null;
        }
    }

    public static boolean m312a(long j) {
        if (j == 0 || System.currentTimeMillis() <= j) {
            return true;
        }
        return false;
    }

    public static boolean m314a(Intent intent) {
        return jr.m120a().f284a.getPackageManager().queryIntentActivities(intent, AccessibilityNodeInfoCompat.ACTION_CUT).size() > 0;
    }

    public static String m323g(String str) {
        return str.replace("\\b", XmlPullParser.NO_NAMESPACE).replace("\\n", XmlPullParser.NO_NAMESPACE).replace("\\r", XmlPullParser.NO_NAMESPACE).replace("\\t", XmlPullParser.NO_NAMESPACE).replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"");
    }

    public static Map<String, String> m324h(String str) {
        Map<String, String> hashMap = new HashMap();
        if (!TextUtils.isEmpty(str)) {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                if (!split2[0].equals(NotificationCompatApi24.CATEGORY_EVENT)) {
                    hashMap.put(m320d(split2[0]), m320d(split2[1]));
                }
            }
        }
        return hashMap;
    }

    public static long m325i(String str) {
        if (str == null) {
            return 0;
        }
        long j = 1125899906842597L;
        int i = 0;
        while (i < str.length()) {
            long charAt = ((long) str.charAt(i)) + (j * 31);
            i++;
            j = charAt;
        }
        return j;
    }

    public static long m307a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[Util.DEFAULT_COPY_BUFFER_SIZE];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read < 0) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    public static byte[] m315a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        m307a(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static double m306a(double d) {
        return ((double) Math.round(Math.pow(10.0d, 3.0d) * d)) / Math.pow(10.0d, 3.0d);
    }

    public static boolean m313a(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            if (context.getPackageManager().checkPermission(str, context.getPackageName()) == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            kf.m182a(6, f417a, "Error occured when checking if app has permission.  Error: " + e.getMessage());
            return false;
        }
    }
}
