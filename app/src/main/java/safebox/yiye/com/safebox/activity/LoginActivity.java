package safebox.yiye.com.safebox.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import safebox.yiye.com.safebox.R;


import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.widget.Toast;

import cn.smssdk.gui.CommonDialog;
import safebox.yiye.com.safebox.utils.ToastUtil;


import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.socks.library.KLog;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String APPKEY = "17ac5532d8552";
    private static final String APPSECRECT = "03cbddd8f9b3b70c78bc0a40ba610160";
    private EditText mEtPhone;
    private EditText mEtPhoneCode;
    private Button mBtnSubmitUser;
    private TextView mTvGetPhoneCode;
    private Button button_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initSMSSDK();

        initView();

        setListener();
    }
    private void initView() {
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtPhoneCode = (EditText) findViewById(R.id.et_phone_code);
        mTvGetPhoneCode = (TextView) findViewById(R.id.tv_get_phone_code);
        mBtnSubmitUser = (Button) findViewById(R.id.btn_submit_user);
        button_test = (Button) findViewById(R.id.btn_submit_test);
    }

    private void setListener() {
        mTvGetPhoneCode.setOnClickListener(this);
        mBtnSubmitUser.setOnClickListener(this);
        button_test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击获取验证码控件
            case R.id.tv_get_phone_code:
                mTvGetPhoneCode.requestFocus();
                if (validatePhone()) {
                    //启动获取验证码 86是中国
                    String phone = mEtPhone.getText().toString().trim();
                    SMSSDK.getVerificationCode("86", phone);//发送短信验证码到手机号
                    timer.start();//使用计时器 设置验证码的时间限制
                } else {
                    ToastUtil.startShort(LoginActivity.this, "手机号不正确！");
                    mEtPhone.requestFocus();
                }
                break;
            //点击提交信息按钮
            case R.id.btn_submit_user:
                mTvGetPhoneCode.requestFocus();
                if (validatePhone()) {
                    submitInfo();
                } else {
                    ToastUtil.startShort(LoginActivity.this, "手机号不正确！");
                    mEtPhone.requestFocus();
                }

                break;
            case R.id.btn_submit_test:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            break;
        }
    }

    /**
     * 验证用户的其他信息
     * 这里验证两次密码是否一致 以及验证码判断
     */
    private void submitInfo() {
        //密码验证
        KLog.e("提交按钮点击了");
        String phone = mEtPhone.getText().toString().trim();
        String code = mEtPhoneCode.getText().toString().trim();
        SMSSDK.submitVerificationCode("86", phone, code);//提交验证码  在eventHandler里面查看验证结果

    }

    /**
     * 使用计时器来限定验证码
     * 在发送验证码的过程 不可以再次申请获取验证码 在指定时间之后没有获取到验证码才能重新进行发送
     * 这里限定的时间是60s
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mTvGetPhoneCode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            mTvGetPhoneCode.setEnabled(true);
            mTvGetPhoneCode.setText("获取验证码");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止使用短信验证 产生内存溢出问题
        SMSSDK.unregisterAllEventHandler();
    }

    /**
     * 验证手机号码是否符合要求，11位 并且没有注册过
     *
     * @return 是否符合要求
     */
    private boolean validatePhone() {
        String phone = mEtPhone.getText().toString().trim();
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(phone );
        return m.matches();
    }
    private void initSMSSDK() {
        //初始化短信验证
        SMSSDK.initSDK(this, APPKEY, APPSECRECT);

        //注册短信回调
        SMSSDK.registerEventHandler(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                switch (event) {
                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            KLog.e("验证成功");

                            ToastUtil.startShort(LoginActivity.this, "登陆成功！1");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            KLog.e("验证失败");

                            ToastUtil.startShort(LoginActivity.this, "验证失败！1");
                        }
                        break;
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            KLog.e("获取验证成功");

                            ToastUtil.startShort(LoginActivity.this, "您已经登陆！");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                            finish();

                        } else {
                            KLog.e("获取验证失败");

                            ToastUtil.startShort(LoginActivity.this, "验证失败！2");
                        }
                        break;
                }
            }
        });
    }
//
////        SMSSDK.initSDK(this, "17ac5532d8552", "03cbddd8f9b3b70c78bc0a40ba610160");
////
//
//
//        RegisterPage registerPage = new RegisterPage();
//        registerPage.setRegisterCallback(new EventHandler() {
//            public void afterEvent(int event, int result, Object data) {
//                // 解析注册结果
//                if (result == SMSSDK.RESULT_COMPLETE) {
//                    @SuppressWarnings("unchecked")
//                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
//                    String country = (String) phoneMap.get("country");
//                    String phone = (String) phoneMap.get("phone");
//                    // 提交用户信息
//                    registerUser(country, phone);
//                }
//            }
//        });
//        registerPage.show(this);
//
//
//        gettingFriends = false;
//
//        loadSharePrefrence();
//
//        if (Build.VERSION.SDK_INT >= 23) {
//            int readPhone = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
//            int receiveSms = checkSelfPermission(Manifest.permission.RECEIVE_SMS);
//            int readSms = checkSelfPermission(Manifest.permission.READ_SMS);
//            int readContacts = checkSelfPermission(Manifest.permission.READ_CONTACTS);
//            int readSdcard = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
//
//            int requestCode = 0;
//            ArrayList<String> permissions = new ArrayList<String>();
//            if (readPhone != PackageManager.PERMISSION_GRANTED) {
//                requestCode |= 1 << 0;
//                permissions.add(Manifest.permission.READ_PHONE_STATE);
//            }
//            if (receiveSms != PackageManager.PERMISSION_GRANTED) {
//                requestCode |= 1 << 1;
//                permissions.add(Manifest.permission.RECEIVE_SMS);
//            }
//            if (readSms != PackageManager.PERMISSION_GRANTED) {
//                requestCode |= 1 << 2;
//                permissions.add(Manifest.permission.READ_SMS);
//            }
//            if (readContacts != PackageManager.PERMISSION_GRANTED) {
//                requestCode |= 1 << 3;
//                permissions.add(Manifest.permission.READ_CONTACTS);
//            }
//            if (readSdcard != PackageManager.PERMISSION_GRANTED) {
//                requestCode |= 1 << 4;
//                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//            }
//            if (requestCode > 0) {
//                String[] permission = new String[permissions.size()];
//                this.requestPermissions(permissions.toArray(permission), requestCode);
//                return;
//            }
//        }
//
//        initSDK();

    }

