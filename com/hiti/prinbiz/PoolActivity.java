package com.hiti.prinbiz;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.Flurry.FlurryLogString;
import com.hiti.Flurry.FlurryUtility;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.jumpinfo.FlowMode;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.jumpinfo.JumpPreferenceKey.PHOTO_MODE;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.trace.GlobalVariable_WifiAutoConnectInfo;
import com.hiti.ui.cacheadapter.viewholder.BaseViewHolder;
import com.hiti.ui.cacheadapter.viewholder.GalleryViewHolder;
import com.hiti.ui.drawview.garnishitem.FilterColorValue;
import com.hiti.ui.drawview.garnishitem.utility.EditMeta;
import com.hiti.ui.drawview.garnishitem.utility.EditMeta.EditMode;
import com.hiti.ui.drawview.garnishitem.utility.EditMetaListener;
import com.hiti.ui.drawview.garnishitem.utility.EditMetaUtility;
import com.hiti.ui.drawview.garnishitem.utility.MakeEditPhoto;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.ui.indexview.CellTarget;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.PrinterListListener;
import com.hiti.utility.ShowPrinterList;
import com.hiti.utility.UserInfo;
import com.hiti.utility.Verify.ThreadMode;
import com.hiti.utility.dialog.DialogListener;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import com.hiti.utility.dialog.ShowMSGDialog.HintDialog;
import com.hiti.utility.grid.ImageAdapter;
import com.hiti.utility.grid.ImageAdapter.Type;
import com.hiti.utility.wifi.WifiAutoConnect;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.xmlpull.v1.XmlPullParser;

public class PoolActivity extends Activity {
    private static int COPIES_MAX;
    private static int COPIES_MIN;
    private OnClickListener DelPhotoListen;
    private OnClickListener GoEditListen;
    private OnClickListener GoToPrintViewListen;
    private String IP;
    LogManager LOG;
    private int MAX_HEIGHT;
    private int MAX_WIDTH;
    private OnClickListener StatusListen;
    String TAG;
    private FlowMode mFlowMode;
    NFCInfo mNFCInfo;
    private GlideAdater m_Adapter;
    private ImageButton m_AddButton;
    private ImageButton m_BackButton;
    private ProgressBar m_CloseListProgressBar;
    private ShowMSGDialog m_CropMSG;
    private ImageButton m_DelButton;
    private ImageButton m_EditButton;
    private EditMeta m_EditMeta;
    private EditMetaUtility m_EditMetaUtility;
    private ShowMSGDialog m_ErrorDialog;
    ArrayList<ArrayList<FilterColorValue>> m_FCVLList;
    private GridView m_GridView;
    private MakeEditPhoto m_MakeEditPhoto;
    private OnEditMetaListener m_OnEditMetaListener;
    private PHOTO_MODE m_PhotoMode;
    private ImageButton m_PrintViewButton;
    private ImageButton m_ReduceButton;
    private RadioButton m_SelectAllButton;
    private ShowMSGDialog m_ShowMSGDialog;
    private ShowPrinterList m_ShowPrinterList;
    private ImageButton m_StatusButton;
    private boolean m_ThreadStop;
    private GlobalVariable_WifiAutoConnectInfo m_WifiInfo;
    private boolean m_bBackStop;
    private boolean m_bFirstIn;
    private ArrayList<Boolean> m_bIsEditList;
    private ArrayList<Boolean> m_bIsVerticalList;
    private ArrayList<Integer> m_iBorderPosList;
    private ArrayList<Integer> m_iFilterPosList;
    private int m_iItemSize;
    private int m_iOriFValueLength;
    private ArrayList<Integer> m_iPhotoCopiesList;
    private ArrayList<Integer> m_iPhotoIdList;
    private ArrayList<Integer> m_iPhotoSIDList;
    private int m_iPort;
    private int m_iScreenWidth;
    private ArrayList<Integer> m_iSelIndexList;
    private int m_iSelRoute;
    private int m_iTotalThumbnail;
    private ArrayList<Long> m_lIsLowQtyList;
    private ArrayList<Long> m_lmobileIdList;
    private ArrayList<String> m_strCollagePathList;
    private String m_strCurrentSSID;
    private String m_strEdit;
    private String m_strFetch;
    private ArrayList<String> m_strFetchImgList;
    private String m_strLastSSID;
    private ArrayList<String> m_strMobilePathList;
    private long m_strOnePhotoID;
    private String m_strOnePhotoPath;
    private String m_strPath;
    private String m_strSecurityKey;
    private ArrayList<String> m_strThumbPathList;
    private ArrayList<String> m_strXMLpathList;
    private AutoWifiConnect m_wifiAutoConnect;

    /* renamed from: com.hiti.prinbiz.PoolActivity.15 */
    static /* synthetic */ class AnonymousClass15 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$utility$Verify$ThreadMode;

