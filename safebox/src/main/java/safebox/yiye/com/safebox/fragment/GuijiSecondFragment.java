package safebox.yiye.com.safebox.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import safebox.yiye.com.safebox.Globle.SafeboxApplication;
import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.view.MyMarkerView;


/**
 * Created by aina on 2016/10/17.
 */

public class GuijiSecondFragment extends Fragment implements
        OnChartGestureListener, OnChartValueSelectedListener {


    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    private Context mContext;


    private LineChart mChartMounth;
    private LineChart mChartWeek;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;
    private View mView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = SafeboxApplication.getContext();
        mTfRegular = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Light.ttf");

        mView = View.inflate(mContext, R.layout.fragment_line_column_dependency, null);

        mChartWeek = (LineChart) mView.findViewById(R.id.chart_week);
        mChartMounth = (LineChart) mView.findViewById(R.id.chart_mounth);
        mChartWeek.setOnChartGestureListener(this);
        mChartWeek.setOnChartValueSelectedListener(this);
        mChartWeek.setDrawGridBackground(false);
//        mChartMounth.setOnChartGestureListener(this);
//        mChartMounth.setOnChartValueSelectedListener(this);
//        mChartMounth.setDrawGridBackground(false);

        // no description text
        mChartWeek.getDescription().setEnabled(false);
        mChartMounth.getDescription().setEnabled(false);

        // enable touch gestures  设置可触摸可点击
//        mChartWeek.setTouchEnabled(true);

        // enable scaling and dragging
        mChartWeek.setDragEnabled(true);
        mChartWeek.setScaleEnabled(true);
        mChartMounth.setDragEnabled(true);
        mChartMounth.setScaleEnabled(true);
        // mChartWeek.setScaleXEnabled(true);
        // mChartWeek.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChartWeek.setPinchZoom(true);
        mChartMounth.setPinchZoom(true);

        // set an alternative background color
        // mChartWeek.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it  设置点击后出现的的mark标记
        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
        mv.setChartView(mChartMounth); // For bounds control
        mChartMounth.setMarker(mv); // Set the marker to the chart

//        // x-axis limit line
//        LimitLine llXAxis = new LimitLine(10f, "鬼知道呢");
//        llXAxis.setLineWidth(4f);
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);

        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");
//        XAxis xAxis = holder.chart.getXAxis();

        XAxis xAxis = mChartWeek.getXAxis();

        //设置X坐标字的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        //设置画格子
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.enableGridDashedLine(10f, 10f, 0);

        XAxis xAxisMounth = mChartMounth.getXAxis();

        //设置X坐标字的位置
        xAxisMounth.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisMounth.setTypeface(tf);
        //设置画格子
        xAxisMounth.setDrawGridLines(false);
        xAxisMounth.setDrawAxisLine(true);
        xAxisMounth.enableGridDashedLine(10f, 10f, 0);


        YAxis leftAxis = mChartWeek.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1);
//        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        YAxis leftAxisMounth = mChartMounth.getAxisLeft();
        leftAxisMounth.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1);
//        leftAxis.addLimitLine(ll2);
        leftAxisMounth.setAxisMaximum(100f);
        leftAxisMounth.setAxisMinimum(0f);
        //leftAxis.setYOffset(20f);
        leftAxisMounth.enableGridDashedLine(10f, 10f, 0);
        leftAxisMounth.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxisMounth.setDrawLimitLinesBehindData(true);

        mChartWeek.getAxisRight().setEnabled(false);
        mChartMounth.getAxisRight().setEnabled(false);


        // add data
        setDataWeek(7, 20);
        setDataMounth(30, 30);


        mChartWeek.animateX(0);
        mChartMounth.animateX(0);
        //mChartWeek.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChartWeek.getLegend();
        Legend lMounth = mChartMounth.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        lMounth.setForm(Legend.LegendForm.LINE);


        //清楚顶点数字
        List<ILineDataSet> sets = mChartMounth.getData()
                .getDataSets();

        for (ILineDataSet iSet : sets) {

            LineDataSet set = (LineDataSet) iSet;
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
        mChartMounth.invalidate();


        // // dont forget to refresh the drawing
        mChartWeek.animateXY(3000, 3000);
        mChartMounth.animateXY(3000, 3000);

        return mView;
    }


    protected float getRandom(int range, int startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mChartWeek.animateXY(3000, 3000);
        mChartMounth.animateXY(3000, 3000);
    }

    private void setDataWeek(int count, int range) {

        ArrayList<Entry> valuesWeek = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            int val = (int) ((Math.random() * range) + 80);
            valuesWeek.add(new Entry(i, val));
        }

        LineDataSet set1Week;

        if (mChartWeek.getData() != null &&
                mChartWeek.getData().getDataSetCount() > 0) {
            set1Week = (LineDataSet) mChartWeek.getData().getDataSetByIndex(0);
            set1Week.setValues(valuesWeek);
            mChartWeek.getData().notifyDataChanged();
            mChartWeek.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1Week = new LineDataSet(valuesWeek, "近一周的评分趋势图");

            // set the line to be drawn like this "- - - - - -"
            set1Week.enableDashedLine(10f, 5f, 0f);
            set1Week.enableDashedHighlightLine(10f, 5f, 0f);
            set1Week.setColor(Color.BLACK);
            set1Week.setCircleColor(Color.BLACK);
            set1Week.setLineWidth(1f);
            set1Week.setCircleRadius(5f);
            set1Week.setDrawCircleHole(false);
            set1Week.setValueTextSize(10f);
            set1Week.setDrawFilled(true);
            set1Week.setFormLineWidth(1f);
            set1Week.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1Week.setFormSize(15f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.fade_red);
                set1Week.setFillDrawable(drawable);
            } else {
                set1Week.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSetsWeek = new ArrayList<ILineDataSet>();
            dataSetsWeek.add(set1Week); // add the datasets

            // create a data object with the datasets
            LineData dataWeek = new LineData(dataSetsWeek);

            // set data
            mChartWeek.setData(dataWeek);
        }
    }


    private void setDataMounth(int count, int range) {

        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            int val = (int) ((Math.random() * range) + 70);
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mChartMounth.getData() != null &&
                mChartMounth.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChartMounth.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChartMounth.getData().notifyDataChanged();
            mChartMounth.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "近一月的评分趋势图");

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(5f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(10f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChartMounth.setData(data);
        }
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChartWeek.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
        mChartMounth.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
    }

    @Override
    public void onNothingSelected() {
    }
}

