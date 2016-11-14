package safebox.yiye.com.safebox.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.ReverseFlyEffect;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import safebox.yiye.com.safebox.Globle.SafeboxApplication;
import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.activity.MainActivity;
import safebox.yiye.com.safebox.activity.PersonInfoActivity;
import safebox.yiye.com.safebox.activity.SingleCarLocationInfoActivity;
import safebox.yiye.com.safebox.adapter.IndexCarScoreAdapter;
import safebox.yiye.com.safebox.beans.ServerVersionInfo;
import safebox.yiye.com.safebox.constant.Model;
import safebox.yiye.com.safebox.firstanmi.AlphaForegroundColorSpan;
import safebox.yiye.com.safebox.http.CarScoreAndListModel;
import safebox.yiye.com.safebox.http.HttpApi;
import safebox.yiye.com.safebox.utils.BaseUrl;
import safebox.yiye.com.safebox.utils.SPUtils;
import safebox.yiye.com.safebox.utils.ToastUtil;
import safebox.yiye.com.safebox.utils.UpdateUtils;
import safebox.yiye.com.safebox.view.DotCircularRingView;

/**
 * Created by aina on 2016/9/20.
 */
public class IndexFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    //当前服务器获取版本号的标志
    protected static final int SERVERVERSIONCONTENT = 100;

    //服务器下载完成的标志
    private static final int DOWNFINISH = 101;

    //开启当前安装的intent意图
    private static final int INSTALLREQUEST = 102;
    private static final String TAG = "INDEXFRAGMENT";

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

////                    //说明数据不为空,进行解析
////                    ServerVersionInfo info = parseJSON(serverVersionContent);
////
////                    //校验当前的数据是否为空
////                    if (info == null) {
////                        //进入主界面
////                        go2login();
////                        return;//返回
////                    }
//
//                    //打印
//                    Log.i(TAG, info.versincode + "-->" + info.downURL);

                    //得到本地的版本号
                    int localversion = UpdateUtils.getLocalCode(SafeboxApplication.getContext());

//                    //对比本地的版本号与服务器版本号是否一致
//                    if (localversion != info.versincode) {
//                        //不等于,弹出对话框
//                        Log.i(TAG, "弹出对话框");
//                        showUpdateDialog(info.downURL);
//
//                    } else {
//                        //进入主界面
//                        go2login();
//                    }
                    //TODO  进行更换上面的diamante
                    if (localversion != 0) {
//                        //不等于,弹出对话框
//                        Log.i(TAG, "弹出对话框");
//                        showUpdateDialog("string");
                        Toast.makeText(SafeboxApplication.getContext(), "有新版本", Toast.LENGTH_SHORT).show();
                        mUpdateMsg.setVisibility(View.VISIBLE);
                        mUpdateMsg.setText("  1");
                    } else {
                        //进入主界面
                        go2login();
                    }

                    break;

                case DOWNFINISH://下载完成
                    //安装我的下载的apk
                    File downFile = (File) msg.obj;
