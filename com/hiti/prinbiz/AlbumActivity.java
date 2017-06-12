package com.hiti.prinbiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.hiti.jumpinfo.FlowMode;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.jumpinfo.JumpInfo;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.trace.GlobalVariable_AlbumSelInfo;
import com.hiti.ui.cacheadapter.viewholder.AlbumViewHolder;
import com.hiti.ui.cacheadapter.viewholder.BaseViewHolder;
import com.hiti.ui.drawview.garnishitem.PathLoader.ThumbnailLoader;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.ui.hitiwebview.HITI_WEB_VIEW_STATUS;
import com.hiti.ui.indexview.CellTarget;
import com.hiti.utility.LogManager;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import com.hiti.utility.grid.ImageAdapter;
import com.hiti.utility.grid.ImageAdapter.Type;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

public class AlbumActivity extends Activity {
    LogManager LOG;
    String TAG;
    FlowMode mFlowMode;
    NFCInfo mNFCInfo;
    private GlideAdapter m_AlbumAdapter;
    private ListView m_AlbumListView;
    private AlbumLoader m_AlbumLoader;
    private ImageButton m_BackButton;
    private ProgressBar m_ProgressBar;
    private ShowMSGDialog m_ShowMSGDialog;
    private TextView m_TitleTextView;
    private boolean m_bFromCollage;
    private int m_iItemSize;
    private int m_iPathRoute;
    private int m_iScreenWidth;
    private int m_iTotalThumbnail;
    private ArrayList<Long> m_lPhotoIDList;
    private JumpPreferenceKey m_pathPref;
    private ArrayList<String> m_strAlbumIDList;
    private ArrayList<String> m_strAlbumNameList;
    private ArrayList<String> m_strPhotoPathList;
    GlobalVariable_AlbumSelInfo prefAlbumInfo;

    /* renamed from: com.hiti.prinbiz.AlbumActivity.1 */
    class C02571 implements OnClickListener {
        C02571() {
        }

        public void onClick(View v) {
            AlbumActivity.this.onBackButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.prinbiz.AlbumActivity.2 */
    class C02582 implements OnItemClickListener {
        C02582() {
        }

        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
            AlbumActivity.this.SelectAlbum(position);
        }
    }

