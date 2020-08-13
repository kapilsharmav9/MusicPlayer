package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {


    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listview);

        RuntimePermission();

    }

    public void RuntimePermission() {

        Dexter.withActivity(MainActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        display();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }


    public ArrayList<File> findsong(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File single : files) {
            if (single.isDirectory() && !single.isHidden()) {
                arrayList.addAll(findsong(single));

            } else if (single.getName().endsWith(".mp3") || single.getName().endsWith(".wav")) {
                arrayList.add(single);
            }
        }

        return arrayList;
    }

    void display() {


        final ArrayList<File> mysongs = findsong(Environment.getExternalStorageDirectory());
        String[]  items = new String[mysongs.size()];
        for (int i = 0; i < mysongs.size(); i++) {

            items[i] = mysongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");

        }
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this,R.layout.list,items);
        listView.setAdapter(myadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songname=listView.getItemAtPosition(position).toString();
                Intent i=new Intent(MainActivity.this,PlayerActivity.class).putExtra("songs",mysongs).putExtra("songname",songname).putExtra("pos",position);
                startActivity(i);
            }
        });
    }
}
