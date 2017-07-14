package com.android.lvxin.musicplayer.musicdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.lvxin.musicplayer.R;

public class MusicDetailActivity extends AppCompatActivity {

    public static void start(Context context, long musicId) {
        Intent intent = new Intent(context, MusicDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
    }
}
