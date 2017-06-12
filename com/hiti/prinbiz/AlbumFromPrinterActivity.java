package com.hiti.prinbiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.hiti.printerprotocol.request.HitiPPR_GetAlbumData;
import com.hiti.printerprotocol.request.HitiPPR_GetAlbumMeta;
import com.hiti.printerprotocol.request.HitiPPR_GetPrinterInfo;
import com.hiti.printerprotocol.request.HitiPPR_GetThumbData;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.trace.GlobalVariable_AlbumSelInfo;
import com.hiti.trace.GlobalVariable_SaveLoadedMeta;
import com.hiti.trace.GlobalVariable_WifiAutoConnectInfo;
import com.hiti.ui.cacheadapter.CatchBmpFromPrinter;
import com.hiti.ui.cacheadapter.PenddingGroup;
import com.hiti.ui.cacheadapter.viewholder.AlbumViewHolder;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.utility.ByteConvertUtility;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.PrinterListListener;
import com.hiti.utility.ShowPrinterList;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.Verify.ThreadMode;
import com.hiti.utility.dialog.DialogListener;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import com.hiti.utility.wifi.WifiAutoConnect;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.nntp.NNTPReply;
import org.xmlpull.v1.XmlPullParser;

public class AlbumFromPrinterActivity extends Activity {
    private static final String ALBUM_LOADED_INDEX = "ALBUM_LOADED_INDEX";
    private static final String ALBUM_LOADED_NAME = "ALBUM_LOADED_NAME";
    private static final String ALBUM_LOADED_PATH = "ALBUM_LOADED_PATH";
    String IP;
    LogManager LOG;
    private Message MSG;
    String TAG;
    private GetAlbumHandler handler;
    FlowMode mFlowMode;
    NFCInfo mNFCInfo;
    private AlbumAdapter m_AlbumAdapter;
    private ArrayList<byte[]> m_AlbumIDList;
    private ListView m_AlbumListView;
    private ArrayList<byte[]> m_AlbumStorageIDList;
    private ImageButton m_BackButton;
    private ProgressBar m_CenterProgressBar;
    private HitiPPR_GetAlbumData m_GetAlbumData;
    private HitiPPR_GetAlbumMeta m_GetAlbumMeta;
    private HitiPPR_GetThumbData m_GetOneThumb;
    HitiPPR_GetPrinterInfo m_HitiPPR_GetPrinterInfo;
    private ProgressBar m_ProgressBar;
    private ShowMSGDialog m_ShowMSGDialog;
    ShowPrinterList m_ShowPrinterList;
    private TextView m_TitleTextView;
    private ShowMSGDialog m_WaitingDialog;
    private GlobalVariable_WifiAutoConnectInfo m_WifiInfo;
    private boolean m_bBackIDCollage;
    private boolean m_bBackState;
    boolean m_bHaveIPandPorted;
    boolean m_bOnMDNS;
    private boolean m_bSockedConnected;
    boolean m_bThreadStop;
    private Bitmap m_bpThumbAlbum;
    private ArrayList<Integer> m_iAlbumIndexList;
    private int m_iItemSize;
    private int m_iPathRoute;
    int m_iPort;
    private int m_iScreenWidth;
    private int m_iTotalThumbnail;
    JumpPreferenceKey m_pref;
    GlobalVariable_SaveLoadedMeta m_prefAlbumLoadedMeta;
    private ArrayList<String> m_strAlbumNameList;
    private String m_strCurrentSSID;
    private String m_strLastSSID;
    private String m_strSecurityKey;
    private ArrayList<String> m_strThumbPathList;
    private AlbumAutoWifiConnect m_wifiAutoConnect;
    boolean mbPageLeave;

    /* renamed from: com.hiti.prinbiz.AlbumFromPrinterActivity.11 */
    static /* synthetic */ class AnonymousClass11 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$utility$Verify$ThreadMode;

