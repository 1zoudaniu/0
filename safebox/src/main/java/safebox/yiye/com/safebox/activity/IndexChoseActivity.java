package safebox.yiye.com.safebox.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.beans.CarIndexTwoBean;
import safebox.yiye.com.safebox.constant.Model;
import safebox.yiye.com.safebox.utils.AMapUtil;
import safebox.yiye.com.safebox.utils.ActivityCollector;
import safebox.yiye.com.safebox.utils.ToastUtil;

public class IndexChoseActivity extends AppCompatActivity implements
        AMapLocationListener, LocationSource,
        AMap.OnMapLoadedListener, AMap.OnCameraChangeListener, AMap.OnInfoWindowClickListener,
        AMap.OnMarkerClickListener, AMap.InfoWindowAdapter,
        AMap.OnPOIClickListener, GeocodeSearch.OnGeocodeSearchListener, View.OnClickListener {

    private AppBarLayout mAppBarLayout;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", /*Locale.getDefault()*/Locale.ENGLISH);
    private CompactCalendarView mCompactCalendarView;
    private boolean isExpanded = false;
    private ImageView arrow;
    private LinearLayout datePickerButton;
    private Toolbar toolbar;

    //////
    private MapView mMapView = null;
    private AMap aMap = null;
    private LocationSource.OnLocationChangedListener mOnLocationChangedListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private UiSettings mUiSettings;
    private ArrayList<CarIndexTwoBean> fakes;
    private Marker regeoMarker;
    private StringBuilder addressName = new StringBuilder();
    private LatLonPoint latLonPoint = new LatLonPoint(31.2396997086, 121.4995909338);
    private Marker markerSingleDot=null;
    private LatLng latLngww;
    private Float[] setLatitude;
    private Float[] setLongitude;
    private List<Marker> markerlst;
    private List<Marker> markerlst1;
    private ImageView imageviewBace;
    private String data_no_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_chose);
        ActivityCollector.addActivity(this);
        Intent intent = getIntent();
        data_no_ = intent.getStringExtra("data_no_");

        initView();

        initData();

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);

        initMap();

        initPath();

        initListener();


    }
    private void initMapListener() {
        aMap.setOnMapLoadedListener(this);
        aMap.setOnCameraChangeListener(this);
        aMap.setOnMarkerClickListener(this);
        aMap.setOnInfoWindowClickListener(this);
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        aMap.setOnPOIClickListener(this);
    }
    private void initMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            regeoMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)).draggable(false));
            mUiSettings = aMap.getUiSettings();
            // 自定义系统定位小蓝点
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                    .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
            myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
            myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
            myLocationStyle.strokeWidth(12f);// 设置圆形的边框粗细
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.setMyLocationRotateAngle(180);
            aMap.setLocationSource(this);// 设置定位监听
            mUiSettings.setMyLocationButtonEnabled(true); // 是否显示默认的定位按钮
            mUiSettings.setTiltGesturesEnabled(true);// 设置地图是否可以倾斜
            mUiSettings.setScaleControlsEnabled(true);// 设置地图默认的比例尺是否显示
            mUiSettings.setZoomControlsEnabled(false);
            initMapListener();
        }

        initFirstAllMark();
    }
    //初始化所有的标记
    private void initFirstAllMark() {
        ArrayList<MarkerOptions> markerOptionlst = new ArrayList<MarkerOptions>();
        // 绘制一个虚线三角形
        PolylineOptions polylineOptions = new PolylineOptions();

        for (int i = 0; i < 8; i++) {

            MarkerOptions markOptiopns = new MarkerOptions();

            LatLng allLagng = new LatLng(setLatitude[i], setLongitude[i]);
//绘制虚线的参数坐标
            polylineOptions.add(allLagng);

            markOptiopns.position(allLagng).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))    // 将Marker设置为贴地显示，可以双指下拉看效果
                    .anchor(0.5f,0.5f)
                    .setFlat(true);
            if (i == 0) {
                markOptiopns.icon(BitmapDescriptorFactory.fromResource(R.drawable.dir_start));
            } else if (i == 7) {
                markOptiopns.icon(BitmapDescriptorFactory.fromResource(R.drawable.dir_end));
            } else {
                markOptiopns.icon(BitmapDescriptorFactory.fromResource(R.drawable.point6));
            }
            markerOptionlst.add(markOptiopns);
        }

        polylineOptions.width(5).setDottedLine(true).geodesic(true)
                .color(Color.argb(255, 190, 16, 16));
        Polyline polyline = aMap.addPolyline(polylineOptions);

        markerlst = aMap.addMarkers(markerOptionlst, true);


    }

    private void initListener() {
        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubtitle(dateFormat.format(dateClicked));

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
        datePickerButton.setOnClickListener(this);

        imageviewBace.setOnClickListener(this);
    }

    private void initData() {
        // Set current date to today
        setCurrentDate(new Date());


        fakes = new ArrayList<>();
        setLatitude = Model.INDEX_TWO_Latitude;
        setLongitude = Model.INDEX_TWO_Longitude;
        String[] strings = Model.INDEX_TWO_LISTVIEW;
        for (int i = 0; i < 8; i++) {
            CarIndexTwoBean carIndexInfoBean = new CarIndexTwoBean();
            carIndexInfoBean.setTime(new Random().nextInt(24) + ":" + new Random().nextInt(60));
            carIndexInfoBean.setEvent(strings[i]);
            carIndexInfoBean.setScore("-" + new Random().nextInt(10));
            carIndexInfoBean.setLatitude(setLatitude[i]);
            carIndexInfoBean.setLongitude(setLongitude[i]);
            fakes.add(carIndexInfoBean);
        }
    }

    private void initPath() {
        setSupportActionBar(toolbar);
        setTitle(data_no_);
        // Force English
        mCompactCalendarView.setLocale(TimeZone.getDefault(), Locale.ENGLISH);

        mCompactCalendarView.setShouldDrawDaysHeader(true);
    }

    private void initView() {
        arrow = (ImageView) findViewById(R.id.date_picker_arrow);
        datePickerButton = (LinearLayout) findViewById(R.id.date_picker_button);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        imageviewBace = (ImageView) findViewById(R.id.chose_back);
        // Set up the CompactCalendarView
        mCompactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);

        mMapView  = (MapView) findViewById(R.id.activity_index_chose_mapview);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }

        if (mMapView != null) {
            mMapView.onDestroy();
        }
        if (aMap != null) {
            aMap.clear();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理

        if (mMapView != null) {
            mMapView.onPause();
        }
        deactivate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        mOnLocationChangedListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();

        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mOnLocationChangedListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {

                mOnLocationChangedListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败,请检查网络";
                Log.e("AmapErr", errText);
                ToastUtil.startShort(this, errText);
            }
        }
    }

    @Override
    public void deactivate() {
        mOnLocationChangedListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onMapLoaded() {
        // 设置所有maker显示在当前可视区域地图中
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < 8; i++) {
            LatLng allLagng = new LatLng(setLatitude[i], setLongitude[i]);
            builder.include(allLagng);
        }
        LatLngBounds build = builder.build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(build, 150));
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.poikeywordsearch_uri,
                null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(marker.getTitle());

        TextView snippet = (TextView) view.findViewById(R.id.snippet);
        snippet.setText(marker.getSnippet());
        ImageButton button = (ImageButton) view
                .findViewById(R.id.start_amap_app);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        ToastUtil.startShort(this, "getInfoContents:" + marker.getId());
        return null;
    }
    private void initGeocodeSearch() {
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.GPS);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocodeSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }
    @Override
    public void onPOIClick(Poi poi) {
        poi.getCoordinate();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

//        marker.showInfoWindow();
//
//        if (aMap != null) {
//            for (int i = 0; i < markerlst.size(); i++) {
//                if (marker.equals(markerlst.get(i))) {
//                    addressName = new StringBuilder(fakes.get(i).getTime() + "  " + fakes.get(i).getEvent() + "  扣分" + fakes.get(i).getScore() + "\n");
//                    LatLng latLng = new LatLng(setLatitude[i], setLongitude[i]);
//                    latLonPoint = new LatLonPoint(setLatitude[i], setLongitude[i]);
//                    latLngww = new LatLng(setLatitude[i], setLongitude[i]);
//
//                    initGeocodeSearch();
//                }
//            }
//        }
        return false;
    }

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker, final LatLng latLngww) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        Point startPoint = proj.toScreenLocation(latLngww);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {

                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * latLngww.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * latLngww.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {

        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {

                if (markerSingleDot != null) {
                    markerSingleDot.remove();
                }

//                aMap.clear();
                addressName.append(result.getRegeocodeAddress().getFormatAddress());

                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        AMapUtil.convertToLatLng(latLonPoint), 8));
                LatLng marker1 = new LatLng(39.90403, 116.407525);

                regeoMarker.setPosition(AMapUtil.convertToLatLng(latLonPoint));

                MarkerOptions markOptiopns = new MarkerOptions();
                markOptiopns.position(latLngww).icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))    // 将Marker设置为贴地显示，可以双指下拉看效果
                        .setFlat(true);
                TextView textView = new TextView(getApplicationContext());
                textView.setText(addressName);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundResource(R.drawable.custom_info_bubble);
                markOptiopns.icon(BitmapDescriptorFactory.fromView(textView));

                ArrayList<MarkerOptions> markerOptionlst = new ArrayList<MarkerOptions>();

                markerOptionlst.add(markOptiopns);
                markerlst1 = aMap.addMarkers(markerOptionlst, true);

                markerSingleDot = markerlst1.get(0);

                //设置中心点和缩放比例
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLngww));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(12));

                jumpPoint(markerSingleDot, latLngww);

            } else {
                ToastUtil.startShort(this, "没有明确地址");
            }
        } else {
            ToastUtil.showerror(this, rCode);
        }
    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.chose_back:
                finish();
                break;
            case  R.id.date_picker_button:
                if (isExpanded) {
                    ViewCompat.animate(arrow).rotation(0).start();
                    mAppBarLayout.setExpanded(false, true);
                    isExpanded = false;

                } else {
                    ViewCompat.animate(arrow).rotation(180).start();
                    mAppBarLayout.setExpanded(true, true);
                    isExpanded = true;
                }
                break;
        }
    }
}
