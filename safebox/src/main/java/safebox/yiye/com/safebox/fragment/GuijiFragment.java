package safebox.yiye.com.safebox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.activity.MainActivity;
import safebox.yiye.com.safebox.adapter.GuijiFragmentPageAdapter;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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

        viewPagerAdapter = new GuijiFragmentPageAdapter(mainActivity.getSupportFragmentManager(),fragments,titles);
        viewPagerGuiji.setAdapter(viewPagerAdapter);
        int currentItem = viewPagerGuiji.getCurrentItem();
        if (currentItem == 1) {
            callBack.onLogined();
        }
        viewPagerGuiji.setCurrentItem(0);
        //将TabLayout和ViewPager关联起来。
        tabLayoutGuiji.setupWithViewPager(viewPagerGuiji);
        //给TabLayout设置适配器
        tabLayoutGuiji.setTabsFromPagerAdapter(viewPagerAdapter);
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public interface ReturnToHome{
        void toHome();
    }


    OnClickTabCallBack callBack;
    public void setOnClickTabCallBack(OnClickTabCallBack callBack) {
        this.callBack = callBack;
    }
    public interface OnClickTabCallBack {
        void onLogined();
    }
}
