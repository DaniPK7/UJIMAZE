package com.al375875.ujimaze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);




    }

    public void startGame(View view) {
       /* Intent intent= new Intent(this, MazeActivity.class);
        startActivity(intent);*/
        super.startActivity(new Intent(this,MazeActivity.class));

    }


}
