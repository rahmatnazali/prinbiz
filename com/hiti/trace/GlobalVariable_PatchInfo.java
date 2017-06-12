package com.hiti.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class GlobalVariable_PatchInfo extends BaseGlobalVariable {
    private int m_Patch002;

    public GlobalVariable_PatchInfo(Context context) {
        super(context);
        this.m_Patch002 = 0;
        this.m_fsp = context.getSharedPreferences("pref_fgv_patch_info", 0);
        this.LOG.m385i("GlobalVariable_PatchInfo", "Create");
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_Patch002 = this.m_fsp.getInt("GV_M_PATCH_002", 0);
            this.LOG.m385i("m_Patch002", String.valueOf(this.m_Patch002));
            SetEdit(false);
            this.LOG.m385i("GlobalVariable_PatchInfo", "RestoreGlobalVariable");
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_PatchInfo", "RestoreGlobalVariable Fail");
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
                spe.putInt("GV_M_PATCH_002", this.m_Patch002);
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_PatchInfo", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_PatchInfo", "SaveGlobalVariableForever");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_PatchInfo", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public int GetPatch002() {
        return this.m_Patch002;
    }

    public void SetPatch002(int patch002) {
        this.m_Patch002 = patch002;
        SetEdit(true);
    }
}
