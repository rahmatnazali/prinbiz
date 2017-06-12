package com.hiti.utility;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.net.io.Util;
import org.xmlpull.v1.XmlPullParser;

public class ZipUtility {
    public static String UnpackZipForFW(String strUnZipFolder, String strZipPath) {
        String strDownLoadFilePath = XmlPullParser.NO_NAMESPACE;
        try {
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(strZipPath)));
            byte[] buffer = new byte[Util.DEFAULT_COPY_BUFFER_SIZE];
            if (!(strUnZipFolder.lastIndexOf("\\") == strUnZipFolder.length() - 1 || strUnZipFolder.lastIndexOf("/") == strUnZipFolder.length() - 1)) {
                strUnZipFolder = strUnZipFolder + "/";
            }
            FileUtility.CreateFolder(strUnZipFolder);
            while (true) {
                ZipEntry ze = zis.getNextEntry();
                if (ze != null) {
                    String filename = ze.getName();
                    if (ze.isDirectory()) {
                        new File(strUnZipFolder + filename).mkdirs();
                    } else {
                        FileOutputStream fout = new FileOutputStream(strUnZipFolder + filename);
                        while (true) {
                            int count = zis.read(buffer);
                            if (count == -1) {
                                break;
                            }
                            fout.write(buffer, 0, count);
                        }
                        fout.close();
                        zis.closeEntry();
                        strDownLoadFilePath = strUnZipFolder + filename;
                        Log.v("unzip filename", XmlPullParser.NO_NAMESPACE + strDownLoadFilePath);
                    }
                } else {
                    zis.close();
                    return strDownLoadFilePath;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean UnpackZip(String strUnZipFolder, String strZipPath) {
        try {
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(strZipPath)));
            byte[] buffer = new byte[Util.DEFAULT_COPY_BUFFER_SIZE];
            if (!(strUnZipFolder.lastIndexOf("\\") == strUnZipFolder.length() - 1 || strUnZipFolder.lastIndexOf("/") == strUnZipFolder.length() - 1)) {
                strUnZipFolder = strUnZipFolder + "/";
            }
            FileUtility.CreateFolder(strUnZipFolder);
            while (true) {
                ZipEntry ze = zis.getNextEntry();
                if (ze != null) {
                    String filename = ze.getName();
                    if (ze.isDirectory()) {
                        new File(strUnZipFolder + filename).mkdirs();
                    } else {
                        FileOutputStream fout = new FileOutputStream(strUnZipFolder + filename);
                        while (true) {
                            int count = zis.read(buffer);
                            if (count == -1) {
                                break;
                            }
                            fout.write(buffer, 0, count);
                        }
                        fout.close();
                        zis.closeEntry();
                    }
                } else {
                    zis.close();
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
