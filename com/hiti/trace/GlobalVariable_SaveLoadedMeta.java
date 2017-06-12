package com.hiti.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.hiti.jscommand.JSCommand;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_SaveLoadedMeta extends BaseGlobalVariable_Prinbiz {
    boolean m_bAlbumType;
    boolean m_bIsNoData;
    ArrayList<Boolean> m_bLoadedSizeList;
    boolean m_bSetFstPosition;
    ArrayList<Integer> m_iAlbumIDList;
    int m_iAlbumId;
    ArrayList<Integer> m_iAlbumSIDList;
    int m_iFstPos;
    ArrayList<Integer> m_iLoadIndexList;
    ArrayList<Integer> m_iLoadedIndexList;
    ArrayList<Integer> m_iPhotoIdList;
    ArrayList<String> m_strLoadedNameList;
    ArrayList<String> m_strLoadedPathList;

    public GlobalVariable_SaveLoadedMeta(Context context, String strAlbumId) {
        super(context);
        this.m_iPhotoIdList = null;
        this.m_iAlbumIDList = null;
        this.m_iAlbumSIDList = null;
        this.m_iLoadIndexList = null;
        this.m_iLoadedIndexList = null;
        this.m_bLoadedSizeList = null;
        this.m_strLoadedPathList = null;
        this.m_strLoadedNameList = null;
        this.m_bIsNoData = false;
        this.m_bAlbumType = false;
        this.m_bSetFstPosition = false;
        this.m_iFstPos = -1;
        this.m_iAlbumId = 0;
        this.LOG.m384e("GlobalVariable_SaveLoadedMeta_id", String.valueOf(strAlbumId));
        if (strAlbumId == null) {
            strAlbumId = "pref_gv_album_loaded_meta";
            this.m_bAlbumType = true;
            this.m_iLoadIndexList = new ArrayList();
            this.m_iAlbumIDList = new ArrayList();
            this.m_iAlbumSIDList = new ArrayList();
        } else {
            strAlbumId = "pref_gv_gallery_loaded_meta_" + strAlbumId;
            this.m_iPhotoIdList = new ArrayList();
            this.m_bLoadedSizeList = new ArrayList();
            this.m_iLoadedIndexList = new ArrayList();
        }
        this.m_sp = context.getSharedPreferences(strAlbumId, 0);
        this.m_strLoadedPathList = new ArrayList();
        this.m_strLoadedNameList = new ArrayList();
    }

    public void RestoreGlobalVariable() {
        int i;
        int pathLen = this.m_sp.getInt("GV_M_LOADED_PATH_LEN", 0);
        int nameLen = this.m_sp.getInt("GV_M_LOADED_NAME_LEN", 0);
        int idLen;
        int indexLen;
        if (this.m_bAlbumType) {
            idLen = this.m_sp.getInt("GV_M_ALBUM_ID_LEN", 0);
            int sidLen = this.m_sp.getInt("GV_M_ALBUM_SID_LEN", 0);
            indexLen = this.m_sp.getInt("GV_M_LOADED_INDEX_LEN", 0);
            this.m_iAlbumIDList.clear();
            for (i = 0; i < idLen; i++) {
                int id = this.m_sp.getInt("GV_M_ALBUM_ID" + i, 0);
                this.m_iAlbumIDList.add(Integer.valueOf(id));
                this.LOG.m385i("restore ID" + i, String.valueOf(id));
            }
            this.m_iAlbumSIDList.clear();
            for (i = 0; i < sidLen; i++) {
                this.m_iAlbumSIDList.add(Integer.valueOf(this.m_sp.getInt("GV_M_ALBUM_SID" + i, 0)));
            }
            this.m_iLoadIndexList.clear();
            for (i = 0; i < indexLen; i++) {
                this.m_iLoadIndexList.add(Integer.valueOf(this.m_sp.getInt("GV_M_LOADED_INDEX" + i, 0)));
            }
        } else {
            idLen = this.m_sp.getInt("GV_M_GAL_ID_LEN", 0);
            indexLen = this.m_sp.getInt("GV_M_LOADED_INDEX_LEN", 0);
            int sizeLen = this.m_sp.getInt("GV_M_LOADED_SIZE_LEN", 0);
            if (pathLen == 0) {
                this.m_bIsNoData = true;
                return;
            }
            this.m_bIsNoData = false;
            this.LOG.m385i("restore m_bIsNoData", XmlPullParser.NO_NAMESPACE + this.m_bIsNoData);
            this.m_iPhotoIdList.clear();
            for (i = 0; i < idLen; i++) {
                this.m_iPhotoIdList.add(Integer.valueOf(this.m_sp.getInt("GV_M_GAL_ID" + i, 0)));
            }
            this.m_iLoadedIndexList.clear();
            for (i = 0; i < indexLen; i++) {
                this.m_iLoadedIndexList.add(Integer.valueOf(this.m_sp.getInt("GV_M_LOADED_INDEX" + i, 0)));
            }
            this.m_bLoadedSizeList.clear();
            for (i = 0; i < sizeLen; i++) {
                Boolean size = Boolean.valueOf(this.m_sp.getBoolean("GV_M_LOADED_SIZE" + i, false));
                this.m_bLoadedSizeList.add(size);
                this.LOG.m385i("restore SIZE" + i, String.valueOf(size));
            }
        }
        this.m_strLoadedNameList.clear();
        for (i = 0; i < nameLen; i++) {
            String name = this.m_sp.getString("GV_M_LOADED_NAME" + i, XmlPullParser.NO_NAMESPACE);
            this.m_strLoadedNameList.add(name);
            this.LOG.m385i("restore_name" + i, name);
        }
        this.m_strLoadedPathList.clear();
        for (i = 0; i < pathLen; i++) {
            this.m_strLoadedPathList.add(this.m_sp.getString("GV_M_LOADED_PATH" + i, XmlPullParser.NO_NAMESPACE));
        }
        SetEdit(false);
    }

    public boolean IsNoData() {
        if (this.m_bIsNoData) {
            return true;
        }
        return false;
    }

    public int GetVisionFstPosition(int iAlbumId) {
        int iFstPos = this.m_sp.getInt("GV_M_VISION_FIRST_POSITION" + iAlbumId, -1);
        if (iFstPos != -1) {
            SetEdit(true);
            Editor spe = this.m_sp.edit();
            spe.putInt("GV_M_VISION_FIRST_POSITION" + iAlbumId, 0);
            spe.commit();
            SetEdit(false);
        }
        return iFstPos;
    }

    public void GetIdAndLoadedPathList(ArrayList<Integer> iAllIdList, ArrayList<String> strLoadedPathList) {
        if (iAllIdList != null) {
            iAllIdList.clear();
            Iterator it = this.m_iPhotoIdList.iterator();
            while (it.hasNext()) {
                iAllIdList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        if (strLoadedPathList != null) {
            strLoadedPathList.clear();
            Iterator it2 = this.m_strLoadedPathList.iterator();
            while (it2.hasNext()) {
                strLoadedPathList.add((String) it2.next());
            }
        }
    }

    public void GetLoadedIndexAndSizeList(ArrayList<Integer> iLoadedIndexList, ArrayList<Boolean> bLoadedSizeList) {
        Iterator it;
        if (iLoadedIndexList != null) {
            iLoadedIndexList.clear();
            it = this.m_iLoadedIndexList.iterator();
            while (it.hasNext()) {
                iLoadedIndexList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        if (bLoadedSizeList != null) {
            bLoadedSizeList.clear();
            it = this.m_bLoadedSizeList.iterator();
            while (it.hasNext()) {
                bLoadedSizeList.add(Boolean.valueOf(((Boolean) it.next()).booleanValue()));
            }
        }
    }

    public void GetLoadedNameAndIndexList(ArrayList<String> strLoadedNameList, ArrayList<Integer> iLoadedIndexList) {
        if (strLoadedNameList != null) {
            strLoadedNameList.clear();
            Iterator it = this.m_strLoadedNameList.iterator();
            while (it.hasNext()) {
                strLoadedNameList.add((String) it.next());
            }
        }
        if (iLoadedIndexList != null) {
            iLoadedIndexList.clear();
            Iterator it2 = this.m_iLoadIndexList.iterator();
            while (it2.hasNext()) {
                iLoadedIndexList.add(Integer.valueOf(((Integer) it2.next()).intValue()));
            }
        }
    }

    public void GetIdAndSIDList(ArrayList<Integer> iAlbumIdList, ArrayList<Integer> iAlbumSIDList) {
        Iterator it;
        if (iAlbumIdList != null) {
            iAlbumIdList.clear();
            it = this.m_iAlbumIDList.iterator();
            while (it.hasNext()) {
                int iID = ((Integer) it.next()).intValue();
                iAlbumIdList.add(Integer.valueOf(iID));
                this.LOG.m384e("GetIdAndSIDList", JSCommand.SERVER_RESPONSE_SUB_ATTR_FILE_ID + iID);
            }
        }
        if (iAlbumSIDList != null) {
            iAlbumSIDList.clear();
            it = this.m_iAlbumSIDList.iterator();
            while (it.hasNext()) {
                iAlbumSIDList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
    }

    public void ClearAlbumMeta() {
        this.m_iAlbumIDList.clear();
        this.m_iAlbumSIDList.clear();
        this.m_strLoadedNameList.clear();
        this.m_strLoadedPathList.clear();
        this.m_iLoadIndexList.clear();
    }

    public void ClearPhotoMeta() {
        this.m_iPhotoIdList.clear();
        this.m_strLoadedPathList.clear();
        this.m_iLoadedIndexList.clear();
        this.m_bLoadedSizeList.clear();
        this.m_strLoadedNameList.clear();
    }

    public void SaveGlobalVariable() {
        SaveGlobalVariableForever();
    }

    public void SaveGlobalVariableForever() {
        if (IsEdit()) {
            try {
                Editor spe = this.m_sp.edit();
                if (this.m_bSetFstPosition) {
                    spe.putInt("GV_M_VISION_FIRST_POSITION" + this.m_iAlbumId, this.m_iFstPos);
                    this.m_bSetFstPosition = false;
                    spe.commit();
                    SetEdit(false);
                    return;
                }
                int i;
                for (i = 0; i < this.m_strLoadedNameList.size(); i++) {
                    spe.putString("GV_M_LOADED_NAME" + i, (String) this.m_strLoadedNameList.get(i));
                    this.LOG.m385i("save_name" + i, "=" + ((String) this.m_strLoadedNameList.get(i)));
                }
                spe.putInt("GV_M_LOADED_NAME_LEN", this.m_strLoadedNameList.size());
                if (this.m_bAlbumType) {
                    for (i = 0; i < this.m_iAlbumIDList.size(); i++) {
                        spe.putInt("GV_M_ALBUM_ID" + i, ((Integer) this.m_iAlbumIDList.get(i)).intValue());
                        this.LOG.m385i("save_id" + i, "=" + this.m_iAlbumIDList.get(i));
                    }
                    for (i = 0; i < this.m_iAlbumSIDList.size(); i++) {
                        spe.putInt("GV_M_ALBUM_SID" + i, ((Integer) this.m_iAlbumSIDList.get(i)).intValue());
                    }
                    for (i = 0; i < this.m_iLoadIndexList.size(); i++) {
                        spe.putInt("GV_M_LOADED_INDEX" + i, ((Integer) this.m_iLoadIndexList.get(i)).intValue());
                        this.LOG.m385i("save_index" + i, "=" + this.m_iLoadIndexList.get(i));
                    }
                    spe.putInt("GV_M_LOADED_INDEX_LEN", this.m_iLoadIndexList.size());
                    spe.putInt("GV_M_ALBUM_ID_LEN", this.m_iAlbumIDList.size());
                    spe.putInt("GV_M_ALBUM_SID_LEN", this.m_iAlbumSIDList.size());
                } else {
                    for (i = 0; i < this.m_iPhotoIdList.size(); i++) {
                        spe.putInt("GV_M_GAL_ID" + i, ((Integer) this.m_iPhotoIdList.get(i)).intValue());
                    }
                    for (i = 0; i < this.m_iLoadedIndexList.size(); i++) {
                        spe.putInt("GV_M_LOADED_INDEX" + i, ((Integer) this.m_iLoadedIndexList.get(i)).intValue());
                    }
                    for (i = 0; i < this.m_bLoadedSizeList.size(); i++) {
                        spe.putBoolean("GV_M_LOADED_SIZE" + i, ((Boolean) this.m_bLoadedSizeList.get(i)).booleanValue());
                    }
                    spe.putInt("GV_M_LOADED_INDEX_LEN", this.m_iLoadedIndexList.size());
                    spe.putInt("GV_M_GAL_ID_LEN", this.m_iPhotoIdList.size());
                    spe.putInt("GV_M_LOADED_SIZE_LEN", this.m_bLoadedSizeList.size());
                }
                for (i = 0; i < this.m_strLoadedPathList.size(); i++) {
                    spe.putString("GV_M_LOADED_PATH" + i, (String) this.m_strLoadedPathList.get(i));
                    this.LOG.m385i("save_path" + i, "=" + ((String) this.m_strLoadedPathList.get(i)));
                }
                spe.putInt("GV_M_LOADED_PATH_LEN", this.m_strLoadedPathList.size());
                this.LOG.m385i("save_path", "=" + this.m_strLoadedPathList.size());
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_LOADED_META", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void SetAllIdAndLoadedPathList(ArrayList<Integer> iAllIdList, ArrayList<String> strLoadedPathList) {
        if (!this.m_bAlbumType) {
            this.m_iPhotoIdList.clear();
        }
        this.m_strLoadedPathList.clear();
        if (iAllIdList != null) {
            Iterator it = iAllIdList.iterator();
            while (it.hasNext()) {
                this.m_iPhotoIdList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        if (strLoadedPathList != null) {
            Iterator it2 = strLoadedPathList.iterator();
            while (it2.hasNext()) {
                this.m_strLoadedPathList.add((String) it2.next());
            }
        }
        SetEdit(true);
    }

    public void SetLoadedIndexAndSizeList(ArrayList<Integer> iLoadedIndexList, ArrayList<Boolean> bLoadedSizeList) {
        Iterator it;
        this.m_iLoadedIndexList.clear();
        this.m_bLoadedSizeList.clear();
        if (iLoadedIndexList != null) {
            it = iLoadedIndexList.iterator();
            while (it.hasNext()) {
                this.m_iLoadedIndexList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        if (bLoadedSizeList != null) {
            it = bLoadedSizeList.iterator();
            while (it.hasNext()) {
                this.m_bLoadedSizeList.add(Boolean.valueOf(((Boolean) it.next()).booleanValue()));
            }
        }
        SetEdit(true);
    }

    public void SetLoadedNameAndIndexList(ArrayList<String> iLoadedNameList, ArrayList<Integer> iAlbumIndexList) {
        this.m_strLoadedNameList.clear();
        if (iAlbumIndexList != null) {
            this.m_iLoadIndexList.clear();
        }
        if (iLoadedNameList != null) {
            Iterator it = iLoadedNameList.iterator();
            while (it.hasNext()) {
                this.m_strLoadedNameList.add((String) it.next());
            }
        }
        if (iAlbumIndexList != null) {
            Iterator it2 = iAlbumIndexList.iterator();
            while (it2.hasNext()) {
                this.m_iLoadIndexList.add(Integer.valueOf(((Integer) it2.next()).intValue()));
            }
        }
        SetEdit(true);
    }

    public void SetAlbumIDandSIDList(ArrayList<Integer> iAlbumIdList, ArrayList<Integer> iAlbumSIDList) {
        Iterator it;
        this.m_iAlbumIDList.clear();
        this.m_iAlbumSIDList.clear();
        if (iAlbumIdList != null) {
            it = iAlbumIdList.iterator();
            while (it.hasNext()) {
                this.m_iAlbumIDList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        if (iAlbumSIDList != null) {
            it = iAlbumSIDList.iterator();
            while (it.hasNext()) {
                this.m_iAlbumSIDList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        SetEdit(true);
    }

    public void SetVisionFirstPosition(int iFstPos, int iAlbumId) {
        this.m_bSetFstPosition = true;
        this.m_iFstPos = iFstPos;
        this.m_iAlbumId = iAlbumId;
        SetEdit(true);
    }
}
