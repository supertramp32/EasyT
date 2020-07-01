package com.seshra.everestcab.models;

import com.google.gson.annotations.SerializedName;

public class ModelConfirm {


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
        private String merchant_id;
        private int merchant_booking_id;
        private String user_id;
        private String country_area_id;
        private String service_type_id;

        public int getMerchant_booking_id() {
            return merchant_booking_id;
        }

        public void setMerchant_booking_id(int merchant_booking_id) {
            this.merchant_booking_id = merchant_booking_id;
        }

        private String vehicle_type_id;
        private Object package_id;
        private String price_card_id;
        private String total_drop_location;
        private int auto_upgradetion;
        private String number_of_rider;
        private String payment_method_id;
        private String card_id;
        private String pickup_latitude;
        private String pickup_longitude;
        private String pickup_location;
        private String drop_latitude;
        private String drop_longitude;
        private String drop_location;
        private String waypoints;
        private String promo_code;
        private String map_image;
        private String estimate_bill;
        private String estimate_distance;
        private String estimate_time;
        private String estimate_driver_distnace;
        private String estimate_driver_time;
        private String booking_type;
        private Object later_booking_date;
        private Object later_booking_time;
        private Object return_date;
        private Object return_time;
        private Object additional_notes;
        private String created_at;
        private String updated_at;
        private int booking_timestamp;
        private int booking_status;
        private int id;
        @SerializedName("package")
        private Object packageX;
        private VehicleTypeBean vehicle_type;
        private UserBean user;
        private PaymentMethodBean payment_method;
        private ServiceTypeBean service_type;

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCountry_area_id() {
            return country_area_id;
        }

        public void setCountry_area_id(String country_area_id) {
            this.country_area_id = country_area_id;
        }

        public String getService_type_id() {
            return service_type_id;
        }

        public void setService_type_id(String service_type_id) {
            this.service_type_id = service_type_id;
        }

        public String getVehicle_type_id() {
            return vehicle_type_id;
        }

        public void setVehicle_type_id(String vehicle_type_id) {
            this.vehicle_type_id = vehicle_type_id;
        }

        public Object getPackage_id() {
            return package_id;
        }

        public void setPackage_id(Object package_id) {
            this.package_id = package_id;
        }

        public String getPrice_card_id() {
            return price_card_id;
        }

        public void setPrice_card_id(String price_card_id) {
            this.price_card_id = price_card_id;
        }

        public String getTotal_drop_location() {
            return total_drop_location;
        }

        public void setTotal_drop_location(String total_drop_location) {
            this.total_drop_location = total_drop_location;
        }

        public int getAuto_upgradetion() {
            return auto_upgradetion;
        }

        public void setAuto_upgradetion(int auto_upgradetion) {
            this.auto_upgradetion = auto_upgradetion;
        }

        public String getNumber_of_rider() {
            return number_of_rider;
        }

        public void setNumber_of_rider(String number_of_rider) {
            this.number_of_rider = number_of_rider;
        }

        public String getPayment_method_id() {
            return payment_method_id;
        }

        public void setPayment_method_id(String payment_method_id) {
            this.payment_method_id = payment_method_id;
        }

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public String getPickup_latitude() {
            return pickup_latitude;
        }

        public void setPickup_latitude(String pickup_latitude) {
            this.pickup_latitude = pickup_latitude;
        }

        public String getPickup_longitude() {
            return pickup_longitude;
        }

        public void setPickup_longitude(String pickup_longitude) {
            this.pickup_longitude = pickup_longitude;
        }

        public String getPickup_location() {
            return pickup_location;
        }

