package safebox.yiye.com.safebox.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.beans.CarIndexInfoBean;
import safebox.yiye.com.safebox.holder.FragmentIndexListViewHolder;

/**
 * Created by aina on 2016/9/22.
 */

public class IndexCarScoreAdapter extends BaseAdapter {
    private Context mContext;
    private int mFragment_index_listview_item;
    private ArrayList<CarIndexInfoBean> mFakes;

    public IndexCarScoreAdapter(Context context, int fragment_index_listview_item, ArrayList<CarIndexInfoBean> fakes) {
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
        FragmentIndexListViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, mFragment_index_listview_item, null);
            holder = new FragmentIndexListViewHolder();
            holder.iv_car_icon = (RoundedImageView) convertView.findViewById(R.id.index_listview_item_icon);
            holder.tvCarNo = (TextView) convertView.findViewById(R.id.index_listview_item_no);
            holder.tvCarScore = (TextView) convertView.findViewById(R.id.index_listview_item_score);
            convertView.setTag(holder);
        } else {
            holder = (FragmentIndexListViewHolder) convertView.getTag();
        }
        holder.iv_car_icon.setImageResource(mFakes.get(position).getIv_car_icon());
        holder.tvCarNo.setText(mFakes.get(position).getTvCarNo());
        holder.tvCarScore.setText(mFakes.get(position).getTvCarScore());

        return convertView;
    }
}
