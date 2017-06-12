package com.hiti.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import com.hiti.nfc.mifare.MifareUltraTech;
import com.hiti.nfc.nfcv.NfcvTech;
import java.io.IOException;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;

public class Nfc {
    public static final String NFC_DATA = "NFC data";
    public static final int NFC_IS_WRITING = 5;
    public static final int NFC_WRITE_FAILED = 3;
    public static final int NFC_WRITE_SUCCESS = 4;
    private static final boolean localLOG = true;
    private static final String tag;
    protected NfcTagData tagData;

    /* renamed from: com.hiti.nfc.Nfc.1 */
    class C02371 implements Runnable {
        final /* synthetic */ Handler val$handler;

        C02371(Handler handler) {
            this.val$handler = handler;
        }

        public void run() {
            if (Nfc.this.tagData.ndefMsg == null) {
                Log.v(Nfc.tag, "readNFC(): call internal read NFC function");
                Nfc.this.tagData.ndefMsg = Nfc.this.InternalReadNFC();
            }
            Message msg = Message.obtain();
            Bundle b = new Bundle();
            b.putParcelable(Nfc.NFC_DATA, Nfc.this.tagData.ndefMsg);
            msg.setData(b);
            this.val$handler.sendMessage(msg);
        }
    }

    /* renamed from: com.hiti.nfc.Nfc.2 */
    class C02382 implements Runnable {
        int retVal;
        final /* synthetic */ Handler val$handler;
        final /* synthetic */ String val$pkgName;
        final /* synthetic */ String val$printer;
        final /* synthetic */ String val$ssid;

        C02382(String str, String str2, String str3, Handler handler) {
            this.val$ssid = str;
            this.val$printer = str2;
            this.val$pkgName = str3;
            this.val$handler = handler;
            this.retVal = Nfc.NFC_WRITE_FAILED;
        }

        public void run() {
            NdefMessage ndefMsg = Nfc.this.produceNdefMessage(this.val$ssid, this.val$printer, this.val$pkgName);
            Nfc.this.internalWriteNFC(ndefMsg);
            if (ndefMsg.equals(Nfc.this.InternalReadNFC())) {
                Log.v(Nfc.tag, "writeNFC(): equal");
                this.retVal = Nfc.NFC_WRITE_SUCCESS;
            }
            Message msg = Message.obtain();
            msg.what = this.retVal;
            this.val$handler.sendMessage(msg);
        }
    }

    protected class NfcTagData {
        byte[] ID;
        NdefMessage ndefMsg;
        public Tag nfcTag;
        String[] techArray;

        protected NfcTagData() {
            this.ndefMsg = null;
        }
    }

    static {
        tag = Nfc.class.getSimpleName();
    }

    public static Nfc getNfcInstance(Intent intent) {
        Tag tagFromIntent = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
        if (tagFromIntent == null) {
            Log.v(tag, "getNfcInstance():  can not get tagFromIntent");
            return null;
        } else if (MifareUltralight.get(tagFromIntent) != null) {
            Log.v(tag, "getNfcInstance():  MifareUltralight instance");
            return new MifareUltraTech(intent);
        } else if (NfcV.get(tagFromIntent) != null) {
            Log.v(tag, "getNfcInstance(): NfcV instance");
            return new NfcvTech(intent);
        } else if (Ndef.get(tagFromIntent) != null) {
            Log.v(tag, "getNfcInstance(): Nfc instance");
            return new Nfc(intent);
        } else {
            Log.v(tag, "getNfcInstance():  not support the NFC type");
            return null;
        }
    }

    protected Nfc(Intent intent) {
        this.tagData = null;
        Tag tagFromIntent = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
        if (tagFromIntent != null) {
            this.tagData = new NfcTagData();
            this.tagData.nfcTag = tagFromIntent;
            this.tagData.ID = tagFromIntent.getId();
            this.tagData.techArray = tagFromIntent.getTechList();
            for (String s : this.tagData.techArray) {
                Log.v(tag, "Nfc(): support tech: " + s);
            }
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
            if (rawMsgs != null) {
                this.tagData.ndefMsg = (NdefMessage) rawMsgs[0];
            }
        }
    }

    public void readNFC(Handler handler) {
        Log.v(tag, "readNFC(): begin");
        new Thread(new C02371(handler)).start();
    }

