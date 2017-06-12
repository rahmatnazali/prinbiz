package com.hiti.ui.drawview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Region;
import android.graphics.Region.Op;
import java.util.ArrayList;
import java.util.Iterator;

public class RollerRegionGroup {
    public static final int ROLLER_COLOR_TYPE = 0;
    public static final int ROLLER_GS_TYPE = 1;
    public static final int ROLLER_METHOD_ALTERNATION = 1;
    Matrix m_Matrix;
    float[] m_MatrixValue;
    ArrayList<Bitmap> m_RollerBitmapArrayList;
    int m_RollerMethod;
    ArrayList<RollerRegion> m_RollerRegionArrayList;
    int m_RollerType;
    float m_fHeight;
    float m_fWidth;
    float m_fX;
    float m_fY;

    public RollerRegionGroup(float fX, float fY, float fWidth, float Height) {
        this.m_RollerRegionArrayList = null;
        this.m_RollerBitmapArrayList = null;
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
        this.m_RollerRegionArrayList = new ArrayList();
        this.m_RollerBitmapArrayList = new ArrayList();
    }

    public float GetMaxRollerSize(int iType) {
        float fMaxSize = 0.0f;
        Iterator it = this.m_RollerRegionArrayList.iterator();
        while (it.hasNext()) {
            RollerRegion RollerRegion = (RollerRegion) it.next();
            if (RollerRegion.GetRollerType() == iType && fMaxSize < RollerRegion.GetRollerSize()) {
                fMaxSize = RollerRegion.GetRollerSize();
            }
        }
        return fMaxSize;
    }

    public void SetRollerType(int iType) {
        this.m_RollerType = iType;
    }

    public int GetRollerType() {
        return this.m_RollerType;
    }

    public void SetRollerMethod(int iMethod) {
        this.m_RollerMethod = iMethod;
    }

    public int GetRollerMethod() {
        return this.m_RollerMethod;
    }

    public void AddRollerBitmap(Bitmap bmp) {
        this.m_RollerBitmapArrayList.add(bmp);
    }

    public void ResetRollerBitmap() {
        this.m_RollerBitmapArrayList.clear();
    }

    public RollerRegion CreateRollerRegion() {
        RollerRegion RollerRegion = new RollerRegion();
        RollerRegion.SetRollerType(GetRollerType());
        RollerRegion.SetRollerMethod(GetRollerMethod());
        for (int i = ROLLER_COLOR_TYPE; i < this.m_RollerBitmapArrayList.size(); i += ROLLER_METHOD_ALTERNATION) {
            RollerRegion.AddBitmap((Bitmap) this.m_RollerBitmapArrayList.get(i));
        }
        this.m_RollerRegionArrayList.add(RollerRegion);
        return RollerRegion;
    }

    public RollerRegion GetLastRollerRegion() {
        if (this.m_RollerRegionArrayList.size() > 0) {
            return (RollerRegion) this.m_RollerRegionArrayList.get(this.m_RollerRegionArrayList.size() - 1);
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

    private PointF SetRollerTrans(float x, float y) {
        Matrix matrix = new Matrix();
        this.m_Matrix.invert(matrix);
        matrix.preTranslate(x, y);
        matrix.getValues(this.m_MatrixValue);
        return new PointF(this.m_MatrixValue[2], this.m_MatrixValue[5]);
    }

    public void PathMoveTo(RollerRegion RollerRegion, float x, float y) {
        if (RollerRegion != null) {
            PointF retPointF = SetRollerTrans(x, y);
            RollerRegion.PathMoveTo(retPointF.x, retPointF.y);
        }
    }

    public void PathLineTo(RollerRegion RollerRegion, float x, float y) {
        if (RollerRegion != null) {
            PointF retPointF = SetRollerTrans(x, y);
            RollerRegion.PathLineTo(retPointF.x, retPointF.y);
        }
    }

    public void DrawRoller(Canvas canvas) {
        Iterator it = this.m_RollerRegionArrayList.iterator();
        while (it.hasNext()) {
            RollerRegion RollerRegion = (RollerRegion) it.next();
            canvas.save();
            canvas.concat(this.m_Matrix);
            RollerRegion.DrawRoller(canvas);
            canvas.restore();
        }
    }

    public void ClearRoller() {
        Iterator it = this.m_RollerRegionArrayList.iterator();
        while (it.hasNext()) {
            ((RollerRegion) it.next()).Clear(true);
        }
        this.m_RollerRegionArrayList.clear();
    }

    public void ClearScreen() {
        Iterator it = this.m_RollerRegionArrayList.iterator();
        while (it.hasNext()) {
            RollerRegion RollerRegion = (RollerRegion) it.next();
            boolean boClear = true;
            Iterator it2 = this.m_RollerBitmapArrayList.iterator();
            while (it2.hasNext()) {
                if (RollerRegion.ContainBitmap((Bitmap) it2.next())) {
                    boClear = false;
                    break;
                }
            }
            RollerRegion.Clear(boClear);
        }
        this.m_RollerRegionArrayList.clear();
    }

    public boolean HaveRoller(int iType) {
        Iterator it = this.m_RollerRegionArrayList.iterator();
        while (it.hasNext()) {
            if (((RollerRegion) it.next()).GetRollerType() == iType) {
                return true;
            }
        }
        return false;
    }

    public boolean HaveRoller() {
        if (this.m_RollerRegionArrayList == null || this.m_RollerRegionArrayList.size() <= 0) {
            return false;
        }
        return true;
    }

    public Region GetRollerRegion(int iType) {
        if (this.m_RollerRegionArrayList.size() == 0) {
            return null;
        }
        Region retRegion = new Region();
        if (this.m_RollerRegionArrayList.size() == ROLLER_METHOD_ALTERNATION) {
            if (((RollerRegion) this.m_RollerRegionArrayList.get(ROLLER_COLOR_TYPE)).GetRollerType() == iType) {
                retRegion = ((RollerRegion) this.m_RollerRegionArrayList.get(ROLLER_COLOR_TYPE)).GetRegion();
            }
            return retRegion;
        }
        Iterator it = this.m_RollerRegionArrayList.iterator();
        while (it.hasNext()) {
            RollerRegion RollerRegion = (RollerRegion) it.next();
            if (RollerRegion.GetRollerType() == iType) {
                retRegion.op(RollerRegion.GetRegion(), Op.UNION);
            }
        }
        return retRegion;
    }

    public void GetRollerBitmap(Bitmap bmp, int x, int y, int iType) {
        Canvas canvas = new Canvas(bmp);
        canvas.translate((float) (-x), (float) (-y));
        Iterator it = this.m_RollerRegionArrayList.iterator();
        while (it.hasNext()) {
            RollerRegion RollerRegion = (RollerRegion) it.next();
            if (RollerRegion.GetRollerType() == iType) {
                canvas.save();
                RollerRegion.DrawRoller(canvas);
                canvas.restore();
            }
        }
    }
}
