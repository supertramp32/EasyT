package com.seshra.everestcab.models;

import java.util.List;

public class ModelFragmentActiveRideds {


    /**
     * result : 1
     * message : Your Ride History
     * total_pages : 1
     * current_page : 1
     * data : [{"booking_id":2,"highlighted_text":"Normal #2","small_text":"2018-07-18 11:10:17","pick_visibility":true,"pick_text":"438, Sohna Rd, Park View City 1, Tatvam Villas, Dhani, Sector 48, Gurugram, Haryana 122103, India","drop_visibility":true,"drop_location":"11, Orchid Petals Internal Road, Sector 49, Gurugram, Haryana 122018, India","driver_block_visibility":true,"driver_image":"driver/T0kG0JF9YHKX4GE39IQArg8zz8DCbbHcpdx0YpHg.jpeg","driver_name":"Samir Goel","driver_rating":"3","status_text":"Accepted","value_text":"0","value_color":"2ecc71","status_color":"bbbbbb"}]
     */

    private String result;
    private String message;
    private int total_pages;
    private int current_page;
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

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * booking_id : 2
         * highlighted_text : Normal #2
         * small_text : 2018-07-18 11:10:17
         * pick_visibility : true
         * pick_text : 438, Sohna Rd, Park View City 1, Tatvam Villas, Dhani, Sector 48, Gurugram, Haryana 122103, India
         * drop_visibility : true
         * drop_location : 11, Orchid Petals Internal Road, Sector 49, Gurugram, Haryana 122018, India
         * driver_block_visibility : true
         * driver_image : driver/T0kG0JF9YHKX4GE39IQArg8zz8DCbbHcpdx0YpHg.jpeg
         * driver_name : Samir Goel
         * driver_rating : 3
         * status_text : Accepted
         * value_text : 0
         * value_color : 2ecc71
         * status_color : bbbbbb
         */

        private int booking_id;
        private String highlighted_text;
        private String small_text;
        private boolean pick_visibility;
        private String pick_text;
        private boolean drop_visibility;
        private String drop_location;
        private boolean driver_block_visibility;
        private String driver_image;
        private String driver_name;
        private String driver_rating;
        private String status_text;
        private String value_text;
        private String value_color;
        private String status_color;
        private String estimate_bill;
        private String highlighted_left_text_color;

        public String getHighlighted_left_text_color() {
            return highlighted_left_text_color;
        }

        public void setHighlighted_left_text_color(String highlighted_left_text_color) {
            this.highlighted_left_text_color = highlighted_left_text_color;
        }

        public String getEstimate_bill() {
            return estimate_bill;
        }

        public void setEstimate_bill(String estimate_bill) {
            this.estimate_bill = estimate_bill;
        }

        public int getBooking_id() {
            return booking_id;
        }

        public void setBooking_id(int booking_id) {
            this.booking_id = booking_id;
        }

        public String getHighlighted_text() {
            return highlighted_text;
        }

        public void setHighlighted_text(String highlighted_text) {
            this.highlighted_text = highlighted_text;
        }

        public String getSmall_text() {
            return small_text;
        }

        public void setSmall_text(String small_text) {
            this.small_text = small_text;
        }

        public boolean isPick_visibility() {
            return pick_visibility;
        }

        public void setPick_visibility(boolean pick_visibility) {
            this.pick_visibility = pick_visibility;
        }

        public String getPick_text() {
            return pick_text;
        }

        public void setPick_text(String pick_text) {
            this.pick_text = pick_text;
        }

        public boolean isDrop_visibility() {
            return drop_visibility;
        }

        public void setDrop_visibility(boolean drop_visibility) {
            this.drop_visibility = drop_visibility;
        }

        public String getDrop_location() {
            return drop_location;
        }

        public void setDrop_location(String drop_location) {
            this.drop_location = drop_location;
        }

        public boolean isDriver_block_visibility() {
            return driver_block_visibility;
        }

        public void setDriver_block_visibility(boolean driver_block_visibility) {
            this.driver_block_visibility = driver_block_visibility;
        }

        public String getDriver_image() {
            return driver_image;
        }

        public void setDriver_image(String driver_image) {
            this.driver_image = driver_image;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getDriver_rating() {
            return driver_rating;
        }

        public void setDriver_rating(String driver_rating) {
            this.driver_rating = driver_rating;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public String getValue_text() {
            return value_text;
        }

        public void setValue_text(String value_text) {
            this.value_text = value_text;
        }

        public String getValue_color() {
            return value_color;
        }

        public void setValue_color(String value_color) {
            this.value_color = value_color;
        }

        public String getStatus_color() {
            return status_color;
        }

        public void setStatus_color(String status_color) {
            this.status_color = status_color;
        }
    }
}
