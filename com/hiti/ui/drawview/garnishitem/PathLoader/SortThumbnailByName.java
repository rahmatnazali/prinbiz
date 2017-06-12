package com.hiti.ui.drawview.garnishitem.PathLoader;

public class SortThumbnailByName implements Comparable<Object> {
    protected String m_id;
    protected String m_text;

    public SortThumbnailByName(String id) {
        this.m_text = id;
        int iStart = id.lastIndexOf("/");
        if (iStart < 0) {
            iStart = 0;
        }
        this.m_id = id.substring(iStart).substring(4);
    }

    public int compareTo(Object o) {
        return this.m_id.compareTo(((SortThumbnailByName) o).m_id);
    }

    public String toString() {
        return this.m_text;
    }
}
