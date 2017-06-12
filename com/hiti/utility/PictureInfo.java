package com.hiti.utility;

import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.media.ExifInterface;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PictureInfo {
    public static String GetDateTime(String filePath) {
        try {
            String dateTime = new ExifInterface(filePath).getAttribute("DateTime");
            if (dateTime != null) {
                return DateToString(StringToDate(dateTime, "yyyy:MM:dd HH:mm"), "yyyy-MM-dd");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public static Date StringToDate(String stringDate, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String DateToString(Date date, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(date);
    }

    public static Point GetImageSize(String path) throws FileNotFoundException, IOException {
        FileInputStream in = new FileInputStream(path);
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, opts);
        in.close();
        return new Point(opts.outWidth, opts.outHeight);
    }

    public static boolean GetImgOrientation(Point imgSize) {
        if (imgSize.x >= imgSize.y) {
            return true;
        }
        return false;
    }
}
