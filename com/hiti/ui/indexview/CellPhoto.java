package com.hiti.ui.indexview;

import android.content.Context;
import android.os.AsyncTask;
import com.hiti.ui.cacheadapter.viewholder.BaseViewHolder;
import com.hiti.ui.indexview.IndexType.Page;

public class CellPhoto extends AsyncTask<CellsGroup, Void, CellsGroup> {
    Context mContext;
    BaseViewHolder mHolder;
    int mID;
    IIndexPrint mIIndexPrint;

    public interface IIndexPrint {
        void AfterMakeOnePage(CellsGroup cellsGroup);

        String GetPhotoPath();

        void ShowProgressBar(boolean z);
    }

    public CellPhoto(Context context, IIndexPrint indexPrint) {
        this.mContext = null;
        this.mIIndexPrint = null;
        this.mHolder = null;
        this.mID = 0;
        this.mContext = context;
        this.mIIndexPrint = indexPrint;
    }

    public void SeICellmageViewtInfo(BaseViewHolder holder, int id) {
        this.mHolder = holder;
        this.mID = id;
    }

    protected void onPreExecute() {
        if (this.mIIndexPrint != null) {
            this.mIIndexPrint.ShowProgressBar(true);
        }
    }

    protected CellsGroup doInBackground(CellsGroup... params) {
        CellsGroup cellsGroup = params[0];
        if (cellsGroup.Type() == Page.Real) {
            if (cellsGroup.GetNowPage() == -1) {
                for (int i = 0; i < cellsGroup.Pages(); i++) {
                    MakeOnePagePhoto(cellsGroup, i);
                }
            } else {
                MakeOnePagePhoto(cellsGroup, -1);
            }
        }
        return cellsGroup;
    }

    private void MakeOnePagePhoto(CellsGroup cellsGroup, int page) {
        if (page != -1) {
            cellsGroup.SetNowPage(page);
        }
        if (IndexUtility.MakeOnePagePhoto(this.mContext, cellsGroup) == 0 && this.mIIndexPrint != null) {
            IndexUtility.SavePhotoFile(this.mContext, cellsGroup, this.mIIndexPrint.GetPhotoPath());
        }
    }

    protected void onPostExecute(CellsGroup cellsGroup) {
        if (this.mIIndexPrint != null) {
            this.mIIndexPrint.ShowProgressBar(false);
        }
        if (this.mIIndexPrint != null) {
            this.mIIndexPrint.AfterMakeOnePage(cellsGroup);
        }
    }
}