    /* renamed from: com.hiti.prinbiz.AlbumActivity.3 */
    class C07433 implements INfcPreview {
        C07433() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            AlbumActivity.this.mNFCInfo = nfcInfo;
            AlbumActivity.this.LOG.m383d(AlbumActivity.this.TAG, "GetNfcData  " + MobileInfo.GetHmsSStamp());
            if (AlbumActivity.this.mNFCInfo.mPrintMode == PrintMode.NormalMain) {
                AlbumActivity.this.ReLoadThumbnail();
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.AlbumActivity.4 */
    class C07444 implements MSGListener {
        C07444() {
        }

        public void OKClick() {
            AlbumActivity.this.Exit(51, null);
        }

        public void Close() {
        }

        public void CancelClick() {
        }
    }

    class AlbumLoader extends ThumbnailLoader {
        public AlbumLoader(Context context) {
            super(context, null, null);
        }

        public void LoadFinish() {
            AlbumActivity.this.LOG.m383d(AlbumActivity.this.TAG, "LoadFinish " + MobileInfo.GetHmsSStamp());
            AlbumActivity.this.m_ProgressBar.setVisibility(8);
            if (AlbumActivity.this.m_AlbumAdapter != null) {
                AlbumActivity.this.m_iTotalThumbnail = AlbumActivity.this.m_strAlbumNameList.size();
                AlbumActivity.this.m_AlbumAdapter.ClearCache();
                AlbumActivity.this.m_AlbumListView.setAdapter(AlbumActivity.this.m_AlbumAdapter);
                AlbumActivity.this.m_AlbumAdapter.notifyDataSetChanged();
            }
        }

        public void BeforeLoadFinish() {
        }
    }

    class GlideAdapter extends ImageAdapter {

        /* renamed from: com.hiti.prinbiz.AlbumActivity.GlideAdapter.1 */
        class C07451 implements Callback {
            final /* synthetic */ BaseViewHolder val$holder;

            C07451(BaseViewHolder baseViewHolder) {
                this.val$holder = baseViewHolder;
            }

            public void onSuccess() {
                GlideAdapter.this.AfterBuildImage(this.val$holder, false);
            }

            public void onError() {
                GlideAdapter.this.AfterBuildImage(this.val$holder, true);
            }
        }

        public GlideAdapter(Context context, Type type) {
            super(context, type);
        }

        public View SetHolder(LayoutInflater inflater, BaseViewHolder holder) {
            AlbumViewHolder mHolder = (AlbumViewHolder) holder;
            View convertView = inflater.inflate(C0349R.layout.item_album, null);
            mHolder.m_HolderImageView = (ImageView) convertView.findViewById(C0349R.id.m_AlbumImageView);
            mHolder.m_HolderImageView.getLayoutParams().height = AlbumActivity.this.m_iItemSize;
            mHolder.m_HolderImageView.getLayoutParams().width = AlbumActivity.this.m_iItemSize;
            mHolder.m_CheckView = (ImageView) convertView.findViewById(C0349R.id.m_AlbumCheckImageView);
            mHolder.m_AlbumTextView = (TextView) convertView.findViewById(C0349R.id.m_AlbumTextView);
            mHolder.m_AlbumTextView.setWidth(AlbumActivity.this.m_iScreenWidth / 3);
            convertView.setTag(mHolder);
            return convertView;
        }

        public void ClearViewHodler(BaseViewHolder holder) {
            Picasso.with(AlbumActivity.this).cancelRequest(holder.m_HolderImageView);
        }

        public void SetImageView(BaseViewHolder holder) {
            Picasso.with(AlbumActivity.this).load(Uri.parse("file://" + ((String) AlbumActivity.this.m_strPhotoPathList.get(holder.m_iID)))).resize(AlbumActivity.this.m_iItemSize, AlbumActivity.this.m_iItemSize).noFade().centerCrop().placeholder((int) C0349R.drawable.thumb_album).into(holder.m_HolderImageView, new C07451(holder));
        }

        public void SetItem(BaseViewHolder mHolder) {
            AlbumViewHolder holder = (AlbumViewHolder) mHolder;
            int position = holder.m_iID;
            holder.m_CheckView.setId(position);
            holder.m_AlbumTextView.setId(position);
            holder.m_HolderImageView.setId(position);
            if (position < AlbumActivity.this.m_strAlbumNameList.size()) {
                holder.m_AlbumTextView.setText((CharSequence) AlbumActivity.this.m_strAlbumNameList.get(position));
            }
        }

        public int GetTotalCout() {
            return AlbumActivity.this.m_iTotalThumbnail;
        }

        public Bitmap GetPhotoThumbnail(int id) {
            return CellTarget.GetThumbnail(AlbumActivity.this, ((Long) AlbumActivity.this.m_lPhotoIDList.get(id)).longValue(), C0349R.drawable.thumb_album);
        }
    }

    public AlbumActivity() {
        this.m_AlbumLoader = null;
        this.m_strAlbumNameList = null;
        this.m_strAlbumIDList = null;
        this.m_strPhotoPathList = null;
        this.m_lPhotoIDList = null;
        this.m_AlbumListView = null;
        this.m_AlbumAdapter = null;
        this.m_iTotalThumbnail = 0;
        this.m_BackButton = null;
        this.m_TitleTextView = null;
        this.m_ProgressBar = null;
        this.m_ShowMSGDialog = null;
        this.m_iScreenWidth = 0;
        this.m_iItemSize = 0;
        this.prefAlbumInfo = null;
        this.m_pathPref = null;
        this.m_bFromCollage = false;
        this.m_iPathRoute = 0;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = XmlPullParser.NO_NAMESPACE;
        this.mFlowMode = FlowMode.Normal;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0349R.layout.activity_album);
        this.LOG = new LogManager(0);
        this.TAG = getClass().getSimpleName();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.m_iScreenWidth = dm.widthPixels;
        this.m_iItemSize = this.m_iScreenWidth / 5;
        GetBundle();
        this.m_TitleTextView = (TextView) findViewById(C0349R.id.m_TitleTextView);
        this.m_TitleTextView.setText((String) getResources().getText(C0349R.string.ALBUM_SOURCE));
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_BackButton.setOnClickListener(new C02571());
        this.m_ProgressBar = (ProgressBar) findViewById(C0349R.id.loadThumbnailProgressBar);
        this.m_ProgressBar.setVisibility(0);
        this.m_ProgressBar.setIndeterminate(true);
        this.m_strAlbumNameList = new ArrayList();
        this.m_strAlbumIDList = new ArrayList();
        this.m_strPhotoPathList = new ArrayList();
        this.m_lPhotoIDList = new ArrayList();
        this.m_AlbumListView = (ListView) findViewById(C0349R.id.listAlbum);
        this.m_AlbumListView.setOnItemClickListener(new C02582());
        this.m_AlbumAdapter = new GlideAdapter(this, Type.Album);
    }

    private void GetBundle() {
        this.m_pathPref = new JumpPreferenceKey(this);
        this.m_iPathRoute = this.m_pathPref.GetPathSelectedPref();
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                this.m_bFromCollage = bundle.getBoolean(JumpBundleMessage.BUNDLE_MSG_COLLAGE_FOLLOW);
            }
        }
    }

    protected void onStart() {
        super.onStart();
        this.LOG.m385i(this.TAG, "onStart");
    }

