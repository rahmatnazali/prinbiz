package com.hiti.ui.drawview.garnishitem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.util.Log;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.printerprotocol.RequestState;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.PringoConvenientConst;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class GarnishItem implements Comparable<GarnishItem> {
    public static final int BACKGROUND_TYPE = 0;
    public static final int COLOR_BORDER_TYPE = 2;
    public static final int COLOR_GARNISH_TYPE = 3;
    public static final int COLOR_QRCODE_TYPE = 4;
    public static final int EDIT_PHOTO_TYPE = 1;
    public static final int FROM_TYPE_ASSET = 1;
    public static final int FROM_TYPE_SD_CARD = 2;
    public static final int FROM_TYPR_NON = 0;
    public static final int GS_BORDER_TYPE = 5;
    public static final int GS_GARNISH_TYPE = 6;
    public static final int GS_MASK_TYPE = 7;
    public static final long NON_COMPOSE = -1;
    public static final String NON_FILTER = "NON_FILTER";
    public static final int No_EXCLUDE_TYPE = -1;
    public static final int SCALE_LIMIT_MIN = 10;
    public static final int VIEW_MASK_TYPE = 8;
    private LogManager LOG;
    private Bitmap m_BackupFilterViewScaleBitmap;
    private Bitmap m_BackupViewScaleBitmap;
    private long m_ComposeID;
    private Context m_Context;
    private String m_GarnishPath;
    private long m_ID;
    private Matrix m_Matrix;
    private float[] m_MatrixValue;
    private float m_Scale;
    private Bitmap m_ViewScaleBitmap;
    private float m_Zoom;
    private float m_fBlue;
    private float m_fContrast;
    private float m_fCoordX;
    private float m_fCoordY;
    private int m_fDegree;
    private float m_fGreen;
    private float m_fHue;
    private float m_fLight;
    private float m_fRed;
    private float m_fSaturation;
    private float m_fStartX;
    private float m_fStartY;
    private float m_fViewScale;
    private int m_iFromType;
    private int m_iHeight;
    private int m_iType;
    private int m_iViewScaleBitmapHeight;
    private int m_iViewScaleBitmapWidth;
    private int m_iWidth;
    private String m_strFilter;

    public GarnishItem(Context context, int iType) {
        this.m_Context = null;
        this.m_ID = NON_COMPOSE;
        this.m_ComposeID = NON_COMPOSE;
        this.m_fViewScale = 1.0f;
        this.m_iViewScaleBitmapHeight = FROM_TYPR_NON;
        this.m_iViewScaleBitmapWidth = FROM_TYPR_NON;
        this.m_ViewScaleBitmap = null;
        this.m_BackupViewScaleBitmap = null;
        this.m_BackupFilterViewScaleBitmap = null;
        this.m_GarnishPath = null;
        this.m_iFromType = FROM_TYPR_NON;
        this.m_iType = FROM_TYPR_NON;
        this.m_fCoordX = 0.0f;
        this.m_fCoordY = 0.0f;
        this.m_fStartX = 0.0f;
        this.m_fStartY = 0.0f;
        this.m_iHeight = FROM_TYPR_NON;
        this.m_iWidth = FROM_TYPR_NON;
        this.m_fDegree = FROM_TYPR_NON;
        this.m_Scale = 1.0f;
        this.m_Zoom = 1.0f;
        this.m_Matrix = new Matrix();
        this.m_MatrixValue = new float[9];
        this.m_strFilter = null;
        this.m_fSaturation = 0.0f;
        this.m_fLight = 0.0f;
        this.m_fContrast = 0.0f;
        this.m_fHue = 0.0f;
        this.m_fRed = 0.0f;
        this.m_fGreen = 0.0f;
        this.m_fBlue = 0.0f;
        this.LOG = null;
        this.m_Context = context;
        this.LOG = new LogManager(FROM_TYPR_NON);
        this.m_iType = iType;
        this.m_ID = CreateID();
    }

    private long CreateID() {
        String StrNewID = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(new Date(System.currentTimeMillis()));
        this.LOG.m385i("CreateID", String.valueOf(StrNewID));
        return Long.parseLong(StrNewID);
    }

    public static long CreateComposeID() {
        String StrNewID = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(new Date(System.currentTimeMillis()));
        Log.i("CreateComposeID", String.valueOf(StrNewID));
        return Long.parseLong(StrNewID);
    }

    public long GetID() {
        return this.m_ID;
    }

    public void SetID(long lID) {
        this.m_ID = lID;
    }

    public long GetComposeID() {
        return this.m_ComposeID;
    }

    public void SetComposeID(long lComposeID) {
        this.m_ComposeID = lComposeID;
    }

    public static GarnishItem CopyGarnishItem(Context context, GarnishItem copyGarnishItem) {
        GarnishItem garnishItem = null;
        if (copyGarnishItem == null) {
            return null;
        }
        if (copyGarnishItem.m_iType == COLOR_GARNISH_TYPE || copyGarnishItem.m_iType == GS_GARNISH_TYPE || copyGarnishItem.m_iType == COLOR_QRCODE_TYPE) {
            garnishItem = new GarnishItem(context, copyGarnishItem.m_iType);
            garnishItem.InitUIView(copyGarnishItem, false);
        }
        return garnishItem;
    }

    public static GarnishItem RecordGarnishItem(Context context, GarnishItem recordGarnishItem) {
        GarnishItem garnishItem = null;
        if (recordGarnishItem == null) {
            return null;
        }
        if (recordGarnishItem.m_iType == COLOR_GARNISH_TYPE || recordGarnishItem.m_iType == GS_GARNISH_TYPE || recordGarnishItem.m_iType == COLOR_QRCODE_TYPE) {
            garnishItem = new GarnishItem(context, recordGarnishItem.m_iType);
            garnishItem.InitUIView(recordGarnishItem, true);
        }
        return garnishItem;
    }

    public static GarnishItem GetComposeGarnishItem(ArrayList<GarnishItem> garnishItemGroup, GarnishItem garnishItem) {
        if (garnishItem == null) {
            return null;
        }
        if (garnishItem.GetComposeID() == NON_COMPOSE) {
            return null;
        }
        Iterator it = garnishItemGroup.iterator();
        while (it.hasNext()) {
            GarnishItem cGarnishItem = (GarnishItem) it.next();
            if (cGarnishItem.GetID() != garnishItem.GetID() && cGarnishItem.GetComposeID() == garnishItem.GetComposeID()) {
                if (cGarnishItem.m_iType == COLOR_GARNISH_TYPE || cGarnishItem.m_iType == GS_GARNISH_TYPE || cGarnishItem.m_iType == COLOR_QRCODE_TYPE) {
                    return cGarnishItem;
                }
                return null;
            }
        }
        return null;
    }

    public static void SetGarnishComposeSameAction(GarnishItem garnishItem, GarnishItem cGarnishItem) {
        if ((garnishItem.m_iType == COLOR_GARNISH_TYPE || garnishItem.m_iType == GS_GARNISH_TYPE || garnishItem.m_iType == COLOR_QRCODE_TYPE) && cGarnishItem != null) {
            cGarnishItem.InitUIAction(garnishItem);
        }
    }

    public static void SetSameAction(GarnishItem garnishItem, GarnishItem cGarnishItem) {
        if (cGarnishItem != null) {
            cGarnishItem.InitUIAction(garnishItem);
        }
    }

    public static void SaveGarnishORGBitmap(Context context, String strFolderName, GarnishItem garnishItem, Bitmap saveBitmap) {
        OutputStream outputStream;
        FileNotFoundException e;
        IOException e2;
        if (garnishItem != null) {
            FileUtility.CreateFolder(FileUtility.GetSDAppRootPath(context) + strFolderName);
            if (saveBitmap != null) {
                try {
                    OutputStream fos = new FileOutputStream(new File(FileUtility.GetSDAppRootPath(context) + strFolderName, String.valueOf(garnishItem.GetID()) + PringoConvenientConst.PRINBIZ_BORDER_EXT));
                    try {
                        saveBitmap.compress(CompressFormat.PNG, 100, fos);
                        fos.close();
                        garnishItem.SetGarnishPath(FileUtility.GetSDAppRootPath(context) + strFolderName + "/" + String.valueOf(garnishItem.GetID()) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                        garnishItem.SetFromType(FROM_TYPE_SD_CARD);
                        outputStream = fos;
                    } catch (FileNotFoundException e3) {
                        e = e3;
                        outputStream = fos;
                        e.printStackTrace();
                    } catch (IOException e4) {
                        e2 = e4;
                        outputStream = fos;
                        e2.printStackTrace();
                    }
                } catch (FileNotFoundException e5) {
                    e = e5;
                    e.printStackTrace();
                } catch (IOException e6) {
                    e2 = e6;
                    e2.printStackTrace();
                }
            }
        }
    }

    public int InitUIView(Bitmap bmp, PointF pointF, float ViewScale, String strPath, int iFromType) {
        if (!CheckFromType(strPath, iFromType)) {
            return FROM_TYPR_NON;
        }
        if (ViewScale != 0.0f) {
            BitmapMonitorResult bmr = BitmapMonitor.CreateScaledBitmap(bmp, (int) (((float) bmp.getWidth()) / ViewScale), (int) (((float) bmp.getHeight()) / ViewScale), true);
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            SetViewScaleBitmap(bmr.GetBitmap());
            this.m_fViewScale = ViewScale;
        }
        this.m_iWidth = bmp.getWidth();
        this.m_iHeight = bmp.getHeight();
        this.m_fCoordX = pointF.x;
        this.m_fCoordY = pointF.y;
        this.m_fStartX = this.m_fCoordX;
        this.m_fStartY = this.m_fCoordY;
        SetGarnishPath(strPath);
        SetFromType(iFromType);
        SetTransMatrix(this.m_fCoordX, this.m_fCoordY);
        SetFilter(NON_FILTER);
        return FROM_TYPR_NON;
    }

    public int InitUIView(GarnishItem garnishItem, boolean boOnlyRecord) {
        this.m_ComposeID = NON_COMPOSE;
        this.m_fViewScale = garnishItem.m_fViewScale;
        if (boOnlyRecord) {
            this.m_ID = garnishItem.m_ID;
            this.m_ComposeID = garnishItem.m_ComposeID;
            this.m_iViewScaleBitmapWidth = garnishItem.m_iViewScaleBitmapWidth;
            this.m_iViewScaleBitmapHeight = garnishItem.m_iViewScaleBitmapHeight;
        } else {
            BitmapMonitorResult bmr;
            if (garnishItem.m_ViewScaleBitmap != null) {
                bmr = BitmapMonitor.Copy(garnishItem.m_ViewScaleBitmap, garnishItem.m_ViewScaleBitmap.getConfig(), true);
                if (!bmr.IsSuccess()) {
                    return bmr.GetResult();
                }
                SetViewScaleBitmap(bmr.GetBitmap());
            }
            if (garnishItem.m_BackupViewScaleBitmap != null) {
                bmr = BitmapMonitor.Copy(garnishItem.m_BackupViewScaleBitmap, garnishItem.m_BackupViewScaleBitmap.getConfig(), true);
                if (!bmr.IsSuccess()) {
                    return bmr.GetResult();
                }
                this.m_BackupViewScaleBitmap = bmr.GetBitmap();
            }
            if (garnishItem.m_BackupFilterViewScaleBitmap != null) {
                bmr = BitmapMonitor.Copy(garnishItem.m_BackupFilterViewScaleBitmap, garnishItem.m_BackupFilterViewScaleBitmap.getConfig(), true);
                if (!bmr.IsSuccess()) {
                    return bmr.GetResult();
                }
                this.m_BackupFilterViewScaleBitmap = bmr.GetBitmap();
            }
        }
        this.m_fCoordX = garnishItem.m_fCoordX;
        this.m_fCoordY = garnishItem.m_fCoordY;
        this.m_fStartX = garnishItem.m_fStartX;
        this.m_fStartY = garnishItem.m_fStartY;
        this.m_iHeight = garnishItem.m_iHeight;
        this.m_iWidth = garnishItem.m_iWidth;
        this.m_GarnishPath = garnishItem.m_GarnishPath;
        this.m_iFromType = garnishItem.m_iFromType;
        this.m_fDegree = garnishItem.m_fDegree;
        this.m_Scale = garnishItem.m_Scale;
        this.m_Zoom = garnishItem.m_Zoom;
        this.m_Matrix.set(garnishItem.m_Matrix);
        for (int i = FROM_TYPR_NON; i < 9; i += FROM_TYPE_ASSET) {
            this.m_MatrixValue[i] = garnishItem.m_MatrixValue[i];
        }
        this.m_strFilter = garnishItem.m_strFilter;
        return FROM_TYPR_NON;
    }

    public int InitUIAction(GarnishItem garnishItem) {
        this.m_fViewScale = garnishItem.m_fViewScale;
        this.m_ComposeID = garnishItem.m_ComposeID;
        this.m_iViewScaleBitmapWidth = garnishItem.m_iViewScaleBitmapWidth;
        this.m_iViewScaleBitmapHeight = garnishItem.m_iViewScaleBitmapHeight;
        this.m_fCoordX = garnishItem.m_fCoordX;
        this.m_fCoordY = garnishItem.m_fCoordY;
        this.m_fStartX = garnishItem.m_fStartX;
        this.m_fStartY = garnishItem.m_fStartY;
        this.m_iHeight = garnishItem.m_iHeight;
        this.m_iWidth = garnishItem.m_iWidth;
        this.m_fDegree = garnishItem.m_fDegree;
        this.m_Scale = garnishItem.m_Scale;
        this.m_Zoom = garnishItem.m_Zoom;
        this.m_Matrix.set(garnishItem.m_Matrix);
        for (int i = FROM_TYPR_NON; i < 9; i += FROM_TYPE_ASSET) {
            this.m_MatrixValue[i] = garnishItem.m_MatrixValue[i];
        }
        this.m_strFilter = garnishItem.m_strFilter;
        this.m_fSaturation = garnishItem.m_fSaturation;
        this.m_fLight = garnishItem.m_fLight;
        this.m_fContrast = garnishItem.m_fContrast;
        this.m_fHue = garnishItem.m_fHue;
        this.m_fRed = garnishItem.m_fRed;
        this.m_fGreen = garnishItem.m_fGreen;
        this.m_fBlue = garnishItem.m_fBlue;
        return FROM_TYPR_NON;
    }

    public void Clear() {
        SetViewScaleBitmap(null);
        if (!(this.m_BackupViewScaleBitmap == null || this.m_BackupViewScaleBitmap.isRecycled())) {
            this.LOG.m385i("Clear", "m_BackupViewScaleBitmap");
            this.m_BackupViewScaleBitmap.recycle();
        }
        this.m_BackupViewScaleBitmap = null;
        if (!(this.m_BackupFilterViewScaleBitmap == null || this.m_BackupFilterViewScaleBitmap.isRecycled())) {
            this.LOG.m385i("Clear", "m_BackupFilterViewScaleBitmap");
            this.m_BackupFilterViewScaleBitmap.recycle();
        }
        this.m_BackupFilterViewScaleBitmap = null;
    }

    public float GetX() {
        return this.m_fCoordX;
    }

    public void SetX(float newValue) {
        this.m_fCoordX = newValue;
    }

    public float GetY() {
        return this.m_fCoordY;
    }

    public void SetY(float newValue) {
        this.m_fCoordY = newValue;
    }

    public float GetStartX() {
        return this.m_fStartX;
    }

    public float GetStartY() {
        return this.m_fStartY;
    }

    public int GetHeight() {
        return this.m_iHeight;
    }

    public int GetWidth() {
        return this.m_iWidth;
    }

    public float GetScale() {
        return this.m_Scale;
    }

    public void SetScale(float fScale) {
        this.m_Scale = fScale;
    }

    public float GetZoom() {
        return this.m_Zoom;
    }

    public void SetZoom(float fZoom) {
        this.m_Zoom = fZoom;
    }

    public float GetDegree() {
        return (float) this.m_fDegree;
    }

    public void SetDegree(float fDegree) {
        this.m_fDegree = ((int) fDegree) % RequestState.REQUEST_CREATE_BITMAP_SCALE_HINT;
    }

    public Matrix GetMatrix() {
        return this.m_Matrix;
    }

    public Matrix GetPreMatrix(float TX, float TY, float zoom) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postTranslate((float) ((-this.m_iViewScaleBitmapWidth) / FROM_TYPE_SD_CARD), (float) ((-this.m_iViewScaleBitmapHeight) / FROM_TYPE_SD_CARD));
        matrix.postRotate(GetDegree());
        matrix.postScale(zoom, zoom);
        matrix.postScale(GetScale(), GetScale());
        matrix.postTranslate(TX, TY);
        return matrix;
    }

    public void SetTransMatrix(float TX, float TY) {
        this.m_Matrix.reset();
        this.m_Matrix.postTranslate((float) ((-this.m_iViewScaleBitmapWidth) / FROM_TYPE_SD_CARD), (float) ((-this.m_iViewScaleBitmapHeight) / FROM_TYPE_SD_CARD));
        this.m_Matrix.postRotate(GetDegree());
        this.m_Matrix.postScale(GetZoom(), GetZoom());
        this.m_Matrix.postScale(GetScale(), GetScale());
        this.m_Matrix.postTranslate(TX, TY);
        this.m_Matrix.getValues(this.m_MatrixValue);
    }

    public void SetZoomMatrix(float ZX, float ZY) {
        this.m_Matrix.reset();
        this.m_Matrix.postTranslate((float) ((-this.m_iViewScaleBitmapWidth) / FROM_TYPE_SD_CARD), (float) ((-this.m_iViewScaleBitmapHeight) / FROM_TYPE_SD_CARD));
        this.m_Matrix.postRotate(GetDegree());
        this.m_Matrix.postScale(ZX, ZY);
        this.m_Matrix.postScale(GetScale(), GetScale());
        this.m_Matrix.postTranslate(GetX(), GetY());
        this.m_Matrix.getValues(this.m_MatrixValue);
    }

    public void SetScaleMatrix(float SX, float SY) {
        this.m_Matrix.reset();
        this.m_Matrix.postTranslate((float) ((-this.m_iViewScaleBitmapWidth) / FROM_TYPE_SD_CARD), (float) ((-this.m_iViewScaleBitmapHeight) / FROM_TYPE_SD_CARD));
        this.m_Matrix.postRotate(GetDegree());
        this.m_Matrix.postScale(GetZoom(), GetZoom());
        this.m_Matrix.postScale(SX, SY);
        this.m_Matrix.postTranslate(GetX(), GetY());
        this.m_Matrix.getValues(this.m_MatrixValue);
    }

    public void SetRotateMatrix(float fDegree) {
        this.m_Matrix.reset();
        this.m_Matrix.postTranslate((float) ((-this.m_iViewScaleBitmapWidth) / FROM_TYPE_SD_CARD), (float) ((-this.m_iViewScaleBitmapHeight) / FROM_TYPE_SD_CARD));
        this.m_Matrix.postRotate(fDegree);
        this.m_Matrix.postScale(GetZoom(), GetZoom());
        this.m_Matrix.postScale(GetScale(), GetScale());
        this.m_Matrix.postTranslate(GetX(), GetY());
        this.m_Matrix.getValues(this.m_MatrixValue);
    }

    public float GetViewScale() {
        return this.m_fViewScale;
    }

    public int compareTo(GarnishItem obj) {
        int iRet = FROM_TYPR_NON;
        if (this.m_iType > obj.m_iType) {
            iRet = FROM_TYPE_ASSET;
        }
        if (this.m_iType < obj.m_iType) {
            return No_EXCLUDE_TYPE;
        }
        return iRet;
    }

    public boolean IsGSType() {
        if (this.m_iType == GS_BORDER_TYPE || this.m_iType == GS_GARNISH_TYPE || this.m_iType == GS_MASK_TYPE) {
            return true;
        }
        return false;
    }

    public int GetType() {
        return this.m_iType;
    }

    public void SetType(int iType) {
        this.m_iType = iType;
    }

    public boolean CanMove(int excludeType) {
        boolean boRet = false;
        if (this.m_iType == COLOR_GARNISH_TYPE || this.m_iType == GS_GARNISH_TYPE || this.m_iType == COLOR_QRCODE_TYPE) {
            boRet = true;
        } else if (this.m_iType == FROM_TYPE_ASSET || this.m_iType == 0 || this.m_iType == GS_BORDER_TYPE || this.m_iType == FROM_TYPE_SD_CARD || this.m_iType == VIEW_MASK_TYPE) {
            boRet = false;
        }
        if (this.m_iType == excludeType) {
            return false;
        }
        return boRet;
    }

    public void SetGarnishPath(String GarnishPath) {
        this.m_GarnishPath = GarnishPath;
    }

    public String GetGarnishPath() {
        return this.m_GarnishPath;
    }

    public int GetFromType() {
        return this.m_iFromType;
    }

    public void SetFromType(int iFromType) {
        this.m_iFromType = iFromType;
    }

    private boolean CheckFromType(String strPath, int iFromType) {
        if (iFromType == 0 && strPath == null) {
            return true;
        }
        if (iFromType == FROM_TYPE_ASSET && strPath != null) {
            return true;
        }
        if (iFromType != FROM_TYPE_SD_CARD || strPath == null) {
            return false;
        }
        return true;
    }

    private boolean SetViewScaleBitmap(Bitmap bmp) {
        if (this.m_ViewScaleBitmap != null) {
            if (!this.m_ViewScaleBitmap.isRecycled()) {
                this.m_ViewScaleBitmap.recycle();
            }
            this.m_ViewScaleBitmap = null;
        }
        if (bmp != null) {
            this.m_iViewScaleBitmapWidth = bmp.getWidth();
            this.m_iViewScaleBitmapHeight = bmp.getHeight();
            this.m_ViewScaleBitmap = bmp;
        } else {
            this.m_iViewScaleBitmapWidth = FROM_TYPR_NON;
            this.m_iViewScaleBitmapHeight = FROM_TYPR_NON;
        }
        return true;
    }

    public Bitmap GetUIBitmap() {
        return this.m_ViewScaleBitmap;
    }

    public Bitmap GetUIBitmap(boolean noFilter) {
        if (!noFilter || this.m_BackupViewScaleBitmap == null) {
            return this.m_ViewScaleBitmap;
        }
        return this.m_BackupViewScaleBitmap;
    }

    public BitmapMonitorResult GetBitmapWithDefaultMaxOutputLimit(int iDefaultMaxShort, int iDefaultMaxLong) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        Log.i("SDcard ", "m_iFromType:" + this.m_iFromType);
        if (this.m_iFromType == 0) {
            return BitmapMonitor.CreateScaledBitmap(this.m_ViewScaleBitmap, (int) (((float) this.m_iViewScaleBitmapWidth) * this.m_fViewScale), (int) (((float) this.m_iViewScaleBitmapHeight) * this.m_fViewScale), true);
        }
        if (this.m_iFromType == FROM_TYPE_ASSET) {
            try {
                return BitmapMonitor.CreateBitmap(this.m_Context.getAssets().open(this.m_GarnishPath), false);
            } catch (IOException e) {
                bmr.SetResult(97);
                e.printStackTrace();
                return bmr;
            }
        } else if (this.m_iFromType != FROM_TYPE_SD_CARD) {
            return bmr;
        } else {
            Log.i("SDcard ", "m_iType:" + this.m_iType);
            if (this.m_iType == FROM_TYPE_ASSET) {
                int iWidth = iDefaultMaxShort;
                int iHeight = iDefaultMaxLong;
                if (this.m_iViewScaleBitmapWidth > this.m_iViewScaleBitmapHeight) {
                    iWidth = iDefaultMaxLong;
                    iHeight = iDefaultMaxShort;
                }
                return BitmapMonitor.CreateCroppedBitmapNew(this.m_Context, Uri.parse(this.m_GarnishPath), iWidth, iHeight);
            }
            Options options = new Options();
            options.inJustDecodeBounds = false;
            try {
                return BitmapMonitor.CreateBitmap(new BufferedInputStream(this.m_Context.getContentResolver().openInputStream(Uri.parse("file://" + this.m_GarnishPath))), null, options, false);
            } catch (FileNotFoundException e2) {
                bmr.SetResult(97);
                e2.printStackTrace();
                return bmr;
            }
        }
    }

    public BitmapMonitorResult GetBitmap(int iLimitWidth, int iLimitHeight) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        if (this.m_iFromType == 0) {
            return BitmapMonitor.CreateScaledBitmap(this.m_ViewScaleBitmap, (int) (((float) this.m_iViewScaleBitmapWidth) * this.m_fViewScale), (int) (((float) this.m_iViewScaleBitmapHeight) * this.m_fViewScale), true);
        }
        if (this.m_iFromType == FROM_TYPE_ASSET) {
            try {
                return BitmapMonitor.CreateBitmap(this.m_Context.getAssets().open(this.m_GarnishPath), false);
            } catch (IOException e) {
                bmr.SetResult(97);
                e.printStackTrace();
                return bmr;
            }
        } else if (this.m_iFromType != FROM_TYPE_SD_CARD) {
            return bmr;
        } else {
            if (this.m_iType == FROM_TYPE_ASSET) {
                bmr = BitmapMonitor.CreateCroppedBitmapNew(this.m_Context, Uri.parse(this.m_GarnishPath), iLimitWidth, iLimitHeight);
                Log.i("after 2CreateCroppedBitmapNew ", "GetResult:" + bmr.GetResult());
                return bmr;
            }
            Options options = new Options();
            options.inJustDecodeBounds = false;
            try {
                return BitmapMonitor.CreateBitmap(new BufferedInputStream(this.m_Context.getContentResolver().openInputStream(Uri.parse("file://" + this.m_GarnishPath))), null, options, false);
            } catch (FileNotFoundException e2) {
                bmr.SetResult(97);
                e2.printStackTrace();
                return bmr;
            }
        }
    }

    public String GetFilter() {
        return this.m_strFilter;
    }

    public void SetFilter(String strFilter) {
        if (strFilter == null) {
            strFilter = NON_FILTER;
        }
        this.m_strFilter = strFilter;
    }

    public int SetFilterViewScaleBitmap(Bitmap bmp, String strFilter) {
        SetFilter(strFilter);
        SetViewScaleBitmap(bmp);
        return FROM_TYPR_NON;
    }

    public int SetFilterColorViewScaleBitmap(Bitmap bmp, float fHue, float fSaturation, float fLight, float fContrast, float fRed, float fGreen, float fBlue) {
        SetHue(fHue);
        SetSaturation(fSaturation);
        SetLight(fLight);
        SetContrast(fContrast);
        SetRed(fRed);
        SetGreen(fGreen);
        SetBlue(fBlue);
        SetViewScaleBitmap(bmp);
        return FROM_TYPR_NON;
    }

    public Bitmap GetBackUpViewScaleBitmap() {
        return this.m_BackupViewScaleBitmap;
    }

    public Bitmap GetBackUpFilterViewScaleBitmap() {
        return this.m_BackupFilterViewScaleBitmap;
    }

    public int SetBackUpViewScaleBitmap() {
        if (this.m_BackupViewScaleBitmap == null) {
            BitmapMonitorResult bmr = BitmapMonitor.Copy(this.m_ViewScaleBitmap, Config.ARGB_8888, true);
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            this.m_BackupViewScaleBitmap = bmr.GetBitmap();
        }
        return FROM_TYPR_NON;
    }

    public int SetBackUpFilterViewScaleBitmap() {
        if (this.m_BackupFilterViewScaleBitmap == null) {
            BitmapMonitorResult bmr = BitmapMonitor.Copy(this.m_ViewScaleBitmap, Config.ARGB_8888, true);
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            this.m_BackupFilterViewScaleBitmap = bmr.GetBitmap();
        }
        return FROM_TYPR_NON;
    }

    public int ResetViewScaleBitmap() {
        if (this.m_BackupViewScaleBitmap != null) {
            BitmapMonitorResult bmr = BitmapMonitor.Copy(this.m_BackupViewScaleBitmap, Config.ARGB_8888, true);
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            if (!(this.m_ViewScaleBitmap == null || this.m_ViewScaleBitmap.isRecycled())) {
                this.m_ViewScaleBitmap.recycle();
            }
            this.m_ViewScaleBitmap = bmr.GetBitmap();
        }
        SetFilter(NON_FILTER);
        return FROM_TYPR_NON;
    }

    public int ResetFilterViewScaleBitmap() {
        if (this.m_BackupFilterViewScaleBitmap != null) {
            BitmapMonitorResult bmr = BitmapMonitor.Copy(this.m_BackupFilterViewScaleBitmap, Config.ARGB_8888, true);
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            if (!(this.m_ViewScaleBitmap == null || this.m_ViewScaleBitmap.isRecycled())) {
                this.m_ViewScaleBitmap.recycle();
            }
            this.m_ViewScaleBitmap = bmr.GetBitmap();
        }
        ResetFilterColorRGBValue();
        ResetFilterColorHSLCValue();
        return FROM_TYPR_NON;
    }

    public void RemoveFilterViewScaleBitmap() {
        if (this.m_BackupFilterViewScaleBitmap != null) {
            if (!this.m_BackupFilterViewScaleBitmap.isRecycled()) {
                this.m_BackupFilterViewScaleBitmap.recycle();
            }
            this.m_BackupFilterViewScaleBitmap = null;
        }
    }

    public void ResetFilterColorHSLCValue() {
        SetSaturation(0.0f);
        SetLight(0.0f);
        SetContrast(0.0f);
        SetHue(0.0f);
    }

    public void ResetFilterColorRGBValue() {
        SetRed(0.0f);
        SetGreen(0.0f);
        SetBlue(0.0f);
    }

    public float GetSaturation() {
        return this.m_fSaturation;
    }

    public void SetSaturation(float fSaturation) {
        this.m_fSaturation = fSaturation;
    }

    public float GetLight() {
        return this.m_fLight;
    }

    public void SetLight(float fLight) {
        this.m_fLight = fLight;
    }

    public float GetContrast() {
        return this.m_fContrast;
    }

    public void SetContrast(float fContrast) {
        this.m_fContrast = fContrast;
    }

    public float GetHue() {
        return this.m_fHue;
    }

    public void SetHue(float fHue) {
        this.m_fHue = fHue;
    }

    public float GetRed() {
        return this.m_fRed;
    }

    public void SetRed(float fRed) {
        this.m_fRed = fRed;
    }

    public float GetGreen() {
        return this.m_fGreen;
    }

    public void SetGreen(float fGreen) {
        this.m_fGreen = fGreen;
    }

    public float GetBlue() {
        return this.m_fBlue;
    }

    public void SetBlue(float fBlue) {
        this.m_fBlue = fBlue;
    }
}
