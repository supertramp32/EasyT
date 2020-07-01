package com.seshra.everestcab.models;

import java.util.List;

public class ModelFavourite {

    private String result;
    private String message;
    private List<DataBean> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * user_id : 1
         * latitude : 28.606824449651036
         * longitude : 77.23811890929937
         * location : 30, Bapa Nagar, India Gate, New Delhi, Delhi 110003, India
         * category : 3
         * other_name : Ghhhhhh
         * created_at : 2018-07-07 10:42:49
         * updated_at : 2018-07-09 04:37:03
         */

        private int id;
        private String user_id;
        private String latitude;
        private String longitude;
        private String location;
        private String category;
        private String other_name;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getOther_name() {
            return other_name;
        }

        public void setOther_name(String other_name) {
            this.other_name = other_name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
