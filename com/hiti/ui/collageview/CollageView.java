package com.hiti.ui.collageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.InputDeviceCompat;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.hiti.HitiChunk.HitiChunkUtility;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.ui.drawview.DrawView;
import com.hiti.ui.drawview.DrawViewListener;
import com.hiti.ui.drawview.garnishitem.GarnishItem;
import com.hiti.ui.drawview.garnishitem.parser.GarnishItemXMLCreator;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.PringoConvenientConst;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.telnet.TelnetOption;
import org.xmlpull.v1.XmlPullParser;

public class CollageView extends RelativeLayout {
    public static final int BUSINESS_CARD_MODE = 1;
    public static final int COLLAGE_MODE = 0;
    public static final int GREETING_CARD_MODE = 2;
    private static final String PRINGO_FOLDER = "Pringo";
    private static final String PRINGO_TEMP_FOLDER = "/temp";
    private LogManager LOG;
    private ArrayList<ImageButton> m_AddPhotoImageButtonList;
    private Bitmap m_CollageBackgroundBitmap;
    private ImageView m_CollageBackgroundImageView;
    private Bitmap m_CollageFocusBitmap;
    private ImageView m_CollageFocusImageView;
    private Bitmap m_CollageForegroundBitmap;
    private ImageView m_CollageForegroundImageView;
    private CollageInfoGroup m_CollageInfoGroup;
    private Bitmap m_CollageMetalBitmap;
    private ImageView m_CollageMetalImageView;
    private CollageViewTouchListener m_CollageViewTouchListener;
    private Context m_Context;
    private ArrayList<DrawView> m_DrawViewManager;
    private Paint m_FoucsCollagePaint;
    private byte[] m_bRegionTable;
    private boolean m_boEnableDrawView;
    private boolean m_boPhotoActionConnection;
    private float m_fViewHeight;
    private float m_fViewScale;
    private float m_fViewWidth;
    private float m_fViewWindowHeight;
    private float m_fViewWindowWidth;
    private int m_iGSMaskColor;
    private int m_iLastSelectDrawView;
    private int m_iRegionTableHeight;
    private int m_iRegionTableWidth;

    class OnDrawViewListener extends DrawViewListener {
        OnDrawViewListener() {
        }

        public void OnZoomStart(float fZoom) {
        }

        public void OnZoomEnd(float fZoom) {
        }

        public void OnFocusGarnish() {
        }

        public void OnMissFocusGarnish() {
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
        }

        public void OnIDPhotoMode(Matrix matrix) {
            CollageView.this.LOG.m384e("Collage View OnIDPhotoMode", "Collage View OnIDPhotoMode");
            if (CollageView.this.IsPhotoActionConnection()) {
                int iCurrent = CollageView.this.GetLastSelectDrawView();
                GarnishItem currentGarnishItem = ((DrawView) CollageView.this.m_DrawViewManager.get(iCurrent)).GetEditPhotoGarnishItem();
                GarnishItem currentGSMaskGarnishItem = ((DrawView) CollageView.this.m_DrawViewManager.get(iCurrent)).GetGSMaskGarnishItem();
                int i = CollageView.COLLAGE_MODE;
                while (i < CollageView.this.m_DrawViewManager.size()) {
                    if (i != iCurrent && CollageView.this.IsSameCollageInfoGroup(iCurrent, i)) {
                        CollageView.this.m_CollageInfoGroup.GetCollageInfo(i).GetGroup();
                        ((DrawView) CollageView.this.m_DrawViewManager.get(i)).setEnabled(true);
                        GarnishItem.SetSameAction(currentGarnishItem, ((DrawView) CollageView.this.m_DrawViewManager.get(i)).GetEditPhotoGarnishItem());
                        if (currentGSMaskGarnishItem != null) {
                            GarnishItem.SetSameAction(currentGarnishItem, ((DrawView) CollageView.this.m_DrawViewManager.get(i)).GetGSMaskGarnishItem());
                        }
                        ((DrawView) CollageView.this.m_DrawViewManager.get(i)).setEnabled(false);
                    }
                    i += CollageView.BUSINESS_CARD_MODE;
                }
            }
        }
    }

    public CollageView(Context context) {
        super(context);
        this.m_boEnableDrawView = true;
        this.m_boPhotoActionConnection = false;
        this.m_fViewWidth = 0.0f;
        this.m_fViewHeight = 0.0f;
        this.m_iRegionTableWidth = COLLAGE_MODE;
        this.m_iRegionTableHeight = COLLAGE_MODE;
        this.m_fViewWindowWidth = 0.0f;
        this.m_fViewWindowHeight = 0.0f;
        this.m_fViewScale = 1.0f;
        this.m_iLastSelectDrawView = -1;
        this.m_DrawViewManager = null;
        this.m_AddPhotoImageButtonList = null;
        this.m_CollageBackgroundImageView = null;
        this.m_CollageBackgroundBitmap = null;
        this.m_CollageMetalImageView = null;
        this.m_CollageForegroundImageView = null;
        this.m_CollageFocusImageView = null;
        this.m_CollageMetalBitmap = null;
        this.m_CollageForegroundBitmap = null;
        this.m_CollageFocusBitmap = null;
        this.m_FoucsCollagePaint = new Paint();
        this.m_CollageInfoGroup = null;
        this.m_bRegionTable = null;
        this.LOG = null;
        this.m_iGSMaskColor = 15658734;
        this.m_CollageViewTouchListener = null;
        this.m_Context = context;
        this.m_DrawViewManager = new ArrayList();
        this.m_AddPhotoImageButtonList = new ArrayList();
        this.LOG = new LogManager(COLLAGE_MODE);
    }

    public void EnableDrawView(boolean boEnableDrawView) {
        this.m_boEnableDrawView = boEnableDrawView;
    }

    public boolean InitCollageView(float fWidth, float fHeight, float fWindowWidth, float fWindowHeight) {
        this.m_fViewWidth = fWidth;
        this.m_fViewHeight = fHeight;
        this.m_iRegionTableWidth = (int) this.m_fViewWidth;
        this.m_iRegionTableHeight = (int) this.m_fViewHeight;
        this.m_fViewWindowWidth = fWindowWidth;
        this.m_fViewWindowHeight = fWindowHeight;
        LayoutParams CollageViewLayoutParams = new LayoutParams(-2, -2);
        CollageViewLayoutParams.width = (int) fWindowWidth;
        CollageViewLayoutParams.height = (int) fWindowHeight;
        CollageViewLayoutParams.addRule(13);
        setLayoutParams(CollageViewLayoutParams);
        setBackgroundColor(-1);
        return true;
    }

    public float GetViewWidth() {
        return this.m_fViewWidth;
    }

    public float GetViewHeight() {
        return this.m_fViewHeight;
    }

    public int GetRegionTableWidth() {
        return this.m_iRegionTableWidth;
    }

    public int GetRegionTableHeight() {
        return this.m_iRegionTableHeight;
    }

