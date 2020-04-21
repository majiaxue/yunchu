package com.example.bean;

import java.io.Serializable;

public class CashInfoBean implements Serializable {
    /**
     * id : 1
     * settlementDay : 0
     * cashOutDay : 0
     * checkWay : 1
     * serviceFee : 3
     * minMoney : 10
     * cashOutWay : 1
     * note : 1234567
     * isEnable : 1
     * updateTime : 2019-06-2910:34:49
     * tenantId : 1
     */

    private int id;
    private int settlementDay;
    private int cashOutDay;
    private String checkWay;
    private int serviceFee;
    private int minMoney;
    private String cashOutWay;
    private String note;
    private int isEnable;
    private String updateTime;
    private int tenantId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSettlementDay() {
        return settlementDay;
    }

    public void setSettlementDay(int settlementDay) {
        this.settlementDay = settlementDay;
    }

    public int getCashOutDay() {
        return cashOutDay;
    }

    public void setCashOutDay(int cashOutDay) {
        this.cashOutDay = cashOutDay;
    }

    public String getCheckWay() {
        return checkWay;
    }

    public void setCheckWay(String checkWay) {
        this.checkWay = checkWay;
    }

    public int getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(int serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(int minMoney) {
        this.minMoney = minMoney;
    }

    public String getCashOutWay() {
        return cashOutWay;
    }

    public void setCashOutWay(String cashOutWay) {
        this.cashOutWay = cashOutWay;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }
}
