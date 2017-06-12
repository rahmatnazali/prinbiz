package com.hiti.ui.drawview.garnishitem.PathLoader;

import android.content.Context;
import com.hiti.ui.drawview.garnishitem.security.GarnishSecurity;
import com.hiti.utility.FileUtility;
import com.hiti.utility.PringoConvenientConst;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GarnishCatalogLoader extends ThumbnailLoader {
    public GarnishCatalogLoader(Context context, GarnishSecurity security, LoadFinishListener loadFinishListener) {
        super(context, security, loadFinishListener);
    }

    public void BeforeLoadFinish() {
        ArrayList<String> SDPathList = GetPathFromWhat(true);
        Iterator it = SDPathList.iterator();
        while (it.hasNext()) {
            this.m_strThumbnailPathList.remove((String) it.next());
        }
        this.m_Security.CheckExpire(SDPathList);
        SDPathList.clear();
        SDPathList = this.m_Security.GetAllVerifyItemName(FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.C_GARNISH_PATH + "/" + PringoConvenientConst.CATTHUMB + "/");
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
        SDPathList.clear();
    }

    public void LoadFinish() {
        if (this.m_LoadFinishListener != null) {
            this.m_LoadFinishListener.LoadFinish();
        }
    }
}
