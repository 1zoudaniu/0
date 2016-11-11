package safebox.yiye.com.safebox.activity;

import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.constant.SpContent;
import safebox.yiye.com.safebox.fragment.BaseFragment;
import safebox.yiye.com.safebox.fragment.GuijiFragment;
import safebox.yiye.com.safebox.fragment.IndexFragment;
import safebox.yiye.com.safebox.fragment.PaihangFragment;
import safebox.yiye.com.safebox.utils.ToastUtil;
import safebox.yiye.com.safebox.view.MyTabWidget;

public class MainActivity extends FragmentActivity implements
        MyTabWidget.OnTabSelectedListener,BaseFragment.BackHandledInterface {
    private MyTabWidget mTabWidget;
    private int mIndex = SpContent.HOME_FRAGMENT_INDEX;
    private FragmentManager mFragmentManager;
    private mReturnToHome returnToHome;
    int index = 0;
    public boolean isUpdate = true;


    private GuijiFragment guijiFirstFragment;
    private IndexFragment indexFragment;
    private PaihangFragment paihangFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActivityCollector.addActivity(MainActivity.this);

        applyKitKatTranslucency();
        init();
        initEvents();
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
    private void init() {
        mFragmentManager = getSupportFragmentManager();
        mTabWidget = (MyTabWidget) findViewById(R.id.tab_widget);
//        MyActivityManager.getInstance().pushOneActivity(this);
    }

    private void initEvents() {
        mTabWidget.setOnTabSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        index = getIntent().getIntExtra("INDEX", 0);
        if (index != 0) {
            mIndex = index;
            mTabWidget.setVisibility(View.GONE);
        } else {
            mTabWidget.setVisibility(View.VISIBLE);
        }
        onTabSelected(mIndex, 0);
        mTabWidget.setTabsDisplay(this, mIndex);

    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {

    }


    class MyTableSelectListener implements IndexFragment.TableSelectListener {
        @Override
        public void tableSelect(int position, int tag) {
//			mTabWidget.setTabsDisplay(ClientHomeActivity.this, position);
            onTabSelected(position, tag);
        }

    }

    @Override
    public void onTabSelected(int index, int tag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case SpContent.HOME_FRAGMENT_INDEX:
                if (null == indexFragment) {
                    MyTableSelectListener listener = new MyTableSelectListener();
                    indexFragment = new IndexFragment();
                    indexFragment.setTableSelectListener(listener);
                    transaction.add(R.id.fl_content, indexFragment);
                } else {
                    transaction.show(indexFragment);
                }
                break;
            case SpContent.GUIJI_FRAGMENT_INDEX:
                if (null == guijiFirstFragment) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("TAG", tag);
                    guijiFirstFragment = new GuijiFragment();
                    returnToHome = new mReturnToHome();
                    guijiFirstFragment.setToHome(returnToHome);
                    guijiFirstFragment.setArguments(bundle);
                    transaction.add(R.id.fl_content, guijiFirstFragment);
                } else {
                    transaction.show(guijiFirstFragment);
                }
                break;
            case SpContent.PAIHANG_FRAGMENT_INDEX:
                if (null == paihangFragment) {
                    paihangFragment = new PaihangFragment();
                    transaction.add(R.id.fl_content, paihangFragment);
                } else {
                    transaction.show(paihangFragment);
                }

                break;
            default:
                break;
        }
        mIndex = index;
        transaction.commit();
//        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != indexFragment) {
            transaction.hide(indexFragment);
        }
        if (null != guijiFirstFragment) {
            transaction.hide(guijiFirstFragment);
        }
        if (null != paihangFragment) {
            transaction.hide(paihangFragment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 自己记录fragment的位置,防止activity被系统回收时，fragment错乱的问题
        // super.onSaveInstanceState(outState);
        outState.putInt("index", mIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // super.onRestoreInstanceState(savedInstanceState);
        mIndex = savedInstanceState.getInt("index");
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (index != 0) {
                finish();
            } else {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    ToastUtil.startShort(this, "再按一次退出程序");
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class mReturnToHome implements GuijiFragment.ReturnToHome {

        @Override
        public void toHome() {
            mTabWidget.setVisibility(View.VISIBLE);
            onTabSelected(0, 0);
            mTabWidget.setTabsDisplay(MainActivity.this, 0);
        }
    }


}
