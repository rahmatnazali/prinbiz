package com.hiti.prinbiz;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.trace.GlobalVariable_BorderPath;
import com.hiti.ui.cacheadapter.CacheBmp;
import com.hiti.ui.cacheadapter.PenddingGroup;
import com.hiti.ui.cacheadapter.viewholder.BorderViewHolder;
import com.hiti.ui.cacheadapter.viewholder.GalleryViewHolder;
import com.hiti.utility.FileUtility;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class BorderDelFragment extends Fragment {
    public static final int DEFAULT_ORG_THUMBNAIL_VIEW_SCALE = 4;
    public static final int HORIZONTAL_CACHE_BITMAP_SIZE = 8;
    public static final int PENDDING_SIZE = 32;
    AssetManager m_AssetManager;
    ImageButton m_BackImageButton;
    int m_BorderViewHorizontalSpacing;
    BorderDelMainFragmentActivity m_Context;
    private ImageAdapter m_ImageAdapter;
    private GridView m_ImageGridView;
    ImageButton m_OKImageButton;
    TextView m_PrintOutTextView;
    boolean m_bClicked;
    int m_iBorderItemSize;
    int m_iBorderItemSizeHeight;
    int m_iBorderItemSizeWidth;
    int m_iItemSizeH;
    int m_iItemSizeW;
    int m_iSelectBorder;
    private int m_iTotalThumbnail;
    GlobalVariable_BorderPath m_prefBorderPath;
    ArrayList<String> m_strBorderPathList;
    ArrayList<String> m_strSelBorderList;
    String m_strTabLabel;
    ArrayList<String> m_strVBorderShowList;

    /* renamed from: com.hiti.prinbiz.BorderDelFragment.1 */
    class C02611 implements OnClickListener {
        C02611() {
        }

        public void onClick(View v) {
            BorderDelFragment.this.m_Context.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.BorderDelFragment.2 */
    class C02622 implements OnClickListener {
        C02622() {
        }

        public void onClick(View v) {
            BorderDelFragment.this.DelBorder(BorderDelFragment.this.m_strSelBorderList, BorderDelFragment.this.m_strTabLabel);
        }
    }

    public class ImageAdapter extends BaseAdapter {
        public static final int CATCH_BITMAP_SIZE = 32;
        public static final int PENDDING_SIZE = 40;
        BorderViewHolder holder;
        private LayoutInflater mInflater;
        CacheBmp m_CacheBmp;
        PenddingGroup<BorderViewHolder> m_PenddingGroup;
        ReflashImage m_ReflashThread;
        Bitmap m_bpDefaultPicture;
        ArrayList<String> m_strEachPrintOutList;

        /* renamed from: com.hiti.prinbiz.BorderDelFragment.ImageAdapter.1 */
        class C02631 implements OnCheckedChangeListener {
            C02631() {
            }

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = buttonView.getId();
                Log.e("buttonView", String.valueOf(buttonView.getId()));
                if (BorderDelFragment.this.m_bClicked) {
                    BorderDelFragment.this.m_bClicked = false;
                    return;
                }
                String strSelpath = (String) ImageAdapter.this.m_strEachPrintOutList.get(buttonView.getId());
                if (isChecked) {
                    if (!BorderDelFragment.this.m_strSelBorderList.contains(strSelpath)) {
                        BorderDelFragment.this.m_strSelBorderList.add(strSelpath);
                    }
                    Log.e("add_path=", XmlPullParser.NO_NAMESPACE + strSelpath.substring(strSelpath.lastIndexOf("/")));
                    return;
                }
                BorderDelFragment.this.m_strSelBorderList.remove(strSelpath);
            }
        }

        /* renamed from: com.hiti.prinbiz.BorderDelFragment.ImageAdapter.2 */
        class C02642 implements OnClickListener {
            C02642() {
            }

            public void onClick(View v) {
                String strSelpath = (String) ImageAdapter.this.m_strEachPrintOutList.get(v.getId());
                BorderDelFragment.this.m_bClicked = true;
                Log.e("touch_" + v.getId(), String.valueOf(ImageAdapter.this.m_strEachPrintOutList));
                if (BorderDelFragment.this.m_strSelBorderList.contains(strSelpath)) {
                    BorderDelFragment.this.m_strSelBorderList.remove(strSelpath);
                } else {
                    BorderDelFragment.this.m_strSelBorderList.add(strSelpath);
                }
                ImageAdapter.this.notifyDataSetChanged();
            }
        }

        class ReflashImage extends AsyncTask<Object, String, Bitmap> {
            private BorderViewHolder m_holder;
            public int m_iID;

            public ReflashImage(BorderViewHolder borderViewHolder) {
                this.m_holder = borderViewHolder;
                this.m_iID = borderViewHolder.m_iID;
            }

            protected void onPreExecute() {
                this.m_holder.m_ProgressBar.setVisibility(0);
            }

            protected Bitmap doInBackground(Object... params) {
                if (isCancelled()) {
                    return null;
                }
                try {
                    String path = (String) ImageAdapter.this.m_strEachPrintOutList.get(this.m_iID);
                    if (!new File(path).exists()) {
                        return ImageAdapter.this.m_bpDefaultPicture;
                    }
                    if (FileUtility.IsFromSDCard(BorderDelFragment.this.getActivity(), path)) {
                        return BitmapMonitor.CreateBitmap(path, false).GetBitmap();
                    }
                    return ImageAdapter.this.m_bpDefaultPicture;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            protected void onPostExecute(Bitmap result) {
                if (result != null) {
                    if (this.m_iID == this.m_holder.m_iID) {
                        Drawable drawable = this.m_holder.m_HolderImageView.getDrawable();
                        if (drawable instanceof BitmapDrawable) {
                            Bitmap RecycleBitmap = ((BitmapDrawable) drawable).getBitmap();
                            if (!(RecycleBitmap == null || RecycleBitmap == ImageAdapter.this.m_bpDefaultPicture || RecycleBitmap == result || ImageAdapter.this.m_CacheBmp.IsCache(this.m_iID))) {
                                RecycleBitmap.recycle();
                            }
                        }
                        this.m_holder.m_HolderImageView.setImageBitmap(result);
                        ImageAdapter.this.m_CacheBmp.AddCache(this.m_iID, result);
                        Log.e("m_iID", String.valueOf(this.m_iID));
                        ImageAdapter.this.m_PenddingGroup.RemoveFirstPenddingViewHolder();
                    } else {
                        result.recycle();
                    }
                }
                this.m_holder.m_ProgressBar.setVisibility(BorderDelFragment.HORIZONTAL_CACHE_BITMAP_SIZE);
                if (ImageAdapter.this.m_PenddingGroup.HavePendding()) {
                    ImageAdapter.this.m_ReflashThread = new ReflashImage((BorderViewHolder) ImageAdapter.this.m_PenddingGroup.GetFirstPenddingViewHolder());
                    ImageAdapter.this.m_ReflashThread.execute(new Object[0]);
                }
            }
        }

        public ImageAdapter() {
            this.holder = null;
            this.m_bpDefaultPicture = null;
            this.m_ReflashThread = null;
            this.m_PenddingGroup = null;
            this.m_CacheBmp = null;
            this.m_strEachPrintOutList = null;
            this.mInflater = (LayoutInflater) BorderDelFragment.this.m_Context.getSystemService("layout_inflater");
            this.m_bpDefaultPicture = BitmapFactory.decodeStream(BorderDelFragment.this.m_Context.getResources().openRawResource(C0349R.drawable.thumb_photo));
            this.m_CacheBmp = new CacheBmp(CATCH_BITMAP_SIZE);
            this.m_PenddingGroup = new PenddingGroup(PENDDING_SIZE);
        }

        public void SetPathList(ArrayList<String> strEachPrintOutList) {
            this.m_strEachPrintOutList = strEachPrintOutList;
            BorderDelFragment.this.m_iTotalThumbnail = this.m_strEachPrintOutList.size();
        }

        public int getCount() {
            return BorderDelFragment.this.m_iTotalThumbnail;
        }

        public GalleryViewHolder getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                this.holder = new BorderViewHolder();
                convertView = this.mInflater.inflate(C0349R.layout.item_border_gallery, null);
                this.holder.m_DefaultImageView = (ImageView) convertView.findViewById(C0349R.id.m_DefaultImageView);
                this.holder.m_HolderImageView = (ImageView) convertView.findViewById(C0349R.id.m_BorderImageView);
                this.holder.m_ProgressBar = (ProgressBar) convertView.findViewById(C0349R.id.m_BorderProgressBar);
                this.holder.m_CheckBox = (CheckBox) convertView.findViewById(C0349R.id.m_CheckBox);
                this.holder.m_CheckBox.setOnCheckedChangeListener(new C02631());
                this.holder.m_HolderImageView.setOnClickListener(new C02642());
                this.holder.m_DefaultImageView.getLayoutParams().height = BorderDelFragment.this.m_iBorderItemSizeHeight + BorderDelFragment.this.m_BorderViewHorizontalSpacing;
                this.holder.m_DefaultImageView.getLayoutParams().width = BorderDelFragment.this.m_iItemSizeW;
                this.holder.m_HolderImageView.getLayoutParams().height = BorderDelFragment.this.m_iBorderItemSizeHeight;
                this.holder.m_HolderImageView.getLayoutParams().width = BorderDelFragment.this.m_iBorderItemSizeWidth;
                convertView.setTag(this.holder);
            } else {
                this.holder = (BorderViewHolder) convertView.getTag();
            }
            this.holder.m_iID = position;
            this.holder.m_CheckBox.setId(position);
            this.holder.m_HolderImageView.setId(position);
            if (BorderDelFragment.this.m_strSelBorderList.contains((String) this.m_strEachPrintOutList.get(position))) {
                this.holder.m_CheckBox.setChecked(true);
            } else {
                this.holder.m_CheckBox.setChecked(false);
            }
            try {
                if (this.m_CacheBmp.IsCache(this.holder.m_iID)) {
                    this.holder.m_HolderImageView.setImageBitmap(this.m_CacheBmp.GetCache(this.holder.m_iID));
                } else {
                    this.holder.m_HolderImageView.setImageBitmap(this.m_bpDefaultPicture);
                    PenddingReflash(this.holder);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        void PenddingReflash(BorderViewHolder holder) {
            this.m_PenddingGroup.AddPendding(holder);
            if (this.m_ReflashThread == null || this.m_ReflashThread.getStatus() == Status.FINISHED) {
                this.m_ReflashThread = new ReflashImage((BorderViewHolder) this.m_PenddingGroup.GetFirstPenddingViewHolder());
                this.m_ReflashThread.execute(new Object[0]);
            }
        }
    }

    public BorderDelFragment() {
        this.m_strBorderPathList = null;
        this.m_strSelBorderList = null;
        this.m_strVBorderShowList = null;
        this.m_prefBorderPath = null;
        this.m_BackImageButton = null;
        this.m_OKImageButton = null;
        this.m_PrintOutTextView = null;
        this.m_BorderViewHorizontalSpacing = 0;
        this.m_iSelectBorder = 0;
        this.m_iBorderItemSizeWidth = 0;
        this.m_iBorderItemSizeHeight = 0;
        this.m_iBorderItemSize = 0;
        this.m_AssetManager = null;
        this.m_iItemSizeW = 0;
        this.m_iTotalThumbnail = 0;
        this.m_ImageAdapter = null;
        this.m_ImageGridView = null;
        this.m_Context = null;
        this.m_strTabLabel = null;
        this.m_bClicked = false;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("onAttach", "onAttach");
        this.m_Context = (BorderDelMainFragmentActivity) activity;
    }

    private void GetPref() {
        this.m_strBorderPathList = new ArrayList();
        this.m_strVBorderShowList = new ArrayList();
        if (this.m_prefBorderPath == null) {
            this.m_prefBorderPath = new GlobalVariable_BorderPath(getActivity());
        }
        this.m_prefBorderPath.RestoreGlobalVariable();
        if (!this.m_prefBorderPath.IsNoData()) {
            this.m_prefBorderPath.GetBorderPathList(this.m_strBorderPathList);
            for (int i = 0; i < this.m_strBorderPathList.size(); i += DEFAULT_ORG_THUMBNAIL_VIEW_SCALE) {
                this.m_strVBorderShowList.add(this.m_strBorderPathList.get(i));
            }
        }
    }

    private void SetBaseView() {
        this.m_BackImageButton = (ImageButton) this.m_Context.findViewById(C0349R.id.m_BackImageButton);
        this.m_OKImageButton = (ImageButton) this.m_Context.findViewById(C0349R.id.m_OKImageButton);
        this.m_strSelBorderList = new ArrayList();
        this.m_BackImageButton.setOnClickListener(new C02611());
        this.m_OKImageButton.setOnClickListener(new C02622());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("onCreateView", "onCreateView");
        View v = inflater.inflate(C0349R.layout.fragment_borderdel, container, false);
        SetView(v);
        return v;
    }

    private void SetView(View v) {
        this.m_ImageGridView = (GridView) v.findViewById(C0349R.id.m_ImageGridView);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.m_strTabLabel = getArguments().getString("tab");
        Log.e("onActivityCreated", "m_strTabLabe=" + this.m_strTabLabel);
        GetPref();
        SetBaseView();
        CreateContainer();
    }

    synchronized boolean CreateContainer() {
        this.m_BorderViewHorizontalSpacing = 20;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int iScreenWidth = dm.widthPixels;
        int iScreenHeight = dm.heightPixels;
        this.m_iItemSizeW = (int) (((float) iScreenWidth) / 4.0f);
        this.m_iItemSizeH = (int) (((float) iScreenHeight) / 5.0f);
        this.m_iBorderItemSizeWidth = this.m_iItemSizeW - this.m_BorderViewHorizontalSpacing;
        this.m_iBorderItemSizeHeight = (iScreenHeight / 6) - this.m_BorderViewHorizontalSpacing;
        SetBorderAdapter();
        return true;
    }

    private void SetBorderAdapter() {
        if (this.m_strTabLabel != null) {
            ArrayList<String> strEachPrintOutList = new ArrayList();
            Iterator it = this.m_strVBorderShowList.iterator();
            while (it.hasNext()) {
                String path = (String) it.next();
                if (path.contains(this.m_strTabLabel)) {
                    strEachPrintOutList.add(path);
                }
            }
            if (this.m_ImageAdapter != null) {
                this.m_ImageGridView.setAdapter(null);
                this.m_ImageAdapter = null;
            }
            it = strEachPrintOutList.iterator();
            while (it.hasNext()) {
                String i = (String) it.next();
                Log.i("i_" + strEachPrintOutList.lastIndexOf(i), String.valueOf(i.substring(i.lastIndexOf("/"))));
            }
            if (strEachPrintOutList.isEmpty()) {
                this.m_ImageGridView.setVisibility(HORIZONTAL_CACHE_BITMAP_SIZE);
                return;
            }
            this.m_ImageGridView.setVisibility(0);
            this.m_ImageAdapter = new ImageAdapter();
            this.m_ImageAdapter.SetPathList(strEachPrintOutList);
            this.m_ImageGridView.setSelection(this.m_ImageAdapter.getCount() - 1);
            this.m_ImageGridView.setAdapter(this.m_ImageAdapter);
            this.m_ImageAdapter.notifyDataSetChanged();
        }
    }

    private void DelBorder(ArrayList<String> strDelBorderList, String tabPrintout) {
        ArrayList<String> strLaterDelList = new ArrayList();
        Log.e("del_m_strTabLabel", String.valueOf(tabPrintout));
        Log.e("DelBorder_sel", String.valueOf(strDelBorderList));
        if (!strDelBorderList.isEmpty() && !this.m_strVBorderShowList.isEmpty() && !this.m_strBorderPathList.isEmpty()) {
            Iterator it = strDelBorderList.iterator();
            while (it.hasNext()) {
                String vPath = (String) it.next();
                this.m_strVBorderShowList.remove(vPath);
                if (this.m_strBorderPathList.contains(vPath)) {
                    int i = this.m_strBorderPathList.indexOf(vPath);
                    for (int j = 0; j < DEFAULT_ORG_THUMBNAIL_VIEW_SCALE; j++) {
                        String oPath = (String) this.m_strBorderPathList.get(i + j);
                        if (FileUtility.FileExist(oPath)) {
                            FileUtility.DeleteFile(oPath);
                            strLaterDelList.add(oPath);
                            if (j % 2 == 1) {
                                FileUtility.DeleteALLFolder(oPath.substring(0, oPath.lastIndexOf("/")));
                            }
                        }
                    }
                }
                Iterator it2 = strLaterDelList.iterator();
                while (it2.hasNext()) {
                    this.m_strBorderPathList.remove((String) it2.next());
                }
                strLaterDelList.clear();
            }
            SetBorderAdapter();
            if (this.m_prefBorderPath == null) {
                this.m_prefBorderPath = new GlobalVariable_BorderPath(getActivity());
            }
            this.m_prefBorderPath.ClearRestorePrefBorderPath();
            this.m_prefBorderPath.ClearGlobalVariable();
            this.m_prefBorderPath.SetBorderPathList(this.m_strBorderPathList);
            this.m_prefBorderPath.SaveGlobalVariable();
        }
    }
}
