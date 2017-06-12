package com.hiti.ui.indexview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class CellTarget implements Target {
    Callback callback;
    Canvas canvas;
    CellsGroup cellsGroup;
    Context context;
    int id;

    public interface Callback {
        Long GetPhotoId(int i);

        void MakeOnPagePhotoDone(CellsGroup cellsGroup);
    }

    public CellTarget(Context context, int id, CellsGroup cellsGroup, Canvas canvas, Callback callback) {
        this.context = null;
        this.canvas = null;
        this.callback = null;
        this.id = 0;
        this.context = context;
        this.cellsGroup = cellsGroup;
        this.canvas = canvas;
        this.callback = callback;
        this.id = id;
    }

    public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
        Log.d("Get Cell", "onBitmapLoaded: " + this.id);
        GetCellBitmap(this.context, this.cellsGroup, GetBitmapResult(bitmap), this.id, this.canvas, this.callback);
    }

    public void onBitmapFailed(Drawable errorDrawable) {
        Log.d("Get Cell", "onBitmapFailed: " + this.id);
        GetCellBitmap(this.context, this.cellsGroup, GetBitmapResult(GetThumbnail(this.context, this.callback.GetPhotoId(this.id).longValue(), this.cellsGroup.GetDefaultErrorPhoto())), this.id, this.canvas, this.callback);
    }

    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }

    public static Bitmap GetThumbnail(Context context, long sourceUrlId, int defaultPhotoId) {
        Bitmap bmp = null;
        if (sourceUrlId != -1) {
            bmp = Thumbnails.getThumbnail(context.getContentResolver(), sourceUrlId, 3, null);
        }
        if (bmp == null) {
            return BitmapFactory.decodeStream(context.getResources().openRawResource(defaultPhotoId));
        }
        return bmp;
    }

    public static BitmapMonitorResult GetBitmapResult(Bitmap bitmap) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        bmr.SetBitmap(bitmap);
        bmr.SetResult(0);
        return bmr;
    }

    public static void GetBitmapForCell(Context context, CellsGroup cellsGroup, int id, Canvas canvas, Callback callback) {
        Log.d("Next GetBitmapForCell", "id: " + id);
        String path = cellsGroup.Path(id);
        int cellWidth = cellsGroup.Width(cellsGroup.Type());
        int cellHeight = cellsGroup.Height(cellsGroup.Type());
        Picasso.with(context).load(path).resize(cellWidth, cellHeight).onlyScaleDown().centerCrop().rotate(IndexUtility.IsNeedRotate(context, path, cellWidth, cellHeight) ? -90.0f : 0.0f).into(new CellTarget(context, id, cellsGroup, canvas, callback));
    }

    private static void GetCellBitmap(Context context, CellsGroup cellsGroup, BitmapMonitorResult bmr, int id, Canvas canvas, Callback callback) {
        id++;
        if (id < cellsGroup.Sum()) {
            IndexUtility.SetCellInfo(cellsGroup, id);
            GetBitmapForCell(context, cellsGroup, id, canvas, callback);
            return;
        }
        callback.MakeOnPagePhotoDone(cellsGroup);
    }
}
