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
import android.util.Log;
import android.widget.Toast;

import cn.smssdk.gui.CommonDialog;
import safebox.yiye.com.safebox.utils.ToastUtil;


import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

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

        SMSSDK.initSDK(this,APPKEY,APPSECRECT);
        EventHandler eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);

        initView();



        setListener();
    }
    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event="+event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println("----"+event);
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    if (result==SMSSDK.RESULT_COMPLETE) {
                        Toast.makeText(getApplicationContext(), "依然走短信验证", Toast.LENGTH_SHORT).show();
                    }

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
//                    textView2.setText("验证码已经发送");
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
//                    countryTextView.setText(data.toString());
                    System.out.println("+++"+getApplicationContext());
                }else if(event==SMSSDK.RESULT_ERROR){
                    Toast .makeText(getApplicationContext(),"------" ,Toast.LENGTH_SHORT).show();
                }
            } else {
                ((Throwable) data).printStackTrace();
                Toast.makeText(getApplicationContext(), "错误"+data, Toast.LENGTH_SHORT).show();
            }

        }

    };
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
        Matcher m = p.matcher(phone);
        return m.matches();
    }
//
//    private void initSMSSDK() {
//        SMSSDK.initSDK(this, APPKEY, APPSECRECT, false);
//        EventHandler eh = new EventHandler() {
//            public void afterEvent(int event, int result, Object data) {
//                if (result == SMSSDK.RESULT_COMPLETE) {
//                    //回调完成
//                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                        //提交验证码成功
////                        if (msgLoginDlg != null && msgLoginDlg.isShowing()) {
////                            msgLoginDlg.dismiss();
////                        }
//                        Log.d("TAG", "提交验证码成功");
//                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                        //获取验证码成功
//                        Log.d("TAG", "获取验证码成功");
//                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
//                        ArrayList<HashMap<String, Object>> hashMaps = (ArrayList<HashMap<String, Object>>) data;
//                        for (HashMap<String, Object> each : hashMaps) {
//                            Log.d("TAG", each.toString());
//                        }
//                    }
//                } else {
//                    Throwable throwable = (Throwable) data;
//                    Log.d("TAG", throwable.getLocalizedMessage());
//                    Log.d("TAG", throwable.getMessage());
//                    try {
//                        JSONObject object = new JSONObject(throwable.getMessage());
//                        Log.d("TAG", object.getString("detail"));
//                        Log.d("TAG", object.getString("status"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        };
//        //注册短信验证的监听
//        SMSSDK.registerEventHandler(eh);
//


//
//        //初始化短信验证
//        SMSSDK.initSDK(this, APPKEY, APPSECRECT);
//
//        //注册短信回调
//        SMSSDK.registerEventHandler(new EventHandler() {
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//                switch (event) {
//                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
//                        if (result == SMSSDK.RESULT_COMPLETE) {
////                            ToastUtil.startShort(LoginActivity.this, "登陆成功！1");
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
////                            finish();
//                        } else {
//                            ToastUtil.startShort(LoginActivity.this, "请检查网络是否连接！");
//                        }
//                        break;
//                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
//                        if (result == SMSSDK.RESULT_COMPLETE) {
//
//                            ToastUtil.startShort(LoginActivity.this, "您已经登陆！");
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//
//                            finish();
//
//                        } else {
//
//                            ToastUtil.startShort(LoginActivity.this, "请检查网络是否连接！！！");
//
//                        }
//                        break;
//                }
//            }
//        });

}