//                    UpdateUtils.installAPK(getActivity(), downFile, INSTALLREQUEST);
                    break;

                default:
                    break;
            }
        }

    };
    private TextView mUpdateMsg;

    private void go2login() {
        Toast.makeText(SafeboxApplication.getContext(), "有了", Toast.LENGTH_SHORT).show();
        mUpdateMsg.setVisibility(View.GONE);
    }

    /**
     * 显示一个更新对话框
     */
    private void showUpdateDialog(final String url) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SafeboxApplication.getContext());
        builder.setTitle("有新版本啦");
        builder.setMessage("是否更新新版本");
        builder.setCancelable(false);//当前对话框外部点击不消失
        builder.setNegativeButton("立即更新", new DialogInterface.OnClickListener() {

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

        builder.setPositiveButton("下次再说", new DialogInterface.OnClickListener() {

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






    public static boolean isFirstRoate = true;

    private volatile boolean isRunning = true;

    //新近添加开始
    private int mActionBarTitleColor;
    private int mActionBarHeight;
    private int mActionBarHeight_null;

    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private int mMinHeaderTranslation_null;
    private JazzyListView mListView;

    private TextView mHeaderPicture;
    private RelativeLayout mHeader;

    private AccelerateDecelerateInterpolator mSmoothInterpolator;

    private RectF mRect1 = new RectF();
    private RectF mRect2 = new RectF();

    private AlphaForegroundColorSpan mAlphaForegroundColorSpan;
    private SpannableString mSpannableString;
    //新近添加结束

    private View mView;
    private TextView mHeaderRefresh;
    private RotateAnimation rotateAnimation;
    private ArrayList<CarScoreAndListModel.ResDataBean> fakes;
    private IndexCarScoreAdapter carAdapter;
    private DotCircularRingView dotCircularRingView;
    private TextView header_refresh_level;
    private TextView header_refresh_car;
    private TextView header_refresh_score;

    public static int proressbar = 50 + new Random().nextInt(50);
    private View mPlaceHolderView;
    private ObjectAnimator anim;
    private ImageView imageView_message;
    private ImageView imageView_menu;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initListViewData();

        initViewData(inflater);

        //TODO 新增加的
        initCheckUpdate();

        setupListView();

        initRota();

        initRefreshTitle();

        return mView;

    }

    private void initCheckUpdate() {

        //新加的
        //TODO
        //得到自动更新的标志
        boolean autoisopen = SPUtils.getBoolean_true(SafeboxApplication.getContext(), Model.AUTOISOPEN);
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

    private void initListViewData() {
        fakes = new ArrayList<CarScoreAndListModel.ResDataBean>();

        httpApi.getCarList("321")
                .enqueue(new Callback<CarScoreAndListModel>() {

                    @Override
                    public void onResponse(Call<CarScoreAndListModel> call, retrofit2.Response<CarScoreAndListModel> response) {
                        //判断是否成功
                        if (response.isSuccessful()) {
                            CarScoreAndListModel responseLimitProductModel = response.body();
                            //判断是否后台返回错误
                            List<CarScoreAndListModel.ResDataBean> res_data = responseLimitProductModel.getRes_data();
                            fakes.addAll(res_data);
                        }
                    }

                    @Override
                    public void onFailure(Call<CarScoreAndListModel> call, Throwable t) {
                        ToastUtil.startShort(getActivity(), "请检查网络");
                    }
                });

    }

    private void initRefreshTitle() {
        //actionbar字体颜色  白色
        mActionBarTitleColor = getResources().getColor(R.color.actionbar_title_color);
        //actionbar字
        mSpannableString = new SpannableString(getString(R.string.noboringactionbar_title));
        mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(mActionBarTitleColor);
    }

    private void initViewData(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.fragment_index, null);
        mSmoothInterpolator = new AccelerateDecelerateInterpolator();
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mActionBarHeight = getResources().getDimensionPixelSize(R.dimen.header_actionbar);
        mActionBarHeight_null = getResources().getDimensionPixelSize(R.dimen.header_actionbar_null);

        mMinHeaderTranslation = -mHeaderHeight + mActionBarHeight;
        mMinHeaderTranslation_null = -mHeaderHeight + mActionBarHeight_null;


        mPlaceHolderView = getHeadView();
        mListView = (JazzyListView) mView.findViewById(R.id.listview);
        imageView_menu = (ImageView) mView.findViewById(R.id.fragment_index_menu);
        imageView_message = (ImageView) mView.findViewById(R.id.fragment_index_message);
        mUpdateMsg = (TextView) mView.findViewById(R.id.fragment_index_msg);

        imageView_menu.setOnClickListener(this);
        imageView_message.setOnClickListener(this);


        mHeader = (RelativeLayout) mPlaceHolderView.findViewById(R.id.header);
        mHeaderPicture = (TextView) mPlaceHolderView.findViewById(R.id.header_picture);
        dotCircularRingView = (DotCircularRingView) mPlaceHolderView.findViewById(R.id.circleView);
        mHeaderRefresh = (TextView) mPlaceHolderView.findViewById(R.id.header_refresh);
        header_refresh_car = (TextView) mPlaceHolderView.findViewById(R.id.header_refresh_car);
        header_refresh_score = (TextView) mPlaceHolderView.findViewById(R.id.header_refresh_score);
        header_refresh_level = (TextView) mPlaceHolderView.findViewById(R.id.header_refresh_level);


        mHeader.setClickable(false);
        mHeaderRefresh.setClickable(false);


        mHeaderRefresh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (rotateAnimation != null) {
                        rotateAnimation.cancel();
                    }
                    proressbar = 40 + new Random().nextInt(60);
                    initRota();

                }
                return true;
            }
        });

    }

    private void initRota() {
        anim = ObjectAnimator.ofFloat(dotCircularRingView, "rotation", 0f, 360f);
        anim.setDuration(5000);
        anim.setRepeatCount(0);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int n = proressbar - 30;
                if (n <= 1) {
                    n = 1;
                }
                int currentBarScore = new Random().nextInt(n);
                dotCircularRingView.setProgress(0 + "%");
                header_refresh_score.setText(currentBarScore + "");
                header_refresh_level.setText("火速计算中...");
            }
        });

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mHeaderRefresh.setClickable(false);

                dotCircularRingView.setProgress("0%");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mHeaderRefresh.setClickable(true);

                header_refresh_score.setText(proressbar + "");
                dotCircularRingView.setProgress(proressbar + "%");
                if (proressbar >= 90) {
                    header_refresh_level.setText("优秀");
                } else if (proressbar >= 80 && proressbar < 90) {
                    header_refresh_level.setText("良好");
                } else if (proressbar >= 60 && proressbar < 80) {
                    header_refresh_level.setText("中等");
                } else {
                    header_refresh_level.setText("较差");
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private void setupListView() {

        mListView.addHeaderView(mPlaceHolderView);
        carAdapter = new IndexCarScoreAdapter(getContext(), R.layout.fragment_index_listview_item, fakes);

        mListView.setTransitionEffect(new ReverseFlyEffect());

        mListView.setAdapter(carAdapter);

        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int scrollY = getScrollY();

                mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation_null));
