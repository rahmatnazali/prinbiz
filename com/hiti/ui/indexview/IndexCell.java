package com.hiti.ui.indexview;

import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.ui.indexview.IndexType.Page;
import com.hiti.ui.indexview.IndexType.Tag;
import java.util.LinkedHashMap;
import java.util.Map;

public class IndexCell {
    private Map<Page, Integer> Height;
    private Map<Page, BitmapMonitorResult> Photo;
    private Map<Page, Integer> Width;
    private int mID;
    private int mLocationX;
    private int mLocationY;
    private String mPhotoName;
    private String mPhotoPath;
    private Tag mTag;
    private Page mType;
    private int textSize;

    public IndexCell(int id) {
        this.Photo = null;
        this.Width = null;
        this.Height = null;
        this.mID = 0;
        this.mPhotoPath = null;
        this.mPhotoName = null;
        this.mLocationX = 0;
        this.mLocationY = 0;
        this.mTag = Tag.None;
        this.textSize = 0;
        this.mType = Page.UI;
        this.mID = id;
        this.Photo = new LinkedHashMap();
        this.Width = new LinkedHashMap();
        this.Height = new LinkedHashMap();
    }

    public void SetName(String name) {
        this.mPhotoName = name;
    }

    public void SetPath(String path) {
        this.mPhotoPath = path;
    }

    public void SetType(Page type) {
        this.mType = type;
    }

    public void SetLocation(int cells, int column) {
        this.mLocationX = (this.mID % cells) % column;
        this.mLocationY = (this.mID % cells) / column;
    }

    public void SetPhoto(BitmapMonitorResult bmr, Page type) {
        this.Photo.put(type, bmr);
    }

    public void SetLength(int width, int height, Page type) {
        this.Width.put(type, Integer.valueOf(width));
        this.Height.put(type, Integer.valueOf(height));
    }

    public void SetTag(Tag tag) {
        this.mTag = tag;
    }

    public void SetTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int ID() {
        return this.mID;
    }

    public Page Type() {
        return this.mType;
    }

    public Tag Tag() {
        return this.mTag;
    }

    public int Left(Page type, CellsGroup cellsGroup) {
        return GetLeft(type, cellsGroup);
    }

    public int Top(Page type, CellsGroup cellsGroup) {
        return GetTop(type, cellsGroup);
    }

    public String Path() {
        return this.mPhotoPath;
    }

    public String Name() {
        return this.mPhotoName;
    }

    public int Width(Page type) {
        return ((Integer) this.Width.get(type)).intValue();
    }

    public int Height(Page type) {
        return ((Integer) this.Height.get(type)).intValue();
    }

    public BitmapMonitorResult GetPhoto(Page type) {
        return (BitmapMonitorResult) this.Photo.get(type);
    }

    public int TextSize() {
        return this.textSize;
    }

    private int GetLeft(Page type, CellsGroup cellsGroup) {
        int left = cellsGroup.Left(type);
        int width = cellsGroup.Width(type);
        return (this.mLocationX * (cellsGroup.SpaceH(type) + width)) + left;
    }

    private int GetTop(Page type, CellsGroup cellsGroup) {
        int top = cellsGroup.Top(type);
        int height = cellsGroup.Height(type);
        return (this.mLocationY * (cellsGroup.SpaceV(type) + height)) + top;
    }
}
