package com.hiti.ui.drawview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import java.util.ArrayList;
import java.util.Iterator;

public class RollerRegion {
    private ArrayList<RollerBitmap> m_RollerBitmapArrayList;
    private int m_RollerMethod;
    private Paint m_RollerPaint;
    private Path m_RollerPath;
    private ArrayList<PointF> m_RollerPointF;
    private Region m_RollerRegion;
    private int m_RollerType;

    class RollerBitmap {
        Bitmap m_Bmp;
        int m_iDiagonal;

        RollerBitmap(Bitmap bmp) {
            this.m_Bmp = bmp;
            this.m_iDiagonal = (int) Math.sqrt((double) ((bmp.getWidth() * bmp.getWidth()) + (bmp.getHeight() * bmp.getHeight())));
        }
    }

    RollerRegion() {
        this.m_RollerRegion = null;
        this.m_RollerPaint = null;
        this.m_RollerPath = null;
        this.m_RollerPointF = null;
        this.m_RollerBitmapArrayList = null;
        this.m_RollerType = -1;
        this.m_RollerMethod = -1;
        this.m_RollerPath = new Path();
        this.m_RollerRegion = new Region();
        this.m_RollerPointF = new ArrayList();
        this.m_RollerBitmapArrayList = new ArrayList();
        this.m_RollerPaint = new Paint();
        this.m_RollerPaint.setColor(-1);
        this.m_RollerPaint.setStrokeWidth(1.0f);
        this.m_RollerPaint.setAntiAlias(true);
        this.m_RollerPaint.setStyle(Style.STROKE);
        this.m_RollerPaint.setStrokeJoin(Join.ROUND);
        this.m_RollerPaint.setStrokeCap(Cap.ROUND);
        this.m_RollerType = 0;
        this.m_RollerMethod = 1;
    }

    Paint GetRollerPaint() {
        return this.m_RollerPaint;
    }

    void SetRollerMethod(int iMethod) {
        this.m_RollerMethod = iMethod;
    }

    int GetRollerMethod() {
        return this.m_RollerMethod;
    }

    void SetRollerType(int iType) {
        this.m_RollerType = iType;
    }

    int GetRollerType() {
        return this.m_RollerType;
    }

    float GetRollerSize() {
        int iMaxSize = 0;
        for (int i = 0; i < this.m_RollerBitmapArrayList.size(); i++) {
            if (iMaxSize < ((RollerBitmap) this.m_RollerBitmapArrayList.get(i)).m_iDiagonal) {
                iMaxSize = ((RollerBitmap) this.m_RollerBitmapArrayList.get(i)).m_iDiagonal;
            }
        }
        return (float) iMaxSize;
    }

    Region GetRegion() {
        RectF rectF = new RectF();
        this.m_RollerPath.computeBounds(rectF, true);
        int left = (int) rectF.left;
        int top = (int) rectF.top;
        int right = (int) rectF.right;
        int bottom = (int) rectF.bottom;
        if (left == 0 && top == 0 && right == 0 && bottom == 0) {
            if (this.m_RollerPointF.size() > 0) {
                left = (int) ((PointF) this.m_RollerPointF.get(0)).x;
                top = (int) ((PointF) this.m_RollerPointF.get(0)).y;
                this.m_RollerRegion.set(left, top, left + 1, top + 1);
            }
        } else if (left == right || top == bottom) {
            left = (int) ((PointF) this.m_RollerPointF.get(0)).x;
            top = (int) ((PointF) this.m_RollerPointF.get(0)).y;
            this.m_RollerRegion.set(left, top, left + 1, top + 1);
        } else {
            this.m_RollerRegion.set(left, top, right, bottom);
        }
        return this.m_RollerRegion;
    }

