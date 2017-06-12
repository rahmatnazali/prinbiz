package com.hiti.ui.drawview.garnishitem.PathLoader;

import android.util.Log;
import java.io.File;

public class SortThumbnailByDate implements Comparable<Object> {
    protected String m_id;
    protected String m_text;

    public SortThumbnailByDate(String id) {
        this.m_text = id;
        File file = new File(id);
        Log.e("lastModDate", String.valueOf(file.lastModified()));
        this.m_id = String.valueOf(file.lastModified());
    }

    public int compareTo(Object o) {
        return this.m_id.compareTo(((SortThumbnailByDate) o).m_id);
    }

    public String toString() {
        return this.m_text;
    }
}
