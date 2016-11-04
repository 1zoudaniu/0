package safebox.yiye.com.safebox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.beans.Left;


/**
 * Created by shen on 2015/8/24.
 */
public class LeftAdapter extends BaseAdapter {
    private int selectedPosition = 0;// 选中的位置
    private Context context;
    private ArrayList<String> list;
    private LayoutInflater inflater;
    public LeftAdapter(Context context, ArrayList<String> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_left_list,null);
            viewHolder=new ViewHolder();
            viewHolder.tvContent=(TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.llMain=(LinearLayout) convertView.findViewById(R.id.ll_main);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvContent.setText(list.get(position));
        if (selectedPosition == position) {
            viewHolder.llMain.setBackgroundResource(R.color.white);
        }else{
            viewHolder.llMain.setBackgroundResource(R.color.linen);
        }

        return convertView;
    }

    static class ViewHolder{
        TextView tvContent;
        LinearLayout llMain;
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
}
