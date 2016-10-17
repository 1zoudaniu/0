package safebox.yiye.com.testmodule;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;


public class LineColumnDependencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_column_dependency);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec",};

        public final static String[] days = new String[]{"0", "10", "20", "30", "40", "50", "60",};

        private LineChartView chartTop;
        private ColumnChartView chartBottom;

        private LineChartData lineData;
        private ColumnChartData columnData;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_line_column_dependency, container, false);

            // *** TOP LINE CHART ***
            chartTop = (LineChartView) rootView.findViewById(R.id.chart_top);

            // Generate and set data for line chart
            generateInitialLineData();

            // *** BOTTOM COLUMN CHART ***

            chartBottom = (ColumnChartView) rootView.findViewById(R.id.chart_bottom);

//            generateColumnData();

//            initNewData();

            return rootView;
        }

        private void initNewData() {
            int numSubcolumns = 1;
            int numColumns = months.length;

            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;
            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
                }

                axisValues.add(new AxisValue(i).setLabel(months[i]));
//仅为选定的标签
                columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
            }

            columnData = new ColumnChartData(columns);

            columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
            columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2));

            chartBottom.setColumnChartData(columnData);

            // Set value touch listener that will trigger changes for chartTop.
            //触摸听者会触发设定值变化charttop。
            chartBottom.setOnValueTouchListener(new ValueTouchListener());


            // Set selection mode to keep selected month column highlighted.  点击点击下面的点高亮
            chartBottom.setValueSelectionEnabled(true);

            chartBottom.setZoomType(ZoomType.HORIZONTAL);
        }

//        private void generateColumnData() {
//
//            int numSubcolumns = 1;
//            int numColumns = months.length;
//
//            List<AxisValue> axisValues = new ArrayList<AxisValue>();
//            List<Column> columns = new ArrayList<Column>();
//            List<SubcolumnValue> values;
//            for (int i = 0; i < numColumns; ++i) {
//
//                values = new ArrayList<SubcolumnValue>();
//                for (int j = 0; j < numSubcolumns; ++j) {
//                    values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
//                }
//
//                axisValues.add(new AxisValue(i).setLabel(months[i]));
////仅为选定的标签
//                columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
//            }
//
//            columnData = new ColumnChartData(columns);
//
//            columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
//            columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2));
//
//            chartBottom.setColumnChartData(columnData);
//
//            // Set value touch listener that will trigger changes for chartTop.
//            //触摸听者会触发设定值变化charttop。
//            chartBottom.setOnValueTouchListener(new ValueTouchListener());
//
//            // Set selection mode to keep selected month column highlighted.  点击点击下面的点高亮
//            chartBottom.setValueSelectionEnabled(true);
//
//            chartBottom.setZoomType(ZoomType.HORIZONTAL);
//
//            // chartBottom.setOnClickListener(new View.OnClickListener() {
//            //
//            // @Override
//            // public void onClick(View v) {
//            // SelectedValue sv = chartBottom.getSelectedValue();
//            // if (!sv.isSet()) {
//            // generateInitialLineData();
//            // }
//            //
//            // }
//            // });
//
//        }

        /**
         * Generates initial data for line chart. At the begining all Y values are equals 0. That will change when user
         * will select value on column chart.
         * 生成行图的初始数据。在所有的Y值的开始等于0。当用户将在列图上选择值时，将更改。
         */
        private void generateInitialLineData() {
            //折线图的点数
            int numValues = 7;

            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            List<PointValue> values = new ArrayList<PointValue>();
            for (int i = 0; i < numValues; ++i) {
                //点数
                values.add(new PointValue(i, 0));
                //X轴的数据（0---100----）
                axisValues.add(new AxisValue(i).setLabel(days[i]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

            List<Line> lines = new ArrayList<Line>();
            lines.add(line);

            lineData = new LineChartData(lines);
            lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
            lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

            chartTop.setLineChartData(lineData);

            // For build-up animation you have to disable viewport recalculation.
            chartTop.setViewportCalculationEnabled(false);

            // And set initial max viewport and current viewport- remember to set viewports after data.
            Viewport v = new Viewport(0, 110, 6, 0);
            chartTop.setMaximumViewport(v);
            chartTop.setCurrentViewport(v);

            chartTop.setZoomType(ZoomType.HORIZONTAL);
            chartTop.setOnValueTouchListener(new ValueTouchListener());
            generateLineData(Color.BLUE, 100);
        }

        private void generateLineData(int color, float range) {
            // Cancel last animation if not finished.
            chartTop.cancelDataAnimation();

            // Modify data targets
            Line line = lineData.getLines().get(0);// For this example there is always only one line.
            line.setColor(color);
            for (PointValue value : line.getValues()) {
                // Change target only for Y value.
                value.setTarget(value.getX(), 1+(float) Math.random() * range);
            }

            // Start new data animation with 300ms duration;
            chartTop.startDataAnimation(300);
        }

        private class ValueTouchListener implements ColumnChartOnValueSelectListener, LineChartOnValueSelectListener {
            //设置被选择的时候的最大值是100
            @Override
            public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
                generateLineData(value.getColor(), 100);
            }

            //取消选择的时候的值为0
            @Override
            public void onValueDeselected() {

                generateLineData(ChartUtils.COLOR_GREEN, 0);

            }

            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                Toast.makeText(getContext(),lineIndex+" 5 "+ pointIndex+ " 5 "+ value.getX(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
