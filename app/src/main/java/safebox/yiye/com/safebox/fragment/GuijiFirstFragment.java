package safebox.yiye.com.safebox.fragment;


import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.GrowEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.beans.GuijiExpandGroupBean;
import safebox.yiye.com.safebox.beans.GuijiExpandItemBean;
import safebox.yiye.com.safebox.holder.JuijiExpandGroupHolder;
import safebox.yiye.com.safebox.utils.AMapUtil;
import safebox.yiye.com.safebox.utils.LogUtils;
import safebox.yiye.com.safebox.utils.ToastUtil;

public class GuijiFirstFragment extends Fragment implements AdapterView.OnItemClickListener,
        LocationSource, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener {
    private AMap childmMap;

    private List<GuijiExpandItemBean> childArray;
    private List<GuijiExpandGroupBean> groupArray;
    private JazzyListView expandableListView;

    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient = new AMapLocationClient(getContext());
    private AMapLocationClientOption mLocationOption;
    private LatLonPoint latLonPoint;
    private LatLng latLng;
    private JazzyListViewAdapter jazzyListViewAdapter;
    private MapView mapView_frame;
    private Marker marker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);

        return view;
    }

    private void initData() {
        groupArray = new ArrayList<GuijiExpandGroupBean>();

        for (int i = 0; i < 20; i++) {
            GuijiExpandGroupBean guijiExpandGroupBean = new GuijiExpandGroupBean();
            guijiExpandGroupBean.setCarid("沪C" + new Random().nextInt(10000));
            guijiExpandGroupBean.setCarstatus("行驶中");
            guijiExpandGroupBean.setCarkm(new Random().nextInt(200) + "km");

             if (i % 5 == 0) {
                guijiExpandGroupBean.setLatitude(31.216634f);
                guijiExpandGroupBean.setLongitude(121.61481f);
            } else if (i % 5 == 1) {
                guijiExpandGroupBean.setLatitude(31.316744f);
                guijiExpandGroupBean.setLongitude(121.61491f);
            } else if (i % 5 == 2) {
                guijiExpandGroupBean.setLatitude(31.416854f);
                guijiExpandGroupBean.setLongitude(121.61501f);
            } else if (i % 5 == 3) {
                guijiExpandGroupBean.setLatitude(31.516964f);
                guijiExpandGroupBean.setLongitude(121.61511f);
            } else if (i % 5 == 4) {
                guijiExpandGroupBean.setLatitude(31.617074f);
                guijiExpandGroupBean.setLongitude(121.61521f);
            }


            groupArray.add(guijiExpandGroupBean);
        }
    }

    private AMap aMap_frame;

    /**
     * 初始化AMap对象
     */
    private void initMap() {

        if (aMap_frame == null) {
            aMap_frame = mapView_frame.getMap();
//            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(31.2396997086, 121.4995909338))
//                    .icon(BitmapDescriptorFactory
//                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))    // 将Marker设置为贴地显示，可以双指下拉看效果
//                    .setFlat(true);
//
//            TextView textView = new TextView(getActivity());
//            textView.setText("上海市东方明珠");
//            textView.setGravity(Gravity.CENTER);
//            textView.setTextColor(Color.BLACK);
//            textView.setBackgroundResource(R.drawable.custom_info_bubble);
//            markerOptions.icon(BitmapDescriptorFactory.fromView(textView));
//
//            UiSettings uiSettings = aMap_frame.getUiSettings();
//
            // 自定义系统定位小蓝点
//
//            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(
//                    //15是缩放比例，0是倾斜度，30显示比例
//                    new CameraPosition(new LatLng(31.2396997086, 121.4995909338), 13, 30, BitmapDescriptorFactory.HUE_ROSE));//这是地理位置，就是经纬度。
//            aMap_frame.moveCamera(cameraUpdate);//定位的方法

//            LatLonPoint latLonPoint = new LatLonPoint(31.2396997086, 121.4995909338);
//            aMap_frame.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                    AMapUtil.convertToLatLng(latLonPoint), 11));
//
//
//            MarkerOptions markOptiopns = new MarkerOptions();
//            markOptiopns.position(latLng)
//                    .icon(BitmapDescriptorFactory
//                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))    // 将Marker设置为贴地显示，可以双指下拉看效果
//                    .setFlat(true);
//            TextView textView = new TextView(getContext());
//            textView.setText("dddd");
//            textView.setGravity(Gravity.CENTER);
//            textView.setTextColor(Color.BLACK);
//            textView.setBackgroundResource(R.drawable.custom_info_bubble);
//            markOptiopns.icon(BitmapDescriptorFactory.fromView(textView));
//
//            marker = aMap_frame.addMarker(markOptiopns);


            UiSettings uiSettings = aMap_frame.getUiSettings();

            // 自定义系统定位小蓝点
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                    .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
            myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
            myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
            myLocationStyle.strokeWidth(12f);// 设置圆形的边框粗细
            aMap_frame.setMyLocationStyle(myLocationStyle);
            aMap_frame.setMyLocationRotateAngle(60);
            aMap_frame.setLocationSource(this);// 设置定位监听
            uiSettings.setMyLocationButtonEnabled(false); // 是否显示默认的定位按钮
            uiSettings.setTiltGesturesEnabled(true);// 设置地图是否可以倾斜
            uiSettings.setScaleControlsEnabled(true);// 设置地图默认的比例尺是否显示
            uiSettings.setZoomControlsEnabled(true);

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//        expandableListView.setSelection(2);
//        expandableListView.smoothScrollToPosition(2);
//        expandableListView.setSelection(position);
        view.setSelected(true);

        Float latitude = groupArray.get(position).getLatitude();
        Float longitude = groupArray.get(position).getLongitude();
        latLonPoint = new LatLonPoint(latitude, longitude);
        latLng = new LatLng(latitude, longitude);

        ToastUtil.startShort(getActivity(), "唯独" + groupArray.get(position).getLatitude());

        mapView_frame.setVisibility(View.VISIBLE);


        initGeocodeSearch();

    }

    private void initGeocodeSearch() {
        GeocodeSearch geocodeSearch = new GeocodeSearch(getContext());
        geocodeSearch.setOnGeocodeSearchListener(this);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.GPS);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocodeSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();

        expandableListView = (JazzyListView) getView().findViewById(R.id.expandableListView);
        mapView_frame = (MapView) getView().findViewById(R.id.list_fragment_map);
        mapView_frame.onCreate(savedInstanceState);// 此方法必须重写
        mapView_frame.setVisibility(View.GONE);

        initMap();

        jazzyListViewAdapter = new JazzyListViewAdapter();
        expandableListView.setTransitionEffect(new GrowEffect());
        expandableListView.setAdapter(jazzyListViewAdapter);
        expandableListView.setOnItemClickListener(this);


        expandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                ToastUtil.startShort(getActivity(), "正在改变onScrollonScrollStateChanged");


//                switch (scrollState) {
//                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                        // 手指触屏拉动准备滚动，只触发一次        顺序: 1
//                        mapView_frame.setVisibility(View.GONE);
//                        break;
//                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
//                        // 持续滚动开始，只触发一次                顺序: 2
//                        mapView_frame.setVisibility(View.GONE);
//                        break;
//                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//                        // 整个滚动事件结束，只触发一次            顺序: 4
//                        mapView_frame.setVisibility(View.VISIBLE);
//                        break;
//
//                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

//                ToastUtil.startShort(getActivity(), "正在改变onScroll");


//                int scrollY = expandableListView.getScrollY();
//                LogUtils.showLog("GGGG","VVV"+scrollY);
////                if (scrollY > 5) {
//                    mapView_frame.setVisibility(View.GONE);
////                } else {
////                    mapView_frame.setVisibility(View.VISIBLE);
////                }
//
//
////                if (scaleY < 10) {
////                    ToastUtil.startShort(getActivity(), "正在改变onScroll");
////
////                    mapView_frame.setVisibility(View.GONE);
////                } else {
////                    mapView_frame.setVisibility(View.VISIBLE);
////                }
            }
        });