//
//    // 填写从短信SDK应用后台注册得到的APPKEY
//    //此APPKEY仅供测试使用，且不定期失效，请到mob.com后台申请正式APPKEY
//    private static String APPKEY = "17ac5532d8552";
//
//    // 填写从短信SDK应用后台注册得到的APPSECRET
//    private static String APPSECRET = "03cbddd8f9b3b70c78bc0a40ba610160";
//
//    // 短信注册，随机产生头像
//    private static final String[] AVATARS = {
//            "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
//            "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
//            "http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
//            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
//            "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
//            "http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
//            "http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
//            "http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
//            "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
//            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
//            "http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
//            "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
//    };
//
//    private boolean ready;
//    private boolean gettingFriends;
//    private Dialog pd;
//
//
//    private void initSDK() {
//        // 初始化短信SDK
//        SMSSDK.initSDK(this, APPKEY, APPSECRET, true);
//        if (APPKEY.equalsIgnoreCase("f3fc6baa9ac4")) {
//            Toast.makeText(this, "此APPKEY仅供测试使用，且不定期失效，请到mob.com后台申请正式APPKEY", Toast.LENGTH_SHORT).show();
//        }
//        final Handler handler = new Handler(this);
//        EventHandler eventHandler = new EventHandler() {
//            public void afterEvent(int event, int result, Object data) {
//                Message msg = new Message();
//                msg.arg1 = event;
//                msg.arg2 = result;
//                msg.obj = data;
//                handler.sendMessage(msg);
//            }
//        };
//        // 注册回调监听接口
//        SMSSDK.registerEventHandler(eventHandler);
//        ready = true;
//
//        // 获取新好友个数
//        showDialog();
//        SMSSDK.getNewFriendsCount();
//        gettingFriends = true;
//    }
//
//    private void loadSharePrefrence() {
//        SharedPreferences p = getSharedPreferences("SMSSDK_SAMPLE", Context.MODE_PRIVATE);
//        APPKEY = p.getString("APPKEY", APPKEY);
//        APPSECRET = p.getString("APPSECRET", APPSECRET);
//    }
//
//    private void setSharePrefrence() {
//        SharedPreferences p = getSharedPreferences("SMSSDK_SAMPLE", Context.MODE_PRIVATE);
//        Editor edit = p.edit();
//        edit.putString("APPKEY", APPKEY);
//        edit.putString("APPSECRET", APPSECRET);
//        edit.commit();
//    }
//
//    protected void onDestroy() {
//        if (ready) {
//            // 销毁回调监听接口
//            SMSSDK.unregisterAllEventHandler();
//        }
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (ready && !gettingFriends) {
//            // 获取新好友个数
//            showDialog();
//            SMSSDK.getNewFriendsCount();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//
//    public boolean handleMessage(Message msg) {
//        if (pd != null && pd.isShowing()) {
//            pd.dismiss();
//        }
//
//        int event = msg.arg1;
//        int result = msg.arg2;
//        Object data = msg.obj;
//        if (event == SMSSDK.EVENT_SUBMIT_USER_INFO) {
//            // 短信注册成功后，返回MainActivity,然后提示新好友
//            if (result == SMSSDK.RESULT_COMPLETE) {
//
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//
//            } else {
//                ((Throwable) data).printStackTrace();
//            }
//        } else if (event == SMSSDK.EVENT_GET_NEW_FRIENDS_COUNT) {
//            if (result == SMSSDK.RESULT_COMPLETE) {
//                refreshViewCount(data);
//                gettingFriends = false;
//            } else {
//                ((Throwable) data).printStackTrace();
//            }
//        }
//        return false;
//    }
//
//    // 更新，新好友个数
//    private void refreshViewCount(Object data) {
//        int newFriendsCount = 0;
//        try {
//            newFriendsCount = Integer.parseInt(String.valueOf(data));
//        } catch (Throwable t) {
//            newFriendsCount = 0;
//        }
//
//        if (pd != null && pd.isShowing()) {
//            pd.dismiss();
//        }
//    }
//
//    // 弹出加载框
//    private void showDialog() {
//        if (pd != null && pd.isShowing()) {
//            pd.dismiss();
//        }
//        pd = CommonDialog.ProgressDialog(this);
//        pd.show();
//    }
//
//    // 提交用户信息
//    private void registerUser(String country, String phone) {
//        Random rnd = new Random();
//        int id = Math.abs(rnd.nextInt());
//        String uid = String.valueOf(id);
//        String nickName = "SmsSDK_User_" + uid;
//        String avatar = AVATARS[id % 12];
//        SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
//    }