    public float GetViewWindowWidth() {
        return this.m_fViewWindowWidth;
    }

    public float GetViewWindowHeight() {
        return this.m_fViewWindowHeight;
    }

    public void SetViewScale(float ViewScale) {
        if (ViewScale != 0.0f) {
            this.m_fViewScale = ViewScale;
        }
    }

    public float GetViewScale() {
        return this.m_fViewScale;
    }

    public int GetLastSelectDrawView() {
        return this.m_iLastSelectDrawView;
    }

    public int GetGSMaskColor() {
        return this.m_iGSMaskColor;
    }

    public void SetGSMaskColor(int iGSMaskColor) {
        this.m_iGSMaskColor = iGSMaskColor;
    }

    public int SetCollageTemplete(float fViewScale, ArrayList<String> arrayList, CollageInfoGroup collageInfoGroup, int iButtonRes, int iOutputDefaultMaxWidth, int iOutputDefaultMaxHeight) {
        int i;
        int iResult;
        RemoveAllView();
        SetViewScale(fViewScale);
        this.m_CollageInfoGroup = collageInfoGroup;
        LoadCollageBackground();
        LoadCollageMetal();
        int iSize = this.m_CollageInfoGroup.GetCollageNumbers();
        for (i = COLLAGE_MODE; i < iSize; i += BUSINESS_CARD_MODE) {
            this.m_DrawViewManager.add(new DrawView(this.m_Context));
        }
        this.LOG.m385i("m_CollageInfoGroup", String.valueOf(this.m_CollageInfoGroup));
        for (i = COLLAGE_MODE; i < this.m_DrawViewManager.size(); i += BUSINESS_CARD_MODE) {
            this.LOG.m385i("m_CollageInfoGroup i", String.valueOf(i));
            int x = (int) (this.m_CollageInfoGroup.GetCollageInfo(i).GetLeft() / this.m_fViewScale);
            int y = (int) (this.m_CollageInfoGroup.GetCollageInfo(i).GetTop() / this.m_fViewScale);
            float fViewWidth = ((float) ((int) this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth())) / this.m_fViewScale;
            float fViewHeight = ((float) ((int) this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight())) / this.m_fViewScale;
            DrawView drawView = (DrawView) this.m_DrawViewManager.get(i);
            LayoutParams drawViewParams = new LayoutParams(-2, -2);
            drawViewParams.width = (int) fViewWidth;
            drawViewParams.height = (int) fViewHeight;
            drawViewParams.leftMargin = x;
            drawViewParams.topMargin = y;
            this.LOG.m384e("CollageInfo x", String.valueOf(x));
            this.LOG.m384e("CollageInfo y", String.valueOf(y));
            this.LOG.m384e("fViewWidth", String.valueOf(fViewWidth));
            this.LOG.m384e("fViewHeight", String.valueOf(fViewHeight));
            drawView.setLayoutParams(drawViewParams);
            iResult = drawView.InitDrawView(fViewWidth, fViewHeight, fViewWidth, fViewHeight, true, iOutputDefaultMaxWidth, iOutputDefaultMaxHeight);
            if (IsPhotoActionConnection()) {
                drawView.SetMode(93);
                drawView.SetDrawViewListener(new OnDrawViewListener());
            }
            drawView.SetViewScale(this.m_fViewScale);
            if (iResult != 0) {
                return iResult;
            }
            iResult = LoadRegionViewMask(drawView, i);
            if (iResult != 0) {
                return iResult;
            }
            addView(drawView);
        }
        iResult = LoadRegionTable();
        if (iResult != 0) {
            return iResult;
        }
        LoadCollageForeground();
        LoadAddPhotoImageButton(iButtonRes);
        LoadCollageFocus();
        return COLLAGE_MODE;
    }

    public boolean HaveCollageViewTouchListener() {
        if (this.m_CollageViewTouchListener != null) {
            return true;
        }
        return false;
    }

    public void SetCollageViewTouchListener(CollageViewTouchListener collageViewTouchListener) {
        this.m_CollageViewTouchListener = collageViewTouchListener;
    }

    public void RemoveAllView() {
        int i;
        if (!(this.m_DrawViewManager == null || this.m_DrawViewManager.size() == 0)) {
            for (i = COLLAGE_MODE; i < this.m_DrawViewManager.size(); i += BUSINESS_CARD_MODE) {
                removeView((View) this.m_DrawViewManager.get(i));
            }
            for (i = COLLAGE_MODE; i < this.m_DrawViewManager.size(); i += BUSINESS_CARD_MODE) {
                ((DrawView) this.m_DrawViewManager.get(i)).Clear();
            }
            this.m_DrawViewManager.clear();
        }
        if (this.m_AddPhotoImageButtonList != null && this.m_AddPhotoImageButtonList.size() != 0) {
            for (i = COLLAGE_MODE; i < this.m_AddPhotoImageButtonList.size(); i += BUSINESS_CARD_MODE) {
                removeView((View) this.m_AddPhotoImageButtonList.get(i));
            }
            this.m_AddPhotoImageButtonList.clear();
        }
    }

    private void LoadAddPhotoImageButton(int iButtonRes) {
        for (int i = COLLAGE_MODE; i < this.m_DrawViewManager.size(); i += BUSINESS_CARD_MODE) {
            if (IsValidButtonPosition(this.m_CollageInfoGroup.GetCollageInfo(i).GetAddPhotoButtonLeft(), this.m_CollageInfoGroup.GetCollageInfo(i).GetAddPhotoButtonTop())) {
                ImageButton imageButton = new ImageButton(this.m_Context);
                imageButton.setBackgroundResource(iButtonRes);
                int x = (int) (this.m_CollageInfoGroup.GetCollageInfo(i).GetAddPhotoButtonLeft() / this.m_fViewScale);
                int y = (int) (this.m_CollageInfoGroup.GetCollageInfo(i).GetAddPhotoButtonTop() / this.m_fViewScale);
                LayoutParams imageButtonParams = new LayoutParams(-2, -2);
                imageButtonParams.leftMargin = x;
                imageButtonParams.topMargin = y;
                imageButtonParams.width = (int) (((float) this.m_CollageInfoGroup.GetAddPhotoButtonWidth()) / this.m_fViewScale);
                imageButtonParams.height = (int) (((float) this.m_CollageInfoGroup.GetAddPhotoButtonHeight()) / this.m_fViewScale);
                imageButton.setId(i);
                imageButton.setLayoutParams(imageButtonParams);
                this.m_AddPhotoImageButtonList.add(imageButton);
                addView(imageButton);
            }
        }
    }

    public void ResetAddPhotoImageButtonBackground(int iCurrent, int iRes) {
        if (iCurrent < this.m_AddPhotoImageButtonList.size() && iCurrent >= 0) {
            ((ImageButton) this.m_AddPhotoImageButtonList.get(iCurrent)).setBackgroundResource(iRes);
        }
    }

