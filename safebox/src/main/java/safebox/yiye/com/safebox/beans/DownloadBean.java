package safebox.yiye.com.safebox.beans;

import rx.Subscription;
import zlc.season.practicalrecyclerview.ItemType;

/**
 * Author: Season(ssseasonnn@gmail.com)
 * Date: 2016/10/28
 * Time: 09:30
 * FIXME
 */
public class DownloadBean implements ItemType {
    public static final int START = 0;
    public static final int PAUSE = 1;
    public static final int DONE = 2;

    public String name;
    public String url;
    public String image;
    public int state;
    public Subscription subscription;

    /**
     * 取消订阅
     */
    public void unsubscrbe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public int itemType() {
        return 0;
    }
}
