package safebox.yiye.com.testmodule.Map3DExpandlistview;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import safebox.yiye.com.testmodule.R;


public class GuijiActivity extends AppCompatActivity{
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guiji);
        initView();
        initViewPager();
    }



    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> titles = new ArrayList<>();
        titles.add("现在");
        titles.add("历史");
        for(int i=0;i<titles.size();i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();

        for(int i=0;i<titles.size();i++){
            fragments.add(new GuijiFirstFragment());
        }
        GuijiFragmentPageAdapter mGuijiFragmentPageAdapteradapter =
                 new GuijiFragmentPageAdapter(getSupportFragmentManager(), fragments, titles);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mGuijiFragmentPageAdapteradapter);
        //将TabLayout和ViewPager关联起来。
        mTabLayout.setupWithViewPager(mViewPager);
        //给TabLayout设置适配器
        mTabLayout.setTabsFromPagerAdapter(mGuijiFragmentPageAdapteradapter);
    }

}
