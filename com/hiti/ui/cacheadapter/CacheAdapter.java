package com.hiti.ui.cacheadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.ui.cacheadapter.viewholder.BaseViewHolder;
import com.hiti.ui.cacheadapter.viewholder.BaseViewHolder.Type;

public abstract class CacheAdapter extends BaseAdapter {
    public static final int CATCH_BITMAP_SIZE = 32;
    public static final int PENDDING_SIZE = 32;
    private AdapterView<?> m_AdapterView;
    private CacheBmp m_CacheBmp;
    private Context m_Context;
    protected LayoutInflater m_Inflater;
    private PenddingGroup<BaseViewHolder> m_PenddingGroup;
    private ReflashThumbnail m_ReflashThread;
    private boolean m_boCancel;
    private Bitmap m_defaultPhtot;

    /* renamed from: com.hiti.ui.cacheadapter.CacheAdapter.1 */
    class C04231 implements OnItemClickListener {
        C04231() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            CacheAdapter.this.ClickItem(arg1);
        }
    }

    class ReflashThumbnail extends AsyncTask<Object, String, Bitmap> {
        private BaseViewHolder m_holder;
        public int m_iID;
        private int m_iResult;

        public ReflashThumbnail(BaseViewHolder holder) {
            this.m_holder = holder;
            this.m_iID = holder.m_iID;
            this.m_iResult = 0;
        }

        protected Bitmap doInBackground(Object... params) {
            Bitmap bmp = null;
            if (!isCancelled()) {
                try {
                    if (CacheAdapter.this.m_boCancel || this.m_iID < 0 || CacheAdapter.this.getCount() <= this.m_iID || CacheAdapter.this.getCount() == 0) {
                        return null;
                    }
                    BitmapMonitorResult bmr = CacheAdapter.this.LoadThumbnail(this.m_iID);
                    if (bmr == null) {
                        return null;
                    }
                    if (bmr.IsSuccess()) {
                        bmp = bmr.GetBitmap();
                    } else {
                        this.m_iResult = bmr.GetResult();
                        return null;
                    }
                } catch (Exception e) {
                    Log.i("doInBackground", "outOfMemory");
                    this.m_iResult = 97;
                    e.printStackTrace();
                }
            }
            return bmp;
        }

        public void onProgressUpdate(String... progress) {
        }

        protected void onPostExecute(Bitmap result) {
            if (!CacheAdapter.this.m_boCancel) {
                if (this.m_iResult != 0) {
                    CacheAdapter.this.LoadThumbnailFail(this.m_iResult);
                    return;
                }
                if (result != null) {
                    if (this.m_iID == this.m_holder.m_iID || result == CacheAdapter.this.m_defaultPhtot) {
                        CacheAdapter.this.LoadThumbnailSuccess(this.m_holder, result);
                        if (!result.isRecycled()) {
                            CacheAdapter.this.m_CacheBmp.AddCache(this.m_iID, result);
                            CacheAdapter.this.m_PenddingGroup.RemovePendding(this.m_holder);
                        }
                    } else {
                        result.recycle();
                    }
                }
                if (CacheAdapter.this.m_PenddingGroup.HavePendding()) {
                    CacheAdapter.this.m_ReflashThread = new ReflashThumbnail((BaseViewHolder) CacheAdapter.this.m_PenddingGroup.GetFirstPenddingViewHolder());
                    CacheAdapter.this.m_ReflashThread.execute(new Object[0]);
                    return;
                }
                Log.e("onPostExecute", "m_PenddingGroup < 0");
            }
        }
    }

    public abstract void GetCachePhoto(BaseViewHolder baseViewHolder, Bitmap bitmap);

    public abstract int GetListSize();

    public abstract View InitItem(int i, View view, ViewGroup viewGroup);

    public abstract BitmapMonitorResult LoadThumbnail(int i);

    public abstract void LoadThumbnailFail(int i);

    public abstract void LoadThumbnailSuccess(BaseViewHolder baseViewHolder, Bitmap bitmap);

    public abstract void OnClickItem(int i);

    public abstract void ReflashBackground(BaseViewHolder baseViewHolder);

    public abstract void ReflashCheckState(BaseViewHolder baseViewHolder);

    public CacheAdapter(Context context, AdapterView<?> adapterView, int iCacheSize, int iPenddingSize) {
        this.m_AdapterView = null;
        this.m_Inflater = null;
        this.m_Context = null;
        this.m_ReflashThread = null;
        this.m_PenddingGroup = null;
        this.m_CacheBmp = null;
        this.m_boCancel = false;
        this.m_defaultPhtot = null;
        this.m_Context = context;
        this.m_Inflater = (LayoutInflater) this.m_Context.getSystemService("layout_inflater");
        if (iCacheSize <= 0 || iPenddingSize <= 0) {
            iCacheSize = PENDDING_SIZE;
            iPenddingSize = PENDDING_SIZE;
        }
        this.m_CacheBmp = new CacheBmp(iCacheSize);
        this.m_PenddingGroup = new PenddingGroup(iPenddingSize);
        SetAdapterView(adapterView);
    }

    public void SetDefaultThumbnail(Bitmap bmp) {
        this.m_defaultPhtot = bmp;
    }

    private void SetAdapterView(AdapterView<?> adapterView) {
        this.m_AdapterView = adapterView;
        this.m_AdapterView.setOnItemClickListener(new C04231());
    }

    public int getCount() {
        return GetListSize();
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View itemView, ViewGroup parent) {
        View convertView = InitItem(position, itemView, parent);
        BaseViewHolder holder = (BaseViewHolder) convertView.getTag();
        holder.m_iID = position;
        ReflashBackground(holder);
        ReflashCheckState(holder);
        try {
            if (this.m_CacheBmp.IsCache(holder.m_iID)) {
                GetCachePhoto(holder, this.m_CacheBmp.GetCache(holder.m_iID));
            } else {
                GetCachePhoto(holder, null);
                if (holder.mType == Type.Thumbnail) {
                    PenddingReflash(holder);
                } else {
                    PenddingGlide(holder);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public void PenddingReflash(BaseViewHolder holder) {
        if (!this.m_boCancel) {
            this.m_PenddingGroup.AddPendding(holder);
            if (this.m_ReflashThread == null || this.m_ReflashThread.getStatus() == Status.FINISHED) {
                this.m_ReflashThread = new ReflashThumbnail((BaseViewHolder) this.m_PenddingGroup.GetFirstPenddingViewHolder());
                this.m_ReflashThread.execute(new Object[0]);
            }
        }
    }

    public void PenddingGlide(BaseViewHolder holder) {
    }

    private void ClickItem(View v) {
        ClickItem(((BaseViewHolder) v.getTag()).m_iID);
        ReflashViewScope();
    }

    private void ClickItem(int iID) {
        OnClickItem(iID);
    }

    public void ReflashViewScope() {
        int i;
        for (i = 0; i < this.m_AdapterView.getChildCount(); i++) {
            ReflashCheckState((BaseViewHolder) this.m_AdapterView.getChildAt(i).getTag());
        }
        for (i = 0; i < this.m_AdapterView.getChildCount(); i++) {
            ReflashBackground((BaseViewHolder) this.m_AdapterView.getChildAt(i).getTag());
        }
    }

    public Bitmap GetCurrentBmp(BaseViewHolder holder) {
        if (holder != null) {
            Drawable drawable = holder.m_HolderImageView.getDrawable();
            int iID = holder.m_iID;
            if (drawable instanceof BitmapDrawable) {
                Bitmap RecycleBitmap = ((BitmapDrawable) drawable).getBitmap();
                if (!(RecycleBitmap == null || this.m_CacheBmp.IsCache(iID))) {
                    Log.e("Get Current Bmp", "GetCurrentBmp");
                    return RecycleBitmap;
                }
            }
        }
        return null;
    }

    public void ClearCache() {
        Log.i("CacheAdapter", "ClearCache");
        this.m_CacheBmp.ClearCache();
        this.m_PenddingGroup.ClearPendding();
    }

    public void ClearCache(int iID) {
        Log.i("CacheAdapter", "ClearCache " + iID);
        this.m_CacheBmp.ClearCache(iID);
    }

    public void StopReflashThumbnail() {
        if (!(this.m_ReflashThread == null || this.m_ReflashThread.getStatus() == Status.FINISHED)) {
            this.m_ReflashThread.cancel(true);
            this.m_boCancel = true;
        }
        this.m_ReflashThread = null;
    }

    public void AddCatch(int id, Bitmap bmp) {
        this.m_CacheBmp.AddCache(id, bmp);
    }

    public Bitmap GetCatch(int id) {
        return this.m_CacheBmp.IsCache(id) ? this.m_CacheBmp.GetCache(id) : null;
    }
}
