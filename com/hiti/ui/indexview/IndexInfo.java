package com.hiti.ui.indexview;

import android.content.Context;
import android.graphics.Bitmap;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.hititest_prinfanlib.C0232R;
import com.hiti.ui.indexview.IndexType.Format;
import com.hiti.ui.indexview.IndexType.Page;
import com.hiti.ui.indexview.IndexType.Size;
import com.hiti.ui.indexview.IndexType.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.net.ftp.FTPClient;

public class IndexInfo {
    private int mCellHeight;
    private int mCellWidth;
    private int mColumnNumber;
    Context mContext;
    private int mDefaultErrorPath;
    private int mHorizontalSpace;
    private Map<Integer, IndexCell> mIndexCellsMap;
    private Page mIndexType;
    private int mMarginLeft;
    private int mMarginTop;
    private int mNowPage;
    private ArrayList<Bitmap> mOutputPhotoList;
    private int mPageHeight;
    private int mPageWidth;
    private ArrayList<String> mPhotoNameList;
    private ArrayList<String> mPhotoPathList;
    private int mRowNumber;
    private int mTextSize;
    private int mVerticalSpace;
    private float mViewScale;

    /* renamed from: com.hiti.ui.indexview.IndexInfo.1 */
    static /* synthetic */ class C04411 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$ui$indexview$IndexType$Format;
        static final /* synthetic */ int[] $SwitchMap$com$hiti$ui$indexview$IndexType$Size;

