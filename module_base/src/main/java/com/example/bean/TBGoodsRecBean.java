package com.example.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cuihaohao on 2019/6/14
 * Describe:
 */
public class TBGoodsRecBean {

    /**
     * code : 200
     * data : [{"category_id":"50012598","category_name":"西式糕点","commission_rate":"900","commission_type":"MKT","coupon_amount":"10","coupon_end_time":"2019-06-20","coupon_id":"6d52b391612a4fb5bffd83d351fe0181","coupon_info":"满29.00元减10元","coupon_remain_count":"44500","coupon_share_url":"//uland.taobao.com/coupon/edetail?e=tdYLpfRSfAYNfLV8niU3RwXoB%2BDaBK5LQS0Flu%2FfbSp4QsdWMikAalrisGmre1Id522H2TxuqpI%2FgtGnjoXDCey2B03pxESxPkDM1Gs7PuZkvSUqK96SlyBAb%2BhhLPlfiL368k4KC6HLoBqh%2BNBXEwdnYtArTsWpIXSyO3%2FYDr2MTaLwxM2XJLh8NxHPHyz4MXWvr%2Bgbg0sM1L%2FmEra8u344d%2BzmctAY&&app_pvid=59590_11.11.116.233_2017_1560503538120&ptl=floorId:2836;app_pvid:59590_11.11.116.233_2017_1560503538120;tpp_pvid:100_11.178.152.82_5579_3251560503538124388&xId=O40D6X0QzYNywRsjatnEPpYp4H6qpmNnu3MaZWH7wR3RyeTf4exSRx664vexQmgphj7UMDFTPlNmnC3ubvdHSV&union_lens=lensId:0b0b74e9_0c6a_16b5542524a_1df9","coupon_start_fee":"29.00","coupon_start_time":"2019-06-14","coupon_total_count":"50000","include_dxjh":"false","include_mkt":"true","info_dxjh":"{}","item_description":"新鲜营养美味 厂家直发 1kg整箱20个计重","item_id":"576361024098","item_url":"https://detail.tmall.com/item.htm?id=576361024098","level_one_category_id":"50002766","level_one_category_name":"零食/坚果/特产","nick":"abd巧卖专卖店","num_iid":"576361024098","pict_url":"https://img.alicdn.com/bao/uploaded/i1/4118776387/O1CN011x3I8VvDc5lCFod_!!0-item_pic.jpg","provcity":"广东 韶关","reserve_price":"48","seller_id":"4118776387","shop_dsr":"48219","shop_title":"abd巧卖专卖店","short_title":"abd丹麦鲜1kg手撕整箱糕点面包","small_images":{"string":["https://img.alicdn.com/i2/4118776387/O1CN011x3I7oR9hT6uXZy_!!4118776387.png","https://img.alicdn.com/i2/4118776387/O1CN012fFepL1x3IBT7AFTn_!!4118776387.jpg","https://img.alicdn.com/i2/4118776387/O1CN011x3I7vfMUUziXbc_!!4118776387.jpg","https://img.alicdn.com/i3/4118776387/O1CN01Z3gmAQ1x3IBSTyela_!!4118776387.jpg"]},"title":"ABD丹麦鲜面包1Kg手撕面包整箱营养早餐食品糕点美食零食","tk_total_commi":"62193.4","tk_total_sales":"9989","url":"//s.click.taobao.com/t?e=m%3D2%26s%3DkThFbe8UwLIcQipKwQzePOeEDrYVVa64r4ll3HtqqoxyINtkUhsv0P2EBmPLxhf%2BGnSyCxgm87EACql9sFJBycIYjcEdGCyGKZJ%2BmXzkieu0zUIy5%2FJOG0bXQw2TLQSOQ46khh5dizkGZTSugABF%2B3OSKsmav1kzC2TKqEFvn7inXTIMRtDNDhT%2B6nY8XtX9BulicziMHz%2FPZMUUR31Kpg%3D%3D&scm=null&pvid=100_11.178.152.82_5579_3251560503538124388&app_pvid=59590_11.11.116.233_2017_1560503538120&ptl=floorId:2836;pvid:100_11.178.152.82_5579_3251560503538124388;app_pvid:59590_11.11.116.233_2017_1560503538120&xId=O40D6X0QzYNywRsjatnEPpYp4H6qpmNnu3MaZWH7wR3RyeTf4exSRx664vexQmgphj7UMDFTPlNmnC3ubvdHSV&union_lens=lensId:0b0b74e9_0c6a_16b5542524a_1df9","user_type":"1","volume":"10634","white_image":"https://img.alicdn.com/bao/uploaded/O1CN011x3I7uyNAQufm3F_!!4118776387.png","x_id":"O40D6X0QzYNywRsjatnEPpYp4H6qpmNnu3MaZWH7wR3RyeTf4exSRx664vexQmgphj7UMDFTPlNmnC3ubvdHSV","zk_final_price":"29.9","quanlimit":29,"youhuiquan":10},{"category_id":"50010513","category_name":"传统糕点","commission_rate":"900","commission_type":"MKT","coupon_amount":"5","coupon_end_time":"2019-06-16","coupon_id":"362ce372f6c24e278d7f596846f92b86","coupon_info":"满14.00元减5元","coupon_remain_count":"93000","coupon_share_url":"//uland.taobao.com/coupon/edetail?e=jy1DwABlU%2B8NfLV8niU3RwXoB%2BDaBK5LQS0Flu%2FfbSp4QsdWMikAalrisGmre1Id522H2TxuqpL32rJnfliPypwb5ww4p9WQPkDM1Gs7PuZkvSUqK96SlyBAb%2BhhLPlfiL368k4KC6HLoBqh%2BNBXE9%2B%2Fb%2B55Rxf2FAkhBJHzEkUu%2BiB%2BN24JDI2I5D2ct%2FhiMXWvr%2Bgbg0sM1L%2FmEra8u344d%2BzmctAY&&app_pvid=59590_11.11.116.233_2017_1560503538120&ptl=floorId:2836;app_pvid:59590_11.11.116.233_2017_1560503538120;tpp_pvid:100_11.178.152.82_5579_3251560503538124388&xId=IqjjQhcwGzAIYZDpDtrNfaG9c6GiC0dfLrQbREu2Ducu5ZTXu3i4N0UE1s4sVpCntkoMaKvjniIrRIH9TwE9Qh&union_lens=lensId:0b0b74e9_0c6a_16b5542524a_1dfa","coupon_start_fee":"14.00","coupon_start_time":"2019-06-14","coupon_total_count":"100000","include_dxjh":"true","include_mkt":"true","info_dxjh":"{}","item_description":[],"item_id":"591049968195","item_url":"https://detail.tmall.com/item.htm?id=591049968195","level_one_category_id":"50002766","level_one_category_name":"零食/坚果/特产","nick":"老先生食品旗舰店","num_iid":"591049968195","pict_url":"https://img.alicdn.com/bao/uploaded/i2/3034588402/O1CN01R3Rwvt2BwAHYWV4PE_!!0-item_pic.jpg","provcity":"福建 漳州","reserve_price":"28.9","seller_id":"3034588402","shop_dsr":"48113","shop_title":"老先生食品旗舰店","short_title":"老先生爆馅干吃汤圆休闲整箱麻薯","small_images":{"string":["https://img.alicdn.com/i4/3034588402/O1CN01jvd30X2BwAHEkupxu_!!0-item_pic.jpg","https://img.alicdn.com/i3/3034588402/O1CN01ZGcm0z2BwAFH9zqTh_!!3034588402.jpg","https://img.alicdn.com/i3/3034588402/O1CN01k8RJ8J2BwAFHzT2iK_!!3034588402.jpg","https://img.alicdn.com/i3/3034588402/O1CN015ofkJl2BwAFJ322wV_!!3034588402.jpg"]},"title":"老先生爆馅麻薯干吃汤圆休闲网红小零食四种口味混合整箱糕点美食","tk_total_commi":"46350.7","tk_total_sales":"14338","url":"//s.click.taobao.com/t?e=m%3D2%26s%3DXd4iCAvLYCUcQipKwQzePOeEDrYVVa64r4ll3HtqqoxyINtkUhsv0P2EBmPLxhf%2BGnSyCxgm87EACql9sFJBycIYjcEdGCyGKZJ%2BmXzkieu0zUIy5%2FJOG0bXQw2TLQSOQ46khh5dizm977ekTyTlukwwdylQ6CGKC2TKqEFvn7inXTIMRtDNDlDgCcnUz6klJB2zHs2tzakzNeYKK0PFBA%3D%3D&scm=null&pvid=100_11.178.152.82_5579_3251560503538124388&app_pvid=59590_11.11.116.233_2017_1560503538120&ptl=floorId:2836;pvid:100_11.178.152.82_5579_3251560503538124388;app_pvid:59590_11.11.116.233_2017_1560503538120&xId=IqjjQhcwGzAIYZDpDtrNfaG9c6GiC0dfLrQbREu2Ducu5ZTXu3i4N0UE1s4sVpCntkoMaKvjniIrRIH9TwE9Qh&union_lens=lensId:0b0b74e9_0c6a_16b5542524a_1dfa","user_type":"1","volume":"5090","white_image":"https://img.alicdn.com/bao/uploaded/TB1aoHIX21H3KVjSZFBXXbSMXXa.png","x_id":"IqjjQhcwGzAIYZDpDtrNfaG9c6GiC0dfLrQbREu2Ducu5ZTXu3i4N0UE1s4sVpCntkoMaKvjniIrRIH9TwE9Qh","zk_final_price":"14.99","quanlimit":14,"youhuiquan":5}]
     * totalcount : 37080
     * msg : 淘客商品获取成功
     */