    public void ResetAddPhotoImageButtonBackgroundByGroup(int iCurrent, int iRes, boolean boSameGroup) {
        if (iCurrent < this.m_DrawViewManager.size() && iCurrent >= 0) {
            for (int i = COLLAGE_MODE; i < this.m_AddPhotoImageButtonList.size(); i += BUSINESS_CARD_MODE) {
                boolean doSet = IsSameCollageInfoGroup(iCurrent, ((ImageButton) this.m_AddPhotoImageButtonList.get(i)).getId());
                if (!boSameGroup) {
                    doSet = !doSet;
                }
                if (doSet) {
                    ((ImageButton) this.m_AddPhotoImageButtonList.get(i)).setBackgroundResource(iRes);
                }
            }
        }
    }

    public void SetAddPhotoImageButtonClickListener(OnClickListener l) {
        Iterator it = this.m_AddPhotoImageButtonList.iterator();
        while (it.hasNext()) {
            ((ImageButton) it.next()).setOnClickListener(l);
        }
    }

    public int ChangeEditPhoto(int iID, String strPhotoPath) {
        if (IsPhotoActionConnection()) {
            return ChangeEditPhotoForGroup(iID, strPhotoPath);
        }
        if (iID >= this.m_CollageInfoGroup.GetCollageNumbers()) {
            return COLLAGE_MODE;
        }
        int iPhotoWidth = (int) this.m_CollageInfoGroup.GetCollageInfo(iID).GetWidth();
        int iPhotoHeight = (int) this.m_CollageInfoGroup.GetCollageInfo(iID).GetHeight();
        if (strPhotoPath == null) {
            return COLLAGE_MODE;
        }
        DrawView drawView = (DrawView) this.m_DrawViewManager.get(iID);
        int iResult = LoadEditPhoto(drawView, iPhotoWidth, iPhotoHeight, strPhotoPath);
        drawView.invalidate();
        this.m_iLastSelectDrawView = iID;
        return iResult;
    }

