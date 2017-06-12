package com.hiti.ui.indexview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.trace.GlobalVariable_IndexInfo;
import com.hiti.ui.drawview.garnishitem.utility.EditMeta;
import com.hiti.ui.drawview.garnishitem.utility.EditMetaUtility;
import com.hiti.ui.indexview.CellPhoto.IIndexPrint;
import com.hiti.ui.indexview.IndexType.Format;
import com.hiti.ui.indexview.IndexType.Page;
import com.hiti.ui.indexview.IndexType.Size;
import com.hiti.ui.indexview.IndexType.Source;
import com.hiti.ui.indexview.IndexType.Tag;
import com.hiti.utility.FileUtility;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.PringoConvenientConst;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import org.xmlpull.v1.XmlPullParser;

public class IndexUtility {
    private static CellsGroup GetDefaultSelectedFormat(Context context, ArrayList<String> photoPathList, ArrayList<String> photoNameList, Source source) {
        CellsGroup cellsGroup = new CellsGroup(new IndexInfo(context));
        cellsGroup.SetPathAndNameList(photoPathList, photoNameList);
        if (source == Source.Preference) {
            GlobalVariable_IndexInfo indexInfo = new GlobalVariable_IndexInfo(context);
            indexInfo.RestoreGlobalVariable();
            IndexType indexType = SetIndexType(indexInfo.GetSelectedFormat(), indexInfo.GetSelectedSize(), indexInfo.GetSelectedTag());
            cellsGroup.SetIndexType(indexType);
            cellsGroup.SelectFormat(indexType.GetFormat(), indexType.GetSize(), indexType.GetTag());
        }
        return cellsGroup;
    }

    private static IndexType SetIndexType(int format, int size, int tag) {
        IndexType indexType = new IndexType();
        indexType.SetFormat(format);
        indexType.SetSize(size);
        indexType.SetTag(tag);
        return indexType;
    }

    private static IndexType SetIndexType(Format format, Size size, Tag tag) {
        IndexType indexType = new IndexType();
        indexType.SetFormat(format);
        indexType.SetSize(size);
        indexType.SetTag(tag);
        return indexType;
    }

