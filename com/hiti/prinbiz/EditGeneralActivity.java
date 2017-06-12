package com.hiti.prinbiz;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.Flurry.FlurryLogString;
import com.hiti.Flurry.FlurryUtility;
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
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.service.upload.UploadService;
import com.hiti.trace.GlobalVariable_AlbumSelInfo;
import com.hiti.trace.GlobalVariable_PrintSettingInfo;
import com.hiti.ui.cacheadapter.CacheAdapter;
import com.hiti.ui.cacheadapter.viewholder.BaseViewHolder;
import com.hiti.ui.cacheadapter.viewholder.BorderViewHolder;
import com.hiti.ui.cacheadapter.viewholder.FilterViewHolder;
import com.hiti.ui.drawview.DrawView;
import com.hiti.ui.drawview.DrawViewListener;
import com.hiti.ui.drawview.garnishitem.FilterColorValue;
import com.hiti.ui.drawview.garnishitem.GarnishItem;
import com.hiti.ui.drawview.garnishitem.PathLoader.BorderLoader;
import com.hiti.ui.drawview.garnishitem.PathLoader.LoadFinishListener;
import com.hiti.ui.drawview.garnishitem.parser.GarnishItemXMLCreator;
import com.hiti.ui.drawview.garnishitem.security.GarnishSecurity;
import com.hiti.ui.drawview.garnishitem.utility.EditMeta;
import com.hiti.ui.drawview.garnishitem.utility.EditMetaListener;
import com.hiti.ui.drawview.garnishitem.utility.EditMetaUtility;
import com.hiti.ui.drawview.garnishitem.utility.GarnishItemUtility;
import com.hiti.ui.horizontallistview.HorizontalListView;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.MediaUtil;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.dialog.CreateWaitDialog;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;

public class EditGeneralActivity extends Activity {
    private static final int BORDER_STATE = 99;
    private static final int COLOR_HSLC = 1;
    private static final int COLOR_RGB = 2;
    private static int COPIES_MAX = 0;
    private static int COPIES_MIN = 0;
    private static final int FILTER_STATE = 94;
    private static final int HORIZONTAL_CACHE_BITMAP_SIZE = 4;
    private static final int NEXT_PAGE = 111;
    private static final int PENDDING_SIZE = 8;
    private static final String PRINBIZ_EDIT_XML = "_EM.xml";
    private OnClickListener AddCopiesListener;
    private OnClickListener BorderListener;
    int DEFAULT_ORG_THUMBNAIL_VIEW_SCALE;
    private OnClickListener FxListener;
    private String IP;
    LogManager LOG;
    private OnClickListener ReduceCopiesListener;
    private OnClickListener ResetListener;
    private OnClickListener RotateListener;
    private OnClickListener SavePhotoListener;
    private OnClickListener ShowFilterListener;
    NFCInfo mNFCInfo;
    ImageButton m_AddButton;
    private AssetManager m_AssetManager;
    BorderAdapter m_BorderAdapter;
    ImageButton m_BorderButton;
    HorizontalListView m_BorderHorizontalListView;
    ImageButton m_BorderImageButton;
    RelativeLayout m_BorderLayout;
    BorderLoadFinishListener m_BorderLoadFinishListener;
    BorderLoader m_BorderLoader;
    RelativeLayout m_BorderRelativeLayout;
    int m_BorderViewHorizontalSpacing;
    ColorPainter_Scorller m_ColorPainter_Scorller;
    RelativeLayout m_ContentLayout;
    TextView m_CopiesTextView;
    Bitmap m_DefaultOrgThumbnailBmp;
    Matrix m_DefaultOrgThumbnailMatrix;
    Bitmap m_DefaultOrgThumbnailNoMatrixBmp;
    Bitmap m_DefaultUIThumbnailBmp;
    Bitmap m_EditBmp;
    private DrawView m_EditDrawView;
    private int m_EditDrawViewBackgroundColor;
    private Uri m_EditFileUri;
    private EditMeta m_EditMeta;
    private EditMetaUtility m_EditMetaUtility;
    RelativeLayout m_EffectLayout;
    private ShowMSGDialog m_ErrorDialog;
    ArrayList<ArrayList<FilterColorValue>> m_FCVLList;
    FilterAdapter m_FilterAdapter;
    ImageButton m_FilterColorImgButton;
    RelativeLayout m_FilterColorLayout;
    FilterRGBColorSelector m_FilterColorSelector;
    ArrayList<FilterColorValue> m_FilterColorValueList;
    HorizontalListView m_FilterHorizontalListView;
    ImageButton m_FilterImageButton;
    FilterRGBColorPainter m_FilterRGBColorPainter;
    RelativeLayout m_FilterRelativeLayout;
    RelativeLayout m_FilterSelectorLayout;
    int m_FilterViewHorizontalSpacing;
    ImageView m_FixImg;
    ImageButton m_FxButton;
    GarnishSecurity m_HBorderGarnishSecurity;
    private ImageFilter m_ImageFilter;
    private ImageFilterPlus m_ImageRGB;
    ImageButton m_NextImgButton;
    Paint m_NormalPaint;
    TextView m_NumIDTextView;
    private OnEditMetaListener m_OnEditMetaListener;
    ImageView m_OriImg;
    ImageButton m_PoolBackButton;
    ImageButton m_PreviousImgButton;
    ImageButton m_ReduceButton;
    ImageButton m_ResetButton;
    ImageButton m_RotateButton;
    ImageButton m_SaveButton;
    private SaveImage m_SaveImage;
    RelativeLayout m_ShowImgLayout;
    GarnishSecurity m_VBorderGarnishSecurity;
    private float m_ViewScale;
    private CreateWaitDialog m_WaitingDialog;
    private boolean m_bBackLoack;
    private boolean m_bBackPoolFor1stPhotoError;
    private boolean m_bCallBack;
    private ArrayList<Boolean> m_bIsEditedList;
    private ArrayList<Boolean> m_bIsVerticalList;
    private boolean m_bNextLock;
    private boolean m_bNoSDcardRead;
    private boolean m_boFirstLoadPhotoVertical;
    private boolean m_boLastVertical;
    private int m_iBorderH;
    int m_iBorderItemSize;
    int m_iBorderItemSizeHeight;
    int m_iBorderItemSizeWidth;
    private ArrayList<Integer> m_iBorderPosList;
    private int m_iBorderW;
    int m_iBrushItemSize;
    private int m_iEditDrawViewHeight;
    private int m_iEditDrawViewWidth;
    private ArrayList<Integer> m_iEditSDcardIDList;
    private ArrayList<Integer> m_iEditSDcardSIDList;
    int m_iFilterCount;
    int m_iFilterItemNameSizeHeight;
    int m_iFilterItemSize;
    int m_iFilterItemSizeHeight;
    int m_iFilterItemSizeWidth;
    private ArrayList<Integer> m_iFilterPosList;
    private int m_iIdeaSize;
    private int m_iMaxH;
    private int m_iMaxW;
    private int m_iMode;
    private int m_iNum;
    private int m_iOriMaxH;
    private int m_iOriMaxW;
    private ArrayList<Integer> m_iPhotoCopiesList;
    private int m_iPhotoNum;
    private int m_iPort;
    private int m_iPrePos;
    private int m_iRoute;
    private int m_iScreenWidth;
    private ArrayList<Integer> m_iSelPosList;
    int m_iSelectBorder;
    int m_iSelectFilter;
    GlobalVariable_AlbumSelInfo m_prefAlbumInfo;
    GlobalVariable_PrintSettingInfo m_prefInfo;
    private String m_strBorderRootPath;
    ArrayList<String> m_strBorderThumbnailPathList;
    private ArrayList<String> m_strCollagePathList;
    private String m_strEditFilePath;
    private ArrayList<String> m_strEditPathList;
    private ArrayList<String> m_strPoolPathList;
    private String m_strRoot;
    private String m_strSaveFolder;
    private String m_strXMLPath;
    private ArrayList<String> m_strXMLPathList;

    /* renamed from: com.hiti.prinbiz.EditGeneralActivity.3 */
    class C02763 implements OnClickListener {
        C02763() {
        }

        public void onClick(View v) {
            EditGeneralActivity.this.ShowWaitDialog(true);
            EditGeneralActivity.this.SaveInfoBe4Next(EditGeneralActivity.this.m_iPhotoNum);
            EditGeneralActivity.this.m_iPrePos = EditGeneralActivity.this.m_iPhotoNum;
            EditGeneralActivity.this.CheckImage(EditGeneralActivity.access$406(EditGeneralActivity.this));
        }
    }

    /* renamed from: com.hiti.prinbiz.EditGeneralActivity.4 */
    class C02774 implements OnClickListener {
        C02774() {
        }

