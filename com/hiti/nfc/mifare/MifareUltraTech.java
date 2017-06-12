package com.hiti.nfc.mifare;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.util.Log;
import com.hiti.nfc.ByteUtility;
import com.hiti.nfc.Nfc;
import java.io.IOException;
import java.util.Arrays;

public class MifareUltraTech extends Nfc {
    private static final boolean localLOG = true;
    private static final String tag;
    byte[] mNfcFooter;
    byte[] mNfcHeader;

    static {
        tag = MifareUltraTech.class.getSimpleName();
    }

    public MifareUltraTech(Intent intent) {
        super(intent);
        this.mNfcHeader = new byte[]{(byte) 52, (byte) 3, (byte) 0};
        this.mNfcFooter = new byte[]{(byte) 0, (byte) -2};
    }

    protected NdefMessage InternalReadNFC() {
        Log.v(tag, "InternalReadNFC()");
        NdefMessage msg = super.InternalReadNFC();
        if (msg != null) {
            return msg;
        }
        byte[] nDefRecord = null;
        try {
            byte[] recordLength = MifareCommand.readNFCData(this.tagData.nfcTag, 22, 1);
            if (recordLength != null) {
                int nDefRecordLength = ByteUtility.byte2Int(recordLength);
                Log.v(tag, "InternalReadNFC(): trying to trim data, data length = " + nDefRecordLength);
                nDefRecord = MifareCommand.readNFCData(this.tagData.nfcTag, 23, nDefRecordLength);
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
            byte[] byteRecord = msg.toByteArray();
            byte[] nfcData = new byte[((this.mNfcHeader.length + byteRecord.length) + this.mNfcFooter.length)];
            System.arraycopy(this.mNfcHeader, 0, nfcData, 0, this.mNfcHeader.length);
            System.arraycopy(byteRecord, 0, nfcData, this.mNfcHeader.length, byteRecord.length);
            System.arraycopy(this.mNfcFooter, 0, nfcData, this.mNfcHeader.length + byteRecord.length, this.mNfcFooter.length);
            nfcData[2] = (byte) byteRecord.length;
            byte[] completeNfcData = new byte[140];
            Arrays.fill(completeNfcData, (byte) 0);
            System.arraycopy(nfcData, 0, completeNfcData, 0, nfcData.length);
            MifareCommand.writeNFCData(this.tagData.nfcTag, 20, completeNfcData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        return 4;
    }
}
