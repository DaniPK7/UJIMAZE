package com.al375875.ujimaze;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import es.uji.vj1229.framework.GameActivity;
import es.uji.vj1229.framework.IGameController;

public class MazeActivity extends GameActivity   {

    private String[] soundLabels;
    private SoundPool soundPool;
    private AudioAttributes attributes;
    private int[] soundIds;
    private int[] sounds= {R.raw.ramp, R.raw.bulletproof};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.maze);
    }

    @Override
    protected IGameController buildGameController() {
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        prepareSoundPool();

        return new Controller(getApplicationContext(), displayMetrics.widthPixels, displayMetrics.heightPixels);

    }

    public void endGame(){


        //ShowEndScreen();
        /*Intent intent= new Intent (this, EndActivity.class);

        super.startActivity(intent);*/
    }
    private void ShowEndScreen(View view){
        super.startActivity(new Intent(this,EndActivity.class));


    }

    //
    @Override
    protected void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        prepareSoundPool();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
    private void prepareSoundPool() {

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(attributes)
                .build();

        soundIds = new int[sounds.length];

        /*for (int i = 0 ; i < sounds.length ; i++)
        {

            soundIds[i] = soundPool.load(this, sounds[i], 0);
            //Log.d("music","idRaw "+ sounds[i]+ " idLoad: "+ soundIds[i]);

        }*/
        soundIds[0] = soundPool.load(this,R.raw.ramp, 1);
        soundIds[1] = soundPool.load(this, R.raw.bulletproof, 1);

        //soundPool.play(soundIds[1],1,1,1,0,1);


        //playSound(soundIds[1]);



    }

    public void playSound(int id) {
        Log.d("music","id "+ id);

        soundPool.play(soundIds[1], 1f, 1f, 0, 0, 1);
    }



}
