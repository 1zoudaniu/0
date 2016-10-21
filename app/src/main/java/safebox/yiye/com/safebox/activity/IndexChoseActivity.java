package safebox.yiye.com.safebox.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.twotoasters.jazzylistview.JazzyListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.utils.ToastUtil;

public class IndexChoseActivity extends AppCompatActivity{


    private AppBarLayout mAppBarLayout;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", /*Locale.getDefault()*/Locale.CHINESE);

    private CompactCalendarView mCompactCalendarView;

    private boolean isExpanded = false;
    private ImageView arrow;
    private RelativeLayout datePickerButton;
    private Toolbar toolbar;
    private MapView mapView;
    private JazzyListView jazzyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_chose);

        initView();

        initData();

        initPath();

        initListener();
    }

    private void initListener() {
        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubtitle(dateFormat.format(dateClicked));
                ToastUtil.startShort(IndexChoseActivity.this, "onDayClick");
                ToastUtil.startShort(IndexChoseActivity.this, dateFormat.format(dateClicked));

                ViewCompat.animate(arrow).rotation(0).start();
                mAppBarLayout.setExpanded(false, true);
                isExpanded = false;

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setSubtitle(dateFormat.format(firstDayOfNewMonth));
            }
        });
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    ViewCompat.animate(arrow).rotation(0).start();
                    mAppBarLayout.setExpanded(false, true);
                    isExpanded = false;
                    ToastUtil.startShort(IndexChoseActivity.this, "dianjieledatePickerButton isExpanded");
                } else {
                    ViewCompat.animate(arrow).rotation(180).start();
                    mAppBarLayout.setExpanded(true, true);
                    isExpanded = true;
                    ToastUtil.startShort(IndexChoseActivity.this, "dianjieledatePickerButton");
                }
            }
        });
    }

    private void initData() {
        // Set current date to today
        setCurrentDate(new Date());
    }

    private void initPath() {
        setSupportActionBar(toolbar);
        setTitle("沪A6903");
        // Force English
        mCompactCalendarView.setLocale(TimeZone.getDefault(), Locale.ENGLISH);

        mCompactCalendarView.setShouldDrawDaysHeader(true);
    }

    private void initView() {
        arrow = (ImageView) findViewById(R.id.date_picker_arrow);
        datePickerButton = (RelativeLayout) findViewById(R.id.date_picker_button);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        // Set up the CompactCalendarView
        mCompactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);

        mapView = (MapView) findViewById(R.id.activity_index_chose_mapview);
        jazzyListView = (JazzyListView) findViewById(R.id.activity_index_chose_listview);

    }

    public void setCurrentDate(Date date) {
        setSubtitle(dateFormat.format(date));
        if (mCompactCalendarView != null) {
            mCompactCalendarView.setCurrentDate(date);
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        TextView tvTitle = (TextView) findViewById(R.id.title);

        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void setSubtitle(String subtitle) {
        TextView datePickerTextView = (TextView) findViewById(R.id.date_picker_text_view);

        if (datePickerTextView != null) {
            datePickerTextView.setText(subtitle);
        }
    }
}
