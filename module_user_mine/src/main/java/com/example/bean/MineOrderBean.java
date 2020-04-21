package com.example.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public class MineOrderBean implements Serializable {

    private List<OrderListBean> orderList;

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean implements Serializable {
        /**
         * orderId : null
         * sellerName : null
         * goodsName : 萌米无添加补水5件套男女护肤品洗面奶爽肤水面霜美颜CC霜精华
         * status : 1
         * totalAmount : 0.01
         * orderItems : [{"id":1268,"orderId":null,"orderSn":"1911061350000138","productId":106,"productPic":"http://132.232.118.153:8083/goods/69e6e1ce7aad4c8e8e04a4b33c151b95.jpg","productName":"萌米无添加补水5件套男女护肤品洗面奶爽肤水面霜美颜CC霜精华 ","productBrand":"","productSn":"","productPrice":0.01,"productQuantity":1,"productSkuId":11040,"productSkuCode":null,"productCategoryId":null,"sp1":"补水五件套","sp2":"","sp3":"","promotionName":null,"promotionAmount":null,"couponAmount":0,"integrationAmount":null,"realAmount":null,"giftIntegration":0,"giftGrowth":0,"productAttr":"规格:补水五件套","couponId":0,"itemDeliveryTemplateId":null,"expireDate":null,"sellerName":null,"sellerType":2,"buyRatio":null,"type":null,"buyAmount":null}]
         * totalCount : 1
         * sellerId : 1
         * orderSn : 1911061350000138
         * createTime : 2019-11-06 13:50:33
         * isLevelOrder : 1
         * payAmount : 0.01
         */

        private String orderId;
        private String sellerName;
        private String goodsName;
        private int status;
        private double totalAmount;
        private int totalCount;
        private String sellerId;
        private String orderSn;
        private String createTime;
        private int isLevelOrder;
        private double payAmount;
        private List<OrderItemsBean> orderItems;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getIsLevelOrder() {
            return isLevelOrder;
        }

        public void setIsLevelOrder(int isLevelOrder) {
            this.isLevelOrder = isLevelOrder;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public List<OrderItemsBean> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItemsBean> orderItems) {
            this.orderItems = orderItems;
        }

        public static class OrderItemsBean implements Serializable {
            /**
             * id : 1268
             * orderId : null
             * orderSn : 1911061350000138
             * productId : 106
             * productPic : http://132.232.118.153:8083/goods/69e6e1ce7aad4c8e8e04a4b33c151b95.jpg
             * productName : 萌米无添加补水5件套男女护肤品洗面奶爽肤水面霜美颜CC霜精华
             * productBrand :
             * productSn :
             * productPrice : 0.01
             * productQuantity : 1
             * productSkuId : 11040
             * productSkuCode : null
             * productCategoryId : null
             * sp1 : 补水五件套
             * sp2 :
             * sp3 :
             * promotionName : null
             * promotionAmount : null
             * couponAmount : 0
             * integrationAmount : null
             * realAmount : null
             * giftIntegration : 0
             * giftGrowth : 0
             * productAttr : 规格:补水五件套
             * couponId : 0
             * itemDeliveryTemplateId : null
             * expireDate : null
             * sellerName : null
             * sellerType : 2
             * buyRatio : null
             * type : null
             * buyAmount : null
             */

            private String id;
            private String orderId;
            private String orderSn;
            private String productId;
            private String productPic;
            private String productName;
            private String productBrand;
            private String productSn;
            private double productPrice;
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
            private int giftIntegration;
            private int giftGrowth;
            private String productAttr;
            private String couponId;
            private String itemDeliveryTemplateId;
            private String expireDate;
            private String sellerName;
            private int sellerType;
            private String buyRatio;
            private String type;
            private String buyAmount;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
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

            public double getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(double productPrice) {
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

            public int getGiftIntegration() {
                return giftIntegration;
            }

            public void setGiftIntegration(int giftIntegration) {
                this.giftIntegration = giftIntegration;
            }

            public int getGiftGrowth() {
                return giftGrowth;
            }

            public void setGiftGrowth(int giftGrowth) {
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

            public String getSellerName() {
                return sellerName;
            }

            public void setSellerName(String sellerName) {
                this.sellerName = sellerName;
            }

            public int getSellerType() {
                return sellerType;
            }

            public void setSellerType(int sellerType) {
                this.sellerType = sellerType;
            }

            public String getBuyRatio() {
                return buyRatio;
            }

            public void setBuyRatio(String buyRatio) {
                this.buyRatio = buyRatio;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getBuyAmount() {
                return buyAmount;
            }

            public void setBuyAmount(String buyAmount) {
                this.buyAmount = buyAmount;
            }

            @Override
            public String toString() {
                return "OrderItemsBean{" +
                        "id='" + id + '\'' +
                        ", orderId='" + orderId + '\'' +
                        ", orderSn='" + orderSn + '\'' +
                        ", productId='" + productId + '\'' +
                        ", productPic='" + productPic + '\'' +
                        ", productName='" + productName + '\'' +
                        ", productBrand='" + productBrand + '\'' +
                        ", productSn='" + productSn + '\'' +
                        ", productPrice=" + productPrice +
                        ", productQuantity=" + productQuantity +
                        ", productSkuId=" + productSkuId +
                        ", productSkuCode='" + productSkuCode + '\'' +
                        ", productCategoryId='" + productCategoryId + '\'' +
                        ", sp1='" + sp1 + '\'' +
                        ", sp2='" + sp2 + '\'' +
                        ", sp3='" + sp3 + '\'' +
                        ", promotionName='" + promotionName + '\'' +
                        ", promotionAmount='" + promotionAmount + '\'' +
                        ", couponAmount=" + couponAmount +
                        ", integrationAmount='" + integrationAmount + '\'' +
                        ", realAmount='" + realAmount + '\'' +
                        ", giftIntegration=" + giftIntegration +
                        ", giftGrowth=" + giftGrowth +
                        ", productAttr='" + productAttr + '\'' +
                        ", couponId='" + couponId + '\'' +
                        ", itemDeliveryTemplateId='" + itemDeliveryTemplateId + '\'' +
                        ", expireDate='" + expireDate + '\'' +
                        ", sellerName='" + sellerName + '\'' +
                        ", sellerType=" + sellerType +
                        ", buyRatio='" + buyRatio + '\'' +
                        ", type='" + type + '\'' +
                        ", buyAmount='" + buyAmount + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "OrderListBean{" +
                    "orderId='" + orderId + '\'' +
                    ", sellerName='" + sellerName + '\'' +
                    ", goodsName='" + goodsName + '\'' +
                    ", status=" + status +
                    ", totalAmount=" + totalAmount +
                    ", totalCount=" + totalCount +
                    ", sellerId='" + sellerId + '\'' +
                    ", orderSn='" + orderSn + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", isLevelOrder=" + isLevelOrder +
                    ", payAmount=" + payAmount +
                    ", orderItems=" + orderItems +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MineOrderBean{" +
                "orderList=" + orderList +
                '}';
    }
}