        public void setPickup_location(String pickup_location) {
            this.pickup_location = pickup_location;
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

        public String getWaypoints() {
            return waypoints;
        }

        public void setWaypoints(String waypoints) {
            this.waypoints = waypoints;
        }

        public String getPromo_code() {
            return promo_code;
        }

        public void setPromo_code(String promo_code) {
            this.promo_code = promo_code;
        }

        public String getMap_image() {
            return map_image;
        }

        public void setMap_image(String map_image) {
            this.map_image = map_image;
        }

        public String getEstimate_bill() {
            return estimate_bill;
        }

        public void setEstimate_bill(String estimate_bill) {
            this.estimate_bill = estimate_bill;
        }

        public String getEstimate_distance() {
            return estimate_distance;
        }

        public void setEstimate_distance(String estimate_distance) {
            this.estimate_distance = estimate_distance;
        }

        public String getEstimate_time() {
            return estimate_time;
        }

        public void setEstimate_time(String estimate_time) {
            this.estimate_time = estimate_time;
        }

        public String getEstimate_driver_distnace() {
            return estimate_driver_distnace;
        }

        public void setEstimate_driver_distnace(String estimate_driver_distnace) {
            this.estimate_driver_distnace = estimate_driver_distnace;
        }

        public String getEstimate_driver_time() {
            return estimate_driver_time;
        }

        public void setEstimate_driver_time(String estimate_driver_time) {
            this.estimate_driver_time = estimate_driver_time;
        }

        public String getBooking_type() {
            return booking_type;
        }

        public void setBooking_type(String booking_type) {
            this.booking_type = booking_type;
        }

        public Object getLater_booking_date() {
            return later_booking_date;
        }

        public void setLater_booking_date(Object later_booking_date) {
            this.later_booking_date = later_booking_date;
        }

        public Object getLater_booking_time() {
            return later_booking_time;
        }

        public void setLater_booking_time(Object later_booking_time) {
            this.later_booking_time = later_booking_time;
        }

        public Object getReturn_date() {
            return return_date;
        }

        public void setReturn_date(Object return_date) {
            this.return_date = return_date;
        }

        public Object getReturn_time() {
            return return_time;
        }

        public void setReturn_time(Object return_time) {
            this.return_time = return_time;
        }

        public Object getAdditional_notes() {
            return additional_notes;
        }

        public void setAdditional_notes(Object additional_notes) {
            this.additional_notes = additional_notes;
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

        public int getBooking_timestamp() {
            return booking_timestamp;
        }

        public void setBooking_timestamp(int booking_timestamp) {
            this.booking_timestamp = booking_timestamp;
        }

        public int getBooking_status() {
            return booking_status;
        }

        public void setBooking_status(int booking_status) {
            this.booking_status = booking_status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getPackageX() {
            return packageX;
        }

        public void setPackageX(Object packageX) {
            this.packageX = packageX;
        }

        public VehicleTypeBean getVehicle_type() {
            return vehicle_type;
        }

        public void setVehicle_type(VehicleTypeBean vehicle_type) {
            this.vehicle_type = vehicle_type;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public PaymentMethodBean getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(PaymentMethodBean payment_method) {
            this.payment_method = payment_method;
        }

        public ServiceTypeBean getService_type() {
            return service_type;
        }

        public void setService_type(ServiceTypeBean service_type) {
            this.service_type = service_type;
        }

        public static class VehicleTypeBean {
            private int id;
            private String merchant_id;
            private String vehicleTypeImage;
            private String vehicleTypeMapImage;
            private String vehicleTypeRank;
            private String vehicleTypeStatus;
            private String pool_enable;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(String merchant_id) {
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

            public String getVehicleTypeRank() {
                return vehicleTypeRank;
            }

            public void setVehicleTypeRank(String vehicleTypeRank) {
                this.vehicleTypeRank = vehicleTypeRank;
            }

            public String getVehicleTypeStatus() {
                return vehicleTypeStatus;
            }

            public void setVehicleTypeStatus(String vehicleTypeStatus) {
                this.vehicleTypeStatus = vehicleTypeStatus;
            }

            public String getPool_enable() {
                return pool_enable;
            }

            public void setPool_enable(String pool_enable) {
                this.pool_enable = pool_enable;
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

        public static class UserBean {
            private int id;
            private String merchant_id;
            private String country_id;
            private Object social_id;
            private String county_area_id;
            private Object unique_number;
            private String user_type;
            private Object corporate_id;
            private Object corporate_email;
            private String UserName;
            private String UserPhone;
            private String email;
            private String password;
            private String ride_otp;
            private String total_trips;
            private Object wallet_balance;
            private String UserSignupType;
            private String UserSignupFrom;
            private String UserProfileImage;
            private String ReferralCode;
            private String rating;
            private String manual_user;
            private String EmailVerified;
            private String PhoneVerified;
            private String UserStatus;
            private Object outstanding_amount;
            private Object user_gender;
            private Object remember_token;
            private String created_at;
            private String updated_at;
            private Object user_delete;
            private Object my_services;
            private Object detail_status;
            private Object identity_verification_status;
            private Object average_rating;
            private Object signup_completed;
            private Object signup_status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(String merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getCountry_id() {
                return country_id;
            }

            public void setCountry_id(String country_id) {
                this.country_id = country_id;
            }

            public Object getSocial_id() {
                return social_id;
            }

            public void setSocial_id(Object social_id) {
                this.social_id = social_id;
            }

            public String getCounty_area_id() {
                return county_area_id;
            }

            public void setCounty_area_id(String county_area_id) {
                this.county_area_id = county_area_id;
            }

            public Object getUnique_number() {
                return unique_number;
            }

            public void setUnique_number(Object unique_number) {
                this.unique_number = unique_number;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }

            public Object getCorporate_id() {
                return corporate_id;
            }

            public void setCorporate_id(Object corporate_id) {
                this.corporate_id = corporate_id;
            }

            public Object getCorporate_email() {
                return corporate_email;
            }

            public void setCorporate_email(Object corporate_email) {
                this.corporate_email = corporate_email;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String UserName) {
                this.UserName = UserName;
            }

            public String getUserPhone() {
                return UserPhone;
            }

            public void setUserPhone(String UserPhone) {
                this.UserPhone = UserPhone;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getRide_otp() {
                return ride_otp;
            }

            public void setRide_otp(String ride_otp) {
                this.ride_otp = ride_otp;
            }

            public String getTotal_trips() {
                return total_trips;
            }

            public void setTotal_trips(String total_trips) {
                this.total_trips = total_trips;
            }

            public Object getWallet_balance() {
                return wallet_balance;
            }

            public void setWallet_balance(Object wallet_balance) {
                this.wallet_balance = wallet_balance;
            }

            public String getUserSignupType() {
                return UserSignupType;
            }

            public void setUserSignupType(String UserSignupType) {
                this.UserSignupType = UserSignupType;
            }

            public String getUserSignupFrom() {
                return UserSignupFrom;
            }

            public void setUserSignupFrom(String UserSignupFrom) {
                this.UserSignupFrom = UserSignupFrom;
            }

            public String getUserProfileImage() {
                return UserProfileImage;
            }

            public void setUserProfileImage(String UserProfileImage) {
                this.UserProfileImage = UserProfileImage;
            }

            public String getReferralCode() {
                return ReferralCode;
            }

            public void setReferralCode(String ReferralCode) {
                this.ReferralCode = ReferralCode;
            }

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

            public String getManual_user() {
                return manual_user;
            }

            public void setManual_user(String manual_user) {
                this.manual_user = manual_user;
            }

            public String getEmailVerified() {
                return EmailVerified;
            }

            public void setEmailVerified(String EmailVerified) {
                this.EmailVerified = EmailVerified;
            }

            public String getPhoneVerified() {
                return PhoneVerified;
            }

            public void setPhoneVerified(String PhoneVerified) {
                this.PhoneVerified = PhoneVerified;
            }

            public String getUserStatus() {
                return UserStatus;
            }

            public void setUserStatus(String UserStatus) {
                this.UserStatus = UserStatus;
            }

            public Object getOutstanding_amount() {
                return outstanding_amount;
            }

            public void setOutstanding_amount(Object outstanding_amount) {
                this.outstanding_amount = outstanding_amount;
            }

            public Object getUser_gender() {
                return user_gender;
            }

            public void setUser_gender(Object user_gender) {
                this.user_gender = user_gender;
            }

            public Object getRemember_token() {
                return remember_token;
            }

            public void setRemember_token(Object remember_token) {
                this.remember_token = remember_token;
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

            public Object getUser_delete() {
                return user_delete;
            }

            public void setUser_delete(Object user_delete) {
                this.user_delete = user_delete;
            }

            public Object getMy_services() {
                return my_services;
            }

            public void setMy_services(Object my_services) {
                this.my_services = my_services;
            }

            public Object getDetail_status() {
                return detail_status;
            }

            public void setDetail_status(Object detail_status) {
                this.detail_status = detail_status;
            }

            public Object getIdentity_verification_status() {
                return identity_verification_status;
            }

            public void setIdentity_verification_status(Object identity_verification_status) {
                this.identity_verification_status = identity_verification_status;
            }

            public Object getAverage_rating() {
                return average_rating;
            }

            public void setAverage_rating(Object average_rating) {
                this.average_rating = average_rating;
            }

            public Object getSignup_completed() {
                return signup_completed;
            }

            public void setSignup_completed(Object signup_completed) {
                this.signup_completed = signup_completed;
            }

            public Object getSignup_status() {
                return signup_status;
            }

            public void setSignup_status(Object signup_status) {
                this.signup_status = signup_status;
            }
        }

        public static class PaymentMethodBean {
            private int id;
            private String payment_method;
            private String payment_method_type;
            private String payment_method_status;
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

            public String getPayment_method_type() {
                return payment_method_type;
            }

            public void setPayment_method_type(String payment_method_type) {
                this.payment_method_type = payment_method_type;
            }

            public String getPayment_method_status() {
                return payment_method_status;
            }

            public void setPayment_method_status(String payment_method_status) {
                this.payment_method_status = payment_method_status;
            }

            public String getPayment_icon() {
                return payment_icon;
            }

            public void setPayment_icon(String payment_icon) {
                this.payment_icon = payment_icon;
            }
        }

        public static class ServiceTypeBean {
            private int id;
            private String serviceName;
            private String serviceStatus;
            @SerializedName("package")
            private String packageX;
            private String type;
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

            public String getServiceStatus() {
                return serviceStatus;
            }

            public void setServiceStatus(String serviceStatus) {
                this.serviceStatus = serviceStatus;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
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
    }
}