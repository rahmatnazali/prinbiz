package com.hiti.plugins.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public class ColorSelector extends FrameLayout {
    private int R_COLOR_GS_COLOR;
    private int R_DRAWABLE_switch_off;
    private int R_DRAWABLE_switch_on;
    private int R_ID_color_history_painter;
    private int R_ID_color_selector_painter;
    private int R_ID_m_SilverPainterRelativeLayout;
    private int R_ID_m_UseSilverButton;
    private int R_LAYOUT_plugins_color_selector;
    private int color;
    private int color_type;
    private ColorHistoryPainter m_ColorHistoryPainter;
    private ColorPainter m_ColorPainter;
    private onColorChangedListener m_ColorPainterListener;
    private onColorChangedListener m_ColorSelectorOutListener;
    private RelativeLayout m_SilverPainterRelativeLayout;
    private Button m_UseSilverButton;
    private boolean m_boUseSilver;

    /* renamed from: com.hiti.plugins.common.ColorSelector.2 */
    class C02452 implements OnClickListener {
        C02452() {
        }

        public void onClick(View v) {
            ColorSelector.this.onUseSilverButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.plugins.common.ColorSelector.1 */
    class C07361 extends onColorChangedListener {
        C07361() {
        }

        public void onColorChanged(int color_changed, int type) {
            ColorSelector.this.color = color_changed;
            ColorSelector.this.color_type = type;
            if (ColorSelector.this.m_ColorSelectorOutListener != null) {
                ColorSelector.this.m_ColorSelectorOutListener.onColorChanged(color_changed, type);
            }
        }
    }

    private void GetResourceID(Context context) {
        this.R_LAYOUT_plugins_color_selector = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "plugins_color_selector");
        this.R_ID_color_selector_painter = ResourceSearcher.getId(context, RS_TYPE.ID, "color_selector_painter");
        this.R_ID_color_history_painter = ResourceSearcher.getId(context, RS_TYPE.ID, "color_history_painter");
        this.R_ID_m_SilverPainterRelativeLayout = ResourceSearcher.getId(context, RS_TYPE.ID, "m_SilverPainterRelativeLayout");
        this.R_ID_m_UseSilverButton = ResourceSearcher.getId(context, RS_TYPE.ID, "m_UseSilverButton");
        this.R_DRAWABLE_switch_on = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "switch_on");
        this.R_DRAWABLE_switch_off = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "switch_off");
        this.R_COLOR_GS_COLOR = ResourceSearcher.getId(context, RS_TYPE.COLOR, "GS_COLOR");
    }

    public ColorSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.color = -7829368;
        this.color_type = 0;
        init(context);
    }

    public ColorSelector(Context context) {
        super(context);
        this.color = -7829368;
        this.color_type = 0;
        init(context);
    }

    private void init(Context context) {
        GetResourceID(context);
        this.m_ColorPainterListener = new C07361();
        View v = inflate(context, this.R_LAYOUT_plugins_color_selector, this);
        this.m_ColorPainter = (ColorPainter) v.findViewById(this.R_ID_color_selector_painter);
        this.m_ColorPainter.setOnColorChangedListener(this.m_ColorPainterListener);
        this.m_ColorHistoryPainter = (ColorHistoryPainter) v.findViewById(this.R_ID_color_history_painter);
        this.m_ColorHistoryPainter.setOnColorChangedListener(this.m_ColorPainterListener);
        this.m_SilverPainterRelativeLayout = (RelativeLayout) v.findViewById(this.R_ID_m_SilverPainterRelativeLayout);
        this.m_UseSilverButton = (Button) v.findViewById(this.R_ID_m_UseSilverButton);
        this.m_UseSilverButton.setBackgroundResource(this.R_DRAWABLE_switch_off);
        this.m_boUseSilver = false;
        this.m_UseSilverButton.setOnClickListener(new C02452());
    }

    public int getColor() {
        return this.color;
    }

    public int getColorType() {
        return this.color_type;
    }

    public void setColor(int color, int type) {
        this.color = color;
        this.color_type = type;
        if (this.m_ColorSelectorOutListener != null) {
            this.m_ColorSelectorOutListener.onColorChanged(color, type);
        }
    }

    public void setOnColorChangedListener(onColorChangedListener outListener) {
        this.m_ColorSelectorOutListener = outListener;
    }

    public void ReflashCurrentColor() {
        this.m_ColorPainter.SetHSV(-1.0f, -1.0f, -1.0f);
    }

    public void NoSilverMode() {
        this.m_SilverPainterRelativeLayout.setVisibility(8);
    }

    public void onUseSilverButtonClicked(View v) {
        this.m_boUseSilver = !this.m_boUseSilver;
        if (this.m_boUseSilver) {
            this.m_UseSilverButton.setBackgroundResource(this.R_DRAWABLE_switch_on);
            this.m_ColorPainterListener.onColorChanged(getResources().getColor(this.R_COLOR_GS_COLOR), 1);
            this.m_ColorPainter.Enable(false);
            this.m_ColorHistoryPainter.SetColorHistoryViewEnable(false);
            return;
        }
        this.m_UseSilverButton.setBackgroundResource(this.R_DRAWABLE_switch_off);
        this.m_ColorPainter.Enable(true);
        ReflashCurrentColor();
        this.m_ColorHistoryPainter.SetColorHistoryViewEnable(true);
    }
}
