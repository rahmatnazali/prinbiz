package com.hiti.plugins.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public class FilterColorSelector extends FrameLayout {
    private int R_DRAWABLE_button_filter_color_select_cancel;
    private int R_DRAWABLE_button_filter_color_select_ok;
    private int R_ID_filter_color_selector_painter;
    private int R_ID_m_OKButton;
    private int R_ID_m_ResetButton;
    private int R_LAYOUT_view_filter_color_selector;
    private FilterColorPainter m_ColorPainter;
    private OnColorScrollerChangedListener m_ColorPainterListener;
    private OnFilterColorSelectChangedListener m_ColorSelectorOutListener;
    private Button m_OKButton;
    private Button m_ResetButton;

    /* renamed from: com.hiti.plugins.common.FilterColorSelector.2 */
    class C02462 implements OnClickListener {
        C02462() {
        }

        public void onClick(View v) {
            FilterColorSelector.this.onResetButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.plugins.common.FilterColorSelector.3 */
    class C02473 implements OnClickListener {
        C02473() {
        }

        public void onClick(View v) {
            FilterColorSelector.this.onOKButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.plugins.common.FilterColorSelector.1 */
    class C07401 extends OnColorScrollerChangedListener {
        C07401() {
        }

        public void onColorChanged(float value, int colorType) {
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
            if (FilterColorSelector.this.m_ColorSelectorOutListener != null) {
                FilterColorSelector.this.m_ColorSelectorOutListener.onColorChanged(fHue, fSaturation, fLight, fContrast);
            }
        }
    }

    private void GetResourceID(Context context) {
        this.R_LAYOUT_view_filter_color_selector = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "view_filter_color_selector");
        this.R_ID_filter_color_selector_painter = ResourceSearcher.getId(context, RS_TYPE.ID, "filter_color_selector_painter");
        this.R_ID_m_ResetButton = ResourceSearcher.getId(context, RS_TYPE.ID, "m_ResetButton");
        this.R_ID_m_OKButton = ResourceSearcher.getId(context, RS_TYPE.ID, "m_OKButton");
        this.R_DRAWABLE_button_filter_color_select_ok = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "button_filter_color_select_ok");
        this.R_DRAWABLE_button_filter_color_select_cancel = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "button_filter_color_select_cancel");
    }

    public FilterColorSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterColorSelector(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        GetResourceID(context);
        this.m_ColorPainterListener = new C07401();
        View v = inflate(context, this.R_LAYOUT_view_filter_color_selector, this);
        this.m_ColorPainter = (FilterColorPainter) v.findViewById(this.R_ID_filter_color_selector_painter);
        this.m_ColorPainter.setOnColorChangedListener(this.m_ColorPainterListener);
        this.m_ResetButton = (Button) v.findViewById(this.R_ID_m_ResetButton);
        this.m_ResetButton.setOnClickListener(new C02462());
        this.m_OKButton = (Button) v.findViewById(this.R_ID_m_OKButton);
        this.m_OKButton.setOnClickListener(new C02473());
    }

    public void setOnColorChangedListener(OnFilterColorSelectChangedListener outListener) {
        this.m_ColorSelectorOutListener = outListener;
    }

    public void SetHSLCAndPostion(float fHue, float fSaturation, float fLight, float fContrast) {
        this.m_ColorPainter.SetHSLCAndPostion(fHue, fSaturation, fLight, fContrast);
    }

    public void onResetButtonClicked(View v) {
        if (this.m_ColorSelectorOutListener != null) {
            this.m_ColorSelectorOutListener.onResetButtonClicked(v);
        }
    }

    public void onOKButtonClicked(View v) {
        if (this.m_ColorSelectorOutListener != null) {
            this.m_ColorSelectorOutListener.onOKButtonClicked(v);
        }
    }

    public void SetOKButtonStatus(boolean boOk) {
        if (boOk) {
            this.m_OKButton.setBackgroundResource(this.R_DRAWABLE_button_filter_color_select_ok);
        } else {
            this.m_OKButton.setBackgroundResource(this.R_DRAWABLE_button_filter_color_select_cancel);
        }
    }
}