        static {
            $SwitchMap$com$hiti$utility$Verify$ThreadMode = new int[ThreadMode.values().length];
            try {
                $SwitchMap$com$hiti$utility$Verify$ThreadMode[ThreadMode.AutoWifi.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$utility$Verify$ThreadMode[ThreadMode.FethcImage.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$utility$Verify$ThreadMode[ThreadMode.MakePhoto.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$utility$Verify$ThreadMode[ThreadMode.DeleteToEdit.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.PoolActivity.3 */
    class C03253 implements OnClickListener {
        C03253() {
        }

        public void onClick(View v) {
            PoolActivity.this.SelAllClick();
        }
    }

    /* renamed from: com.hiti.prinbiz.PoolActivity.4 */
    class C03264 implements OnClickListener {
        C03264() {
        }

        public void onClick(View v) {
            PoolActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.PoolActivity.5 */
    class C03275 implements OnClickListener {
        C03275() {
        }

        public void onClick(View v) {
            Iterator it = PoolActivity.this.m_iSelIndexList.iterator();
            while (it.hasNext()) {
                int pos = ((Integer) it.next()).intValue();
                int iSelCp = ((Integer) PoolActivity.this.m_iPhotoCopiesList.get(pos)).intValue();
                if (iSelCp < PoolActivity.COPIES_MAX) {
                    PoolActivity.this.m_iPhotoCopiesList.set(pos, Integer.valueOf(iSelCp + 1));
                }
            }
            PoolActivity.this.m_Adapter.notifyDataSetChanged();
        }
    }

    /* renamed from: com.hiti.prinbiz.PoolActivity.6 */
    class C03286 implements OnClickListener {
        C03286() {
        }

        public void onClick(View v) {
            Iterator it = PoolActivity.this.m_iSelIndexList.iterator();
            while (it.hasNext()) {
                int pos = ((Integer) it.next()).intValue();
                int iSelCp = ((Integer) PoolActivity.this.m_iPhotoCopiesList.get(pos)).intValue();
                if (iSelCp > PoolActivity.COPIES_MIN) {
                    PoolActivity.this.m_iPhotoCopiesList.set(pos, Integer.valueOf(iSelCp - 1));
                }
            }
            PoolActivity.this.m_Adapter.notifyDataSetChanged();
        }
    }

    /* renamed from: com.hiti.prinbiz.PoolActivity.7 */
    class C03297 implements OnClickListener {
        C03297() {
        }

        public void onClick(View v) {
            PoolActivity.this.CheckWifi();
        }
    }

    /* renamed from: com.hiti.prinbiz.PoolActivity.8 */
    class C03308 implements OnClickListener {
        C03308() {
        }

        public void onClick(View v) {
            PoolActivity.this.PrepareToEdit();
        }
    }

    /* renamed from: com.hiti.prinbiz.PoolActivity.9 */
    class C03319 implements OnClickListener {
        C03319() {
        }

        public void onClick(View v) {
            if (!PoolActivity.this.m_iSelIndexList.isEmpty()) {
                if (PoolActivity.this.m_iSelRoute == 1) {
                    PoolActivity.this.DropSelPhotoOnMobile();
                } else {
                    PoolActivity.this.DropSelPhotoOnSDcard();
                }
                PoolActivity.this.DropEditInfoList();
                PoolActivity.this.m_iSelIndexList.clear();
                PoolActivity.this.LoadThumbnail();
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.PoolActivity.1 */
    class C07731 implements INfcPreview {
        C07731() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            PoolActivity.this.mNFCInfo = nfcInfo;
        }
    }

    /* renamed from: com.hiti.prinbiz.PoolActivity.2 */
    class C07742 extends DialogListener {
        C07742() {
        }

        public void SetNowConnSSID(String strNowSSID) {
            PoolActivity.this.ShowPrinterListDialog();
        }

        public void SetLastConnSSID(String strLastSSID) {
            PoolActivity.this.m_ShowMSGDialog.ShowWaitingHintDialog(ThreadMode.AutoWifi, "\n" + PoolActivity.this.getString(C0349R.string.CONNECTING) + "\n");
            PoolActivity.this.m_wifiAutoConnect = new AutoWifiConnect(PoolActivity.this.getBaseContext(), strLastSSID, PoolActivity.this.m_strSecurityKey);
            PoolActivity.this.m_wifiAutoConnect.execute(new Void[0]);
        }

        public void CancelConnetion(ThreadMode mode) {
            PoolActivity.this.ShowWaitDialog(ThreadMode.Non, false);
            PoolActivity.this.m_ThreadStop = true;
            switch (AnonymousClass15.$SwitchMap$com$hiti$utility$Verify$ThreadMode[mode.ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    if (PoolActivity.this.m_wifiAutoConnect != null) {
                        PoolActivity.this.m_wifiAutoConnect.ConnectionStop();
                    }
                    PoolActivity.this.m_bBackStop = false;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    if (PoolActivity.this.m_EditMetaUtility != null) {
                        PoolActivity.this.m_EditMetaUtility.FetchImageStop();
                    }
                    PoolActivity.this.ShowFetchImageDialog(false, XmlPullParser.NO_NAMESPACE);
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    if (PoolActivity.this.m_MakeEditPhoto != null) {
                        PoolActivity.this.m_MakeEditPhoto.SetStop(true);
                    }
                default:
            }
        }

        public void LeaveConfirm() {
        }

        public void LeaveCancel() {
        }

        public void LeaveClose() {
        }
    }

    class AutoWifiConnect extends WifiAutoConnect {
        public AutoWifiConnect(Context context, String strSSID, String strPassword) {
            super(context, strSSID, strPassword);
            PoolActivity.this.m_ThreadStop = false;
        }

        public void ConnectionSuccess() {
            Log.e("ConnectionSuccess", "ConnectionSuccess");
            ConnectionStop();
            if (!PoolActivity.this.m_ThreadStop) {
                PoolActivity.this.ShowPrinterListDialog();
            }
        }

        public void ConnectionFail() {
            Log.e("ConnectionFail", "ConnectionFail");
            ConnectionStop();
            if (!PoolActivity.this.m_ThreadStop) {
                PoolActivity.this.ShowNoWiFiDialog();
            }
        }

        public void ConnectionStop() {
            Log.e("autowifi", "stop");
            PoolActivity.this.m_ShowMSGDialog.StopWaitingDialog();
            SetStop(true);
        }
    }

    class GlideAdater extends ImageAdapter {

        /* renamed from: com.hiti.prinbiz.PoolActivity.GlideAdater.1 */
        class C03321 implements OnClickListener {
            final /* synthetic */ GalleryViewHolder val$holder;

            C03321(GalleryViewHolder galleryViewHolder) {
                this.val$holder = galleryViewHolder;
            }

            public void onClick(View v) {
                GlideAdater.this.SelectPicture(this.val$holder);
            }
        }

        /* renamed from: com.hiti.prinbiz.PoolActivity.GlideAdater.2 */
        class C07752 implements Callback {
            final /* synthetic */ BaseViewHolder val$holder;

            C07752(BaseViewHolder baseViewHolder) {
                this.val$holder = baseViewHolder;
            }

            public void onSuccess() {
                if (PoolActivity.this.m_iSelRoute == 1) {
                    PoolActivity.this.m_Adapter.AfterBuildImage(this.val$holder, false);
                }
            }

            public void onError() {
                if (PoolActivity.this.m_iSelRoute == 1) {
                    PoolActivity.this.m_Adapter.AfterBuildImage(this.val$holder, true);
                }
            }
        }

        public GlideAdater(Context context, Type type) {
            super(context, type);
        }

        public View SetHolder(LayoutInflater inflater, BaseViewHolder mHolder) {
            GalleryViewHolder holder = (GalleryViewHolder) mHolder;
            View convertView = inflater.inflate(C0349R.layout.item_pool, null);
            holder.m_HolderImageView = (ImageView) convertView.findViewById(C0349R.id.m_ContentImageView);
            holder.m_HolderImageView.getLayoutParams().height = PoolActivity.this.m_iItemSize;
            holder.m_HolderImageView.getLayoutParams().width = PoolActivity.this.m_iItemSize;
            holder.m_CheckView = (ImageView) convertView.findViewById(C0349R.id.m_ItemFrameImageView);
            holder.m_CheckView.getLayoutParams().height = PoolActivity.this.m_iItemSize;
            holder.m_CheckView.getLayoutParams().width = PoolActivity.this.m_iItemSize;
            holder.m_EditView = (ImageView) convertView.findViewById(C0349R.id.editImage);
            holder.m_QtyView = (ImageView) convertView.findViewById(C0349R.id.m_ItemQtyImageView);
            holder.m_QtyView.getLayoutParams().height = PoolActivity.this.m_iItemSize;
            holder.m_QtyView.getLayoutParams().width = PoolActivity.this.m_iItemSize;
            holder.m_AmountButton = (Button) convertView.findViewById(C0349R.id.m_ItemAmountText);
            holder.m_AmountButton.getLayoutParams().width = PoolActivity.this.m_iItemSize;
            holder.m_AmountButton.getLayoutParams().height = PoolActivity.this.m_iItemSize;
            holder.m_QtyView.setVisibility(8);
            holder.m_AmountButton.setOnClickListener(new C03321(holder));
            return convertView;
        }

        public void SetItem(BaseViewHolder mHolder) {
            GalleryViewHolder holder = (GalleryViewHolder) mHolder;
            int position = holder.m_iID;
            holder.m_AmountButton.setId(position);
            holder.m_CheckView.setId(position);
            holder.m_HolderImageView.setId(position);
            holder.m_AmountButton.setText(String.valueOf(((Integer) PoolActivity.this.m_iPhotoCopiesList.get(position)).intValue()));
        }

        public void ClearViewHodler(BaseViewHolder holder) {
            Picasso.with(PoolActivity.this).cancelRequest(holder.m_HolderImageView);
        }

        public void SetImageView(BaseViewHolder holder) {
            String strPath;
            int position = holder.m_iID;
            if (PoolActivity.this.m_iSelRoute == 1) {
                strPath = (String) PoolActivity.this.m_strMobilePathList.get(position);
            } else {
                strPath = (String) PoolActivity.this.m_strThumbPathList.get(position);
            }
            Picasso.with(PoolActivity.this).load(Uri.parse("file://" + strPath)).centerCrop().resize(PoolActivity.this.m_iItemSize, PoolActivity.this.m_iItemSize).noFade().placeholder((int) C0349R.drawable.thumb_album).into(holder.m_HolderImageView, new C07752(holder));
        }

        void SelectPicture(GalleryViewHolder holder) {
            int id = holder.m_iID;
            if (PoolActivity.this.m_iSelIndexList.contains(Integer.valueOf(id))) {
                PoolActivity.this.m_iSelIndexList.remove(PoolActivity.this.m_iSelIndexList.indexOf(Integer.valueOf(id)));
                holder.m_CheckView.setVisibility(8);
                return;
            }
            PoolActivity.this.m_iSelIndexList.add(Integer.valueOf(id));
            holder.m_CheckView.setVisibility(0);
        }

        public int GetTotalCout() {
            return PoolActivity.this.m_iTotalThumbnail;
        }

        public Bitmap GetPhotoThumbnail(int id) {
            return CellTarget.GetThumbnail(PoolActivity.this, ((Long) PoolActivity.this.m_lmobileIdList.get(id)).longValue(), C0349R.drawable.thumb_photo);
        }

        public boolean PhotoQualityCheck(int id) {
            return BitmapMonitor.IsPhotoLowQuality(PoolActivity.this, Uri.parse("file://" + ((String) PoolActivity.this.m_strMobilePathList.get(id))), PoolActivity.this.MAX_WIDTH, PoolActivity.this.MAX_HEIGHT);
        }

        public void ViewQualityCheck(BaseViewHolder mHolder, boolean bLow) {
            if (PoolActivity.this.m_iSelRoute == 1) {
                ((GalleryViewHolder) mHolder).m_QtyView.setVisibility(bLow ? 0 : 8);
            }
        }

        public void ViewSelectCheck(BaseViewHolder mHolder) {
            GalleryViewHolder holder = (GalleryViewHolder) mHolder;
            int position = holder.m_iID;
            if (PoolActivity.this.m_bIsEditList.isEmpty()) {
                holder.m_EditView.setVisibility(8);
            } else if (((Boolean) PoolActivity.this.m_bIsEditList.get(position)).booleanValue()) {
                holder.m_EditView.setVisibility(0);
            } else {
                holder.m_EditView.setVisibility(8);
            }
            if (PoolActivity.this.m_iSelIndexList.contains(Integer.valueOf(holder.m_iID))) {
                holder.m_CheckView.setVisibility(0);
            } else {
                holder.m_CheckView.setVisibility(8);
            }
            if (PoolActivity.this.m_iSelIndexList.size() < PoolActivity.this.m_iTotalThumbnail) {
                PoolActivity.this.m_SelectAllButton.setButtonDrawable(C0349R.drawable.select_button);
            } else if (PoolActivity.this.m_iSelIndexList.size() == PoolActivity.this.m_iTotalThumbnail) {
                PoolActivity.this.m_SelectAllButton.setButtonDrawable(C0349R.drawable.select_button_clicked);
            }
        }
    }

    private class OnEditMetaListener extends EditMetaListener {
        private OnEditMetaListener() {
        }

        public void FetchingBegin() {
            PoolActivity.this.ShowFetchImageDialog(true, null);
        }

        public void FetchImageDone(int pos, String strImagePath) {
            PoolActivity.this.ShowFetchImageDialog(false, null);
            if (strImagePath != null) {
                PoolActivity.this.m_strFetchImgList.set(pos, strImagePath);
                PoolActivity.this.ShowWaitDialog(ThreadMode.DeleteToEdit, true);
                PoolActivity.this.m_EditMetaUtility.DeleteFLValueData();
            }
        }

        public void FetchImgRatio(int iPos, String strRatio) {
            PoolActivity.this.ShowFetchImageDialog(true, strRatio);
        }

        public void FetchImgTimeOut(String strErr) {
            PoolActivity.this.ShowErrorMSG(strErr);
        }

        public void FetchImgError(String strErr) {
            PoolActivity.this.ShowErrorMSG(strErr);
        }

        public void InitDrawViewEnd(int iPos) {
        }

        public void InitDrawView(int iPos) {
        }

        public void SaveEditPhoto() {
            if (!PoolActivity.this.m_ThreadStop) {
                PoolActivity.this.DelForFilterValue();
            }
        }

        public void SaveEditPhotoDone() {
            if (!PoolActivity.this.m_ThreadStop) {
                PoolActivity.this.NextToPirintView();
            }
        }

        public void DelFLValueData() {
            PoolActivity.this.DelForFilterValue();
            PoolActivity.this.SetEditMeta(EditMode.Edit);
            PoolActivity.this.GoToEditPage();
        }
    }

    public PoolActivity() {
        this.m_PrintViewButton = null;
        this.m_EditButton = null;
        this.m_DelButton = null;
        this.m_StatusButton = null;
        this.m_GridView = null;
        this.m_Adapter = null;
        this.m_AddButton = null;
        this.m_ReduceButton = null;
        this.m_BackButton = null;
        this.m_SelectAllButton = null;
        this.m_ShowMSGDialog = null;
        this.m_iSelRoute = 0;
        this.m_iPhotoCopiesList = null;
        this.m_iSelIndexList = null;
        this.m_bIsEditList = null;
        this.m_bIsVerticalList = null;
        this.m_strXMLpathList = null;
        this.m_strFetchImgList = null;
        this.m_strMobilePathList = null;
        this.m_strCollagePathList = null;
        this.m_lmobileIdList = null;
        this.m_lIsLowQtyList = null;
        this.m_strThumbPathList = null;
        this.m_iPhotoIdList = null;
        this.m_iPhotoSIDList = null;
        this.m_iTotalThumbnail = 0;
        this.m_iOriFValueLength = 0;
        this.m_EditMetaUtility = null;
        this.m_OnEditMetaListener = null;
        this.m_EditMeta = null;
        this.m_ErrorDialog = null;
        this.m_strPath = PringoConvenientConst.PRINGO_TEMP_FOLDER + File.separator;
        this.m_strFetch = this.m_strPath + PringoConvenientConst.PRINBIZ_FETCH_IMG_FOLDER;
        this.m_strEdit = this.m_strPath + PringoConvenientConst.PRINBIZ_EDIT_IMG_FOLDER;
        this.MAX_WIDTH = 0;
        this.MAX_HEIGHT = 0;
        this.m_iScreenWidth = 0;
        this.m_iItemSize = 0;
        this.m_strLastSSID = null;
        this.m_strCurrentSSID = null;
        this.m_strSecurityKey = null;
        this.m_wifiAutoConnect = null;
        this.m_CloseListProgressBar = null;
        this.m_WifiInfo = null;
        this.m_ShowPrinterList = null;
        this.m_bBackStop = false;
        this.m_FCVLList = null;
        this.m_iBorderPosList = null;
        this.m_iFilterPosList = null;
        this.m_ThreadStop = false;
        this.m_MakeEditPhoto = null;
        this.IP = XmlPullParser.NO_NAMESPACE;
        this.m_iPort = 0;
        this.m_CropMSG = null;
        this.m_strOnePhotoPath = null;
        this.m_strOnePhotoID = 0;
        this.m_bFirstIn = true;
        this.m_PhotoMode = PHOTO_MODE.MODE_DEFAULT;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = null;
        this.mFlowMode = FlowMode.Normal;
        this.StatusListen = new C03297();
        this.GoEditListen = new C03308();
        this.DelPhotoListen = new C03319();
        this.GoToPrintViewListen = new OnClickListener() {
            public void onClick(View v) {
                if (!PoolActivity.this.m_iPhotoCopiesList.isEmpty()) {
                    PoolActivity.this.ShowWaitDialog(ThreadMode.Non, true);
                    PoolActivity.this.SetEditMeta(EditMode.Print);
                    PoolActivity.this.NextToPirintView();
                }
            }
        };
    }

    static {
        COPIES_MAX = 90;
        COPIES_MIN = 1;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_pool);
        this.LOG = new LogManager(0);
        this.TAG = getClass().getSimpleName();
        this.LOG.m383d(this.TAG, "onCreate savedInstanceState:" + savedInstanceState);
        if (GetBundle()) {
            InitalEditMeta(savedInstanceState);
            SetPrintOut();
            SetMSGDialog();
            FlurryUtility.init(this, FlurryUtility.FLURRY_API_KEY_PRINBIZ);
            if (savedInstanceState == null) {
                ShowCropHint();
            }
            SetView();
            LoadThumbnail();
            if (this.m_PhotoMode.equals(PHOTO_MODE.MODE_OUTPHOTO) && this.m_bFirstIn) {
                this.m_iSelIndexList.add(Integer.valueOf(0));
                PrepareToEdit();
                return;
            }
            return;
        }
        finish();
    }

    protected void onStart() {
        super.onStart();
        FlurryUtility.onStartSession(this);
        FlurryUtility.logEvent(FlurryLogString.UI_PAGE_pool_TARGET_stay_time, FlurryLogString.KEY_USER, UserInfo.GetUser(this));
    }

    protected void onNewIntent(Intent intent) {
        this.LOG.m386v("onNewIntent ", XmlPullParser.NO_NAMESPACE + intent);
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        if (this.mFlowMode == FlowMode.BackMainPage) {
            finish();
            return;
        }
        if (this.mFlowMode == FlowMode.SaveInstance) {
            SetEditMeta(EditMode.Edit);
        }
        this.mFlowMode = FlowMode.Normal;
        if (this.m_Adapter != null) {
            this.m_Adapter.notifyDataSetChanged();
        }
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07731());
    }

    protected void onPause() {
        if (!(this.mFlowMode == FlowMode.BackMainPage || this.mNFCInfo.mNfcAdapter == null)) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        FlurryUtility.endTimedEvent(FlurryLogString.UI_PAGE_pool_TARGET_stay_time);
        FlurryUtility.onEndSession(this);
    }

    private void ShowCropHint() {
        int iPathRoute = this.m_EditMetaUtility.GetPathRoute();
        if (iPathRoute == ControllerState.NO_PLAY_ITEM) {
            this.m_CropMSG = new ShowMSGDialog(this, false);
            if (this.m_CropMSG.IsNeedHintAgain(HintDialog.Crop, iPathRoute)) {
                this.m_CropMSG.ShowCropWaringDialog(iPathRoute, getString(C0349R.string.HINT_TITLE));
            }
        }
    }

    private void SetMSGDialog() {
        this.m_ShowMSGDialog = new ShowMSGDialog(this, true);
        this.m_ShowMSGDialog.SetDialogListener(new C07742());
    }

    private boolean GetBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
            this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        } else if (bundle.get("android.intent.extra.STREAM") != null) {
            this.m_PhotoMode = PHOTO_MODE.MODE_OUTPHOTO;
            for (String key : bundle.keySet()) {
                Log.i("key", key);
            }
            String[] proj = new String[]{"_data", "_id"};
            Cursor cursor = getContentResolver().query((Uri) bundle.get("android.intent.extra.STREAM"), proj, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    int iPathIndex = cursor.getColumnIndex(proj[0]);
                    int iIdIndex = cursor.getColumnIndex(proj[1]);
                    this.m_strOnePhotoPath = cursor.getString(iPathIndex);
                    this.m_strOnePhotoID = cursor.getLong(iIdIndex);
                }
                cursor.close();
                if (this.m_strOnePhotoPath == null) {
                    this.m_strOnePhotoPath = ((Uri) getIntent().getParcelableExtra("android.intent.extra.STREAM")).toString();
                }
            } else if (getIntent().getData() == null) {
                return false;
            } else {
                this.m_strOnePhotoPath = getIntent().getData().getPath();
            }
        } else {
            this.IP = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP);
            this.m_iPort = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT);
        }
        return true;
    }

    protected void onSaveInstanceState(Bundle outState) {
        int i;
        this.LOG.m383d(this.TAG, "onSaveInstanceState");
        outState.remove(JumpBundleMessage520.BUNDLE_MSG_COPIES_LIST);
        outState.remove(JumpBundleMessage520.BUNDLE_MSG_SEL_PHOTO_ID_LIST);
        outState.putIntegerArrayList(JumpBundleMessage520.BUNDLE_MSG_COPIES_LIST, this.m_iPhotoCopiesList);
        outState.putIntegerArrayList(JumpBundleMessage520.BUNDLE_MSG_SEL_EDIT_INDEX, this.m_iSelIndexList);
        outState.putBoolean(JumpBundleMessage.BUNDLE_MSG_FIRST_OUTPUTPHOTO, this.m_bFirstIn);
        for (i = 0; i < this.m_bIsEditList.size(); i++) {
            outState.remove(JumpBundleMessage520.BUNDLE_MSG_IS_EDIT + i);
            outState.putBoolean(JumpBundleMessage520.BUNDLE_MSG_IS_EDIT + i, ((Boolean) this.m_bIsEditList.get(i)).booleanValue());
        }
        if (this.m_iSelRoute == 1) {
            for (i = 0; i < this.m_lmobileIdList.size(); i++) {
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_SEL_EDIT_INDEX + i);
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_SEL_THUMB_PATH + i);
                outState.putLong(JumpBundleMessage520.BUNDLE_MSG_SEL_PHOTO_ID_LIST + i, ((Long) this.m_lmobileIdList.get(i)).longValue());
                outState.putString(JumpBundleMessage520.BUNDLE_MSG_SEL_THUMB_PATH + i, (String) this.m_strMobilePathList.get(i));
            }
        } else {
            for (i = 0; i < this.m_iPhotoIdList.size(); i++) {
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP);
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT);
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_SEL_PHOTO_ID + i);
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_SEL_STORAGE_ID + i);
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_SEL_EDIT_PATH + i);
                outState.remove(JumpBundleMessage520.BUNDLE_MSG_FETCH_IMG + i);
                outState.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
                outState.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
                outState.putInt(JumpBundleMessage520.BUNDLE_MSG_SEL_PHOTO_ID + i, ((Integer) this.m_iPhotoIdList.get(i)).intValue());
                outState.putInt(JumpBundleMessage520.BUNDLE_MSG_SEL_STORAGE_ID + i, ((Integer) this.m_iPhotoSIDList.get(i)).intValue());
                outState.putString(JumpBundleMessage520.BUNDLE_MSG_SEL_EDIT_PATH + i, (String) this.m_strThumbPathList.get(i));
                if (i < this.m_strFetchImgList.size()) {
                    outState.putString(JumpBundleMessage520.BUNDLE_MSG_FETCH_IMG + i, (String) this.m_strFetchImgList.get(i));
                }
            }
        }
        super.onSaveInstanceState(outState);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.LOG.m386v("pool ", "onActivityResult requestCode: " + requestCode);
        switch (requestCode) {
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                this.m_ShowMSGDialog.StopWaitingDialog();
                CheckWifi();
            case ConnectionResult.SERVICE_MISSING_PERMISSION /*19*/:
                if (resultCode == 50) {
                    this.mFlowMode = FlowMode.BackMainPage;
                    setResult(50);
                }
            case TFTP.DEFAULT_PORT /*69*/:
                this.mFlowMode = FlowMode.ActivityResult;
                this.m_DelButton.setEnabled(false);
                GetFilterValue();
                this.m_EditMeta = this.m_EditMetaUtility.GetEditMeta(this.m_iSelRoute);
                this.m_iPhotoCopiesList = this.m_EditMeta.GetCopiesList();
                GetEditMeta();
                GetFetchAndThumbData();
            default:
        }
    }

    private void GetEditMeta() {
        this.LOG.m385i(this.TAG, "GetEditMeta");
        this.m_strXMLpathList = this.m_EditMeta.GetXMLList();
        this.m_bIsVerticalList = this.m_EditMeta.GetIsVerticalList();
        this.m_bIsEditList = this.m_EditMeta.GetIsEditList();
        this.m_iBorderPosList = this.m_EditMeta.GetBorderPosList();
        this.m_iFilterPosList = this.m_EditMeta.GetFilterPosList();
        this.m_strCollagePathList = this.m_EditMeta.GetCollagePathList();
        this.m_iSelIndexList = this.m_EditMeta.GetSelPosList();
        this.LOG.m385i(this.TAG, "GetEditMeta m_strCollagePathList: " + this.m_strCollagePathList);
    }

    private void GetFetchAndThumbData() {
        if (this.m_iSelRoute == 2) {
            this.m_strFetchImgList = this.m_EditMeta.GetFetchPathList();
            this.m_strThumbPathList = this.m_EditMeta.GetThumbPathList();
            if (this.m_strFetchImgList.isEmpty()) {
                for (int i = 0; i < this.m_iPhotoCopiesList.size(); i++) {
                    this.m_strFetchImgList.add(XmlPullParser.NO_NAMESPACE);
                }
            }
        }
    }

    private void SetEditMeta(EditMode mode) {
        this.LOG.m385i(this.TAG, "SetEditMeta mode: " + mode);
        this.LOG.m385i(this.TAG, "SetEditMeta m_iSelRoute: " + this.m_iSelRoute);
        this.m_EditMeta = new EditMeta(this.m_iSelRoute);
        this.m_EditMeta.SetCopies(this.m_iPhotoCopiesList);
        if (this.m_bIsEditList.contains(Boolean.valueOf(true))) {
            this.m_EditMeta.SetBorderAndFilterPos(this.m_iBorderPosList, this.m_iFilterPosList);
            this.m_EditMeta.SetXMLVerticalIsEdit(this.m_strXMLpathList, this.m_bIsVerticalList, this.m_bIsEditList);
        }
        if (mode == EditMode.Edit) {
            this.m_EditMeta.SetEditSelPos(this.m_iSelIndexList);
        }
        ArrayList<String> strEditPathList = CheckHaveCollagePhoto();
        if (this.m_iSelRoute == 1) {
            this.m_EditMeta.SetMobilePathAndID(strEditPathList, this.m_lmobileIdList);
        } else {
            this.m_EditMeta.SetThumbPathAndName(this.m_strThumbPathList, null);
            this.m_EditMeta.SetSDcardMeta(this.m_iPhotoIdList, this.m_iPhotoSIDList);
            this.m_EditMeta.SetFetchPath(strEditPathList);
            this.LOG.m385i(this.TAG, "To Print photo id: " + this.m_iPhotoIdList);
        }
        this.m_EditMetaUtility.SetEditMeta(this.m_EditMeta);
    }

    private ArrayList<String> CheckHaveCollagePhoto() {
        ArrayList<String> strEditPathList;
        this.LOG.m383d(this.TAG, "CheckHaveCollagePhoto m_strCollagePathList:" + this.m_strCollagePathList);
        if (this.m_iSelRoute == 1) {
            strEditPathList = this.m_strMobilePathList;
        } else {
            strEditPathList = this.m_strFetchImgList;
        }
        if (!(this.m_strCollagePathList == null || this.m_strCollagePathList.isEmpty())) {
            for (int i = 0; i < this.m_strCollagePathList.size(); i++) {
                if (!((String) this.m_strCollagePathList.get(i)).isEmpty()) {
                    strEditPathList.set(i, this.m_strCollagePathList.get(i));
                }
            }
        }
        return strEditPathList;
    }

    private void InitialEditXMLVerticalData() {
        this.m_strXMLpathList = new ArrayList();
        this.m_bIsVerticalList = new ArrayList();
        this.m_bIsEditList = new ArrayList();
        for (int i = 0; i < this.m_iPhotoCopiesList.size(); i++) {
            this.m_strXMLpathList.add(XmlPullParser.NO_NAMESPACE);
            this.m_bIsVerticalList.add(Boolean.valueOf(false));
            this.m_bIsEditList.add(Boolean.valueOf(false));
        }
    }

    private void InitialBorderAndFilterData() {
        this.m_iBorderPosList = new ArrayList();
        this.m_iFilterPosList = new ArrayList();
        for (int i = 0; i < this.m_iPhotoCopiesList.size(); i++) {
            this.m_iBorderPosList.add(Integer.valueOf(0));
            this.m_iFilterPosList.add(Integer.valueOf(0));
        }
    }

    private void GetFilterValue() {
        int i;
        if (this.m_FCVLList != null) {
            this.m_FCVLList.clear();
        } else {
            this.m_FCVLList = new ArrayList();
        }
        for (i = 0; i < this.m_iPhotoCopiesList.size(); i++) {
            this.m_FCVLList.add(new ArrayList());
        }
        for (i = 0; i < this.m_iPhotoCopiesList.size(); i++) {
            ArrayList<FilterColorValue> m_FCVList = new ArrayList();
            for (int j = 0; j < 25; j++) {
                FilterColorValue fcv = new FilterColorValue();
                fcv.m_fHue = this.m_EditMetaUtility.GetFilterValue(i, j, "_Hue");
                fcv.m_fSaturation = this.m_EditMetaUtility.GetFilterValue(i, j, "_Saturation");
                fcv.m_fLight = this.m_EditMetaUtility.GetFilterValue(i, j, "_Light");
                fcv.m_fContrast = this.m_EditMetaUtility.GetFilterValue(i, j, "_Contrast");
                fcv.m_fRed = this.m_EditMetaUtility.GetFilterValue(i, j, "_Red");
                fcv.m_fGreen = this.m_EditMetaUtility.GetFilterValue(i, j, "_Green");
                fcv.m_fBlue = this.m_EditMetaUtility.GetFilterValue(i, j, "_Blue");
                m_FCVList.add(fcv);
            }
            this.m_FCVLList.set(i, m_FCVList);
        }
        this.m_iOriFValueLength = this.m_FCVLList.size();
        this.m_DelButton.setEnabled(true);
    }

    private void InitalEditMeta(Bundle savedInstanceState) {
        if (this.m_PhotoMode.equals(PHOTO_MODE.MODE_OUTPHOTO) || this.m_PhotoMode.equals(PHOTO_MODE.MODE_CLOUDALBUM)) {
            EditMetaUtility.SetSrcRoute(this, 1);
            if (this.m_PhotoMode.equals(PHOTO_MODE.MODE_OUTPHOTO)) {
                EditMetaUtility.SetPathRoute(this, ControllerState.NO_PLAY_ITEM);
            }
        }
        this.m_EditMetaUtility = new EditMetaUtility(this);
        this.m_OnEditMetaListener = new OnEditMetaListener();
        this.m_EditMetaUtility.SetEditMetaListener(this.m_OnEditMetaListener);
        this.m_EditMetaUtility.SetIPandPort(this.IP, this.m_iPort);
        this.m_iSelRoute = EditMetaUtility.GetSrcRoute(this);
        this.LOG.m385i(this.TAG, "InitalEditMeta m_iSelRoute: " + this.m_iSelRoute);
        if (savedInstanceState != null) {
            GetSaveInstance(savedInstanceState);
            this.mFlowMode = FlowMode.SaveInstance;
        } else {
            this.m_EditMeta = this.m_EditMetaUtility.GetEditMeta(this.m_iSelRoute);
            this.m_iPhotoCopiesList = this.m_EditMeta.GetCopiesList();
            this.m_EditMetaUtility.CleanFilterValuePref();
            if (this.m_iSelRoute == 1) {
                GetMobilePathAndID();
            } else {
                GetSDcardIDAndPath();
            }
        }
        InitialEditXMLVerticalData();
        InitialBorderAndFilterData();
        this.m_iSelIndexList = new ArrayList();
        this.m_strCollagePathList = new ArrayList();
        this.m_lIsLowQtyList = new ArrayList();
    }

    private void GetSaveInstance(Bundle savedInstanceState) {
        int i;
        this.m_iPhotoCopiesList = savedInstanceState.getIntegerArrayList(JumpBundleMessage520.BUNDLE_MSG_COPIES_LIST);
        this.m_iSelIndexList = savedInstanceState.getIntegerArrayList(JumpBundleMessage520.BUNDLE_MSG_SEL_EDIT_INDEX);
        this.m_bFirstIn = savedInstanceState.getBoolean(JumpBundleMessage.BUNDLE_MSG_FIRST_OUTPUTPHOTO);
        this.m_bIsEditList = new ArrayList();
        for (i = 0; i < this.m_iPhotoCopiesList.size(); i++) {
            this.m_bIsEditList.add(Boolean.valueOf(savedInstanceState.getBoolean(JumpBundleMessage520.BUNDLE_MSG_IS_EDIT + i)));
        }
        if (this.m_iSelRoute == 1) {
            this.m_lmobileIdList = new ArrayList();
            this.m_strMobilePathList = new ArrayList();
            this.m_lIsLowQtyList = new ArrayList();
            for (i = 0; i < this.m_iPhotoCopiesList.size(); i++) {
                this.m_lmobileIdList.add(Long.valueOf(savedInstanceState.getLong(JumpBundleMessage520.BUNDLE_MSG_SEL_PHOTO_ID_LIST + i)));
                this.m_strMobilePathList.add(savedInstanceState.getString(JumpBundleMessage520.BUNDLE_MSG_SEL_THUMB_PATH + i));
            }
            return;
        }
        this.IP = savedInstanceState.getString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP);
        this.m_iPort = savedInstanceState.getInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT);
        this.m_iPhotoIdList = new ArrayList();
        this.m_iPhotoSIDList = new ArrayList();
        this.m_strFetchImgList = new ArrayList();
        this.m_strThumbPathList = new ArrayList();
        for (i = 0; i < this.m_iPhotoCopiesList.size(); i++) {
            this.m_iPhotoIdList.add(Integer.valueOf(savedInstanceState.getInt(JumpBundleMessage520.BUNDLE_MSG_SEL_PHOTO_ID + i)));
            this.m_iPhotoSIDList.add(Integer.valueOf(savedInstanceState.getInt(JumpBundleMessage520.BUNDLE_MSG_SEL_STORAGE_ID + i)));
            this.m_strThumbPathList.add(savedInstanceState.getString(JumpBundleMessage520.BUNDLE_MSG_SEL_EDIT_PATH + i));
            this.m_strFetchImgList.add(savedInstanceState.getString(JumpBundleMessage520.BUNDLE_MSG_FETCH_IMG + i));
        }
    }

    private void SetPrintOut() {
        switch (this.m_EditMetaUtility.GetServerPaperType()) {
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                this.MAX_WIDTH = 1844;
                this.MAX_HEIGHT = 1240;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                this.MAX_WIDTH = 2140;
                this.MAX_HEIGHT = 1544;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                this.MAX_WIDTH = 2434;
                this.MAX_HEIGHT = 1844;
            default:
        }
    }

    private void SelAllClick() {
        int iPhotoSize;
        if (this.m_iSelRoute == 1) {
            iPhotoSize = this.m_lmobileIdList.size();
        } else {
            iPhotoSize = this.m_iPhotoIdList.size();
        }
        if (this.m_iSelIndexList.size() < iPhotoSize) {
            this.m_iSelIndexList.clear();
            for (int pos = 0; pos < iPhotoSize; pos++) {
                this.m_iSelIndexList.add(Integer.valueOf(pos));
            }
            this.m_SelectAllButton.setButtonDrawable(C0349R.drawable.select_button_clicked);
        } else {
            this.m_iSelIndexList.clear();
            this.m_SelectAllButton.setButtonDrawable(C0349R.drawable.select_button);
        }
        this.m_Adapter.notifyDataSetChanged();
    }

    private void SetView() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.m_iScreenWidth = dm.widthPixels;
        this.m_iItemSize = this.m_iScreenWidth / 4;
        this.m_WifiInfo = new GlobalVariable_WifiAutoConnectInfo(this);
        this.m_CloseListProgressBar = (ProgressBar) findViewById(C0349R.id.m_CloseListprogressBar);
        this.m_CloseListProgressBar.setVisibility(8);
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_StatusButton = (ImageButton) findViewById(C0349R.id.m_StatusButton);
        this.m_EditButton = (ImageButton) findViewById(C0349R.id.m_EditButton);
        this.m_DelButton = (ImageButton) findViewById(C0349R.id.m_DeleteButton);
        this.m_PrintViewButton = (ImageButton) findViewById(C0349R.id.m_PrintButton);
        this.m_AddButton = (ImageButton) findViewById(C0349R.id.m_AddButton);
        this.m_ReduceButton = (ImageButton) findViewById(C0349R.id.m_ReduceButton);
        this.m_SelectAllButton = (RadioButton) findViewById(C0349R.id.m_SelectAllButton);
        this.m_StatusButton.setOnClickListener(this.StatusListen);
        this.m_DelButton.setOnClickListener(this.DelPhotoListen);
        this.m_EditButton.setOnClickListener(this.GoEditListen);
        this.m_PrintViewButton.setOnClickListener(this.GoToPrintViewListen);
        this.m_SelectAllButton.setButtonDrawable(C0349R.drawable.select_button);
        this.m_SelectAllButton.setOnClickListener(new C03253());
        if (this.m_PhotoMode.equals(PHOTO_MODE.MODE_OUTPHOTO)) {
            this.m_BackButton.setVisibility(8);
        } else {
            this.m_BackButton.setOnClickListener(new C03264());
        }
        this.m_GridView = (GridView) findViewById(C0349R.id.m_PhotoGridView);
        this.m_Adapter = new GlideAdater(this, Type.Gallery);
        this.m_AddButton.setOnClickListener(new C03275());
        this.m_ReduceButton.setOnClickListener(new C03286());
        this.m_ErrorDialog = new ShowMSGDialog(this, false);
    }

    private void PrepareToEdit() {
        if (this.m_iSelIndexList.isEmpty()) {
            Toast.makeText(getBaseContext(), getString(C0349R.string.NO_THUMB_SEL), 0).show();
        } else if (GetRouteSelected() == ControllerState.PLAY_COUNT_STATE) {
            setResult(60);
            finish();
        } else if (this.m_iSelRoute == 1) {
            ShowWaitDialog(ThreadMode.DeleteToEdit, true);
            this.m_EditMetaUtility.DeleteFLValueData();
        } else {
            int iPos = ((Integer) this.m_iSelIndexList.get(0)).intValue();
            this.LOG.m385i(this.TAG, "PrepareToEdit m_iSelIndexList size: " + this.m_iSelIndexList.size());
            this.LOG.m385i(this.TAG, "PrepareToEdit m_strFetchImgList size: " + this.m_strFetchImgList.size());
            if (((String) this.m_strFetchImgList.get(iPos)).isEmpty()) {
                FetchImgFromPrinter(iPos);
                return;
            }
            ShowWaitDialog(ThreadMode.DeleteToEdit, true);
            this.m_EditMetaUtility.DeleteFLValueData();
        }
    }

    private void GoToEditPage() {
        ShowWaitDialog(ThreadMode.Non, false);
        Intent intent = new Intent(getBaseContext(), EditGeneralActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
        bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
        intent.putExtras(bundle);
        startActivityForResult(intent, 69);
    }

    private void DropEditInfoList() {
        Iterator it = this.m_iSelIndexList.iterator();
        while (it.hasNext()) {
            int iPos = ((Integer) it.next()).intValue();
            this.m_bIsEditList.set(iPos, null);
            this.m_strXMLpathList.set(iPos, null);
            this.m_bIsVerticalList.set(iPos, null);
            this.m_iBorderPosList.set(iPos, null);
            this.m_iFilterPosList.set(iPos, null);
            if (this.m_iSelRoute == 2 && iPos < this.m_strFetchImgList.size()) {
                this.m_strFetchImgList.set(iPos, null);
            }
        }
        int i = this.m_bIsEditList.size() - 1;
        while (i >= 0) {
            if (this.m_bIsEditList.get(i) == null) {
                this.m_bIsEditList.remove(i);
            }
            if (this.m_strXMLpathList.get(i) == null) {
                this.m_strXMLpathList.remove(i);
            }
            if (this.m_bIsVerticalList.get(i) == null) {
                this.m_bIsVerticalList.remove(i);
            }
            if (this.m_iBorderPosList.get(i) == null) {
                this.m_iBorderPosList.remove(i);
            }
            if (this.m_iFilterPosList.get(i) == null) {
                this.m_iFilterPosList.remove(i);
            }
            if (this.m_iSelRoute == 2 && i < this.m_strFetchImgList.size() && this.m_strFetchImgList.get(i) == null) {
                this.m_strFetchImgList.remove(i);
            }
            i--;
        }
        if (this.m_FCVLList != null) {
            it = this.m_iSelIndexList.iterator();
            while (it.hasNext()) {
                this.m_FCVLList.set(((Integer) it.next()).intValue(), null);
            }
            for (i = this.m_bIsEditList.size() - 1; i >= 0; i--) {
                if (this.m_FCVLList.get(i) == null) {
                    this.m_FCVLList.remove(i);
                }
            }
        }
    }

    private void DelForFilterValue() {
        if (this.m_iOriFValueLength != 0 && this.m_iOriFValueLength != this.m_iPhotoCopiesList.size()) {
            this.m_EditMetaUtility.CleanFilterValuePref();
            for (int i = 0; i < this.m_FCVLList.size(); i++) {
                ArrayList<FilterColorValue> fcvl = (ArrayList) this.m_FCVLList.get(i);
                if (fcvl != null) {
                    this.m_EditMetaUtility.SetFilterValue(i, fcvl);
                }
            }
        }
    }

    private void DropSelPhotoOnMobile() {
        Iterator it = this.m_iSelIndexList.iterator();
        while (it.hasNext()) {
            int iSelPos = ((Integer) it.next()).intValue();
            this.m_strMobilePathList.set(iSelPos, XmlPullParser.NO_NAMESPACE);
            this.m_lmobileIdList.set(iSelPos, Long.valueOf(-1));
            this.m_iPhotoCopiesList.set(iSelPos, Integer.valueOf(-1));
        }
        for (int i = this.m_strMobilePathList.size() - 1; i >= 0; i--) {
            if (((String) this.m_strMobilePathList.get(i)).isEmpty()) {
                this.m_strMobilePathList.remove(i);
            }
            if (((Long) this.m_lmobileIdList.get(i)).longValue() == -1) {
                this.m_lmobileIdList.remove(i);
            }
            if (((Integer) this.m_iPhotoCopiesList.get(i)).intValue() == -1) {
                this.m_iPhotoCopiesList.remove(i);
            }
        }
        this.LOG.m385i(this.TAG, "After drop: path: " + this.m_strMobilePathList);
        this.LOG.m385i(this.TAG, "After drop: id: " + this.m_lmobileIdList);
    }

    private void DropSelPhotoOnSDcard() {
        Iterator it = this.m_iSelIndexList.iterator();
        while (it.hasNext()) {
            int iSelPos = ((Integer) it.next()).intValue();
            this.m_iPhotoIdList.set(iSelPos, Integer.valueOf(-1));
            this.m_iPhotoSIDList.set(iSelPos, Integer.valueOf(-1));
            this.m_iPhotoCopiesList.set(iSelPos, Integer.valueOf(-1));
            this.m_strThumbPathList.set(iSelPos, XmlPullParser.NO_NAMESPACE);
        }
        for (int i = this.m_iPhotoIdList.size() - 1; i >= 0; i--) {
            if (((Integer) this.m_iPhotoIdList.get(i)).intValue() == -1) {
                this.m_iPhotoIdList.remove(i);
            }
            if (((Integer) this.m_iPhotoSIDList.get(i)).intValue() == -1) {
                this.m_iPhotoSIDList.remove(i);
            }
            if (((Integer) this.m_iPhotoCopiesList.get(i)).intValue() == -1) {
                this.m_iPhotoCopiesList.remove(i);
            }
            if (((String) this.m_strThumbPathList.get(i)).isEmpty()) {
                this.m_strThumbPathList.remove(i);
            }
        }
    }

    private void GetMobilePathAndID() {
        if (this.m_PhotoMode.equals(PHOTO_MODE.MODE_OUTPHOTO)) {
            this.m_strMobilePathList = new ArrayList();
            this.m_lmobileIdList = new ArrayList();
            this.m_strMobilePathList.add(this.m_strOnePhotoPath);
            this.m_lmobileIdList.add(Long.valueOf(this.m_strOnePhotoID));
        } else {
            this.m_strMobilePathList = this.m_EditMeta.GetMobilePathList();
            this.m_lmobileIdList = this.m_EditMeta.GetMobileIDList();
        }
        this.LOG.m386v("Get m_strMobilePathList", "=" + String.valueOf(this.m_strMobilePathList));
        if (this.m_iPhotoCopiesList.isEmpty()) {
            for (int i = 0; i < this.m_strMobilePathList.size(); i++) {
                this.m_iPhotoCopiesList.add(Integer.valueOf(1));
            }
        }
        this.LOG.m386v("Get m_iPhotoCopiesList", "=" + String.valueOf(this.m_iPhotoCopiesList));
    }

    private void GetSDcardIDAndPath() {
        int i;
        this.LOG.m385i(this.TAG, "GetSDcardIDAndPath");
        this.m_iPhotoIdList = this.m_EditMeta.GetSDcardIDList();
        this.m_iPhotoSIDList = this.m_EditMeta.GetSDcardSIDList();
        this.m_strThumbPathList = this.m_EditMeta.GetThumbPathList();
        this.m_strFetchImgList = new ArrayList();
        for (i = 0; i < this.m_iPhotoIdList.size(); i++) {
            this.m_strFetchImgList.add(XmlPullParser.NO_NAMESPACE);
        }
        if (this.m_iPhotoCopiesList.isEmpty()) {
            for (i = 0; i < this.m_iPhotoIdList.size(); i++) {
                this.m_iPhotoCopiesList.add(Integer.valueOf(1));
            }
        }
        this.LOG.m385i(this.TAG, "GetSDcardIDAndPath m_iPhotoIdList size: " + this.m_iPhotoIdList.size());
        this.LOG.m385i(this.TAG, "GetSDcardIDAndPath m_strFetchImgList size: " + this.m_strFetchImgList.size());
    }

    public void onBackPressed() {
        if (this.m_bIsEditList.contains(Boolean.valueOf(true))) {
            ShowLeaveDialog();
        } else {
            onBackClicked();
        }
    }

    private void onBackClicked() {
        if (!this.m_bBackStop) {
            SetEditMeta(EditMode.Back);
            if (this.m_iSelRoute == 1) {
                setResult(55);
            } else {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
                bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
                intent.putExtras(bundle);
                setResult(56, intent);
            }
            DeleteFolder(this.m_strFetch);
            DeleteFolder(this.m_strEdit);
            if (this.m_bIsEditList.contains(Boolean.valueOf(true)) || !this.m_strXMLpathList.isEmpty()) {
                CleanEditMetaPref();
            }
            finish();
        }
    }

    private void ShowLeaveDialog() {
        ShowWaitDialog(ThreadMode.Non, false);
        String strTitle = getString(C0349R.string.LEAVE_COLLAGE_TITLE);
        String strMessage = getString(C0349R.string.LEAVE_COLLAGE_MSG);
        this.m_ShowMSGDialog.SetMSGListener(new MSGListener() {
            public void OKClick() {
                PoolActivity.this.onBackClicked();
            }

            public void Close() {
            }

            public void CancelClick() {
            }
        });
        this.m_ShowMSGDialog.ShowMessageDialog(strMessage, strTitle);
    }

    private void DeleteFolder(String strPath) {
        if (getExternalFilesDir(null) != null) {
            FileUtility.DeleteFolder((getExternalFilesDir(null).getAbsolutePath() + File.separator) + strPath);
        }
    }

    private void CleanEditMetaPref() {
        this.m_EditMetaUtility.ClearPoolEditMeta();
        this.m_EditMetaUtility.CleanFilterValuePref();
    }

    void LoadThumbnail() {
        try {
            if (this.m_Adapter != null) {
                this.m_iTotalThumbnail = this.m_iPhotoCopiesList.size();
                if (this.m_iTotalThumbnail == 0) {
                    this.m_SelectAllButton.setButtonDrawable(C0349R.drawable.select_button);
                }
                this.m_Adapter.ClearCache();
                this.m_GridView.setSelection(0);
                this.m_GridView.setAdapter(this.m_Adapter);
                this.m_Adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int GetRouteSelected() {
        return new JumpPreferenceKey(this).GetPathSelectedPref();
    }

    private void FetchImgFromPrinter(int iPos) {
        int iImgID = ((Integer) this.m_iPhotoIdList.get(iPos)).intValue();
        int iImgSID = ((Integer) this.m_iPhotoSIDList.get(iPos)).intValue();
        this.m_ThreadStop = false;
        this.m_EditMetaUtility.FetchPhoto(iPos, iImgID, iImgSID);
    }

    private void NextToPirintView() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setClass(this, PrintViewActivity.class);
        if (this.m_PhotoMode.equals(PHOTO_MODE.MODE_OUTPHOTO)) {
            bundle.putString(JumpPreferenceKey.PHOTO_MODE, PHOTO_MODE.MODE_OUTPHOTO.toString());
            intent.putExtras(bundle);
        } else {
            bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
            bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
            intent.putExtras(bundle);
            ShowWaitDialog(ThreadMode.Non, false);
        }
        startActivityForResult(intent, 19);
    }

    private void ShowErrorMSG(String strErr) {
        ShowWaitDialog(ThreadMode.Non, false);
        this.m_ErrorDialog.ShowSimpleMSGDialog(strErr, getString(C0349R.string.ERROR));
    }

    private void ShowWaitDialog(ThreadMode mode, boolean bShow) {
        if (this.m_ShowMSGDialog == null) {
            return;
        }
        if (bShow) {
            this.m_ThreadStop = false;
            this.m_ShowMSGDialog.ShowWaitingHintDialog(mode, getString(C0349R.string.PLEASE_WAIT));
            return;
        }
        this.m_ShowMSGDialog.StopWaitingDialog();
    }

    private void ShowFetchImageDialog(boolean bShow, String strRatio) {
        if (this.m_ShowMSGDialog != null) {
            this.m_ShowMSGDialog.SetRatio(strRatio);
            String strMSG = getString(C0349R.string.LOADING_IMAGE);
            if (this.m_ThreadStop) {
                bShow = false;
            }
            if (bShow) {
                this.m_ShowMSGDialog.ShowWaitingHintDialog(ThreadMode.FethcImage, strMSG);
            } else {
                this.m_ShowMSGDialog.StopWaitingDialog();
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private void CheckWifi() {
        NetworkInfo mWifi = ((ConnectivityManager) getSystemService("connectivity")).getNetworkInfo(1);
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
            this.m_ShowMSGDialog.CreateConnectWifiHintDialog(this.m_strCurrentSSID, this.m_strLastSSID);
        } else if (this.m_strLastSSID.length() != 0) {
            this.m_ShowMSGDialog.ShowWaitingHintDialog(ThreadMode.AutoWifi, "\n" + getString(C0349R.string.CONNECTING) + "\n");
            this.m_wifiAutoConnect = new AutoWifiConnect(getBaseContext(), this.m_strLastSSID, this.m_strSecurityKey);
            this.m_wifiAutoConnect.execute(new Void[0]);
        } else {
            ShowNoWiFiDialog();
        }
    }

    private void ShowPrinterListDialog() {
        if (this.m_ShowPrinterList == null) {
            this.m_ShowPrinterList = new ShowPrinterList(this);
            this.m_ShowPrinterList.SetPrinterListListener(new PrinterListListener() {
                public void PrinterListFinish(String strPrinterSSID, String IP, int iPort, String strConn) {
                    PoolActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    GetPrinterInfoToList(strPrinterSSID, IP, iPort, strConn);
                }

                public void IsBackStateOnMDNS(boolean bMDNS) {
                }

                public void BackFinish() {
                    PoolActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    PoolActivity.this.m_CloseListProgressBar.setVisibility(8);
                    PoolActivity.this.SetAllViewEnable(true);
                }

                public void BackStart() {
                    PoolActivity.this.getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    PoolActivity.this.m_CloseListProgressBar.setVisibility(0);
                    PoolActivity.this.SetAllViewEnable(false);
                    if (PoolActivity.this.m_ShowPrinterList.IsShowing()) {
                        PoolActivity.this.m_ShowPrinterList.ListClose();
                    }
                }

                private void GetPrinterInfoToList(String strPrinterSSID, String IP, int iPort, String strConn) {
                    Intent intent = new Intent(PoolActivity.this.getBaseContext(), SettingPrinterActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, IP);
                    bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, iPort);
                    bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_SSID, strPrinterSSID);
                    bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_CONN_MODE, strConn);
                    intent.putExtras(bundle);
                    PoolActivity.this.startActivityForResult(intent, 24);
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

    void OpenWifi() {
        startActivityForResult(new Intent("android.settings.WIFI_SETTINGS"), 10);
    }

    public void ShowNoWiFiDialog() {
        Builder alertDialog = new Builder(this);
        alertDialog.setTitle(getString(C0349R.string.UNABLE_TO_CONNECT_TO_PRINTER));
        alertDialog.setMessage(getString(C0349R.string.PLEASE_SELECT_NETWORK));
        alertDialog.setPositiveButton(getString(C0349R.string.OK), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                PoolActivity.this.OpenWifi();
            }
        });
        alertDialog.setNegativeButton(getString(C0349R.string.CANCEL), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void SetAllViewEnable(boolean bEnable) {
        this.m_bBackStop = !bEnable;
        this.m_StatusButton.setEnabled(bEnable);
        this.m_BackButton.setEnabled(bEnable);
        this.m_EditButton.setEnabled(bEnable);
        this.m_DelButton.setEnabled(bEnable);
        this.m_PrintViewButton.setEnabled(bEnable);
        this.m_AddButton.setEnabled(bEnable);
        this.m_ReduceButton.setEnabled(bEnable);
        this.m_SelectAllButton.setEnabled(bEnable);
    }
}
