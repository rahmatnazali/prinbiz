package com.hiti.ui.drawview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.net.Uri;
import android.support.v4.view.InputDeviceCompat;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import com.hiti.ImageFilter.ImageFilter;
import com.hiti.ImageFilter.ImageFilter.IMAGE_FILTER_TYPE;
import com.hiti.ImageFilter.ImageFilterPlus;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.ui.drawview.garnishitem.FilterColorValue;
import com.hiti.ui.drawview.garnishitem.GarnishItem;
import com.hiti.ui.drawview.garnishitem.LayerManager.GarnishItemLayerManager;
import com.hiti.ui.drawview.garnishitem.history.GarnishItemHistoryManager;
import com.hiti.ui.drawview.garnishitem.parser.GarnishItemXMLCreator;
import com.hiti.utility.LogManager;
import com.hiti.utility.MathUtility;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.apache.commons.net.telnet.TelnetOption;
import org.xmlpull.v1.XmlPullParser;

public class DrawView extends View {
    public static final int BORDER_STATE = 99;
    public static final int BRUSH_STATE = 96;
    private static final int BRUSH_TOUCH_MODE = 7;
    public static final int COLOR_GARNISH_STATE = 97;
    private static final int COPY_TOUCH_MODE = 9;
    private static final int DRAG_TOUCH_MODE = 1;
    public static final int EDIT_PHOTO_LAYER = 1;
    public static final int FILTER_STATE = 94;
    public static final int GS_GARNISH_STATE = 98;
    public static final int ID_PHOTO_STATE = 93;
    private static final int NONE_TOUCH_MODE = 0;
    public static final int NONTHING_STATE = 100;
    private static final int PAN_TOUCH_MODE = 4;
    private static final int PHOTO_DRAG_TOUCH_MODE = 6;
    private static final int PHOTO_ROTATE_TOUCH_MODE = 5;
    public static final int ROLLER_STATE = 95;
    private static final int ROLLER_TOUCH_MODE = 8;
    private static final int SCALE_TOUCH_MODE = 2;
    private static final int ZOOM_TOUCH_MODE = 3;
    private final int EDIT_PHOTO_PAINT_SIZE;
    private final int EDIT_PHOTO_UPRIGHT_PAINT_SIZE;
    private final int FOUCS_PAINT_SIZE;
    private LogManager LOG;
    private int R_COLOR_EDIT_PHOTO_MASK_COLOR;
    private int R_COLOR_MASK_ORG_COLOR;
    private int R_COLOR_MASK_ZOOM_COLOR;
    private int R_DRAWABLE_zoom;
    private Paint m_AlphaPaint;
    private float m_BoundX;
    private float m_BoundY;
    private BrushRegionGroup m_BrushManager;
    private Paint m_CanvasFocusPaint;
    private PaintFlagsDrawFilter m_CanvasPaintFlagsDrawFilter;
    private Bitmap m_CanvasRegionViewMask;
    private Context m_Context;
    private DrawViewListener m_DrawViewListener;
    private Paint m_EditPhotoLinePaint;
    private Paint m_EditPhotoPaint;
    private ScaleGestureDetector m_EditPhotoScaleDetector;
    private Paint m_EditPhotoUprightLinePaint;
    private GarnishItem m_FocusGarnish;
    private Matrix m_FocusMatrix;
    private Paint m_FocusPaint;
    private Path m_FocusPath;
    private RectF m_FocusRectF;
    private Bitmap m_FocusScaleBitmap;
    private GestureDetector m_GarnishGestureDetector;
    private GarnishItemHistoryManager m_GarnishItemHistoryManager;
    private ArrayList<GarnishItem> m_GarnishManager;
    private ArrayList<GarnishItem> m_LastGarnishGroup;
    private Object m_Lock;
    private GarnishItem m_MaskGarnishOrgItem;
    private GarnishItem m_MaskGarnishZoomItem;
    private Bitmap m_RegionViewMask;
    private Paint m_RegionViewMaskPaint;
    private RollerRegionGroup m_RollerManager;
    private int m_StateMode;
    private int m_TouchMode;
    private float m_Zoom;
    private ScaleGestureDetector m_ZoomDetector;
    private boolean m_boEdit;
    private boolean m_boMirror;
    private boolean m_boVertical;
    private float m_fEditAreaHeight;
    private float m_fEditAreaWidth;
    private float m_fHeight;
    private float m_fLastX;
    private float m_fLastY;
    private float m_fNewDiagonal;
    private float m_fORGDiagonal;
    private float m_fOrgDegree;
    private float m_fPrevX;
    private float m_fPrevY;
    private float m_fViewScale;
    private float m_fWidth;
    private int m_iAdjustRotate;
    private int m_iOutputDefaultMaxLong;
    private int m_iOutputDefaultMaxShort;

    private class EditPhotoScaleGestureListener implements OnScaleGestureListener {
        private EditPhotoScaleGestureListener() {
        }

        public boolean onScale(ScaleGestureDetector detector) {
            float scale = detector.getScaleFactor();
            GarnishItem garnishItem = DrawView.this.SearchGarnishFromFirst((int) DrawView.EDIT_PHOTO_LAYER);
            if (garnishItem == null) {
                return true;
            }
            float fEditPhotoScale = garnishItem.GetScale();
            GarnishItem maskGarnishItem = null;
            for (int i = DrawView.this.m_GarnishManager.size() - 1; i >= 0; i--) {
                if (((GarnishItem) DrawView.this.m_GarnishManager.get(i)).GetType() == DrawView.BRUSH_TOUCH_MODE) {
                    maskGarnishItem = (GarnishItem) DrawView.this.m_GarnishManager.get(i);
                }
            }
            if (fEditPhotoScale * scale < 0.1f && ((double) (Math.abs(fEditPhotoScale - 0.1f) / Math.max(Math.abs(fEditPhotoScale), Math.abs(0.1f)))) < 1.0E-6d) {
                return false;
            }
            if (fEditPhotoScale * scale > 10.0f && ((double) (Math.abs(fEditPhotoScale - 10.0f) / Math.max(Math.abs(fEditPhotoScale), Math.abs(10.0f)))) < 1.0E-6d) {
                return false;
            }
            fEditPhotoScale = Math.max(0.1f, Math.min(fEditPhotoScale * scale, 10.0f));
            float centerX = detector.getFocusX();
            float diffX = centerX - garnishItem.GetX();
            float diffY = detector.getFocusY() - garnishItem.GetY();
            DrawView.this.AddTrans(garnishItem, -((diffX * scale) - diffX), -((diffY * scale) - diffY));
            garnishItem.SetScaleMatrix(fEditPhotoScale, fEditPhotoScale);
            garnishItem.SetScale(fEditPhotoScale);
            if (maskGarnishItem != null) {
                DrawView.this.SetTrans(maskGarnishItem, garnishItem.GetX(), garnishItem.GetY());
                maskGarnishItem.SetScaleMatrix(fEditPhotoScale, fEditPhotoScale);
                maskGarnishItem.SetScale(fEditPhotoScale);
            }
            DrawView.this.invalidate();
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
            DrawView.this.m_fLastX = detector.getFocusX();
            DrawView.this.m_fLastY = detector.getFocusY();
        }
    }

    private class GarnishGestureListener extends SimpleOnGestureListener {
        private GarnishGestureListener() {
        }

        public boolean onDown(MotionEvent event) {
            return DrawView.this.onGarnishModePressDown(event);
        }

        public boolean onSingleTapUp(MotionEvent event) {
            return DrawView.this.onGarnishModeSingleTapUp(event);
        }

        public boolean onScroll(MotionEvent e1, MotionEvent event, float distanceX, float distanceY) {
            return DrawView.this.onGarnishModeScroll(e1, event, distanceX, distanceY);
        }

        public void onLongPress(MotionEvent event) {
            DrawView.this.onGarnishModeLongPress(event);
        }
    }

    private class ZoomGestureListener implements OnScaleGestureListener {
        private ZoomGestureListener() {
        }

        public boolean onScale(ScaleGestureDetector detector) {
            DrawView.this.m_TouchMode = DrawView.ZOOM_TOUCH_MODE;
            float scale = detector.getScaleFactor();
            if (DrawView.this.m_Zoom * scale < 1.0f && ((double) (Math.abs(DrawView.this.m_Zoom - 1.0f) / Math.max(Math.abs(DrawView.this.m_Zoom), Math.abs(1.0f)))) < 1.0E-6d) {
                return false;
            }
            if (DrawView.this.m_Zoom * scale > 10.0f && ((double) (Math.abs(DrawView.this.m_Zoom - 10.0f) / Math.max(Math.abs(DrawView.this.m_Zoom), Math.abs(10.0f)))) < 1.0E-6d) {
                return false;
            }
            if (DrawView.this.m_Zoom * scale < 1.0f) {
                scale = 1.0f / DrawView.this.m_Zoom;
            } else if (DrawView.this.m_Zoom * scale > 10.0f) {
                scale = 10.0f / DrawView.this.m_Zoom;
            }
            DrawView.this.m_Zoom = DrawView.this.m_Zoom * scale;
            DrawView.this.LOG.m385i("m_Zoom", String.valueOf(DrawView.this.m_Zoom));
            float centerX = detector.getFocusX();
            float centerY = detector.getFocusY();
            PointF fOffsetBound = DrawView.this.GetBoundOffset(centerX, centerY, scale, DrawView.this.m_Zoom);
            Iterator it = DrawView.this.m_GarnishManager.iterator();
            while (it.hasNext()) {
                DrawView.this.SetZoom((GarnishItem) it.next(), DrawView.this.m_Zoom, scale, centerX, centerY, fOffsetBound);
            }
            DrawView.this.m_GarnishItemHistoryManager.SetZoom(DrawView.this.m_Zoom, scale, centerX, centerY, fOffsetBound);
            it = DrawView.this.m_LastGarnishGroup.iterator();
            while (it.hasNext()) {
                DrawView.this.SetZoom((GarnishItem) it.next(), DrawView.this.m_Zoom, scale, centerX, centerY, fOffsetBound);
            }
            if (DrawView.this.m_StateMode == DrawView.BRUSH_STATE) {
                DrawView.this.m_BrushManager.SetZoom(DrawView.this.m_Zoom, scale, centerX, centerY, fOffsetBound);
            }
            if (DrawView.this.m_StateMode == DrawView.ROLLER_STATE) {
                DrawView.this.m_RollerManager.SetZoom(DrawView.this.m_Zoom, scale, centerX, centerY, fOffsetBound);
            }
            if (((double) (Math.abs(DrawView.this.m_Zoom - 1.0f) / Math.max(Math.abs(DrawView.this.m_Zoom), Math.abs(1.0f)))) < 1.0E-6d) {
                DrawView.this.ResetViewMask();
            }
            DrawView.this.invalidate();
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
            DrawView.this.m_fLastX = detector.getFocusX();
            DrawView.this.m_fLastY = detector.getFocusY();
        }
    }

    public DrawView(Context context) {
        super(context);
        this.m_EditPhotoPaint = new Paint();
        this.m_EditPhotoLinePaint = new Paint();
        this.m_EditPhotoUprightLinePaint = new Paint();
        this.EDIT_PHOTO_PAINT_SIZE = SCALE_TOUCH_MODE;
        this.EDIT_PHOTO_UPRIGHT_PAINT_SIZE = ZOOM_TOUCH_MODE;
        this.m_FocusGarnish = null;
        this.m_LastGarnishGroup = new ArrayList();
        this.m_FocusPaint = new Paint();
        this.m_FocusRectF = new RectF();
        this.m_FocusMatrix = new Matrix();
        this.m_FocusPath = new Path();
        this.m_FocusScaleBitmap = null;
        this.FOUCS_PAINT_SIZE = SCALE_TOUCH_MODE;
        this.m_CanvasPaintFlagsDrawFilter = null;
        this.m_CanvasFocusPaint = new Paint();
        this.m_AlphaPaint = null;
        this.m_boMirror = false;
        this.m_TouchMode = NONE_TOUCH_MODE;
        this.m_StateMode = BORDER_STATE;
        this.m_boEdit = false;
        this.m_MaskGarnishZoomItem = null;
        this.m_MaskGarnishOrgItem = null;
        this.m_RegionViewMask = null;
        this.m_CanvasRegionViewMask = null;
        this.m_RegionViewMaskPaint = new Paint();
        this.m_fLastX = 0.0f;
        this.m_fLastY = 0.0f;
        this.m_fPrevX = 0.0f;
        this.m_fPrevY = 0.0f;
        this.m_fORGDiagonal = 0.0f;
        this.m_fNewDiagonal = 0.0f;
        this.m_fOrgDegree = 0.0f;
        this.m_fEditAreaWidth = 0.0f;
        this.m_fEditAreaHeight = 0.0f;
        this.m_fWidth = 0.0f;
        this.m_fHeight = 0.0f;
        this.m_BoundX = 0.0f;
        this.m_BoundY = 0.0f;
        this.m_Zoom = 1.0f;
        this.m_fViewScale = 1.0f;
        this.m_boVertical = true;
        this.m_iAdjustRotate = ZOOM_TOUCH_MODE;
        this.m_GarnishManager = new ArrayList();
        this.m_GarnishItemHistoryManager = null;
        this.m_BrushManager = null;
        this.m_RollerManager = null;
        this.m_DrawViewListener = null;
        this.m_iOutputDefaultMaxShort = NONE_TOUCH_MODE;
        this.m_iOutputDefaultMaxLong = NONE_TOUCH_MODE;
        this.LOG = null;
        this.m_Lock = new Object();
        this.R_DRAWABLE_zoom = NONE_TOUCH_MODE;
        this.R_COLOR_EDIT_PHOTO_MASK_COLOR = NONE_TOUCH_MODE;
        this.R_COLOR_MASK_ORG_COLOR = NONE_TOUCH_MODE;
        this.R_COLOR_MASK_ZOOM_COLOR = NONE_TOUCH_MODE;
        this.m_Context = context;
        GetResourceID(context);
        this.LOG = new LogManager(NONE_TOUCH_MODE);
        this.m_GarnishItemHistoryManager = new GarnishItemHistoryManager(context);
        setBackgroundColor(NONE_TOUCH_MODE);
    }

