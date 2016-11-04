package safebox.yiye.com.safebox.http;

import java.util.List;

/**
 * Created by aina on 2016/10/24.
 */

public class CarScoreAndListModel {

    /**
     * res_data : [{"id":"00001803","name":"测试公司","score":"","car_code":"沪A01803"},{"id":"08100003","name":"测试公司","score":"","car_code":"沪A53X56"},{"id":"08100007","name":"测试公司","score":"","car_code":"沪DB4616"},{"id":"08100001","name":"测试公司","score":"","car_code":"沪DB4587"},{"id":"12345678","name":"测试公司","score":"","car_code":"晋A01808"},{"id":"00001806","name":"测试公司","score":""},{"id":"08100004","name":"测试公司","score":"","car_code":"沪DB4592"},{"id":"00001802","name":"测试公司","score":"","car_code":"沪A01802"},{"id":"08100005","name":"测试公司","score":"","car_code":"沪DB4619"},{"id":"00001801","name":"测试公司","score":"","car_code":"沪A01801"},{"id":"08100009","name":"测试公司","score":"","car_code":"沪DB4655"},{"id":"08100011","name":"测试公司","score":"","car_code":"沪DF2472"},{"id":"08100010","name":"测试公司","score":"","car_code":"沪DB4646"},{"id":"08100002","name":"测试公司","score":"","car_code":"沪DD0916"}]
     * res_code : 1
     * res_msg : 收消息成功
     */

    private String res_code;
    private String res_msg;
    /**
     * id : 00001803
     * name : 测试公司
     * score :
     * car_code : 沪A01803
     */

    private List<ResDataBean> res_data;

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }

    public String getRes_msg() {
        return res_msg;
    }

    public void setRes_msg(String res_msg) {
        this.res_msg = res_msg;
    }

    public List<ResDataBean> getRes_data() {
        return res_data;
    }

    public void setRes_data(List<ResDataBean> res_data) {
        this.res_data = res_data;
    }

    public static class ResDataBean {
        private String id;
        private String name;
        private String score;
        private String car_code;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getCar_code() {
            return car_code;
        }

        public void setCar_code(String car_code) {
            this.car_code = car_code;
        }
    }
}
