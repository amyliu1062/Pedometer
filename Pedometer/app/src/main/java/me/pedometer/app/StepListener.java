package me.pedometer.app;

import me.pedometer.app.model.CalorieInfo;

/**
 * Created by liudan on 15/11/10.
 */
public interface StepListener {
    public abstract void onStep(CalorieInfo calorieInfo);

}
