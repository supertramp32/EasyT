package com.seshra.everestcab.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelRideInfo implements Serializable{


    /**
     * result : 1
     * message : Ride Detail
     * data : {"id":205,"unique_id":"5c7a39862be59","drop_longitude":"77.03786954283714","drop_latitude":"28.41055647313725","ride_otp":"7143","ploy_points":"_hllDstfuM?j@","pickup_latitude":"28.412481830190167","pickup_longitude":"77.04386293888092","booking_status":1002,"total_drop_location":1,"share_able_link":"","otp_enable":1,"cancelable":true,"sos_visibility":false,"shareable":false,"tip_status":false,"location":{"trip_status_text":"Arriving Now","location_headline":"Pickup","location_text":"IRIS Tech Park, Sector 49, Gurugram, Haryana 122018, India","location_color":"2ecc71","location_editable":false},"payment_editable":false,"polydata":{"polyline_width":"8","polyline_color":"333333","polyline":"_hllDstfuM?j@"},"still_marker":{"marker_type":"PICK","marker_lat":"28.412481830190167","marker_long":"77.04386293888092"},"movable_marker":{"driver_marker_type":"CAR_ONE","driver_marker_lat":"28.4124452","driver_marker_long":"77.0440977","driver_marker_bearing":"0.0"},"address_changeable":true,"vehicle_details":{"service":"Normal","vehicle":"Sedan","vehicle_type_image":"vehicle/eV2VLceCmFossYimUAQ2f4udNsKFA053VeqUWUv5.png","vehicle_number":"HR1223445","vehicle_color":"blue","vehicle_image":"driverVehicle/RXbMhekvFAQNIIWoPJnYvF04rWHS4xSuaArEr7eA.jpeg","vehicle_make":"Honda","vehicle_model":"2016"},"sos":[{"id":2,"merchant_id":2,"number":"100","sosStatus":1,"created_at":"2019-02-28 16:57:26","updated_at":"2019-03-01 07:40:19","name":"Police"},{"id":4,"merchant_id":2,"number":"123","sosStatus":1,"created_at":"2019-03-01 07:40:59","updated_at":"2019-03-01 07:40:59","name":"Fire Brigade"},{"id":5,"merchant_id":2,"number":"101","sosStatus":1,"created_at":"2019-03-01 07:41:58","updated_at":"2019-03-01 07:41:58","name":"Customer"}],"service_type":{"id":1,"serviceName":"Normal","serviceStatus":1,"package":0,"type":1,"created_at":"2018-05-18 20:30:00","updated_at":"2018-05-18 21:30:00"},"vehicle_type":{"id":16,"merchant_id":2,"vehicleTypeImage":"vehicle/eV2VLceCmFossYimUAQ2f4udNsKFA053VeqUWUv5.png","vehicleTypeMapImage":"vehicle/cjhfHxnWJsHt8KtmqEt5TkcIda44mAbGD46PyUoS.png","vehicleTypeRank":1,"vehicleTypeStatus":1,"pool_enable":1,"sequence":1,"rating":"0","created_at":"2019-02-23 12:52:18","updated_at":"2019-03-02 15:37:52"},"payment_method":{"id":1,"payment_method":"Cash","payment_method_type":1,"payment_method_status":1,"payment_icon":"payment_icon/qhQT6SwoNJbrvqGcVZ0fGqwfk8MXvcqjuNTmw3P8.png"},"driver":{"id":39,"merchant_id":2,"unique_number":null,"driver_gender":0,"fullName":"Man","email":"man@gmail.com","password":"$2y$10$.nS1PSXh36VhyTjabl.A6ur5NtOmZDOKYYHSNR.nuXY5SRhBhYqQ2","home_location_active":2,"pool_ride_active":1,"status_for_pool":1,"avail_seats":4,"occupied_seats":0,"pick_exceed":null,"pool_user_id":null,"phoneNumber":"+919879879879","profile_image":"driver/aEGjZFX1YtzSqv1iHUvUCxp989tQCyCMnKsTDl79.jpeg","wallet_money":null,"total_trips":1,"total_earnings":"45.00","total_comany_earning":"5.00","outstand_amount":"-5.00","current_latitude":"28.4124452","current_longitude":"77.0440977","last_location_update_time":"2019-03-02 13:32:56","bearing":"0.0","accuracy":"13.359","player_id":"76370114-9af3-44a0-b087-35380c6c4119","rating":"0","country_area_id":3,"login_logout":1,"online_offline":1,"free_busy":1,"bank_name":"","account_holder_name":"","account_number":"","driver_verify_status":1,"signupFrom":1,"signupStep":3,"driver_verification_date":null,"driver_admin_status":1,"access_token_id":"03a27419d544e2ab8ee9bb301ae15dbb3512c9a3229de19ac2ac4665598588f7b69f812ca16f060c","driver_delete":null,"last_ride_request_timestamp":"2019-03-02 13:36:24","created_at":"2019-02-27 11:06:57","updated_at":"2019-03-02 13:36:30"}}
     */

    private String result;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 205
         * unique_id : 5c7a39862be59
         * drop_longitude : 77.03786954283714
         * drop_latitude : 28.41055647313725
         * ride_otp : 7143
         * ploy_points : _hllDstfuM?j@
         * pickup_latitude : 28.412481830190167
         * pickup_longitude : 77.04386293888092
         * booking_status : 1002
         * total_drop_location : 1
         * share_able_link :
         * otp_enable : 1
         * cancelable : true
         * sos_visibility : false
         * shareable : false
         * tip_status : false
         * location : {"trip_status_text":"Arriving Now","location_headline":"Pickup","location_text":"IRIS Tech Park, Sector 49, Gurugram, Haryana 122018, India","location_color":"2ecc71","location_editable":false}
         * payment_editable : false
         * polydata : {"polyline_width":"8","polyline_color":"333333","polyline":"_hllDstfuM?j@"}
         * still_marker : {"marker_type":"PICK","marker_lat":"28.412481830190167","marker_long":"77.04386293888092"}
         * movable_marker : {"driver_marker_type":"CAR_ONE","driver_marker_lat":"28.4124452","driver_marker_long":"77.0440977","driver_marker_bearing":"0.0"}
         * address_changeable : true
         * vehicle_details : {"service":"Normal","vehicle":"Sedan","vehicle_type_image":"vehicle/eV2VLceCmFossYimUAQ2f4udNsKFA053VeqUWUv5.png","vehicle_number":"HR1223445","vehicle_color":"blue","vehicle_image":"driverVehicle/RXbMhekvFAQNIIWoPJnYvF04rWHS4xSuaArEr7eA.jpeg","vehicle_make":"Honda","vehicle_model":"2016"}
         * sos : [{"id":2,"merchant_id":2,"number":"100","sosStatus":1,"created_at":"2019-02-28 16:57:26","updated_at":"2019-03-01 07:40:19","name":"Police"},{"id":4,"merchant_id":2,"number":"123","sosStatus":1,"created_at":"2019-03-01 07:40:59","updated_at":"2019-03-01 07:40:59","name":"Fire Brigade"},{"id":5,"merchant_id":2,"number":"101","sosStatus":1,"created_at":"2019-03-01 07:41:58","updated_at":"2019-03-01 07:41:58","name":"Customer"}]
         * service_type : {"id":1,"serviceName":"Normal","serviceStatus":1,"package":0,"type":1,"created_at":"2018-05-18 20:30:00","updated_at":"2018-05-18 21:30:00"}
         * vehicle_type : {"id":16,"merchant_id":2,"vehicleTypeImage":"vehicle/eV2VLceCmFossYimUAQ2f4udNsKFA053VeqUWUv5.png","vehicleTypeMapImage":"vehicle/cjhfHxnWJsHt8KtmqEt5TkcIda44mAbGD46PyUoS.png","vehicleTypeRank":1,"vehicleTypeStatus":1,"pool_enable":1,"sequence":1,"rating":"0","created_at":"2019-02-23 12:52:18","updated_at":"2019-03-02 15:37:52"}
         * payment_method : {"id":1,"payment_method":"Cash","payment_method_type":1,"payment_method_status":1,"payment_icon":"payment_icon/qhQT6SwoNJbrvqGcVZ0fGqwfk8MXvcqjuNTmw3P8.png"}
         * driver : {"id":39,"merchant_id":2,"unique_number":null,"driver_gender":0,"fullName":"Man","email":"man@gmail.com","password":"$2y$10$.nS1PSXh36VhyTjabl.A6ur5NtOmZDOKYYHSNR.nuXY5SRhBhYqQ2","home_location_active":2,"pool_ride_active":1,"status_for_pool":1,"avail_seats":4,"occupied_seats":0,"pick_exceed":null,"pool_user_id":null,"phoneNumber":"+919879879879","profile_image":"driver/aEGjZFX1YtzSqv1iHUvUCxp989tQCyCMnKsTDl79.jpeg","wallet_money":null,"total_trips":1,"total_earnings":"45.00","total_comany_earning":"5.00","outstand_amount":"-5.00","current_latitude":"28.4124452","current_longitude":"77.0440977","last_location_update_time":"2019-03-02 13:32:56","bearing":"0.0","accuracy":"13.359","player_id":"76370114-9af3-44a0-b087-35380c6c4119","rating":"0","country_area_id":3,"login_logout":1,"online_offline":1,"free_busy":1,"bank_name":"","account_holder_name":"","account_number":"","driver_verify_status":1,"signupFrom":1,"signupStep":3,"driver_verification_date":null,"driver_admin_status":1,"access_token_id":"03a27419d544e2ab8ee9bb301ae15dbb3512c9a3229de19ac2ac4665598588f7b69f812ca16f060c","driver_delete":null,"last_ride_request_timestamp":"2019-03-02 13:36:24","created_at":"2019-02-27 11:06:57","updated_at":"2019-03-02 13:36:30"}
         */

        private int id;
//        private String unique_id;
//        private String drop_longitude;
//        private String drop_latitude;
////        private String ride_otp;
//        private String ploy_points;
//        private String pickup_latitude;
//        private String pickup_longitude;
        private int booking_status;
//        private int total_drop_location;
//        private String share_able_link;
//        private int otp_enable;
//        private boolean cancelable;
//        private boolean sos_visibility;
//        private boolean shareable;
//        private boolean tip_status;
        private LocationBean location;
//        private boolean payment_editable;
//        private PolydataBean polydata;
//        private StillMarkerBean still_marker;
//        private MovableMarkerBean movable_marker;
//        private boolean address_changeable;
        private VehicleDetailsBean vehicle_details;
//        private ServiceTypeBean service_type;
//        private VehicleTypeBean vehicle_type;
//        private PaymentMethodBean payment_method;
        private DriverBean driver;
//        private List<SosBean> sos;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

//        public String getUnique_id() {
//            return unique_id;
//        }
//
//        public void setUnique_id(String unique_id) {
//            this.unique_id = unique_id;
//        }
//
//        public String getDrop_longitude() {
//            return drop_longitude;
//        }
//
//        public void setDrop_longitude(String drop_longitude) {
//            this.drop_longitude = drop_longitude;
//        }
//
//        public String getDrop_latitude() {
//            return drop_latitude;
//        }
//
//        public void setDrop_latitude(String drop_latitude) {
//            this.drop_latitude = drop_latitude;
//        }
//
////        public String getRide_otp() {
////            return ride_otp;
////        }
////
////        public void setRide_otp(String ride_otp) {
////            this.ride_otp = ride_otp;
////        }
//
//        public String getPloy_points() {
//            return ploy_points;
//        }
//
//        public void setPloy_points(String ploy_points) {
//            this.ploy_points = ploy_points;
//        }
//
//        public String getPickup_latitude() {
//            return pickup_latitude;
//        }
//
//        public void setPickup_latitude(String pickup_latitude) {
//            this.pickup_latitude = pickup_latitude;
//        }
//
//        public String getPickup_longitude() {
//            return pickup_longitude;
//        }
//
//        public void setPickup_longitude(String pickup_longitude) {
//            this.pickup_longitude = pickup_longitude;
//        }
//
        public int getBooking_status() {
            return booking_status;
        }
//
        public void setBooking_status(int booking_status) {
            this.booking_status = booking_status;
        }
//
//        public int getTotal_drop_location() {
//            return total_drop_location;
//        }
//
//        public void setTotal_drop_location(int total_drop_location) {
//            this.total_drop_location = total_drop_location;
//        }
//
//        public String getShare_able_link() {
//            return share_able_link;
//        }
//
//        public void setShare_able_link(String share_able_link) {
//            this.share_able_link = share_able_link;
//        }
//
//        public int getOtp_enable() {
//            return otp_enable;
//        }
//
//        public void setOtp_enable(int otp_enable) {
//            this.otp_enable = otp_enable;
//        }
//
//        public boolean isCancelable() {
//            return cancelable;
//        }
//
//        public void setCancelable(boolean cancelable) {
//            this.cancelable = cancelable;
//        }
//
//        public boolean isSos_visibility() {
//            return sos_visibility;
//        }
//
//        public void setSos_visibility(boolean sos_visibility) {
//            this.sos_visibility = sos_visibility;
//        }
//
//        public boolean isShareable() {
//            return shareable;
//        }
//
//        public void setShareable(boolean shareable) {
//            this.shareable = shareable;
//        }
//
//        public boolean isTip_status() {
//            return tip_status;
//        }
//
//        public void setTip_status(boolean tip_status) {
//            this.tip_status = tip_status;
//        }
//
        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }
