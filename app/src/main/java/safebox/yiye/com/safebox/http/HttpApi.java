package safebox.yiye.com.safebox.http;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by CC on 2016/7/2.
 * Hello wolrd
 */
public interface HttpApi {
    /**
     * 首页  车队名字
     *
     * @return
     */
    @GET("getRealTimeCarInfoByOri?tel=321")
    Call<CarScoreAndListModel> getCarList();


//    @GET("getRealTimeCarInfoByOri")
//    Call<CarScoreAndListModel> getCarList();
//
////    /**
//     * 获取热门搜索关键字
//     *
//     * @return
//     */
//    @GET("search/recommend")
//    Call<ResponseSearchHotKeywordModel> getHotKeyWorld();

//
//    /**
//     * 关键字搜索商品列表
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("search")
//    Call<ResponseProductListModel> searchProdut(@FieldMap Map<String, String> params);
//
//    /***
//     * 进入购物车
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("cart")
//    Call<ResponseShopCarModel> goShopping(@FieldMap Map<String, String> params);
//
//    /***
//     * 添加购物车
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("cart/add")
//    Call<ResponseShopCarModel> addCar(@FieldMap Map<String, String> params);
//
//    /***
//     * 修改数量
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("cart/edit")
//    Call<ResponseShopCarModel> updateCount(@FieldMap Map<String, String> params);
//
//
//    /***
//     * 删除商品
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("cart/delete")
//    Call<ResponseShopCarModel> deleteGoods(@FieldMap Map<String, String> params);
//
//    /***
//     * 付款
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("checkout")
//    Call<ResponseOrderListModel> payMoney(@FieldMap Map<String, String> params);
//
//    /***
//     * 提交购物车,生成订单
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("ordersumbit")
//    Call<ResponseOrderModel> createOrder(@FieldMap Map<String, String> params);
//
//
//    /***
//     * 订单列表
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("ResponseOrderListModel")
//    Call<OrderInfoModel> showOrderList(@FieldMap Map<String, String> params);
//
//    /***
//     * 取消订单
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("ResponseOrderListModel")
//    Call<OrderInfoModel> orderCancel(@FieldMap Map<String, String> params);
//
//
//    /**
//     * 删除订单
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("ResponseOrderListModel")
//    Call<OrderInfoModel> orderDelete(@FieldMap Map<String, String> params);
//
//    /***
//     * 地址列表
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("addresslist")
//    Call<ResponseAddressModel> addressList(@FieldMap Map<String, String> params);
//
//    /***
//     * 地址三级列表
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("addressarea")
//    Call<ResponseAddressModel> addressarea(@FieldMap Map<String, String> params);
//
//    /***
//     * 地址保存
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("addresssave")
//    Call<ResponseAddressModel> addressSave(@FieldMap Map<String, String> params);
//
//    /***
//     * 设置默认地址
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("addressdefault")
//    Call<ResponseAddressModel> addressdeFault(@FieldMap Map<String, String> params);
//
//    /***
//     * 删除地址
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("addressdelete")
//    Call<ResponseAddressModel> addressDelete(@FieldMap Map<String, String> params);
//
//    /***
//     * 获取地址
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("addressDetail")
//    Call<ResponseAddressModel> addressDetail(@FieldMap Map<String, String> params);
//
//    /***
//     * 获取默认地址
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("addressDefaultDetail")
//    Call<ResponseAddressModel> addressDefaultDetail(@FieldMap Map<String, String> params);
//
//    /***
//     * 用户注册
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("register")
//    Call<ResponseUserInfoModel> registerDetail(@FieldMap Map<String, String> params);
//
//    //用户登录
//    @FormUrlEncoded
//    @POST("login")
//    Call<ResponseUserInfoModel> loginDetail(@FieldMap Map<String, String> params);
//
//    //用户注销
//    @FormUrlEncoded
//    @POST("logout")
//    Call<ResponseUserInfoModel> logoutDetail(@FieldMap Map<String, String> params);
//
//    //帮助中心
//    @FormUrlEncoded
//    @POST("help")
//    Call<HelperParentModel> postHelpData(@Field("version") String version);
//
//
//    @GET("help_detail")
//    Call<ResponseHelperDetailModel> getHelpItemData(@Query("id") String id);
//
//    @FormUrlEncoded
//    @POST("orderlist")
//    Call<ResponseOrderListModel> postOrderlist(@FieldMap Map<String, String> params);
//
//
//    @GET("userinfo")
//    Call<ResponseUserInfoModel> getUserInfo(@QueryMap Map<String, String> params);
//
//    @GET("addressDetail")
//    Call<ResponseAddressModel> getAddress(@QueryMap Map<String, String> params);
//
//
//    //限时抢购
//    @GET("limitbuy?page=1&pageNum=10")
//    Call<ResponseLimitProductModel> getlimitproduct();
//
//    //促销快报
//    @GET("topic?page=1&pageNum=10")
//    Call<ResponseTopicModel> getSalesPromotion();
//
//    @FormUrlEncoded
//    @POST("orderdelete")
//    Call<BaseResponse> postOrderDelete(@FieldMap Map<String, String> params);
//
//    //新品
//    @GET("newproduct?page=1&pageNum=10&orderby=saleDown")
//    Call<ResponseProductListModel> getNewProduct();
//
//    //热门
//    @GET("newproduct?page=1&pageNum=10&orderby=saleDown")
//    Call<ResponseProductListModel> getHotProduct();
//
//    @GET("addresslist")
//    Call<ResponseAddressModel> getAddresslist(@QueryMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("addresssave")
//    Call<ResponseAddressModel> saveAddress(@FieldMap Map<String, String> params);
//
//    @GET("productlist")
//    Call<ResponseProductListModel>
//    getProductTable(@QueryMap Map<String, String> params);
//
//    /**
//     * 商品详情
//     *
//     * @param params
//     * @return
//     */
//    @GET("product")
//    Call<ResponseProductDetailModel>
//    getProductModel(@QueryMap Map<String, String> params);
//
//    /**
//     * 跳转购物车
//     *
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("ordersumbit")
//    Call<ResponseShopCarModel> postToShopCar(@FieldMap Map<String, String> params);
//
//    //限时抢购的商品
//    @GET("limitbuy")
//    Call<ResponseProductListModel> getlimitbuyProduct(@QueryMap Map<String, String> params);
//
//    @GET("{path1}/{path2}")
////注意：如果路径用“/”分割，就需要使用多个参数表示
//    Call<ResponseProductListModel> getSalesProduct(@Path("path1") String path1, @Path("path2") String path2, @QueryMap Map<String, String> params);
//
//
//    @GET("addressDefaultDetail")
//    Call<ResponseAddressModel> getAddressDefaultDetail(@QueryMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("addressdelete")
//    Call<BaseResponse> getAddressdelete(@FieldMap Map<String, String> params);
//
//    //获取收藏夹里的参数
//    @GET("favorites")
//    Call<ResponseLimitProductModel> getFavorites(@QueryMap Map<String, String> params);
//    /**
//     * 添加到收藏夹
//     */
//    @GET("addfavorites")
//    Call<ResponseLimitProductModel> setFavorites(@QueryMap Map<String, String> params);
//
//    @GET("brand")
//    Call<ResponseSuggestModel> getSuggestBrand();
}
