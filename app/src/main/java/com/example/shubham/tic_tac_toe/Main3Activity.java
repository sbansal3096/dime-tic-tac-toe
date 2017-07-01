package com.example.shubham.tic_tac_toe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class Main3Activity extends AppCompatActivity {

    VideoView vv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        vv = (VideoView)findViewById(R.id.video1);

        //Video Loop
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                vv.start(); //need to make transition seamless.
            }
        });

        Uri uri = Uri.parse("android.resource://com.example.shubham.tic_tac_toe/"
                + R.raw.demo);

        vv.setVideoURI(uri);
        vv.requestFocus();
        vv.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vv.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vv.start();
    }


    public void clickedStart(View view)
    {
        Intent i= new Intent(this, Main2Activity.class);
        startActivity(i);
    }
}