package com.hiti.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import java.util.ArrayList;

public class GlobalVariable_ColorHistoryInfo extends BaseGlobalVariable {
    public final int INVALID_COLOR;
    public int MAX_COLOR_SIZE;
    private ArrayList<Integer> m_ColoeHistoryList;

    public GlobalVariable_ColorHistoryInfo(Context context) {
        super(context);
        this.MAX_COLOR_SIZE = 8;
        this.INVALID_COLOR = ViewCompat.MEASURED_SIZE_MASK;
        this.m_ColoeHistoryList = null;
        this.m_fsp = context.getSharedPreferences("pref_fgv_history_color", 0);
        this.m_ColoeHistoryList = new ArrayList();
        this.LOG.m385i("GlobalVariable_ColorHistoryInfo", "Create");
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_ColoeHistoryList.clear();
            int i = 0;
            int iColoeHistory = this.m_fsp.getInt("GV_COLOR_HISTORY" + 0, ViewCompat.MEASURED_SIZE_MASK);
            while (iColoeHistory != ViewCompat.MEASURED_SIZE_MASK) {
                this.m_ColoeHistoryList.add(Integer.valueOf(iColoeHistory));
                i++;
                iColoeHistory = this.m_fsp.getInt("GV_COLOR_HISTORY" + i, ViewCompat.MEASURED_SIZE_MASK);
            }
            SetEdit(false);
            this.LOG.m385i("GlobalVariable_ColorHistoryInfo", "RestoreGlobalVariable");
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_ColorHistoryInfo", "RestoreGlobalVariable Fail");
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
                for (int i = 0; i < this.m_ColoeHistoryList.size(); i++) {
                    spe.putInt("GV_COLOR_HISTORY" + i, ((Integer) this.m_ColoeHistoryList.get(i)).intValue());
                }
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_ColorHistoryInfo", "SaveGlobalVariable Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_ColorHistoryInfo", "SaveGlobalVariable");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_ColorHistoryInfo", "SaveGlobalVariable Fail");
                ex.printStackTrace();
            }
        }
    }

    public int GetColoeHistory(int iIndex) {
        int iColor = -1;
        if (iIndex >= 0 && iIndex < this.m_ColoeHistoryList.size()) {
            iColor = ((Integer) this.m_ColoeHistoryList.get(iIndex)).intValue();
        }
        Log.e("GetColoeHistory...", String.valueOf(iColor));
        return iColor;
    }

    public int GetColorHistorySize() {
        return this.m_ColoeHistoryList.size();
    }

    public void AddNewColor(int iColor) {
        if (!this.m_ColoeHistoryList.contains(Integer.valueOf(iColor))) {
            if (this.m_ColoeHistoryList.size() >= this.MAX_COLOR_SIZE) {
                this.m_ColoeHistoryList.remove(this.MAX_COLOR_SIZE - 1);
                this.m_ColoeHistoryList.add(0, Integer.valueOf(iColor));
            } else {
                this.m_ColoeHistoryList.add(0, Integer.valueOf(iColor));
            }
            SetEdit(true);
        }
    }
}
