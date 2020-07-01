package com.seshra.everestcab.models;

import java.util.ArrayList;

public class ResultCheckHomeServices {

    private String result;
    private String message;
    private String home_screen;
    private String currency;
    private Data data;


    public class Data{


        String vehicleImage;
        String eta;
        ArrayList<Drivers> drivers;


        public String getVehicleImage() {
            return vehicleImage;
        }

        public String getEta() {
            return eta;
        }

        public ArrayList<Drivers> getDrivers() {
            return drivers;
        }

        public class Drivers{

            String current_latitude;
            String current_longitude;
            String distance;
            int driver_id;
            String bearing;

            public String getCurrent_latitude() {
                return current_latitude;
            }

            public String getCurrent_longitude() {
                return current_longitude;
            }

            public String getDistance() {
                return distance;
            }

            public int getDriver_id() {
                return driver_id;
            }

            public String getBearing() {
                return bearing;
            }
        }




    }

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

    public String getHome_screen() {
        return home_screen;
    }

    public void setHome_screen(String home_screen) {
        this.home_screen = home_screen;
    }

    public String getCurrency() {
        return currency;
    }

    public Data getData() {
        return data;
    }
}
