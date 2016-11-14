package safebox.yiye.com.safebox.adapter;

import android.view.ViewGroup;

import safebox.yiye.com.safebox.beans.DownloadBean;
import safebox.yiye.com.safebox.holder.DownloadViewHolder;
import zlc.season.practicalrecyclerview.AbstractAdapter;

/**
 * Author: Season(ssseasonnn@gmail.com)
 * Date: 2016/10/28
 * Time: 10:02
 * FIXME
 */
public class DownloadAdapter extends AbstractAdapter<DownloadBean, DownloadViewHolder> {
    @Override
    protected DownloadViewHolder onNewCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownloadViewHolder(parent);
    }

    @Override
    protected void onNewBindViewHolder(DownloadViewHolder holder, int position) {
        holder.setData(get(position));
    }
}
