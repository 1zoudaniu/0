package safebox.yiye.com.safebox.constant;

import safebox.yiye.com.safebox.R;

public class Model {


    // 第一个listview的文本数据数组
    public static String[] LISTVIEWTXT = new String[]{"车队安全指数", "百公里平均时速",
            "车队怠速时长", "车队使用率", "百公里加速", "出行恶劣天气占比", "疲劳驾驶次数", "百公里超速次数",
            "百公里怠速次数", "汽车横向抖动次数"};
    // 第二个listview的文本数据
    public static String[][] MORELISTTXT = {
            {"全部0"},
            {"全部1"},
            {"全部2"},
            {"全部3"},
            {"全部4"},
            {"全部5"},
            {"全部5"},
            {"全部6"},
            {"全部7"},
            {"全部8"},
            {"全部9"}
    };



    // 父listview的文本数据数组
    public static String[] INDEX_TWO_LISTVIEW = new String[]{"疲劳驾驶", "长时间怠速",
            "较大速度波动", "较大横向抖动", "超速", "急加速", "急减速", "急转弯"};


    // 父listview的文本数据数组
    public static Float[] INDEX_TWO_Latitude = new Float[]{31.236524f, 31.225526f,
            31.215335f, 31.20366f, 31.223853f, 31.195682f, 31.185526f, 31.215335f};

    // 父listview的文本数据数组
    public static Float[] INDEX_TWO_Longitude = new Float[]{121.61904f, 121.61471f,
            121.881078f, 121.744427f, 121.507345f, 121.307345f, 121.60471f, 121.761078f};

    //自动更新
    public static final String AUTOISOPEN= "AUTOISOPEN";
    //把url地址抽出来
    public static final String SERVERVERSONURL = "http://www.baidu.com";
}
