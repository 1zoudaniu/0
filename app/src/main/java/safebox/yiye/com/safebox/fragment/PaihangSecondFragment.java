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

import static com.loc.e.i;

/**
 * Created by aina on 2016/10/21.
 */

public class PaihangSecondFragment extends  BaseFragment {

    private RightAdapter rightAdapter;
    private LeftAdapter leftAdapter;
    private PinnedSectionListView pslvRight;
    private ListView lvLeft;
    private ArrayList<Left> alLeft;
    private int posi=0;
    private int pos=0;
    private int first=0;
    private ArrayList<String> alString;
    private ArrayList<String> dataLeftBeen;
    private ArrayList<PaiHangJsonModel.DataBean.CategoriesBean> dataRightBeen;
    private ExpandableListView expandableListView;
    private PaiHangJsonModel paiHangJsonModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = View.inflate(getContext(), R.layout.fragment_paihang_second, null);

        initView(mView);

        alLeft=new ArrayList<>();
        alString = new ArrayList<>();

        initLfetData();

        initAdapter();

        initListener();

        return mView;
    }

    private void initListener() {
        /** 点击左边ListView重定位右边psListView中数据**/
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                pslvRight.setSelection(Integer.parseInt(alLeft.get(position).info));
//                posi = position;
                leftAdapter.setSelectedPosition(position);
                leftAdapter.notifyDataSetInvalidated();
                ToastUtil.startShort(getActivity(), "动起来");

                List<PaiHangJsonModel.DataBean.CategoriesBean> categories = paiHangJsonModel.getData().get(position).getCategories();
                initRightData(categories);
                rightAdapter.notifyDataSetChanged();
            }
        });
        /**得到左边ListView第一列的位置（显示左边ListView被选中但被隐藏的Item时用）**/
        lvLeft.setOnScrollListener(new AbsListView.OnScrollListener() {
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
//        pslvRight.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        leftAdapter=new LeftAdapter(getContext(),dataLeftBeen);
        lvLeft.setAdapter(leftAdapter);


        rightAdapter=new RightAdapter(getContext(),dataRightBeen);
        expandableListView.setAdapter(rightAdapter);
        //遍历所有group,将所有项设置成默认展开
        int count = expandableListView.getCount();
        for (int i=0; i<count; i++)
        {
            expandableListView.expandGroup(i);
        };
    }

    private void initView(View mView) {
        lvLeft=(ListView) mView.findViewById(R.id.lv_left);
//        pslvRight=(PinnedSectionListView)mView.findViewById(R.id.pslv_right);
        expandableListView = (ExpandableListView) mView.findViewById(R.id.expendlist);

    }

    //生成左边的数据
    private void initLfetData(){
        dataLeftBeen = new ArrayList<>();
        dataRightBeen = new ArrayList<>();
        String json = JsonUtils.getJson(getContext(), "safebox.json");
        Gson gson = new Gson();
        paiHangJsonModel = gson.fromJson(json, PaiHangJsonModel.class);
        List<PaiHangJsonModel.DataBean> data = paiHangJsonModel.getData();

        dataLeftBeen.clear();
        for (int i = 0; i < data.size(); i++) {
            String cname = data.get(i).getCname();
            dataLeftBeen.add(cname);
        }

        List<PaiHangJsonModel.DataBean.CategoriesBean> categories = data.get(0).getCategories();
        initRightData(categories);

    }

    private void initRightData(List<PaiHangJsonModel.DataBean.CategoriesBean> dataBean) {
        dataRightBeen.clear();
        dataRightBeen.addAll(dataBean);

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
