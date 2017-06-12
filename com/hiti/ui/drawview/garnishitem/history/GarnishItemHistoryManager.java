package com.hiti.ui.drawview.garnishitem.history;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.PointF;
import android.net.Uri;
import android.util.Log;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.ui.drawview.garnishitem.GarnishItem;
import com.hiti.ui.drawview.garnishitem.LayerManager.GarnishItemLayerManager;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class GarnishItemHistoryManager {
    private Context m_Context;
    private ArrayList<GarnishItemHistory> m_GarnishHistoryManager;

    public GarnishItemHistoryManager(Context context) {
        this.m_GarnishHistoryManager = null;
        this.m_Context = null;
        this.m_GarnishHistoryManager = new ArrayList();
        this.m_Context = context;
    }

    public boolean CreateAction(GarnishItem recordGarnishItem) {
        if (recordGarnishItem == null) {
            return false;
        }
        if (recordGarnishItem.GetType() == 3 || recordGarnishItem.GetType() == 6 || recordGarnishItem.GetType() == 4) {
            AddRecord(recordGarnishItem, GARNISH_ITEM_ACTION.ACTION_CREATE);
        }
        return true;
    }

    public boolean EditAction(GarnishItem recordGarnishItem) {
        if (recordGarnishItem == null) {
            return false;
        }
        if (recordGarnishItem.GetType() == 3 || recordGarnishItem.GetType() == 6 || recordGarnishItem.GetType() == 4) {
            AddRecord(recordGarnishItem, GARNISH_ITEM_ACTION.ACTION_EDIT);
        }
        return true;
    }

    public boolean LayerMoveAction(GarnishItem recordGarnishItem, boolean boUp) {
        if (recordGarnishItem == null) {
            return false;
        }
        if (recordGarnishItem.GetType() == 3 || recordGarnishItem.GetType() == 6 || recordGarnishItem.GetType() == 4) {
            if (boUp) {
                AddRecord(recordGarnishItem, GARNISH_ITEM_ACTION.ACTION_LAYER_UP);
            } else {
                AddRecord(recordGarnishItem, GARNISH_ITEM_ACTION.ACTION_LAYER_DOWN);
            }
        }
        return true;
    }

    public boolean DeleteAction(GarnishItem recordGarnishItem) {
        if (recordGarnishItem == null) {
            return false;
        }
        if (recordGarnishItem.GetType() == 3 || recordGarnishItem.GetType() == 6 || recordGarnishItem.GetType() == 4) {
            AddRecord(recordGarnishItem, GARNISH_ITEM_ACTION.ACTION_DELETE);
        }
        return true;
    }

    public boolean ReflashAction(GarnishItem recordGarnishItem) {
        if (recordGarnishItem == null) {
            return false;
        }
        if (recordGarnishItem.GetType() == 3 || recordGarnishItem.GetType() == 6 || recordGarnishItem.GetType() == 4) {
            AddRecord(recordGarnishItem, GARNISH_ITEM_ACTION.ACTION_REFLASH);
        }
        return true;
    }

    private boolean AddRecord(GarnishItem recordGarnishItem, GARNISH_ITEM_ACTION action) {
        if (action == GARNISH_ITEM_ACTION.ACTION_CREATE) {
            Log.e("AddRecord", "ACTION_CREATE");
        }
        if (action == GARNISH_ITEM_ACTION.ACTION_EDIT) {
            Log.e("AddRecord", "ACTION_EDIT");
        }
        if (action == GARNISH_ITEM_ACTION.ACTION_DELETE) {
            Log.e("AddRecord", "ACTION_DELETE");
        }
        if (action == GARNISH_ITEM_ACTION.ACTION_REFLASH) {
            Log.e("AddRecord", "ACTION_REFLASH");
        }
        if (action == GARNISH_ITEM_ACTION.ACTION_LAYER_UP) {
            Log.e("AddRecord", "ACTION_LAYER_UP");
        }
        if (action == GARNISH_ITEM_ACTION.ACTION_LAYER_DOWN) {
            Log.e("AddRecord", "ACTION_LAYER_DOWN");
        }
        this.m_GarnishHistoryManager.add(new GarnishItemHistory(GarnishItem.RecordGarnishItem(this.m_Context, recordGarnishItem), action));
        Log.e("AddRecord", String.valueOf(this.m_GarnishHistoryManager.size()));
        return true;
    }

    private GarnishItemHistory GetPreRecord() {
        if (this.m_GarnishHistoryManager.size() <= 0) {
            return null;
        }
        return GetLastGarnishItemHistory();
    }

    public void RunRecord(ArrayList<GarnishItem> garnishManager) {
        GarnishItemHistory lastGarnishItemHistory = GetPreRecord();
        if (lastGarnishItemHistory != null) {
            GarnishItem recordGarnishItem;
            if (lastGarnishItemHistory.GetAction() == GARNISH_ITEM_ACTION.ACTION_EDIT) {
                DoPreEdit(garnishManager, lastGarnishItemHistory);
                recordGarnishItem = lastGarnishItemHistory.GetGarnishItem();
                RemoveLastGarnishHistory();
                if (recordGarnishItem.GetComposeID() != -1) {
                    lastGarnishItemHistory = GetLastGarnishItemHistory(recordGarnishItem.GetID(), recordGarnishItem.GetComposeID());
                    DoPreEdit(garnishManager, lastGarnishItemHistory);
                    RemoveLastGarnishHistory(lastGarnishItemHistory);
                }
            } else if (lastGarnishItemHistory.GetAction() == GARNISH_ITEM_ACTION.ACTION_LAYER_UP) {
                DoPreLayerUp(garnishManager, lastGarnishItemHistory);
                recordGarnishItem = lastGarnishItemHistory.GetGarnishItem();
                RemoveLastGarnishHistory();
                if (recordGarnishItem.GetComposeID() != -1) {
                    lastGarnishItemHistory = GetLastGarnishItemHistory(recordGarnishItem.GetID(), recordGarnishItem.GetComposeID());
                    DoPreLayerUp(garnishManager, lastGarnishItemHistory);
                    RemoveLastGarnishHistory(lastGarnishItemHistory);
                }
            } else if (lastGarnishItemHistory.GetAction() == GARNISH_ITEM_ACTION.ACTION_LAYER_DOWN) {
                DoPreLayerDown(garnishManager, lastGarnishItemHistory);
                recordGarnishItem = lastGarnishItemHistory.GetGarnishItem();
                RemoveLastGarnishHistory();
                if (recordGarnishItem.GetComposeID() != -1) {
                    lastGarnishItemHistory = GetLastGarnishItemHistory(recordGarnishItem.GetID(), recordGarnishItem.GetComposeID());
                    DoPreLayerDown(garnishManager, lastGarnishItemHistory);
                    RemoveLastGarnishHistory(lastGarnishItemHistory);
                }
            } else if (lastGarnishItemHistory.GetAction() == GARNISH_ITEM_ACTION.ACTION_CREATE) {
                DoPreCreate(garnishManager, lastGarnishItemHistory);
                recordGarnishItem = lastGarnishItemHistory.GetGarnishItem();
                RemoveLastGarnishHistory();
                if (recordGarnishItem.GetComposeID() != -1) {
                    lastGarnishItemHistory = GetLastGarnishItemHistory(recordGarnishItem.GetID(), recordGarnishItem.GetComposeID());
                    DoPreCreate(garnishManager, lastGarnishItemHistory);
                    RemoveLastGarnishHistory(lastGarnishItemHistory);
                }
            } else if (lastGarnishItemHistory.GetAction() == GARNISH_ITEM_ACTION.ACTION_DELETE) {
                DoPreDelete(garnishManager, lastGarnishItemHistory);
                recordGarnishItem = lastGarnishItemHistory.GetGarnishItem();
                RemoveLastGarnishHistory();
                if (recordGarnishItem.GetComposeID() != -1) {
                    lastGarnishItemHistory = GetLastGarnishItemHistory(recordGarnishItem.GetID(), recordGarnishItem.GetComposeID());
                    DoPreDelete(garnishManager, lastGarnishItemHistory);
                    RemoveLastGarnishHistory(lastGarnishItemHistory);
                }
            } else if (lastGarnishItemHistory.GetAction() == GARNISH_ITEM_ACTION.ACTION_REFLASH) {
                do {
                    DoPreFlash(garnishManager, lastGarnishItemHistory);
                    RemoveLastGarnishHistory();
                    lastGarnishItemHistory = GetPreRecord();
                    if (lastGarnishItemHistory == null) {
                        return;
                    }
                } while (lastGarnishItemHistory.GetAction() == GARNISH_ITEM_ACTION.ACTION_REFLASH);
            }
        }
    }

    private void DoPreEdit(ArrayList<GarnishItem> garnishManager, GarnishItemHistory retGarnishItemHistory) {
        if (retGarnishItemHistory != null) {
            GarnishItem recordGarnishItem = retGarnishItemHistory.GetGarnishItem();
            if (recordGarnishItem != null) {
                GarnishItem garnishItem = null;
                Iterator it = garnishManager.iterator();
                while (it.hasNext()) {
                    GarnishItem searchGarnishItem = (GarnishItem) it.next();
                    if (recordGarnishItem.GetID() == searchGarnishItem.GetID()) {
                        garnishItem = searchGarnishItem;
                        break;
                    }
                }
                if (garnishItem != null) {
                    garnishItem.InitUIView(recordGarnishItem, true);
                }
            }
        }
    }

    private void DoPreCreate(ArrayList<GarnishItem> garnishManager, GarnishItemHistory retGarnishItemHistory) {
        if (retGarnishItemHistory != null) {
            GarnishItem recordGarnishItem = retGarnishItemHistory.GetGarnishItem();
            if (recordGarnishItem != null) {
                GarnishItem garnishItem = null;
                Iterator it = garnishManager.iterator();
                while (it.hasNext()) {
                    GarnishItem searchGarnishItem = (GarnishItem) it.next();
                    if (recordGarnishItem.GetID() == searchGarnishItem.GetID()) {
                        garnishItem = searchGarnishItem;
                        break;
                    }
                }
                if (garnishItem != null) {
                    garnishManager.remove(garnishItem);
                }
            }
        }
    }

    private void DoPreLayerUp(ArrayList<GarnishItem> garnishManager, GarnishItemHistory retGarnishItemHistory) {
        if (retGarnishItemHistory != null) {
            GarnishItem recordGarnishItem = retGarnishItemHistory.GetGarnishItem();
            if (recordGarnishItem != null) {
                GarnishItem garnishItem = null;
                Iterator it = garnishManager.iterator();
                while (it.hasNext()) {
                    GarnishItem searchGarnishItem = (GarnishItem) it.next();
                    if (recordGarnishItem.GetID() == searchGarnishItem.GetID()) {
                        garnishItem = searchGarnishItem;
                        break;
                    }
                }
                if (garnishItem != null) {
                    GarnishItemLayerManager.SwapGarnishLayer(garnishManager, garnishItem, false);
                }
            }
        }
    }

    private void DoPreLayerDown(ArrayList<GarnishItem> garnishManager, GarnishItemHistory retGarnishItemHistory) {
        if (retGarnishItemHistory != null) {
            GarnishItem recordGarnishItem = retGarnishItemHistory.GetGarnishItem();
            if (recordGarnishItem != null) {
                GarnishItem garnishItem = null;
                Iterator it = garnishManager.iterator();
                while (it.hasNext()) {
                    GarnishItem searchGarnishItem = (GarnishItem) it.next();
                    if (recordGarnishItem.GetID() == searchGarnishItem.GetID()) {
                        garnishItem = searchGarnishItem;
                        break;
                    }
                }
                if (garnishItem != null) {
                    GarnishItemLayerManager.SwapGarnishLayer(garnishManager, garnishItem, true);
                }
            }
        }
    }

    private GarnishItem DoPreDelete(ArrayList<GarnishItem> garnishManager, GarnishItemHistory retGarnishItemHistory) {
        IOException e;
        GarnishItem recordGarnishItem = retGarnishItemHistory.GetGarnishItem();
        if (recordGarnishItem == null) {
            GarnishItem garnishItem = null;
            return null;
        }
        String strGarnishPath = recordGarnishItem.GetGarnishPath();
        float sx = recordGarnishItem.GetStartX();
        float sy = recordGarnishItem.GetStartY();
        float x = recordGarnishItem.GetX();
        float y = recordGarnishItem.GetY();
        int iType = recordGarnishItem.GetType();
        float fViewScale = recordGarnishItem.GetViewScale();
        float fScale = recordGarnishItem.GetScale();
        float fDegree = recordGarnishItem.GetDegree();
        long lID = recordGarnishItem.GetID();
        long lCID = recordGarnishItem.GetComposeID();
        int iFromType = recordGarnishItem.GetFromType();
        String strFilter = recordGarnishItem.GetFilter();
        float fZoom = recordGarnishItem.GetZoom();
        BitmapMonitorResult bmr = null;
        Options options = new Options();
        options.inJustDecodeBounds = false;
        if (strGarnishPath != null) {
            if (iFromType == 1) {
                try {
                    bmr = BitmapMonitor.CreateBitmap(this.m_Context.getAssets().open(strGarnishPath), false);
                } catch (IOException e2) {
                    e = e2;
                    garnishItem = null;
                    e.printStackTrace();
                    return garnishItem;
                }
            } else if (iFromType == 2) {
                bmr = BitmapMonitor.CreateBitmap(new BufferedInputStream(this.m_Context.getContentResolver().openInputStream(Uri.parse("file://" + strGarnishPath))), null, options, false);
            }
            if (bmr.IsSuccess()) {
                Bitmap thumbnails = bmr.GetBitmap();
                garnishItem = new GarnishItem(this.m_Context, iType);
                try {
                    if (garnishItem.InitUIView(thumbnails, new PointF(sx, sy), fViewScale, strGarnishPath, iFromType) != 0) {
                        thumbnails.recycle();
                        return null;
                    }
                    thumbnails.recycle();
                    garnishItem.SetID(lID);
                    garnishItem.SetComposeID(lCID);
                    garnishItem.SetRotateMatrix(fDegree);
                    garnishItem.SetDegree(fDegree);
                    garnishItem.SetScaleMatrix(fScale, fScale);
                    garnishItem.SetScale(fScale);
                    garnishItem.SetTransMatrix(x, y);
                    garnishItem.SetX(x);
                    garnishItem.SetY(y);
                    garnishItem.SetZoomMatrix(fZoom, fZoom);
                    garnishItem.SetZoom(fZoom);
                    garnishItem.SetFromType(iFromType);
                    garnishItem.SetFilter(strFilter);
                    garnishManager.add(garnishItem);
                } catch (IOException e3) {
                    e = e3;
                    e.printStackTrace();
                    return garnishItem;
                }
            }
            garnishItem = null;
            return null;
        }
        garnishItem = null;
        return garnishItem;
    }

    private GarnishItem DoPreFlash(ArrayList<GarnishItem> garnishManager, GarnishItemHistory retGarnishItemHistory) {
        return DoPreDelete(garnishManager, retGarnishItemHistory);
    }

    public GarnishItemHistory GetGarnishItemHistory(int iIndex) {
        return (GarnishItemHistory) this.m_GarnishHistoryManager.get(iIndex);
    }

    public GarnishItemHistory GetLastGarnishItemHistory() {
        if (this.m_GarnishHistoryManager.size() <= 0) {
            return null;
        }
        return (GarnishItemHistory) this.m_GarnishHistoryManager.get(this.m_GarnishHistoryManager.size() - 1);
    }

    public GarnishItemHistory GetLastGarnishItemHistory(long excludelID, long lCompose) {
        if (this.m_GarnishHistoryManager.size() <= 0) {
            return null;
        }
        for (int i = this.m_GarnishHistoryManager.size() - 1; i >= 0; i--) {
            GarnishItemHistory garnishItemHistory = (GarnishItemHistory) this.m_GarnishHistoryManager.get(i);
            if (garnishItemHistory.GetGarnishItem().GetID() != excludelID && garnishItemHistory.GetGarnishItem().GetComposeID() == lCompose) {
                return (GarnishItemHistory) this.m_GarnishHistoryManager.get(i);
            }
        }
        return null;
    }

    public void RemoveLastGarnishHistory() {
        if (this.m_GarnishHistoryManager.size() > 0) {
            this.m_GarnishHistoryManager.remove(this.m_GarnishHistoryManager.size() - 1);
        }
    }

    public void RemoveLastGarnishHistory(GarnishItemHistory garnishItemHistory) {
        if (garnishItemHistory != null && this.m_GarnishHistoryManager.size() > 0) {
            this.m_GarnishHistoryManager.remove(garnishItemHistory);
        }
    }

    public GarnishItem GetNextRecord(ArrayList<GarnishItem> garnishManager) {
        if (GetLastGarnishItemHistory() == null) {
            return null;
        }
        long iID = GetLastGarnishItemHistory().GetGarnishItem().GetID();
        Iterator it = garnishManager.iterator();
        while (it.hasNext()) {
            GarnishItem garnishItem = (GarnishItem) it.next();
            if (garnishItem.GetID() == iID) {
                return garnishItem;
            }
        }
        return null;
    }

    public void SetZoom(float fZoom, float scale, float centerX, float centerY, PointF fOffsetBound) {
        for (int i = 0; i < this.m_GarnishHistoryManager.size(); i++) {
            GarnishItem garnishItem = ((GarnishItemHistory) this.m_GarnishHistoryManager.get(i)).GetGarnishItem();
            float diffX = centerX - garnishItem.GetX();
            float diffY = centerY - garnishItem.GetY();
            float mPosX = garnishItem.GetX() + (-((diffX * scale) - diffX));
            float mPosY = garnishItem.GetY() + (-((diffY * scale) - diffY));
            garnishItem.SetTransMatrix(mPosX, mPosY);
            garnishItem.SetX(mPosX);
            garnishItem.SetY(mPosY);
            garnishItem.SetZoomMatrix(fZoom, fZoom);
            garnishItem.SetZoom(fZoom);
            mPosX = garnishItem.GetX() + fOffsetBound.x;
            mPosY = garnishItem.GetY() + fOffsetBound.y;
            garnishItem.SetTransMatrix(mPosX, mPosY);
            garnishItem.SetX(mPosX);
            garnishItem.SetY(mPosY);
        }
    }

    public void SetPan(float dx, float dy, PointF fOffsetBound) {
        for (int i = 0; i < this.m_GarnishHistoryManager.size(); i++) {
            GarnishItem garnishItem = ((GarnishItemHistory) this.m_GarnishHistoryManager.get(i)).GetGarnishItem();
            float mPosX = garnishItem.GetX() + dx;
            float mPosY = garnishItem.GetY() + dy;
            garnishItem.SetTransMatrix(mPosX, mPosY);
            garnishItem.SetX(mPosX);
            garnishItem.SetY(mPosY);
            mPosX = garnishItem.GetX() + fOffsetBound.x;
            mPosY = garnishItem.GetY() + fOffsetBound.y;
            garnishItem.SetTransMatrix(mPosX, mPosY);
            garnishItem.SetX(mPosX);
            garnishItem.SetY(mPosY);
        }
    }

    public void Clear() {
        this.m_GarnishHistoryManager.clear();
    }
}
