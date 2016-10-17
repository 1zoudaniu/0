package safebox.yiye.com.safebox.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.utils.ToastUtil;

/**
 * Created by aina on 2016/10/17.
 */

public class GuijiSecondFragment extends Fragment {

    public final static String[] days = new String[]{"1", "2", "3", "4", "5", "6", "7",};

    private LineChartView chartTop;


    private LineChartData lineData;

    public GuijiSecondFragment() {
    }
//    99CC00

    //一下是一个月的
    private LineChartView chart;
    private LineChartData data;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 31;

    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    //出现阴影面积
    private boolean isFilled = true;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    //点击后出现具体数字
    private boolean hasLabelForSelected = true;
    private boolean pointsHaveDifferentColor;

    //一下是一个周的
    private LineChartView chart_week;
    private LineChartData data_week;
    private int numberOfLines_week = 1;
    private int maxNumberOfLines_week = 4;
    private int numberOfPoints_week = 7;

    float[][] randomNumbersTab_week = new float[maxNumberOfLines_week][numberOfPoints_week];

    private boolean hasAxes_week = true;
    private boolean hasAxesNames_week = true;
    private boolean hasLines_week = true;
    private boolean hasPoints_week = true;
    private ValueShape shape_week = ValueShape.CIRCLE;
    //出现阴影面积
    private boolean isFilled_week = true;
    private boolean hasLabels_week = true;
    private boolean isCubic_week = false;
    //点击后出现具体数字
    private boolean hasLabelForSelected_week = false;
    private boolean pointsHaveDifferentColor_week;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_line_column_dependency, container, false);
//一个周的第一个版本
//        // *** TOP LINE CHART ***
//        chartTop = (LineChartView) rootView.findViewById(R.id.chart_top);
//
//        // Generate and set data for line chart
//        generateInitialLineData();
//
//        generateLineData(ChartUtils.COLOR_GREEN, 100);

        //一下是一个周的
        chart_week = (LineChartView) rootView.findViewById(R.id.chart_week);
        pointsHaveDifferentColor_week = !pointsHaveDifferentColor_week;
        // Generate some random values.
        generateValues_week();
        chart_week.setValueSelectionEnabled(hasLabelForSelected_week);
        generateData_week();

        // Disable viewport recalculations, see toggleCubic() method for more info.
        chart_week.setViewportCalculationEnabled(false);
        resetViewport_week();


        //一下是一个月的
        chart = (LineChartView) rootView.findViewById(R.id.chart_mounth);
        pointsHaveDifferentColor = !pointsHaveDifferentColor;
        // Generate some random values.
        generateValues();

        generateData();

        // Disable viewport recalculations, see toggleCubic() method for more info.
        chart.setViewportCalculationEnabled(false);
        chart.setValueSelectionEnabled(hasLabelForSelected);

        resetViewport();

        return rootView;
    }
    //一个周的第一个版本
//
//    /**
//     * Generates initial data for line chart. At the begining all Y values are equals 0. That will change when user
//     * will select value on column chart.
//     */
//    private void generateInitialLineData() {
//        int numValues = 7;
//
//        List<AxisValue> axisValues = new ArrayList<AxisValue>();
//        List<PointValue> values = new ArrayList<PointValue>();
//        for (int i = 0; i < numValues; ++i) {
//            values.add(new PointValue(i, 0));
//            axisValues.add(new AxisValue(i).setLabel(days[i]));
//        }
//
//        Line line = new Line(values);
//        line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);
//
//        List<Line> lines = new ArrayList<Line>();
//        lines.add(line);
//
//        lineData = new LineChartData(lines);
//        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
//        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));
//
//        chartTop.setLineChartData(lineData);
//
//        // For build-up animation you have to disable viewport recalculation.
//        chartTop.setViewportCalculationEnabled(false);
//
//        // And set initial max viewport and current viewport- remember to set viewports after data.
//        Viewport v = new Viewport(0, 100, 6, 0);
//        chartTop.setMaximumViewport(v);
//        chartTop.setCurrentViewport(v);
//
//        chartTop.setZoomType(ZoomType.HORIZONTAL);
//
//        chartTop.setOnValueTouchListener(new LineChartOnValueSelectListener() {
//            @Override
//            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
//                int i = (int) (value.getX() + 1);
//
//                float y = value.getY();
//                ToastUtil.startShort(getActivity(),"第"+ i +"天的评分为："+ y +"分");
//            }
//
//            @Override
//            public void onValueDeselected() {
//
//            }
//        });
//    }
//
//    private void generateLineData(int color, float range) {
//        // Cancel last animation if not finished.
//        chartTop.cancelDataAnimation();
//
//        // Modify data targets
//        Line line = lineData.getLines().get(0);// For this example there is always only one line.
//        line.setColor(color);
//        for (PointValue value : line.getValues()) {
//            // Change target only for Y value.
//            value.setTarget(value.getX(), (float) Math.random() * range);
//        }
//
//        // Start new data animation with 300ms duration;
//        chartTop.startDataAnimation(300);
//    }
//
//    private class ValueTouchListener implements ColumnChartOnValueSelectListener {
//
//        @Override
//        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
//            generateLineData(value.getColor(), 100);
//        }
//
//        @Override
//        public void onValueDeselected() {
//
//            generateLineData(ChartUtils.COLOR_GREEN, 0);
//
//        }
//    }
    //一下是一个周的

    /**
     * 生成点的数据
     */
    private void generateValues_week() {
        for (int i = 0; i < maxNumberOfLines_week; ++i) {
            for (int j = 0; j < numberOfPoints_week; ++j) {
                randomNumbersTab_week[i][j] = (float) Math.random() * 100f;
            }
        }
    }

    /**
     * 设置视图的点数  起点终点  最大值
     */
    private void resetViewport_week() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart_week.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 1;
        v.right = numberOfPoints_week-1;
        chart_week.setMaximumViewport(v);
        chart_week.setCurrentViewport(v);
    }

    private void generateData_week() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines_week; ++i) {

            List<PointValue> values_week = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints_week; j++) {
                values_week.add(new PointValue(j, randomNumbersTab_week[i][j]));
            }

            Line line = new Line(values_week);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(shape_week);
            line.setCubic(isCubic_week);
            line.setFilled(isFilled_week);
            line.setHasLabels(hasLabels_week);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected_week);
            line.setHasLines(hasLines_week);
            line.setHasPoints(hasPoints_week);
            if (pointsHaveDifferentColor_week) {
//                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);

            }
            lines.add(line);
        }

        data_week = new LineChartData(lines);

        if (hasAxes_week) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
//            if (hasAxesNames) {
//                axisX.setName("日期");
//                axisY.setName("评分");
//            }
            data_week.setAxisXBottom(axisX);
            data_week.setAxisYLeft(axisY);
        } else {
            data_week.setAxisXBottom(null);
            data_week.setAxisYLeft(null);
        }

        data_week.setBaseValue(Float.NEGATIVE_INFINITY);
        chart_week.setLineChartData(data_week);

    }

    //一下是一个月的

    /**
     * 生成点的数据
     */
    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 100f;
            }
        }
    }


    /**
     * 设置视图的点数  起点终点  最大值
     */
    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 0;
        v.right = numberOfPoints;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }

    private void generateData() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; j++) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            if (pointsHaveDifferentColor) {
//                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);

            }
            lines.add(line);
        }

        data = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
//            if (hasAxesNames) {
//                axisX.setName("日期");
//                axisY.setName("评分");
//            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);

    }
}

