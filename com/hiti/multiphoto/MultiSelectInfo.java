package com.hiti.multiphoto;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class MultiSelectInfo {
    public static final String MULTI_UPLOADING = "MULTI_UPLOADING";
    public static final int MULTI_UPLOAD_CLOSE = 2;
    public static final int MULTI_UPLOAD_OPEN = 1;
    protected static final String MULTI_UPLOAD_PHOTO_INFO = "pref_multi_upload_photo_info";
    protected static final String MULTI_UPLOAD_PHOTO_LENGTH = "MULTI_UPLOAD_PHOTO_LENGTH";
    protected static final String MULTI_UPLOAD_PHOTO_PATH = "MULTI_UPLOAD_PHOTO_PATH";
    protected static final String MULTI_UPLOAD_THUMBNAIL_ID = "MULTI_UPLOAD_THUMBNAIL_ID";
    protected static final String OPEN_MULTI_UPLOAD = "OPEN_MULTI_UPLOAD";
    Context m_Context;
    ArrayList<Long> m_MultiThumbnailIDList;
    ArrayList<String> m_MultiUploadPhotoPathList;
    SharedPreferences sp;

    private static SharedPreferences OpenPrefrence(Context context) {
        return context.getSharedPreferences(MULTI_UPLOAD_PHOTO_INFO, 0);
    }

    public void ClearInfo() {
        Editor spe = this.sp.edit();
        spe.clear();
        spe.commit();
    }

    public MultiSelectInfo(Context context) {
        this.sp = null;
        this.m_MultiUploadPhotoPathList = null;
        this.m_MultiThumbnailIDList = null;
        this.m_Context = null;
        this.m_Context = context;
        this.sp = context.getSharedPreferences(MULTI_UPLOAD_PHOTO_INFO, 0);
        this.m_MultiUploadPhotoPathList = new ArrayList();
        this.m_MultiThumbnailIDList = new ArrayList();
    }

    public void RestorePhotoList() {
        int Length = this.sp.getInt(MULTI_UPLOAD_PHOTO_LENGTH, 0);
        this.m_MultiUploadPhotoPathList.clear();
        this.m_MultiThumbnailIDList.clear();
        for (int i = 0; i < Length; i += MULTI_UPLOAD_OPEN) {
            this.m_MultiUploadPhotoPathList.add(this.sp.getString(MULTI_UPLOAD_PHOTO_PATH + i, XmlPullParser.NO_NAMESPACE));
            this.m_MultiThumbnailIDList.add(Long.valueOf(this.sp.getLong(MULTI_UPLOAD_THUMBNAIL_ID + i, -1)));
        }
    }

    public void GetMultiPhotoList(ArrayList<String> strPathList, ArrayList<Long> strIDList) {
        Iterator it = this.m_MultiUploadPhotoPathList.iterator();
        while (it.hasNext()) {
            strPathList.add((String) it.next());
        }
        it = this.m_MultiThumbnailIDList.iterator();
        while (it.hasNext()) {
            strIDList.add((Long) it.next());
        }
    }

    public void SetMultiPhotoList(ArrayList<String> strPhotoPathList, ArrayList<Long> strTumbnailIDList) {
        this.m_MultiUploadPhotoPathList.clear();
        this.m_MultiThumbnailIDList.clear();
        Iterator it = strPhotoPathList.iterator();
        while (it.hasNext()) {
            this.m_MultiUploadPhotoPathList.add((String) it.next());
        }
        it = strTumbnailIDList.iterator();
        while (it.hasNext()) {
            this.m_MultiThumbnailIDList.add((Long) it.next());
        }
    }

    public void SaveSelection() {
        Editor spe = this.sp.edit();
        int state = this.sp.getInt(OPEN_MULTI_UPLOAD, -1);
        int length = this.m_MultiUploadPhotoPathList.size();
        spe.clear();
        spe.putInt(MULTI_UPLOAD_PHOTO_LENGTH, length);
        spe.putInt(OPEN_MULTI_UPLOAD, state);
        for (int i = 0; i < length; i += MULTI_UPLOAD_OPEN) {
            spe.putString(MULTI_UPLOAD_PHOTO_PATH + i, (String) this.m_MultiUploadPhotoPathList.get(i));
            spe.putLong(MULTI_UPLOAD_THUMBNAIL_ID + i, ((Long) this.m_MultiThumbnailIDList.get(i)).longValue());
        }
        spe.commit();
    }

    public String GetItem(int index) {
        if (this.sp.getInt(MULTI_UPLOAD_PHOTO_LENGTH, 0) == 0) {
            return null;
        }
        return (String) this.m_MultiUploadPhotoPathList.get(index);
    }

    public void RemoveItem(int index) {
        if (this.sp.getInt(MULTI_UPLOAD_PHOTO_LENGTH, 0) >= MULTI_UPLOAD_OPEN) {
            this.m_MultiUploadPhotoPathList.remove(index);
            this.m_MultiThumbnailIDList.remove(index);
            SaveSelection();
        }
    }

    public int GetListSize() {
        return this.m_MultiThumbnailIDList.size();
    }
}
