package com.example.bean;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public class AlterationBean {


    private List<RBean> r;

    public List<RBean> getR() {
        return r;
    }

    public void setR(List<RBean> r) {
        this.r = r;
    }

    public static class RBean {
        /**
         * id : 31
         * orderId : 189
         * companyAddressId : null
         * productId : 56
         * orderSn : 19061914200001
         * createTime : 2019-07-03 14:04:46
         * memberUsername : 18639488962
         * returnAmount : null
         * returnName : null
         * returnPhone : null
         * status : 8
         * handleTime : null
         * productPic : null
         * productName : 凉鞋女夏平底ins潮女士百搭2019新款夏季网红超火时尚运动沙滩鞋
         * productAttr : null
         * productCount : null
         * productPrice : 88
         * productRealPrice : 99
         * reason : 拍错/多拍/不想要
         * description : null
         * proofPics : http://172.16.240.23:9000/order/785f00829af14e9f83fa4f7ae64f179f..jpg,http://172.16.240.23:9000/order/7b8c4e3f302647458635d5f76a5c4866..jpg
         * handleNote : null
         * handleMan : null
         * receiveMan : null
         * receiveTime : null
         * receiveNote : null
         * sellerId : null
         * returnType : null
         * sellerName : Allisjoy/我的时代
         * receiverPhone : 18818881888
         * receiverName : 辅助
         * receiverRegion : 金水区
         * receiverCity : 郑州
         * receiverProvince : 河南省
         * orderAddress : 金城国际广场
         * payWay : null
         * itemlist : [{"id":null,"orderId":null,"orderSn":null,"productId":null,"productPic":"http://47.99.93.123:8083/goods/8f49f74d0df943c484d1a48ac226d05d.jpg","productName":"凉鞋女夏平底ins潮女士百搭2019新款夏季网红超火时尚运动沙滩鞋","productBrand":null,"productSn":null,"productPrice":88,"productQuantity":1,"productSkuId":null,"productSkuCode":null,"productCategoryId":null,"sp1":null,"sp2":null,"sp3":null,"promotionName":null,"promotionAmount":null,"couponAmount":null,"StringegrationAmount":null,"realAmount":null,"giftStringegration":null,"giftGrowth":null,"productAttr":null,"couponId":null,"itemDeliveryTemplateId":null,"expireDate":null}]
         */

        private String id;
        private String orderId;
        private String companyAddressId;
        private String productId;
        private String orderSn;
        private String createTime;
        private String memberUsername;
        private String returnAmount;
        private String returnName;
        private String returnPhone;
        private int status;
        private String handleTime;
        private String productPic;
        private String productName;
        private String productAttr;
        private String productCount;
        private String productPrice;
        private String productRealPrice;
        private String reason;
        private String description;
        private String proofPics;
        private String handleNote;
        private String handleMan;
        private String receiveMan;
        private String receiveTime;
        private String receiveNote;
        private String sellerId;
        private String returnType;
        private String sellerName;
        private String receiverPhone;
        private String receiverName;
        private String receiverRegion;
        private String receiverCity;
        private String receiverProvince;
        private String orderAddress;
        private String payWay;
        private List<ItemlistBean> itemlist;

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

        public String getCompanyAddressId() {
            return companyAddressId;
        }

        public void setCompanyAddressId(String companyAddressId) {
            this.companyAddressId = companyAddressId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
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

        public String getMemberUsername() {
            return memberUsername;
        }

        public void setMemberUsername(String memberUsername) {
            this.memberUsername = memberUsername;
        }

        public String getReturnAmount() {
            return returnAmount;
        }

        public void setReturnAmount(String returnAmount) {
            this.returnAmount = returnAmount;
        }

        public String getReturnName() {
            return returnName;
        }

        public void setReturnName(String returnName) {
            this.returnName = returnName;
        }

        public String getReturnPhone() {
            return returnPhone;
        }

        public void setReturnPhone(String returnPhone) {
            this.returnPhone = returnPhone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getHandleTime() {
            return handleTime;
        }

        public void setHandleTime(String handleTime) {
            this.handleTime = handleTime;
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

        public String getProductAttr() {
            return productAttr;
        }

        public void setProductAttr(String productAttr) {
            this.productAttr = productAttr;
        }

        public String getProductCount() {
            return productCount;
        }

        public void setProductCount(String productCount) {
            this.productCount = productCount;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductRealPrice() {
            return productRealPrice;
        }

        public void setProductRealPrice(String productRealPrice) {
            this.productRealPrice = productRealPrice;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProofPics() {
            return proofPics;
        }

        public void setProofPics(String proofPics) {
            this.proofPics = proofPics;
        }

        public String getHandleNote() {
            return handleNote;
        }

        public void setHandleNote(String handleNote) {
            this.handleNote = handleNote;
        }

        public String getHandleMan() {
            return handleMan;
        }

        public void setHandleMan(String handleMan) {
            this.handleMan = handleMan;
        }

        public String getReceiveMan() {
            return receiveMan;
        }

        public void setReceiveMan(String receiveMan) {
            this.receiveMan = receiveMan;
        }

        public String getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getReceiveNote() {
            return receiveNote;
        }

        public void setReceiveNote(String receiveNote) {
            this.receiveNote = receiveNote;
        }

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        public String getReturnType() {
            return returnType;
        }

        public void setReturnType(String returnType) {
            this.returnType = returnType;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
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

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
        }

        public List<ItemlistBean> getItemlist() {
            return itemlist;
        }

        public void setItemlist(List<ItemlistBean> itemlist) {
            this.itemlist = itemlist;
        }

        public static class ItemlistBean {
            /**
             * id : null
             * orderId : null
             * orderSn : null
             * productId : null
             * productPic : http://47.99.93.123:8083/goods/8f49f74d0df943c484d1a48ac226d05d.jpg
             * productName : 凉鞋女夏平底ins潮女士百搭2019新款夏季网红超火时尚运动沙滩鞋
             * productBrand : null
             * productSn : null
             * productPrice : 88
             * productQuantity : 1
             * productSkuId : null
             * productSkuCode : null
             * productCategoryId : null
             * sp1 : null
             * sp2 : null
             * sp3 : null
             * promotionName : null
             * promotionAmount : null
             * couponAmount : null
             * StringegrationAmount : null
             * realAmount : null
             * giftStringegration : null
             * giftGrowth : null
             * productAttr : null
             * couponId : null
             * itemDeliveryTemplateId : null
             * expireDate : null
             */

            private String id;
            private String orderId;
            private String orderSn;
            private String productId;
            private String productPic;
            private String productName;
            private String productBrand;
            private String productSn;
            private String productPrice;
            private String productQuantity;
            private String productSkuId;
            private String productSkuCode;
            private String productCategoryId;
            private String sp1;
            private String sp2;
            private String sp3;
            private String promotionName;
            private String promotionAmount;
            private String couponAmount;
            private String StringegrationAmount;
            private String realAmount;
            private String giftStringegration;
            private String giftGrowth;
            private String productAttr;
            private String couponId;
            private String itemDeliveryTemplateId;
            private String expireDate;

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

            public String getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(String productPrice) {
                this.productPrice = productPrice;
            }

            public String getProductQuantity() {
                return productQuantity;
            }

            public void setProductQuantity(String productQuantity) {
                this.productQuantity = productQuantity;
            }

            public String getProductSkuId() {
                return productSkuId;
            }

            public void setProductSkuId(String productSkuId) {
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

            public String getCouponAmount() {
                return couponAmount;
            }

            public void setCouponAmount(String couponAmount) {
                this.couponAmount = couponAmount;
            }

            public String getStringegrationAmount() {
                return StringegrationAmount;
            }

            public void setStringegrationAmount(String StringegrationAmount) {
                this.StringegrationAmount = StringegrationAmount;
            }

            public String getRealAmount() {
                return realAmount;
            }

            public void setRealAmount(String realAmount) {
                this.realAmount = realAmount;
            }

            public String getGiftStringegration() {
                return giftStringegration;
            }

            public void setGiftStringegration(String giftStringegration) {
                this.giftStringegration = giftStringegration;
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

            @Override
            public String toString() {
                return "ItemlistBean{" +
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
                        ", productSkuId='" + productSkuId + '\'' +
                        ", productSkuCode='" + productSkuCode + '\'' +
                        ", productCategoryId='" + productCategoryId + '\'' +
                        ", sp1='" + sp1 + '\'' +
                        ", sp2='" + sp2 + '\'' +
                        ", sp3='" + sp3 + '\'' +
                        ", promotionName='" + promotionName + '\'' +
                        ", promotionAmount='" + promotionAmount + '\'' +
                        ", couponAmount='" + couponAmount + '\'' +
                        ", StringegrationAmount='" + StringegrationAmount + '\'' +
                        ", realAmount='" + realAmount + '\'' +
                        ", giftStringegration='" + giftStringegration + '\'' +
                        ", giftGrowth='" + giftGrowth + '\'' +
                        ", productAttr='" + productAttr + '\'' +
                        ", couponId='" + couponId + '\'' +
                        ", itemDeliveryTemplateId='" + itemDeliveryTemplateId + '\'' +
                        ", expireDate='" + expireDate + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "RBean{" +
                    "id=" + id +
                    ", orderId=" + orderId +
                    ", companyAddressId='" + companyAddressId + '\'' +
                    ", productId=" + productId +
                    ", orderSn='" + orderSn + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", memberUsername='" + memberUsername + '\'' +
                    ", returnAmount='" + returnAmount + '\'' +
                    ", returnName='" + returnName + '\'' +
                    ", returnPhone='" + returnPhone + '\'' +
                    ", status=" + status +
                    ", handleTime='" + handleTime + '\'' +
                    ", productPic='" + productPic + '\'' +
                    ", productName='" + productName + '\'' +
                    ", productAttr='" + productAttr + '\'' +
                    ", productCount='" + productCount + '\'' +
                    ", productPrice=" + productPrice +
                    ", productRealPrice=" + productRealPrice +
                    ", reason='" + reason + '\'' +
                    ", description='" + description + '\'' +
                    ", proofPics='" + proofPics + '\'' +
                    ", handleNote='" + handleNote + '\'' +
                    ", handleMan='" + handleMan + '\'' +
                    ", receiveMan='" + receiveMan + '\'' +
                    ", receiveTime='" + receiveTime + '\'' +
                    ", receiveNote='" + receiveNote + '\'' +
                    ", sellerId='" + sellerId + '\'' +
                    ", returnType='" + returnType + '\'' +
                    ", sellerName='" + sellerName + '\'' +
                    ", receiverPhone='" + receiverPhone + '\'' +
                    ", receiverName='" + receiverName + '\'' +
                    ", receiverRegion='" + receiverRegion + '\'' +
                    ", receiverCity='" + receiverCity + '\'' +
                    ", receiverProvince='" + receiverProvince + '\'' +
                    ", orderAddress='" + orderAddress + '\'' +
                    ", payWay='" + payWay + '\'' +
                    ", itemlist=" + itemlist +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AlterationBean{" +
                "r=" + r +
                '}';
    }
}
