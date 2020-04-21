package com.example.bean;

import java.util.List;

/**
 * Created by cuihaohao on 2019/6/16
 * Describe:
 */
public class BrowsingBean {

    private List<RecordsBean> records;

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * createTime : 2019-09-04 00:00:00
         * item : [{"id":150,"productCategoryId":409,"feightTemplateId":32,"productAttributeCategoryId":27,"name":"LUMILASER 保湿无暇蚕丝面膜","pic":"http://47.105.53.79:8083/goods/0147df3116834ed696f63fa67691d2ef.jpg","productSn":"","deleteStatus":0,"publishStatus":1,"newStatus":1,"recommandStatus":1,"verifyStatus":1,"sort":0,"sale":0,"price":1.68,"promotionPrice":0,"giftGrowth":0,"giftPoint":0,"usePointLimit":0,"subTitle":"LUMILASER 保湿无暇蚕丝面膜","description":"LUMILASER 保湿无暇蚕丝面膜","originalPrice":1.68,"stock":9999,"lowStock":0,"unit":"盒","weight":0,"previewStatus":0,"serviceIds":"3","keywords":"LUMILASER 保湿无暇蚕丝面膜","note":"LUMILASER 保湿无暇蚕丝面膜","albumPics":"http://47.105.53.79:8083/goods/f1c8f206aa174125b34f2bae9c87c7b9.jpg","detailTitle":"LUMILASER 保湿无暇蚕丝面膜","detailDesc":"LUMILASER 保湿无暇蚕丝面膜","detailHtml":"<p><img src=\"http://47.105.53.79:8083/goods/6b87aa59d4d34f61b9c2ffba607b5bd6.jpg\"><\/p>","detailMobileHtml":"","promotionStartTime":null,"promotionEndTime":null,"promotionPerLimit":0,"promotionType":0,"brandName":"","productCategoryName":"","supplyId":null,"createTime":null,"sellerId":1,"returnRatio":0,"levelId":null,"sellerName":"Allisjoy/我的时代1","goodReputation":"尚无评论","favoriteId":null,"sellerLogo":null,"historyId":8600}]
         */

        private String createTime;
        private List<ItemBean> item;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * id : 150
             * productCategoryId : 409
             * feightTemplateId : 32
             * productAttributeCategoryId : 27
             * name : LUMILASER 保湿无暇蚕丝面膜
             * pic : http://47.105.53.79:8083/goods/0147df3116834ed696f63fa67691d2ef.jpg
             * productSn :
             * deleteStatus : 0
             * publishStatus : 1
             * newStatus : 1
             * recommandStatus : 1
             * verifyStatus : 1
             * sort : 0
             * sale : 0
             * price : 1.68
             * promotionPrice : 0
             * giftGrowth : 0
             * giftPoint : 0
             * usePointLimit : 0
             * subTitle : LUMILASER 保湿无暇蚕丝面膜
             * description : LUMILASER 保湿无暇蚕丝面膜
             * originalPrice : 1.68
             * stock : 9999
             * lowStock : 0
             * unit : 盒
             * weight : 0
             * previewStatus : 0
             * serviceIds : 3
             * keywords : LUMILASER 保湿无暇蚕丝面膜
             * note : LUMILASER 保湿无暇蚕丝面膜
             * albumPics : http://47.105.53.79:8083/goods/f1c8f206aa174125b34f2bae9c87c7b9.jpg
             * detailTitle : LUMILASER 保湿无暇蚕丝面膜
             * detailDesc : LUMILASER 保湿无暇蚕丝面膜
             * detailHtml : <p><img src="http://47.105.53.79:8083/goods/6b87aa59d4d34f61b9c2ffba607b5bd6.jpg"></p>
             * detailMobileHtml :
             * promotionStartTime : null
             * promotionEndTime : null
             * promotionPerLimit : 0
             * promotionType : 0
             * brandName :
             * productCategoryName :
             * supplyId : null
             * createTime : null
             * sellerId : 1
             * returnRatio : 0
             * levelId : null
             * sellerName : Allisjoy/我的时代1
             * goodReputation : 尚无评论
             * favoriteId : null
             * sellerLogo : null
             * historyId : 8600
             */

