package com.seshra.everestcab.models;

public class ModelTackRide {


    /**
     * result : 1
     * message : Testing
     * data : {"stil_marker":{"marker_type":"PICK","marker_lat":"28.412481830190167","marker_long":"77.04386293888092"},"tip_status":false,"movable_marker_type":{"driver_marker_type":"CAR_ONE","driver_marker_lat":"28.4124452","driver_marker_long":"77.0440977","driver_marker_bearing":"0.0"},"polydata":{"polyline_width":"8","polyline_color":"333333","polyline":"_hllDstfuM?j@"},"location":{"estimate_driver_time":"1 min","estimate_driver_distnace":"21 m","trip_status_text":"Arrive At Location","location_headline":"Pickup","location_text":"IRIS Tech Park, Sector 49, Gurugram, Haryana 122018, India","location_color":"2ecc71","location_editable":false},"cancelable":true}
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
         * stil_marker : {"marker_type":"PICK","marker_lat":"28.412481830190167","marker_long":"77.04386293888092"}
         * tip_status : false
         * movable_marker_type : {"driver_marker_type":"CAR_ONE","driver_marker_lat":"28.4124452","driver_marker_long":"77.0440977","driver_marker_bearing":"0.0"}
         * polydata : {"polyline_width":"8","polyline_color":"333333","polyline":"_hllDstfuM?j@"}
         * location : {"estimate_driver_time":"1 min","estimate_driver_distnace":"21 m","trip_status_text":"Arrive At Location","location_headline":"Pickup","location_text":"IRIS Tech Park, Sector 49, Gurugram, Haryana 122018, India","location_color":"2ecc71","location_editable":false}
         * cancelable : true
         */

        private StilMarkerBean stil_marker;
        private boolean tip_status;
        private MovableMarkerTypeBean movable_marker_type;
        private PolydataBean polydata;
        private LocationBean location;
        private boolean cancelable;

        public StilMarkerBean getStil_marker() {
            return stil_marker;
        }

        public void setStil_marker(StilMarkerBean stil_marker) {
            this.stil_marker = stil_marker;
        }

        public boolean isTip_status() {
            return tip_status;
        }

        public void setTip_status(boolean tip_status) {
            this.tip_status = tip_status;
        }

        public MovableMarkerTypeBean getMovable_marker_type() {
            return movable_marker_type;
        }

        public void setMovable_marker_type(MovableMarkerTypeBean movable_marker_type) {
            this.movable_marker_type = movable_marker_type;
        }

        public PolydataBean getPolydata() {
            return polydata;
        }

        public void setPolydata(PolydataBean polydata) {
            this.polydata = polydata;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public boolean isCancelable() {
            return cancelable;
        }

        public void setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
        }

        public static class StilMarkerBean {
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

        public static class MovableMarkerTypeBean {
            /**
             * driver_marker_type : CAR_ONE
             * driver_marker_lat : 28.4124452
             * driver_marker_long : 77.0440977
             * driver_marker_bearing : 0.0
             */

            private String driver_marker_name;
            private String driver_marker_type;
            private String driver_marker_lat;
            private String driver_marker_long;

            public String getDriver_marker_name() {
                return driver_marker_name;
            }

            public void setDriver_marker_name(String driver_marker_name) {
                this.driver_marker_name = driver_marker_name;
            }

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

        public static class LocationBean {
            /**
             * estimate_driver_time : 1 min
             * estimate_driver_distnace : 21 m
             * trip_status_text : Arrive At Location
             * location_headline : Pickup
             * location_text : IRIS Tech Park, Sector 49, Gurugram, Haryana 122018, India
             * location_color : 2ecc71
             * location_editable : false
             */

            private String estimate_driver_time;
            private String estimate_driver_distnace;
            private String trip_status_text;
            private String location_headline;
            private String location_text;
            private String location_color;
            private boolean location_editable;

            public String getEstimate_driver_time() {
                return estimate_driver_time;
            }

            public void setEstimate_driver_time(String estimate_driver_time) {
                this.estimate_driver_time = estimate_driver_time;
            }

            public String getEstimate_driver_distnace() {
                return estimate_driver_distnace;
            }

            public void setEstimate_driver_distnace(String estimate_driver_distnace) {
                this.estimate_driver_distnace = estimate_driver_distnace;
            }

            public String getTrip_status_text() {
                return trip_status_text;
            }

            public void setTrip_status_text(String trip_status_text) {
                this.trip_status_text = trip_status_text;
            }

            public String getLocation_headline() {
                return location_headline;
            }

            public void setLocation_headline(String location_headline) {
                this.location_headline = location_headline;
            }

            public String getLocation_text() {
                return location_text;
            }

            public void setLocation_text(String location_text) {
                this.location_text = location_text;
            }

            public String getLocation_color() {
                return location_color;
            }

            public void setLocation_color(String location_color) {
                this.location_color = location_color;
            }

            public boolean isLocation_editable() {
                return location_editable;
            }

            public void setLocation_editable(boolean location_editable) {
                this.location_editable = location_editable;
            }
        }
    }
}
