package com.example.bean;

public class ContactUsBean {

    /**
     * id : 5
     * dictCode : 客服信息
     * typeName : customer_service
     * info : 1325330000
     * status : 1
     * pic : http://47.105.211.98:8083/order/b7d54e7c27804df0a6ea941e8391d09d.png
     * name : 云厨一号客服
     */

    private int id;
    private String dictCode;
    private String typeName;
    private String info;
    private int status;
    private String pic;
    private String name;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
