package com.hiti.ui.drawview.garnishitem.PathLoader;

import android.content.Context;
import android.util.Log;
import com.hiti.ui.drawview.garnishitem.security.GarnishSecurity;
import com.hiti.ui.drawview.garnishitem.utility.GarnishItemUtility;
import com.hiti.utility.FileUtility;
import com.hiti.utility.PringoConvenientConst;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class BorderExpandLoader extends ThumbnailLoader {
    boolean m_boVertival;

    public BorderExpandLoader(Context context, GarnishSecurity security, LoadFinishListener loadFinishListener, boolean boVertival) {
        super(context, security, loadFinishListener);
        this.m_boVertival = true;
        this.m_boVertival = boVertival;
    }

    public void BeforeLoadFinish() {
        ArrayList<String> SDPathList = GetPathFromWhat(true);
        Iterator it = SDPathList.iterator();
        while (it.hasNext()) {
            String SDPath = (String) it.next();
            this.m_strThumbnailPathList.remove(SDPath);
        }
        this.m_Security.CheckExpire(SDPathList);
        SDPathList.clear();
        String strPath = FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.V_BORDER_PATH + "/" + PringoConvenientConst.CATTHUMB + "/";
        if (!this.m_boVertival) {
            strPath = FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.H_BORDER_PATH + "/" + PringoConvenientConst.CATTHUMB + "/";
        }
        SDPathList = this.m_Security.GetAllVerifyItemName(strPath);
        if (SDPathList.size() > 0) {
            ArrayList<SortThumbnailByDate> ByDateSDPathList = new ArrayList();
            it = SDPathList.iterator();
            while (it.hasNext()) {
                ByDateSDPathList.add(new SortThumbnailByDate((String) it.next()));
            }
            Collections.sort(ByDateSDPathList);
            ArrayList<String> strSDThumbnailPathList = new ArrayList();
            it = ByDateSDPathList.iterator();
            while (it.hasNext()) {
                strSDThumbnailPathList.add(0, ((SortThumbnailByDate) it.next()).toString());
            }
            SDPathList.clear();
            it = strSDThumbnailPathList.iterator();
            while (it.hasNext()) {
                String strCatalogThumbnailPath = (String) it.next();
                String strBorderFolder = GarnishItemUtility.GetCatalogFolderName(strCatalogThumbnailPath);
                String strBorderFolderPath = FileUtility.GetFolderFullPath(strCatalogThumbnailPath).replace("/catthumb", XmlPullParser.NO_NAMESPACE);
                strBorderFolderPath = strBorderFolderPath + "/" + strBorderFolder + "/" + PringoConvenientConst.THUMB;
                String[] fileList = new File(strBorderFolderPath).list();
                if (fileList != null) {
                    Iterator it2 = new ArrayList(Arrays.asList(fileList)).iterator();
                    while (it2.hasNext()) {
                        String strThumbnailName = (String) it2.next();
                        SDPathList.add(strBorderFolderPath + "/" + strThumbnailName);
                        Log.e("strBorderFolderPath 999", strBorderFolderPath + "/" + strThumbnailName);
                    }
                } else {
                    return;
                }
            }
            it = SDPathList.iterator();
            while (it.hasNext()) {
                String path = (String) it.next();
                this.m_strThumbnailPathList.add(0, path);
            }
            SDPathList.clear();
        }
    }

    public void LoadFinish() {
        if (this.m_LoadFinishListener != null) {
            this.m_LoadFinishListener.LoadFinish();
        }
    }
}
