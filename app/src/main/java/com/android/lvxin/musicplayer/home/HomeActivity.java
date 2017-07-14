package com.android.lvxin.musicplayer.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.lvxin.musicplayer.R;
import com.android.lvxin.musicplayer.musics.MusicsActivity;
import com.android.lvxin.musicplayer.util.ActivityUtils;
import com.android.lvxin.musicplayer.util.ViewModelHolder;

public class HomeActivity extends AppCompatActivity implements HomeNavigator {

    private static final String HOME_VIEW_MODEL_TAG = "home_view_model_tag";
    HomeViewModel homeViewModel;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupToolbar();
        setupNavigationDrawer();

        HomeFragment homeFragment = findOrCreateViewFragment();

        homeViewModel = findOrCreateViewModel();
        homeViewModel.setNavigator(this);

        homeFragment.setViewModel(homeViewModel);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (null != navigationView) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settings_navigation_menu_item:
                        // do something
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private HomeFragment findOrCreateViewFragment() {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == homeFragment) {
            homeFragment = HomeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), homeFragment, R.id.contentFrame);
        }
        return homeFragment;
    }

    private HomeViewModel findOrCreateViewModel() {
        ViewModelHolder<HomeViewModel> retainedViewModel = (ViewModelHolder<HomeViewModel>) getSupportFragmentManager().findFragmentByTag(HOME_VIEW_MODEL_TAG);
        if (null == retainedViewModel || null == retainedViewModel.getViewModel()) {
            HomeViewModel viewModel = new HomeViewModel(this);
            retainedViewModel = ViewModelHolder.createContainer(viewModel);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), retainedViewModel, HOME_VIEW_MODEL_TAG);
        }
        return retainedViewModel.getViewModel();

    }

    @Override
    public void openClickItem() {
        MusicsActivity.start(this);
    }

    @Override
    protected void onDestroy() {
        homeViewModel.onActivityDestroyed();
        super.onDestroy();
    }
}
