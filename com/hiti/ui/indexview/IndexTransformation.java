package com.hiti.ui.indexview;

import android.graphics.Bitmap;
import android.util.Log;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.ui.indexview.IndexType.Page;
import com.squareup.picasso.Transformation;

public class IndexTransformation implements Transformation {
    CellsGroup cellsGroup;
    IndexCell mCell;

    public IndexTransformation(CellsGroup cellsGroup, IndexCell cell) {
        this.mCell = null;
        this.cellsGroup = null;
        this.mCell = cell;
        this.cellsGroup = cellsGroup;
    }

    public Bitmap transform(Bitmap source) {
        Log.d("IndexTransformation", "cell id:" + this.mCell.ID() + "_" + (this.cellsGroup.getCell(this.mCell.ID()) != null ? "exist" : "null"));
        if (this.cellsGroup.getCell(this.mCell.ID()) == null) {
            BitmapMonitorResult bmr = new BitmapMonitorResult();
            bmr.SetBitmap(source);
            bmr.SetResult(0);
            this.mCell.SetPhoto(bmr, Page.UI);
            this.cellsGroup.putCell(this.mCell);
            Log.d("IndexTransformation", "add cell: " + this.mCell.ID());
        }
        return source;
    }

    public String key() {
        return "square()";
    }
}
