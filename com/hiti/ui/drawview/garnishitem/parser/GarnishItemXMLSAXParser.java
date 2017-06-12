package com.hiti.ui.drawview.garnishitem.parser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.PointF;
import android.net.Uri;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.ui.drawview.garnishitem.GarnishItem;
import com.hiti.utility.LogManager;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;

public class GarnishItemXMLSAXParser extends DefaultHandler {
    private LogManager LOG;
    private Context m_Context;
    private GarnishItem m_GarnishItem;
    private ArrayList<GarnishItem> m_GarnishItemList;
    private int m_iH4x6;
    private int m_iW4x6;
    private String m_preTag;

    public String GetPreTag() {
        return this.m_preTag;
    }

    public GarnishItemXMLSAXParser(Context context, int iW4x6, int iH4x6) {
        this.m_GarnishItemList = null;
        this.m_GarnishItem = null;
        this.m_Context = null;
        this.m_iW4x6 = 0;
        this.m_iH4x6 = 0;
        this.m_preTag = null;
        this.LOG = null;
        this.m_Context = context;
        this.m_preTag = null;
        this.m_iW4x6 = iW4x6;
        this.m_iH4x6 = iH4x6;
        this.LOG = new LogManager(0);
    }

    public ArrayList<GarnishItem> getGarnishItemList() {
        return this.m_GarnishItemList;
    }

    public void startDocument() throws SAXException {
        this.m_GarnishItemList = new ArrayList();
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("ITEM".equals(localName)) {
            String strGarnishPath = attributes.getValue(XmlPullParser.NO_NAMESPACE, "PATH");
            float sx = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "SX")).floatValue();
            float sy = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "SY")).floatValue();
            float x = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "X")).floatValue();
            float y = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "Y")).floatValue();
            int iType = Integer.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "TYPE")).intValue();
            float fViewScale = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "VIEWSCALE")).floatValue();
            float fScale = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "SCALE")).floatValue();
            float fDegree = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "DEGREE")).floatValue();
            long lID = Long.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "ID")).longValue();
            long lCID = Long.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "CID")).longValue();
            int iFromType = Integer.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "FROMTYPE")).intValue();
            String strFilter = attributes.getValue(XmlPullParser.NO_NAMESPACE, "FILTER");
            float fHue = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "HUE")).floatValue();
            float fSaturation = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "SATURATION")).floatValue();
            float fLight = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "LIGHT")).floatValue();
            float fContrast = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "CONTRAST")).floatValue();
            float fRed = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "RED")).floatValue();
            float fGreen = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "GREEN")).floatValue();
            float fBlue = Float.valueOf(attributes.getValue(XmlPullParser.NO_NAMESPACE, "BLUE")).floatValue();
            BitmapMonitorResult bmr = null;
            Options options = new Options();
            options.inJustDecodeBounds = false;
            if (strGarnishPath != null) {
                try {
                    this.LOG.m385i("Add GarnishItem From XML", strGarnishPath);
                    if (iType == 1) {
                        bmr = BitmapMonitor.CreateCroppedBitmapNew(this.m_Context, Uri.parse("file://" + strGarnishPath), this.m_iW4x6, this.m_iH4x6);
                        this.LOG.m385i("GetResult", XmlPullParser.NO_NAMESPACE + bmr.GetResult());
                    } else if (iFromType == 1) {
                        bmr = BitmapMonitor.CreateBitmap(this.m_Context.getAssets().open(strGarnishPath), false);
                    } else if (iFromType == 2) {
                        bmr = BitmapMonitor.CreateBitmap(new BufferedInputStream(this.m_Context.getContentResolver().openInputStream(Uri.parse("file://" + strGarnishPath))), null, options, false);
                    }
                    if (bmr.IsSuccess()) {
                        Bitmap thumbnails = bmr.GetBitmap();
                        this.m_GarnishItem = new GarnishItem(this.m_Context, iType);
                        if (this.m_GarnishItem.InitUIView(thumbnails, new PointF(sx, sy), fViewScale, strGarnishPath, iFromType) != 0) {
                            this.m_GarnishItem = null;
                            thumbnails.recycle();
                            return;
                        }
                        thumbnails.recycle();
                        this.m_GarnishItem.SetID(lID);
                        this.m_GarnishItem.SetComposeID(lCID);
                        this.m_GarnishItem.SetRotateMatrix(fDegree);
                        this.m_GarnishItem.SetDegree(fDegree);
                        this.m_GarnishItem.SetScaleMatrix(fScale, fScale);
                        this.m_GarnishItem.SetScale(fScale);
                        this.m_GarnishItem.SetTransMatrix(x, y);
                        this.m_GarnishItem.SetX(x);
                        this.m_GarnishItem.SetY(y);
                        this.m_GarnishItem.SetFromType(iFromType);
                        this.m_GarnishItem.SetFilter(strFilter);
                        this.m_GarnishItem.SetHue(fHue);
                        this.m_GarnishItem.SetSaturation(fSaturation);
                        this.m_GarnishItem.SetLight(fLight);
                        this.m_GarnishItem.SetContrast(fContrast);
                        this.m_GarnishItem.SetRed(fRed);
                        this.m_GarnishItem.SetGreen(fGreen);
                        this.m_GarnishItem.SetBlue(fBlue);
                    } else {
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        this.m_preTag = localName;
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("ITEM".equals(localName) && this.m_GarnishItem != null) {
            this.m_GarnishItemList.add(this.m_GarnishItem);
            this.m_GarnishItem = null;
        }
        this.m_preTag = null;
    }
}