        static {
            $SwitchMap$com$hiti$ui$indexview$IndexType$Format = new int[Format.values().length];
            try {
                $SwitchMap$com$hiti$ui$indexview$IndexType$Format[Format.PaperV_PhotoH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$ui$indexview$IndexType$Format[Format.PaperH_PhotoV.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$ui$indexview$IndexType$Format[Format.PaperV_PhotoV.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$ui$indexview$IndexType$Format[Format.PaperH_PhotoH.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $SwitchMap$com$hiti$ui$indexview$IndexType$Size = new int[Size.values().length];
            try {
                $SwitchMap$com$hiti$ui$indexview$IndexType$Size[Size.large.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$ui$indexview$IndexType$Size[Size.middle.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$ui$indexview$IndexType$Size[Size.small.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public IndexInfo(Context context) {
        this.mContext = null;
        this.mPageWidth = 0;
        this.mPageHeight = 0;
        this.mCellWidth = 0;
        this.mCellHeight = 0;
        this.mMarginLeft = 0;
        this.mMarginTop = 0;
        this.mHorizontalSpace = 0;
        this.mVerticalSpace = 0;
        this.mTextSize = 0;
        this.mColumnNumber = 0;
        this.mRowNumber = 0;
        this.mViewScale = 0.0f;
        this.mIndexType = Page.UI;
        this.mDefaultErrorPath = 0;
        this.mNowPage = 0;
        this.mPhotoPathList = null;
        this.mPhotoNameList = null;
        this.mIndexCellsMap = null;
        this.mOutputPhotoList = null;
        this.mContext = context;
        this.mPhotoPathList = new ArrayList();
        this.mPhotoNameList = new ArrayList();
        this.mIndexCellsMap = new HashMap();
        this.mOutputPhotoList = new ArrayList();
    }

    public void SelectFormat(Format format, Size size, Tag tag) {
        switch (C04411.$SwitchMap$com$hiti$ui$indexview$IndexType$Format[format.ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                this.mPageWidth = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_page_width));
                this.mPageHeight = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_page_height));
                switch (C04411.$SwitchMap$com$hiti$ui$indexview$IndexType$Size[size.ordinal()]) {
                    case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                        this.mColumnNumber = 2;
                        this.mCellWidth = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_two_column_cell_width));
                        this.mCellHeight = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_two_column_cell_height));
                        this.mMarginLeft = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_two_column_margin_Left));
                        this.mMarginTop = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_two_column_margin_Top));
                        this.mHorizontalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_two_column_horizontal_space));
                        this.mVerticalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_two_column_vertical_space));
                        this.mTextSize = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_two_column_text_size));
                        break;
                    case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                        this.mColumnNumber = 3;
                        this.mCellWidth = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_three_column_cell_width));
                        this.mCellHeight = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_three_column_cell_height));
                        this.mMarginLeft = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_three_column_margin_Left));
                        this.mMarginTop = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_three_column_margin_Top));
                        this.mHorizontalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_three_column_horizontal_space));
                        this.mVerticalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_three_column_vertical_space));
                        this.mTextSize = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_three_column_text_size));
                        break;
                    case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                        this.mColumnNumber = 4;
                        this.mCellWidth = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_four_column_cell_width));
                        this.mCellHeight = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_four_column_cell_height));
                        this.mMarginLeft = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_four_column_margin_Left));
                        this.mMarginTop = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_four_column_margin_Top));
                        this.mHorizontalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_four_column_horizontal_space));
                        this.mVerticalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_four_column_vertical_space));
                        this.mTextSize = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_four_column_text_size));
                        break;
                    default:
                        break;
                }
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                this.mPageWidth = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_page_width));
                this.mPageHeight = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_page_height));
                switch (C04411.$SwitchMap$com$hiti$ui$indexview$IndexType$Size[size.ordinal()]) {
                    case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                        this.mColumnNumber = 5;
                        this.mCellWidth = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_two_row_cell_width));
                        this.mCellHeight = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_two_row_cell_height));
                        this.mMarginLeft = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_two_row_margin_Left));
                        this.mMarginTop = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_two_row_margin_Top));
                        this.mHorizontalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_two_row_horizontal_space));
                        this.mVerticalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_two_row_vertical_space));
                        this.mTextSize = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_two_row_text_size));
                        break;
                    case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                        this.mColumnNumber = 7;
                        this.mCellWidth = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_three_row_cell_width));
                        this.mCellHeight = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_three_row_cell_height));
                        this.mMarginLeft = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_three_row_margin_Left));
                        this.mMarginTop = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_three_row_margin_Top));
                        this.mHorizontalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_three_row_horizontal_space));
                        this.mVerticalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_three_row_vertical_space));
                        this.mTextSize = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_three_row_text_size));
                        break;
                    case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                        this.mColumnNumber = 9;
                        this.mCellWidth = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_four_row_cell_width));
                        this.mCellHeight = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_four_row_cell_height));
                        this.mMarginLeft = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_four_row_margin_Left));
                        this.mMarginTop = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_four_row_margin_Top));
                        this.mHorizontalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_four_row_horizontal_space));
                        this.mVerticalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_four_row_vertical_space));
                        this.mTextSize = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_three_row_text_size));
                        break;
                    default:
                        break;
                }
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                this.mPageWidth = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_page_width));
                this.mPageHeight = Integer.parseInt(this.mContext.getString(C0232R.string.vertical_page_height));
                switch (C04411.$SwitchMap$com$hiti$ui$indexview$IndexType$Size[size.ordinal()]) {
                    case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                        this.mColumnNumber = 3;
                        this.mCellWidth = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_small_cell_width));
                        this.mCellHeight = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_small_cell_height));
                        this.mMarginLeft = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_small_margin_Left));
                        this.mMarginTop = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_small_margin_Top));
                        this.mHorizontalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_small_horizontal_space));
                        this.mVerticalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_small_vertical_space));
                        this.mTextSize = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_small_text_size));
                        break;
                    case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                        this.mColumnNumber = 4;
                        this.mCellWidth = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_middle_cell_width));
                        this.mCellHeight = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_middle_cell_height));
                        this.mMarginLeft = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_middle_margin_Left));
                        this.mMarginTop = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_middle_margin_Top));
                        this.mHorizontalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_middle_horizontal_space));
                        this.mVerticalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_middle_vertical_space));
                        this.mTextSize = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_middle_text_size));
                        break;
                    case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                        this.mColumnNumber = 5;
                        this.mCellWidth = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_large_cell_width));
                        this.mCellHeight = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_large_cell_height));
                        this.mMarginLeft = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_large_margin_Left));
                        this.mMarginTop = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_large_margin_Top));
                        this.mHorizontalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_large_horizontal_space));
                        this.mVerticalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_large_vertical_space));
                        this.mTextSize = Integer.parseInt(this.mContext.getString(C0232R.string.paper_v_cell_v_large_text_size));
                        break;
                    default:
                        break;
                }
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                this.mPageWidth = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_page_width));
                this.mPageHeight = Integer.parseInt(this.mContext.getString(C0232R.string.horizontal_page_height));
                switch (C04411.$SwitchMap$com$hiti$ui$indexview$IndexType$Size[size.ordinal()]) {
                    case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                        this.mColumnNumber = 3;
                        this.mCellWidth = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_small_cell_width));
                        this.mCellHeight = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_small_cell_height));
                        this.mMarginLeft = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_small_margin_Left));
                        this.mMarginTop = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_small_margin_Top));
                        this.mHorizontalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_small_horizontal_space));
                        this.mVerticalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_small_vertical_space));
                        this.mTextSize = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_small_text_size));
                        break;
                    case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                        this.mColumnNumber = 4;
                        this.mCellWidth = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_middle_cell_width));
                        this.mCellHeight = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_middle_cell_height));
                        this.mMarginLeft = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_middle_margin_Left));
                        this.mMarginTop = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_middle_margin_Top));
                        this.mHorizontalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_middle_horizontal_space));
                        this.mVerticalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_middle_vertical_space));
                        this.mTextSize = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_middle_text_size));
                        break;
                    case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                        this.mColumnNumber = 5;
                        this.mCellWidth = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_large_cell_width));
                        this.mCellHeight = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_large_cell_height));
                        this.mMarginLeft = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_large_margin_Left));
                        this.mMarginTop = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_large_margin_Top));
                        this.mHorizontalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_large_horizontal_space));
                        this.mVerticalSpace = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_large_vertical_space));
                        this.mTextSize = Integer.parseInt(this.mContext.getString(C0232R.string.paper_h_cell_h_large_text_size));
                        break;
                    default:
                        break;
                }
        }
        this.mRowNumber = this.mPageHeight / (this.mCellHeight + this.mVerticalSpace);
    }

    public void SetPathAndNameList(ArrayList<String> photoPathList, ArrayList<String> photoNameList) {
        Iterator it;
        if (photoPathList != null) {
            this.mPhotoPathList.clear();
        }
        if (photoNameList != null) {
            this.mPhotoNameList.clear();
        }
        if (photoPathList != null) {
            it = photoPathList.iterator();
            while (it.hasNext()) {
                this.mPhotoPathList.add((String) it.next());
            }
        }
        if (photoNameList != null) {
            it = photoNameList.iterator();
            while (it.hasNext()) {
                this.mPhotoNameList.add((String) it.next());
            }
        }
    }

    public ArrayList<String> GetPhotoList() {
        return this.mPhotoPathList;
    }

    public ArrayList<String> GetNameList() {
        return this.mPhotoNameList;
    }

    public int GetTotalPhotoNumber() {
        return this.mPhotoPathList.size();
    }

    public int GetCellsInPage() {
        return this.mColumnNumber * this.mRowNumber;
    }

    public int GetColumnNumber() {
        return this.mColumnNumber;
    }

    public void SetViewScale(float scale) {
        this.mViewScale = scale;
    }

    public void SetIndexType(Page indexType) {
        this.mIndexType = indexType;
    }

    public Page GetIndexType() {
        return this.mIndexType;
    }

    public int GetPageWidth(Page type) {
        if (type == Page.Real) {
            return this.mPageWidth;
        }
        return this.mViewScale > 0.0f ? (int) (((float) this.mPageWidth) / this.mViewScale) : 0;
    }

    public int GetPageHeight(Page type) {
        if (type == Page.Real) {
            return this.mPageHeight;
        }
        return this.mViewScale > 0.0f ? (int) (((float) this.mPageHeight) / this.mViewScale) : 0;
    }

    public int GetCellWidth(Page type) {
        if (type == Page.Real) {
            return this.mCellWidth;
        }
        return this.mViewScale > 0.0f ? (int) (((float) this.mCellWidth) / this.mViewScale) : 0;
    }

    public int GetCellHeight(Page type) {
        if (type == Page.Real) {
            return this.mCellHeight;
        }
        return this.mViewScale > 0.0f ? (int) (((float) this.mCellHeight) / this.mViewScale) : 0;
    }

    public int GetMarginLeft(Page type) {
        if (type == Page.Real) {
            return this.mMarginLeft;
        }
        return this.mViewScale > 0.0f ? (int) (((float) this.mMarginLeft) / this.mViewScale) : 0;
    }

    public int GetMarginTop(Page type) {
        if (type == Page.Real) {
            return this.mMarginTop;
        }
        return this.mViewScale > 0.0f ? (int) (((float) this.mMarginTop) / this.mViewScale) : 0;
    }

    public int GetHorizontalSpace(Page type) {
        if (type == Page.Real) {
            return this.mHorizontalSpace;
        }
        return this.mViewScale > 0.0f ? (int) (((float) this.mHorizontalSpace) / this.mViewScale) : 0;
    }

    public int GetVerticalSpace(Page type) {
        if (type == Page.Real) {
            return this.mVerticalSpace;
        }
        return this.mViewScale > 0.0f ? (int) (((float) this.mVerticalSpace) / this.mViewScale) : 0;
    }

    public void AddCell(IndexCell indexCell) {
        this.mIndexCellsMap.put(Integer.valueOf(indexCell.ID()), indexCell);
    }

    public IndexCell GetCell(int id) {
        return (IndexCell) this.mIndexCellsMap.get(Integer.valueOf(id));
    }

    public int GetLoadedCellsCount() {
        return this.mIndexCellsMap.size();
    }

    public void SetOnePagePhoto(Bitmap bitmap) {
        this.mOutputPhotoList.add(bitmap);
    }

    public Bitmap GetOnePagePhoto(int i) {
        return i < GetPagePhotoNumber() ? (Bitmap) this.mOutputPhotoList.get(i) : null;
    }

    public int GetPagePhotoNumber() {
        return this.mOutputPhotoList.size();
    }

    public void SetDefaultErrorPath(int defaultErrorPath) {
        this.mDefaultErrorPath = defaultErrorPath;
    }

    public int GetDefaultErrorPath() {
        return this.mDefaultErrorPath;
    }

    public void SetNowPage(int page) {
        this.mNowPage = page;
    }

    public int GetNowPage() {
        return this.mNowPage;
    }

    public int GetTextSize(Page type) {
        if (type == Page.Real) {
            return this.mTextSize;
        }
        return this.mViewScale > 0.0f ? (int) (((float) this.mTextSize) / this.mViewScale) : 0;
    }
}
