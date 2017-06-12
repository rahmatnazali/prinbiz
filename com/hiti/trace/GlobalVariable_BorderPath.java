package com.hiti.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_BorderPath extends BaseGlobalVariable_Prinbiz {
    boolean m_bIsNoData;
    ArrayList<String> m_strBorderPathList;

    public GlobalVariable_BorderPath(Context context) {
        super(context);
        this.m_strBorderPathList = null;
        this.m_bIsNoData = false;
        this.m_sp = context.getSharedPreferences("pref_gv_border_meta", 0);
        this.m_strBorderPathList = new ArrayList();
    }

    public void RestoreGlobalVariable() {
        int pathLen = this.m_sp.getInt("GV_M_BORDER_PATH_LEN", 0);
        if (pathLen == 0) {
            this.m_bIsNoData = true;
            return;
        }
        this.m_bIsNoData = false;
        this.m_strBorderPathList.clear();
        for (int i = 0; i < pathLen; i++) {
            String path = this.m_sp.getString("GV_M_BORDER_PATH" + i, XmlPullParser.NO_NAMESPACE);
            this.m_strBorderPathList.add(path);
            this.LOG.m385i("path" + i, path);
        }
        SetEdit(false);
    }

    public boolean IsNoData() {
        if (this.m_bIsNoData) {
            return true;
        }
        return false;
    }

    public void GetBorderPathList(ArrayList<String> strBorderPathList) {
        if (strBorderPathList != null) {
            Iterator it = this.m_strBorderPathList.iterator();
            while (it.hasNext()) {
                strBorderPathList.add((String) it.next());
            }
        }
    }

    public void SaveGlobalVariable() {
        SaveGlobalVariableForever();
    }

    public void SaveGlobalVariableForever() {
        if (IsEdit()) {
            Editor spe = this.m_sp.edit();
            int i = 0;
            while (i < this.m_strBorderPathList.size()) {
                try {
                    spe.putString("GV_M_BORDER_PATH" + i, (String) this.m_strBorderPathList.get(i));
                    i++;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return;
                }
            }
            spe.putInt("GV_M_BORDER_PATH_LEN", this.m_strBorderPathList.size());
            if (!spe.commit()) {
                this.LOG.m384e("GlobalVariable_LOADED_META", "SaveGlobalVariableForever Fail");
            }
            SetEdit(false);
        }
    }

    public void ClearRestorePrefBorderPath() {
        this.m_strBorderPathList.clear();
    }

    public void SetBorderPathList(ArrayList<String> strBorderPathList) {
        if (strBorderPathList != null) {
            Iterator it = strBorderPathList.iterator();
            while (it.hasNext()) {
                this.m_strBorderPathList.add((String) it.next());
            }
        }
        SetEdit(true);
    }
}
