package com.hiti.ui.indexview;

import android.graphics.Bitmap;
import com.hiti.ui.indexview.IndexType.Format;
import com.hiti.ui.indexview.IndexType.Page;
import com.hiti.ui.indexview.IndexType.Size;
import com.hiti.ui.indexview.IndexType.Tag;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CellsGroup {
    Map<Integer, IndexCell> cellsMap;
    IndexInfo mIndexInfo;
    IndexType mIndexType;
    ArrayList<String> mOnePagePhotoPath;

    public CellsGroup() {
        this.mIndexInfo = null;
        this.mOnePagePhotoPath = null;
        this.mIndexType = null;
        this.cellsMap = null;
        this.cellsMap = new LinkedHashMap();
    }

    public CellsGroup(IndexInfo indexInfo) {
        this.mIndexInfo = null;
        this.mOnePagePhotoPath = null;
        this.mIndexType = null;
        this.cellsMap = null;
        this.mIndexInfo = indexInfo;
        this.mOnePagePhotoPath = new ArrayList();
        this.cellsMap = new LinkedHashMap();
    }

    public Page Type() {
        return this.mIndexInfo.GetIndexType();
    }

    public int Sum() {
        return this.mIndexInfo.GetTotalPhotoNumber();
    }

    public int CellsInPage() {
        return this.mIndexInfo.GetCellsInPage();
    }

    public int Column() {
        return this.mIndexInfo.GetColumnNumber();
    }

    public int lastCells() {
        return Sum() % CellsInPage();
    }

    public int Pages() {
        return (lastCells() == 0 ? 0 : 1) + (Sum() / CellsInPage());
    }

    public int Left(Page type) {
        return this.mIndexInfo.GetMarginLeft(type);
    }

    public int Top(Page type) {
        return this.mIndexInfo.GetMarginTop(type);
    }

    public int SpaceH(Page type) {
        return this.mIndexInfo.GetHorizontalSpace(type);
    }

    public int SpaceV(Page type) {
        return this.mIndexInfo.GetVerticalSpace(type);
    }

    public String Path(int id) {
        return (String) this.mIndexInfo.GetPhotoList().get(id);
    }

    public int Width(Page type) {
        return this.mIndexInfo.GetCellWidth(type);
    }

    public int Height(Page type) {
        return this.mIndexInfo.GetCellHeight(type);
    }

    public int PageWidth(Page type) {
        return this.mIndexInfo.GetPageWidth(type);
    }

    public int PageHeight(Page type) {
        return this.mIndexInfo.GetPageHeight(type);
    }

    public int TextSize(Page type) {
        return this.mIndexInfo.GetTextSize(type);
    }

    public void SelectFormat(Format format, Size size, Tag tag) {
        this.mIndexInfo.SelectFormat(format, size, tag);
    }

    public void SetPathAndNameList(ArrayList<String> photoPathList, ArrayList<String> photoNameList) {
        this.mIndexInfo.SetPathAndNameList(photoPathList, photoNameList);
    }

    public ArrayList<String> GetPhotoPathList() {
        return this.mIndexInfo.GetPhotoList();
    }

    public ArrayList<String> GetPhotoNameList() {
        return this.mIndexInfo.GetNameList();
    }

    public void SetPageType(Page type) {
        this.mIndexInfo.SetIndexType(type);
    }

    public void SetViewScale(float viewScale) {
        this.mIndexInfo.SetViewScale(viewScale);
    }

    public void AddCell(IndexCell indexCell) {
        this.mIndexInfo.AddCell(indexCell);
    }

    public IndexCell GetCell(int i) {
        return this.mIndexInfo.GetCell(i);
    }

    public void SetDefaultErrorPhoto(int iRes) {
        this.mIndexInfo.SetDefaultErrorPath(iRes);
    }

    public int GetDefaultErrorPhoto() {
        return this.mIndexInfo.GetDefaultErrorPath();
    }

    public void SetOnePagePhoto(Bitmap bitmap) {
        this.mIndexInfo.SetOnePagePhoto(bitmap);
    }

    public Bitmap OnePagePhoto(int i) {
        return this.mIndexInfo.GetOnePagePhoto(i);
    }

    public void SetNowPage(int page) {
        this.mIndexInfo.SetNowPage(page);
    }

    public int GetNowPage() {
        return this.mIndexInfo.GetNowPage();
    }

    public int NowPageCells() {
        if (GetNowPage() == Pages() - 1) {
            return lastCells() == 0 ? CellsInPage() : lastCells();
        } else {
            return CellsInPage();
        }
    }

    public int First() {
        return GetNowPage() * CellsInPage();
    }

    public int Last() {
        return (GetNowPage() * CellsInPage()) + NowPageCells();
    }

    public int RealLoadedCellsCount() {
        return this.mIndexInfo.GetLoadedCellsCount();
    }

    public void SetOnePagePhotoPath(String path) {
        this.mOnePagePhotoPath.add(path);
    }

    public String OnePagePhotoPath(int page) {
        return page < this.mOnePagePhotoPath.size() ? (String) this.mOnePagePhotoPath.get(page) : null;
    }

    public void putCell(IndexCell cell) {
        this.cellsMap.put(Integer.valueOf(cell.ID()), cell);
    }

    public IndexCell getCell(int i) {
        return (IndexCell) this.cellsMap.get(Integer.valueOf(i));
    }

    public Map GetCellsMap() {
        return this.cellsMap;
    }

    public void SetIndexType(IndexType indexType) {
        this.mIndexType = indexType;
    }

    public IndexType GetIndexType() {
        return this.mIndexType;
    }
}
