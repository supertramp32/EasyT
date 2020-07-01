package com.seshra.everestcab.models;

import java.util.List;

public class ModelConfiguration {

    private String result;
    private String message;
    private Data data;



    public class Data{


        private App_Version app_version;
        private App_Maintainance app_maintainance;
        private List<Languages> languages;
        private List<Countries> countries;
        private Ride_Config ride_config;
        private Customer_Support customer_support;



        public class App_Version{

            private boolean show_dialog;
            private boolean mandatory;
            private String dialog_message;
            private String ios_user_appid;


            public boolean isShow_dialog() {
                return show_dialog;
            }

            public boolean isMadatory() {
                return mandatory;
            }

            public String getDialog_message() {
                return dialog_message;
            }

            public String getIos_user_appid() {
                return ios_user_appid;
            }

            public void setShow_dialog(boolean show_dialog) {
                this.show_dialog = show_dialog;
            }

            public void setMadatory(boolean mandatory) {
                this.mandatory = mandatory;
            }

            public void setDialog_message(String dialog_message) {
                this.dialog_message = dialog_message;
            }

            public void setIos_user_appid(String ios_user_appid) {
                this.ios_user_appid = ios_user_appid;
            }
        }



        public class App_Maintainance{

            private boolean show_dialog;
            private String show_message;


            public boolean isShow_dialog() {
                return show_dialog;
            }

            public String getShow_message() {
                return show_message;
            }

            public void setShow_dialog(boolean show_dialog) {
                this.show_dialog = show_dialog;
            }

            public void setShow_message(String show_message) {
                this.show_message = show_message;
            }
        }



        public class Languages{

            private String id;
            private String name;
            private String locale;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLocale() {
                return locale;
            }

            public void setLocale(String locale) {
                this.locale = locale;
            }
        }


        public class Countries{

            int id;
            String isoCode;
            String phoneCode;
            String name;
            String currency;


            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIsoCode() {
                return isoCode;
            }

            public void setIsoCode(String isoCode) {
                this.isoCode = isoCode;
            }

            public String getPhoneCode() {
                return phoneCode;
            }

            public void setPhoneCode(String phoneCode) {
                this.phoneCode = phoneCode;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }
        }



        public class Ride_Config{


            String outstation_ride_now_enabled;
            String user_request_timeout;

            public String getOutstation_ride_now_enabled() {
                return outstation_ride_now_enabled;
            }

            public void setOutstation_ride_now_enabled(String outstation_ride_now_enabled) {
                this.outstation_ride_now_enabled = outstation_ride_now_enabled;
            }

            public String getUser_request_timeout() {
                return user_request_timeout;
            }

            public void setUser_request_timeout(String user_request_timeout) {
                this.user_request_timeout = user_request_timeout;
            }
        }



        public class General_Config{


            String googleKey;
            String wallet;
            String homescreen_estimate_fare;
            String default_language;
            String sur_charge;
            String emergency_contact;


            public String getGoogleKey() {
                return googleKey;
            }

            public void setGoogleKey(String googleKey) {
                this.googleKey = googleKey;
            }

            public String getWallet() {
                return wallet;
            }

            public void setWallet(String wallet) {
                this.wallet = wallet;
            }

            public String getHomescreen_estimate_fare() {
                return homescreen_estimate_fare;
            }

            public void setHomescreen_estimate_fare(String homescreen_estimate_fare) {
                this.homescreen_estimate_fare = homescreen_estimate_fare;
            }

            public String getDefault_language() {
                return default_language;
            }

            public void setDefault_language(String default_language) {
                this.default_language = default_language;
            }

            public String getSur_charge() {
                return sur_charge;
            }

            public void setSur_charge(String sur_charge) {
                this.sur_charge = sur_charge;
            }

            public String getEmergency_contact() {
                return emergency_contact;
            }

            public void setEmergency_contact(String emergency_contact) {
                this.emergency_contact = emergency_contact;
            }
        }



        public class Customer_Support{

            String mail;
            String phone;
            String address;
            String about_us;

            public String getMail() {
                return mail;
            }

            public void setMail(String mail) {
                this.mail = mail;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }


            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAbout_us() {
                return about_us;
            }

            public void setAbout_us(String about_us) {
                this.about_us = about_us;
            }
        }


        public class PaymentOption{

        }


        public App_Version getApp_version() {
            return app_version;
        }

        public void setApp_version(App_Version app_version) {
            this.app_version = app_version;
        }

        public App_Maintainance getApp_maintainance() {
            return app_maintainance;
        }

        public void setApp_maintainance(App_Maintainance app_maintainance) {
            this.app_maintainance = app_maintainance;
        }

        public List<Languages> getLanguages() {
            return languages;
        }

        public void setLanguages(List<Languages> languages) {
            this.languages = languages;
        }

        public List<Countries> getCountries() {
            return countries;
        }

        public void setCountries(List<Countries> countries) {
            this.countries = countries;
        }

        public Ride_Config getRide_config() {
            return ride_config;
        }

        public void setRide_config(Ride_Config ride_config) {
            this.ride_config = ride_config;
        }

        public Customer_Support getCustomer_support() {
            return customer_support;
        }

        public void setCustomer_support(Customer_Support customer_support) {
            this.customer_support = customer_support;
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
