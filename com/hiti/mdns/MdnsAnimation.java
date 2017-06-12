package com.hiti.mdns;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.hiti.animation.AnimaEffect;
import com.hiti.prinbiz.C0349R;
import com.hiti.utility.LogManager;
import com.hiti.utility.wifi.ShowPrinterList.Scan;
import org.xmlpull.v1.XmlPullParser;

public class MdnsAnimation {
    LogManager LOG;
    boolean bIsStop;
    AnimaEffect mMdnsMessageAnimation;
    View mMdnsMessageView;
    AnimaEffect mMdnsSignalAnimation;
    View mMdnsSignalView;
    TextView mMdnsTitleView;
    String strUndetect;

    public MdnsAnimation(Context context, View mdnsSignalView, View mdnsMessageView, TextView mdnsTitleView) {
        this.mMdnsSignalAnimation = null;
        this.mMdnsMessageAnimation = null;
        this.mMdnsSignalView = null;
        this.mMdnsMessageView = null;
        this.mMdnsTitleView = null;
        this.strUndetect = XmlPullParser.NO_NAMESPACE;
        this.bIsStop = false;
        this.LOG = null;
        this.mMdnsSignalAnimation = new AnimaEffect(context);
        this.mMdnsMessageAnimation = new AnimaEffect(context);
        this.mMdnsSignalView = mdnsSignalView;
        this.mMdnsMessageView = mdnsMessageView;
        this.mMdnsTitleView = mdnsTitleView;
        this.strUndetect = context.getString(C0349R.string.PRINTER_UNDETECT);
        this.LOG = new LogManager(0);
    }

    public void Initial() {
        this.mMdnsSignalAnimation.Start(this.mMdnsSignalView, C0349R.anim.alert, 2200);
        this.mMdnsMessageAnimation.Start(this.mMdnsMessageView, C0349R.anim.fade, 3000);
        this.mMdnsTitleView.setVisibility(0);
    }

    public void StartAnimation() {
        this.LOG.m386v("MdnsAnimation", "StartAnimation");
        this.bIsStop = false;
        if (this.mMdnsSignalAnimation.IsStop()) {
            this.mMdnsSignalAnimation.Start(this.mMdnsSignalView, C0349R.anim.alert, 2200);
        }
        if (this.mMdnsMessageAnimation.IsStop()) {
            this.mMdnsMessageAnimation.Start(this.mMdnsMessageView, C0349R.anim.fade, 3000);
        }
        this.mMdnsTitleView.setText(this.strUndetect);
        this.mMdnsMessageView.setVisibility(0);
    }

    public void StopAnimation() {
        if (!this.bIsStop) {
            this.bIsStop = true;
            this.mMdnsSignalAnimation.Remove(this.mMdnsSignalView);
            this.mMdnsMessageAnimation.Remove(this.mMdnsMessageView);
            this.mMdnsMessageView.setVisibility(4);
        }
    }

    public void GetState(Scan state, String strSSID) {
        this.LOG.m386v("GetState", " state" + state + " SSID: " + strSSID);
        if (state != Scan.NoPrinter) {
            this.mMdnsTitleView.setText(strSSID);
            StopAnimation();
            return;
        }
        StartAnimation();
    }

    public boolean IsStop() {
        return this.bIsStop;
    }
}
