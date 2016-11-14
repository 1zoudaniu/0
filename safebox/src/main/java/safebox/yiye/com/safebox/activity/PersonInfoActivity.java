package safebox.yiye.com.safebox.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.adapter.DownloadAdapter;
import safebox.yiye.com.safebox.beans.DownloadBean;
import safebox.yiye.com.safebox.holder.DownloadViewHolder;
import safebox.yiye.com.safebox.utils.ActivityCollector;
import safebox.yiye.com.safebox.utils.SPUtils;
import zlc.season.practicalrecyclerview.PracticalRecyclerView;

public class PersonInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    @BindView(R.id.recycler)
    PracticalRecyclerView mRecycler;

    private DownloadAdapter mAdapter;
    private String weixin = "http://dldir1.qq.com/weixin/android/weixin6327android880.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        initView();

        mAdapter = new DownloadAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapterWithLoading(mAdapter);

        loadData();


    }

    private void loadData() {
        Resources res = getResources();
        final String[] names = res.getStringArray(R.array.name);
        final String[] images = res.getStringArray(R.array.image);
        final String[] urls = res.getStringArray(R.array.url);
        List<DownloadBean> list = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            DownloadBean temp = new DownloadBean();
            temp.name = names[i];
            temp.image = images[i];
            temp.url = urls[i];
            temp.state = DownloadBean.START;
            list.add(temp);
        }
        mAdapter.addAll(list);
    }

    private void unsubscribe() {
        List<DownloadBean> list = mAdapter.getData();
        for (DownloadBean each : list) {
            each.unsubscrbe();
        }
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        button = (Button) findViewById(R.id.exit);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.exit:
                ActivityCollector.finishAll();
                SPUtils.delString(PersonInfoActivity.this, "login_safebox");
                Intent intent = new Intent(PersonInfoActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    class Finished implements DownloadViewHolder.InstallApkFinished {
        @Override
        public void finished(String finished) {
            if (finished.equals("finish")) {
                mRecycler.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(PersonInfoActivity.this, "anzhuang", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
