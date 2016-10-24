package safebox.yiye.com.safebox.http;

import java.util.List;

/**
 * Created by aina on 2016/10/24.
 */

public class CarScoreAndListModel {
    /**
     * res_data : [{"id":"00001803","name":"测试公司","score":"","car_code":"沪A01803"},
     * {"id":"08100003","name":"测试公司","score":"","car_code":"沪A53X56"},
     * {"id":"08100007","name":"测试公司","score":"","car_code":"沪DB4616"},
     * {"id":"08100001","name":"测试公司","score":"","car_code":"沪DB4587"},
     * {"id":"12345678","name":"测试公司","score":"","car_code":"晋A01808"},
     * {"id":"00001806","name":"测试公司","score":""},
     * {"id":"08100004","name":"测试公司","score":"","car_code":"沪DB4592"},
     * {"id":"00001802","name":"测试公司","score":"","car_code":"沪A01802"},
     * {"id":"08100005","name":"测试公司","score":"","car_code":"沪DB4619"},
     * {"id":"00001801","name":"测试公司","score":"","car_code":"沪A01801"},
     * {"id":"08100009","name":"测试公司","score":"","car_code":"沪DB4655"},
     * {"id":"08100011","name":"测试公司","score":"","car_code":"沪DF2472"},
     * {"id":"08100010","name":"测试公司","score":"","car_code":"沪DB4646"},
     * {"id":"08100002","name":"测试公司","score":"","car_code":"沪DD0916"}]
     * res_code : 1
     * res_msg : 收消息成功
     */

    public String res_code;
    public String res_msg;
    /**
     * id : 00001803
     * name : 测试公司
     * score :
     * car_code : 沪A01803
     */

    public List<ResDataBean> res_data;

    public static class ResDataBean {
        public String id;
        public String name;
        public String score;
        public String car_code;
    }
}