//        guijiListViewAdapter = new ExpandableListViewaAdapter();
//        expandableListView.setAdapter(guijiListViewAdapter);
//
//        expandableListView.setOnItemClickListener(this);
//
//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                ToastUtil.startShort(getActivity(), "onGroupExpand  childmMap");
//                guijiListViewAdapter.notifyDataSetChanged();
//            }
//        });
//        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                ToastUtil.startShort(getActivity(), "onGroupCollapse  childmMap");
//
//                guijiListViewAdapter.notifyDataSetChanged();
//
//            }
//        });
    }


    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView_frame.onPause();
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView_frame.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView_frame.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }


    /**
     * //         * marker点击时跳动一下
     * //
     */
    public void jumpPoint(final Marker marker, final LatLng latLng) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap_frame.getProjection();
        Point startPoint = proj.toScreenLocation(latLng);
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
                double lng = t * latLng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * latLng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });

    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getActivity());
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

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {

                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {

        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {


                aMap_frame.clear();

                String formatAddress = result.getRegeocodeAddress().getFormatAddress();

                aMap_frame.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        AMapUtil.convertToLatLng(latLonPoint), 11));


                MarkerOptions markOptiopns = new MarkerOptions();
                markOptiopns.position(latLng).icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))    // 将Marker设置为贴地显示，可以双指下拉看效果
                        .setFlat(true);
                TextView textView = new TextView(getContext());
                textView.setText(formatAddress);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundResource(R.drawable.custom_info_bubble);
                markOptiopns.icon(BitmapDescriptorFactory.fromView(textView));

                Marker marker1 = aMap_frame.addMarker(markOptiopns);

                marker1.setPosition(AMapUtil.convertToLatLng(latLonPoint));

                jumpPoint(marker1, latLng);
            } else {

            }
        } else {

        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    private class JazzyListViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return groupArray.size();
        }

        @Override
        public Object getItem(int position) {
            return groupArray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            JuijiExpandGroupHolder groupHolder = null;
            GuijiExpandGroupBean guijiExpandGroupBean = groupArray.get(position);
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.guiji_expand_groupitem, null);
                groupHolder = new JuijiExpandGroupHolder();

                groupHolder.carid = (TextView) convertView.findViewById(R.id.tv_group_carid);
                groupHolder.carstatus = (TextView) convertView.findViewById(R.id.tv_group_status);
                groupHolder.carkm = (TextView) convertView.findViewById(R.id.tv_group_km);

                convertView.setTag(groupHolder);

            } else {
                groupHolder = (JuijiExpandGroupHolder) convertView.getTag();
            }
            groupHolder.carid.setText(guijiExpandGroupBean.getCarid());
            groupHolder.carstatus.setText(guijiExpandGroupBean.getCarstatus());
            groupHolder.carkm.setText(guijiExpandGroupBean.getCarkm());

            return convertView;
        }
    }
