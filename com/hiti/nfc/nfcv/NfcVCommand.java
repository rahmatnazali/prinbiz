package com.hiti.nfc.nfcv;

import android.nfc.tech.NfcV;
import android.util.Log;
import com.hiti.nfc.ByteUtility;
import java.io.IOException;
import java.util.Arrays;

public class NfcVCommand {
    private static final boolean localLOG = true;
    private static final String tag;

    static {
        tag = NfcVCommand.class.getSimpleName();
    }

    static byte[] readNFCData(NfcTagInfo nfcInfo, int offset, int bytes) throws IOException {
        if (nfcInfo.nfcTag == null) {
            Log.v(tag, "readData(): invalid parameter.");
            return null;
        }
        NfcV nfcV = NfcV.get(nfcInfo.nfcTag);
        if (nfcV == null) {
            Log.v(tag, "readData(): not support NfcV.");
            return null;
        }
        if (bytes > (nfcInfo.blockNumber * nfcInfo.oneBlockSize) - offset) {
            bytes = (nfcInfo.blockNumber * nfcInfo.oneBlockSize) - offset;
        }
        int sourceBlock = offset / nfcInfo.oneBlockSize;
        int sourceOffset = offset % nfcInfo.oneBlockSize;
        int endBlock = ((offset + bytes) - 1) / nfcInfo.oneBlockSize;
        byte[] tmpData = new byte[(((endBlock - sourceBlock) + 1) * nfcInfo.oneBlockSize)];
        try {
            nfcV.connect();
            Log.v(tag, "readData(): NfcV connect.");
            byte[] ID = nfcInfo.nfcTag.getId();
            for (int i = 0; i <= endBlock - sourceBlock; i++) {
                byte[] blockData = readOneBlock(nfcV, ID, i + sourceBlock);
                if (blockData != null) {
                    System.arraycopy(blockData, 0, tmpData, nfcInfo.oneBlockSize * i, nfcInfo.oneBlockSize);
                }
            }
            if (nfcV.isConnected()) {
                nfcV.close();
            }
            byte[] retData = new byte[bytes];
            System.arraycopy(tmpData, sourceOffset, retData, 0, bytes);
            Log.v(tag, "readData(): retData =" + ByteUtility.printHexString(retData));
            return retData;
        } catch (IOException e) {
            throw e;
        } catch (Throwable th) {
            if (nfcV.isConnected()) {
                nfcV.close();
            }
        }
    }

    static boolean writeNFCData(NfcTagInfo nfcInfo, int offset, byte[] data) throws IOException, InterruptedException {
        if (nfcInfo.nfcTag == null) {
            Log.v(tag, "writeData(): invalid parameter.");
            return false;
        }
        NfcV nfcV = NfcV.get(nfcInfo.nfcTag);
        if (nfcV == null) {
            Log.v(tag, "writeData(): nfcV can't get.");
            return false;
        } else if (data.length > (nfcInfo.blockNumber * nfcInfo.oneBlockSize) - offset) {
            Log.v(tag, "writeData(): input data will over maximum size of NFC tag.");
            return false;
        } else {
            try {
                nfcV.connect();
                int sourceBlock = offset / nfcInfo.oneBlockSize;
                int sourceOffset = offset % nfcInfo.oneBlockSize;
                int endBlock = ((data.length + offset) - 1) / nfcInfo.oneBlockSize;
                if (data.length % nfcInfo.oneBlockSize != 0) {
                    byte[] ndata = new byte[(((endBlock - sourceBlock) + 1) * nfcInfo.oneBlockSize)];
                    Arrays.fill(ndata, (byte) 0);
                    System.arraycopy(data, 0, ndata, sourceOffset, data.length);
                    data = ndata;
                }
                for (int i = 0; i <= endBlock - sourceBlock; i++) {
                    byte[] tmpData = new byte[nfcInfo.oneBlockSize];
                    System.arraycopy(data, nfcInfo.oneBlockSize * i, tmpData, 0, nfcInfo.oneBlockSize);
                    writeOneBlock(nfcV, nfcInfo.oneBlockSize, tmpData, i + sourceBlock);
                }
                if (nfcV.isConnected()) {
                    nfcV.close();
                }
                return localLOG;
            } catch (IOException e) {
                throw e;
            } catch (Throwable th) {
                if (nfcV.isConnected()) {
                    nfcV.close();
                }
            }
        }
    }

    static NfcTagInfo getNfcvInfo(NfcTagInfo nfcInfo) throws IOException {
        if (nfcInfo.nfcTag == null) {
            Log.v(tag, "getNfcvInfo(): invalid parameter.");
            return null;
        }
        NfcV nfcV = NfcV.get(nfcInfo.nfcTag);
        if (nfcV == null) {
            Log.v(tag, "getNfcvInfo(): nfcV can't get.");
            return null;
        }
        try {
            nfcV.connect();
            byte[] ID = nfcInfo.nfcTag.getId();
            byte[] cmd = new byte[10];
            cmd[0] = (byte) 34;
            cmd[1] = (byte) 43;
            System.arraycopy(ID, 0, cmd, 2, ID.length);
            byte[] infoRmation = nfcV.transceive(cmd);
            if (infoRmation.length > 14) {
                nfcInfo.blockNumber = infoRmation[12] + 1;
                Log.v(tag, "getNfcvInfo(): block number = " + nfcInfo.blockNumber);
                nfcInfo.oneBlockSize = infoRmation[13] + 1;
                Log.v(tag, "getNfcvInfo(): one block size = " + nfcInfo.oneBlockSize);
                nfcInfo.AFI = ByteUtility.printHexString(new byte[]{infoRmation[11]});
                Log.v(tag, "getNfcvInfo(): AFI = " + nfcInfo.AFI);
                nfcInfo.DSFID = ByteUtility.printHexString(new byte[]{infoRmation[10]});
                Log.v(tag, "getNfcvInfo(): DSFID = " + nfcInfo.DSFID);
            }
            nfcV.close();
            return nfcInfo;
        } catch (IOException e) {
            throw e;
        } catch (Throwable th) {
            nfcV.close();
        }
    }

    private static byte[] readOneBlock(NfcV nfcV, byte[] ID, int blockNumber) throws IOException {
        if (nfcV == null || ID == null) {
            return null;
        }
        byte[] cmd = new byte[11];
        cmd[0] = (byte) 34;
        cmd[1] = (byte) 32;
        System.arraycopy(ID, 0, cmd, 2, ID.length);
        cmd[10] = (byte) blockNumber;
        byte[] res = nfcV.transceive(cmd);
        if (res[0] != null) {
            return null;
        }
        byte[] block = new byte[(res.length - 1)];
        System.arraycopy(res, 1, block, 0, res.length - 1);
        return block;
    }

    private static boolean writeOneBlock(NfcV nfcV, int oneBlockSize, byte[] data, int blockNumber) throws IOException, InterruptedException {
        if (nfcV == null || data == null) {
            return false;
        }
        byte[] cmd = new byte[(oneBlockSize + 3)];
        cmd[0] = (byte) 66;
        cmd[1] = (byte) 33;
        cmd[2] = (byte) blockNumber;
        for (int i = 0; i < oneBlockSize; i++) {
            cmd[i + 3] = data[i];
        }
        try {
            nfcV.transceive(cmd);
            return localLOG;
        } catch (IOException e) {
            return localLOG;
        }
    }
}
