package com.hiti.ui.drawview.garnishitem.parser;

import android.content.Context;
import com.hiti.ui.drawview.garnishitem.GarnishItem;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class GarnishItemXMLCreator {
    public static ArrayList<GarnishItem> ReadGarnishXML(InputStream inStream, Context context, int iW4x6, int iH4x6) throws Exception {
        SAXParser saxParse = SAXParserFactory.newInstance().newSAXParser();
        GarnishItemXMLSAXParser handler = new GarnishItemXMLSAXParser(context, iW4x6, iH4x6);
        saxParse.parse(inStream, handler);
        inStream.close();
        return handler.getGarnishItemList();
    }

    public static String WriteGarnishXML(ArrayList<GarnishItem> GarnishManager) {
        StringWriter stringWriter = new StringWriter();
        try {
            XmlSerializer xmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
            xmlSerializer.setOutput(stringWriter);
            xmlSerializer.startDocument("utf-8", Boolean.valueOf(true));
            xmlSerializer.startTag(null, "GARNISH");
            Iterator it = GarnishManager.iterator();
            while (it.hasNext()) {
                GarnishItem garnishItem = (GarnishItem) it.next();
                if (!(garnishItem.GetType() == 0 || garnishItem.GetGarnishPath() == null)) {
                    xmlSerializer.startTag(null, "ITEM");
                    xmlSerializer.attribute(null, "PATH", garnishItem.GetGarnishPath());
                    xmlSerializer.attribute(null, "SX", String.valueOf(garnishItem.GetStartX()));
                    xmlSerializer.attribute(null, "SY", String.valueOf(garnishItem.GetStartY()));
                    xmlSerializer.attribute(null, "X", String.valueOf(garnishItem.GetX()));
                    xmlSerializer.attribute(null, "Y", String.valueOf(garnishItem.GetY()));
                    xmlSerializer.attribute(null, "TYPE", String.valueOf(garnishItem.GetType()));
                    xmlSerializer.attribute(null, "VIEWSCALE", String.valueOf(garnishItem.GetViewScale()));
                    xmlSerializer.attribute(null, "SCALE", String.valueOf(garnishItem.GetScale()));
                    xmlSerializer.attribute(null, "DEGREE", String.valueOf(garnishItem.GetDegree()));
                    xmlSerializer.attribute(null, "ID", String.valueOf(garnishItem.GetID()));
                    xmlSerializer.attribute(null, "CID", String.valueOf(garnishItem.GetComposeID()));
                    xmlSerializer.attribute(null, "FROMTYPE", String.valueOf(garnishItem.GetFromType()));
                    xmlSerializer.attribute(null, "FILTER", garnishItem.GetFilter());
                    xmlSerializer.attribute(null, "HUE", String.valueOf(garnishItem.GetHue()));
                    xmlSerializer.attribute(null, "SATURATION", String.valueOf(garnishItem.GetSaturation()));
                    xmlSerializer.attribute(null, "LIGHT", String.valueOf(garnishItem.GetLight()));
                    xmlSerializer.attribute(null, "CONTRAST", String.valueOf(garnishItem.GetContrast()));
                    xmlSerializer.attribute(null, "RED", String.valueOf(garnishItem.GetRed()));
                    xmlSerializer.attribute(null, "GREEN", String.valueOf(garnishItem.GetGreen()));
                    xmlSerializer.attribute(null, "BLUE", String.valueOf(garnishItem.GetBlue()));
                    xmlSerializer.endTag(null, "ITEM");
                }
            }
            xmlSerializer.endTag(null, "GARNISH");
            xmlSerializer.endDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }
}
