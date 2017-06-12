package com.hiti.ui.drawview.garnishitem.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.utility.FileUtility;
import com.hiti.utility.PringoConvenientConst;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

public class GarnishItemUtility {
    public static final int PARSE_BORDER = 1;
    public static final int PARSE_GARNISH = 2;
    public static final int PARSE_ROLLER = 3;
    public static final int PARSE_SNOW_GLOBE = 4;

    public static boolean IsGSGarnish(String strFileName) {
        boolean boRet = false;
        if (strFileName.contains("pgs_")) {
            boRet = true;
        }
        if (strFileName.contains("pys_")) {
            return true;
        }
        return boRet;
    }

    public static String GetGarnishFolderName(String strFileName, int boParseWhich) {
        String strFolderName = null;
        String strStart;
        if (boParseWhich == PARSE_BORDER) {
            if (strFileName.contains("tba_")) {
                strStart = "tba_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.length());
            } else if (strFileName.contains("tbs_")) {
                strStart = "tbs_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.length());
            } else if (strFileName.contains("tbc_")) {
                strStart = "tbc_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.length());
            }
            return strFolderName != null ? strFolderName.replace(PringoConvenientConst.PRINBIZ_BORDER_EXT, XmlPullParser.NO_NAMESPACE) : strFolderName;
        } else if (boParseWhich == PARSE_GARNISH) {
            if (strFileName.contains("tga_")) {
                strStart = "tga_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.lastIndexOf("_"));
            } else if (strFileName.contains("tgs_")) {
                strStart = "tgs_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.lastIndexOf("_"));
            } else if (strFileName.contains("tgc_")) {
                strStart = "tgc_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.lastIndexOf("_"));
            } else if (strFileName.contains("ctg_")) {
                strStart = "ctg_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.length());
            }
            if (strFolderName != null) {
                return strFolderName.replace(PringoConvenientConst.PRINBIZ_BORDER_EXT, XmlPullParser.NO_NAMESPACE);
            }
            return strFolderName;
        } else if (boParseWhich == PARSE_ROLLER) {
            if (strFileName.contains("tra_")) {
                strStart = "tra_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.lastIndexOf("_"));
            } else if (strFileName.contains("trs_")) {
                strStart = "trs_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.lastIndexOf("_"));
            } else if (strFileName.contains("trc_")) {
                strStart = "trc_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.lastIndexOf("_"));
            }
            if (strFolderName != null) {
                return strFolderName.substring(strFolderName.indexOf("_") + PARSE_BORDER, strFolderName.length());
            }
            return strFolderName;
        } else if (boParseWhich != PARSE_SNOW_GLOBE) {
            return null;
        } else {
            if (strFileName.contains("tya_")) {
                strStart = "tya_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.lastIndexOf("_"));
            } else if (strFileName.contains("tys_")) {
                strStart = "tys_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.lastIndexOf("_"));
            } else if (strFileName.contains("tyc_")) {
                strStart = "tyc_";
                strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.lastIndexOf("_"));
            }
            if (strFolderName != null) {
                return strFolderName.substring(strFolderName.indexOf("_") + PARSE_BORDER, strFolderName.length());
            }
            return strFolderName;
        }
    }

    public static ArrayList<String> GetAllGarnishPictureName(String strFileName, int iParseWhich) {
        ArrayList<String> AllPictureName = new ArrayList();
        String strFolderName = GetGarnishFolderName(strFileName, iParseWhich);
        if (iParseWhich == PARSE_BORDER) {
            if (strFileName.contains("tba_")) {
                AllPictureName.add("pbc_" + strFolderName + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                AllPictureName.add("pbs_" + strFolderName + PringoConvenientConst.PRINBIZ_BORDER_EXT);
            } else if (strFileName.contains("tbs_")) {
                AllPictureName.add("pbs_" + strFolderName + PringoConvenientConst.PRINBIZ_BORDER_EXT);
            } else if (strFileName.contains("tbc_")) {
                AllPictureName.add("pbc_" + strFolderName + PringoConvenientConst.PRINBIZ_BORDER_EXT);
            }
        } else if (iParseWhich == PARSE_GARNISH) {
            if (strFileName.contains("tga_")) {
                strPrefix = "pgc_";
                for (i = 0; i < GetGarnishAssembleCount(strFileName, true); i += PARSE_BORDER) {
                    AllPictureName.add(strPrefix + strFolderName + "_" + (i + PARSE_BORDER) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                }
                strPrefix = "pgs_";
                for (i = 0; i < GetGarnishAssembleCount(strFileName, false); i += PARSE_BORDER) {
                    AllPictureName.add(strPrefix + strFolderName + "_" + (i + PARSE_BORDER) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                }
            } else if (strFileName.contains("tgs_")) {
                strPrefix = "pgs_";
                for (i = 0; i < GetGarnishAssembleCount(strFileName, false); i += PARSE_BORDER) {
                    AllPictureName.add(strPrefix + strFolderName + "_" + (i + PARSE_BORDER) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                }
            } else if (strFileName.contains("tgc_")) {
                strPrefix = "pgc_";
                for (i = 0; i < GetGarnishAssembleCount(strFileName, true); i += PARSE_BORDER) {
                    AllPictureName.add(strPrefix + strFolderName + "_" + (i + PARSE_BORDER) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                }
            }
        } else if (iParseWhich == PARSE_ROLLER) {
            if (strFileName.contains("tra_")) {
                strPrefix = "prc_";
                for (i = 0; i < GetGarnishAssembleCount(strFileName, true); i += PARSE_BORDER) {
                    AllPictureName.add(strPrefix + strFolderName + "_" + (i + PARSE_BORDER) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                }
                strPrefix = "prs_";
                for (i = 0; i < GetGarnishAssembleCount(strFileName, false); i += PARSE_BORDER) {
                    AllPictureName.add(strPrefix + strFolderName + "_" + (i + PARSE_BORDER) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                }
            } else if (strFileName.contains("trs_")) {
                strPrefix = "prs_";
                for (i = 0; i < GetGarnishAssembleCount(strFileName, false); i += PARSE_BORDER) {
                    AllPictureName.add(strPrefix + strFolderName + "_" + (i + PARSE_BORDER) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                }
            } else if (strFileName.contains("trc_")) {
                strPrefix = "prc_";
                for (i = 0; i < GetGarnishAssembleCount(strFileName, true); i += PARSE_BORDER) {
                    AllPictureName.add(strPrefix + strFolderName + "_" + (i + PARSE_BORDER) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                }
            }
        } else if (iParseWhich == PARSE_SNOW_GLOBE) {
            if (strFileName.contains("tya_")) {
                strPrefix = "pyc_";
                for (i = 0; i < GetGarnishAssembleCount(strFileName, true); i += PARSE_BORDER) {
                    AllPictureName.add(strPrefix + strFolderName + "_" + (i + PARSE_BORDER) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                }
                strPrefix = "pys_";
                for (i = 0; i < GetGarnishAssembleCount(strFileName, false); i += PARSE_BORDER) {
                    AllPictureName.add(strPrefix + strFolderName + "_" + (i + PARSE_BORDER) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                }
            } else if (strFileName.contains("tys_")) {
                strPrefix = "pys_";
                for (i = 0; i < GetGarnishAssembleCount(strFileName, false); i += PARSE_BORDER) {
                    AllPictureName.add(strPrefix + strFolderName + "_" + (i + PARSE_BORDER) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                }
            } else if (strFileName.contains("tyc_")) {
                strPrefix = "pyc_";
                for (i = 0; i < GetGarnishAssembleCount(strFileName, true); i += PARSE_BORDER) {
                    AllPictureName.add(strPrefix + strFolderName + "_" + (i + PARSE_BORDER) + PringoConvenientConst.PRINBIZ_BORDER_EXT);
                }
            }
        }
        return AllPictureName;
    }

    public static int GetGarnishAssembleCount(String strFileName, boolean boColor) {
        String strCount;
        String strStart = "_";
        if (boColor) {
            strCount = strFileName.substring(strFileName.lastIndexOf(strStart) + PARSE_BORDER, strFileName.lastIndexOf(strStart) + PARSE_GARNISH);
        } else {
            strCount = strFileName.substring(strFileName.lastIndexOf(strStart) + PARSE_GARNISH, strFileName.lastIndexOf(strStart) + PARSE_ROLLER);
        }
        return Integer.parseInt(strCount);
    }

    public static String GetCatalogFolderName(String strFileName) {
        String strStart;
        if (strFileName.contains("ctg_")) {
            strStart = "ctg_";
            return strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.lastIndexOf("."));
        } else if (!strFileName.contains("ctb_")) {
            return null;
        } else {
            strStart = "ctb_";
            return strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.lastIndexOf("."));
        }
    }

    public static boolean ContainBorderFolderName(String strFileName) {
        if (strFileName.contains("tba_") || strFileName.contains("tbs_") || strFileName.contains("tbc_")) {
            return true;
        }
        return false;
    }

    public static int GetRollerMethod(String strFileName) {
        String strRollerMethod = null;
        String strStart;
        int iStart;
        if (strFileName.contains("tra_")) {
            strStart = "tra_";
            iStart = strFileName.indexOf(strStart) + strStart.length();
            strRollerMethod = strFileName.substring(iStart, strFileName.indexOf("_", iStart));
        } else if (strFileName.contains("trs_")) {
            strStart = "trs_";
            iStart = strFileName.indexOf(strStart) + strStart.length();
            strRollerMethod = strFileName.substring(iStart, strFileName.indexOf("_", iStart));
        } else if (strFileName.contains("trc_")) {
            strStart = "trc_";
            iStart = strFileName.indexOf(strStart) + strStart.length();
            strRollerMethod = strFileName.substring(iStart, strFileName.indexOf("_", iStart));
        }
        if (strRollerMethod != null) {
            return Integer.valueOf(strRollerMethod).intValue();
        }
        return -1;
    }

    public static int GetSnowGlobeMethod(String strFileName) {
        String strSnowGlobeMethod = null;
        String strStart;
        int iStart;
        if (strFileName.contains("tya_")) {
            strStart = "tya_";
            iStart = strFileName.indexOf(strStart) + strStart.length();
            strSnowGlobeMethod = strFileName.substring(iStart, strFileName.indexOf("_", iStart));
        } else if (strFileName.contains("tys_")) {
            strStart = "tys_";
            iStart = strFileName.indexOf(strStart) + strStart.length();
            strSnowGlobeMethod = strFileName.substring(iStart, strFileName.indexOf("_", iStart));
        } else if (strFileName.contains("tyc_")) {
            strStart = "tyc_";
            iStart = strFileName.indexOf(strStart) + strStart.length();
            strSnowGlobeMethod = strFileName.substring(iStart, strFileName.indexOf("_", iStart));
        }
        if (strSnowGlobeMethod != null) {
            return Integer.valueOf(strSnowGlobeMethod).intValue();
        }
        return -1;
    }

    public static String GetSnowGlobeCatalogFolderName(String strFileName) {
        if (!ContainSnowGlobeCatalogFolderName(strFileName)) {
            return null;
        }
        String strStart = "cty_";
        String strFolderName = strFileName.substring(strFileName.indexOf(strStart) + strStart.length(), strFileName.lastIndexOf("."));
        strStart = "_";
        return strFolderName.substring(strFolderName.indexOf(strStart) + strStart.length(), strFolderName.length());
    }

    public static boolean ContainSnowGlobeCatalogFolderName(String strFileName) {
        if (strFileName.contains("cty_")) {
            return true;
        }
        return false;
    }

    public static ArrayList<String> SearchGarnishConfig(String strUnZipFolderPath) {
        ArrayList<String> retStringList = new ArrayList();
        if (FileUtility.FileExist(strUnZipFolderPath + "/cgarnish" + "/config" + "/config.xml")) {
            retStringList.add(strUnZipFolderPath + "/cgarnish" + "/config" + "/config.xml");
        }
        if (FileUtility.FileExist(strUnZipFolderPath + PringoConvenientConst.V_BORDER_PATH + "/config" + "/config.xml")) {
            retStringList.add(strUnZipFolderPath + PringoConvenientConst.V_BORDER_PATH + "/config" + "/config.xml");
        }
        if (FileUtility.FileExist(strUnZipFolderPath + PringoConvenientConst.H_BORDER_PATH + "/config" + "/config.xml")) {
            retStringList.add(strUnZipFolderPath + PringoConvenientConst.H_BORDER_PATH + "/config" + "/config.xml");
        }
        if (FileUtility.FileExist(strUnZipFolderPath + "/vcollage" + "/config" + "/config.xml")) {
            retStringList.add(strUnZipFolderPath + "/vcollage" + "/config" + "/config.xml");
        }
        if (FileUtility.FileExist(strUnZipFolderPath + "/hcollage" + "/config" + "/config.xml")) {
            retStringList.add(strUnZipFolderPath + "/hcollage" + "/config" + "/config.xml");
        }
        if (FileUtility.FileExist(strUnZipFolderPath + "/vbusinesscard" + "/config" + "/config.xml")) {
            retStringList.add(strUnZipFolderPath + "/vbusinesscard" + "/config" + "/config.xml");
        }
        if (FileUtility.FileExist(strUnZipFolderPath + "/hbusinesscard" + "/config" + "/config.xml")) {
            retStringList.add(strUnZipFolderPath + "/hbusinesscard" + "/config" + "/config.xml");
        }
        if (FileUtility.FileExist(strUnZipFolderPath + "/vgreetingcard" + "/config" + "/config.xml")) {
            retStringList.add(strUnZipFolderPath + "/vgreetingcard" + "/config" + "/config.xml");
        }
        if (FileUtility.FileExist(strUnZipFolderPath + "/hgreetingcard" + "/config" + "/config.xml")) {
            retStringList.add(strUnZipFolderPath + "/hgreetingcard" + "/config" + "/config.xml");
        }
        return retStringList;
    }

    public static void ReplaceMaskColor(Context context, Bitmap maskBmp, int iColor) {
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(maskBmp.getWidth(), maskBmp.getHeight(), Config.ARGB_8888);
        if (bmr.IsSuccess()) {
            bmr.GetBitmap().eraseColor(iColor);
            Paint paint = new Paint();
            Canvas canvas = new Canvas(maskBmp);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bmr.GetBitmap(), 0.0f, 0.0f, paint);
        }
    }
}
