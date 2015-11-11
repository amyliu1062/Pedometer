package me.pedometer.app.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import me.pedometer.SensorChangeListener;
import me.pedometer.StepListener;
import me.pedometer.model.CalorieInfo;

/**
 * Created by liudan on 15/11/11.
 */
public class PedometerService extends Service implements StepListener {
    private static final String TAG = PedometerService.class.getSimpleName();
    private static SensorChangeListener sensorChangeListener;
    private SensorManager mSensorMgr;

    public static final String ACTION_PEDOMETER = "me.ACTION_PEDOMETER";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mSensorMgr = (SensorManager) this.getSystemService(android.content.Context.SENSOR_SERVICE);
        Sensor sensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorChangeListener = new SensorChangeListener(new CalorieInfo());
        sensorChangeListener.setStepListener(this);
        //此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
        mSensorMgr.registerListener(sensorChangeListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onStep(CalorieInfo calorieInfo) {
        Log.d(TAG, "onStep: " + calorieInfo);
        Intent intent = new Intent(ACTION_PEDOMETER);
        intent.putExtra("data", calorieInfo);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != mSensorMgr){
            sensorChangeListener.setStepListener(null);
            mSensorMgr.unregisterListener(sensorChangeListener);
        }
    }
}
