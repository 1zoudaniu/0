package safebox.yiye.com.safebox.holder;

import android.Manifest;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import safebox.yiye.com.safebox.Globle.SafeboxApplication;
import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.activity.MainActivity;
import safebox.yiye.com.safebox.beans.DownloadBean;
import safebox.yiye.com.safebox.fragment.GuijiFragment;
import safebox.yiye.com.safebox.utils.UpdateUtils;
import zlc.season.practicalrecyclerview.AbstractViewHolder;
import zlc.season.rxdownload.DownloadStatus;
import zlc.season.rxdownload.RxDownload;

import static safebox.yiye.com.safebox.R.id.percent;

/**
 * Author: Season(ssseasonnn@gmail.com)
 * Date: 2016/10/28
 * Time: 09:37
 * FIXME
 */
public class DownloadViewHolder extends AbstractViewHolder<DownloadBean> {
    //开启当前安装的intent意图
    private static final int INSTALLREQUEST = 102;
    @BindView(R.id.img)
    ImageView mImg;
    @BindView(percent)
    TextView mPercent;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.size)
    TextView mSize;
    @BindView(R.id.status)
    Button mStatus;

    DownloadBean data;
    private Context mContext;
    private InstallApkFinished finishedListener;

    public DownloadViewHolder(ViewGroup parent) {
        super(parent, R.layout.download_item);
        ButterKnife.bind(this, itemView);
        mContext = parent.getContext();
    }

    @Override
    public void setData(DownloadBean data) {
        this.data = data;
        Picasso.with(mContext).load(data.image).into(mImg);
        mStatus.setText("开始");

    }

    @OnClick(R.id.status)
    public void onClick() {
        if (data.state == DownloadBean.START) {
            data.state = DownloadBean.PAUSE;
            mStatus.setText("暂停");

            data.subscription = RxPermissions.getInstance(mContext)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .doOnNext(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (!granted) {
                                throw new RuntimeException("no permission");
                            }
                        }
                    })
                    .observeOn(Schedulers.io())
                    .compose(RxDownload.getInstance().transform(data.url, data.name, null))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<DownloadStatus>() {
                        @Override
                        public void onCompleted() {
                            data.state = DownloadBean.DONE;
                            mStatus.setText("已完成");
                            Toast.makeText(mContext, "下载完成，稍后請點擊安装", Toast.LENGTH_SHORT).show();
                            UpdateUtils.installAPK(mContext, data.url);
                            data.unsubscrbe();

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.w("TAG", e);
                            data.state = DownloadBean.START;
                            mStatus.setText("继续");
                            data.unsubscrbe();
                        }

                        @Override
                        public void onNext(final DownloadStatus status) {
                            mProgress.setIndeterminate(status.isChunked);
                            mProgress.setMax((int) status.getTotalSize());
                            mProgress.setProgress((int) status.getDownloadSize());
                            mPercent.setText(status.getPercent());
                            mSize.setText(status.getFormatStatusString());
                        }
                    });
        } else if (data.state == DownloadBean.PAUSE) {
            data.unsubscrbe();
            data.state = DownloadBean.START;
            mStatus.setText("继续");
        }
    }


    public interface InstallApkFinished {
        void finished(String finished);
    }

    public void setFinished(InstallApkFinished finishedListener) {
        this.finishedListener = finishedListener;
    }

}
