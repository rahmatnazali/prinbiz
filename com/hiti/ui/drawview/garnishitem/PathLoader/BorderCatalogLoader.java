package com.hiti.ui.drawview.garnishitem.PathLoader;

import android.content.Context;
import android.util.Log;
import com.hiti.ui.drawview.garnishitem.security.GarnishSecurity;
import com.hiti.utility.FileUtility;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.Verify.SortType;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class BorderCatalogLoader extends ThumbnailLoader {
    SortType mSortType;
    boolean m_boVertival;

    public BorderCatalogLoader(Context context, GarnishSecurity security, LoadFinishListener loadFinishListener, boolean boVertival) {
        super(context, security, loadFinishListener);
        this.m_boVertival = true;
        this.mSortType = SortType.Date;
        this.m_boVertival = boVertival;
    }

    public void BeforeLoadFinish() {
        String strRootPath = null;
        if (this.m_LoadFinishListener != null) {
            strRootPath = this.m_LoadFinishListener.BeforeLoadFinish();
        }
        if (strRootPath == null) {
            strRootPath = PringoConvenientConst.V_BORDER_PATH;
        }
        BeforeLoadFinish(strRootPath);
    }

    public void SetSortType(SortType type) {
        this.mSortType = type;
        Log.i("SetSortType", String.valueOf(this.mSortType));
    }

    private void BeforeLoadFinish(String strRootPath) {
        ArrayList<String> SDPathList = GetPathFromWhat(true);
        Iterator it = SDPathList.iterator();
        while (it.hasNext()) {
            this.m_strThumbnailPathList.remove((String) it.next());
        }
        this.m_Security.CheckExpire(SDPathList);
        SDPathList.clear();
        String strPath = FileUtility.GetSDAppRootPath(this.m_Context) + File.separator + strRootPath + File.separator + PringoConvenientConst.CATTHUMB + "/";
        if (!this.m_boVertival) {
            strPath = FileUtility.GetSDAppRootPath(this.m_Context) + File.separator + strRootPath.replace(PringoConvenientConst.V_BORDER_PATH, PringoConvenientConst.H_BORDER_PATH) + File.separator + PringoConvenientConst.CATTHUMB + "/";
        }
        SDPathList = this.m_Security.GetAllVerifyItemName(strPath);
        SortAssetListByName(this.m_strThumbnailPathList);
        if (this.mSortType == SortType.Date) {
            ArrayList<SortThumbnailByDate> ByDateSDPathList = new ArrayList();
            it = SDPathList.iterator();
            while (it.hasNext()) {
                ByDateSDPathList.add(new SortThumbnailByDate((String) it.next()));
            }
            Collections.sort(ByDateSDPathList);
            it = ByDateSDPathList.iterator();
            while (it.hasNext()) {
                this.m_strThumbnailPathList.add(0, ((SortThumbnailByDate) it.next()).toString());
            }
        }
        if (this.mSortType == SortType.Name) {
            ArrayList<SortThumbnailByName> ByNameSDPathList = new ArrayList();
            it = SDPathList.iterator();
            while (it.hasNext()) {
                ByNameSDPathList.add(new SortThumbnailByName((String) it.next()));
            }
            Collections.sort(ByNameSDPathList);
            it = ByNameSDPathList.iterator();
            while (it.hasNext()) {
                this.m_strThumbnailPathList.add(0, ((SortThumbnailByName) it.next()).toString());
            }
        }
        SDPathList.clear();
    }

    void SortAssetListByName(ArrayList<String> sourceList) {
        ArrayList<SortThumbnailByName> ByNameSDPathList = new ArrayList();
        Iterator it = sourceList.iterator();
        while (it.hasNext()) {
            ByNameSDPathList.add(new SortThumbnailByName((String) it.next()));
        }
        Collections.sort(ByNameSDPathList);
        sourceList.clear();
        it = ByNameSDPathList.iterator();
        while (it.hasNext()) {
            sourceList.add(0, ((SortThumbnailByName) it.next()).toString());
        }
    }

    public void LoadFinish() {
        if (this.m_LoadFinishListener != null) {
            this.m_LoadFinishListener.LoadFinish();
        }
    }
}
