package com.example.bean;

public class BankCardBean {

    /**
     * id : 9
     * name : 付韶
     * idCard : 410728199704253017
     * bankName : 磁条卡卡号
     * bankCard : 6215994980000103753
     * phone : 17719956722
     * createdTime : 2019-09-02 20:25:27
     * userCode : 297881222686703616
     */

    private String id;
    private String name;
    private String idCard;
    private String bankName;
    private String bankCard;
    private String phone;
    private String createdTime;
    private String userCode;
    private boolean isCheck;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "BankCardBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", phone='" + phone + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", userCode='" + userCode + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
