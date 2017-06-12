package com.hiti.webhiti;

import org.xmlpull.v1.XmlPullParser;

public class HitiWebValue_GetUpdateInfo {
    public String ElementID;
    public String FTP;
    public String Info;
    public String NewsetVersion;
    public String Password;
    public String Path;
    public String Response;
    public String Signature;
    public String TimeStamp;
    public String UpdateID;
    public String UserName;
    public String Version;

    public HitiWebValue_GetUpdateInfo() {
        this.Response = XmlPullParser.NO_NAMESPACE;
        this.Version = XmlPullParser.NO_NAMESPACE;
        this.UpdateID = XmlPullParser.NO_NAMESPACE;
        this.ElementID = XmlPullParser.NO_NAMESPACE;
        this.TimeStamp = XmlPullParser.NO_NAMESPACE;
        this.Signature = XmlPullParser.NO_NAMESPACE;
        this.NewsetVersion = XmlPullParser.NO_NAMESPACE;
        this.Info = XmlPullParser.NO_NAMESPACE;
        this.FTP = XmlPullParser.NO_NAMESPACE;
        this.Path = XmlPullParser.NO_NAMESPACE;
        this.UserName = XmlPullParser.NO_NAMESPACE;
        this.Password = XmlPullParser.NO_NAMESPACE;
    }

    public static boolean IsValid(HitiWebValue_GetUpdateInfo gui) {
        if (gui.Response == null || gui.Response.length() == 0 || gui.Response.toLowerCase().equals("false") || !gui.Response.toLowerCase().equals("true")) {
            return false;
        }
        return true;
    }
}
