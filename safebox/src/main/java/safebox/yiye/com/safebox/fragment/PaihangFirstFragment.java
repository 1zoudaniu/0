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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.adapter.LeftAdapter;
import safebox.yiye.com.safebox.adapter.PaiHangMainAdapter;
import safebox.yiye.com.safebox.adapter.PaiHangMoreAdapter;
import safebox.yiye.com.safebox.adapter.RightAdapter;
import safebox.yiye.com.safebox.beans.Left;
import safebox.yiye.com.safebox.constant.Model;
import safebox.yiye.com.safebox.http.PaiHangJsonModel;
import safebox.yiye.com.safebox.utils.JsonUtils;
import safebox.yiye.com.safebox.utils.ToastUtil;
import safebox.yiye.com.safebox.view.PinnedSectionListView;

/**
 * Created by aina on 2016/10/21.
 */

public class PaihangFirstFragment extends  BaseFragment {
    //注释掉了
//    private ListView mainlist;
//    private ListView morelist;
//    private List<Map<String, Object>> mainList;
//    PaiHangMainAdapter mainAdapter;
//    PaiHangMoreAdapter moreAdapter;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View mView = inflater.inflate(R.layout.fragment_paihang_frist, null);
//
//        initModle();
//
//        mainlist = (ListView) mView.findViewById(R.id.classify_mainlist);
//        morelist = (ListView) mView.findViewById(R.id.classify_morelist);
//        mainAdapter = new PaiHangMainAdapter(getActivity(), mainList);
//        mainAdapter.setSelectItem(0);
//        mainlist.setAdapter(mainAdapter);
//
//        mainlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                initAdapter(Model.MORELISTTXT[position]);
//                mainAdapter.setSelectItem(position);
//                mainAdapter.notifyDataSetChanged();
//            }
//        });
//        mainlist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        // 一定要设置这个属性，否则ListView不会刷新
//        initAdapter(Model.MORELISTTXT[0]);
//
//        morelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                moreAdapter.setSelectItem(position);
//                moreAdapter.notifyDataSetChanged();
//            }
//        });
//        return mView;
//    }
//
//    private void initAdapter(String[] array) {
//        moreAdapter = new PaiHangMoreAdapter(getContext(), array);
//        morelist.setAdapter(moreAdapter);
//        moreAdapter.notifyDataSetChanged();
//    }
//
//    private void initModle() {
//
//        mainList = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < Model.LISTVIEWTXT.length; i++) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("txt", Model.LISTVIEWTXT[i]);
//            mainList.add(map);
//        }
//    }


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

        View mView = View.inflate(getContext(), R.layout.fragment_paihang_first, null);

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
        lvLeft=(ListView) mView.findViewById(R.id.lv_left_first);
//        pslvRight=(PinnedSectionListView)mView.findViewById(R.id.pslv_right);
        expandableListView = (ExpandableListView) mView.findViewById(R.id.expendlist_first);

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
