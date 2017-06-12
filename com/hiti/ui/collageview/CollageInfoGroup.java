package com.hiti.ui.collageview;

import android.content.Context;
import java.util.ArrayList;

public class CollageInfoGroup {
    public static final int RESOURCE_FROM_ASSEST = 0;
    public static final int RESOURCE_FROM_SDCARD = 1;
    BusinessCardInfo m_BusinessCardInfo;
    private ArrayList<CollageInfo> m_CollageInfoManager;
    private Context m_Context;
    GreetingCardInfo m_GreetingCardInfo;
    private int m_iAddPhotoButtonHeight;
    private int m_iAddPhotoButtonWidth;
    private int m_iCollageNumbers;
    private int m_iPhotoHeight;
    private int m_iPhotoWidth;
    private int m_iResourceFromWhere;
    private String m_strBackgroundPhotoPath;
    private String m_strCollageTemplatePath;
    private String m_strDemoBackgroundPhotoPath;
    private String m_strForegroundPhotoPath;
    private String m_strMetalPhotoPath;
    private String m_strRegionTablePath;

    public CollageInfoGroup(Context context) {
        this.m_CollageInfoManager = null;
        this.m_BusinessCardInfo = null;
        this.m_GreetingCardInfo = null;
        this.m_iPhotoWidth = RESOURCE_FROM_ASSEST;
        this.m_iPhotoHeight = RESOURCE_FROM_ASSEST;
        this.m_strBackgroundPhotoPath = null;
        this.m_strDemoBackgroundPhotoPath = null;
        this.m_strForegroundPhotoPath = null;
        this.m_strMetalPhotoPath = null;
        this.m_strRegionTablePath = null;
        this.m_iCollageNumbers = RESOURCE_FROM_ASSEST;
        this.m_strCollageTemplatePath = null;
        this.m_iResourceFromWhere = RESOURCE_FROM_ASSEST;
        this.m_Context = context;
        this.m_CollageInfoManager = new ArrayList();
    }

    public void SetPhotoWidth(int iPhotoWidth) {
        this.m_iPhotoWidth = iPhotoWidth;
    }

    public void SetPhotoHeight(int iPhotoHeight) {
        this.m_iPhotoHeight = iPhotoHeight;
    }

    public void SetAddPhotoButtonWidth(int iAddPhotoButtonWidth) {
        this.m_iAddPhotoButtonWidth = iAddPhotoButtonWidth;
    }

    public void SetAddPhotoButtonHeight(int iAddPhotoButtonHeight) {
        this.m_iAddPhotoButtonHeight = iAddPhotoButtonHeight;
    }

    public void SetBackgroundPhotoPath(String strBackgroundPhotoPath) {
        this.m_strBackgroundPhotoPath = strBackgroundPhotoPath;
    }

    public void SetForegroundPhotoPath(String strForegroundPhotoPath) {
        this.m_strForegroundPhotoPath = strForegroundPhotoPath;
    }

    public void SetMetalPhotoPath(String strMetalPhotoPath) {
        this.m_strMetalPhotoPath = strMetalPhotoPath;
    }

    public void SetRegionTablePath(String strRegionTablePath) {
        this.m_strRegionTablePath = strRegionTablePath;
    }

    public void SetCollageNumbers(int iCollageNumbers) {
        this.m_iCollageNumbers = iCollageNumbers;
    }

    public void SetCollageTemplatePath(String strCollageTemplatePath) {
        this.m_strCollageTemplatePath = strCollageTemplatePath;
    }

    public void SetResourceFromWhere(int iFromWhere) {
        this.m_iResourceFromWhere = iFromWhere;
    }

    public int GetPhotoWidth() {
        return this.m_iPhotoWidth;
    }

    public int GetPhotoHeight() {
        return this.m_iPhotoHeight;
    }

    public int GetAddPhotoButtonWidth() {
        return this.m_iAddPhotoButtonWidth;
    }

    public int GetAddPhotoButtonHeight() {
        return this.m_iAddPhotoButtonHeight;
    }

    public String GetBackgroundPhotoPath() {
        return this.m_strBackgroundPhotoPath;
    }

    public String GetForegroundPhotoPath() {
        return this.m_strForegroundPhotoPath;
    }

    public String GetMetalPhotoPath() {
        return this.m_strMetalPhotoPath;
    }

    public String GetRegionTablePath() {
        return this.m_strRegionTablePath;
    }

    public int GetCollageNumbers() {
        return this.m_iCollageNumbers;
    }

    public String GetDemoBackgroundPhotoPath() {
        return this.m_strDemoBackgroundPhotoPath;
    }

    public void SetDemoBackgroundPhotoPath(String strDemoBackgroundPhotoPath) {
        this.m_strDemoBackgroundPhotoPath = strDemoBackgroundPhotoPath;
    }

    public CollageInfo GetCollageInfo(int i) {
        if (i < this.m_CollageInfoManager.size()) {
            return (CollageInfo) this.m_CollageInfoManager.get(i);
        }
        return null;
    }

    public void AddCollageInfo(CollageInfo collageInfo) {
        this.m_CollageInfoManager.add(collageInfo);
    }

    public String GetCollageTemplatePath() {
        return this.m_strCollageTemplatePath;
    }

    public int GetResourceFromWhere() {
        return this.m_iResourceFromWhere;
    }

    public BusinessCardInfo GetBusinessCardInfo() {
        return this.m_BusinessCardInfo;
    }

    public void SetBusinessCardInfo(BusinessCardInfo businessCardInfo) {
        this.m_BusinessCardInfo = businessCardInfo;
    }

    public GreetingCardInfo GetGreetingCardInfo() {
        return this.m_GreetingCardInfo;
    }

    public void SetGreetingCardInfo(GreetingCardInfo greetingCardInfo) {
        this.m_GreetingCardInfo = greetingCardInfo;
    }

    public void Clear() {
        this.m_CollageInfoManager.clear();
    }
}
