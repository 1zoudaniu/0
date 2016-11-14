package safebox.yiye.com.safebox.Globle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Process;

/***
全局上下文 创建在所有的四大组件之前
*/
public class SafeboxApplication extends Application {
	private static Context context;

	/*** 方法
	*/
	@Override
	public void onCreate() {
		super.onCreate();
//		Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
//			@Override
//			public void uncaughtException(Thread thread, Throwable ex) {
////				System.out.println(ex);
//				//静默关闭当前的应用
//				//创建文件  字符串输出流
//				try {
//					PrintWriter pw=new PrintWriter(new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".safebox.log"));
//					ex.printStackTrace(pw);
//					pw.flush();
//					pw.close();
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				//发送到服务器。。
//				Process.killProcess(Process.myPid());
//			}
//		});
		this.context=getApplicationContext();
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());


	}

	public static Context getContext(){
		return context;
	}
//	public static synchronized newInstance (final Context mContext)
//	{
//		if (mInstance == null)
//		{
//			mInstance = new XXClass(context.getApplicationContext());
//		}
//		return mInstance;
//	}
}
