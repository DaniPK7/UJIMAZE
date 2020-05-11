package com.al375875.ujimaze;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

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


        return new Controller(getApplicationContext(), displayMetrics.widthPixels, displayMetrics.heightPixels);

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        soundPool.play(soundIds[1], 1f, 1f, 0, 0, 1);
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
        Log.d("music","preparando ");


        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(attributes)
                .build();

        soundIds = new int[sounds.length];

        for (int i = 0 ; i < sounds.length ; i++)
        {

            soundIds[i] = soundPool.load(this, sounds[i], 0);
            //Log.d("music","idRaw "+ sounds[i]+ " idLoad: "+ soundIds[i]);

        }



        //playSound(soundIds[1]);//prueba play



    }

    public void playSound(int id) {
        Log.d("music","id "+ id);

        soundPool.play(soundIds[1], 1f, 1f, 0, 0, 1);

    }



}
