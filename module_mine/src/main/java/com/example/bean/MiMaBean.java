package com.example.bean;

public class MiMaBean {
    String oldPayPassword;
    String userCode;
    String newPayPassword;

    @Override
    public String toString() {
        return "MiMaBean{" +
                "oldPayPassword='" + oldPayPassword + '\'' +
                ", userCode='" + userCode + '\'' +
                ", newPayPassword='" + newPayPassword + '\'' +
                '}';
    }

    public String getOldPayPassword() {
        return oldPayPassword;
    }

    public void setOldPayPassword(String oldPayPassword) {
        this.oldPayPassword = oldPayPassword;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getNewPayPassword() {
        return newPayPassword;
    }

    public void setNewPayPassword(String newPayPassword) {
        this.newPayPassword = newPayPassword;
    }
}
