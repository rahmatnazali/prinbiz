package com.hiti.ui.drawview.garnishitem.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.PointF;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.RelativeLayout.LayoutParams;
import com.hiti.HitiChunk.HitiChunk.ChunkType;
import com.hiti.HitiChunk.HitiChunkUtility;
import com.hiti.ImageFilter.ImageFilter;
import com.hiti.ImageFilter.ImageFilter.IMAGE_FILTER_TYPE;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.ui.drawview.DrawView;
import com.hiti.ui.drawview.garnishitem.GarnishItem;
import com.hiti.ui.drawview.garnishitem.parser.GarnishItemXMLCreator;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.dialog.CreateWaitDialog;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import com.hiti.utility.resource.ResourceId;
import com.hiti.utility.resource.ResourceId.Page;
import java.io.BufferedInputStream;
import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class MakeEditPhoto {
    private static final String PRINBIZ_EDIT_XML = "_EM.xml";
    LogManager LOG;
    private String TAG;
    ResourceId mRID;
    Context m_Context;
    DrawView m_EditDrawView;
    int m_EditDrawViewBackgroundColor;
    EditMeta m_EditMeta;
    EditMetaUtility m_EditMetaUtility;
    ShowMSGDialog m_ErrorDialog;
    ImageFilter m_ImageFilter;
    MakePhotoListener m_MakePhotoListener;
    float m_ViewScale;
    CreateWaitDialog m_WaitingDialog;
    ArrayList<Boolean> m_bIsEditedList;
    ArrayList<Boolean> m_bIsVerticalList;
    boolean m_bStop;
    boolean m_boFirstLoadPhotoVertical;
    boolean m_boLastVertical;
    int m_iEditDrawViewHeight;
    int m_iEditDrawViewWidth;
    int m_iMaxH;
    int m_iMaxW;
    int m_iOriMaxH;
    int m_iOriMaxW;
    String m_strEditFile;
    ArrayList<String> m_strEditPathList;
    String m_strImgRoot;
    ArrayList<String> m_strSaveIMGList;
    String m_strTmpRoot;
    String m_strXMLPath;
    ArrayList<String> m_strXMLPathList;

    class SaveImage extends AsyncTask<Socket, String, String> {
        private int iPos;
        Socket mSocket;
        private String strSavePath;

        public SaveImage(int iPos, String strOriPath) {
            this.iPos = -1;
            this.strSavePath = null;
            this.mSocket = null;
            this.iPos = iPos;
            String strSaveName = FileUtility.GetNewName(FileUtility.ChangeFileExt(FileUtility.GetFileName(strOriPath), PringoConvenientConst.PRINBIZ_BORDER_EXT), PringoConvenientConst.NEW_FILE_MAKE_EDIT);
            this.strSavePath = MakeEditPhoto.this.m_strImgRoot + "/" + strSaveName.substring(strSaveName.indexOf("HITI") - 4);
            MakeEditPhoto.this.LOG.m385i("SaveImage ImgPath: " + iPos, String.valueOf(strOriPath));
        }

        protected String doInBackground(Socket... socket) {
            this.mSocket = socket[0];
            MakeEditPhoto.this.LOG.m386v("doInBackground iPos", String.valueOf(this.iPos));
            BitmapMonitor.TrySystemGC();
            BitmapMonitorResult bmr = MakeEditPhoto.this.m_EditDrawView.GetEditPhoto(MakeEditPhoto.this.m_iMaxW, MakeEditPhoto.this.m_iMaxH, false);
            if (!bmr.IsSuccess()) {
                return bmr.GetError(MakeEditPhoto.this.m_Context);
            }
            Bitmap editBmp = bmr.GetBitmap();
            if (!SaveBitmap2File(this.strSavePath, editBmp, CompressFormat.PNG)) {
                return MakeEditPhoto.this.m_Context.getString(MakeEditPhoto.this.mRID.R_STRING_ERROR_TITLT);
            }
            if (editBmp != null) {
                editBmp.recycle();
                BitmapMonitor.TrySystemGC();
            }
            if (MakeEditPhoto.this.m_EditDrawView.HaveGSGarnish()) {
                bmr = MakeEditPhoto.this.m_EditDrawView.GetGSGarnish(MakeEditPhoto.this.m_iMaxW, MakeEditPhoto.this.m_iMaxH, false);
                if (!bmr.IsSuccess()) {
                    return bmr.GetError(MakeEditPhoto.this.m_Context);
                }
                Bitmap maskBmp = bmr.GetBitmap();
                HitiChunkUtility.AddHiTiChunk(this.strSavePath, maskBmp, ChunkType.PNG);
                if (maskBmp != null) {
                    maskBmp.recycle();
                    BitmapMonitor.TrySystemGC();
                }
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (!MakeEditPhoto.this.m_bStop) {
                MakeEditPhoto.this.LOG.m386v("onPostExecute result", XmlPullParser.NO_NAMESPACE + String.valueOf(result));
                if (result != null) {
                    MakeEditPhoto.this.ShowErrorMSG(result);
                    return;
                }
                MakeEditPhoto.this.m_EditDrawView.SetEdit(false);
                MakeEditPhoto.this.MakeOneEditPhotoDone(this.mSocket, this.iPos, this.strSavePath);
            }
        }

        private boolean SaveBitmap2File(String strSavePath, Bitmap bmp, CompressFormat format) {
            return FileUtility.SaveBitmap(strSavePath, bmp, format);
        }
    }

    /* renamed from: com.hiti.ui.drawview.garnishitem.utility.MakeEditPhoto.1 */
    class C08021 implements MSGListener {
        C08021() {
        }

        public void OKClick() {
            if (MakeEditPhoto.this.m_MakePhotoListener != null) {
                MakeEditPhoto.this.m_MakePhotoListener.onMakePhotoError();
            }
        }

        public void Close() {
        }

        public void CancelClick() {
        }
    }

    public MakeEditPhoto(Context context, int iScreenWidth) {
        this.m_strEditPathList = null;
        this.m_strSaveIMGList = null;
        this.m_strXMLPathList = null;
        this.m_bIsEditedList = null;
        this.m_bIsVerticalList = null;
        this.m_strTmpRoot = null;
        this.m_strImgRoot = null;
        this.m_EditMetaUtility = null;
        this.m_EditMeta = null;
        this.m_WaitingDialog = null;
        this.m_Context = null;
        this.m_ErrorDialog = null;
        this.m_MakePhotoListener = null;
        this.m_ImageFilter = null;
        this.m_iMaxW = 0;
        this.m_iMaxH = 0;
        this.m_iOriMaxW = 0;
        this.m_iOriMaxH = 0;
        this.m_EditDrawView = null;
        this.m_strEditFile = null;
        this.m_strXMLPath = null;
        this.m_iEditDrawViewWidth = 0;
        this.m_iEditDrawViewHeight = 0;
        this.m_ViewScale = 1.0f;
        this.m_boFirstLoadPhotoVertical = false;
        this.m_boLastVertical = false;
        this.m_bStop = false;
        this.m_EditDrawViewBackgroundColor = 0;
        this.TAG = null;
        this.mRID = null;
        this.LOG = null;
        this.m_Context = context;
        this.TAG = context.getClass().getSimpleName();
        this.m_strEditPathList = new ArrayList();
        this.m_bIsEditedList = new ArrayList();
        this.m_strXMLPathList = new ArrayList();
        this.m_bIsVerticalList = new ArrayList();
        this.m_strSaveIMGList = new ArrayList();
        this.m_ImageFilter = new ImageFilter(context);
        this.m_EditMetaUtility = new EditMetaUtility(context);
        this.m_EditMeta = new EditMeta(EditMetaUtility.GetSrcRoute(context));
        this.m_WaitingDialog = new CreateWaitDialog(context);
        this.LOG = new LogManager(0);
        this.mRID = new ResourceId(context, Page.MakeEditPhoto);
        this.m_iEditDrawViewWidth = iScreenWidth;
        this.m_iEditDrawViewHeight = iScreenWidth;
        this.m_EditDrawViewBackgroundColor = this.mRID.R_COLOR_EDIT_DRAW_VIEW_BACKGROUND;
    }

    public void SetStop(boolean stop) {
        this.m_bStop = stop;
    }

    public void SetPhotoPath(ArrayList<String> strEditPathList) {
        if (strEditPathList != null) {
            Iterator it = strEditPathList.iterator();
            while (it.hasNext()) {
                this.m_strEditPathList.add((String) it.next());
                this.m_strSaveIMGList.add(XmlPullParser.NO_NAMESPACE);
            }
        }
        this.LOG.m383d(this.TAG, "SetPhotoPath: " + this.m_strEditPathList);
    }

    public void SetIsEditAndIsVerticalAndXMLpath(ArrayList<Boolean> bIsEditedList, ArrayList<Boolean> bIsVerticalList, ArrayList<String> strXMLPathList) {
        Iterator it;
        this.m_bIsEditedList.clear();
        this.m_strXMLPathList.clear();
        this.m_bIsVerticalList.clear();
        if (bIsEditedList != null) {
            it = bIsEditedList.iterator();
            while (it.hasNext()) {
                this.m_bIsEditedList.add(Boolean.valueOf(((Boolean) it.next()).booleanValue()));
            }
        }
        if (bIsVerticalList != null) {
            it = bIsVerticalList.iterator();
            while (it.hasNext()) {
                this.m_bIsVerticalList.add(Boolean.valueOf(((Boolean) it.next()).booleanValue()));
            }
        }
        if (strXMLPathList != null) {
            Iterator it2 = strXMLPathList.iterator();
            while (it2.hasNext()) {
                this.m_strXMLPathList.add((String) it2.next());
            }
        }
    }

    public void SetListener(MakePhotoListener makePhotoListener) {
        this.m_MakePhotoListener = makePhotoListener;
        int[] format = this.m_MakePhotoListener.SetPrintout(this.m_EditMetaUtility.GetServerPaperType());
        this.m_iOriMaxW = format[0];
        this.m_iOriMaxH = format[1];
    }

    void GetMaxSizeConfig() {
        this.m_iMaxW = this.m_iOriMaxW;
        this.m_iMaxH = this.m_iOriMaxH;
    }

    private void GetEditFileAndXML(int iPos) {
        this.m_strEditFile = (String) this.m_strEditPathList.get(iPos);
        this.m_strXMLPath = this.m_strTmpRoot + String.valueOf(this.m_strEditFile.subSequence(this.m_strEditFile.lastIndexOf(File.separator), this.m_strEditFile.lastIndexOf(".")).toString()) + PRINBIZ_EDIT_XML;
    }

    void CalculateUIScale(String strPath) {
        this.m_ViewScale = ((float) this.m_iMaxH) / ((float) this.m_iEditDrawViewHeight);
        if (DecideAutoRotate(Uri.parse("file://" + strPath))) {
            if (this.m_iMaxW < this.m_iMaxH) {
                int temp = this.m_iMaxW;
                this.m_iMaxW = this.m_iMaxH;
                this.m_iMaxH = temp;
            }
            this.m_boFirstLoadPhotoVertical = false;
            return;
        }
        this.m_boFirstLoadPhotoVertical = true;
    }

    public boolean DecideAutoRotate(Uri uri) {
        if (BitmapMonitor.IsVertical(this.m_Context, uri)) {
            return false;
        }
        return true;
    }

    private void InitEditDrawView() {
        LayoutParams params1 = new LayoutParams(-2, -2);
        if (this.m_EditDrawView == null) {
            this.m_EditDrawView = new DrawView(this.m_Context);
        } else {
            this.m_EditDrawView.Clear();
        }
        params1.height = this.m_iEditDrawViewHeight;
        params1.width = this.m_iEditDrawViewWidth;
        this.m_EditDrawView.setLayoutParams(params1);
        int iResult = this.m_EditDrawView.InitDrawView(((float) this.m_iMaxW) / this.m_ViewScale, ((float) this.m_iMaxH) / this.m_ViewScale, (float) this.m_iEditDrawViewWidth, (float) this.m_iEditDrawViewHeight, this.m_boFirstLoadPhotoVertical, this.m_iOriMaxW, this.m_iOriMaxH);
        if (iResult != 0) {
            ShowErrorMSG(String.valueOf(BitmapMonitorResult.GetError(this.m_Context, iResult)));
            return;
        }
        this.m_EditDrawView.SetViewScale(this.m_ViewScale);
        this.m_EditDrawView.SetDrawViewListener(null);
        this.m_EditDrawView.setBackgroundColor(this.m_Context.getResources().getColor(this.m_EditDrawViewBackgroundColor));
    }

    private void PrepareDrawView(Socket socket, int iPos, String strPhotoPath) {
        GetMaxSizeConfig();
        GetEditFileAndXML(iPos);
        CalculateUIScale(this.m_strEditFile);
        InitEditDrawView();
        ModifyDrawView(iPos, strPhotoPath, socket);
    }

    private void ModifyDrawView(int iPos, String strPhotoPath, Socket socket) {
        this.LOG.m385i(this.TAG, "ModifyDrawView_" + iPos + "= " + String.valueOf(iPos));
        this.m_boLastVertical = ((Boolean) this.m_bIsVerticalList.get(iPos)).booleanValue();
        this.m_strXMLPath = (String) this.m_strXMLPathList.get(iPos);
        if (InitGarnishByLastEdit()) {
            new SaveImage(iPos, strPhotoPath).execute(new Socket[]{socket});
            return;
        }
        ShowWaitDialog(false);
    }

    boolean InitGarnishByLastEdit() {
        this.LOG.m385i(this.TAG, "InitGarnishByLastEdit");
        InitGarnish_Background();
        try {
            ArrayList GarnishList = GarnishItemXMLCreator.ReadGarnishXML(new BufferedInputStream(this.m_Context.getContentResolver().openInputStream(Uri.parse("file://" + this.m_strXMLPath))), this.m_Context, this.m_iMaxW, this.m_iMaxH);
            Iterator it = GarnishList.iterator();
            while (it.hasNext()) {
                GarnishItem garnishItem = (GarnishItem) it.next();
                if (!garnishItem.GetFilter().contains(GarnishItem.NON_FILTER)) {
                    IMAGE_FILTER_TYPE filterType = IMAGE_FILTER_TYPE.valueOf(garnishItem.GetFilter());
                    garnishItem.SetBackUpViewScaleBitmap();
                    SetImageFilter(garnishItem, filterType);
                }
            }
            if (this.m_boLastVertical != this.m_EditDrawView.IsVertical() && !RotateEditDrawViewWithoutContent()) {
                return false;
            }
            this.m_EditDrawView.AddGarnish(GarnishList);
            this.m_EditDrawView.SetEdit(false);
            return true;
        } catch (Exception e) {
            ShowErrorMSG(this.m_Context.getString(this.mRID.R_STRING_CREATE_BITMAP_OUT_OF_MEMORY));
            e.printStackTrace();
            return false;
        }
    }

    boolean InitGarnish_Background() {
        this.LOG.m385i(this.TAG, "InitGarnish_Background");
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(this.m_iMaxW, this.m_iMaxH, Config.ARGB_8888);
        if (bmr.IsSuccess()) {
            bmr.GetBitmap().eraseColor(this.m_Context.getResources().getColor(this.m_EditDrawViewBackgroundColor));
            if (this.m_EditDrawView.AddGarnish(bmr.GetBitmap(), new PointF(this.m_EditDrawView.GetViewWindowCenterX(), this.m_EditDrawView.GetViewWindowCenterY()), 0, null, 0) <= 0) {
                ShowErrorMSG(this.m_Context.getString(this.mRID.R_STRING_CREATE_BITMAP_OUT_OF_MEMORY));
                return false;
            }
            bmr.GetBitmap().recycle();
            return true;
        }
        ShowErrorMSG(BitmapMonitorResult.GetError(this.m_Context, bmr.GetResult()).toString());
        return false;
    }

    boolean RotateEditDrawViewWithoutContent() {
        this.LOG.m385i("RotateEditDrawViewWithoutContent", "~");
        int temp = this.m_iMaxW;
        this.m_iMaxW = this.m_iMaxH;
        this.m_iMaxH = temp;
        int iResult = this.m_EditDrawView.Rotate90WithoutContent();
        if (iResult == 0) {
            return true;
        }
        ShowErrorMSG(BitmapMonitorResult.GetError(this.m_Context, iResult));
        return false;
    }

    void SetImageFilter(GarnishItem garnishItem, IMAGE_FILTER_TYPE filterType) {
        BitmapMonitorResult bmr = BitmapMonitor.Copy(garnishItem.GetBackUpViewScaleBitmap(), Config.ARGB_8888, true);
        if (bmr.IsSuccess()) {
            Bitmap bmp = bmr.GetBitmap();
            this.m_ImageFilter.ProcessImage(bmp, filterType);
            int iResult = garnishItem.SetFilterViewScaleBitmap(bmp, filterType.toString());
            if (iResult != 0) {
                ShowErrorMSG(BitmapMonitorResult.GetError(this.m_Context, iResult));
                return;
            }
            this.m_EditDrawView.SetEdit(true);
            this.m_EditDrawView.invalidate();
            return;
        }
        ShowErrorMSG(BitmapMonitorResult.GetError(this.m_Context, bmr.GetResult()));
    }

    public void StartMake(Socket socket) {
        SetRootPath();
        PrepareAllEditPath(socket);
    }

    public void MakeNextPhoto(Socket socket) {
        int iPos = -1;
        Iterator it = this.m_strSaveIMGList.iterator();
        while (it.hasNext()) {
            String path = (String) it.next();
            if (!path.isEmpty() && !path.contains(PringoConvenientConst.NEW_FILE_MAKE_EDIT)) {
                iPos = this.m_strSaveIMGList.indexOf(path);
                this.LOG.m386v("MakeNextPhoto path", String.valueOf(path));
                break;
            }
        }
        this.LOG.m386v("MakeNextPhoto m_strSaveIMGList", String.valueOf(this.m_strSaveIMGList));
        this.LOG.m386v("MakeNextPhoto iPos", String.valueOf(iPos));
        if (iPos != -1) {
            MakeOneEditPhoto(socket, iPos, (String) this.m_strSaveIMGList.get(iPos));
        } else {
            MakeOneEditPhotoDone(socket, -1, null);
        }
    }

    private void SetRootPath() {
        this.m_strTmpRoot = this.m_Context.getExternalFilesDir(null).getAbsolutePath() + "/" + PringoConvenientConst.PRINGO_TEMP_FOLDER;
        FileUtility.CreateFolder(this.m_strTmpRoot);
        this.m_strImgRoot = this.m_strTmpRoot + File.separator + PringoConvenientConst.PRINBIZ_EDIT_IMG_FOLDER;
        FileUtility.DeleteFolder(this.m_strImgRoot);
        FileUtility.CreateFolder(this.m_strImgRoot);
        this.LOG.m386v("SetRootPath", String.valueOf(this.m_strImgRoot));
    }

    private void PrepareAllEditPath(Socket socket) {
        String strImgPath = null;
        int iPos = -1;
        for (int i = 0; i < this.m_bIsEditedList.size(); i++) {
            if (((Boolean) this.m_bIsEditedList.get(i)).booleanValue()) {
                String path = (String) this.m_strEditPathList.get(i);
                this.m_strSaveIMGList.set(i, path);
                if (strImgPath == null) {
                    iPos = i;
                    strImgPath = path;
                }
            }
        }
        this.LOG.m383d(this.TAG, "PrepareAllEditPath: " + this.m_strSaveIMGList);
        if (iPos != -1) {
            ShowWaitDialog(true);
            MakeOneEditPhoto(socket, iPos, strImgPath);
        } else if (this.m_MakePhotoListener != null) {
            this.m_MakePhotoListener.MakeOneEditPhotoDone(socket, -1, null);
        }
    }

    private void MakeOneEditPhoto(Socket socket, int iPos, String strPhotoPath) {
        this.LOG.m385i("MakeOneEditPhoto" + iPos, String.valueOf(strPhotoPath));
        PrepareDrawView(socket, iPos, strPhotoPath);
    }

    private void MakeOneEditPhotoDone(Socket socket, int iPos, String strPhotoPath) {
        this.LOG.m383d(this.TAG, "MakeOneEditPhotoDone: " + strPhotoPath);
        if (iPos != -1) {
            this.m_strSaveIMGList.set(iPos, strPhotoPath);
        }
        ShowWaitDialog(false);
        if (this.m_MakePhotoListener != null) {
            this.m_MakePhotoListener.MakeOneEditPhotoDone(socket, iPos, strPhotoPath);
        }
    }

    private void ShowErrorMSG(String strErr) {
        ShowWaitDialog(false);
        if (this.m_ErrorDialog == null) {
            this.m_ErrorDialog = new ShowMSGDialog(this.m_Context, false);
            this.m_ErrorDialog.SetMSGListener(new C08021());
        }
        this.m_ErrorDialog.ShowSimpleMSGDialog(strErr, this.m_Context.getString(this.mRID.R_STRING_ERROR_TITLT));
    }

    private void ShowWaitDialog(boolean bShow) {
        if (!bShow) {
            this.m_WaitingDialog.DismissDialog();
        } else if (!this.m_WaitingDialog.IsShowing()) {
            this.m_WaitingDialog.ShowDialog(this.m_Context.getString(this.mRID.R_STRING_PLEASE_WAIT));
        }
    }
}
