package com.hiti.ui.drawview.garnishitem.PathLoader;

import android.content.Context;
import android.util.Log;
import com.hiti.ui.drawview.garnishitem.security.GarnishSecurity;
import com.hiti.utility.PringoConvenientConst;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class CollageLoader extends ThumbnailLoader {
    public CollageLoader(Context context, GarnishSecurity security, LoadFinishListener loadFinishListener) {
        super(context, security, loadFinishListener);
    }

    public void BeforeLoadFinish() {
        if (GetThumbnailPathFromSDCard() != null) {
            ArrayList<String> SDPathList = GetPathFromWhat(true);
            Iterator it = SDPathList.iterator();
            while (it.hasNext()) {
                this.m_strThumbnailPathList.remove((String) it.next());
            }
            this.m_Security.CheckExpire(SDPathList);
            SDPathList.clear();
            SDPathList = this.m_Security.GetAllVerifyItemName(GetThumbnailPathFromSDCard().replace(PringoConvenientConst.CONFIG, PringoConvenientConst.THUMB) + "/");
            ArrayList<SortThumbnailByDate> ByDateSDPathList = new ArrayList();
            if (SDPathList.size() > 0) {
                it = SDPathList.iterator();
                while (it.hasNext()) {
                    ByDateSDPathList.add(new SortThumbnailByDate((String) it.next()));
                }
                Collections.sort(ByDateSDPathList);
                it = ByDateSDPathList.iterator();
                while (it.hasNext()) {
                    SortThumbnailByDate ByDateSDPath = (SortThumbnailByDate) it.next();
                    this.m_strThumbnailPathList.add(0, ByDateSDPath.toString());
                    Log.e("DOWNLOAD Collage", ByDateSDPath.toString());
                }
                SDPathList.clear();
            }
        }
    }

    public void LoadFinish() {
        if (this.m_LoadFinishListener != null) {
            this.m_LoadFinishListener.LoadFinish();
        }
    }
}
