package safebox.yiye.com.safebox.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import safebox.yiye.com.safebox.BroadcastReceiver.BroadCastActivity_SMS;
import safebox.yiye.com.safebox.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import safebox.yiye.com.safebox.utils.SPUtils;
import safebox.yiye.com.safebox.utils.ToastUtil;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String APPKEY = "17ac5532d8552";
    private static final String APPSECRECT = "03cbddd8f9b3b70c78bc0a40ba610160";
    private EditText mEtPhone;
    private EditText mEtPhoneCode;
    private Button mBtnSubmitUser;
    private TextView mTvGetPhoneCode;
    private Button button_test;

    private static final int CODE_ING = 1;   //已发送，倒计时
    private static final int CODE_REPEAT = 2;  //重新发送
    private static final int SMSDDK_HANDLER = 3;  //短信回调
    private int TIME = 60;//倒计时60s
    private String login_safebox;
    private String substring;
    private IntentFilter intentFilter;
    private BroadCastActivity_SSS broadCastActivity_sss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_safebox = SPUtils.getString(LoginActivity.this, "login_safebox");
        if (!TextUtils.isEmpty(login_safebox)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            initView();

            intentFilter = new IntentFilter();
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
            broadCastActivity_sss = new BroadCastActivity_SSS();
            registerReceiver(broadCastActivity_sss, intentFilter);

            SMSSDK.initSDK(this, APPKEY, APPSECRECT);
            EventHandler eh = new EventHandler() {

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
            setListener();
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println("----" + event);
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        String trim = mEtPhone.getText().toString().trim();
                        SPUtils.saveString(LoginActivity.this, "login_safebox", trim);
                        Toast.makeText(getApplicationContext(), "短信验证成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();


//                    textView2.setText("验证码已经发送");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
//                    countryTextView.setText(data.toString());
                    System.out.println("+++" + getApplicationContext());
                } else if (event == SMSSDK.RESULT_ERROR) {
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Toast.makeText(getApplicationContext(), des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) {
                    }
                }
            } else {
                try {
                    Throwable throwable = (Throwable) data;
                    throwable.printStackTrace();
                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");//错误描述
                    int status = object.optInt("status");//错误代码
                    if (status > 0 && !TextUtils.isEmpty(des)) {
                        Toast.makeText(getApplicationContext(), des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private int editStart;//光标开始位置
    private int editEnd;//光标结束位置
    private final int charMaxNum = 11;

    private void setListener() {
        mTvGetPhoneCode.setOnClickListener(this);
        mBtnSubmitUser.setOnClickListener(this);
        button_test.setOnClickListener(this);
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
                if (mEtPhone.getText().toString().trim().length() >= 11) {
                    if (!validatePhone()) {
                        ToastUtil.startShort(LoginActivity.this, "手机号不对！");
                    } else {
                        if (mEtPhone.getText().toString().trim().length() == charMaxNum) {
                            mEtPhoneCode.requestFocus();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击获取验证码控件
            case R.id.tv_get_phone_code:
                mEtPhoneCode.requestFocus();
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
                mEtPhoneCode.requestFocus();
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
        if (TextUtils.isEmpty(login_safebox)) {
            //防止使用短信验证 产生内存溢出问题
            SMSSDK.unregisterAllEventHandler();
        }
        if (broadCastActivity_sss != null) {

            unregisterReceiver(broadCastActivity_sss);
        }
    }

    /**
     * 验证手机号码是否符合要求，11位 并且没有注册过
     *
     * @return 是否符合要求
     */
    private boolean validatePhone() {
        String phone = mEtPhone.getText().toString().trim();
        Pattern pattern = Pattern.compile("^1[35678][0-9]{9}$");
        Matcher m = pattern.matcher(phone);


//        String regex = "([a-zA-Z0-9]{6,12})";

        return m.matches();
    }
    class BroadCastActivity_SSS extends BroadcastReceiver {
//
//        public SmsMessage[] getMessageFromIntent(Intent intent) {
//            SmsMessage retmeMessage[] = null;
//            Bundle bundle = intent.getExtras();
//            Object pdus[] = (Object[]) bundle.get("pdus");
//            retmeMessage = new SmsMessage[pdus.length];
//            for (int i = 0; i < pdus.length; i++) {
//                byte[] bytedata = (byte[]) pdus[i];
//                retmeMessage[i] = SmsMessage.createFromPdu(bytedata);
//            }
//            return retmeMessage;
//        }
//
//        public static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

        @Override
        public void onReceive(Context context, Intent intent) {
//                            // TODO Auto-generated method stub
//                            if (ACTION.equals(intent.getAction())) {
//                                Intent i = new Intent(context, BroadCastActivity_SMS.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                SmsMessage[] msgs = getMessageFromIntent(intent);
//
//                                StringBuilder sBuilder = new StringBuilder();
//                                if (msgs != null && msgs.length > 0) {
//                                    for (SmsMessage msg : msgs) {
//                                        if (msg.getDisplayOriginatingAddress().startsWith("106571207117008")) {
//                                            String displayMessageBody = msg.getDisplayMessageBody();
//                                            substring = displayMessageBody.substring(displayMessageBody.length() - 4);
//                                            i.putExtra("sms_body", substring);
//                                            if (!TextUtils.isEmpty(substring)) {
//                                                mEtPhoneCode.requestFocus();
//                                                mEtPhoneCode.setText(substring);
//                                                ToastUtil.startShort(LoginActivity.this,"验证码已输入，请点击登录！");
//                                            }
//                                        }
//                                    }
//                                }
//                                Toast.makeText(context, sBuilder.toString(), Toast.LENGTH_LONG).show();
//                                context.startActivity(i);

            Bundle bundle = intent.getExtras();
            Object[] objects = (Object[]) bundle.get("puds");
            SmsMessage[] smsMessage = new SmsMessage[objects.length];
            for (int i = 0; i < smsMessage.length; i++) {
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
            }
            String address = smsMessage[0].getOriginatingAddress();
            if (address.equals("106571207117008")) {
                String fullMessage = "";
                for (SmsMessage message : smsMessage) {
                    fullMessage += message.getMessageBody();
                }
                String substring = fullMessage.substring(fullMessage.length() - 4);
                mEtPhoneCode.setText(substring);
                Toast.makeText(LoginActivity.this,"获取到了验证码："+substring,Toast.LENGTH_LONG).show();
            }

        }
    }
}