    protected void onNewIntent(Intent intent) {
        this.LOG.m386v(this.TAG, "onNewIntent " + intent);
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        this.LOG.m383d(this.TAG, "onResume " + MobileInfo.GetHmsSStamp());
        if (this.mFlowMode == FlowMode.BackMainPage) {
            finish();
            return;
        }
        this.m_ProgressBar.setVisibility(0);
        this.m_ProgressBar.setIndeterminate(true);
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07433());
    }

    protected void onPause() {
        this.LOG.m385i(this.TAG, "onPause " + this.m_AlbumAdapter);
        if (!(this.mFlowMode == FlowMode.BackMainPage || this.mNFCInfo.mNfcAdapter == null)) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        this.LOG.m385i("onStop()", "AlbumActivity " + this.m_AlbumAdapter);
    }

    public void onDestroy() {
        super.onDestroy();
        this.LOG.m385i("onDestroy", "AlbumActivity");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.LOG.m385i(this.TAG, "onActivityResult " + resultCode);
        this.LOG.m385i(this.TAG, "mFlowMode " + this.mFlowMode);
        if (resultCode == 50) {
            this.mFlowMode = FlowMode.BackMainPage;
            setResult(50);
        }
    }

    public void onBackButtonClicked(View v) {
        if (this.m_iPathRoute == ControllerState.PLAY_COUNT_STATE) {
            Exit(51, null);
        } else {
            ShowMSGDialog(getString(C0349R.string.HINT_MSG), getString(C0349R.string.HINT_TITLE));
        }
    }

    public void onBackPressed() {
        onBackButtonClicked(null);
    }

    void Exit(int iResult, Bundle bundle) {
        this.LOG.m383d(this.TAG, "Exit " + iResult);
        try {
            this.m_AlbumListView.setAdapter(null);
            if (this.m_AlbumAdapter != null) {
                this.m_AlbumAdapter.Stop();
                this.m_AlbumAdapter = null;
            }
            if (this.m_AlbumLoader != null) {
                this.m_AlbumLoader.Stop();
                this.m_AlbumLoader = null;
            }
        } catch (Exception e) {
            this.LOG.m385i(HITI_WEB_VIEW_STATUS.ERROR, "Exit");
            e.printStackTrace();
        }
        switch (iResult) {
            case JumpInfo.RESULT_PHOTO_ACTIVITY /*55*/:
                if (this.m_bFromCollage) {
                    setResult(iResult, new Intent().putExtras(bundle));
                    finish();
                    return;
                }
                Intent intent = new Intent(this, GalleryActivity.class);
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                startActivityForResult(intent, iResult);
            default:
                setResult(iResult, null);
                finish();
        }
    }

    void ReLoadThumbnail() {
        try {
            if (this.m_AlbumLoader != null) {
                this.m_AlbumLoader.Stop();
                this.m_AlbumLoader = null;
            }
            this.m_AlbumListView.setAdapter(null);
            this.m_strAlbumNameList.clear();
            this.m_strAlbumIDList.clear();
            this.m_strPhotoPathList.clear();
            this.m_lPhotoIDList.clear();
            if (this.m_AlbumAdapter == null) {
                this.m_AlbumAdapter = new GlideAdapter(this, Type.Album);
            }
            this.m_AlbumLoader = new AlbumLoader(this);
            if (this.m_AlbumLoader != null) {
                this.LOG.m383d(this.TAG, "ReLoadThumbnail " + MobileInfo.GetHmsSStamp());
                this.m_AlbumLoader.LoadAlbum(this.m_strAlbumNameList, this.m_strAlbumIDList, this.m_strPhotoPathList, this.m_lPhotoIDList);
                this.m_AlbumLoader.execute(new String[]{XmlPullParser.NO_NAMESPACE});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SelectAlbum(int position) {
        this.prefAlbumInfo = new GlobalVariable_AlbumSelInfo(getBaseContext(), this.m_bFromCollage);
        String strAlbumId = (String) this.m_strAlbumIDList.get(position);
        String strAlbumName = (String) this.m_strAlbumNameList.get(position);
        this.prefAlbumInfo.ClearGlobalVariable();
        this.prefAlbumInfo.SetAlbumRoute(1);
        this.prefAlbumInfo.SaveGlobalVariable();
        this.prefAlbumInfo.SetMobileAlbumId(Integer.parseInt(strAlbumId));
        this.prefAlbumInfo.SetAlbumName(strAlbumName);
        this.prefAlbumInfo.SaveGlobalVariable();
        Bundle bundle = null;
        if (this.m_bFromCollage) {
            bundle = new Bundle();
            bundle.putString(JumpBundleMessage520.BUNDLE_MSG_ALBUM_ID, strAlbumId);
            bundle.putString(JumpBundleMessage520.BUNDLE_MSG_ALBUM_NAME, strAlbumName);
            bundle.putBoolean(JumpBundleMessage.BUNDLE_MSG_COLLAGE_FOLLOW, true);
        } else if (getIntent() != null) {
            bundle = getIntent().getExtras();
        }
        this.LOG.m383d(this.TAG, "SelectAlbum m_bFromCollage: " + this.m_bFromCollage);
        this.LOG.m383d(this.TAG, "SelectAlbum bundle: " + bundle);
        Exit(55, bundle);
    }

    public void ShowMSGDialog(String strMessage, String strTitle) {
        if (this.m_ShowMSGDialog == null) {
            this.m_ShowMSGDialog = new ShowMSGDialog(this, false);
        }
        this.m_ShowMSGDialog.SetMSGListener(new C07444());
        this.m_ShowMSGDialog.ShowMessageDialog(strMessage, strTitle);
    }
}
