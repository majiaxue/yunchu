package com.example.bean;

import java.io.Serializable;
import java.util.List;

public class OrderDetailBean implements Serializable {

    /**
     * id : 51
     * orderSn : 19061211110001
     * masterSn : 19061211110001
     * userName : null
     * userId : null
     * tradeStatus : 1
     * autoTakeTime : 30
     * receiverPhone : 13201835918
     * receiverName : 晓玉
     * receiverRegion : 金水区
     * receiverCity : 郑州市
     * receiverProvince : 河南省
     * orderAddress : 金城国际3号楼
     * billContent : null
     * billHeader : null
     * billType : null
     * billReceiverEmail : null
     * billReceiverPhone : null
     * orderCreateTime : null
     * orderPayTime : null
     * orderUpdateTime : null
     * orderOutTime : null
     * receiveTime : null
     * orderOverTime : null
     * commentTime : null
     * orderPayAmount : null
     * payAmount : null
     * integrationAmount : null
     * couponAmount : 0
     * discountAmount : null
     * promotionAmount : null
     * freightAmount : 1
     * totalAmount : 1
     * remark :
     * payWay : null
     * sourceType : 1
     * outerTradeNo : null
     * isRemove : null
     * useIntegration : null
     * confirmStatus : null
     * deliverySn : null
     * deliveryCompany : null
     * prepayId : null
     * supplyId : null
     * goodsId : null
     * goodsName : 夜店女装夜总会性感连衣裙气质大露背修身包臀开叉夜场裙子小礼服
     * orderCategory : 0
     * sellerId : 11
     * sellerName : Allisjoy/舞悦时节
     * status : 6
     * promotionInfo : null
     * integration : null
     * receiverPostCode : null
     * items : [{"id":14,"orderId":51,"orderSn":"19061211110001","productId":null,"productPic":"http://192.168.1.22:9000/goods/d099e84d9b294fcaabd6d91db3d16f83.jpg","productName":"夜店女装夜总会性感连衣裙气质大露背修身包臀开叉夜场裙子小礼服","productBrand":"","productSn":null,"productPrice":null,"productQuantity":1,"productSkuId":263,"productSkuCode":null,"productCategoryId":null,"sp1":"黑色","sp2":"M","sp3":"","promotionName":null,"promotionAmount":null,"couponAmount":0,"integrationAmount":null,"realAmount":1,"giftIntegration":null,"giftGrowth":null,"productAttr":"颜色：黑色，尺码：M","couponId":null,"itemDeliveryTemplateId":null,"expireDate":null}]
     */

    private int id;
    private String orderSn;
    private String masterSn;
    private String userName;
    private String userId;
    private int tradeStatus;
    private long autoTakeTime;
    private String receiverPhone;
    private String receiverName;
    private String receiverRegion;
    private String receiverCity;
    private String receiverProvince;
    private String orderAddress;
    private String billContent;
    private String billHeader;
    private String billType;
    private String billReceiverEmail;
    private String billReceiverPhone;
    private String orderCreateTime;
    private String orderPayTime;
    private String orderUpdateTime;
    private String orderOutTime;
    private String receiveTime;
    private String orderOverTime;
    private String commentTime;
    private Double orderPayAmount;
    private Double payAmount;
    private Double integrationAmount;
    private Double couponAmount;
    private Double discountAmount;
    private Double promotionAmount;
    private Double freightAmount;
    private Double totalAmount;
    private String remark;
    private String payWay;
    private int sourceType;
    private String outerTradeNo;
    private String isRemove;
    private String useIntegration;
    private String confirmStatus;
    private String deliverySn;
    private String deliveryCompany;
    private String prepayId;
    private String supplyId;
    private String goodsId;
    private String goodsName;
    private int orderCategory;
    private int sellerId;
    private String sellerName;
    private int status;
    private String promotionInfo;
    private String integration;
    private String receiverPostCode;
    private List<ItemsBean> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getMasterSn() {
        return masterSn;
    }

