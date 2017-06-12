package com.hiti.plugins.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.hiti.trace.GlobalVariable_ColorHistoryInfo;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;
import java.util.ArrayList;
import java.util.Iterator;

public class ColorHistoryPainter extends LinearLayout {
    private int R_DRAWABLE_static_color_background;
    private onColorChangedListener m_ColorHistoryPainterOutListener;
    private ArrayList<View> m_ColorsDisplayViewList;
    private GlobalVariable_ColorHistoryInfo m_GVColorHistoryInfo;
    private int m_SelectedColor;
    private boolean m_boEnable;

    class OnClickListener_Color_Pick implements OnClickListener {
        private int color;

        public OnClickListener_Color_Pick(int color) {
            this.color = color;
        }

        public void onClick(View v) {
            if (ColorHistoryPainter.this.IsEnable()) {
                ColorHistoryPainter.this.m_SelectedColor = this.color;
                if (ColorHistoryPainter.this.m_ColorHistoryPainterOutListener != null) {
                    ColorHistoryPainter.this.m_ColorHistoryPainterOutListener.onColorChanged(this.color, 0);
                }
            }
        }
    }

    private void GetResourceID(Context context) {
        this.R_DRAWABLE_static_color_background = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "static_color_background");
    }

    public ColorHistoryPainter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m_ColorHistoryPainterOutListener = null;
        this.m_ColorsDisplayViewList = null;
        this.m_GVColorHistoryInfo = null;
        this.m_boEnable = true;
        init(context);
    }

    public ColorHistoryPainter(Context context) {
        super(context);
        this.m_ColorHistoryPainterOutListener = null;
        this.m_ColorsDisplayViewList = null;
        this.m_GVColorHistoryInfo = null;
        this.m_boEnable = true;
        init(context);
    }

    private void init(Context context) {
        GetResourceID(context);
        this.m_GVColorHistoryInfo = new GlobalVariable_ColorHistoryInfo(context);
        this.m_GVColorHistoryInfo.RestoreGlobalVariable();
        setOrientation(0);
        LayoutParams params = new LayoutParams(-1, -2);
        params.weight = 1.0f;
        params.setMargins(10, 10, 10, 10);
        params.gravity = 1;
        setLayoutParams(params);
        LayoutParams lrl = new LayoutParams(-1, -2);
        lrl.weight = 1.0f;
        lrl.gravity = 17;
        lrl.gravity = 1;
        RelativeLayout.LayoutParams riv = new RelativeLayout.LayoutParams(-2, -2);
        riv.setMargins(10, 10, 10, 10);
        this.m_ColorsDisplayViewList = new ArrayList();
        for (int i = 0; i < this.m_GVColorHistoryInfo.MAX_COLOR_SIZE; i++) {
            RelativeLayout rl = new RelativeLayout(context);
            rl.setLayoutParams(lrl);
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(riv);
            iv.setImageResource(this.R_DRAWABLE_static_color_background);
            iv.setBackgroundColor(this.m_GVColorHistoryInfo.GetColoeHistory(i));
            iv.setScaleType(ScaleType.FIT_XY);
            if (i < this.m_GVColorHistoryInfo.GetColorHistorySize()) {
                iv.setOnClickListener(new OnClickListener_Color_Pick(this.m_GVColorHistoryInfo.GetColoeHistory(i)));
            }
            iv.setTag(Integer.valueOf(this.m_GVColorHistoryInfo.GetColoeHistory(i)));
            this.m_ColorsDisplayViewList.add(iv);
            rl.addView(iv);
            addView(rl);
        }
    }

    public int GetColor() {
        return this.m_SelectedColor;
    }

    public void setOnColorChangedListener(onColorChangedListener outListener) {
        this.m_ColorHistoryPainterOutListener = outListener;
    }

    public void ReflashColorHistory() {
        for (int i = 0; i < this.m_GVColorHistoryInfo.MAX_COLOR_SIZE; i++) {
            ((View) this.m_ColorsDisplayViewList.get(i)).setBackgroundColor(this.m_GVColorHistoryInfo.GetColoeHistory(i));
            ((View) this.m_ColorsDisplayViewList.get(i)).invalidate();
            invalidate();
        }
    }

    public boolean IsEnable() {
        return this.m_boEnable;
    }

    public void SetColorHistoryViewEnable(boolean boEnable) {
        this.m_boEnable = boEnable;
        if (boEnable) {
            Iterator it = this.m_ColorsDisplayViewList.iterator();
            while (it.hasNext()) {
                View view = (View) it.next();
                view.setBackgroundColor(((Integer) view.getTag()).intValue());
            }
            return;
        }
        Iterator it2 = this.m_ColorsDisplayViewList.iterator();
        while (it2.hasNext()) {
            ((View) it2.next()).setBackgroundColor(-1);
        }
    }
}
