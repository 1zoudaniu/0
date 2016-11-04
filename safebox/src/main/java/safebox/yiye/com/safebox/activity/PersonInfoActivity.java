package safebox.yiye.com.safebox.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.utils.ActivityCollector;
import safebox.yiye.com.safebox.utils.SPUtils;

public class PersonInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ActivityCollector.addActivity(this);
        initView();
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
}
