package com.example.maicom.rocket;
//alt+1 mostrar projetos

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager gerente;
    private Sensor sensor;
    private JanelaJogo janelaJogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        janelaJogo = new JanelaJogo(this);
        setContentView(janelaJogo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gerente = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = gerente.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gerente.registerListener(this, sensor, gerente.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gerente.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            moveBola(e.values[1], e.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void moveBola(float x, float y) {
        janelaJogo.move((int)x, (int)y);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        janelaJogo.setLimits(janelaJogo.getWidth(), janelaJogo.getHeight());
    }
}