//    //////////
//    class ExpandableListViewaAdapter extends BaseExpandableListAdapter implements LocationSource, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener {
//
//        /**
//         * marker点击时跳动一下
//         */
//        public void jumpPoint(final Marker marker, final LatLng latLng) {
//            final Handler handler = new Handler();
//            final long start = SystemClock.uptimeMillis();
//            Projection proj = childmMap.getProjection();
//            Point startPoint = proj.toScreenLocation(latLng);
//            startPoint.offset(0, -100);
//            final LatLng startLatLng = proj.fromScreenLocation(startPoint);
//            final long duration = 1500;
//            final Interpolator interpolator = new BounceInterpolator();
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    long elapsed = SystemClock.uptimeMillis() - start;
//                    float t = interpolator.getInterpolation((float) elapsed
//                            / duration);
//                    double lng = t * latLng.longitude + (1 - t)
//                            * startLatLng.longitude;
//                    double lat = t * latLng.latitude + (1 - t)
//                            * startLatLng.latitude;
//                    marker.setPosition(new LatLng(lat, lng));
//                    if (t < 1.0) {
//                        handler.postDelayed(this, 16);
//                    }
//                }
//            });
//
//        }
//
//
//        public ExpandableListViewaAdapter() {
//        }
//
//        /*-----------------Child */
//        @Override
//        public Object getChild(int groupPosition, int childPosition) {
//            // TODO Auto-generated method stub
//            return childArray.get(groupPosition).getMapViewBean();
//        }
//
//        @Override
//        public long getChildId(int groupPosition, int childPosition) {
//            // TODO Auto-generated method stub
//            return childPosition;
//        }
//
//        @Override
//        public View getChildView(int groupPosition, int childPosition,
//                                 boolean isLastChild, View convertView, ViewGroup parent) {
//
//            convertView = View.inflate(getContext(), R.layout.guiji_expand_childitem, null);
//
//            if (childmMap == null) {
//                childmMap = ((SupportMapFragment) getActivity().getSupportFragmentManager()
//                        .findFragmentById(R.id.expan_item_mapview)).getMap();
//
//                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(31.2396997086, 121.4995909338))
//                        .icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))    // 将Marker设置为贴地显示，可以双指下拉看效果
//                        .setFlat(true);
//
//                TextView textView = new TextView(getActivity());
//                textView.setText("上海市东方明珠");
//                textView.setGravity(Gravity.CENTER);
//                textView.setTextColor(Color.BLACK);
//                textView.setBackgroundResource(R.drawable.custom_info_bubble);
//                markerOptions.icon(BitmapDescriptorFactory.fromView(textView));
//
//                initGeocodeSearch();
//
//                Marker marker = childmMap.addMarker(markerOptions);
//
////                jumpPoint(marker, new LatLng(31.2396997086, 121.4995909338));
//
//                UiSettings uiSettings = childmMap.getUiSettings();
//
//                // 自定义系统定位小蓝点
//                MyLocationStyle myLocationStyle = new MyLocationStyle();
//                myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//                        .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
//                myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
//                myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
//                myLocationStyle.strokeWidth(12f);// 设置圆形的边框粗细
//                childmMap.setMyLocationStyle(myLocationStyle);
//                childmMap.setMyLocationRotateAngle(60);
//                childmMap.setLocationSource(this);// 设置定位监听
//                uiSettings.setMyLocationButtonEnabled(false); // 是否显示默认的定位按钮
//                uiSettings.setTiltGesturesEnabled(true);// 设置地图是否可以倾斜
//                uiSettings.setScaleControlsEnabled(true);// 设置地图默认的比例尺是否显示
//                uiSettings.setZoomControlsEnabled(true);
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(
//                        //15是缩放比例，0是倾斜度，30显示比例
//                        new CameraPosition(new LatLng(31.2396997086, 121.4995909338), 13, 30, BitmapDescriptorFactory.HUE_ROSE));//这是地理位置，就是经纬度。
//                childmMap.moveCamera(cameraUpdate);//定位的方法
//            }
//            return convertView;
//        }
//
//        private void initGeocodeSearch() {
//            GeocodeSearch geocodeSearch = new GeocodeSearch(getContext());
//            geocodeSearch.setOnGeocodeSearchListener(this);
//            RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
//                    GeocodeSearch.GPS);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
//            geocodeSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
//        }
//        @Override
//        public int getChildrenCount(int groupPosition) {
//            return 1;
//        }
//
//        /* ----------------------------Group */
//        @Override
//        public Object getGroup(int groupPosition) {
//            return getGroup(groupPosition);
//        }
//
//        @Override
//        public int getGroupCount() {
//            return groupArray.size();
//        }
//
//        @Override
//        public long getGroupId(int groupPosition) {
//            return groupPosition;
//        }
//
//        @Override
//        public View getGroupView(int groupPosition, boolean isExpanded,
//                                 View convertView, ViewGroup parent) {
//
//            JuijiExpandGroupHolder groupHolder = null;
//            GuijiExpandGroupBean guijiExpandGroupBean = groupArray.get(groupPosition);
//            if (convertView == null) {
//                convertView = View.inflate(getContext(), R.layout.guiji_expand_groupitem, null);
//                groupHolder = new JuijiExpandGroupHolder();
//
//                groupHolder.carid = (TextView) convertView.findViewById(R.id.tv_group_carid);
//                groupHolder.carstatus = (TextView) convertView.findViewById(R.id.tv_group_status);
//                groupHolder.carkm = (TextView) convertView.findViewById(R.id.tv_group_km);
//
//                convertView.setTag(groupHolder);
//
//            } else {
//                groupHolder = (JuijiExpandGroupHolder) convertView.getTag();
//            }
//            groupHolder.carid.setText(guijiExpandGroupBean.getCarid());
//            groupHolder.carstatus.setText(guijiExpandGroupBean.getCarstatus());
//            groupHolder.carkm.setText(guijiExpandGroupBean.getCarkm());
//
//            return convertView;
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return false;
//        }
//
//        @Override
//        public boolean isChildSelectable(int groupPosition, int childPosition) {
//            return true;
//        }
//
//        @Override
//        public void activate(OnLocationChangedListener listener) {
//            mListener = listener;
//            if (mlocationClient == null) {
//                mlocationClient = new AMapLocationClient(getActivity());
//                mLocationOption = new AMapLocationClientOption();
//                //设置定位监听
//                mlocationClient.setLocationListener(this);
//                //设置为高精度定位模式
//                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//                //设置定位参数
//                mlocationClient.setLocationOption(mLocationOption);
//                // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
//                // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
//                // 在定位结束后，在合适的生命周期调用onDestroy()方法
//                // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//                mlocationClient.startLocation();
//            }
//        }
//
//        @Override
//        public void deactivate() {
//            mListener = null;
//            if (mlocationClient != null) {
//                mlocationClient.stopLocation();
//                mlocationClient.onDestroy();
//            }
//            mlocationClient = null;
//        }
//
//        @Override
//        public void onLocationChanged(AMapLocation amapLocation) {
//            if (mListener != null && amapLocation != null) {
//                if (amapLocation != null
//                        && amapLocation.getErrorCode() == 0) {
//
//                    mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
//
//                } else {
//                    String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
//                    Log.e("AmapErr", errText);
//                }
//            }
//        }
//
//        @Override
//        public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
//
//        }
//
//        @Override
//        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
//
//        }
//    }
}