//
//        public boolean isPayment_editable() {
//            return payment_editable;
//        }
//
//        public void setPayment_editable(boolean payment_editable) {
//            this.payment_editable = payment_editable;
//        }
//
//        public PolydataBean getPolydata() {
//            return polydata;
//        }
//
//        public void setPolydata(PolydataBean polydata) {
//            this.polydata = polydata;
//        }
//
//        public StillMarkerBean getStill_marker() {
//            return still_marker;
//        }
//
//        public void setStill_marker(StillMarkerBean still_marker) {
//            this.still_marker = still_marker;
//        }
//
//        public MovableMarkerBean getMovable_marker() {
//            return movable_marker;
//        }
//
//        public void setMovable_marker(MovableMarkerBean movable_marker) {
//            this.movable_marker = movable_marker;
//        }
//
//        public boolean isAddress_changeable() {
//            return address_changeable;
//        }
//
//        public void setAddress_changeable(boolean address_changeable) {
//            this.address_changeable = address_changeable;
//        }

        public VehicleDetailsBean getVehicle_details() {
            return vehicle_details;
        }

        public void setVehicle_details(VehicleDetailsBean vehicle_details) {
            this.vehicle_details = vehicle_details;
        }

//        public ServiceTypeBean getService_type() {
//            return service_type;
//        }
//
//        public void setService_type(ServiceTypeBean service_type) {
//            this.service_type = service_type;
//        }
//
//        public VehicleTypeBean getVehicle_type() {
//            return vehicle_type;
//        }
//
//        public void setVehicle_type(VehicleTypeBean vehicle_type) {
//            this.vehicle_type = vehicle_type;
//        }
//
//        public PaymentMethodBean getPayment_method() {
//            return payment_method;
//        }
//
//        public void setPayment_method(PaymentMethodBean payment_method) {
//            this.payment_method = payment_method;
//        }
//
        public DriverBean getDriver() {
            return driver;
        }

        public void setDriver(DriverBean driver) {
            this.driver = driver;
        }
