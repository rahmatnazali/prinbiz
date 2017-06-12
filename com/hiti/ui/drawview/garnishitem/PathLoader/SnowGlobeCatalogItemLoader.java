package com.hiti.ui.drawview.garnishitem.PathLoader;

import android.content.Context;
import com.hiti.ui.drawview.garnishitem.security.GarnishSecurity;

public class SnowGlobeCatalogItemLoader extends ThumbnailLoader {
    public SnowGlobeCatalogItemLoader(Context context, GarnishSecurity security, LoadFinishListener loadFinishListener) {
        super(context, security, loadFinishListener);
    }

    public void BeforeLoadFinish() {
    }

    public void LoadFinish() {
        if (this.m_LoadFinishListener != null) {
            this.m_LoadFinishListener.LoadFinish();
        }
    }
}
