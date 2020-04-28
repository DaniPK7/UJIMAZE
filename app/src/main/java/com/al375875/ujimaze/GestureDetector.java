package com.al375875.ujimaze;

enum gesture{
    CLICK,
    NONE,
    SWIPE;
}

public class GestureDetector {

    int touchX0, touchY0;
    int touchX1, touchY1;

    final int SWIPE_THRESHOLD=100;
    final int CLICK_MARGIN=10;
    final int SWIPE_MARGIN=80;

    String gesture;
    public void onTouchDown(int x, int y){
        touchX0 = x;
        touchY0 = y;
    }

    public void onTouchUp(int x, int y){
        touchX1 = x;
        touchY1 = y;


    }
}
