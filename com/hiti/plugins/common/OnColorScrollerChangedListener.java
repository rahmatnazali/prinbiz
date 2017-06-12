package com.hiti.plugins.common;

public abstract class OnColorScrollerChangedListener {
    public static final int Color_Type_Normal = 0;
    public static final int Color_Type_Silver = 1;

    public abstract void onColorChanged(float f, float f2, float f3, float f4);

    public abstract void onColorChanged(float f, int i);
}
