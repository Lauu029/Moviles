package com.example.androidengine;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorHandler implements SensorEventListener {
    private SensorManager sensorManager_;
    private Sensor sensor_;
    private Context context_;

    public SensorHandler(Context context) {
        context_ = context;
        sensorManager_ = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor_ = sensorManager_.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager_.registerListener(this, sensor_, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float xAxis = event.values[0];
            float yAxis = event.values[1];
            float zAxis = event.values[2];

            // Puedes utilizar los valores de los ejes aquí según tus necesidades
//            Log.d("SensorHandler", "Acelerómetro - X: " + xAxis + ", Y: " + yAxis + ", Z: " + zAxis);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Este método se llama cuando cambia la precisión del sensor (puede ignorarse en este caso)
    }

    public void onResume() {
        sensorManager_.registerListener(this, sensor_, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        sensorManager_.unregisterListener(this);
    }
}