    private int code;
    private String totalcount;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * category_id : 50012598
         * category_name : 西式糕点
         * commission_rate : 900
         * commission_type : MKT
         * coupon_amount : 10
         * coupon_end_time : 2019-06-20
         * coupon_id : 6d52b391612a4fb5bffd83d351fe0181
         * coupon_info : 满29.00元减10元
         * coupon_remain_count : 44500
         * coupon_share_url : //uland.taobao.com/coupon/edetail?e=tdYLpfRSfAYNfLV8niU3RwXoB%2BDaBK5LQS0Flu%2FfbSp4QsdWMikAalrisGmre1Id522H2TxuqpI%2FgtGnjoXDCey2B03pxESxPkDM1Gs7PuZkvSUqK96SlyBAb%2BhhLPlfiL368k4KC6HLoBqh%2BNBXEwdnYtArTsWpIXSyO3%2FYDr2MTaLwxM2XJLh8NxHPHyz4MXWvr%2Bgbg0sM1L%2FmEra8u344d%2BzmctAY&&app_pvid=59590_11.11.116.233_2017_1560503538120&ptl=floorId:2836;app_pvid:59590_11.11.116.233_2017_1560503538120;tpp_pvid:100_11.178.152.82_5579_3251560503538124388&xId=O40D6X0QzYNywRsjatnEPpYp4H6qpmNnu3MaZWH7wR3RyeTf4exSRx664vexQmgphj7UMDFTPlNmnC3ubvdHSV&union_lens=lensId:0b0b74e9_0c6a_16b5542524a_1df9
         * coupon_start_fee : 29.00
         * coupon_start_time : 2019-06-14
         * coupon_total_count : 50000
         * include_dxjh : false
         * include_mkt : true
         * info_dxjh : {}
         * item_description : 新鲜营养美味 厂家直发 1kg整箱20个计重
         * item_id : 576361024098
         * item_url : https://detail.tmall.com/item.htm?id=576361024098
         * level_one_category_id : 50002766
         * level_one_category_name : 零食/坚果/特产
         * nick : abd巧卖专卖店
         * num_iid : 576361024098
         * pict_url : https://img.alicdn.com/bao/uploaded/i1/4118776387/O1CN011x3I8VvDc5lCFod_!!0-item_pic.jpg
         * provcity : 广东 韶关
         * reserve_price : 48
         * seller_id : 4118776387
         * shop_dsr : 48219
         * shop_title : abd巧卖专卖店
         * short_title : abd丹麦鲜1kg手撕整箱糕点面包
         * small_images : {"string":["https://img.alicdn.com/i2/4118776387/O1CN011x3I7oR9hT6uXZy_!!4118776387.png","https://img.alicdn.com/i2/4118776387/O1CN012fFepL1x3IBT7AFTn_!!4118776387.jpg","https://img.alicdn.com/i2/4118776387/O1CN011x3I7vfMUUziXbc_!!4118776387.jpg","https://img.alicdn.com/i3/4118776387/O1CN01Z3gmAQ1x3IBSTyela_!!4118776387.jpg"]}
         * title : ABD丹麦鲜面包1Kg手撕面包整箱营养早餐食品糕点美食零食
         * tk_total_commi : 62193.4
         * tk_total_sales : 9989
         * url : //s.click.taobao.com/t?e=m%3D2%26s%3DkThFbe8UwLIcQipKwQzePOeEDrYVVa64r4ll3HtqqoxyINtkUhsv0P2EBmPLxhf%2BGnSyCxgm87EACql9sFJBycIYjcEdGCyGKZJ%2BmXzkieu0zUIy5%2FJOG0bXQw2TLQSOQ46khh5dizkGZTSugABF%2B3OSKsmav1kzC2TKqEFvn7inXTIMRtDNDhT%2B6nY8XtX9BulicziMHz%2FPZMUUR31Kpg%3D%3D&scm=null&pvid=100_11.178.152.82_5579_3251560503538124388&app_pvid=59590_11.11.116.233_2017_1560503538120&ptl=floorId:2836;pvid:100_11.178.152.82_5579_3251560503538124388;app_pvid:59590_11.11.116.233_2017_1560503538120&xId=O40D6X0QzYNywRsjatnEPpYp4H6qpmNnu3MaZWH7wR3RyeTf4exSRx664vexQmgphj7UMDFTPlNmnC3ubvdHSV&union_lens=lensId:0b0b74e9_0c6a_16b5542524a_1df9
         * user_type : 1
         * volume : 10634
         * white_image : https://img.alicdn.com/bao/uploaded/O1CN011x3I7uyNAQufm3F_!!4118776387.png
         * x_id : O40D6X0QzYNywRsjatnEPpYp4H6qpmNnu3MaZWH7wR3RyeTf4exSRx664vexQmgphj7UMDFTPlNmnC3ubvdHSV
         * zk_final_price : 29.9
         * quanlimit : 29
         * youhuiquan : 10
         */

