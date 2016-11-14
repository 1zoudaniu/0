package safebox.yiye.com.safebox.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateUtils {
    /**
     * 获取版本号
     * 1. baidu, 找别人去实现这个功能,找.....
     * @return
     */
    public static  String getLocalVersion(Context context) {
        try {
            //实现 版本号
            PackageManager manager = context.getPackageManager();
            //得到版本信息
            /**
             * packageName, 包名 ,在清单文件中
             * flags 标志 ,默认0
             */
            PackageInfo packageInfo = manager.getPackageInfo("safebox.yiye.com.safebox", 0);
            //得到版本号
            String versionName = packageInfo.versionName;
            return "版本:" + versionName;
        }
        catch (NameNotFoundException e) {
            
            e.printStackTrace();
            return "未知";
        }
        
    }
    
    /**
     * 得到一个本地的版本号
     * @return
     */
    public static int getLocalCode(Context context) {
        try {
            //实现 版本号
            PackageManager manager = context.getPackageManager();
            //得到版本信息
            /**
             * packageName, 包名 ,在清单文件中
             * flags 标志 ,默认0
             */
            PackageInfo packageInfo = manager.getPackageInfo("safebox.yiye.com.safebox", 0);
            //得到版本号
            return packageInfo.versionCode;
            
        }
        catch (NameNotFoundException e) {
           
            e.printStackTrace();
            //返回-1表达错误
            return -1;
        }

    }
    
    
    /**
     * 安装apk
     */
    /**
     */
    public static void installAPK(Context context,String url) {
        //创建一个意图
        Intent intent_install = new Intent();
        intent_install.setAction("android.intent.action.VIEW");
        intent_install.addCategory("android.intent.category.DEFAULT");
        Uri data = Uri.parse(url);
        intent_install.setDataAndType(data, "application/vnd.android.package-archive");
        intent_install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent_install);
    }
    
    /**
     * @return返回一个response对象
     * @throws IOException
     */
    public static  Response getResponse(String url)
        throws IOException {
        
        OkHttpClient okHttpClient = new OkHttpClient
            .Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .build();
        
//            client.connectTimeoutMillis();
        
        //执行
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }
    
    /**
     * 写入文件
     * @param inputStream
     * @param
     * @throws IOException
     */
    public static void writeFile(InputStream inputStream, File downFile)
        throws IOException {
      //通过输出流去写
        FileOutputStream fileOutputStream = new FileOutputStream(downFile);
        
        //定义缓存 
        byte[] buffer = new byte[1024];
        
        //定义读取的长度
        int len = -1;
        
        //循环的去读写
        while ((len = inputStream.read(buffer)) != -1) {
            //写入文件
            fileOutputStream.write(buffer, 0, len);
            
        }
        
        //关流
        /*           if (inputStream != null) {
                       inputStream.close();
                       inputStream = null;
                   }
                   
                   if (fileOutputStream != null) {
                       fileOutputStream.close();
                       fileOutputStream = null;
                   }*/
        
        //关闭流的方法
        closeStream(inputStream);
        closeStream(fileOutputStream);
    }
    
    /**
     * 关闭流的方法
     */
    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    
    /**
     * 如何得到卡号
     * @return
     */
    public static String getSimNO(Context context) {
/*       当前的信息:89014103211118510720-->Android*/

        //得到一个电话管理器
        TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = manager.getSimSerialNumber();
        String simOperatorName = manager.getSimOperatorName();
        
        return simSerialNumber;//返回sim卡的值 
    }
    
    /**
     * 电话号码:
     * 内容:
     */
    public static void sendSMS(String phone,String content)
    {
        //得到短信管理器
        SmsManager manager = SmsManager.getDefault();
        //发送短信
        manager.sendTextMessage(phone, null, content, null, null);
    }
}
