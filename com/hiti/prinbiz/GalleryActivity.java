package com.hiti.prinbiz;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
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
import com.hiti.trace.GlobalVariable_AlbumSelInfo;
import com.hiti.trace.GlobalVariable_MultiSelContainer;
import com.hiti.trace.GlobalVariable_PrintSettingInfo;
import com.hiti.trace.GlobalVariable_SaveLoadedMeta;
import com.hiti.ui.cacheadapter.CacheAdapter;
import com.hiti.ui.cacheadapter.viewholder.BaseViewHolder;
import com.hiti.ui.cacheadapter.viewholder.BaseViewHolder.Type;
import com.hiti.ui.cacheadapter.viewholder.BorderViewHolder;
import com.hiti.ui.cacheadapter.viewholder.GalleryViewHolder;
import com.hiti.ui.drawview.garnishitem.PathLoader.ThumbnailLoader;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.ui.hitiwebview.HITI_WEB_VIEW_STATUS;
import com.hiti.ui.horizontallistview.HorizontalListView;
import com.hiti.ui.indexview.CellTarget;
import com.hiti.utility.LogManager;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.dialog.ShowMSGDialog;
import com.hiti.utility.grid.ImageAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.telnet.TelnetOption;
import org.xmlpull.v1.XmlPullParser;

public class GalleryActivity extends Activity {
    public static final int DEFAULT_ORG_THUMBNAIL_VIEW_SCALE = 4;
    public static final int HORIZONTAL_CACHE_BITMAP_SIZE = 8;
    public static final int PENDDING_SIZE = 32;
    private LogManager LOG;
    private int MAX_HEIGHT;
    private int MAX_WIDTH;
    String TAG;
    private Bundle mCollageBundle;
    FlowMode mFlowMode;
    NFCInfo mNFCInfo;
    private ImageButton m_BackButton;
    BorderAdapter m_BorderAdapter;
    HorizontalListView m_BorderHorizontalListView;
    int m_BorderViewHorizontalSpacing;
    private HorizontalListView m_BottomBar;
    private GlideAdapter m_ImageAdapter;
    private GridView m_ImageGridView;
    private ProgressBar m_LoadingPhotoProgressBar;
    private ImageButton m_NextButton;
    private PhotoLoader m_PhotoLoader;
    private ImageButton m_SelAllButton;
    private TextView m_SelAllTextView;
    private ShowMSGDialog m_ShowMSGDialog;
    private TextView m_TitleTextView;
    private boolean m_bFromCollageBegin;
    private boolean m_boCheckEdit;
    private Bitmap m_bpDefaultErrorPicture;
    int m_iBorderItemSizeHeight;
    int m_iBorderItemSizeWidth;
    private ArrayList<Integer> m_iCopiesList;
    int m_iFirstPos;
    private int m_iItemSize;
    private int m_iScreenWidth;
    ArrayList<String> m_iSelAllList;
    int m_iSelectBorder;
    private int m_iTotalThumbnail;
    private ArrayList<Long> m_lEditPhotoIDList;
    private ArrayList<Long> m_lPhotoIDList;
    private long m_lSelectPhotoID;
    private ArrayList<Long> m_lSelectPhotoIDList;
    GlobalVariable_MultiSelContainer m_multiSelContainer;
    GlobalVariable_AlbumSelInfo m_prefAlbumInfo;
    GlobalVariable_SaveLoadedMeta m_prefFstPos;
    GlobalVariable_PrintSettingInfo m_prefQPinfo;
    JumpPreferenceKey m_prefRoute;
    private String m_strAlbumID;
    private String m_strAlbumName;
    private ArrayList<String> m_strPhotoPathList;
    private String m_strSelectPhotoPath;
    private ArrayList<String> m_strSelectPhotoPathList;
    boolean m_toAll;

    /* renamed from: com.hiti.prinbiz.GalleryActivity.1 */
    class C02911 implements OnClickListener {
        C02911() {
        }

