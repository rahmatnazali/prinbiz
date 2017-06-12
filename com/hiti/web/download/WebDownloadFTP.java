package com.hiti.web.download;

import org.xmlpull.v1.XmlPullParser;

public class WebDownloadFTP {
    public static String GetDomainName(String strPath) {
        String strDomainName = null;
        if (strPath == null) {
            return null;
        }
        if (strPath.contains("ftp://")) {
            strDomainName = strPath.replace("ftp://", XmlPullParser.NO_NAMESPACE);
            strDomainName = strDomainName.substring(0, strDomainName.indexOf("/"));
        }
        return strDomainName;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.hiti.web.download.WEB_DOWNLOAD_ERROR FTPDownload(java.lang.String r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, java.lang.String r22, java.lang.String r23) {
        /*
        r7 = new org.apache.commons.net.ftp.FTPClient;
        r7.<init>();
        r5 = 0;
        r12 = com.hiti.web.download.WEB_DOWNLOAD_ERROR.NON;
        r12 = com.hiti.web.download.WEB_DOWNLOAD_ERROR.ERROR_OPEN_CONNECTION;	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r14 = java.lang.Integer.valueOf(r19);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r14 = r14.intValue();	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r0 = r18;
        r7.connect(r0, r14);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r12 = com.hiti.web.download.WEB_DOWNLOAD_ERROR.NON;	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r12 = com.hiti.web.download.WEB_DOWNLOAD_ERROR.ERROR_USER;	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r0 = r20;
        r1 = r21;
        r7.login(r0, r1);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r11 = r7.getReplyCode();	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r14 = org.apache.commons.net.ftp.FTPReply.isPositiveCompletion(r11);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        if (r14 != 0) goto L_0x0031;
    L_0x002c:
        r7.disconnect();	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r13 = r12;
    L_0x0030:
        return r13;
    L_0x0031:
        r12 = com.hiti.web.download.WEB_DOWNLOAD_ERROR.NON;	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r12 = com.hiti.web.download.WEB_DOWNLOAD_ERROR.ERROR_SAVE_FILE;	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r14 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r7.setBufferSize(r14);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r14 = 2;
        r7.setFileType(r14);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r7.enterLocalPassiveMode();	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r10 = com.hiti.utility.FileUtility.GetFileName(r22);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r14 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r14.<init>();	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r15 = "ftp://";
        r14 = r14.append(r15);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r0 = r18;
        r14 = r14.append(r0);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r15 = "/";
        r14 = r14.append(r15);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r14 = r14.toString();	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r15 = "";
        r0 = r22;
        r9 = r0.replace(r14, r15);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r9 = com.hiti.utility.FileUtility.GetFolderFullPath(r9);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r14 = "remoteFileName";
        android.util.Log.i(r14, r10);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r4 = r7.listFiles(r9);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r14 = "files size";
        r15 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r15.<init>();	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r16 = "";
        r15 = r15.append(r16);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r0 = r4.length;	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r16 = r0;
        r15 = r15.append(r16);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r15 = r15.toString();	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        android.util.Log.i(r14, r15);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r15 = r4.length;	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r14 = 0;
        r6 = r5;
    L_0x0093:
        if (r14 >= r15) goto L_0x00d8;
    L_0x0095:
        r3 = r4[r14];	 Catch:{ FileNotFoundException -> 0x011d, SocketException -> 0x011a, IOException -> 0x0117, Exception -> 0x0114 }
        r16 = r3.getName();	 Catch:{ FileNotFoundException -> 0x011d, SocketException -> 0x011a, IOException -> 0x0117, Exception -> 0x0114 }
        r0 = r16;
        r16 = r0.equals(r10);	 Catch:{ FileNotFoundException -> 0x011d, SocketException -> 0x011a, IOException -> 0x0117, Exception -> 0x0114 }
        if (r16 == 0) goto L_0x0120;
    L_0x00a3:
        r8 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x011d, SocketException -> 0x011a, IOException -> 0x0117, Exception -> 0x0114 }
        r0 = r23;
        r8.<init>(r0);	 Catch:{ FileNotFoundException -> 0x011d, SocketException -> 0x011a, IOException -> 0x0117, Exception -> 0x0114 }
        r5 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x011d, SocketException -> 0x011a, IOException -> 0x0117, Exception -> 0x0114 }
        r5.<init>(r8);	 Catch:{ FileNotFoundException -> 0x011d, SocketException -> 0x011a, IOException -> 0x0117, Exception -> 0x0114 }
        r16 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r16.<init>();	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r0 = r16;
        r16 = r0.append(r9);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r17 = "/";
        r16 = r16.append(r17);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r17 = r3.getName();	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r16 = r16.append(r17);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r16 = r16.toString();	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r0 = r16;
        r7.retrieveFile(r0, r5);	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
        r5.close();	 Catch:{ FileNotFoundException -> 0x00e8, SocketException -> 0x00ef, IOException -> 0x00f6, Exception -> 0x00fd }
    L_0x00d4:
        r14 = r14 + 1;
        r6 = r5;
        goto L_0x0093;
    L_0x00d8:
        r12 = com.hiti.web.download.WEB_DOWNLOAD_ERROR.NON;	 Catch:{ FileNotFoundException -> 0x011d, SocketException -> 0x011a, IOException -> 0x0117, Exception -> 0x0114 }
        r12 = com.hiti.web.download.WEB_DOWNLOAD_ERROR.ERROR_CLOSE_CONNECTION;	 Catch:{ IOException -> 0x0104, Exception -> 0x010c }
        r7.logout();	 Catch:{ IOException -> 0x0104, Exception -> 0x010c }
        r7.disconnect();	 Catch:{ IOException -> 0x0104, Exception -> 0x010c }
        r12 = com.hiti.web.download.WEB_DOWNLOAD_ERROR.NON;	 Catch:{ IOException -> 0x0104, Exception -> 0x010c }
        r5 = r6;
        r13 = r12;
        goto L_0x0030;
    L_0x00e8:
        r2 = move-exception;
    L_0x00e9:
        r2.printStackTrace();
        r13 = r12;
        goto L_0x0030;
    L_0x00ef:
        r2 = move-exception;
    L_0x00f0:
        r2.printStackTrace();
        r13 = r12;
        goto L_0x0030;
    L_0x00f6:
        r2 = move-exception;
    L_0x00f7:
        r2.printStackTrace();
        r13 = r12;
        goto L_0x0030;
    L_0x00fd:
        r2 = move-exception;
    L_0x00fe:
        r2.printStackTrace();
        r13 = r12;
        goto L_0x0030;
    L_0x0104:
        r2 = move-exception;
        r2.printStackTrace();
        r5 = r6;
        r13 = r12;
        goto L_0x0030;
    L_0x010c:
        r2 = move-exception;
        r2.printStackTrace();
        r5 = r6;
        r13 = r12;
        goto L_0x0030;
    L_0x0114:
        r2 = move-exception;
        r5 = r6;
        goto L_0x00fe;
    L_0x0117:
        r2 = move-exception;
        r5 = r6;
        goto L_0x00f7;
    L_0x011a:
        r2 = move-exception;
        r5 = r6;
        goto L_0x00f0;
    L_0x011d:
        r2 = move-exception;
        r5 = r6;
        goto L_0x00e9;
    L_0x0120:
        r5 = r6;
        goto L_0x00d4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hiti.web.download.WebDownloadFTP.FTPDownload(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String):com.hiti.web.download.WEB_DOWNLOAD_ERROR");
    }
}
