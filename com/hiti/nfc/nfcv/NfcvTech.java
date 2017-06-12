package com.hiti.nfc.nfcv;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.util.Log;
import com.hiti.nfc.ByteUtility;
import com.hiti.nfc.Nfc;
import java.io.IOException;
import java.util.Arrays;

public class NfcvTech extends Nfc {
    private static final boolean localLOG = true;
    private static final String tag;
    byte[] mNfcFooter;
    byte[] mNfcHeader;
    NfcTagInfo tagInfo;

    class NfcTagInfo {
        String AFI;
        String DSFID;
        int blockNumber;
        Tag nfcTag;
        int oneBlockSize;

        NfcTagInfo() {
        }
    }

    static {
        tag = NfcvTech.class.getSimpleName();
    }

    public NfcvTech(Intent intent) {
        super(intent);
        this.tagInfo = null;
        this.mNfcHeader = new byte[]{(byte) -31, (byte) 64, (byte) 32, (byte) 1, (byte) 3, (byte) 0};
        this.mNfcFooter = new byte[]{(byte) 0, (byte) -2};
        this.tagInfo = new NfcTagInfo();
        this.tagInfo.nfcTag = this.tagData.nfcTag;
    }

    protected NdefMessage InternalReadNFC() {
        Log.v(tag, "InternalReadNFC()");
        NdefMessage msg = super.InternalReadNFC();
        if (msg != null) {
            return msg;
        }
        byte[] nDefRecord = null;
        try {
            Log.v(tag, "InternalReadNFC(): Get System info");
            this.tagInfo = NfcVCommand.getNfcvInfo(this.tagInfo);
            byte[] recordLength = NfcVCommand.readNFCData(this.tagInfo, 5, 1);
            Log.v(tag, "InternalReadNFC(): total record length(not include NFC header) = 0x" + ByteUtility.printHexString(recordLength));
            if (recordLength[0] != null) {
                nDefRecord = NfcVCommand.readNFCData(this.tagInfo, 6, ByteUtility.byte2Int(recordLength));
            }
        } catch (IOException e) {
            Log.v(tag, "InternalReadNFC(): nfcV IOException");
        }
        if (nDefRecord != null) {
            Log.v(tag, "InternalReadNFC(): prepare to parse records");
            try {
                msg = new NdefMessage(nDefRecord);
            } catch (FormatException e2) {
                e2.printStackTrace();
            }
        }
        return msg;
    }

    protected int internalWriteNFC(NdefMessage msg) {
        Log.v(tag, "internalWriteNFC()");
        try {
            this.tagInfo = NfcVCommand.getNfcvInfo(this.tagInfo);
            byte[] byteRecord = msg.toByteArray();
            byte[] nfcData = new byte[((this.mNfcHeader.length + byteRecord.length) + this.mNfcFooter.length)];
            System.arraycopy(this.mNfcHeader, 0, nfcData, 0, this.mNfcHeader.length);
            System.arraycopy(byteRecord, 0, nfcData, this.mNfcHeader.length, byteRecord.length);
            System.arraycopy(this.mNfcFooter, 0, nfcData, this.mNfcHeader.length + byteRecord.length, this.mNfcFooter.length);
            nfcData[5] = (byte) byteRecord.length;
            byte[] completeNfcData = new byte[(this.tagInfo.blockNumber * this.tagInfo.oneBlockSize)];
            Arrays.fill(completeNfcData, (byte) 0);
            System.arraycopy(nfcData, 0, completeNfcData, 0, nfcData.length);
            NfcVCommand.writeNFCData(this.tagInfo, 0, completeNfcData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        return 4;
    }
}
