package safebox.yiye.com.safebox.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import safebox.yiye.com.safebox.http.HttpApi;
import safebox.yiye.com.safebox.utils.BaseUrl;

/**
 * @author Chance
 *         <p/>
 *         进行了Retrofit的初始化
 */
public abstract class BaseFragment extends Fragment {


    public HttpApi httpApi;

    /**
     * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     */
    public boolean onBackPressed(){
        return false;
    }
    protected BackHandledInterface mBackHandledInterface;

    /**
     * http 接口初始化
     */
//    public HttpApi httpApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化接口
        if (!(getActivity() instanceof BackHandledInterface)) {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        } else {
            this.mBackHandledInterface = (BackHandledInterface) getActivity();
        }

        httpApi = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HttpApi.class);
    }

    /**
     * 统一的显示错误提示的方法
     * 逻辑根据需要自己写
     *
     * @param error
     */
    public void showError(String error) {
//		Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }





    @Override
    public void onStart() {
        super.onStart();
        //告诉FragmentActivity，当前Fragment在栈顶
        mBackHandledInterface.setSelectedFragment(this);
    }

    public interface BackHandledInterface {

        public abstract void setSelectedFragment(BaseFragment selectedFragment);
    }
}
