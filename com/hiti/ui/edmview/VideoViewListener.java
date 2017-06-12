package com.hiti.ui.edmview;

public abstract class VideoViewListener {
    public abstract void Complete();

    public abstract void Error(int i);

    public abstract void Loading();

    public abstract void StartLoading();

    public abstract void StopLoading();
}
