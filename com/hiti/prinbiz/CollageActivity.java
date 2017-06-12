package com.hiti.prinbiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.media.TransportMediator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.HitiChunk.HitiChunk.ChunkType;
import com.hiti.HitiChunk.HitiChunkUtility;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.jumpinfo.JumpInfo;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.trace.GlobalVariable_AlbumSelInfo;
import com.hiti.trace.GlobalVariable_MultiSelContainer;
import com.hiti.trace.GlobalVariable_PrintSettingInfo;
import com.hiti.trace.GlobalVariable_WifiAutoConnectInfo;
import com.hiti.ui.collageview.CollageInfoGroup;
import com.hiti.ui.collageview.CollageParser;
import com.hiti.ui.collageview.CollageView;
import com.hiti.ui.collageview.CollageViewTouchListener;
import com.hiti.ui.drawview.garnishitem.FilterColorValue;
import com.hiti.ui.drawview.garnishitem.PathLoader.CollageLoader;
import com.hiti.ui.drawview.garnishitem.security.GarnishSecurity;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.ui.horizontallistview.HorizontalListView;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.PrinterListListener;
import com.hiti.utility.ShowPrinterList;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import com.hiti.utility.wifi.WifiAutoConnect;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.telnet.TelnetCommand;
import org.apache.commons.net.telnet.TelnetOption;
import org.xmlpull.v1.XmlPullParser;

public class CollageActivity extends Activity {
    public static final int HORIZONTAL_CACHE_BITMAP_SIZE = 8;
    public static final int IN_COLLAGE_FOLLOW_PERIOD = 2;
    public static final int IN_COLLAGE_FOLLOW_START = 1;
    public static final int PENDDING_SIZE = 32;
    OnClickListener GoEditListen;
    LogManager LOG;
    OnClickListener StatusOfPhotoListen;
    String TAG;
    private String mEditTempFolder;
    private String mEditThumbPath;
    private String mEditXMLpath;
    NFCInfo mNFCInfo;
    private ImageButton m_AddButton;
    AssetManager m_AssetManager;
    ImageButton m_BackButton;
    private String m_BackEditThumbPath;
    private ArrayList<String> m_CollageEditPhotoPaths;
    HorizontalListView m_CollageHorizontalListView;
    private CollageInfoGroup m_CollageInfoGroup;
    CollageInfoLoader m_CollageInfoLoader;
    CollageLoader m_CollageLoader;
    RelativeLayout m_CollageRelativeLayout;
    private CollageView m_CollageView;
    int m_CollageViewHorizontalSpacing;
    int m_CollagerViewHorizontalSpacing;
    private View m_ConnectWifiDialogView;
    private AlertDialog m_ConnectWifiHintDialog;
    Bitmap m_DefaultCollageThumbnailBmp;
    int m_DefaultOrgThumbnailViewScale;
    private ImageButton m_EditButton;
    EditPhotoLoader m_EditPhotoLoader;
    private ShowMSGDialog m_ErrorDialog;
    FilterColorValue m_FilterColorValue;
    ArrayList<FilterColorValue> m_FilterColorValueList;
    private boolean m_FromLastEdit;
    RelativeLayout m_H1FineButtonGroupRelativeLayout;
    RelativeLayout m_H2FineButtonGroupRelativeLayout;
    GarnishSecurity m_HCollageGarnishSecurity;
    ImageButton m_HFineDownImageButton;
    ImageButton m_HFineLeftImageButton;
    ImageButton m_HFineRightImageButton;
    ImageButton m_HFineSacleDownImageButton;
    ImageButton m_HFineSacleUpImageButton;
    ImageButton m_HFineUpImageButton;
    RelativeLayout m_ImageRelativeLayout;
    private String m_InterruptLastEditPhotoPath;
    Object m_Lock;
    private ShowMSGDialog m_MSGDilaog;
    private ImageButton m_PrintViewButton;
    ProgressBar m_ProgressBar;
    private ImageButton m_ReduceButton;
    ImageButton m_RotateCollageImageButton;
    private ShowPrinterList m_ShowPrinterList;
    private ImageButton m_StatusButton;
    private TextView m_SumCopiesTextView;
    TextView m_TitleTextView;
    RelativeLayout m_V1FineButtonGroupRelativeLayout;
    RelativeLayout m_V2FineButtonGroupRelativeLayout;
    GarnishSecurity m_VCollageGarnishSecurity;
    ImageButton m_VFineDownImageButton;
    ImageButton m_VFineLeftImageButton;
    ImageButton m_VFineRightImageButton;
    ImageButton m_VFineSacleDownImageButton;
    ImageButton m_VFineSacleUpImageButton;
    ImageButton m_VFineUpImageButton;
    float m_ViewScale;
    private ShowMSGDialog m_WaitingHintDialog;
    private GlobalVariable_WifiAutoConnectInfo m_WifiInfo;
    private boolean m_bBackSource;
    private boolean m_bBackState;
    private boolean m_bBackStop;
    private boolean m_bIsVertical;
    private boolean m_bNoNeedUnsharpen;
    boolean m_boLastVertical;
    boolean m_boVertical;
    int m_iCollageFileCount;
    int m_iCollageInfoGroup;
    int m_iCollageItemSize;
    int m_iCollageItemSizeHeight;
    int m_iCollageItemSizeWidth;
    private int m_iCpoies;
    int m_iEditDrawViewHeight;
    int m_iEditDrawViewWidth;
    int m_iH4x6;
    int m_iHSelectID;
    int m_iOutPutH4x6;
    int m_iOutPutW4x6;
    int m_iScreenHeight;
    int m_iScreenWidth;
    int m_iSelCollageInfoHeight;
    int m_iSelCollageInfoWidth;
    int m_iSelRoute;
    int m_iSelectCollageFile;
    int m_iSelectCollageView;
    int m_iVSelectID;
    int m_iW4x6;
    GlobalVariable_MultiSelContainer m_multiSelContainer;
    private ArrayList<Pair<Integer, String[]>> m_pAlbumMetaList;
    JumpPreferenceKey m_pref;
    GlobalVariable_AlbumSelInfo m_prefAlbumInfo;
    ArrayList<String> m_strCollageFilePathList;
    String m_strCollageFolderPath;
    private String m_strCurrentSSID;
    private String m_strDefaultEditFile;
    ArrayList<String> m_strEditTempThumbPathList;
    ArrayList<String> m_strEditXMLPathList;
    private String m_strIDFormatPath;
    String m_strLastCollageFolderPath;
    private String m_strLastSSID;
    private String m_strSecurityKey;
    String m_strSelCollageInfoPath;
    private AutoWifiConnect m_wifiAutoConnect;

    /* renamed from: com.hiti.prinbiz.CollageActivity.24 */
    class AnonymousClass24 implements OnClickListener {
        final /* synthetic */ RadioButton val$m_SelCurrentRadioButton;
        final /* synthetic */ RadioButton val$m_SelLastRadioButton;

        AnonymousClass24(RadioButton radioButton, RadioButton radioButton2) {
            this.val$m_SelCurrentRadioButton = radioButton;
            this.val$m_SelLastRadioButton = radioButton2;
        }

        public void onClick(View v) {
            if (this.val$m_SelCurrentRadioButton.isChecked()) {
                CollageActivity.this.ShowPrinterListDialog();
            }
            if (this.val$m_SelLastRadioButton.isChecked()) {
                CollageActivity.this.CreateWaitingHintDialog();
                CollageActivity.this.m_wifiAutoConnect = new AutoWifiConnect(CollageActivity.this.getBaseContext(), CollageActivity.this.m_strLastSSID, CollageActivity.this.m_strSecurityKey);
                CollageActivity.this.m_wifiAutoConnect.execute(new Void[0]);
            }
            CollageActivity.this.m_ConnectWifiHintDialog.dismiss();
        }
    }

    /* renamed from: com.hiti.prinbiz.CollageActivity.2 */
    class C02692 implements OnClickListener {
        C02692() {
        }

