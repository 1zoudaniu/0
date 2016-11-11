package safebox.yiye.com.safebox.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.adapter.LeftAdapter;
import safebox.yiye.com.safebox.adapter.RightAdapter;
import safebox.yiye.com.safebox.beans.Left;
import safebox.yiye.com.safebox.http.PaiHangJsonModel;
import safebox.yiye.com.safebox.utils.JsonUtils;
import safebox.yiye.com.safebox.utils.LogUtils;
import safebox.yiye.com.safebox.utils.ToastUtil;
import safebox.yiye.com.safebox.view.PinnedSectionListView;


/**
 * Created by aina on 2016/10/21.
 */

public class PaihangSecondFragment extends  BaseFragment {

    private RightAdapter rightSecondAdapter;
    private LeftAdapter leftSecondAdapter;
    private PinnedSectionListView pslvSecondRight;
    private ListView lvSecondLeft;
    private ArrayList<Left> alLeft;
    private int posi=0;
    private int pos=0;
    private int first=0;
    private ArrayList<String> alSecondString;
    private ArrayList<String> dataSecondLeftBeen;
    private ArrayList<PaiHangJsonModel.DataBean.CategoriesBean> dataSecondRightBeen;
    private ExpandableListView expandableListViewSecond;
    private PaiHangJsonModel paiHangJsonSecondModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = View.inflate(getContext(), R.layout.fragment_paihang_second, null);

        initView(mView);

        alLeft=new ArrayList<>();
        alSecondString = new ArrayList<>();

        initLfetData();

        initAdapter();

        initListener();

        return mView;
    }

    private void initListener() {
        /** 点击左边ListView重定位右边psListView中数据**/
        lvSecondLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                pslvSecondRight.setSelection(Integer.parseInt(alLeft.get(position).info));
//                posi = position;
                leftSecondAdapter.setSelectedPosition(position);
                leftSecondAdapter.notifyDataSetInvalidated();
                ToastUtil.startShort(getActivity(), "动起来");

                List<PaiHangJsonModel.DataBean.CategoriesBean> categories = paiHangJsonSecondModel.getData().get(position).getCategories();
                initRightData(categories);
                rightSecondAdapter.notifyDataSetChanged();
            }
        });
        /**得到左边ListView第一列的位置（显示左边ListView被选中但被隐藏的Item时用）**/
        lvSecondLeft.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                first=firstVisibleItem;
            }
        });

//
//        /**滚动右边psListView刷新左边ListView选中状态**/
//        pslvSecondRight.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if(firstVisibleItem!=pos) {
//                    posi = firstVisibleItem / 5;
//                    initData();
//                    pos=firstVisibleItem;
//                }
//
//            }
//        });
    }

    private void initAdapter() {
        leftSecondAdapter=new LeftAdapter(getContext(),dataSecondLeftBeen);
        lvSecondLeft.setAdapter(leftSecondAdapter);


        rightSecondAdapter=new RightAdapter(getContext(),dataSecondRightBeen);
        expandableListViewSecond.setAdapter(rightSecondAdapter);
        //遍历所有group,将所有项设置成默认展开
        int count = expandableListViewSecond.getCount();
        for (int i=0; i<count; i++)
        {
            expandableListViewSecond.expandGroup(i);
        };
    }

    private void initView(View mView) {
        lvSecondLeft=(ListView) mView.findViewById(R.id.lv_left_second);
//        pslvSecondRight=(PinnedSectionListView)mView.findViewById(R.id.pslv_right);
        expandableListViewSecond = (ExpandableListView) mView.findViewById(R.id.expendlist_second);

    }

    //生成左边的数据
    private void initLfetData(){
        dataSecondLeftBeen = new ArrayList<>();
        dataSecondRightBeen = new ArrayList<>();
        String json = JsonUtils.getJson(getContext(), "safebox.json");
        Gson gson = new Gson();
        paiHangJsonSecondModel = gson.fromJson(json, PaiHangJsonModel.class);
        List<PaiHangJsonModel.DataBean> data = paiHangJsonSecondModel.getData();

        dataSecondLeftBeen.clear();
        for (int i = 0; i < data.size(); i++) {
            String cname = data.get(i).getCname();
            dataSecondLeftBeen.add(cname);
        }

        List<PaiHangJsonModel.DataBean.CategoriesBean> categories = data.get(0).getCategories();
        initRightData(categories);

    }

    private void initRightData(List<PaiHangJsonModel.DataBean.CategoriesBean> dataBean) {
        dataSecondRightBeen.clear();
        dataSecondRightBeen.addAll(dataBean);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
