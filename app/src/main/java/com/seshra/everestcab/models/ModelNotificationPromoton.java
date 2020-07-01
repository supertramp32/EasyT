package com.seshra.everestcab.models;

public class ModelNotificationPromoton {

    /**
     * data : {"image":"promotions/FOv5ryBrv94COKFNZTGF4dTvXkaVZV0IjjWVlNxi.png","application":"2","updated_at":"2018-10-18 04:37:13","created_at":"2018-10-18 04:37:13","id":18,"merchant_id":1,"message":"mmmmmmmm","title":"ttttttt","url":"https://onesignal.com/"}
     * type : 2
     */

    private DataBean data;
    private int type;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class DataBean {
        /**
         * image : promotions/FOv5ryBrv94COKFNZTGF4dTvXkaVZV0IjjWVlNxi.png
         * application : 2
         * updated_at : 2018-10-18 04:37:13
         * created_at : 2018-10-18 04:37:13
         * id : 18
         * merchant_id : 1
         * message : mmmmmmmm
         * title : ttttttt
         * url : https://onesignal.com/
         */

        private String image;
        private String application;
        private String updated_at;
        private String created_at;
        private int id;
        private int merchant_id;
        private String message;
        private String title;
        private String url;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getApplication() {
            return application;
        }

        public void setApplication(String application) {
            this.application = application;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
