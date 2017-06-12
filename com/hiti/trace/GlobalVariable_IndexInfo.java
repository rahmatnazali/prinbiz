package com.hiti.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class GlobalVariable_IndexInfo extends BaseGlobalVariable {
    private int mSelectedFormat;
    private int mSelectedSize;
    private int mSelectedTag;

    public GlobalVariable_IndexInfo(Context context) {
        super(context);
        this.mSelectedFormat = 0;
        this.mSelectedSize = 0;
        this.mSelectedTag = 0;
        this.m_fsp = context.getSharedPreferences("pref_fgv_patch_info", 0);
        this.LOG.m385i("GlobalVariable_PatchInfo", "Create");
    }

    public void RestoreGlobalVariable() {
        try {
            this.mSelectedFormat = this.m_fsp.getInt("GV_M_INDEX_FORMAT", 0);
            this.mSelectedSize = this.m_fsp.getInt("GV_M_INDEX_SIZE", 0);
            this.mSelectedTag = this.m_fsp.getInt("GV_M_INDEX_TAG", 0);
            this.LOG.m385i("mSelectedFormat", String.valueOf(this.mSelectedFormat));
            SetEdit(false);
            this.LOG.m385i("GlobalVariable_IndexInfo", "RestoreGlobalVariable");
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
                spe.putInt("GV_M_INDEX_FORMAT", this.mSelectedFormat);
                spe.putInt("GV_M_INDEX_SIZE", this.mSelectedSize);
                spe.putInt("GV_M_INDEX_TAG", this.mSelectedTag);
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

    public int GetSelectedFormat() {
        return this.mSelectedFormat;
    }

    public void SetSelectedFormat(int format) {
        this.mSelectedFormat = format;
        SetEdit(true);
    }

    public int GetSelectedSize() {
        return this.mSelectedSize;
    }

    public void SetSelectedSize(int size) {
        this.mSelectedSize = size;
        SetEdit(true);
    }

    public int GetSelectedTag() {
        return this.mSelectedTag;
    }

    public void SetSelectedTag(int tag) {
        this.mSelectedTag = tag;
        SetEdit(true);
    }
}
