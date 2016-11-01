package safebox.yiye.com.safebox.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;
import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.beans.ServerVersionInfo;
import safebox.yiye.com.safebox.constant.Model;
import safebox.yiye.com.safebox.utils.SPUtils;
import safebox.yiye.com.safebox.utils.UpdateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

public class SplashActivity extends Activity {

    protected static final String TAG = "SplashActivity";

    //当前服务器获取版本号的标志
    protected static final int SERVERVERSIONCONTENT = 100;

    //服务器下载完成的标志
    private static final int DOWNFINISH = 101;

    //开启当前安装的intent意图
    private static final int INSTALLREQUEST = 102;

    private TextView mTv_splash_version;

    //定义接收数据handler
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SERVERVERSIONCONTENT:
                    //获取服务器版本内容
                    String serverVersionContent = (String) msg.obj;
                    if (TextUtils.isEmpty(serverVersionContent)) {
                        //如果是空的
                        //进入主界面
                        go2login();//进入主界面

                        return;//结束当前的操作
                    }

                    //说明数据不为空,进行解析
                    ServerVersionInfo info = parseJSON(serverVersionContent);

                    //校验当前的数据是否为空
                    if (info == null) {
                        //进入主界面
                        go2login();
                        return;//返回
                    }

                    //打印
                    Log.i(TAG, info.versincode + "-->" + info.downURL);

                    //得到本地的版本号
                    int localversion = UpdateUtils.getLocalCode(SplashActivity.this);

                    //对比本地的版本号与服务器版本号是否一致
                    if (localversion != info.versincode) {
                        //不等于,弹出对话框
                        Log.i(TAG, "弹出对话框");
                        showUpdateDialog(info.downURL);

                    } else {
                        //进入主界面
                        go2login();
                    }

                    break;

                case DOWNFINISH://下载完成
                    //安装我的下载的apk
                    File downFile = (File) msg.obj;
                    UpdateUtils.installAPK(SplashActivity.this, downFile, INSTALLREQUEST);
                    break;

                default:
                    break;
            }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //得到结果
        if (INSTALLREQUEST == requestCode) {
            //安装 意图
            Log.i(TAG, "返回的数据:" + resultCode + "data:" + data);
            //如果返回一个0就是取消
            if (resultCode == Activity.RESULT_CANCELED) {
                //进入主界面
                go2login();
            }
            //如果是确定就不需要操作
        }
    }

    /**
     * 显示一个更新对话框
     */
    private void showUpdateDialog(final String url) {

        Builder builder = new Builder(this);
        builder.setTitle("有新版本啦");
        builder.setMessage("是否更新新版本");
        builder.setCancelable(false);//当前对话框外部点击不消失
        builder.setNegativeButton("立即更新", new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //下载操作
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        downAPK(url);
                    }
                }).start();

            }

        });

        builder.setPositiveButton("下次再说", new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //直接进入主界面
                go2login();

            }
        });

        //显示对话框
        builder.show();
    }

    /**
     * 专门用于下载
     */
    private void downAPK(String url) {
        try {
            //得到我们的response对象
            Response response = UpdateUtils.getResponse(url);
            //得到请求内容
            InputStream inputStream = response.body().byteStream();

            //写入sd卡
            //写入的sd状态

            //判断当前的状态是否可写可读SD
            if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
                //可读可写
                File storageDirectory = Environment.getExternalStorageDirectory();
                //创建一个文件
                File downFile = new File(storageDirectory, "ichuche.apk");

                //写入文件
                UpdateUtils.writeFile(inputStream, downFile);

                Message msg = Message.obtain();
                msg.obj = downFile;//下载文件
                msg.what = DOWNFINISH;
                //提交数据
                mHandler.sendMessage(msg);

            } else {
                //出错
                go2login();
            }

        } catch (IOException e) {
            e.printStackTrace();
            /**
             * 有两种方法
             * 1. 如果要提示用户,直接mhandler
             * 2. 不提示用户
             */
            //直接进入主界面
            go2login();
        }

    }

    //解析json
    private ServerVersionInfo parseJSON(String json) {
        /*   {
               "downurl": "http://www.heima.com",
               "version": "2"
           }*/

        try {
            JSONObject jsonObject = new JSONObject(json);
            int versoncode = jsonObject.getInt("version");
            String downurl = jsonObject.getString("downurl");

            ServerVersionInfo info = new ServerVersionInfo();
            info.versincode = versoncode;
            info.downURL = downurl;

            return info;//返回数据 

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }

    }

    /**
     * 进入主界面操作
     */
    private void go2login() {
        //TODO: 待会来处理
        //FIXME: 看一下效果
        Log.i(TAG, "进入主界面");

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        //关闭当前的界面
        finish();
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.activity_splash);

        init();//初始化

        //得到自动更新的标志
        boolean autoisopen = SPUtils.getBoolean_true(this, Model.AUTOISOPEN);
        Log.i(TAG, "自动更新的状态" + autoisopen);

        //判断如果是true;就去版本更新
        //如果是false就直接进入主界面

        if (autoisopen) {
            //更新版本
            checkServerVersion();
        } else {
            go2login();
        }
    }

    /**
     * 版本更新
     */
    private void checkServerVersion() {
        /**
         * 访问网络 
         * 耗时操作
         */
        new Thread(new Runnable() {

            @Override
            public void run() {
                //去服务器访问最新的版本信息
                try {
                    //得到我们的response对象
                    Response response = UpdateUtils.getResponse(Model.SERVERVERSONURL);
                    //打印数据体
                    String content = response.body().string();
                    Log.i(TAG, "serverversion:" + content);

                    Message msg = Message.obtain();
                    msg.obj = content;//数据体
                    msg.what = SERVERVERSIONCONTENT;//当前标识
                    //传送数据
                    mHandler.sendMessage(msg);
                } catch (IOException e) {

                    e.printStackTrace();
                    //直接进入主界面
                    go2login();
                }
            }
        }).start();

    }

    /**
     * 初始化界面的
     */
    private void init() {
        //找到控件
        initView();

        //初始化数据
        initData();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        String version = UpdateUtils.getLocalVersion(this);
        mTv_splash_version.setText(version);
    }

    /**
     * 找到我们的控件
     */
    private void initView() {
        mTv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
    }
}
