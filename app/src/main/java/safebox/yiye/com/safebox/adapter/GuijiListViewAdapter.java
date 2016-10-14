package safebox.yiye.com.safebox.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.beans.GuijiExpandGroupBean;
import safebox.yiye.com.safebox.holder.JuijiExpandGroupHolder;

/**
 * Created by aina on 2016/10/12.
 */

public class GuijiListViewAdapter extends BaseAdapter {
    private final List<GuijiExpandGroupBean> groupArray;
    private final Context context;

    public GuijiListViewAdapter(Context context, List<GuijiExpandGroupBean> groupArray) {
        this.context=context;
        this.groupArray=groupArray;
    }

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
            convertView = View.inflate(context, R.layout.guiji_expand_groupitem, null);
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
