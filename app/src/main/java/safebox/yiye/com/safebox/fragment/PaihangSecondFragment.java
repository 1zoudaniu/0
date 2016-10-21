package safebox.yiye.com.safebox.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.adapter.PaiHangMainAdapter;
import safebox.yiye.com.safebox.adapter.PaiHangMoreAdapter;
import safebox.yiye.com.safebox.constant.Model;

/**
 * Created by aina on 2016/10/21.
 */

public class PaihangSecondFragment extends  BaseFragment {
    private ListView mainlist;
    private ListView morelist;
    private List<Map<String, Object>> mainList;
    PaiHangMainAdapter mainAdapter;
    PaiHangMoreAdapter moreAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_paihang_frist, null);

        initModle();

        mainlist = (ListView) mView.findViewById(R.id.classify_mainlist);
        morelist = (ListView) mView.findViewById(R.id.classify_morelist);
        mainAdapter = new PaiHangMainAdapter(getActivity(), mainList);
        mainAdapter.setSelectItem(0);
        mainlist.setAdapter(mainAdapter);

        mainlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                initAdapter(Model.MORELISTTXT[position]);
                mainAdapter.setSelectItem(position);
                mainAdapter.notifyDataSetChanged();
            }
        });
        mainlist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // 一定要设置这个属性，否则ListView不会刷新
        initAdapter(Model.MORELISTTXT[0]);

        morelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                moreAdapter.setSelectItem(position);
                moreAdapter.notifyDataSetChanged();
            }
        });
        return mView;
    }

    private void initAdapter(String[] array) {
        moreAdapter = new PaiHangMoreAdapter(getContext(), array);
        morelist.setAdapter(moreAdapter);
        moreAdapter.notifyDataSetChanged();
    }

    private void initModle() {

        mainList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Model.LISTVIEWTXT.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("txt", Model.LISTVIEWTXT[i]);
            mainList.add(map);
        }
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
