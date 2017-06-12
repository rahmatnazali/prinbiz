package com.hiti.ui.collageview;

public class CollageInfo {
    public static float ATTR_VALUE_NO_BUTTON_POSITION;
    public static int ATTR_VALUE_NO_GROUP;
    private float m_fAddPhotoButtonLeft;
    private float m_fAddPhotoButtonTop;
    private float m_fHeight;
    private float m_fLeft;
    private float m_fTop;
    private float m_fWidth;
    private int m_iColorSelect;
    private int m_iGroup;
    private String m_strBiometricLinePhotoPath;
    private String m_strMaskPath;

    static {
        ATTR_VALUE_NO_GROUP = -1;
        ATTR_VALUE_NO_BUTTON_POSITION = -9999.0f;
    }

    CollageInfo() {
        this.m_fLeft = 0.0f;
        this.m_fTop = 0.0f;
        this.m_fHeight = 0.0f;
        this.m_fWidth = 0.0f;
        this.m_strMaskPath = null;
        this.m_iColorSelect = 0;
        this.m_fAddPhotoButtonLeft = ATTR_VALUE_NO_BUTTON_POSITION;
        this.m_fAddPhotoButtonTop = ATTR_VALUE_NO_BUTTON_POSITION;
        this.m_iGroup = ATTR_VALUE_NO_GROUP;
        this.m_strBiometricLinePhotoPath = null;
    }

    public void SetLeft(float fLeft) {
        this.m_fLeft = fLeft;
    }

    public void SetTop(float fTop) {
        this.m_fTop = fTop;
    }

    public void SetHeight(float fHeight) {
        this.m_fHeight = fHeight;
    }

    public void SetWidth(float fWidth) {
        this.m_fWidth = fWidth;
    }

    public void SetMaskPath(String strMaskPath) {
        this.m_strMaskPath = strMaskPath;
    }

    public void SetColorSelect(int iColorSelect) {
        this.m_iColorSelect = iColorSelect;
    }

    public void SetAddPhotoButtonLeft(float fAddPhotoButtonLeft) {
        this.m_fAddPhotoButtonLeft = fAddPhotoButtonLeft;
    }

    public void SetAddPhotoButtonTop(float fAddPhotoButtonTop) {
        this.m_fAddPhotoButtonTop = fAddPhotoButtonTop;
    }

    public void SetBiometricLinePhotoPath(String strBioLinePhotoPath) {
        this.m_strBiometricLinePhotoPath = strBioLinePhotoPath;
    }

    public float GetLeft() {
        return this.m_fLeft;
    }

    public float GetTop() {
        return this.m_fTop;
    }

    public float GetHeight() {
        return this.m_fHeight;
    }

    public float GetWidth() {
        return this.m_fWidth;
    }

    public String GetMaskPath() {
        return this.m_strMaskPath;
    }

    public int GetColorSelect() {
        return this.m_iColorSelect;
    }

    public float GetAddPhotoButtonLeft() {
        return this.m_fAddPhotoButtonLeft;
    }

    public float GetAddPhotoButtonTop() {
        return this.m_fAddPhotoButtonTop;
    }

    public int GetGroup() {
        return this.m_iGroup;
    }

    public void SetGroup(int iGroup) {
        this.m_iGroup = iGroup;
    }

    public String GetBiometricLinePhotoPath() {
        return this.m_strBiometricLinePhotoPath;
    }
}