        private String category_id;
        private String category_name;
        private String commission_rate;
        private String commission_type;
        private String coupon_amount;
        private String coupon_end_time;
        private String coupon_id;
        private String coupon_info;
        private String coupon_remain_count;
        private String coupon_share_url;
        private String coupon_start_fee;
        private String coupon_start_time;
        private String coupon_total_count;
        private String include_dxjh;
        private String include_mkt;
        private String info_dxjh;
        private String item_description;
        private String item_id;
        private String item_url;
        private String level_one_category_id;
        private String level_one_category_name;
        private String nick;
        private String num_iid;
        private String pict_url;
        private String provcity;
        private String reserve_price;
        private String seller_id;
        private String shop_dsr;
        private String shop_title;
        private String short_title;
        private SmallImagesBean small_images;
        private String title;
        private String tk_total_commi;
        private String tk_total_sales;
        private String url;
        private String user_type;
        private String volume;
        private String white_image;
        private String x_id;
        private String zk_final_price;
        private int quanlimit;
        private int youhuiquan;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getCommission_rate() {
            return commission_rate;
        }

        public void setCommission_rate(String commission_rate) {
            this.commission_rate = commission_rate;
        }

        public String getCommission_type() {
            return commission_type;
        }

        public void setCommission_type(String commission_type) {
            this.commission_type = commission_type;
        }