            private int id;
            private int productCategoryId;
            private int feightTemplateId;
            private int productAttributeCategoryId;
            private String name;
            private String pic;
            private String productSn;
            private int deleteStatus;
            private int publishStatus;
            private int newStatus;
            private int recommandStatus;
            private int verifyStatus;
            private int sort;
            private int sale;
            private double price;
            private int promotionPrice;
            private int giftGrowth;
            private int giftPoint;
            private int usePointLimit;
            private String subTitle;
            private String description;
            private double originalPrice;
            private int stock;
            private int lowStock;
            private String unit;
            private int weight;
            private int previewStatus;
            private String serviceIds;
            private String keywords;
            private String note;
            private String albumPics;
            private String detailTitle;
            private String detailDesc;
            private String detailHtml;
            private String detailMobileHtml;
            private Object promotionStartTime;
            private Object promotionEndTime;
            private int promotionPerLimit;
            private int promotionType;
            private String brandName;
            private String productCategoryName;
            private Object supplyId;
            private Object createTime;
            private int sellerId;
            private int returnRatio;
            private Object levelId;
            private String sellerName;
            private String goodReputation;
            private Object favoriteId;
            private Object sellerLogo;
            private int historyId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProductCategoryId() {
                return productCategoryId;
            }

            public void setProductCategoryId(int productCategoryId) {
                this.productCategoryId = productCategoryId;
            }

            public int getFeightTemplateId() {
                return feightTemplateId;
            }

            public void setFeightTemplateId(int feightTemplateId) {
                this.feightTemplateId = feightTemplateId;
            }

            public int getProductAttributeCategoryId() {
                return productAttributeCategoryId;
            }

