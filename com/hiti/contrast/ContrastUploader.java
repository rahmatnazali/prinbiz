package com.hiti.contrast;

import android.content.Context;
import org.xmlpull.v1.XmlPullParser;

public class ContrastUploader {
    private String ENDL;
    private String END_TAG;
    private String MAIL_TAG;
    private String NAME_TAG;
    private String PHONE_TAG;
    private Context m_Context;

    public ContrastUploader(Context context) {
        this.NAME_TAG = "[Hiti_TAG_01]";
        this.MAIL_TAG = "[Hiti_TAG_02]";
        this.PHONE_TAG = "[Hiti_TAG_03]";
        this.END_TAG = "[Hiti_TAG_00]";
        this.ENDL = XmlPullParser.NO_NAMESPACE;
        this.m_Context = null;
        this.m_Context = context;
    }
}