        public String getCoupon_amount() {
            return coupon_amount;
        }

        public void setCoupon_amount(String coupon_amount) {
            this.coupon_amount = coupon_amount;
        }

        public String getCoupon_end_time() {
            return coupon_end_time;
        }

        public void setCoupon_end_time(String coupon_end_time) {
            this.coupon_end_time = coupon_end_time;
        }

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getCoupon_info() {
            return coupon_info;
        }

        public void setCoupon_info(String coupon_info) {
            this.coupon_info = coupon_info;
        }

        public String getCoupon_remain_count() {
            return coupon_remain_count;
        }

        public void setCoupon_remain_count(String coupon_remain_count) {
            this.coupon_remain_count = coupon_remain_count;
        }

        public String getCoupon_share_url() {
            return coupon_share_url;
        }

        public void setCoupon_share_url(String coupon_share_url) {
            this.coupon_share_url = coupon_share_url;
        }

        public String getCoupon_start_fee() {
            return coupon_start_fee;
        }

        public void setCoupon_start_fee(String coupon_start_fee) {
            this.coupon_start_fee = coupon_start_fee;
        }

        public String getCoupon_start_time() {
            return coupon_start_time;
        }

        public void setCoupon_start_time(String coupon_start_time) {
            this.coupon_start_time = coupon_start_time;
        }

