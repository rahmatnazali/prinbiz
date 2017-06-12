package com.hiti.multiphoto;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.trace.GlobalVariable_UploadInfo;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.UserInfo;
import java.io.File;
import org.xmlpull.v1.XmlPullParser;

public class MultiUpload {
    private static final int UPOLAD_IMAGE_MAX_SIZE_LIMIT = 20971520;
    private static final int UPOLAD_WIDTH_LIMTI = 1500;
    LogManager LOG;
    String TAG;
    Handler mHandler;
    String m_AlbumPID;
    AlertDialog m_AlertDialog;
    private Button m_CancelButton;
    Context m_Context;
    GlobalVariable_UploadInfo m_GVUploadInfo;
    MultiSelectInfo m_MultiSelectInfo;
    MultiUploadInterface m_MultiUploadListener;
    private View m_UploadPhotoDialogView;
    private AlertDialog m_UploadPhotoHintDialog;
    WebUpload m_WebUpload;
    boolean m_bUploadCancel;
    int m_iUpLoadTotalNumber;
    int m_iUploadIncrement;
    private String m_strUploadTempPath;
    Pair<String, String> m_userPair;

    /* renamed from: com.hiti.multiphoto.MultiUpload.2 */
    class C02342 implements Runnable {
        C02342() {
        }

        public void run() {
            MultiUpload.this.CreateNewUpload();
        }
    }

    /* renamed from: com.hiti.multiphoto.MultiUpload.3 */
    class C02353 implements OnClickListener {
        C02353() {
        }

        public void onClick(View v) {
            MultiUpload.this.m_CancelButton.setEnabled(false);
            MultiUpload.this.m_CancelButton.setAlpha(0.7f);
            MultiUpload.this.SetUploadCancel(true);
        }
    }

