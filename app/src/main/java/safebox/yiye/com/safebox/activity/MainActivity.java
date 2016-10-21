package safebox.yiye.com.safebox.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.adapter.GuijiFragmentPageAdapter;
import safebox.yiye.com.safebox.fragment.BaseFragment;

import safebox.yiye.com.safebox.fragment.GuijiFirstFragment;
import safebox.yiye.com.safebox.fragment.GuijiFragment;
import safebox.yiye.com.safebox.fragment.GuijiSecondFragment;
import safebox.yiye.com.safebox.fragment.IndexFragment;
import safebox.yiye.com.safebox.fragment.PaihangFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseFragment.BackHandledInterface {

    private FragmentManager supportFragmentManager;

    private IndexFragment indexFragment;
    private PaihangFragment paihangFragment;
    private RadioGroup radioGroup;
    private BaseFragment mBackHandedFragment;
    public long getFirsttime = new Date().getTime();
    private TextView textView;
    private GuijiFragment guijiFirstFragment;
    private GuijiFragmentPageAdapter guijiFragmentPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        applyKitKatTranslucency();


        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar_index);

        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ////开始 初始化 fragment管理器
        supportFragmentManager = getSupportFragmentManager();
        //初始化fragment
        initFragment();
        //初始化view
        initView();
        //初始化页面为首页
        changeFragment(indexFragment);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                String visibleFragment = getVisibleFragment().getClass().getName();
                if (visibleFragment.equals(IndexFragment.class.getName())
                        || (visibleFragment.equals(GuijiFragment.class.getName()))
                        || visibleFragment.equals(PaihangFragment.class.getName())) {
                    supportFragmentManager.popBackStack(null, 1);
                    radioGroup.setVisibility(View.VISIBLE);
                    isShowBottonRadioGroup(1);
                    long nowtime = new Date().getTime();
                    if (nowtime - getFirsttime <= 2000) {
                        System.exit(0);
                    } else {
                        getFirsttime = nowtime;
                        //Toast.makeText(this, "再次点击back退出程序", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                isShowBottonRadioGroup(1);
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //初始化fragment
    private void initFragment() {
        indexFragment = new IndexFragment();
        guijiFirstFragment = new GuijiFragment();
        paihangFragment = new PaihangFragment();


    }

    //初始化view
    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        radioGroup.setOnCheckedChangeListener(listener);

    }

    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    changeFragment(indexFragment);//切换到首页
                    break;
                case R.id.rb_paihang:
                    changeFragment(paihangFragment);//切换到排行页
                    break;
                case R.id.rb_guiji:
                    changeFragment(guijiFirstFragment);//切换到排行页
                    break;


            }
        }
    };

    /**
     * 切换Fragment
     *
     * @param frag
     */
    public void changeFragment(Fragment frag) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        supportFragmentManager.popBackStack(null, 1);
        transaction.setCustomAnimations(
                R.anim.fragment_slide_right_in, R.anim.fragment_slide_left_out,
                R.anim.fragment_slide_left_in, R.anim.fragment_slide_right_out
        );
        transaction.replace(R.id.fl_content, frag);
        transaction.commit();
    }

    /**
     * 添加Fragment到回退栈
     *
     * @param fragment
     */
    public void addToBackStack(Fragment fragment) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * 清空栈
     */
    public void clearBackStack() {
        supportFragmentManager.popBackStack(null, 0);//参数为0，清除栈顶的Fragment，参数为1，清空栈
    }

    public RadioGroup findRadiogroup() {
        return radioGroup;
    }

    public void back(View view) {
        isShowBottonRadioGroup(1);
        clearBackStack();
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();

        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    public void isShowBottonRadioGroup() {
        String visibleFragment = getVisibleFragment().getClass().getName();

        if (visibleFragment.equals(IndexFragment.class.getName())
                || visibleFragment.equals(GuijiFragment.class.getName())
                || visibleFragment.equals(PaihangFragment.class.getName())) {
            radioGroup.setVisibility(View.VISIBLE);
        } else {
            radioGroup.setVisibility(View.GONE);
        }
    }

    public void isShowBottonRadioGroup(int i) {
        String visibleFragment = getVisibleFragment().getClass().getName();

        if (visibleFragment.equals(IndexFragment.class.getName())) {
            radioGroup.check(R.id.rb_home);
            radioGroup.setVisibility(View.VISIBLE);

        } else if (visibleFragment.equals(GuijiFragment.class.getName())) {
            radioGroup.check(R.id.rb_guiji);
            radioGroup.setVisibility(View.VISIBLE);
        } else if (visibleFragment.equals(PaihangFragment.class.getName())) {
            radioGroup.check(R.id.rb_paihang);
            radioGroup.setVisibility(View.VISIBLE);
        } else {
            radioGroup.setVisibility(View.GONE);
        }
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }


    /**
     * 设置顶部通知栏样式方法
     */
    private void applyKitKatTranslucency() {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintResource(R.color.colorPrimary);//通知栏所需颜色
        }

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
