package com.hiti.prinbiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.trace.GlobalVariable_PrintSettingInfo;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.utility.LogManager;
import com.hiti.utility.dialog.ShowMSGDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public class SettingPrintAttrActivity extends Activity {
    LogManager LOG;
    private OnClickListener ListenOK;
    String TAG;
    ArrayList<Integer> mItemIdList;
    Map<Integer, Integer> mItemSelectMap;
    NFCInfo mNFCInfo;
    ImageButton m_BackButton;
    ImageButton m_ConfirmButton;
    SettingAdapter m_SettingAdapter;
    ListView m_SettingAttrListView;
    ShowMSGDialog m_ShowMSGDialog;
    TextView m_TitleTextView;
    private int m_iRoute;
    LinkedList<Integer> m_iSettingImgList;
    private int m_iTotalSetting;
    private JumpPreferenceKey m_pref;
    GlobalVariable_PrintSettingInfo m_prefQPrintInfo;
    private String m_strPrinterModel;
    LinkedList<String> m_strSettingTitleList;

    /* renamed from: com.hiti.prinbiz.SettingPrintAttrActivity.1 */
    class C03761 implements OnItemClickListener {
        C03761() {
        }

        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingPrintAttrActivity.2 */
    class C03772 implements OnClickListener {
        C03772() {
        }

        public void onClick(View v) {
            SettingPrintAttrActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingPrintAttrActivity.3 */
    class C03783 implements OnClickListener {
        C03783() {
        }

        public void onClick(View v) {
            SettingPrintAttrActivity.this.LOG.m383d(SettingPrintAttrActivity.this.TAG, "ListenOK " + SettingPrintAttrActivity.this.mItemSelectMap);
            Iterator it = SettingPrintAttrActivity.this.mItemIdList.iterator();
            while (it.hasNext()) {
                int id = ((Integer) it.next()).intValue();
                if (SettingPrintAttrActivity.this.mItemSelectMap.containsKey(Integer.valueOf(id))) {
                    int value = ((Integer) SettingPrintAttrActivity.this.mItemSelectMap.get(Integer.valueOf(id))).intValue();
                    switch (id) {
                        case C0349R.id.setting_duplex /*2131427329*/:
                            SettingPrintAttrActivity.this.m_prefQPrintInfo.SetPrintDuplex(value);
                            break;
                        case C0349R.id.setting_layout /*2131427331*/:
                            SettingPrintAttrActivity.this.m_prefQPrintInfo.SetServerPaperType(value);
                            break;
                        case C0349R.id.setting_method /*2131427332*/:
                            SettingPrintAttrActivity.this.m_prefQPrintInfo.SetPrintMethod(value);
                            break;
                        case C0349R.id.setting_sharpen /*2131427334*/:
                            SettingPrintAttrActivity.this.m_prefQPrintInfo.SetPrintSharpen(value);
                            break;
                        case C0349R.id.setting_texture /*2131427335*/:
                            SettingPrintAttrActivity.this.m_prefQPrintInfo.SetPrintTexture(value);
                            break;
                        default:
                            break;
                    }
                }
            }
            SettingPrintAttrActivity.this.m_prefQPrintInfo.SaveGlobalVariable();
            SettingPrintAttrActivity.this.onBackPressed();
        }
    }

    private class SettingAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        int m_iSeekBarValue;

        /* renamed from: com.hiti.prinbiz.SettingPrintAttrActivity.SettingAdapter.1 */
        class C03791 implements OnClickListener {
            C03791() {
            }

            public void onClick(View v) {
                SettingPrintAttrActivity.this.m_ShowMSGDialog.ShowHODInfoDialog();
            }
        }

        /* renamed from: com.hiti.prinbiz.SettingPrintAttrActivity.SettingAdapter.2 */
        class C03802 implements OnCheckedChangeListener {
            C03802() {
            }

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getId();
                if (group.getCheckedRadioButtonId() != -1) {
                    SettingPrintAttrActivity.this.DispatchCommand(id, checkedId);
                }
            }
        }

        class OnSharpenSeekBarChangeListener implements OnSeekBarChangeListener {
            ViewHolder holder;
            View view;

            OnSharpenSeekBarChangeListener() {
                this.view = null;
                this.holder = null;
            }

            public void SetViewAndHolder(View view, ViewHolder holder) {
                this.view = view;
                this.holder = holder;
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SettingPrintAttrActivity.this.LOG.m383d(SettingPrintAttrActivity.this.TAG, "onProgressChanged " + fromUser);
                if (fromUser) {
                    SettingPrintAttrActivity.this.mItemSelectMap.put(Integer.valueOf(C0349R.id.setting_sharpen), Integer.valueOf(progress));
                }
                if (fromUser) {
                    SettingAdapter.this.m_iSeekBarValue = progress;
                }
                SettingAdapter.this.SetNumberOnSharpenBar(this.holder, this.view);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        }

        class ViewHolder {
            int id;
            RadioButton m_FirstItem;
            ImageButton m_InfoHODImageButton;
            RadioGroup m_ItemRadioGroup;
            LinearLayout m_NumberLinearLayout;
            LinearLayout m_RadioLayout;
            RadioButton m_SecondItem;
            RelativeLayout m_SharpenLayout;
            SeekBar m_SharpenSeekBar;
            RadioButton m_ThirdItem;
            TextView m_TitleTextView;

            ViewHolder() {
            }
        }

        public SettingAdapter() {
            this.m_iSeekBarValue = 0;
            this.mInflater = (LayoutInflater) SettingPrintAttrActivity.this.getSystemService("layout_inflater");
            this.m_iSeekBarValue = SettingPrintAttrActivity.this.m_prefQPrintInfo.GetPrintSharpen();
        }

        public int getCount() {
            return SettingPrintAttrActivity.this.m_iTotalSetting;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = this.mInflater.inflate(C0349R.layout.item_setting_attr, null);
                holder.m_RadioLayout = (LinearLayout) convertView.findViewById(C0349R.id.m_RadioLayout);
                holder.m_SharpenLayout = (RelativeLayout) convertView.findViewById(C0349R.id.m_SharpenLayout);
                holder.m_NumberLinearLayout = (LinearLayout) convertView.findViewById(C0349R.id.m_NumberLinearLayout);
                holder.m_TitleTextView = (TextView) convertView.findViewById(C0349R.id.m_TitleTextView);
                holder.m_ItemRadioGroup = (RadioGroup) convertView.findViewById(C0349R.id.m_ItemRadioGroup);
                holder.m_FirstItem = (RadioButton) convertView.findViewById(C0349R.id.m_FirstItem);
                holder.m_SecondItem = (RadioButton) convertView.findViewById(C0349R.id.m_SecondItem);
                holder.m_ThirdItem = (RadioButton) convertView.findViewById(C0349R.id.m_ThirdItem);
                holder.m_SharpenSeekBar = (SeekBar) convertView.findViewById(C0349R.id.m_SharpenSeekBar);
                holder.m_InfoHODImageButton = (ImageButton) convertView.findViewById(C0349R.id.m_InfoHODImageView);
                holder.m_ItemRadioGroup.setId(position);
                holder.m_FirstItem.setId(C0349R.id.setting_first);
                holder.m_SecondItem.setId(C0349R.id.setting_second);
                holder.m_ThirdItem.setId(C0349R.id.setting_third);
                holder.m_InfoHODImageButton.setVisibility(8);
                holder.m_InfoHODImageButton.setOnClickListener(new C03791());
                int width = SettingPrintAttrActivity.this.getResources().getDrawable(C0349R.drawable.scale).getIntrinsicWidth();
                holder.m_SharpenSeekBar.getLayoutParams().width = width;
                holder.m_NumberLinearLayout.getLayoutParams().width = (width * 9) / 8;
                OnSharpenSeekBarChangeListener listener = new OnSharpenSeekBarChangeListener();
                listener.SetViewAndHolder(convertView, holder);
                holder.m_SharpenSeekBar.setOnSeekBarChangeListener(listener);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.m_ItemRadioGroup.setId(((Integer) SettingPrintAttrActivity.this.mItemIdList.get(position)).intValue());
            holder.id = ((Integer) SettingPrintAttrActivity.this.mItemIdList.get(position)).intValue();
            SettingItemView(holder);
            if (holder.id == C0349R.id.setting_sharpen) {
                SetNumberOnSharpenBar(holder, convertView);
            }
            return convertView;
        }

        private void SettingItemView(ViewHolder holder) {
            SetRadioGroup(holder);
            holder.m_ItemRadioGroup.setOnCheckedChangeListener(new C03802());
        }

        private void SetRadioGroup(ViewHolder holder) {
            int itemID = holder.id;
            InitRadioGroup(holder);
            holder.m_TitleTextView.setText((CharSequence) SettingPrintAttrActivity.this.m_strSettingTitleList.get(SettingPrintAttrActivity.this.mItemIdList.indexOf(Integer.valueOf(itemID))));
            if (itemID == C0349R.id.setting_texture) {
                holder.m_FirstItem.setText(SettingPrintAttrActivity.this.getString(C0349R.string.BRIGHTFACE));
                holder.m_SecondItem.setText(SettingPrintAttrActivity.this.getString(C0349R.string.SHADOWFACE));
                if (holder.m_ItemRadioGroup.getCheckedRadioButtonId() == -1) {
                    if (SettingPrintAttrActivity.this.m_prefQPrintInfo.GetPrintTexture() == 1) {
                        holder.m_ItemRadioGroup.check(C0349R.id.setting_second);
                    } else {
                        holder.m_ItemRadioGroup.check(C0349R.id.setting_first);
                    }
                }
            } else if (itemID == C0349R.id.setting_method) {
                if (SettingPrintAttrActivity.this.m_strPrinterModel.equals(WirelessType.TYPE_P310W)) {
                    holder.m_FirstItem.setText(SettingPrintAttrActivity.this.getString(C0349R.string.QTY_NORMAL));
                    holder.m_SecondItem.setText(SettingPrintAttrActivity.this.getString(C0349R.string.QTY_HOD));
                    holder.m_InfoHODImageButton.setVisibility(0);
                } else if (SettingPrintAttrActivity.this.m_strPrinterModel.equals(WirelessType.TYPE_P750L)) {
                    holder.m_FirstItem.setText(SettingPrintAttrActivity.this.getString(C0349R.string.QTY_NORMAL));
                    holder.m_SecondItem.setText(SettingPrintAttrActivity.this.getString(C0349R.string.QTY_REFINEMENT));
                }
                if (holder.m_ItemRadioGroup.getCheckedRadioButtonId() == -1) {
                    if (SettingPrintAttrActivity.this.m_prefQPrintInfo.GetPrintMethod() == 1) {
                        holder.m_ItemRadioGroup.check(C0349R.id.setting_second);
                    } else {
                        holder.m_ItemRadioGroup.check(C0349R.id.setting_first);
                    }
                }
            } else if (itemID == C0349R.id.setting_layout) {
                if (SettingPrintAttrActivity.this.m_strPrinterModel.equals(WirelessType.TYPE_P310W)) {
                    holder.m_RadioLayout.setVisibility(8);
                } else {
                    holder.m_ThirdItem.setVisibility(0);
                    holder.m_FirstItem.setText(SettingPrintAttrActivity.this.getString(C0349R.string.PRINT_OUT_4x6));
                    if (SettingPrintAttrActivity.this.m_strPrinterModel.equals(WirelessType.TYPE_P530D)) {
                        holder.m_SecondItem.setText(SettingPrintAttrActivity.this.getString(C0349R.string.PRINT_OUT_6x6));
                    } else {
                        holder.m_SecondItem.setText(SettingPrintAttrActivity.this.getString(C0349R.string.PRINT_OUT_5x7));
                    }
                    holder.m_ThirdItem.setText(SettingPrintAttrActivity.this.getString(C0349R.string.PRINT_OUT_6x8));
                    if (holder.m_ItemRadioGroup.getCheckedRadioButtonId() == -1) {
                        int iPaperType = SettingPrintAttrActivity.this.m_prefQPrintInfo.GetServerPaperType();
                        if (iPaperType == 4) {
                            holder.m_ItemRadioGroup.check(C0349R.id.setting_third);
                        } else if (iPaperType == 3) {
                            holder.m_ItemRadioGroup.check(C0349R.id.setting_second);
                        } else if (iPaperType == 8) {
                            holder.m_ItemRadioGroup.check(C0349R.id.setting_second);
                        } else if (iPaperType == 2) {
                            holder.m_ItemRadioGroup.check(C0349R.id.setting_first);
                        } else if (iPaperType == 6) {
                            holder.m_ItemRadioGroup.check(C0349R.id.setting_first);
                        }
                    }
                }
            } else if (itemID == C0349R.id.setting_sharpen) {
                holder.m_RadioLayout.setVisibility(8);
                holder.m_SharpenLayout.setVisibility(0);
            } else if (itemID == C0349R.id.setting_duplex) {
                holder.m_TitleTextView.setVisibility(8);
                holder.m_FirstItem.setText(SettingPrintAttrActivity.this.getString(C0349R.string.PAGE_SINGLE));
                holder.m_SecondItem.setText(SettingPrintAttrActivity.this.getString(C0349R.string.PAGE_DUPLEX));
                if (holder.m_ItemRadioGroup.getCheckedRadioButtonId() == -1) {
                    if (SettingPrintAttrActivity.this.m_prefQPrintInfo.GetPrintDuplex() == 0) {
                        holder.m_ItemRadioGroup.check(C0349R.id.setting_first);
                    } else {
                        holder.m_ItemRadioGroup.check(C0349R.id.setting_second);
                    }
                }
            }
            holder.m_ItemRadioGroup.invalidate();
        }

        private void InitRadioGroup(ViewHolder holder) {
            holder.m_RadioLayout.setVisibility(0);
            holder.m_InfoHODImageButton.setVisibility(8);
            holder.m_SharpenLayout.setVisibility(8);
            holder.m_ThirdItem.setVisibility(8);
            holder.m_ItemRadioGroup.clearCheck();
        }

        private void SetNumberOnSharpenBar(ViewHolder holder, View v) {
            SettingPrintAttrActivity.this.LOG.m383d(SettingPrintAttrActivity.this.TAG, "SetNumberOnSharpenBar " + this.m_iSeekBarValue);
            holder.m_SharpenSeekBar.setProgress(this.m_iSeekBarValue);
            holder.m_NumberLinearLayout.removeAllViews();
            for (int i = 0; i <= 8; i++) {
                TextView numTextView = new TextView(v.getContext());
                numTextView.setBackgroundColor(SettingPrintAttrActivity.this.getResources().getColor(17170445));
                numTextView.setTextColor(SettingPrintAttrActivity.this.getResources().getColor(C0349R.color.SETTING_GRAY));
                if (i == this.m_iSeekBarValue) {
                    numTextView.setTextColor(SettingPrintAttrActivity.this.getResources().getColor(C0349R.color.SETTING_ORANGE));
                } else {
                    numTextView.setTextColor(SettingPrintAttrActivity.this.getResources().getColor(C0349R.color.SETTING_GRAY));
                }
                numTextView.setLayoutParams(new LayoutParams(-1, -2, 1.0f));
                numTextView.setTextSize(13.0f);
                numTextView.setPadding(5, 0, 5, 0);
                numTextView.setGravity(3);
                numTextView.setText(String.valueOf(i - 1));
                holder.m_NumberLinearLayout.addView(numTextView);
            }
            holder.m_NumberLinearLayout.invalidate();
        }
    }

    public SettingPrintAttrActivity() {
        this.m_BackButton = null;
        this.m_ConfirmButton = null;
        this.m_TitleTextView = null;
        this.m_ShowMSGDialog = null;
        this.m_prefQPrintInfo = null;
        this.m_pref = null;
        this.m_iRoute = 0;
        this.m_strPrinterModel = null;
        this.m_iSettingImgList = null;
        this.m_strSettingTitleList = null;
        this.m_SettingAdapter = null;
        this.m_SettingAttrListView = null;
        this.m_iTotalSetting = 0;
        this.mItemIdList = null;
        this.mItemSelectMap = null;
        this.mNFCInfo = null;
        this.LOG = null;
        this.TAG = getClass().getSimpleName();
        this.ListenOK = new C03783();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_setting_quick_life);
        this.LOG.m386v(this.TAG, "onCreate");
        SetView();
    }

    protected void onNewIntent(Intent intent) {
        this.LOG.m386v("onNewIntent ", XmlPullParser.NO_NAMESPACE + intent);
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void SetPref(int iRoute) {
        this.m_iRoute = iRoute;
        this.m_pref = new JumpPreferenceKey(this);
        this.m_strPrinterModel = this.m_pref.GetModelPreference();
        this.m_prefQPrintInfo = new GlobalVariable_PrintSettingInfo(this, iRoute);
        this.m_prefQPrintInfo.RestoreGlobalVariable();
    }

    private void SetView() {
        this.m_BackButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_ConfirmButton = (ImageButton) findViewById(C0349R.id.m_ConfirmButton);
        this.m_TitleTextView = (TextView) findViewById(C0349R.id.m_TitleTextView);
        this.m_ShowMSGDialog = new ShowMSGDialog(this, false);
        this.mItemSelectMap = new HashMap();
        if (this.m_iRoute == ControllerState.PLAY_PHOTO) {
            this.m_TitleTextView.setText(getString(C0349R.string.QUICKTXT));
        } else {
            this.m_TitleTextView.setText(getString(C0349R.string.LIFETEXT));
        }
        SetOnClickListen();
        this.m_iSettingImgList = new LinkedList();
        this.m_strSettingTitleList = new LinkedList();
        this.mItemIdList = new ArrayList();
        SetSettingItem();
        this.m_SettingAttrListView = (ListView) findViewById(C0349R.id.m_SettingAttrListView);
        this.m_SettingAttrListView.setCacheColorHint(getResources().getColor(17170445));
        this.m_SettingAttrListView.setOnItemClickListener(new C03761());
        if (this.m_SettingAdapter == null) {
            this.m_SettingAdapter = new SettingAdapter();
        }
        this.m_SettingAttrListView.setAdapter(this.m_SettingAdapter);
        this.m_SettingAttrListView.setFocusableInTouchMode(true);
        this.m_SettingAdapter.notifyDataSetChanged();
    }

    void SetSettingItem() {
        this.m_strSettingTitleList.clear();
        this.m_strSettingTitleList.add(getString(C0349R.string.MSG_FACE));
        this.m_strSettingTitleList.add(getString(C0349R.string.PRINT_QUALITY));
        this.m_strSettingTitleList.add(getString(C0349R.string.MSG_PRINTOUT));
        this.m_strSettingTitleList.add(getString(C0349R.string.PAGE_DUPLEX));
        this.m_strSettingTitleList.add(getString(C0349R.string.MSG_SHARPEN));
        this.mItemIdList.clear();
        this.mItemIdList.add(Integer.valueOf(C0349R.id.setting_texture));
        this.mItemIdList.add(Integer.valueOf(C0349R.id.setting_method));
        this.mItemIdList.add(Integer.valueOf(C0349R.id.setting_layout));
        this.mItemIdList.add(Integer.valueOf(C0349R.id.setting_duplex));
        this.mItemIdList.add(Integer.valueOf(C0349R.id.setting_sharpen));
        if (this.m_strPrinterModel.equals(WirelessType.TYPE_P520L) || this.m_strPrinterModel.equals(WirelessType.TYPE_P530D)) {
            int id = this.mItemIdList.indexOf(Integer.valueOf(C0349R.id.setting_method));
            this.m_strSettingTitleList.remove(id);
            this.mItemIdList.remove(id);
        }
        if (this.m_strPrinterModel.equals(WirelessType.TYPE_P310W)) {
            id = this.mItemIdList.indexOf(Integer.valueOf(C0349R.id.setting_layout));
            this.m_strSettingTitleList.remove(id);
            this.mItemIdList.remove(id);
        }
        if (!this.m_strPrinterModel.equals(WirelessType.TYPE_P310W)) {
            id = this.mItemIdList.indexOf(Integer.valueOf(C0349R.id.setting_sharpen));
            this.m_strSettingTitleList.remove(id);
            this.mItemIdList.remove(id);
        }
        if (!this.m_strPrinterModel.equals(WirelessType.TYPE_P530D)) {
            id = this.mItemIdList.indexOf(Integer.valueOf(C0349R.id.setting_duplex));
            this.m_strSettingTitleList.remove(id);
            this.mItemIdList.remove(id);
        }
        this.m_iTotalSetting = this.mItemIdList.size();
    }

    private void DispatchCommand(int id, int checkedId) {
        int i = 1;
        Map map;
        Integer valueOf;
        if (id == C0349R.id.setting_texture) {
            map = this.mItemSelectMap;
            valueOf = Integer.valueOf(id);
            if (checkedId != C0349R.id.setting_second) {
                i = 0;
            }
            map.put(valueOf, Integer.valueOf(i));
        } else if (id == C0349R.id.setting_method) {
            map = this.mItemSelectMap;
            valueOf = Integer.valueOf(id);
            if (checkedId != C0349R.id.setting_second) {
                i = 0;
            }
            map.put(valueOf, Integer.valueOf(i));
        } else if (id == C0349R.id.setting_layout) {
            int iPrintOutType = 0;
            switch (checkedId) {
                case C0349R.id.setting_first /*2131427330*/:
                    if (!this.m_strPrinterModel.equals(WirelessType.TYPE_P530D)) {
                        iPrintOutType = 2;
                        break;
                    } else {
                        iPrintOutType = 6;
                        break;
                    }
                case C0349R.id.setting_second /*2131427333*/:
                    if (!this.m_strPrinterModel.equals(WirelessType.TYPE_P530D)) {
                        iPrintOutType = 3;
                        break;
                    } else {
                        iPrintOutType = 8;
                        break;
                    }
                case C0349R.id.setting_third /*2131427336*/:
                    iPrintOutType = 4;
                    break;
            }
            this.mItemSelectMap.put(Integer.valueOf(id), Integer.valueOf(iPrintOutType));
        } else if (id == C0349R.id.setting_duplex) {
            map = this.mItemSelectMap;
            valueOf = Integer.valueOf(id);
            if (checkedId != C0349R.id.setting_second) {
                i = 0;
            }
            map.put(valueOf, Integer.valueOf(i));
        }
    }

    private void SetOnClickListen() {
        this.m_ConfirmButton.setOnClickListener(this.ListenOK);
        this.m_BackButton.setOnClickListener(new C03772());
    }
}
