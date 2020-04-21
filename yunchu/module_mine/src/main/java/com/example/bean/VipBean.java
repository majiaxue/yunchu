package com.example.bean;

public class VipBean {
    /**
     * id : 1
     * name : 普通会员
     * isEnable : 1
     * upType : 1
     * price : 0
     * firstCommission : 0
     * secondCommission : 0
     * note : null
     * updateTime : null
     * createTime : null
     * cycle : 12
     * tenantId : 1
     */

    private int id;
    private String name;
    private int isEnable;
    private String upType;
    private double price;
    private String firstCommission;
    private String secondCommission;
    private String note;
    private String updateTime;
    private String createTime;
    private String cycle;
    private String tenantId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public String getUpType() {
        return upType;
    }

    public void setUpType(String upType) {
        this.upType = upType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFirstCommission() {
        return firstCommission;
    }

    public void setFirstCommission(String firstCommission) {
        this.firstCommission = firstCommission;
    }

    public String getSecondCommission() {
        return secondCommission;
    }

    public void setSecondCommission(String secondCommission) {
        this.secondCommission = secondCommission;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