        static {
            $SwitchMap$com$hiti$utility$Verify$ThreadMode = new int[ThreadMode.values().length];
            try {
                $SwitchMap$com$hiti$utility$Verify$ThreadMode[ThreadMode.AutoWifi.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$utility$Verify$ThreadMode[ThreadMode.AlbumMeta.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$utility$Verify$ThreadMode[ThreadMode.GetInfo.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.AlbumFromPrinterActivity.1 */
    class C02591 implements OnClickListener {
        C02591() {
        }

        public void onClick(View v) {
            AlbumFromPrinterActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.AlbumFromPrinterActivity.2 */
    class C02602 implements OnItemClickListener {
        C02602() {
        }

        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
            if (AlbumFromPrinterActivity.this.m_AlbumAdapter != null) {
                AlbumFromPrinterActivity.this.m_AlbumAdapter.SelectAlbum(position);
            }
        }
    }

    public class AlbumAdapter extends BaseAdapter {
        public static final int CATCH_BITMAP_SIZE = 32;
        public static final int PENDDING_SIZE = 40;
        private LayoutInflater mInflater;
        CatchBmpFromPrinter m_CacheBmp;
        PenddingGroup<AlbumViewHolder> m_PenddingGroup;
        ReflashImage m_ReflashThread;
        Socket m_Socket;
        Bitmap m_bpDefaultPicture;
        int m_iCnt;

        class ReflashImage {
            private AlbumViewHolder m_holder;
            public int m_iID;
            Pair<String, Bitmap> pair;

            public ReflashImage(AlbumViewHolder holder, Socket socket) {
                this.m_holder = null;
                this.pair = null;
                this.m_holder = holder;
                this.m_iID = holder.m_iID;
                AlbumFromPrinterActivity.this.handler.SetStop(false);
                GetAlbumNameANdOneThumb(socket);
            }

            private void GetAlbumNameANdOneThumb(Socket socket) {
                int id = this.m_iID;
                byte[] albumId = (byte[]) AlbumFromPrinterActivity.this.m_AlbumIDList.get(id);
                byte[] storageId = (byte[]) AlbumFromPrinterActivity.this.m_AlbumStorageIDList.get(id);
                if (AlbumFromPrinterActivity.this.m_iAlbumIndexList.contains(Integer.valueOf(id))) {
                    int iid = AlbumFromPrinterActivity.this.m_iAlbumIndexList.indexOf(Integer.valueOf(id));
                    String strLoadedAlbumName = (String) AlbumFromPrinterActivity.this.m_strAlbumNameList.get(iid);
                    String strLoadedThumbPath = (String) AlbumFromPrinterActivity.this.m_strThumbPathList.get(iid);
                    if (AlbumFromPrinterActivity.this.m_GetAlbumMeta != null && AlbumFromPrinterActivity.this.m_GetAlbumMeta.IsRunning()) {
                        AlbumFromPrinterActivity.this.m_GetAlbumMeta.Stop();
                    }
                    AlbumFromPrinterActivity.this.MSG = new Message();
                    AlbumFromPrinterActivity.this.MSG.what = RequestState.REQUEST_GET_ALBUM_DATA;
                    Bundle data = new Bundle();
                    data.putInt(AlbumFromPrinterActivity.ALBUM_LOADED_INDEX, id);
                    data.putString(AlbumFromPrinterActivity.ALBUM_LOADED_PATH, strLoadedThumbPath);
                    data.putString(AlbumFromPrinterActivity.ALBUM_LOADED_NAME, strLoadedAlbumName);
                    AlbumFromPrinterActivity.this.MSG.setData(data);
                    AlbumFromPrinterActivity.this.handler.sendMessage(AlbumFromPrinterActivity.this.MSG);
                    return;
                }
                AlbumFromPrinterActivity.this.m_GetAlbumData = new HitiPPR_GetAlbumData(AlbumFromPrinterActivity.this.getBaseContext(), AlbumFromPrinterActivity.this.IP, AlbumFromPrinterActivity.this.m_iPort, AlbumFromPrinterActivity.this.handler);
                AlbumFromPrinterActivity.this.m_GetAlbumData.PutIDs(storageId, albumId);
                AlbumFromPrinterActivity.this.m_GetAlbumData.PutIndex(id);
                AlbumFromPrinterActivity.this.m_GetAlbumData.SetSocket(socket);
                AlbumFromPrinterActivity.this.m_GetAlbumData.start();
            }

            private void SetAlbumName(String albumName, int id) {
                if (!AlbumFromPrinterActivity.this.m_iAlbumIndexList.contains(Integer.valueOf(id))) {
                    AlbumFromPrinterActivity.this.m_iAlbumIndexList.add(Integer.valueOf(this.m_iID));
                }
                this.m_holder.m_AlbumTextView.setText(albumName);
            }

            protected void BuildThumbnail(String path, String name, boolean bLoadedThumb, Socket socket) {
                Bitmap bmpOneThumb = null;
                try {
                    if (FileUtility.FileExist(path)) {
                        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(path, false);
                        if (bmr.IsSuccess()) {
                            bmpOneThumb = bmr.GetBitmap();
                        } else {
                            bmpOneThumb = AlbumFromPrinterActivity.this.m_bpThumbAlbum.copy(AlbumFromPrinterActivity.this.m_bpThumbAlbum.getConfig(), false);
                        }
                    } else {
                        bmpOneThumb = AlbumFromPrinterActivity.this.m_bpThumbAlbum.copy(AlbumFromPrinterActivity.this.m_bpThumbAlbum.getConfig(), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SetImage(name, socket, bmpOneThumb, path);
            }

            private void SetImage(String name, Socket socket, Bitmap bmpOneThumb, String path) {
                if (bmpOneThumb != null) {
                    if (this.m_iID == this.m_holder.m_iID) {
                        Drawable drawable = this.m_holder.m_HolderImageView.getDrawable();
                        if (drawable instanceof BitmapDrawable) {
                            Bitmap RecycleBitmap = ((BitmapDrawable) drawable).getBitmap();
                            if (!(RecycleBitmap == null || RecycleBitmap == AlbumAdapter.this.m_bpDefaultPicture || RecycleBitmap == AlbumFromPrinterActivity.this.m_bpThumbAlbum || RecycleBitmap == bmpOneThumb || AlbumAdapter.this.m_CacheBmp.IsCache(this.m_iID))) {
                                RecycleBitmap.recycle();
                            }
                        }
                        this.m_holder.m_HolderImageView.setImageBitmap(bmpOneThumb);
                        this.pair = new Pair(name, bmpOneThumb);
                        AlbumFromPrinterActivity.this.LOG.m385i("AddCache", XmlPullParser.NO_NAMESPACE + this.m_iID);
                        AlbumAdapter.this.m_CacheBmp.AddCache(this.m_iID, this.pair);
                        AlbumAdapter.this.m_PenddingGroup.RemoveFirstPenddingViewHolder();
                    } else if (!path.equals("xxx")) {
                        bmpOneThumb.recycle();
                    }
                }
                NextReflashPhoto(socket);
            }

            private void NextReflashPhoto(Socket socket) {
                AlbumFromPrinterActivity.this.LOG.m385i(AlbumFromPrinterActivity.this.TAG, "ReflashImage NextReflashPhoto " + AlbumAdapter.this.m_PenddingGroup.HavePendding());
                if (AlbumAdapter.this.m_PenddingGroup.HavePendding()) {
                    AlbumAdapter.this.m_ReflashThread = new ReflashImage((AlbumViewHolder) AlbumAdapter.this.m_PenddingGroup.GetFirstPenddingViewHolder(), socket);
                    return;
                }
                AlbumAdapter.this.m_ReflashThread = null;
                if (AlbumFromPrinterActivity.this.m_ProgressBar != null) {
                    AlbumFromPrinterActivity.this.m_ProgressBar.setVisibility(8);
                }
                if (AlbumFromPrinterActivity.this.m_GetAlbumData != null) {
                    AlbumFromPrinterActivity.this.m_GetAlbumData.Stop();
                }
            }
        }

        public AlbumAdapter() {
            this.m_bpDefaultPicture = null;
            this.m_iCnt = -1;
            this.m_Socket = null;
            this.m_ReflashThread = null;
            this.m_PenddingGroup = null;
            this.m_CacheBmp = null;
            this.mInflater = (LayoutInflater) AlbumFromPrinterActivity.this.getSystemService("layout_inflater");
            try {
                InputStream is = AlbumFromPrinterActivity.this.getResources().openRawResource(C0349R.drawable.thumb_album);
                this.m_bpDefaultPicture = BitmapFactory.decodeStream(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.m_CacheBmp = new CatchBmpFromPrinter(CATCH_BITMAP_SIZE);
            this.m_PenddingGroup = new PenddingGroup(PENDDING_SIZE);
        }

        public void SetSocket(Socket socket) {
            this.m_Socket = socket;
        }

        public int getCount() {
            return AlbumFromPrinterActivity.this.m_iTotalThumbnail;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            AlbumViewHolder holder;
            if (convertView == null) {
                holder = new AlbumViewHolder();
                convertView = this.mInflater.inflate(C0349R.layout.item_album, null);
                holder.m_HolderImageView = (ImageView) convertView.findViewById(C0349R.id.m_AlbumImageView);
                holder.m_HolderImageView.getLayoutParams().height = AlbumFromPrinterActivity.this.m_iItemSize;
                holder.m_HolderImageView.getLayoutParams().width = AlbumFromPrinterActivity.this.m_iItemSize;
                holder.m_CheckView = (ImageView) convertView.findViewById(C0349R.id.m_AlbumCheckImageView);
                holder.m_AlbumTextView = (TextView) convertView.findViewById(C0349R.id.m_AlbumTextView);
                holder.m_AlbumTextView.setWidth(AlbumFromPrinterActivity.this.m_iScreenWidth / 3);
                convertView.setTag(holder);
            } else {
                holder = (AlbumViewHolder) convertView.getTag();
            }
            holder.m_CheckView.setId(position);
            holder.m_AlbumTextView.setId(position);
            holder.m_HolderImageView.setId(position);
            holder.m_iID = position;
            try {
                boolean bRet = this.m_CacheBmp.IsCache(holder.m_iID);
                AlbumFromPrinterActivity.this.LOG.m385i(AlbumFromPrinterActivity.this.TAG, "getView() ID: " + holder.m_iID + " IsCache: " + bRet);
                if (bRet) {
                    holder.m_HolderImageView.setImageBitmap(this.m_CacheBmp.GetCacheBmp(holder.m_iID));
                    holder.m_AlbumTextView.setText(this.m_CacheBmp.GetCacheText(holder.m_iID));
                } else {
                    holder.m_HolderImageView.setImageBitmap(this.m_bpDefaultPicture.copy(this.m_bpDefaultPicture.getConfig(), true));
                    holder.m_AlbumTextView.setText("loading...");
                    PenddingReflash(holder);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        void PenddingReflash(AlbumViewHolder holder) {
            this.m_PenddingGroup.AddPendding(holder);
            if (this.m_ReflashThread == null) {
                this.m_ReflashThread = new ReflashImage((AlbumViewHolder) this.m_PenddingGroup.GetFirstPenddingViewHolder(), null);
            }
        }

        void SelectAlbum(int position) {
            if (AlbumFromPrinterActivity.this.m_iAlbumIndexList.indexOf(Integer.valueOf(position)) != -1) {
                AlbumFromPrinterActivity.this.SetAlbumData(position);
                AlbumFromPrinterActivity.this.m_bBackState = false;
                AlbumFromPrinterActivity.this.CheckIfStoreNewWifiInfo();
            }
        }

        void ClearCache() {
            this.m_CacheBmp.ClearCache();
            this.m_PenddingGroup.ClearPendding();
        }
    }

    /* renamed from: com.hiti.prinbiz.AlbumFromPrinterActivity.3 */
    class C07463 extends DialogListener {
        C07463() {
        }

        public void SetNowConnSSID(String strNowSSID) {
            if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                AlbumFromPrinterActivity.this.ShowPrinterListDialog();
            }
        }

        public void SetLastConnSSID(String strLastSSID) {
            if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                AlbumFromPrinterActivity.this.m_WaitingDialog.ShowWaitingHintDialog(ThreadMode.AutoWifi, AlbumFromPrinterActivity.this.getString(C0349R.string.CONN_SEARCHING));
                AlbumFromPrinterActivity.this.m_wifiAutoConnect = new AlbumAutoWifiConnect(AlbumFromPrinterActivity.this.getBaseContext(), strLastSSID, AlbumFromPrinterActivity.this.m_strSecurityKey);
                AlbumFromPrinterActivity.this.m_wifiAutoConnect.execute(new Void[0]);
            }
        }

        public void CancelConnetion(ThreadMode mode) {
            AlbumFromPrinterActivity.this.m_WaitingDialog.StopWaitingDialog();
            AlbumFromPrinterActivity.this.m_bThreadStop = true;
            switch (AnonymousClass11.$SwitchMap$com$hiti$utility$Verify$ThreadMode[mode.ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    if (AlbumFromPrinterActivity.this.m_wifiAutoConnect != null) {
                        AlbumFromPrinterActivity.this.m_wifiAutoConnect.ConnectionStop();
                        break;
                    }
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    if (AlbumFromPrinterActivity.this.m_GetAlbumMeta != null) {
                        AlbumFromPrinterActivity.this.m_GetAlbumMeta.Stop();
                        break;
                    }
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    if (AlbumFromPrinterActivity.this.m_HitiPPR_GetPrinterInfo != null) {
                        AlbumFromPrinterActivity.this.m_HitiPPR_GetPrinterInfo.Stop();
                        break;
                    }
                    break;
            }
            AlbumFromPrinterActivity.this.Exit(51, null);
        }

        public void LeaveConfirm() {
        }

        public void LeaveCancel() {
        }

        public void LeaveClose() {
        }
    }

    /* renamed from: com.hiti.prinbiz.AlbumFromPrinterActivity.4 */
    class C07474 extends DialogListener {
        C07474() {
        }

        public void SetNowConnSSID(String strNowSSID) {
            if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                AlbumFromPrinterActivity.this.ShowPrinterListDialog();
            }
        }

        public void SetLastConnSSID(String strLastSSID) {
            if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                AlbumFromPrinterActivity.this.m_WaitingDialog.ShowWaitingHintDialog(ThreadMode.AutoWifi, AlbumFromPrinterActivity.this.getString(C0349R.string.CONN_SEARCHING));
                AlbumFromPrinterActivity.this.m_wifiAutoConnect = new AlbumAutoWifiConnect(AlbumFromPrinterActivity.this.getBaseContext(), strLastSSID, AlbumFromPrinterActivity.this.m_strSecurityKey);
                AlbumFromPrinterActivity.this.m_wifiAutoConnect.execute(new Void[0]);
            }
        }

        public void CancelConnetion(ThreadMode mode) {
            AlbumFromPrinterActivity.this.m_WaitingDialog.StopWaitingDialog();
            AlbumFromPrinterActivity.this.m_bThreadStop = true;
            switch (AnonymousClass11.$SwitchMap$com$hiti$utility$Verify$ThreadMode[mode.ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    if (AlbumFromPrinterActivity.this.m_wifiAutoConnect != null) {
                        AlbumFromPrinterActivity.this.m_wifiAutoConnect.ConnectionStop();
                        break;
                    }
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    if (AlbumFromPrinterActivity.this.m_GetAlbumMeta != null) {
                        AlbumFromPrinterActivity.this.m_GetAlbumMeta.Stop();
                        break;
                    }
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    if (AlbumFromPrinterActivity.this.m_HitiPPR_GetPrinterInfo != null) {
                        AlbumFromPrinterActivity.this.m_HitiPPR_GetPrinterInfo.Stop();
                        break;
                    }
                    break;
            }
            AlbumFromPrinterActivity.this.Exit(51, null);
        }

        public void LeaveConfirm() {
        }

        public void LeaveCancel() {
        }

        public void LeaveClose() {
        }
    }

    /* renamed from: com.hiti.prinbiz.AlbumFromPrinterActivity.5 */
    class C07485 implements INfcPreview {
        C07485() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            AlbumFromPrinterActivity.this.mNFCInfo = nfcInfo;
        }
    }

    /* renamed from: com.hiti.prinbiz.AlbumFromPrinterActivity.6 */
    class C07496 implements MSGListener {
        C07496() {
        }

        public void OKClick() {
            AlbumFromPrinterActivity.this.handler.SetStop(false);
            AlbumFromPrinterActivity.this.OpenWifi();
        }

        public void Close() {
        }

        public void CancelClick() {
            AlbumFromPrinterActivity.this.GoToGalleyOrBack(AlbumFromPrinterActivity.this.m_bBackState);
        }
    }

    /* renamed from: com.hiti.prinbiz.AlbumFromPrinterActivity.7 */
    class C07507 implements MSGListener {
        C07507() {
        }

        public void OKClick() {
            AlbumFromPrinterActivity.this.handler.SetStop(false);
            AlbumFromPrinterActivity.this.Exit(51, null);
        }

        public void Close() {
        }

        public void CancelClick() {
        }
    }

    /* renamed from: com.hiti.prinbiz.AlbumFromPrinterActivity.8 */
    class C07518 implements MSGListener {
        C07518() {
        }

        public void OKClick() {
            AlbumFromPrinterActivity.this.handler.SetStop(true);
            AlbumFromPrinterActivity.this.CheckIfStoreNewWifiInfo();
        }

        public void Close() {
        }

        public void CancelClick() {
            AlbumFromPrinterActivity.this.m_bBackState = false;
        }
    }

    /* renamed from: com.hiti.prinbiz.AlbumFromPrinterActivity.9 */
    class C07529 implements MSGListener {
        final /* synthetic */ String val$strNowSSID;

        C07529(String str) {
            this.val$strNowSSID = str;
        }

        public void OKClick() {
            AlbumFromPrinterActivity.this.SaveNewWifiInfo(this.val$strNowSSID);
            AlbumFromPrinterActivity.this.GoToGalleyOrBack(AlbumFromPrinterActivity.this.m_bBackState);
        }

        public void Close() {
        }

        public void CancelClick() {
            AlbumFromPrinterActivity.this.GoToGalleyOrBack(AlbumFromPrinterActivity.this.m_bBackState);
        }
    }

    class AlbumAutoWifiConnect extends WifiAutoConnect {
        public AlbumAutoWifiConnect(Context context, String strSSID, String strPassword) {
            super(context, strSSID, strPassword);
            AlbumFromPrinterActivity.this.m_bThreadStop = false;
        }

        public void ConnectionSuccess() {
            ConnectionStop();
            AlbumFromPrinterActivity.this.handler.SetStop(false);
            if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                AlbumFromPrinterActivity.this.ShowPrinterListDialog();
            }
        }

        public void ConnectionFail() {
            ConnectionStop();
            AlbumFromPrinterActivity.this.ShowNoWiFiDialog();
        }

        public void ConnectionStop() {
            AlbumFromPrinterActivity.this.m_WaitingDialog.StopWaitingDialog();
            SetStop(true);
        }
    }

    class GetAlbumHandler extends MSGHandler {
        GetAlbumHandler() {
        }

        public void handleMessage(Message msg) {
            if (!IsStop()) {
                AlbumFromPrinterActivity.this.LOG.m385i(AlbumFromPrinterActivity.this.TAG, "GetAlbumHandler " + msg.what);
                Socket socket;
                String strMsg;
                String strAlbumName;
                Socket m_Socket;
                String thumbPath;
                switch (msg.what) {
                    case RequestState.REQUEST_GET_PRINTER_INFO /*310*/:
                        String strPID = AlbumFromPrinterActivity.this.m_HitiPPR_GetPrinterInfo.GetAttrProductIDString();
                        socket = AlbumFromPrinterActivity.this.m_HitiPPR_GetPrinterInfo.GetSocket();
                        if (socket == null) {
                            AlbumFromPrinterActivity.this.m_HitiPPR_GetPrinterInfo.Stop();
                        }
                        AlbumFromPrinterActivity.this.m_WaitingDialog.StopWaitingDialog();
                        String strModel = AlbumFromPrinterActivity.this.m_pref.GetModelPreference();
                        AlbumFromPrinterActivity.this.LOG.m385i(AlbumFromPrinterActivity.this.TAG, "app strModel " + strModel);
                        AlbumFromPrinterActivity.this.LOG.m385i(AlbumFromPrinterActivity.this.TAG, "get strPID " + strPID);
                        if (strModel.equals(strPID)) {
                            if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                                AlbumFromPrinterActivity.this.ReLoadAlbum(socket);
                                return;
                            }
                            return;
                        }
                        String strErr = AlbumFromPrinterActivity.this.getString(C0349R.string.ERROR_MODEL);
                        AlbumFromPrinterActivity.this.KillThread();
                        if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                            AlbumFromPrinterActivity.this.ShowAlertDialog(strErr, AlbumFromPrinterActivity.this.getString(C0349R.string.ERROR));
                        }
                    case RequestState.REQUEST_GET_PRINTER_INFO_ERROR /*311*/:
                        strMsg = msg.getData().getString(MSGHandler.MSG);
                        AlbumFromPrinterActivity.this.m_WaitingDialog.StopWaitingDialog();
                        AlbumFromPrinterActivity.this.KillThread();
                        if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                            AlbumFromPrinterActivity.this.ShowAlertDialog(strMsg, AlbumFromPrinterActivity.this.getString(C0349R.string.ERROR));
                        }
                    case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                        AlbumFromPrinterActivity.this.m_WaitingDialog.StopWaitingDialog();
                        AlbumFromPrinterActivity.this.handler.SetStop(true);
                        strMsg = msg.getData().getString(MSGHandler.MSG);
                        AlbumFromPrinterActivity.this.KillThread();
                        if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                            AlbumFromPrinterActivity.this.ShowAlertDialog(strMsg, AlbumFromPrinterActivity.this.getString(C0349R.string.ERROR));
                        }
                    case RequestState.REQUEST_GET_ALBUM_ID_META /*342*/:
                        AlbumFromPrinterActivity.this.m_WaitingDialog.StopWaitingDialog();
                        if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                            if (AlbumFromPrinterActivity.this.m_ProgressBar != null) {
                                AlbumFromPrinterActivity.this.m_ProgressBar.setVisibility(0);
                            }
                            if (AlbumFromPrinterActivity.this.m_AlbumIDList.isEmpty()) {
                                AlbumFromPrinterActivity.this.m_AlbumIDList.clear();
                                AlbumFromPrinterActivity.this.m_AlbumStorageIDList.clear();
                                AlbumFromPrinterActivity.this.m_iAlbumIndexList.clear();
                                if (AlbumFromPrinterActivity.this.m_GetAlbumMeta != null) {
                                    AlbumFromPrinterActivity.this.m_GetAlbumMeta.GetAlbumID(AlbumFromPrinterActivity.this.m_AlbumIDList, AlbumFromPrinterActivity.this.m_AlbumStorageIDList);
                                }
                                AlbumFromPrinterActivity.this.ReverseMetaList(AlbumFromPrinterActivity.this.m_AlbumIDList, AlbumFromPrinterActivity.this.m_AlbumStorageIDList);
                            }
                            if (AlbumFromPrinterActivity.this.m_GetAlbumMeta == null) {
                                socket = null;
                            } else {
                                socket = AlbumFromPrinterActivity.this.m_GetAlbumMeta.GetSocket();
                            }
                            AlbumFromPrinterActivity.this.m_iTotalThumbnail = AlbumFromPrinterActivity.this.m_AlbumIDList.size();
                            AlbumFromPrinterActivity.this.m_AlbumAdapter.m_iCnt = -1;
                            AlbumFromPrinterActivity.this.m_AlbumAdapter.ClearCache();
                            AlbumFromPrinterActivity.this.m_AlbumAdapter.SetSocket(socket);
                            AlbumFromPrinterActivity.this.m_AlbumListView.setAdapter(AlbumFromPrinterActivity.this.m_AlbumAdapter);
                        }
                    case RequestState.REQUEST_GET_ALBUM_ID_META_ERROR /*343*/:
                        strMsg = msg.getData().getString(MSGHandler.MSG);
                        AlbumFromPrinterActivity.this.m_WaitingDialog.StopWaitingDialog();
                        AlbumFromPrinterActivity.this.KillThread();
                        if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                            AlbumFromPrinterActivity.this.ShowAlertDialog(strMsg, AlbumFromPrinterActivity.this.getString(C0349R.string.ERROR));
                        }
                    case RequestState.REQUEST_GET_ALBUM_DATA /*346*/:
                        strAlbumName = null;
                        String strAlbumPath = null;
                        int id = -1;
                        Bundle data = msg.getData();
                        if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                            if (!(data == null || data.isEmpty())) {
                                id = data.getInt(AlbumFromPrinterActivity.ALBUM_LOADED_INDEX);
                                strAlbumPath = data.getString(AlbumFromPrinterActivity.ALBUM_LOADED_PATH);
                                strAlbumName = data.getString(AlbumFromPrinterActivity.ALBUM_LOADED_NAME);
                                data.clear();
                            }
                            if (id != -1) {
                                if (AlbumFromPrinterActivity.this.m_iAlbumIndexList.contains(Integer.valueOf(id))) {
                                    try {
                                        Thread.sleep(50);
                                        AlbumFromPrinterActivity.this.m_AlbumAdapter.m_ReflashThread.SetAlbumName(strAlbumName, id);
                                        AlbumFromPrinterActivity.this.m_AlbumAdapter.m_ReflashThread.BuildThumbnail(strAlbumPath, strAlbumName, true, null);
                                        return;
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        return;
                                    }
                                }
                            }
                            if (AlbumFromPrinterActivity.this.m_GetAlbumData != null) {
                                AlbumFromPrinterActivity.this.handler.SetStop(true);
                                byte[] strOneThumbId = AlbumFromPrinterActivity.this.m_GetAlbumData.GetOneThumbID();
                                byte[] strStorageId = AlbumFromPrinterActivity.this.m_GetAlbumData.GetStorageID();
                                strAlbumName = AlbumFromPrinterActivity.this.m_GetAlbumData.GetAlbumName();
                                int iNumberOfPhoto = AlbumFromPrinterActivity.this.m_GetAlbumData.GetNumberOfPhoto();
                                id = AlbumFromPrinterActivity.this.m_GetAlbumData.GetIndex();
                                m_Socket = AlbumFromPrinterActivity.this.m_GetAlbumData.GetSocket();
                                strAlbumName = strAlbumName + "(" + iNumberOfPhoto + ")";
                                AlbumFromPrinterActivity.this.m_strAlbumNameList.add(strAlbumName);
                                AlbumFromPrinterActivity.this.m_AlbumAdapter.m_ReflashThread.SetAlbumName(strAlbumName, id);
                                AlbumFromPrinterActivity.this.handler.SetStop(false);
                                if (m_Socket != null) {
                                    AlbumFromPrinterActivity albumFromPrinterActivity = AlbumFromPrinterActivity.this;
                                    AlbumFromPrinterActivity albumFromPrinterActivity2 = AlbumFromPrinterActivity.this;
                                    r0.m_GetOneThumb = new HitiPPR_GetThumbData(r0.getBaseContext(), AlbumFromPrinterActivity.this.IP, AlbumFromPrinterActivity.this.m_iPort, AlbumFromPrinterActivity.this.handler);
                                }
                                AlbumFromPrinterActivity.this.m_GetOneThumb.PutIDs(strOneThumbId, strStorageId);
                                AlbumFromPrinterActivity.this.m_GetOneThumb.PutAlbumName(strAlbumName);
                                AlbumFromPrinterActivity.this.m_GetOneThumb.SetSocket(m_Socket);
                                AlbumFromPrinterActivity.this.m_GetOneThumb.start();
                            }
                        }
                    case RequestState.REQUEST_GET_ALBUM_DATA_ERROR /*347*/:
                        strMsg = msg.getData().getString(MSGHandler.MSG);
                        AlbumFromPrinterActivity.this.KillThread();
                        if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                            AlbumFromPrinterActivity.this.ShowAlertDialog(strMsg, AlbumFromPrinterActivity.this.getString(C0349R.string.ERROR));
                        }
                    case FTPReply.FILE_ACTION_PENDING /*350*/:
                        thumbPath = XmlPullParser.NO_NAMESPACE;
                        if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                            m_Socket = AlbumFromPrinterActivity.this.m_GetOneThumb.GetSocket();
                            thumbPath = AlbumFromPrinterActivity.this.m_GetOneThumb.GetThumbPath();
                            strAlbumName = AlbumFromPrinterActivity.this.m_GetOneThumb.GetAlbumName();
                            AlbumFromPrinterActivity.this.m_strThumbPathList.add(thumbPath);
                            AlbumFromPrinterActivity.this.m_AlbumAdapter.m_ReflashThread.BuildThumbnail(thumbPath, strAlbumName, false, m_Socket);
                        }
                    case RequestState.REQUEST_GET_THUMBNAILS_DATA_ERROR /*351*/:
                        AlbumFromPrinterActivity.this.handler.SetStop(true);
                        strMsg = msg.getData().getString(MSGHandler.MSG);
                        AlbumFromPrinterActivity.this.KillThread();
                        if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                            AlbumFromPrinterActivity.this.ShowAlertDialog(strMsg, AlbumFromPrinterActivity.this.getString(C0349R.string.ERROR));
                        }
                    case RequestState.REQUEST_GET_THUMBNAILS_DATA_NO_THUMBNAIL /*397*/:
                    case NNTPReply.SERVICE_DISCONTINUED /*400*/:
                        if (!AlbumFromPrinterActivity.this.m_bThreadStop) {
                            if (AlbumFromPrinterActivity.this.m_AlbumAdapter.m_ReflashThread != null) {
                                thumbPath = "xxx";
                                AlbumFromPrinterActivity.this.m_strThumbPathList.add(thumbPath);
                                strAlbumName = AlbumFromPrinterActivity.this.m_GetOneThumb.GetAlbumName();
                                AlbumFromPrinterActivity.this.m_AlbumAdapter.m_ReflashThread.BuildThumbnail(thumbPath, strAlbumName, false, null);
                            }
                        }
                    default:
                }
            }
        }
    }

