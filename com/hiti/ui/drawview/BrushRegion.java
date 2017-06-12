package com.hiti.ui.drawview;

import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import java.util.ArrayList;

public class BrushRegion {
    private Paint m_BrushPaint;
    private Path m_BrushPath;
    private ArrayList<PointF> m_BrushPointF;
    private Region m_BrushRegion;
    private int m_BrushType;

    BrushRegion() {
        this.m_BrushPaint = null;
        this.m_BrushPath = null;
        this.m_BrushPointF = null;
        this.m_BrushRegion = null;
        this.m_BrushType = -1;
        this.m_BrushPaint = new Paint();
        this.m_BrushPath = new Path();
        this.m_BrushRegion = new Region();
        this.m_BrushPointF = new ArrayList();
        this.m_BrushPaint.setAntiAlias(true);
        this.m_BrushPaint.setStyle(Style.STROKE);
        this.m_BrushPaint.setStrokeJoin(Join.ROUND);
        this.m_BrushPaint.setStrokeCap(Cap.ROUND);
    }

    void SetBrushType(int iType) {
        this.m_BrushType = iType;
    }

    int GetBrushType() {
        return this.m_BrushType;
    }

    void SetBrushColor(int iColor) {
        this.m_BrushPaint.setColor(iColor);
    }

    int GetBrushColor() {
        return this.m_BrushPaint.getColor();
    }

    void SetBrushSize(float fSize) {
        this.m_BrushPaint.setStrokeWidth(fSize);
    }

    float GetBrushSize() {
        return this.m_BrushPaint.getStrokeWidth();
    }

    void SetBrushPath(Path path) {
        this.m_BrushPath.reset();
        this.m_BrushPath.addPath(path);
    }

    Path GetBrushPath() {
        return this.m_BrushPath;
    }

    Paint GetBrushPaint() {
        return this.m_BrushPaint;
    }

    Region GetRegion() {
        RectF rectF = new RectF();
        this.m_BrushPath.computeBounds(rectF, true);
        this.m_BrushRegion.set((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        return this.m_BrushRegion;
    }

    void Clear() {
        this.m_BrushPaint = null;
        this.m_BrushPath = null;
        this.m_BrushRegion = null;
        this.m_BrushPointF.clear();
        this.m_BrushPointF = null;
    }

    int GetPointCount() {
        return this.m_BrushPointF.size();
    }

    void AddPoint(PointF point) {
        this.m_BrushPointF.add(point);
    }

    PointF GetPoint(int iIndex) {
        if (GetPointCount() < iIndex) {
            return (PointF) this.m_BrushPointF.get(iIndex);
        }
        return null;
    }

    void PathMoveTo(float x, float y) {
        this.m_BrushPath.moveTo(x, y);
        AddPoint(new PointF(x, y));
    }

    void PathLineTo(float x, float y) {
        this.m_BrushPath.lineTo(x, y);
        AddPoint(new PointF(x, y));
    }

    Path GetQuadPath() {
        Path path = null;
        if (this.m_BrushPointF.size() > 2) {
            path = new Path();
            for (int i = 0; i < this.m_BrushPointF.size(); i++) {
                PointF pointF = (PointF) this.m_BrushPointF.get(i);
                if (i == 0) {
                    path.moveTo(pointF.x, pointF.y);
                } else {
                    float dx = Math.abs(((PointF) this.m_BrushPointF.get(i - 1)).x - ((PointF) this.m_BrushPointF.get(i)).x);
                    float dy = Math.abs(((PointF) this.m_BrushPointF.get(i - 1)).y - ((PointF) this.m_BrushPointF.get(i)).y);
                    if (dx >= 3.0f || dy >= 3.0f) {
                        path.quadTo(((PointF) this.m_BrushPointF.get(i - 1)).x, ((PointF) this.m_BrushPointF.get(i - 1)).y, (((PointF) this.m_BrushPointF.get(i - 1)).x + ((PointF) this.m_BrushPointF.get(i)).x) / 2.0f, (((PointF) this.m_BrushPointF.get(i - 1)).y + ((PointF) this.m_BrushPointF.get(i)).y) / 2.0f);
                    } else if (i == this.m_BrushPointF.size() - 1) {
                        path.quadTo(((PointF) this.m_BrushPointF.get(i - 1)).x, ((PointF) this.m_BrushPointF.get(i - 1)).y, ((PointF) this.m_BrushPointF.get(i)).x, ((PointF) this.m_BrushPointF.get(i)).y);
                    } else {
                        path.lineTo(((PointF) this.m_BrushPointF.get(i)).x, ((PointF) this.m_BrushPointF.get(i)).y);
                    }
                }
            }
        }
        return path;
    }
}