    void Clear(boolean boClearBitmap) {
        this.m_RollerPaint = null;
        this.m_RollerPath = null;
        this.m_RollerRegion = null;
        this.m_RollerPointF.clear();
        this.m_RollerPointF = null;
        if (boClearBitmap) {
            int i = 0;
            while (i < this.m_RollerBitmapArrayList.size()) {
                if (!(((RollerBitmap) this.m_RollerBitmapArrayList.get(i)).m_Bmp == null || ((RollerBitmap) this.m_RollerBitmapArrayList.get(i)).m_Bmp.isRecycled())) {
                    ((RollerBitmap) this.m_RollerBitmapArrayList.get(i)).m_Bmp.recycle();
                    ((RollerBitmap) this.m_RollerBitmapArrayList.get(i)).m_Bmp = null;
                }
                i++;
            }
        }
        this.m_RollerBitmapArrayList.clear();
    }

    int GetPointCount() {
        return this.m_RollerPointF.size();
    }

    void AddPoint(PointF point) {
        this.m_RollerPointF.add(point);
    }

    PointF GetPoint(int iIndex) {
        if (GetPointCount() < iIndex) {
            return (PointF) this.m_RollerPointF.get(iIndex);
        }
        return null;
    }

    void PathMoveTo(float x, float y) {
        this.m_RollerPath.moveTo(x, y);
        AddPoint(new PointF(x, y));
    }

    void PathLineTo(float x, float y) {
        this.m_RollerPath.lineTo(x, y);
        AddPoint(new PointF(x, y));
    }

    void AddBitmap(Bitmap bmp) {
        if (bmp != null) {
            this.m_RollerBitmapArrayList.add(new RollerBitmap(bmp));
        }
    }

    Bitmap GetBitmap(int iIndex) {
        if (iIndex < this.m_RollerBitmapArrayList.size()) {
            return ((RollerBitmap) this.m_RollerBitmapArrayList.get(iIndex)).m_Bmp;
        }
        return null;
    }

    int GetBitmapDiagonal(int iIndex) {
        if (iIndex < this.m_RollerBitmapArrayList.size()) {
            return ((RollerBitmap) this.m_RollerBitmapArrayList.get(iIndex)).m_iDiagonal;
        }
        return -1;
    }

    void DrawRoller(Canvas canvas) {
        if (this.m_RollerMethod == 1) {
            RollerMethodTypeAlternation(canvas);
        }
    }

    void RollerMethodTypeAlternation(Canvas canvas) {
        PointF preCenterPointF = null;
        int iNext = 0;
        int iMod = this.m_RollerBitmapArrayList.size();
        int iPreDist = 0;
        for (int i = 0; i < this.m_RollerPointF.size(); i++) {
            iNext %= iMod;
            Bitmap bmp = ((RollerBitmap) this.m_RollerBitmapArrayList.get(iNext)).m_Bmp;
            int iWidth = bmp.getWidth();
            int iHeight = bmp.getHeight();
            PointF centerPointF = (PointF) this.m_RollerPointF.get(i);
            int iDist = iWidth / 2;
            if (i > 0) {
                iDist = (int) Spacing(preCenterPointF.x, preCenterPointF.y, centerPointF.x, centerPointF.y);
            }
            if (iDist >= (iWidth / 2) + iPreDist) {
                canvas.save();
                canvas.translate((float) ((-iWidth) / 2), (float) ((-iHeight) / 2));
                canvas.drawBitmap(bmp, centerPointF.x, centerPointF.y, this.m_RollerPaint);
                canvas.restore();
                iNext++;
                preCenterPointF = centerPointF;
                iPreDist = iWidth / 2;
            }
        }
    }

    public float Spacing(float x, float y, float dx, float dy) {
        float x2 = x - dx;
        float y2 = y - dy;
        return (float) Math.sqrt((double) ((x2 * x2) + (y2 * y2)));
    }

    boolean ContainBitmap(Bitmap bmp) {
        boolean boRet = false;
        Iterator it = this.m_RollerBitmapArrayList.iterator();
        while (it.hasNext()) {
            if (((RollerBitmap) it.next()).m_Bmp == bmp) {
                boRet = true;
            }
        }
        return boRet;
    }
}
