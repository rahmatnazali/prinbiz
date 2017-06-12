package com.hiti.plugins.text;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.hiti.plugins.common.AbstractPageHandler;
import com.hiti.plugins.common.AbstractPluginActivity;
import com.hiti.plugins.drawer.TextDrawer;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class TextChooseFontHandler extends AbstractPageHandler {
    public static final int ACTION_SET_TEXT = 1;
    public static final int DATA_FONT_PATH = 1;
    static final int catchSize = 20;
    private String ASSET_TEXT_PATH;
    private File ExternalPath;
    ArrayList<Pair<Integer, Typeface>> FontCatch;
    private int R_ID_list_font_fakedrawer;
    private int R_ID_list_font_icon;
    private int R_ID_list_font_textdrawer;
    private int R_ID_list_font_title;
    private int R_ID_text_plugin_choosefont_listview;
    private int R_LAYOUT_list_font;
    private int R_LAYOUT_plugins_text_choosefont;
    private int R_STRING_choose_a_font;
    private BaseAdapter ba;
    private View choosefontPage;
    String currentFont;
    private String[] fonts;
    private ListView listview;
    int m_iItemSize;
    private OnItemClickListener otcl_listview;
    private String text;
    private int totalFontCount;

    /* renamed from: com.hiti.plugins.text.TextChooseFontHandler.1 */
    class C02531 extends ArrayList<Pair<Integer, Typeface>> {
        C02531() {
        }

        public synchronized boolean add(Pair<Integer, Typeface> object) {
            Log.d("Font Added To Catch", "Add " + object.first);
            if (size() > TextChooseFontHandler.catchSize) {
                remove(0);
            }
            return super.add(object);
        }

        public synchronized Pair<Integer, Typeface> remove(int index) {
            return (Pair) super.remove(index);
        }
    }

    /* renamed from: com.hiti.plugins.text.TextChooseFontHandler.2 */
    class C02552 extends BaseAdapter {
        ArrayList<Pair<View, String>> target;
        int total;
        private UpdateAsyncTask updateAsyncTask;

        /* renamed from: com.hiti.plugins.text.TextChooseFontHandler.2.1 */
        class C02541 extends ArrayList<Pair<View, String>> {
            private static final long serialVersionUID = 1;

            C02541() {
            }

            public synchronized boolean add(Pair<View, String> object) {
                if (size() > C02552.this.total) {
                    remove(0);
                }
                return super.add(object);
            }

            public synchronized Pair<View, String> remove(int index) {
                return (Pair) super.remove(index);
            }
        }

        /* renamed from: com.hiti.plugins.text.TextChooseFontHandler.2.UpdateAsyncTask */
        class UpdateAsyncTask extends AsyncTask<Integer, Void, SparseArray<Object>> {
            UpdateAsyncTask() {
            }

            protected SparseArray<Object> doInBackground(Integer... params) {
                Pair<View, String> targetpair = (Pair) C02552.this.target.remove(0);
                boolean shouldServeThisPair = true;
                Iterator it = C02552.this.target.iterator();
                while (it.hasNext()) {
                    if (((Pair) it.next()).first == targetpair.first) {
                        shouldServeThisPair = false;
                        break;
                    }
                }
                if (!shouldServeThisPair) {
                    return null;
                }
                Typeface typeface = TextDrawer.createTypefaceFromPath(TextChooseFontHandler.this.activity, (String) targetpair.second);
                SparseArray<Object> objs = new SparseArray();
                objs.put(0, typeface);
                objs.put(TextChooseFontHandler.DATA_FONT_PATH, targetpair.first);
                objs.put(2, targetpair.second);
                return objs;
            }

            protected void onPostExecute(SparseArray<Object> objs) {
                if (objs != null) {
                    Typeface typeface = (Typeface) objs.get(0);
                    View ThisTarget = (View) objs.get(TextChooseFontHandler.DATA_FONT_PATH);
                    String ThisTargetData = (String) objs.get(2);
                    boolean shouldServeThisPair = true;
                    Iterator it = C02552.this.target.iterator();
                    while (it.hasNext()) {
                        if (((Pair) it.next()).first == ThisTarget) {
                            shouldServeThisPair = false;
                            break;
                        }
                    }
                    if (shouldServeThisPair) {
                        TextChooseFontHandler.this.FontCatch.add(new Pair(Integer.valueOf(ThisTargetData.hashCode()), typeface));
                        ThisTarget.findViewById(TextChooseFontHandler.this.R_ID_list_font_fakedrawer).setVisibility(4);
                        TextDrawer textdrawer = (TextDrawer) ThisTarget.findViewById(TextChooseFontHandler.this.R_ID_list_font_textdrawer);
                        textdrawer.setFont(typeface);
                        textdrawer.setText(TextChooseFontHandler.this.text);
                        textdrawer.setVisibility(0);
                        ImageView icon = (ImageView) ThisTarget.findViewById(TextChooseFontHandler.this.R_ID_list_font_icon);
                        if (ThisTargetData.equals(TextChooseFontHandler.this.currentFont)) {
                            icon.setVisibility(0);
                            ThisTarget.setBackgroundColor(-1);
                        } else {
                            icon.setVisibility(4);
                        }
                    }
                }
                if (C02552.this.target.isEmpty()) {
                    C02552.this.updateAsyncTask = null;
                } else {
                    C02552.this.updateAsyncTask = new UpdateAsyncTask();
                    C02552.this.updateAsyncTask.execute(new Integer[0]);
                }
                super.onPostExecute(objs);
            }
        }

        C02552() {
            this.total = 50;
            this.updateAsyncTask = null;
            this.target = new C02541();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View target = convertView;
            if (target == null) {
                target = View.inflate(TextChooseFontHandler.this.activity, TextChooseFontHandler.this.R_LAYOUT_list_font, null);
            }
            TextDrawer targetTextDrawer = (TextDrawer) target.findViewById(TextChooseFontHandler.this.R_ID_list_font_textdrawer);
            TextView targetFakeDrawer = (TextView) target.findViewById(TextChooseFontHandler.this.R_ID_list_font_fakedrawer);
            targetTextDrawer.setText(" ");
            String titleName = new File(TextChooseFontHandler.this.fonts[position]).getName();
            ((TextView) target.findViewById(TextChooseFontHandler.this.R_ID_list_font_title)).setText(titleName.substring(0, titleName.lastIndexOf(46)));
            int fontID = TextChooseFontHandler.this.fonts[position].hashCode();
            Typeface foundFont = null;
            Iterator it = TextChooseFontHandler.this.FontCatch.iterator();
            while (it.hasNext()) {
                Pair<Integer, Typeface> P = (Pair) it.next();
                if (((Integer) P.first).intValue() == fontID) {
                    foundFont = P.second;
                    break;
                }
            }
            if (foundFont == null) {
                targetTextDrawer.setVisibility(4);
                targetFakeDrawer.setText(TextChooseFontHandler.this.text);
                targetFakeDrawer.setVisibility(0);
                this.target.add(new Pair(target, TextChooseFontHandler.this.fonts[position]));
                if (this.updateAsyncTask == null) {
                    this.updateAsyncTask = new UpdateAsyncTask();
                    this.updateAsyncTask.execute(new Integer[0]);
                }
            } else {
                targetTextDrawer.setVisibility(0);
                targetTextDrawer.setText(TextChooseFontHandler.this.text);
                targetTextDrawer.setFont(foundFont);
                targetFakeDrawer.setVisibility(4);
            }
            if (TextChooseFontHandler.this.fonts[position].equals(TextChooseFontHandler.this.currentFont)) {
                target.findViewById(TextChooseFontHandler.this.R_ID_list_font_icon).setVisibility(0);
                target.setBackgroundColor(-1);
            } else {
                target.findViewById(TextChooseFontHandler.this.R_ID_list_font_icon).setVisibility(4);
                target.setBackgroundColor(0);
            }
            return target;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public Object getItem(int position) {
            return null;
        }

        public int getCount() {
            return TextChooseFontHandler.this.totalFontCount;
        }
    }

    /* renamed from: com.hiti.plugins.text.TextChooseFontHandler.3 */
    class C02563 implements OnItemClickListener {
        C02563() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            TextChooseFontHandler.this.currentFont = TextChooseFontHandler.this.fonts[arg2];
            TextChooseFontHandler.this.activity.turnToPage(TextChooseFontHandler.this.activity.getCurrentPageNumber() + TextChooseFontHandler.DATA_FONT_PATH);
        }
    }

    private void GetResourceID(Context context) {
        this.R_ID_list_font_fakedrawer = ResourceSearcher.getId(context, RS_TYPE.ID, "list_font_fakedrawer");
        this.R_ID_list_font_textdrawer = ResourceSearcher.getId(context, RS_TYPE.ID, "list_font_textdrawer");
        this.R_ID_list_font_icon = ResourceSearcher.getId(context, RS_TYPE.ID, "list_font_icon");
        this.R_LAYOUT_list_font = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "list_font");
        this.R_ID_list_font_title = ResourceSearcher.getId(context, RS_TYPE.ID, "list_font_title");
        this.R_ID_text_plugin_choosefont_listview = ResourceSearcher.getId(context, RS_TYPE.ID, "text_plugin_choosefont_listview");
        this.R_STRING_choose_a_font = ResourceSearcher.getId(context, RS_TYPE.STRING, "choose_a_font");
        this.R_LAYOUT_plugins_text_choosefont = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "plugins_text_choosefont");
    }

    public SparseArray<Object> getData() {
        SparseArray<Object> data = new SparseArray(2);
        data.put(DATA_FONT_PATH, this.currentFont);
        return data;
    }

    public Object getData(int data) {
        switch (data) {
            case DATA_FONT_PATH /*1*/:
                return this.currentFont;
            default:
                return null;
        }
    }

    public void requestAction(int action, Object... params) {
        switch (action) {
            case DATA_FONT_PATH /*1*/:
                this.text = params[0];
            default:
        }
    }

    public String getTitle() {
        return this.activity.getString(this.R_STRING_choose_a_font);
    }

    public TextChooseFontHandler(AbstractPluginActivity activity, Bundle bundle) {
        super(activity);
        this.ASSET_TEXT_PATH = "FONT_PRINGO";
        this.text = XmlPullParser.NO_NAMESPACE;
        this.totalFontCount = 0;
        this.ExternalPath = new File(Environment.getExternalStorageDirectory(), this.ASSET_TEXT_PATH);
        this.m_iItemSize = 0;
        this.FontCatch = new C02531();
        this.ba = new C02552();
        this.otcl_listview = new C02563();
        GetResourceID(activity);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.m_iItemSize = dm.widthPixels / 6;
        this.choosefontPage = View.inflate(activity, this.R_LAYOUT_plugins_text_choosefont, null);
        findViews();
        initFonts();
        if (bundle != null && bundle.containsKey("font_path")) {
            this.currentFont = bundle.getString("font_path");
        }
    }

    private void initFonts() {
        int i;
        String[] items_assets = new String[0];
        try {
            items_assets = this.activity.getAssets().list(this.ASSET_TEXT_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.fonts = new String[items_assets.length];
        for (i = 0; i < items_assets.length; i += DATA_FONT_PATH) {
            this.fonts[i] = TextDrawer.font_from_assets_indicator + items_assets[i];
        }
        for (i = 0; i < catchSize; i += DATA_FONT_PATH) {
            this.FontCatch.add(new Pair(Integer.valueOf(this.fonts[i].hashCode()), TextDrawer.createTypefaceFromPath(this.activity, this.fonts[i])));
        }
        this.totalFontCount = this.fonts.length;
    }

    private void findViews() {
        this.listview = (ListView) this.choosefontPage.findViewById(this.R_ID_text_plugin_choosefont_listview);
        this.listview.setCacheColorHint(0);
        this.listview.setAdapter(this.ba);
        this.listview.setOnItemClickListener(this.otcl_listview);
    }

    public View getPage() {
        return this.choosefontPage;
    }
}
