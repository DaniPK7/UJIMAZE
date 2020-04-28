package com.al375875.ujimaze;

import android.os.Bundle;
import android.util.DisplayMetrics;

import es.uji.vj1229.framework.GameActivity;
import es.uji.vj1229.framework.IGameController;

public class MazeActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maze);
    }

    @Override
    protected IGameController buildGameController() {
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        return new Controller(getApplicationContext(), displayMetrics.widthPixels, displayMetrics.heightPixels);

    }



}
