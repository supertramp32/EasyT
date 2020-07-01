package com.seshra.everestcab.models;

public class OTPModel {

    String result;
    String message;
    Data data;

    public class  Data {
        boolean auto_fill;
        String otp;

        public boolean isAuto_fill() {
            return auto_fill;
        }

        public void setAuto_fill(boolean auto_fill) {
            this.auto_fill = auto_fill;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
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
