package com.hiti.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_SelectPhotoInfo extends BaseGlobalVariable {
    private ArrayList<Integer> m_SelectPhotoCopyList;
    private ArrayList<Long> m_SelectPhotoIDList;
    private ArrayList<String> m_SelectPhotoPathList;
    private int m_iCount;
    private String m_strAlbumName;

    public GlobalVariable_SelectPhotoInfo(Context context) {
        super(context);
        this.m_strAlbumName = XmlPullParser.NO_NAMESPACE;
        this.m_iCount = 0;
        this.m_SelectPhotoIDList = null;
        this.m_SelectPhotoPathList = null;
        this.m_SelectPhotoCopyList = null;
        this.m_sp = context.getSharedPreferences("pref_gv_select_photo_info", 0);
        this.m_SelectPhotoIDList = new ArrayList();
        this.m_SelectPhotoPathList = new ArrayList();
        this.m_SelectPhotoCopyList = new ArrayList();
        this.LOG.m385i("GlobalVariable_SelectPhotoInfo", "Create");
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_strAlbumName = XmlPullParser.NO_NAMESPACE;
            this.m_SelectPhotoIDList.clear();
            this.m_SelectPhotoPathList.clear();
            this.m_SelectPhotoCopyList.clear();
            this.m_iCount = 0;
            this.m_strAlbumName = this.m_sp.getString("GV_M_ALBUM_NAME", XmlPullParser.NO_NAMESPACE);
            int i = 0;
            Long lSelectPhotoID = Long.valueOf(this.m_sp.getLong("GV_M_SELECT_PHOTO_ID_LIST" + 0, -1));
            while (lSelectPhotoID.longValue() != -1) {
                this.m_SelectPhotoIDList.add(lSelectPhotoID);
                i++;
                lSelectPhotoID = Long.valueOf(this.m_sp.getLong("GV_M_SELECT_PHOTO_ID_LIST" + i, -1));
            }
            i = 0;
            String strSelectPhotoPath = this.m_sp.getString("GV_M_SELECT_PHOTO_PATH_LIST" + 0, XmlPullParser.NO_NAMESPACE);
            while (strSelectPhotoPath != XmlPullParser.NO_NAMESPACE) {
                this.m_SelectPhotoPathList.add(strSelectPhotoPath);
                i++;
                strSelectPhotoPath = this.m_sp.getString("GV_M_SELECT_PHOTO_PATH_LIST" + i, XmlPullParser.NO_NAMESPACE);
            }
            i = 0;
            int iCopy = this.m_sp.getInt("GV_M_SELECT_PHOTO_COPY_LIST" + 0, -1);
            while (iCopy != -1) {
                this.m_SelectPhotoCopyList.add(Integer.valueOf(iCopy));
                i++;
                iCopy = this.m_sp.getInt("GV_M_SELECT_PHOTO_COPY_LIST" + i, -1);
            }
            this.m_iCount = this.m_sp.getInt("GV_M_ICOUNT", 0);
            SetEdit(false);
            this.LOG.m385i("GlobalVariable_SelectPhotoInfo", "RestoreGlobalVariable");
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_SelectPhotoInfo", "RestoreGlobalVariable Fail");
            e.printStackTrace();
        }
    }

    public void SaveGlobalVariable() {
        if (IsEdit()) {
            try {
                int i;
                Editor spe = this.m_sp.edit();
                spe.clear();
                spe.putString("GV_M_ALBUM_NAME", this.m_strAlbumName);
                for (i = 0; i < this.m_SelectPhotoIDList.size(); i++) {
                    spe.putLong("GV_M_SELECT_PHOTO_ID_LIST" + i, ((Long) this.m_SelectPhotoIDList.get(i)).longValue());
                }
                for (i = 0; i < this.m_SelectPhotoPathList.size(); i++) {
                    spe.putString("GV_M_SELECT_PHOTO_PATH_LIST" + i, (String) this.m_SelectPhotoPathList.get(i));
                }
                for (i = 0; i < this.m_SelectPhotoCopyList.size(); i++) {
                    spe.putInt("GV_M_SELECT_PHOTO_COPY_LIST" + i, ((Integer) this.m_SelectPhotoCopyList.get(i)).intValue());
                }
                spe.putInt("GV_M_ICOUNT", this.m_iCount);
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_SelectPhotoInfo", "SaveGlobalVariable Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_SelectPhotoInfo", "SaveGlobalVariable");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_SelectPhotoInfo", "SaveGlobalVariable Fail");
                ex.printStackTrace();
            }
        }
    }

    public void SaveGlobalVariableForever() {
    }

    public String GetAlbumName() {
        return this.m_strAlbumName;
    }

    public void SetAlbumName(String strAlbumName) {
        this.m_strAlbumName = strAlbumName;
        SetEdit(true);
    }

    public int GetCount() {
        return this.m_iCount;
    }

    private void SetCount(int iCount) {
        this.m_iCount = iCount;
        SetEdit(true);
    }

    private void AddCount() {
        this.m_iCount++;
        SetEdit(true);
    }

    public Long GetSelectPhotoID(int iIndex) {
        return (Long) this.m_SelectPhotoIDList.get(iIndex);
    }

    private void AddSelectPhotoID(long PhotoID) {
        this.m_SelectPhotoIDList.add(Long.valueOf(PhotoID));
        SetEdit(true);
    }

    private void ClearSelectPhotoIDList() {
        this.m_SelectPhotoIDList.clear();
        SetEdit(true);
    }

    public String GetSelectPhotoPath(int iIndex) {
        return (String) this.m_SelectPhotoPathList.get(iIndex);
    }

    private void AddSelectPhotoPath(String SelectPhotoPath) {
        this.m_SelectPhotoPathList.add(SelectPhotoPath);
        SetEdit(true);
    }

    private void ClearSelectPhotoPathList() {
        this.m_SelectPhotoPathList.clear();
        SetEdit(true);
    }

    public Integer GetSelectPhotoCopy(int iIndex) {
        return (Integer) this.m_SelectPhotoCopyList.get(iIndex);
    }

    private void AddSelectPhotoCopyList(Integer SelectPhotoCopy) {
        this.m_SelectPhotoCopyList.add(SelectPhotoCopy);
        SetEdit(true);
    }

    private void ClearSelectPhotoCopyList() {
        this.m_SelectPhotoCopyList.clear();
        SetEdit(true);
    }

    public void AddSelectPhotoInfo(long lID, String SelectPhotoPath, int iCopy) {
        AddSelectPhotoID(lID);
        AddSelectPhotoPath(SelectPhotoPath);
        AddSelectPhotoCopyList(Integer.valueOf(iCopy));
        AddCount();
        SetEdit(true);
    }

    public void ClearSelectPhotoInfo() {
        ClearSelectPhotoIDList();
        ClearSelectPhotoPathList();
        ClearSelectPhotoCopyList();
        SetCount(0);
        SetEdit(true);
    }
}
