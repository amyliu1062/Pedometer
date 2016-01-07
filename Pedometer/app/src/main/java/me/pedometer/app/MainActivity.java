package me.pedometer.app;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import me.pedometer.StepDetector;
import me.pedometer.app.service.PedometerService;
import me.pedometer.app.service.StepCounterService;
import me.pedometer.model.CalorieInfo;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView stepTv;
    private Thread thread;  //定义线程对象
    private int total_step = 0;   //走的总步数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepTv = (TextView) findViewById(R.id.activity_main_step_tv);

//        IntentFilter filter = new IntentFilter();
//        filter.addAction(PedometerService.ACTION_PEDOMETER);
//        registerReceiver(broadcastReceiver, filter);

        initThreadToPedometer();

    }

    private void initThreadToPedometer() {
        if (thread == null) {

            thread = new Thread() {// 子线程用于监听当前步数的变化

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    super.run();
                    int temp = 0;
                    while (true) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (StepCounterService.FLAG) {
                            Message msg = new Message();
                            if (temp != StepDetector.CURRENT_STEP) {
                                temp = StepDetector.CURRENT_STEP;
                            }
                            handler.sendMessage(msg);// 通知主线程
                        }
                    }
                }
            };
            thread.start();
        }
    }

    // 当创建一个新的Handler实例时, 它会绑定到当前线程和消息的队列中,开始分发数据
    // Handler有两个作用, (1) : 定时执行Message和Runnalbe 对象
    // (2): 让一个动作,在不同的线程中执行.

    Handler handler = new Handler() {// Handler对象用于更新当前步数,定时发送消息，调用方法查询数据用于显示？？？？？？？？？？
        //主要接受子线程发送的数据, 并用此数据配合主线程更新UI
        //Handler运行在主线程中(UI线程中), 它与子线程可以通过Message对象来传递数据,
        //Handler就承担着接受子线程传过来的(子线程用sendMessage()方法传递Message对象，(里面包含数据)
        //把这些消息放入主线程队列中，配合主线程进行更新UI。

        @Override                  //这个方法是从父类/接口 继承过来的，需要重写一次
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);        // 此处可以更新UI

            countStep();          //调用步数方法
            stepTv.setText(total_step + "");// 显示当前步数
        }
    };

    /**
     * 实际的步数
     */
    private void countStep() {
        if (StepDetector.CURRENT_STEP % 2 == 0) {
            total_step = StepDetector.CURRENT_STEP;
        } else {
            total_step = StepDetector.CURRENT_STEP + 1;
        }

        total_step = StepDetector.CURRENT_STEP;
    }

//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            CalorieInfo calorieInfo = (CalorieInfo) intent.getSerializableExtra("data");
//            Log.d(TAG, "[Activity]calorieInfo: " + calorieInfo);
//            stepTv.setText(String.valueOf(count++));
//        }
//    };


    public void onStartClick(View view) {
//        count = 0;
//        startService(new Intent(this, PedometerService.class));
        startService(new Intent(this, StepCounterService.class));
    }

    public void onStopClick(View view) {
//        stopService(new Intent(this, PedometerService.class));

        stopService(new Intent(this, StepCounterService.class));
        StepDetector.CURRENT_STEP = 0;
        handler.removeCallbacks(thread);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(broadcastReceiver);
    }
}
