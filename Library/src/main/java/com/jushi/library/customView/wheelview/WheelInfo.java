package com.jushi.library.customView.wheelview;

/**
 * WheelViewDialog中用于显示文字的类
 * created by wyf on 2021-10-25
 */
public class WheelInfo {
    private String name;
    private int value;

    public WheelInfo() {
    }

    public WheelInfo(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
