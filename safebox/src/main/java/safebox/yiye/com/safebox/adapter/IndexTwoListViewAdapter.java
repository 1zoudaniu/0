package safebox.yiye.com.safebox.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.holder.IndexTwoListviewHolder;
import safebox.yiye.com.safebox.http.CarIndexSingleModel;

/**
 * Created by aina on 2016/10/9.
 */

public class IndexTwoListViewAdapter extends BaseAdapter {

    private Context mContext;
    private int mFragment_index_listview_item;
    private ArrayList<CarIndexSingleModel> mFakes;

    public IndexTwoListViewAdapter(Context context, int fragment_index_listview_item, ArrayList<CarIndexSingleModel> fakes) {
        this.mContext = context;
        this.mFragment_index_listview_item = fragment_index_listview_item;
        this.mFakes = fakes;
    }


    @Override
    public int getCount() {
        return mFakes.size();
    }

    @Override
    public Object getItem(int position) {
        return mFakes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IndexTwoListviewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, mFragment_index_listview_item, null);
            holder = new IndexTwoListviewHolder();
            holder.iv_two_time = (TextView) convertView.findViewById(R.id.index_two_listview_item_time);
            holder.iv_two_event = (TextView) convertView.findViewById(R.id.index_two_listview_item_event);
            holder.iv_two_score = (TextView) convertView.findViewById(R.id.index_two_listview_item_score);
            convertView.setTag(holder);

        } else {
            holder = (IndexTwoListviewHolder) convertView.getTag();
        }
        holder.iv_two_time.setText(mFakes.get(position).getTime());
        String status1 = mFakes.get(position).getStatus();
        switch (position/5) {
            case 0: status1 = "横向抖动";break;
            case 1: status1 = "急加速";break;
            case 2: status1 = "长时间怠慢";break;
            case 3: status1 = "急减速";break;
            case 4: status1 = "左急转弯";break;
            default:status1 = "右急转弯";
        }
        holder.iv_two_event.setText(status1);
        holder.iv_two_score.setText(mFakes.get(position).getScore());
        if (position == selectItem) {
            convertView.setBackgroundColor(Color.RED);
        } else {
            convertView.setBackgroundColor(Color.BLUE);
        }
        return convertView;
    }

    public void setSelectItemPosition(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;
}
