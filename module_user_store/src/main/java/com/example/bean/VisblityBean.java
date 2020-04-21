package com.example.bean;

public class VisblityBean {

    /**
     * id : 1
     * dictCode : 平台补贴商品区
     * typeName : platform_product_show
     * info : 0
     * status : 1
     */

    private int id;
    private String dictCode;
    private String typeName;
    private String info;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
