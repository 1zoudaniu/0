package safebox.yiye.com.safebox.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
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
public class RightAdapter extends BaseExpandableListAdapter


//        BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter
{
    private Context context;
    private ArrayList<PaiHangJsonModel.DataBean> dataRightBeen;
    private final ArrayList<PaiHangJsonModel.DataBean.CategoriesBean> group_list;

    public RightAdapter(Context context, ArrayList<PaiHangJsonModel.DataBean.CategoriesBean> dataRightBeen) {
        this.context = context;
        group_list = dataRightBeen;
    }

    @Override
    public int getGroupCount() {
        return group_list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return group_list.get(groupPosition).getSubcategories().size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return group_list.get(groupPosition).getName();
    }

    @Override
    public PaiHangJsonModel.DataBean.CategoriesBean.SubcategoriesBean getChild(int groupPosition, int childPosition) {
        return  group_list.get(groupPosition).getSubcategories().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context,R.layout.fragment_paihang_second_group, null);
            groupHolder = new GroupHolder();
            groupHolder.txt = (TextView) convertView.findViewById(R.id.paihang_second_group_carname);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.txt.setText(group_list.get(groupPosition).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PaihangSecondPinnedtemHolder vh=null;
        PaiHangJsonModel.DataBean.CategoriesBean.SubcategoriesBean subcategoriesBean = group_list.get(groupPosition).getSubcategories().get(childPosition);

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

        vh.car.setText(subcategoriesBean.getName());
        vh.score.setText(subcategoriesBean.getItems_count()+"");
        vh.imageView.setImageResource(R.drawable.head002);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class GroupHolder {
        public TextView txt;
    }
//    public static final int ITEM = 0;
//    public static final int SECTION = 1;
//
//    private Context context;
//    private ArrayList<PaiHangJsonModel.DataBean.CategoriesBean> list;
//    private LayoutInflater inflater;
//
//    public RightAdapter(Context context, ArrayList<PaiHangJsonModel.DataBean.CategoriesBean> list) {
//        this.context = context;
//        this.list = list;
//        inflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public PaiHangJsonModel.DataBean.CategoriesBean getItem(int position) {
//        return list.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//////        switch (getItemViewType(position)) {
//////            case Item.GROUP:
//////
//////                if (convertView == null) {
//////                    convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
//////                }
//////
//////                TextView textView_group = (TextView) convertView.findViewById(android.R.id.text1);
//////                textView_group.setText(getItem(position).text);
//////                textView_group.setTextColor(Color.BLUE);
//////                textView_group.setTextSize(30);
//////                textView_group.setBackgroundColor(Color.GRAY);
//////
//////                break;
//////
//////            case Item.CHILD:
//////                if (convertView == null) {
//////                    convertView = inflater.inflate(R.layout.item, null);
//////                }
//////
//////                TextView textView_child = (TextView) convertView.findViewById(R.id.textView);
//////                textView_child.setText(getItem(position).text);
//////                textView_child.setBackgroundColor(Color.YELLOW);
//////
//////                break;
//////        }
////        return convertView;
////
//        PaihangSecondPinnedtemHolder vh=null;
//        //对listview进行缓存
//        if(convertView==null){
//            vh=new PaihangSecondPinnedtemHolder();
//            convertView= LayoutInflater.from(context).inflate(R.layout.fragment_paihang_second_item, null);
//            vh.car=(TextView)convertView.findViewById(R.id.paihang_second_item_carname);
//            vh.score= (TextView) convertView.findViewById(R.id.paihang_second_item_score);
//            vh.imageView=(ImageView)convertView.findViewById(R.id.paihang_second_item_imageView);
//            convertView.setTag(vh);
//        }else{
//            vh=(PaihangSecondPinnedtemHolder) convertView.getTag();
//        }
//
//        PaiHangJsonModel.DataBean.CategoriesBean content= getItem(position);
//        vh.car.setText(content.getSubcategories().get(position).getName());
//        vh.score.setText(content.getSubcategories().get(position).getItems_count()+"");
//        vh.imageView.setImageResource(R.drawable.head002);
//
//        //根据类型进行不同的布局以及显示
//        if (content ) {
//            vh.car.setBackgroundColor(Color.GRAY);
//            //隐藏置顶栏图片
//            vh.imageView.setVisibility(View.GONE);
//            vh.car.setTextSize(14);
//            //隐藏置顶栏的内容
//            vh.score.setVisibility(View.GONE);
//        }else{
//            vh.car.setBackgroundColor(Color.BLUE);
//            vh.imageView.setVisibility(View.VISIBLE);
//        }
//        return convertView;
//    }
//
//    static class ViewHolder {
//        TextView tvNum;
//        LinearLayout llMain;
//    }
//
//    /**
//     * 根据getItemViewType方法返回结果最终确定是否是标题行
//     *
//     * @param viewType
//     * @return true:标题行   false:普通行
//     */
//    @Override
//    public boolean isItemViewTypePinned(int viewType) {
//        return viewType == SECTION;
//    }
//
//    /**
//     * 确定psListView中哪一行是标题行
//     *
//     * @param position
//     * @return 1：标题行   0：普通行
//     */
//    @Override
//    public int getItemViewType(int position) {
//        // TODO Auto-generated method stub
//        return getItem(position).getName().length() > 0 ? SECTION : ITEM;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }
}