    public static CellsGroup GetIndexFormat(Context context, Intent data, Source formatSource) {
        ArrayList<String> photoPathList;
        EditMetaUtility editMetaUtility = new EditMetaUtility(context);
        int sourceRoute = EditMetaUtility.GetSrcRoute(context);
        EditMeta photoData = editMetaUtility.GetEditMeta(sourceRoute);
        if (sourceRoute == 1) {
            photoPathList = photoData.GetMobilePathList();
        } else {
            photoPathList = photoData.GetThumbPathList();
        }
        ArrayList<String> photoNameList = sourceRoute == 1 ? null : photoData.GetPhotoNameList();
        Log.d("GetIndexFormat", "photoNameList: " + photoNameList);
        CellsGroup cellsGroup = GetDefaultSelectedFormat(context, photoPathList, photoNameList, formatSource);
        if (formatSource == Source.Bundle && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                IndexType indexType = SetIndexType(Format.valueOf(bundle.getString(JumpBundleMessage.BUNDLE_MSG_INDEX_FORMAT, Format.PaperV_PhotoH.toString())), Size.valueOf(bundle.getString(JumpBundleMessage.BUNDLE_MSG_INDEX_SIZE, Size.middle.toString())), Tag.valueOf(bundle.getString(JumpBundleMessage.BUNDLE_MSG_INDEX_TAG, Tag.None.toString())));
                cellsGroup.SelectFormat(indexType.GetFormat(), indexType.GetSize(), indexType.GetTag());
                cellsGroup.SetIndexType(indexType);
            }
        }
        return cellsGroup;
    }

    public static float SetViewScale(Context context, CellsGroup cellsGroup) {
        float viewScale;
        Page type = cellsGroup.Type();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float viewScaleH = ((float) cellsGroup.PageWidth(Page.Real)) / (((float) dm.widthPixels) * 0.7777778f);
        float viewScaleV = ((float) cellsGroup.PageHeight(Page.Real)) / (((float) dm.heightPixels) * 0.7777778f);
        if (viewScaleH > viewScaleV) {
            viewScale = viewScaleH;
        } else {
            viewScale = viewScaleV;
        }
        cellsGroup.SetViewScale(viewScale);
        cellsGroup.SetPageType(type);
        return viewScale;
    }

    public static void SetGridViewParams(CellsGroup cellsGroup, GridView mImageGridView, RelativeLayout relativeLayout) {
        int mUiMarginLeft = cellsGroup.Left(Page.UI);
        int mUiMarginTop = cellsGroup.Top(Page.UI);
        int mUiPageWidth = cellsGroup.PageWidth(Page.UI) + (mUiMarginLeft * 2);
        int mUiPageHeight = cellsGroup.PageHeight(Page.UI) + mUiMarginTop;
        relativeLayout.getLayoutParams().width = mUiPageWidth;
        relativeLayout.getLayoutParams().height = mUiPageHeight;
        LayoutParams params = new LayoutParams(mUiPageWidth, -2);
        params.setMargins(mUiMarginLeft, mUiMarginTop, mUiMarginLeft, mUiMarginTop);
        mImageGridView.setLayoutParams(params);
        mImageGridView.setNumColumns(cellsGroup.Column());
        mImageGridView.setHorizontalSpacing(cellsGroup.SpaceH(Page.UI));
        mImageGridView.setVerticalSpacing(cellsGroup.SpaceV(Page.UI));
    }

    public static int MakeOnePagePhoto(Context context, CellsGroup cellsGroup) {
        Page type = cellsGroup.Type();
        int nowPage = cellsGroup.GetNowPage();
        int bgWidth = cellsGroup.PageWidth(type);
        int bgHeight = cellsGroup.PageHeight(type);
        int pages = cellsGroup.Pages();
        int cells = cellsGroup.CellsInPage();
        BitmapMonitorResult bgBmr = BitmapMonitor.CreateBitmap(bgWidth, bgHeight, Config.ARGB_8888);
        if (!bgBmr.IsSuccess()) {
            return bgBmr.GetResult();
        }
        Canvas bgCanvas = new Canvas(bgBmr.GetBitmap());
        bgCanvas.drawColor(-1);
        int first = nowPage * cells;
        int last = nowPage + 1 < pages ? first + cells : cellsGroup.Sum();
        for (int id = first; id < last; id++) {
            IndexCell cell = SetCellInfo(cellsGroup, id);
            BitmapMonitorResult cellBmr = CreateCellPhoto(context, cell, type, cellsGroup.GetDefaultErrorPhoto());
            if (!cellBmr.IsSuccess()) {
                return cellBmr.GetResult();
            }
            Canvas cellCanvas = new Canvas(cellBmr.GetBitmap());
            if (cell.Tag() != Tag.None) {
                DrawText(cellCanvas, cell, cellsGroup);
            }
            DrawOnCanvas(bgCanvas, cell, cellBmr.GetBitmap(), cellsGroup);
        }
        cellsGroup.SetOnePagePhoto(bgBmr.GetBitmap());
        return 0;
    }

    public static IndexCell SetCellInfo(CellsGroup cellsGroup, int id) {
        Page type = cellsGroup.Type();
        int pages = cellsGroup.Pages();
        int page = cellsGroup.GetNowPage();
        int cells = cellsGroup.CellsInPage();
        int lastCells = cellsGroup.Sum() % cells;
        int column = cellsGroup.Column();
        Tag tag = cellsGroup.GetIndexType().GetTag();
        if (lastCells != 0 && page + 1 == pages && lastCells < column) {
            column = lastCells;
        }
        IndexCell cell = new IndexCell(id);
        cell.SetType(type);
        cell.SetLocation(cells, column);
        cell.SetPath(cellsGroup.Path(id));
        if (cellsGroup.GetPhotoNameList().isEmpty()) {
            cell.SetName(FileUtility.GetFileName(cell.Path()));
        } else {
            cell.SetName((String) cellsGroup.GetPhotoNameList().get(id));
        }
        cell.SetTag(tag);
        cell.SetTextSize(cellsGroup.TextSize(type));
        cell.SetLength(cellsGroup.Width(type), cellsGroup.Height(type), type);
        cellsGroup.putCell(cell);
        return cell;
    }

    private static void DrawOnCanvas(Canvas canvas, IndexCell cell, Bitmap bitmap, CellsGroup cellsGroup) {
        Page type = cellsGroup.Type();
        canvas.drawBitmap(bitmap, (float) cell.Left(type, cellsGroup), (float) cell.Top(type, cellsGroup), null);
        bitmap.recycle();
    }

    public static void DrawText(Canvas canvas, IndexCell cell, CellsGroup cellsGroup) {
        float textX;
        float textY;
        Page type = cell.Type();
        Tag tag = cell.Tag();
        if (type == Page.UI) {
            textX = tag == Tag.Number ? 3.0f : (float) (cell.Width(type) / 2);
            textY = (float) cellsGroup.Height(type);
        } else {
            textX = (float) cell.Left(type, cellsGroup);
            if (tag == Tag.FileName) {
                textX = (float) (cell.Width(type) / 2);
            }
            if (tag == Tag.Number) {
                textX = 5.0f;
            }
            textY = (float) cellsGroup.Height(type);
        }
        Paint paint = new Paint();
        paint.setColor(-1);
        paint.setTextSize((float) cell.TextSize());
        textY -= paint.getFontMetrics().bottom;
        if (tag == Tag.FileName) {
            paint.setTextAlign(Align.CENTER);
        }
        String text = XmlPullParser.NO_NAMESPACE;
        if (tag == Tag.Number) {
            text = String.format("%03d", new Object[]{Integer.valueOf(cell.ID() + 1)});
        } else if (tag == Tag.FileName) {
            text = cell.Name();
        }
        canvas.drawText(text, textX, textY, paint);
    }

    public static boolean IsNeedRotate(Context context, String photoPath, int cellWidth, int cellHeight) {
        boolean z;
        boolean bRet = BitmapMonitor.IsVertical(context, Uri.parse("file://" + photoPath));
        if (cellWidth < cellHeight) {
            z = true;
        } else {
            z = false;
        }
        if (z == bRet) {
            return false;
        }
        return true;
    }

    public static Pair<Integer, Integer> ResizePhotoSide(Context context, Uri uri, int cellWidth, int cellHeight, boolean bIsneedRotate) {
        Pair<Integer, Integer> pair = BitmapMonitor.GetPhotoSides(context, uri);
        int photoWidth = ((Integer) pair.first).intValue();
        int photoHeight = ((Integer) pair.second).intValue();
        if (bIsneedRotate) {
            int temp = photoWidth;
            photoWidth = photoHeight;
            photoHeight = temp;
        }
        float ratio = Math.max(((float) photoWidth) / ((float) cellWidth), ((float) photoHeight) / ((float) cellHeight));
        return Pair.create(Integer.valueOf((int) (((float) photoWidth) * ratio)), Integer.valueOf((int) (((float) photoHeight) * ratio)));
    }

    public static BitmapMonitorResult CreateCellPhoto(Context context, IndexCell cell, Page type, int defaultCellPath) {
        String path = cell.Path();
        int cellWidth = cell.Width(type);
        int cellHeight = cell.Height(type);
        int iLimitWidth = cellWidth;
        int iLimitHeight = cellHeight;
        BitmapMonitorResult bmr;
        try {
            Uri uri = Uri.parse("file://" + path);
            boolean bNeedRotated = IsNeedRotate(context, path, iLimitWidth, iLimitHeight);
            if (bNeedRotated) {
                int tmp = iLimitHeight;
                iLimitHeight = iLimitWidth;
                iLimitWidth = tmp;
            }
            bmr = BitmapMonitor.CreateCroppedBitmapNew(context, uri, iLimitWidth, iLimitHeight);
            if (bmr.GetResult() != 0) {
                return GetDefaultBitmap(context, defaultCellPath, cellWidth, cellHeight);
            }
            int iCropWidth = bmr.GetBitmap().getWidth();
            int iCropHeight = bmr.GetBitmap().getHeight();
            if (iCropWidth > iLimitWidth || iCropHeight > iLimitHeight) {
                bmr = CropByCanvas(bmr.GetBitmap(), iLimitWidth, iLimitHeight);
            }
            if (bNeedRotated) {
                return RotatePhoto(bmr, iLimitWidth, iLimitHeight);
            }
            return bmr;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                return GetDefaultBitmap(context, defaultCellPath, cellWidth, cellHeight);
            } catch (IOException e1) {
                e1.printStackTrace();
                bmr = new BitmapMonitorResult();
                bmr.SetResult(97);
                return bmr;
            }
        }
    }

    public static BitmapMonitorResult GetDefaultBitmap(Context context, int defaultCellPath, int width, int height) throws IOException {
        InputStream is = context.getResources().openRawResource(defaultCellPath);
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(is, true);
        if (bmr.IsSuccess()) {
            bmr = BitmapMonitor.CreateScaledBitmap(bmr.GetBitmap(), width, height, true);
        }
        is.close();
        return bmr;
    }

    private static BitmapMonitorResult CropByCanvas(Bitmap photoBitmap, int limitWidth, int limitHeight) {
        int photoWidth = photoBitmap.getWidth();
        int photoHeight = photoBitmap.getHeight();
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(limitWidth, limitHeight, Config.ARGB_8888);
        new Canvas(bmr.GetBitmap()).drawBitmap(photoBitmap, (float) ((-(photoWidth - limitWidth)) / 2), (float) ((-(photoHeight - limitHeight)) / 2), null);
        return bmr;
    }

    private static BitmapMonitorResult RotatePhoto(BitmapMonitorResult sourceBmr, int width, int height) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        bmr.SetBitmap(Bitmap.createBitmap(height, width, Config.ARGB_8888));
        bmr.SetResult(0);
        Canvas canvas = new Canvas(bmr.GetBitmap());
        Matrix matrix = new Matrix();
        matrix.setRotate(-90.0f, (float) (width / 2), (float) (height / 2));
        matrix.postTranslate((float) ((height / 2) - (width / 2)), (float) ((width / 2) - (height / 2)));
        canvas.drawBitmap(sourceBmr.GetBitmap(), matrix, null);
        return bmr;
    }

    public static void CreateOnePagePhoto(Context context, CellsGroup cellsGroup, int page, Page type, IIndexPrint listener) {
        if (cellsGroup != null) {
            cellsGroup.SetPageType(type);
            cellsGroup.SetNowPage(page);
            new CellPhoto(context, listener).execute(new CellsGroup[]{cellsGroup});
        }
    }

    public static ArrayList<String> SaveThumbnail(CellsGroup cellsGroup, String folder) {
        ArrayList<IndexCell> cellsList = new ArrayList();
        for (Entry<Integer, IndexCell> item : cellsGroup.GetCellsMap().entrySet()) {
            cellsList.add(cellsGroup.getCell(((Integer) item.getKey()).intValue()));
        }
        if (cellsList.size() <= 0) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList();
        FileUtility.CreateFolder(folder);
        Iterator it = cellsList.iterator();
        while (it.hasNext()) {
            BitmapMonitorResult bmr = ((IndexCell) it.next()).GetPhoto(Page.UI);
            if (bmr.IsSuccess()) {
                StringBuilder fileName = new StringBuilder(folder);
                fileName.append("Index_");
                fileName.append(MobileInfo.GetHmsSStamp());
                fileName.append(PringoConvenientConst.PRINBIZ_PHOTO_EXT_JPG);
                if (FileUtility.SaveBitmap(fileName.toString(), bmr.GetBitmap(), CompressFormat.JPEG)) {
                    arrayList.add(fileName.toString());
                }
            }
        }
        return arrayList;
    }

    public static void SavePhotoFile(Context context, CellsGroup cellsGroup, String folderPath) {
        String photoPath = FileUtility.GetSDAppRootPath(context) + File.separator + folderPath + File.separator + FileUtility.GetNewNameWithExt(PringoConvenientConst.PRINBIZ_PHOTO_EXT_JPG, folderPath);
        Bitmap bitmap = cellsGroup.OnePagePhoto(cellsGroup.GetNowPage());
        FileUtility.SaveBitmap(photoPath, bitmap, CompressFormat.JPEG);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        cellsGroup.SetOnePagePhotoPath(photoPath);
    }

    public static void SaveBundle(Bundle bundle, CellsGroup cellsGroup) {
        bundle.remove(JumpBundleMessage.BUNDLE_MSG_INDEX_FORMAT);
        bundle.remove(JumpBundleMessage.BUNDLE_MSG_INDEX_SIZE);
        bundle.remove(JumpBundleMessage.BUNDLE_MSG_INDEX_TAG);
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_INDEX_FORMAT, cellsGroup.GetIndexType().GetFormat().toString());
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_INDEX_SIZE, cellsGroup.GetIndexType().GetSize().toString());
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_INDEX_TAG, cellsGroup.GetIndexType().GetTag().toString());
    }
}