    protected NdefMessage InternalReadNFC() {
        Log.v(tag, "InternalReadNFC(): entry");
        NdefMessage msg = null;
        Ndef nDef = Ndef.get(this.tagData.nfcTag);
        if (nDef != null) {
            try {
                Log.v(tag, "InternalReadNFC(): try to connect ndef tag");
                nDef.connect();
                msg = nDef.getNdefMessage();
                Log.v(tag, "InternalReadNFC(): ndef IO operation success");
                try {
                    if (nDef.isConnected()) {
                        nDef.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                try {
                    if (nDef.isConnected()) {
                        nDef.close();
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    if (nDef.isConnected()) {
                        nDef.close();
                    }
                } catch (IOException e32) {
                    e32.printStackTrace();
                }
            }
        }
        return msg;
    }

    public static void enableNdefExchangeMode(NfcAdapter mAdapter, Activity activity, PendingIntent mPendingIntent) {
        if (mAdapter == null || activity == null || mPendingIntent == null) {
            throw new IllegalArgumentException("all the parameter should not be null");
        }
        IntentFilter ndef = new IntentFilter("android.nfc.action.NDEF_DISCOVERED");
        try {
            String typeName = "application/" + activity.getApplicationContext().getPackageName().toLowerCase(Locale.ENGLISH).replace("com.", XmlPullParser.NO_NAMESPACE);
            Log.v(tag, "enableNdefExchangeMode(): type filter = " + typeName);
            ndef.addDataType(typeName);
        } catch (MalformedMimeTypeException e) {
            e.printStackTrace();
        }
        IntentFilter nt = new IntentFilter("android.nfc.action.TAG_DISCOVERED");
        Tag tagFromIntent = null;
        if (activity.getIntent() != null) {
            tagFromIntent = (Tag) activity.getIntent().getParcelableExtra("android.nfc.extra.TAG");
        }
        String[][] mTechLists = null;
        if (tagFromIntent != null) {
            if (MifareUltralight.get(tagFromIntent) != null) {
                Log.v(tag, "enableNdefExchangeMode(): set Mifare as filter");
                mTechLists = new String[1][];
                mTechLists[0] = new String[]{MifareUltralight.class.getName()};
            } else if (NfcV.get(tagFromIntent) != null) {
                Log.v(tag, "enableNdefExchangeMode(): set nfcv as filter");
                mTechLists = new String[1][];
                mTechLists[0] = new String[]{NfcV.class.getName()};
            }
        }
        mAdapter.enableForegroundDispatch(activity, mPendingIntent, new IntentFilter[]{ndef, nt}, mTechLists);
    }

    public void writeNFC(String ssid, String printer, String pkgName, Handler handler) {
        Log.v(tag, "writeTag(): begin");
        if (pkgName == null || handler == null) {
            Log.v(tag, "writeTag(): parameter is null");
        } else {
            new Thread(new C02382(ssid, printer, pkgName, handler)).start();
        }
    }

    protected int internalWriteNFC(NdefMessage msg) {
        Log.v(tag, "internalWriteNFC(): begin");
        int retVal = NFC_WRITE_FAILED;
        if (msg == null) {
            return NFC_WRITE_FAILED;
        }
        int messageLength = msg.toByteArray().length;
        Ndef nDef = Ndef.get(this.tagData.nfcTag);
        if (nDef != null) {
            try {
                if (!nDef.isWritable()) {
                    try {
                        if (nDef.isConnected()) {
                            nDef.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return NFC_WRITE_FAILED;
                } else if (nDef.getMaxSize() < messageLength) {
                    try {
                        if (nDef.isConnected()) {
                            nDef.close();
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    return NFC_WRITE_FAILED;
                } else {
                    nDef.connect();
                    Log.v(tag, "internalWriteNFC(): start to write NFC by Ndef");
                    nDef.writeNdefMessage(msg);
                    retVal = NFC_WRITE_SUCCESS;
                    Log.v(tag, "internalWriteNFC(): write success");
                    try {
                        if (nDef.isConnected()) {
                            nDef.close();
                        }
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                }
            } catch (IOException e222) {
                e222.printStackTrace();
                try {
                    if (nDef.isConnected()) {
                        nDef.close();
                    }
                } catch (IOException e2222) {
                    e2222.printStackTrace();
                }
            } catch (FormatException e3) {
                e3.printStackTrace();
                try {
                    if (nDef.isConnected()) {
                        nDef.close();
                    }
                } catch (IOException e22222) {
                    e22222.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    if (nDef.isConnected()) {
                        nDef.close();
                    }
                } catch (IOException e222222) {
                    e222222.printStackTrace();
                }
            }
        } else {
            NdefFormatable format = NdefFormatable.get(this.tagData.nfcTag);
            if (format != null) {
                try {
                    format.connect();
                    Log.v(tag, "internalWriteNFC(): start to write NFC by NdefFormatable");
                    format.format(msg);
                    retVal = NFC_WRITE_SUCCESS;
                    Log.v(tag, "internalWriteNFC(): write success");
                    try {
                        if (format.isConnected()) {
                            nDef.close();
                        }
                    } catch (IOException e2222222) {
                        e2222222.printStackTrace();
                    }
                } catch (IOException e22222222) {
                    e22222222.printStackTrace();
                    try {
                        if (format.isConnected()) {
                            nDef.close();
                        }
                    } catch (IOException e222222222) {
                        e222222222.printStackTrace();
                    }
                } catch (FormatException e32) {
                    e32.printStackTrace();
                    try {
                        if (format.isConnected()) {
                            nDef.close();
                        }
                    } catch (IOException e2222222222) {
                        e2222222222.printStackTrace();
                    }
                } catch (Throwable th2) {
                    try {
                        if (format.isConnected()) {
                            nDef.close();
                        }
                    } catch (IOException e22222222222) {
                        e22222222222.printStackTrace();
                    }
                }
            }
        }
        return retVal;
    }

    private NdefMessage produceNdefMessage(String ssid, String printer, String pkgName) {
        NdefRecord ssidRecord;
        String appName = pkgName.toLowerCase(Locale.ENGLISH).replace("com.hiti.", XmlPullParser.NO_NAMESPACE);
        if (ssid != null) {
            ssidRecord = NdefRecord.createMime("application/hiti." + appName, ssid.getBytes());
        } else {
            ssidRecord = NdefRecord.createMime("application/hiti." + appName, null);
        }
        NdefRecord aarRecord = NdefRecord.createApplicationRecord(pkgName);
        if (printer != null) {
            NdefRecord printerRecord = NdefRecord.createMime("printer/" + printer, null);
            return new NdefMessage(ssidRecord, new NdefRecord[]{printerRecord, aarRecord});
        }
        return new NdefMessage(ssidRecord, new NdefRecord[]{aarRecord});
    }
}
