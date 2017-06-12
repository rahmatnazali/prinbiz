package com.hiti.ui.indexview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IndexType {
    ArrayList<Format> mFormats;
    Map<Item, Format> mSelectedFormat;
    Map<Item, Size> mSelectedSize;
    Map<Item, Tag> mSelectedTag;
    ArrayList<Size> mSizes;
    ArrayList<Tag> mTags;

    public enum Format {
        PaperV_PhotoH,
        PaperV_PhotoV,
        PaperH_PhotoH,
        PaperH_PhotoV
    }

    enum Item {
        Format,
        Size,
        Tag
    }

    public enum Page {
        Real,
        UI
    }

    public enum Size {
        small,
        middle,
        large
    }

    public enum Source {
        Bundle,
        Preference
    }

    public enum Spinner {
        Format,
        Size
    }

    public enum Tag {
        None,
        Number,
        FileName
    }

    public IndexType() {
        this.mFormats = null;
        this.mSizes = null;
        this.mTags = null;
        this.mSelectedFormat = null;
        this.mSelectedSize = null;
        this.mSelectedTag = null;
        this.mSelectedFormat = new HashMap();
        this.mSelectedSize = new HashMap();
        this.mSelectedTag = new HashMap();
        this.mFormats = new ArrayList();
        this.mFormats.add(Format.PaperV_PhotoH);
        this.mFormats.add(Format.PaperV_PhotoV);
        this.mFormats.add(Format.PaperH_PhotoV);
        this.mFormats.add(Format.PaperH_PhotoH);
        this.mSizes = new ArrayList();
        this.mSizes.add(Size.large);
        this.mSizes.add(Size.middle);
        this.mSizes.add(Size.small);
        this.mTags = new ArrayList();
        this.mTags.add(Tag.None);
        this.mTags.add(Tag.Number);
        this.mTags.add(Tag.FileName);
    }

    public void SetFormat(int i) {
        Map map = this.mSelectedFormat;
        Item item = Item.Format;
        ArrayList arrayList = this.mFormats;
        if (i >= this.mFormats.size()) {
            i = 0;
        }
        map.put(item, arrayList.get(i));
    }

    public void SetFormat(Format format) {
        this.mSelectedFormat.put(Item.Format, format);
    }

    public void SetSize(int i) {
        Map map = this.mSelectedSize;
        Item item = Item.Size;
        ArrayList arrayList = this.mSizes;
        if (i >= this.mSizes.size()) {
            i = 0;
        }
        map.put(item, arrayList.get(i));
    }

    public void SetSize(Size size) {
        this.mSelectedSize.put(Item.Size, size);
    }

    public void SetTag(int i) {
        Map map = this.mSelectedTag;
        Item item = Item.Tag;
        ArrayList arrayList = this.mTags;
        if (i >= this.mTags.size()) {
            i = 0;
        }
        map.put(item, arrayList.get(i));
    }

    public void SetTag(Tag tag) {
        this.mSelectedTag.put(Item.Tag, tag);
    }

    public Format GetFormat() {
        return (Format) this.mSelectedFormat.get(Item.Format);
    }

    public Size GetSize() {
        return (Size) this.mSelectedSize.get(Item.Size);
    }

    public Tag GetTag() {
        return (Tag) this.mSelectedTag.get(Item.Tag);
    }

    public int FormatId() {
        return this.mFormats.indexOf(GetFormat());
    }

    public int SizeId() {
        return this.mSizes.indexOf(GetSize());
    }

    public int TagId() {
        return this.mTags.indexOf(GetTag());
    }
}
