package com.hiti.plugins.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public class ColorPainter_Scorller extends View {
    public static int BAR_CENTER;
    public static int BAR_LEFT;
    public static int BAR_RIGHT;
    private int R_DRAWABLE_color_selector_colour_circle;
    private OnColorScrollerChangedListener m_ColorScrollerOutListener;
    private Drawable m_ColourCircle;
    private boolean m_boEnable;
    private float m_fColourBottom;
    private float m_fColourLeft;
    private float m_fColourRight;
    private float m_fColourTop;
    private float m_fMaxValue;
    private float m_fX;
    private float m_fY;
    private int m_iHeight;
    private int m_iWidth;

    static {
        BAR_LEFT = 0;
        BAR_CENTER = 1;
        BAR_RIGHT = 2;
    }

    public ColorPainter_Scorller(Context context) {
        super(context);
        this.m_fY = 0.0f;
        this.m_iWidth = 0;
        this.m_iHeight = 0;
        this.m_fMaxValue = 0.0f;
        this.m_boEnable = true;
        init(context);
    }

    public ColorPainter_Scorller(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m_fY = 0.0f;
        this.m_iWidth = 0;
        this.m_iHeight = 0;
        this.m_fMaxValue = 0.0f;
        this.m_boEnable = true;
        init(context);
    }

    private void GetResourceID(Context context) {
        this.R_DRAWABLE_color_selector_colour_circle = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "color_selector_colour_circle");
    }

    private void init(Context context) {
        GetResourceID(context);
        this.m_ColourCircle = getResources().getDrawable(this.R_DRAWABLE_color_selector_colour_circle);
    }

    public void InitArea(int iWidth, int iHeight, float fColourLeft, float fColourTop, float fColourRight, float fColourBottom) {
        this.m_iWidth = iWidth;
        this.m_iHeight = iHeight;
        this.m_fColourLeft = fColourLeft;
        this.m_fColourRight = fColourRight;
        this.m_fColourTop = fColourTop;
        this.m_fColourBottom = fColourBottom;
        this.m_fMaxValue = this.m_fColourRight - this.m_fColourLeft;
    }

    public void SetInitPosition(int iPosition) {
        OnlyResetInitPosition(iPosition);
        UpdateColor();
        invalidate();
    }

    public void OnlyResetInitPosition(int iPosition) {
        if (iPosition == BAR_LEFT) {
            this.m_fX = 0.0f;
        }
        if (iPosition == BAR_CENTER) {
            this.m_fX = ((this.m_fColourRight - this.m_fColourLeft) / 2.0f) + this.m_fColourLeft;
        }
        if (iPosition == BAR_RIGHT) {
            this.m_fX = (float) ((int) this.m_fColourRight);
        }
        invalidate();
    }

    public void OnlySetPosition(float fPosition) {
        this.m_fX = this.m_fColourLeft + fPosition;
        invalidate();
    }

    public void onDraw(Canvas canvas) {
        this.m_fX = (float) ((int) Math.min(Math.max(this.m_fX, this.m_fColourLeft), this.m_fColourRight));
        this.m_fY = (float) ((int) Math.min(Math.max(this.m_fY, this.m_fColourTop), this.m_fColourBottom));
        this.m_ColourCircle.setBounds((int) (this.m_fX - ((float) (this.m_iHeight / 2))), (int) this.m_fColourTop, (int) (this.m_fX + ((float) (this.m_iHeight / 2))), (int) this.m_fColourBottom);
        this.m_ColourCircle.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (IsEnable()) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (this.m_fColourLeft < ((float) x) && ((float) x) < this.m_fColourRight) {
                this.m_fX = (float) x;
                this.m_fY = (float) y;
                UpdateColor();
            } else if (x > 0 && ((float) x) <= this.m_fColourLeft) {
                this.m_fX = 0.0f;
                this.m_fY = (float) y;
                UpdateColor();
            } else if (((float) x) > this.m_fColourRight && x <= this.m_iWidth) {
                this.m_fX = this.m_fColourRight;
                this.m_fY = (float) y;
                UpdateColor();
            }
            invalidate();
        }
        return true;
    }

    private void UpdateColor() {
        if (this.m_ColorScrollerOutListener != null) {
            this.m_ColorScrollerOutListener.onColorChanged(this.m_fX - this.m_fColourLeft, 0);
        }
    }

    public float GetMaxValue() {
        return this.m_fMaxValue;
    }

    public void setOnColorScrollerChangedListener(OnColorScrollerChangedListener outListener) {
        this.m_ColorScrollerOutListener = outListener;
    }

    public void Enable(boolean boEnable) {
        this.m_boEnable = boEnable;
    }

    public boolean IsEnable() {
        return this.m_boEnable;
    }
}