    public AlbumFromPrinterActivity() {
        this.m_AlbumIDList = null;
        this.m_AlbumStorageIDList = null;
        this.m_strAlbumNameList = null;
        this.m_iAlbumIndexList = null;
        this.m_AlbumListView = null;
        this.m_AlbumAdapter = null;
        this.m_iTotalThumbnail = 0;
        this.m_BackButton = null;
        this.m_TitleTextView = null;
        this.m_ProgressBar = null;
        this.m_CenterProgressBar = null;
        this.m_iScreenWidth = 0;
        this.m_iItemSize = 0;
        this.m_GetAlbumMeta = null;
        this.m_GetAlbumData = null;
        this.m_GetOneThumb = null;
        this.handler = null;
        this.m_strLastSSID = null;
        this.m_strCurrentSSID = null;
        this.m_strSecurityKey = null;
        this.m_wifiAutoConnect = null;
        this.m_ShowMSGDialog = null;
        this.m_WaitingDialog = null;
        this.m_WifiInfo = null;
        this.m_bSockedConnected = false;
        this.m_bBackState = true;
        this.m_prefAlbumLoadedMeta = null;
        this.m_HitiPPR_GetPrinterInfo = null;
        this.m_strThumbPathList = null;
        this.m_bpThumbAlbum = null;
        this.MSG = null;
        this.m_bBackIDCollage = false;
        this.m_iPathRoute = 0;
        this.m_pref = null;
        this.m_ShowPrinterList = null;
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        this.m_bOnMDNS = false;
        this.m_bThreadStop = false;
        this.m_bHaveIPandPorted = false;
        this.mbPageLeave = false;
        this.mFlowMode = FlowMode.Normal;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = getClass().getSimpleName();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0349R.layout.activity_album);
        this.LOG = new LogManager(0);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.m_iScreenWidth = dm.widthPixels;
        this.m_iItemSize = this.m_iScreenWidth / 5;
        this.m_TitleTextView = (TextView) findViewById(C0349R.id.m_TitleTextView);
        this.m_TitleTextView.setText((String) getResources().getText(C0349R.string.ALBUM_SDCARD));
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_BackButton.setOnClickListener(new C02591());
        this.m_ProgressBar = (ProgressBar) findViewById(C0349R.id.loadThumbnailProgressBar);
        this.m_ProgressBar.setVisibility(8);
        this.m_CenterProgressBar = (ProgressBar) findViewById(C0349R.id.m_CenterProgressBar);
        this.m_AlbumIDList = new ArrayList();
        this.m_AlbumStorageIDList = new ArrayList();
        this.m_strAlbumNameList = new ArrayList();
        this.m_iAlbumIndexList = new ArrayList();
        this.m_strThumbPathList = new ArrayList();
        GetBundle();
        SetMSGDialog();
        this.m_AlbumListView = (ListView) findViewById(C0349R.id.listAlbum);
        this.m_AlbumListView.setOnItemClickListener(new C02602());
        this.m_AlbumAdapter = new AlbumAdapter();
        this.handler = new GetAlbumHandler();
        CreateTempFolderForPhoto();
        this.LOG.m385i(this.TAG, "onCreate " + savedInstanceState);
        if (savedInstanceState != null) {
            String strSSID = savedInstanceState.getString(JumpBundleMessage.BUNDLE_MSG_SSID);
            if (strSSID != null) {
                this.m_strLastSSID = strSSID;
            }
        }
    }

    private void CreateTempFolderForPhoto() {
        FileUtility.CreateFolder(getExternalFilesDir(null).getAbsolutePath() + File.separator + PringoConvenientConst.PRINGO_TEMP_FOLDER);
        this.m_bpThumbAlbum = BitmapFactory.decodeStream(getResources().openRawResource(C0349R.drawable.thumb_album));
    }

    private void SetMSGDialog() {
        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        this.m_ShowMSGDialog = new ShowMSGDialog(this, false);
        this.m_ShowMSGDialog.SetDialogListener(new C07463());
        this.m_WaitingDialog = new ShowMSGDialog(this, true);
        this.m_WaitingDialog.SetDialogListener(new C07474());
    }

    protected void onStart() {
        super.onStart();
    }

    private void GetBundle() {
        this.m_pref = new JumpPreferenceKey(this);
        this.m_iPathRoute = this.m_pref.GetPathSelectedPref();
        if (this.m_iPathRoute == ControllerState.PLAY_COUNT_STATE) {
            this.m_bBackIDCollage = this.m_pref.GetStatePreference("BackID_Collage");
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.IP = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP);
            this.m_iPort = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT);
            if (this.IP != null && this.m_iPort != 0) {
                this.m_bSockedConnected = true;
            }
        }
    }

    protected void onNewIntent(Intent intent) {
        this.LOG.m386v(this.TAG, "onNewIntent " + intent);
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void onResume() {
        this.mbPageLeave = false;
        super.onResume();
        this.LOG.m385i(this.TAG, "onResume ");
        if (this.mFlowMode == FlowMode.BackMainPage) {
            finish();
            return;
        }
        NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07485());
        if (this.mNFCInfo.mPrintMode == PrintMode.NormalMain) {
            CheckWifi();
        }
    }

    private void CheckPrinterInfo() {
        this.LOG.m385i(this.TAG, "CheckPrinterInfo");
        this.m_WaitingDialog.ShowWaitingHintDialog(ThreadMode.GetInfo, getString(C0349R.string.CONNECTING));
        this.handler.SetStop(false);
        this.m_HitiPPR_GetPrinterInfo = new HitiPPR_GetPrinterInfo(this, this.IP, this.m_iPort, this.handler);
        this.m_HitiPPR_GetPrinterInfo.SetRetry(false);
        this.m_HitiPPR_GetPrinterInfo.start();
    }

    protected void onPause() {
        this.mbPageLeave = true;
        if (!(this.mFlowMode == FlowMode.BackMainPage || this.mNFCInfo.mNfcAdapter == null)) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
        if (this.mFlowMode != FlowMode.BackMainPage) {
            SaveLoadedMeta();
        }
    }

    protected void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.LOG.m385i(this.TAG, "onActivityResult: " + requestCode + " m_AlbumAdapter: " + this.m_AlbumAdapter);
        switch (requestCode) {
            case JumpInfo.REQUEST_GALLERY_FROM_PRINTER_ACTIVITY /*76*/:
                if (resultCode == 50) {
                    this.mFlowMode = FlowMode.BackMainPage;
                    setResult(50);
                }
                if (GetNowSSID().contains(this.m_strLastSSID)) {
                    this.m_bSockedConnected = true;
                }
            default:
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        if (this.m_strLastSSID != null) {
            outState.remove(JumpBundleMessage.BUNDLE_MSG_SSID);
        }
        if (this.m_strLastSSID != null) {
            outState.putString(JumpBundleMessage.BUNDLE_MSG_SSID, this.m_strLastSSID);
        }
        super.onSaveInstanceState(outState);
    }

    public void onBackButtonClicked(View v) {
        this.m_bBackState = true;
        if (this.m_iPathRoute == ControllerState.PLAY_COUNT_STATE) {
            CheckIfStoreNewWifiInfo();
        } else {
            HintClearRecordDialog(getString(C0349R.string.HINT_MSG), getString(C0349R.string.HINT_TITLE));
        }
    }

    public void onBackPressed() {
        if (this.m_ShowPrinterList == null || !this.m_bOnMDNS) {
            onBackButtonClicked(null);
        } else {
            this.m_ShowPrinterList.onBackClick();
        }
    }

    void Exit(int iResult, Intent returnIntent) {
        try {
            this.m_AlbumListView.setAdapter(null);
            this.handler.SetStop(true);
            KillThread();
            this.m_AlbumAdapter = null;
            if (this.m_wifiAutoConnect != null) {
                this.m_wifiAutoConnect.SetStop(true);
                this.m_wifiAutoConnect = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (returnIntent == null) {
            returnIntent = new Intent();
        }
        if (iResult == 76) {
            startActivityForResult(returnIntent, iResult);
            return;
        }
        setResult(iResult, returnIntent);
        finish();
    }

    private boolean KillThread() {
        if (!(this.m_AlbumAdapter == null || this.m_AlbumAdapter.m_ReflashThread == null)) {
            this.m_AlbumAdapter.m_ReflashThread = null;
        }
        if (this.m_GetOneThumb != null) {
            this.m_GetOneThumb.Stop();
            this.m_GetOneThumb = null;
        }
        if (this.m_GetAlbumData != null) {
            this.m_GetAlbumData.Stop();
            this.m_GetAlbumData = null;
        }
        if (this.m_GetAlbumMeta != null) {
            this.m_GetAlbumMeta.Stop();
            this.m_GetAlbumMeta = null;
        }
        if (this.m_ProgressBar != null) {
            this.m_ProgressBar.setVisibility(8);
        }
        return true;
    }

    void ReLoadAlbum(Socket socket) {
        this.LOG.m385i(this.TAG, "ReLoadAlbum " + this.m_AlbumAdapter);
        if (this.m_AlbumAdapter == null) {
            this.m_AlbumAdapter = new AlbumAdapter();
        }
        this.handler = new GetAlbumHandler();
        this.m_AlbumListView.setAdapter(null);
        if (KillThread()) {
            RestoreLoadedMeta(socket);
        }
    }

    void RestoreLoadedMeta(Socket socket) {
        this.LOG.m385i(this.TAG, "RestoreLoadedMeta");
        if (RestoreLoadedPref()) {
            if (this.m_HitiPPR_GetPrinterInfo != null && this.m_HitiPPR_GetPrinterInfo.IsRunning()) {
                this.m_HitiPPR_GetPrinterInfo.Stop();
            }
            while (this.m_strAlbumNameList.size() > this.m_strThumbPathList.size()) {
                this.m_strAlbumNameList.remove(this.m_strAlbumNameList.size() - 1);
                this.m_iAlbumIndexList.remove(this.m_iAlbumIndexList.size() - 1);
            }
            this.handler.sendEmptyMessage(RequestState.REQUEST_GET_ALBUM_ID_META);
            return;
        }
        this.m_WaitingDialog.StopWaitingDialog();
        this.m_WaitingDialog.ShowWaitingHintDialog(ThreadMode.AlbumMeta, getString(C0349R.string.CONNECTING));
        this.m_GetAlbumMeta = new HitiPPR_GetAlbumMeta(this, this.IP, this.m_iPort, this.handler);
        this.m_GetAlbumMeta.SetSocket(socket);
        this.m_GetAlbumMeta.start();
    }

    private boolean RestoreLoadedPref() {
        this.LOG.m385i(this.TAG, "RestoreLoadedPref");
        this.m_strAlbumNameList.clear();
        this.m_strThumbPathList.clear();
        this.m_AlbumIDList.clear();
        this.m_AlbumStorageIDList.clear();
        if (this.m_prefAlbumLoadedMeta == null) {
            this.m_prefAlbumLoadedMeta = new GlobalVariable_SaveLoadedMeta(this, null);
        }
        this.m_prefAlbumLoadedMeta.RestoreGlobalVariable();
        if (!this.m_prefAlbumLoadedMeta.IsNoData()) {
            ArrayList<Integer> m_iAlbumIdList = new ArrayList();
            ArrayList<Integer> m_iAlbumSIDList = new ArrayList();
            this.m_prefAlbumLoadedMeta.GetIdAndSIDList(m_iAlbumIdList, m_iAlbumSIDList);
            Iterator it = m_iAlbumIdList.iterator();
            while (it.hasNext()) {
                this.m_AlbumIDList.add(ByteConvertUtility.IntToByte(((Integer) it.next()).intValue()));
            }
            it = m_iAlbumSIDList.iterator();
            while (it.hasNext()) {
                this.m_AlbumStorageIDList.add(ByteConvertUtility.IntToByte(((Integer) it.next()).intValue()));
            }
            this.m_prefAlbumLoadedMeta.GetIdAndLoadedPathList(null, this.m_strThumbPathList);
            this.m_prefAlbumLoadedMeta.GetLoadedNameAndIndexList(this.m_strAlbumNameList, this.m_iAlbumIndexList);
            this.m_prefAlbumLoadedMeta.ClearAlbumMeta();
            this.m_prefAlbumLoadedMeta.ClearGlobalVariable();
        }
        if (this.m_AlbumIDList.isEmpty()) {
            return false;
        }
        return true;
    }

    private void ReverseMetaList(ArrayList<byte[]> iPhotoIdList, ArrayList<byte[]> iPhotoSIDList) {
        int i;
        ArrayList<byte[]> tmpList = new ArrayList();
        int iLen = iPhotoIdList.size();
        for (i = iLen - 1; i >= 0; i--) {
            tmpList.add(iPhotoIdList.get(i));
        }
        iPhotoIdList.clear();
        Iterator it = tmpList.iterator();
        while (it.hasNext()) {
            iPhotoIdList.add((byte[]) it.next());
        }
        tmpList.clear();
        for (i = iLen - 1; i >= 0; i--) {
            tmpList.add(iPhotoSIDList.get(i));
        }
        iPhotoSIDList.clear();
        it = tmpList.iterator();
        while (it.hasNext()) {
            iPhotoSIDList.add((byte[]) it.next());
        }
    }

    private void SaveLoadedMeta() {
        this.handler.SetStop(true);
        KillThread();
        if (this.m_prefAlbumLoadedMeta == null) {
            this.m_prefAlbumLoadedMeta = new GlobalVariable_SaveLoadedMeta(getBaseContext(), null);
        }
        if (!this.m_strAlbumNameList.isEmpty()) {
            this.m_prefAlbumLoadedMeta.RestoreGlobalVariable();
            this.m_prefAlbumLoadedMeta.ClearGlobalVariable();
            this.m_prefAlbumLoadedMeta.SetAllIdAndLoadedPathList(null, this.m_strThumbPathList);
            this.m_prefAlbumLoadedMeta.SetLoadedNameAndIndexList(this.m_strAlbumNameList, this.m_iAlbumIndexList);
            ArrayList<Integer> m_iAlbumIdList = new ArrayList();
            ArrayList<Integer> m_iAlbumSIDList = new ArrayList();
            Iterator it = this.m_AlbumIDList.iterator();
            while (it.hasNext()) {
                m_iAlbumIdList.add(Integer.valueOf(ByteConvertUtility.ByteToInt((byte[]) it.next(), 0, 4)));
            }
            it = this.m_AlbumStorageIDList.iterator();
            while (it.hasNext()) {
                m_iAlbumSIDList.add(Integer.valueOf(ByteConvertUtility.ByteToInt((byte[]) it.next(), 0, 4)));
            }
            this.m_prefAlbumLoadedMeta.SetAlbumIDandSIDList(m_iAlbumIdList, m_iAlbumSIDList);
            this.m_prefAlbumLoadedMeta.SaveGlobalVariable();
        }
    }

    private void SetAlbumData(int position) {
        int iAlbumId = ByteConvertUtility.ByteToInt((byte[]) this.m_AlbumIDList.get(position), 0, 4);
        int iAlbumStorageId = ByteConvertUtility.ByteToInt((byte[]) this.m_AlbumStorageIDList.get(position), 0, 4);
        String strAlbumName = (String) this.m_strAlbumNameList.get(this.m_iAlbumIndexList.indexOf(Integer.valueOf(position)));
        GlobalVariable_AlbumSelInfo prefAlbumInfo = new GlobalVariable_AlbumSelInfo(getBaseContext(), this.m_bBackIDCollage);
        prefAlbumInfo.ClearGlobalVariable();
        prefAlbumInfo.SetAlbumRoute(2);
        prefAlbumInfo.SaveGlobalVariable();
        prefAlbumInfo.SetAlbumId(iAlbumId);
        prefAlbumInfo.SetAlbumStorageId(iAlbumStorageId);
        prefAlbumInfo.SetAlbumName(strAlbumName);
        prefAlbumInfo.SaveGlobalVariable();
    }

    private void CheckIfStoreNewWifiInfo() {
        String strNowSSID = GetNowSSID();
        this.m_WifiInfo = new GlobalVariable_WifiAutoConnectInfo(getBaseContext());
        this.m_WifiInfo.RestoreGlobalVariable();
        String strLastSSID = this.m_WifiInfo.GetSSID();
        if (strLastSSID.length() == 0) {
            SaveNewWifiInfo(strNowSSID);
            GoToGalleyOrBack(this.m_bBackState);
        } else if (strNowSSID.contains(strLastSSID) || strNowSSID.equals("0x")) {
            GoToGalleyOrBack(this.m_bBackState);
        } else {
            CheckWifiInfoDialog(strNowSSID);
        }
    }

    private void GoToGalleyOrBack(boolean bBack) {
        if (bBack) {
            Exit(51, null);
            return;
        }
        Intent intent = new Intent(this, GalleryFromPrinterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
        bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
        intent.putExtras(bundle);
        Exit(76, intent);
    }

    private void SaveNewWifiInfo(String strNowSSID) {
        if (this.m_GetAlbumMeta != null) {
            String strNowPwd = this.m_GetAlbumMeta.GetSecurityKey();
            this.m_WifiInfo.RestoreGlobalVariable();
            this.m_WifiInfo.SetSSID(strNowSSID);
            this.m_WifiInfo.SetPassword(strNowPwd);
            this.m_WifiInfo.SaveGlobalVariable();
        }
        String strLastSSID = this.m_WifiInfo.GetSSID();
    }

    private void CheckWifi() {
        this.LOG.m385i(this.TAG, "CheckWifi");
        NetworkInfo mWifi = ((ConnectivityManager) getSystemService("connectivity")).getNetworkInfo(1);
        this.m_WifiInfo = new GlobalVariable_WifiAutoConnectInfo(this);
        this.m_WifiInfo.RestoreGlobalVariable();
        this.m_strLastSSID = this.m_strLastSSID == null ? this.m_WifiInfo.GetSSID() : this.m_strLastSSID;
        this.m_strSecurityKey = this.m_WifiInfo.GetPassword();
        this.m_strCurrentSSID = GetNowSSID();
        if (mWifi.isConnected()) {
            if (!this.m_strCurrentSSID.contains(this.m_strLastSSID)) {
                this.m_ShowMSGDialog.CreateConnectWifiHintDialog(this.m_strCurrentSSID, this.m_strLastSSID);
            } else if (this.m_bSockedConnected) {
                this.m_bSockedConnected = false;
                CheckPrinterInfo();
            } else {
                ShowPrinterListDialog();
            }
        } else if (this.m_strLastSSID.length() != 0) {
            this.m_WaitingDialog.ShowWaitingHintDialog(ThreadMode.AutoWifi, getString(C0349R.string.CONN_SEARCHING));
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
        this.handler.SetStop(true);
        this.m_ShowMSGDialog.StopMSGDialog();
        this.m_ShowMSGDialog.SetMSGListener(new C07496());
        String strTitle = getString(C0349R.string.UNABLE_TO_CONNECT_TO_PRINTER);
        this.m_ShowMSGDialog.ShowMessageDialog(getString(C0349R.string.PLEASE_SELECT_NETWORK), strTitle);
    }

    public void ShowAlertDialog(String strMessage, String strTitle) {
        this.handler.SetStop(true);
        this.m_ShowMSGDialog.SetMSGListener(new C07507());
        this.m_ShowMSGDialog.ShowSimpleMSGDialog(strMessage, strTitle);
    }

    public void HintClearRecordDialog(String strMessage, String strTitle) {
        this.m_ShowMSGDialog.SetMSGListener(new C07518());
        this.m_ShowMSGDialog.ShowMessageDialog(strMessage, strTitle);
    }

    public void CheckWifiInfoDialog(String strNowSSID) {
        if (!this.mbPageLeave) {
            String strMSG = getString(C0349R.string.MODIFY_DEFAULT_CONNECTION_1) + strNowSSID + getString(C0349R.string.MODIFY_DEFAULT_CONNECTION_2);
            this.handler.SetStop(true);
            this.m_ShowMSGDialog.SetMSGListener(new C07529(strNowSSID));
            this.m_ShowMSGDialog.ShowMessageDialog(strMSG, getString(C0349R.string.TITLE_WIFI_SETTING));
        }
    }

    private void ShowPrinterListDialog() {
        if (this.m_WaitingDialog != null) {
            this.m_WaitingDialog.StopWaitingDialog();
        }
        if (this.m_ShowPrinterList == null) {
            this.m_ShowPrinterList = new ShowPrinterList(this);
            this.m_ShowPrinterList.SetPrinterListListener(new PrinterListListener() {
                public void PrinterListFinish(String strPrinterSSID, String IP, int iPort, String strConn) {
                    AlbumFromPrinterActivity.this.LOG.m383d(AlbumFromPrinterActivity.this.TAG, "PrinterListFinish " + strPrinterSSID);
                    AlbumFromPrinterActivity.this.m_strLastSSID = AlbumFromPrinterActivity.this.GetNowSSID();
                    AlbumFromPrinterActivity.this.m_strCurrentSSID = AlbumFromPrinterActivity.this.m_strLastSSID;
                    AlbumFromPrinterActivity.this.IP = IP;
                    AlbumFromPrinterActivity.this.m_iPort = iPort;
                    AlbumFromPrinterActivity.this.m_bSockedConnected = true;
                    AlbumFromPrinterActivity.this.m_bOnMDNS = false;
                    AlbumFromPrinterActivity.this.CheckPrinterInfo();
                }

                public void IsBackStateOnMDNS(boolean bMDNS) {
                    AlbumFromPrinterActivity.this.m_bOnMDNS = bMDNS;
                }

                public void BackFinish() {
                    if (AlbumFromPrinterActivity.this.m_CenterProgressBar != null) {
                        AlbumFromPrinterActivity.this.m_CenterProgressBar.setVisibility(8);
                    }
                    AlbumFromPrinterActivity.this.m_bOnMDNS = false;
                    AlbumFromPrinterActivity.this.CheckIfStoreNewWifiInfo();
                }

                public void BackStart() {
                    if (AlbumFromPrinterActivity.this.m_CenterProgressBar != null) {
                        AlbumFromPrinterActivity.this.m_CenterProgressBar.setVisibility(0);
                    }
                }
            });
        }
        this.m_ShowMSGDialog.StopWaitingDialog();
        this.m_ShowPrinterList.Show();
    }
}