    /* renamed from: com.hiti.multiphoto.MultiUpload.4 */
    class C02364 implements DialogInterface.OnClickListener {
        C02364() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            if (MultiUpload.this.m_MultiUploadListener != null) {
                MultiUpload.this.m_MultiUploadListener.UploadFail();
            }
        }
    }

    public interface MultiUploadInterface {
        Button SetDialogButton(View view);

        String SetErrorAlertBtn();

        String SetErrorAlertMessage(WEB_UPLOAD_ERROR web_upload_error);

        View SetProgressDialogView(int i);

        void UploadCancel();

        void UploadComplete();

        void UploadFail();

        void onProgress(int i, String str, WEB_UPLOAD_ERROR web_upload_error);
    }

    /* renamed from: com.hiti.multiphoto.MultiUpload.1 */
    class C07301 extends WebUpload {
        C07301(Context context) {
            super(context);
        }

        public void UploadSuccess(String strPreviewPath) {
            MultiUpload.this.RemoveDownSizeUploadPhoto(strPreviewPath);
            MultiUpload.this.onUploadSuccess(strPreviewPath);
        }

        public void UploadFail(WEB_UPLOAD_ERROR errorCode, String strPreviewPath) {
            MultiUpload.this.LOG.m385i(MultiUpload.this.TAG, "UploadFail-w " + errorCode);
            MultiUpload.this.RemoveDownSizeUploadPhoto(strPreviewPath);
            if (errorCode == WEB_UPLOAD_ERROR.UPLOAD_CANCEL) {
                MultiUpload.this.LOG.m385i(MultiUpload.this.TAG, "Uploadcancel-w");
                MultiUpload.this.onProgress(strPreviewPath, errorCode);
                MultiUpload.this.onUploadCancel();
                return;
            }
            MultiUpload.this.onUploadFail(MultiUpload.this.m_MultiUploadListener.SetErrorAlertMessage(errorCode));
        }

        public void RemoveRecord(String code) {
            MultiUpload.this.LOG.m385i(MultiUpload.this.TAG, "RemoveRecord " + code);
            MultiUpload.this.m_MultiSelectInfo.RemoveItem(0);
        }
    }

    public MultiUpload(Context context, MultiUploadInterface listener, String albumPID) {
        this.m_UploadPhotoHintDialog = null;
        this.m_UploadPhotoDialogView = null;
        this.m_CancelButton = null;
        this.m_AlertDialog = null;
        this.m_WebUpload = null;
        this.m_MultiSelectInfo = null;
        this.m_MultiUploadListener = null;
        this.m_GVUploadInfo = null;
        this.m_userPair = null;
        this.m_Context = null;
        this.m_AlbumPID = null;
        this.m_iUploadIncrement = 0;
        this.m_iUpLoadTotalNumber = 0;
        this.m_bUploadCancel = false;
        this.m_strUploadTempPath = XmlPullParser.NO_NAMESPACE;
        this.mHandler = null;
        this.LOG = null;
        this.TAG = null;
        this.m_Context = context;
        this.m_AlbumPID = albumPID;
        this.m_MultiUploadListener = listener;
        this.m_MultiSelectInfo = new MultiSelectInfo(context);
        this.LOG = new LogManager(0);
        this.TAG = "MultiUpload";
        this.m_strUploadTempPath = FileUtility.GetSDAppRootPath(this.m_Context) + File.separator + PringoConvenientConst.PRINGO_TEMP_FOLDER;
        FileUtility.CreateFolder(this.m_strUploadTempPath);
        this.m_strUploadTempPath += File.separator + "uploadcloudalbum";
        this.mHandler = new Handler();
        this.m_userPair = GetUserInfo(context);
        CreateUploadPhotoHintDialog();
    }

    public MultiUpload(Context context) {
        this.m_UploadPhotoHintDialog = null;
        this.m_UploadPhotoDialogView = null;
        this.m_CancelButton = null;
        this.m_AlertDialog = null;
        this.m_WebUpload = null;
        this.m_MultiSelectInfo = null;
        this.m_MultiUploadListener = null;
        this.m_GVUploadInfo = null;
        this.m_userPair = null;
        this.m_Context = null;
        this.m_AlbumPID = null;
        this.m_iUploadIncrement = 0;
        this.m_iUpLoadTotalNumber = 0;
        this.m_bUploadCancel = false;
        this.m_strUploadTempPath = XmlPullParser.NO_NAMESPACE;
        this.mHandler = null;
        this.LOG = null;
        this.TAG = null;
        this.m_Context = context;
        this.m_MultiSelectInfo = new MultiSelectInfo(context);
        this.m_MultiSelectInfo.RestorePhotoList();
    }

    public static Pair<String, String> GetUserInfo(Context context) {
        GlobalVariable_UploadInfo mGVUploadInfo = new GlobalVariable_UploadInfo(context);
        mGVUploadInfo.RestoreGlobalVariable();
        return UserInfo.GetUP(context, mGVUploadInfo.GetUploader());
    }

    private void CreateNewUpload() {
        this.LOG.m385i("CreateNewUpload", "CreateNewUpload");
        this.m_MultiSelectInfo.RestorePhotoList();
        String strNowPath = this.m_MultiSelectInfo.GetItem(0);
        long imageSize = new File(strNowPath).length();
        this.LOG.m385i("strNowPath", XmlPullParser.NO_NAMESPACE + strNowPath);
        this.LOG.m385i("Image Size", XmlPullParser.NO_NAMESPACE + ((imageSize / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + "MB");
        if (imageSize > 20971520) {
            String strPath = this.m_strUploadTempPath + PringoConvenientConst.PRINBIZ_PHOTO_EXT_JPG;
            BitmapMonitorResult bmr = BitmapMonitor.DownSizeBitmap(strNowPath, strPath, UPOLAD_WIDTH_LIMTI);
            if (bmr.IsSuccess()) {
                strNowPath = strPath;
            } else {
                onUploadFail(bmr.GetError(this.m_Context));
                return;
            }
        }
        this.m_WebUpload = new C07301(this.m_Context);
        this.m_WebUpload.execute(new String[]{(String) this.m_userPair.first, (String) this.m_userPair.second, strNowPath, this.m_AlbumPID});
    }

    private void RemoveDownSizeUploadPhoto(String strPreviewPath) {
        if (strPreviewPath.contains(this.m_strUploadTempPath) && FileUtility.FileExist(strPreviewPath)) {
            FileUtility.DeleteFile(strPreviewPath);
        }
    }

    public void StartUpload() {
        this.m_UploadPhotoHintDialog.show();
        SetUploadCancel(false);
        this.m_MultiSelectInfo.RestorePhotoList();
        NextUpload(this.m_MultiSelectInfo.GetListSize());
    }

    public void NextUpload(int iLastSize) {
        this.LOG.m385i("NextUpload", "last count:" + iLastSize);
        if (iLastSize == 0) {
            onUploadComplete();
            return;
        }
        if (this.m_WebUpload != null) {
            this.m_WebUpload.cancel(true);
            this.m_WebUpload = null;
        }
        if (IsUploadCancel()) {
            onUploadCancel();
        } else {
            this.mHandler.postDelayed(new C02342(), 500);
        }
    }

    private void onUploadSuccess(String strPreviewPath) {
        this.LOG.m385i("onUploadSuccess", String.valueOf(strPreviewPath));
        onProgress(strPreviewPath, WEB_UPLOAD_ERROR.UPLOAD_SUCCESS);
    }

    private void onProgress(String strPreviewPath, WEB_UPLOAD_ERROR iErrorCode) {
        this.LOG.m385i("onProgress", String.valueOf(strPreviewPath));
        this.m_MultiSelectInfo.RestorePhotoList();
        int iNowSize = this.m_MultiSelectInfo.GetListSize();
        if (this.m_MultiUploadListener != null) {
            this.m_MultiUploadListener.onProgress(iNowSize, strPreviewPath, iErrorCode);
        }
    }

    private void onUploadFail(String strErr) {
        this.LOG.m385i(this.TAG, "onUploadFail: " + strErr);
        if (this.m_UploadPhotoHintDialog != null) {
            this.m_UploadPhotoHintDialog.dismiss();
        }
        ShowErrorAlertDialog(strErr);
    }

    private void onUploadComplete() {
        CleanUploadPhotoInfo();
        if (this.m_UploadPhotoHintDialog != null) {
            this.m_UploadPhotoHintDialog.dismiss();
        }
        if (this.m_MultiUploadListener != null) {
            this.m_MultiUploadListener.UploadComplete();
        }
    }

    private void onUploadCancel() {
        this.LOG.m385i(this.TAG, "onUploadCancel");
        if (this.m_UploadPhotoHintDialog != null) {
            this.m_UploadPhotoHintDialog.dismiss();
        }
        if (this.m_MultiUploadListener != null) {
            this.m_MultiUploadListener.UploadCancel();
        }
    }

    public void CleanUploadPhotoInfo() {
        this.m_MultiSelectInfo.ClearInfo();
    }

    private void SetUploadCancel(boolean bUploadCancel) {
        this.m_bUploadCancel = bUploadCancel;
        if (this.m_WebUpload != null && bUploadCancel) {
            this.m_WebUpload.UploadCancel();
        }
        if (!bUploadCancel && this.m_UploadPhotoHintDialog != null) {
            this.m_CancelButton.setEnabled(true);
            this.m_CancelButton.setAlpha(1.0f);
        }
    }

    private boolean IsUploadCancel() {
        return this.m_bUploadCancel;
    }

    void CreateUploadPhotoHintDialog() {
        if (this.m_UploadPhotoHintDialog == null) {
            this.m_UploadPhotoHintDialog = new Builder(this.m_Context).create();
            this.m_UploadPhotoHintDialog.setCanceledOnTouchOutside(false);
            this.m_UploadPhotoHintDialog.setCancelable(false);
            this.m_MultiSelectInfo.RestorePhotoList();
            this.m_UploadPhotoDialogView = this.m_MultiUploadListener.SetProgressDialogView(this.m_MultiSelectInfo.GetListSize());
            this.m_CancelButton = this.m_MultiUploadListener.SetDialogButton(this.m_UploadPhotoDialogView);
            this.m_CancelButton.setOnClickListener(new C02353());
            this.m_UploadPhotoHintDialog.setView(this.m_UploadPhotoDialogView);
        }
    }

    public void ShowErrorAlertDialog(String strErr) {
        String strBtn = this.m_MultiUploadListener.SetErrorAlertBtn();
        this.LOG.m385i(this.TAG, "ShowErrorAlertDialog: " + strErr);
        if (this.m_AlertDialog == null) {
            this.m_AlertDialog = new Builder(this.m_Context).create();
            this.m_AlertDialog.setIcon(17301569);
            this.m_AlertDialog.setButton(-1, strBtn, new C02364());
        }
        this.m_AlertDialog.setMessage(strErr);
        this.m_AlertDialog.show();
    }
}
