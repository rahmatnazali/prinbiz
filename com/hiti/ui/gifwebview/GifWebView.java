package com.hiti.ui.gifwebview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class GifWebView extends WebView {
    public boolean m_IsAnimationOn;

    public GifWebView(Context context) {
        super(context);
        this.m_IsAnimationOn = true;
    }

    public GifWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m_IsAnimationOn = true;
    }

    public GifWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.m_IsAnimationOn = true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.m_IsAnimationOn) {
            return false;
        }
        return true;
    }
}
