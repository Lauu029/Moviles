package com.example.androidengine;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;



public class SensorHandler implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    void onResume(Context context){
//        sensorManager = (SensorManager) getSystemService(context.SENSOR_SERVICE);
//        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //u otro sensor
//        sensorManager .registerListener( this, sensor , SensorManager.SENSOR_DELAY_NORMAL);
    }
}
