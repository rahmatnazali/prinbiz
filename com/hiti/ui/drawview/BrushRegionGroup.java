package com.hiti.ui.drawview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;
import android.graphics.Region.Op;
import java.util.ArrayList;
import java.util.Iterator;

public class BrushRegionGroup {
    public static final int BRUSH_COLOR_TYPE = 0;
    public static final int BRUSH_GS_TYPE = 1;
    int m_BrushColor;
    ArrayList<BrushRegion> m_BrushRegionArrayList;
    float m_BrushSize;
    int m_BrushType;
    Matrix m_Matrix;
    float[] m_MatrixValue;
    float m_fHeight;
    float m_fWidth;
    float m_fX;
    float m_fY;

    public BrushRegionGroup(float fX, float fY, float fWidth, float Height) {
        this.m_BrushRegionArrayList = null;
        this.m_Matrix = null;
        this.m_MatrixValue = null;
        this.m_fX = 0.0f;
        this.m_fY = 0.0f;
        this.m_fWidth = 0.0f;
        this.m_fHeight = 0.0f;
        this.m_Matrix = new Matrix();
        this.m_MatrixValue = new float[9];
        this.m_fX = fX;
        this.m_fY = fY;
        this.m_fWidth = fWidth;
        this.m_fHeight = Height;
        this.m_BrushRegionArrayList = new ArrayList();
    }

    public void SetBrushSize(float fSize) {
        this.m_BrushSize = fSize;
    }

    public float GetBrushSize() {
        return this.m_BrushSize;
    }

    public float GetMaxBrushSize(int iType) {
        float fMaxSize = 0.0f;
        Iterator it = this.m_BrushRegionArrayList.iterator();
        while (it.hasNext()) {
            BrushRegion brushRegion = (BrushRegion) it.next();
            if (brushRegion.GetBrushType() == iType && fMaxSize < brushRegion.GetBrushSize()) {
                fMaxSize = brushRegion.GetBrushSize();
            }
        }
        return fMaxSize;
    }

    public void SetBrushColor(int iColor) {
        this.m_BrushColor = iColor;
    }

    public int GetBrushColor() {
        return this.m_BrushColor;
    }

    public void SetBrushType(int iType) {
        this.m_BrushType = iType;
    }

    public int GetBrushType() {
        return this.m_BrushType;
    }

    public BrushRegion CreateBrushRegion() {
        BrushRegion brushRegion = new BrushRegion();
        brushRegion.SetBrushSize(GetBrushSize());
        brushRegion.SetBrushColor(GetBrushColor());
        brushRegion.SetBrushType(GetBrushType());
        this.m_BrushRegionArrayList.add(brushRegion);
        return brushRegion;
    }

    public BrushRegion GetLastBrushRegion() {
        if (this.m_BrushRegionArrayList.size() > 0) {
            return (BrushRegion) this.m_BrushRegionArrayList.get(this.m_BrushRegionArrayList.size() - 1);
        }
        return null;
    }

    public void SetZoom(float fZoom, float scale, float centerX, float centerY, PointF fOffsetBound) {
        float diffX = centerX - this.m_fX;
        float diffY = centerY - this.m_fY;
        float mPosX = (this.m_fX - ((diffX * scale) - diffX)) + fOffsetBound.x;
        float mPosY = (this.m_fY - ((diffY * scale) - diffY)) + fOffsetBound.y;
        this.m_Matrix.reset();
        this.m_Matrix.postTranslate((-this.m_fWidth) / 2.0f, (-this.m_fHeight) / 2.0f);
        this.m_Matrix.postScale(fZoom, fZoom);
        this.m_Matrix.postTranslate(mPosX, mPosY);
        this.m_fX = mPosX;
        this.m_fY = mPosY;
    }

    private PointF SetBrushTrans(float x, float y) {
        Matrix matrix = new Matrix();
        this.m_Matrix.invert(matrix);
        matrix.preTranslate(x, y);
        matrix.getValues(this.m_MatrixValue);
        return new PointF(this.m_MatrixValue[2], this.m_MatrixValue[5]);
    }

    public void PathMoveTo(BrushRegion brushRegion, float x, float y) {
        if (brushRegion != null) {
            PointF retPointF = SetBrushTrans(x, y);
            brushRegion.PathMoveTo(retPointF.x, retPointF.y);
        }
    }

    public void PathLineTo(BrushRegion brushRegion, float x, float y) {
        if (brushRegion != null) {
            PointF retPointF = SetBrushTrans(x, y);
            brushRegion.PathLineTo(retPointF.x, retPointF.y);
        }
    }

    public void DrawBrush(Canvas canvas) {
        Iterator it = this.m_BrushRegionArrayList.iterator();
        while (it.hasNext()) {
            BrushRegion brushRegion = (BrushRegion) it.next();
            canvas.save();
            Path path = brushRegion.GetBrushPath();
            canvas.concat(this.m_Matrix);
            canvas.drawPath(path, brushRegion.GetBrushPaint());
            canvas.restore();
        }
    }

    public void ClearBrush() {
        Iterator it = this.m_BrushRegionArrayList.iterator();
        while (it.hasNext()) {
            ((BrushRegion) it.next()).Clear();
        }
        this.m_BrushRegionArrayList.clear();
    }

    public boolean HaveBrush(int iType) {
        Iterator it = this.m_BrushRegionArrayList.iterator();
        while (it.hasNext()) {
            if (((BrushRegion) it.next()).GetBrushType() == iType) {
                return true;
            }
        }
        return false;
    }

    public boolean HaveBrush() {
        if (this.m_BrushRegionArrayList == null || this.m_BrushRegionArrayList.size() <= 0) {
            return false;
        }
        return true;
    }

    public Region GetBrushRegion(int iType) {
        if (this.m_BrushRegionArrayList.size() == 0) {
            return null;
        }
        Region retRegion = new Region();
        if (this.m_BrushRegionArrayList.size() == BRUSH_GS_TYPE) {
            if (((BrushRegion) this.m_BrushRegionArrayList.get(BRUSH_COLOR_TYPE)).GetBrushType() == iType) {
                retRegion = ((BrushRegion) this.m_BrushRegionArrayList.get(BRUSH_COLOR_TYPE)).GetRegion();
            }
            return retRegion;
        }
        Iterator it = this.m_BrushRegionArrayList.iterator();
        while (it.hasNext()) {
            BrushRegion brushRegion = (BrushRegion) it.next();
            if (brushRegion.GetBrushType() == iType) {
                retRegion.op(brushRegion.GetRegion(), Op.UNION);
            }
        }
        return retRegion;
    }

    public void GetBrushBitmap(Bitmap bmp, int x, int y, int iType) {
        Canvas canvas = new Canvas(bmp);
        canvas.translate((float) (-x), (float) (-y));
        Iterator it = this.m_BrushRegionArrayList.iterator();
        while (it.hasNext()) {
            BrushRegion brushRegion = (BrushRegion) it.next();
            if (brushRegion.GetBrushType() == iType) {
                canvas.save();
                canvas.drawPath(brushRegion.GetBrushPath(), brushRegion.GetBrushPaint());
                canvas.restore();
            }
        }
    }
}
