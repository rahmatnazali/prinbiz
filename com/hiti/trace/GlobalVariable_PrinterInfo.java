package com.hiti.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_PrinterInfo extends BaseGlobalVariable {
    private String m_PrinterIP;
    private String m_PrinterName;
    private int m_PrinterPort;
    private boolean m_boFlashCard;
    private boolean m_boMetalEnable;
    private boolean m_boMirror;
    private int m_iCleanNumber;
    private int m_iMaskColor;

    public GlobalVariable_PrinterInfo(Context context) {
        super(context);
        this.m_PrinterIP = XmlPullParser.NO_NAMESPACE;
        this.m_PrinterPort = 0;
        this.m_PrinterName = XmlPullParser.NO_NAMESPACE;
        this.m_iMaskColor = 0;
        this.m_boFlashCard = false;
        this.m_boMirror = false;
        this.m_boMetalEnable = true;
        this.m_iCleanNumber = 0;
        this.m_fsp = context.getSharedPreferences("pref_fgv_printer_info", 0);
        this.LOG.m385i("GlobalVariable_PrinterInfo", "Create");
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_PrinterName = this.m_fsp.getString("GV_M_PRINTER_NAME", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_PrinterName", this.m_PrinterName);
            this.m_PrinterIP = this.m_fsp.getString("GV_M_PRINTER_IP", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_PrinterIP", this.m_PrinterIP);
            this.m_PrinterPort = this.m_fsp.getInt("GV_M_PRINTER_PORT", -1);
            this.LOG.m385i("m_PrinterPort", String.valueOf(this.m_PrinterPort));
            this.m_iMaskColor = this.m_fsp.getInt("GV_M_PRINTER_MASK_COLOR", 0);
            this.LOG.m385i("m_iMaskColor", String.valueOf(this.m_iMaskColor));
            this.m_boFlashCard = this.m_fsp.getBoolean("GV_M_FLASH_CARD", false);
            this.LOG.m385i("m_boFlashCard", String.valueOf(this.m_boFlashCard));
            this.m_boMirror = this.m_fsp.getBoolean("GV_M_MIRROR", false);
            this.LOG.m385i("m_boMirror", String.valueOf(this.m_boMirror));
            this.m_boMetalEnable = this.m_fsp.getBoolean("GV_M_METAL_ENABLE", true);
            this.LOG.m385i("m_boMetalEnable", String.valueOf(this.m_boMetalEnable));
            this.m_iCleanNumber = this.m_fsp.getInt("GV_M_CLEAN_NUMBER", 0);
            SetEdit(false);
            this.LOG.m385i("GlobalVariable_PrinterInfo", "RestoreGlobalVariable");
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_PrinterInfo", "RestoreGlobalVariable Fail");
            e.printStackTrace();
        }
    }

    public void SaveGlobalVariable() {
        SaveGlobalVariableForever();
    }

    public void SaveGlobalVariableForever() {
        if (IsEdit()) {
            try {
                Editor spe = this.m_fsp.edit();
                spe.clear();
                spe.putString("GV_M_PRINTER_NAME", this.m_PrinterName);
                spe.putString("GV_M_PRINTER_IP", this.m_PrinterIP);
                spe.putInt("GV_M_PRINTER_PORT", this.m_PrinterPort);
                spe.putInt("GV_M_PRINTER_MASK_COLOR", this.m_iMaskColor);
                spe.putBoolean("GV_M_FLASH_CARD", this.m_boFlashCard);
                spe.putBoolean("GV_M_MIRROR", this.m_boMirror);
                spe.putBoolean("GV_M_METAL_ENABLE", this.m_boMetalEnable);
                spe.putInt("GV_M_CLEAN_NUMBER", this.m_iCleanNumber);
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_PrinterInfo", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_PrinterInfo", "SaveGlobalVariableForever");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_PrinterInfo", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public String GetPrinterIP() {
        return this.m_PrinterIP;
    }

    public void SetPrinterIP(String PrinterIP) {
        this.m_PrinterIP = PrinterIP;
        SetEdit(true);
    }

    public int GetPrinterPort() {
        return this.m_PrinterPort;
    }

    public void SetPrinterPort(int PrinterPort) {
        this.m_PrinterPort = PrinterPort;
        SetEdit(true);
    }

    public String GetPrinterName() {
        return this.m_PrinterName;
    }

    public void SetPrinterName(String PrinterName) {
        this.m_PrinterName = PrinterName;
        SetEdit(true);
    }

    public int GetMaskColor() {
        return this.m_iMaskColor;
    }

    public void SetMaskColor(int iMaskColor) {
        this.m_iMaskColor = iMaskColor;
        SetEdit(true);
    }

    public boolean GetFlashCard() {
        return this.m_boFlashCard;
    }

    public void SetFlashCard(boolean boFlashCard) {
        this.m_boFlashCard = boFlashCard;
        SetEdit(true);
    }

    public boolean GetMirror() {
        return this.m_boMirror;
    }

    public void SetMirror(boolean boMirror) {
        this.m_boMirror = boMirror;
        SetEdit(true);
    }

    public boolean GetMetalEnable() {
        return this.m_boMetalEnable;
    }

    public void SetMetalEnable(boolean boMetalEnable) {
        this.m_boMetalEnable = boMetalEnable;
        SetEdit(true);
    }

    public int GetCleanNumber() {
        return this.m_iCleanNumber;
    }

    public void SetCleanNumber(int iCleanNumber) {
        this.m_iCleanNumber = iCleanNumber;
        SetEdit(true);
    }
}
