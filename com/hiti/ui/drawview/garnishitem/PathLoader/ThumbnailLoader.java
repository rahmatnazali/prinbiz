package com.hiti.ui.drawview.garnishitem.PathLoader;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import com.hiti.ui.drawview.garnishitem.security.GarnishSecurity;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public abstract class ThumbnailLoader extends AsyncTask<String, String, Object> {
    public static int LOAD_TYPE_ALBUM = 0;
    public static int LOAD_TYPE_PHOTO = 0;
    public static int LOAD_TYPE_THUMBNAIL_NAME_FROM_ASSETS = 0;
    public static int LOAD_TYPE_THUMBNAIL_NAME_FROM_SDCARD = 0;
    public static int LOAD_TYPE_THUMBNAIL_NAME_FROM_SDCARD_AND_ASSETS = 0;
    private static final String NEW_FILE_NAME_EDIT = "HITI_EDIT";
    private LogManager LOG;
    protected Context m_Context;
    protected LoadFinishListener m_LoadFinishListener;
    protected GarnishSecurity m_Security;
    private boolean m_boStop;
    private int m_iLoadType;
    private ArrayList<Long> m_lEditPhotoIDList;
    private ArrayList<Long> m_lPhotoIDList;
    private String m_strAlbumID;
    private ArrayList<String> m_strAlbumIDList;
    private String m_strAlbumName;
    private ArrayList<String> m_strAlbumNameList;
    private ArrayList<String> m_strPhotoPathList;
    private String m_strThumbnailPathFromAssets;
    private String m_strThumbnailPathFromSDCard;
    protected ArrayList<String> m_strThumbnailPathList;

    public abstract void BeforeLoadFinish();

    public abstract void LoadFinish();

    static {
        LOAD_TYPE_ALBUM = 0;
        LOAD_TYPE_PHOTO = 1;
        LOAD_TYPE_THUMBNAIL_NAME_FROM_ASSETS = 2;
        LOAD_TYPE_THUMBNAIL_NAME_FROM_SDCARD = 3;
        LOAD_TYPE_THUMBNAIL_NAME_FROM_SDCARD_AND_ASSETS = 4;
    }

    public ThumbnailLoader(Context context, GarnishSecurity security, LoadFinishListener loadFinishListener) {
        this.m_Context = null;
        this.m_strAlbumNameList = null;
        this.m_strAlbumIDList = null;
        this.m_strPhotoPathList = null;
        this.m_lPhotoIDList = null;
        this.m_lEditPhotoIDList = null;
        this.m_strAlbumName = null;
        this.m_strAlbumID = null;
        this.m_strThumbnailPathList = null;
        this.m_strThumbnailPathFromAssets = null;
        this.m_strThumbnailPathFromSDCard = null;
        this.m_boStop = false;
        this.m_iLoadType = -1;
        this.LOG = null;
        this.m_LoadFinishListener = null;
        this.m_Security = null;
        this.m_Context = context;
        this.LOG = new LogManager(0);
        this.m_iLoadType = -1;
        this.m_LoadFinishListener = loadFinishListener;
        this.m_Security = security;
    }

    public void LoadAlbum(ArrayList<String> strAlbumNameList, ArrayList<String> strAlbumIDList, ArrayList<String> strPhotoPathList, ArrayList<Long> lPhotoIDList) {
        this.m_strAlbumNameList = strAlbumNameList;
        this.m_strAlbumIDList = strAlbumIDList;
        this.m_strPhotoPathList = strPhotoPathList;
        this.m_lPhotoIDList = lPhotoIDList;
        this.m_iLoadType = LOAD_TYPE_ALBUM;
    }

    public void LoadPhoto(ArrayList<String> strPhotoPathList, ArrayList<Long> lPhotoIDList, String strAlbumName, String strAlbumID) {
        this.m_strPhotoPathList = strPhotoPathList;
        this.m_lPhotoIDList = lPhotoIDList;
        this.m_strAlbumName = strAlbumName;
        this.m_strAlbumID = strAlbumID;
        this.m_iLoadType = LOAD_TYPE_PHOTO;
    }

    public void LoadPhoto(ArrayList<String> strPhotoPathList, ArrayList<Long> lPhotoIDList, ArrayList<Long> lEditPhotoIDList, String strAlbumName, String strAlbumID) {
        this.m_strPhotoPathList = strPhotoPathList;
        this.m_lPhotoIDList = lPhotoIDList;
        this.m_lEditPhotoIDList = lEditPhotoIDList;
        this.m_strAlbumName = strAlbumName;
        this.m_strAlbumID = strAlbumID;
        this.m_iLoadType = LOAD_TYPE_PHOTO;
    }

    public void LoadThumbnailFromAssets(String strThumbnailPathFromAssets, ArrayList<String> strThumbnailPathList) {
        this.m_strThumbnailPathList = strThumbnailPathList;
        this.m_strThumbnailPathFromAssets = strThumbnailPathFromAssets;
        this.m_iLoadType = LOAD_TYPE_THUMBNAIL_NAME_FROM_ASSETS;
    }

    public void LoadThumbnailFromSDCard(String strThumbnailPathFromSDCard, ArrayList<String> strThumbnailPathList) {
        this.m_strThumbnailPathFromSDCard = strThumbnailPathFromSDCard;
        this.m_strThumbnailPathList = strThumbnailPathList;
        this.m_iLoadType = LOAD_TYPE_THUMBNAIL_NAME_FROM_SDCARD;
    }

    public void LoadThumbnailFromSDCardAndAssets(String strThumbnailPathFromSDCard, String strThumbnailPathFromAssets, ArrayList<String> strThumbnailPathList) {
        this.m_strThumbnailPathFromSDCard = strThumbnailPathFromSDCard;
        this.m_strThumbnailPathFromAssets = strThumbnailPathFromAssets;
        this.m_strThumbnailPathList = strThumbnailPathList;
        this.m_iLoadType = LOAD_TYPE_THUMBNAIL_NAME_FROM_SDCARD_AND_ASSETS;
    }

    private void GetAlbums() {
        String strAlbumName;
        String strAlbumID;
        int i;
        ArrayList<Boolean> boDeleteAlbumList = new ArrayList();
        String[] projection = new String[]{"bucket_display_name", "bucket_id"};
        Cursor cursor = this.m_Context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, projection, null, null, "bucket_display_name");
        int bucketNameColumnIndex = cursor.getColumnIndex("bucket_display_name");
        int bucketIDColumnIndex = cursor.getColumnIndex("bucket_id");
        if (cursor.moveToFirst()) {
            while (!this.m_boStop) {
                strAlbumName = cursor.getString(bucketNameColumnIndex);
                strAlbumID = cursor.getString(bucketIDColumnIndex);
                if (!this.m_strAlbumIDList.contains(strAlbumID)) {
                    this.m_strAlbumIDList.add(strAlbumID);
                    this.m_strAlbumNameList.add(strAlbumName);
                }
                if (!cursor.moveToNext()) {
                }
            }
            cursor.close();
            return;
        }
        cursor.close();
        for (i = 0; i < this.m_strAlbumNameList.size(); i++) {
            boDeleteAlbumList.add(Boolean.valueOf(true));
        }
        i = 0;
        while (i < this.m_strAlbumNameList.size()) {
            boolean boGetOnePhoto = false;
            if (!this.m_boStop) {
                strAlbumName = (String) this.m_strAlbumNameList.get(i);
                strAlbumID = (String) this.m_strAlbumIDList.get(i);
                String strQuery = XmlPullParser.NO_NAMESPACE;
                if (strAlbumName != null) {
                    projection = new String[]{"_id", "_data", "_size"};
                    cursor = this.m_Context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, projection, "bucket_display_name = \"" + strAlbumName + "\"" + " AND " + "bucket_id = \"" + strAlbumID + "\"", null, "date_added DESC");
                    if (cursor.moveToFirst()) {
                        while (!this.m_boStop) {
                            if (SearchFileExist(cursor)) {
                                boGetOnePhoto = true;
                            } else if (!cursor.moveToNext()) {
                            }
                        }
                        cursor.close();
                        return;
                    }
                    cursor.close();
                    if (!boGetOnePhoto) {
                        boDeleteAlbumList.set(i, Boolean.valueOf(false));
                    }
                    i++;
                } else {
                    return;
                }
            }
            return;
        }
        for (i = boDeleteAlbumList.size() - 1; i >= 0; i--) {
            if (!((Boolean) boDeleteAlbumList.get(i)).booleanValue()) {
                this.m_strAlbumNameList.remove(i);
                this.m_strAlbumIDList.remove(i);
                Log.i("Remove Thumbnail", "No picture");
            }
        }
    }

    private void GetPhotos() {
        this.LOG.m384e("GetPhotos AlbumName", String.valueOf(this.m_strAlbumName));
        this.LOG.m384e("GetPhotos AlbumID", String.valueOf(this.m_strAlbumID));
        if (this.m_strAlbumName != null && !this.m_strAlbumName.equals("All") && this.m_strAlbumID != null) {
            String orderBy = "date_added DESC";
            String[] projection = new String[]{"_id", "_data", "_size"};
            Cursor cursor = this.m_Context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, projection, "bucket_display_name = \"" + this.m_strAlbumName + "\"" + " AND " + "bucket_id = \"" + this.m_strAlbumID + "\"", null, "date_added DESC");
            int idIndex = cursor.getColumnIndex("_id");
            int pathIndex = cursor.getColumnIndex("_data");
            int sizeIndex = cursor.getColumnIndex("_size");
            if (cursor.moveToFirst()) {
                while (!this.m_boStop) {
                    long lID = cursor.getLong(idIndex);
                    String strPath = cursor.getString(pathIndex);
                    if (cursor.getInt(sizeIndex) <= 0) {
                        this.LOG.m384e("Size == 0", XmlPullParser.NO_NAMESPACE + String.valueOf(lID));
                    } else if (this.m_lPhotoIDList.contains(Long.valueOf(lID))) {
                        this.LOG.m384e("Duplicate id", XmlPullParser.NO_NAMESPACE + String.valueOf(lID));
                    } else {
                        this.m_lPhotoIDList.add(Long.valueOf(lID));
                        this.m_strPhotoPathList.add(strPath);
                        if (this.m_lEditPhotoIDList != null && strPath.contains(NEW_FILE_NAME_EDIT)) {
                            this.m_lEditPhotoIDList.add(Long.valueOf(lID));
                        }
                    }
                    if (!cursor.moveToNext()) {
                        break;
                    }
                }
            }
            cursor.close();
        }
    }

    private ArrayList<String> GetThumbnailNameFromAssets() {
        if (this.m_strThumbnailPathFromAssets == null) {
            return null;
        }
        try {
            return new ArrayList(Arrays.asList(this.m_Context.getAssets().list(this.m_strThumbnailPathFromAssets)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void GetThumbnailFromAssets() {
        ArrayList<String> strThumbnailPathList = GetThumbnailNameFromAssets();
        if (this.m_strThumbnailPathList != null) {
            this.m_strThumbnailPathList.clear();
        }
        if (strThumbnailPathList != null) {
            Iterator it = strThumbnailPathList.iterator();
            while (it.hasNext()) {
                this.m_strThumbnailPathList.add(this.m_strThumbnailPathFromAssets + "/" + ((String) it.next()));
            }
            strThumbnailPathList.clear();
        }
    }

    private ArrayList<String> GetThumbnailNameFromSDCard() {
        if (this.m_strThumbnailPathFromSDCard == null) {
            return null;
        }
        String[] fileList = new File(this.m_strThumbnailPathFromSDCard).list();
        if (fileList != null) {
            return new ArrayList(Arrays.asList(fileList));
        }
        return null;
    }

    private void GetThumbnailFromSDCard() {
        ArrayList<String> strThumbnailPathList = GetThumbnailNameFromSDCard();
        if (this.m_strThumbnailPathList != null) {
            this.m_strThumbnailPathList.clear();
        }
        if (strThumbnailPathList != null) {
            Iterator it = strThumbnailPathList.iterator();
            while (it.hasNext()) {
                this.m_strThumbnailPathList.add(this.m_strThumbnailPathFromSDCard + "/" + ((String) it.next()));
            }
            strThumbnailPathList.clear();
        }
    }

    private void GetThumbnailFromSDCardAndAssets() {
        Iterator it;
        if (this.m_strThumbnailPathList != null) {
            this.m_strThumbnailPathList.clear();
        }
        ArrayList<String> strThumbnailPathList = GetThumbnailNameFromSDCard();
        if (strThumbnailPathList != null) {
            it = strThumbnailPathList.iterator();
            while (it.hasNext()) {
                this.m_strThumbnailPathList.add(this.m_strThumbnailPathFromSDCard + "/" + ((String) it.next()));
            }
            strThumbnailPathList.clear();
        }
        strThumbnailPathList = GetThumbnailNameFromAssets();
        if (strThumbnailPathList != null) {
            it = strThumbnailPathList.iterator();
            while (it.hasNext()) {
                this.m_strThumbnailPathList.add(this.m_strThumbnailPathFromAssets + "/" + ((String) it.next()));
            }
            strThumbnailPathList.clear();
        }
    }

    protected Object doInBackground(String... para) {
        this.LOG.m384e("doInBackground", String.valueOf(this.m_iLoadType));
        if (this.m_iLoadType == LOAD_TYPE_ALBUM) {
            GetAlbums();
        } else if (this.m_iLoadType == LOAD_TYPE_PHOTO) {
            GetPhotos();
        } else if (this.m_iLoadType == LOAD_TYPE_THUMBNAIL_NAME_FROM_ASSETS) {
            GetThumbnailFromAssets();
        } else if (this.m_iLoadType == LOAD_TYPE_THUMBNAIL_NAME_FROM_SDCARD) {
            GetThumbnailFromSDCard();
        } else if (this.m_iLoadType == LOAD_TYPE_THUMBNAIL_NAME_FROM_SDCARD_AND_ASSETS) {
            GetThumbnailFromSDCardAndAssets();
        }
        if (!this.m_boStop) {
            BeforeLoadFinish();
        }
        return null;
    }

    public void onProgressUpdate(String... progress) {
    }

    protected void onPostExecute(Object result) {
        if (!this.m_boStop) {
            LoadFinish();
        }
    }

    private boolean SearchFileExist(Cursor cursor) {
        int idIndex = cursor.getColumnIndex("_id");
        int pathIndex = cursor.getColumnIndex("_data");
        int sizeIndex = cursor.getColumnIndex("_size");
        String strPath = cursor.getString(pathIndex);
        long lID = cursor.getLong(idIndex);
        int iSize = cursor.getInt(sizeIndex);
        if (!FileUtility.FileExist(strPath) || iSize <= 0) {
            return false;
        }
        this.m_strPhotoPathList.add(strPath);
        this.m_lPhotoIDList.add(Long.valueOf(lID));
        this.LOG.m385i("Add album path", String.valueOf(strPath));
        return true;
    }

    public void Stop() {
        this.m_boStop = true;
    }

    public ArrayList<String> GetPathFromWhat(boolean boSDCard) {
        ArrayList<String> strThumbnailPathList = new ArrayList();
        if (!((this.m_strThumbnailPathFromSDCard == null && boSDCard) || this.m_strThumbnailPathList == null)) {
            Iterator it = this.m_strThumbnailPathList.iterator();
            while (it.hasNext()) {
                String strThumbnailPath = (String) it.next();
                if (strThumbnailPath.contains(this.m_strThumbnailPathFromSDCard) == boSDCard) {
                    strThumbnailPathList.add(strThumbnailPath);
                }
            }
        }
        return strThumbnailPathList;
    }

    public String GetThumbnailPathFromSDCard() {
        return this.m_strThumbnailPathFromSDCard;
    }

    public void SetThumbnailPathFromSDCard(String strThumbnailPathFromSDCard) {
        this.m_strThumbnailPathFromSDCard = strThumbnailPathFromSDCard;
    }
}
