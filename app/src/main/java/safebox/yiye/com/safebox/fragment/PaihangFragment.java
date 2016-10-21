package safebox.yiye.com.safebox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.activity.MainActivity;
import safebox.yiye.com.safebox.adapter.GuijiFragmentPageAdapter;
import safebox.yiye.com.safebox.adapter.PaiHangMainAdapter;
import safebox.yiye.com.safebox.adapter.PaiHangMoreAdapter;
import safebox.yiye.com.safebox.adapter.PaihangFragmentPageAdapter;
import safebox.yiye.com.safebox.beans.PaihangCar;
import safebox.yiye.com.safebox.constant.Model;
import safebox.yiye.com.safebox.utils.ToastUtil;

public class PaihangFragment extends Fragment {

    private MainActivity mainActivity;
    private ViewPager viewPager;

    private TabLayout tabLayout;
    private View view;
    private PaihangFirstFragment paihangFirstFragment;
    private PaihangSecondFragment paihangSecondFragment;
    private PaihangFragmentPageAdapter paihangFragmentPageAdapter;

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

        view = inflater.inflate(R.layout.fragment_paihang, container, false);

        paihangFirstFragment = new PaihangFirstFragment();
        paihangSecondFragment = new PaihangSecondFragment();

        mainActivity = (MainActivity) getActivity();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);


        initViewPager();
        return view;
    }

    private void initViewPager() {

        List<String> titles = new ArrayList<>();
        titles.add("当月");
        titles.add("上月");
        for (int i = 0; i < titles.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(paihangFirstFragment);
        fragments.add(paihangSecondFragment);

        paihangFragmentPageAdapter = new PaihangFragmentPageAdapter(mainActivity.getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(paihangFragmentPageAdapter);
        viewPager.setCurrentItem(0);
        //将TabLayout和ViewPager关联起来。
        tabLayout.setupWithViewPager(viewPager);
        //给TabLayout设置适配器
        tabLayout.setTabsFromPagerAdapter(paihangFragmentPageAdapter);
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
}
