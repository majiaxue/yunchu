package com.example.bean;

public class RebateItemBean {
    /**
     * id : 584
     * orderId : 465
     * rebateNum : 12
     * num : 1
     * rebateStatus : 0
     * rebateBalance : 420
     * createTime : 2020-01-13 15:37:07
     * rebateTime : 2020-02-1300:00:00
     * rebateTimeStr : 2020-02-13
     * updateTime : null
     */

    private int id;
    private int orderId;
    private int rebateNum;
    private int num;
    private int rebateStatus;
    private double rebateBalance;
    private String createTime;
    private String rebateTime;
    private String rebateTimeStr;
    private Object updateTime;

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

    public int getRebateNum() {
        return rebateNum;
    }

    public void setRebateNum(int rebateNum) {
        this.rebateNum = rebateNum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getRebateStatus() {
        return rebateStatus;
    }

    public void setRebateStatus(int rebateStatus) {
        this.rebateStatus = rebateStatus;
    }

    public double getRebateBalance() {
        return rebateBalance;
    }

    public void setRebateBalance(double rebateBalance) {
        this.rebateBalance = rebateBalance;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRebateTime() {
        return rebateTime;
    }

    public void setRebateTime(String rebateTime) {
        this.rebateTime = rebateTime;
    }

    public String getRebateTimeStr() {
        return rebateTimeStr;
    }

    public void setRebateTimeStr(String rebateTimeStr) {
        this.rebateTimeStr = rebateTimeStr;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }
}
