package com.hiti.utility;

import com.hiti.ui.drawview.garnishitem.FilterColorValue;

public class CollageIdEditInfo {
    FilterColorValue m_FilterColorValue;
    boolean m_bIsCollageVertical;
    int m_iCollageInfoGroupNum;
    int m_iCollageInfoHeight;
    int m_iCollageInfoId;
    int m_iCollageInfoWidth;
    String m_strCollageEditPath;
    String m_strCollageXMLPath;

    public CollageIdEditInfo() {
        this.m_strCollageEditPath = null;
        this.m_strCollageXMLPath = null;
        this.m_iCollageInfoWidth = 0;
        this.m_iCollageInfoHeight = 0;
        this.m_iCollageInfoId = 0;
        this.m_iCollageInfoGroupNum = 0;
        this.m_bIsCollageVertical = false;
        this.m_FilterColorValue = null;
        this.m_FilterColorValue = new FilterColorValue();
    }

    public void SetFilterColorValue(FilterColorValue m_FilterColorValue) {
        this.m_FilterColorValue = m_FilterColorValue;
    }

    public FilterColorValue GetFilterColorValue() {
        return this.m_FilterColorValue;
    }

    public void SetCollageEditPath(String m_strCollageEditPath) {
        this.m_strCollageEditPath = m_strCollageEditPath;
    }

    public String GetCollageEditPath() {
        return this.m_strCollageEditPath;
    }

    public void SetCollageXMLPath(String m_strCollageXMLPath) {
        this.m_strCollageXMLPath = m_strCollageXMLPath;
    }

    public String GetCollageXMLPath() {
        return this.m_strCollageXMLPath;
    }

    public void SetCollageInfoWidth(int m_iCollageInfoWidth) {
        this.m_iCollageInfoWidth = m_iCollageInfoWidth;
    }

    public int GetCollageInfoWidth() {
        return this.m_iCollageInfoWidth;
    }

    public void SetCollageInfoHeight(int m_iCollageInfoHeight) {
        this.m_iCollageInfoHeight = m_iCollageInfoHeight;
    }

    public int GetCollageInfoHeight() {
        return this.m_iCollageInfoHeight;
    }

    public void SetCollageInfoId(int m_iCollageInfoId) {
        this.m_iCollageInfoId = m_iCollageInfoId;
    }

    public int GetCollageInfoId() {
        return this.m_iCollageInfoId;
    }

    public void SetCollageInfoGroupNum(int m_iCollageInfoGroupNum) {
        this.m_iCollageInfoGroupNum = m_iCollageInfoGroupNum;
    }

    public int GetCollageInfoGroupNum() {
        return this.m_iCollageInfoGroupNum;
    }

    public void SetCollageInfoIsVertical(boolean m_bIsCollageVertical) {
        this.m_bIsCollageVertical = m_bIsCollageVertical;
    }

    public boolean GetCollageInfoIsVertical() {
        return this.m_bIsCollageVertical;
    }
}