//                //header_logo --> actionbar icon
                float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
                float ratio_null = clamp(mHeader.getTranslationY() / mMinHeaderTranslation_null, 0.0f, 1.0f);
//                //设置进度条图片变化的动画


                interpolateProgressBar(dotCircularRingView, mSmoothInterpolator.getInterpolation(ratio));
                interpolateProgressBar_text(header_refresh_car, mSmoothInterpolator.getInterpolation(ratio));
                interpolateProgressBar_text_level(header_refresh_level, mSmoothInterpolator.getInterpolation(ratio));
                interpolateProgressBar(header_refresh_score, mSmoothInterpolator.getInterpolation(ratio));

                mHeaderRefresh.setAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));

                if (scrollY <= 20) {
                    mHeaderRefresh.setAlpha(1.0f);
                }
            }
        });

    }

    private View getHeadView() {
        //条目展示
        return getLayoutInflater(new Bundle()).inflate(R.layout.view_header_placeholder, mListView, false);
    }

    private void setTitleAlpha(float alpha) {
        mAlphaForegroundColorSpan.setAlpha(alpha);
        mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mHeaderRefresh.setText(mSpannableString);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position <= 0) {

        } else {
            Intent intent = new Intent(getActivity(), SingleCarLocationInfoActivity.class);
            intent.putExtra("data_no", fakes.get(position - 1).getCar_code());
            intent.putExtra("data_score", fakes.get(position - 1).getScore());
            startActivity(intent);
        }
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    //设置进度条文字的动画
    private void interpolateProgressBar_text_level(View view1, float interpolation) {
        getOnScreenRect(mRect1, view1);

        float scaleX = 1.0F + interpolation * (mRect2.width() / mRect1.width() - 1.0F);
        float scaleY = 1.0F + interpolation * (mRect2.height() / mRect1.height() - 1.0F);
        //XY都往小地方走
        float translationX = 0.35F * (interpolation * (mRect2.left - 100 + mRect2.right - mRect1.left - mRect1.right));
        float translationY = 0.465F * (interpolation * (mRect2.top + 100 + mRect2.bottom - mRect1.top - mRect1.bottom));


        if (scaleX <= 0.3f) {
            scaleX = 0.3f;

        }
        if (scaleY <= 0.3f) {
            scaleY = 0.3f;

        }


        float translationY_pinyi = translationY - mHeader.getTranslationY();


        view1.setTranslationY(translationY_pinyi);
        view1.setTranslationX(translationX);

        view1.setScaleX(scaleX);
        view1.setScaleY(scaleY);

        view1.setAlpha(1);

        if (translationY_pinyi >= 132.0f) {
            view1.setAlpha(0);
        }

    }

    //设置进度条文字的动画
    private void interpolateProgressBar_text(View view1, float interpolation) {
        getOnScreenRect(mRect1, view1);

        float scaleX = 1.0F + interpolation * (mRect2.width() / mRect1.width() - 1.0F);
        float scaleY = 1.0F + interpolation * (mRect2.height() / mRect1.height() - 1.0F);
        //XY都往小地方走
        float translationX = 0.35F * (interpolation * (mRect2.left - 100 + mRect2.right - mRect1.left - mRect1.right));
        float translationY = 0.465F * (interpolation * (mRect2.top + 100 + mRect2.bottom - mRect1.top - mRect1.bottom));


        if (scaleX <= 0.3f) {
            scaleX = 0.3f;

        }
        if (scaleY <= 0.3f) {
            scaleY = 0.3f;

        }


        float translationY_pinyi = translationY - mHeader.getTranslationY();

        view1.setTranslationY(translationY_pinyi);
        view1.setTranslationX(translationX);

        view1.setScaleX(scaleX);
        view1.setScaleY(scaleY);

        view1.setAlpha(1);

        if (translationY_pinyi >= 319.0f) {
            view1.setAlpha(0);
        }
    }


    //设置进度条的动画
    private void interpolateProgressBar(View view1, float interpolation) {
        getOnScreenRect(mRect1, view1);

        float scaleX = 1.0F + interpolation * (mRect2.width() / mRect1.width() - 1.0F);
        float scaleY = 1.0F + interpolation * (mRect2.height() / mRect1.height() - 1.0F);
        //XY都往小地方走
        float translationX = 0.35F * (interpolation * (mRect2.left - 100 + mRect2.right - mRect1.left - mRect1.right));
        float translationY = 0.465F * (interpolation * (mRect2.top + 100 + mRect2.bottom - mRect1.top - mRect1.bottom));

        if (scaleX <= 0.3f) {
            scaleX = 0.3f;
        }
        if (scaleY <= 0.3f) {
            scaleY = 0.3f;
        }

        float translationY_pinyi = translationY - mHeader.getTranslationY();

        view1.setTranslationX(translationX);
        view1.setTranslationY(translationY_pinyi);

        view1.setScaleX(scaleX);
        view1.setScaleY(scaleY);
    }

    //设置刷新按钮的动画
    private void interpolateRefresh(View view1, float interpolation) {
        getOnScreenRect(mRect1, view1);

        float scaleX = 1.0F + interpolation * (mRect2.width() / mRect1.width() - 1.0F);
        float scaleY = 1.0F + interpolation * (mRect2.height() / mRect1.height() - 1.0F);
        //XY都往小地方走
        float translationX = 0.1F * (interpolation * (mRect2.left - 100 + mRect2.right - mRect1.left - mRect1.right));
        float translationY = 0.5F * (interpolation * (mRect2.top + 100 + mRect2.bottom - mRect1.top - mRect1.bottom));


        if (scaleX <= 0.8f) {
            scaleX = 1.0f;
        }
        if (scaleY <= 0.8f) {
            scaleY = 1.0f;
        }

        float translationY_pinyi = translationY - mHeader.getTranslationY();

        view1.setTranslationX(translationX);
        view1.setTranslationY(translationY_pinyi);

        view1.setScaleX(scaleX);
        view1.setScaleY(scaleY);
    }

    ///
    private RectF getOnScreenRect(RectF rect, View view) {
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        return rect;
    }

    public int getScrollY() {
        View c = mListView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 10;//头尾相隔的距离
        if (firstVisiblePosition >= 1) {
            headerHeight = getHeadView().getHeight();
        }
        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_index_menu:
                Intent intent = new Intent(getActivity(), PersonInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.fragment_index_message:
                ToastUtil.startShort(getActivity(), "消息界面");
                break;
        }
    }

    private class myAsync extends AsyncTask<Void, Integer, Void> {
        int duration = 0;
        int current = 0;

        @Override
        protected Void doInBackground(Void... params) {
            do {
                current += 10;

                try {
                    publishProgress(current);
                    Thread.sleep(1000);
                    if (current >= 100) {
                        break;
                    }
                } catch (Exception e) {
                }
            } while (current <= 80);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            System.out.println(values[0]);
            dotCircularRingView.setProgress(values[0] + "%");
        }
    }

    TableSelectListener tableSelectListener = null;

    public TableSelectListener getTableSelectListener() {
        return tableSelectListener;
    }

    public void setTableSelectListener(
            TableSelectListener tableSelectListener) {
        this.tableSelectListener = tableSelectListener;
    }

    public interface TableSelectListener {
        void tableSelect(int position, int tag);
    }
}

