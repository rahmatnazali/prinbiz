package com.hiti.utility.permissionRequest;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.hiti.utility.LogManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public class PermissionAsk {
    public static final int ACCESS_LOCATION = 2;
    public static final int CAMERA = 3;
    public static final int IGNORE_BATTERY_OPTIMIZATIONS = 4;
    public static final int WRITE_EXTERNAL_STORAGE = 1;
    public static final int ask_not_yet = 1;
    public static final int asked_denied = 2;
    public static final int asked_grant = 3;
    LogManager LOG;
    String TAG;
    Map<Integer, Integer> mIsAskedMap;
    ArrayList<String> notGrantedPermissionList;
    ArrayList<Integer> permissionRequestList;

    public interface IPermission {
        void GetGrantState(int i, int i2);

        int SetLatterState(int i, int i2);
    }

    public PermissionAsk() {
        this.mIsAskedMap = null;
        this.permissionRequestList = null;
        this.notGrantedPermissionList = null;
        this.LOG = null;
        this.TAG = "PermissionAsk";
        this.LOG = new LogManager(0);
        this.permissionRequestList = new ArrayList();
        this.notGrantedPermissionList = new ArrayList();
        this.mIsAskedMap = new HashMap();
    }

    public void SetAskState(String permission, int askState) {
        int requestCode = GetPermissionCode(permission);
        askState = askState == 0 ? asked_grant : asked_denied;
        this.LOG.m383d(this.TAG, "SetAskState requestCode: " + requestCode + " " + askState);
        SetAskState(requestCode, askState);
    }

    public void SetAskState(int requestCode, int askState) {
        if (requestCode != -1) {
            this.mIsAskedMap.put(Integer.valueOf(requestCode), Integer.valueOf(askState));
        }
    }

    public int HavePermissionAsked(int requestCode, IPermission callback) {
        this.LOG.m383d(this.TAG, "HavePermissionAsked: " + requestCode + " " + this.mIsAskedMap.get(Integer.valueOf(requestCode)));
        if (this.mIsAskedMap.get(Integer.valueOf(requestCode)) == null) {
            return ask_not_yet;
        }
        int askState = ((Integer) this.mIsAskedMap.get(Integer.valueOf(requestCode))).intValue();
        this.LOG.m383d(this.TAG, "HavePermissionAsked: " + requestCode + " " + askState);
        callback.GetGrantState(requestCode, askState);
        return askState;
    }

    public static boolean IsGranted(Context context, int requestCode) {
        return ContextCompat.checkSelfPermission(context, GetPermission(requestCode)) == 0;
    }

    public static boolean Request(Activity context, int requestCode) {
        if (VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(context, GetPermission(requestCode)) == 0) {
            return false;
        }
        String[] strArr = new String[ask_not_yet];
        strArr[0] = GetPermission(requestCode);
        ActivityCompat.requestPermissions(context, strArr, requestCode);
        return true;
    }

    int GetPermissionCode(String permission) {
        this.LOG.m383d(this.TAG, "GetPermissionCode " + permission);
        if (permission.equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
            return ask_not_yet;
        }
        if (permission.equals("android.permission.ACCESS_COARSE_LOCATION")) {
            return asked_denied;
        }
        if (permission.equals("android.permission.CAMERA")) {
            return asked_grant;
        }
        if (permission.equals("android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS")) {
            return IGNORE_BATTERY_OPTIMIZATIONS;
        }
        return -1;
    }

    public static String GetPermission(int requestCode) {
        switch (requestCode) {
            case ask_not_yet /*1*/:
                return "android.permission.WRITE_EXTERNAL_STORAGE";
            case asked_denied /*2*/:
                return "android.permission.ACCESS_COARSE_LOCATION";
            case asked_grant /*3*/:
                return "android.permission.CAMERA";
            case IGNORE_BATTERY_OPTIMIZATIONS /*4*/:
                return "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS";
            default:
                return XmlPullParser.NO_NAMESPACE;
        }
    }

    public void RequestList(Activity context, int requestCode, IPermission listener) {
        this.permissionRequestList.clear();
        this.notGrantedPermissionList.clear();
        this.permissionRequestList.add(Integer.valueOf(ask_not_yet));
        this.permissionRequestList.add(Integer.valueOf(asked_denied));
        Iterator it = this.permissionRequestList.iterator();
        while (it.hasNext()) {
            String permission = GetPermission(((Integer) it.next()).intValue());
            if (ContextCompat.checkSelfPermission(context, permission) != 0) {
                this.notGrantedPermissionList.add(permission);
            }
        }
        if (this.notGrantedPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(context, (String[]) this.notGrantedPermissionList.toArray(new String[this.notGrantedPermissionList.size()]), requestCode);
        }
    }

    public boolean JustAfterCheck(int requestCode, IPermission callback) {
        boolean z = true;
        if (VERSION.SDK_INT < 23) {
            return false;
        }
        int askState = HavePermissionAsked(requestCode, callback);
        SetAskState(requestCode, callback.SetLatterState(requestCode, askState));
        this.LOG.m383d(this.TAG, "JustAfterCheck: " + askState);
        if (askState == ask_not_yet) {
            z = false;
        }
        return z;
    }
}
