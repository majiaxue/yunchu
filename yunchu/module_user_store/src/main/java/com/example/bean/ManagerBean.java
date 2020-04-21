package com.example.bean;

import java.util.List;

public class ManagerBean {

    /**
     * total : 1
     * records : [{"id":1,"code":"zccccc","createdTime":"2019-09-03 14:27:19","isUpgrade":0,"userCode":"304468286475599872","username":"张层","phone":"18639197022","icon":"http://thirdwx.qlogo.cn/mmopen/vi_32/UjFK0rbVNm1ibqI1eyTSyIpFoiaKxeH0mzIwuHtx7l0IlR8CDYibJF5jjNSdwNyib4vCianfITWEzQTPItBzpLgG7fg/132","puserCode":"311804548412866560"}]
     */

    private int total;
    private List<RecordsBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * id : 1
         * code : zccccc
         * createdTime : 2019-09-03 14:27:19
         * isUpgrade : 0
         * userCode : 304468286475599872
         * username : 张层
         * phone : 18639197022
         * icon : http://thirdwx.qlogo.cn/mmopen/vi_32/UjFK0rbVNm1ibqI1eyTSyIpFoiaKxeH0mzIwuHtx7l0IlR8CDYibJF5jjNSdwNyib4vCianfITWEzQTPItBzpLgG7fg/132
         * puserCode : 311804548412866560
         */

        private int id;
        private String code;
        private String createdTime;
        private int isUpgrade;
        private String userCode;
        private String username;
        private String phone;
        private String icon;
        private String puserCode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public int getIsUpgrade() {
            return isUpgrade;
        }

        public void setIsUpgrade(int isUpgrade) {
            this.isUpgrade = isUpgrade;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getPuserCode() {
            return puserCode;
        }

        public void setPuserCode(String puserCode) {
            this.puserCode = puserCode;
        }
    }
}
