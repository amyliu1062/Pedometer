package me.pedometer.model;

/**
 * Created by liudan on 15/11/10.
 */

public class CalorieInfo {

    //ID
    private int id;

    // 走步数
    private int frequency;

    // 卡路里
    private double calorie;

    // 行走距离
    private double distance;

    // 步/每分钟
    private long pace = 0;

    // 英里/公里/每小时
    private float speed = 0;

    private long startStepTime;

    private long lastStepTime;

    private String createTime;

    // 用户ID
    private int uId;


    private long[] mLastStepDeltas = {-1, -1, -1, -1};
    private int mLastStepDeltasIndex = 0;


    // 获得步幅数值
    public int getFrequency() {
        return this.frequency;
    }

    // 设置步幅数值
    public void setFrequency(int frequency) {
        //第一次计步
        if (this.frequency == 0) {
            startStepTime = System.currentTimeMillis();
        }
        this.frequency = frequency;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStartStepTime() {
        return startStepTime;
    }

    public void setmStartStepTime(long startStepTime) {
        this.startStepTime = startStepTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCalorie() {
        return this.calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public long getPace() {
        return pace;
    }

    public void setPace(long pace) {
        this.pace = pace;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public long getLastStepTime() {
        return lastStepTime;
    }

    public void setLastStepTime(long lastStepTime) {
        this.lastStepTime = lastStepTime;
    }

    public void setStartStepTime(long startStepTime) {
        this.startStepTime = startStepTime;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }


}