    private int ChangeEditPhotoForGroup(int iCurrent, String strPhotoPath) {
        int iResult = COLLAGE_MODE;
        if (iCurrent >= this.m_CollageInfoGroup.GetCollageNumbers()) {
            return COLLAGE_MODE;
        }
        for (int i = COLLAGE_MODE; i < this.m_DrawViewManager.size(); i += BUSINESS_CARD_MODE) {
            if (IsSameCollageInfoGroup(iCurrent, i)) {
                int iPhotoWidth = (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth();
                int iPhotoHeight = (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight();
                if (strPhotoPath != null) {
                    DrawView drawView = (DrawView) this.m_DrawViewManager.get(i);
                    iResult = LoadEditPhoto(drawView, iPhotoWidth, iPhotoHeight, strPhotoPath);
                    drawView.invalidate();
                    if (iResult != 0) {
                        return iResult;
                    }
                } else {
                    continue;
                }
            }
        }
        return iResult;
    }

    private int LoadCollageBackground() {
        String strBackgroundPhotoPath = null;
        if (!(this.m_CollageInfoGroup.GetBackgroundPhotoPath() == null || this.m_CollageInfoGroup.GetBackgroundPhotoPath().equals(XmlPullParser.NO_NAMESPACE))) {
            strBackgroundPhotoPath = this.m_CollageInfoGroup.GetBackgroundPhotoPath();
        }
        if (!(this.m_CollageInfoGroup.GetDemoBackgroundPhotoPath() == null || this.m_CollageInfoGroup.GetDemoBackgroundPhotoPath().equals(XmlPullParser.NO_NAMESPACE))) {
            strBackgroundPhotoPath = this.m_CollageInfoGroup.GetDemoBackgroundPhotoPath();
        }
        if (strBackgroundPhotoPath == null) {
            return COLLAGE_MODE;
        }
        this.LOG.m384e("---- Collage Background path ----", this.m_CollageInfoGroup.GetBackgroundPhotoPath());
        this.LOG.m384e("---- Collage Demo Background path ----", this.m_CollageInfoGroup.GetDemoBackgroundPhotoPath());
        int iPhotoWidth = (int) (((float) this.m_CollageInfoGroup.GetPhotoWidth()) / this.m_fViewScale);
        int iPhotoHeight = (int) (((float) this.m_CollageInfoGroup.GetPhotoHeight()) / this.m_fViewScale);
        BitmapMonitorResult bmr = null;
        try {
            InputStream is;
            if (this.m_CollageInfoGroup.GetResourceFromWhere() == 0) {
                is = this.m_Context.getAssets().open(strBackgroundPhotoPath);
            } else {
                is = new FileInputStream(strBackgroundPhotoPath);
            }
            bmr = BitmapMonitor.CreateBitmap(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        Bitmap bmp = bmr.GetBitmap();
        bmr = BitmapMonitor.CreateScaledBitmap(bmp, iPhotoWidth, iPhotoHeight, true);
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        bmp.recycle();
        if (this.m_CollageBackgroundImageView == null) {
            this.m_CollageBackgroundImageView = new ImageView(this.m_Context);
        }
        if (!(this.m_CollageBackgroundBitmap == null || this.m_CollageBackgroundBitmap.isRecycled())) {
            this.m_CollageBackgroundBitmap.recycle();
        }
        this.m_CollageBackgroundBitmap = bmr.GetBitmap();
        LayoutParams backgroundImageViewParams = new LayoutParams(-2, -2);
        backgroundImageViewParams.leftMargin = COLLAGE_MODE;
        backgroundImageViewParams.topMargin = COLLAGE_MODE;
        backgroundImageViewParams.width = (int) (((float) this.m_CollageInfoGroup.GetPhotoWidth()) / this.m_fViewScale);
        backgroundImageViewParams.height = (int) (((float) this.m_CollageInfoGroup.GetPhotoHeight()) / this.m_fViewScale);
        this.m_CollageBackgroundImageView.setLayoutParams(backgroundImageViewParams);
        this.m_CollageBackgroundImageView.setImageBitmap(this.m_CollageBackgroundBitmap);
        addView(this.m_CollageBackgroundImageView);
        return COLLAGE_MODE;
    }

    private int LoadCollageForeground() {
        String strForegroundPhotoPath = null;
        if (!(this.m_CollageInfoGroup.GetForegroundPhotoPath() == null || this.m_CollageInfoGroup.GetForegroundPhotoPath().equals(XmlPullParser.NO_NAMESPACE))) {
            strForegroundPhotoPath = this.m_CollageInfoGroup.GetForegroundPhotoPath();
        }
        if (strForegroundPhotoPath == null) {
            return COLLAGE_MODE;
        }
        this.LOG.m384e("---- Collage Foreground path ----", this.m_CollageInfoGroup.GetForegroundPhotoPath());
        int iPhotoWidth = (int) (((float) this.m_CollageInfoGroup.GetPhotoWidth()) / this.m_fViewScale);
        int iPhotoHeight = (int) (((float) this.m_CollageInfoGroup.GetPhotoHeight()) / this.m_fViewScale);
        BitmapMonitorResult bmr = null;
        try {
            InputStream is;
            if (this.m_CollageInfoGroup.GetResourceFromWhere() == 0) {
                is = this.m_Context.getAssets().open(strForegroundPhotoPath);
            } else {
                is = new FileInputStream(strForegroundPhotoPath);
            }
            bmr = BitmapMonitor.CreateBitmap(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        Bitmap bmp = bmr.GetBitmap();
        bmr = BitmapMonitor.CreateScaledBitmap(bmp, iPhotoWidth, iPhotoHeight, true);
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        bmp.recycle();
        if (this.m_CollageForegroundImageView == null) {
            this.m_CollageForegroundImageView = new ImageView(this.m_Context);
        }
        if (!(this.m_CollageForegroundBitmap == null || this.m_CollageForegroundBitmap.isRecycled())) {
            this.m_CollageForegroundBitmap.recycle();
        }
        this.m_CollageForegroundBitmap = bmr.GetBitmap();
        LayoutParams foregroundImageViewParams = new LayoutParams(-2, -2);
        foregroundImageViewParams.leftMargin = COLLAGE_MODE;
        foregroundImageViewParams.topMargin = COLLAGE_MODE;
        foregroundImageViewParams.width = (int) (((float) this.m_CollageInfoGroup.GetPhotoWidth()) / this.m_fViewScale);
        foregroundImageViewParams.height = (int) (((float) this.m_CollageInfoGroup.GetPhotoHeight()) / this.m_fViewScale);
        this.m_CollageForegroundImageView.setLayoutParams(foregroundImageViewParams);
        this.m_CollageForegroundImageView.setImageBitmap(this.m_CollageForegroundBitmap);
        addView(this.m_CollageForegroundImageView);
        return COLLAGE_MODE;
    }

    private int LoadCollageMetal() {
        String strMetalPhotoPath = null;
        if (!(this.m_CollageInfoGroup.GetMetalPhotoPath() == null || this.m_CollageInfoGroup.GetMetalPhotoPath().equals(XmlPullParser.NO_NAMESPACE))) {
            strMetalPhotoPath = this.m_CollageInfoGroup.GetMetalPhotoPath();
        }
        if (strMetalPhotoPath == null) {
            return COLLAGE_MODE;
        }
        this.LOG.m384e("---- Collage Metal path ----", this.m_CollageInfoGroup.GetMetalPhotoPath());
        int iPhotoWidth = (int) (((float) this.m_CollageInfoGroup.GetPhotoWidth()) / this.m_fViewScale);
        int iPhotoHeight = (int) (((float) this.m_CollageInfoGroup.GetPhotoHeight()) / this.m_fViewScale);
        BitmapMonitorResult bmr = null;
        try {
            InputStream is;
            if (this.m_CollageInfoGroup.GetResourceFromWhere() == 0) {
                is = this.m_Context.getAssets().open(strMetalPhotoPath);
            } else {
                is = new FileInputStream(strMetalPhotoPath);
            }
            bmr = BitmapMonitor.CreateBitmap(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        Bitmap bmp = bmr.GetBitmap();
        bmr = BitmapMonitor.CreateScaledBitmap(bmp, iPhotoWidth, iPhotoHeight, true);
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        bmp.recycle();
        if (this.m_CollageMetalImageView == null) {
            this.m_CollageMetalImageView = new ImageView(this.m_Context);
        }
        if (!(this.m_CollageMetalBitmap == null || this.m_CollageMetalBitmap.isRecycled())) {
            this.m_CollageMetalBitmap.recycle();
        }
        this.m_CollageMetalBitmap = bmr.GetBitmap();
        LayoutParams metalImageViewParams = new LayoutParams(-2, -2);
        metalImageViewParams.leftMargin = COLLAGE_MODE;
        metalImageViewParams.topMargin = COLLAGE_MODE;
        metalImageViewParams.width = (int) (((float) this.m_CollageInfoGroup.GetPhotoWidth()) / this.m_fViewScale);
        metalImageViewParams.height = (int) (((float) this.m_CollageInfoGroup.GetPhotoHeight()) / this.m_fViewScale);
        this.m_CollageMetalImageView.setLayoutParams(metalImageViewParams);
        this.m_CollageMetalImageView.setImageBitmap(this.m_CollageMetalBitmap);
        addView(this.m_CollageMetalImageView);
        return COLLAGE_MODE;
    }

    private int LoadCollageFocus() {
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap((int) (((float) this.m_CollageInfoGroup.GetPhotoWidth()) / this.m_fViewScale), (int) (((float) this.m_CollageInfoGroup.GetPhotoHeight()) / this.m_fViewScale), Config.ARGB_8888);
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        if (this.m_CollageFocusImageView == null) {
            this.m_CollageFocusImageView = new ImageView(this.m_Context);
        }
        if (!(this.m_CollageFocusBitmap == null || this.m_CollageFocusBitmap.isRecycled())) {
            this.m_CollageFocusBitmap.recycle();
        }
        this.m_CollageFocusBitmap = bmr.GetBitmap();
        LayoutParams focusImageViewParams = new LayoutParams(-2, -2);
        focusImageViewParams.leftMargin = COLLAGE_MODE;
        focusImageViewParams.topMargin = COLLAGE_MODE;
        focusImageViewParams.width = (int) (((float) this.m_CollageInfoGroup.GetPhotoWidth()) / this.m_fViewScale);
        focusImageViewParams.height = (int) (((float) this.m_CollageInfoGroup.GetPhotoHeight()) / this.m_fViewScale);
        this.m_CollageFocusImageView.setLayoutParams(focusImageViewParams);
        this.m_CollageFocusImageView.setImageBitmap(this.m_CollageFocusBitmap);
        addView(this.m_CollageFocusImageView);
        this.m_FoucsCollagePaint.reset();
        this.m_FoucsCollagePaint.setAntiAlias(true);
        this.m_FoucsCollagePaint.setStyle(Style.STROKE);
        this.m_FoucsCollagePaint.setColor(InputDeviceCompat.SOURCE_ANY);
        this.m_FoucsCollagePaint.setStrokeWidth(TypedValue.applyDimension(BUSINESS_CARD_MODE, 6.0f, getResources().getDisplayMetrics()));
        this.m_FoucsCollagePaint.setShadowLayer(TypedValue.applyDimension(BUSINESS_CARD_MODE, 3.0f, getResources().getDisplayMetrics()), 1.0f, 1.0f, -12303292);
        return COLLAGE_MODE;
    }

    private int LoadEditPhoto(DrawView drawView, int iPhotoWidth, int iPhotoHeight, String strPhotoPath) {
        if (strPhotoPath != null) {
            if (!BitmapMonitor.BitmapExist(this.m_Context, strPhotoPath)) {
                return 97;
            }
            try {
                BitmapMonitorResult bmr = BitmapMonitor.CreateCroppedBitmapNew(this.m_Context, Uri.parse("file://" + strPhotoPath), iPhotoWidth, iPhotoHeight);
                if (!bmr.IsSuccess()) {
                    return bmr.GetResult();
                }
                Bitmap editPictureBmp = bmr.GetBitmap();
                GarnishItem photoGarnishItem = new GarnishItem(this.m_Context, BUSINESS_CARD_MODE);
                int iResult = photoGarnishItem.InitUIView(editPictureBmp, new PointF(drawView.GetViewWindowCenterX(), drawView.GetViewWindowCenterY()), this.m_fViewScale, "file://" + strPhotoPath, GREETING_CARD_MODE);
                if (iResult != 0) {
                    return iResult;
                }
                drawView.AddGarnish(photoGarnishItem);
                LoadGSMask(drawView, editPictureBmp.getWidth(), editPictureBmp.getHeight(), strPhotoPath);
                editPictureBmp.recycle();
            } catch (Exception e) {
                e.printStackTrace();
                return 97;
            }
        }
        return COLLAGE_MODE;
    }

    private int LoadBackground(DrawView drawView, int iPhotoWidth, int iPhotoHeight) {
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(iPhotoWidth, iPhotoHeight, Config.ARGB_8888);
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        Bitmap backgroundBmp = bmr.GetBitmap();
        backgroundBmp.eraseColor(COLLAGE_MODE);
        if (drawView.AddGarnish(backgroundBmp, new PointF(drawView.GetViewWindowCenterX(), drawView.GetViewWindowCenterY()), COLLAGE_MODE, null, COLLAGE_MODE) <= 0) {
            return 99;
        }
        backgroundBmp.recycle();
        return COLLAGE_MODE;
    }

    public int LoadGSMask(DrawView drawView, int iPhotoWidth, int iPhotoHeight, String strPhotoPath) {
        String strFilePath = XmlPullParser.NO_NAMESPACE;
        if (strPhotoPath.contains(PringoConvenientConst.PRINGO_COLLAGE_FILE)) {
            strFilePath = strPhotoPath;
        } else {
            if (!strPhotoPath.contains(PRINGO_FOLDER)) {
                return COLLAGE_MODE;
            }
            strFilePath = Environment.getExternalStorageDirectory().getPath() + File.separator + PRINGO_FOLDER + "/" + FileUtility.GetFileName(strPhotoPath);
        }
        if (!FileUtility.FileExist(strFilePath)) {
            return COLLAGE_MODE;
        }
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(new BufferedInputStream(this.m_Context.getContentResolver().openInputStream(Uri.parse("file://" + strPhotoPath))), null, options, false);
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            bmr = BitmapMonitor.CreateBitmap(options.outWidth, options.outHeight, Config.ARGB_8888);
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            Bitmap gsMaskBmp = bmr.GetBitmap();
            if (HitiChunkUtility.GetMaskFromFile(strFilePath, gsMaskBmp, this.m_iGSMaskColor)) {
                bmr = BitmapMonitor.CreateScaledBitmap(gsMaskBmp, iPhotoWidth, iPhotoHeight, true);
                gsMaskBmp.recycle();
                gsMaskBmp = bmr.GetBitmap();
                GarnishItem maskItem = new GarnishItem(this.m_Context, 7);
                int iResult = maskItem.InitUIView(gsMaskBmp, new PointF(drawView.GetViewWindowCenterX(), drawView.GetViewWindowCenterY()), this.m_fViewScale, null, COLLAGE_MODE);
                if (iResult != 0) {
                    return iResult;
                }
                GarnishItem.SaveGarnishORGBitmap(this.m_Context, PRINGO_TEMP_FOLDER, maskItem, gsMaskBmp);
                drawView.AddGarnish(maskItem);
                gsMaskBmp.recycle();
            } else {
                gsMaskBmp.recycle();
            }
            return COLLAGE_MODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int LoadAllEditPhoto(ArrayList<String> strPhotoPath) {
        for (int i = COLLAGE_MODE; i < this.m_DrawViewManager.size(); i += BUSINESS_CARD_MODE) {
            DrawView drawView = (DrawView) this.m_DrawViewManager.get(i);
            int iResult = LoadBackground(drawView, (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth(), (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight());
            if (iResult != 0) {
                return iResult;
            }
            iResult = LoadEditPhoto(drawView, (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth(), (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight(), (String) strPhotoPath.get(i));
            if (iResult != 0) {
                return iResult;
            }
        }
        return COLLAGE_MODE;
    }

    public int LoadIDFormatEditPhoto(ArrayList<String> strPhotoPath, ArrayList<String> strXMLPaths) {
        for (int i = COLLAGE_MODE; i < this.m_DrawViewManager.size(); i += BUSINESS_CARD_MODE) {
            DrawView drawView = (DrawView) this.m_DrawViewManager.get(i);
            int iResult = LoadBackground(drawView, (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth(), (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight());
            if (iResult != 0) {
                return iResult;
            }
            String strXMLPath = (String) strXMLPaths.get(i);
            if (strXMLPath.isEmpty()) {
                iResult = LoadEditPhoto(drawView, (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth(), (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight(), (String) strPhotoPath.get(i));
                if (iResult != 0) {
                    return iResult;
                }
            } else {
                try {
                    drawView.AddGarnish(GarnishItemXMLCreator.ReadGarnishXML(new BufferedInputStream(this.m_Context.getContentResolver().openInputStream(Uri.parse("file://" + strXMLPath))), this.m_Context, (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth(), (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight()));
                } catch (Exception e) {
                    e.printStackTrace();
                    return 97;
                }
            }
        }
        return COLLAGE_MODE;
    }

    public int LoadAllLastEditPhoto(ArrayList<String> strXMLPaths) {
        int i = COLLAGE_MODE;
        while (i < this.m_DrawViewManager.size()) {
            DrawView drawView = (DrawView) this.m_DrawViewManager.get(i);
            int iResult = LoadBackground(drawView, (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth(), (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight());
            if (iResult != 0) {
                return iResult;
            }
            try {
                drawView.AddGarnish(GarnishItemXMLCreator.ReadGarnishXML(new BufferedInputStream(this.m_Context.getContentResolver().openInputStream(Uri.parse("file://" + ((String) strXMLPaths.get(i))))), this.m_Context, (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth(), (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight()));
                i += BUSINESS_CARD_MODE;
            } catch (Exception e) {
                e.printStackTrace();
                return 97;
            }
        }
        return COLLAGE_MODE;
    }

    private int LoadRegionViewMask(DrawView drawView, int i) {
        if (this.m_CollageInfoGroup.GetCollageInfo(i).GetMaskPath() == XmlPullParser.NO_NAMESPACE) {
            return COLLAGE_MODE;
        }
        try {
            InputStream is;
            if (this.m_CollageInfoGroup.GetResourceFromWhere() == 0) {
                is = this.m_Context.getAssets().open(this.m_CollageInfoGroup.GetCollageInfo(i).GetMaskPath());
            } else {
                is = new FileInputStream(this.m_CollageInfoGroup.GetCollageInfo(i).GetMaskPath());
            }
            BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(is, false);
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            Bitmap bmp = bmr.GetBitmap();
            bmr = BitmapMonitor.CreateScaledBitmap(bmp, (int) (this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth() / this.m_fViewScale), (int) (this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight() / this.m_fViewScale), true);
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            Bitmap regionViewMask = bmr.GetBitmap();
            bmp.recycle();
            return drawView.AddRegionViewMask(regionViewMask);
        } catch (IOException e) {
            e.printStackTrace();
            return 97;
        }
    }

    private int LoadRegionTable() {
        try {
            InputStream is;
            if (this.m_CollageInfoGroup.GetResourceFromWhere() == 0) {
                is = this.m_Context.getAssets().open(this.m_CollageInfoGroup.GetRegionTablePath());
            } else {
                is = new FileInputStream(this.m_CollageInfoGroup.GetRegionTablePath());
            }
            BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(is, false);
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            Bitmap bmp = bmr.GetBitmap();
            bmr = BitmapMonitor.CreateScaledBitmap(bmp, this.m_iRegionTableWidth, this.m_iRegionTableHeight, true);
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            Bitmap RegionBmp = bmr.GetBitmap();
            bmp.recycle();
            ByteBuffer RegionTableByteBuffer = ByteBuffer.allocate((this.m_iRegionTableWidth * this.m_iRegionTableHeight) * 4);
            RegionBmp.copyPixelsToBuffer(RegionTableByteBuffer);
            RegionBmp.recycle();
            this.m_bRegionTable = new byte[(this.m_iRegionTableWidth * this.m_iRegionTableHeight)];
            for (int i = COLLAGE_MODE; i < this.m_iRegionTableWidth * this.m_iRegionTableHeight; i += BUSINESS_CARD_MODE) {
                this.m_bRegionTable[i] = RegionTableByteBuffer.get((i * 4) + BUSINESS_CARD_MODE);
            }
            RegionTableByteBuffer.clear();
            return COLLAGE_MODE;
        } catch (IOException e) {
            e.printStackTrace();
            return 97;
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (this.m_DrawViewManager == null) {
            return super.dispatchTouchEvent(event);
        }
        if (this.m_DrawViewManager.size() <= 0) {
            return super.dispatchTouchEvent(event);
        }
        int iTouchIndex = ((int) event.getX()) + (this.m_iRegionTableWidth * ((int) event.getY()));
        switch (event.getAction() & TelnetOption.MAX_OPTION_VALUE) {
            case COLLAGE_MODE /*0*/:
                if (iTouchIndex >= 0 && iTouchIndex < this.m_iRegionTableWidth * this.m_iRegionTableHeight) {
                    int i = (this.m_bRegionTable[iTouchIndex] & TelnetOption.MAX_OPTION_VALUE) | COLLAGE_MODE;
                    this.LOG.m384e("KAKAGet " + iTouchIndex + " " + (this.m_iRegionTableWidth * this.m_iRegionTableHeight), String.valueOf(i));
                    for (int j = COLLAGE_MODE; j < this.m_CollageInfoGroup.GetCollageNumbers(); j += BUSINESS_CARD_MODE) {
                        if (i == this.m_CollageInfoGroup.GetCollageInfo(j).GetColorSelect()) {
                            this.LOG.m384e("KaKa get", String.valueOf(i));
                            ((DrawView) this.m_DrawViewManager.get(j)).setEnabled(true);
                            if (this.m_iLastSelectDrawView != j) {
                                this.m_iLastSelectDrawView = j;
                                this.LOG.m384e("Touch", String.valueOf(j));
                                if (HaveCollageViewTouchListener()) {
                                    this.m_CollageViewTouchListener.onCollageViewTouch((View) this.m_DrawViewManager.get(j));
                                }
                            }
                            this.m_iLastSelectDrawView = j;
                            if (!this.m_boEnableDrawView) {
                                ((DrawView) this.m_DrawViewManager.get(j)).setEnabled(false);
                            }
                        } else {
                            ((DrawView) this.m_DrawViewManager.get(j)).setEnabled(false);
                        }
                    }
                    break;
                }
        }
        return super.dispatchTouchEvent(event);
    }

    public BitmapMonitorResult GetCollageBitmap() {
        BitmapMonitorResult bmr;
        InputStream is;
        if (this.m_CollageInfoGroup.GetBackgroundPhotoPath().equals(XmlPullParser.NO_NAMESPACE)) {
            bmr = BitmapMonitor.CreateBitmap(this.m_CollageInfoGroup.GetPhotoWidth(), this.m_CollageInfoGroup.GetPhotoHeight(), Config.ARGB_8888);
        } else {
            try {
                if (this.m_CollageInfoGroup.GetResourceFromWhere() == 0) {
                    is = this.m_Context.getAssets().open(this.m_CollageInfoGroup.GetBackgroundPhotoPath());
                } else {
                    is = new FileInputStream(this.m_CollageInfoGroup.GetBackgroundPhotoPath());
                }
                bmr = BitmapMonitor.CreateBitmap(is, true);
            } catch (IOException e) {
                bmr = new BitmapMonitorResult();
                bmr.SetResult(97);
                e.printStackTrace();
            }
        }
        if (!bmr.IsSuccess()) {
            return bmr;
        }
        Canvas canvas = new Canvas(bmr.GetBitmap());
        if (this.m_CollageInfoGroup.GetBackgroundPhotoPath().equals(XmlPullParser.NO_NAMESPACE)) {
            canvas.drawColor(-1);
        }
        DrawEditPhoto(canvas);
        if (!this.m_CollageInfoGroup.GetForegroundPhotoPath().equals(XmlPullParser.NO_NAMESPACE)) {
            try {
                if (this.m_CollageInfoGroup.GetResourceFromWhere() == 0) {
                    is = this.m_Context.getAssets().open(this.m_CollageInfoGroup.GetForegroundPhotoPath());
                } else {
                    is = new FileInputStream(this.m_CollageInfoGroup.GetForegroundPhotoPath());
                }
                BitmapMonitorResult foregroundBmr = BitmapMonitor.CreateBitmap(is, true);
                if (!foregroundBmr.IsSuccess()) {
                    return foregroundBmr;
                }
                canvas.drawBitmap(foregroundBmr.GetBitmap(), 0.0f, 0.0f, null);
                foregroundBmr.GetBitmap().recycle();
            } catch (IOException e2) {
                bmr = new BitmapMonitorResult();
                bmr.SetResult(97);
                e2.printStackTrace();
            }
        }
        return bmr;
    }

    public BitmapMonitorResult GetCollageBitmap(int iWidth, int iHeight) {
        BitmapMonitorResult bmr;
        InputStream is;
        if (this.m_CollageInfoGroup.GetBackgroundPhotoPath().equals(XmlPullParser.NO_NAMESPACE)) {
            bmr = BitmapMonitor.CreateBitmap(this.m_CollageInfoGroup.GetPhotoWidth(), this.m_CollageInfoGroup.GetPhotoHeight(), Config.ARGB_8888);
        } else {
            try {
                if (this.m_CollageInfoGroup.GetResourceFromWhere() == 0) {
                    is = this.m_Context.getAssets().open(this.m_CollageInfoGroup.GetBackgroundPhotoPath());
                } else {
                    is = new FileInputStream(this.m_CollageInfoGroup.GetBackgroundPhotoPath());
                }
                bmr = BitmapMonitor.CreateBitmap(is, true);
            } catch (IOException e) {
                bmr = new BitmapMonitorResult();
                bmr.SetResult(97);
                e.printStackTrace();
            }
        }
        if (!bmr.IsSuccess()) {
            return bmr;
        }
        Canvas canvas = new Canvas(bmr.GetBitmap());
        if (this.m_CollageInfoGroup.GetBackgroundPhotoPath().equals(XmlPullParser.NO_NAMESPACE)) {
            canvas.drawColor(-1);
        }
        DrawEditPhoto(canvas);
        if (!this.m_CollageInfoGroup.GetForegroundPhotoPath().equals(XmlPullParser.NO_NAMESPACE)) {
            try {
                if (this.m_CollageInfoGroup.GetResourceFromWhere() == 0) {
                    is = this.m_Context.getAssets().open(this.m_CollageInfoGroup.GetForegroundPhotoPath());
                } else {
                    is = new FileInputStream(this.m_CollageInfoGroup.GetForegroundPhotoPath());
                }
                BitmapMonitorResult foregroundBmr = BitmapMonitor.CreateBitmap(is, true);
                if (!foregroundBmr.IsSuccess()) {
                    return foregroundBmr;
                }
                canvas.drawBitmap(foregroundBmr.GetBitmap(), 0.0f, 0.0f, null);
                foregroundBmr.GetBitmap().recycle();
            } catch (IOException e2) {
                bmr = new BitmapMonitorResult();
                bmr.SetResult(97);
                e2.printStackTrace();
            }
        }
        if (this.m_CollageInfoGroup.GetPhotoWidth() == iWidth && this.m_CollageInfoGroup.GetPhotoHeight() == iHeight) {
            return bmr;
        }
        BitmapMonitorResult retBmr = BitmapMonitor.CreateBitmap(iWidth, iHeight, Config.ARGB_8888);
        if (!retBmr.IsSuccess()) {
            return retBmr;
        }
        Bitmap retBmp = retBmr.GetBitmap();
        retBmp.eraseColor(-1);
        Canvas retCanvas = new Canvas(retBmp);
        Paint retPaint = new Paint();
        retPaint.setAntiAlias(true);
        retPaint.setFilterBitmap(true);
        float height = (float) ((retBmr.GetBitmap().getHeight() - bmr.GetBitmap().getHeight()) / GREETING_CARD_MODE);
        retCanvas.drawBitmap(bmr.GetBitmap(), (float) ((retBmr.GetBitmap().getWidth() - bmr.GetBitmap().getWidth()) / GREETING_CARD_MODE), height, retPaint);
        bmr.GetBitmap().recycle();
        return retBmr;
    }

    private int DrawEditPhoto(Canvas canvas) {
        int i = COLLAGE_MODE;
        while (i < this.m_DrawViewManager.size()) {
            try {
                InputStream is;
                if (this.m_CollageInfoGroup.GetResourceFromWhere() == 0) {
                    is = this.m_Context.getAssets().open(this.m_CollageInfoGroup.GetCollageInfo(i).GetMaskPath());
                } else {
                    is = new FileInputStream(this.m_CollageInfoGroup.GetCollageInfo(i).GetMaskPath());
                }
                BitmapMonitorResult maskBmr = BitmapMonitor.CreateBitmap(is, false);
                if (!maskBmr.IsSuccess()) {
                    return maskBmr.GetResult();
                }
                BitmapMonitorResult editPhotoBmr = ((DrawView) this.m_DrawViewManager.get(i)).GetPhotoWithDrawViewMask(BUSINESS_CARD_MODE, maskBmr.GetBitmap(), (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth(), (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight(), false);
                if (!editPhotoBmr.IsSuccess()) {
                    return editPhotoBmr.GetResult();
                }
                maskBmr.GetBitmap().recycle();
                this.LOG.m384e("Collage w", String.valueOf(editPhotoBmr.GetBitmap().getWidth()));
                this.LOG.m384e("Collage h", String.valueOf(editPhotoBmr.GetBitmap().getHeight()));
                canvas.drawBitmap(editPhotoBmr.GetBitmap(), this.m_CollageInfoGroup.GetCollageInfo(i).GetLeft(), this.m_CollageInfoGroup.GetCollageInfo(i).GetTop(), null);
                editPhotoBmr.GetBitmap().recycle();
                i += BUSINESS_CARD_MODE;
            } catch (IOException e) {
                e.printStackTrace();
                return 97;
            }
        }
        return COLLAGE_MODE;
    }

    public BitmapMonitorResult GetCollageGSMaskBitmap() {
        BitmapMonitorResult bmr;
        if (this.m_CollageInfoGroup.GetMetalPhotoPath().equals(XmlPullParser.NO_NAMESPACE)) {
            bmr = BitmapMonitor.CreateBitmap(this.m_CollageInfoGroup.GetPhotoWidth(), this.m_CollageInfoGroup.GetPhotoHeight(), Config.ARGB_8888);
            bmr.GetBitmap().eraseColor(COLLAGE_MODE);
        } else {
            try {
                InputStream is;
                if (this.m_CollageInfoGroup.GetResourceFromWhere() == 0) {
                    is = this.m_Context.getAssets().open(this.m_CollageInfoGroup.GetMetalPhotoPath());
                } else {
                    is = new FileInputStream(this.m_CollageInfoGroup.GetMetalPhotoPath());
                }
                bmr = BitmapMonitor.CreateBitmap(is, true);
            } catch (IOException e) {
                bmr = new BitmapMonitorResult();
                bmr.SetResult(97);
                e.printStackTrace();
            }
        }
        if (bmr.IsSuccess()) {
            DrawGSMask(new Canvas(bmr.GetBitmap()));
        }
        return bmr;
    }

    private int DrawGSMask(Canvas canvas) {
        int i = COLLAGE_MODE;
        while (i < this.m_DrawViewManager.size()) {
            try {
                if (((DrawView) this.m_DrawViewManager.get(i)).HaveGSGarnish()) {
                    InputStream is;
                    if (this.m_CollageInfoGroup.GetResourceFromWhere() == 0) {
                        is = this.m_Context.getAssets().open(this.m_CollageInfoGroup.GetCollageInfo(i).GetMaskPath());
                    } else {
                        is = new FileInputStream(this.m_CollageInfoGroup.GetCollageInfo(i).GetMaskPath());
                    }
                    BitmapMonitorResult maskBmr = BitmapMonitor.CreateBitmap(is, false);
                    if (!maskBmr.IsSuccess()) {
                        return maskBmr.GetResult();
                    }
                    BitmapMonitorResult GSMaskBmr = ((DrawView) this.m_DrawViewManager.get(i)).GetPhotoWithDrawViewMask(7, maskBmr.GetBitmap(), (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth(), (int) this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight(), false);
                    if (!GSMaskBmr.IsSuccess()) {
                        return GSMaskBmr.GetResult();
                    }
                    maskBmr.GetBitmap().recycle();
                    canvas.drawBitmap(GSMaskBmr.GetBitmap(), this.m_CollageInfoGroup.GetCollageInfo(i).GetLeft(), this.m_CollageInfoGroup.GetCollageInfo(i).GetTop(), null);
                    GSMaskBmr.GetBitmap().recycle();
                }
                i += BUSINESS_CARD_MODE;
            } catch (IOException e) {
                e.printStackTrace();
                return 97;
            }
        }
        return COLLAGE_MODE;
    }

    public boolean HaveGSMask() {
        boolean boRet = false;
        if (!(this.m_CollageInfoGroup.GetMetalPhotoPath() == null || this.m_CollageInfoGroup.GetMetalPhotoPath().equals(XmlPullParser.NO_NAMESPACE))) {
            boRet = true;
        }
        for (int i = COLLAGE_MODE; i < this.m_DrawViewManager.size(); i += BUSINESS_CARD_MODE) {
            if (((DrawView) this.m_DrawViewManager.get(i)).HaveGSGarnish()) {
                return true;
            }
        }
        return boRet;
    }

    public int GetDrawViewNumbers() {
        if (this.m_DrawViewManager == null) {
            return COLLAGE_MODE;
        }
        return this.m_DrawViewManager.size();
    }

    public DrawView GetDrawView(int iIndex) {
        if (this.m_DrawViewManager != null && iIndex < this.m_DrawViewManager.size() && iIndex >= 0) {
            return (DrawView) this.m_DrawViewManager.get(iIndex);
        }
        return null;
    }

    public boolean SaveGarnish2XML(ArrayList<String> collageLastEditPhotoXMLs) {
        if (collageLastEditPhotoXMLs.size() != this.m_DrawViewManager.size()) {
            return false;
        }
        for (int i = COLLAGE_MODE; i < this.m_DrawViewManager.size(); i += BUSINESS_CARD_MODE) {
            this.LOG.m383d("CollageView " + ((String) collageLastEditPhotoXMLs.get(i)), " (" + i + ") SaveGarnish2XML " + ((DrawView) this.m_DrawViewManager.get(i)).SaveGarnish2XML((String) collageLastEditPhotoXMLs.get(i)));
        }
        return true;
    }

    public void SetPhotoActionConnection(boolean boPhotoActionConnection) {
        this.m_boPhotoActionConnection = boPhotoActionConnection;
    }

    public boolean IsPhotoActionConnection() {
        return this.m_boPhotoActionConnection;
    }

    private boolean IsSameCollageInfoGroup(int iIndex, int iSameIndex) {
        boolean boRet = false;
        if (iIndex < 0 || iSameIndex < 0) {
            return COLLAGE_MODE;
        }
        if (iIndex >= this.m_CollageInfoGroup.GetCollageNumbers() || iSameIndex >= this.m_CollageInfoGroup.GetCollageNumbers()) {
            return COLLAGE_MODE;
        }
        if (this.m_CollageInfoGroup.GetCollageInfo(iIndex).GetGroup() == this.m_CollageInfoGroup.GetCollageInfo(iSameIndex).GetGroup()) {
            boRet = true;
        }
        return boRet;
    }

    private boolean IsValidButtonPosition(float x, float y) {
        if (x < CollageInfo.ATTR_VALUE_NO_BUTTON_POSITION + 1.0f || y < CollageInfo.ATTR_VALUE_NO_BUTTON_POSITION + 1.0f) {
            return false;
        }
        return true;
    }

    private RectF GetGroupRect(int iCurrent, float fOffset) {
        if (iCurrent >= this.m_CollageInfoGroup.GetCollageNumbers()) {
            return null;
        }
        float fLeft = -1.0f;
        float fTop = -1.0f;
        float fRight = -1.0f;
        float fBottom = -1.0f;
        for (int i = COLLAGE_MODE; i < this.m_DrawViewManager.size(); i += BUSINESS_CARD_MODE) {
            if (IsSameCollageInfoGroup(iCurrent, i)) {
                if (fLeft == -1.0f && fTop == -1.0f) {
                    fLeft = this.m_CollageInfoGroup.GetCollageInfo(i).GetLeft() / this.m_fViewScale;
                    fTop = this.m_CollageInfoGroup.GetCollageInfo(i).GetTop() / this.m_fViewScale;
                    fRight = (this.m_CollageInfoGroup.GetCollageInfo(i).GetLeft() + this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth()) / this.m_fViewScale;
                    fBottom = (this.m_CollageInfoGroup.GetCollageInfo(i).GetTop() + this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight()) / this.m_fViewScale;
                }
                float fCompareValue = this.m_CollageInfoGroup.GetCollageInfo(i).GetLeft() / this.m_fViewScale;
                if (fLeft > fCompareValue) {
                    fLeft = fCompareValue;
                }
                fCompareValue = this.m_CollageInfoGroup.GetCollageInfo(i).GetTop() / this.m_fViewScale;
                if (fTop > fCompareValue) {
                    fTop = fCompareValue;
                }
                fCompareValue = (this.m_CollageInfoGroup.GetCollageInfo(i).GetLeft() + this.m_CollageInfoGroup.GetCollageInfo(i).GetWidth()) / this.m_fViewScale;
                if (fRight < fCompareValue) {
                    fRight = fCompareValue;
                }
                fCompareValue = (this.m_CollageInfoGroup.GetCollageInfo(i).GetTop() + this.m_CollageInfoGroup.GetCollageInfo(i).GetHeight()) / this.m_fViewScale;
                if (fBottom < fCompareValue) {
                    fBottom = fCompareValue;
                }
            }
        }
        return new RectF(fLeft - fOffset, fTop - fOffset, fRight + fOffset, fBottom + fOffset);
    }

    public void DrawCollageFoucs(int iCurrent) {
        if (iCurrent < this.m_CollageInfoGroup.GetCollageNumbers() && this.m_CollageFocusBitmap != null) {
            Canvas canvas = new Canvas(this.m_CollageFocusBitmap);
            this.m_CollageFocusBitmap.eraseColor(COLLAGE_MODE);
            canvas.drawRect(GetGroupRect(iCurrent, this.m_FoucsCollagePaint.getStrokeWidth() / 2.0f), this.m_FoucsCollagePaint);
            this.m_CollageFocusImageView.invalidate();
        }
    }

    public void SetFocusCollagePaint(Paint paint) {
        this.m_FoucsCollagePaint = paint;
    }
}
