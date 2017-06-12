package com.flurry.sdk;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.commons.net.io.Util;

public final class lq {
    private static String f416a;

    static {
        f416a = lq.class.getSimpleName();
    }

    public static File m300a() {
        File file = null;
        Context context = jr.m120a().f284a;
        if ("mounted".equals(Environment.getExternalStorageState()) && (VERSION.SDK_INT >= 19 || context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
            file = context.getExternalFilesDir(null);
        }
        if (file == null) {
            return context.getFilesDir();
        }
        return file;
    }

    public static File m303b() {
        Context context = jr.m120a().f284a;
        File file = null;
        if ("mounted".equals(Environment.getExternalStorageState()) && (VERSION.SDK_INT >= 19 || context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
            file = context.getExternalCacheDir();
        }
        if (file == null) {
            return context.getCacheDir();
        }
        return file;
    }

    public static boolean m302a(File file) {
        if (file == null || file.getAbsoluteFile() == null) {
            return false;
        }
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return true;
        }
        if (parentFile.mkdirs() || parentFile.isDirectory()) {
            return true;
        }
        kf.m182a(6, f416a, "Unable to create persistent dir: " + parentFile);
        return false;
    }

    public static boolean m304b(File file) {
        if (file != null && file.isDirectory()) {
            for (String file2 : file.list()) {
                if (!m304b(new File(file, file2))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    @Deprecated
    public static String m305c(File file) {
        Closeable fileInputStream;
        StringBuilder stringBuilder;
        Throwable th;
        Throwable th2;
        if (file == null || !file.exists()) {
            kf.m182a(4, f416a, "Persistent file doesn't exist.");
            return null;
        }
        kf.m182a(4, f416a, "Loading persistent data: " + file.getAbsolutePath());
        try {
            fileInputStream = new FileInputStream(file);
            try {
                stringBuilder = new StringBuilder();
                byte[] bArr = new byte[Util.DEFAULT_COPY_BUFFER_SIZE];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    stringBuilder.append(new String(bArr, 0, read));
                }
                lr.m311a(fileInputStream);
            } catch (Throwable th3) {
                th = th3;
                try {
                    kf.m183a(6, f416a, "Error when loading persistent file", th);
                    lr.m311a(fileInputStream);
                    stringBuilder = null;
                    if (stringBuilder != null) {
                        return null;
                    }
                    return stringBuilder.toString();
                } catch (Throwable th4) {
                    th2 = th4;
                    lr.m311a(fileInputStream);
                    throw th2;
                }
            }
        } catch (Throwable th5) {
            fileInputStream = null;
            th2 = th5;
            lr.m311a(fileInputStream);
            throw th2;
        }
        if (stringBuilder != null) {
            return stringBuilder.toString();
        }
        return null;
    }

    @Deprecated
    public static void m301a(File file, String str) {
        Closeable fileOutputStream;
        Throwable th;
        if (file == null) {
            kf.m182a(4, f416a, "No persistent file specified.");
        } else if (str == null) {
            kf.m182a(4, f416a, "No data specified; deleting persistent file: " + file.getAbsolutePath());
            file.delete();
        } else {
            kf.m182a(4, f416a, "Writing persistent data: " + file.getAbsolutePath());
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    fileOutputStream.write(str.getBytes());
                    lr.m311a(fileOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        kf.m183a(6, f416a, "Error writing persistent file", th);
                        lr.m311a(fileOutputStream);
                    } catch (Throwable th3) {
                        th = th3;
                        lr.m311a(fileOutputStream);
                        throw th;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                lr.m311a(fileOutputStream);
                throw th;
            }
        }
    }
}
