package com.seshra.everestcab.models;

import java.util.ArrayList;
import java.util.List;

public class TempRideInfo {


    String result;
    String message;
    Data data;

    public class Data{

        int id;
        int booking_status;
        String pickup_location;
        String drop_location;
        String estimate_bill;
        String pickup_latitude;
        String pickup_longitude;
        String drop_longitude;
        String drop_latitude;

        Driver_Details driver_details;
        Payment_Method payment_method;
        Movable_marker movable_marker;

        ArrayList<Sos> sos;
        Vehicle_Type vehicle_type;
        String share_able_link;

        Vehicle_Details vehicle_details;
        private List<WaypointsBean> waypoints;


        public List<WaypointsBean> getWaypoints() {
            return waypoints;
        }

        public void setWaypoints(List<WaypointsBean> waypoints) {
            this.waypoints = waypoints;
        }

        public Vehicle_Details getVehicle_details() {
            return vehicle_details;
        }

        public void setVehicle_details(Vehicle_Details vehicle_details) {
            this.vehicle_details = vehicle_details;
        }

        public String getShare_able_link() {
            return share_able_link;
        }

        public Vehicle_Type getVehicle_type() {
            return vehicle_type;
        }

        public String getPickup_latitude() {
            return pickup_latitude;
        }

        public String getPickup_longitude() {
            return pickup_longitude;
        }

        public String getDrop_longitude() {
            return drop_longitude;
        }

        public String getDrop_latitude() {
            return drop_latitude;
        }

        public class Vehicle_Type{

            String vehicleTypeMapImage;


            public String getVehicleTypeMapImage() {
                return vehicleTypeMapImage;
            }
        }



        public  class WaypointsBean {
            /**
             * stop : 1
             * drop_latitude : 28.5245787
             * drop_longitude : 77.206615
             * drop_location : Saket, New Delhi, Delhi, India
             * status : 1
             */

            private int stop;
            private String drop_latitude;
            private String drop_longitude;
            private String drop_location;
            private String status;

            public int getStop() {
                return stop;
            }

            public void setStop(int stop) {
                this.stop = stop;
            }

            public String getDrop_latitude() {
                return drop_latitude;
            }

            public void setDrop_latitude(String drop_latitude) {
                this.drop_latitude = drop_latitude;
            }

            public String getDrop_longitude() {
                return drop_longitude;
            }

            public void setDrop_longitude(String drop_longitude) {
                this.drop_longitude = drop_longitude;
            }

            public String getDrop_location() {
                return drop_location;
            }

            public void setDrop_location(String drop_location) {
                this.drop_location = drop_location;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }






        public class Vehicle_Details{

            String service;
            String vehicle;
            String vehicle_type_image;
            String vehicle_number;
            String vehicle_color;
            String vehicle_image;
            String vehicle_make;
            String vehicle_model;


            public String getService() {
                return service;
            }

            public String getVehicle() {
                return vehicle;
            }

            public String getVehicle_type_image() {
                return vehicle_type_image;
            }

            public String getVehicle_number() {
                return vehicle_number;
            }

            public String getVehicle_color() {
                return vehicle_color;
            }

            public String getVehicle_image() {
                return vehicle_image;
            }

            public String getVehicle_make() {
                return vehicle_make;
            }

            public String getVehicle_model() {
                return vehicle_model;
            }
        }

        public class Sos{

           int id;
            String number;
            int sosStatus;
            String name;


            public int getId() {
                return id;
            }

            public String getNumber() {
                return number;
            }

            public int getSosStatus() {
                return sosStatus;
            }

            public String getName() {
                return name;
            }
        }


        public class Movable_marker{

           String driver_marker_type;
            String driver_marker_lat;
            String driver_marker_long;
            String driver_marker_bearing;

            public String getDriver_marker_type() {
                return driver_marker_type;
            }

            public String getDriver_marker_lat() {
                return driver_marker_lat;
            }

            public String getDriver_marker_long() {
                return driver_marker_long;
            }

            public String getDriver_marker_bearing() {
                return driver_marker_bearing;
            }
        }


        public Movable_marker getMovable_marker() {
            return movable_marker;
        }

        public Payment_Method getPayment_method() {
            return payment_method;
        }

        public String getPickup_location() {
            return pickup_location;
        }

        public String getDrop_location() {
            return drop_location;
        }

        public String getEstimate_bill() {
            return estimate_bill;
        }

        public ArrayList<Sos> getSos() {
            return sos;
        }

        public int getBooking_status() {
            return booking_status;
        }

        public class Payment_Method{

            int id;
            String payment_method;
            int payment_method_type;
            int payment_method_status;
            String payment_icon;


            public int getId() {
                return id;
            }

            public String getPayment_method() {
                return payment_method;
            }

            public int getPayment_method_type() {
                return payment_method_type;
            }

            public int getPayment_method_status() {
                return payment_method_status;
            }

            public String getPayment_icon() {
                return payment_icon;
            }
        }



        public class Driver_Details{

            int id;
            String first_name;
            String last_name;
            String fullName;
            String email;
            String phoneNumber;
            String profile_image;
            String rating;
            String current_latitude;
            String driver_marker_long;
            String driver_marker_bearing;


            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getProfile_image() {
                return profile_image;
            }

            public void setProfile_image(String profile_image) {
                this.profile_image = profile_image;
            }

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

            public String getCurrent_latitude() {
                return current_latitude;
            }

            public void setCurrent_latitude(String current_latitude) {
                this.current_latitude = current_latitude;
            }

            public String getDriver_marker_long() {
                return driver_marker_long;
            }

            public void setDriver_marker_long(String driver_marker_long) {
                this.driver_marker_long = driver_marker_long;
            }

            public String getDriver_marker_bearing() {
                return driver_marker_bearing;
            }

            public void setDriver_marker_bearing(String driver_marker_bearing) {
                this.driver_marker_bearing = driver_marker_bearing;
            }
        }


        public int getId() {
            return id;
        }

        public Driver_Details getDriver_details() {
            return driver_details;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }




}
