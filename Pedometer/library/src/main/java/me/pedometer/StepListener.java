package me.pedometer;

import me.pedometer.model.CalorieInfo;

/**
 * Created by liudan on 15/11/10.
 */
public interface StepListener {
    public abstract void onStep(CalorieInfo calorieInfo);

}
