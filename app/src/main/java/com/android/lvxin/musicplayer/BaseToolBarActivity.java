package com.android.lvxin.musicplayer;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.lvxin.musicplayer.event.MusicCompletedEvent;
import com.android.lvxin.musicplayer.event.MusicErrorEvent;
import com.android.lvxin.musicplayer.event.MusicPreparedEvent;
import com.android.lvxin.musicplayer.event.UpdateMusicInfoEvent;
import com.android.lvxin.musicplayer.event.UpdateMusicPlayStatusEvent;
import com.android.lvxin.musicplayer.event.UpdateMusicProgressEvent;
import com.android.lvxin.musicplayer.receiver.PlayStatusReceiver;
import com.android.lvxin.musicplayer.service.IMusicAidlInterface;
import com.android.lvxin.musicplayer.service.MusicPlayer;
import com.android.lvxin.musicplayer.util.ActivityStack;
import com.android.lvxin.musicplayer.util.DeviceUtils;
import com.android.lvxin.musicplayer.util.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @ClassName: BaseToolBarActivity
 * @Description: Activity 的基类
 * @Author: lvxin
 * @Date: 2017-07-17 09:30:41
 */
public class BaseToolBarActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private static final String TAG = "BaseToolBarActivity";

    protected Toolbar mToolbar;
    protected ImageView mBackBtn;
    protected TextView toolBarTitle;
    protected TextView line;
    protected FrameLayout bottomLayout; // 底部控制条
    private ViewGroup rootLayout;
    private boolean isCollapsingToolbar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getIns().push(this);
        MusicPlayer.bindService(this, this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (!isCollapsingToolbar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

                ViewUtils.miuisetstatusbarlightmode(getWindow(), true);
                ViewUtils.flymeSetStatusBarLightMode(getWindow(), true);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * toolbar是否可伸缩
     *
     * @return
     */
    public boolean isCollapsingToolbar() {
        return isCollapsingToolbar;
    }

    /**
     * 设置toolbar是否可伸缩
     *
     * @param isCoolsapsing
     */
    public void setCollapsingToolbar(boolean isCoolsapsing) {
        this.isCollapsingToolbar = isCoolsapsing;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initToolbarBase() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
    }

    /**
     * 改变toolbar高度
     *
     * @param height dp
     */
    public void updateToolbarHeight(int height) {
        ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
        layoutParams.height = height;
        mToolbar.setLayoutParams(layoutParams);
        mToolbar.requestLayout();
    }

    /**
     * 改变toolbar高度
     */
    public void updateToolbarHeight() {
        ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.toolbar_height);
        mToolbar.setLayoutParams(layoutParams);
        mToolbar.requestLayout();
    }

    /**
     * 隐藏Toolbar
     */
    public void hideToolBar() {
        mToolbar.setVisibility(View.GONE);
    }

    /**
     * 更新状态栏背景色
     *
     * @param color
     */
    public void updateToolbarBackgroundColor(int color) {
        mToolbar.setBackgroundColor(color);
    }

    /**
     * 更新状态栏背景色
     *
     * @param colorString
     */
    public void updateToolbarBackgroundColor(String colorString) {
        mToolbar.setBackgroundColor(Color.parseColor(colorString));
    }

    /**
     * 更新状态栏背景色
     *
     * @param resId
     */
    public void updateToolbarBackgroundResource(int resId) {
        mToolbar.setBackgroundResource(resId);
    }

    /**
     * 添加自定义toolbar的布局
     *
     * @param resId
     */
    public void addCustomView(int resId) {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setCustomView(View.inflate(this, resId, null), layoutParams);
        toolBarTitle = (TextView) findViewById(R.id.title_tv);
        mBackBtn = (ImageView) findViewById(R.id.left_iv);
        mBackBtn.setOnClickListener(this);
    }

    /**
     * 添加自定义toolbar的布局, 不带返回按钮
     *
     * @param resId
     */
    public void addCustomViewWithoutBack(int resId) {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setCustomView(View.inflate(this, resId, null), layoutParams);
    }

    /**
     * 添加最常用的自定义toolbar布局
     *
     * @Description: 左边标题，右边一个按钮
     */
    public void addDefaultCustomView() {
        addCustomView(R.layout.layout_custom_tool_bar_with_title);
        line = (TextView) findViewById(R.id.toolbar_line);
        toolBarTitle = (TextView) findViewById(R.id.title_tv);
//        mToolbar.setBackgroundResource(R.mipmap.ic_toolbar_bg);
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(layoutResID, false);
    }

    /**
     * @param layoutResID
     * @param isCollapsingToolbar
     */
    public void setContentView(int layoutResID, boolean isCollapsingToolbar) {
        this.isCollapsingToolbar = isCollapsingToolbar;
        super.setContentView(isCollapsingToolbar ? R.layout.activity_base_layout_collapsing_toolbar : R.layout.activity_base_linear);
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        bottomLayout = (FrameLayout) findViewById(R.id.layout_bottom);
        bottomLayout.setVisibility(View.GONE);
        rootLayout = (LinearLayout) findViewById(R.id.layout_root);
        if (null == rootLayout) {
            return;
        }
        rootLayout.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        initToolbarBase();


    }

    /**
     * 添加toolbar中可折叠的布局
     *
     * @param layoutResID
     */
    public void addCollapsingView(int layoutResID) {
        FrameLayout collapsingContainer = (FrameLayout) findViewById(R.id.custom_collapsing_container);
        collapsingContainer.addView(View.inflate(this, layoutResID, null));
    }

    /**
     * 添加AppBar中的view
     *
     * @param layoutResID
     */
    public void addAppBarView(int layoutResID) {
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addView(View.inflate(this, layoutResID, null));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 没有网络显示提示
     */
    public boolean noNetwork() {
        if (!DeviceUtils.checkNetEnv(this)) {
            showToast(getString(R.string.network_not_available));
            return true;
        }
        return false;
    }

    /**
     * 显示Toast
     *
     * @param errorInfo 显示的信息
     */
    public void showToast(String errorInfo) {
        Toast.makeText(this, errorInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getIns().popup(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.left_iv) {
            handleBack();
        }
    }

    /**
     * 处理返回按钮事件
     */
    protected void handleBack() {
        finish();
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        MusicPlayer.mService = IMusicAidlInterface.Stub.asInterface(iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateMusicInfo(UpdateMusicInfoEvent event) {
        Log.d(TAG, "onUpdateMusicInfo: ");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPrepared(MusicPreparedEvent event) {
        Log.d(TAG, "onPrepared: ");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompletion(MusicCompletedEvent event) {
        Log.d(TAG, "onCompletion: ");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onError(MusicErrorEvent event) {
        Log.d(TAG, "onError: ");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateProgress(UpdateMusicProgressEvent event) {
//        Log.d(TAG, "onUpdateProgress: ");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdatePlayStatus(UpdateMusicPlayStatusEvent event) {
        Log.d(TAG, "onUpdatePlayStatus: ");
    }
}