    private void GetResourceID(Context context) {
        this.R_DRAWABLE_zoom = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "zoom");
        this.R_COLOR_MASK_ORG_COLOR = ResourceSearcher.getId(context, RS_TYPE.COLOR, "MASK_ORG_COLOR");
        this.R_COLOR_MASK_ZOOM_COLOR = ResourceSearcher.getId(context, RS_TYPE.COLOR, "MASK_ZOOM_COLOR");
        this.R_COLOR_EDIT_PHOTO_MASK_COLOR = ResourceSearcher.getId(context, RS_TYPE.COLOR, "EDIT_PHOTO_MASK_COLOR");
    }

    public void InitMirror() {
        this.m_AlphaPaint = new Paint();
        this.m_AlphaPaint.setStyle(Style.STROKE);
        this.m_AlphaPaint.setColor(-1);
        this.m_AlphaPaint.setAlpha(80);
        this.m_AlphaPaint.setStyle(Style.FILL);
        this.m_boMirror = true;
    }

    public int InitDrawView(float fEditAreaWidth, float fEditAreaHeight, float fWidth, float fHeight, boolean boVertical, int iOutputDefaultMaxShort, int iOutputDefaultMaxLong) {
        this.LOG.m385i("InitDrawView", "InitDrawView");
        this.m_ZoomDetector = new ScaleGestureDetector(this.m_Context, new ZoomGestureListener());
        this.m_GarnishGestureDetector = new GestureDetector(this.m_Context, new GarnishGestureListener());
        this.m_EditPhotoScaleDetector = new ScaleGestureDetector(this.m_Context, new EditPhotoScaleGestureListener());
        this.m_CanvasPaintFlagsDrawFilter = new PaintFlagsDrawFilter(NONE_TOUCH_MODE, ZOOM_TOUCH_MODE);
        this.m_CanvasFocusPaint.setAntiAlias(true);
        this.m_fEditAreaWidth = fEditAreaWidth;
        this.m_fEditAreaHeight = fEditAreaHeight;
        this.m_fWidth = fWidth;
        this.m_fHeight = fHeight;
        this.m_BrushManager = new BrushRegionGroup(fEditAreaWidth / 2.0f, fEditAreaHeight / 2.0f, fEditAreaWidth, fEditAreaHeight);
        this.m_RollerManager = new RollerRegionGroup(fEditAreaWidth / 2.0f, fEditAreaHeight / 2.0f, fEditAreaWidth, fEditAreaHeight);
        this.m_BoundX = (this.m_fWidth - this.m_fEditAreaWidth) / 2.0f;
        this.m_BoundY = (this.m_fHeight - this.m_fEditAreaHeight) / 2.0f;
        this.m_FocusPaint.reset();
        this.m_FocusPaint.setAntiAlias(true);
        this.m_FocusPaint.setStyle(Style.STROKE);
        this.m_FocusPaint.setColor(-1);
        float px = TypedValue.applyDimension(EDIT_PHOTO_LAYER, 2.0f, getResources().getDisplayMetrics());
        this.m_FocusPaint.setStrokeWidth(px);
        this.m_FocusPaint.setShadowLayer(px, px / 2.0f, px / 2.0f, -12303292);
        InputStream is = getResources().openRawResource(this.R_DRAWABLE_zoom);
        if (this.m_FocusScaleBitmap != null) {
            this.m_FocusScaleBitmap.recycle();
        }
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(is, false);
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        this.m_FocusScaleBitmap = bmr.GetBitmap();
        this.m_EditPhotoLinePaint.reset();
        this.m_EditPhotoLinePaint.setAntiAlias(true);
        this.m_EditPhotoLinePaint.setStyle(Style.STROKE);
        this.m_EditPhotoLinePaint.setColor(-1);
        this.m_EditPhotoLinePaint.setStrokeWidth(2.0f);
        this.m_EditPhotoUprightLinePaint.reset();
        this.m_EditPhotoUprightLinePaint.setAntiAlias(true);
        this.m_EditPhotoUprightLinePaint.setStyle(Style.STROKE);
        this.m_EditPhotoUprightLinePaint.setColor(InputDeviceCompat.SOURCE_ANY);
        this.m_EditPhotoUprightLinePaint.setStrokeWidth(3.0f);
        this.m_EditPhotoPaint.reset();
        this.m_EditPhotoPaint.setAntiAlias(true);
        this.m_EditPhotoPaint.setStyle(Style.FILL);
        this.m_EditPhotoPaint.setColor(getResources().getColor(this.R_COLOR_EDIT_PHOTO_MASK_COLOR));
        this.m_EditPhotoPaint.setStrokeWidth(2.0f);
        this.m_boVertical = boVertical;
        SetOutputDefaultMaxLimit(iOutputDefaultMaxShort, iOutputDefaultMaxLong);
        return AddMaskGarnish();
    }

    public float GetWidth() {
        return this.m_fWidth;
    }

    public float GetHeight() {
        return this.m_fHeight;
    }

    public float GetViewWindowCenterX() {
        return this.m_fWidth / 2.0f;
    }

    public float GetViewWindowCenterY() {
        return this.m_fHeight / 2.0f;
    }

    public float GetEditAreaWidth() {
        return this.m_fEditAreaWidth;
    }

    public float GetEditAreaHeight() {
        return this.m_fEditAreaHeight;
    }

    public float GetBoundX() {
        return this.m_BoundX;
    }

    public float GetBoundY() {
        return this.m_BoundY;
    }

    public boolean IsVertical() {
        return this.m_boVertical;
    }

    public void SetDrawViewListener(DrawViewListener drawViewListener) {
        this.m_DrawViewListener = drawViewListener;
    }

    public boolean HaveDrawViewListener() {
        if (this.m_DrawViewListener != null) {
            return true;
        }
        return false;
    }

    public void SetMode(int iMode) {
        this.m_StateMode = iMode;
        this.m_FocusGarnish = null;
        if (HaveDrawViewListener()) {
            this.m_DrawViewListener.OnMissFocusGarnish();
        }
        invalidate();
    }

    public int GetMode() {
        return this.m_StateMode;
    }

    public void SetViewScale(float ViewScale) {
        if (ViewScale != 0.0f) {
            this.m_fViewScale = ViewScale;
        }
    }

    private boolean onGarnishModePressDown(MotionEvent event) {
        if (this.m_FocusGarnish != null) {
            GarnishItem searchGarnishItem = SearchFocusGarnishItem(event.getX(), event.getY(), true);
            GarnishItem cGarnishItem;
            if (searchGarnishItem == null || this.m_FocusGarnish.GetID() != searchGarnishItem.GetID()) {
                searchGarnishItem = SearchFocusGarnishItem(event.getX(), event.getY(), false);
                if (!(searchGarnishItem == null || this.m_FocusGarnish == null || searchGarnishItem.GetID() != this.m_FocusGarnish.GetID())) {
                    this.m_TouchMode = EDIT_PHOTO_LAYER;
                    this.LOG.m385i("ENTER", "DRAG_TOUCH_MODE");
                    this.m_fLastX = event.getX();
                    this.m_fLastY = event.getY();
                    this.m_FocusGarnish = searchGarnishItem;
                    if (HaveDrawViewListener()) {
                        this.m_DrawViewListener.OnFocusGarnish();
                    }
                    MoveFocusGarnishItemToTop(this.m_FocusGarnish);
                    this.m_GarnishItemHistoryManager.EditAction(this.m_FocusGarnish);
                    cGarnishItem = GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish);
                    SetFocusGarnishCompose(cGarnishItem);
                    SetFocusGarnishCompose_EditAction(cGarnishItem);
                    if (this.m_StateMode == COLOR_GARNISH_STATE && HaveDrawViewListener()) {
                        this.m_DrawViewListener.OnColorGarnishMode();
                    }
                    if (this.m_StateMode == GS_GARNISH_STATE && HaveDrawViewListener()) {
                        this.m_DrawViewListener.OnGSGarnishMode();
                    }
                    return true;
                }
            }
            this.m_TouchMode = SCALE_TOUCH_MODE;
            this.LOG.m385i("ENTER", "SCALE_MODE");
            this.m_fLastX = event.getX();
            this.m_fLastY = event.getY();
            this.m_fPrevX = event.getX();
            this.m_fPrevY = event.getY();
            this.m_fORGDiagonal = MathUtility.Diagonal((float) searchGarnishItem.GetUIBitmap().getWidth(), (float) searchGarnishItem.GetUIBitmap().getHeight());
            this.m_fOrgDegree = searchGarnishItem.GetDegree();
            this.m_FocusGarnish = searchGarnishItem;
            if (HaveDrawViewListener()) {
                this.m_DrawViewListener.OnFocusGarnish();
            }
            MoveFocusGarnishItemToTop(this.m_FocusGarnish);
            this.m_GarnishItemHistoryManager.EditAction(this.m_FocusGarnish);
            cGarnishItem = GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish);
            SetFocusGarnishCompose(cGarnishItem);
            SetFocusGarnishCompose_EditAction(cGarnishItem);
            return true;
        }
        this.LOG.m384e("onGarnishModePressDown", "onGarnishModePressDown");
        if (this.m_FocusGarnish != null) {
            SetLastGarnish(this.m_FocusGarnish);
        }
        this.m_TouchMode = COPY_TOUCH_MODE;
        this.LOG.m385i("ENTER", "COPY_TOUCH_MODE");
        this.m_fLastX = event.getX();
        this.m_fLastY = event.getY();
        return true;
    }

    private boolean onGarnishModeSingleTapUp(MotionEvent event) {
        if (this.m_LastGarnishGroup.size() > 0 && this.m_FocusGarnish == null && this.m_TouchMode == COPY_TOUCH_MODE) {
            AddLastGarnish(this.m_LastGarnishGroup, event);
        } else {
            this.m_FocusGarnish = null;
            if (HaveDrawViewListener()) {
                this.m_DrawViewListener.OnMissFocusGarnish();
            }
        }
        this.m_TouchMode = NONE_TOUCH_MODE;
        invalidate();
        this.LOG.m384e("SingleTapUp", "Leave mode");
        return true;
    }

    private boolean onGarnishModeLongPressSingleTapUp(MotionEvent event) {
        this.LOG.m384e("LongPress SingleTapUp", "Leave mode");
        this.m_TouchMode = NONE_TOUCH_MODE;
        return true;
    }

    private boolean onGarnishModeLongPressScroll(MotionEvent event) {
        if (this.m_TouchMode == EDIT_PHOTO_LAYER && this.m_FocusGarnish != null) {
            float x = event.getX();
            float y = event.getY();
            AddTrans(this.m_FocusGarnish, x - this.m_fLastX, y - this.m_fLastY);
            SetFocusGarnishComposeAction(GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish));
            this.m_fLastX = x;
            this.m_fLastY = y;
        }
        return true;
    }

    private boolean onGarnishModeScroll(MotionEvent e1, MotionEvent event, float distanceX, float distanceY) {
        if (this.m_FocusGarnish == null) {
            this.m_TouchMode = PAN_TOUCH_MODE;
        }
        float x;
        float y;
        float dx;
        float dy;
        if (this.m_TouchMode == EDIT_PHOTO_LAYER || this.m_TouchMode == ZOOM_TOUCH_MODE) {
            if (this.m_FocusGarnish != null) {
                x = event.getX();
                y = event.getY();
                dx = x - this.m_fLastX;
                dy = y - this.m_fLastY;
                AddTrans(this.m_FocusGarnish, dx, dy);
                SetFocusGarnishComposeAction(GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish));
                this.m_fLastX = x;
                this.m_fLastY = y;
            }
        } else if (this.m_TouchMode == PAN_TOUCH_MODE || this.m_TouchMode == COPY_TOUCH_MODE) {
            PointF fOffsetBound = new PointF();
            x = event.getX();
            y = event.getY();
            if (((double) (Math.abs(this.m_Zoom - 1.0f) / Math.max(Math.abs(this.m_Zoom), Math.abs(1.0f)))) < 1.0E-6d) {
                return true;
            }
            dx = x - this.m_fLastX;
            dy = y - this.m_fLastY;
            fOffsetBound = GetBoundOffset(dx, dy, this.m_Zoom);
            for (int i = NONE_TOUCH_MODE; i < this.m_GarnishManager.size(); i += EDIT_PHOTO_LAYER) {
                GarnishItem garnishItem = (GarnishItem) this.m_GarnishManager.get(i);
                AddTrans(garnishItem, dx, dy);
                AddTrans(garnishItem, fOffsetBound.x, fOffsetBound.y);
            }
            this.m_GarnishItemHistoryManager.SetPan(dx, dy, fOffsetBound);
            this.m_fLastX = x;
            this.m_fLastY = y;
        } else if (this.m_TouchMode == SCALE_TOUCH_MODE && this.m_FocusGarnish != null) {
            this.m_fNewDiagonal = MathUtility.Spacing(this.m_FocusGarnish.GetX(), this.m_FocusGarnish.GetY(), event.getX(), event.getY()) * 2.0f;
            SetGarnishScale(this.m_FocusGarnish, (double) (this.m_fNewDiagonal - this.m_fORGDiagonal));
            SetGarnishRotate(this.m_FocusGarnish, this.m_fPrevX, this.m_fPrevY, event.getX(), event.getY());
            SetFocusGarnishComposeAction(GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish));
        }
        invalidate();
        return true;
    }

    private void onGarnishModeLongPress(MotionEvent event) {
        this.LOG.m384e("Longpress detected", "Longpress detected");
        if (this.m_TouchMode != ZOOM_TOUCH_MODE) {
            GarnishItem searchGarnishItem = SearchGarnishItem(event.getX(), event.getY(), false);
            if (searchGarnishItem != null) {
                this.m_TouchMode = EDIT_PHOTO_LAYER;
                this.m_fLastX = event.getX();
                this.m_fLastY = event.getY();
                this.m_FocusGarnish = searchGarnishItem;
                if (HaveDrawViewListener()) {
                    this.m_DrawViewListener.OnFocusGarnish();
                }
                MoveFocusGarnishItemToTop(this.m_FocusGarnish);
                this.m_GarnishItemHistoryManager.EditAction(this.m_FocusGarnish);
                GarnishItem cGarnishItem = GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish);
                SetFocusGarnishCompose(cGarnishItem);
                SetFocusGarnishCompose_EditAction(cGarnishItem);
                if (this.m_StateMode == COLOR_GARNISH_STATE && HaveDrawViewListener()) {
                    this.m_DrawViewListener.OnColorGarnishMode();
                }
                if (this.m_StateMode == GS_GARNISH_STATE && HaveDrawViewListener()) {
                    this.m_DrawViewListener.OnGSGarnishMode();
                }
            } else {
                this.m_TouchMode = NONE_TOUCH_MODE;
            }
            invalidate();
        }
    }

    private boolean onEditPhotoModeTouchEvent(MotionEvent event) {
        this.m_EditPhotoScaleDetector.onTouchEvent(event);
        GarnishItem garnishItem = SearchGarnishFromFirst((int) EDIT_PHOTO_LAYER);
        if (garnishItem != null) {
            GarnishItem maskGarnishItem = null;
            for (int i = this.m_GarnishManager.size() - 1; i >= 0; i--) {
                if (((GarnishItem) this.m_GarnishManager.get(i)).GetType() == BRUSH_TOUCH_MODE) {
                    maskGarnishItem = (GarnishItem) this.m_GarnishManager.get(i);
                }
            }
            switch (event.getAction() & TelnetOption.MAX_OPTION_VALUE) {
                case NONE_TOUCH_MODE /*0*/:
                    this.m_TouchMode = PHOTO_DRAG_TOUCH_MODE;
                    this.m_fLastX = event.getX();
                    this.m_fLastY = event.getY();
                    SetEdit(true);
                    break;
                case EDIT_PHOTO_LAYER /*1*/:
                case PHOTO_DRAG_TOUCH_MODE /*6*/:
                    this.m_TouchMode = NONE_TOUCH_MODE;
                    if (HaveDrawViewListener()) {
                        if (this.m_StateMode != BORDER_STATE) {
                            if (this.m_StateMode != FILTER_STATE) {
                                if (this.m_StateMode == ID_PHOTO_STATE) {
                                    this.m_DrawViewListener.OnIDPhotoMode(garnishItem.GetMatrix());
                                    break;
                                }
                            }
                            this.m_DrawViewListener.OnFilterMode();
                            break;
                        }
                        this.m_DrawViewListener.OnBorderMode();
                        break;
                    }
                    break;
                case SCALE_TOUCH_MODE /*2*/:
                    if (this.m_TouchMode != PHOTO_ROTATE_TOUCH_MODE) {
                        if (this.m_TouchMode == PHOTO_DRAG_TOUCH_MODE) {
                            float x = event.getX();
                            float y = event.getY();
                            AddTrans(garnishItem, x - this.m_fLastX, y - this.m_fLastY);
                            if (maskGarnishItem != null) {
                                SetTrans(maskGarnishItem, garnishItem.GetX(), garnishItem.GetY());
                            }
                            this.m_fLastX = x;
                            this.m_fLastY = y;
                            break;
                        }
                    }
                    float fRotation = AdjustRotate(GetRotate(event) - this.m_fOrgDegree);
                    garnishItem.SetRotateMatrix(fRotation);
                    garnishItem.SetDegree(fRotation);
                    if (maskGarnishItem != null) {
                        maskGarnishItem.SetRotateMatrix(fRotation);
                        maskGarnishItem.SetDegree(fRotation);
                    }
                    this.LOG.m385i("SetDegree", String.valueOf(fRotation));
                    break;
                    break;
                case PHOTO_ROTATE_TOUCH_MODE /*5*/:
                    this.m_TouchMode = PHOTO_ROTATE_TOUCH_MODE;
                    this.m_fOrgDegree = GetRotate(event) - garnishItem.GetDegree();
                    this.LOG.m385i("SetDegree", String.valueOf(this.m_fOrgDegree));
                    break;
            }
            invalidate();
        }
        return true;
    }

    private boolean onGarnishModeTouchEvent(MotionEvent event) {
        this.m_ZoomDetector.onTouchEvent(event);
        if (!this.m_ZoomDetector.isInProgress()) {
            if (!this.m_GarnishGestureDetector.onTouchEvent(event)) {
                this.LOG.m384e("GarnishGestureDetector boRet", "false");
                switch (event.getAction() & TelnetOption.MAX_OPTION_VALUE) {
                    case EDIT_PHOTO_LAYER /*1*/:
                        onGarnishModeLongPressSingleTapUp(event);
                        break;
                    case SCALE_TOUCH_MODE /*2*/:
                        onGarnishModeLongPressScroll(event);
                        break;
                    default:
                        break;
                }
            }
            this.LOG.m384e("GarnishGestureDetector boRet", "true");
        }
        invalidate();
        return true;
    }

    private boolean onBrushModeTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        this.m_ZoomDetector.onTouchEvent(event);
        if (!this.m_ZoomDetector.isInProgress()) {
            switch (event.getAction() & TelnetOption.MAX_OPTION_VALUE) {
                case NONE_TOUCH_MODE /*0*/:
                    this.m_TouchMode = BRUSH_TOUCH_MODE;
                    this.m_BrushManager.PathMoveTo(this.m_BrushManager.CreateBrushRegion(), pointX, pointY);
                    this.LOG.m384e("Add DOWN", "[ " + String.valueOf(pointX) + ", " + String.valueOf(pointY) + " ]");
                    break;
                case EDIT_PHOTO_LAYER /*1*/:
                case PHOTO_DRAG_TOUCH_MODE /*6*/:
                    if (this.m_TouchMode == BRUSH_TOUCH_MODE) {
                        BrushRegion brushRegion = this.m_BrushManager.GetLastBrushRegion();
                        Path quadPath = brushRegion.GetQuadPath();
                        if (quadPath != null) {
                            brushRegion.SetBrushPath(quadPath);
                        }
                    }
                    if (HaveDrawViewListener()) {
                        this.m_DrawViewListener.OnBrushMode();
                    }
                    this.m_TouchMode = NONE_TOUCH_MODE;
                    break;
                case SCALE_TOUCH_MODE /*2*/:
                    if (this.m_TouchMode != ZOOM_TOUCH_MODE) {
                        if (this.m_TouchMode == BRUSH_TOUCH_MODE) {
                            this.m_BrushManager.PathLineTo(this.m_BrushManager.GetLastBrushRegion(), pointX, pointY);
                            break;
                        }
                    } else if (this.m_FocusGarnish != null) {
                        float x = event.getX();
                        float y = event.getY();
                        AddTrans(this.m_FocusGarnish, x - this.m_fLastX, y - this.m_fLastY);
                        this.m_fLastX = x;
                        this.m_fLastY = y;
                        break;
                    }
                    break;
                case PHOTO_ROTATE_TOUCH_MODE /*5*/:
                    this.m_TouchMode = ZOOM_TOUCH_MODE;
                    break;
            }
        }
        invalidate();
        return true;
    }

    private boolean onRollerModeTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        this.m_ZoomDetector.onTouchEvent(event);
        if (!this.m_ZoomDetector.isInProgress()) {
            switch (event.getAction() & TelnetOption.MAX_OPTION_VALUE) {
                case NONE_TOUCH_MODE /*0*/:
                    this.m_TouchMode = ROLLER_TOUCH_MODE;
                    this.m_RollerManager.PathMoveTo(this.m_RollerManager.CreateRollerRegion(), pointX, pointY);
                    this.LOG.m384e("Add DOWN", "[ " + String.valueOf(pointX) + ", " + String.valueOf(pointY) + " ]");
                    break;
                case EDIT_PHOTO_LAYER /*1*/:
                case PHOTO_DRAG_TOUCH_MODE /*6*/:
                    if (HaveDrawViewListener()) {
                        this.m_DrawViewListener.OnRollerMode();
                    }
                    this.m_TouchMode = NONE_TOUCH_MODE;
                    break;
                case SCALE_TOUCH_MODE /*2*/:
                    if (this.m_TouchMode != ZOOM_TOUCH_MODE) {
                        if (this.m_TouchMode == ROLLER_TOUCH_MODE) {
                            this.m_RollerManager.PathLineTo(this.m_RollerManager.GetLastRollerRegion(), pointX, pointY);
                            this.LOG.m384e("Add MOVE", "[ " + String.valueOf(pointX) + ", " + String.valueOf(pointY) + " ]");
                            break;
                        }
                    } else if (this.m_FocusGarnish != null) {
                        float x = event.getX();
                        float y = event.getY();
                        AddTrans(this.m_FocusGarnish, x - this.m_fLastX, y - this.m_fLastY);
                        this.m_fLastX = x;
                        this.m_fLastY = y;
                        break;
                    }
                    break;
                case PHOTO_ROTATE_TOUCH_MODE /*5*/:
                    this.m_TouchMode = ZOOM_TOUCH_MODE;
                    break;
            }
        }
        invalidate();
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!super.isEnabled()) {
            return false;
        }
        if (this.m_StateMode == BRUSH_STATE) {
            return onBrushModeTouchEvent(event);
        }
        if (this.m_StateMode == ROLLER_STATE) {
            return onRollerModeTouchEvent(event);
        }
        if (this.m_StateMode == BORDER_STATE || this.m_StateMode == FILTER_STATE || this.m_StateMode == ID_PHOTO_STATE) {
            return onEditPhotoModeTouchEvent(event);
        }
        if (this.m_StateMode == COLOR_GARNISH_STATE || this.m_StateMode == GS_GARNISH_STATE) {
            return onGarnishModeTouchEvent(event);
        }
        return true;
    }

    public void onDraw(Canvas canvas) {
        Collections.sort(this.m_GarnishManager);
        canvas.setDrawFilter(this.m_CanvasPaintFlagsDrawFilter);
        if (this.m_RegionViewMask != null) {
            DrawRegionViewMask(canvas);
            return;
        }
        DrawGarnish(canvas);
        DrawMirror(canvas);
        DrawFocus(canvas);
        DrawViewMask(canvas);
        DrawBrush(canvas);
        DrawRoller(canvas);
    }

    private void DrawGarnish(Canvas canvas) {
        synchronized (this.m_Lock) {
            Iterator it = this.m_GarnishManager.iterator();
            while (it.hasNext()) {
                GarnishItem garnishItem = (GarnishItem) it.next();
                canvas.save();
                canvas.concat(garnishItem.GetMatrix());
                if (!(garnishItem.GetType() == ROLLER_TOUCH_MODE || garnishItem.GetUIBitmap() == null)) {
                    if (garnishItem.IsGSType()) {
                        canvas.drawBitmap(garnishItem.GetUIBitmap(), 0.0f, 0.0f, this.m_AlphaPaint);
                    } else {
                        canvas.drawBitmap(garnishItem.GetUIBitmap(), 0.0f, 0.0f, null);
                    }
                    DrawEditPhotoMask(garnishItem, canvas);
                }
                canvas.restore();
            }
        }
    }

    private void DrawEditPhotoMask(GarnishItem garnishItem, Canvas canvas) {
        if (garnishItem.GetUIBitmap() != null && garnishItem.GetType() == EDIT_PHOTO_LAYER) {
            if ((this.m_StateMode == BORDER_STATE || this.m_StateMode == FILTER_STATE || this.m_StateMode == ID_PHOTO_STATE) && this.m_TouchMode != 0) {
                boolean boUpright = false;
                float fDegree = Math.abs(garnishItem.GetDegree() % 360.0f);
                float fDegree90 = Math.abs(fDegree - 90.0f) / Math.max(Math.abs(fDegree), Math.abs(90.0f));
                float fDegree180 = Math.abs(fDegree - 180.0f) / Math.max(Math.abs(fDegree), Math.abs(180.0f));
                float fDegree270 = Math.abs(fDegree - 270.0f) / Math.max(Math.abs(fDegree), Math.abs(270.0f));
                float fDegree360 = Math.abs(fDegree - 360.0f) / Math.max(Math.abs(fDegree), Math.abs(360.0f));
                if (fDegree == 0.0f || ((double) fDegree90) < 1.0E-6d || ((double) fDegree180) < 1.0E-6d || ((double) fDegree270) < 1.0E-6d || ((double) fDegree360) < 1.0E-6d) {
                    boUpright = true;
                }
                this.m_FocusMatrix.reset();
                this.m_FocusPath.reset();
                int iWidth = garnishItem.GetUIBitmap().getWidth();
                int iHeight = garnishItem.GetUIBitmap().getHeight();
                float XLineOffset = (float) (iWidth / ZOOM_TOUCH_MODE);
                float YLineOffset = (float) (iHeight / ZOOM_TOUCH_MODE);
                this.m_FocusPath.moveTo(0.0f, 0.0f);
                this.m_FocusPath.lineTo(0.0f, (float) iHeight);
                this.m_FocusPath.lineTo((float) (iWidth + NONE_TOUCH_MODE), (float) iHeight);
                this.m_FocusPath.lineTo((float) (iWidth + NONE_TOUCH_MODE), 0.0f);
                this.m_FocusPath.close();
                this.m_FocusPath.computeBounds(this.m_FocusRectF, true);
                canvas.drawRect(this.m_FocusRectF, this.m_EditPhotoPaint);
                canvas.drawLine(0.0f, 0.0f, 0.0f, (float) iHeight, this.m_EditPhotoLinePaint);
                canvas.drawLine(XLineOffset, 0.0f, XLineOffset, (float) iHeight, this.m_EditPhotoLinePaint);
                canvas.drawLine(XLineOffset * 2.0f, 0.0f, XLineOffset * 2.0f, (float) iHeight, this.m_EditPhotoLinePaint);
                canvas.drawLine((float) iWidth, 0.0f, (float) iWidth, (float) iHeight, this.m_EditPhotoLinePaint);
                canvas.drawLine(0.0f, 0.0f, (float) iWidth, 0.0f, this.m_EditPhotoLinePaint);
                canvas.drawLine(0.0f, YLineOffset, (float) iWidth, YLineOffset, this.m_EditPhotoLinePaint);
                canvas.drawLine(0.0f, YLineOffset * 2.0f, (float) iWidth, YLineOffset * 2.0f, this.m_EditPhotoLinePaint);
                canvas.drawLine(0.0f, (float) iHeight, (float) iWidth, (float) iHeight, this.m_EditPhotoLinePaint);
                if (boUpright) {
                    canvas.drawLine(0.0f, 0.0f, 0.0f, (float) iHeight, this.m_EditPhotoUprightLinePaint);
                    canvas.drawLine((float) iWidth, 0.0f, (float) iWidth, (float) iHeight, this.m_EditPhotoUprightLinePaint);
                    canvas.drawLine(0.0f, 0.0f, (float) iWidth, 0.0f, this.m_EditPhotoUprightLinePaint);
                    canvas.drawLine(0.0f, (float) iHeight, (float) iWidth, (float) iHeight, this.m_EditPhotoUprightLinePaint);
                }
            }
        }
    }

    private void DrawViewMask(Canvas canvas) {
        Iterator it = this.m_GarnishManager.iterator();
        while (it.hasNext()) {
            GarnishItem garnishItem = (GarnishItem) it.next();
            if (garnishItem.GetType() == ROLLER_TOUCH_MODE) {
                if (this.m_MaskGarnishOrgItem.GetID() == garnishItem.GetID()) {
                    if (this.m_Zoom > 1.0f) {
                        if (HaveDrawViewListener()) {
                            this.m_DrawViewListener.OnZoomStart(this.m_Zoom);
                        }
                    } else if (HaveDrawViewListener()) {
                        this.m_DrawViewListener.OnZoomEnd(this.m_Zoom);
                    }
                }
                synchronized (this.m_Lock) {
                    canvas.save();
                    canvas.concat(garnishItem.GetMatrix());
                    if (garnishItem.GetUIBitmap() != null) {
                        canvas.drawBitmap(garnishItem.GetUIBitmap(), 0.0f, 0.0f, null);
                    }
                    canvas.restore();
                }
            }
        }
    }

    private void DrawBrush(Canvas canvas) {
        if (this.m_StateMode == BRUSH_STATE) {
            canvas.setDrawFilter(null);
        }
        this.m_BrushManager.DrawBrush(canvas);
        canvas.setDrawFilter(this.m_CanvasPaintFlagsDrawFilter);
    }

    private void DrawRoller(Canvas canvas) {
        if (this.m_StateMode == ROLLER_STATE) {
            this.m_RollerManager.DrawRoller(canvas);
        }
    }

    private void DrawMirror(Canvas canvas) {
        if (this.m_boMirror) {
            canvas.drawRect(0.0f, 0.0f, this.m_fWidth, this.m_fHeight, this.m_AlphaPaint);
        }
    }

    private void DrawFocus(Canvas canvas) {
        if (this.m_FocusGarnish != null) {
            Iterator it = this.m_GarnishManager.iterator();
            while (it.hasNext()) {
                GarnishItem garnishItem = (GarnishItem) it.next();
                if (garnishItem.GetID() == this.m_FocusGarnish.GetID()) {
                    canvas.save();
                    this.m_FocusMatrix.reset();
                    this.m_FocusPath.reset();
                    int iWidth = garnishItem.GetUIBitmap().getWidth();
                    int iHeight = garnishItem.GetUIBitmap().getHeight();
                    this.m_FocusPath.moveTo(0.0f, 0.0f);
                    this.m_FocusPath.lineTo(0.0f, (float) iHeight);
                    this.m_FocusPath.lineTo((float) (iWidth + NONE_TOUCH_MODE), (float) iHeight);
                    this.m_FocusPath.lineTo((float) (iWidth + NONE_TOUCH_MODE), 0.0f);
                    this.m_FocusPath.close();
                    this.m_FocusMatrix.set(garnishItem.GetMatrix());
                    this.m_FocusPath.transform(garnishItem.GetMatrix());
                    this.m_FocusPath.computeBounds(this.m_FocusRectF, true);
                    this.m_FocusMatrix.postRotate(-garnishItem.GetDegree(), this.m_FocusRectF.left + ((this.m_FocusRectF.right - this.m_FocusRectF.left) / 2.0f), this.m_FocusRectF.top + ((this.m_FocusRectF.bottom - this.m_FocusRectF.top) / 2.0f));
                    this.m_FocusPath.reset();
                    this.m_FocusPath.moveTo(0.0f, 0.0f);
                    this.m_FocusPath.lineTo(0.0f, (float) iHeight);
                    this.m_FocusPath.lineTo((float) (iWidth + NONE_TOUCH_MODE), (float) iHeight);
                    this.m_FocusPath.lineTo((float) (iWidth + NONE_TOUCH_MODE), 0.0f);
                    this.m_FocusPath.close();
                    this.m_FocusPath.transform(this.m_FocusMatrix);
                    this.m_FocusPath.computeBounds(this.m_FocusRectF, true);
                    canvas.rotate(garnishItem.GetDegree(), this.m_FocusRectF.left + ((this.m_FocusRectF.right - this.m_FocusRectF.left) / 2.0f), this.m_FocusRectF.top + ((this.m_FocusRectF.bottom - this.m_FocusRectF.top) / 2.0f));
                    canvas.drawRect(this.m_FocusRectF, this.m_FocusPaint);
                    canvas.drawBitmap(this.m_FocusScaleBitmap, this.m_FocusRectF.right - ((float) (this.m_FocusScaleBitmap.getWidth() / SCALE_TOUCH_MODE)), this.m_FocusRectF.top - ((float) (this.m_FocusScaleBitmap.getHeight() / SCALE_TOUCH_MODE)), this.m_CanvasFocusPaint);
                    canvas.restore();
                }
            }
        }
    }

    private void DrawRegionViewMask(Canvas canvas) {
        if (this.m_RegionViewMask != null) {
            Canvas canvasMask = new Canvas(this.m_CanvasRegionViewMask);
            this.m_CanvasRegionViewMask.eraseColor(NONE_TOUCH_MODE);
            canvasMask.setMatrix(getMatrix());
            DrawGarnish(canvasMask);
            this.m_RegionViewMaskPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
            canvasMask.drawBitmap(this.m_RegionViewMask, 0.0f, 0.0f, this.m_RegionViewMaskPaint);
            this.m_RegionViewMaskPaint.setXfermode(null);
            canvas.drawBitmap(this.m_CanvasRegionViewMask, 0.0f, 0.0f, this.m_RegionViewMaskPaint);
        }
    }

    private void SetLastGarnish(GarnishItem lastGarnishItem) {
        if (lastGarnishItem != null) {
            if (this.m_LastGarnishGroup == null) {
                this.m_LastGarnishGroup = new ArrayList();
            }
            Iterator it = this.m_LastGarnishGroup.iterator();
            while (it.hasNext()) {
                ((GarnishItem) it.next()).Clear();
            }
            this.m_LastGarnishGroup.clear();
            this.m_LastGarnishGroup.add(GarnishItem.CopyGarnishItem(this.m_Context, lastGarnishItem));
            GarnishItem cGarnishItem = GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, lastGarnishItem);
            if (cGarnishItem != null) {
                this.m_LastGarnishGroup.add(GarnishItem.CopyGarnishItem(this.m_Context, cGarnishItem));
                long lComposeID = GarnishItem.CreateComposeID();
                it = this.m_LastGarnishGroup.iterator();
                while (it.hasNext()) {
                    ((GarnishItem) it.next()).SetComposeID(lComposeID);
                }
            }
        }
    }

    private void AddLastGarnish(ArrayList<GarnishItem> lastGarnishItemGroup, MotionEvent event) {
        if (lastGarnishItemGroup.size() != 0) {
            long lComposeID = -1;
            if (lastGarnishItemGroup.size() > EDIT_PHOTO_LAYER) {
                lComposeID = GarnishItem.CreateComposeID();
            }
            Iterator it = lastGarnishItemGroup.iterator();
            while (it.hasNext()) {
                GarnishItem lastGarnishItem = (GarnishItem) it.next();
                if (lastGarnishItem.GetType() == ZOOM_TOUCH_MODE || lastGarnishItem.GetType() == PHOTO_DRAG_TOUCH_MODE || lastGarnishItem.GetType() == PAN_TOUCH_MODE) {
                    GarnishItem garnishItem = new GarnishItem(this.m_Context, lastGarnishItem.GetType());
                    garnishItem.InitUIView(lastGarnishItem, false);
                    garnishItem.SetComposeID(lComposeID);
                    float x = event.getX();
                    float y = event.getY();
                    SetTrans(garnishItem, x, y);
                    this.m_fLastX = x;
                    this.m_fLastY = y;
                    this.m_GarnishManager.add(garnishItem);
                    this.m_FocusGarnish = garnishItem;
                    if (HaveDrawViewListener()) {
                        this.m_DrawViewListener.OnFocusGarnish();
                    }
                    SetEdit(garnishItem.GetType());
                    this.m_GarnishItemHistoryManager.CreateAction(this.m_FocusGarnish);
                    this.LOG.m384e("Add Copy", "Add Copy");
                }
            }
        }
    }

    private int AddMaskGarnish() {
        RectF MaskRectF = new RectF();
        Paint MaskPaint = new Paint();
        PointF MaskPoint = new PointF();
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap((int) this.m_fWidth, (int) this.m_fHeight, Config.ARGB_8888);
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        Bitmap MaskBitmapZoom = bmr.GetBitmap();
        Canvas canvas = new Canvas(MaskBitmapZoom);
        MaskPoint.set(this.m_fWidth / 2.0f, this.m_fHeight / 2.0f);
        MaskPaint.setAntiAlias(true);
        if (MathUtility.IsZero(this.m_fEditAreaWidth, this.m_fWidth) && MathUtility.IsZero(this.m_fEditAreaHeight, this.m_fHeight)) {
            MaskPaint.setColor(NONE_TOUCH_MODE);
        } else {
            MaskPaint.setColor(getResources().getColor(this.R_COLOR_MASK_ORG_COLOR));
        }
        MaskPaint.setStyle(Style.FILL);
        if (this.m_boVertical) {
            MaskRectF.left = 0.0f;
            MaskRectF.right = this.m_BoundX + ((float) EDIT_PHOTO_LAYER);
            MaskRectF.top = 0.0f;
            MaskRectF.bottom = this.m_fHeight;
        } else {
            MaskRectF.left = 0.0f;
            MaskRectF.right = this.m_fWidth;
            MaskRectF.top = 0.0f;
            MaskRectF.bottom = this.m_BoundY + ((float) EDIT_PHOTO_LAYER);
        }
        canvas.drawRect(MaskRectF, MaskPaint);
        if (this.m_boVertical) {
            MaskRectF = new RectF();
            MaskRectF.left = this.m_fWidth - this.m_BoundX;
            MaskRectF.right = this.m_fWidth;
            MaskRectF.top = 0.0f;
            MaskRectF.bottom = this.m_fHeight;
        } else {
            MaskRectF.left = 0.0f;
            MaskRectF.right = this.m_fWidth;
            MaskRectF.top = this.m_fHeight - this.m_BoundY;
            MaskRectF.bottom = this.m_fHeight;
        }
        canvas.drawRect(MaskRectF, MaskPaint);
        if (this.m_MaskGarnishZoomItem != null) {
            this.m_GarnishManager.remove(this.m_MaskGarnishZoomItem);
            this.m_MaskGarnishZoomItem.Clear();
        }
        this.m_MaskGarnishZoomItem = new GarnishItem(this.m_Context, ROLLER_TOUCH_MODE);
        this.m_MaskGarnishZoomItem.InitUIView(MaskBitmapZoom, MaskPoint, 1.0f, null, NONE_TOUCH_MODE);
        MaskBitmapZoom.recycle();
        bmr = BitmapMonitor.CreateBitmap((int) this.m_fWidth, (int) this.m_fHeight, Config.ARGB_8888);
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        Bitmap MaskBitmapOrg = bmr.GetBitmap();
        canvas = new Canvas(MaskBitmapOrg);
        if (MathUtility.IsZero(this.m_fEditAreaWidth, this.m_fWidth) && MathUtility.IsZero(this.m_fEditAreaHeight, this.m_fHeight)) {
            MaskPaint.setColor(NONE_TOUCH_MODE);
        } else {
            MaskPaint.setColor(getResources().getColor(this.R_COLOR_MASK_ZOOM_COLOR));
        }
        if (this.m_boVertical) {
            MaskRectF.left = 0.0f;
            MaskRectF.right = this.m_BoundX + ((float) EDIT_PHOTO_LAYER);
            MaskRectF.top = 0.0f;
            MaskRectF.bottom = this.m_fHeight;
        } else {
            MaskRectF.left = 0.0f;
            MaskRectF.right = this.m_fWidth;
            MaskRectF.top = 0.0f;
            MaskRectF.bottom = this.m_BoundY + ((float) EDIT_PHOTO_LAYER);
        }
        canvas.drawRect(MaskRectF, MaskPaint);
        if (this.m_boVertical) {
            MaskRectF = new RectF();
            MaskRectF.left = this.m_fWidth - this.m_BoundX;
            MaskRectF.right = this.m_fWidth;
            MaskRectF.top = 0.0f;
            MaskRectF.bottom = this.m_fHeight;
        } else {
            MaskRectF.left = 0.0f;
            MaskRectF.right = this.m_fWidth;
            MaskRectF.top = this.m_fHeight - this.m_BoundY;
            MaskRectF.bottom = this.m_fHeight;
        }
        canvas.drawRect(MaskRectF, MaskPaint);
        if (this.m_MaskGarnishOrgItem != null) {
            this.m_GarnishManager.remove(this.m_MaskGarnishOrgItem);
            this.m_MaskGarnishOrgItem.Clear();
        }
        this.m_MaskGarnishOrgItem = new GarnishItem(this.m_Context, ROLLER_TOUCH_MODE);
        this.m_MaskGarnishOrgItem.InitUIView(MaskBitmapOrg, MaskPoint, 1.0f, null, NONE_TOUCH_MODE);
        MaskBitmapOrg.recycle();
        AddGarnish(this.m_MaskGarnishZoomItem);
        AddGarnish(this.m_MaskGarnishOrgItem);
        return NONE_TOUCH_MODE;
    }

    public int AddRegionViewMask(Bitmap bmp) {
        this.m_RegionViewMask = bmp;
        this.m_RegionViewMaskPaint.setFilterBitmap(true);
        this.m_RegionViewMaskPaint.setAntiAlias(true);
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(this.m_RegionViewMask.getWidth(), this.m_RegionViewMask.getHeight(), Config.ARGB_8888);
        if (!bmr.IsSuccess()) {
            return bmr.GetResult();
        }
        this.m_CanvasRegionViewMask = bmr.GetBitmap();
        return NONE_TOUCH_MODE;
    }

    public void AddGarnish(ArrayList<GarnishItem> GarnishList) {
        this.LOG.m385i("AddGarnish", "AddGarnish");
        Iterator it = GarnishList.iterator();
        while (it.hasNext()) {
            this.m_GarnishManager.add((GarnishItem) it.next());
        }
        Collections.sort(this.m_GarnishManager);
    }

    public void AddGarnish(GarnishItem garnishItem) {
        SetEdit(garnishItem.GetType());
        if (garnishItem.GetType() == PHOTO_ROTATE_TOUCH_MODE) {
            RemoveBorder(PHOTO_ROTATE_TOUCH_MODE);
            this.m_GarnishManager.add(garnishItem);
        } else if (garnishItem.GetType() == SCALE_TOUCH_MODE) {
            RemoveBorder(SCALE_TOUCH_MODE);
            this.m_GarnishManager.add(garnishItem);
        } else if (garnishItem.GetType() == EDIT_PHOTO_LAYER) {
            RemoveEditPhoto();
            this.m_GarnishManager.add(EDIT_PHOTO_LAYER, garnishItem);
        } else {
            this.m_GarnishManager.add(garnishItem);
            if (this.m_Zoom != 1.0f) {
                SetCurrentZoom(garnishItem, this.m_Zoom, this.m_fWidth / 2.0f, this.m_fHeight / 2.0f);
            }
            if (garnishItem.GetType() != 0 && garnishItem.GetType() != ROLLER_TOUCH_MODE && garnishItem.GetType() != BRUSH_TOUCH_MODE) {
                this.m_FocusGarnish = garnishItem;
                this.m_GarnishItemHistoryManager.CreateAction(this.m_FocusGarnish);
                if (HaveDrawViewListener()) {
                    this.m_DrawViewListener.OnFocusGarnish();
                }
            }
        }
    }

    public long AddGarnish(Bitmap bitmap, PointF pointF, int iType, String strGarnishPath, int iFromType) {
        GarnishItem garnishItem = new GarnishItem(this.m_Context, iType);
        if (garnishItem.InitUIView(bitmap, pointF, this.m_fViewScale, strGarnishPath, iFromType) != 0) {
            return -1;
        }
        SetEdit(iType);
        AddGarnish(garnishItem);
        return garnishItem.GetID();
    }

    public long AddGarnish(Bitmap bitmap, PointF pointF, int iType, String strGarnishPath, int iFromType, long lComposeID) {
        GarnishItem garnishItem = new GarnishItem(this.m_Context, iType);
        if (garnishItem.InitUIView(bitmap, pointF, this.m_fViewScale, strGarnishPath, iFromType) != 0) {
            return -1;
        }
        SetEdit(iType);
        garnishItem.SetComposeID(lComposeID);
        AddGarnish(garnishItem);
        return garnishItem.GetID();
    }

    public GarnishItem SearchGarnishItem(float x, float y, boolean boGarnishEdit) {
        for (int i = this.m_GarnishManager.size() - 1; i >= 0; i--) {
            GarnishItem garnishItem = (GarnishItem) this.m_GarnishManager.get(i);
            float fTouchLength = (((float) (this.m_FocusScaleBitmap.getHeight() / SCALE_TOUCH_MODE)) / garnishItem.GetScale()) / this.m_Zoom;
            float fRight = (float) garnishItem.GetUIBitmap().getWidth();
            float fBttton = (float) garnishItem.GetUIBitmap().getHeight();
            Path p = new Path();
            p.moveTo(0.0f, 0.0f);
            p.lineTo(0.0f, fBttton);
            p.lineTo(fRight, fBttton);
            p.lineTo(fRight, 0.0f);
            p.close();
            if (boGarnishEdit == EDIT_PHOTO_LAYER) {
                p.reset();
                p.moveTo(fRight - fTouchLength, 0.0f - fTouchLength);
                p.lineTo(fRight - fTouchLength, 0.0f + fTouchLength);
                p.lineTo(fRight + fTouchLength, 0.0f + fTouchLength);
                p.lineTo(fRight + fTouchLength, 0.0f - fTouchLength);
                p.close();
            }
            RectF rectF = new RectF();
            p.computeBounds(rectF, true);
            p.transform(garnishItem.GetMatrix());
            p.computeBounds(rectF, true);
            Region r = new Region();
            r.setPath(p, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
            if (r.contains((int) x, (int) y) && garnishItem.CanMove(-1)) {
                return garnishItem;
            }
        }
        return null;
    }

    public GarnishItem SearchFocusGarnishItem(float x, float y, boolean boGarnishEdit) {
        GarnishItem garnishItem = this.m_FocusGarnish;
        float fTouchLength = (((float) (this.m_FocusScaleBitmap.getHeight() / SCALE_TOUCH_MODE)) / garnishItem.GetScale()) / this.m_Zoom;
        float fRight = (float) garnishItem.GetUIBitmap().getWidth();
        float fBttton = (float) garnishItem.GetUIBitmap().getHeight();
        Path p = new Path();
        p.moveTo(0.0f, 0.0f);
        p.lineTo(0.0f, fBttton);
        p.lineTo(fRight, fBttton);
        p.lineTo(fRight, 0.0f);
        p.close();
        if (boGarnishEdit == EDIT_PHOTO_LAYER) {
            p.reset();
            p.moveTo(fRight - fTouchLength, 0.0f - fTouchLength);
            p.lineTo(fRight - fTouchLength, 0.0f + fTouchLength);
            p.lineTo(fRight + fTouchLength, 0.0f + fTouchLength);
            p.lineTo(fRight + fTouchLength, 0.0f - fTouchLength);
            p.close();
        }
        RectF rectF = new RectF();
        p.computeBounds(rectF, true);
        p.transform(garnishItem.GetMatrix());
        p.computeBounds(rectF, true);
        Region r = new Region();
        r.setPath(p, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        if (r.contains((int) x, (int) y)) {
            return garnishItem;
        }
        return null;
    }

    public GarnishItem SearchGarnishFromFirst(int iType) {
        for (int i = NONE_TOUCH_MODE; i < this.m_GarnishManager.size(); i += EDIT_PHOTO_LAYER) {
            if (((GarnishItem) this.m_GarnishManager.get(i)).GetType() == iType) {
                return (GarnishItem) this.m_GarnishManager.get(i);
            }
        }
        return null;
    }

    public GarnishItem GetGarnishFromLast(int iType) {
        for (int i = this.m_GarnishManager.size() - 1; i >= 0; i--) {
            if (((GarnishItem) this.m_GarnishManager.get(i)).GetType() == iType) {
                return (GarnishItem) this.m_GarnishManager.get(i);
            }
        }
        return null;
    }

    public GarnishItem SearchGarnishFromFirst(long iID) {
        for (int i = NONE_TOUCH_MODE; i < this.m_GarnishManager.size(); i += EDIT_PHOTO_LAYER) {
            if (((GarnishItem) this.m_GarnishManager.get(i)).GetID() == iID) {
                return (GarnishItem) this.m_GarnishManager.get(i);
            }
        }
        return null;
    }

    public GarnishItem GetGarnishFromLast(long iID) {
        for (int i = this.m_GarnishManager.size() - 1; i >= 0; i--) {
            if (((GarnishItem) this.m_GarnishManager.get(i)).GetID() == iID) {
                return (GarnishItem) this.m_GarnishManager.get(i);
            }
        }
        return null;
    }

    public GarnishItem GetEditPhotoGarnishItem() {
        return SearchGarnishFromFirst((int) EDIT_PHOTO_LAYER);
    }

    public GarnishItem GetGSMaskGarnishItem() {
        return SearchGarnishFromFirst((int) BRUSH_TOUCH_MODE);
    }

    public GarnishItem GetBackGroundGarnishItem() {
        Iterator it = this.m_GarnishManager.iterator();
        while (it.hasNext()) {
            GarnishItem garnishItem = (GarnishItem) it.next();
            if (garnishItem.GetType() == 0) {
                return garnishItem;
            }
        }
        return null;
    }

    public GarnishItem GetBorderGarnishItem(int iType) {
        if (iType != SCALE_TOUCH_MODE && iType != PHOTO_ROTATE_TOUCH_MODE) {
            return null;
        }
        Iterator it = this.m_GarnishManager.iterator();
        while (it.hasNext()) {
            GarnishItem garnishItem = (GarnishItem) it.next();
            if (garnishItem.GetType() == iType) {
                return garnishItem;
            }
        }
        return null;
    }

    public GarnishItem GetGarnishItem(long lID) {
        Iterator it = this.m_GarnishManager.iterator();
        while (it.hasNext()) {
            GarnishItem garnishItem = (GarnishItem) it.next();
            if (garnishItem.GetID() == lID) {
                return garnishItem;
            }
        }
        return null;
    }

    public boolean HaveGSGarnish() {
        synchronized (this.m_Lock) {
            Iterator it = this.m_GarnishManager.iterator();
            while (it.hasNext()) {
                GarnishItem garnishItem = (GarnishItem) it.next();
                if (garnishItem.GetType() != 0 && garnishItem.GetType() != EDIT_PHOTO_LAYER && garnishItem.GetType() != ZOOM_TOUCH_MODE && garnishItem.GetType() != SCALE_TOUCH_MODE && garnishItem.GetType() != PAN_TOUCH_MODE && garnishItem.GetType() != ROLLER_TOUCH_MODE) {
                    return true;
                }
            }
            return false;
        }
    }

    public void MoveFocusGarnishItemToTop(GarnishItem FocusGarnish) {
    }

    public void SwapGarnishLayer(boolean boUp) {
        if (this.m_FocusGarnish != null) {
            GarnishItemLayerManager.SwapGarnishLayer(this.m_GarnishManager, this.m_FocusGarnish, boUp);
            this.m_GarnishItemHistoryManager.LayerMoveAction(this.m_FocusGarnish, boUp);
            GarnishItem cGarnishItem = GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish);
            GarnishItemLayerManager.SwapGarnishLayer(this.m_GarnishManager, cGarnishItem, boUp);
            SetFocusGarnishCompose_LayerMoveAction(cGarnishItem, boUp);
        }
        invalidate();
    }

    public boolean CanGarnishMoveUpLayer() {
        GarnishItem cGarnishItem = GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish);
        if (GarnishItemLayerManager.CanGarnishMoveUpLayer(this.m_GarnishManager, this.m_FocusGarnish) == -1 && GarnishItemLayerManager.CanGarnishMoveUpLayer(this.m_GarnishManager, cGarnishItem) == -1) {
            return false;
        }
        return true;
    }

    public boolean CanGarnishMoveDownLayer() {
        GarnishItem cGarnishItem = GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish);
        if (GarnishItemLayerManager.CanGarnishMoveDownLayer(this.m_GarnishManager, this.m_FocusGarnish) == -1 && GarnishItemLayerManager.CanGarnishMoveDownLayer(this.m_GarnishManager, cGarnishItem) == -1) {
            return false;
        }
        return true;
    }

    public void SetEditPhotoGarnishScale(float dy) {
        GarnishItem garnishItem = SearchGarnishFromFirst((int) EDIT_PHOTO_LAYER);
        if (garnishItem != null) {
            AddGarnishScale(garnishItem, dy);
        }
        GarnishItem gsGarnishItem = SearchGarnishFromFirst((int) BRUSH_TOUCH_MODE);
        if (gsGarnishItem != null) {
            AddGarnishScale(gsGarnishItem, dy);
        }
        if (garnishItem != null && HaveDrawViewListener() && this.m_StateMode == ID_PHOTO_STATE) {
            this.m_DrawViewListener.OnIDPhotoMode(garnishItem.GetMatrix());
        }
        invalidate();
    }

    public void SetFocusGarnishScale(float dy) {
        if (this.m_FocusGarnish != null && (this.m_FocusGarnish.GetType() == PHOTO_DRAG_TOUCH_MODE || this.m_FocusGarnish.GetType() == ZOOM_TOUCH_MODE || this.m_FocusGarnish.GetType() == PAN_TOUCH_MODE)) {
            this.m_GarnishItemHistoryManager.EditAction(this.m_FocusGarnish);
            AddGarnishScale(this.m_FocusGarnish, dy);
            GarnishItem cGarnishItem = GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish);
            SetFocusGarnishCompose_EditAction(cGarnishItem);
            SetFocusGarnishComposeAction(cGarnishItem);
        }
        invalidate();
    }

    private void AddGarnishScale(GarnishItem garnishItem, float dy) {
        float fScale = garnishItem.GetScale();
        this.LOG.m384e("AddScale fScale", String.valueOf(fScale));
        if (fScale >= 0.0f && fScale + dy >= 0.0f) {
            fScale += dy;
            garnishItem.SetScaleMatrix(fScale, fScale);
            garnishItem.SetScale(fScale);
            SetEdit(true);
        }
    }

    private void SetGarnishScale(GarnishItem garnishItem, double dy) {
        int iWidth = garnishItem.GetUIBitmap().getWidth();
        int iHeight = garnishItem.GetUIBitmap().getHeight();
        double dDiagonal = (double) MathUtility.Diagonal((float) iWidth, (float) iHeight);
        float fScale = (float) (((((double) iHeight) * (dDiagonal + dy)) / dDiagonal) / ((double) iHeight));
        garnishItem.SetScaleMatrix(fScale / this.m_Zoom, fScale / this.m_Zoom);
        garnishItem.SetScale(fScale / this.m_Zoom);
        SetEdit(true);
    }

    public void SetEditPhotoGarnishTrans(float dx, float dy) {
        GarnishItem garnishItem = SearchGarnishFromFirst((int) EDIT_PHOTO_LAYER);
        if (garnishItem != null) {
            AddTrans(garnishItem, dx, dy);
        }
        GarnishItem gsGarnishItem = SearchGarnishFromFirst((int) BRUSH_TOUCH_MODE);
        if (gsGarnishItem != null) {
            AddTrans(gsGarnishItem, dx, dy);
        }
        if (garnishItem != null && HaveDrawViewListener() && this.m_StateMode == ID_PHOTO_STATE) {
            this.m_DrawViewListener.OnIDPhotoMode(garnishItem.GetMatrix());
        }
        invalidate();
    }

    public void SetFocusGarnishTrans(float dx, float dy) {
        if (this.m_FocusGarnish != null && (this.m_FocusGarnish.GetType() == PHOTO_DRAG_TOUCH_MODE || this.m_FocusGarnish.GetType() == ZOOM_TOUCH_MODE || this.m_FocusGarnish.GetType() == PAN_TOUCH_MODE)) {
            this.m_GarnishItemHistoryManager.EditAction(this.m_FocusGarnish);
            AddTrans(this.m_FocusGarnish, dx, dy);
            GarnishItem cGarnishItem = GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish);
            SetFocusGarnishCompose_EditAction(cGarnishItem);
            SetFocusGarnishComposeAction(cGarnishItem);
        }
        invalidate();
    }

    private void AddTrans(GarnishItem garnishItem, float dx, float dy) {
        SetTrans(garnishItem, garnishItem.GetX() + dx, garnishItem.GetY() + dy);
    }

    private void SetTrans(GarnishItem garnishItem, float x, float y) {
        garnishItem.SetTransMatrix(x, y);
        garnishItem.SetX(x);
        garnishItem.SetY(y);
        SetEdit(true);
    }

    private void SetCurrentZoom(GarnishItem garnishItem, float scale, float centerX, float centerY) {
        float diffX = centerX - garnishItem.GetX();
        float diffY = centerY - garnishItem.GetY();
        AddTrans(garnishItem, -((diffX * scale) - diffX), -((diffY * scale) - diffY));
        garnishItem.SetZoomMatrix(this.m_Zoom, this.m_Zoom);
        garnishItem.SetZoom(this.m_Zoom);
    }

    public void SetZoom(GarnishItem garnishItem, float fZoom, float scale, float centerX, float centerY, PointF fOffsetBound) {
        float diffX = centerX - garnishItem.GetX();
        float diffY = centerY - garnishItem.GetY();
        AddTrans(garnishItem, -((diffX * scale) - diffX), -((diffY * scale) - diffY));
        garnishItem.SetZoomMatrix(fZoom, fZoom);
        garnishItem.SetZoom(fZoom);
        AddTrans(garnishItem, fOffsetBound.x, fOffsetBound.y);
    }

    public void ResetZoom() {
        PointF fOffsetBound = new PointF();
        float centerX = this.m_fWidth / 2.0f;
        float centerY = this.m_fHeight / 2.0f;
        float scale = 1.0f / this.m_Zoom;
        if (((double) (Math.abs(this.m_Zoom - 1.0f) / Math.max(Math.abs(this.m_Zoom), Math.abs(1.0f)))) >= 1.0E-6d) {
            this.m_Zoom *= scale;
            Collections.sort(this.m_GarnishManager);
            fOffsetBound = GetBoundOffset(centerX, centerY, scale, this.m_Zoom);
            Iterator it = this.m_GarnishManager.iterator();
            while (it.hasNext()) {
                SetZoom((GarnishItem) it.next(), this.m_Zoom, scale, centerX, centerY, fOffsetBound);
            }
            it = this.m_LastGarnishGroup.iterator();
            while (it.hasNext()) {
                SetZoom((GarnishItem) it.next(), this.m_Zoom, scale, centerX, centerY, fOffsetBound);
            }
            if (this.m_StateMode == BRUSH_STATE && this.m_BrushManager.HaveBrush()) {
                this.m_BrushManager.SetZoom(this.m_Zoom, scale, centerX, centerY, fOffsetBound);
            }
            if (this.m_StateMode == ROLLER_STATE && this.m_RollerManager.HaveRoller()) {
                this.m_RollerManager.SetZoom(this.m_Zoom, scale, centerX, centerY, fOffsetBound);
            }
            if (((double) (Math.abs(this.m_Zoom - 1.0f) / Math.max(Math.abs(this.m_Zoom), Math.abs(1.0f)))) < 1.0E-6d) {
                ResetViewMask();
            }
            invalidate();
        }
    }

    private void ResetViewMask() {
        for (int i = NONE_TOUCH_MODE; i < this.m_GarnishManager.size(); i += EDIT_PHOTO_LAYER) {
            GarnishItem garnishItem = (GarnishItem) this.m_GarnishManager.get(i);
            if (garnishItem.GetType() == ROLLER_TOUCH_MODE) {
                SetTrans(garnishItem, garnishItem.GetStartX(), garnishItem.GetStartY());
            }
        }
    }

    private void SetGarnishRotate(GarnishItem garnishItem, float AX, float AY, float BX, float BY) {
        float CX = garnishItem.GetX();
        float CY = garnishItem.GetY();
        double fALength = (double) MathUtility.Spacing(AX, AY, CX, CY);
        double dSinA = ((double) (AX - CX)) / fALength;
        double dCosA = ((double) (AY - CY)) / fALength;
        double fBLength = (double) MathUtility.Spacing(BX, BY, CX, CY);
        double dSinB = ((double) (BX - CX)) / fBLength;
        double dCosB = ((double) (BY - CY)) / fBLength;
        double dSinA_B = (dSinA * dCosB) - (dCosA * dSinB);
        double dAngleA_B = (Math.asin(dSinA_B) * 180.0d) / 3.14159265359d;
        if ((dCosA * dCosB) + (dSinA * dSinB) <= 0.0d) {
            if (dSinA_B >= 0.0d) {
                dAngleA_B = 180.0d - dAngleA_B;
            } else {
                dAngleA_B = -180.0d - dAngleA_B;
            }
        }
        garnishItem.SetRotateMatrix((float) (((double) this.m_fOrgDegree) + dAngleA_B));
        garnishItem.SetDegree((float) (((double) this.m_fOrgDegree) + dAngleA_B));
        SetEdit(true);
    }

    private float AdjustRotate(float iRotate) {
        float adjustRotate = iRotate % 360.0f;
        if (adjustRotate >= 0.0f && adjustRotate <= ((float) this.m_iAdjustRotate)) {
            return 0.0f;
        }
        if (adjustRotate >= ((float) (90 - this.m_iAdjustRotate)) && adjustRotate <= ((float) (this.m_iAdjustRotate + 90))) {
            return 90.0f;
        }
        if (adjustRotate >= ((float) (180 - this.m_iAdjustRotate)) && adjustRotate <= ((float) (this.m_iAdjustRotate + 180))) {
            return 180.0f;
        }
        if (adjustRotate >= ((float) (270 - this.m_iAdjustRotate)) && adjustRotate <= ((float) (this.m_iAdjustRotate + 270))) {
            return 270.0f;
        }
        if (adjustRotate >= ((float) (360 - this.m_iAdjustRotate)) && adjustRotate <= 360.0f) {
            return 360.0f;
        }
        if (adjustRotate >= ((float) (-this.m_iAdjustRotate)) && adjustRotate <= 0.0f) {
            return 0.0f;
        }
        if (adjustRotate >= ((float) (-90 - this.m_iAdjustRotate)) && adjustRotate <= ((float) (this.m_iAdjustRotate - 90))) {
            return -90.0f;
        }
        if (adjustRotate >= ((float) (-180 - this.m_iAdjustRotate)) && adjustRotate <= ((float) (this.m_iAdjustRotate - 180))) {
            return -180.0f;
        }
        if (adjustRotate >= ((float) (-270 - this.m_iAdjustRotate)) && adjustRotate <= ((float) (this.m_iAdjustRotate - 270))) {
            return -270.0f;
        }
        if (adjustRotate < -360.0f || adjustRotate > ((float) (this.m_iAdjustRotate - 360))) {
            return adjustRotate;
        }
        return -360.0f;
    }

    public int Rotate90WithContent() {
        float temp = this.m_fEditAreaWidth;
        this.m_fEditAreaWidth = this.m_fEditAreaHeight;
        this.m_fEditAreaHeight = temp;
        this.m_boVertical = !this.m_boVertical;
        int iResult = InitDrawView(this.m_fEditAreaWidth, this.m_fEditAreaHeight, this.m_fWidth, this.m_fHeight, this.m_boVertical, this.m_iOutputDefaultMaxShort, this.m_iOutputDefaultMaxLong);
        if (iResult == 0) {
            Iterator it = this.m_GarnishManager.iterator();
            while (it.hasNext()) {
                GarnishItem garnishItem = (GarnishItem) it.next();
                if (garnishItem.GetType() == EDIT_PHOTO_LAYER || garnishItem.GetType() == BRUSH_TOUCH_MODE || garnishItem.GetType() == 0) {
                    float fDagree = garnishItem.GetDegree() + 90.0f;
                    float mPosX = garnishItem.GetX() - (this.m_fWidth / 2.0f);
                    float mPosY = garnishItem.GetY() - (this.m_fHeight / 2.0f);
                    garnishItem.SetTransMatrix(this.m_fWidth / 2.0f, this.m_fHeight / 2.0f);
                    garnishItem.SetX(this.m_fWidth / 2.0f);
                    garnishItem.SetY(this.m_fHeight / 2.0f);
                    garnishItem.SetRotateMatrix(fDagree);
                    garnishItem.SetDegree(fDagree);
                    float mPosX2 = garnishItem.GetX() - mPosY;
                    float mPosY2 = garnishItem.GetY() + mPosX;
                    garnishItem.SetTransMatrix(mPosX2, mPosY2);
                    garnishItem.SetX(mPosX2);
                    garnishItem.SetY(mPosY2);
                }
            }
            invalidate();
        }
        return iResult;
    }

    public int Rotate90WithoutContent() {
        this.LOG.m385i("Rotate90WithoutContent", "Rotate90WithoutContent");
        float temp = this.m_fEditAreaWidth;
        this.m_fEditAreaWidth = this.m_fEditAreaHeight;
        this.m_fEditAreaHeight = temp;
        this.m_boVertical = !this.m_boVertical;
        GarnishItem bgGarnishItem = GetBackGroundGarnishItem();
        float fDagree = bgGarnishItem.GetDegree() + 90.0f;
        bgGarnishItem.SetRotateMatrix(fDagree);
        bgGarnishItem.SetDegree(fDagree);
        return InitDrawView(this.m_fEditAreaWidth, this.m_fEditAreaHeight, this.m_fWidth, this.m_fHeight, this.m_boVertical, this.m_iOutputDefaultMaxShort, this.m_iOutputDefaultMaxLong);
    }

    private float GetRotate(MotionEvent event) {
        return (float) Math.toDegrees(Math.atan2((double) (event.getY(NONE_TOUCH_MODE) - event.getY(EDIT_PHOTO_LAYER)), (double) (event.getX(NONE_TOUCH_MODE) - event.getX(EDIT_PHOTO_LAYER))));
    }

    public void DeleteALLGarnish() {
        synchronized (this.m_Lock) {
            this.m_GarnishItemHistoryManager.Clear();
            for (int i = this.m_GarnishManager.size() - 1; i >= 0; i--) {
                GarnishItem garnishItem = (GarnishItem) this.m_GarnishManager.get(i);
                if (garnishItem.GetType() == SCALE_TOUCH_MODE || garnishItem.GetType() == ZOOM_TOUCH_MODE || garnishItem.GetType() == PAN_TOUCH_MODE || garnishItem.GetType() == PHOTO_ROTATE_TOUCH_MODE || garnishItem.GetType() == PHOTO_DRAG_TOUCH_MODE || garnishItem.GetType() == BRUSH_TOUCH_MODE) {
                    this.m_GarnishManager.remove(garnishItem);
                    garnishItem.Clear();
                    invalidate();
                }
            }
        }
    }

    public void DeleteAllStampGarnish() {
        synchronized (this.m_Lock) {
            for (int i = this.m_GarnishManager.size() - 1; i >= 0; i--) {
                GarnishItem garnishItem = (GarnishItem) this.m_GarnishManager.get(i);
                if (garnishItem.GetType() == SCALE_TOUCH_MODE || garnishItem.GetType() == ZOOM_TOUCH_MODE || garnishItem.GetType() == PAN_TOUCH_MODE) {
                    GarnishItem cGarnishItem = GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, garnishItem);
                    SetFocusGarnishCompose_DeleteAction(cGarnishItem);
                    ClearFocusGarnishCompose(cGarnishItem);
                    this.m_GarnishManager.remove(garnishItem);
                    this.m_GarnishItemHistoryManager.DeleteAction(garnishItem);
                    garnishItem.Clear();
                    invalidate();
                }
            }
        }
    }

    public void DeleteStampGarnish() {
        synchronized (this.m_Lock) {
            if (this.m_FocusGarnish != null && (this.m_FocusGarnish.GetType() == PHOTO_DRAG_TOUCH_MODE || this.m_FocusGarnish.GetType() == PAN_TOUCH_MODE || this.m_FocusGarnish.GetType() == ZOOM_TOUCH_MODE)) {
                GarnishItem cGarnishItem = GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish);
                SetFocusGarnishCompose_DeleteAction(cGarnishItem);
                ClearFocusGarnishCompose(cGarnishItem);
                this.m_GarnishManager.remove(this.m_FocusGarnish);
                this.m_GarnishItemHistoryManager.DeleteAction(this.m_FocusGarnish);
                this.m_FocusGarnish.Clear();
                this.m_FocusGarnish = null;
                if (HaveDrawViewListener()) {
                    this.m_DrawViewListener.OnMissFocusGarnish();
                }
                invalidate();
            }
        }
    }

    public void Clear() {
        synchronized (this.m_Lock) {
            Iterator it = this.m_GarnishManager.iterator();
            while (it.hasNext()) {
                ((GarnishItem) it.next()).Clear();
            }
            this.m_GarnishManager.clear();
        }
    }

    public void ClearBrush() {
        this.m_BrushManager.ClearBrush();
        invalidate();
    }

    public void ClearRoller() {
        this.m_RollerManager.ClearRoller();
        invalidate();
    }

    public void ClearRollerScreen() {
        this.m_RollerManager.ClearScreen();
        invalidate();
    }

    public void RemoveBorder(int iType) {
        synchronized (this.m_Lock) {
            if (iType == SCALE_TOUCH_MODE || iType == PHOTO_ROTATE_TOUCH_MODE) {
                for (int i = this.m_GarnishManager.size() - 1; i >= 0; i--) {
                    if (((GarnishItem) this.m_GarnishManager.get(i)).GetType() == iType) {
                        ((GarnishItem) this.m_GarnishManager.get(i)).Clear();
                        this.m_GarnishManager.remove(i);
                    }
                }
                return;
            }
        }
    }

    public void RemoveBorder() {
        synchronized (this.m_Lock) {
            int i;
            for (i = this.m_GarnishManager.size() - 1; i >= 0; i--) {
                if (((GarnishItem) this.m_GarnishManager.get(i)).GetType() == SCALE_TOUCH_MODE) {
                    ((GarnishItem) this.m_GarnishManager.get(i)).Clear();
                    this.m_GarnishManager.remove(i);
                }
            }
            for (i = this.m_GarnishManager.size() - 1; i >= 0; i--) {
                if (((GarnishItem) this.m_GarnishManager.get(i)).GetType() == PHOTO_ROTATE_TOUCH_MODE) {
                    ((GarnishItem) this.m_GarnishManager.get(i)).Clear();
                    this.m_GarnishManager.remove(i);
                }
            }
        }
    }

    public void RemoveEditPhoto() {
        synchronized (this.m_Lock) {
            GarnishItem editPhotoGarnishItem = SearchGarnishFromFirst((int) EDIT_PHOTO_LAYER);
            if (editPhotoGarnishItem != null) {
                this.m_GarnishManager.remove(EDIT_PHOTO_LAYER);
                editPhotoGarnishItem.Clear();
            }
            GarnishItem GsMaskGarnishItem = SearchGarnishFromFirst((int) BRUSH_TOUCH_MODE);
            if (GsMaskGarnishItem != null) {
                this.m_GarnishManager.remove(GsMaskGarnishItem);
                GsMaskGarnishItem.Clear();
            }
        }
    }

    public float AdjustCoordinates(float pos) {
        if (pos > 0.0f && pos < 2.0f) {
            return 0.0f;
        }
        if (pos <= -2.0f || pos >= 0.0f) {
            return pos;
        }
        return 0.0f;
    }

    private void DrawFilter(Bitmap bmp, GarnishItem garnishItem) {
        ImageFilter imageFilter = new ImageFilter(this.m_Context);
        ImageFilterPlus imageFilterPlus = new ImageFilterPlus(this.m_Context);
        if (!garnishItem.GetFilter().contains(GarnishItem.NON_FILTER)) {
            imageFilter.ProcessImage(bmp, IMAGE_FILTER_TYPE.valueOf(garnishItem.GetFilter()));
        }
        if (FilterColorValue.HaveModifyHSLCValue(garnishItem.GetHue(), garnishItem.GetSaturation(), garnishItem.GetLight(), garnishItem.GetContrast())) {
            imageFilter.ProcessImage_HSBC(bmp, IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_adjust_S_B_C_new, garnishItem.GetHue(), garnishItem.GetSaturation(), garnishItem.GetLight(), garnishItem.GetContrast());
        }
        if (FilterColorValue.HaveModifyRGBValue(garnishItem.GetRed(), garnishItem.GetGreen(), garnishItem.GetBlue())) {
            imageFilterPlus.ProcessImage_RGB(bmp, garnishItem.GetRed(), garnishItem.GetGreen(), garnishItem.GetBlue());
        }
    }

    private int DrawORGEditPhoto(GarnishItem garnishItem, Canvas canvas) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        this.LOG.m385i("DrawORGEditPhoto", "Type=" + garnishItem.GetType());
        if (garnishItem.GetType() == EDIT_PHOTO_LAYER) {
            boolean boTrySuperiorQuality = false;
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            paint.setAntiAlias(true);
            Options options = new Options();
            this.LOG.m385i("garnishItem.GetGarnishPath()", garnishItem.GetGarnishPath());
            options.inJustDecodeBounds = true;
            try {
                bmr = BitmapMonitor.CreateBitmap(new BufferedInputStream(this.m_Context.getContentResolver().openInputStream(Uri.parse("file://" + garnishItem.GetGarnishPath()))), null, options, false);
            } catch (FileNotFoundException e) {
                bmr.SetResult(COLOR_GARNISH_STATE);
                e.printStackTrace();
            }
            if (!bmr.IsSuccess()) {
                return bmr.GetResult();
            }
            Matrix matrix;
            float[] MatrixValue;
            Bitmap OrgBitmap = bmr.GetBitmap();
            int iOrgWidth = options.outWidth;
            int iOrgHeight = options.outHeight;
            float fScale = ((float) garnishItem.GetHeight()) / ((float) iOrgHeight);
            File file = new File(garnishItem.GetGarnishPath().replace("file://", XmlPullParser.NO_NAMESPACE));
            this.LOG.m385i("garnishItem.GetScale()", String.valueOf(garnishItem.GetScale()));
            this.LOG.m385i("DrawORGEditPhoto fScale", String.valueOf(fScale));
            this.LOG.m385i("DrawORGEditPhoto iOrgWidth", String.valueOf(iOrgWidth));
            this.LOG.m385i("DrawORGEditPhoto iOrgHeight", String.valueOf(iOrgHeight));
            this.LOG.m385i("file", String.valueOf(file.length()));
            if (garnishItem.GetFilter().contains(GarnishItem.NON_FILTER) && !FilterColorValue.HaveModifyHSLCValue(garnishItem.GetHue(), garnishItem.GetSaturation(), garnishItem.GetLight(), garnishItem.GetContrast()) && !FilterColorValue.HaveModifyRGBValue(garnishItem.GetRed(), garnishItem.GetGreen(), garnishItem.GetBlue()) && fScale < 1.0f && garnishItem.GetScale() > 1.0f) {
                if (Runtime.getRuntime().freeMemory() > ((long) (iOrgWidth * iOrgHeight))) {
                    this.LOG.m384e("TrySuperiorQuality", "true");
                    options.inJustDecodeBounds = false;
                    try {
                        bmr = BitmapMonitor.CreateBitmap(new BufferedInputStream(this.m_Context.getContentResolver().openInputStream(Uri.parse("file://" + garnishItem.GetGarnishPath()))), null, options, false);
                    } catch (FileNotFoundException e2) {
                        bmr.SetResult(COLOR_GARNISH_STATE);
                        e2.printStackTrace();
                    }
                    if (!bmr.IsSuccess()) {
                        return bmr.GetResult();
                    }
                    OrgBitmap = bmr.GetBitmap();
                    matrix = new Matrix();
                    MatrixValue = new float[COPY_TOUCH_MODE];
                    matrix.set(garnishItem.GetMatrix());
                    matrix.getValues(MatrixValue);
                    this.LOG.m384e("1.ORG EDIT Photo SAVE X", String.valueOf(MatrixValue[SCALE_TOUCH_MODE]));
                    this.LOG.m384e("1.ORG EDIT Photo SAVE Y", String.valueOf(MatrixValue[PHOTO_ROTATE_TOUCH_MODE]));
                    float X = (MatrixValue[SCALE_TOUCH_MODE] * this.m_fViewScale) - (this.m_BoundX * this.m_fViewScale);
                    float Y = (MatrixValue[PHOTO_ROTATE_TOUCH_MODE] * this.m_fViewScale) - (this.m_BoundY * this.m_fViewScale);
                    this.LOG.m384e("2.ORG EDIT Photo SAVE X", String.valueOf(X));
                    this.LOG.m384e("2.ORG EDIT Photo SAVE Y", String.valueOf(Y));
                    MatrixValue[SCALE_TOUCH_MODE] = X / fScale;
                    MatrixValue[PHOTO_ROTATE_TOUCH_MODE] = Y / fScale;
                    this.LOG.m384e("3.ORG EDIT Photo SAVE X", String.valueOf(MatrixValue[SCALE_TOUCH_MODE]));
                    this.LOG.m384e("3.ORG EDIT Photo SAVE Y", String.valueOf(MatrixValue[PHOTO_ROTATE_TOUCH_MODE]));
                    matrix.setValues(MatrixValue);
                    matrix.postScale(fScale, fScale, 0.0f, 0.0f);
                    canvas.concat(matrix);
                    canvas.drawBitmap(OrgBitmap, 0.0f, 0.0f, paint);
                    OrgBitmap.recycle();
                    boTrySuperiorQuality = true;
                }
            }
            if (!boTrySuperiorQuality) {
                this.LOG.m384e("TrySuperiorQuality", "False");
                matrix = new Matrix();
                MatrixValue = new float[COPY_TOUCH_MODE];
                matrix.set(garnishItem.GetMatrix());
                matrix.getValues(MatrixValue);
                this.LOG.m384e("1.EDIT Photo SAVE X", String.valueOf(MatrixValue[SCALE_TOUCH_MODE]));
                this.LOG.m384e("1.EDIT Photo SAVE Y", String.valueOf(MatrixValue[PHOTO_ROTATE_TOUCH_MODE]));
                this.LOG.m384e("1.EDIT Photo SAVE m_BoundX", String.valueOf(this.m_BoundX));
                this.LOG.m384e("1.EDIT Photo SAVE m_BoundY", String.valueOf(this.m_BoundY));
                this.LOG.m384e("1.EDIT Photo SAVE m_fViewScale", String.valueOf(this.m_fViewScale));
                MatrixValue[SCALE_TOUCH_MODE] = AdjustCoordinates((MatrixValue[SCALE_TOUCH_MODE] * this.m_fViewScale) - (this.m_BoundX * this.m_fViewScale));
                MatrixValue[PHOTO_ROTATE_TOUCH_MODE] = AdjustCoordinates((MatrixValue[PHOTO_ROTATE_TOUCH_MODE] * this.m_fViewScale) - (this.m_BoundY * this.m_fViewScale));
                this.LOG.m384e("2.EDIT Photo SAVE X", String.valueOf(MatrixValue[SCALE_TOUCH_MODE]));
                this.LOG.m384e("2.EDIT Photo SAVE Y", String.valueOf(MatrixValue[PHOTO_ROTATE_TOUCH_MODE]));
                matrix.setValues(MatrixValue);
                canvas.concat(matrix);
                bmr = garnishItem.GetBitmapWithDefaultMaxOutputLimit(this.m_iOutputDefaultMaxShort, this.m_iOutputDefaultMaxLong);
                if (!bmr.IsSuccess()) {
                    return bmr.GetResult();
                }
                Bitmap bmp = bmr.GetBitmap();
                DrawFilter(bmp, garnishItem);
                int iBiasLeft = NONE_TOUCH_MODE;
                int iBiasTop = NONE_TOUCH_MODE;
                if (bmr.GetScale() == 1.0d) {
                    iBiasLeft = 10;
                    iBiasTop = 10;
                    this.LOG.m385i("Adjust canvas", "w:" + canvas.getWidth());
                    this.LOG.m385i("Adjust bmp", "w:" + bmp.getWidth());
                    this.LOG.m385i("Adjust canvas", "left:" + 10);
                    this.LOG.m385i("Adjust canvas", "top:" + 10);
                }
                canvas.drawBitmap(bmp, (float) iBiasLeft, (float) iBiasTop, paint);
                bmp.recycle();
            }
        }
        return NONE_TOUCH_MODE;
    }

    public BitmapMonitorResult GetEditPhoto(int iWidth, int iHeight, boolean boForceDirection) {
        BitmapMonitorResult IMGBmr = BitmapMonitor.CreateBitmap(iWidth, iHeight, Config.ARGB_8888);
        this.LOG.m385i("GetEditPhoto", "IMGBmr:" + IMGBmr.GetResult());
        if (!IMGBmr.IsSuccess()) {
            return IMGBmr;
        }
        Bitmap IMG = IMGBmr.GetBitmap();
        synchronized (this.m_Lock) {
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            paint.setAntiAlias(true);
            Canvas canvas = new Canvas(IMG);
            canvas.drawColor(-1);
            Iterator it = this.m_GarnishManager.iterator();
            while (it.hasNext()) {
                BitmapMonitorResult bmr;
                GarnishItem garnishItem = (GarnishItem) it.next();
                if (!(garnishItem.GetType() == PHOTO_ROTATE_TOUCH_MODE || garnishItem.GetType() == PHOTO_DRAG_TOUCH_MODE || garnishItem.GetType() == BRUSH_TOUCH_MODE || garnishItem.GetType() == ROLLER_TOUCH_MODE)) {
                    canvas.save();
                    this.LOG.m385i("GetEditPhoto", "GetType:" + garnishItem.GetType());
                    if (garnishItem.GetType() == EDIT_PHOTO_LAYER) {
                        int iResult = DrawORGEditPhoto(garnishItem, canvas);
                        this.LOG.m385i("GetEditPhoto", "iResult:" + iResult);
                        if (iResult != 0) {
                            IMGBmr.SetResult(iResult);
                            return IMGBmr;
                        }
                    }
                    Matrix matrix = new Matrix();
                    float[] MatrixValue = new float[COPY_TOUCH_MODE];
                    matrix.set(garnishItem.GetMatrix());
                    matrix.getValues(MatrixValue);
                    this.LOG.m384e("1.SAVE X", String.valueOf(MatrixValue[SCALE_TOUCH_MODE]));
                    this.LOG.m384e("1.SAVE Y", String.valueOf(MatrixValue[PHOTO_ROTATE_TOUCH_MODE]));
                    MatrixValue[SCALE_TOUCH_MODE] = AdjustCoordinates((MatrixValue[SCALE_TOUCH_MODE] * this.m_fViewScale) - (this.m_BoundX * this.m_fViewScale));
                    MatrixValue[PHOTO_ROTATE_TOUCH_MODE] = AdjustCoordinates((MatrixValue[PHOTO_ROTATE_TOUCH_MODE] * this.m_fViewScale) - (this.m_BoundY * this.m_fViewScale));
                    matrix.setValues(MatrixValue);
                    this.LOG.m384e("2.SAVE X", String.valueOf(MatrixValue[SCALE_TOUCH_MODE]));
                    this.LOG.m384e("2.SAVE Y", String.valueOf(MatrixValue[PHOTO_ROTATE_TOUCH_MODE]));
                    canvas.concat(matrix);
                    bmr = garnishItem.GetBitmapWithDefaultMaxOutputLimit(this.m_iOutputDefaultMaxShort, this.m_iOutputDefaultMaxLong);
                    if (bmr.IsSuccess()) {
                        Bitmap bmp = bmr.GetBitmap();
                        DrawFilter(bmp, garnishItem);
                        canvas.drawBitmap(bmp, 0.0f, 0.0f, paint);
                        bmp.recycle();
                    } else {
                        return bmr;
                    }
                    canvas.restore();
                }
            }
            if (boForceDirection && iWidth > iHeight) {
                bmr = BitmapMonitor.CreateBitmap(iHeight, iWidth, Config.ARGB_8888);
                if (bmr.IsSuccess()) {
                    Canvas canvas2 = new Canvas(bmr.GetBitmap());
                    canvas2.save();
                    canvas2.rotate(90.0f, (float) (iHeight / SCALE_TOUCH_MODE), (float) (iWidth / SCALE_TOUCH_MODE));
                    canvas2.drawBitmap(IMG, (float) ((iHeight - iWidth) / SCALE_TOUCH_MODE), (float) ((-(iHeight - iWidth)) / SCALE_TOUCH_MODE), paint);
                    canvas2.restore();
                    IMG.recycle();
                    IMGBmr = bmr;
                } else {
                    return bmr;
                }
            }
            return IMGBmr;
        }
    }

    public BitmapMonitorResult GetGSGarnish(int iWidth, int iHeight, boolean boForceDirection) {
        BitmapMonitorResult IMGBmr = BitmapMonitor.CreateBitmap(iWidth, iHeight, Config.ARGB_8888);
        if (!IMGBmr.IsSuccess()) {
            return IMGBmr;
        }
        Bitmap IMG = IMGBmr.GetBitmap();
        synchronized (this.m_Lock) {
            BitmapMonitorResult bmr;
            boolean boGSEdit = false;
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            paint.setAntiAlias(true);
            Canvas canvas = new Canvas(IMG);
            Iterator it = this.m_GarnishManager.iterator();
            while (it.hasNext()) {
                GarnishItem garnishItem = (GarnishItem) it.next();
                if (!(garnishItem.GetType() == 0 || garnishItem.GetType() == EDIT_PHOTO_LAYER || garnishItem.GetType() == ZOOM_TOUCH_MODE || garnishItem.GetType() == SCALE_TOUCH_MODE || garnishItem.GetType() == PAN_TOUCH_MODE || garnishItem.GetType() == ROLLER_TOUCH_MODE)) {
                    canvas.save();
                    Matrix matrix = new Matrix();
                    float[] MatrixValue = new float[COPY_TOUCH_MODE];
                    matrix.set(garnishItem.GetMatrix());
                    matrix.getValues(MatrixValue);
                    MatrixValue[SCALE_TOUCH_MODE] = AdjustCoordinates((MatrixValue[SCALE_TOUCH_MODE] * this.m_fViewScale) - (this.m_BoundX * this.m_fViewScale));
                    MatrixValue[PHOTO_ROTATE_TOUCH_MODE] = AdjustCoordinates((MatrixValue[PHOTO_ROTATE_TOUCH_MODE] * this.m_fViewScale) - (this.m_BoundY * this.m_fViewScale));
                    matrix.setValues(MatrixValue);
                    canvas.concat(matrix);
                    bmr = garnishItem.GetBitmapWithDefaultMaxOutputLimit(this.m_iOutputDefaultMaxShort, this.m_iOutputDefaultMaxLong);
                    if (bmr.IsSuccess()) {
                        Bitmap bmp = bmr.GetBitmap();
                        canvas.drawBitmap(bmp, 0.0f, 0.0f, paint);
                        bmp.recycle();
                        canvas.restore();
                        boGSEdit = true;
                    } else {
                        return bmr;
                    }
                }
            }
            if (boForceDirection && iWidth > iHeight) {
                bmr = BitmapMonitor.CreateBitmap(iHeight, iWidth, Config.ARGB_8888);
                if (bmr.IsSuccess()) {
                    Canvas canvas2 = new Canvas(bmr.GetBitmap());
                    canvas2.save();
                    canvas2.rotate(90.0f, (float) (iHeight / SCALE_TOUCH_MODE), (float) (iWidth / SCALE_TOUCH_MODE));
                    canvas2.drawBitmap(IMG, (float) ((iHeight - iWidth) / SCALE_TOUCH_MODE), (float) ((-(iHeight - iWidth)) / SCALE_TOUCH_MODE), paint);
                    canvas2.restore();
                    IMG.recycle();
                    IMGBmr = bmr;
                } else {
                    return bmr;
                }
            }
            if (!boGSEdit) {
                IMG.recycle();
            }
            return IMGBmr;
        }
    }

    public BitmapMonitorResult GetEditPhotoWithLimit(int iWidth, int iHeight) {
        BitmapMonitorResult IMGBmr = BitmapMonitor.CreateBitmap(iWidth, iHeight, Config.ARGB_8888);
        if (!IMGBmr.IsSuccess()) {
            return IMGBmr;
        }
        Bitmap IMG = IMGBmr.GetBitmap();
        synchronized (this.m_Lock) {
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            paint.setAntiAlias(true);
            Canvas canvas = new Canvas(IMG);
            Iterator it = this.m_GarnishManager.iterator();
            while (it.hasNext()) {
                GarnishItem garnishItem = (GarnishItem) it.next();
                canvas.save();
                if (garnishItem.GetType() == EDIT_PHOTO_LAYER) {
                    Matrix matrix = new Matrix();
                    float[] MatrixValue = new float[COPY_TOUCH_MODE];
                    matrix.set(garnishItem.GetMatrix());
                    matrix.getValues(MatrixValue);
                    MatrixValue[SCALE_TOUCH_MODE] = AdjustCoordinates((MatrixValue[SCALE_TOUCH_MODE] * this.m_fViewScale) - (this.m_BoundX * this.m_fViewScale));
                    MatrixValue[PHOTO_ROTATE_TOUCH_MODE] = AdjustCoordinates((MatrixValue[PHOTO_ROTATE_TOUCH_MODE] * this.m_fViewScale) - (this.m_BoundY * this.m_fViewScale));
                    matrix.setValues(MatrixValue);
                    canvas.concat(matrix);
                    BitmapMonitorResult bmr = garnishItem.GetBitmap(iWidth, iHeight);
                    if (bmr.IsSuccess()) {
                        Bitmap bmp = bmr.GetBitmap();
                        DrawFilter(bmp, garnishItem);
                        canvas.drawBitmap(bmp, 0.0f, 0.0f, paint);
                        bmp.recycle();
                    } else {
                        return bmr;
                    }
                }
                canvas.restore();
            }
            return IMGBmr;
        }
    }

    public BitmapMonitorResult GetGSMaskWithLimit(int iWidth, int iHeight) {
        BitmapMonitorResult IMGBmr = BitmapMonitor.CreateBitmap(iWidth, iHeight, Config.ARGB_8888);
        if (!IMGBmr.IsSuccess()) {
            return IMGBmr;
        }
        Bitmap IMG = IMGBmr.GetBitmap();
        synchronized (this.m_Lock) {
            boolean boGSEdit = false;
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            paint.setAntiAlias(true);
            Canvas canvas = new Canvas(IMG);
            Iterator it = this.m_GarnishManager.iterator();
            while (it.hasNext()) {
                GarnishItem garnishItem = (GarnishItem) it.next();
                if (garnishItem.GetType() == BRUSH_TOUCH_MODE) {
                    canvas.save();
                    Matrix matrix = new Matrix();
                    float[] MatrixValue = new float[COPY_TOUCH_MODE];
                    matrix.set(garnishItem.GetMatrix());
                    matrix.getValues(MatrixValue);
                    MatrixValue[SCALE_TOUCH_MODE] = AdjustCoordinates((MatrixValue[SCALE_TOUCH_MODE] * this.m_fViewScale) - (this.m_BoundX * this.m_fViewScale));
                    MatrixValue[PHOTO_ROTATE_TOUCH_MODE] = AdjustCoordinates((MatrixValue[PHOTO_ROTATE_TOUCH_MODE] * this.m_fViewScale) - (this.m_BoundY * this.m_fViewScale));
                    matrix.setValues(MatrixValue);
                    canvas.concat(matrix);
                    BitmapMonitorResult bmr = garnishItem.GetBitmap(iWidth, iHeight);
                    if (bmr.IsSuccess()) {
                        Bitmap bmp = bmr.GetBitmap();
                        canvas.drawBitmap(bmp, 0.0f, 0.0f, paint);
                        bmp.recycle();
                        canvas.restore();
                        boGSEdit = true;
                        if (!boGSEdit) {
                            IMG.recycle();
                        }
                        return IMGBmr;
                    }
                    return bmr;
                }
            }
            if (boGSEdit) {
                IMG.recycle();
            }
            return IMGBmr;
        }
    }

    public BitmapMonitorResult GetPhotoWithDrawViewMask(int iType, Bitmap maskBmp, int iWidth, int iHeight, boolean boForceDirection) {
        BitmapMonitorResult IMGBmr = new BitmapMonitorResult();
        if (maskBmp == null) {
            IMGBmr.SetResult(COLOR_GARNISH_STATE);
            return IMGBmr;
        }
        if (iType == EDIT_PHOTO_LAYER) {
            IMGBmr = GetEditPhotoWithLimit(iWidth, iHeight);
        } else if (iType != BRUSH_TOUCH_MODE) {
            return IMGBmr;
        } else {
            IMGBmr = GetGSMaskWithLimit(iWidth, iHeight);
        }
        if (!IMGBmr.IsSuccess()) {
            return IMGBmr;
        }
        Canvas canvasMask = new Canvas(IMGBmr.GetBitmap());
        Paint drawViewMaskPaint = new Paint();
        drawViewMaskPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvasMask.drawBitmap(maskBmp, 0.0f, 0.0f, drawViewMaskPaint);
        drawViewMaskPaint.setXfermode(null);
        return IMGBmr;
    }

    public BitmapMonitorResult GetGSGarnishAndEditPhoto(int iWidth, int iHeight) {
        BitmapMonitorResult editBmr = GetEditPhoto(iWidth, iHeight, false);
        if (!editBmr.IsSuccess()) {
            return editBmr;
        }
        Bitmap editBmp = editBmr.GetBitmap();
        if (!HaveGSGarnish()) {
            return editBmr;
        }
        BitmapMonitorResult maskBmr = GetGSGarnish(iWidth, iHeight, false);
        if (!maskBmr.IsSuccess()) {
            return maskBmr;
        }
        CropMask(editBmp, maskBmr.GetBitmap());
        return editBmr;
    }

    private void CropMask(Bitmap bmp, Bitmap maskBmp) {
        Canvas c = new Canvas(bmp);
        Paint paint = new Paint(EDIT_PHOTO_LAYER);
        c.drawBitmap(bmp, 0.0f, 0.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        c.drawBitmap(maskBmp, 0.0f, 0.0f, paint);
    }

    public boolean SaveGarnish2XML(String strXMLPath) {
        String strXML = GarnishItemXMLCreator.WriteGarnishXML(this.m_GarnishManager);
        this.LOG.m385i(strXML, " SaveGarnish2XML");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(strXMLPath, false));
            bw.write(strXML);
            bw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int SaveNonORGPathGarnishItam(String strSaveFolder) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        Iterator it = this.m_GarnishManager.iterator();
        while (it.hasNext()) {
            GarnishItem garnishItem = (GarnishItem) it.next();
            if (garnishItem.GetFromType() == 0 && (garnishItem.GetType() == SCALE_TOUCH_MODE || garnishItem.GetType() == ZOOM_TOUCH_MODE || garnishItem.GetType() == PAN_TOUCH_MODE || garnishItem.GetType() == PHOTO_ROTATE_TOUCH_MODE || garnishItem.GetType() == PHOTO_DRAG_TOUCH_MODE || garnishItem.GetType() == BRUSH_TOUCH_MODE)) {
                try {
                    bmr = garnishItem.GetBitmapWithDefaultMaxOutputLimit(this.m_iOutputDefaultMaxShort, this.m_iOutputDefaultMaxLong);
                    if (!bmr.IsSuccess()) {
                        return bmr.GetResult();
                    }
                    Bitmap orgBmp = bmr.GetBitmap();
                    OutputStream fos = new FileOutputStream(new File(strSaveFolder + "/" + String.valueOf(garnishItem.GetID()) + PringoConvenientConst.PRINBIZ_BORDER_EXT));
                    OutputStream outputStream;
                    try {
                        orgBmp.compress(CompressFormat.PNG, NONTHING_STATE, fos);
                        fos.close();
                        orgBmp.recycle();
                        garnishItem.SetGarnishPath(strSaveFolder + "/" + String.valueOf(garnishItem.GetID()) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                        garnishItem.SetFromType(SCALE_TOUCH_MODE);
                        outputStream = fos;
                    } catch (FileNotFoundException e) {
                        FileNotFoundException e2 = e;
                        outputStream = fos;
                        bmr.SetResult(COLOR_GARNISH_STATE);
                        e2.printStackTrace();
                    } catch (IOException e3) {
                        IOException e4 = e3;
                        outputStream = fos;
                        bmr.SetResult(NONTHING_STATE);
                        e4.printStackTrace();
                    }
                } catch (FileNotFoundException e5) {
                    e2 = e5;
                    bmr.SetResult(COLOR_GARNISH_STATE);
                    e2.printStackTrace();
                } catch (IOException e6) {
                    e4 = e6;
                    bmr.SetResult(NONTHING_STATE);
                    e4.printStackTrace();
                }
            }
        }
        return NONE_TOUCH_MODE;
    }

    public boolean IsEdit() {
        return this.m_boEdit;
    }

    public void SetEdit(boolean boEdit) {
        this.m_boEdit = boEdit;
    }

    public void SetEdit(int iType) {
        if (iType == SCALE_TOUCH_MODE || iType == ZOOM_TOUCH_MODE || iType == PAN_TOUCH_MODE || iType == PHOTO_ROTATE_TOUCH_MODE || iType == PHOTO_DRAG_TOUCH_MODE || iType == EDIT_PHOTO_LAYER) {
            SetEdit(true);
        }
    }

    public PointF GetBoundOffset(float dx, float dy, float zoom) {
        PointF retOffset = new PointF();
        Iterator it = this.m_GarnishManager.iterator();
        while (it.hasNext()) {
            GarnishItem garnishItem = (GarnishItem) it.next();
            if (garnishItem.GetType() == 0) {
                float fRight = (float) garnishItem.GetUIBitmap().getWidth();
                float fBttton = (float) garnishItem.GetUIBitmap().getHeight();
                Path p = new Path();
                p.moveTo(0.0f, 0.0f);
                p.lineTo(0.0f, fBttton);
                p.lineTo(fRight, fBttton);
                p.lineTo(fRight, 0.0f);
                p.close();
                RectF rectF = new RectF();
                p.computeBounds(rectF, true);
                p.transform(garnishItem.GetPreMatrix(garnishItem.GetX() + dx, garnishItem.GetY() + dy, zoom));
                p.computeBounds(rectF, true);
                if (rectF.left > this.m_BoundX) {
                    retOffset.x = this.m_BoundX - rectF.left;
                } else if (rectF.right < this.m_BoundX + this.m_fEditAreaWidth) {
                    retOffset.x = (this.m_BoundX + this.m_fEditAreaWidth) - rectF.right;
                }
                if (rectF.top > this.m_BoundY) {
                    retOffset.y = this.m_BoundY - rectF.top;
                } else if (rectF.bottom < this.m_BoundY + this.m_fEditAreaHeight) {
                    retOffset.y = (this.m_BoundY + this.m_fEditAreaHeight) - rectF.bottom;
                }
                return retOffset;
            }
        }
        return retOffset;
    }

    public PointF GetBoundOffset(float centerX, float centerY, float scale, float zoom) {
        PointF retOffset = new PointF();
        Iterator it = this.m_GarnishManager.iterator();
        while (it.hasNext()) {
            GarnishItem garnishItem = (GarnishItem) it.next();
            if (garnishItem.GetType() == 0) {
                float fRight = (float) garnishItem.GetUIBitmap().getWidth();
                float fBttton = (float) garnishItem.GetUIBitmap().getHeight();
                Path p = new Path();
                p.moveTo(0.0f, 0.0f);
                p.lineTo(0.0f, fBttton);
                p.lineTo(fRight, fBttton);
                p.lineTo(fRight, 0.0f);
                p.close();
                float diffX = centerX - garnishItem.GetX();
                float diffY = centerY - garnishItem.GetY();
                float dx = -((diffX * scale) - diffX);
                float dy = -((diffY * scale) - diffY);
                RectF rectF = new RectF();
                p.computeBounds(rectF, true);
                p.transform(garnishItem.GetPreMatrix(garnishItem.GetX() + dx, garnishItem.GetY() + dy, zoom));
                p.computeBounds(rectF, true);
                if (rectF.left > this.m_BoundX) {
                    retOffset.x = this.m_BoundX - rectF.left;
                } else if (rectF.right < this.m_BoundX + this.m_fEditAreaWidth) {
                    retOffset.x = (this.m_BoundX + this.m_fEditAreaWidth) - rectF.right;
                }
                if (rectF.top > this.m_BoundY) {
                    retOffset.y = this.m_BoundY - rectF.top;
                } else if (rectF.bottom < this.m_BoundY + this.m_fEditAreaHeight) {
                    retOffset.y = (this.m_BoundY + this.m_fEditAreaHeight) - rectF.bottom;
                }
                return retOffset;
            }
        }
        return retOffset;
    }

    public void SetBrushSize(float fSize) {
        this.m_BrushManager.SetBrushSize(fSize);
    }

    public float GetBrushSize() {
        return this.m_BrushManager.GetBrushSize();
    }

    public float GetMaxBrushSize(int iType) {
        return this.m_BrushManager.GetMaxBrushSize(iType);
    }

    public void SetBrushColor(int iColor) {
        this.m_BrushManager.SetBrushColor(iColor);
    }

    public int GetBrushColor() {
        return this.m_BrushManager.GetBrushColor();
    }

    public void SetBrushType(int iType) {
        this.m_BrushManager.SetBrushType(iType);
    }

    public int GetBrushType() {
        return this.m_BrushManager.GetBrushType();
    }

    public boolean HaveBrush(int iType) {
        return this.m_BrushManager.HaveBrush(iType);
    }

    public Region GetBrushRegion(int iType) {
        return this.m_BrushManager.GetBrushRegion(iType);
    }

    public void GetBrushBitmap(Bitmap bmp, int x, int y, int iType) {
        this.m_BrushManager.GetBrushBitmap(bmp, x, y, iType);
    }

    public void AddRoller(Bitmap bmp) {
        this.m_RollerManager.AddRollerBitmap(bmp);
    }

    public void ResetRollerBitmap() {
        this.m_RollerManager.ResetRollerBitmap();
    }

    public float GetMaxRollerSize(int iType) {
        return this.m_RollerManager.GetMaxRollerSize(iType);
    }

    public void SetRollerType(int iType) {
        this.m_RollerManager.SetRollerType(iType);
    }

    public int GetRollerType() {
        return this.m_RollerManager.GetRollerType();
    }

    public void SetRollerMethod(int iMethod) {
        this.m_RollerManager.SetRollerMethod(iMethod);
    }

    public boolean HaveRoller(int iType) {
        return this.m_RollerManager.HaveRoller(iType);
    }

    public Region GetRollerRegion(int iType) {
        return this.m_RollerManager.GetRollerRegion(iType);
    }

    public void GetRollerBitmap(Bitmap bmp, int x, int y, int iType) {
        this.m_RollerManager.GetRollerBitmap(bmp, x, y, iType);
    }

    public void Recovery() {
        this.m_FocusGarnish = null;
        if (HaveDrawViewListener()) {
            this.m_DrawViewListener.OnMissFocusGarnish();
        }
        this.m_GarnishItemHistoryManager.RunRecord(this.m_GarnishManager);
        if (this.m_FocusGarnish == null) {
            GarnishItem garnishItem = this.m_GarnishItemHistoryManager.GetNextRecord(this.m_GarnishManager);
            if (garnishItem != null) {
                this.m_FocusGarnish = garnishItem;
                if (HaveDrawViewListener()) {
                    this.m_DrawViewListener.OnFocusGarnish();
                }
                MoveFocusGarnishItemToTop(this.m_FocusGarnish);
                SetFocusGarnishCompose(GarnishItem.GetComposeGarnishItem(this.m_GarnishManager, this.m_FocusGarnish));
            }
            invalidate();
        }
    }

    public void Reflash() {
        this.m_FocusGarnish = null;
        if (HaveDrawViewListener()) {
            this.m_DrawViewListener.OnMissFocusGarnish();
        }
        for (int i = this.m_GarnishManager.size() - 1; i >= 0; i--) {
            GarnishItem garnishItem = (GarnishItem) this.m_GarnishManager.get(i);
            if (garnishItem.GetType() == PHOTO_DRAG_TOUCH_MODE || garnishItem.GetType() == PAN_TOUCH_MODE || garnishItem.GetType() == ZOOM_TOUCH_MODE) {
                this.m_GarnishManager.remove(garnishItem);
                this.m_GarnishItemHistoryManager.ReflashAction(garnishItem);
                garnishItem.Clear();
            }
        }
        invalidate();
    }

    public void ClearFocusGarnishCompose(GarnishItem cGarnishItem) {
        if (cGarnishItem != null) {
            this.m_GarnishManager.remove(cGarnishItem);
            cGarnishItem.Clear();
        }
    }

    public void SetFocusGarnishCompose(GarnishItem cGarnishItem) {
        if (cGarnishItem == null) {
        }
    }

    public void SetFocusGarnishComposeAction(GarnishItem cGarnishItem) {
        if (cGarnishItem != null) {
            GarnishItem.SetGarnishComposeSameAction(this.m_FocusGarnish, cGarnishItem);
        }
    }

    public void SetFocusGarnishCompose_EditAction(GarnishItem cGarnishItem) {
        if (cGarnishItem != null) {
            this.m_GarnishItemHistoryManager.EditAction(cGarnishItem);
        }
    }

    public void SetFocusGarnishCompose_LayerMoveAction(GarnishItem cGarnishItem, boolean boUp) {
        if (cGarnishItem != null) {
            this.m_GarnishItemHistoryManager.LayerMoveAction(cGarnishItem, boUp);
        }
    }

    public void SetFocusGarnishCompose_DeleteAction(GarnishItem cGarnishItem) {
        if (cGarnishItem != null) {
            this.m_GarnishItemHistoryManager.DeleteAction(cGarnishItem);
        }
    }

    public void SetOutputDefaultMaxLimit(int iOutputDefaultMaxShort, int iOutputDefaultMaxLong) {
        this.m_iOutputDefaultMaxShort = iOutputDefaultMaxShort;
        this.m_iOutputDefaultMaxLong = iOutputDefaultMaxLong;
    }
}
