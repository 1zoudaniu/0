package safebox.yiye.com.safebox.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
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
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.ReverseFlyEffect;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.activity.PersonInfoActivity;
import safebox.yiye.com.safebox.activity.SingleCarLocationInfoActivity;
import safebox.yiye.com.safebox.adapter.IndexCarScoreAdapter;
import safebox.yiye.com.safebox.firstanmi.AlphaForegroundColorSpan;
import safebox.yiye.com.safebox.http.CarScoreAndListModel;
import safebox.yiye.com.safebox.http.HttpApi;
import safebox.yiye.com.safebox.utils.BaseUrl;
import safebox.yiye.com.safebox.utils.ToastUtil;
import safebox.yiye.com.safebox.view.DotCircularRingView;

/**
 * Created by aina on 2016/9/20.
 */
public class IndexFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    public static boolean isFirstRoate = true;

    private volatile boolean isRunning = true;

    //新近添加开始
    private static final String TAG = "NoBoringActionBarActivity";
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

        setupListView();

        initRota();

        initRefreshTitle();

        return mView;

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
                        ToastUtil.startShort(getActivity(),"请检查网络");
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
}