            public void setProductAttributeCategoryId(int productAttributeCategoryId) {
                this.productAttributeCategoryId = productAttributeCategoryId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getProductSn() {
                return productSn;
            }

            public void setProductSn(String productSn) {
                this.productSn = productSn;
            }

            public int getDeleteStatus() {
                return deleteStatus;
            }

            public void setDeleteStatus(int deleteStatus) {
                this.deleteStatus = deleteStatus;
            }

            public int getPublishStatus() {
                return publishStatus;
            }

            public void setPublishStatus(int publishStatus) {
                this.publishStatus = publishStatus;
            }

            public int getNewStatus() {
                return newStatus;
            }

            public void setNewStatus(int newStatus) {
                this.newStatus = newStatus;
            }

            public int getRecommandStatus() {
                return recommandStatus;
            }

            public void setRecommandStatus(int recommandStatus) {
                this.recommandStatus = recommandStatus;
            }

            public int getVerifyStatus() {
                return verifyStatus;
            }

            public void setVerifyStatus(int verifyStatus) {
                this.verifyStatus = verifyStatus;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getSale() {
                return sale;
            }

            public void setSale(int sale) {
                this.sale = sale;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getPromotionPrice() {
                return promotionPrice;
            }

            public void setPromotionPrice(int promotionPrice) {
                this.promotionPrice = promotionPrice;
            }

            public int getGiftGrowth() {
                return giftGrowth;
            }

            public void setGiftGrowth(int giftGrowth) {
                this.giftGrowth = giftGrowth;
            }

            public int getGiftPoint() {
                return giftPoint;
            }

            public void setGiftPoint(int giftPoint) {
                this.giftPoint = giftPoint;
            }

            public int getUsePointLimit() {
                return usePointLimit;
            }

            public void setUsePointLimit(int usePointLimit) {
                this.usePointLimit = usePointLimit;
            }

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public double getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(double originalPrice) {
                this.originalPrice = originalPrice;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public int getLowStock() {
                return lowStock;
            }

            public void setLowStock(int lowStock) {
                this.lowStock = lowStock;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }

            public int getPreviewStatus() {
                return previewStatus;
            }

            public void setPreviewStatus(int previewStatus) {
                this.previewStatus = previewStatus;
            }

            public String getServiceIds() {
                return serviceIds;
            }

            public void setServiceIds(String serviceIds) {
                this.serviceIds = serviceIds;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getAlbumPics() {
                return albumPics;
            }

            public void setAlbumPics(String albumPics) {
                this.albumPics = albumPics;
            }

            public String getDetailTitle() {
                return detailTitle;
            }

            public void setDetailTitle(String detailTitle) {
                this.detailTitle = detailTitle;
            }

            public String getDetailDesc() {
                return detailDesc;
            }

            public void setDetailDesc(String detailDesc) {
                this.detailDesc = detailDesc;
            }

            public String getDetailHtml() {
                return detailHtml;
            }

            public void setDetailHtml(String detailHtml) {
                this.detailHtml = detailHtml;
            }

            public String getDetailMobileHtml() {
                return detailMobileHtml;
            }

            public void setDetailMobileHtml(String detailMobileHtml) {
                this.detailMobileHtml = detailMobileHtml;
            }

            public Object getPromotionStartTime() {
                return promotionStartTime;
            }

            public void setPromotionStartTime(Object promotionStartTime) {
                this.promotionStartTime = promotionStartTime;
            }

            public Object getPromotionEndTime() {
                return promotionEndTime;
            }

            public void setPromotionEndTime(Object promotionEndTime) {
                this.promotionEndTime = promotionEndTime;
            }

            public int getPromotionPerLimit() {
                return promotionPerLimit;
            }

            public void setPromotionPerLimit(int promotionPerLimit) {
                this.promotionPerLimit = promotionPerLimit;
            }

            public int getPromotionType() {
                return promotionType;
            }

            public void setPromotionType(int promotionType) {
                this.promotionType = promotionType;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getProductCategoryName() {
                return productCategoryName;
            }

            public void setProductCategoryName(String productCategoryName) {
                this.productCategoryName = productCategoryName;
            }

            public Object getSupplyId() {
                return supplyId;
            }

            public void setSupplyId(Object supplyId) {
                this.supplyId = supplyId;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public int getSellerId() {
                return sellerId;
            }

            public void setSellerId(int sellerId) {
                this.sellerId = sellerId;
            }

            public int getReturnRatio() {
                return returnRatio;
            }

            public void setReturnRatio(int returnRatio) {
                this.returnRatio = returnRatio;
            }

            public Object getLevelId() {
                return levelId;
            }

            public void setLevelId(Object levelId) {
                this.levelId = levelId;
            }

            public String getSellerName() {
                return sellerName;
            }

            public void setSellerName(String sellerName) {
                this.sellerName = sellerName;
            }

            public String getGoodReputation() {
                return goodReputation;
            }

            public void setGoodReputation(String goodReputation) {
                this.goodReputation = goodReputation;
            }

            public Object getFavoriteId() {
                return favoriteId;
            }

            public void setFavoriteId(Object favoriteId) {
                this.favoriteId = favoriteId;
            }

            public Object getSellerLogo() {
                return sellerLogo;
            }

            public void setSellerLogo(Object sellerLogo) {
                this.sellerLogo = sellerLogo;
            }

            public int getHistoryId() {
                return historyId;
            }

            public void setHistoryId(int historyId) {
                this.historyId = historyId;
            }

            @Override
            public String toString() {
                return "ItemBean{" +
                        "id=" + id +
                        ", productCategoryId=" + productCategoryId +
                        ", feightTemplateId=" + feightTemplateId +
                        ", productAttributeCategoryId=" + productAttributeCategoryId +
                        ", name='" + name + '\'' +
                        ", pic='" + pic + '\'' +
                        ", productSn='" + productSn + '\'' +
                        ", deleteStatus=" + deleteStatus +
                        ", publishStatus=" + publishStatus +
                        ", newStatus=" + newStatus +
                        ", recommandStatus=" + recommandStatus +
                        ", verifyStatus=" + verifyStatus +
                        ", sort=" + sort +
                        ", sale=" + sale +
                        ", price=" + price +
                        ", promotionPrice=" + promotionPrice +
                        ", giftGrowth=" + giftGrowth +
                        ", giftPoint=" + giftPoint +
                        ", usePointLimit=" + usePointLimit +
                        ", subTitle='" + subTitle + '\'' +
                        ", description='" + description + '\'' +
                        ", originalPrice=" + originalPrice +
                        ", stock=" + stock +
                        ", lowStock=" + lowStock +
                        ", unit='" + unit + '\'' +
                        ", weight=" + weight +
                        ", previewStatus=" + previewStatus +
                        ", serviceIds='" + serviceIds + '\'' +
                        ", keywords='" + keywords + '\'' +
                        ", note='" + note + '\'' +
                        ", albumPics='" + albumPics + '\'' +
                        ", detailTitle='" + detailTitle + '\'' +
                        ", detailDesc='" + detailDesc + '\'' +
                        ", detailHtml='" + detailHtml + '\'' +
                        ", detailMobileHtml='" + detailMobileHtml + '\'' +
                        ", promotionStartTime=" + promotionStartTime +
                        ", promotionEndTime=" + promotionEndTime +
                        ", promotionPerLimit=" + promotionPerLimit +
                        ", promotionType=" + promotionType +
                        ", brandName='" + brandName + '\'' +
                        ", productCategoryName='" + productCategoryName + '\'' +
                        ", supplyId=" + supplyId +
                        ", createTime=" + createTime +
                        ", sellerId=" + sellerId +
                        ", returnRatio=" + returnRatio +
                        ", levelId=" + levelId +
                        ", sellerName='" + sellerName + '\'' +
                        ", goodReputation='" + goodReputation + '\'' +
                        ", favoriteId=" + favoriteId +
                        ", sellerLogo=" + sellerLogo +
                        ", historyId=" + historyId +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "RecordsBean{" +
                    "createTime='" + createTime + '\'' +
                    ", item=" + item +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BrowsingBean{" +
                "records=" + records +
                '}';
    }
}
