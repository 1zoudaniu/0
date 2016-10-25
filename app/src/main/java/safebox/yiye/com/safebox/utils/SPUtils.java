package safebox.yiye.com.safebox.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/7/3.
 */
public class SPUtils {



    private static SharedPreferences preferences;

    private static void init(Context context)
    {
        if(preferences==null)
        {
            preferences=context.getSharedPreferences("safebox", Context.MODE_PRIVATE);
        }
    }
    //保存一个String 方法
    public static void saveString(Context context, String key, String value)
    {
        init(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    //删除一个String 方法
    public static void delString(Context context, String key)
    {
        init(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.remove(key);
        edit.commit();
    }

    //获取一个String值
    public static String getString(Context context,String key)
    {
        init(context);
        return  preferences.getString(key, "");
    }
}
