package safebox.yiye.com.safebox.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import safebox.yiye.com.safebox.fragment.GuijiFirstFragment;
import safebox.yiye.com.safebox.fragment.GuijiSecondFragment;


public class GuijiFragmentPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public GuijiFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }



//    @Override
//    public Fragment getItem(int position) {
//        return mFragments.get(position);
//    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
                if(mFragments.get(position) == null){
                    return new GuijiFirstFragment();
                }
            return mFragments.get(position);
        }else if(position ==1){
                if(mFragments.get(position) == null){
                    return new GuijiSecondFragment();
                }
            return mFragments.get(position);
        }
        return null;
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

}
