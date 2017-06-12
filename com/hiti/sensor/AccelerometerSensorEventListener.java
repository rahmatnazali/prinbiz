package com.hiti.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public abstract class AccelerometerSensorEventListener implements SensorEventListener {
    static final int SHAKE_THRESHOLD = 1000;
    float acceChangeRate;
    long lastShakeTime;
    long lastUpdate;
    float last_x;
    float last_y;
    float f442x;
    float f443y;

    public abstract void OnShake(SensorEvent sensorEvent);

    public AccelerometerSensorEventListener() {
        this.lastShakeTime = 0;
        this.last_x = 0.0f;
        this.last_y = 0.0f;
        this.acceChangeRate = 0.0f;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent e) {
        long curTime = System.currentTimeMillis();
        if (curTime - this.lastUpdate > 100) {
            long diffTime = curTime - this.lastUpdate;
            this.lastUpdate = curTime;
            this.f442x = e.values[0];
            this.f443y = e.values[1];
            this.acceChangeRate = 0.0f;
            if (this.last_x != 0.0f) {
                this.acceChangeRate = (Math.abs(((this.f442x + this.f443y) - this.last_x) - this.last_y) / ((float) diffTime)) * 10000.0f;
            }
            if (this.acceChangeRate > 1000.0f && curTime - this.lastShakeTime > 2000) {
                this.lastShakeTime = curTime;
                OnShake(e);
            }
            this.last_x = this.f442x;
            this.last_y = this.f443y;
        }
    }
}