        public void onClick(View v) {
            if (CollageActivity.this.m_iCpoies <= TelnetCommand.GA) {
                CollageActivity.this.m_SumCopiesTextView.setText(String.valueOf(CollageActivity.access$004(CollageActivity.this)));
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.CollageActivity.3 */
    class C02703 implements OnClickListener {
        C02703() {
        }

        public void onClick(View v) {
            if (CollageActivity.this.m_iCpoies >= CollageActivity.IN_COLLAGE_FOLLOW_PERIOD) {
                CollageActivity.this.m_SumCopiesTextView.setText(String.valueOf(CollageActivity.access$006(CollageActivity.this)));
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.CollageActivity.4 */
    class C02714 implements OnClickListener {
        C02714() {
        }

        public void onClick(View v) {
            CollageActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.CollageActivity.5 */
    class C02725 implements OnClickListener {
        C02725() {
        }

        public void onClick(View v) {
            if (!CollageActivity.this.m_bBackSource) {
                CollageActivity.this.m_bBackSource = true;
                CollageActivity.this.ShowLeaveDialog(CollageActivity.this.getBaseContext().getString(C0349R.string.LEAVE_COLLAGE_TO_PRINT), CollageActivity.this.getBaseContext().getString(C0349R.string.LEAVE_COLLAGE_TITLE));
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.CollageActivity.6 */
    class C02736 implements OnClickListener {
        C02736() {
        }

        public void onClick(View v) {
            CollageActivity.this.CheckWifi();
        }
    }

    /* renamed from: com.hiti.prinbiz.CollageActivity.7 */
    class C02747 implements OnClickListener {
        C02747() {
        }

        public void onClick(View v) {
            CollageActivity.this.PrepareCollageInfoParamsForEdit();
        }
    }

    /* renamed from: com.hiti.prinbiz.CollageActivity.9 */
    class C02759 implements OnClickListener {
        C02759() {
        }

        public void onClick(View v) {
            if (CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()) != null) {
                CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()).SetEditPhotoGarnishScale(-0.1f);
            }
        }
    }

    private class AddPhotoImageButtonClickListener implements OnClickListener {
        private AddPhotoImageButtonClickListener() {
        }

        public void onClick(View v) {
            CollageActivity.this.onAddPhotoImageButtonClicked(v);
        }
    }

    class CollageInfoLoader extends AsyncTask<String, String, Integer> {
        private Context m_Context;
        private CollageInfoGroup m_NewCollageInfoGroup;
        private boolean m_boStop;

        CollageInfoLoader(Context context) {
            this.m_boStop = false;
            this.m_NewCollageInfoGroup = null;
            this.m_Context = context;
        }

        protected Integer doInBackground(String... strCollageFile) {
            if (this.m_boStop) {
                return Integer.valueOf(0);
            }
            this.m_NewCollageInfoGroup = CollageParser.GetCollageInfoGroup(this.m_Context, strCollageFile[0]);
            return Integer.valueOf(0);
        }

        protected void onPostExecute(Integer result) {
            if (!this.m_boStop && this.m_NewCollageInfoGroup != null) {
                CollageActivity.this.InitCollage(this.m_NewCollageInfoGroup);
                if (CollageActivity.this.m_CollageInfoGroup != null) {
                    CollageActivity.this.m_CollageInfoGroup.Clear();
                }
                CollageActivity.this.m_CollageInfoGroup = this.m_NewCollageInfoGroup;
                int iXmLSize = CollageActivity.this.m_CollageInfoGroup.GetCollageInfo(CollageActivity.this.m_CollageInfoGroup.GetCollageNumbers() - 1).GetGroup();
                for (int i = 0; i < iXmLSize; i += CollageActivity.IN_COLLAGE_FOLLOW_START) {
                    CollageActivity.this.m_FilterColorValueList.add(null);
                }
                CollageActivity.this.LOG.m383d(CollageActivity.this.TAG, "SelectCollage iXmLSize: " + iXmLSize);
            }
        }

        public void Stop() {
            this.m_boStop = true;
        }
    }

    class EditPhotoLoader extends AsyncTask<Void, String, Integer> {
        private Context m_Context;
        private CollageView m_NewCollageView;
        private boolean m_boStop;
        private ArrayList<String> m_strPhotoPathList;

        EditPhotoLoader(Context context) {
            this.m_boStop = false;
            this.m_NewCollageView = null;
            this.m_strPhotoPathList = null;
            this.m_Context = context;
        }

        void SetEditPhotoInfo(CollageView collageView, ArrayList<String> strPhotoPath) {
            this.m_NewCollageView = collageView;
            this.m_strPhotoPathList = strPhotoPath;
        }

        protected Integer doInBackground(Void... params) {
            if (this.m_boStop) {
                return Integer.valueOf(0);
            }
            if (this.m_NewCollageView == null || this.m_strPhotoPathList == null) {
                return Integer.valueOf(0);
            }
            if (CollageActivity.this.IsEditXMLPathEmpty()) {
                CollageActivity.this.SetFromLastEdit(false);
            }
            if (!CollageActivity.this.GetFromLastEdit()) {
                return Integer.valueOf(this.m_NewCollageView.LoadAllEditPhoto(this.m_strPhotoPathList));
            }
            int iResult = this.m_NewCollageView.LoadIDFormatEditPhoto(this.m_strPhotoPathList, CollageActivity.this.m_strEditXMLPathList);
            CollageActivity.this.SetFromLastEdit(false);
            return Integer.valueOf(iResult);
        }

        protected void onPostExecute(Integer result) {
            if (this.m_boStop) {
                CollageActivity.this.m_WaitingHintDialog.StopWaitingDialog();
            } else if (result.intValue() != 0) {
                CollageActivity.this.ShowGetBitmapErrorAlertDialog(BitmapMonitorResult.GetError(this.m_Context, result.intValue()));
            } else {
                if (CollageActivity.this.m_CollageView != null) {
                    CollageActivity.this.m_CollageView.RemoveAllView();
                    CollageActivity.this.m_ImageRelativeLayout.removeView(CollageActivity.this.m_CollageView);
                }
                CollageActivity.this.m_CollageView = this.m_NewCollageView;
                CollageActivity.this.m_ImageRelativeLayout.addView(CollageActivity.this.m_CollageView);
                CollageActivity.this.m_CollageView.SetAddPhotoImageButtonClickListener(new AddPhotoImageButtonClickListener(null));
                CollageActivity.this.m_CollageView.SetCollageViewTouchListener(new OnCollageViewTouchListener());
                CollageActivity.this.m_CollageView.setBackgroundColor(-1);
                CollageActivity.this.m_CollageView.invalidate();
                CollageActivity.this.InitEditPath();
                CollageActivity.this.HaveCollageInfoThumbToChange();
                CollageActivity.this.HaveInterruptLastEditPhoto();
                CollageActivity.this.HaveEditInfoToChangeCollage();
                CollageActivity.this.m_WaitingHintDialog.StopWaitingDialog();
            }
        }

        public void Stop() {
            this.m_boStop = true;
        }
    }

    class SaveImage extends AsyncTask<Void, String, Integer> {
        String m_strSavePath;

        SaveImage() {
            this.m_strSavePath = null;
        }

        protected Integer doInBackground(Void... params) {
            FileNotFoundException e;
            OutputStream outputStream;
            IOException e2;
            if (CollageActivity.this.getExternalFilesDir(null) == null) {
                return Integer.valueOf(95);
            }
            FileUtility.CreateFolder(CollageActivity.this.mEditTempFolder);
            this.m_strSavePath = CollageActivity.this.mEditTempFolder + "/" + PringoConvenientConst.PRINGO_COLLAGE_FILE;
            BitmapMonitor.TrySystemGC();
            int iLong = CollageActivity.this.m_iOutPutW4x6;
            int iShort = CollageActivity.this.m_iOutPutH4x6;
            if (iLong < iShort) {
                iLong = CollageActivity.this.m_iOutPutH4x6;
                iShort = CollageActivity.this.m_iOutPutW4x6;
            }
            BitmapMonitorResult bmr = CollageActivity.this.m_CollageView.GetCollageBitmap(iLong, iShort);
            if (!bmr.IsSuccess()) {
                return Integer.valueOf(bmr.GetResult());
            }
            try {
                OutputStream fos = new FileOutputStream(new File(this.m_strSavePath));
                try {
                    bmr.GetBitmap().compress(CompressFormat.PNG, 100, fos);
                    fos.close();
                    bmr.GetBitmap().recycle();
                    if (CollageActivity.this.m_CollageView.HaveGSMask()) {
                        bmr = CollageActivity.this.m_CollageView.GetCollageGSMaskBitmap();
                        if (!bmr.IsSuccess()) {
                            return Integer.valueOf(bmr.GetResult());
                        }
                        HitiChunkUtility.AddHiTiChunk(this.m_strSavePath, bmr.GetBitmap(), ChunkType.PNG);
                        bmr.GetBitmap().recycle();
                        BitmapMonitor.TrySystemGC();
                    }
                    return Integer.valueOf(0);
                } catch (FileNotFoundException e3) {
                    e = e3;
                    outputStream = fos;
                    e.printStackTrace();
                    return Integer.valueOf(0);
                } catch (IOException e4) {
                    e2 = e4;
                    outputStream = fos;
                    e2.printStackTrace();
                    return Integer.valueOf(0);
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                e.printStackTrace();
                return Integer.valueOf(0);
            } catch (IOException e6) {
                e2 = e6;
                e2.printStackTrace();
                return Integer.valueOf(0);
            }
        }

        protected void onPostExecute(Integer iResult) {
            CollageActivity.this.m_WaitingHintDialog.StopWaitingDialog();
            if (iResult.intValue() != 0) {
                CollageActivity.this.ShowGetBitmapErrorAlertDialog(BitmapMonitorResult.GetError(CollageActivity.this, iResult.intValue()));
            } else {
                CollageActivity.this.StartPrintView(this.m_strSavePath);
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.CollageActivity.1 */
    class C07551 implements INfcPreview {
        C07551() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            CollageActivity.this.mNFCInfo = nfcInfo;
        }
    }

    /* renamed from: com.hiti.prinbiz.CollageActivity.8 */
    class C07568 implements MSGListener {
        C07568() {
        }

        public void OKClick() {
            if (CollageActivity.this.m_bBackState) {
                CollageActivity.this.onBackButtonClicked(null);
            } else {
                CollageActivity.this.onNextButtonClicked();
            }
        }

        public void Close() {
        }

        public void CancelClick() {
            CollageActivity.this.m_bBackSource = false;
            CollageActivity.this.m_bBackState = false;
        }
    }

    class AutoWifiConnect extends WifiAutoConnect {
        public AutoWifiConnect(Context context, String strSSID, String strPassword) {
            super(context, strSSID, strPassword);
        }

        public void ConnectionSuccess() {
            Log.e("ConnectionSuccess", "ConnectionSuccess");
            ConnectionStop();
            CollageActivity.this.ShowPrinterListDialog();
        }

        public void ConnectionFail() {
            Log.e("ConnectionFail", "ConnectionFail");
            ConnectionStop();
            CollageActivity.this.ShowNoWiFiDialog();
        }

        public void ConnectionStop() {
            Log.e("autowifi", "stop");
            CollageActivity.this.m_WaitingHintDialog.StopWaitingDialog();
            SetStop(true);
        }
    }

    class OnCollageViewTouchListener extends CollageViewTouchListener {
        OnCollageViewTouchListener() {
        }

        public void onCollageViewTouch(View v) {
            CollageActivity.this.GetCollageInfoData(CollageActivity.this.m_CollageView.GetLastSelectDrawView());
            CollageActivity.this.ChangeSelectButtonBackground(CollageActivity.this.m_CollageView.GetLastSelectDrawView());
            CollageActivity.this.m_CollageView.DrawCollageFoucs(CollageActivity.this.m_CollageView.GetLastSelectDrawView());
        }
    }

    public CollageActivity() {
        this.m_CollageInfoGroup = null;
        this.m_CollageView = null;
        this.m_CollageEditPhotoPaths = null;
        this.m_strDefaultEditFile = null;
        this.m_DefaultOrgThumbnailViewScale = 4;
        this.m_H1FineButtonGroupRelativeLayout = null;
        this.m_HFineSacleUpImageButton = null;
        this.m_HFineSacleDownImageButton = null;
        this.m_H2FineButtonGroupRelativeLayout = null;
        this.m_HFineUpImageButton = null;
        this.m_HFineDownImageButton = null;
        this.m_HFineLeftImageButton = null;
        this.m_HFineRightImageButton = null;
        this.m_V1FineButtonGroupRelativeLayout = null;
        this.m_VFineSacleUpImageButton = null;
        this.m_VFineSacleDownImageButton = null;
        this.m_V2FineButtonGroupRelativeLayout = null;
        this.m_VFineUpImageButton = null;
        this.m_VFineDownImageButton = null;
        this.m_VFineLeftImageButton = null;
        this.m_VFineRightImageButton = null;
        this.m_VCollageGarnishSecurity = null;
        this.m_HCollageGarnishSecurity = null;
        this.m_RotateCollageImageButton = null;
        this.m_CollageRelativeLayout = null;
        this.m_CollageHorizontalListView = null;
        this.m_CollageViewHorizontalSpacing = 12;
        this.m_strCollageFolderPath = null;
        this.m_strLastCollageFolderPath = XmlPullParser.NO_NAMESPACE;
        this.m_CollageLoader = null;
        this.m_strCollageFilePathList = null;
        this.m_strEditTempThumbPathList = null;
        this.m_strEditXMLPathList = null;
        this.m_iSelectCollageView = -1;
        this.m_iCollageFileCount = 0;
        this.m_iSelectCollageFile = 0;
        this.m_iCollageInfoGroup = 0;
        this.m_boVertical = true;
        this.m_boLastVertical = true;
        this.m_iCollageItemSizeWidth = 0;
        this.m_iCollageItemSizeHeight = 0;
        this.m_CollageInfoLoader = null;
        this.m_EditPhotoLoader = null;
        this.m_DefaultCollageThumbnailBmp = null;
        this.m_FromLastEdit = false;
        this.m_InterruptLastEditPhotoPath = null;
        this.m_BackEditThumbPath = null;
        this.m_Lock = new Object();
        this.m_AssetManager = null;
        this.m_TitleTextView = null;
        this.m_ProgressBar = null;
        this.m_BackButton = null;
        this.m_ImageRelativeLayout = null;
        this.m_iCollageItemSize = 0;
        this.m_CollagerViewHorizontalSpacing = 0;
        this.m_pref = null;
        this.m_prefAlbumInfo = null;
        this.m_iSelRoute = -1;
        this.m_multiSelContainer = null;
        this.m_ErrorDialog = null;
        this.m_WaitingHintDialog = null;
        this.m_MSGDilaog = null;
        this.m_ViewScale = 0.0f;
        this.m_iScreenWidth = 0;
        this.m_iScreenHeight = 0;
        this.m_iW4x6 = 0;
        this.m_iH4x6 = 0;
        this.m_iOutPutW4x6 = 0;
        this.m_iOutPutH4x6 = 0;
        this.m_iEditDrawViewWidth = 0;
        this.m_iEditDrawViewHeight = 0;
        this.m_iVSelectID = 0;
        this.m_iHSelectID = 0;
        this.m_iSelCollageInfoWidth = 0;
        this.m_iSelCollageInfoHeight = 0;
        this.m_strSelCollageInfoPath = null;
        this.m_FilterColorValue = null;
        this.m_FilterColorValueList = null;
        this.m_AddButton = null;
        this.m_ReduceButton = null;
        this.m_PrintViewButton = null;
        this.m_EditButton = null;
        this.m_StatusButton = null;
        this.m_SumCopiesTextView = null;
        this.m_iCpoies = IN_COLLAGE_FOLLOW_START;
        this.m_bBackSource = false;
        this.m_bBackState = false;
        this.m_bIsVertical = false;
        this.m_bNoNeedUnsharpen = true;
        this.m_strIDFormatPath = null;
        this.m_pAlbumMetaList = null;
        this.m_strLastSSID = null;
        this.m_strCurrentSSID = null;
        this.m_strSecurityKey = null;
        this.m_wifiAutoConnect = null;
        this.m_ConnectWifiHintDialog = null;
        this.m_ConnectWifiDialogView = null;
        this.m_WifiInfo = null;
        this.m_ShowPrinterList = null;
        this.m_bBackStop = false;
        this.mEditThumbPath = null;
        this.mEditXMLpath = null;
        this.mEditTempFolder = null;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = null;
        this.StatusOfPhotoListen = new C02736();
        this.GoEditListen = new C02747();
    }

    static /* synthetic */ int access$004(CollageActivity x0) {
        int i = x0.m_iCpoies + IN_COLLAGE_FOLLOW_START;
        x0.m_iCpoies = i;
        return i;
    }

    static /* synthetic */ int access$006(CollageActivity x0) {
        int i = x0.m_iCpoies - 1;
        x0.m_iCpoies = i;
        return i;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(IN_COLLAGE_FOLLOW_START);
        setContentView(C0349R.layout.activity_id_photo);
        setRequestedOrientation(IN_COLLAGE_FOLLOW_START);
        this.LOG = new LogManager(0);
        this.TAG = getClass().getSimpleName();
        this.LOG.m383d(this.TAG, "onCreate");
        this.m_AssetManager = getAssets();
        this.m_CollageEditPhotoPaths = new ArrayList();
        this.m_strEditXMLPathList = new ArrayList();
        this.m_VCollageGarnishSecurity = new GarnishSecurity(this);
        this.m_HCollageGarnishSecurity = new GarnishSecurity(this);
        this.m_pAlbumMetaList = new ArrayList();
        this.m_FilterColorValueList = new ArrayList();
        this.mEditTempFolder = getExternalFilesDir(null).getAbsolutePath() + "/" + PringoConvenientConst.PRINGO_TEMP_FOLDER;
        GetBundle(getIntent().getExtras());
        GetPref();
        CalculateUISize();
        CalculateUIScale();
        this.m_iVSelectID = C0349R.drawable.v_collage_select;
        this.m_iHSelectID = C0349R.drawable.h_collage_select;
        this.m_ProgressBar = (ProgressBar) findViewById(C0349R.id.m_ProgressBar);
        this.m_ProgressBar.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
        this.m_strCollageFolderPath = PringoConvenientConst.V_ID_PHOTO_PATH_4X6;
        this.m_ImageRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_ImageRelativeLayout);
        LayoutParams imageRelativeLayoutParams = (LayoutParams) this.m_ImageRelativeLayout.getLayoutParams();
        imageRelativeLayoutParams.height = this.m_iScreenWidth;
        imageRelativeLayoutParams.width = this.m_iScreenWidth;
        this.m_ImageRelativeLayout.setLayoutParams(imageRelativeLayoutParams);
        this.m_ImageRelativeLayout.setBackgroundColor(getResources().getColor(C0349R.color.COLLAGE_VIEW_BACKGROUND_COLOR));
        ShowFineButtonGroup(false);
        onRotateCollageButtonClicked(null);
        if (HaveLastEdit(savedInstanceState)) {
            WillInitCollageByLastEdit();
        }
        SetView();
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        this.m_bBackSource = false;
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07551());
    }

    protected void onPause() {
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    public void onStop() {
        super.onStop();
        this.LOG.m385i(this.TAG, "onStop");
    }

    public void onDestroy() {
        super.onDestroy();
        this.LOG.m383d(this.TAG, "onDestroy");
        if (this.m_CollageView != null) {
            this.m_CollageView.RemoveAllView();
            this.m_ImageRelativeLayout.removeView(this.m_CollageView);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.LOG.m383d("onActivityResult requestCode", String.valueOf(requestCode));
        this.LOG.m383d("onActivityResult resultCode", String.valueOf(resultCode));
        Bundle bundle;
        switch (requestCode) {
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                if (this.m_ConnectWifiHintDialog != null && this.m_ConnectWifiHintDialog.isShowing()) {
                    this.m_ConnectWifiHintDialog.cancel();
                }
                CheckWifi();
            case ConnectionResult.SERVICE_UPDATING /*18*/:
                if (resultCode == 50) {
                    setResult(50);
                    finish();
                }
            case TelnetOption.TERMINAL_LOCATION_NUMBER /*28*/:
                switch (resultCode) {
                    case TelnetOption.REGIME_3270 /*29*/:
                        if (data != null && data.getExtras() != null) {
                            Pair<Integer, String[]> pAlbumMeta;
                            bundle = data.getExtras();
                            String strPhotoPath = bundle.getString(JumpBundleMessage.BUNDLE_MSG_SELECT_PHOTO_PATH);
                            this.LOG.m383d(this.TAG, "strPhotoPath: " + strPhotoPath);
                            int iBackRoute = bundle.getInt(JumpBundleMessage520.BUNDLE_NSG_SEL_ROUTE);
                            if (iBackRoute == IN_COLLAGE_FOLLOW_PERIOD) {
                                pAlbumMeta = new Pair(Integer.valueOf(this.m_iSelectCollageView), new String[]{String.valueOf(bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_ALBUM_ID)), String.valueOf(bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_ALBUM_STORAGE_ID)), bundle.getString(JumpBundleMessage520.BUNDLE_MSG_ALBUM_NAME), String.valueOf(iBackRoute)});
                            } else {
                                String[] strAlbumMeta = new String[IN_COLLAGE_FOLLOW_START];
                                strAlbumMeta[0] = String.valueOf(iBackRoute);
                                pAlbumMeta = new Pair(Integer.valueOf(this.m_iSelectCollageView), strAlbumMeta);
                            }
                            Iterator it = this.m_pAlbumMetaList.iterator();
                            while (it.hasNext()) {
                                Pair<Integer, String[]> pair = (Pair) it.next();
                                if (((Integer) pair.first).intValue() == this.m_iSelectCollageView) {
                                    this.m_pAlbumMetaList.remove(pair);
                                    this.m_pAlbumMetaList.add(pAlbumMeta);
                                    if (GetFromLastEdit()) {
                                        ChangeCollageViewPhoto(strPhotoPath);
                                    } else {
                                        this.m_InterruptLastEditPhotoPath = strPhotoPath;
                                    }
                                    this.LOG.m383d(this.TAG, "onActivity Back Select path: " + strPhotoPath);
                                    break;
                                }
                            }
                            this.m_pAlbumMetaList.add(pAlbumMeta);
                            if (GetFromLastEdit()) {
                                ChangeCollageViewPhoto(strPhotoPath);
                            } else {
                                this.m_InterruptLastEditPhotoPath = strPhotoPath;
                            }
                            this.LOG.m383d(this.TAG, "onActivity Back Select path: " + strPhotoPath);
                        } else {
                            return;
                        }
                        break;
                }
                this.m_bBackSource = false;
            case TelnetOption.LINEMODE /*34*/:
                if (resultCode == 35) {
                    this.m_strLastCollageFolderPath = XmlPullParser.NO_NAMESPACE;
                    CreateCollage();
                }
            case JumpInfo.RESULT_EDIT_ID_ACTIVITY /*60*/:
                if (data != null) {
                    bundle = data.getExtras();
                    if (resultCode == 67) {
                        if (bundle != null) {
                            String strXMLPath = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_XML);
                            String strThumbPath = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_TMP);
                            int iCollageInfoNum = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_NUMBER);
                            boolean bIsVertical = bundle.getBoolean(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_VERTICAL);
                            this.m_iSelectCollageView = iCollageInfoNum;
                            this.m_FilterColorValue = new FilterColorValue();
                            this.m_FilterColorValue.m_fHue = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_HUE);
                            this.m_FilterColorValue.m_fSaturation = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_SATURATION);
                            this.m_FilterColorValue.m_fLight = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIGHT);
                            this.m_FilterColorValue.m_fContrast = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_CONTRAST);
                            this.m_FilterColorValue.m_fRed = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_RED);
                            this.m_FilterColorValue.m_fGreen = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_GREEN);
                            this.m_FilterColorValue.m_fBlue = bundle.getFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_BLUE);
                            this.LOG.m383d(this.TAG, "onActivityResult strThumbPath: " + strThumbPath);
                            this.LOG.m383d(this.TAG, "onActivityResult strXMLPath: " + strXMLPath);
                            SetIsVertival(bIsVertical);
                            if (GetFromLastEdit()) {
                                SetEditData(strThumbPath, strXMLPath);
                            } else {
                                ChangeCollageInfoPhotoAndSetXML(strThumbPath, strXMLPath);
                            }
                        }
                    } else if (resultCode == 0 && GetFromLastEdit()) {
                        this.LOG.m383d(this.TAG, this.m_iSelectCollageView + ") m_strEditTempThumbPathList: " + this.m_strEditTempThumbPathList);
                        this.m_BackEditThumbPath = (String) this.m_strEditTempThumbPathList.get(this.m_iSelectCollageView);
                    }
                }
            default:
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.remove(JumpBundleMessage.BUNDLE_MSG_COLLAGE_EDIT_PHOTO_PATHS);
        outState.remove(JumpBundleMessage.BUNDLE_MSG_COLLAGE_SELECT_VIEW_INDEX);
        outState.remove(JumpBundleMessage.BUNDLE_MSG_COLLAGE_SELECT_COLLAGE_FILE);
        outState.remove(JumpBundleMessage.BUNDLE_MSG_COLLAGE_FILE_VERTICAL);
        outState.remove(JumpBundleMessage.BUNDLE_MSG_COLLAGE_LAST_EDIT_PHOTO_XMLS);
        outState.remove(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_TMP);
        outState.remove(JumpBundleMessage520.BUNDLE_MSG_IDPHOTO_COPIES);
        outState.putInt(JumpBundleMessage.BUNDLE_MSG_COLLAGE_SELECT_VIEW_INDEX, this.m_iSelectCollageView);
        outState.putInt(JumpBundleMessage.BUNDLE_MSG_COLLAGE_SELECT_COLLAGE_FILE, this.m_iSelectCollageFile);
        outState.putInt(JumpBundleMessage520.BUNDLE_MSG_IDPHOTO_COPIES, this.m_iCpoies);
        outState.putBoolean(JumpBundleMessage.BUNDLE_MSG_COLLAGE_FILE_VERTICAL, this.m_boVertical);
        outState.putStringArrayList(JumpBundleMessage.BUNDLE_MSG_COLLAGE_EDIT_PHOTO_PATHS, this.m_CollageEditPhotoPaths);
        outState.putStringArrayList(JumpBundleMessage.BUNDLE_MSG_COLLAGE_LAST_EDIT_PHOTO_XMLS, this.m_strEditXMLPathList);
        outState.putStringArrayList(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_TMP, this.m_strEditTempThumbPathList);
        this.LOG.m383d(this.TAG, "onSaveInstanceState m_strEditXMLPathList: " + this.m_strEditXMLPathList);
    }

