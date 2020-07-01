package com.seshra.everestcab.models;

public class ModelApplyPromoCode {

    String result;
    String message;
    Data data;


    public class Data{

        String estimate_bill;
        String discounted_amout;

        public String getEstimate_bill() {
            return estimate_bill;
        }

        public String getDiscounted_amout() {
            return discounted_amout;
        }
    }


    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }
}
