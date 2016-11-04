package safebox.yiye.com.safebox.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import safebox.yiye.com.safebox.R;


/**
 * Created by Administrator on 2016/7/2.
 */
public class FragmentUtil {
    //从activity跳转到fragment


    public static void ActivityChangeFragment(FragmentActivity activity, Fragment frag){
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content,frag);
        transaction.commit();
    }
    /*
    //从fragment跳转到fragment不添加到栈中

    id:fragment填充的界面
    currentFragment 当前的fragment
    toFragment 要跳转的fragment
    添加到回退栈中
     */

    public static void startFragment(int id,Fragment currentFragment, Fragment toFragment) {
        if (currentFragment != null && toFragment != null) {
            FragmentManager manager = currentFragment.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(
                    R.anim.fragment_slide_right_in, R.anim.fragment_slide_left_out,
                    R.anim.fragment_slide_left_in,R.anim.fragment_slide_right_out
            );
            //FIXME

            if (toFragment.isAdded()) {
                transaction.hide(currentFragment)
                        .show(toFragment)
                        .commit();
            } else {

                transaction.add(id,toFragment, "toFragment")
                        .hide(currentFragment)
                        .show(toFragment)
                        .commit();
            }

        }
    }
//从fragment跳转到fragment不添加到栈中
    public static  void replaceFragment(Fragment currentFragment, Fragment toFragment){
        if (currentFragment != null && toFragment != null) {
            FragmentManager manager = currentFragment.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.commit();
            transaction.replace(R.id.fl_content, toFragment);
        }
    }
    //从fragment跳转到fragment添加到栈中
    public static void startFragmentAddtoStack(int id,Fragment currentFragment, Fragment toFragment) {
        if (currentFragment != null && toFragment != null) {
            FragmentManager manager = currentFragment.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(
                    R.anim.fragment_slide_right_in, R.anim.fragment_slide_left_out,
                    R.anim.fragment_slide_left_in,R.anim.fragment_slide_right_out
            );
            //FIXME
            transaction.addToBackStack(null);
            if (toFragment.isAdded()) {
                transaction.hide(currentFragment)
                        .show(toFragment)
                        .commit();
            } else {
                transaction.add(id,toFragment)
                        .hide(currentFragment)
                        .show(toFragment)
                        .commit();
            }

        }
    }
    //从fragment跳转到fragment添加到栈中

    public static  void replaceFragment(int id,Fragment currentFragment, Fragment toFragment){
        if (currentFragment != null && toFragment != null) {
            FragmentManager manager = currentFragment.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(
                    R.anim.fragment_slide_right_in, R.anim.fragment_slide_left_out,
                    R.anim.fragment_slide_left_in,R.anim.fragment_slide_right_out
            );
            transaction.addToBackStack(null);
            transaction.replace(id, toFragment);
            transaction.commit();
        }
    }
    public static  void replaceFragment(int id,Fragment currentFragment, Fragment toFragment,int i){
        if (currentFragment != null && toFragment != null) {
            FragmentManager manager = currentFragment.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(
                     R.anim.fragment_slide_left_out,R.anim.fragment_slide_right_in,
                    R.anim.fragment_slide_right_out,R.anim.fragment_slide_left_in
            );
            transaction.addToBackStack(null);
            transaction.replace(id, toFragment);
            transaction.commit();
        }
    }
}
