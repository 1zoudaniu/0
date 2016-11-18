package safebox.yiye.com.safebox.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.activity.MainActivity;
import safebox.yiye.com.safebox.adapter.GuijiFragmentPageAdapter;
import safebox.yiye.com.safebox.event.MessageEvent;

/**
 * Created by aina on 2016/10/20.
 */

public class GuijiFragment extends Fragment {

    private MainActivity mainActivity;
    private ViewPager viewPagerGuiji;
    private GuijiSecondFragment guijiSecondFragment;
    private GuijiFirstFragment guijiFirstFragment;
    private TabLayout tabLayoutGuiji;
    private GuijiFragmentPageAdapter viewPagerAdapter;
    private View view;


    private void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    /**
     * 设置顶部通知栏样式方法
     */
    private void applyKitKatTranslucency() {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(getActivity());
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintResource(R.color.black);//通知栏所需颜色
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        applyKitKatTranslucency();

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }

        view = inflater.inflate(R.layout.fragment_guiji, container, false);

        guijiFirstFragment = new GuijiFirstFragment();
        guijiSecondFragment = new GuijiSecondFragment();

        mainActivity = (MainActivity) getActivity();
        viewPagerGuiji = (ViewPager) view.findViewById(R.id.viewpager_guiji);
        tabLayoutGuiji = (TabLayout) view.findViewById(R.id.tabs_guiji);


        initViewPager();
        return view;
    }

    private void initViewPager() {

        List<String> titles = new ArrayList<>();
        titles.add("现在");
        titles.add("历史");
        for (int i = 0; i < titles.size(); i++) {
            tabLayoutGuiji.addTab(tabLayoutGuiji.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(guijiFirstFragment);
        fragments.add(guijiSecondFragment);

        viewPagerAdapter = new GuijiFragmentPageAdapter(mainActivity.getSupportFragmentManager(), fragments, titles);
        viewPagerGuiji.setAdapter(viewPagerAdapter);

        viewPagerGuiji.setCurrentItem(0);
        //将TabLayout和ViewPager关联起来。
        tabLayoutGuiji.setupWithViewPager(viewPagerGuiji);
        //给TabLayout设置适配器
        tabLayoutGuiji.setTabsFromPagerAdapter(viewPagerAdapter);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    ReturnToHome toHome;

    public ReturnToHome getToHome() {
        return toHome;
    }

    public void setToHome(ReturnToHome toHome) {
        this.toHome = toHome;
    }

    // 回到 首页 接口
    public interface ReturnToHome {
        void toHome();
    }

}
