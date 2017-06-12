package com.hiti.prinbiz;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableRow;
import com.hiti.HitiChunk.HitiChunk.ChunkType;
import com.hiti.HitiChunk.HitiChunkUtility;
import com.hiti.ImageFilter.ImageFilter;
import com.hiti.ImageFilter.ImageFilter.IMAGE_FILTER_TYPE;
import com.hiti.ImageFilter.ImageFilterPlus;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.plugins.common.ColorPainter_Scorller;
import com.hiti.plugins.common.FilterRGBColorPainter;
import com.hiti.plugins.common.FilterRGBColorSelector;
import com.hiti.plugins.common.OnRGBFilterColorSelectChangedListener;
import com.hiti.service.upload.UploadService;
import com.hiti.trace.GlobalVariable_AlbumSelInfo;
import com.hiti.trace.GlobalVariable_PrintSettingInfo;
import com.hiti.ui.drawview.DrawView;
import com.hiti.ui.drawview.DrawViewListener;
import com.hiti.ui.drawview.garnishitem.FilterColorValue;
import com.hiti.ui.drawview.garnishitem.GarnishItem;
import com.hiti.ui.drawview.garnishitem.parser.GarnishItemXMLCreator;
import com.hiti.ui.drawview.garnishitem.security.GarnishSecurity;
import com.hiti.ui.drawview.garnishitem.utility.EditMetaListener;
import com.hiti.ui.drawview.garnishitem.utility.EditMetaUtility;
import com.hiti.ui.horizontallistview.HorizontalListView;
import com.hiti.utility.CollageIdEditInfo;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.MediaUtil;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class EditIdActivity extends Activity {
    private static final int COLOR_HSLC = 1;
    private static final int COLOR_RGB = 2;
    private static final int RESET_ALL = 3;
    private static final int RESET_DWRAW_VIEW = -1;
    int DEFAULT_ORG_THUMBNAIL_VIEW_SCALE;
    private OnClickListener FxListener;
    LogManager LOG;
    private OnClickListener ResetListener;
    String TAG;
    NFCInfo mNFCInfo;
    ImageButton m_AddButton;
    private AssetManager m_AssetManager;
    ImageButton m_BackButton;
    Bitmap m_BioLineBitmap;
    ImageView m_BioLineImageView;
    ImageButton m_BorderButton;
    HorizontalListView m_BorderHorizontalListView;
    ImageButton m_BorderImageButton;
    RelativeLayout m_BorderRelativeLayout;
    int m_BorderViewHorizontalSpacing;
    CollageIdEditInfo m_CollageIdEditInfo;
    ColorPainter_Scorller m_ColorPainter_Scorller;
    Bitmap m_DefaultOrgThumbnailBmp;
    Matrix m_DefaultOrgThumbnailMatrix;
    Bitmap m_DefaultOrgThumbnailNoMatrixBmp;
    Bitmap m_DefaultUIThumbnailBmp;
    Bitmap m_EditBmp;
    private DrawView m_EditDrawView;
    private int m_EditDrawViewBackgroundColor;
    private Uri m_EditFileUri;
    private EditMetaUtility m_EditMetaUtility;
    RelativeLayout m_EditRelativeLayout;
    private ShowMSGDialog m_ErrorDialog;
    ImageButton m_FilterColorImgButton;
    RelativeLayout m_FilterColorLayout;
    FilterRGBColorSelector m_FilterColorSelector;
    FilterColorValue m_FilterColorValue;
    HorizontalListView m_FilterHorizontalListView;
    ImageButton m_FilterImageButton;
    FilterRGBColorPainter m_FilterRGBColorPainter;
    RelativeLayout m_FilterRelativeLayout;
    RelativeLayout m_FilterSelectorLayout;
    int m_FilterViewHorizontalSpacing;
    Button m_FineDownButton;
    Button m_FineLeftButton;
    Button m_FineRightButton;
    Button m_FineUpButton;
    ImageButton m_FxButton;
    GarnishSecurity m_HBorderGarnishSecurity;
    Button m_HFineDownButton;
    Button m_HFineLeftButton;
    Button m_HFineRightButton;
    TableRow m_HFineTableRow;
    Button m_HFineUpButton;
    Button m_HResetButton;
    Button m_HRotateButton;
    Button m_HScaleDownButton;
    TableRow m_HScaleTableRow;
    Button m_HScaleUpButton;
    private ImageFilter m_ImageFilter;
    private ImageFilterPlus m_ImageRGB;
    private ShowMSGDialog m_MSGDialog;
    ImageButton m_NextImgButton;
    Paint m_NormalPaint;
    ImageButton m_OKButton;
    private OnEditMetaListener m_OnEditMetaListener;
    ImageButton m_PreviousImgButton;
    ImageButton m_ReduceButton;
    ImageButton m_ResetButton;
    private SaveImage m_SaveImage;
    ImageButton m_SaveImageButton;
    Button m_ScaleDownButton;
    Button m_ScaleUpButton;
    GarnishSecurity m_VBorderGarnishSecurity;
    RelativeLayout m_VFineRelativeLayout;
    RelativeLayout m_VScaleRelativeLayout;
    private float m_ViewScale;
    private ShowMSGDialog m_WaitingDialog;
    private boolean m_bBackCollage;
    private boolean m_bCallBack;
    boolean m_bJustSave;
    boolean m_bNotChangeFilterSelector;
    private boolean m_boFirstLoadPhotoVertical;
    private boolean m_boLastVertical;
    int m_iBorderItemSize;
    int m_iBorderItemSizeHeight;
    int m_iBorderItemSizeWidth;
    int m_iBrushItemSize;
    private int m_iCollageInfoHeight;
    private int m_iCollageInfoNum;
    private int m_iCollageInfoWidth;
    private int m_iEditDrawViewHeight;
    private int m_iEditPhotoHeight;
    private int m_iEditPhotoWidth;
    int m_iFilterCount;
    int m_iFilterItemNameSizeHeight;
    int m_iFilterItemSize;
    int m_iFilterItemSizeHeight;
    int m_iFilterItemSizeWidth;
    private int m_iMaxH;
    private int m_iMaxW;
    private int m_iOriLong;
    private int m_iOriShort;
    int m_iSelectBorder;
    int m_iSelectFilter;
    GlobalVariable_AlbumSelInfo m_prefAlbumInfo;
    GlobalVariable_PrintSettingInfo m_prefInfo;
    private String m_strBiometricLinePath;
    private String m_strBorderRootPath;
    ArrayList<String> m_strBorderThumbnailPathList;
    private String m_strCollageEditPath;
    private String m_strJustSaveFolder;
    private String m_strLastXMLPath;
    private String m_strRoot;
    private String m_strSaveFolder;
    private String m_strSavePath;
    private String m_strXMLPath;

    /* renamed from: com.hiti.prinbiz.EditIdActivity.2 */
    class C02832 implements OnClickListener {
        C02832() {
        }

        public void onClick(View v) {
            EditIdActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.EditIdActivity.3 */
    class C02843 implements OnClickListener {
        C02843() {
        }

        public void onClick(View v) {
            EditIdActivity.this.m_EditMetaUtility.onSaveEditPhoto();
        }
    }

    /* renamed from: com.hiti.prinbiz.EditIdActivity.4 */
    class C02854 implements OnClickListener {
        C02854() {
        }

        public void onClick(View v) {
            EditIdActivity.this.m_bJustSave = true;
            if (EditIdActivity.this.m_strJustSaveFolder == null) {
                EditIdActivity.this.m_strJustSaveFolder = Environment.getExternalStorageDirectory().getPath() + File.separator + PringoConvenientConst.PRINBIZ_FOLDER;
                FileUtility.CreateFolder(EditIdActivity.this.m_strJustSaveFolder);
            }
            EditIdActivity.this.onSaveImageButtonClicked();
        }
    }

    /* renamed from: com.hiti.prinbiz.EditIdActivity.5 */
    class C02865 implements OnClickListener {
        C02865() {
        }

        public void onClick(View v) {
            EditIdActivity.this.m_FxButton.setBackgroundResource(C0349R.drawable.fx_button_clicked);
            EditIdActivity.this.CreateFilter();
        }
    }

    /* renamed from: com.hiti.prinbiz.EditIdActivity.6 */
    class C02876 implements OnClickListener {
        C02876() {
        }

        public void onClick(View v) {
            EditIdActivity.this.OnResetListener(v);
        }
    }

    /* renamed from: com.hiti.prinbiz.EditIdActivity.9 */
    class C02889 implements OnClickListener {
        C02889() {
        }

        public void onClick(View v) {
            if (EditIdActivity.this.m_EditDrawView != null) {
                EditIdActivity.this.m_EditDrawView.SetEditPhotoGarnishScale(-0.1f);
            }
        }
    }

    class SaveImage extends AsyncTask<Void, String, String> {
        private String m_strOriPath;

        public SaveImage(String strOriPath) {
            this.m_strOriPath = null;
            this.m_strOriPath = strOriPath;
        }

        void ReWriteDrawView(int iPos) {
            EditIdActivity.this.PrepareDrawView(iPos);
        }

        protected String doInBackground(Void... parms) {
            String strSavePath;
            String strSaveName = FileUtility.GetNewName(FileUtility.ChangeFileExt(FileUtility.GetFileName(this.m_strOriPath), PringoConvenientConst.PRINBIZ_BORDER_EXT), PringoConvenientConst.NEW_FILE_NAME_EDIT);
            strSaveName = strSaveName.substring(strSaveName.indexOf("HITI") - 4);
            if (EditIdActivity.this.m_bJustSave) {
                strSavePath = EditIdActivity.this.m_strJustSaveFolder + "/" + strSaveName;
            } else {
                strSavePath = EditIdActivity.this.m_strSaveFolder + "/" + strSaveName;
            }
            BitmapMonitor.TrySystemGC();
            BitmapMonitorResult bmr = EditIdActivity.this.m_EditDrawView.GetEditPhotoWithLimit(EditIdActivity.this.m_iMaxW, EditIdActivity.this.m_iMaxH);
            if (!bmr.IsSuccess()) {
                return bmr.GetError(EditIdActivity.this.getBaseContext());
            }
            Bitmap bmp = bmr.GetBitmap();
            BitmapMonitorResult bg = BitmapMonitor.CreateBitmap(EditIdActivity.this.m_iMaxW, EditIdActivity.this.m_iMaxH, Config.ARGB_8888);
            if (bg.IsSuccess()) {
                Bitmap bmp2 = bg.GetBitmap();
                Canvas canvas = new Canvas(bmp2);
                canvas.drawColor(EditIdActivity.RESET_DWRAW_VIEW);
                canvas.drawBitmap(bmp, 0.0f, 0.0f, null);
                bmp.recycle();
                if (!EditIdActivity.this.SaveBitmap2File(strSavePath, bmp2, CompressFormat.PNG)) {
                    return EditIdActivity.this.getString(C0349R.string.ERROR);
                }
            }
            bmp.recycle();
            BitmapMonitor.TrySystemGC();
            if (EditIdActivity.this.m_EditDrawView.HaveGSGarnish()) {
                bmr = EditIdActivity.this.m_EditDrawView.GetGSGarnish(EditIdActivity.this.m_iMaxW, EditIdActivity.this.m_iMaxH, false);
                if (!bmr.IsSuccess()) {
                    return bmr.GetError(EditIdActivity.this.getBaseContext());
                }
                Bitmap maskBmp = bmr.GetBitmap();
                HitiChunkUtility.AddHiTiChunk(strSavePath, maskBmp, ChunkType.PNG);
                maskBmp.recycle();
                BitmapMonitor.TrySystemGC();
            }
            EditIdActivity.this.m_boLastVertical = EditIdActivity.this.m_EditDrawView.IsVertical();
            EditIdActivity.this.SetSavePath(strSavePath);
            return null;
        }

        protected void onPostExecute(String result) {
            EditIdActivity.this.ShowWaitDialog(false);
            if (result != null) {
                EditIdActivity.this.ShowErrorMSG(result);
            } else if (EditIdActivity.this.m_bJustSave) {
                EditIdActivity.this.m_bJustSave = false;
                if (EditIdActivity.this.m_EditFileUri != null) {
                    EditIdActivity.this.ShowSharePhotoAlertDialog();
                }
            } else {
                EditIdActivity.this.m_EditDrawView.SetEdit(false);
                EditIdActivity.this.m_EditMetaUtility.SaveEditPhotoDone();
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.EditIdActivity.1 */
    class C07591 implements INfcPreview {
        C07591() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            EditIdActivity.this.mNFCInfo = nfcInfo;
        }
    }

    /* renamed from: com.hiti.prinbiz.EditIdActivity.7 */
    class C07607 implements MSGListener {
        C07607() {
        }

        public void OKClick() {
            EditIdActivity.this.OpenShareList(EditIdActivity.this.m_EditFileUri);
        }

        public void Close() {
        }

        public void CancelClick() {
        }
    }

    /* renamed from: com.hiti.prinbiz.EditIdActivity.8 */
    class C07618 implements MSGListener {
        C07618() {
        }

        public void Close() {
        }

        public void OKClick() {
            if (EditIdActivity.this.m_bBackCollage) {
                EditIdActivity.this.finish();
            }
        }

        public void CancelClick() {
        }
    }

    class OnDrawViewListener extends DrawViewListener {
        OnDrawViewListener() {
        }

        public void OnZoomStart(float fZoom) {
        }

        public void OnZoomEnd(float fZoom) {
        }

        public void OnGSGarnishMode() {
        }

        public void OnColorGarnishMode() {
        }

        public void OnBorderMode() {
        }

        public void OnBrushMode() {
        }

        public void OnRollerMode() {
        }

        public void OnFilterMode() {
            EditIdActivity.this.CreatePictureEditThumbnail();
        }

        public void OnIDPhotoMode(Matrix matrix) {
        }

        public void OnFocusGarnish() {
        }

        public void OnMissFocusGarnish() {
        }
    }

    private class OnEditMetaListener extends EditMetaListener {
        private OnEditMetaListener() {
        }

        public void FetchingBegin() {
        }

        public void FetchImageDone(int pos, String strImagePath) {
        }

        public void FetchImgError(String strErr) {
            EditIdActivity.this.ShowWaitDialog(false);
            EditIdActivity.this.ShowErrorMSG(strErr);
        }

        public void FetchImgTimeOut(String strErr) {
            EditIdActivity.this.ShowWaitDialog(false);
            EditIdActivity.this.ShowErrorMSG(strErr);
        }

        public void InitDrawView(int iPos) {
            EditIdActivity.this.PrepareDrawView(iPos);
        }

        public void InitDrawViewEnd(int iState) {
            EditIdActivity.this.ModifyDrawView(iState);
        }

        public void FetchImgRatio(int iPos, String strRatio) {
            EditIdActivity.this.m_WaitingDialog.SetRatio(strRatio);
            EditIdActivity.this.m_WaitingDialog.ShowWaitingHintDialog(null, EditIdActivity.this.getString(C0349R.string.LOADING_IMAGE));
        }

        public void SaveEditPhoto() {
            EditIdActivity.this.SaveLastEditProgress(EditIdActivity.this.m_strXMLPath);
            FileUtility.CreateFolder(EditIdActivity.this.m_strSaveFolder);
            EditIdActivity.this.onSaveImageButtonClicked();
        }

        public void SaveEditPhotoDone() {
            EditIdActivity.this.PutEditDataBack();
        }

        public void DelFLValueData() {
        }
    }

    class RGBFilterColorSelectorListener extends OnRGBFilterColorSelectChangedListener {
        RGBFilterColorSelectorListener() {
        }

        public void onOKButtonClicked(View v) {
            EditIdActivity.this.ShowFilterSelector(false);
        }

        public void onResetButtonClicked(View v) {
            EditIdActivity.this.ResetFilterColorValue(EditIdActivity.this.m_FilterColorSelector.GetFilterType());
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
            EditIdActivity.this.m_FilterColorSelector.SetOKButtonStatus(true);
            GarnishItem garnishItem = EditIdActivity.this.m_EditDrawView.GetEditPhotoGarnishItem();
            EditIdActivity.this.SetFilterColorValue(fHue, fSaturation, fLight, fContrast);
            EditIdActivity.this.AdjustImageFilter(garnishItem, EditIdActivity.this.m_FilterColorValue);
        }

        public void onHueButtonClicked(View v) {
            EditIdActivity.this.DecideFilterColorButtonState(EditIdActivity.COLOR_RGB);
        }

        public void onBrightnessButtonClicked(View v) {
            EditIdActivity.this.DecideFilterColorButtonState(EditIdActivity.COLOR_HSLC);
        }

        public void onRGBColorChanged(float fRed, float fGreen, float fBlue) {
            EditIdActivity.this.m_FilterColorSelector.SetOKButtonStatus(true);
            GarnishItem garnishItem = EditIdActivity.this.m_EditDrawView.GetEditPhotoGarnishItem();
            EditIdActivity.this.SetFilterRGBvalue(fRed, fGreen, fBlue);
            EditIdActivity.this.AdjustImageFilter(garnishItem, EditIdActivity.this.m_FilterColorValue);
        }
    }

    public EditIdActivity() {
        this.m_FilterColorLayout = null;
        this.m_EditRelativeLayout = null;
        this.m_FilterSelectorLayout = null;
        this.m_VScaleRelativeLayout = null;
        this.m_VFineRelativeLayout = null;
        this.m_HScaleTableRow = null;
        this.m_HFineTableRow = null;
        this.m_PreviousImgButton = null;
        this.m_NextImgButton = null;
        this.m_FilterColorImgButton = null;
        this.m_BackButton = null;
        this.m_OKButton = null;
        this.m_SaveImageButton = null;
        this.m_BioLineImageView = null;
        this.m_AddButton = null;
        this.m_ReduceButton = null;
        this.m_FxButton = null;
        this.m_BorderButton = null;
        this.m_ResetButton = null;
        this.m_HRotateButton = null;
        this.m_HResetButton = null;
        this.m_ScaleUpButton = null;
        this.m_ScaleDownButton = null;
        this.m_HScaleUpButton = null;
        this.m_HScaleDownButton = null;
        this.m_FineUpButton = null;
        this.m_FineRightButton = null;
        this.m_FineLeftButton = null;
        this.m_FineDownButton = null;
        this.m_HFineUpButton = null;
        this.m_HFineRightButton = null;
        this.m_HFineLeftButton = null;
        this.m_HFineDownButton = null;
        this.m_prefAlbumInfo = null;
        this.m_EditMetaUtility = null;
        this.m_OnEditMetaListener = null;
        this.m_FilterRGBColorPainter = null;
        this.m_ColorPainter_Scorller = null;
        this.m_WaitingDialog = null;
        this.m_ErrorDialog = null;
        this.m_EditBmp = null;
        this.m_BioLineBitmap = null;
        this.m_iMaxW = 0;
        this.m_iMaxH = 0;
        this.m_iOriShort = 0;
        this.m_iOriLong = 0;
        this.m_prefInfo = null;
        this.m_iEditDrawViewHeight = 0;
        this.m_ViewScale = 0.0f;
        this.m_boFirstLoadPhotoVertical = true;
        this.m_EditDrawView = null;
        this.m_EditDrawViewBackgroundColor = 0;
        this.m_MSGDialog = null;
        this.m_ImageFilter = null;
        this.m_ImageRGB = null;
        this.m_boLastVertical = false;
        this.m_bCallBack = false;
        this.m_AssetManager = null;
        this.m_BorderImageButton = null;
        this.m_BorderRelativeLayout = null;
        this.m_BorderHorizontalListView = null;
        this.m_BorderViewHorizontalSpacing = 0;
        this.m_strBorderThumbnailPathList = null;
        this.m_NormalPaint = new Paint();
        this.m_iBorderItemSizeWidth = 0;
        this.m_iBorderItemSizeHeight = 0;
        this.m_iBorderItemSize = 0;
        this.m_iBrushItemSize = 0;
        this.m_iSelectBorder = 0;
        this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE = 5;
        this.m_DefaultUIThumbnailBmp = null;
        this.m_DefaultOrgThumbnailBmp = null;
        this.m_DefaultOrgThumbnailNoMatrixBmp = null;
        this.m_DefaultOrgThumbnailMatrix = null;
        this.m_HBorderGarnishSecurity = new GarnishSecurity(this);
        this.m_VBorderGarnishSecurity = new GarnishSecurity(this);
        this.m_strBorderRootPath = null;
        this.m_strRoot = null;
        this.m_strSavePath = null;
        this.m_SaveImage = null;
        this.m_EditFileUri = null;
        this.m_iEditPhotoWidth = 0;
        this.m_iEditPhotoHeight = 0;
        this.m_strCollageEditPath = null;
        this.m_strBiometricLinePath = null;
        this.m_strSaveFolder = null;
        this.m_strJustSaveFolder = null;
        this.m_strXMLPath = null;
        this.m_strLastXMLPath = null;
        this.m_iCollageInfoWidth = 0;
        this.m_iCollageInfoHeight = 0;
        this.m_iCollageInfoNum = 0;
        this.m_bBackCollage = false;
        this.m_FilterImageButton = null;
        this.m_FilterColorSelector = null;
        this.m_FilterRelativeLayout = null;
        this.m_FilterHorizontalListView = null;
        this.m_CollageIdEditInfo = null;
        this.m_FilterColorValue = null;
        this.m_FilterViewHorizontalSpacing = 0;
        this.m_iFilterCount = 0;
        this.m_iSelectFilter = 0;
        this.m_iFilterItemSize = 0;
        this.m_iFilterItemSizeWidth = 0;
        this.m_iFilterItemSizeHeight = 0;
        this.m_iFilterItemNameSizeHeight = 0;
        this.m_bNotChangeFilterSelector = false;
        this.m_bJustSave = false;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = null;
        this.FxListener = new C02865();
        this.ResetListener = new C02876();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_edit_idphoto);
        this.LOG = new LogManager(0);
        this.TAG = getClass().getSimpleName();
        this.LOG.m385i(this.TAG, "onCreate");
        UploadService.StopUploadService(this, UploadService.class);
        if (savedInstanceState != null) {
            this.LOG.m385i("onCreate", "savedInstanceState");
            this.m_bCallBack = true;
            ReBuildData(savedInstanceState);
        }
        GetPref();
        GetBundle();
        SetView();
        ShowFineButtonGroup(false);
        ShowWaitDialog(true);
        this.m_EditMetaUtility.InitDrawView(0);
    }

    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07591());
    }

    protected void onPause() {
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    private void ShowWaitDialog(boolean bShow) {
        if (bShow) {
            this.m_WaitingDialog.ShowWaitingHintDialog(null, getString(C0349R.string.PLEASE_WAIT));
        } else {
            this.m_WaitingDialog.StopWaitingDialog();
        }
    }

    private void ReBuildData(Bundle bundle) {
        this.m_bCallBack = false;
        int iLen = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST_LEN);
        int jLen = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LEN);
        for (int i = 0; i < iLen; i += COLOR_HSLC) {
            ArrayList<FilterColorValue> m_FCVList = new ArrayList();
            for (int j = 0; j < jLen; j += COLOR_HSLC) {
                FilterColorValue m_FCV = new FilterColorValue();
                m_FCV.m_fHue = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Hue");
                m_FCV.m_fSaturation = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Saturation");
                m_FCV.m_fLight = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Light");
                m_FCV.m_fContrast = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Contrast");
                m_FCV.m_fRed = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Red");
                m_FCV.m_fGreen = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Green");
                m_FCV.m_fBlue = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Blue");
                m_FCVList.add(m_FCV);
            }
        }
    }

    private void GetPref() {
        this.m_WaitingDialog = new ShowMSGDialog(this, false);
        this.m_EditMetaUtility = new EditMetaUtility(this);
        this.m_OnEditMetaListener = new OnEditMetaListener();
        this.m_FilterColorValue = new FilterColorValue();
        this.m_EditMetaUtility.SetEditMetaListener(this.m_OnEditMetaListener);
        MakeTempEditImageFolder();
    }

    private void GetBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.m_strCollageEditPath = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_PATH);
            this.m_iCollageInfoWidth = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_WIDTH);
            this.m_iCollageInfoHeight = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_HEIGHT);
            this.m_iCollageInfoNum = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_NUMBER);
            this.m_strLastXMLPath = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_XML);
            this.m_boLastVertical = bundle.getBoolean(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_VERTICAL);
            this.m_strBiometricLinePath = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_BIOMETRIC_LINE_PATH);
            this.m_FilterColorValue.m_fHue = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_HUE);
            this.m_FilterColorValue.m_fSaturation = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_SATURATION);
            this.m_FilterColorValue.m_fLight = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIGHT);
            this.m_FilterColorValue.m_fContrast = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_CONTRAST);
            this.m_FilterColorValue.m_fRed = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_RED);
            this.m_FilterColorValue.m_fGreen = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_GREEN);
            this.m_FilterColorValue.m_fBlue = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_BLUE);
            this.LOG.m383d(this.TAG, "getBundle m_strCollageEditPath: " + this.m_strCollageEditPath);
            this.LOG.m383d(this.TAG, "getBundle m_strLastXMLPath: " + this.m_strLastXMLPath);
        }
    }

    private void MakeTempEditImageFolder() {
        if (getExternalFilesDir(null) != null) {
            this.m_strRoot = getExternalFilesDir(null).getAbsolutePath() + "/" + PringoConvenientConst.PRINGO_TEMP_FOLDER;
            FileUtility.CreateFolder(this.m_strRoot);
            this.m_strSaveFolder = this.m_strRoot + File.separator + PringoConvenientConst.PRINBIZ_EDIT_IMG_FOLDER;
            return;
        }
        this.m_bBackCollage = true;
        ShowErrorMSG(BitmapMonitorResult.GetError(this, 95));
    }

    private void SetView() {
        this.m_OKButton = (ImageButton) findViewById(C0349R.id.m_OKButton);
        this.m_FxButton = (ImageButton) findViewById(C0349R.id.m_FxButton);
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_SaveImageButton = (ImageButton) findViewById(C0349R.id.m_SaveImageButton);
        this.m_EditRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_EditRelativeLayout);
        this.m_FilterColorLayout = (RelativeLayout) findViewById(C0349R.id.m_FilterColorLayout);
        this.m_MSGDialog = new ShowMSGDialog(this, false);
        this.m_ImageFilter = new ImageFilter(this);
        this.m_ImageRGB = new ImageFilterPlus(this);
        this.m_AssetManager = getAssets();
        this.m_EditDrawViewBackgroundColor = C0349R.color.WHITE;
        this.m_BackButton.setOnClickListener(new C02832());
        this.m_OKButton.setOnClickListener(new C02843());
        this.m_FxButton.setOnClickListener(this.FxListener);
        this.m_SaveImageButton.setOnClickListener(new C02854());
    }

    private void PrepareDrawView(int iState) {
        GetMaxSizeConfig();
        CalculateUISize();
        CalculateUIScale(this.m_strCollageEditPath);
        GetEditFileAndXML();
        InitEditDrawView();
        ModifyDrawView(iState);
    }

    private void ModifyDrawView(int iState) {
        boolean bHaveGarnish = true;
        if (this.m_bCallBack) {
            InitGarnishByLastEdit();
        } else if (this.m_strLastXMLPath == null || this.m_strLastXMLPath.isEmpty() || iState == RESET_DWRAW_VIEW) {
            bHaveGarnish = InitGarnish();
        } else {
            this.m_strXMLPath = this.m_strLastXMLPath;
            bHaveGarnish = InitGarnishByLastEdit();
        }
        if (bHaveGarnish) {
            this.m_EditRelativeLayout.removeAllViews();
            this.m_EditRelativeLayout.addView(this.m_EditDrawView);
            AddBioLineImageView(this.m_EditRelativeLayout);
            if (iState != RESET_DWRAW_VIEW) {
                InitFilterEffect();
            } else {
                ResetFilterColorValue(RESET_ALL);
            }
            ShowFineButtonGroup(true);
            ShowWaitDialog(false);
            return;
        }
        ShowWaitDialog(false);
    }

    private void InitFilterEffect() {
        GarnishItem garnishItem = this.m_EditDrawView.GetEditPhotoGarnishItem();
        SetFilterRGBvalue(this.m_FilterColorValue.m_fRed, this.m_FilterColorValue.m_fGreen, this.m_FilterColorValue.m_fBlue);
        AdjustImageFilter(garnishItem, this.m_FilterColorValue);
    }

    private int AddBioLineImageView(RelativeLayout EditRelativeLayout) {
        BitmapMonitorResult bmr = null;
        int iPhotoHeight = (int) (((float) this.m_iMaxH) / this.m_ViewScale);
        int iPhotoWidth = (int) (((float) this.m_iMaxW) / this.m_ViewScale);
        if (this.m_strBiometricLinePath == null || this.m_strBiometricLinePath.isEmpty()) {
            bmr = DrawDefaultCrossDashLine(iPhotoWidth, iPhotoHeight);
        } else {
            try {
                bmr = BitmapMonitor.CreateBitmap(getAssets().open(this.m_strBiometricLinePath), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            Bitmap bmp = bmr.GetBitmap();
            bmr = BitmapMonitor.CreateScaledBitmap(bmp, iPhotoWidth, iPhotoHeight, true);
            bmp.recycle();
        }
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        if (this.m_BioLineImageView == null) {
            this.m_BioLineImageView = new ImageView(this);
        }
        if (!(this.m_BioLineBitmap == null || this.m_BioLineBitmap.isRecycled())) {
            this.m_BioLineBitmap.recycle();
        }
        this.m_BioLineBitmap = bmr.GetBitmap();
        LayoutParams parmas = new LayoutParams(iPhotoWidth, iPhotoHeight);
        parmas.leftMargin = 0;
        parmas.topMargin = 0;
        this.m_BioLineImageView.setLayoutParams(parmas);
        this.m_BioLineImageView.setImageBitmap(this.m_BioLineBitmap);
        EditRelativeLayout.addView(this.m_BioLineImageView);
        return 0;
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.remove(JumpBundleMessage520.BUNDLE_MSG_EDIT_IMG_POS);
        outState.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LEN);
        SaveLastEditProgress(this.m_strXMLPath);
    }

    boolean SaveLastEditProgress(String strXMLPath) {
        if (this.m_EditDrawView.SaveNonORGPathGarnishItam(this.m_strRoot) != 0) {
            return false;
        }
        return this.m_EditDrawView.SaveGarnish2XML(strXMLPath);
    }

    void GetMaxSizeConfig() {
        this.m_iOriShort = this.m_iCollageInfoWidth;
        this.m_iOriLong = this.m_iCollageInfoHeight;
        this.m_iMaxW = this.m_iOriShort;
        this.m_iMaxH = this.m_iOriLong;
        this.LOG.m385i("m_iMaxW", String.valueOf(this.m_iMaxW));
        this.LOG.m385i("m_iMaxH", String.valueOf(this.m_iMaxH));
    }

    void CalculateUISize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.m_iEditDrawViewHeight = dm.widthPixels - ((getResources().getDrawable(C0349R.drawable.button_id_reset).getIntrinsicWidth() + 12) * COLOR_RGB);
    }

    private void GetEditFileAndXML() {
        this.m_strXMLPath = this.m_strRoot + File.separator + FileUtility.GetNewName(".xml", XmlPullParser.NO_NAMESPACE);
    }

    void CalculateUIScale(String strPath) {
        this.m_ViewScale = ((float) this.m_iMaxH) / ((float) this.m_iEditDrawViewHeight);
        if (this.m_iMaxW > this.m_iMaxH) {
            this.m_ViewScale = ((float) this.m_iMaxW) / ((float) this.m_iEditDrawViewHeight);
            int temp = this.m_iOriShort;
            this.m_iOriShort = this.m_iOriLong;
            this.m_iOriLong = temp;
        }
        if (DecideAutoRotate(Uri.parse("file://" + strPath))) {
            this.m_boFirstLoadPhotoVertical = false;
        } else {
            this.m_boFirstLoadPhotoVertical = true;
        }
    }

    public boolean DecideAutoRotate(Uri uri) {
        if (BitmapMonitor.IsVertical(this, uri)) {
            return false;
        }
        return true;
    }

    private void InitEditDrawView() {
        LayoutParams params1 = new LayoutParams(-2, -2);
        if (this.m_EditDrawView == null) {
            this.m_EditDrawView = new DrawView(this);
        } else {
            this.m_EditDrawView.Clear();
        }
        params1.height = (int) (((float) this.m_iMaxH) / this.m_ViewScale);
        params1.width = (int) (((float) this.m_iMaxW) / this.m_ViewScale);
        this.m_EditDrawView.setLayoutParams(params1);
        int iResult = this.m_EditDrawView.InitDrawView(((float) this.m_iMaxW) / this.m_ViewScale, ((float) this.m_iMaxH) / this.m_ViewScale, ((float) this.m_iMaxW) / this.m_ViewScale, ((float) this.m_iMaxH) / this.m_ViewScale, this.m_boFirstLoadPhotoVertical, this.m_iOriShort, this.m_iOriLong);
        this.LOG.m383d(this.TAG, "InitEditDrawView " + this.m_iOriShort);
        this.LOG.m383d(this.TAG, "InitEditDrawView " + this.m_iOriLong);
        if (iResult != 0) {
            ShowErrorMSG(String.valueOf(BitmapMonitorResult.GetError(this, iResult)));
            return;
        }
        this.m_EditDrawView.SetViewScale(this.m_ViewScale);
        this.m_EditDrawView.SetDrawViewListener(new OnDrawViewListener());
        this.m_EditDrawView.setBackgroundColor(getResources().getColor(this.m_EditDrawViewBackgroundColor));
    }

    boolean InitGarnish() {
        if (InitGarnish_Background() && InitGarnish_EditPhoto(this.m_strCollageEditPath)) {
            return true;
        }
        return false;
    }

    boolean InitGarnish_Background() {
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(this.m_iMaxW, this.m_iMaxH, Config.ARGB_8888);
        if (bmr.IsSuccess()) {
            bmr.GetBitmap().eraseColor(getResources().getColor(this.m_EditDrawViewBackgroundColor));
            if (this.m_EditDrawView.AddGarnish(bmr.GetBitmap(), new PointF(this.m_EditDrawView.GetViewWindowCenterX(), this.m_EditDrawView.GetViewWindowCenterY()), 0, null, 0) <= 0) {
                ShowErrorMSG(getString(C0349R.string.CREATE_BITMAP_OUT_OF_MEMORY));
                return false;
            }
            bmr.GetBitmap().recycle();
            return true;
        }
        ShowErrorMSG(BitmapMonitorResult.GetError(this, bmr.GetResult()).toString());
        return false;
    }

    boolean InitGarnish_EditPhoto(String strEditFile) {
        if (BitmapMonitor.BitmapExist(this, strEditFile)) {
            BitmapMonitorResult bmr = BitmapMonitor.CreateCroppedBitmapNew(this, Uri.parse("file://" + strEditFile), this.m_iMaxW, this.m_iMaxH);
            if (bmr.IsSuccess()) {
                Bitmap editPicture = bmr.GetBitmap();
                GarnishItem photoGarnishItem = new GarnishItem(this, COLOR_HSLC);
                int iResult = photoGarnishItem.InitUIView(editPicture, new PointF(this.m_EditDrawView.GetViewWindowCenterX(), this.m_EditDrawView.GetViewWindowCenterY()), this.m_ViewScale, "file://" + strEditFile, COLOR_RGB);
                if (iResult != 0) {
                    ShowErrorMSG(BitmapMonitorResult.GetError(this, iResult));
                    return false;
                }
                this.m_EditDrawView.AddGarnish(photoGarnishItem);
                this.m_iEditPhotoWidth = editPicture.getWidth();
                this.m_iEditPhotoHeight = editPicture.getHeight();
                this.LOG.m383d(this.TAG, "InitGarnish_EditPhoto: " + this.m_iMaxW);
                this.LOG.m383d(this.TAG, "InitGarnish_EditPhoto: " + this.m_iMaxH);
                this.LOG.m383d(this.TAG, "InitGarnish_EditPhoto: " + this.m_iEditPhotoWidth);
                this.LOG.m383d(this.TAG, "InitGarnish_EditPhoto: " + this.m_iEditPhotoHeight);
                editPicture.recycle();
                if (!strEditFile.contains(PringoConvenientConst.PRINGO_COLLAGE_FILE)) {
                    if (!strEditFile.contains(PringoConvenientConst.PRINBIZ_FOLDER)) {
                        return true;
                    }
                    if (!FileUtility.FileExist(this.m_strSaveFolder + "/" + FileUtility.GetFileName(strEditFile))) {
                        return true;
                    }
                }
                bmr = BitmapMonitor.CreateBitmap(this.m_iEditPhotoWidth, this.m_iEditPhotoHeight, Config.ARGB_8888);
                if (bmr.IsSuccess()) {
                    Bitmap maskBitmap = bmr.GetBitmap();
                    if (HitiChunkUtility.GetMaskFromFile(strEditFile, maskBitmap, getResources().getColor(C0349R.color.GS_COLOR_NO_ALPHA))) {
                        GarnishItem maskItem = new GarnishItem(this, 7);
                        iResult = maskItem.InitUIView(maskBitmap, new PointF(this.m_EditDrawView.GetViewWindowCenterX(), this.m_EditDrawView.GetViewWindowCenterY()), this.m_ViewScale, null, 0);
                        if (iResult != 0) {
                            ShowErrorMSG(BitmapMonitorResult.GetError(this, iResult));
                            return false;
                        }
                        GarnishItem.SaveGarnishORGBitmap(this, "/temp", maskItem, maskBitmap);
                        this.m_EditDrawView.AddGarnish(maskItem);
                    }
                    maskBitmap.recycle();
                    return true;
                }
                ShowErrorMSG(BitmapMonitorResult.GetError(this, bmr.GetResult()));
                return false;
            }
            ShowErrorMSG(BitmapMonitorResult.GetError(this, bmr.GetResult()));
            return false;
        }
        ShowErrorMSG(getString(C0349R.string.CREATE_BITMAP_SOURCE_NOT_FOUND));
        return false;
    }

    boolean InitGarnishByLastEdit() {
        InitGarnish_Background();
        try {
            ArrayList GarnishList = GarnishItemXMLCreator.ReadGarnishXML(new BufferedInputStream(getContentResolver().openInputStream(Uri.parse("file://" + this.m_strXMLPath))), this, this.m_iMaxW, this.m_iMaxH);
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
            ShowErrorMSG(getString(C0349R.string.CREATE_BITMAP_SOURCE_NOT_FOUND));
            e.printStackTrace();
            return false;
        }
    }

    boolean RotateEditDrawViewWithoutContent() {
        int temp = this.m_iMaxW;
        this.m_iMaxW = this.m_iMaxH;
        this.m_iMaxH = temp;
        int iResult = this.m_EditDrawView.Rotate90WithoutContent();
        if (iResult == 0) {
            return true;
        }
        ShowErrorMSG(BitmapMonitorResult.GetError(this, iResult));
        return false;
    }

    void SetImageFilter(GarnishItem garnishItem, IMAGE_FILTER_TYPE filterType) {
        BitmapMonitorResult bmr = BitmapMonitor.Copy(garnishItem.GetBackUpViewScaleBitmap(), Config.ARGB_8888, true);
        if (bmr.IsSuccess()) {
            Bitmap bmp = bmr.GetBitmap();
            this.m_ImageFilter.ProcessImage(bmp, filterType);
            int iResult = garnishItem.SetFilterViewScaleBitmap(bmp, filterType.toString());
            if (iResult != 0) {
                ShowErrorMSG(BitmapMonitorResult.GetError(this, iResult));
                return;
            }
            this.m_EditDrawView.SetEdit(true);
            this.m_EditDrawView.invalidate();
            return;
        }
        ShowErrorMSG(BitmapMonitorResult.GetError(this, bmr.GetResult()));
    }

    synchronized void onRotateImageButtonClicked(View v) {
        ShowWaitDialog(true);
        RotateBorder();
        int iResult = this.m_EditDrawView.Rotate90WithContent();
        if (iResult != 0) {
            ShowErrorMSG(BitmapMonitorResult.GetError(this, iResult));
        } else if (!CreateFilter()) {
        }
    }

    private void RotateBorder() {
        int temp = this.m_iMaxW;
        this.m_iMaxW = this.m_iMaxH;
        this.m_iMaxH = temp;
        GarnishItem CGarnishItem = this.m_EditDrawView.GetBorderGarnishItem(COLOR_RGB);
        GarnishItem GSGarnishItem = this.m_EditDrawView.GetBorderGarnishItem(5);
        if (CGarnishItem != null) {
            RotateBorderGarnish(CGarnishItem);
        }
        if (GSGarnishItem != null) {
            RotateBorderGarnish(GSGarnishItem);
        }
    }

    private void RotateBorderGarnish(GarnishItem garnishItem) {
        Object obj = null;
        Bitmap Borderthumbnail = null;
        Long lID = Long.valueOf(0);
        Long lComposeID = Long.valueOf(0);
        if (garnishItem != null) {
            BitmapMonitorResult bmr;
            String strBorderPath = garnishItem.GetGarnishPath();
            lID = Long.valueOf(garnishItem.GetID());
            lComposeID = Long.valueOf(garnishItem.GetComposeID());
            int iType = garnishItem.GetType();
            int iFromType = garnishItem.GetFromType();
            if (!this.m_EditDrawView.IsVertical()) {
                obj = COLOR_HSLC;
            }
            if (obj == COLOR_HSLC) {
                if (strBorderPath.contains(this.m_strBorderRootPath + "/" + PringoConvenientConst.H_BORDER_PATH)) {
                    strBorderPath = strBorderPath.replace(PringoConvenientConst.H_BORDER_PATH, PringoConvenientConst.V_BORDER_PATH);
                }
            } else if (strBorderPath.contains(this.m_strBorderRootPath + "/" + PringoConvenientConst.V_BORDER_PATH)) {
                strBorderPath = strBorderPath.replace(PringoConvenientConst.V_BORDER_PATH, PringoConvenientConst.H_BORDER_PATH);
            }
            if (iFromType == COLOR_RGB) {
                try {
                    bmr = BitmapMonitor.CreateBitmap(strBorderPath, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bmr = BitmapMonitor.CreateBitmap(this.m_AssetManager.open(strBorderPath), false);
            }
            if (bmr.IsSuccess()) {
                Borderthumbnail = bmr.GetBitmap();
                GarnishItem rgarnishItem = new GarnishItem(this, iType);
                int iResult = rgarnishItem.InitUIView(Borderthumbnail, new PointF(this.m_EditDrawView.GetViewWindowCenterX(), this.m_EditDrawView.GetViewWindowCenterY()), this.m_ViewScale, strBorderPath, iFromType);
                if (iResult != 0) {
                    ShowErrorMSG(BitmapMonitorResult.GetError(this, iResult));
                    return;
                }
                Borderthumbnail.recycle();
                rgarnishItem.SetID(lID.longValue());
                rgarnishItem.SetComposeID(lComposeID.longValue());
                this.m_EditDrawView.AddGarnish(rgarnishItem);
                return;
            }
            ShowErrorMSG(BitmapMonitorResult.GetError(this, bmr.GetResult()));
        }
    }

    private void OnResetListener(View v) {
        ShowWaitDialog(true);
        this.m_EditMetaUtility.InitDrawView(RESET_DWRAW_VIEW);
    }

    private void PutEditDataBack() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_NUMBER);
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_TMP);
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_XML);
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_VERTICAL);
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_IS_EDIT);
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_HUE);
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_SATURATION);
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIGHT);
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_CONTRAST);
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_RED);
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_GREEN);
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_BLUE);
        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_XML, this.m_strXMLPath);
        bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_NUMBER, this.m_iCollageInfoNum);
        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_TMP, GetSavePath());
        bundle.putBoolean(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_VERTICAL, this.m_boLastVertical);
        bundle.putBoolean(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_IS_EDIT, IsFilterColorValueModified());
        bundle.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_HUE, this.m_FilterColorValue.m_fHue);
        bundle.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_SATURATION, this.m_FilterColorValue.m_fSaturation);
        bundle.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIGHT, this.m_FilterColorValue.m_fLight);
        bundle.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_CONTRAST, this.m_FilterColorValue.m_fContrast);
        bundle.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_RED, this.m_FilterColorValue.m_fRed);
        bundle.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_GREEN, this.m_FilterColorValue.m_fGreen);
        bundle.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_BLUE, this.m_FilterColorValue.m_fBlue);
        this.LOG.m383d("PutEditDataBack", "Edit temp: " + GetSavePath());
        this.LOG.m383d("PutEditDataBack", "m_strXMLPath: " + this.m_strXMLPath);
        intent.putExtras(bundle);
        setResult(67, intent);
        finish();
    }

    void onSaveImageButtonClicked() {
        ShowWaitDialog(true);
        this.m_EditDrawView.ResetZoom();
        if (FileUtility.SDCardState()) {
            this.m_SaveImage = new SaveImage(this.m_strCollageEditPath);
            this.m_SaveImage.execute(new Void[0]);
            return;
        }
        ShowWaitDialog(false);
        ShowErrorMSG(getString(C0349R.string.SD_CARD_NOT_READYING));
    }

    private void SetSavePath(String strSavePath) {
        this.m_strSavePath = strSavePath;
    }

    private String GetSavePath() {
        return this.m_strSavePath;
    }

    private boolean SaveBitmap2File(String strSavePath, Bitmap bmp, CompressFormat format) {
        boolean bResult = FileUtility.SaveBitmap(strSavePath, bmp, format);
        this.m_EditFileUri = MediaUtil.SaveIntoMedia(this, strSavePath);
        return bResult;
    }

    void ShowSharePhotoAlertDialog() {
        this.m_MSGDialog.SetMSGListener(new C07607());
        this.m_MSGDialog.ShowMessageDialog(getString(C0349R.string.CHECK_SHARE_PHOTO), XmlPullParser.NO_NAMESPACE);
    }

    void OpenShareList(Uri uri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.putExtra("android.intent.extra.STREAM", uri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(C0349R.string.SHARE_PHOTO_TO)));
    }

    private void ShowErrorMSG(String strErr) {
        ShowWaitDialog(false);
        if (this.m_ErrorDialog == null) {
            this.m_ErrorDialog = new ShowMSGDialog(this, false);
            this.m_ErrorDialog.SetMSGListener(new C07618());
        }
        this.m_ErrorDialog.ShowSimpleMSGDialog(strErr, getString(C0349R.string.ERROR));
    }

    protected void onDestroy() {
        ShowWaitDialog(false);
        super.onDestroy();
    }

    public void onBackPressed() {
        setResult(0, new Intent());
        finish();
    }

    void Exit() {
        setResult(0, new Intent());
        this.m_EditDrawView.Clear();
        finish();
    }

    boolean CreateDefaultOrgThumbnail(float fScale, float fBoundX, float fBoundY) {
        float[] MatrixValue = new float[9];
        GarnishItem garnishItem = this.m_EditDrawView.GetEditPhotoGarnishItem();
        if (garnishItem == null) {
            return false;
        }
        garnishItem.GetMatrix().getValues(MatrixValue);
        Bitmap DeleteDefaultOrgThumbnailBmp = this.m_DefaultOrgThumbnailBmp;
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(this.m_iMaxW / this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE, this.m_iMaxH / this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE, Config.ARGB_8888);
        if (!bmr.IsSuccess()) {
            return false;
        }
        Bitmap DefaultOrgThumbnailBmp = bmr.GetBitmap();
        DefaultOrgThumbnailBmp.eraseColor(getResources().getColor(this.m_EditDrawViewBackgroundColor));
        Canvas canvas = new Canvas(DefaultOrgThumbnailBmp);
        canvas.translate((MatrixValue[COLOR_RGB] * fScale) - (fBoundX * fScale), (MatrixValue[5] * fScale) - (fBoundY * fScale));
        canvas.rotate(garnishItem.GetDegree());
        canvas.scale(garnishItem.GetScale(), garnishItem.GetScale());
        CreateDefaultOrgThumbnailMatrix(canvas.getMatrix());
        canvas.scale(fScale, fScale);
        canvas.drawBitmap(garnishItem.GetUIBitmap(true), 0.0f, 0.0f, this.m_NormalPaint);
        this.m_DefaultOrgThumbnailBmp = DefaultOrgThumbnailBmp;
        if (DeleteDefaultOrgThumbnailBmp != null) {
            DeleteDefaultOrgThumbnailBmp.recycle();
        }
        if (this.m_DefaultOrgThumbnailNoMatrixBmp != null) {
            this.m_DefaultOrgThumbnailNoMatrixBmp.recycle();
        }
        this.m_DefaultOrgThumbnailNoMatrixBmp = null;
        if (this.m_DefaultOrgThumbnailNoMatrixBmp == null) {
            bmr = BitmapMonitor.CreateBitmap((int) ((((float) garnishItem.GetUIBitmap(true).getWidth()) * this.m_ViewScale) / ((float) this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE)), (int) ((((float) garnishItem.GetUIBitmap(true).getHeight()) * this.m_ViewScale) / ((float) this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE)), Config.ARGB_8888);
            if (!bmr.IsSuccess()) {
                return false;
            }
            this.m_DefaultOrgThumbnailNoMatrixBmp = bmr.GetBitmap();
            if (this.m_DefaultOrgThumbnailNoMatrixBmp == null) {
                return false;
            }
            canvas = new Canvas(this.m_DefaultOrgThumbnailNoMatrixBmp);
            canvas.scale(fScale, fScale);
            canvas.drawBitmap(garnishItem.GetUIBitmap(true), 0.0f, 0.0f, this.m_NormalPaint);
        }
        return true;
    }

    void CreateDefaultOrgThumbnailMatrix(Matrix matrix) {
        if (this.m_DefaultOrgThumbnailMatrix == null) {
            this.m_DefaultOrgThumbnailMatrix = new Matrix();
        }
        this.m_DefaultOrgThumbnailMatrix.reset();
        this.m_DefaultOrgThumbnailMatrix.set(matrix);
    }

    boolean CreateDefaultUIThumbnail(float fScale, float fBoundX, float fBoundY) {
        float[] MatrixValue = new float[9];
        GarnishItem garnishItem = this.m_EditDrawView.GetEditPhotoGarnishItem();
        garnishItem.GetMatrix().getValues(MatrixValue);
        Bitmap DeleteDefaultIUThumbnailBmp = this.m_DefaultUIThumbnailBmp;
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(this.m_iMaxW / this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE, this.m_iMaxH / this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE, Config.ARGB_8888);
        if (!bmr.IsSuccess()) {
            return false;
        }
        Bitmap DefaultUIThumbnailBmp = bmr.GetBitmap();
        DefaultUIThumbnailBmp.eraseColor(getResources().getColor(this.m_EditDrawViewBackgroundColor));
        Canvas canvas = new Canvas(DefaultUIThumbnailBmp);
        canvas.translate((MatrixValue[COLOR_RGB] * fScale) - (fBoundX * fScale), (MatrixValue[5] * fScale) - (fBoundY * fScale));
        canvas.rotate(garnishItem.GetDegree());
        canvas.scale(garnishItem.GetScale(), garnishItem.GetScale());
        CreateDefaultOrgThumbnailMatrix(canvas.getMatrix());
        canvas.scale(fScale, fScale);
        canvas.drawBitmap(garnishItem.GetUIBitmap(), 0.0f, 0.0f, this.m_NormalPaint);
        this.m_DefaultUIThumbnailBmp = DefaultUIThumbnailBmp;
        if (DeleteDefaultIUThumbnailBmp != null) {
            DeleteDefaultIUThumbnailBmp.recycle();
        }
        return true;
    }

    boolean CreatePictureEditThumbnail() {
        float fScale = this.m_ViewScale / ((float) this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE);
        this.m_BorderViewHorizontalSpacing = 12;
        this.m_FilterViewHorizontalSpacing = 12;
        this.m_iBorderItemSizeWidth = (((int) (((float) this.m_iMaxW) / this.m_ViewScale)) / this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE) + this.m_BorderViewHorizontalSpacing;
        this.m_iBorderItemSizeHeight = ((int) (((float) this.m_iMaxH) / this.m_ViewScale)) / this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE;
        this.m_iFilterItemSizeWidth = (((int) (((float) this.m_iMaxW) / this.m_ViewScale)) / this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE) + this.m_FilterViewHorizontalSpacing;
        this.m_iFilterItemSizeHeight = ((int) (((float) this.m_iMaxH) / this.m_ViewScale)) / this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE;
        if (!CreateDefaultOrgThumbnail(fScale, this.m_EditDrawView.GetBoundX(), this.m_EditDrawView.GetBoundY())) {
            ShowErrorMSG(getString(C0349R.string.CREATE_BITMAP_OUT_OF_MEMORY));
            return false;
        } else if (CreateDefaultUIThumbnail(fScale, this.m_EditDrawView.GetBoundX(), this.m_EditDrawView.GetBoundY())) {
            return true;
        } else {
            ShowErrorMSG(getString(C0349R.string.CREATE_BITMAP_OUT_OF_MEMORY));
            return false;
        }
    }

    boolean CheckVerifyBorder(String strBorderPath) {
        if (strBorderPath == null) {
            return false;
        }
        GarnishSecurity security = this.m_HBorderGarnishSecurity;
        if (strBorderPath.contains("vborder")) {
            security = this.m_VBorderGarnishSecurity;
        }
        if (security.CheckItemVerify(strBorderPath)) {
            return true;
        }
        return false;
    }

    boolean CreateFilter() {
        Log.e("!!!!!", "CreateFilter");
        if (!CreatePictureEditThumbnail()) {
            return false;
        }
        Log.e("???", "CreateFilter");
        if (this.m_FilterColorSelector == null) {
            this.m_FilterColorSelector = (FilterRGBColorSelector) findViewById(C0349R.id.m_FilterColorSelector);
            this.m_FilterColorSelector.setOnColorChangedListener(new RGBFilterColorSelectorListener());
        }
        InitFilterColorPosition(0);
        InitFilterRGBColorPosition(0);
        if (this.m_bNotChangeFilterSelector) {
            this.m_bNotChangeFilterSelector = false;
        } else {
            this.m_FilterColorLayout.setVisibility(0);
        }
        return true;
    }

    private void FilterRGB(Bitmap bmpEdit, FilterColorValue filterColorValue) {
        if (filterColorValue.HaveModifyRGBValue()) {
            this.m_ImageRGB.ProcessImage_RGB(bmpEdit, filterColorValue.m_fRed, filterColorValue.m_fGreen, filterColorValue.m_fBlue);
        }
    }

    private void FilterHSLC(Bitmap bmpEdit, FilterColorValue filterColorValue) {
        if (filterColorValue.HaveModifyHSLCValue()) {
            this.m_ImageFilter.ProcessImage_HSBC(bmpEdit, IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_adjust_S_B_C_new, 0.0f, filterColorValue.m_fSaturation, filterColorValue.m_fLight, filterColorValue.m_fContrast);
        }
    }

    boolean IsFilterColorValueModified() {
        if (this.m_FilterColorValue.HaveModifyRGBValue() || this.m_FilterColorValue.HaveModifyHSLCValue()) {
            return true;
        }
        return false;
    }

    void ResetFilterColorValue(int iType) {
        if (this.m_FilterColorSelector == null) {
            this.m_bNotChangeFilterSelector = true;
            CreateFilter();
        }
        if (IsFilterColorValueModified()) {
            GarnishItem garnishItem = this.m_EditDrawView.GetEditPhotoGarnishItem();
            garnishItem.ResetFilterViewScaleBitmap();
            this.m_FilterColorSelector.SetOKButtonStatus(false);
            if (iType != COLOR_RGB) {
                this.m_FilterColorSelector.SetHSVCAndPostion(0.0f, 0.0f, 0.0f, 0.0f);
                SetFilterColorValue(0.0f, 0.0f, 0.0f, 0.0f);
            }
            if (iType != COLOR_HSLC) {
                this.m_FilterColorSelector.SetRGBtoPostion(0.0f, 0.0f, 0.0f);
                SetFilterRGBvalue(0.0f, 0.0f, 0.0f);
            }
            AdjustImageFilter(garnishItem, this.m_FilterColorValue);
        }
    }

    void AdjustImageFilter(GarnishItem garnishItem, FilterColorValue filterColorValue) {
        if (IsFilterColorValueModified()) {
            Bitmap oriBmp = GetOriFilterBmp(garnishItem);
            if (oriBmp != null) {
                FilterHSLC(oriBmp, filterColorValue);
                FilterRGB(oriBmp, filterColorValue);
                int iResult = garnishItem.SetFilterColorViewScaleBitmap(oriBmp, filterColorValue.m_fHue, filterColorValue.m_fSaturation, filterColorValue.m_fLight, filterColorValue.m_fContrast, filterColorValue.m_fRed, filterColorValue.m_fGreen, filterColorValue.m_fBlue);
                if (iResult != 0) {
                    ShowErrorMSG(BitmapMonitorResult.GetError(this, iResult));
                    return;
                }
                this.m_EditDrawView.SetEdit(true);
                this.m_EditDrawView.invalidate();
                return;
            }
            return;
        }
        this.m_EditDrawView.invalidate();
    }

    private Bitmap GetOriFilterBmp(GarnishItem garnishItem) {
        if (garnishItem.GetBackUpViewScaleBitmap() == null) {
            garnishItem.SetBackUpViewScaleBitmap();
        }
        if (garnishItem.GetBackUpFilterViewScaleBitmap() == null) {
            garnishItem.SetBackUpFilterViewScaleBitmap();
        }
        BitmapMonitorResult bmr = BitmapMonitor.Copy(garnishItem.GetBackUpFilterViewScaleBitmap(), Config.ARGB_8888, true);
        if (bmr.IsSuccess()) {
            return bmr.GetBitmap();
        }
        ShowErrorMSG(BitmapMonitorResult.GetError(this, bmr.GetResult()));
        return null;
    }

    void SetFilterColorValue(float fHue, float fSaturation, float fLight, float fContrast) {
        this.m_FilterColorValue.m_fHue = fHue;
        this.m_FilterColorValue.m_fSaturation = fSaturation;
        this.m_FilterColorValue.m_fLight = fLight;
        this.m_FilterColorValue.m_fContrast = fContrast;
    }

    private void SetFilterRGBvalue(float fRed, float fGreen, float fBlue) {
        this.m_FilterColorValue.m_fRed = fRed;
        this.m_FilterColorValue.m_fGreen = fGreen;
        this.m_FilterColorValue.m_fBlue = fBlue;
    }

    void InitFilterColorPosition(int iID) {
        this.m_FilterColorSelector.SetHSVCAndPostion(this.m_FilterColorValue.m_fHue, this.m_FilterColorValue.m_fSaturation, this.m_FilterColorValue.m_fLight, this.m_FilterColorValue.m_fContrast);
    }

    void InitFilterRGBColorPosition(int iID) {
        this.m_FilterColorSelector.SetRGBtoPostion(this.m_FilterColorValue.m_fRed, this.m_FilterColorValue.m_fGreen, this.m_FilterColorValue.m_fBlue);
    }

    void DecideFilterColorButtonState(int iType) {
        if (this.m_FilterColorLayout.isShown()) {
            boolean bIsModify;
            if (iType == COLOR_HSLC) {
                bIsModify = this.m_FilterColorValue.HaveModifyHSLCValue();
            } else {
                bIsModify = this.m_FilterColorValue.HaveModifyRGBValue();
            }
            if (bIsModify) {
                this.m_FilterColorSelector.SetOKButtonStatus(true);
            } else {
                this.m_FilterColorSelector.SetOKButtonStatus(false);
            }
        }
    }

    void ShowFilterSelector(boolean bShow) {
        if (bShow) {
            this.m_FilterColorLayout.setVisibility(0);
            this.m_FilterColorSelector.onBrightnessButtonClicked(null);
            return;
        }
        this.m_FilterColorLayout.setVisibility(8);
        this.m_FxButton.setBackgroundResource(C0349R.drawable.fx_button);
    }

    void ShowFineButtonGroup(boolean boShow) {
        this.m_ScaleDownButton = (Button) findViewById(C0349R.id.m_ScaleDownButton);
        this.m_ScaleDownButton.setOnClickListener(new C02889());
        this.m_ScaleUpButton = (Button) findViewById(C0349R.id.m_ScaleUpButton);
        this.m_ScaleUpButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (EditIdActivity.this.m_EditDrawView != null) {
                    EditIdActivity.this.m_EditDrawView.SetEditPhotoGarnishScale(0.1f);
                }
            }
        });
        this.m_FineDownButton = (Button) findViewById(C0349R.id.m_FineDownButton);
        this.m_FineDownButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (EditIdActivity.this.m_EditDrawView != null) {
                    EditIdActivity.this.m_EditDrawView.SetEditPhotoGarnishTrans(0.0f, 3.0f);
                }
            }
        });
        this.m_FineUpButton = (Button) findViewById(C0349R.id.m_FineUpButton);
        this.m_FineUpButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (EditIdActivity.this.m_EditDrawView != null) {
                    EditIdActivity.this.m_EditDrawView.SetEditPhotoGarnishTrans(0.0f, -3.0f);
                }
            }
        });
        this.m_FineLeftButton = (Button) findViewById(C0349R.id.m_FineLeftButton);
        this.m_FineLeftButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (EditIdActivity.this.m_EditDrawView != null) {
                    EditIdActivity.this.m_EditDrawView.SetEditPhotoGarnishTrans(-3.0f, 0.0f);
                }
            }
        });
        this.m_FineRightButton = (Button) findViewById(C0349R.id.m_FineRightButton);
        this.m_FineRightButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (EditIdActivity.this.m_EditDrawView != null) {
                    EditIdActivity.this.m_EditDrawView.SetEditPhotoGarnishTrans(3.0f, 0.0f);
                }
            }
        });
        this.m_ResetButton = (ImageButton) findViewById(C0349R.id.m_ResetButton);
        this.m_ResetButton.setOnClickListener(this.ResetListener);
    }

    BitmapMonitorResult DrawDefaultCrossDashLine(int iWidth, int iHeight) {
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(iWidth, iHeight, Config.ARGB_4444);
        this.LOG.m385i(this.TAG, "DrawDefaultCrossDashLine");
        if (bmr.GetResult() == 0) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Style.STROKE);
            paint.setColor(getResources().getColor(C0349R.color.SETTING_ORANGE));
            paint.setStrokeWidth(TypedValue.applyDimension(COLOR_HSLC, 1.0f, getResources().getDisplayMetrics()));
            paint.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f, 5.0f, 5.0f}, 1.0f));
            Canvas canvas = new Canvas(bmr.GetBitmap());
            canvas.drawLine((float) (iWidth / COLOR_RGB), 0.0f, (float) (iWidth / COLOR_RGB), (float) iHeight, paint);
            canvas.drawLine(0.0f, (float) (iHeight / COLOR_RGB), (float) iWidth, (float) (iHeight / COLOR_RGB), paint);
        }
        return bmr;
    }
}
