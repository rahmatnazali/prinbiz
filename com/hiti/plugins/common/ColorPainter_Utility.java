package com.hiti.plugins.common;

public class ColorPainter_Utility {
    public static float PositionToHue(float x, float fMax) {
        if (fMax == 0.0f) {
            return 0.0f;
        }
        float i = fMax / 360.0f;
        if (i == 0.0f) {
            return 0.0f;
        }
        float fHue = x / i;
        if (fHue < 3.0f) {
            fHue = 0.0f;
        }
        if (fHue > 357.0f) {
            fHue = 360.0f;
        }
        return fHue;
    }

    public static float PositionToLight(float x, float fMax) {
        if (fMax == 0.0f) {
            return 0.0f;
        }
        float i = fMax / 1.0f;
        if (i == 0.0f) {
            return 0.0f;
        }
        float fPos = x / i;
        if (((double) fPos) < 0.1d) {
            fPos = 0.0f;
        }
        if (((double) fPos) > 0.97d) {
            fPos = 1.0f;
        }
        return fPos;
    }

    public static float PositionToSaturation(float x, float fMax) {
        return PositionToLight(x, fMax);
    }

    public static float PositionToFilterColor(float x, float fMax, int iRangeValue) {
        int iRange = iRangeValue;
        int iMid = iRange / 2;
        if (fMax == 0.0f) {
            return 0.0f;
        }
        float i = fMax / ((float) iRange);
        if (i == 0.0f) {
            return 0.0f;
        }
        float fPos = (x / i) - ((float) iMid);
        if (((double) fPos) < 0.1d && ((double) fPos) > -0.1d) {
            fPos = 0.0f;
        }
        if (((double) fPos) > ((double) iMid) - 0.3d) {
            fPos = (float) iMid;
        }
        if (((double) fPos) < ((double) (-iMid)) + 0.3d) {
            fPos = (float) (-iMid);
        }
        return fPos;
    }

    public static float FilterColorToPosition(float x, float fMax, int iRangeValue) {
        int iRange = iRangeValue;
        int iMid = iRange / 2;
        if (fMax == 0.0f) {
            return 0.0f;
        }
        float i = fMax / ((float) iRange);
        if (i == 0.0f) {
            return 0.0f;
        }
        float fPos = (x + ((float) iMid)) * i;
        if (fPos <= fMax) {
            return fPos;
        }
        return fMax;
    }
}
