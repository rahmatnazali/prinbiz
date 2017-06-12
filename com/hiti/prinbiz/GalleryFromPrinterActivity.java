package com.hiti.prinbiz;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.jumpinfo.FlowMode;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.jumpinfo.JumpInfo;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.request.HitiPPR_GetImageData;
import com.hiti.printerprotocol.request.HitiPPR_GetThumbData;
import com.hiti.printerprotocol.request.HitiPPR_GetThumbMeta;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew.Error;
import com.hiti.trace.GlobalVariable_AlbumSelInfo;
import com.hiti.trace.GlobalVariable_MultiSelContainer;
import com.hiti.trace.GlobalVariable_SaveLoadedMeta;
import com.hiti.trace.GlobalVariable_WifiAutoConnectInfo;
import com.hiti.ui.cacheadapter.CacheAdapter;
import com.hiti.ui.cacheadapter.CacheBmp;
import com.hiti.ui.cacheadapter.PenddingGroup;
import com.hiti.ui.cacheadapter.viewholder.BaseViewHolder;
import com.hiti.ui.cacheadapter.viewholder.BorderViewHolder;
import com.hiti.ui.cacheadapter.viewholder.GalleryViewHolder;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.ui.hitiwebview.HITI_WEB_VIEW_STATUS;
import com.hiti.ui.horizontallistview.HorizontalListView;
import com.hiti.utility.ByteConvertUtility;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.Verify.ThreadMode;
import com.hiti.utility.dialog.DialogListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import com.hiti.utility.wifi.WifiAutoConnect;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.telnet.TelnetOption;
import org.xmlpull.v1.XmlPullParser;

public class GalleryFromPrinterActivity extends Activity {
    public static final int DEFAULT_ORG_THUMBNAIL_VIEW_SCALE = 4;
    public static final int HORIZONTAL_CACHE_BITMAP_SIZE = 8;
    public static final int PENDDING_SIZE = 32;
    private static final String PHOTO_LOADED_INDEX = "PHOTO_LOADED_INDEX";
    private static final String PHOTO_LOADED_PATH = "PHOTO_LOADED_PATH";
    private static final int RequestCode_GallerySDcard = 34;
    private String IP;
    LogManager LOG;
    String TAG;
    private DisplayMetrics dm;
    private GetThumbnailHandler handler;
    private FlowMode mFlowMode;
    NFCInfo mNFCInfo;
    AssetManager m_AssetManager;
    private ImageButton m_BackButton;
    BorderAdapter m_BorderAdapter;
    HorizontalListView m_BorderHorizontalListView;
    ImageButton m_BorderImageButton;
    int m_BorderViewHorizontalSpacing;
    private HitiPPR_GetImageData m_GetImageData;
    private HitiPPR_GetThumbData m_HitiPPR_GetThumbData;
    private HitiPPR_GetThumbMeta m_HitiPPR_GetThumbMeta;
    private ImageAdapter m_ImageAdapter;
    private GridView m_ImageGridView;
    ArrayList<String> m_MultiSelPhotoPathList;
    ArrayList<Integer> m_MultiSelStorageIdList;
    private ImageButton m_NextButton;
    private ProgressBar m_ProgressBar;
    private int m_ReLoadCommandCase;
    private ImageButton m_SelAllButton;
    private TextView m_SelAllTextView;
    private ShowMSGDialog m_ShowMSGDialog;
    private TextView m_TitleTextView;
    private GlobalVariable_WifiAutoConnectInfo m_WifiInfo;
    private byte[] m_bAlbumID;
    private byte[] m_bAlbumStorageID;
    private boolean m_bBackIDCollage;
    private boolean m_bFetchImage;
    private boolean m_bFromCollageBegin;
    private ArrayList<Boolean> m_bPhotoSizeList;
    private boolean m_bThreadStop;
    private boolean m_bWifiReBulid;
    private int m_iAlbumId;
    int m_iBorderItemSize;
    int m_iBorderItemSizeHeight;
    int m_iBorderItemSizeWidth;
    private ArrayList<Integer> m_iCopiesList;
    private int m_iFirstPos;
    private int m_iItemSize;
    ArrayList<Integer> m_iLoadedIndexList;
    ArrayList<Integer> m_iMultiSelPhotoIdList;
    private ArrayList<Integer> m_iPhotoIDList;
    private int m_iPort;
    private int m_iScreenWidth;
    private ArrayList<String> m_iSelAllList;
    int m_iSelectBorder;
    private int m_iStorageId;
    private int m_iTotalThumbnail;
    GlobalVariable_MultiSelContainer m_multiSelContainer;
    GlobalVariable_AlbumSelInfo m_prefAlbumInfo;
    GlobalVariable_SaveLoadedMeta m_prefLoadedMeta;
    private JumpPreferenceKey m_prefRoute;
    private String m_strAlbumName;
    ArrayList<String> m_strBorderThumbnailPathList;
    private String m_strCurrentSSID;
    private String m_strLastSSID;
    private ArrayList<String> m_strPhotoNameList;
    private String m_strSecurityKey;
    private String m_strSelectPhotoPath;
    private ArrayList<String> m_strThumbPathList;
    private boolean m_toAll;
    private AlbumAutoWifiConnect m_wifiAutoConnect;

    /* renamed from: com.hiti.prinbiz.GalleryFromPrinterActivity.1 */
    class C02981 implements OnClickListener {
        C02981() {
        }

        public void onClick(View v) {
            GalleryFromPrinterActivity.this.onBackButtonClicked();
        }
    }

    /* renamed from: com.hiti.prinbiz.GalleryFromPrinterActivity.2 */
    class C02992 implements OnClickListener {
        C02992() {
        }

        public void onClick(View v) {
            GalleryFromPrinterActivity.this.onNextButtonClicked();
        }
    }

    /* renamed from: com.hiti.prinbiz.GalleryFromPrinterActivity.3 */
    class C03003 implements OnClickListener {
        C03003() {
        }

        public void onClick(View v) {
            GalleryFromPrinterActivity.this.SelAllPhoto();
        }
    }

    /* renamed from: com.hiti.prinbiz.GalleryFromPrinterActivity.6 */
    class C03016 implements DialogInterface.OnClickListener {
        C03016() {
        }

        public void onClick(DialogInterface dialog, int which) {
            GalleryFromPrinterActivity.this.handler.SetStop(false);
            GalleryFromPrinterActivity.this.OpenWifi();
        }
    }

    /* renamed from: com.hiti.prinbiz.GalleryFromPrinterActivity.7 */
    class C03027 implements DialogInterface.OnClickListener {
        C03027() {
        }

        public void onClick(DialogInterface dialog, int which) {
            GalleryFromPrinterActivity.this.onBackButtonClicked();
        }
    }

    /* renamed from: com.hiti.prinbiz.GalleryFromPrinterActivity.8 */
    class C03038 implements DialogInterface.OnClickListener {
        C03038() {
        }

        public void onClick(DialogInterface dialog, int which) {
            GalleryFromPrinterActivity.this.handler.SetStop(false);
            GalleryFromPrinterActivity.this.onBackButtonClicked();
        }
    }

