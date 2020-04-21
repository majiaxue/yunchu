package com.example.bean;

/**
 * Created by cuihaohao on 2019/5/24
 * Describe:
 */
public class CartBean {

    /**
     * id : 8
     * productId : 79
     * productSkuId : 501
     * userId : 337448873746235392
     * quantity : 1
     * price : 1
     * sp1 : m
     * sp2 : 红色
     * sp3 : 送美女
     * productPic :
     * productName : 123123123
     * productSubTitle : 123123
     * memberNickname :
     * createDate : 2019-09-27 17:24:28
     * modifyDate : null
     * productCategoryId : 151
     * productBrand : null
     * productSn : 11
     * productAttr : 颜色：m、套餐：红色、尺寸:送美女
     * checked : 1
     * sellerId : 1
     * sellerName : null
     * sellerType : 1
     */

    private int id;
    private int productId;
    private int productSkuId;
    private long userId;
    private int quantity;
    private double price;
    private String sp1;
    private String sp2;
    private String sp3;
    private String productPic;
    private String productName;
    private String productSubTitle;
    private String memberNickname;
    private String createDate;
    private String modifyDate;
    private int productCategoryId;
    private String productBrand;
    private String productSn;
    private String productAttr;
    private int checked;
    private int sellerId;
    private String sellerName;
    private int sellerType;
    private int limitNum;

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(int productSkuId) {
        this.productSkuId = productSkuId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(String productSubTitle) {
        this.productSubTitle = productSubTitle;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
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

    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
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

    public int getSellerType() {
        return sellerType;
    }

    public void setSellerType(int sellerType) {
        this.sellerType = sellerType;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "id=" + id +
                ", productId=" + productId +
                ", productSkuId=" + productSkuId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", sp1='" + sp1 + '\'' +
                ", sp2='" + sp2 + '\'' +
                ", sp3='" + sp3 + '\'' +
                ", productPic='" + productPic + '\'' +
                ", productName='" + productName + '\'' +
                ", productSubTitle='" + productSubTitle + '\'' +
                ", memberNickname='" + memberNickname + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", productCategoryId=" + productCategoryId +
                ", productBrand='" + productBrand + '\'' +
                ", productSn='" + productSn + '\'' +
                ", productAttr='" + productAttr + '\'' +
                ", checked=" + checked +
                ", sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", sellerType=" + sellerType +
                '}';
    }
}
