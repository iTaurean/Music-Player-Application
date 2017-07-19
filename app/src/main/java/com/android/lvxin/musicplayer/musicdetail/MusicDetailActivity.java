package com.android.lvxin.musicplayer.musicdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.lvxin.musicplayer.BaseToolBarActivity;
import com.android.lvxin.musicplayer.R;

public class MusicDetailActivity extends BaseToolBarActivity {

    public static void start(Context context, long musicId) {
        Intent intent = new Intent(context, MusicDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        addDefaultCustomView();
    }
}
