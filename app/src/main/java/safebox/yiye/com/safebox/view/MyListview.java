package safebox.yiye.com.safebox.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by aina on 2016/10/19.
 */

public class MyListview extends ListView {
    public MyListview(Context context) {
        this(context, null);
    }

    public MyListview(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int scrollY = getScrollY();
        return super.onTouchEvent(ev);
    }
}