    public void setMasterSn(String masterSn) {
        this.masterSn = masterSn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(int tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public long getAutoTakeTime() {
        return autoTakeTime;
    }

    public void setAutoTakeTime(long autoTakeTime) {
        this.autoTakeTime = autoTakeTime;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverRegion() {
        return receiverRegion;
    }

    public void setReceiverRegion(String receiverRegion) {
        this.receiverRegion = receiverRegion;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getBillContent() {
        return billContent;
    }

    public void setBillContent(String billContent) {
        this.billContent = billContent;
    }

    public String getBillHeader() {
        return billHeader;
    }

    public void setBillHeader(String billHeader) {
        this.billHeader = billHeader;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillReceiverEmail() {
        return billReceiverEmail;
    }

    public void setBillReceiverEmail(String billReceiverEmail) {
        this.billReceiverEmail = billReceiverEmail;
    }

    public String getBillReceiverPhone() {
        return billReceiverPhone;
    }

    public void setBillReceiverPhone(String billReceiverPhone) {
        this.billReceiverPhone = billReceiverPhone;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getOrderPayTime() {
        return orderPayTime;
    }

    public void setOrderPayTime(String orderPayTime) {
        this.orderPayTime = orderPayTime;
    }

    public String getOrderUpdateTime() {
        return orderUpdateTime;
    }

    public void setOrderUpdateTime(String orderUpdateTime) {
        this.orderUpdateTime = orderUpdateTime;
    }

    public String getOrderOutTime() {
        return orderOutTime;
    }

    public void setOrderOutTime(String orderOutTime) {
        this.orderOutTime = orderOutTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getOrderOverTime() {
        return orderOverTime;
    }

    public void setOrderOverTime(String orderOverTime) {
        this.orderOverTime = orderOverTime;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public Double getOrderPayAmount() {
        return orderPayAmount;
    }

    public void setOrderPayAmount(Double orderPayAmount) {
        this.orderPayAmount = orderPayAmount;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public Double getIntegrationAmount() {
        return integrationAmount;
    }

    public void setIntegrationAmount(Double integrationAmount) {
        this.integrationAmount = integrationAmount;
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(Double promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public Double getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(Double freightAmount) {
        this.freightAmount = freightAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public String getOuterTradeNo() {
        return outerTradeNo;
    }

    public void setOuterTradeNo(String outerTradeNo) {
        this.outerTradeNo = outerTradeNo;
    }

    public String getIsRemove() {
        return isRemove;
    }

    public void setIsRemove(String isRemove) {
        this.isRemove = isRemove;
    }

    public String getUseIntegration() {
        return useIntegration;
    }

    public void setUseIntegration(String useIntegration) {
        this.useIntegration = useIntegration;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPromotionInfo() {
        return promotionInfo;
    }

    public void setPromotionInfo(String promotionInfo) {
        this.promotionInfo = promotionInfo;
    }

    public String getIntegration() {
        return integration;
    }

    public void setIntegration(String integration) {
        this.integration = integration;
    }

    public String getReceiverPostCode() {
        return receiverPostCode;
    }

    public void setReceiverPostCode(String receiverPostCode) {
        this.receiverPostCode = receiverPostCode;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean implements Serializable{
        /**
         * id : 14
         * orderId : 51
         * orderSn : 19061211110001
         * productId : null
         * productPic : http://192.168.1.22:9000/goods/d099e84d9b294fcaabd6d91db3d16f83.jpg
         * productName : 夜店女装夜总会性感连衣裙气质大露背修身包臀开叉夜场裙子小礼服
         * productBrand :
         * productSn : null
         * productPrice : null
         * productQuantity : 1
         * productSkuId : 263
         * productSkuCode : null
         * productCategoryId : null
         * sp1 : 黑色
         * sp2 : M
         * sp3 :
         * promotionName : null
         * promotionAmount : null
         * couponAmount : 0
         * integrationAmount : null
         * realAmount : 1
         * giftIntegration : null
         * giftGrowth : null
         * productAttr : 颜色：黑色，尺码：M
         * couponId : null
         * itemDeliveryTemplateId : null
         * expireDate : null
         */

        private int id;
        private int orderId;
        private String orderSn;
        private String productId;
        private String productPic;
        private String productName;
        private String productBrand;
        private String productSn;
        private String productPrice;
        private int productQuantity;
        private int productSkuId;
        private String productSkuCode;
        private String productCategoryId;
        private String sp1;
        private String sp2;
        private String sp3;
        private String promotionName;
        private String promotionAmount;
        private int couponAmount;
        private String integrationAmount;
        private String realAmount;
        private String giftIntegration;
        private String giftGrowth;
        private String productAttr;
        private String couponId;
        private String itemDeliveryTemplateId;
        private String expireDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductPic() {
            return productPic;
        }

        public void setProductPic(String productPic) {
            this.productPic = productPic;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductBrand() {
            return productBrand;
        }

        public void setProductBrand(String productBrand) {
            this.productBrand = productBrand;
        }

        public String getProductSn() {
            return productSn;
        }

        public void setProductSn(String productSn) {
            this.productSn = productSn;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(int productQuantity) {
            this.productQuantity = productQuantity;
        }

        public int getProductSkuId() {
            return productSkuId;
        }

        public void setProductSkuId(int productSkuId) {
            this.productSkuId = productSkuId;
        }

        public String getProductSkuCode() {
            return productSkuCode;
        }

        public void setProductSkuCode(String productSkuCode) {
            this.productSkuCode = productSkuCode;
        }

        public String getProductCategoryId() {
            return productCategoryId;
        }

        public void setProductCategoryId(String productCategoryId) {
            this.productCategoryId = productCategoryId;
        }

        public String getSp1() {
            return sp1;
        }

        public void setSp1(String sp1) {
            this.sp1 = sp1;
        }

        public String getSp2() {
            return sp2;
        }

        public void setSp2(String sp2) {
            this.sp2 = sp2;
        }

        public String getSp3() {
            return sp3;
        }

        public void setSp3(String sp3) {
            this.sp3 = sp3;
        }

        public String getPromotionName() {
            return promotionName;
        }

        public void setPromotionName(String promotionName) {
            this.promotionName = promotionName;
        }

        public String getPromotionAmount() {
            return promotionAmount;
        }

        public void setPromotionAmount(String promotionAmount) {
            this.promotionAmount = promotionAmount;
        }

        public int getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(int couponAmount) {
            this.couponAmount = couponAmount;
        }

        public String getIntegrationAmount() {
            return integrationAmount;
        }

        public void setIntegrationAmount(String integrationAmount) {
            this.integrationAmount = integrationAmount;
        }

        public String getRealAmount() {
            return realAmount;
        }

        public void setRealAmount(String realAmount) {
            this.realAmount = realAmount;
        }

        public String getGiftIntegration() {
            return giftIntegration;
        }

        public void setGiftIntegration(String giftIntegration) {
            this.giftIntegration = giftIntegration;
        }

        public String getGiftGrowth() {
            return giftGrowth;
        }

        public void setGiftGrowth(String giftGrowth) {
            this.giftGrowth = giftGrowth;
        }

        public String getProductAttr() {
            return productAttr;
        }

        public void setProductAttr(String productAttr) {
            this.productAttr = productAttr;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getItemDeliveryTemplateId() {
            return itemDeliveryTemplateId;
        }

        public void setItemDeliveryTemplateId(String itemDeliveryTemplateId) {
            this.itemDeliveryTemplateId = itemDeliveryTemplateId;
        }

        public String getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
        }
    }
}