    /* renamed from: com.hiti.prinbiz.GalleryFromPrinterActivity.9 */
    static /* synthetic */ class C03049 {
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
                $SwitchMap$com$hiti$utility$Verify$ThreadMode[ThreadMode.ThumnailMeta.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$utility$Verify$ThreadMode[ThreadMode.SetSSID.ordinal()] = GalleryFromPrinterActivity.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public class ImageAdapter extends BaseAdapter {
        public static final int CATCH_BITMAP_SIZE = 32;
        public static final int PENDDING_SIZE = 40;
        private LayoutInflater mInflater;
        CacheBmp m_CacheBmp;
        PenddingGroup<GalleryViewHolder> m_PenddingGroup;
        private ReflashImage m_ReflashThread;
        Socket m_Socket;
        Bitmap m_bpDefaultPicture;

        /* renamed from: com.hiti.prinbiz.GalleryFromPrinterActivity.ImageAdapter.1 */
        class C03061 implements OnClickListener {
            final /* synthetic */ GalleryViewHolder val$holder;

            C03061(GalleryViewHolder galleryViewHolder) {
                this.val$holder = galleryViewHolder;
            }

            public void onClick(View v) {
                ImageAdapter.this.SelectPicture(this.val$holder);
            }
        }

        /* renamed from: com.hiti.prinbiz.GalleryFromPrinterActivity.ImageAdapter.2 */
        class C03072 implements OnClickListener {
            final /* synthetic */ GalleryViewHolder val$holder;

            C03072(GalleryViewHolder galleryViewHolder) {
                this.val$holder = galleryViewHolder;
            }

            public void onClick(View v) {
                ImageAdapter.this.SelectPicture(this.val$holder);
            }
        }

        class ReflashImage {
            private GalleryViewHolder m_holder;
            public int m_iID;

            public ReflashImage(GalleryViewHolder holder, Socket socket) {
                this.m_holder = holder;
                this.m_iID = holder.m_iID;
                GalleryFromPrinterActivity.this.m_ProgressBar.setVisibility(0);
                GalleryFromPrinterActivity.this.m_ProgressBar.setIndeterminate(true);
                GetPhoto(socket);
            }

            private void GetPhoto(Socket socket) {
                int id = this.m_iID;
                byte[] photoId = ByteConvertUtility.IntToByte(((Integer) GalleryFromPrinterActivity.this.m_iPhotoIDList.get(id)).intValue());
                GalleryFromPrinterActivity.this.handler.SetStop(false);
                if (GalleryFromPrinterActivity.this.m_iLoadedIndexList.contains(Integer.valueOf(id))) {
                    if (GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbMeta != null && GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbMeta.IsRunning()) {
                        GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbMeta.Stop();
                    }
                    String path = (String) GalleryFromPrinterActivity.this.m_strThumbPathList.get(GalleryFromPrinterActivity.this.m_iLoadedIndexList.indexOf(Integer.valueOf(id)));
                    Message MSG = new Message();
                    MSG.what = FTPReply.FILE_ACTION_PENDING;
                    Bundle bundle = new Bundle();
                    bundle.putInt(GalleryFromPrinterActivity.PHOTO_LOADED_INDEX, id);
                    bundle.putString(GalleryFromPrinterActivity.PHOTO_LOADED_PATH, path);
                    MSG.setData(bundle);
                    GalleryFromPrinterActivity.this.handler.sendMessage(MSG);
                    return;
                }
                GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData = new HitiPPR_GetThumbData(GalleryFromPrinterActivity.this.getBaseContext(), GalleryFromPrinterActivity.this.IP, GalleryFromPrinterActivity.this.m_iPort, GalleryFromPrinterActivity.this.handler);
                GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData.PutIDs(photoId, GalleryFromPrinterActivity.this.m_bAlbumStorageID);
                GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData.PutLoadedIndex(this.m_iID);
                GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData.SetSocket(socket);
                GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData.start();
            }

            private void BuildThumbnail(String thumbnailPath, boolean bLoadedThumb, Socket socket) {
                Bitmap bmpThumbnail = null;
                if (thumbnailPath != null) {
                    try {
                        if (FileUtility.FileExist(thumbnailPath)) {
                            BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(thumbnailPath, false);
                            if (bmr.IsSuccess()) {
                                bmpThumbnail = bmr.GetBitmap();
                            } else {
                                bmpThumbnail = ImageAdapter.this.m_bpDefaultPicture.copy(ImageAdapter.this.m_bpDefaultPicture.getConfig(), true);
                            }
                            SetImage(bmpThumbnail, socket, thumbnailPath);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                bmpThumbnail = ImageAdapter.this.m_bpDefaultPicture.copy(ImageAdapter.this.m_bpDefaultPicture.getConfig(), true);
                SetImage(bmpThumbnail, socket, thumbnailPath);
            }

            private void SetImage(Bitmap bmpThumbnail, Socket socket, String path) {
                if (bmpThumbnail != null && this.m_iID == this.m_holder.m_iID) {
                    Drawable drawable = this.m_holder.m_HolderImageView.getDrawable();
                    if (drawable instanceof BitmapDrawable) {
                        Bitmap RecycleBitmap = ((BitmapDrawable) drawable).getBitmap();
                    }
                    this.m_holder.m_HolderImageView.setImageBitmap(bmpThumbnail);
                    ImageAdapter.this.m_CacheBmp.AddCache(this.m_iID, bmpThumbnail);
                    if (GalleryFromPrinterActivity.this.m_iMultiSelPhotoIdList.contains(Integer.valueOf(((Integer) GalleryFromPrinterActivity.this.m_iPhotoIDList.get(this.m_holder.m_iID)).intValue()))) {
                        this.m_holder.m_CheckView.setVisibility(0);
                    } else {
                        this.m_holder.m_CheckView.setVisibility(GalleryFromPrinterActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
                    }
                    ImageAdapter.this.m_PenddingGroup.RemoveFirstPenddingViewHolder();
                }
                NextReflashPhoto(socket);
            }

            private void NextReflashPhoto(Socket socket) {
                if (ImageAdapter.this.m_PenddingGroup.HavePendding()) {
                    ImageAdapter.this.m_ReflashThread = new ReflashImage((GalleryViewHolder) ImageAdapter.this.m_PenddingGroup.GetFirstPenddingViewHolder(), socket);
                    return;
                }
                GalleryFromPrinterActivity.this.m_ProgressBar.setVisibility(GalleryFromPrinterActivity.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE);
                ImageAdapter.this.m_ReflashThread = null;
                if (GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData != null) {
                    GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData.Stop();
                }
            }
        }

        public ImageAdapter() {
            this.m_bpDefaultPicture = null;
            this.m_ReflashThread = null;
            this.m_PenddingGroup = null;
            this.m_CacheBmp = null;
            this.m_Socket = null;
            this.mInflater = (LayoutInflater) GalleryFromPrinterActivity.this.getSystemService("layout_inflater");
            InputStream is = GalleryFromPrinterActivity.this.getResources().openRawResource(C0349R.drawable.thumb_photo);
            this.m_bpDefaultPicture = BitmapFactory.decodeStream(is);
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.m_CacheBmp = new CacheBmp(CATCH_BITMAP_SIZE);
            this.m_PenddingGroup = new PenddingGroup(PENDDING_SIZE);
        }

        public int getCount() {
            return GalleryFromPrinterActivity.this.m_iTotalThumbnail;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public void SetSocket(Socket socket) {
            this.m_Socket = socket;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            GalleryViewHolder holder;
            if (convertView == null) {
                holder = new GalleryViewHolder();
                convertView = this.mInflater.inflate(C0349R.layout.item_gallery, null);
                holder.m_HolderImageView = (ImageView) convertView.findViewById(C0349R.id.thumbImage);
                holder.m_HolderImageView.getLayoutParams().height = GalleryFromPrinterActivity.this.m_iItemSize;
                holder.m_HolderImageView.getLayoutParams().width = GalleryFromPrinterActivity.this.m_iItemSize;
                holder.m_CheckView = (ImageView) convertView.findViewById(C0349R.id.checkImage);
                holder.m_CheckView.getLayoutParams().height = GalleryFromPrinterActivity.this.m_iItemSize;
                holder.m_CheckView.getLayoutParams().width = GalleryFromPrinterActivity.this.m_iItemSize;
                holder.m_EditView = (ImageView) convertView.findViewById(C0349R.id.editImage);
                holder.m_EditView.getLayoutParams().height = GalleryFromPrinterActivity.this.m_iItemSize;
                holder.m_EditView.getLayoutParams().width = GalleryFromPrinterActivity.this.m_iItemSize;
                convertView.setTag(holder);
            } else {
                holder = (GalleryViewHolder) convertView.getTag();
            }
            holder.m_EditView.setVisibility(GalleryFromPrinterActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
            holder.m_CheckView.setId(position);
            holder.m_HolderImageView.setId(position);
            holder.m_CheckView.setOnClickListener(new C03061(holder));
            holder.m_HolderImageView.setOnClickListener(new C03072(holder));
            holder.m_iID = position;
            try {
                if (this.m_CacheBmp.IsCache(holder.m_iID)) {
                    holder.m_HolderImageView.setImageBitmap(this.m_CacheBmp.GetCache(holder.m_iID));
                    if (GalleryFromPrinterActivity.this.m_iMultiSelPhotoIdList.contains(Integer.valueOf(((Integer) GalleryFromPrinterActivity.this.m_iPhotoIDList.get(holder.m_iID)).intValue()))) {
                        holder.m_CheckView.setVisibility(0);
                    } else {
                        holder.m_CheckView.setVisibility(GalleryFromPrinterActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
                    }
                } else {
                    holder.m_HolderImageView.setImageResource(C0349R.drawable.thumb_photo);
                    holder.m_CheckView.setVisibility(GalleryFromPrinterActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
                    PenddingReflash(holder);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            GalleryFromPrinterActivity.this.m_iFirstPos = GalleryFromPrinterActivity.this.m_ImageGridView.getFirstVisiblePosition();
            return convertView;
        }

        void PenddingReflash(GalleryViewHolder holder) {
            this.m_PenddingGroup.AddPendding(holder);
            if (this.m_ReflashThread == null || GalleryFromPrinterActivity.this.m_bWifiReBulid) {
                GalleryFromPrinterActivity.this.m_bWifiReBulid = false;
                this.m_ReflashThread = new ReflashImage((GalleryViewHolder) this.m_PenddingGroup.GetFirstPenddingViewHolder(), null);
            }
        }

        void SelectPicture(GalleryViewHolder holder) {
            int pos = holder.m_iID;
            int iSelID = ((Integer) GalleryFromPrinterActivity.this.m_iPhotoIDList.get(pos)).intValue();
            int storageId = ByteConvertUtility.ByteToInt(GalleryFromPrinterActivity.this.m_bAlbumStorageID, 0, GalleryFromPrinterActivity.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE);
            if (!GalleryFromPrinterActivity.this.m_bFetchImage) {
                int id;
                if (pos < GalleryFromPrinterActivity.this.m_bPhotoSizeList.size()) {
                    id = GalleryFromPrinterActivity.this.m_iLoadedIndexList.indexOf(Integer.valueOf(pos));
                    if (id != -1 && id < GalleryFromPrinterActivity.this.m_bPhotoSizeList.size() && ((Boolean) GalleryFromPrinterActivity.this.m_bPhotoSizeList.get(id)).booleanValue()) {
                        Toast.makeText(GalleryFromPrinterActivity.this.getBaseContext(), GalleryFromPrinterActivity.this.getBaseContext().getString(C0349R.string.SIZE_OVER_25), 0).show();
                        return;
                    }
                }
                if (!GalleryFromPrinterActivity.this.m_iLoadedIndexList.contains(Integer.valueOf(pos))) {
                    Toast.makeText(GalleryFromPrinterActivity.this.getBaseContext(), GalleryFromPrinterActivity.this.getBaseContext().getString(C0349R.string.THUMB_LOADING), 0).show();
                } else if (GalleryFromPrinterActivity.this.GetRouteSelected() == ControllerState.PLAY_COUNT_STATE) {
                    holder.m_CheckView.setVisibility(0);
                    GalleryFromPrinterActivity.this.m_bFetchImage = true;
                    GalleryFromPrinterActivity.this.FetchImageFromPrinter(iSelID, storageId);
                } else {
                    if (GalleryFromPrinterActivity.this.m_iMultiSelPhotoIdList.contains(Integer.valueOf(iSelID))) {
                        id = GalleryFromPrinterActivity.this.m_iMultiSelPhotoIdList.indexOf(Integer.valueOf(iSelID));
                        GalleryFromPrinterActivity.this.m_MultiSelPhotoPathList.remove(id);
                        GalleryFromPrinterActivity.this.m_iMultiSelPhotoIdList.remove(id);
                        GalleryFromPrinterActivity.this.m_MultiSelStorageIdList.remove(id);
                        GalleryFromPrinterActivity.this.m_iCopiesList.remove(id);
                        holder.m_CheckView.setVisibility(GalleryFromPrinterActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
                    } else {
                        GalleryFromPrinterActivity.this.m_MultiSelPhotoPathList.add((String) GalleryFromPrinterActivity.this.m_strThumbPathList.get(GalleryFromPrinterActivity.this.m_iLoadedIndexList.indexOf(Integer.valueOf(pos))));
                        GalleryFromPrinterActivity.this.m_iMultiSelPhotoIdList.add(Integer.valueOf(iSelID));
                        GalleryFromPrinterActivity.this.m_MultiSelStorageIdList.add(Integer.valueOf(storageId));
                        GalleryFromPrinterActivity.this.m_iCopiesList.add(Integer.valueOf(1));
                        holder.m_CheckView.setVisibility(0);
                    }
                    GalleryFromPrinterActivity.this.m_BorderAdapter.ClearCache();
                    GalleryFromPrinterActivity.this.m_BorderAdapter.notifyDataSetChanged();
                    GalleryFromPrinterActivity.this.CheckIfSelAll();
                }
            }
        }

        void ClearCache() {
            this.m_CacheBmp.ClearCache();
            this.m_PenddingGroup.ClearPendding();
        }
    }

    /* renamed from: com.hiti.prinbiz.GalleryFromPrinterActivity.4 */
    class C07654 extends DialogListener {
        C07654() {
        }

        public void SetNowConnSSID(String strNowSSID) {
            if (!GalleryFromPrinterActivity.this.m_bThreadStop) {
                GalleryFromPrinterActivity.this.ReLoadThumbnail();
            }
        }

        public void SetLastConnSSID(String strLastSSID) {
            if (!GalleryFromPrinterActivity.this.m_bThreadStop) {
                GalleryFromPrinterActivity.this.CreateWaitingHintDialog(ThreadMode.AutoWifi, GalleryFromPrinterActivity.this.getString(C0349R.string.CONN_SEARCHING));
                GalleryFromPrinterActivity.this.m_wifiAutoConnect = new AlbumAutoWifiConnect(GalleryFromPrinterActivity.this.getBaseContext(), GalleryFromPrinterActivity.this.m_strLastSSID, GalleryFromPrinterActivity.this.m_strSecurityKey);
                GalleryFromPrinterActivity.this.m_wifiAutoConnect.execute(new Void[0]);
            }
        }

        public void CancelConnetion(ThreadMode mode) {
            GalleryFromPrinterActivity.this.m_ShowMSGDialog.StopWaitingDialog();
            GalleryFromPrinterActivity.this.m_bThreadStop = true;
            switch (C03049.$SwitchMap$com$hiti$utility$Verify$ThreadMode[mode.ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    if (GalleryFromPrinterActivity.this.m_wifiAutoConnect != null) {
                        GalleryFromPrinterActivity.this.m_wifiAutoConnect.ConnectionStop();
                    }
                    GalleryFromPrinterActivity.this.onBackButtonClicked();
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    if (GalleryFromPrinterActivity.this.m_GetImageData != null) {
                        GalleryFromPrinterActivity.this.m_GetImageData.Stop();
                    }
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    if (GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbMeta != null) {
                        GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbMeta.Stop();
                    }
                    GalleryFromPrinterActivity.this.onBackButtonClicked();
                case GalleryFromPrinterActivity.DEFAULT_ORG_THUMBNAIL_VIEW_SCALE /*4*/:
                    GalleryFromPrinterActivity.this.onBackButtonClicked();
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

    /* renamed from: com.hiti.prinbiz.GalleryFromPrinterActivity.5 */
    class C07665 implements INfcPreview {
        C07665() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            GalleryFromPrinterActivity.this.mNFCInfo = nfcInfo;
        }
    }

    class AlbumAutoWifiConnect extends WifiAutoConnect {
        public AlbumAutoWifiConnect(Context context, String strSSID, String strPassword) {
            super(context, strSSID, strPassword);
            GalleryFromPrinterActivity.this.m_bThreadStop = false;
        }

        public void ConnectionSuccess() {
            ConnectionStop();
            GalleryFromPrinterActivity.this.handler.SetStop(false);
            if (!GalleryFromPrinterActivity.this.m_bThreadStop) {
                switch (GalleryFromPrinterActivity.this.m_ReLoadCommandCase) {
                    case RequestState.REQUEST_GET_THUMBNAILS_META_ERROR /*349*/:
                        GalleryFromPrinterActivity.this.m_bWifiReBulid = true;
                        GalleryFromPrinterActivity.this.ReLoadThumbnail();
                    case RequestState.REQUEST_GET_THUMBNAILS_DATA_ERROR /*351*/:
                        GalleryFromPrinterActivity.this.m_bWifiReBulid = true;
                        Message msg = Message.obtain();
                        msg.what = RequestState.REQUEST_GET_THUMBNAILS_META;
                        GalleryFromPrinterActivity.this.handler.sendMessage(msg);
                    default:
                        GalleryFromPrinterActivity.this.ReLoadThumbnail();
                }
            }
        }

        public void ConnectionFail() {
            ConnectionStop();
            if (!GalleryFromPrinterActivity.this.m_bThreadStop) {
                GalleryFromPrinterActivity.this.ShowNoWiFiDialog();
            }
        }

        public void ConnectionStop() {
            GalleryFromPrinterActivity.this.m_ShowMSGDialog.StopWaitingDialog();
            SetStop(true);
        }
    }

    public class BorderAdapter extends CacheAdapter {
        Bitmap bmpDefaultThumbnail;

        /* renamed from: com.hiti.prinbiz.GalleryFromPrinterActivity.BorderAdapter.1 */
        class C03051 implements OnClickListener {
            C03051() {
            }

            public void onClick(View v) {
                int id = v.getId();
                GalleryFromPrinterActivity.this.m_MultiSelPhotoPathList.remove(id);
                GalleryFromPrinterActivity.this.m_iMultiSelPhotoIdList.remove(id);
                GalleryFromPrinterActivity.this.m_MultiSelStorageIdList.remove(id);
                GalleryFromPrinterActivity.this.m_iCopiesList.remove(id);
                GalleryFromPrinterActivity.this.m_BorderAdapter.ClearCache();
                GalleryFromPrinterActivity.this.m_BorderAdapter.notifyDataSetChanged();
                GalleryFromPrinterActivity.this.m_ImageAdapter.notifyDataSetChanged();
                GalleryFromPrinterActivity.this.CheckIfSelAll();
            }
        }

        public BorderAdapter(Context context, AdapterView<?> adapterView, int iCacheSize, int iPenddingSize) {
            super(context, adapterView, iCacheSize, iPenddingSize);
            this.bmpDefaultThumbnail = null;
            try {
                InputStream is = GalleryFromPrinterActivity.this.getResources().openRawResource(C0349R.drawable.thumb_photo);
                this.bmpDefaultThumbnail = BitmapFactory.decodeStream(is);
                is.close();
                SetDefaultThumbnail(this.bmpDefaultThumbnail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public View InitItem(int position, View itemView, ViewGroup parent) {
            BorderViewHolder holder = new BorderViewHolder();
            if (itemView == null) {
                itemView = this.m_Inflater.inflate(C0349R.layout.item_border_sel, null);
                holder.m_DefaultImageView = (ImageView) itemView.findViewById(C0349R.id.DefaultBorderImageView);
                holder.m_HolderImageView = (ImageView) itemView.findViewById(C0349R.id.BorderImageView);
                holder.m_ProgressBar = (ProgressBar) itemView.findViewById(C0349R.id.BorderProgressBar);
                holder.m_DelButton = (ImageButton) itemView.findViewById(C0349R.id.m_DelImageView);
                holder.m_DelButton.setVisibility(0);
                holder.m_DelButton.setId(position);
                holder.m_DelButton.setOnClickListener(new C03051());
                itemView.setTag(holder);
            } else {
                holder = (BorderViewHolder) itemView.getTag();
                holder.m_DelButton.setId(position);
            }
            holder.m_DefaultImageView.getLayoutParams().height = GalleryFromPrinterActivity.this.m_iBorderItemSizeHeight + (GalleryFromPrinterActivity.this.m_BorderViewHorizontalSpacing * 2);
            holder.m_DefaultImageView.getLayoutParams().width = GalleryFromPrinterActivity.this.m_iBorderItemSizeWidth + (GalleryFromPrinterActivity.this.m_BorderViewHorizontalSpacing * 2);
            holder.m_HolderImageView.getLayoutParams().height = GalleryFromPrinterActivity.this.m_iBorderItemSizeHeight;
            holder.m_HolderImageView.getLayoutParams().width = GalleryFromPrinterActivity.this.m_iBorderItemSizeWidth;
            holder.m_DelButton.getLayoutParams().height = (GalleryFromPrinterActivity.this.m_iBorderItemSizeHeight / 5) * 2;
            holder.m_DelButton.getLayoutParams().width = (GalleryFromPrinterActivity.this.m_iBorderItemSizeWidth / 5) * 2;
            holder.m_ProgressBar.setVisibility(0);
            return itemView;
        }

        public void GetCachePhoto(BaseViewHolder holder, Bitmap bmp) {
            BorderViewHolder bVH = (BorderViewHolder) holder;
            if (bmp != null) {
                bVH.m_ProgressBar.setVisibility(GalleryFromPrinterActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
            }
            bVH.m_HolderImageView.setImageBitmap(bmp);
        }

        public BitmapMonitorResult LoadThumbnail(int iID) {
            BitmapMonitorResult bmr = new BitmapMonitorResult();
            try {
                String strThumbnailPath = (String) GalleryFromPrinterActivity.this.m_MultiSelPhotoPathList.get(iID);
                if (strThumbnailPath == "xxx") {
                    bmr.SetResult(0);
                    bmr.SetBitmap(this.bmpDefaultThumbnail.copy(this.bmpDefaultThumbnail.getConfig(), false));
                    return bmr;
                }
                if (FileUtility.IsFromSDCard(GalleryFromPrinterActivity.this.getBaseContext(), strThumbnailPath)) {
                    bmr = BitmapMonitor.CreateBitmap((String) GalleryFromPrinterActivity.this.m_MultiSelPhotoPathList.get(iID), false);
                } else {
                    bmr = BitmapMonitor.CreateBitmap(GalleryFromPrinterActivity.this.m_AssetManager.open((String) GalleryFromPrinterActivity.this.m_MultiSelPhotoPathList.get(iID)), false);
                }
                if (!bmr.IsSuccess()) {
                    bmr.SetResult(0);
                    bmr.SetBitmap(this.bmpDefaultThumbnail.copy(this.bmpDefaultThumbnail.getConfig(), false));
                }
                return bmr;
            } catch (IOException e) {
                bmr.SetResult(97);
                e.printStackTrace();
            }
        }

        public void OnClickItem(int iID) {
            if (GalleryFromPrinterActivity.this.m_iSelectBorder == iID || iID == 0) {
                GalleryFromPrinterActivity.this.m_iSelectBorder = 0;
            } else {
                GalleryFromPrinterActivity.this.m_iSelectBorder = iID;
            }
        }

        public void LoadThumbnailSuccess(BaseViewHolder holder, Bitmap bmp) {
            BorderViewHolder bVH = (BorderViewHolder) holder;
            bVH.m_ProgressBar.setVisibility(GalleryFromPrinterActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
            Bitmap recycleBmp = GetCurrentBmp(holder);
            bVH.m_HolderImageView.setImageBitmap(bmp);
            if (recycleBmp != null && recycleBmp != bmp && recycleBmp != this.bmpDefaultThumbnail && !recycleBmp.isRecycled()) {
                recycleBmp.recycle();
            }
        }

        public void ReflashCheckState(BaseViewHolder SelectHolder) {
        }

        public int GetListSize() {
            return GalleryFromPrinterActivity.this.m_MultiSelPhotoPathList.size();
        }

        public void LoadThumbnailFail(int arg0) {
        }

        public void ReflashBackground(BaseViewHolder arg0) {
        }
    }

    class GetThumbnailHandler extends MSGHandler {
        GetThumbnailHandler() {
        }

        public void handleMessage(Message msg) {
            if (!IsStop()) {
                String strMsg;
                switch (msg.what) {
                    case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                        GalleryFromPrinterActivity.this.m_ShowMSGDialog.StopWaitingDialog();
                        GalleryFromPrinterActivity.this.handler.SetStop(true);
                        if (!GalleryFromPrinterActivity.this.m_bThreadStop) {
                            if (GalleryFromPrinterActivity.this.m_bFetchImage) {
                                GalleryFromPrinterActivity.this.m_bFetchImage = false;
                            }
                            GalleryFromPrinterActivity.this.ShowAlertDialog(msg.getData().getString(MSGHandler.MSG), HITI_WEB_VIEW_STATUS.ERROR);
                        }
                    case RequestState.REQUEST_GET_THUMBNAILS_META /*348*/:
                        GalleryFromPrinterActivity.this.LOG.m383d(GalleryFromPrinterActivity.this.TAG, "REQUEST_GET_THUMBNAILS_META threadStop:" + GalleryFromPrinterActivity.this.m_bThreadStop);
                        GalleryFromPrinterActivity.this.m_ShowMSGDialog.StopWaitingDialog();
                        if (!GalleryFromPrinterActivity.this.m_bThreadStop && GalleryFromPrinterActivity.this.m_ImageAdapter != null) {
                            Socket socket;
                            if (GalleryFromPrinterActivity.this.m_iPhotoIDList.isEmpty() && GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbMeta != null) {
                                GalleryFromPrinterActivity.this.m_strPhotoNameList.clear();
                                GalleryFromPrinterActivity.this.m_iPhotoIDList.clear();
                                if (GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbMeta != null) {
                                    GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbMeta.GetThumbIDList(GalleryFromPrinterActivity.this.m_iPhotoIDList);
                                }
                                GalleryFromPrinterActivity.this.ReverseMetaList(GalleryFromPrinterActivity.this.m_iPhotoIDList);
                            }
                            GalleryFromPrinterActivity.this.m_iTotalThumbnail = GalleryFromPrinterActivity.this.m_iPhotoIDList.size();
                            GalleryFromPrinterActivity.this.m_ImageAdapter.ClearCache();
                            if (GalleryFromPrinterActivity.this.m_iFirstPos != -1) {
                                GalleryFromPrinterActivity.this.m_ImageGridView.setSelection(GalleryFromPrinterActivity.this.m_iFirstPos);
                            } else {
                                GalleryFromPrinterActivity.this.m_ImageGridView.setSelection(0);
                            }
                            if (GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbMeta == null) {
                                socket = null;
                            } else {
                                socket = GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbMeta.GetSocket();
                            }
                            GalleryFromPrinterActivity.this.m_ImageAdapter.SetSocket(socket);
                            GalleryFromPrinterActivity.this.m_ImageGridView.setAdapter(GalleryFromPrinterActivity.this.m_ImageAdapter);
                            GalleryFromPrinterActivity.this.CheckIfSelAll();
                            GalleryFromPrinterActivity.this.m_ImageAdapter.notifyDataSetChanged();
                        }
                    case RequestState.REQUEST_GET_THUMBNAILS_META_ERROR /*349*/:
                        if (!GalleryFromPrinterActivity.this.m_bThreadStop) {
                            strMsg = msg.getData().getString(MSGHandler.MSG);
                            if (strMsg == GalleryFromPrinterActivity.this.getString(C0349R.string.ERROR_PRINTER_CONNECT)) {
                                GalleryFromPrinterActivity.this.m_ReLoadCommandCase = RequestState.REQUEST_GET_THUMBNAILS_META_ERROR;
                                GalleryFromPrinterActivity.this.CheckWifi();
                                return;
                            }
                            GalleryFromPrinterActivity.this.ShowAlertDialog(strMsg, HITI_WEB_VIEW_STATUS.ERROR);
                        }
                    case FTPReply.FILE_ACTION_PENDING /*350*/:
                        GalleryFromPrinterActivity.this.m_ProgressBar.setVisibility(GalleryFromPrinterActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
                        GalleryFromPrinterActivity.this.handler.SetStop(true);
                        String strThumbPath = null;
                        Bundle data = msg.getData();
                        int index = -1;
                        String path = null;
                        boolean bLoadedThumb = false;
                        Socket m_Socket = null;
                        if (!GalleryFromPrinterActivity.this.m_bThreadStop) {
                            if (!(data == null || data.isEmpty())) {
                                index = data.getInt(GalleryFromPrinterActivity.PHOTO_LOADED_INDEX);
                                path = data.getString(GalleryFromPrinterActivity.PHOTO_LOADED_PATH);
                                data.clear();
                            }
                            if (index != -1 && GalleryFromPrinterActivity.this.m_iLoadedIndexList.contains(Integer.valueOf(index))) {
                                bLoadedThumb = true;
                                strThumbPath = path;
                            } else if (GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData != null) {
                                strThumbPath = GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData.GetThumbPath();
                                int iLoadedIndex = GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData.GetLoadedIndex();
                                m_Socket = GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData.GetSocket();
                                GalleryFromPrinterActivity.this.m_iLoadedIndexList.add(Integer.valueOf(iLoadedIndex));
                                GalleryFromPrinterActivity.this.m_strThumbPathList.add(strThumbPath);
                                GalleryFromPrinterActivity.this.m_bPhotoSizeList.add(Boolean.valueOf(false));
                            }
                            GalleryFromPrinterActivity.this.m_ImageAdapter.m_ReflashThread.BuildThumbnail(strThumbPath, bLoadedThumb, m_Socket);
                        }
                    case RequestState.REQUEST_GET_THUMBNAILS_DATA_ERROR /*351*/:
                    case RequestState.REQUEST_GET_THUMBNAILS_DATA_NO_THUMBNAIL /*397*/:
                    case NNTPReply.SERVICE_DISCONTINUED /*400*/:
                        strMsg = msg.getData().getString(MSGHandler.MSG);
                        GalleryFromPrinterActivity.this.handler.SetStop(true);
                        if (!GalleryFromPrinterActivity.this.m_bThreadStop && !GalleryFromPrinterActivity.this.m_bFetchImage) {
                            if (!strMsg.equals(Error.NOTHUMBNAIL.toString()) && !strMsg.equals(Error.SIZE_OVER_25.toString())) {
                                GalleryFromPrinterActivity.this.ShowAlertDialog(strMsg, HITI_WEB_VIEW_STATUS.ERROR);
                            } else if (GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData != null && GalleryFromPrinterActivity.this.m_ImageAdapter.m_ReflashThread != null) {
                                GalleryFromPrinterActivity.this.m_iLoadedIndexList.add(Integer.valueOf(GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData.GetLoadedIndex()));
                                String thumbPath = GalleryFromPrinterActivity.this.m_HitiPPR_GetThumbData.GetThumbPath();
                                if (thumbPath == null) {
                                    thumbPath = "xxx";
                                }
                                GalleryFromPrinterActivity.this.m_strThumbPathList.add(thumbPath);
                                if (strMsg.equals(Error.SIZE_OVER_25.toString())) {
                                    GalleryFromPrinterActivity.this.m_bPhotoSizeList.add(Boolean.valueOf(true));
                                } else {
                                    GalleryFromPrinterActivity.this.m_bPhotoSizeList.add(Boolean.valueOf(false));
                                }
                                GalleryFromPrinterActivity.this.m_ImageAdapter.m_ReflashThread.BuildThumbnail(thumbPath, false, null);
                            }
                        }
                    case RequestState.REQUEST_GET_IMAGE_DATA /*370*/:
                        GalleryFromPrinterActivity.this.m_ShowMSGDialog.StopWaitingDialog();
                        GalleryFromPrinterActivity.this.m_bFetchImage = false;
                        GalleryFromPrinterActivity.this.handler.SetStop(false);
                        if (!GalleryFromPrinterActivity.this.m_bThreadStop && GalleryFromPrinterActivity.this.m_GetImageData != null) {
                            String strImgPath = GalleryFromPrinterActivity.this.m_GetImageData.GetImagePath();
                            GalleryFromPrinterActivity.this.m_MultiSelPhotoPathList.add(strImgPath);
                            GalleryFromPrinterActivity.this.onCollageImageButtonClicked(strImgPath);
                        }
                    case RequestState.REQUEST_GET_IMAGE_DATA_ERROR /*371*/:
                        GalleryFromPrinterActivity.this.m_bFetchImage = false;
                        strMsg = msg.getData().getString(MSGHandler.MSG);
                        GalleryFromPrinterActivity.this.m_ShowMSGDialog.StopWaitingDialog();
                        if (!GalleryFromPrinterActivity.this.m_bThreadStop) {
                            GalleryFromPrinterActivity.this.ShowAlertDialog(strMsg, HITI_WEB_VIEW_STATUS.ERROR);
                        }
                    case RequestState.REQUEST_RATIO_OF_FETCH_IMG /*377*/:
                        if (!GalleryFromPrinterActivity.this.m_bThreadStop) {
                            GalleryFromPrinterActivity.this.m_ShowMSGDialog.SetRatio(msg.getData().getString(MSGHandler.MSG));
                            GalleryFromPrinterActivity.this.CreateWaitingHintDialog(ThreadMode.FethcImage, GalleryFromPrinterActivity.this.getString(C0349R.string.LOADING_IMAGE));
                        }
                    default:
                }
            }
        }
    }

    public GalleryFromPrinterActivity() {
        this.m_strAlbumName = null;
        this.m_bAlbumID = null;
        this.m_bAlbumStorageID = null;
        this.m_strSelectPhotoPath = XmlPullParser.NO_NAMESPACE;
        this.m_iTotalThumbnail = 0;
        this.m_ImageAdapter = null;
        this.m_ImageGridView = null;
        this.m_iPhotoIDList = null;
        this.m_iCopiesList = null;
        this.m_strPhotoNameList = null;
        this.m_strThumbPathList = null;
        this.m_bPhotoSizeList = null;
        this.m_TitleTextView = null;
        this.m_SelAllTextView = null;
        this.m_ProgressBar = null;
        this.m_BackButton = null;
        this.m_NextButton = null;
        this.m_SelAllButton = null;
        this.m_iScreenWidth = 0;
        this.m_iItemSize = 0;
        this.m_MultiSelPhotoPathList = null;
        this.m_iMultiSelPhotoIdList = null;
        this.m_MultiSelStorageIdList = null;
        this.m_iLoadedIndexList = null;
        this.handler = null;
        this.m_HitiPPR_GetThumbMeta = null;
        this.m_HitiPPR_GetThumbData = null;
        this.m_strLastSSID = null;
        this.m_strCurrentSSID = null;
        this.m_strSecurityKey = null;
        this.m_wifiAutoConnect = null;
        this.m_ReLoadCommandCase = -1;
        this.m_WifiInfo = null;
        this.m_BorderImageButton = null;
        this.m_BorderHorizontalListView = null;
        this.m_BorderViewHorizontalSpacing = 0;
        this.m_BorderAdapter = null;
        this.m_strBorderThumbnailPathList = null;
        this.m_iSelectBorder = 0;
        this.m_iBorderItemSizeWidth = 0;
        this.m_iBorderItemSizeHeight = 0;
        this.m_iBorderItemSize = 0;
        this.m_AssetManager = null;
        this.m_multiSelContainer = null;
        this.m_prefAlbumInfo = null;
        this.m_prefLoadedMeta = null;
        this.m_ShowMSGDialog = null;
        this.dm = null;
        this.m_iAlbumId = -1;
        this.m_iStorageId = -1;
        this.m_prefRoute = null;
        this.m_iSelAllList = null;
        this.m_toAll = false;
        this.m_bWifiReBulid = false;
        this.m_iFirstPos = -1;
        this.m_bBackIDCollage = false;
        this.m_bFetchImage = false;
        this.m_bFromCollageBegin = false;
        this.m_GetImageData = null;
        this.IP = XmlPullParser.NO_NAMESPACE;
        this.m_iPort = 0;
        this.m_bThreadStop = false;
        this.mFlowMode = FlowMode.Normal;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = getClass().getSimpleName();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0349R.layout.activity_gallery);
        this.LOG = new LogManager(0);
        this.m_iTotalThumbnail = 0;
        this.m_strPhotoNameList = new ArrayList();
        this.m_iPhotoIDList = new ArrayList();
        this.m_strThumbPathList = new ArrayList();
        this.m_MultiSelPhotoPathList = new ArrayList();
        this.m_MultiSelStorageIdList = new ArrayList();
        this.m_iMultiSelPhotoIdList = new ArrayList();
        this.m_iLoadedIndexList = new ArrayList();
        this.m_bPhotoSizeList = new ArrayList();
        this.m_iSelAllList = new ArrayList();
        this.m_iCopiesList = new ArrayList();
        if (savedInstanceState != null) {
            this.m_iTotalThumbnail = savedInstanceState.getInt(JumpBundleMessage520.BUNDLE_MSG_TOTAL_THUMBNAILS);
            this.m_strLastSSID = savedInstanceState.getString(JumpBundleMessage.BUNDLE_MSG_SSID);
        }
        this.dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(this.dm);
        this.m_iScreenWidth = this.dm.widthPixels;
        this.m_iItemSize = this.m_iScreenWidth / DEFAULT_ORG_THUMBNAIL_VIEW_SCALE;
        SetMSGDialog();
        this.m_ProgressBar = (ProgressBar) findViewById(C0349R.id.m_ProgressBar);
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_NextButton = (ImageButton) findViewById(C0349R.id.m_NextButton);
        this.m_SelAllTextView = (TextView) findViewById(C0349R.id.m_SelectAllTextView);
        this.m_SelAllButton = (ImageButton) findViewById(C0349R.id.m_SelectAllButton);
        this.m_BorderHorizontalListView = (HorizontalListView) findViewById(C0349R.id.m_BorderHorizontalListView);
        IsIDPath();
        GetBundle(getIntent().getExtras());
        this.m_TitleTextView = (TextView) findViewById(C0349R.id.m_TitleTextView);
        this.m_TitleTextView.setText(this.m_strAlbumName);
        this.m_BackButton.setOnClickListener(new C02981());
        this.m_NextButton.setOnClickListener(new C02992());
        this.m_SelAllButton.setOnClickListener(new C03003());
        this.m_ImageGridView = (GridView) findViewById(C0349R.id.m_ImageGridView);
        this.m_ImageAdapter = new ImageAdapter();
        this.handler = new GetThumbnailHandler();
    }

    private void SetMSGDialog() {
        this.m_ShowMSGDialog = new ShowMSGDialog(this, true);
        this.m_ShowMSGDialog.SetDialogListener(new C07654());
    }

    void GetBundle(Bundle bundle) {
        if (this.m_bFromCollageBegin && bundle != null) {
            this.m_iAlbumId = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_ALBUM_ID);
            this.m_iStorageId = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_ALBUM_STORAGE_ID);
            this.m_strAlbumName = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_ALBUM_NAME);
            this.m_BorderHorizontalListView.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
            this.m_NextButton.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
        }
        if (this.m_strAlbumName != null) {
            this.m_prefAlbumInfo = new GlobalVariable_AlbumSelInfo(getBaseContext(), true);
            this.m_prefAlbumInfo.RestoreGlobalVariable();
            this.m_prefAlbumInfo.ClearGlobalVariable();
            this.m_prefAlbumInfo.SetAlbumRoute(2);
            this.m_prefAlbumInfo.SaveGlobalVariable();
            this.m_prefAlbumInfo.SetAlbumId(this.m_iAlbumId);
            this.m_prefAlbumInfo.SetAlbumStorageId(this.m_iStorageId);
            this.m_prefAlbumInfo.SetAlbumName(this.m_strAlbumName);
            this.m_prefAlbumInfo.SaveGlobalVariable();
            this.m_prefRoute.SetPreference("bFromCollageBegin", false);
        } else {
            this.m_prefAlbumInfo = new GlobalVariable_AlbumSelInfo(this, this.m_bBackIDCollage);
            this.m_prefAlbumInfo.RestoreGlobalVariable();
            this.m_iAlbumId = this.m_prefAlbumInfo.GetAlbumId();
            this.m_iStorageId = this.m_prefAlbumInfo.GetAlbumStorageId();
            this.m_strAlbumName = this.m_prefAlbumInfo.GetAlbumName();
        }
        if (bundle != null) {
            this.IP = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP);
            this.m_iPort = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT);
        } else {
            this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
            this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        }
        this.m_bAlbumID = ByteConvertUtility.IntToByte(this.m_iAlbumId);
        this.m_bAlbumStorageID = ByteConvertUtility.IntToByte(this.m_iStorageId);
        if (GetRouteSelected() != ControllerState.PLAY_COUNT_STATE && bundle != null) {
            this.m_iSelAllList = bundle.getStringArrayList(JumpBundleMessage520.BUNDLE_MSG_SEL_ALL);
            if (this.m_iSelAllList == null) {
                this.m_iSelAllList = new ArrayList();
            } else if (this.m_iSelAllList.contains(String.valueOf(this.m_iAlbumId))) {
                this.m_toAll = true;
            } else {
                this.m_toAll = false;
            }
            if (this.m_toAll) {
                this.m_SelAllButton.setBackgroundResource(C0349R.drawable.select_button_clicked);
            } else {
                this.m_SelAllButton.setBackgroundResource(C0349R.drawable.select_button);
            }
        }
    }

    private void IsIDPath() {
        if (GetRouteSelected() != ControllerState.PLAY_COUNT_STATE) {
            CreateContainer();
            return;
        }
        this.m_BorderHorizontalListView.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
        this.m_SelAllButton.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
        this.m_SelAllTextView.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
        this.m_NextButton.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
        this.m_bBackIDCollage = this.m_prefRoute.GetStatePreference("BackID_Collage");
        this.m_bFromCollageBegin = this.m_prefRoute.GetStatePreference("bFromCollageBegin");
    }

    private void SelAllPhoto() {
        int iLen = this.m_iLoadedIndexList.size();
        int pos;
        if (this.m_toAll) {
            this.m_SelAllButton.setBackgroundResource(C0349R.drawable.select_button);
            for (pos = 0; pos < iLen; pos++) {
                int id = this.m_iMultiSelPhotoIdList.indexOf(this.m_iPhotoIDList.get(pos));
                if (id == -1) {
                    break;
                }
                this.m_iMultiSelPhotoIdList.remove(this.m_iPhotoIDList.get(pos));
                this.m_MultiSelPhotoPathList.remove(this.m_strThumbPathList.get(pos));
                this.m_MultiSelStorageIdList.remove(id);
                this.m_iCopiesList.remove(id);
            }
            this.m_toAll = false;
        } else {
            this.m_SelAllButton.setBackgroundResource(C0349R.drawable.select_button_clicked);
            for (pos = 0; pos < iLen; pos++) {
                if (!this.m_iMultiSelPhotoIdList.contains(this.m_iPhotoIDList.get(pos))) {
                    this.m_iMultiSelPhotoIdList.add(this.m_iPhotoIDList.get(pos));
                    this.m_MultiSelPhotoPathList.add(this.m_strThumbPathList.get(pos));
                    this.m_MultiSelStorageIdList.add(Integer.valueOf(ByteConvertUtility.ByteToInt(this.m_bAlbumStorageID, 0, DEFAULT_ORG_THUMBNAIL_VIEW_SCALE)));
                    this.m_iCopiesList.add(Integer.valueOf(1));
                }
            }
            this.m_toAll = true;
        }
        this.m_ImageAdapter.ClearCache();
        this.m_ImageAdapter.notifyDataSetChanged();
        this.m_BorderAdapter.ClearCache();
        this.m_BorderAdapter.notifyDataSetChanged();
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
        if (this.mFlowMode == FlowMode.BackMainPage) {
            finish();
            return;
        }
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07665());
        if (this.m_ImageAdapter != null) {
            this.m_ShowMSGDialog.StopWaitingDialog();
            this.m_iTotalThumbnail = 0;
            this.handler.SetStop(false);
            if (this.mNFCInfo.mPrintMode == PrintMode.NormalMain) {
                CheckWifi();
            }
        }
    }

    protected void onPause() {
        if (this.mFlowMode != FlowMode.BackMainPage) {
            SaveLoadedMeta();
            if (this.mNFCInfo.mNfcAdapter != null) {
                NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
            }
        }
        super.onPause();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        KillThread();
        this.m_ShowMSGDialog.StopWaitingDialog();
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.LOG.m383d(this.TAG, "onActivityResult: " + requestCode);
        if (this.m_ImageAdapter == null) {
            this.m_ImageAdapter = new ImageAdapter();
        }
        switch (requestCode) {
            case ConnectionResult.SERVICE_UPDATING /*18*/:
            case JumpInfo.RESULT_POOL_ACTIVITY /*57*/:
            case JumpInfo.RESULT_PRINT_VIEW_ACTIVITY /*62*/:
                if (resultCode == 50) {
                    this.mFlowMode = FlowMode.BackMainPage;
                    setResult(resultCode);
                }
            default:
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.remove(JumpBundleMessage520.BUNDLE_MSG_TOTAL_THUMBNAILS);
        SaveMultiSelPref();
        outState.putInt(JumpBundleMessage520.BUNDLE_MSG_TOTAL_THUMBNAILS, this.m_iTotalThumbnail);
        if (this.m_strLastSSID != null) {
            outState.putString(JumpBundleMessage.BUNDLE_MSG_SSID, this.m_strLastSSID);
        }
    }

    private boolean RestoreLoadedMeta() {
        this.LOG.m383d(this.TAG, "RestoreLoadedMeta");
        if (this.m_prefLoadedMeta == null) {
            this.m_prefLoadedMeta = new GlobalVariable_SaveLoadedMeta(this, String.valueOf(this.m_iAlbumId));
        }
        this.m_prefLoadedMeta.RestoreGlobalVariable();
        if (!this.m_prefLoadedMeta.IsNoData()) {
            this.m_prefLoadedMeta.GetIdAndLoadedPathList(this.m_iPhotoIDList, this.m_strThumbPathList);
            this.m_prefLoadedMeta.GetLoadedIndexAndSizeList(this.m_iLoadedIndexList, this.m_bPhotoSizeList);
            int iFstPos = this.m_prefLoadedMeta.GetVisionFstPosition(this.m_iAlbumId);
            if (iFstPos != -1) {
                this.m_iFirstPos = iFstPos;
            }
        }
        if (this.m_iPhotoIDList.isEmpty()) {
            this.LOG.m383d(this.TAG, "RestoreLoadedMeta ID empty: true");
            return false;
        }
        this.LOG.m383d(this.TAG, "RestoreLoadedMeta ID empty: false");
        return true;
    }

    void SaveMultiSelPref() {
        this.m_multiSelContainer = new GlobalVariable_MultiSelContainer(this, 2);
        this.m_multiSelContainer.RestoreGlobalVariable();
        this.m_multiSelContainer.ClearGlobalVariable();
        this.m_multiSelContainer.SetPhotoIdAndStorageIdList(this.m_iMultiSelPhotoIdList, this.m_MultiSelStorageIdList);
        this.m_multiSelContainer.SetThumbPathAndNameList(this.m_MultiSelPhotoPathList, null);
        if (!this.m_iCopiesList.isEmpty()) {
            this.m_multiSelContainer.SetPhotoCopiesList(this.m_iCopiesList);
        }
        this.m_multiSelContainer.SaveGlobalVariable();
    }

    void SaveLoadedMeta() {
        if (this.m_prefLoadedMeta == null) {
            this.m_prefLoadedMeta = new GlobalVariable_SaveLoadedMeta(this, String.valueOf(this.m_iAlbumId));
        }
        if (!this.m_strThumbPathList.isEmpty()) {
            this.m_prefLoadedMeta.ClearGlobalVariable();
            if (this.m_ImageGridView != null) {
                this.m_prefLoadedMeta.SetVisionFirstPosition(this.m_ImageGridView.getFirstVisiblePosition(), this.m_iAlbumId);
                this.m_prefLoadedMeta.SaveGlobalVariable();
            }
            this.m_prefLoadedMeta.SetAllIdAndLoadedPathList(this.m_iPhotoIDList, this.m_strThumbPathList);
            this.m_prefLoadedMeta.SetLoadedIndexAndSizeList(this.m_iLoadedIndexList, this.m_bPhotoSizeList);
            this.m_prefLoadedMeta.SaveGlobalVariable();
        }
    }

    public void onBackButtonClicked() {
        Bundle bundle = new Bundle();
        if (GetRouteSelected() != ControllerState.PLAY_COUNT_STATE) {
            SaveMultiSelPref();
            String strAlbumID = String.valueOf(this.m_iAlbumId);
            if (!this.m_toAll || strAlbumID == null) {
                if (this.m_iSelAllList.contains(strAlbumID)) {
                    this.m_iSelAllList.remove(strAlbumID);
                }
            } else if (!this.m_iSelAllList.contains(strAlbumID)) {
                this.m_iSelAllList.add(strAlbumID);
            }
            bundle.putStringArrayList(JumpBundleMessage520.BUNDLE_MSG_SEL_ALL, this.m_iSelAllList);
            bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
            bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
        }
        Exit(54, new Intent().putExtras(bundle));
    }

    public void onNextButtonClicked() {
        if (this.m_iMultiSelPhotoIdList.size() == 0) {
            Toast.makeText(getApplicationContext(), getString(C0349R.string.NO_SEL_PHOTO), 0).show();
            return;
        }
        SaveMultiSelPref();
        switch (GetRouteSelected()) {
            case ControllerState.PLAY_PHOTO /*101*/:
                Exit(62, new Intent(this, PrintViewActivity.class));
            case ControllerState.NO_PLAY_ITEM /*102*/:
                Bundle bundle = new Bundle();
                bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
                bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
                Exit(57, new Intent(this, PoolActivity.class).putExtras(bundle));
            case ControllerState.PLAY_COUNT_STATE /*103*/:
            default:
        }
    }

    void onCollageImageButtonClicked(String strCollageFile) {
        Intent intent = new Intent(this, CollageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_SELECT_PHOTO_PATH, strCollageFile);
        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_ALBUM_NAME, this.m_strAlbumName);
        bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_ALBUM_ID, this.m_iAlbumId);
        bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_ALBUM_STORAGE_ID, this.m_iStorageId);
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_COLLAGE_DEFAULT_PHOTO_PHOTO_PATH, strCollageFile);
        bundle.putInt(JumpBundleMessage.BUNDLE_MSG_COLLAGE_MODE, 0);
        bundle.putInt(JumpBundleMessage520.BUNDLE_NSG_SEL_ROUTE, 2);
        intent.putExtras(bundle);
        if (this.m_bBackIDCollage) {
            Exit(29, intent);
            return;
        }
        intent.putExtra("TYPE", 58);
        Exit(18, intent);
    }

    private void FetchImageFromPrinter(int id, int sid) {
        byte[] byID = ByteConvertUtility.IntToByte(id);
        byte[] bySID = ByteConvertUtility.IntToByte(sid);
        if (this.m_HitiPPR_GetThumbData != null) {
            this.m_HitiPPR_GetThumbData.Stop();
        }
        this.handler.SetStop(false);
        this.m_bThreadStop = false;
        this.m_GetImageData = new HitiPPR_GetImageData(this, this.IP, this.m_iPort, this.handler);
        this.m_GetImageData.PutIDs(byID, bySID);
        this.m_GetImageData.start();
    }

    private int GetRouteSelected() {
        if (this.m_prefRoute == null) {
            this.m_prefRoute = new JumpPreferenceKey(this);
        }
        return this.m_prefRoute.GetPathSelectedPref();
    }

    public void onBackPressed() {
        onBackButtonClicked();
    }

    void Exit(int iRequest, Intent intent) {
        this.m_ShowMSGDialog.StopWaitingDialog();
        try {
            KillThread();
            this.m_ImageGridView.setAdapter(null);
            this.m_ImageAdapter = null;
            if (this.m_wifiAutoConnect != null) {
                this.m_wifiAutoConnect.SetStop(true);
                this.m_wifiAutoConnect = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (iRequest) {
            case TelnetOption.REGIME_3270 /*29*/:
                new JumpPreferenceKey(this).SetPreference("BackID_Collage", false);
                break;
            case JumpInfo.RESULT_ALBUM_FROM_PRINTER_ACTIVITY /*54*/:
                break;
            default:
                if (intent != null) {
                    startActivityForResult(intent, iRequest);
                    return;
                }
                return;
        }
        setResult(iRequest, intent);
        finish();
    }

    private void KillThread() {
        if (!(this.m_ImageAdapter == null || this.m_ImageAdapter.m_ReflashThread == null)) {
            this.m_ImageAdapter.m_ReflashThread = null;
        }
        if (this.m_HitiPPR_GetThumbData != null) {
            this.m_HitiPPR_GetThumbData.Stop();
            this.handler.removeCallbacks(this.m_HitiPPR_GetThumbData);
            this.m_HitiPPR_GetThumbData = null;
        }
        if (this.m_HitiPPR_GetThumbMeta != null) {
            this.m_HitiPPR_GetThumbMeta.Stop();
            this.handler.removeCallbacks(this.m_HitiPPR_GetThumbMeta);
            this.m_HitiPPR_GetThumbMeta = null;
        }
        if (this.m_GetImageData != null) {
            this.m_GetImageData.Stop();
            this.handler.removeCallbacks(this.m_GetImageData);
            this.m_GetImageData = null;
        }
        this.handler.SetStop(true);
    }

    private void ReLoadThumbnail() {
        this.LOG.m383d(this.TAG, "ReLoadThumbnail m_bFetchImage " + this.m_bFetchImage);
        try {
            this.handler.SetStop(false);
            if (!RestoreLoadedMeta()) {
                CreateWaitingHintDialog(ThreadMode.ThumnailMeta, getString(C0349R.string.PLEASE_WAIT));
                this.m_HitiPPR_GetThumbMeta = new HitiPPR_GetThumbMeta(this, this.IP, this.m_iPort, this.handler);
                this.m_HitiPPR_GetThumbMeta.PutIDs(this.m_bAlbumID, this.m_bAlbumStorageID);
                this.m_HitiPPR_GetThumbMeta.start();
            } else if (!this.m_bFetchImage) {
                this.handler.sendEmptyMessage(RequestState.REQUEST_GET_THUMBNAILS_META);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ReverseMetaList(ArrayList<Integer> iPhotoIdList) {
        ArrayList<Integer> tmpList = new ArrayList();
        for (int i = iPhotoIdList.size() - 1; i >= 0; i--) {
            tmpList.add(iPhotoIdList.get(i));
        }
        iPhotoIdList.clear();
        Iterator it = tmpList.iterator();
        while (it.hasNext()) {
            iPhotoIdList.add(Integer.valueOf(((Integer) it.next()).intValue()));
        }
    }

    private void CheckIfSelAll() {
        Iterator it = this.m_iPhotoIDList.iterator();
        while (it.hasNext()) {
            if (!this.m_iMultiSelPhotoIdList.contains(Integer.valueOf(((Integer) it.next()).intValue()))) {
                this.m_SelAllButton.setBackgroundResource(C0349R.drawable.select_button);
                this.m_toAll = false;
                return;
            }
        }
        this.m_toAll = true;
        this.m_SelAllButton.setBackgroundResource(C0349R.drawable.select_button_clicked);
    }

    void StartPictureEdit() {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_SEL_PHOTO_PATH, this.m_strSelectPhotoPath);
        returnIntent.putExtras(bundle);
        Exit(33, returnIntent);
    }

    private void CheckWifi() {
        NetworkInfo mWifi = ((ConnectivityManager) getSystemService("connectivity")).getNetworkInfo(1);
        this.m_WifiInfo = new GlobalVariable_WifiAutoConnectInfo(this);
        this.m_WifiInfo.RestoreGlobalVariable();
        this.m_strLastSSID = this.m_strLastSSID == null ? this.m_WifiInfo.GetSSID() : this.m_strLastSSID;
        this.m_strSecurityKey = this.m_WifiInfo.GetPassword();
        this.m_strCurrentSSID = GetNowSSID();
        this.LOG.m383d(this.TAG, "CheckWifi LastSSID: " + this.m_strLastSSID);
        if (mWifi.isConnected()) {
            if (this.m_strCurrentSSID.contains(this.m_strLastSSID)) {
                ReLoadThumbnail();
                return;
            }
            this.handler.SetStop(true);
            KillThread();
            this.m_bThreadStop = false;
            this.m_ShowMSGDialog.CreateConnectWifiHintDialog(this.m_strCurrentSSID, this.m_strLastSSID);
        } else if (this.m_strLastSSID.length() != 0) {
            this.m_ShowMSGDialog.ShowWaitingHintDialog(ThreadMode.AutoWifi, getString(C0349R.string.CONN_SEARCHING));
            this.m_wifiAutoConnect = new AlbumAutoWifiConnect(getBaseContext(), this.m_strLastSSID, this.m_strSecurityKey);
            this.m_wifiAutoConnect.execute(new Void[0]);
        } else {
            ShowNoWiFiDialog();
        }
    }

    private String GetNowSSID() {
        WifiInfo wifiInfo = ((WifiManager) getSystemService("wifi")).getConnectionInfo();
        if (wifiInfo.getSSID() == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        return wifiInfo.getSSID();
    }

    void OpenWifi() {
        startActivityForResult(new Intent("android.settings.WIFI_SETTINGS"), 10);
    }

    public void ShowNoWiFiDialog() {
        Builder alertDialog = new Builder(this);
        alertDialog.setTitle(getString(C0349R.string.PLEASE_SELECT_NETWORK));
        alertDialog.setMessage(getString(C0349R.string.UNABLE_TO_CONNECT_TO_PRINTER));
        alertDialog.setNegativeButton(getString(C0349R.string.OK), new C03016());
        alertDialog.setPositiveButton(getString(C0349R.string.CANCEL), new C03027());
        alertDialog.show();
    }

    public void ShowAlertDialog(String strMessage, String strTitle) {
        this.m_bFetchImage = false;
        this.m_ShowMSGDialog.StopWaitingDialog();
        KillThread();
        Builder alertDialog = new Builder(this);
        alertDialog.setTitle(strTitle);
        alertDialog.setMessage(strMessage);
        alertDialog.setPositiveButton(getString(C0349R.string.OK), new C03038());
        alertDialog.show();
    }

    synchronized boolean CreateContainer() {
        Log.i("Gallery", "CreateContainer");
        this.m_BorderViewHorizontalSpacing = 15;
        this.m_iBorderItemSizeWidth = this.m_iItemSize - (this.m_BorderViewHorizontalSpacing * 2);
        this.m_iBorderItemSizeHeight = this.m_iItemSize - (this.m_BorderViewHorizontalSpacing * 2);
        this.m_BorderHorizontalListView.setVisibility(0);
        if (this.m_BorderAdapter != null) {
            this.m_BorderHorizontalListView.setAdapter(null);
            this.m_BorderAdapter.StopReflashThumbnail();
            this.m_BorderAdapter.ClearCache();
            this.m_BorderAdapter = null;
        }
        RestoreMultiSelContainer();
        this.m_BorderAdapter = new BorderAdapter(this, this.m_BorderHorizontalListView, HORIZONTAL_CACHE_BITMAP_SIZE, PENDDING_SIZE);
        this.m_BorderHorizontalListView.getLayoutParams().height = this.m_iItemSize;
        this.m_BorderHorizontalListView.setSelection(this.m_BorderAdapter.getCount() - 1);
        this.m_BorderHorizontalListView.setAdapter(this.m_BorderAdapter);
        this.m_BorderAdapter.notifyDataSetChanged();
        return true;
    }

    void RestoreMultiSelContainer() {
        if (this.m_multiSelContainer == null) {
            this.m_multiSelContainer = new GlobalVariable_MultiSelContainer(getBaseContext(), 2);
            this.m_multiSelContainer.RestoreGlobalVariable();
            if (!this.m_multiSelContainer.IsNoData()) {
                this.m_iMultiSelPhotoIdList = this.m_multiSelContainer.GetSDcardIDList();
                this.m_MultiSelStorageIdList = this.m_multiSelContainer.GetSDcardSIDList();
                this.m_MultiSelPhotoPathList = this.m_multiSelContainer.GetThumbPathList();
            }
            if (!this.m_multiSelContainer.IsNoData()) {
                this.m_iCopiesList = this.m_multiSelContainer.GetPhotoCopiesList();
            }
        }
    }

    private void CreateWaitingHintDialog(ThreadMode mMode, String strMSG) {
        this.m_bThreadStop = false;
        this.m_ShowMSGDialog.ShowWaitingHintDialog(mMode, strMSG);
    }
}