        public String getCoupon_total_count() {
            return coupon_total_count;
        }

        public void setCoupon_total_count(String coupon_total_count) {
            this.coupon_total_count = coupon_total_count;
        }

        public String getInclude_dxjh() {
            return include_dxjh;
        }

        public void setInclude_dxjh(String include_dxjh) {
            this.include_dxjh = include_dxjh;
        }

        public String getInclude_mkt() {
            return include_mkt;
        }

        public void setInclude_mkt(String include_mkt) {
            this.include_mkt = include_mkt;
        }

        public String getInfo_dxjh() {
            return info_dxjh;
        }

        public void setInfo_dxjh(String info_dxjh) {
            this.info_dxjh = info_dxjh;
        }

        public String getItem_description() {
            return item_description;
        }

        public void setItem_description(String item_description) {
            this.item_description = item_description;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_url() {
            return item_url;
        }

        public void setItem_url(String item_url) {
            this.item_url = item_url;
        }

        public String getLevel_one_category_id() {
            return level_one_category_id;
        }

        public void setLevel_one_category_id(String level_one_category_id) {
            this.level_one_category_id = level_one_category_id;
        }

        public String getLevel_one_category_name() {
            return level_one_category_name;
        }

        public void setLevel_one_category_name(String level_one_category_name) {
            this.level_one_category_name = level_one_category_name;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getNum_iid() {
            return num_iid;
        }

        public void setNum_iid(String num_iid) {
            this.num_iid = num_iid;
        }

        public String getPict_url() {
            return pict_url;
        }

        public void setPict_url(String pict_url) {
            this.pict_url = pict_url;
        }

        public String getProvcity() {
            return provcity;
        }

        public void setProvcity(String provcity) {
            this.provcity = provcity;
        }

        public String getReserve_price() {
            return reserve_price;
        }

        public void setReserve_price(String reserve_price) {
            this.reserve_price = reserve_price;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getShop_dsr() {
            return shop_dsr;
        }

        public void setShop_dsr(String shop_dsr) {
            this.shop_dsr = shop_dsr;
        }

        public String getShop_title() {
            return shop_title;
        }

        public void setShop_title(String shop_title) {
            this.shop_title = shop_title;
        }

        public String getShort_title() {
            return short_title;
        }

        public void setShort_title(String short_title) {
            this.short_title = short_title;
        }

        public SmallImagesBean getSmall_images() {
            return small_images;
        }

        public void setSmall_images(SmallImagesBean small_images) {
            this.small_images = small_images;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTk_total_commi() {
            return tk_total_commi;
        }

        public void setTk_total_commi(String tk_total_commi) {
            this.tk_total_commi = tk_total_commi;
        }

        public String getTk_total_sales() {
            return tk_total_sales;
        }

        public void setTk_total_sales(String tk_total_sales) {
            this.tk_total_sales = tk_total_sales;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getWhite_image() {
            return white_image;
        }

        public void setWhite_image(String white_image) {
            this.white_image = white_image;
        }

        public String getX_id() {
            return x_id;
        }

        public void setX_id(String x_id) {
            this.x_id = x_id;
        }

        public String getZk_final_price() {
            return zk_final_price;
        }

        public void setZk_final_price(String zk_final_price) {
            this.zk_final_price = zk_final_price;
        }

        public int getQuanlimit() {
            return quanlimit;
        }

        public void setQuanlimit(int quanlimit) {
            this.quanlimit = quanlimit;
        }

        public int getYouhuiquan() {
            return youhuiquan;
        }

        public void setYouhuiquan(int youhuiquan) {
            this.youhuiquan = youhuiquan;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "category_id='" + category_id + '\'' +
                    ", category_name='" + category_name + '\'' +
                    ", commission_rate='" + commission_rate + '\'' +
                    ", commission_type='" + commission_type + '\'' +
                    ", coupon_amount='" + coupon_amount + '\'' +
                    ", coupon_end_time='" + coupon_end_time + '\'' +
                    ", coupon_id='" + coupon_id + '\'' +
                    ", coupon_info='" + coupon_info + '\'' +
                    ", coupon_remain_count='" + coupon_remain_count + '\'' +
                    ", coupon_share_url='" + coupon_share_url + '\'' +
                    ", coupon_start_fee='" + coupon_start_fee + '\'' +
                    ", coupon_start_time='" + coupon_start_time + '\'' +
                    ", coupon_total_count='" + coupon_total_count + '\'' +
                    ", include_dxjh='" + include_dxjh + '\'' +
                    ", include_mkt='" + include_mkt + '\'' +
                    ", info_dxjh='" + info_dxjh + '\'' +
                    ", item_description='" + item_description + '\'' +
                    ", item_id='" + item_id + '\'' +
                    ", item_url='" + item_url + '\'' +
                    ", level_one_category_id='" + level_one_category_id + '\'' +
                    ", level_one_category_name='" + level_one_category_name + '\'' +
                    ", nick='" + nick + '\'' +
                    ", num_iid='" + num_iid + '\'' +
                    ", pict_url='" + pict_url + '\'' +
                    ", provcity='" + provcity + '\'' +
                    ", reserve_price='" + reserve_price + '\'' +
                    ", seller_id='" + seller_id + '\'' +
                    ", shop_dsr='" + shop_dsr + '\'' +
                    ", shop_title='" + shop_title + '\'' +
                    ", short_title='" + short_title + '\'' +
                    ", small_images=" + small_images +
                    ", title='" + title + '\'' +
                    ", tk_total_commi='" + tk_total_commi + '\'' +
                    ", tk_total_sales='" + tk_total_sales + '\'' +
                    ", url='" + url + '\'' +
                    ", user_type='" + user_type + '\'' +
                    ", volume='" + volume + '\'' +
                    ", white_image='" + white_image + '\'' +
                    ", x_id='" + x_id + '\'' +
                    ", zk_final_price='" + zk_final_price + '\'' +
                    ", quanlimit=" + quanlimit +
                    ", youhuiquan=" + youhuiquan +
                    '}';
        }

        public static class SmallImagesBean {
            private List<Object> string;

            public List<Object> getString() {
                return string;
            }

            public void setString(List<Object> string) {
                this.string = string;
            }

            @Override
            public String toString() {
                return "SmallImagesBean{" +
                        "string=" + string +
                        '}';
            }
        }

    }

    @Override
    public String toString() {
        return "TBGoodsRecBean{" +
                "code=" + code +
                ", totalcount='" + totalcount + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
