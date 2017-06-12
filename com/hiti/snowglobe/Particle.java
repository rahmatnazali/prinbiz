package com.hiti.snowglobe;

public class Particle {
    public static final int NON_BITMAP = -1;
    double m_dHorizontalSpeed;
    double m_dStartTime;
    double m_dVerticalSpeed;
    float m_fCurrentX;
    float m_fCurrentY;
    float m_fScale;
    float m_fStartX;
    float m_fStartY;
    int m_iAlpha;
    int m_iBmpIndex;
    int m_iID;
    int m_iType;

    public Particle(int iBmpIndex, int iType, float fScale, int iAlpha, double dVerticalSpeed, double dHorizontalSpeed, float fStartX, float fStartY, double dStartTime) {
        this.m_iID = NON_BITMAP;
        this.m_iType = iType;
        this.m_iBmpIndex = iBmpIndex;
        this.m_dVerticalSpeed = dVerticalSpeed;
        this.m_dHorizontalSpeed = dHorizontalSpeed;
        this.m_fStartX = fStartX;
        this.m_fStartY = fStartY;
        this.m_fCurrentX = fStartX;
        this.m_fCurrentY = fStartY;
        this.m_dStartTime = dStartTime;
        this.m_fScale = fScale;
        this.m_iAlpha = iAlpha;
    }
}
