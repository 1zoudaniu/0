package safebox.yiye.com.testmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

import safebox.yiye.com.testmodule.eventbusdemo.MainActivity;

public class ScrollingActivity extends AppCompatActivity {

    private SimpleLineChart mSimpleLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        mSimpleLineChart = (SimpleLineChart) findViewById(R.id.simpleLineChart);
        String[] xItem = {"1", "2", "3", "4", "5", "6", "7"};
        String[] yItem = {"10k", "20k", "30k", "40k", "50k"};
        if (mSimpleLineChart == null)
            Log.e("wing", "null!!!!");
        mSimpleLineChart.setXItem(xItem);
        mSimpleLineChart.setYItem(yItem);
        HashMap<Integer, Integer> pointMap = new HashMap();
        for (int i = 0; i < xItem.length; i++) {
            pointMap.put(i, (int) (Math.random() * 5));
        }
        mSimpleLineChart.setData(pointMap);

        findViewById(R.id.butto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScrollingActivity.this, LineColumnDependencyActivity.class);
                startActivity(intent);
            }
        });
    }

}
