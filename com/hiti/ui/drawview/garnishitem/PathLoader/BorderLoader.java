package com.hiti.ui.drawview.garnishitem.PathLoader;

import android.content.Context;
import com.hiti.ui.drawview.garnishitem.security.GarnishSecurity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class BorderLoader extends ThumbnailLoader {
    public BorderLoader(Context context, GarnishSecurity security, LoadFinishListener loadFinishListener) {
        super(context, security, loadFinishListener);
    }

    public void BeforeLoadFinish() {
        ArrayList<SortThumbnailByName> ByNameList = new ArrayList();
        Iterator it = this.m_strThumbnailPathList.iterator();
        while (it.hasNext()) {
            ByNameList.add(new SortThumbnailByName((String) it.next()));
        }
        Collections.sort(ByNameList);
        this.m_strThumbnailPathList.clear();
        it = ByNameList.iterator();
        while (it.hasNext()) {
            this.m_strThumbnailPathList.add(new String(((SortThumbnailByName) it.next()).toString()));
        }
        ByNameList.clear();
    }

    public void LoadFinish() {
        if (this.m_LoadFinishListener != null) {
            this.m_LoadFinishListener.LoadFinish();
        }
    }
}
