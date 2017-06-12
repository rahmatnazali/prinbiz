package com.hiti.nfc.mifare;

import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.util.Log;
import com.hiti.nfc.ByteUtility;
import java.io.IOException;

public class MifareCommand {
    private static final boolean localLOG = true;
    private static final String tag;

    static {
        tag = MifareCommand.class.getSimpleName();
    }

    static byte[] readNFCData(Tag nfcTag, int offset, int bytes) throws IOException {
        Log.v(tag, "readNFCData() read " + bytes + " bytes, from offset " + offset);
        if (nfcTag == null) {
            Log.v(tag, "readNFCData(): invalid parameter.");
            return null;
        }
        MifareUltralight mifare = MifareUltralight.get(nfcTag);
        if (mifare == null) {
            Log.v(tag, "readNFCData(): not support MifareUltralight.");
            return null;
        }
        int sourcePage = offset / 4;
        int sourceOffset = offset % 4;
        int endPage = ((offset + bytes) - 1) / 4;
        Log.v(tag, "readNFCData(): source page= " + sourcePage + " , endPage= " + endPage);
        byte[] tmpRecord = new byte[(((int) Math.ceil((double) ((endPage - sourcePage) + 1))) * 4)];
        int tmpRecordOffset = 0;
        try {
            Log.v(tag, "readNFCData(): try to connect mifare tag");
            mifare.connect();
            for (int page = sourcePage; page <= endPage; page += 4) {
                int dataLength;
                byte[] pages = mifare.readPages(page);
                Log.v(tag, "readNFCData(): read 4 pages from page" + page + " ,data: " + ByteUtility.printHexString(pages));
                int leftLength = tmpRecord.length - tmpRecordOffset;
                if (leftLength > pages.length) {
                    dataLength = pages.length;
                } else {
                    dataLength = leftLength;
                }
                System.arraycopy(pages, 0, tmpRecord, tmpRecordOffset, dataLength);
                tmpRecordOffset += dataLength;
            }
            byte[] record = new byte[bytes];
            System.arraycopy(tmpRecord, sourceOffset, record, 0, bytes);
            if (mifare.isConnected()) {
                mifare.close();
            }
            Log.v(tag, "readNFCData(): success and retData(hex) =" + ByteUtility.printHexString(record));
            return record;
        } catch (IOException e) {
            Log.e(tag, "readNFCData(): IOException while writing MifareUltralightmessage...", e);
            throw e;
        } catch (Throwable th) {
            if (mifare.isConnected()) {
                mifare.close();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean writeNFCData(android.nfc.Tag r13, int r14, byte[] r15) throws java.io.IOException, java.lang.InterruptedException {
        /*
        r9 = 0;
        r10 = tag;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "writeNFCData() write ";
        r11 = r11.append(r12);
        r12 = r15.length;
        r11 = r11.append(r12);
        r12 = " bytes, from offset ";
        r11 = r11.append(r12);
        r11 = r11.append(r14);
        r11 = r11.toString();
        android.util.Log.v(r10, r11);
        if (r13 != 0) goto L_0x002e;
    L_0x0026:
        r10 = tag;
        r11 = "writeNFCData(): invalid parameter.";
        android.util.Log.v(r10, r11);
    L_0x002d:
        return r9;
    L_0x002e:
        r10 = 16;
        if (r14 < r10) goto L_0x0038;
    L_0x0032:
        r10 = r15.length;
        r10 = r10 + r14;
        r11 = 160; // 0xa0 float:2.24E-43 double:7.9E-322;
        if (r10 <= r11) goto L_0x0040;
    L_0x0038:
        r10 = tag;
        r11 = "writeNFCData(): invalid offset or data is over maximum size.";
        android.util.Log.v(r10, r11);
        goto L_0x002d;
    L_0x0040:
        r4 = android.nfc.tech.MifareUltralight.get(r13);
        if (r4 != 0) goto L_0x004e;
    L_0x0046:
        r10 = tag;
        r11 = "writeNFCData(): not support MifareUltralight.";
        android.util.Log.v(r10, r11);
        goto L_0x002d;
    L_0x004e:
        r7 = r14 / 4;
        r6 = r14 % 4;
        r9 = r15.length;
        r9 = r9 + r14;
        r9 = r9 + -1;
        r2 = r9 / 4;
        r9 = r15.length;
        r9 = r9 + r14;
        r9 = r9 + -1;
        r1 = r9 % 4;
        r4.connect();	 Catch:{ IOException -> 0x009d }
        r9 = r15.length;	 Catch:{ IOException -> 0x009d }
        r9 = r9 % 4;
        if (r9 == 0) goto L_0x0078;
    L_0x0066:
        r9 = r2 - r7;
        r9 = r9 + 1;
        r9 = r9 * 4;
        r5 = new byte[r9];	 Catch:{ IOException -> 0x009d }
        r9 = 0;
        java.util.Arrays.fill(r5, r9);	 Catch:{ IOException -> 0x009d }
        r9 = 0;
        r10 = r15.length;	 Catch:{ IOException -> 0x009d }
        java.lang.System.arraycopy(r15, r9, r5, r6, r10);	 Catch:{ IOException -> 0x009d }
        r15 = r5;
    L_0x0078:
        r3 = 0;
    L_0x0079:
        r9 = r2 - r7;
        if (r3 > r9) goto L_0x008f;
    L_0x007d:
        r9 = 4;
        r8 = new byte[r9];	 Catch:{ IOException -> 0x009d }
        r9 = r3 * 4;
        r10 = 0;
        r11 = 4;
        java.lang.System.arraycopy(r15, r9, r8, r10, r11);	 Catch:{ IOException -> 0x009d }
        r9 = r3 + r7;
        writeOnePage(r4, r9, r8);	 Catch:{ IOException -> 0x009d }
        r3 = r3 + 1;
        goto L_0x0079;
    L_0x008f:
        r4.close();	 Catch:{ IOException -> 0x0094 }
    L_0x0092:
        r9 = 1;
        goto L_0x002d;
    L_0x0094:
        r0 = move-exception;
        r9 = tag;
        r10 = "IOException while closing MifareUltralight...";
        android.util.Log.e(r9, r10, r0);
        goto L_0x0092;
    L_0x009d:
        r0 = move-exception;
        r9 = tag;	 Catch:{ all -> 0x00b2 }
        r10 = "IOException while closing MifareUltralight...";
        android.util.Log.e(r9, r10, r0);	 Catch:{ all -> 0x00b2 }
        r4.close();	 Catch:{ IOException -> 0x00a9 }
        goto L_0x0092;
    L_0x00a9:
        r0 = move-exception;
        r9 = tag;
        r10 = "IOException while closing MifareUltralight...";
        android.util.Log.e(r9, r10, r0);
        goto L_0x0092;
    L_0x00b2:
        r9 = move-exception;
        r4.close();	 Catch:{ IOException -> 0x00b7 }
    L_0x00b6:
        throw r9;
    L_0x00b7:
        r0 = move-exception;
        r10 = tag;
        r11 = "IOException while closing MifareUltralight...";
        android.util.Log.e(r10, r11, r0);
        goto L_0x00b6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hiti.nfc.mifare.MifareCommand.writeNFCData(android.nfc.Tag, int, byte[]):boolean");
    }

    private static boolean writeOnePage(MifareUltralight mifare, int pageNum, byte[] data) throws IOException, InterruptedException {
        if (mifare == null || data == null) {
            return false;
        }
        try {
            mifare.writePage(pageNum, data);
        } catch (IOException e) {
        }
        return localLOG;
    }
}
