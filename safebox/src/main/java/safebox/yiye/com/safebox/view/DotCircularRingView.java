package safebox.yiye.com.safebox.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import safebox.yiye.com.safebox.R;

@SuppressLint("NewApi")
public class DotCircularRingView extends View {

    private int totalPoints = 30;
    private int pieceDegree;
    private int mRadius = 0;
    private int mRingRadius = 9;


    private int mProgress = 0;
    private ValueAnimator valueAnimator;
    private Interpolator interpolator = new AccelerateDecelerateInterpolator();
    private int colorPoints = 0;
    private int color_grey;
    //进度条变化后的颜色
    private int color_highlight;
    private float diameter;
    /**
     * 画笔
     */
    private Paint mPaint;
    private Paint mPaintText;

    private final int mCircleLineStrokeWidth = 8;
    private final int mTxtStrokeWidth = 2;
    private int mMaxProgress = 100;

    public void setProgress(String progressString) {

        String[] split = progressString.split("%");
        int progress = Integer.parseInt(split[0]);

        this.mProgress = progress;

        this.initValueAnimator();

        this.invalidate();

    }
    public int getProgress() {
        return mProgress;
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public DotCircularRingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotCircularRingView(Context context) {
        this(context, null);
    }

    public DotCircularRingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        color_grey = Color.parseColor("#69C7E3");

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DotCircularRingView,
                defStyle, 0);
        //进度条变化后的颜色
        color_highlight = typedArray.getColor(R.styleable.DotCircularRingView_highlightColor, Color.parseColor("#E6E6E6"));
        diameter = typedArray.getDimension(R.styleable.DotCircularRingView_diameter, 0);
        mProgress = typedArray.getInt(R.styleable.DotCircularRingView_progress, 0);
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        pieceDegree = 360 / totalPoints;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (diameter == 0) {
            diameter = getWidth();
        }
        mRadius = ((int) diameter >> 1) - mRingRadius;
        int angle = 0;

        for (int i = 0; i <= totalPoints; i++) {
            int x = (int) (((int) diameter >> 1) + (Math.sin(Math.toRadians(angle)) * mRadius)) + ((getWidth() >> 1) - ((int) diameter >> 1));
            int y = (int) (((int) diameter >> 1) - (Math.cos(Math.toRadians(angle)) * mRadius)) + ((getHeight() >> 1) - ((int) diameter >> 1));

            //Log.d("test", "x: " + x + ",y: " + y);
            if (i > 0 && i <= colorPoints) {
                mPaint.setColor(color_highlight);
            } else {
                mPaint.setColor(color_grey);
            }
            canvas.drawCircle(x, y, mRingRadius, mPaint);


            mPaintText = new Paint();
            // 设置画笔相关属性
            mPaintText.setAntiAlias(true);
            mPaintText.setColor(Color.WHITE);
            canvas.drawColor(Color.TRANSPARENT);
            mPaintText.setStrokeWidth(mCircleLineStrokeWidth);
            mPaintText.setStyle(Paint.Style.STROKE);
            int width = this.getWidth();
            int height = this.getHeight();

            if (width != height) {
                int min = Math.min(width, height);
                width = min;
                height = min;
            }
            // 绘制进度文案显示
//            mPaintText.setStrokeWidth(mTxtStrokeWidth);
//            String text = mProgress + "%";
//            int textHeight = height / 4;
//            mPaintText.setTextSize(textHeight);
//            int textWidth = (int) mPaintText.measureText(text, 0, text.length());
//            mPaintText.setStyle(Paint.Style.FILL);
//            canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 3, mPaintText);
            angle += pieceDegree;
        }
        if (null == valueAnimator) {
            initValueAnimator();
        }
    }

    public int getColorPoints() {
        return colorPoints;
    }

    public void setColorPoints(int colorPoints) {
        this.colorPoints = colorPoints;
    }

    private void initValueAnimator() {
        valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(interpolator);
        setValue(mProgress);
    }

    public void setValue(int mProgress) {
        int colorPoints = (int) (totalPoints * (mProgress / (float) 100));
        valueAnimator.setIntValues(0, colorPoints);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获得当前动画的进度值，整型
                int currentValue = (Integer) valueAnimator.getAnimatedValue();
                setColorPoints(currentValue);
                postInvalidate();
            }
        });
        valueAnimator.setDuration(1000).start();
    }
}
