package com.example.bean;

import java.util.List;

public class TeJIaBean  {

    /**
     * total : 0
     * data : [{"id":161,"productCategoryId":909,"feightTemplateId":16,"productAttributeCategoryId":2,"name":"好吃到想哭的肉","pic":"http://47.97.111.41:8083/goods/65bb167910124b08bba929bb091fb8aa.jpg","productSn":"10001","deleteStatus":0,"publishStatus":1,"newStatus":1,"recommandStatus":1,"verifyStatus":1,"sort":1,"sale":0,"price":1199,"promotionPrice":0,"giftGrowth":0,"giftPoint":0,"usePointLimit":0,"subTitle":"好吃到想哭的肉","description":"55555555 太好吃了","originalPrice":0,"stock":10000,"lowStock":0,"unit":"斤","weight":100,"previewStatus":0,"serviceIds":"1,2,3","keywords":"金纯奢酿套","note":"深层补水、抗皱、提亮肌肤、淡化色斑","albumPics":"http://47.97.111.41:8083/goods/ef71331c79784daeb596e795023ea24c.jpg,http://47.97.111.41:8083/goods/a20383ef63a44dc2af4295a263f82614.jpg,http://47.97.111.41:8083/goods/02a583bdafd949dfbb0fa18dd04d6da7.jpg,http://47.97.111.41:8083/goods/6ba12037ba604131914f1434dce4f616.jpg","detailTitle":"金纯奢酿套","detailDesc":"金纯奢酿套：细密补水、滋养肌肤、蕴含丰富营养、深入修护肌肤屏障、增强肌肤的弹性、焕发肌肤水润光泽、紧致肌肤、使肌肤展现青春活力。","detailHtml":"<p><img src=\"http://47.97.111.41:8083/goods/2f52ad00d29c4d39834bbff8472ff362.jpg\" style=\"max-width:100%;\"><br><\/p>","detailMobileHtml":"","promotionStartTime":null,"promotionEndTime":null,"promotionPerLimit":0,"promotionType":0,"brandName":"","productCategoryName":"特价专区","supplyId":null,"createTime":null,"sellerId":1,"levelId":null,"sellerType":1,"selfBuy":0,"firstSelfBuy":null,"shareBuy":0,"firstShareBuy":null,"rebateStatus":1,"vipPrice":1000,"limitNum":2,"sellerName":null,"goodReputation":null,"favoriteId":null,"sellerLogo":null,"commentNum":0,"historyId":null}]
     */

    private int total;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 161
         * productCategoryId : 909
         * feightTemplateId : 16
         * productAttributeCategoryId : 2
         * name : 好吃到想哭的肉
         * pic : http://47.97.111.41:8083/goods/65bb167910124b08bba929bb091fb8aa.jpg
         * productSn : 10001
         * deleteStatus : 0
         * publishStatus : 1
         * newStatus : 1
         * recommandStatus : 1
         * verifyStatus : 1
         * sort : 1
         * sale : 0
         * price : 1199
         * promotionPrice : 0
         * giftGrowth : 0
         * giftPoint : 0
         * usePointLimit : 0
         * subTitle : 好吃到想哭的肉
         * description : 55555555 太好吃了
         * originalPrice : 0
         * stock : 10000
         * lowStock : 0
         * unit : 斤
         * weight : 100
         * previewStatus : 0
         * serviceIds : 1,2,3
         * keywords : 金纯奢酿套
         * note : 深层补水、抗皱、提亮肌肤、淡化色斑
         * albumPics : http://47.97.111.41:8083/goods/ef71331c79784daeb596e795023ea24c.jpg,http://47.97.111.41:8083/goods/a20383ef63a44dc2af4295a263f82614.jpg,http://47.97.111.41:8083/goods/02a583bdafd949dfbb0fa18dd04d6da7.jpg,http://47.97.111.41:8083/goods/6ba12037ba604131914f1434dce4f616.jpg
         * detailTitle : 金纯奢酿套
         * detailDesc : 金纯奢酿套：细密补水、滋养肌肤、蕴含丰富营养、深入修护肌肤屏障、增强肌肤的弹性、焕发肌肤水润光泽、紧致肌肤、使肌肤展现青春活力。
         * detailHtml : <p><img src="http://47.97.111.41:8083/goods/2f52ad00d29c4d39834bbff8472ff362.jpg" style="max-width:100%;"><br></p>
         * detailMobileHtml :
         * promotionStartTime : null
         * promotionEndTime : null
         * promotionPerLimit : 0
         * promotionType : 0
         * brandName :
         * productCategoryName : 特价专区
         * supplyId : null
         * createTime : null
         * sellerId : 1
         * levelId : null
         * sellerType : 1
         * selfBuy : 0
         * firstSelfBuy : null
         * shareBuy : 0
         * firstShareBuy : null
         * rebateStatus : 1
         * vipPrice : 1000
         * limitNum : 2
         * sellerName : null
         * goodReputation : null
         * favoriteId : null
         * sellerLogo : null
         * commentNum : 0
         * historyId : null
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
        private int originalPrice;
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
        private String promotionStartTime;
        private String promotionEndTime;
        private int promotionPerLimit;
        private int promotionType;
        private String brandName;
        private String productCategoryName;
        private String supplyId;
        private String createTime;
        private int sellerId;
        private String levelId;
        private int sellerType;
        private int selfBuy;
        private String firstSelfBuy;
        private int shareBuy;
        private String firstShareBuy;
        private int rebateStatus;
        private int vipPrice;
        private int limitNum;
        private String sellerName;
        private String goodReputation;
        private String favoriteId;
        private String sellerLogo;
        private int commentNum;
        private String historyId;

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

