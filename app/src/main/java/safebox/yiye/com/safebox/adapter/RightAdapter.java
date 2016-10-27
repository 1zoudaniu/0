package safebox.yiye.com.safebox.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.holder.PaihangSecondPinnedtemHolder;
import safebox.yiye.com.safebox.http.PaiHangJsonModel;
import safebox.yiye.com.safebox.view.PinnedSectionListView;

/**
 * Created by shen on 2015/8/22.
 */
public class RightAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    public static final int ITEM = 0;
    public static final int SECTION = 1;

    private Context context;
    private ArrayList<PaiHangJsonModel.DataBean.CategoriesBean> list;
    private LayoutInflater inflater;

    public RightAdapter(Context context, ArrayList<PaiHangJsonModel.DataBean.CategoriesBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public PaiHangJsonModel.DataBean.CategoriesBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PaihangSecondPinnedtemHolder vh=null;
        //对listview进行缓存
        if(convertView==null){
            vh=new PaihangSecondPinnedtemHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.fragment_paihang_second_item, null);
            vh.car=(TextView)convertView.findViewById(R.id.paihang_second_item_carname);
            vh.score= (TextView) convertView.findViewById(R.id.paihang_second_item_score);
            vh.imageView=(ImageView)convertView.findViewById(R.id.paihang_second_item_imageView);
            convertView.setTag(vh);
        }else{
            vh=(PaihangSecondPinnedtemHolder) convertView.getTag();
        }

        PaiHangJsonModel.DataBean.CategoriesBean content= getItem(position);
        vh.car.setText(content.getSubcategories().get(position).getName());
        vh.score.setText(content.getSubcategories().get(position).getItems_count()+"");
        vh.imageView.setImageResource(R.drawable.head002);

        if (content.getName() ) {
            vh.car.setBackgroundColor(Color.GRAY);
            //隐藏置顶栏图片
            vh.imageView.setVisibility(View.GONE);
            vh.car.setTextSize(14);
            //隐藏置顶栏的内容
            vh.score.setVisibility(View.GONE);
        }else{
            vh.car.setBackgroundColor(Color.BLUE);
            vh.imageView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder{
        TextView tvNum;
        LinearLayout llMain;
    }

    /**
     * 根据getItemViewType方法返回结果最终确定是否是标题行
     * @param viewType
     * @return true:标题行   false:普通行
     */
    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType==SECTION;
    }

    /**
     * 确定psListView中哪一行是标题行
     * @param position
     * @return 1：标题行   0：普通行
     */
    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return getItem(position).getName().length()>0?SECTION:ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}