package com.hiti.multiphoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore.Images.Thumbnails;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.ui.cacheadapter.CacheAdapter;
import com.hiti.ui.cacheadapter.viewholder.BaseViewHolder;
import com.hiti.ui.cacheadapter.viewholder.BorderViewHolder;
import com.hiti.ui.horizontallistview.HorizontalListView;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MultiContainer {
    public static final int CHECK_SELECTED_STATE = -2;
    private static final int HORIZONTAL_CACHE_BITMAP_SIZE = 8;
    public static final int INITIAL_SELECTED_STATE = -1;
    private static final int PENDDING_SIZE = 32;
    BorderAdapter m_BorderAdapter;
    HorizontalListView m_BorderHorizontalListView;
    int m_BorderViewHorizontalSpacing;
    ContainerInterface m_ContainerListener;
    Context m_Context;
    ArrayList<String> m_MultiPhotoPathList;
    ArrayList<Long> m_MultiThumbnailIDList;
    MultiSelectInfo m_MultiUploadInfo;
    Bitmap m_bpDefaultErrorPicture;
    int m_iBorderItemSizeHeight;
    int m_iBorderItemSizeWidth;
    int m_iItemSize;
    ArrayList<String> m_strBorderThumbnailPathList;

    public interface ContainerInterface {
        void InitialDone();

        void RemoveImageItem(long j);

        BorderViewHolder SetBorderHolder(View view, BorderViewHolder borderViewHolder);

        View SetBorderItemView(LayoutInflater layoutInflater);

        HorizontalListView SetContainerListView();

        InputStream SetDefaultErrorPhoto();

        int SetIdeaSize();
    }

    public class BorderAdapter extends CacheAdapter {

        /* renamed from: com.hiti.multiphoto.MultiContainer.BorderAdapter.1 */
        class C02331 implements OnClickListener {
            C02331() {
            }

            public void onClick(View v) {
                MultiContainer.this.RemoveItem(v.getId());
            }
        }

        public BorderAdapter(Context context, AdapterView<?> adapterView, int iCacheSize, int iPenddingSize) {
            super(context, adapterView, iCacheSize, iPenddingSize);
        }

        public View InitItem(int position, View itemView, ViewGroup parent) {
            BorderViewHolder holder = new BorderViewHolder();
            if (itemView == null) {
                itemView = MultiContainer.this.m_ContainerListener.SetBorderItemView(this.m_Inflater);
                holder = MultiContainer.this.m_ContainerListener.SetBorderHolder(itemView, holder);
                holder.m_DelButton.setVisibility(0);
                holder.m_DelButton.setId(position);
                holder.m_DelButton.setOnClickListener(new C02331());
                itemView.setTag(holder);
            } else {
                holder = (BorderViewHolder) itemView.getTag();
                holder.m_DelButton.setId(position);
            }
            holder.m_iID = position;
            holder.m_DefaultImageView.getLayoutParams().height = MultiContainer.this.m_iBorderItemSizeHeight + (MultiContainer.this.m_BorderViewHorizontalSpacing * 2);
            holder.m_DefaultImageView.getLayoutParams().width = MultiContainer.this.m_iBorderItemSizeWidth + (MultiContainer.this.m_BorderViewHorizontalSpacing * 2);
            holder.m_HolderImageView.getLayoutParams().height = MultiContainer.this.m_iBorderItemSizeHeight;
            holder.m_HolderImageView.getLayoutParams().width = MultiContainer.this.m_iBorderItemSizeWidth;
            holder.m_DelButton.getLayoutParams().height = (MultiContainer.this.m_iItemSize / 5) * 2;
            holder.m_DelButton.getLayoutParams().width = (MultiContainer.this.m_iItemSize / 5) * 2;
            holder.m_ProgressBar.setVisibility(0);
            return itemView;
        }

        public void GetCachePhoto(BaseViewHolder holder, Bitmap bmp) {
            BorderViewHolder bVH = (BorderViewHolder) holder;
            if (bmp != null) {
                bVH.m_ProgressBar.setVisibility(MultiContainer.HORIZONTAL_CACHE_BITMAP_SIZE);
            }
            bVH.m_HolderImageView.setImageBitmap(bmp);
        }

        public BitmapMonitorResult LoadThumbnail(int iID) {
            Bitmap thumbnails;
            BitmapMonitorResult bmr = new BitmapMonitorResult();
            if (new File((String) MultiContainer.this.m_MultiPhotoPathList.get(iID)).exists()) {
                thumbnails = Thumbnails.getThumbnail(MultiContainer.this.m_Context.getContentResolver(), ((Long) MultiContainer.this.m_MultiThumbnailIDList.get(iID)).longValue(), 3, null);
            } else {
                thumbnails = MultiContainer.this.m_bpDefaultErrorPicture;
            }
            bmr.SetBitmap(thumbnails);
            bmr.SetResult(0);
            return bmr;
        }

        public void OnClickItem(int iID) {
        }

        public void LoadThumbnailSuccess(BaseViewHolder holder, Bitmap bmp) {
            BorderViewHolder bVH = (BorderViewHolder) holder;
            bVH.m_ProgressBar.setVisibility(MultiContainer.HORIZONTAL_CACHE_BITMAP_SIZE);
            Bitmap recycleBmp = GetCurrentBmp(holder);
            bVH.m_HolderImageView.setImageBitmap(bmp);
            if (recycleBmp != null && recycleBmp != bmp && !recycleBmp.isRecycled()) {
                recycleBmp.recycle();
            }
        }

        public void ReflashCheckState(BaseViewHolder SelectHolder) {
        }

        public void ReflashBackground(BaseViewHolder SelectHolder) {
        }

        public int GetListSize() {
            return MultiContainer.this.m_MultiPhotoPathList.size();
        }

        public void LoadThumbnailFail(int arg0) {
        }
    }

    public MultiContainer(Context context) {
        this.m_BorderHorizontalListView = null;
        this.m_BorderViewHorizontalSpacing = 0;
        this.m_BorderAdapter = null;
        this.m_strBorderThumbnailPathList = null;
        this.m_iBorderItemSizeWidth = 0;
        this.m_iBorderItemSizeHeight = 0;
        this.m_iItemSize = 0;
        this.m_Context = null;
        this.m_bpDefaultErrorPicture = null;
        this.m_MultiPhotoPathList = null;
        this.m_MultiThumbnailIDList = null;
        this.m_MultiUploadInfo = null;
        this.m_ContainerListener = null;
        this.m_Context = context;
    }

    public void SetListener(ContainerInterface listener) {
        this.m_ContainerListener = listener;
        try {
            InputStream is = this.m_ContainerListener.SetDefaultErrorPhoto();
            this.m_bpDefaultErrorPicture = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean CreateContainer() {
        this.m_iItemSize = this.m_ContainerListener.SetIdeaSize();
        this.m_BorderViewHorizontalSpacing = 15;
        this.m_iBorderItemSizeWidth = this.m_iItemSize - (this.m_BorderViewHorizontalSpacing * 2);
        this.m_iBorderItemSizeHeight = this.m_iItemSize - (this.m_BorderViewHorizontalSpacing * 2);
        if (this.m_BorderHorizontalListView == null) {
            this.m_BorderHorizontalListView = this.m_ContainerListener.SetContainerListView();
            this.m_BorderHorizontalListView.setVisibility(0);
        }
        CleanContainer();
        RestoreMultiSeletionList();
        this.m_BorderAdapter = new BorderAdapter(this.m_Context, this.m_BorderHorizontalListView, HORIZONTAL_CACHE_BITMAP_SIZE, PENDDING_SIZE);
        this.m_BorderHorizontalListView.getLayoutParams().height = this.m_iItemSize;
        this.m_BorderHorizontalListView.setSelection(this.m_BorderAdapter.getCount() + INITIAL_SELECTED_STATE);
        this.m_BorderHorizontalListView.setAdapter(this.m_BorderAdapter);
        this.m_BorderAdapter.notifyDataSetChanged();
        return true;
    }

    void RestoreMultiSeletionList() {
        if (this.m_MultiPhotoPathList == null) {
            this.m_MultiPhotoPathList = new ArrayList();
        }
        if (this.m_MultiThumbnailIDList == null) {
            this.m_MultiThumbnailIDList = new ArrayList();
        }
        if (this.m_MultiUploadInfo == null) {
            this.m_MultiUploadInfo = new MultiSelectInfo(this.m_Context);
        }
        this.m_MultiPhotoPathList.clear();
        this.m_MultiThumbnailIDList.clear();
        this.m_MultiUploadInfo.RestorePhotoList();
        this.m_MultiUploadInfo.GetMultiPhotoList(this.m_MultiPhotoPathList, this.m_MultiThumbnailIDList);
        this.m_BorderHorizontalListView.setVisibility(0);
        this.m_ContainerListener.InitialDone();
    }

    public void SaveSelection() {
        this.m_MultiUploadInfo.SetMultiPhotoList(this.m_MultiPhotoPathList, this.m_MultiThumbnailIDList);
        this.m_MultiUploadInfo.SaveSelection();
    }

    public void CleanContainer() {
        if (this.m_BorderAdapter != null) {
            this.m_BorderHorizontalListView.setAdapter(null);
            this.m_BorderAdapter.StopReflashThumbnail();
            this.m_BorderAdapter.ClearCache();
            this.m_BorderAdapter = null;
        }
    }

    public boolean SelectPhoto(String strPath, long lID) {
        boolean IsSelect;
        if (this.m_MultiThumbnailIDList.contains(Long.valueOf(lID))) {
            this.m_MultiPhotoPathList.remove(strPath);
            this.m_MultiThumbnailIDList.remove(Long.valueOf(lID));
            this.m_BorderAdapter.ClearCache();
            IsSelect = false;
        } else {
            this.m_MultiPhotoPathList.add(strPath);
            this.m_MultiThumbnailIDList.add(Long.valueOf(lID));
            IsSelect = true;
        }
        this.m_BorderAdapter.notifyDataSetChanged();
        return IsSelect;
    }

    public void RemoveItem(String strPath) {
        if (this.m_MultiPhotoPathList.contains(strPath)) {
            RemoveItem(this.m_MultiPhotoPathList.indexOf(strPath));
        }
    }

    private void RemoveItem(int id) {
        long lID = ((Long) this.m_MultiThumbnailIDList.get(id)).longValue();
        this.m_MultiThumbnailIDList.remove(id);
        this.m_MultiPhotoPathList.remove(id);
        this.m_BorderAdapter.ClearCache();
        this.m_BorderAdapter.notifyDataSetChanged();
        this.m_ContainerListener.RemoveImageItem(lID);
    }

    public boolean SelectedContain(long lID) {
        if (this.m_MultiThumbnailIDList.contains(Long.valueOf(lID))) {
            return true;
        }
        return false;
    }
}
