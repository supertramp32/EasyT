package com.seshra.everestcab.models;

public class ModelRideNotifications {

    /**
     * data : {"booking_id":91,"booking_status":1002}
     * type : 1
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
         * booking_id : 91
         * booking_status : 1002
         */

        private int booking_id;
        private int booking_status;

        public int getBooking_id() {
            return booking_id;
        }

        public void setBooking_id(int booking_id) {
            this.booking_id = booking_id;
        }

        public int getBooking_status() {
            return booking_status;
        }

        public void setBooking_status(int booking_status) {
            this.booking_status = booking_status;
        }
    }
}
