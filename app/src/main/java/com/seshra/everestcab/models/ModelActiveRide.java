package com.seshra.everestcab.models;

import java.util.List;

public class ModelActiveRide {

    /**
     * result : 1
     * message : User Active Booking
     * bookable : true
     * data : [{"id":61,"booking_status":"1002"},{"id":89,"booking_status":"1002"},{"id":90,"booking_status":"1002"},{"id":91,"booking_status":"1002"},{"id":93,"booking_status":"1002"},{"id":94,"booking_status":"1002"},{"id":95,"booking_status":"1002"},{"id":96,"booking_status":"1002"},{"id":97,"booking_status":"1002"},{"id":98,"booking_status":"1002"},{"id":99,"booking_status":"1003"},{"id":100,"booking_status":"1003"},{"id":101,"booking_status":"1003"},{"id":102,"booking_status":"1004"},{"id":103,"booking_status":"1004"},{"id":104,"booking_status":"1004"},{"id":105,"booking_status":"1002"},{"id":106,"booking_status":"1003"},{"id":107,"booking_status":"1002"},{"id":108,"booking_status":"1002"},{"id":109,"booking_status":"1004"},{"id":110,"booking_status":"1004"},{"id":111,"booking_status":"1004"},{"id":112,"booking_status":"1004"},{"id":113,"booking_status":"1004"},{"id":114,"booking_status":"1002"},{"id":115,"booking_status":"1002"},{"id":116,"booking_status":"1004"},{"id":117,"booking_status":"1004"},{"id":118,"booking_status":"1002"},{"id":119,"booking_status":"1004"},{"id":120,"booking_status":"1004"},{"id":121,"booking_status":"1002"},{"id":126,"booking_status":"1004"},{"id":127,"booking_status":"1003"},{"id":128,"booking_status":"1002"},{"id":129,"booking_status":"1003"},{"id":130,"booking_status":"1002"},{"id":131,"booking_status":"1002"},{"id":132,"booking_status":"1002"},{"id":133,"booking_status":"1002"},{"id":135,"booking_status":"1002"},{"id":136,"booking_status":"1002"},{"id":138,"booking_status":"1004"},{"id":140,"booking_status":"1004"},{"id":141,"booking_status":"1002"},{"id":142,"booking_status":"1004"},{"id":143,"booking_status":"1002"},{"id":144,"booking_status":"1002"},{"id":145,"booking_status":"1002"},{"id":146,"booking_status":"1002"},{"id":147,"booking_status":"1002"},{"id":148,"booking_status":"1003"},{"id":149,"booking_status":"1002"},{"id":150,"booking_status":"1002"},{"id":151,"booking_status":"1002"},{"id":152,"booking_status":"1002"},{"id":153,"booking_status":"1002"},{"id":154,"booking_status":"1002"},{"id":155,"booking_status":"1002"},{"id":156,"booking_status":"1002"},{"id":157,"booking_status":"1002"},{"id":159,"booking_status":"1002"},{"id":162,"booking_status":"1002"},{"id":163,"booking_status":"1002"},{"id":165,"booking_status":"1002"},{"id":166,"booking_status":"1002"},{"id":168,"booking_status":"1002"},{"id":169,"booking_status":"1002"},{"id":170,"booking_status":"1002"},{"id":172,"booking_status":"1002"},{"id":173,"booking_status":"1002"},{"id":174,"booking_status":"1004"},{"id":175,"booking_status":"1003"},{"id":177,"booking_status":"1004"},{"id":178,"booking_status":"1003"},{"id":180,"booking_status":"1003"},{"id":181,"booking_status":"1002"},{"id":183,"booking_status":"1003"},{"id":185,"booking_status":"1003"},{"id":186,"booking_status":"1002"},{"id":187,"booking_status":"1003"},{"id":188,"booking_status":"1003"},{"id":190,"booking_status":"1002"},{"id":191,"booking_status":"1002"}]
     */

    private String result;
    private String message;
    private boolean bookable;
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

    public boolean isBookable() {
        return bookable;
    }

    public void setBookable(boolean bookable) {
        this.bookable = bookable;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        public int getMerchant_booking_id() {
            return merchant_booking_id;
        }

        public void setMerchant_booking_id(int merchant_booking_id) {
            this.merchant_booking_id = merchant_booking_id;
        }

        /**
         * id : 61
         * booking_status : 1002
         */

        private int id;
        private int merchant_booking_id;
        private String booking_status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBooking_status() {
            return booking_status;
        }

        public void setBooking_status(String booking_status) {
            this.booking_status = booking_status;
        }
    }
}
