package safebox.yiye.com.safebox.utils;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aina on 2016/10/25.
 */

public class ActivityCollector {
    public  static List<AppCompatActivity> activityList=new ArrayList<AppCompatActivity>();

    public static void addActivity(AppCompatActivity activity) {
        activityList.add(activity);
    }
    public static void removeActivity(AppCompatActivity activity) {
        activityList.remove(activity);
    }
    public static void finishAll() {
        for (AppCompatActivity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
