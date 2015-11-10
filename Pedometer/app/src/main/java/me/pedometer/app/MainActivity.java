package me.pedometer.app;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import me.pedometer.app.model.CalorieInfo;

public class MainActivity extends Activity implements StepListener {

    private TextView stepTv;
    private SensorManager mSensorMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepTv = (TextView) findViewById(R.id.activity_main_step_tv);

        mSensorMgr = (SensorManager) this.getSystemService(android.content.Context.SENSOR_SERVICE);
        Sensor sensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorListener sensorListener = new SensorListener(new CalorieInfo());
        //此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
        mSensorMgr.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorListener.setStepListener(this);
    }

    @Override
    public void onStep(CalorieInfo calorieInfo) {
        stepTv.setText(String.valueOf(calorieInfo.getFrequency()));
    }
}
