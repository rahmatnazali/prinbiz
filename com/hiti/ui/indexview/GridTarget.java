package com.hiti.ui.indexview;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.ui.cacheadapter.viewholder.BaseViewHolder;
import com.hiti.ui.indexview.IndexType.Page;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class GridTarget implements Target {
    IndexCell cell;
    CellsGroup cellsGroup;
    BaseViewHolder holder;
    IListener mListener;
    String thumbnailPath;

    public interface IListener {
        void SetImageView(BaseViewHolder baseViewHolder, boolean z);
    }

    public GridTarget(BaseViewHolder holder, CellsGroup cellsGroup, String path, IListener listener) {
        this.holder = null;
        this.cellsGroup = null;
        this.thumbnailPath = null;
        this.cell = null;
        this.mListener = null;
        this.holder = holder;
        this.cellsGroup = cellsGroup;
        this.thumbnailPath = path;
        this.mListener = listener;
        this.cell = new IndexCell(holder.m_iID);
        this.cell.SetPath(this.thumbnailPath);
    }

    public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
        this.cell.SetPhoto(GetBitmapResult(bitmap), Page.UI);
        this.cellsGroup.putCell(this.cell);
        this.holder.m_HolderImageView.setImageBitmap(bitmap);
        this.mListener.SetImageView(this.holder, false);
    }

    public void onBitmapFailed(Drawable errorDrawable) {
        this.cell.SetPhoto(GetBitmapResult(((BitmapDrawable) errorDrawable).getBitmap()), Page.UI);
        this.cellsGroup.putCell(this.cell);
        this.holder.m_HolderImageView.setImageDrawable(errorDrawable);
        this.mListener.SetImageView(this.holder, true);
    }

    public void onPrepareLoad(Drawable placeHolderDrawable) {
        this.holder.m_HolderImageView.setImageDrawable(placeHolderDrawable);
    }

    public static BitmapMonitorResult GetBitmapResult(Bitmap bitmap) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        bmr.SetBitmap(bitmap);
        bmr.SetResult(0);
        return bmr;
    }
}
