package com.hiti.ui.advideoview;

public abstract class ADVideoViewListener {
    public abstract void Error(int i);

    public abstract void Loading();

    public abstract void NotifyServerFail();

    public abstract void NotifyServerSuccess(String str);

    public abstract void StartLoading();

    public abstract void StartNotifyServer();

    public abstract void StopLoading();
}