        public void onClick(View v) {
            GalleryActivity.this.onBackButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.prinbiz.GalleryActivity.2 */
    class C02922 implements OnClickListener {
        C02922() {
        }

        public void onClick(View v) {
            GalleryActivity.this.onNextButtonClicked();
        }
    }

    /* renamed from: com.hiti.prinbiz.GalleryActivity.3 */
    class C02933 implements OnClickListener {
        C02933() {
        }

        public void onClick(View v) {
            GalleryActivity.this.SelAllPhoto();
        }
    }

    /* renamed from: com.hiti.prinbiz.GalleryActivity.5 */
    class C02945 implements DialogInterface.OnClickListener {
        C02945() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    /* renamed from: com.hiti.prinbiz.GalleryActivity.4 */
    class C07624 implements INfcPreview {
        C07624() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            GalleryActivity.this.mNFCInfo = nfcInfo;
        }
    }

    public class BorderAdapter extends CacheAdapter {

        /* renamed from: com.hiti.prinbiz.GalleryActivity.BorderAdapter.1 */
        class C02951 implements OnClickListener {
            C02951() {
            }

            public void onClick(View v) {
                int id = v.getId();
                GalleryActivity.this.m_lSelectPhotoIDList.remove(id);
                GalleryActivity.this.m_strSelectPhotoPathList.remove(id);
                GalleryActivity.this.m_iCopiesList.remove(id);
                GalleryActivity.this.m_BorderAdapter.ClearCache();
                GalleryActivity.this.m_BorderAdapter.notifyDataSetChanged();
                GalleryActivity.this.m_ImageAdapter.notifyDataSetChanged();
                GalleryActivity.this.CheckIfSelAll();
            }
        }

        /* renamed from: com.hiti.prinbiz.GalleryActivity.BorderAdapter.2 */
        class C07632 implements Callback {
            final /* synthetic */ BaseViewHolder val$holder;

            C07632(BaseViewHolder baseViewHolder) {
                this.val$holder = baseViewHolder;
            }

            public void onSuccess() {
            }

            public void onError() {
                if (!GalleryActivity.this.m_lSelectPhotoIDList.isEmpty()) {
                    Bitmap bitmap = Thumbnails.getThumbnail(GalleryActivity.this.getContentResolver(), ((Long) GalleryActivity.this.m_lSelectPhotoIDList.get(this.val$holder.m_iID)).longValue(), 3, null);
                    ImageView imageView = this.val$holder.m_HolderImageView;
                    if (bitmap == null) {
                        bitmap = GalleryActivity.this.m_bpDefaultErrorPicture;
                    }
                    imageView.setImageBitmap(bitmap);
                }
            }
        }

        public BorderAdapter(Context context, AdapterView<?> adapterView, int iCacheSize, int iPenddingSize) {
            super(context, adapterView, iCacheSize, iPenddingSize);
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
                holder.m_DelButton.setOnClickListener(new C02951());
                itemView.setTag(holder);
            } else {
                holder = (BorderViewHolder) itemView.getTag();
                holder.m_DelButton.setId(position);
            }
            holder.m_DefaultImageView.getLayoutParams().height = GalleryActivity.this.m_iBorderItemSizeHeight + (GalleryActivity.this.m_BorderViewHorizontalSpacing * 2);
            holder.m_DefaultImageView.getLayoutParams().width = GalleryActivity.this.m_iBorderItemSizeWidth + (GalleryActivity.this.m_BorderViewHorizontalSpacing * 2);
            holder.m_HolderImageView.getLayoutParams().height = GalleryActivity.this.m_iBorderItemSizeHeight;
            holder.m_HolderImageView.getLayoutParams().width = GalleryActivity.this.m_iBorderItemSizeWidth;
            holder.m_DelButton.getLayoutParams().height = (GalleryActivity.this.m_iItemSize / 5) * 2;
            holder.m_DelButton.getLayoutParams().width = (GalleryActivity.this.m_iItemSize / 5) * 2;
            holder.m_ProgressBar.setVisibility(GalleryActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
            holder.mType = Type.Glide;
            GalleryActivity.this.LOG.m386v(GalleryActivity.this.TAG, "InitItem iD: " + holder.mType);
            return itemView;
        }

        public void GetCachePhoto(BaseViewHolder holder, Bitmap bmp) {
            BorderViewHolder bVH = (BorderViewHolder) holder;
            if (bmp != null) {
                bVH.m_ProgressBar.setVisibility(GalleryActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
            }
            GalleryActivity.this.LOG.m386v(GalleryActivity.this.TAG, "GetCachePhoto ID: " + holder.m_iID + " bmp: " + (bmp == null ? "null" : "exist"));
            bVH.m_HolderImageView.setImageBitmap(bmp);
        }

        public BitmapMonitorResult LoadThumbnail(int iID) {
            Bitmap thumbnails;
            GalleryActivity.this.LOG.m386v(GalleryActivity.this.TAG, "LoadThumbnail iD: " + iID);
            BitmapMonitorResult bmr = new BitmapMonitorResult();
            if (new File((String) GalleryActivity.this.m_strSelectPhotoPathList.get(iID)).exists()) {
                thumbnails = Thumbnails.getThumbnail(GalleryActivity.this.getContentResolver(), ((Long) GalleryActivity.this.m_lSelectPhotoIDList.get(iID)).longValue(), 3, null);
            } else {
                thumbnails = GalleryActivity.this.m_bpDefaultErrorPicture;
            }
            bmr.SetBitmap(thumbnails);
            bmr.SetResult(0);
            return bmr;
        }

        public void PenddingGlide(BaseViewHolder holder) {
            GalleryActivity.this.LOG.m386v(GalleryActivity.this.TAG, "PenddingGlide ID: " + holder.m_iID);
            Picasso.with(GalleryActivity.this).cancelRequest(holder.m_HolderImageView);
            Picasso.with(GalleryActivity.this).load(Uri.parse("file://" + ((String) GalleryActivity.this.m_strSelectPhotoPathList.get(holder.m_iID)))).resize(GalleryActivity.this.m_iBorderItemSizeWidth, GalleryActivity.this.m_iBorderItemSizeHeight).centerCrop().placeholder((int) C0349R.drawable.thumb_album).into(holder.m_HolderImageView, new C07632(holder));
        }

        public void OnClickItem(int iID) {
            if (GalleryActivity.this.m_iSelectBorder == iID || iID == 0) {
                GalleryActivity.this.m_iSelectBorder = 0;
            } else {
                GalleryActivity.this.m_iSelectBorder = iID;
            }
        }

        public void LoadThumbnailSuccess(BaseViewHolder holder, Bitmap bmp) {
            BorderViewHolder bVH = (BorderViewHolder) holder;
            bVH.m_ProgressBar.setVisibility(GalleryActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
            Bitmap recycleBmp = GetCurrentBmp(holder);
            bVH.m_HolderImageView.setImageBitmap(bmp);
            if (recycleBmp != null && recycleBmp != bmp && recycleBmp != GalleryActivity.this.m_bpDefaultErrorPicture && !recycleBmp.isRecycled()) {
                recycleBmp.recycle();
            }
        }

        public void ReflashCheckState(BaseViewHolder SelectHolder) {
            if (GalleryActivity.this.m_iSelectBorder != ((BorderViewHolder) SelectHolder).m_iID) {
            }
        }

        public void ReflashBackground(BaseViewHolder SelectHolder) {
        }

        public int GetListSize() {
            return GalleryActivity.this.m_strSelectPhotoPathList.size();
        }

        public void LoadThumbnailFail(int arg0) {
        }
    }

    class GlideAdapter extends ImageAdapter {

        /* renamed from: com.hiti.prinbiz.GalleryActivity.GlideAdapter.1 */
        class C02961 implements OnClickListener {
            final /* synthetic */ GalleryViewHolder val$holder;

            C02961(GalleryViewHolder galleryViewHolder) {
                this.val$holder = galleryViewHolder;
            }

            public void onClick(View v) {
                GlideAdapter.this.SelectPicture(this.val$holder);
                GalleryActivity.this.CheckIfSelAll();
            }
        }

        /* renamed from: com.hiti.prinbiz.GalleryActivity.GlideAdapter.2 */
        class C02972 implements OnClickListener {
            final /* synthetic */ GalleryViewHolder val$holder;

            C02972(GalleryViewHolder galleryViewHolder) {
                this.val$holder = galleryViewHolder;
            }

            public void onClick(View v) {
                GlideAdapter.this.SelectPicture(this.val$holder);
                GalleryActivity.this.CheckIfSelAll();
            }
        }

        /* renamed from: com.hiti.prinbiz.GalleryActivity.GlideAdapter.3 */
        class C07643 implements Callback {
            final /* synthetic */ BaseViewHolder val$holder;

            C07643(BaseViewHolder baseViewHolder) {
                this.val$holder = baseViewHolder;
            }

            public void onSuccess() {
                GlideAdapter.this.AfterBuildImage(this.val$holder, false);
            }

            public void onError() {
                GlideAdapter.this.AfterBuildImage(this.val$holder, true);
            }
        }

        public GlideAdapter(Context context, ImageAdapter.Type type) {
            super(context, type);
        }

        public View SetHolder(LayoutInflater inflater, BaseViewHolder mHolder) {
            View convertView = inflater.inflate(C0349R.layout.item_gallery, null);
            GalleryViewHolder holder = (GalleryViewHolder) mHolder;
            holder.m_HolderImageView = (ImageView) convertView.findViewById(C0349R.id.thumbImage);
            holder.m_HolderImageView.getLayoutParams().height = GalleryActivity.this.m_iItemSize;
            holder.m_HolderImageView.getLayoutParams().width = GalleryActivity.this.m_iItemSize;
            holder.m_CheckView = (ImageView) convertView.findViewById(C0349R.id.checkImage);
            holder.m_CheckView.getLayoutParams().height = GalleryActivity.this.m_iItemSize;
            holder.m_CheckView.getLayoutParams().width = GalleryActivity.this.m_iItemSize;
            holder.m_QtyView = (ImageView) convertView.findViewById(C0349R.id.qtyImage);
            holder.m_QtyView.getLayoutParams().height = GalleryActivity.this.m_iItemSize;
            holder.m_QtyView.getLayoutParams().width = GalleryActivity.this.m_iItemSize;
            holder.m_CheckView.setOnClickListener(new C02961(holder));
            holder.m_HolderImageView.setOnClickListener(new C02972(holder));
            return convertView;
        }

        public void ClearViewHodler(BaseViewHolder holder) {
            Picasso.with(GalleryActivity.this).cancelRequest(holder.m_HolderImageView);
        }

        public void SetImageView(BaseViewHolder holder) {
            GalleryActivity.this.LOG.m383d(GalleryActivity.this.TAG, "SetImageView " + MobileInfo.GetHmsSStamp());
            Picasso.with(GalleryActivity.this).load(Uri.parse("file://" + ((String) GalleryActivity.this.m_strPhotoPathList.get(holder.m_iID)))).resize(GalleryActivity.this.m_iItemSize, GalleryActivity.this.m_iItemSize).noFade().centerCrop().placeholder((int) C0349R.drawable.thumb_album).into(holder.m_HolderImageView, new C07643(holder));
        }

        public void SetItem(BaseViewHolder holder) {
            int position = holder.m_iID;
            GalleryViewHolder mHolder = (GalleryViewHolder) holder;
            mHolder.m_CheckView.setId(position);
            mHolder.m_QtyView.setId(position);
            mHolder.m_HolderImageView.setId(position);
        }

        void SelectPicture(GalleryViewHolder holder) {
            int index = holder.m_iID;
            long lSelPhotoID = ((Long) GalleryActivity.this.m_lPhotoIDList.get(index)).longValue();
            String strSelPhotoPath = (String) GalleryActivity.this.m_strPhotoPathList.get(index);
            if (GalleryActivity.this.GetRouteSelected() == ControllerState.PLAY_COUNT_STATE) {
                GalleryActivity.this.m_lSelectPhotoIDList.add(Long.valueOf(lSelPhotoID));
                GalleryActivity.this.m_strSelectPhotoPathList.add(strSelPhotoPath);
                GalleryActivity.this.CreateWaitingHintDialog();
                GalleryActivity.this.onCollageImageButtonClicked(strSelPhotoPath);
                holder.m_CheckView.setVisibility(0);
                return;
            }
            if (GalleryActivity.this.m_lSelectPhotoIDList.contains(Long.valueOf(lSelPhotoID))) {
                int id = GalleryActivity.this.m_lSelectPhotoIDList.indexOf(Long.valueOf(lSelPhotoID));
                GalleryActivity.this.m_lSelectPhotoIDList.remove(Long.valueOf(lSelPhotoID));
                GalleryActivity.this.m_strSelectPhotoPathList.remove(strSelPhotoPath);
                GalleryActivity.this.m_iCopiesList.remove(id);
                holder.m_CheckView.setVisibility(GalleryActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
            } else {
                GalleryActivity.this.m_lSelectPhotoIDList.add(Long.valueOf(lSelPhotoID));
                GalleryActivity.this.m_strSelectPhotoPathList.add(strSelPhotoPath);
                GalleryActivity.this.m_iCopiesList.add(Integer.valueOf(1));
                holder.m_CheckView.setVisibility(0);
            }
            GalleryActivity.this.m_BorderAdapter.ClearCache();
            GalleryActivity.this.m_BorderAdapter.notifyDataSetChanged();
        }

        public int GetTotalCout() {
            return GalleryActivity.this.m_iTotalThumbnail;
        }

        public Bitmap GetPhotoThumbnail(int id) {
            return CellTarget.GetThumbnail(GalleryActivity.this, ((Long) GalleryActivity.this.m_lPhotoIDList.get(id)).longValue(), C0349R.drawable.thumb_photo);
        }

        public boolean PhotoQualityCheck(int id) {
            return BitmapMonitor.IsPhotoLowQuality(GalleryActivity.this, Uri.parse("file://" + ((String) GalleryActivity.this.m_strPhotoPathList.get(id))), GalleryActivity.this.MAX_WIDTH, GalleryActivity.this.MAX_HEIGHT);
        }

        public void ViewQualityCheck(BaseViewHolder mHolder, boolean bLow) {
            ((GalleryViewHolder) mHolder).m_QtyView.setVisibility(bLow ? 0 : GalleryActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
        }

        public void ViewSelectCheck(BaseViewHolder mHolder) {
            GalleryViewHolder holder = (GalleryViewHolder) mHolder;
            if (GalleryActivity.this.m_lSelectPhotoIDList.contains(Long.valueOf(((Long) GalleryActivity.this.m_lPhotoIDList.get(holder.m_iID)).longValue()))) {
                holder.m_CheckView.setVisibility(0);
            } else {
                holder.m_CheckView.setVisibility(GalleryActivity.HORIZONTAL_CACHE_BITMAP_SIZE);
            }
        }
    }

    class PhotoLoader extends ThumbnailLoader {
        public PhotoLoader(Context context) {
            super(context, null, null);
        }

        public void LoadFinish() {
            GalleryActivity.this.m_ShowMSGDialog.StopWaitingDialog();
            GalleryActivity.this.LOG.m383d(GalleryActivity.this.TAG, "LoadFinish " + MobileInfo.GetHmsSStamp());
            if (GalleryActivity.this.m_ImageAdapter != null) {
                GalleryActivity.this.m_iTotalThumbnail = GalleryActivity.this.m_strPhotoPathList.size();
                GalleryActivity.this.m_ImageAdapter.ClearCache();
                if (GalleryActivity.this.m_iFirstPos != -1) {
                    GalleryActivity.this.m_ImageGridView.setSelection(GalleryActivity.this.m_iFirstPos);
                } else {
                    GalleryActivity.this.m_ImageGridView.setSelection(0);
                }
                GalleryActivity.this.m_ImageGridView.setAdapter(GalleryActivity.this.m_ImageAdapter);
                GalleryActivity.this.m_ImageAdapter.notifyDataSetChanged();
                GalleryActivity.this.CheckIfSelAll();
            }
        }

        public void BeforeLoadFinish() {
            if (GalleryActivity.this.m_prefFstPos == null) {
                GalleryActivity.this.m_prefFstPos = new GlobalVariable_SaveLoadedMeta(GalleryActivity.this.getBaseContext(), GalleryActivity.this.m_strAlbumID);
            }
            GalleryActivity.this.m_iFirstPos = GalleryActivity.this.m_prefFstPos.GetVisionFstPosition(Integer.parseInt(GalleryActivity.this.m_strAlbumID));
        }
    }

    public GalleryActivity() {
        this.m_bpDefaultErrorPicture = null;
        this.m_strAlbumName = null;
        this.m_strAlbumID = null;
        this.m_strSelectPhotoPath = XmlPullParser.NO_NAMESPACE;
        this.m_lSelectPhotoID = -1;
        this.m_iTotalThumbnail = 0;
        this.m_boCheckEdit = false;
        this.m_PhotoLoader = null;
        this.m_ImageAdapter = null;
        this.m_ImageGridView = null;
        this.m_lPhotoIDList = null;
        this.m_strPhotoPathList = null;
        this.m_lEditPhotoIDList = null;
        this.m_lSelectPhotoIDList = null;
        this.m_strSelectPhotoPathList = null;
        this.m_iCopiesList = null;
        this.m_TitleTextView = null;
        this.m_SelAllTextView = null;
        this.m_BackButton = null;
        this.m_NextButton = null;
        this.m_SelAllButton = null;
        this.m_BottomBar = null;
        this.m_iScreenWidth = 0;
        this.m_iItemSize = 0;
        this.m_LoadingPhotoProgressBar = null;
        this.m_ShowMSGDialog = null;
        this.m_BorderHorizontalListView = null;
        this.m_BorderViewHorizontalSpacing = 0;
        this.m_BorderAdapter = null;
        this.m_iSelectBorder = 0;
        this.m_iBorderItemSizeWidth = 0;
        this.m_iBorderItemSizeHeight = 0;
        this.m_prefFstPos = null;
        this.m_multiSelContainer = null;
        this.m_prefAlbumInfo = null;
        this.m_prefRoute = null;
        this.MAX_WIDTH = 0;
        this.MAX_HEIGHT = 0;
        this.m_prefQPinfo = null;
        this.m_iSelAllList = null;
        this.m_toAll = false;
        this.m_iFirstPos = -1;
        this.m_bFromCollageBegin = false;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = null;
        this.mFlowMode = FlowMode.Normal;
        this.mCollageBundle = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0349R.layout.activity_gallery);
        this.LOG = new LogManager(0);
        this.LOG.m384e(getClass().getSimpleName(), "onCreate");
        this.m_iTotalThumbnail = 0;
        this.m_lPhotoIDList = new ArrayList();
        this.m_strPhotoPathList = new ArrayList();
        this.m_lEditPhotoIDList = new ArrayList();
        this.m_lSelectPhotoIDList = new ArrayList();
        this.m_strSelectPhotoPathList = new ArrayList();
        this.m_iSelAllList = new ArrayList();
        this.m_iCopiesList = new ArrayList();
        if (savedInstanceState != null) {
            this.LOG.m384e("Gallery_onCreate", "savedInstanceState");
        }
        GetBundle(getIntent().getExtras());
        this.m_ShowMSGDialog = new ShowMSGDialog(this, false);
        try {
            InputStream is = getResources().openRawResource(C0349R.drawable.default_error_picture);
            this.m_bpDefaultErrorPicture = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.m_iScreenWidth = dm.widthPixels;
        this.m_iItemSize = this.m_iScreenWidth / DEFAULT_ORG_THUMBNAIL_VIEW_SCALE;
        this.m_TitleTextView = (TextView) findViewById(C0349R.id.m_TitleTextView);
        this.m_TitleTextView.setText(this.m_strAlbumName);
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_BackButton.setOnClickListener(new C02911());
        this.m_NextButton = (ImageButton) findViewById(C0349R.id.m_NextButton);
        this.m_NextButton.setOnClickListener(new C02922());
        if (GetRouteSelected() == ControllerState.PLAY_COUNT_STATE) {
            this.m_NextButton.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
        }
        this.m_LoadingPhotoProgressBar = (ProgressBar) findViewById(C0349R.id.m_ProgressBar);
        this.m_boCheckEdit = true;
        this.m_ImageGridView = (GridView) findViewById(C0349R.id.m_ImageGridView);
        this.m_ImageAdapter = new GlideAdapter(this, ImageAdapter.Type.Gallery);
        this.m_BottomBar = (HorizontalListView) findViewById(C0349R.id.m_BorderHorizontalListView);
        if (GetRouteSelected() != ControllerState.PLAY_COUNT_STATE) {
            CreateContainer();
        } else {
            this.m_BottomBar.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
        }
    }

    void GetBundle(Bundle bundle) {
        this.m_SelAllTextView = (TextView) findViewById(C0349R.id.m_SelectAllTextView);
        this.m_SelAllButton = (ImageButton) findViewById(C0349R.id.m_SelectAllButton);
        this.m_SelAllButton.setOnClickListener(new C02933());
        if (GetRouteSelected() == ControllerState.PLAY_COUNT_STATE) {
            if (bundle != null) {
                this.m_bFromCollageBegin = bundle.getBoolean(JumpBundleMessage.BUNDLE_MSG_COLLAGE_FOLLOW);
            }
            this.m_SelAllButton.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
            this.m_SelAllTextView.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
        }
        if (this.m_bFromCollageBegin) {
            this.m_strAlbumID = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_ALBUM_ID);
            this.m_strAlbumName = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_ALBUM_NAME);
            this.mCollageBundle = bundle;
        } else {
            this.m_prefAlbumInfo = new GlobalVariable_AlbumSelInfo(this, false);
            this.m_prefAlbumInfo.RestoreGlobalVariable();
            this.m_strAlbumID = String.valueOf(this.m_prefAlbumInfo.GetMobileAlbumId());
            this.m_strAlbumName = this.m_prefAlbumInfo.GetAlbumName();
        }
        this.m_prefQPinfo = new GlobalVariable_PrintSettingInfo(getBaseContext(), GetRouteSelected());
        this.m_prefQPinfo.RestoreGlobalVariable();
        int iPaperType = this.m_prefQPinfo.GetServerPaperType();
        if (GetRouteSelected() != ControllerState.PLAY_COUNT_STATE) {
            switch (iPaperType) {
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    this.MAX_WIDTH = 1844;
                    this.MAX_HEIGHT = 1240;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    this.MAX_WIDTH = 2140;
                    this.MAX_HEIGHT = 1544;
                case DEFAULT_ORG_THUMBNAIL_VIEW_SCALE /*4*/:
                    this.MAX_WIDTH = 2434;
                    this.MAX_HEIGHT = 1844;
                default:
            }
        }
    }

    private void SelAllPhoto() {
        int pos;
        this.m_toAll = false;
        for (pos = 0; pos < this.m_lPhotoIDList.size(); pos++) {
            if (!this.m_lSelectPhotoIDList.contains(this.m_lPhotoIDList.get(pos))) {
                if (pos >= this.m_strPhotoPathList.size()) {
                    this.m_toAll = false;
                    break;
                }
                this.m_strSelectPhotoPathList.add(this.m_strPhotoPathList.get(pos));
                this.m_lSelectPhotoIDList.add(this.m_lPhotoIDList.get(pos));
                this.m_iCopiesList.add(Integer.valueOf(1));
                if (!this.m_toAll) {
                    this.m_toAll = true;
                }
            }
        }
        if (this.m_toAll) {
            this.m_SelAllButton.setBackgroundResource(C0349R.drawable.select_button_clicked);
        } else {
            this.m_SelAllButton.setBackgroundResource(C0349R.drawable.select_button);
            for (pos = 0; pos < this.m_lPhotoIDList.size(); pos++) {
                int id = this.m_lSelectPhotoIDList.indexOf(this.m_lPhotoIDList.get(pos));
                this.m_lSelectPhotoIDList.remove(this.m_lPhotoIDList.get(pos));
                this.m_strSelectPhotoPathList.remove(this.m_strPhotoPathList.get(pos));
                this.m_iCopiesList.remove(id);
            }
        }
        this.m_ImageAdapter.notifyDataSetChanged();
        this.m_BorderAdapter.ClearCache();
        this.m_BorderAdapter.notifyDataSetChanged();
    }

    protected void onStart() {
        super.onStart();
        this.LOG.m385i("onStart()", "GalleryActivity");
    }

    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        this.LOG.m385i("onResume()", "GalleryActivity");
        if (this.mFlowMode == FlowMode.BackMainPage) {
            finish();
            return;
        }
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07624());
        if (this.mNFCInfo.mPrintMode == PrintMode.NormalMain) {
            this.LOG.m385i("onResume()", "ReLoadThumbnail" + MobileInfo.GetHmsSStamp());
            this.m_ShowMSGDialog.StopWaitingDialog();
            CreateWaitingHintDialog();
            ReLoadThumbnail();
        }
    }

    protected void onPause() {
        this.LOG.m385i("onPause()", "GalleryActivity");
        if (this.mFlowMode != FlowMode.BackMainPage) {
            if (this.m_prefFstPos == null) {
                this.m_prefFstPos = new GlobalVariable_SaveLoadedMeta(getBaseContext(), this.m_strAlbumID);
            }
            if (this.m_ImageGridView != null) {
                this.m_prefFstPos.SetVisionFirstPosition(this.m_ImageGridView.getFirstVisiblePosition(), Integer.parseInt(this.m_strAlbumID));
                this.m_prefFstPos.SaveGlobalVariable();
            }
            if (this.mNFCInfo.mNfcAdapter != null) {
                NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
            }
        }
        super.onPause();
    }

    public void onStop() {
        this.LOG.m385i("onStop", "GalleryActivity");
        super.onStop();
    }

    public void onDestroy() {
        this.LOG.m385i("onDestroy", "GalleryActivity");
        super.onDestroy();
        this.m_ShowMSGDialog.StopWaitingDialog();
        try {
            this.m_ImageGridView.setAdapter(null);
            if (this.m_ImageAdapter != null) {
                this.m_ImageAdapter.Stop();
                this.m_ImageAdapter = null;
            }
            if (this.m_PhotoLoader != null) {
                this.m_PhotoLoader.Stop();
                this.m_PhotoLoader = null;
            }
            if (this.m_prefFstPos != null) {
                this.m_prefFstPos.SetVisionFirstPosition(0, 0);
                this.m_prefFstPos.SaveGlobalVariable();
            }
        } catch (Exception e) {
            this.LOG.m385i(HITI_WEB_VIEW_STATUS.ERROR, "onBackButtonClicked");
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.LOG.m385i("onActivityResult", "GalleryActivity requestCode: " + requestCode);
        switch (requestCode) {
            case JumpInfo.RESULT_POOL_ACTIVITY /*57*/:
            case JumpInfo.RESULT_POOL_ID_ACTIVITY /*58*/:
            case JumpInfo.RESULT_PRINT_VIEW_ACTIVITY /*62*/:
                if (resultCode == 50) {
                    this.mFlowMode = FlowMode.BackMainPage;
                    setResult(50);
                }
            default:
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        this.LOG.m385i("onSaveInstanceState", "GalleryActivity");
        SaveMultiSelPref();
    }

    public void onBackButtonClicked(View v) {
        Bundle bundle;
        this.LOG.m385i("onBackButtonClicked", "GalleryActivity m_bFromCollageBegin: " + this.m_bFromCollageBegin);
        SaveMultiSelPref();
        if (this.m_bFromCollageBegin) {
            bundle = this.mCollageBundle;
        } else {
            bundle = new Bundle();
            if (this.m_toAll) {
                if (!this.m_iSelAllList.contains(this.m_strAlbumID)) {
                    this.m_iSelAllList.add(this.m_strAlbumID);
                }
            } else if (this.m_iSelAllList.contains(this.m_strAlbumID)) {
                this.m_iSelAllList.remove(this.m_strAlbumID);
            }
            bundle.putStringArrayList(JumpBundleMessage520.BUNDLE_MSG_SEL_ALL, this.m_iSelAllList);
        }
        Exit(31, bundle);
    }

    public void onNextButtonClicked() {
        if (this.m_strSelectPhotoPathList.size() == 0) {
            Toast.makeText(getApplicationContext(), getString(C0349R.string.NO_SEL_PHOTO), 0).show();
            return;
        }
        SaveMultiSelPref();
        switch (GetRouteSelected()) {
            case ControllerState.PLAY_PHOTO /*101*/:
                Exit(62, null);
            case ControllerState.NO_PLAY_ITEM /*102*/:
                Exit(57, null);
            case ControllerState.PLAY_COUNT_STATE /*103*/:
            default:
                Exit(62, null);
        }
    }

    void onCollageImageButtonClicked(String strCollageFile) {
        Bundle bundle = new Bundle();
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_SELECT_PHOTO_PATH, strCollageFile);
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_COLLAGE_DEFAULT_PHOTO_PHOTO_PATH, strCollageFile);
        bundle.putInt(JumpBundleMessage.BUNDLE_MSG_COLLAGE_MODE, 0);
        bundle.putInt(JumpBundleMessage520.BUNDLE_NSG_SEL_ROUTE, 1);
        this.LOG.m383d(this.TAG, "onCollageImageButtonClicked " + this.m_bFromCollageBegin);
        if (this.m_bFromCollageBegin) {
            Exit(29, bundle);
        } else {
            Exit(58, bundle);
        }
    }

    void SaveMultiSelPref() {
        this.m_multiSelContainer = new GlobalVariable_MultiSelContainer(this, 1);
        this.m_multiSelContainer.RestoreGlobalVariable();
        this.m_multiSelContainer.ClearGlobalVariable();
        this.m_multiSelContainer.SetMobilePhotoPathAndId(this.m_strSelectPhotoPathList, this.m_lSelectPhotoIDList);
        if (!this.m_iCopiesList.isEmpty()) {
            this.m_multiSelContainer.SetPhotoCopiesList(this.m_iCopiesList);
        }
        this.m_multiSelContainer.SaveGlobalVariable();
    }

    private int GetRouteSelected() {
        if (this.m_prefRoute == null) {
            this.m_prefRoute = new JumpPreferenceKey(this);
        }
        return this.m_prefRoute.GetPathSelectedPref();
    }

    public void onBackPressed() {
        onBackButtonClicked(null);
    }

    void Exit(int iResult, Bundle bundle) {
        Intent intent;
        switch (iResult) {
            case TelnetOption.REGIME_3270 /*29*/:
                setResult(29, new Intent().putExtras(bundle));
                finish();
            case TelnetOption.WINDOW_SIZE /*31*/:
                intent = new Intent();
                intent.putExtras(bundle);
                setResult(iResult, intent);
                finish();
            case JumpInfo.RESULT_POOL_ACTIVITY /*57*/:
                startActivityForResult(new Intent(this, PoolActivity.class), iResult);
            case JumpInfo.RESULT_POOL_ID_ACTIVITY /*58*/:
                intent = new Intent(this, CollageActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, iResult);
            case JumpInfo.RESULT_PRINT_VIEW_ACTIVITY /*62*/:
                startActivityForResult(new Intent(this, PrintViewActivity.class), iResult);
            default:
        }
    }

    void ReLoadThumbnail() {
        try {
            if (this.m_ImageAdapter != null) {
                if (this.m_PhotoLoader != null) {
                    this.m_PhotoLoader.Stop();
                    this.m_PhotoLoader = null;
                }
                this.m_ImageGridView.setAdapter(null);
                this.m_lPhotoIDList.clear();
                this.m_strPhotoPathList.clear();
                this.m_lEditPhotoIDList.clear();
                this.m_iTotalThumbnail = 0;
                this.m_PhotoLoader = new PhotoLoader(this);
                if (this.m_PhotoLoader != null) {
                    if (this.m_boCheckEdit) {
                        this.m_PhotoLoader.LoadPhoto(this.m_strPhotoPathList, this.m_lPhotoIDList, this.m_lEditPhotoIDList, this.m_strAlbumName, this.m_strAlbumID);
                    } else {
                        this.m_PhotoLoader.LoadPhoto(this.m_strPhotoPathList, this.m_lPhotoIDList, this.m_strAlbumName, this.m_strAlbumID);
                    }
                    this.m_PhotoLoader.execute(new String[]{XmlPullParser.NO_NAMESPACE});
                    this.LOG.m385i(this.TAG, "ReLoadThumbnail PhotoLoader start" + MobileInfo.GetHmsSStamp());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CheckIfSelAll() {
        this.m_toAll = true;
        Iterator it = this.m_lPhotoIDList.iterator();
        while (it.hasNext()) {
            if (!this.m_lSelectPhotoIDList.contains(Long.valueOf(((Long) it.next()).longValue()))) {
                this.m_toAll = false;
                break;
            }
        }
        if (this.m_toAll) {
            this.m_SelAllButton.setBackgroundResource(C0349R.drawable.select_button_clicked);
        } else {
            this.m_SelAllButton.setBackgroundResource(C0349R.drawable.select_button);
        }
    }

    private void CreateWaitingHintDialog() {
        this.m_ShowMSGDialog.ShowWaitingHintDialog(null, "\n" + getString(C0349R.string.PLEASE_WAIT) + "\n");
    }

    void StartPictureEdit() {
        Bundle bundle = new Bundle();
        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_SEL_PHOTO_PATH, this.m_strSelectPhotoPath);
        bundle.putLong(JumpBundleMessage520.BUNDLE_MSG_SEL_PHOTO_ID, this.m_lSelectPhotoID);
        Exit(33, bundle);
    }

    public void ShowAlertDialog(String strMessage, String strTitle, int iResId) {
        Builder alertDialog = new Builder(this);
        alertDialog.setIcon(iResId);
        alertDialog.setTitle(strTitle);
        alertDialog.setMessage(strMessage);
        alertDialog.setPositiveButton(getString(C0349R.string.OK), new C02945());
        alertDialog.show();
    }

    synchronized boolean CreateContainer() {
        this.m_BorderViewHorizontalSpacing = 15;
        this.m_iBorderItemSizeWidth = this.m_iItemSize - (this.m_BorderViewHorizontalSpacing * 2);
        this.m_iBorderItemSizeHeight = this.m_iItemSize - (this.m_BorderViewHorizontalSpacing * 2);
        if (this.m_BorderHorizontalListView == null) {
            this.m_BorderHorizontalListView = (HorizontalListView) findViewById(C0349R.id.m_BorderHorizontalListView);
            this.m_BorderHorizontalListView.setVisibility(0);
        }
        if (this.m_BorderAdapter != null) {
            this.m_BorderHorizontalListView.setAdapter(null);
            this.m_BorderAdapter.StopReflashThumbnail();
            this.m_BorderAdapter.ClearCache();
            this.m_BorderAdapter = null;
        }
        this.m_BorderAdapter = new BorderAdapter(this, this.m_BorderHorizontalListView, HORIZONTAL_CACHE_BITMAP_SIZE, PENDDING_SIZE);
        RestoreMultiSelContainer();
        this.m_BorderHorizontalListView.getLayoutParams().height = this.m_iItemSize;
        this.m_BorderHorizontalListView.setSelection(this.m_BorderAdapter.getCount() - 1);
        this.m_BorderHorizontalListView.setAdapter(this.m_BorderAdapter);
        this.m_BorderAdapter.notifyDataSetChanged();
        return true;
    }

    void RestoreMultiSelContainer() {
        this.LOG.m386v("RestoreMultiSelContainer", "Gallery");
        if (this.m_multiSelContainer == null) {
            this.m_multiSelContainer = new GlobalVariable_MultiSelContainer(this, 1);
            this.m_multiSelContainer.RestoreGlobalVariable();
            if (!this.m_multiSelContainer.IsNoData()) {
                this.m_strSelectPhotoPathList = this.m_multiSelContainer.GetMobilePathList();
                this.m_lSelectPhotoIDList = this.m_multiSelContainer.GetMobileIDList();
            }
            if (!this.m_multiSelContainer.IsNoData()) {
                this.m_iCopiesList = this.m_multiSelContainer.GetPhotoCopiesList();
            }
        }
    }
}
