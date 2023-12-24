package com.example.androidengine;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorHandler implements SensorEventListener {
    private SensorManager sensorManager_;
    private Sensor sensor_;
    private Context context_;
    float Axis_[]= new float[3];
    public SensorHandler(Context context) {
        //se crea un sensor handler de tipo acelerometro
        context_ = context;
        sensorManager_ = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor_ = sensorManager_.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager_.registerListener(this, sensor_, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public float[]getAxis(){
        return Axis_;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //guardamos todo el movimiento en el array
            Axis_[0] = event.values[0];
            Axis_[1] = event.values[1];
            Axis_[2] = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onResume() {
        sensorManager_.registerListener(this, sensor_, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        sensorManager_.unregisterListener(this);
    }
}
