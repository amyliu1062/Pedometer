package me.pedometer.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import me.pedometer.app.service.PedometerService;
import me.pedometer.model.CalorieInfo;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView stepTv;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepTv = (TextView) findViewById(R.id.activity_main_step_tv);

        IntentFilter filter = new IntentFilter();
        filter.addAction(PedometerService.ACTION_PEDOMETER);

        registerReceiver(broadcastReceiver, filter);

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CalorieInfo calorieInfo = (CalorieInfo) intent.getSerializableExtra("data");
            Log.d(TAG, "[Activity]calorieInfo: " + calorieInfo);
            stepTv.setText(String.valueOf(count++));
        }
    };

    public void onStartClick(View view) {
        count = 0;
        startService(new Intent(this, PedometerService.class));
    }

    public void onStopClick(View view) {
        stopService(new Intent(this, PedometerService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
