package com.android.lvxin.musicplayer.musics;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.android.lvxin.musicplayer.BaseToolBarActivity;
import com.android.lvxin.musicplayer.R;
import com.android.lvxin.musicplayer.data.source.MusicsRepository;
import com.android.lvxin.musicplayer.player.MusicPlayingActivity;
import com.android.lvxin.musicplayer.util.ActivityUtils;
import com.android.lvxin.musicplayer.util.ViewModelHolder;

public class MusicsActivity extends BaseToolBarActivity implements MusicItemNavigation {

    private static final String LOCAL_MUSICS_VIEW_MODEL_TAG = "local_musics_view_model_tag";
    private static final int REQULT_CONTACTS = 1;
    private MusicsViewModel mViewModel;

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MusicsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_musics);
        addDefaultCustomView();
        getPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQULT_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            permissionRead();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            openDialog();
                        }
                    });
                }
                return;
            }
            default:
                break;
        }
    }

    private void permissionRead() {
        MusicsFragment musicsFragment = findOrCreateViewFragment();
        mViewModel = findOrCreateViewModel();
        mViewModel.setNavigation(this);
        musicsFragment.setViewModel(mViewModel);
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            permissionRead();
        }
    }

    @Override
    protected void onDestroy() {
        mViewModel.onActivityDestroyed();
        super.onDestroy();
    }

    private MusicsFragment findOrCreateViewFragment() {
        MusicsFragment musicsFragment = (MusicsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == musicsFragment) {
            musicsFragment = MusicsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), musicsFragment, R.id.contentFrame);
        }
        return musicsFragment;
    }

    private MusicsViewModel findOrCreateViewModel() {
        ViewModelHolder<MusicsViewModel> retainedViewModel = (ViewModelHolder<MusicsViewModel>)
                getSupportFragmentManager().findFragmentByTag(LOCAL_MUSICS_VIEW_MODEL_TAG);
        if (null == retainedViewModel || null == retainedViewModel.getViewModel()) {
            MusicsViewModel viewModel = new MusicsViewModel(getApplicationContext(), MusicsRepository.getInstance(getApplicationContext()));
            retainedViewModel = ViewModelHolder.createContainer(viewModel);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), retainedViewModel, LOCAL_MUSICS_VIEW_MODEL_TAG);
        }
        return retainedViewModel.getViewModel();

    }

    @Override
    public void openMusicPlayPage() {
        MusicPlayingActivity.start(this, mViewModel.getData());
    }
}