        public int getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(int originalPrice) {
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

        public String getPromotionStartTime() {
            return promotionStartTime;
        }

        public void setPromotionStartTime(String promotionStartTime) {
            this.promotionStartTime = promotionStartTime;
        }

        public String getPromotionEndTime() {
            return promotionEndTime;
        }

        public void setPromotionEndTime(String promotionEndTime) {
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

        public String getSupplyId() {
            return supplyId;
        }

        public void setSupplyId(String supplyId) {
            this.supplyId = supplyId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getSellerId() {
            return sellerId;
        }

        public void setSellerId(int sellerId) {
            this.sellerId = sellerId;
        }

        public String getLevelId() {
            return levelId;
        }

        public void setLevelId(String levelId) {
            this.levelId = levelId;
        }

        public int getSellerType() {
            return sellerType;
        }

        public void setSellerType(int sellerType) {
            this.sellerType = sellerType;
        }

        public int getSelfBuy() {
            return selfBuy;
        }

        public void setSelfBuy(int selfBuy) {
            this.selfBuy = selfBuy;
        }

        public String getFirstSelfBuy() {
            return firstSelfBuy;
        }

        public void setFirstSelfBuy(String firstSelfBuy) {
            this.firstSelfBuy = firstSelfBuy;
        }

        public int getShareBuy() {
            return shareBuy;
        }

        public void setShareBuy(int shareBuy) {
            this.shareBuy = shareBuy;
        }

        public String getFirstShareBuy() {
            return firstShareBuy;
        }

        public void setFirstShareBuy(String firstShareBuy) {
            this.firstShareBuy = firstShareBuy;
        }

        public int getRebateStatus() {
            return rebateStatus;
        }

        public void setRebateStatus(int rebateStatus) {
            this.rebateStatus = rebateStatus;
        }

        public int getVipPrice() {
            return vipPrice;
        }

        public void setVipPrice(int vipPrice) {
            this.vipPrice = vipPrice;
        }

        public int getLimitNum() {
            return limitNum;
        }

        public void setLimitNum(int limitNum) {
            this.limitNum = limitNum;
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

        public String getFavoriteId() {
            return favoriteId;
        }

        public void setFavoriteId(String favoriteId) {
            this.favoriteId = favoriteId;
        }

        public String getSellerLogo() {
            return sellerLogo;
        }

        public void setSellerLogo(String sellerLogo) {
            this.sellerLogo = sellerLogo;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public String getHistoryId() {
            return historyId;
        }

        public void setHistoryId(String historyId) {
            this.historyId = historyId;
        }
    }
}
