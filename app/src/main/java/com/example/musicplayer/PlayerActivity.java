package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity  {
    ImageButton play, back, forword,pause;
    SeekBar seekBar;
    static MediaPlayer player;
    int position;
    ArrayList<File> mysong;
    Thread updateseekbar;
    TextView songgTextLabel;
    String sname;
    MyReciever myReciever;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        play = findViewById(R.id.btnplay);
        back = findViewById(R.id.btnback);
        forword = findViewById(R.id.btnfforword);
        seekBar = findViewById(R.id.progressbar);
        songgTextLabel = findViewById(R.id.tv_song);
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setMax(player.getDuration());
                if (player.isPlaying()) {

                    play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    player.stop();
                } else {
                    play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    player.start();
                }
            }
        });

        forword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                player.release();
                position = (position + 1 % mysong.size());
                Uri uri = Uri.parse(mysong.get(position).toString());
                player = MediaPlayer.create(getApplicationContext(), uri);
                sname = mysong.get(position).getName().toString();
                songgTextLabel.setText(sname);
                player.start();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                player.release();
                position = ((position - 1) < 0) ? (mysong.size() - 1) : (position - 1);
                Uri uri = Uri.parse(mysong.get(position).toString());
                player = MediaPlayer.create(getApplicationContext(), uri);
                sname = mysong.get(position).getName().toString();
                songgTextLabel.setText(sname);
                player.start();
            }
        });
        updateseekbar = new Thread() {

            @Override
            public void run() {
                super.run();

                int totalduration = player.getDuration();
                int currentposition = 0;
                while (currentposition < totalduration) {
                    try {
                        sleep(500);
                        currentposition = player.getCurrentPosition();
                        seekBar.setProgress(currentposition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

            }
        };
        if (player != null) {
            player.stop();
            player.release();

        }
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        mysong = (ArrayList) bundle.getParcelableArrayList("songs");
        sname = mysong.get(position).getName().toString();
        String songname = i.getStringExtra("songname");
        songgTextLabel.setText(songname);
        songgTextLabel.setSelected(true);
        position = bundle.getInt("pos", 0);
        Uri uri = Uri.parse(mysong.get(position).toString());
        player = MediaPlayer.create(getApplicationContext(), uri);
        player.start();
        seekBar.setMax(player.getDuration());
        updateseekbar.start();
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPink), PorterDuff.Mode.MULTIPLY);
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.colorButtonGrey), PorterDuff.Mode.SRC_IN);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
            }
        });
    }



}