    private void SetEditData(String strTmpPath, String strXMLPath) {
        this.mEditThumbPath = strTmpPath;
        this.mEditXMLpath = strXMLPath;
    }

    private void GetPref() {
        this.m_prefAlbumInfo = new GlobalVariable_AlbumSelInfo(this, false);
        this.m_prefAlbumInfo.RestoreGlobalVariable();
        this.m_iSelRoute = this.m_prefAlbumInfo.GetAlbumRoute();
        this.m_WifiInfo = new GlobalVariable_WifiAutoConnectInfo(this);
        GlobalVariable_PrintSettingInfo pref = new GlobalVariable_PrintSettingInfo(this, ControllerState.PLAY_COUNT_STATE);
        pref.RestoreGlobalVariable();
        this.m_strIDFormatPath = pref.GetIDpath();
        this.LOG.m383d("m_strIDFormatPath", String.valueOf(this.m_strIDFormatPath));
    }

    private void SetView() {
        this.m_StatusButton = (ImageButton) findViewById(C0349R.id.m_StatusButton);
        this.m_EditButton = (ImageButton) findViewById(C0349R.id.m_EditButton);
        this.m_PrintViewButton = (ImageButton) findViewById(C0349R.id.m_PrintButton);
        this.m_AddButton = (ImageButton) findViewById(C0349R.id.m_AddButton);
        this.m_ReduceButton = (ImageButton) findViewById(C0349R.id.m_ReduceButton);
        this.m_SumCopiesTextView = (TextView) findViewById(C0349R.id.m_CountNumTextView);
        this.m_MSGDilaog = new ShowMSGDialog(this, false);
        this.m_EditButton.setEnabled(false);
        this.m_StatusButton.setOnClickListener(this.StatusOfPhotoListen);
        this.m_EditButton.setOnClickListener(this.GoEditListen);
        this.m_SumCopiesTextView.setText(String.valueOf(this.m_iCpoies));
        this.LOG.m383d(this.TAG, "SetView m_iCpoies: " + this.m_iCpoies);
        this.m_AddButton.setOnClickListener(new C02692());
        this.m_ReduceButton.setOnClickListener(new C02703());
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_BackButton.setOnClickListener(new C02714());
        this.m_PrintViewButton = (ImageButton) findViewById(C0349R.id.m_PrintButton);
        this.m_PrintViewButton.setOnClickListener(new C02725());
    }

