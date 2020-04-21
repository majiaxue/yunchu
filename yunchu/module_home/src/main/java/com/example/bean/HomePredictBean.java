package com.example.bean;

public class HomePredictBean {

    /**
     * settleLastMonth : 0
     * waitCurrentMonth : 4800
     * settleCurrentMonth : 3600
     */

    private String settleLastMonth;
    private String waitCurrentMonth;
    private String settleCurrentMonth;

    public String getSettleLastMonth() {
        return settleLastMonth;
    }

    public void setSettleLastMonth(String settleLastMonth) {
        this.settleLastMonth = settleLastMonth;
    }

    public String getWaitCurrentMonth() {
        return waitCurrentMonth;
    }

    public void setWaitCurrentMonth(String waitCurrentMonth) {
        this.waitCurrentMonth = waitCurrentMonth;
    }

    public String getSettleCurrentMonth() {
        return settleCurrentMonth;
    }

    public void setSettleCurrentMonth(String settleCurrentMonth) {
        this.settleCurrentMonth = settleCurrentMonth;
    }
}
