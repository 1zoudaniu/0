package safebox.yiye.com.safebox.beans;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;


/**
 * Created by aina on 2016/9/13.
 */
public class CarIndexInfoBean implements Serializable{
    private int iv_car_icon;
    private String tvCarNo;
    private String tvCarScore;

    public int getIv_car_icon() {
        return iv_car_icon;
    }

    public void setIv_car_icon(int iv_car_icon) {
        this.iv_car_icon = iv_car_icon;
    }

    public String getTvCarNo() {
        return tvCarNo;
    }

    public void setTvCarNo(String tvCarNo) {
        this.tvCarNo = tvCarNo;
    }

    public String getTvCarScore() {
        return tvCarScore;
    }

    public void setTvCarScore(String tvCarScore) {
        this.tvCarScore = tvCarScore;
    }
}