//
//        public List<SosBean> getSos() {
//            return sos;
//        }
//
//        public void setSos(List<SosBean> sos) {
//            this.sos = sos;
//        }

        public static class LocationBean {
            /**
             * trip_status_text : Arriving Now
             * location_headline : Pickup
             * location_text : IRIS Tech Park, Sector 49, Gurugram, Haryana 122018, India
             * location_color : 2ecc71
             * location_editable : false
             */

            private String trip_status_text;
//            private String location_headline;
//            private String location_text;
//            private String location_color;
//            private boolean location_editable;

            public String getTrip_status_text() {
                return trip_status_text;
            }

            public void setTrip_status_text(String trip_status_text) {
                this.trip_status_text = trip_status_text;
            }

//            public String getLocation_headline() {
//                return location_headline;
//            }
//
//            public void setLocation_headline(String location_headline) {
//                this.location_headline = location_headline;
//            }
//
//            public String getLocation_text() {
//                return location_text;
//            }
//
//            public void setLocation_text(String location_text) {
//                this.location_text = location_text;
//            }
//
//            public String getLocation_color() {
//                return location_color;
//            }
//
//            public void setLocation_color(String location_color) {
//                this.location_color = location_color;
//            }
//
//            public boolean isLocation_editable() {
//                return location_editable;
//            }
//
//            public void setLocation_editable(boolean location_editable) {
//                this.location_editable = location_editable;
//            }
        }

        public static class PolydataBean {
            /**
             * polyline_width : 8
             * polyline_color : 333333
             * polyline : _hllDstfuM?j@
             */

            private String polyline_width;
            private String polyline_color;
            private String polyline;

            public String getPolyline_width() {
                return polyline_width;
            }

            public void setPolyline_width(String polyline_width) {
                this.polyline_width = polyline_width;
            }

            public String getPolyline_color() {
                return polyline_color;
            }

            public void setPolyline_color(String polyline_color) {
                this.polyline_color = polyline_color;
            }

            public String getPolyline() {
                return polyline;
            }

            public void setPolyline(String polyline) {
                this.polyline = polyline;
            }
        }

        public static class StillMarkerBean {
            /**
             * marker_type : PICK
             * marker_lat : 28.412481830190167
             * marker_long : 77.04386293888092
             */

            private String marker_type;
            private String marker_lat;
            private String marker_long;

            public String getMarker_type() {
                return marker_type;
            }

            public void setMarker_type(String marker_type) {
                this.marker_type = marker_type;
            }

            public String getMarker_lat() {
                return marker_lat;
            }

            public void setMarker_lat(String marker_lat) {
                this.marker_lat = marker_lat;
            }

            public String getMarker_long() {
                return marker_long;
            }

            public void setMarker_long(String marker_long) {
                this.marker_long = marker_long;
            }
        }

        public static class MovableMarkerBean {
            /**
             * driver_marker_type : CAR_ONE
             * driver_marker_lat : 28.4124452
             * driver_marker_long : 77.0440977
             * driver_marker_bearing : 0.0
             */

            private String driver_marker_name;
            private String driver_marker_type;

            public String getDriver_marker_name() {
                return driver_marker_name;
            }

            public void setDriver_marker_name(String driver_marker_name) {
                this.driver_marker_name = driver_marker_name;
            }

            private String driver_marker_lat;
            private String driver_marker_long;
            private String driver_marker_bearing;

            public String getDriver_marker_type() {
                return driver_marker_type;
            }

            public void setDriver_marker_type(String driver_marker_type) {
                this.driver_marker_type = driver_marker_type;
            }

            public String getDriver_marker_lat() {
                return driver_marker_lat;
            }

            public void setDriver_marker_lat(String driver_marker_lat) {
                this.driver_marker_lat = driver_marker_lat;
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

        public static class VehicleDetailsBean {
            /**
             * service : Normal
             * vehicle : Sedan
             * vehicle_type_image : vehicle/eV2VLceCmFossYimUAQ2f4udNsKFA053VeqUWUv5.png
             * vehicle_number : HR1223445
             * vehicle_color : blue
             * vehicle_image : driverVehicle/RXbMhekvFAQNIIWoPJnYvF04rWHS4xSuaArEr7eA.jpeg
             * vehicle_make : Honda
             * vehicle_model : 2016
             */

//            private String service;
//            private String vehicle;
//            private String vehicle_type_image;
            private String vehicle_number;
//            private String vehicle_color;
//            private String vehicle_image;
//            private String vehicle_make;
//            private String vehicle_model;

//            public String getService() {
//                return service;
//            }
//
//            public void setService(String service) {
//                this.service = service;
//            }
//
//            public String getVehicle() {
//                return vehicle;
//            }
//
//            public void setVehicle(String vehicle) {
//                this.vehicle = vehicle;
//            }
//
//            public String getVehicle_type_image() {
//                return vehicle_type_image;
//            }
//
//            public void setVehicle_type_image(String vehicle_type_image) {
//                this.vehicle_type_image = vehicle_type_image;
//            }

            public String getVehicle_number() {
                return vehicle_number;
            }

            public void setVehicle_number(String vehicle_number) {
                this.vehicle_number = vehicle_number;
            }

//            public String getVehicle_color() {
//                return vehicle_color;
//            }
//
//            public void setVehicle_color(String vehicle_color) {
//                this.vehicle_color = vehicle_color;
//            }
//
//            public String getVehicle_image() {
//                return vehicle_image;
//            }
//
//            public void setVehicle_image(String vehicle_image) {
//                this.vehicle_image = vehicle_image;
//            }
//
//            public String getVehicle_make() {
//                return vehicle_make;
//            }
//
//            public void setVehicle_make(String vehicle_make) {
//                this.vehicle_make = vehicle_make;
//            }
//
//            public String getVehicle_model() {
//                return vehicle_model;
//            }
//
//            public void setVehicle_model(String vehicle_model) {
//                this.vehicle_model = vehicle_model;
//            }
        }

        public static class ServiceTypeBean {
            /**
             * id : 1
             * serviceName : Normal
             * serviceStatus : 1
             * package : 0
             * type : 1
             * created_at : 2018-05-18 20:30:00
             * updated_at : 2018-05-18 21:30:00
             */

            private int id;
            private String serviceName;
            private int serviceStatus;
            @SerializedName("package")
            private int packageX;
            private int type;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public int getServiceStatus() {
                return serviceStatus;
            }

            public void setServiceStatus(int serviceStatus) {
                this.serviceStatus = serviceStatus;
            }

            public int getPackageX() {
                return packageX;
            }

            public void setPackageX(int packageX) {
                this.packageX = packageX;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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

        public static class VehicleTypeBean {
            /**
             * id : 16
             * merchant_id : 2
             * vehicleTypeImage : vehicle/eV2VLceCmFossYimUAQ2f4udNsKFA053VeqUWUv5.png
             * vehicleTypeMapImage : vehicle/cjhfHxnWJsHt8KtmqEt5TkcIda44mAbGD46PyUoS.png
             * vehicleTypeRank : 1
             * vehicleTypeStatus : 1
             * pool_enable : 1
             * sequence : 1
             * rating : 0
             * created_at : 2019-02-23 12:52:18
             * updated_at : 2019-03-02 15:37:52
             */

            private int id;
            private int merchant_id;
            private String vehicleTypeImage;
            private String vehicleTypeMapImage;
            private int vehicleTypeRank;
            private int vehicleTypeStatus;
            private int pool_enable;
            private int sequence;
            private String rating;
            private String created_at;
            private String updated_at;

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

            public String getVehicleTypeImage() {
                return vehicleTypeImage;
            }

            public void setVehicleTypeImage(String vehicleTypeImage) {
                this.vehicleTypeImage = vehicleTypeImage;
            }

            public String getVehicleTypeMapImage() {
                return vehicleTypeMapImage;
            }

            public void setVehicleTypeMapImage(String vehicleTypeMapImage) {
                this.vehicleTypeMapImage = vehicleTypeMapImage;
            }

            public int getVehicleTypeRank() {
                return vehicleTypeRank;
            }

            public void setVehicleTypeRank(int vehicleTypeRank) {
                this.vehicleTypeRank = vehicleTypeRank;
            }

            public int getVehicleTypeStatus() {
                return vehicleTypeStatus;
            }

            public void setVehicleTypeStatus(int vehicleTypeStatus) {
                this.vehicleTypeStatus = vehicleTypeStatus;
            }

            public int getPool_enable() {
                return pool_enable;
            }

            public void setPool_enable(int pool_enable) {
                this.pool_enable = pool_enable;
            }

            public int getSequence() {
                return sequence;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
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

        public static class PaymentMethodBean {
            /**
             * id : 1
             * payment_method : Cash
             * payment_method_type : 1
             * payment_method_status : 1
             * payment_icon : payment_icon/qhQT6SwoNJbrvqGcVZ0fGqwfk8MXvcqjuNTmw3P8.png
             */

            private int id;
            private String payment_method;
            private int payment_method_type;
            private int payment_method_status;
            private String payment_icon;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPayment_method() {
                return payment_method;
            }

            public void setPayment_method(String payment_method) {
                this.payment_method = payment_method;
            }

            public int getPayment_method_type() {
                return payment_method_type;
            }

            public void setPayment_method_type(int payment_method_type) {
                this.payment_method_type = payment_method_type;
            }

            public int getPayment_method_status() {
                return payment_method_status;
            }

            public void setPayment_method_status(int payment_method_status) {
                this.payment_method_status = payment_method_status;
            }

            public String getPayment_icon() {
                return payment_icon;
            }

            public void setPayment_icon(String payment_icon) {
                this.payment_icon = payment_icon;
            }
        }

        public static class DriverBean {
            /**
             * id : 39
             * merchant_id : 2
             * unique_number : null
             * driver_gender : 0
             * fullName : Man
             * email : man@gmail.com
             * password : $2y$10$.nS1PSXh36VhyTjabl.A6ur5NtOmZDOKYYHSNR.nuXY5SRhBhYqQ2
             * home_location_active : 2
             * pool_ride_active : 1
             * status_for_pool : 1
             * avail_seats : 4
             * occupied_seats : 0
             * pick_exceed : null
             * pool_user_id : null
             * phoneNumber : +919879879879
             * profile_image : driver/aEGjZFX1YtzSqv1iHUvUCxp989tQCyCMnKsTDl79.jpeg
             * wallet_money : null
             * total_trips : 1
             * total_earnings : 45.00
             * total_comany_earning : 5.00
             * outstand_amount : -5.00
             * current_latitude : 28.4124452
             * current_longitude : 77.0440977
             * last_location_update_time : 2019-03-02 13:32:56
             * bearing : 0.0
             * accuracy : 13.359
             * player_id : 76370114-9af3-44a0-b087-35380c6c4119
             * rating : 0
             * country_area_id : 3
             * login_logout : 1
             * online_offline : 1
             * free_busy : 1
             * bank_name :
             * account_holder_name :
             * account_number :
             * driver_verify_status : 1
             * signupFrom : 1
             * signupStep : 3
             * driver_verification_date : null
             * driver_admin_status : 1
             * access_token_id : 03a27419d544e2ab8ee9bb301ae15dbb3512c9a3229de19ac2ac4665598588f7b69f812ca16f060c
             * driver_delete : null
             * last_ride_request_timestamp : 2019-03-02 13:36:24
             * created_at : 2019-02-27 11:06:57
             * updated_at : 2019-03-02 13:36:30
             */

            private int id;
//            private int merchant_id;
//            private Object unique_number;
//            private int driver_gender;
            private String fullName;
            private String email;
//            private String password;
//            private int home_location_active;
//            private int pool_ride_active;
//            private int status_for_pool;
//            private int avail_seats;
//            private int occupied_seats;
//            private Object pick_exceed;
//            private Object pool_user_id;
            private String phoneNumber;
            private String profile_image;
//            private Object wallet_money;
//            private int total_trips;
//            private String total_earnings;
//            private String total_comany_earning;
//            private String outstand_amount;
//            private String current_latitude;
//            private String current_longitude;
//            private String last_location_update_time;
//            private String bearing;
//            private String accuracy;
//            private String player_id;
            private String rating;
//            private int country_area_id;
//            private int login_logout;
//            private int online_offline;
//            private int free_busy;
//            private String bank_name;
//            private String account_holder_name;
//            private String account_number;
//            private int driver_verify_status;
//            private int signupFrom;
//            private int signupStep;
//            private Object driver_verification_date;
//            private int driver_admin_status;
//            private String access_token_id;
//            private Object driver_delete;
//            private String last_ride_request_timestamp;
//            private String created_at;
//            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

//            public int getMerchant_id() {
//                return merchant_id;
//            }
//
//            public void setMerchant_id(int merchant_id) {
//                this.merchant_id = merchant_id;
//            }
//
//            public Object getUnique_number() {
//                return unique_number;
//            }
//
//            public void setUnique_number(Object unique_number) {
//                this.unique_number = unique_number;
//            }
//
//            public int getDriver_gender() {
//                return driver_gender;
//            }
//
//            public void setDriver_gender(int driver_gender) {
//                this.driver_gender = driver_gender;
//            }

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

//            public String getPassword() {
//                return password;
//            }
//
//            public void setPassword(String password) {
//                this.password = password;
//            }
//
//            public int getHome_location_active() {
//                return home_location_active;
//            }
//
//            public void setHome_location_active(int home_location_active) {
//                this.home_location_active = home_location_active;
//            }

//            public int getPool_ride_active() {
//                return pool_ride_active;
//            }
//
//            public void setPool_ride_active(int pool_ride_active) {
//                this.pool_ride_active = pool_ride_active;
//            }
//
//            public int getStatus_for_pool() {
//                return status_for_pool;
//            }
//
//            public void setStatus_for_pool(int status_for_pool) {
//                this.status_for_pool = status_for_pool;
//            }

//            public int getAvail_seats() {
//                return avail_seats;
//            }
//
//            public void setAvail_seats(int avail_seats) {
//                this.avail_seats = avail_seats;
//            }
//
//            public int getOccupied_seats() {
//                return occupied_seats;
//            }
//
//            public void setOccupied_seats(int occupied_seats) {
//                this.occupied_seats = occupied_seats;
//            }

//            public Object getPick_exceed() {
//                return pick_exceed;
//            }
//
//            public void setPick_exceed(Object pick_exceed) {
//                this.pick_exceed = pick_exceed;
//            }
//
//            public Object getPool_user_id() {
//                return pool_user_id;
//            }
//
//            public void setPool_user_id(Object pool_user_id) {
//                this.pool_user_id = pool_user_id;
//            }

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

//            public Object getWallet_money() {
//                return wallet_money;
//            }
//
//            public void setWallet_money(Object wallet_money) {
//                this.wallet_money = wallet_money;
//            }
//
//            public int getTotal_trips() {
//                return total_trips;
//            }
//
//            public void setTotal_trips(int total_trips) {
//                this.total_trips = total_trips;
//            }
//
//            public String getTotal_earnings() {
//                return total_earnings;
//            }
//
//            public void setTotal_earnings(String total_earnings) {
//                this.total_earnings = total_earnings;
//            }
//
//            public String getTotal_comany_earning() {
//                return total_comany_earning;
//            }
//
//            public void setTotal_comany_earning(String total_comany_earning) {
//                this.total_comany_earning = total_comany_earning;
//            }
//
//            public String getOutstand_amount() {
//                return outstand_amount;
//            }
//
//            public void setOutstand_amount(String outstand_amount) {
//                this.outstand_amount = outstand_amount;
//            }
//
//            public String getCurrent_latitude() {
//                return current_latitude;
//            }
//
//            public void setCurrent_latitude(String current_latitude) {
//                this.current_latitude = current_latitude;
//            }
//
//            public String getCurrent_longitude() {
//                return current_longitude;
//            }
//
//            public void setCurrent_longitude(String current_longitude) {
//                this.current_longitude = current_longitude;
//            }
//
//            public String getLast_location_update_time() {
//                return last_location_update_time;
//            }
//
//            public void setLast_location_update_time(String last_location_update_time) {
//                this.last_location_update_time = last_location_update_time;
//            }
//
//            public String getBearing() {
//                return bearing;
//            }
//
//            public void setBearing(String bearing) {
//                this.bearing = bearing;
//            }
//
//            public String getAccuracy() {
//                return accuracy;
//            }
//
//            public void setAccuracy(String accuracy) {
//                this.accuracy = accuracy;
//            }
//
//            public String getPlayer_id() {
//                return player_id;
//            }
//
//            public void setPlayer_id(String player_id) {
//                this.player_id = player_id;
//            }
//
            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }
//
//            public int getCountry_area_id() {
//                return country_area_id;
//            }
//
//            public void setCountry_area_id(int country_area_id) {
//                this.country_area_id = country_area_id;
//            }
//
//            public int getLogin_logout() {
//                return login_logout;
//            }
//
//            public void setLogin_logout(int login_logout) {
//                this.login_logout = login_logout;
//            }
//
//            public int getOnline_offline() {
//                return online_offline;
//            }
//
//            public void setOnline_offline(int online_offline) {
//                this.online_offline = online_offline;
//            }
//
//            public int getFree_busy() {
//                return free_busy;
//            }
//
//            public void setFree_busy(int free_busy) {
//                this.free_busy = free_busy;
//            }
//
//            public String getBank_name() {
//                return bank_name;
//            }
//
//            public void setBank_name(String bank_name) {
//                this.bank_name = bank_name;
//            }
//
//            public String getAccount_holder_name() {
//                return account_holder_name;
//            }
//
//            public void setAccount_holder_name(String account_holder_name) {
//                this.account_holder_name = account_holder_name;
//            }
//
//            public String getAccount_number() {
//                return account_number;
//            }
//
//            public void setAccount_number(String account_number) {
//                this.account_number = account_number;
//            }
//
//            public int getDriver_verify_status() {
//                return driver_verify_status;
//            }
//
//            public void setDriver_verify_status(int driver_verify_status) {
//                this.driver_verify_status = driver_verify_status;
//            }
//
//            public int getSignupFrom() {
//                return signupFrom;
//            }
//
//            public void setSignupFrom(int signupFrom) {
//                this.signupFrom = signupFrom;
//            }
//
//            public int getSignupStep() {
//                return signupStep;
//            }
//
//            public void setSignupStep(int signupStep) {
//                this.signupStep = signupStep;
//            }
//
//            public Object getDriver_verification_date() {
//                return driver_verification_date;
//            }
//
//            public void setDriver_verification_date(Object driver_verification_date) {
//                this.driver_verification_date = driver_verification_date;
//            }
//
//            public int getDriver_admin_status() {
//                return driver_admin_status;
//            }
//
//            public void setDriver_admin_status(int driver_admin_status) {
//                this.driver_admin_status = driver_admin_status;
//            }
//
//            public String getAccess_token_id() {
//                return access_token_id;
//            }
//
//            public void setAccess_token_id(String access_token_id) {
//                this.access_token_id = access_token_id;
//            }
//
//            public Object getDriver_delete() {
//                return driver_delete;
//            }
//
//            public void setDriver_delete(Object driver_delete) {
//                this.driver_delete = driver_delete;
//            }
//
//            public String getLast_ride_request_timestamp() {
//                return last_ride_request_timestamp;
//            }
//
//            public void setLast_ride_request_timestamp(String last_ride_request_timestamp) {
//                this.last_ride_request_timestamp = last_ride_request_timestamp;
//            }
//
//            public String getCreated_at() {
//                return created_at;
//            }
//
//            public void setCreated_at(String created_at) {
//                this.created_at = created_at;
//            }
//
//            public String getUpdated_at() {
//                return updated_at;
//            }
//
//            public void setUpdated_at(String updated_at) {
//                this.updated_at = updated_at;
//            }
        }

        public static class SosBean {
            /**
             * id : 2
             * merchant_id : 2
             * number : 100
             * sosStatus : 1
             * created_at : 2019-02-28 16:57:26
             * updated_at : 2019-03-01 07:40:19
             * name : Police
             */

            private int id;
            private int merchant_id;
            private String number;
            private int sosStatus;
            private String created_at;
            private String updated_at;
            private String name;

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

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public int getSosStatus() {
                return sosStatus;
            }

            public void setSosStatus(int sosStatus) {
                this.sosStatus = sosStatus;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