        public void onClick(View v) {
            if (!EditGeneralActivity.this.m_bNextLock) {
                EditGeneralActivity.this.ShowWaitDialog(true);
                EditGeneralActivity.this.SaveInfoBe4Next(EditGeneralActivity.this.m_iPhotoNum);
                EditGeneralActivity.this.m_iPrePos = EditGeneralActivity.this.m_iPhotoNum;
                EditGeneralActivity.this.CheckImage(EditGeneralActivity.access$404(EditGeneralActivity.this));
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.EditGeneralActivity.5 */
    class C02785 implements OnClickListener {
        C02785() {
        }

        public void onClick(View v) {
            EditGeneralActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.EditGeneralActivity.6 */
    class C02796 implements OnClickListener {
        C02796() {
        }

        public void onClick(View v) {
            FlurryUtility.logEvent(FlurryLogString.UI_PAGE_edit_photo_TARGET_click_filter);
            EditGeneralActivity.this.ChangeMode(EditGeneralActivity.FILTER_STATE);
            EditGeneralActivity.this.CreateFilter();
        }
    }

    /* renamed from: com.hiti.prinbiz.EditGeneralActivity.7 */
    class C02807 implements OnClickListener {
        C02807() {
        }

        public void onClick(View v) {
            FlurryUtility.logEvent(FlurryLogString.UI_PAGE_edit_photo_TARGET_click_border);
            EditGeneralActivity.this.ChangeMode(EditGeneralActivity.BORDER_STATE);
            EditGeneralActivity.this.CreateBorder();
        }
    }

    /* renamed from: com.hiti.prinbiz.EditGeneralActivity.8 */
    class C02818 implements OnClickListener {
        C02818() {
        }

        public void onClick(View v) {
            if (EditGeneralActivity.this.m_iPhotoNum < EditGeneralActivity.this.m_iSelPosList.size()) {
                int pos = ((Integer) EditGeneralActivity.this.m_iSelPosList.get(EditGeneralActivity.this.m_iPhotoNum)).intValue();
                int cp = ((Integer) EditGeneralActivity.this.m_iPhotoCopiesList.get(pos)).intValue();
                if (cp < EditGeneralActivity.COPIES_MAX) {
                    cp += EditGeneralActivity.COLOR_HSLC;
                    EditGeneralActivity.this.m_iPhotoCopiesList.set(pos, Integer.valueOf(cp));
                    EditGeneralActivity.this.m_CopiesTextView.setText(String.valueOf(cp));
                }
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.EditGeneralActivity.9 */
    class C02829 implements OnClickListener {
        C02829() {
        }

        public void onClick(View v) {
            if (EditGeneralActivity.this.m_iPhotoNum < EditGeneralActivity.this.m_iSelPosList.size()) {
                int pos = ((Integer) EditGeneralActivity.this.m_iSelPosList.get(EditGeneralActivity.this.m_iPhotoNum)).intValue();
                int cp = ((Integer) EditGeneralActivity.this.m_iPhotoCopiesList.get(pos)).intValue();
                if (cp > EditGeneralActivity.COPIES_MIN) {
                    cp--;
                    EditGeneralActivity.this.m_iPhotoCopiesList.set(pos, Integer.valueOf(cp));
                    EditGeneralActivity.this.m_CopiesTextView.setText(String.valueOf(cp));
                }
            }
        }
    }

    class SaveImage extends AsyncTask<Void, String, String> {
        private String m_strOriPath;
        private String m_strSaveRoot;

        public SaveImage(String strSaveRoot, String strOriPath) {
            this.m_strOriPath = null;
            this.m_strSaveRoot = null;
            this.m_strSaveRoot = strSaveRoot;
            this.m_strOriPath = strOriPath;
        }

        protected String doInBackground(Void... parms) {
            String strSaveName = FileUtility.GetNewName(FileUtility.ChangeFileExt(FileUtility.GetFileName(this.m_strOriPath), PringoConvenientConst.PRINBIZ_BORDER_EXT), PringoConvenientConst.NEW_FILE_NAME_EDIT);
            String strSavePath = this.m_strSaveRoot + "/" + strSaveName.substring(strSaveName.indexOf("HITI") - 4);
            BitmapMonitor.TrySystemGC();
            BitmapMonitorResult bmr = EditGeneralActivity.this.m_EditDrawView.GetEditPhoto(EditGeneralActivity.this.m_iMaxW, EditGeneralActivity.this.m_iMaxH, false);
            if (!bmr.IsSuccess()) {
                return bmr.GetError(EditGeneralActivity.this.getBaseContext());
            }
            Bitmap bmp = bmr.GetBitmap();
            if (!EditGeneralActivity.this.SaveBitmap2File(strSavePath, bmp, CompressFormat.PNG)) {
                return EditGeneralActivity.this.getString(C0349R.string.ERROR);
            }
            bmp.recycle();
            BitmapMonitor.TrySystemGC();
            if (EditGeneralActivity.this.m_EditDrawView.HaveGSGarnish()) {
                bmr = EditGeneralActivity.this.m_EditDrawView.GetGSGarnish(EditGeneralActivity.this.m_iMaxW, EditGeneralActivity.this.m_iMaxH, false);
                if (!bmr.IsSuccess()) {
                    return bmr.GetError(EditGeneralActivity.this.getBaseContext());
                }
                Bitmap maskBmp = bmr.GetBitmap();
                HitiChunkUtility.AddHiTiChunk(strSavePath, maskBmp, ChunkType.PNG);
                maskBmp.recycle();
                BitmapMonitor.TrySystemGC();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                EditGeneralActivity.this.ShowErrorMSG(result);
                return;
            }
            EditGeneralActivity.this.m_EditDrawView.SetEdit(false);
            EditGeneralActivity.this.ShowWaitDialog(false);
            if (EditGeneralActivity.this.m_EditFileUri != null) {
                EditGeneralActivity.this.ShowSharePhotoAlertDialog();
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.EditGeneralActivity.1 */
    class C07571 implements INfcPreview {
        C07571() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            EditGeneralActivity.this.mNFCInfo = nfcInfo;
        }
    }

    /* renamed from: com.hiti.prinbiz.EditGeneralActivity.2 */
    class C07582 implements MSGListener {
        C07582() {
        }

        public void OKClick() {
            if (EditGeneralActivity.this.m_bNoSDcardRead || EditGeneralActivity.this.m_bBackPoolFor1stPhotoError) {
                EditGeneralActivity.this.Exit();
            }
        }

        public void Close() {
        }

        public void CancelClick() {
        }
    }

    public class BorderAdapter extends CacheAdapter {
        public BorderAdapter(Context context, AdapterView<?> adapterView, int iCacheSize, int iPenddingSize) {
            super(context, adapterView, iCacheSize, iPenddingSize);
        }

        public View InitItem(int position, View itemView, ViewGroup parent) {
            BorderViewHolder holder = new BorderViewHolder();
            if (itemView == null) {
                itemView = this.m_Inflater.inflate(C0349R.layout.item_border, null);
                holder.m_DefaultImageView = (ImageView) itemView.findViewById(C0349R.id.DefaultBorderImageView);
                holder.m_HolderImageView = (ImageView) itemView.findViewById(C0349R.id.BorderImageView);
                holder.m_CheckView = (ImageView) itemView.findViewById(C0349R.id.BorderCheckImageView);
                holder.m_ProgressBar = (ProgressBar) itemView.findViewById(C0349R.id.BorderProgressBar);
                itemView.setTag(holder);
            } else {
                holder = (BorderViewHolder) itemView.getTag();
            }
            holder.m_DefaultImageView.getLayoutParams().height = EditGeneralActivity.this.m_iBorderItemSizeHeight;
            holder.m_DefaultImageView.getLayoutParams().width = EditGeneralActivity.this.m_iBorderItemSizeWidth;
            holder.m_HolderImageView.getLayoutParams().height = EditGeneralActivity.this.m_iBorderItemSizeHeight;
            holder.m_HolderImageView.getLayoutParams().width = EditGeneralActivity.this.m_iBorderItemSizeWidth;
            holder.m_CheckView.getLayoutParams().height = EditGeneralActivity.this.m_iBorderItemSizeHeight;
            holder.m_CheckView.getLayoutParams().width = EditGeneralActivity.this.m_iBorderItemSizeWidth;
            holder.m_ProgressBar.setVisibility(0);
            holder.m_CheckView.setImageResource(C0349R.drawable.v_border_select);
            if (!EditGeneralActivity.this.m_EditDrawView.IsVertical()) {
                holder.m_CheckView.setImageResource(C0349R.drawable.h_border_select);
            }
            return itemView;
        }

        public void GetCachePhoto(BaseViewHolder holder, Bitmap bmp) {
            BorderViewHolder bVH = (BorderViewHolder) holder;
            if (bmp != null) {
                bVH.m_ProgressBar.setVisibility(EditGeneralActivity.PENDDING_SIZE);
            }
            bVH.m_HolderImageView.setImageBitmap(bmp);
        }

        public BitmapMonitorResult LoadThumbnail(int iID) {
            BitmapMonitorResult bmr;
            if (iID == 0) {
                bmr = BitmapMonitor.CreateBitmap(EditGeneralActivity.this.m_iMaxW / EditGeneralActivity.this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE, EditGeneralActivity.this.m_iMaxH / EditGeneralActivity.this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE, Config.ARGB_8888);
                if (bmr.IsSuccess()) {
                    bmr.GetBitmap().eraseColor(0);
                }
                return bmr;
            }
            bmr = new BitmapMonitorResult();
            try {
                if (FileUtility.IsFromSDCard(EditGeneralActivity.this.getBaseContext(), (String) EditGeneralActivity.this.m_strBorderThumbnailPathList.get(iID))) {
                    bmr = BitmapMonitor.CreateBitmap((String) EditGeneralActivity.this.m_strBorderThumbnailPathList.get(iID), false);
                } else {
                    bmr = BitmapMonitor.CreateBitmap(EditGeneralActivity.this.m_AssetManager.open((String) EditGeneralActivity.this.m_strBorderThumbnailPathList.get(iID)), false);
                }
            } catch (IOException e) {
                bmr.SetResult(97);
                e.printStackTrace();
            }
            return bmr;
        }

        public void OnClickItem(int iID) {
            if (EditGeneralActivity.this.m_iSelectBorder == iID || iID == 0) {
                EditGeneralActivity.this.m_iSelectBorder = 0;
                EditGeneralActivity.this.m_EditDrawView.RemoveBorder();
                EditGeneralActivity.this.m_EditDrawView.invalidate();
            } else {
                long lSGarnishID = -1;
                long lCGarnishID = -1;
                try {
                    ArrayList<String> borderList = GarnishItemUtility.GetAllGarnishPictureName((String) EditGeneralActivity.this.m_strBorderThumbnailPathList.get(iID), EditGeneralActivity.COLOR_HSLC);
                    String strBorderFolder = GarnishItemUtility.GetGarnishFolderName((String) EditGeneralActivity.this.m_strBorderThumbnailPathList.get(iID), EditGeneralActivity.COLOR_HSLC);
                    String GetFolderFullPath = FileUtility.GetFolderFullPath((String) EditGeneralActivity.this.m_strBorderThumbnailPathList.get(iID));
                    String strBorderFolderPath = strBorderFolderPath.replace("/thumb", XmlPullParser.NO_NAMESPACE);
                    EditGeneralActivity.this.m_EditDrawView.ResetZoom();
                    EditGeneralActivity.this.m_EditDrawView.RemoveBorder();
                    EditGeneralActivity.this.m_iSelectBorder = iID;
                    for (int i = 0; i < borderList.size(); i += EditGeneralActivity.COLOR_HSLC) {
                        BitmapMonitorResult bmr;
                        int iFromWhere = EditGeneralActivity.COLOR_HSLC;
                        String strBorderPath = strBorderFolderPath + "/" + strBorderFolder + "/" + ((String) borderList.get(i));
                        if (FileUtility.IsFromSDCard(EditGeneralActivity.this.getBaseContext(), strBorderPath)) {
                            iFromWhere = EditGeneralActivity.COLOR_RGB;
                        }
                        if (strBorderPath.contains("pbc_")) {
                            if (iFromWhere == EditGeneralActivity.COLOR_HSLC) {
                                bmr = BitmapMonitor.CreateBitmap(EditGeneralActivity.this.m_AssetManager.open(strBorderPath), false);
                            } else {
                                bmr = BitmapMonitor.CreateBitmap(strBorderPath, false);
                            }
                            if (bmr.IsSuccess()) {
                                Bitmap CThumbnail = bmr.GetBitmap();
                                lCGarnishID = EditGeneralActivity.this.m_EditDrawView.AddGarnish(CThumbnail, new PointF(EditGeneralActivity.this.m_EditDrawView.GetViewWindowCenterX(), EditGeneralActivity.this.m_EditDrawView.GetViewWindowCenterY()), EditGeneralActivity.COLOR_RGB, strBorderPath, iFromWhere);
                                CThumbnail.recycle();
                                if (lCGarnishID <= 0) {
                                    EditGeneralActivity.this.ShowErrorMSG(EditGeneralActivity.this.getString(C0349R.string.CREATE_BITMAP_OUT_OF_MEMORY));
                                    return;
                                }
                            }
                            EditGeneralActivity.this.ShowErrorMSG(BitmapMonitorResult.GetError(EditGeneralActivity.this.getBaseContext(), bmr.GetResult()));
                            return;
                        }
                        if (strBorderPath.contains("pbs_")) {
                            if (iFromWhere == EditGeneralActivity.COLOR_HSLC) {
                                bmr = BitmapMonitor.CreateBitmap(EditGeneralActivity.this.m_AssetManager.open(strBorderPath), false);
                            } else {
                                bmr = BitmapMonitor.CreateBitmap(strBorderPath, false);
                            }
                            if (bmr.IsSuccess()) {
                                Bitmap SThumbnail = bmr.GetBitmap();
                                lSGarnishID = EditGeneralActivity.this.m_EditDrawView.AddGarnish(SThumbnail, new PointF(EditGeneralActivity.this.m_EditDrawView.GetViewWindowCenterX(), EditGeneralActivity.this.m_EditDrawView.GetViewWindowCenterY()), 5, strBorderPath, iFromWhere);
                                SThumbnail.recycle();
                                if (lSGarnishID <= 0) {
                                    EditGeneralActivity.this.ShowErrorMSG(EditGeneralActivity.this.getString(C0349R.string.CREATE_BITMAP_OUT_OF_MEMORY));
                                    return;
                                }
                            } else {
                                EditGeneralActivity.this.ShowErrorMSG(BitmapMonitorResult.GetError(EditGeneralActivity.this.getBaseContext(), bmr.GetResult()));
                                return;
                            }
                        }
                    }
                    if (!(lCGarnishID == -1 || lSGarnishID == -1)) {
                        EditGeneralActivity.this.m_EditDrawView.GetGarnishItem(lSGarnishID).SetComposeID(lCGarnishID);
                        EditGeneralActivity.this.m_EditDrawView.GetGarnishItem(lCGarnishID).SetComposeID(lSGarnishID);
                    }
                    EditGeneralActivity.this.m_EditDrawView.invalidate();
                    EditGeneralActivity.this.m_bIsEditedList.set(EditGeneralActivity.this.AdjustPos(EditGeneralActivity.this.m_iPhotoNum), Boolean.valueOf(true));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            EditGeneralActivity.this.m_iBorderPosList.set(EditGeneralActivity.this.AdjustPos(EditGeneralActivity.this.m_iPhotoNum), Integer.valueOf(EditGeneralActivity.this.m_iSelectBorder));
        }

        public void LoadThumbnailFail(int iResult) {
            EditGeneralActivity.this.ShowErrorMSG(BitmapMonitorResult.GetError(EditGeneralActivity.this, iResult));
        }

        public void LoadThumbnailSuccess(BaseViewHolder holder, Bitmap bmp) {
            BorderViewHolder bVH = (BorderViewHolder) holder;
            bVH.m_ProgressBar.setVisibility(EditGeneralActivity.PENDDING_SIZE);
            Bitmap recycleBmp = GetCurrentBmp(holder);
            bVH.m_HolderImageView.setImageBitmap(bmp);
            if (recycleBmp != null && recycleBmp != bmp && !recycleBmp.isRecycled()) {
                recycleBmp.recycle();
            }
        }

        public void ReflashCheckState(BaseViewHolder SelectHolder) {
            BorderViewHolder bVH = (BorderViewHolder) SelectHolder;
            bVH.m_CheckView.setVisibility(EditGeneralActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
            if (EditGeneralActivity.this.m_iSelectBorder == bVH.m_iID) {
                bVH.m_CheckView.setVisibility(0);
            }
        }

        public void ReflashBackground(BaseViewHolder SelectHolder) {
            ((BorderViewHolder) SelectHolder).m_DefaultImageView.setImageBitmap(EditGeneralActivity.this.m_DefaultUIThumbnailBmp);
        }

        public int GetListSize() {
            return EditGeneralActivity.this.m_strBorderThumbnailPathList.size();
        }
    }

    class BorderLoadFinishListener extends LoadFinishListener {
        BorderLoadFinishListener() {
        }

        public void LoadFinish() {
            EditGeneralActivity.this.m_BorderHorizontalListView.getLayoutParams().height = EditGeneralActivity.this.m_iBorderItemSizeHeight;
            EditGeneralActivity.this.m_EffectLayout.getLayoutParams().height = EditGeneralActivity.this.m_iBorderItemSizeHeight + 10;
            EditGeneralActivity.this.m_BorderHorizontalListView.setAdapter(EditGeneralActivity.this.m_BorderAdapter);
            EditGeneralActivity.this.m_BorderAdapter.notifyDataSetChanged();
            int iPos = EditGeneralActivity.this.AdjustPos(EditGeneralActivity.this.m_iPhotoNum);
            EditGeneralActivity.this.m_iSelectBorder = ((Integer) EditGeneralActivity.this.m_iBorderPosList.get(iPos)).intValue();
            EditGeneralActivity.this.ChangeMode(EditGeneralActivity.BORDER_STATE);
            EditGeneralActivity.this.ShowWaitDialog(false);
        }

        public String BeforeLoadFinish() {
            return null;
        }
    }

    public class FilterAdapter extends CacheAdapter {
        private Object m_ProtectedThumbnailBmp;
        private Bitmap m_ThumbnailBmp;

        public FilterAdapter(Context context, AdapterView<?> adapterView, int iCacheSize, int iPenddingSize) {
            super(context, adapterView, iCacheSize, iPenddingSize);
            this.m_ProtectedThumbnailBmp = new Object();
            this.m_ThumbnailBmp = null;
        }

        public void ClearCache() {
            super.ClearCache();
            synchronized (this.m_ProtectedThumbnailBmp) {
                if (!(this.m_ThumbnailBmp == null || this.m_ThumbnailBmp.isRecycled())) {
                    this.m_ThumbnailBmp.recycle();
                    this.m_ThumbnailBmp = null;
                }
            }
        }

        public void SetOrgThumbnail(Bitmap srcBmp) {
            synchronized (this.m_ProtectedThumbnailBmp) {
                if (srcBmp != null) {
                    BitmapMonitorResult bmr = BitmapMonitor.Copy(srcBmp, Config.ARGB_8888, true);
                    if (bmr.IsSuccess()) {
                        if (!(this.m_ThumbnailBmp == null || this.m_ThumbnailBmp.isRecycled())) {
                            this.m_ThumbnailBmp.recycle();
                            this.m_ThumbnailBmp = null;
                        }
                        this.m_ThumbnailBmp = bmr.GetBitmap();
                    }
                }
            }
        }

        public BitmapMonitorResult GetOrgBmpThumbnail() {
            synchronized (this.m_ProtectedThumbnailBmp) {
                BitmapMonitorResult bmr = new BitmapMonitorResult();
                if (this.m_ThumbnailBmp == null) {
                    bmr.SetResult(97);
                    return bmr;
                }
                bmr = BitmapMonitor.Copy(this.m_ThumbnailBmp, Config.ARGB_8888, true);
                return bmr;
            }
        }

        public View InitItem(int position, View itemView, ViewGroup parent) {
            EditGeneralActivity.this.ShowWaitDialog(false);
            int iPos = EditGeneralActivity.this.AdjustPos(EditGeneralActivity.this.m_iPhotoNum);
            EditGeneralActivity.this.m_iSelectFilter = ((Integer) EditGeneralActivity.this.m_iFilterPosList.get(iPos)).intValue();
            EditGeneralActivity.this.m_iFilterPosList.set(iPos, Integer.valueOf(EditGeneralActivity.this.m_iSelectFilter));
            Log.e("FilterAdapter", "mode=" + EditGeneralActivity.this.m_iMode);
            EditGeneralActivity.this.InitFilterColorPosition(EditGeneralActivity.this.m_iSelectFilter);
            EditGeneralActivity.this.InitFilterRGBColorPosition(EditGeneralActivity.this.m_iSelectFilter);
            FilterViewHolder holder = new FilterViewHolder();
            if (itemView == null) {
                itemView = this.m_Inflater.inflate(C0349R.layout.item_filter, null);
                holder.m_DefaultImageView = (ImageView) itemView.findViewById(C0349R.id.DefaultFilterImageView);
                holder.m_HolderImageView = (ImageView) itemView.findViewById(C0349R.id.FilterImageView);
                holder.m_CheckView = (ImageView) itemView.findViewById(C0349R.id.FilterCheckImageView);
                holder.m_ProgressBar = (ProgressBar) itemView.findViewById(C0349R.id.FilterProgressBar);
                holder.m_TextView = (TextView) itemView.findViewById(C0349R.id.m_FilterNameTextView);
                itemView.setTag(holder);
            } else {
                holder = (FilterViewHolder) itemView.getTag();
            }
            holder.m_DefaultImageView.getLayoutParams().height = EditGeneralActivity.this.m_iFilterItemSizeHeight;
            holder.m_DefaultImageView.getLayoutParams().width = EditGeneralActivity.this.m_iFilterItemSizeWidth;
            holder.m_HolderImageView.getLayoutParams().height = EditGeneralActivity.this.m_iFilterItemSizeHeight;
            holder.m_HolderImageView.getLayoutParams().width = EditGeneralActivity.this.m_iFilterItemSizeWidth;
            holder.m_CheckView.getLayoutParams().height = EditGeneralActivity.this.m_iFilterItemSizeHeight;
            holder.m_CheckView.getLayoutParams().width = EditGeneralActivity.this.m_iFilterItemSizeWidth;
            holder.m_ProgressBar.setVisibility(0);
            holder.m_TextView.getLayoutParams().height = EditGeneralActivity.this.m_iFilterItemNameSizeHeight;
            holder.m_TextView.getLayoutParams().width = EditGeneralActivity.this.m_iFilterItemSizeWidth;
            holder.m_TextView.setTextColor(EditGeneralActivity.this.getResources().getColor(C0349R.color.FILTER_TEXT_COLOR));
            holder.m_TextView.setTextSize(0, (float) (((double) EditGeneralActivity.this.m_iFilterItemNameSizeHeight) / 1.5d));
            holder.m_TextView.setText(XmlPullParser.NO_NAMESPACE);
            if (position < EditGeneralActivity.this.m_FilterColorValueList.size()) {
                int iResID = ((FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(position)).m_iFilterName;
                if (iResID != -1) {
                    holder.m_TextView.setText(EditGeneralActivity.this.getBaseContext().getResources().getString(iResID));
                }
            }
            holder.m_CheckView.setImageResource(C0349R.drawable.v_filter_select);
            if (!EditGeneralActivity.this.m_EditDrawView.IsVertical()) {
                holder.m_CheckView.setImageResource(C0349R.drawable.h_filter_select);
            }
            return itemView;
        }

        public void GetCachePhoto(BaseViewHolder holder, Bitmap bmp) {
            FilterViewHolder fVH = (FilterViewHolder) holder;
            if (bmp != null) {
                fVH.m_ProgressBar.setVisibility(EditGeneralActivity.PENDDING_SIZE);
            }
            fVH.m_HolderImageView.setImageBitmap(bmp);
        }

        public BitmapMonitorResult LoadThumbnail(int iID) {
            BitmapMonitorResult bmr;
            BitmapMonitorResult filterBmr;
            if (iID == 0) {
                if (((FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(iID)).HaveModifyHSLCValue() || ((FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(iID)).HaveModifyRGBValue()) {
                    bmr = BitmapMonitor.CreateBitmap(EditGeneralActivity.this.m_iMaxW / EditGeneralActivity.this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE, EditGeneralActivity.this.m_iMaxH / EditGeneralActivity.this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE, Config.ARGB_8888);
                    if (!bmr.IsSuccess()) {
                        return bmr;
                    }
                    filterBmr = GetOrgBmpThumbnail();
                    if (!filterBmr.IsSuccess()) {
                        return filterBmr;
                    }
                    Bitmap bmpEdit = filterBmr.GetBitmap();
                    EditGeneralActivity.this.FilterHSLC(bmpEdit, (FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(iID));
                    EditGeneralActivity.this.FilterRGB(bmpEdit, (FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(iID));
                    Canvas canvas = new Canvas(bmr.GetBitmap());
                    canvas.concat(EditGeneralActivity.this.m_DefaultOrgThumbnailMatrix);
                    canvas.drawBitmap(bmpEdit, 0.0f, 0.0f, EditGeneralActivity.this.m_NormalPaint);
                    bmpEdit.recycle();
                } else {
                    bmr = BitmapMonitor.CreateBitmap(EditGeneralActivity.this.m_iMaxW / EditGeneralActivity.this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE, EditGeneralActivity.this.m_iMaxH / EditGeneralActivity.this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE, Config.ARGB_8888);
                    if (bmr.IsSuccess()) {
                        bmr.GetBitmap().eraseColor(0);
                    }
                }
                return bmr;
            }
            bmr = BitmapMonitor.CreateBitmap(EditGeneralActivity.this.m_iMaxW / EditGeneralActivity.this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE, EditGeneralActivity.this.m_iMaxH / EditGeneralActivity.this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE, Config.ARGB_8888);
            if (!bmr.IsSuccess()) {
                return bmr;
            }
            filterBmr = GetOrgBmpThumbnail();
            if (!filterBmr.IsSuccess()) {
                return filterBmr;
            }
            bmpEdit = filterBmr.GetBitmap();
            EditGeneralActivity.this.m_ImageFilter.ProcessImage(bmpEdit, ((FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(iID)).m_FilterType);
            EditGeneralActivity.this.FilterHSLC(bmpEdit, (FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(iID));
            EditGeneralActivity.this.FilterRGB(bmpEdit, (FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(iID));
            canvas = new Canvas(bmr.GetBitmap());
            canvas.concat(EditGeneralActivity.this.m_DefaultOrgThumbnailMatrix);
            canvas.drawBitmap(bmpEdit, 0.0f, 0.0f, EditGeneralActivity.this.m_NormalPaint);
            bmpEdit.recycle();
            return bmr;
        }

        public void OnClickItem(int iID) {
            GarnishItem garnishItem = EditGeneralActivity.this.m_EditDrawView.GetEditPhotoGarnishItem();
            garnishItem.SetBackUpViewScaleBitmap();
            if (EditGeneralActivity.this.m_iSelectFilter == iID || iID == 0) {
                EditGeneralActivity.this.m_iSelectFilter = 0;
                garnishItem.ResetViewScaleBitmap();
            } else {
                EditGeneralActivity.this.m_iSelectFilter = iID;
                EditGeneralActivity.this.SetImageFilter(garnishItem, ((FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(iID)).m_FilterType);
            }
            garnishItem.RemoveFilterViewScaleBitmap();
            garnishItem.SetBackUpFilterViewScaleBitmap();
            EditGeneralActivity.this.AdjustImageFilter(garnishItem, (FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(EditGeneralActivity.this.m_iSelectFilter));
            EditGeneralActivity.this.DecideFilterColorButtonState(EditGeneralActivity.this.m_FilterColorSelector.GetFilterType());
            EditGeneralActivity.this.InitFilterColorPosition(EditGeneralActivity.this.m_iSelectFilter);
            EditGeneralActivity.this.InitFilterRGBColorPosition(EditGeneralActivity.this.m_iSelectFilter);
            EditGeneralActivity.this.m_EditDrawView.invalidate();
            EditGeneralActivity.this.m_bIsEditedList.set(EditGeneralActivity.this.AdjustPos(EditGeneralActivity.this.m_iPhotoNum), Boolean.valueOf(true));
            EditGeneralActivity.this.m_iFilterPosList.set(EditGeneralActivity.this.AdjustPos(EditGeneralActivity.this.m_iPhotoNum), Integer.valueOf(EditGeneralActivity.this.m_iSelectFilter));
        }

        public void LoadThumbnailFail(int iResult) {
            EditGeneralActivity.this.ShowWaitDialog(false);
            EditGeneralActivity.this.ShowErrorMSG(BitmapMonitorResult.GetError(EditGeneralActivity.this.getBaseContext(), iResult));
        }

        public void LoadThumbnailSuccess(BaseViewHolder holder, Bitmap bmp) {
            FilterViewHolder fVH = (FilterViewHolder) holder;
            fVH.m_ProgressBar.setVisibility(EditGeneralActivity.PENDDING_SIZE);
            fVH.m_HolderImageView.setImageBitmap(bmp);
        }

        public void ReflashCheckState(BaseViewHolder SelectHolder) {
            FilterViewHolder fVH = (FilterViewHolder) SelectHolder;
            fVH.m_CheckView.setVisibility(EditGeneralActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
            if (EditGeneralActivity.this.m_iSelectFilter == fVH.m_iID) {
                fVH.m_CheckView.setVisibility(0);
            }
        }

        public void ReflashBackground(BaseViewHolder SelectHolder) {
            ((FilterViewHolder) SelectHolder).m_DefaultImageView.setImageBitmap(EditGeneralActivity.this.m_DefaultOrgThumbnailBmp);
        }

        public int GetListSize() {
            return EditGeneralActivity.this.m_iFilterCount;
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
            Log.e("OnBorderMode", "what~~~");
            EditGeneralActivity.this.m_bIsEditedList.set(EditGeneralActivity.this.AdjustPos(EditGeneralActivity.this.m_iPhotoNum), Boolean.valueOf(true));
            EditGeneralActivity.this.CreatePictureEditThumbnail();
        }

        public void OnBrushMode() {
        }

        public void OnRollerMode() {
        }

        public void OnFilterMode() {
            EditGeneralActivity.this.m_bIsEditedList.set(EditGeneralActivity.this.AdjustPos(EditGeneralActivity.this.m_iPhotoNum), Boolean.valueOf(true));
            EditGeneralActivity.this.CreatePictureEditThumbnail();
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
            int iPos = EditGeneralActivity.this.AdjustPos(pos);
            if (strImagePath != null && iPos < EditGeneralActivity.this.m_strEditPathList.size()) {
                EditGeneralActivity.this.m_strEditPathList.set(iPos, strImagePath);
            }
            EditGeneralActivity.this.NextEditPhoto(pos);
        }

        public void FetchImgError(String strErr) {
            EditGeneralActivity.this.ShowWaitDialog(false);
            EditGeneralActivity.this.ShowErrorMSG(strErr);
        }

        public void FetchImgTimeOut(String strErr) {
            EditGeneralActivity.this.ShowWaitDialog(false);
            EditGeneralActivity.this.ShowErrorMSG(strErr);
        }

        public void InitDrawView(int iPos) {
            EditGeneralActivity.this.PrepareDrawView(iPos);
        }

        public void InitDrawViewEnd(int iPos) {
            EditGeneralActivity.this.ModifyDrawView(iPos, EditGeneralActivity.this.GetEditFilePath());
        }

        public void FetchImgRatio(int iPos, String strRatio) {
            EditGeneralActivity.this.m_WaitingDialog.ShowDialog(strRatio, EditGeneralActivity.this.getString(C0349R.string.LOADING_IMAGE));
        }

        public void SaveEditPhoto() {
            EditGeneralActivity.this.SaveEditData();
            if (EditGeneralActivity.this.m_iRoute == EditGeneralActivity.COLOR_RGB) {
                EditGeneralActivity.this.m_EditMetaUtility.SetFetchStop();
            }
            EditGeneralActivity.this.Exit();
        }

        public void SaveEditPhotoDone() {
        }

        public void DelFLValueData() {
        }
    }

    class EditBorderLoader extends BorderLoader {
        public EditBorderLoader(Context context, GarnishSecurity security, LoadFinishListener loadFinishListener) {
            super(context, security, loadFinishListener);
        }

        public void BeforeLoadFinish() {
            Log.e("BeforeLoadFinish", String.valueOf(EditGeneralActivity.this.m_strBorderThumbnailPathList));
            EditGeneralActivity.this.Add1stPosEmptyPath();
        }
    }

    class RGBFilterColorSelectorListener extends OnRGBFilterColorSelectChangedListener {
        RGBFilterColorSelectorListener() {
        }

        public void onOKButtonClicked(View v) {
            EditGeneralActivity.this.ShowFilterSelector(false);
            EditGeneralActivity.this.SetColorImgBtn(true, true);
        }

        public void onResetButtonClicked(View v) {
            int iType = EditGeneralActivity.this.m_FilterColorSelector.GetFilterType();
            EditGeneralActivity.this.m_FilterColorSelector.SetOKButtonStatus(false);
            GarnishItem garnishItem = EditGeneralActivity.this.m_EditDrawView.GetEditPhotoGarnishItem();
            garnishItem.ResetFilterViewScaleBitmap();
            if (iType == EditGeneralActivity.COLOR_HSLC) {
                EditGeneralActivity.this.m_FilterColorSelector.SetHSVCAndPostion(0.0f, 0.0f, 0.0f, 0.0f);
                EditGeneralActivity.this.SetFilterColorValue(EditGeneralActivity.this.m_iSelectFilter, 0.0f, 0.0f, 0.0f, 0.0f);
            } else {
                EditGeneralActivity.this.m_FilterColorSelector.SetRGBtoPostion(0.0f, 0.0f, 0.0f);
                EditGeneralActivity.this.SetFilterRGBvalue(EditGeneralActivity.this.m_iSelectFilter, 0.0f, 0.0f, 0.0f);
            }
            EditGeneralActivity.this.AdjustImageFilter(garnishItem, (FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(EditGeneralActivity.this.m_iSelectFilter));
            if (EditGeneralActivity.this.m_FilterAdapter != null) {
                EditGeneralActivity.this.m_FilterAdapter.ClearCache(EditGeneralActivity.this.m_iSelectFilter);
                EditGeneralActivity.this.m_FilterAdapter.notifyDataSetChanged();
            }
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
            EditGeneralActivity.this.m_FilterColorSelector.SetOKButtonStatus(true);
            GarnishItem garnishItem = EditGeneralActivity.this.m_EditDrawView.GetEditPhotoGarnishItem();
            EditGeneralActivity.this.SetFilterColorValue(EditGeneralActivity.this.m_iSelectFilter, fHue, fSaturation, fLight, fContrast);
            EditGeneralActivity.this.AdjustImageFilter(garnishItem, (FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(EditGeneralActivity.this.m_iSelectFilter));
            if (EditGeneralActivity.this.m_FilterAdapter != null) {
                EditGeneralActivity.this.m_FilterAdapter.ClearCache(EditGeneralActivity.this.m_iSelectFilter);
                EditGeneralActivity.this.m_FilterAdapter.notifyDataSetChanged();
            }
        }

        public void onHueButtonClicked(View v) {
            EditGeneralActivity.this.DecideFilterColorButtonState(EditGeneralActivity.COLOR_RGB);
        }

        public void onBrightnessButtonClicked(View v) {
            EditGeneralActivity.this.DecideFilterColorButtonState(EditGeneralActivity.COLOR_HSLC);
        }

        public void onRGBColorChanged(float fRed, float fGreen, float fBlue) {
            EditGeneralActivity.this.m_FilterColorSelector.SetOKButtonStatus(true);
            GarnishItem garnishItem = EditGeneralActivity.this.m_EditDrawView.GetEditPhotoGarnishItem();
            EditGeneralActivity.this.SetFilterRGBvalue(EditGeneralActivity.this.m_iSelectFilter, fRed, fGreen, fBlue);
            EditGeneralActivity.this.AdjustImageFilter(garnishItem, (FilterColorValue) EditGeneralActivity.this.m_FilterColorValueList.get(EditGeneralActivity.this.m_iSelectFilter));
            if (EditGeneralActivity.this.m_FilterAdapter != null) {
                EditGeneralActivity.this.m_FilterAdapter.ClearCache(EditGeneralActivity.this.m_iSelectFilter);
                EditGeneralActivity.this.m_FilterAdapter.notifyDataSetChanged();
            }
        }
    }

    public EditGeneralActivity() {
        this.m_ShowImgLayout = null;
        this.m_FilterColorLayout = null;
        this.m_BorderLayout = null;
        this.m_ContentLayout = null;
        this.m_FilterSelectorLayout = null;
        this.m_PreviousImgButton = null;
        this.m_NextImgButton = null;
        this.m_FilterColorImgButton = null;
        this.m_PoolBackButton = null;
        this.m_AddButton = null;
        this.m_ReduceButton = null;
        this.m_FxButton = null;
        this.m_BorderButton = null;
        this.m_RotateButton = null;
        this.m_ResetButton = null;
        this.m_SaveButton = null;
        this.m_NumIDTextView = null;
        this.m_CopiesTextView = null;
        this.m_OriImg = null;
        this.m_FixImg = null;
        this.m_prefAlbumInfo = null;
        this.m_iRoute = 0;
        this.m_strXMLPathList = null;
        this.m_strEditPathList = null;
        this.m_strCollagePathList = null;
        this.m_strPoolPathList = null;
        this.m_iEditSDcardIDList = null;
        this.m_iEditSDcardSIDList = null;
        this.m_iPhotoCopiesList = null;
        this.m_iSelPosList = null;
        this.m_iBorderPosList = null;
        this.m_iFilterPosList = null;
        this.m_bIsVerticalList = null;
        this.m_bIsEditedList = null;
        this.m_EditMetaUtility = null;
        this.m_OnEditMetaListener = null;
        this.m_EditMeta = null;
        this.m_FilterRGBColorPainter = null;
        this.m_ColorPainter_Scorller = null;
        this.m_WaitingDialog = null;
        this.m_ErrorDialog = null;
        this.m_iPhotoNum = 0;
        this.m_iPrePos = 0;
        this.m_EditBmp = null;
        this.m_iIdeaSize = 0;
        this.m_iScreenWidth = 0;
        this.m_iMaxW = 0;
        this.m_iMaxH = 0;
        this.m_iOriMaxW = 0;
        this.m_iOriMaxH = 0;
        this.m_iBorderW = 0;
        this.m_iBorderH = 0;
        this.m_prefInfo = null;
        this.m_iEditDrawViewWidth = 0;
        this.m_iEditDrawViewHeight = 0;
        this.m_ViewScale = 0.0f;
        this.m_boFirstLoadPhotoVertical = true;
        this.m_EditDrawView = null;
        this.m_EditDrawViewBackgroundColor = 0;
        this.m_strEditFilePath = null;
        this.m_strSaveFolder = null;
        this.m_strXMLPath = null;
        this.m_ImageFilter = null;
        this.m_ImageRGB = null;
        this.m_boLastVertical = false;
        this.m_bBackLoack = false;
        this.m_bCallBack = false;
        this.m_bNextLock = false;
        this.m_AssetManager = null;
        this.m_BorderImageButton = null;
        this.m_BorderRelativeLayout = null;
        this.m_EffectLayout = null;
        this.m_BorderHorizontalListView = null;
        this.m_BorderViewHorizontalSpacing = 0;
        this.m_BorderAdapter = null;
        this.m_BorderLoader = null;
        this.m_BorderLoadFinishListener = new BorderLoadFinishListener();
        this.m_strBorderThumbnailPathList = null;
        this.m_NormalPaint = new Paint();
        this.m_iBorderItemSizeWidth = 0;
        this.m_iBorderItemSizeHeight = 0;
        this.m_iBorderItemSize = 0;
        this.m_iBrushItemSize = 0;
        this.m_iSelectBorder = 0;
        this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE = HORIZONTAL_CACHE_BITMAP_SIZE;
        this.m_DefaultUIThumbnailBmp = null;
        this.m_DefaultOrgThumbnailBmp = null;
        this.m_DefaultOrgThumbnailNoMatrixBmp = null;
        this.m_DefaultOrgThumbnailMatrix = null;
        this.m_HBorderGarnishSecurity = new GarnishSecurity(this);
        this.m_VBorderGarnishSecurity = new GarnishSecurity(this);
        this.m_strBorderRootPath = null;
        this.m_strRoot = null;
        this.m_SaveImage = null;
        this.m_EditFileUri = null;
        this.m_iMode = NEXT_PAGE;
        this.m_iNum = 0;
        this.m_bNoSDcardRead = false;
        this.m_bBackPoolFor1stPhotoError = false;
        this.m_FilterImageButton = null;
        this.m_FilterColorSelector = null;
        this.m_FilterRelativeLayout = null;
        this.m_FilterHorizontalListView = null;
        this.m_FCVLList = null;
        this.m_FilterColorValueList = null;
        this.m_FilterAdapter = null;
        this.m_FilterViewHorizontalSpacing = 5;
        this.m_iFilterCount = 0;
        this.m_iSelectFilter = 0;
        this.m_iFilterItemSize = 0;
        this.m_iFilterItemSizeWidth = 0;
        this.m_iFilterItemSizeHeight = 0;
        this.m_iFilterItemNameSizeHeight = 0;
        this.IP = XmlPullParser.NO_NAMESPACE;
        this.m_iPort = 0;
        this.mNFCInfo = null;
        this.LOG = null;
        this.FxListener = new C02796();
        this.BorderListener = new C02807();
        this.AddCopiesListener = new C02818();
        this.ReduceCopiesListener = new C02829();
        this.ShowFilterListener = new OnClickListener() {
            public void onClick(View v) {
                EditGeneralActivity.this.ShowFilterSelector(true);
            }
        };
        this.SavePhotoListener = new OnClickListener() {
            public void onClick(View v) {
                EditGeneralActivity.this.onSaveImageButtonClicked();
            }
        };
        this.RotateListener = new OnClickListener() {
            public void onClick(View v) {
                EditGeneralActivity.this.m_bIsEditedList.set(EditGeneralActivity.this.AdjustPos(EditGeneralActivity.this.m_iPhotoNum), Boolean.valueOf(true));
                EditGeneralActivity.this.onRotateImageButtonClicked(v);
            }
        };
        this.ResetListener = new OnClickListener() {
            public void onClick(View v) {
                EditGeneralActivity.this.OnResetListener(v);
            }
        };
    }

    static /* synthetic */ int access$404(EditGeneralActivity x0) {
        int i = x0.m_iPhotoNum + COLOR_HSLC;
        x0.m_iPhotoNum = i;
        return i;
    }

    static /* synthetic */ int access$406(EditGeneralActivity x0) {
        int i = x0.m_iPhotoNum - 1;
        x0.m_iPhotoNum = i;
        return i;
    }

    static {
        COPIES_MAX = 90;
        COPIES_MIN = COLOR_HSLC;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_edit_general);
        this.LOG = new LogManager(0);
        UploadService.StopUploadService(this, UploadService.class);
        if (savedInstanceState != null) {
            this.m_bCallBack = true;
            ReBuildData(savedInstanceState);
        } else {
            this.m_iPhotoNum = 0;
        }
        GetBundle();
        FlurryUtility.init(this, FlurryUtility.FLURRY_API_KEY_PRINBIZ);
        if (GetPref()) {
            SetView();
            ShowWaitDialog(true);
            this.m_EditMetaUtility.InitDrawView(AdjustPos(this.m_iPhotoNum));
            return;
        }
        this.m_bNoSDcardRead = true;
        ShowErrorMSG(BitmapMonitorResult.GetError(this, 95));
    }

    protected void onStart() {
        FlurryUtility.onStartSession(this);
        super.onStart();
    }

    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07571());
    }

    protected void onPause() {
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    protected void onStop() {
        FlurryUtility.onEndSession(this);
        super.onStop();
    }

    private void GetBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.IP = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP);
            this.m_iPort = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT);
            return;
        }
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
    }

    private void ShowWaitDialog(boolean bShow) {
        if (!bShow) {
            this.m_WaitingDialog.DismissDialog();
        } else if (!this.m_WaitingDialog.IsShowing()) {
            this.m_WaitingDialog.ShowDialog(getString(C0349R.string.PLEASE_WAIT));
        }
    }

    private void ReBuildData(Bundle bundle) {
        this.m_iPhotoNum = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_EDIT_IMG_POS);
        this.m_bCallBack = false;
        int iLen = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST_LEN);
        int jLen = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LEN);
        this.m_FCVLList = new ArrayList();
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
            this.m_FCVLList.add(m_FCVList);
        }
    }

    private boolean GetPref() {
        this.m_WaitingDialog = new CreateWaitDialog(this);
        this.m_ErrorDialog = new ShowMSGDialog(this, false);
        this.m_EditMetaUtility = new EditMetaUtility(this);
        this.m_OnEditMetaListener = new OnEditMetaListener();
        this.m_EditMetaUtility.SetEditMetaListener(this.m_OnEditMetaListener);
        this.m_EditMetaUtility.SetIPandPort(this.IP, this.m_iPort);
        this.m_iRoute = EditMetaUtility.GetSrcRoute(this);
        this.m_ErrorDialog.SetMSGListener(new C07582());
        if (!MakeFolder()) {
            return false;
        }
        GetEditMetaOrInitial();
        DecidePrintOut();
        return true;
    }

    private void DecidePrintOut() {
        this.m_strBorderRootPath = PringoConvenientConst.ROOT_GARNISH_FOLDER;
        String strModel = this.m_EditMetaUtility.GetModel();
        switch (this.m_EditMetaUtility.GetServerPaperType()) {
            case COLOR_RGB /*2*/:
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                if (strModel.equals(WirelessType.TYPE_P310W) || strModel.equals(WirelessType.TYPE_P461)) {
                    this.m_iOriMaxW = Integer.parseInt(getString(C0349R.string.WIDTH_310w_4x6));
                    this.m_iOriMaxH = Integer.parseInt(getString(C0349R.string.HEIGHT_310w_4x6));
                } else {
                    this.m_iOriMaxW = Integer.parseInt(getString(C0349R.string.WIDTH_4x6));
                    this.m_iOriMaxH = Integer.parseInt(getString(C0349R.string.HEIGHT_4x6));
                }
                this.m_iBorderW = Integer.parseInt(getString(C0349R.string.WIDTH_4x6));
                this.m_iBorderH = Integer.parseInt(getString(C0349R.string.HEIGHT_4x6));
                this.m_strBorderRootPath += PringoConvenientConst.BORDER_4x6_ROOT;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                this.m_iOriMaxW = Integer.parseInt(getString(C0349R.string.WIDTH_5x7));
                this.m_iOriMaxH = Integer.parseInt(getString(C0349R.string.HEIGHT_5x7));
                this.m_iBorderW = this.m_iOriMaxW;
                this.m_iBorderH = this.m_iOriMaxH;
                this.m_strBorderRootPath += PringoConvenientConst.BORDER_5x7_ROOT;
            case HORIZONTAL_CACHE_BITMAP_SIZE /*4*/:
                this.m_iOriMaxW = Integer.parseInt(getString(C0349R.string.WIDTH_6x8));
                this.m_iOriMaxH = Integer.parseInt(getString(C0349R.string.HEIGHT_6x8));
                this.m_iBorderW = this.m_iOriMaxW;
                this.m_iBorderH = this.m_iOriMaxH;
                this.m_strBorderRootPath += PringoConvenientConst.BORDER_6x8_ROOT;
            default:
                this.m_iOriMaxW = Integer.parseInt(getString(C0349R.string.WIDTH_4x6));
                this.m_iOriMaxH = Integer.parseInt(getString(C0349R.string.HEIGHT_4x6));
                this.m_iBorderW = this.m_iOriMaxW;
                this.m_iBorderH = this.m_iOriMaxH;
                this.m_strBorderRootPath += PringoConvenientConst.BORDER_4x6_ROOT;
        }
    }

    private boolean MakeFolder() {
        if (getExternalFilesDir(null) == null) {
            return false;
        }
        this.m_strRoot = getExternalFilesDir(null).getAbsolutePath() + "/" + PringoConvenientConst.PRINGO_TEMP_FOLDER;
        FileUtility.CreateFolder(this.m_strRoot);
        this.m_strSaveFolder = Environment.getExternalStorageDirectory().getPath() + File.separator + PringoConvenientConst.PRINBIZ_FOLDER;
        return true;
    }

    private void SetView() {
        this.m_PoolBackButton = (ImageButton) findViewById(C0349R.id.m_PoolBackButton);
        this.m_AddButton = (ImageButton) findViewById(C0349R.id.m_AddButton);
        this.m_ReduceButton = (ImageButton) findViewById(C0349R.id.m_ReduceButton);
        this.m_FxButton = (ImageButton) findViewById(C0349R.id.m_FxButton);
        this.m_BorderButton = (ImageButton) findViewById(C0349R.id.m_BorderButton);
        this.m_RotateButton = (ImageButton) findViewById(C0349R.id.m_RotateButton);
        this.m_ResetButton = (ImageButton) findViewById(C0349R.id.m_ResetButton);
        this.m_SaveButton = (ImageButton) findViewById(C0349R.id.m_SaveButton);
        this.m_NumIDTextView = (TextView) findViewById(C0349R.id.m_NumIdTextView);
        this.m_CopiesTextView = (TextView) findViewById(C0349R.id.m_CopiesTextView);
        this.m_BorderRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_BorderRelativeLayout);
        this.m_BorderHorizontalListView = (HorizontalListView) findViewById(C0349R.id.m_BorderHorizontalListView);
        this.m_ShowImgLayout = (RelativeLayout) findViewById(C0349R.id.m_ImageRelativeLayout);
        this.m_FilterColorLayout = (RelativeLayout) findViewById(C0349R.id.m_FilterColorLayout);
        this.m_FilterSelectorLayout = (RelativeLayout) findViewById(C0349R.id.m_FilterSelectorLayout);
        this.m_FilterRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_FilterRelativeLayout);
        this.m_FilterHorizontalListView = (HorizontalListView) findViewById(C0349R.id.m_FilterHorizontalListView);
        this.m_EffectLayout = (RelativeLayout) findViewById(C0349R.id.m_EffectLayout);
        this.m_PreviousImgButton = (ImageButton) findViewById(C0349R.id.m_PreviousImageButton);
        this.m_NextImgButton = (ImageButton) findViewById(C0349R.id.m_NextImageButton);
        this.m_FilterColorImgButton = (ImageButton) findViewById(C0349R.id.m_FilterColorImageButton);
        this.m_OriImg = new ImageView(this);
        this.m_FixImg = new ImageView(this);
        this.m_ImageFilter = new ImageFilter(this);
        this.m_ImageRGB = new ImageFilterPlus(this);
        this.m_AssetManager = getAssets();
        this.m_EditDrawViewBackgroundColor = C0349R.color.WHITE;
        CalculateUISize();
        this.m_CopiesTextView.setText(String.valueOf(this.m_iPhotoCopiesList.get(AdjustPos(0))));
        this.m_RotateButton.setOnClickListener(this.RotateListener);
        this.m_PreviousImgButton.setOnClickListener(new C02763());
        this.m_NextImgButton.setOnClickListener(new C02774());
        this.m_PoolBackButton.setOnClickListener(new C02785());
        this.m_AddButton.setOnClickListener(this.AddCopiesListener);
        this.m_ReduceButton.setOnClickListener(this.ReduceCopiesListener);
        this.m_FxButton.setOnClickListener(this.FxListener);
        this.m_BorderButton.setOnClickListener(this.BorderListener);
        this.m_SaveButton.setOnClickListener(this.SavePhotoListener);
        this.m_ResetButton.setOnClickListener(this.ResetListener);
        this.m_FilterColorImgButton.setOnClickListener(this.ShowFilterListener);
        CreateRGBSelector();
    }

    private void CreateRGBSelector() {
        if (this.m_FilterColorSelector == null) {
            this.m_FilterColorSelector = (FilterRGBColorSelector) findViewById(C0349R.id.m_FilterColorSelector);
            this.m_FilterColorSelector.setOnColorChangedListener(new RGBFilterColorSelectorListener());
        }
    }

    private void GetEditMetaOrInitial() {
        this.m_EditMeta = this.m_EditMetaUtility.GetEditMeta(this.m_iRoute);
        this.m_iSelPosList = this.m_EditMeta.GetSelPosList();
        this.m_iPhotoCopiesList = this.m_EditMeta.GetCopiesList();
        if (this.m_iRoute == COLOR_HSLC) {
            this.m_strPoolPathList = this.m_EditMeta.GetMobilePathList();
        } else {
            this.m_strPoolPathList = this.m_EditMeta.GetFetchPathList();
            this.m_iEditSDcardIDList = this.m_EditMeta.GetSDcardIDList();
            this.m_iEditSDcardSIDList = this.m_EditMeta.GetSDcardSIDList();
        }
        CheckCollagePath();
        this.m_iNum = this.m_iSelPosList.size();
        GetEditXMLandVerticalData();
        GetEditBorderAndFilterData();
        InitEditMeta();
    }

    private void InitEditMeta() {
        int i;
        this.m_FilterColorValueList = new ArrayList();
        this.m_FCVLList = new ArrayList();
        int iLen = this.m_iPhotoCopiesList.size();
        for (i = 0; i < iLen; i += COLOR_HSLC) {
            this.m_FCVLList.add(new ArrayList());
        }
        if (this.m_strXMLPathList.isEmpty()) {
            for (i = 0; i < iLen; i += COLOR_HSLC) {
                this.m_strXMLPathList.add(XmlPullParser.NO_NAMESPACE);
                this.m_bIsVerticalList.add(Boolean.valueOf(false));
                this.m_bIsEditedList.add(Boolean.valueOf(false));
            }
        }
        if (this.m_iBorderPosList.isEmpty()) {
            for (i = 0; i < iLen; i += COLOR_HSLC) {
                this.m_iBorderPosList.add(Integer.valueOf(0));
                this.m_iFilterPosList.add(Integer.valueOf(0));
            }
        }
    }

    private void CheckCollagePath() {
        this.m_strCollagePathList = this.m_EditMeta.GetCollagePathList();
        this.m_strEditPathList = new ArrayList();
        Iterator it = this.m_strPoolPathList.iterator();
        while (it.hasNext()) {
            this.m_strEditPathList.add((String) it.next());
        }
        if (this.m_strCollagePathList.isEmpty()) {
            for (int i = 0; i < this.m_strEditPathList.size(); i += COLOR_HSLC) {
                this.m_strCollagePathList.add(XmlPullParser.NO_NAMESPACE);
            }
        } else {
            it = this.m_strCollagePathList.iterator();
            while (it.hasNext()) {
                String path = (String) it.next();
                if (!path.isEmpty()) {
                    this.m_strEditPathList.set(this.m_strCollagePathList.indexOf(path), path);
                }
            }
        }
        this.LOG.m383d("CheckCollagePath", "m_strCollagePathList: " + this.m_strCollagePathList);
    }

    void GetEditXMLandVerticalData() {
        this.m_strXMLPathList = this.m_EditMeta.GetXMLList();
        this.m_bIsVerticalList = this.m_EditMeta.GetIsVerticalList();
        this.m_bIsEditedList = this.m_EditMeta.GetIsEditList();
    }

    void GetEditBorderAndFilterData() {
        this.m_iBorderPosList = this.m_EditMeta.GetBorderPosList();
        this.m_iFilterPosList = this.m_EditMeta.GetFilterPosList();
    }

    private void PrepareDrawView(int iPos) {
        if (!CheckPhotoIfIeagalRatio(iPos)) {
            GetMaxSizeConfig();
            String strEditPath = GetEditFileAndXML(iPos);
            CalculateUIScale(strEditPath);
            InitEditDrawView();
            ModifyDrawView(iPos, strEditPath);
        }
    }

    private boolean CheckPhotoIfIeagalRatio(int iPos) {
        int iResult = BitmapMonitor.IsLegalRatio(this, Uri.parse("file://" + ((String) this.m_strEditPathList.get(iPos)))).GetResult();
        if (iResult == 0) {
            return false;
        }
        if (this.m_iPhotoNum == 0) {
            this.m_bBackPoolFor1stPhotoError = true;
        }
        ShowErrorMSG(BitmapMonitorResult.GetError(this, iResult));
        return true;
    }

    private void ModifyDrawView(int iPos, String strEditPath) {
        boolean bHaveGarnish = true;
        this.m_bNextLock = false;
        if (this.m_bCallBack) {
            this.m_boLastVertical = ((Boolean) this.m_bIsVerticalList.get(iPos)).booleanValue();
            this.m_strXMLPath = (String) this.m_strXMLPathList.get(iPos);
            InitGarnishByLastEdit();
        } else if (((String) this.m_strXMLPathList.get(iPos)).isEmpty()) {
            bHaveGarnish = InitGarnish(strEditPath);
            this.m_bIsVerticalList.set(iPos, Boolean.valueOf(this.m_EditDrawView.IsVertical()));
        } else {
            this.m_boLastVertical = ((Boolean) this.m_bIsVerticalList.get(iPos)).booleanValue();
            this.m_strXMLPath = (String) this.m_strXMLPathList.get(iPos);
            bHaveGarnish = InitGarnishByLastEdit();
        }
        if (bHaveGarnish) {
            AfterMakeDrawView();
            if (this.m_ContentLayout == null) {
                this.m_ContentLayout = (RelativeLayout) findViewById(C0349R.id.m_ContentLayout);
                this.m_ContentLayout.getLayoutParams().height = this.m_iScreenWidth;
                this.m_ShowImgLayout.addView(this.m_EditDrawView);
                if (this.m_iNum > COLOR_HSLC) {
                    this.m_PreviousImgButton.setVisibility(0);
                    this.m_NextImgButton.setVisibility(0);
                }
                if (this.m_iPhotoNum == 0) {
                    this.m_PreviousImgButton.setVisibility(PENDDING_SIZE);
                }
            }
            this.m_EditDrawView.invalidate();
            return;
        }
        ShowWaitDialog(false);
    }

    private void AfterMakeDrawView() {
        Log.e("AfterMakeDrawView", String.valueOf("--"));
        SetColorImgBtn(true, true);
        ChangeMode(this.m_iMode);
        switch (this.m_iMode) {
            case FILTER_STATE /*94*/:
                CreateFilter();
                break;
            case BORDER_STATE /*99*/:
                CreateBorder();
                break;
            case NEXT_PAGE /*111*/:
                CreateFilter();
                CreateBorder();
                break;
        }
        if (this.m_iPhotoNum == 0) {
            this.m_PreviousImgButton.setVisibility(PENDDING_SIZE);
        } else {
            this.m_PreviousImgButton.setVisibility(0);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        int i;
        int j;
        int iLen = outState.getInt(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST_LEN);
        int jLen = outState.getInt(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LEN);
        outState.remove(JumpBundleMessage520.BUNDLE_MSG_EDIT_IMG_POS);
        outState.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LEN);
        for (i = 0; i < iLen; i += COLOR_HSLC) {
            for (j = 0; j < jLen; j += COLOR_HSLC) {
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Hue");
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Saturation");
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Light");
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Contrast");
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Red");
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Green");
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Blue");
            }
        }
        i = 0;
        j = 0;
        Iterator it = this.m_FCVLList.iterator();
        while (it.hasNext()) {
            ArrayList<FilterColorValue> fcvl = (ArrayList) it.next();
            if (fcvl != null) {
                i = this.m_FCVLList.indexOf(fcvl);
                Iterator it2 = fcvl.iterator();
                while (it2.hasNext()) {
                    FilterColorValue fcv = (FilterColorValue) it2.next();
                    if (fcv != null) {
                        j = fcvl.indexOf(fcv);
                        if (j != -1) {
                            outState.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Hue", fcv.m_fHue);
                            outState.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Saturation", fcv.m_fSaturation);
                            outState.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Light", fcv.m_fLight);
                            outState.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Contrast", fcv.m_fContrast);
                            outState.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Red", fcv.m_fRed);
                            outState.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Green", fcv.m_fGreen);
                            outState.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST + i + "_" + j + "_Blue", fcv.m_fBlue);
                        }
                    }
                }
            }
        }
        outState.putInt(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LEN, j + COLOR_HSLC);
        outState.putInt(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIST_LEN, i + COLOR_HSLC);
        outState.putInt(JumpBundleMessage520.BUNDLE_MSG_EDIT_IMG_POS, this.m_iPhotoNum);
        SaveEditData();
    }

    private void SaveEditData() {
        this.LOG.m383d("SaveEditData", "m_strCollagePathList: " + this.m_strCollagePathList);
        SaveInfoBe4Next(this.m_iPhotoNum);
        this.m_EditMeta.SetCopies(this.m_iPhotoCopiesList);
        this.m_EditMeta.SetXMLVerticalIsEdit(this.m_strXMLPathList, this.m_bIsVerticalList, this.m_bIsEditedList);
        this.m_EditMeta.SetBorderAndFilterPos(this.m_iBorderPosList, this.m_iFilterPosList);
        this.m_EditMeta.SetCollagePath(this.m_strCollagePathList);
        if (this.m_iRoute == COLOR_RGB) {
            this.m_EditMeta.SetFetchPath(this.m_strEditPathList);
        }
        this.m_EditMetaUtility.SetEditMeta(this.m_EditMeta);
        Iterator it = this.m_iSelPosList.iterator();
        while (it.hasNext()) {
            int i = ((Integer) it.next()).intValue();
            ArrayList<FilterColorValue> fcvl = (ArrayList) this.m_FCVLList.get(i);
            if (!fcvl.isEmpty()) {
                this.m_EditMetaUtility.SetFilterValue(i, fcvl);
            }
        }
    }

    boolean SaveLastEditProgress(String strXMLPath) {
        if (this.m_EditDrawView.SaveNonORGPathGarnishItam(this.m_strRoot) != 0) {
            return false;
        }
        return this.m_EditDrawView.SaveGarnish2XML(strXMLPath);
    }

    void GetMaxSizeConfig() {
        this.m_iMaxW = this.m_iOriMaxW;
        this.m_iMaxH = this.m_iOriMaxH;
    }

    void CalculateUISize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int iScreenWidth = dm.widthPixels;
        this.m_iScreenWidth = iScreenWidth;
        this.m_iIdeaSize = iScreenWidth / 5;
        this.m_iBrushItemSize = iScreenWidth / 5;
        this.m_iBorderItemSize = iScreenWidth / 5;
        this.m_iEditDrawViewWidth = iScreenWidth;
        this.m_iEditDrawViewHeight = iScreenWidth;
    }

    private String GetEditFileAndXML(int iPos) {
        String strEditFile = (String) this.m_strEditPathList.get(iPos);
        SetEditFilePath(strEditFile);
        String strXML = XmlPullParser.NO_NAMESPACE;
        if (this.m_iRoute == COLOR_HSLC) {
            String path = (String) this.m_strEditPathList.get(iPos);
            strXML = ((String) this.m_strEditPathList.get(iPos)).subSequence(path.lastIndexOf(File.separator), path.lastIndexOf(".")).toString();
        } else {
            strXML = File.separator + String.valueOf(this.m_iEditSDcardIDList.get(iPos));
        }
        this.m_strXMLPath = this.m_strRoot + String.valueOf(strXML) + PRINBIZ_EDIT_XML;
        return strEditFile;
    }

    void SetEditFilePath(String strEditPath) {
        this.m_strEditFilePath = strEditPath;
    }

    String GetEditFilePath() {
        return this.m_strEditFilePath;
    }

    void CalculateUIScale(String strPath) {
        this.m_ViewScale = ((float) this.m_iMaxH) / ((float) this.m_iEditDrawViewHeight);
        if (DecideAutoRotate(Uri.parse("file://" + strPath))) {
            int temp = this.m_iMaxW;
            this.m_iMaxW = this.m_iMaxH;
            this.m_iMaxH = temp;
            this.m_boFirstLoadPhotoVertical = false;
        } else {
            this.m_boFirstLoadPhotoVertical = true;
        }
        this.LOG.m383d("CalculateUIScale-m_iMaxW", String.valueOf(this.m_iMaxW));
        this.LOG.m383d("CalculateUIScale-m_iMaxH", String.valueOf(this.m_iMaxH));
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
        params1.height = this.m_iEditDrawViewHeight;
        params1.width = this.m_iEditDrawViewWidth;
        this.m_EditDrawView.setLayoutParams(params1);
        int iResult = this.m_EditDrawView.InitDrawView(((float) this.m_iMaxW) / this.m_ViewScale, ((float) this.m_iMaxH) / this.m_ViewScale, (float) this.m_iEditDrawViewWidth, (float) this.m_iEditDrawViewHeight, this.m_boFirstLoadPhotoVertical, this.m_iOriMaxW, this.m_iOriMaxH);
        if (iResult != 0) {
            ShowErrorMSG(BitmapMonitorResult.GetError(this, iResult));
            return;
        }
        this.m_EditDrawView.SetViewScale(this.m_ViewScale);
        this.m_EditDrawView.SetDrawViewListener(new OnDrawViewListener());
        this.m_EditDrawView.setBackgroundColor(getResources().getColor(this.m_EditDrawViewBackgroundColor));
    }

    boolean InitGarnish(String strEditPath) {
        if (InitGarnish_Background() && InitGarnish_EditPhoto(strEditPath)) {
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
        ShowErrorMSG(BitmapMonitorResult.GetError(this, bmr.GetResult()));
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
                int iEditPhotoWidth = editPicture.getWidth();
                int iEditPhotoHeight = editPicture.getHeight();
                Log.e("iEditPhotoWidth", String.valueOf(iEditPhotoWidth));
                Log.e("iEditPhotoHeight", String.valueOf(iEditPhotoHeight));
                editPicture.recycle();
                if (!strEditFile.contains(PringoConvenientConst.PRINGO_COLLAGE_FILE)) {
                    if (!strEditFile.contains(PringoConvenientConst.PRINBIZ_FOLDER)) {
                        return true;
                    }
                    if (!FileUtility.FileExist(this.m_strSaveFolder + "/" + FileUtility.GetFileName(strEditFile))) {
                        return true;
                    }
                }
                bmr = BitmapMonitor.CreateBitmap(iEditPhotoWidth, iEditPhotoHeight, Config.ARGB_8888);
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
        if (!this.m_WaitingDialog.IsShowing()) {
            this.m_WaitingDialog.ShowDialog(getString(C0349R.string.PLEASE_WAIT));
        }
        RotateBorder();
        int iResult = this.m_EditDrawView.Rotate90WithContent();
        if (iResult != 0) {
            ShowErrorMSG(BitmapMonitorResult.GetError(this, iResult));
        } else if (CreateBorder() && !CreateFilter()) {
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
                if (strBorderPath.contains(this.m_strBorderRootPath + PringoConvenientConst.H_BORDER_PATH)) {
                    strBorderPath = strBorderPath.replace(PringoConvenientConst.H_BORDER_PATH, PringoConvenientConst.V_BORDER_PATH);
                }
            } else if (strBorderPath.contains(this.m_strBorderRootPath + PringoConvenientConst.V_BORDER_PATH)) {
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
        int iPos = AdjustPos(this.m_iPhotoNum);
        this.m_strEditPathList.set(iPos, this.m_strPoolPathList.get(iPos));
        this.m_bIsEditedList.set(iPos, Boolean.valueOf(false));
        this.m_strXMLPathList.set(iPos, XmlPullParser.NO_NAMESPACE);
        this.m_iBorderPosList.set(iPos, Integer.valueOf(0));
        this.m_iFilterPosList.set(iPos, Integer.valueOf(0));
        this.m_EditMetaUtility.InitDrawView(iPos);
    }

    private void CheckImage(int iPos) {
        this.m_bNextLock = true;
        if (this.m_iRoute == COLOR_HSLC) {
            NextEditPhoto(iPos);
        } else if (IsNeedFetch(iPos)) {
            FetchImage(iPos);
        } else {
            NextEditPhoto(iPos);
        }
    }

    private void NextEditPhoto(int iPos) {
        iPos = AdjustPos(iPos);
        this.m_CopiesTextView.setText(String.valueOf(this.m_iPhotoCopiesList.get(iPos)));
        this.m_EditMetaUtility.InitDrawView(iPos);
        ChangeMode(NEXT_PAGE);
    }

    private void SaveInfoBe4Next(int pos) {
        if (this.m_EditDrawView != null) {
            int iPos = AdjustPos(pos);
            SaveLastEditProgress(this.m_strXMLPath);
            this.m_bIsVerticalList.set(iPos, Boolean.valueOf(this.m_EditDrawView.IsVertical()));
            this.m_strXMLPathList.set(iPos, this.m_strXMLPath);
            this.m_iBorderPosList.set(iPos, Integer.valueOf(this.m_iSelectBorder));
            this.m_iFilterPosList.set(iPos, Integer.valueOf(this.m_iSelectFilter));
            if (!((ArrayList) this.m_FCVLList.get(iPos)).isEmpty()) {
                this.m_FCVLList.set(iPos, this.m_FilterColorValueList);
            }
        }
    }

    private int AdjustPos(int iPos) {
        int iLen = this.m_iSelPosList.size();
        if (this.m_iPhotoNum >= iLen) {
            this.m_iPhotoNum -= iLen;
        } else if (this.m_iPhotoNum < 0) {
            this.m_iPhotoNum += iLen;
        }
        if (iPos >= iLen) {
            iPos -= iLen;
        } else if (iPos < 0) {
            iPos += iLen;
        }
        return ((Integer) this.m_iSelPosList.get(iPos)).intValue();
    }

    private boolean IsNeedFetch(int iPos) {
        if (((String) this.m_strEditPathList.get(AdjustPos(iPos))).isEmpty()) {
            return true;
        }
        return false;
    }

    private void FetchImage(int pos) {
        if (IsNeedFetch(pos)) {
            int iPos = AdjustPos(pos);
            this.m_EditMetaUtility.FetchPhoto(pos, ((Integer) this.m_iEditSDcardIDList.get(iPos)).intValue(), ((Integer) this.m_iEditSDcardSIDList.get(iPos)).intValue());
        }
    }

    void onSaveImageButtonClicked() {
        ShowWaitDialog(true);
        String strSaveFolder = this.m_strSaveFolder;
        String strEditPath = GetEditFilePath();
        this.m_EditDrawView.ResetZoom();
        if (FileUtility.SDCardState()) {
            FileUtility.CreateFolder(strSaveFolder);
            this.m_SaveImage = new SaveImage(strSaveFolder, strEditPath);
            this.m_SaveImage.execute(new Void[0]);
            return;
        }
        ShowWaitDialog(false);
        ShowErrorMSG(getString(C0349R.string.SD_CARD_NOT_READYING));
    }

    private boolean SaveBitmap2File(String strSavePath, Bitmap bmp, CompressFormat format) {
        boolean bResult = FileUtility.SaveBitmap(strSavePath, bmp, format);
        this.m_EditFileUri = MediaUtil.SaveIntoMedia(this, strSavePath);
        return bResult;
    }

    void ShowSharePhotoAlertDialog() {
        Builder alertDialog = new Builder(this);
        alertDialog.setMessage(getString(C0349R.string.CHECK_SHARE_PHOTO));
        alertDialog.setPositiveButton(getString(C0349R.string.OK), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                EditGeneralActivity.this.OpenShareList(EditGeneralActivity.this.m_EditFileUri);
            }
        });
        alertDialog.setNegativeButton(getString(C0349R.string.CANCEL), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
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
        this.m_iPhotoNum = this.m_iPrePos;
        this.m_bNextLock = false;
        this.m_ErrorDialog.ShowSimpleMSGDialog(strErr, getString(C0349R.string.ERROR));
    }

    private void RetainData() {
        this.m_bBackLoack = true;
        ShowWaitDialog(true);
        this.m_EditMetaUtility.onSaveEditPhoto();
    }

    protected void onDestroy() {
        ShowWaitDialog(false);
        super.onDestroy();
    }

    public void onBackPressed() {
        if (!this.m_bBackLoack) {
            RetainData();
        }
    }

    void Exit() {
        setResult(59, null);
        if (this.m_EditDrawView != null) {
            this.m_EditDrawView.Clear();
        }
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

    private void SwapWidthHeight() {
        int temp = this.m_iBorderW;
        this.m_iBorderW = this.m_iBorderH;
        this.m_iBorderH = temp;
    }

    boolean CreatePictureEditThumbnail() {
        float fScale = this.m_ViewScale / ((float) this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE);
        this.m_BorderViewHorizontalSpacing = 12;
        this.m_FilterViewHorizontalSpacing = 12;
        if (this.m_iMaxW > this.m_iMaxH) {
            if (this.m_iBorderW < this.m_iBorderH) {
                SwapWidthHeight();
            }
        } else if (this.m_iBorderW > this.m_iBorderH) {
            SwapWidthHeight();
        }
        this.m_iBorderItemSizeWidth = (((int) (((float) this.m_iBorderW) / this.m_ViewScale)) / this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE) + this.m_BorderViewHorizontalSpacing;
        this.m_iBorderItemSizeHeight = ((int) (((float) this.m_iBorderH) / this.m_ViewScale)) / this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE;
        this.m_iFilterItemSizeWidth = (((int) (((float) this.m_iMaxW) / this.m_ViewScale)) / this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE) + this.m_FilterViewHorizontalSpacing;
        this.m_iFilterItemSizeHeight = ((int) (((float) this.m_iMaxH) / this.m_ViewScale)) / this.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE;
        if (!CreateDefaultOrgThumbnail(fScale, this.m_EditDrawView.GetBoundX(), this.m_EditDrawView.GetBoundY())) {
            ShowErrorMSG(getString(C0349R.string.CREATE_BITMAP_OUT_OF_MEMORY));
            return false;
        } else if (CreateDefaultUIThumbnail(fScale, this.m_EditDrawView.GetBoundX(), this.m_EditDrawView.GetBoundY())) {
            if (this.m_BorderAdapter != null) {
                this.m_BorderAdapter.ReflashViewScope();
            }
            if (this.m_FilterAdapter != null) {
                this.m_FilterAdapter.ClearCache();
                this.m_FilterAdapter.SetOrgThumbnail(this.m_DefaultOrgThumbnailNoMatrixBmp);
                this.m_FilterAdapter.notifyDataSetChanged();
            }
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

    boolean CreateBorder() {
        Log.e("CreateBorder", "CreateBorder");
        if (!CreatePictureEditThumbnail()) {
            return false;
        }
        if (this.m_BorderRelativeLayout == null) {
            this.m_BorderRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_BorderRelativeLayout);
            this.m_BorderHorizontalListView = (HorizontalListView) findViewById(C0349R.id.m_BorderHorizontalListView);
        }
        if (this.m_BorderAdapter != null) {
            this.m_BorderHorizontalListView.setAdapter(null);
            this.m_BorderAdapter.StopReflashThumbnail();
            this.m_BorderAdapter.ClearCache();
            this.m_BorderAdapter = null;
        }
        this.m_BorderAdapter = new BorderAdapter(this, this.m_BorderHorizontalListView, HORIZONTAL_CACHE_BITMAP_SIZE, PENDDING_SIZE);
        String strBorderPath = this.m_strBorderRootPath + PringoConvenientConst.V_BORDER_PATH;
        GarnishSecurity security = this.m_VBorderGarnishSecurity;
        if (!this.m_EditDrawView.IsVertical()) {
            strBorderPath = this.m_strBorderRootPath + PringoConvenientConst.H_BORDER_PATH;
            security = this.m_HBorderGarnishSecurity;
        }
        if (this.m_BorderLoader != null) {
            this.m_BorderLoader.Stop();
            this.m_BorderLoader = null;
        }
        if (this.m_BorderLoadFinishListener == null) {
            this.m_BorderLoadFinishListener = new BorderLoadFinishListener();
        }
        if (this.m_strBorderThumbnailPathList == null) {
            this.m_strBorderThumbnailPathList = new ArrayList();
        }
        this.m_BorderLoader = new EditBorderLoader(this, security, this.m_BorderLoadFinishListener);
        this.m_BorderLoader.LoadThumbnailFromSDCardAndAssets(getExternalFilesDir(null).getAbsolutePath() + File.separator + strBorderPath + File.separator + PringoConvenientConst.THUMB, strBorderPath + File.separator + PringoConvenientConst.THUMB, this.m_strBorderThumbnailPathList);
        this.m_BorderLoader.execute(new String[0]);
        return true;
    }

    private void Add1stPosEmptyPath() {
        ArrayList<String> strBorderPlusHeadList = new ArrayList();
        strBorderPlusHeadList.add(XmlPullParser.NO_NAMESPACE);
        Iterator it = this.m_strBorderThumbnailPathList.iterator();
        while (it.hasNext()) {
            strBorderPlusHeadList.add((String) it.next());
        }
        this.m_strBorderThumbnailPathList.clear();
        this.m_strBorderThumbnailPathList = strBorderPlusHeadList;
    }

    boolean CreateFilter() {
        if (!CreatePictureEditThumbnail()) {
            return false;
        }
        if (this.m_FilterColorSelector == null) {
            this.m_FilterColorSelector = (FilterRGBColorSelector) findViewById(C0349R.id.m_FilterColorSelector);
            this.m_FilterColorSelector.setOnColorChangedListener(new RGBFilterColorSelectorListener());
        }
        if (this.m_FilterColorImgButton == null) {
            this.m_FilterColorImgButton = (ImageButton) findViewById(C0349R.id.m_FilterColorImageButton);
        }
        if (this.m_FilterRelativeLayout == null) {
            this.m_FilterRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_FilterRelativeLayout);
            this.m_FilterHorizontalListView = (HorizontalListView) findViewById(C0349R.id.m_FilterHorizontalListView);
        }
        if (this.m_FilterAdapter != null) {
            this.m_FilterHorizontalListView.setAdapter(null);
            this.m_FilterAdapter.StopReflashThumbnail();
            this.m_FilterAdapter.ClearCache();
            this.m_FilterAdapter = null;
        }
        this.m_FilterAdapter = new FilterAdapter(this, this.m_FilterHorizontalListView, HORIZONTAL_CACHE_BITMAP_SIZE, PENDDING_SIZE);
        this.m_FilterAdapter.SetOrgThumbnail(this.m_DefaultOrgThumbnailNoMatrixBmp);
        FakeLoadFilterFromSDCard();
        return true;
    }

    void FakeLoadFilterFromSDCard() {
        this.m_iFilterCount = 36;
        int iPos = AdjustPos(this.m_iPhotoNum);
        if (((ArrayList) this.m_FCVLList.get(iPos)).isEmpty()) {
            this.m_FilterColorValueList = new ArrayList();
            for (int i = 0; i < this.m_iFilterCount; i += COLOR_HSLC) {
                this.m_FilterColorValueList.add(new FilterColorValue());
            }
            ((FilterColorValue) this.m_FilterColorValueList.get(0)).m_iFilterName = -1;
            ((FilterColorValue) this.m_FilterColorValueList.get(COLOR_HSLC)).m_iFilterName = C0349R.string.FN_DREAM;
            ((FilterColorValue) this.m_FilterColorValueList.get(COLOR_RGB)).m_iFilterName = C0349R.string.FN_PINK;
            ((FilterColorValue) this.m_FilterColorValueList.get(3)).m_iFilterName = C0349R.string.FN_DEW;
            ((FilterColorValue) this.m_FilterColorValueList.get(HORIZONTAL_CACHE_BITMAP_SIZE)).m_iFilterName = C0349R.string.FN_BLOOM;
            ((FilterColorValue) this.m_FilterColorValueList.get(5)).m_iFilterName = C0349R.string.FN_MORNING;
            ((FilterColorValue) this.m_FilterColorValueList.get(6)).m_iFilterName = C0349R.string.FN_MODEL;
            ((FilterColorValue) this.m_FilterColorValueList.get(7)).m_iFilterName = C0349R.string.FN_NIGHT;
            ((FilterColorValue) this.m_FilterColorValueList.get(PENDDING_SIZE)).m_iFilterName = C0349R.string.FN_NEPTUNE;
            ((FilterColorValue) this.m_FilterColorValueList.get(9)).m_iFilterName = C0349R.string.FN_DAWN;
            ((FilterColorValue) this.m_FilterColorValueList.get(10)).m_iFilterName = C0349R.string.FN_FOREST;
            ((FilterColorValue) this.m_FilterColorValueList.get(11)).m_iFilterName = C0349R.string.FN_BABY;
            ((FilterColorValue) this.m_FilterColorValueList.get(12)).m_iFilterName = C0349R.string.FN_JOYFUL;
            ((FilterColorValue) this.m_FilterColorValueList.get(13)).m_iFilterName = C0349R.string.FN_FOOD;
            ((FilterColorValue) this.m_FilterColorValueList.get(14)).m_iFilterName = C0349R.string.FN_FOG;
            ((FilterColorValue) this.m_FilterColorValueList.get(15)).m_iFilterName = C0349R.string.FN_HAPPY;
            ((FilterColorValue) this.m_FilterColorValueList.get(16)).m_iFilterName = C0349R.string.FN_STRONG;
            ((FilterColorValue) this.m_FilterColorValueList.get(17)).m_iFilterName = C0349R.string.FN_BRIGHT;
            ((FilterColorValue) this.m_FilterColorValueList.get(18)).m_iFilterName = C0349R.string.FN_WARM;
            ((FilterColorValue) this.m_FilterColorValueList.get(19)).m_iFilterName = C0349R.string.FN_SHY;
            ((FilterColorValue) this.m_FilterColorValueList.get(20)).m_iFilterName = C0349R.string.FN_CANDY;
            ((FilterColorValue) this.m_FilterColorValueList.get(21)).m_iFilterName = C0349R.string.FN_DARK;
            ((FilterColorValue) this.m_FilterColorValueList.get(22)).m_iFilterName = C0349R.string.FN_FAIRY;
            ((FilterColorValue) this.m_FilterColorValueList.get(23)).m_iFilterName = C0349R.string.FN_SMILE;
            ((FilterColorValue) this.m_FilterColorValueList.get(24)).m_iFilterName = C0349R.string.FN_GREEN;
            ((FilterColorValue) this.m_FilterColorValueList.get(25)).m_iFilterName = C0349R.string.FN_WORN;
            ((FilterColorValue) this.m_FilterColorValueList.get(26)).m_iFilterName = C0349R.string.FN_LIGHT;
            ((FilterColorValue) this.m_FilterColorValueList.get(27)).m_iFilterName = C0349R.string.FN_BEIGE;
            ((FilterColorValue) this.m_FilterColorValueList.get(28)).m_iFilterName = C0349R.string.FN_SUNSET;
            ((FilterColorValue) this.m_FilterColorValueList.get(29)).m_iFilterName = C0349R.string.FN_STAR;
            ((FilterColorValue) this.m_FilterColorValueList.get(30)).m_iFilterName = C0349R.string.FN_BLUSH;
            ((FilterColorValue) this.m_FilterColorValueList.get(31)).m_iFilterName = C0349R.string.FN_LOVE;
            ((FilterColorValue) this.m_FilterColorValueList.get(32)).m_iFilterName = C0349R.string.FN_SNOW;
            ((FilterColorValue) this.m_FilterColorValueList.get(33)).m_iFilterName = C0349R.string.FN_DUSK;
            ((FilterColorValue) this.m_FilterColorValueList.get(34)).m_iFilterName = C0349R.string.FN_SEPIA;
            ((FilterColorValue) this.m_FilterColorValueList.get(35)).m_iFilterName = C0349R.string.FN_RANDW;
            ((FilterColorValue) this.m_FilterColorValueList.get(0)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_NON;
            ((FilterColorValue) this.m_FilterColorValueList.get(COLOR_HSLC)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_28;
            ((FilterColorValue) this.m_FilterColorValueList.get(COLOR_RGB)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_29;
            ((FilterColorValue) this.m_FilterColorValueList.get(3)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_30;
            ((FilterColorValue) this.m_FilterColorValueList.get(HORIZONTAL_CACHE_BITMAP_SIZE)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_31;
            ((FilterColorValue) this.m_FilterColorValueList.get(5)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_32;
            ((FilterColorValue) this.m_FilterColorValueList.get(6)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_33;
            ((FilterColorValue) this.m_FilterColorValueList.get(7)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_34;
            ((FilterColorValue) this.m_FilterColorValueList.get(PENDDING_SIZE)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_35;
            ((FilterColorValue) this.m_FilterColorValueList.get(9)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_36;
            ((FilterColorValue) this.m_FilterColorValueList.get(10)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_37;
            ((FilterColorValue) this.m_FilterColorValueList.get(11)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_38;
            ((FilterColorValue) this.m_FilterColorValueList.get(12)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP1;
            ((FilterColorValue) this.m_FilterColorValueList.get(13)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP2;
            ((FilterColorValue) this.m_FilterColorValueList.get(14)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP3;
            ((FilterColorValue) this.m_FilterColorValueList.get(15)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP4;
            ((FilterColorValue) this.m_FilterColorValueList.get(16)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP5;
            ((FilterColorValue) this.m_FilterColorValueList.get(17)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP6;
            ((FilterColorValue) this.m_FilterColorValueList.get(18)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP7;
            ((FilterColorValue) this.m_FilterColorValueList.get(19)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP8;
            ((FilterColorValue) this.m_FilterColorValueList.get(20)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP9;
            ((FilterColorValue) this.m_FilterColorValueList.get(21)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP10;
            ((FilterColorValue) this.m_FilterColorValueList.get(22)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP11;
            ((FilterColorValue) this.m_FilterColorValueList.get(23)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP12;
            ((FilterColorValue) this.m_FilterColorValueList.get(24)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP13;
            ((FilterColorValue) this.m_FilterColorValueList.get(25)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP14;
            ((FilterColorValue) this.m_FilterColorValueList.get(26)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP15;
            ((FilterColorValue) this.m_FilterColorValueList.get(27)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP16;
            ((FilterColorValue) this.m_FilterColorValueList.get(28)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP17;
            ((FilterColorValue) this.m_FilterColorValueList.get(29)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP18;
            ((FilterColorValue) this.m_FilterColorValueList.get(30)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP19;
            ((FilterColorValue) this.m_FilterColorValueList.get(31)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP20;
            ((FilterColorValue) this.m_FilterColorValueList.get(32)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP21;
            ((FilterColorValue) this.m_FilterColorValueList.get(33)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP22;
            ((FilterColorValue) this.m_FilterColorValueList.get(34)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_OLD1;
            ((FilterColorValue) this.m_FilterColorValueList.get(35)).m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_GRAY1;
            for (int j = 0; j < 36; j += COLOR_HSLC) {
                FilterColorValue fcv = (FilterColorValue) this.m_FilterColorValueList.get(j);
                fcv.m_fHue = this.m_EditMetaUtility.GetFilterValue(iPos, j, "_Hue");
                fcv.m_fSaturation = this.m_EditMetaUtility.GetFilterValue(iPos, j, "_Saturation");
                fcv.m_fLight = this.m_EditMetaUtility.GetFilterValue(iPos, j, "_Light");
                fcv.m_fContrast = this.m_EditMetaUtility.GetFilterValue(iPos, j, "_Contrast");
                fcv.m_fRed = this.m_EditMetaUtility.GetFilterValue(iPos, j, "_Red");
                fcv.m_fGreen = this.m_EditMetaUtility.GetFilterValue(iPos, j, "_Green");
                fcv.m_fBlue = this.m_EditMetaUtility.GetFilterValue(iPos, j, "_Blue");
            }
            this.m_FCVLList.set(iPos, this.m_FilterColorValueList);
        } else {
            this.m_FilterColorValueList = (ArrayList) this.m_FCVLList.get(iPos);
        }
        this.m_iFilterItemNameSizeHeight = this.m_iFilterItemSizeHeight / 7;
        if (this.m_iFilterItemSizeHeight < this.m_iFilterItemSizeWidth) {
            this.m_iFilterItemNameSizeHeight = this.m_iFilterItemSizeWidth / 7;
        }
        this.m_FilterHorizontalListView.getLayoutParams().height = this.m_iFilterItemSizeHeight + this.m_iFilterItemNameSizeHeight;
        this.m_EffectLayout.getLayoutParams().height = (this.m_iFilterItemSizeHeight + this.m_iFilterItemNameSizeHeight) + 10;
        this.m_FilterHorizontalListView.setAdapter(this.m_FilterAdapter);
        this.m_FilterAdapter.notifyDataSetChanged();
        if (this.m_iMode == NEXT_PAGE) {
            InitFilterAdjust();
        }
    }

    private void InitFilterAdjust() {
        int iID = ((Integer) this.m_iFilterPosList.get(AdjustPos(this.m_iPhotoNum))).intValue();
        this.m_iSelectFilter = iID;
        GarnishItem garnishItem = this.m_EditDrawView.GetEditPhotoGarnishItem();
        garnishItem.SetBackUpViewScaleBitmap();
        SetImageFilter(garnishItem, ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_FilterType);
        garnishItem.RemoveFilterViewScaleBitmap();
        garnishItem.SetBackUpFilterViewScaleBitmap();
        AdjustImageFilter(garnishItem, (FilterColorValue) this.m_FilterColorValueList.get(iID));
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

    void AdjustImageFilter(GarnishItem garnishItem, FilterColorValue filterColorValue) {
        if (filterColorValue.HaveModifyHSLCValue() || filterColorValue.HaveModifyRGBValue()) {
            int iPos = AdjustPos(this.m_iPhotoNum);
            if (!((Boolean) this.m_bIsEditedList.get(iPos)).booleanValue()) {
                this.m_bIsEditedList.set(iPos, Boolean.valueOf(true));
            }
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
            }
        }
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

    void SetFilterColorValue(int iID, float fHue, float fSaturation, float fLight, float fContrast) {
        ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fHue = fHue;
        ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fSaturation = fSaturation;
        ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fLight = fLight;
        ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fContrast = fContrast;
    }

    private void SetFilterRGBvalue(int iID, float fRed, float fGreen, float fBlue) {
        ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fRed = fRed;
        ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fGreen = fGreen;
        ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fBlue = fBlue;
    }

    void InitFilterColorPosition(int iID) {
        this.m_FilterColorSelector.SetHSVCAndPostion(((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fHue, ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fSaturation, ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fLight, ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fContrast);
    }

    void InitFilterRGBColorPosition(int iID) {
        this.m_FilterColorSelector.SetRGBtoPostion(((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fRed, ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fGreen, ((FilterColorValue) this.m_FilterColorValueList.get(iID)).m_fBlue);
    }

    void DecideFilterColorButtonState(int iType) {
        if (this.m_FilterColorLayout.isShown()) {
            boolean bIsModify;
            if (iType == COLOR_HSLC) {
                bIsModify = ((FilterColorValue) this.m_FilterColorValueList.get(this.m_iSelectFilter)).HaveModifyHSLCValue();
            } else {
                bIsModify = ((FilterColorValue) this.m_FilterColorValueList.get(this.m_iSelectFilter)).HaveModifyRGBValue();
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
        this.m_FilterColorLayout.setVisibility(PENDDING_SIZE);
    }

    void SetColorImgBtn(boolean bShow, boolean bEnable) {
        if (bShow) {
            this.m_FilterColorImgButton.setVisibility(0);
        } else {
            this.m_FilterColorImgButton.setVisibility(PENDDING_SIZE);
        }
        this.m_FilterColorImgButton.setEnabled(bEnable);
    }

    void ChangeMode(int iMode) {
        this.m_iMode = iMode;
        ShowFilterSelector(false);
        Log.e("ChangeMode", String.valueOf(iMode));
        switch (iMode) {
            case FILTER_STATE /*94*/:
                SetColorImgBtn(true, true);
                this.m_EditDrawView.SetMode(FILTER_STATE);
                this.m_BorderRelativeLayout.setVisibility(PENDDING_SIZE);
                this.m_FilterRelativeLayout.setVisibility(0);
                this.m_FxButton.setBackgroundResource(C0349R.drawable.fx_button_clicked);
                this.m_BorderButton.setBackgroundResource(C0349R.drawable.border_button);
            case BORDER_STATE /*99*/:
                SetColorImgBtn(false, false);
                this.m_EditDrawView.SetMode(BORDER_STATE);
                this.m_BorderRelativeLayout.setVisibility(0);
                this.m_FilterRelativeLayout.setVisibility(PENDDING_SIZE);
                this.m_FxButton.setBackgroundResource(C0349R.drawable.fx_button);
                this.m_BorderButton.setBackgroundResource(C0349R.drawable.border_button_clicked);
            case NEXT_PAGE /*111*/:
                SetColorImgBtn(false, false);
                this.m_BorderRelativeLayout.setVisibility(PENDDING_SIZE);
                this.m_FilterRelativeLayout.setVisibility(PENDDING_SIZE);
            default:
        }
    }
}
