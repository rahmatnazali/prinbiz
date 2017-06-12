package com.hiti.plugins.common;

import android.view.View;

public abstract class OnRGBFilterColorSelectChangedListener extends OnFilterColorSelectChangedListener {
    public abstract void onBrightnessButtonClicked(View view);

    public abstract void onHueButtonClicked(View view);

    public abstract void onRGBColorChanged(float f, float f2, float f3);
}