    private void PrepareCollageInfoParamsForEdit() {
        String strEditPath = GetToEditCollagePath();
        if (!CheckPhotoIfIeagalRatio(strEditPath)) {
            this.LOG.m383d(this.TAG, "PrepareCollageInfoParamsForEdit: " + this.m_iSelectCollageView);
            this.LOG.m383d(this.TAG, "PrepareCollageInfoParamsForEdit: " + strEditPath);
            this.LOG.m383d(this.TAG, "PrepareCollageInfoParamsForEdit: " + ((String) this.m_strEditXMLPathList.get(this.m_iSelectCollageView)));
            Intent intent = new Intent(getBaseContext(), EditIdActivity.class);
            Bundle data = new Bundle();
            FilterColorValue fcv = GetFilterColorValue();
            int iTouchID = this.m_CollageView.GetLastSelectDrawView();
            if (iTouchID != -1) {
                data.putInt(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_NUMBER, GetToEditCollageNumber());
                data.putInt(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_WIDTH, GetToEditCollageWidth());
                data.putInt(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_HEIGHT, GetToEditCollageHeight());
                data.putString(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_PATH, strEditPath);
                data.putBoolean(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_VERTICAL, GetIsVertical());
                data.putString(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_XML, (String) this.m_strEditXMLPathList.get(this.m_iSelectCollageView));
                String strBiometricLinePath = this.m_CollageInfoGroup.GetCollageInfo(iTouchID).GetBiometricLinePhotoPath();
                if (!(strBiometricLinePath == null || strBiometricLinePath.isEmpty())) {
                    data.putString(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_BIOMETRIC_LINE_PATH, strBiometricLinePath);
                }
                if (fcv != null) {
                    data.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_HUE, fcv.m_fHue);
                    data.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_SATURATION, fcv.m_fSaturation);
                    data.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_LIGHT, fcv.m_fLight);
                    data.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_CONTRAST, fcv.m_fContrast);
                    data.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_RED, fcv.m_fRed);
                    data.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_GREEN, fcv.m_fGreen);
                    data.putFloat(JumpBundleMessage520.BUNDLE_MSG_FILTER_VALUE_BLUE, fcv.m_fBlue);
                }
                intent.putExtras(data);
                startActivityForResult(intent, 60);
            }
        }
    }

    private boolean CheckPhotoIfIeagalRatio(String strPath) {
        int iResult = BitmapMonitor.IsLegalRatio(this, Uri.parse("file://" + strPath)).GetResult();
        if (iResult == 0) {
            return false;
        }
        ShowErrorMSG(BitmapMonitorResult.GetError(this, iResult));
        return true;
    }

    boolean SaveLastEditProgress(ArrayList<String> collageLastEditPhotoXMLs) {
        this.LOG.m383d(this.TAG, "SaveLastEditProgress");
        return this.m_CollageView.SaveGarnish2XML(collageLastEditPhotoXMLs);
    }

    boolean HaveLastEdit(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return false;
        }
        this.m_strEditXMLPathList = null;
        this.m_CollageEditPhotoPaths = savedInstanceState.getStringArrayList(JumpBundleMessage.BUNDLE_MSG_COLLAGE_EDIT_PHOTO_PATHS);
        this.m_iSelectCollageView = savedInstanceState.getInt(JumpBundleMessage.BUNDLE_MSG_COLLAGE_SELECT_VIEW_INDEX);
        this.m_iSelectCollageFile = savedInstanceState.getInt(JumpBundleMessage.BUNDLE_MSG_COLLAGE_SELECT_COLLAGE_FILE);
        this.m_iCpoies = savedInstanceState.getInt(JumpBundleMessage520.BUNDLE_MSG_IDPHOTO_COPIES);
        this.m_strEditXMLPathList = savedInstanceState.getStringArrayList(JumpBundleMessage.BUNDLE_MSG_COLLAGE_LAST_EDIT_PHOTO_XMLS);
        this.m_boLastVertical = savedInstanceState.getBoolean(JumpBundleMessage.BUNDLE_MSG_COLLAGE_FILE_VERTICAL);
        this.m_strEditTempThumbPathList = savedInstanceState.getStringArrayList(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_EDIT_TMP);
        this.LOG.m383d("HaveLastEdit", "m_strEditXMLPathList: " + this.m_strEditXMLPathList);
        if (this.m_strEditXMLPathList != null) {
            return true;
        }
        return false;
    }

    public void onBackButtonClicked(View v) {
        if (!this.m_bBackSource) {
            this.m_bBackSource = true;
            if (this.m_iSelRoute == IN_COLLAGE_FOLLOW_PERIOD) {
                Exit(56, null);
            } else {
                Exit(55, null);
            }
        }
    }

    public void onBackPressed() {
        if (!this.m_bBackStop) {
            this.m_bBackState = true;
            ShowLeaveDialog(getString(C0349R.string.LEAVE_COLLAGE_MSG), getString(C0349R.string.LEAVE_COLLAGE_TITLE));
        }
    }

    void Exit(int iResult, Intent returnIntent) {
        this.LOG.m383d(this.TAG, "Exit " + iResult);
        if (this.mEditTempFolder != null) {
            FileUtility.DeleteFolder(this.mEditTempFolder + File.separator + PringoConvenientConst.PRINBIZ_EDIT_IMG_FOLDER);
        }
        if (returnIntent == null) {
            returnIntent = new Intent();
        }
        setResult(iResult, returnIntent);
        finish();
    }

    private void ShowLeaveDialog(String strMessage, String strTitle) {
        this.m_WaitingHintDialog.StopWaitingDialog();
        this.m_MSGDilaog.SetMSGListener(new C07568());
        this.m_MSGDilaog.ShowMessageDialog(strMessage, strTitle);
    }

    private void onNextButtonClicked() {
        CreateWaitingHintDialog(getString(C0349R.string.PLEASE_WAIT));
        new SaveImage().execute(new Void[0]);
    }

    public String[] GetAlbumInfo(String strPhotoPath, int iPos) {
        String searchParams = "_data = \"" + strPhotoPath + "\"";
        String[] projection = new String[IN_COLLAGE_FOLLOW_PERIOD];
        projection[0] = "bucket_display_name";
        projection[IN_COLLAGE_FOLLOW_START] = "bucket_id";
        Cursor cursor = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, projection, searchParams, null, null);
        int idIndex = cursor.getColumnIndex("bucket_id");
        int nameIndex = cursor.getColumnIndex("bucket_display_name");
        String strID = null;
        String strName = null;
        if (cursor.moveToFirst()) {
            strID = cursor.getString(idIndex);
            strName = cursor.getString(nameIndex);
        }
        cursor.close();
        return new String[]{strID, strName, String.valueOf(IN_COLLAGE_FOLLOW_START)};
    }

    private String[] GetAlbumInfoFromPrinter(int iPos) {
        Iterator it = this.m_pAlbumMetaList.iterator();
        while (it.hasNext()) {
            Pair<Integer, String[]> obj = (Pair) it.next();
            if (((Integer) obj.first).intValue() == iPos) {
                return (String[]) obj.second;
            }
        }
        this.m_prefAlbumInfo = new GlobalVariable_AlbumSelInfo(getBaseContext(), false);
        this.m_prefAlbumInfo.RestoreGlobalVariable();
        int iAlbumId = this.m_prefAlbumInfo.GetAlbumId();
        int iStorageId = this.m_prefAlbumInfo.GetAlbumStorageId();
        String strAlbumName = this.m_prefAlbumInfo.GetAlbumName();
        return new String[]{String.valueOf(iAlbumId), String.valueOf(iStorageId), strAlbumName, String.valueOf(IN_COLLAGE_FOLLOW_PERIOD)};
    }

    void ShowFineButtonGroup(boolean boShow) {
        if (this.m_H1FineButtonGroupRelativeLayout == null) {
            this.m_H1FineButtonGroupRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_H1FineButtonGroupRelativeLayout);
            this.m_HFineSacleDownImageButton = (ImageButton) findViewById(C0349R.id.m_HFineSacleDownImageButton);
            this.m_HFineSacleDownImageButton.setOnClickListener(new C02759());
            this.m_HFineSacleUpImageButton = (ImageButton) findViewById(C0349R.id.m_HFineSacleUpImageButton);
            this.m_HFineSacleUpImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()) != null) {
                        CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()).SetEditPhotoGarnishScale(0.1f);
                    }
                }
            });
            this.m_H2FineButtonGroupRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_H2FineButtonGroupRelativeLayout);
            this.m_HFineDownImageButton = (ImageButton) findViewById(C0349R.id.m_HFineDownImageButton);
            this.m_HFineDownImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()) != null) {
                        CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()).SetEditPhotoGarnishTrans(0.0f, 3.0f);
                    }
                }
            });
            this.m_HFineUpImageButton = (ImageButton) findViewById(C0349R.id.m_HFineUpImageButton);
            this.m_HFineUpImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()) != null) {
                        CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()).SetEditPhotoGarnishTrans(0.0f, -3.0f);
                    }
                }
            });
            this.m_HFineLeftImageButton = (ImageButton) findViewById(C0349R.id.m_HFineLeftImageButton);
            this.m_HFineLeftImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()) != null) {
                        CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()).SetEditPhotoGarnishTrans(3.0f, 0.0f);
                    }
                }
            });
            this.m_HFineRightImageButton = (ImageButton) findViewById(C0349R.id.m_HFineRightImageButton);
            this.m_HFineRightImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()) != null) {
                        CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()).SetEditPhotoGarnishTrans(-3.0f, 0.0f);
                    }
                }
            });
            this.m_V1FineButtonGroupRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_V1FineButtonGroupRelativeLayout);
            this.m_VFineSacleDownImageButton = (ImageButton) findViewById(C0349R.id.m_VFineSacleDownImageButton);
            this.m_VFineSacleDownImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()) != null) {
                        CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()).SetEditPhotoGarnishScale(-0.1f);
                    }
                }
            });
            this.m_VFineSacleUpImageButton = (ImageButton) findViewById(C0349R.id.m_VFineSacleUpImageButton);
            this.m_VFineSacleUpImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()) != null) {
                        CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()).SetEditPhotoGarnishScale(0.1f);
                    }
                }
            });
            this.m_V2FineButtonGroupRelativeLayout = (RelativeLayout) findViewById(C0349R.id.m_V2FineButtonGroupRelativeLayout);
            this.m_VFineDownImageButton = (ImageButton) findViewById(C0349R.id.m_VFineDownImageButton);
            this.m_VFineDownImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()) != null) {
                        CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()).SetEditPhotoGarnishTrans(0.0f, 3.0f);
                    }
                }
            });
            this.m_VFineUpImageButton = (ImageButton) findViewById(C0349R.id.m_VFineUpImageButton);
            this.m_VFineUpImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()) != null) {
                        CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()).SetEditPhotoGarnishTrans(0.0f, -3.0f);
                    }
                }
            });
            this.m_VFineLeftImageButton = (ImageButton) findViewById(C0349R.id.m_VFineLeftImageButton);
            this.m_VFineLeftImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()) != null) {
                        CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()).SetEditPhotoGarnishTrans(3.0f, 0.0f);
                    }
                }
            });
            this.m_VFineRightImageButton = (ImageButton) findViewById(C0349R.id.m_VFineRightImageButton);
            this.m_VFineRightImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()) != null) {
                        CollageActivity.this.m_CollageView.GetDrawView(CollageActivity.this.m_CollageView.GetLastSelectDrawView()).SetEditPhotoGarnishTrans(-3.0f, 0.0f);
                    }
                }
            });
        }
        if (!boShow) {
            this.m_H1FineButtonGroupRelativeLayout.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
            this.m_H2FineButtonGroupRelativeLayout.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
            this.m_V1FineButtonGroupRelativeLayout.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
            this.m_V2FineButtonGroupRelativeLayout.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
        } else if (this.m_boVertical) {
            this.m_H1FineButtonGroupRelativeLayout.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
            this.m_H2FineButtonGroupRelativeLayout.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
            this.m_V1FineButtonGroupRelativeLayout.setVisibility(0);
            this.m_V2FineButtonGroupRelativeLayout.setVisibility(0);
        } else {
            this.m_H1FineButtonGroupRelativeLayout.setVisibility(0);
            this.m_H2FineButtonGroupRelativeLayout.setVisibility(0);
            this.m_V1FineButtonGroupRelativeLayout.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
            this.m_V2FineButtonGroupRelativeLayout.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
        }
    }

    public void onAddPhotoImageButtonClicked(View v) {
        if (!this.m_bBackSource) {
            this.m_bBackSource = true;
            int iIndex = v.getId();
            int iSelectSourcePath = iIndex;
            this.m_iSelectCollageView = iIndex;
            ChangeSelectButtonBackground(iIndex);
            if (this.m_CollageEditPhotoPaths.size() != 0) {
                if (iIndex >= this.m_CollageEditPhotoPaths.size()) {
                    iSelectSourcePath = 0;
                }
                String strSelPath = (String) this.m_CollageEditPhotoPaths.get(iSelectSourcePath);
                Bundle bundle = new Bundle();
                int iRoute = this.m_iSelRoute;
                Iterator it = this.m_pAlbumMetaList.iterator();
                while (it.hasNext()) {
                    Pair<Integer, String[]> pair = (Pair) it.next();
                    if (((Integer) pair.first).intValue() == iIndex) {
                        iRoute = Integer.parseInt(((String[]) pair.second)[((String[]) pair.second).length - 1]);
                        break;
                    }
                }
                if (iRoute == IN_COLLAGE_FOLLOW_START) {
                    String[] albumMeta = GetAlbumInfo(strSelPath, iIndex);
                    if (albumMeta == null) {
                        albumMeta = GetAlbumInfo(this.m_strDefaultEditFile, iIndex);
                    }
                    if (albumMeta != null) {
                        this.m_iSelectCollageView = iIndex;
                        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_ALBUM_ID, albumMeta[0]);
                        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_ALBUM_NAME, albumMeta[IN_COLLAGE_FOLLOW_START]);
                    }
                } else {
                    String[] albumPara = GetAlbumInfoFromPrinter(iIndex);
                    if (albumPara != null) {
                        int iAlbumID = Integer.parseInt(albumPara[0]);
                        int iAlbumSID = Integer.parseInt(albumPara[IN_COLLAGE_FOLLOW_START]);
                        String strAlbumName = albumPara[IN_COLLAGE_FOLLOW_PERIOD];
                        bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_ALBUM_ID, iAlbumID);
                        bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_ALBUM_STORAGE_ID, iAlbumSID);
                        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_ALBUM_NAME, strAlbumName);
                    }
                }
                bundle.putInt(JumpBundleMessage520.BUNDLE_NSG_SEL_ROUTE, iRoute);
                bundle.putBoolean(JumpBundleMessage.BUNDLE_MSG_COLLAGE_FOLLOW, true);
                Intent intent = new Intent(getApplicationContext(), SourceActivity.class);
                intent.putExtras(bundle);
                if (this.m_pref == null) {
                    this.m_pref = new JumpPreferenceKey(this);
                }
                this.m_pref.SetPreference("BackID_Collage", true);
                this.m_pref.SetPreference("bFromCollageBegin", true);
                GlobalVariable_AlbumSelInfo prefRoute = new GlobalVariable_AlbumSelInfo(this, true);
                prefRoute.SetAlbumRoute(iRoute);
                prefRoute.SaveGlobalVariable();
                startActivityForResult(intent, 28);
            }
        }
    }

    void ChangeSelectButtonBackground(int iIndex) {
        if (this.m_CollageView.IsPhotoActionConnection()) {
            this.m_CollageView.ResetAddPhotoImageButtonBackgroundByGroup(iIndex, C0349R.drawable.button_select_collage_photo, true);
            this.m_CollageView.ResetAddPhotoImageButtonBackgroundByGroup(iIndex, C0349R.drawable.button_select_collage_photo, false);
            return;
        }
        for (int i = 0; i < this.m_CollageView.GetDrawViewNumbers(); i += IN_COLLAGE_FOLLOW_START) {
            if (iIndex == i) {
                this.m_CollageView.ResetAddPhotoImageButtonBackground(iIndex, C0349R.drawable.button_select_collage_photo);
            } else {
                this.m_CollageView.ResetAddPhotoImageButtonBackground(i, C0349R.drawable.button_select_collage_photo);
            }
        }
    }

    public void onRotateCollageButtonClicked(View v) {
        int temp = this.m_iW4x6;
        this.m_iW4x6 = this.m_iH4x6;
        this.m_iH4x6 = temp;
        this.m_boVertical = !this.m_boVertical;
        CreateWaitingHintDialog(getString(C0349R.string.PLEASE_WAIT));
        CreateCollage();
    }

    private void GetBundle(Bundle bundle) {
        if (bundle != null) {
            if (bundle.getString(JumpBundleMessage.BUNDLE_MSG_SELECT_PHOTO_PATH) != null) {
                this.m_CollageEditPhotoPaths.add(bundle.getString(JumpBundleMessage.BUNDLE_MSG_SELECT_PHOTO_PATH));
            }
            if (bundle.getString(JumpBundleMessage.BUNDLE_MSG_COLLAGE_DEFAULT_PHOTO_PHOTO_PATH) != null) {
                this.m_strDefaultEditFile = bundle.getString(JumpBundleMessage.BUNDLE_MSG_COLLAGE_DEFAULT_PHOTO_PHOTO_PATH);
            }
        }
    }

    private void GetMaxSizeConfig() {
        if (this.m_pref == null) {
            this.m_pref = new JumpPreferenceKey(this);
        }
        String strModel = this.m_pref.GetModelPreference();
        this.m_iW4x6 = Integer.parseInt(getString(C0349R.string.WIDTH_4x6));
        this.m_iH4x6 = Integer.parseInt(getString(C0349R.string.HEIGHT_4x6));
        if (strModel.equals(WirelessType.TYPE_P310W)) {
            this.m_iOutPutH4x6 = Integer.parseInt(getString(C0349R.string.OUTPUT_WIDTH_310w_4x6));
            this.m_iOutPutW4x6 = Integer.parseInt(getString(C0349R.string.OUTPUT_HEIGHT_310w_4x6));
            return;
        }
        this.m_iOutPutH4x6 = this.m_iW4x6;
        this.m_iOutPutW4x6 = this.m_iH4x6;
    }

    private void CalculateUISize() {
        GetMaxSizeConfig();
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        this.m_iScreenWidth = dm.widthPixels;
        this.m_iScreenHeight = dm.heightPixels;
        this.m_iEditDrawViewWidth = this.m_iScreenWidth;
        this.m_iEditDrawViewHeight = this.m_iScreenWidth;
        this.m_iCollageItemSize = this.m_iScreenWidth / 6;
        this.m_CollagerViewHorizontalSpacing = 12;
    }

    private void CalculateUIScale() {
        this.m_ViewScale = ((float) this.m_iH4x6) / ((float) this.m_iEditDrawViewHeight);
    }

    private boolean WillInitCollageByLastEdit() {
        SetFromLastEdit(true);
        if (this.m_boVertical != this.m_boLastVertical) {
            int temp = this.m_iW4x6;
            this.m_iW4x6 = this.m_iH4x6;
            this.m_iH4x6 = temp;
            this.m_boVertical = this.m_boLastVertical;
        }
        return true;
    }

    private boolean GetFromLastEdit() {
        this.LOG.m383d(this.TAG, "GetFromLastEdit " + this.m_FromLastEdit);
        return this.m_FromLastEdit;
    }

    private void SetFromLastEdit(boolean fromLastEdit) {
        this.m_FromLastEdit = fromLastEdit;
        this.LOG.m383d(this.TAG, "SetFromLastEdit " + this.m_FromLastEdit);
    }

    private boolean CreateCollage() {
        this.m_iCollageItemSizeWidth = (((int) (((float) this.m_iW4x6) / this.m_ViewScale)) / this.m_DefaultOrgThumbnailViewScale) + this.m_CollageViewHorizontalSpacing;
        this.m_iCollageItemSizeHeight = ((int) (((float) this.m_iH4x6) / this.m_ViewScale)) / this.m_DefaultOrgThumbnailViewScale;
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(this.m_iW4x6 / this.m_DefaultOrgThumbnailViewScale, this.m_iH4x6 / this.m_DefaultOrgThumbnailViewScale, Config.ARGB_8888);
        if (!bmr.IsSuccess()) {
            return false;
        }
        this.m_DefaultCollageThumbnailBmp = bmr.GetBitmap();
        this.m_DefaultCollageThumbnailBmp.eraseColor(-1);
        GetCollage();
        return true;
    }

    private void GetCollage() {
        if (this.m_strIDFormatPath != null) {
            String strCollageFile = FileUtility.GetFileNameWithoutExt(this.m_strIDFormatPath);
            String strCollageFolderPath = FileUtility.GetFolderFullPath(this.m_strIDFormatPath).replace("/thumb", XmlPullParser.NO_NAMESPACE);
            this.m_strLastCollageFolderPath = this.m_strCollageFolderPath;
            SelectCollage(strCollageFolderPath + "/" + strCollageFile + "/" + strCollageFile + ".xml");
        }
    }

    private boolean SelectCollage(String strCollageFilePath) {
        if (this.m_CollageInfoLoader != null) {
            this.m_CollageInfoLoader.Stop();
        }
        this.m_CollageInfoLoader = new CollageInfoLoader(this);
        CollageInfoLoader collageInfoLoader = this.m_CollageInfoLoader;
        String[] strArr = new String[IN_COLLAGE_FOLLOW_START];
        strArr[0] = strCollageFilePath;
        collageInfoLoader.execute(strArr);
        return true;
    }

    private boolean InitCollage(CollageInfoGroup newCollageInfoGroup) {
        if (this.m_EditPhotoLoader != null) {
            this.m_EditPhotoLoader.Stop();
        }
        CollageView newCollageView = new CollageView(this);
        float iCollageViewWidth = ((float) newCollageInfoGroup.GetPhotoWidth()) / this.m_ViewScale;
        float iCollageViewHeight = ((float) newCollageInfoGroup.GetPhotoHeight()) / this.m_ViewScale;
        newCollageView.InitCollageView(iCollageViewWidth, iCollageViewHeight, iCollageViewWidth, iCollageViewHeight);
        newCollageView.SetGSMaskColor(getResources().getColor(C0349R.color.GS_COLOR_NO_ALPHA));
        for (int i = this.m_CollageEditPhotoPaths.size(); i < newCollageInfoGroup.GetCollageNumbers(); i += IN_COLLAGE_FOLLOW_START) {
            this.m_CollageEditPhotoPaths.add(this.m_CollageEditPhotoPaths.get(0));
        }
        newCollageView.SetPhotoActionConnection(true);
        newCollageView.EnableDrawView(false);
        int iResult = newCollageView.SetCollageTemplete(this.m_ViewScale, this.m_CollageEditPhotoPaths, newCollageInfoGroup, C0349R.drawable.button_select_collage_photo, this.m_iOutPutW4x6, this.m_iOutPutH4x6);
        SetFocusCollagePaint(newCollageView);
        if (iResult != 0) {
            ShowGetBitmapErrorAlertDialog(BitmapMonitorResult.GetError(this, iResult));
            return false;
        }
        this.m_EditPhotoLoader = new EditPhotoLoader(this);
        this.m_EditPhotoLoader.SetEditPhotoInfo(newCollageView, this.m_CollageEditPhotoPaths);
        this.m_EditPhotoLoader.execute(new Void[0]);
        return true;
    }

    private void SetFocusCollagePaint(CollageView newCollageView) {
        Paint m_FoucsCollagePaint = new Paint();
        m_FoucsCollagePaint.reset();
        m_FoucsCollagePaint.setAntiAlias(true);
        m_FoucsCollagePaint.setStyle(Style.STROKE);
        m_FoucsCollagePaint.setColor(getResources().getColor(C0349R.color.FRAME_COLOR));
        m_FoucsCollagePaint.setStrokeWidth(TypedValue.applyDimension(IN_COLLAGE_FOLLOW_START, 4.0f, getResources().getDisplayMetrics()));
        newCollageView.SetFocusCollagePaint(m_FoucsCollagePaint);
    }

    boolean IsEditXMLPathEmpty() {
        if (this.m_strEditXMLPathList != null) {
            Iterator it = this.m_strEditXMLPathList.iterator();
            while (it.hasNext()) {
                if (!((String) it.next()).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    void InitEditPath() {
        int i;
        FileUtility.CreateFolder(this.mEditTempFolder);
        if (this.m_strEditXMLPathList.isEmpty()) {
            for (i = 0; i < this.m_CollageView.GetDrawViewNumbers(); i += IN_COLLAGE_FOLLOW_START) {
                this.m_strEditXMLPathList.add(XmlPullParser.NO_NAMESPACE);
            }
        }
        if (this.m_strEditTempThumbPathList == null) {
            this.m_strEditTempThumbPathList = new ArrayList();
            for (i = 0; i < this.m_CollageView.GetDrawViewNumbers(); i += IN_COLLAGE_FOLLOW_START) {
                this.m_strEditTempThumbPathList.add(XmlPullParser.NO_NAMESPACE);
            }
        }
    }

    void HaveCollageInfoThumbToChange() {
        String tmp = null;
        Iterator it = this.m_strEditTempThumbPathList.iterator();
        while (it.hasNext()) {
            String thumbPath = (String) it.next();
            if (!thumbPath.isEmpty() && (tmp == null || !tmp.equals(thumbPath))) {
                tmp = thumbPath;
                this.m_CollageView.ChangeEditPhoto(this.m_strEditTempThumbPathList.indexOf(thumbPath), thumbPath);
            }
        }
    }

    void HaveEditInfoToChangeCollage() {
        if (this.mEditThumbPath != null && this.mEditXMLpath != null) {
            ChangeCollageInfoPhotoAndSetXML(this.mEditThumbPath, this.mEditXMLpath);
            this.mEditThumbPath = null;
            this.mEditXMLpath = null;
        } else if (this.m_BackEditThumbPath != null) {
            HaveCollageInfoThumbToChange();
        }
    }

    void HaveInterruptLastEditPhoto() {
        this.LOG.m383d(this.TAG, "HaveInterruptLastEditPhoto: " + this.m_InterruptLastEditPhotoPath);
        if (this.m_InterruptLastEditPhotoPath != null) {
            ChangeCollageViewPhoto(this.m_InterruptLastEditPhotoPath);
            this.m_InterruptLastEditPhotoPath = null;
        }
    }

    private void ChangeCollageViewPhoto(String strPhotoPath) {
        if (strPhotoPath != null) {
            this.m_CollageView.ChangeEditPhoto(this.m_iSelectCollageView, strPhotoPath);
            int iGroupNum = this.m_CollageInfoGroup.GetCollageInfo(this.m_iSelectCollageView).GetGroup();
            for (int i = 0; i < this.m_CollageView.GetDrawViewNumbers(); i += IN_COLLAGE_FOLLOW_START) {
                if (this.m_CollageInfoGroup.GetCollageInfo(i).GetGroup() == iGroupNum) {
                    this.m_CollageEditPhotoPaths.remove(i);
                    this.m_CollageEditPhotoPaths.add(i, strPhotoPath);
                    this.m_strEditXMLPathList.remove(i);
                    this.m_strEditXMLPathList.add(i, XmlPullParser.NO_NAMESPACE);
                    this.m_strEditTempThumbPathList.remove(i);
                    this.m_strEditTempThumbPathList.add(i, XmlPullParser.NO_NAMESPACE);
                }
            }
            GetCollageInfoData(this.m_iSelectCollageView);
        }
    }

    private void ChangeCollageViewThumbPhotoAndXML(String strThumbPath, String strXML) {
        this.LOG.m383d(this.TAG, "ChangeCollageViewThumbPhotoAndXML: " + strThumbPath);
        if (strThumbPath != null) {
            HaveCollageInfoThumbToChange();
            int iGroupNum = this.m_CollageInfoGroup.GetCollageInfo(this.m_iSelectCollageView).GetGroup();
            for (int i = 0; i < this.m_CollageView.GetDrawViewNumbers(); i += IN_COLLAGE_FOLLOW_START) {
                if (this.m_CollageInfoGroup.GetCollageInfo(i).GetGroup() == iGroupNum) {
                    this.m_strEditXMLPathList.remove(i);
                    this.m_strEditXMLPathList.add(i, strXML);
                }
            }
            GetCollageInfoData(this.m_iSelectCollageView);
        }
    }

    private void ChangeCollageInfoPhotoAndSetXML(String strThumbPath, String strXML) {
        this.m_FilterColorValueList.set(this.m_CollageInfoGroup.GetCollageInfo(this.m_iSelectCollageView).GetGroup() - 1, this.m_FilterColorValue);
        this.m_strEditTempThumbPathList.set(this.m_iSelectCollageView, strThumbPath);
        this.LOG.m383d(this.TAG, this.m_iSelectCollageView + ") ChangeCollageInfoPhotoAndSetXML: " + strThumbPath);
        ChangeCollageViewThumbPhotoAndXML(strThumbPath, strXML);
    }

    void StartPrintView(String strCollagePath) {
        SaveMultiSelPref(strCollagePath);
        Intent data = new Intent(this, PrintViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.remove(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_NO_UNSHARPEN);
        bundle.putBoolean(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_NO_UNSHARPEN, this.m_bNoNeedUnsharpen);
        data.putExtras(bundle);
        startActivityForResult(data, 18);
    }

    void SaveMultiSelPref(String strCollagePath) {
        if (this.m_multiSelContainer == null) {
            this.m_multiSelContainer = new GlobalVariable_MultiSelContainer(this, IN_COLLAGE_FOLLOW_START);
        }
        ArrayList<String> strIdPhotoCollagePath = new ArrayList();
        strIdPhotoCollagePath.add(strCollagePath);
        this.m_multiSelContainer.RestoreGlobalVariable();
        this.m_multiSelContainer.ClearGlobalVariable();
        this.m_multiSelContainer.SetMobilePhotoPathAndId(strIdPhotoCollagePath, null);
        ArrayList<Integer> iCopieList = new ArrayList();
        iCopieList.add(Integer.valueOf(this.m_iCpoies));
        this.m_multiSelContainer.SetPhotoCopiesList(iCopieList);
        this.m_multiSelContainer.SaveGlobalVariable();
    }

    private void ShowGetBitmapErrorAlertDialog(String strMSG) {
        AlertDialog alertDialog = new Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setIcon(17301569);
        alertDialog.setTitle(getString(C0349R.string.ERROR));
        alertDialog.setMessage(strMSG);
        alertDialog.setButton(-1, getString(C0349R.string.OK), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                CollageActivity.this.onBackButtonClicked(null);
            }
        });
        alertDialog.show();
    }

    private void CreateWaitingHintDialog(String strMSG) {
        if (this.m_WaitingHintDialog == null) {
            this.m_WaitingHintDialog = new ShowMSGDialog(this, false);
        }
        this.m_WaitingHintDialog.ShowWaitingHintDialog(null, strMSG);
    }

    boolean CheckVerifyCollage(String strCollagePath) {
        if (strCollagePath == null) {
            return false;
        }
        GarnishSecurity security = this.m_HCollageGarnishSecurity;
        if (strCollagePath.contains("vcollage") || strCollagePath.contains("vbusinesscard") || strCollagePath.contains("vgreetingcard")) {
            security = this.m_VCollageGarnishSecurity;
        }
        if (security.CheckItemVerify(strCollagePath)) {
            return true;
        }
        return false;
    }

    private void GetCollageInfoData(int id) {
        if (this.m_CollageInfoGroup != null) {
            this.m_iSelCollageInfoWidth = (int) this.m_CollageInfoGroup.GetCollageInfo(id).GetWidth();
            this.m_iSelCollageInfoHeight = (int) this.m_CollageInfoGroup.GetCollageInfo(id).GetHeight();
            this.m_strSelCollageInfoPath = (String) this.m_CollageEditPhotoPaths.get(id);
            this.m_iSelectCollageView = id;
            SetCollageInfoGroup(this.m_CollageInfoGroup.GetCollageInfo(id).GetGroup());
            if (!this.m_EditButton.isEnabled()) {
                this.m_EditButton.setImageResource(C0349R.drawable.button_edit);
                this.m_EditButton.setEnabled(true);
            }
        }
    }

    private void SetCollageInfoGroup(int iGroup) {
        this.m_iCollageInfoGroup = iGroup;
    }

    private void SetIsVertival(boolean bIsVertical) {
        this.m_bIsVertical = bIsVertical;
    }

    private FilterColorValue GetFilterColorValue() {
        return (FilterColorValue) this.m_FilterColorValueList.get(this.m_iCollageInfoGroup - 1);
    }

    private boolean GetIsVertical() {
        return this.m_bIsVertical;
    }

    private int GetToEditCollageWidth() {
        return this.m_iSelCollageInfoWidth;
    }

    private int GetToEditCollageHeight() {
        return this.m_iSelCollageInfoHeight;
    }

    private int GetToEditCollageNumber() {
        return this.m_iSelectCollageView;
    }

    private String GetToEditCollagePath() {
        return this.m_strSelCollageInfoPath;
    }

    private void CheckWifi() {
        NetworkInfo mWifi = ((ConnectivityManager) getSystemService("connectivity")).getNetworkInfo(IN_COLLAGE_FOLLOW_START);
        this.m_WifiInfo.RestoreGlobalVariable();
        this.m_strLastSSID = this.m_WifiInfo.GetSSID();
        this.m_strSecurityKey = this.m_WifiInfo.GetPassword();
        this.m_strCurrentSSID = GetNowSSID();
        if (mWifi.isConnected()) {
            Log.e("isConnected", "isConnected");
            if (this.m_strCurrentSSID.contains(this.m_strLastSSID)) {
                Log.e("isConnected", "Match");
                ShowPrinterListDialog();
                return;
            }
            Log.e("isConnected", "notMatch");
            CreateConnectWifiHintDialog(this.m_strCurrentSSID, this.m_strLastSSID);
            return;
        }
        Log.e("not isConnected", "not isConnected");
        if (this.m_strLastSSID.length() != 0) {
            Log.e("isConnected", "auto scan");
            CreateWaitingHintDialog();
            this.m_wifiAutoConnect = new AutoWifiConnect(getBaseContext(), this.m_strLastSSID, this.m_strSecurityKey);
            this.m_wifiAutoConnect.execute(new Void[0]);
            return;
        }
        ShowNoWiFiDialog();
    }

    private void ShowPrinterListDialog() {
        if (this.m_ShowPrinterList == null) {
            this.m_ShowPrinterList = new ShowPrinterList(this);
            this.m_ShowPrinterList.SetPrinterListListener(new PrinterListListener() {
                public void PrinterListFinish(String strPrinterSSID, String IP, int iPort, String strConn) {
                    CollageActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    GetPrinterInfoToList(strPrinterSSID, IP, iPort, strConn);
                }

                public void IsBackStateOnMDNS(boolean bMDNS) {
                }

                public void BackFinish() {
                    CollageActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    CollageActivity.this.m_ProgressBar.setVisibility(CollageActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
                    CollageActivity.this.SetAllViewEnable(true);
                }

                public void BackStart() {
                    CollageActivity.this.getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    CollageActivity.this.m_ProgressBar.setVisibility(0);
                    CollageActivity.this.SetAllViewEnable(false);
                    if (CollageActivity.this.m_ShowPrinterList.IsShowing()) {
                        CollageActivity.this.m_ShowPrinterList.ListClose();
                    }
                }

                private void GetPrinterInfoToList(String strPrinterSSID, String IP, int iPort, String strConn) {
                    Intent intent = new Intent(CollageActivity.this.getBaseContext(), SettingPrinterActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, IP);
                    bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, iPort);
                    bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_SSID, strPrinterSSID);
                    bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_CONN_MODE, strConn);
                    intent.putExtras(bundle);
                    CollageActivity.this.startActivityForResult(intent, 24);
                }
            });
        }
        getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        this.m_ShowPrinterList.Show();
    }

    private String GetNowSSID() {
        String strSSID;
        WifiInfo wifiInfo = ((WifiManager) getSystemService("wifi")).getConnectionInfo();
        if (wifiInfo.getSSID() == null) {
            strSSID = XmlPullParser.NO_NAMESPACE;
        } else {
            strSSID = wifiInfo.getSSID();
        }
        Log.e("GetNowSSID", strSSID);
        return strSSID;
    }

    void CreateConnectWifiHintDialog(String strCurrentSSID, String strLastSSID) {
        this.m_ConnectWifiHintDialog = null;
        if (this.m_ConnectWifiHintDialog == null) {
            this.m_ConnectWifiHintDialog = new Builder(this).create();
            this.m_ConnectWifiHintDialog.setCanceledOnTouchOutside(false);
            this.m_ConnectWifiHintDialog.setCancelable(false);
            this.m_ConnectWifiDialogView = getLayoutInflater().inflate(C0349R.layout.dialog_sel_conn_wifi, null);
            RadioButton m_SelCurrentRadioButton = (RadioButton) this.m_ConnectWifiDialogView.findViewById(C0349R.id.m_SelCurrentRadioButton);
            RadioButton m_SelLastRadioButton = (RadioButton) this.m_ConnectWifiDialogView.findViewById(C0349R.id.m_SelLastRadioButton);
            ImageButton m_OKButton = (ImageButton) this.m_ConnectWifiDialogView.findViewById(C0349R.id.m_SelConnOKButton);
            ((ImageButton) this.m_ConnectWifiDialogView.findViewById(C0349R.id.m_SelConnCancelButton)).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    CollageActivity.this.m_ConnectWifiHintDialog.dismiss();
                }
            });
            m_OKButton.setOnClickListener(new AnonymousClass24(m_SelCurrentRadioButton, m_SelLastRadioButton));
            this.m_ConnectWifiHintDialog.setView(this.m_ConnectWifiDialogView);
            m_SelCurrentRadioButton.setText(getString(C0349R.string.DIALOG_CONN_WIFI_SEL_CURRENT) + strCurrentSSID);
            m_SelLastRadioButton.setText(getString(C0349R.string.DIALOG_CONN_WIFI_SEL_LAST) + strLastSSID);
        }
        this.m_ConnectWifiHintDialog.show();
    }

    void OpenWifi() {
        startActivityForResult(new Intent("android.settings.WIFI_SETTINGS"), 10);
    }

    public void ShowNoWiFiDialog() {
        if (this.m_MSGDilaog == null) {
            this.m_MSGDilaog = new ShowMSGDialog(this, false);
        }
        this.m_MSGDilaog.SetMSGListener(new MSGListener() {
            public void OKClick() {
                CollageActivity.this.OpenWifi();
            }

            public void Close() {
            }

            public void CancelClick() {
            }
        });
        String strTitle = getString(C0349R.string.UNABLE_TO_CONNECT_TO_PRINTER);
        this.m_MSGDilaog.ShowMessageDialog(getString(C0349R.string.PLEASE_SELECT_NETWORK), strTitle);
    }

    private void CreateWaitingHintDialog() {
        if (this.m_WaitingHintDialog == null) {
            this.m_WaitingHintDialog = new ShowMSGDialog(this, false);
        }
        this.m_WaitingHintDialog.ShowWaitingHintDialog(null, getString(C0349R.string.CONNECTING));
    }

    private void SetAllViewEnable(boolean bEnable) {
        this.m_bBackStop = !bEnable;
        this.m_StatusButton.setEnabled(bEnable);
        this.m_BackButton.setEnabled(bEnable);
        this.m_EditButton.setEnabled(bEnable);
        this.m_PrintViewButton.setEnabled(bEnable);
        this.m_AddButton.setEnabled(bEnable);
        this.m_ReduceButton.setEnabled(bEnable);
    }

    private void ShowErrorMSG(String strErr) {
        if (this.m_ErrorDialog == null) {
            this.m_ErrorDialog = new ShowMSGDialog(this, false);
            this.m_ErrorDialog.SetMSGListener(new MSGListener() {
                public void Close() {
                }

                public void OKClick() {
                }

                public void CancelClick() {
                }
            });
        }
        this.m_ErrorDialog.ShowSimpleMSGDialog(strErr, getString(C0349R.string.ERROR));
    }
}
