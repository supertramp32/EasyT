package com.seshra.everestcab.models;

import java.util.List;

public class ModelFragmentPastRides {


    /**
     * result : 1
     * message : Your Ride History
     * next_page_url : url goes here
     * total_pages : 2
     * current_page : 2
     * data : [{"booking_id":6,"map_image_visibility":true,"map_image":"https:maps.googleapis.com/maps/api/staticmap?center=&maptype=roadmap&path=weight:10%7Cenc:qyklD_gduMDB`@K_AgHQkBcBgOq@eH_@{DUkAMuA_@wCGg@o@uFE[}H|AaCd@eRnD_@JmIdBuB`@sAZC_@dFaAnDq@^GXYr@c@FIH[]gAmCmKoAaFoCoLw@iE_@_Cy@gFq@mD{@oD]oAQi@S_@MOu@q@oD}DiCqCeHiFsAiAmDeDiBiBaBaBtA}BpAeB`AkASWGMC]@I@CeAgAvAqBvB_DD[?s@{BIBsBrA@&sensor=false","pick_visibility":true,"pick_text":"1, Doctor Ambedkar Street, Fazilpur Jharsa, Sector 72, Gurugram, Haryana 122004, India","drop_visibility":true,"drop_location":"94, Block N, Mayfield Garden, Sector 51, Gurugram, Haryana 122018, India","driver_block_visibility":true,"status_text":"End","circular_image":"vehicle/mHleuybxEMxkxiGRFeZjoBFf7rFqH2FlQ7SeNJpS.png","highlighted_text":"Luxury","highlighted_small_text":"2018-08-06 11:13:44","value_text":"200Rs.","value_text_visibility":true,"value_text_color":"2ecc71","pickup_latitude":"28.41004718689952","pickup_longitude":"77.03142486512661","drop_latitude":"28.424856773338135","drop_longitude":"77.06141095608473","pick_marker_icon":"mapmarkers/circular-shape-silhouette.png","drop_marker_icon":"mapmarkers/small-circle-inside-a-big-circle.png","ploy_points":"qyklD_gduMDB`@K_AgHQkBcBgOq@eH_@{DUkAMuA_@wCGg@o@uFE[}H|AaCd@eRnD_@JmIdBuB`@sAZC_@dFaAnDq@^GXYr@c@FIH[]gAmCmKoAaFoCoLw@iE_@_Cy@gFq@mD{@oD]oAQi@S_@MOu@q@oD}DiCqCeHiFsAiAmDeDiBiBaBaBtA}BpAeB`AkASWGMC]@I@CeAgAvAqBvB_DD[?s@{BIBsBrA@"},{"booking_id":8,"map_image_visibility":true,"map_image":"https:maps.googleapis.com/maps/api/staticmap?center=&maptype=roadmap&path=weight:10%7Cenc:ukllDwtguMdB?LBDFDbNGFaABGJ?T?VtB@AiFAmCCoL?yDuF@cCB_CAeC?mAAcGFoBDO??yB?}@I}@Ou@i@iBmIfDwEfBYRMJGICCaB{AwDiEiAmAyAiA{DsCuD}C_EaEwIyIyAaBkB_BiFkF_FqEyAsAoBmBEFyArBmCxDyDcEUGy@y@{@lAaA{@w@u@QT&sensor=false","pick_visibility":true,"pick_text":"11, Orchid Petals Internal Road, Sector 49, Gurugram, Haryana 122018, India","drop_visibility":true,"drop_location":"2492, Sector 46 Road, Sector 46, Gurugram, Haryana 122003, India","driver_block_visibility":true,"status_text":"End","circular_image":"vehicle/mHleuybxEMxkxiGRFeZjoBFf7rFqH2FlQ7SeNJpS.png","highlighted_text":"Luxury","highlighted_small_text":"2018-08-06 11:46:21","value_text":"200Rs.","value_text_visibility":true,"value_text_color":"2ecc71","pickup_latitude":"28.413676719431972","pickup_longitude":"77.04961694777012","drop_latitude":"28.434046444868567","drop_longitude":"77.06296127289534","pick_marker_icon":"mapmarkers/circular-shape-silhouette.png","drop_marker_icon":"mapmarkers/small-circle-inside-a-big-circle.png","ploy_points":"ukllDwtguMdB?LBDFDbNGFaABGJ?T?VtB@AiFAmCCoL?yDuF@cCB_CAeC?mAAcGFoBDO??yB?}@I}@Ou@i@iBmIfDwEfBYRMJGICCaB{AwDiEiAmAyAiA{DsCuD}C_EaEwIyIyAaBkB_BiFkF_FqEyAsAoBmBEFyArBmCxDyDcEUGy@y@{@lAaA{@w@u@QT"}]
     */

    private String result;
    private String message;
    private String next_page_url;
    private int total_pages;
    private int current_page;
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

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * booking_id : 6
         * map_image_visibility : true
         * map_image : https:maps.googleapis.com/maps/api/staticmap?center=&maptype=roadmap&path=weight:10%7Cenc:qyklD_gduMDB`@K_AgHQkBcBgOq@eH_@{DUkAMuA_@wCGg@o@uFE[}H|AaCd@eRnD_@JmIdBuB`@sAZC_@dFaAnDq@^GXYr@c@FIH[]gAmCmKoAaFoCoLw@iE_@_Cy@gFq@mD{@oD]oAQi@S_@MOu@q@oD}DiCqCeHiFsAiAmDeDiBiBaBaBtA}BpAeB`AkASWGMC]@I@CeAgAvAqBvB_DD[?s@{BIBsBrA@&sensor=false
         * pick_visibility : true
         * pick_text : 1, Doctor Ambedkar Street, Fazilpur Jharsa, Sector 72, Gurugram, Haryana 122004, India
         * drop_visibility : true
         * drop_location : 94, Block N, Mayfield Garden, Sector 51, Gurugram, Haryana 122018, India
         * driver_block_visibility : true
         * status_text : End
         * circular_image : vehicle/mHleuybxEMxkxiGRFeZjoBFf7rFqH2FlQ7SeNJpS.png
         * highlighted_text : Luxury
         * highlighted_small_text : 2018-08-06 11:13:44
         * value_text : 200Rs.
         * value_text_visibility : true
         * value_text_color : 2ecc71
         * pickup_latitude : 28.41004718689952
         * pickup_longitude : 77.03142486512661
         * drop_latitude : 28.424856773338135
         * drop_longitude : 77.06141095608473
         * pick_marker_icon : mapmarkers/circular-shape-silhouette.png
         * drop_marker_icon : mapmarkers/small-circle-inside-a-big-circle.png
         * ploy_points : qyklD_gduMDB`@K_AgHQkBcBgOq@eH_@{DUkAMuA_@wCGg@o@uFE[}H|AaCd@eRnD_@JmIdBuB`@sAZC_@dFaAnDq@^GXYr@c@FIH[]gAmCmKoAaFoCoLw@iE_@_Cy@gFq@mD{@oD]oAQi@S_@MOu@q@oD}DiCqCeHiFsAiAmDeDiBiBaBaBtA}BpAeB`AkASWGMC]@I@CeAgAvAqBvB_DD[?s@{BIBsBrA@
         */

        private String payment_method;
        private int booking_id;
        private boolean map_image_visibility;
        private String map_image;
        private boolean pick_visibility;
        private String pick_text;
        private boolean drop_visibility;
        private String drop_location;
        private boolean driver_block_visibility;
        private String status_text;
        private String circular_image;
        private String highlighted_text;
        private String highlighted_small_text;
        private String value_text;
        private boolean value_text_visibility;
        private String value_text_color;
        private String pickup_latitude;
        private String pickup_longitude;
        private String drop_latitude;
        private String drop_longitude;
        private String pick_marker_icon;
        private String drop_marker_icon;
        private String ploy_points;

        public int getBooking_id() {
            return booking_id;
        }

        public void setBooking_id(int booking_id) {
            this.booking_id = booking_id;
        }

        public boolean isMap_image_visibility() {
            return map_image_visibility;
        }

        public void setMap_image_visibility(boolean map_image_visibility) {
            this.map_image_visibility = map_image_visibility;
        }

        public String getMap_image() {
            return map_image;
        }

        public void setMap_image(String map_image) {
            this.map_image = map_image;
        }

        public boolean isPick_visibility() {
            return pick_visibility;
        }

        public void setPick_visibility(boolean pick_visibility) {
            this.pick_visibility = pick_visibility;
        }

        public String getPick_text() {
            return pick_text;
        }

        public void setPick_text(String pick_text) {
            this.pick_text = pick_text;
        }

        public boolean isDrop_visibility() {
            return drop_visibility;
        }

        public void setDrop_visibility(boolean drop_visibility) {
            this.drop_visibility = drop_visibility;
        }

        public String getDrop_location() {
            return drop_location;
        }

        public void setDrop_location(String drop_location) {
            this.drop_location = drop_location;
        }

        public boolean isDriver_block_visibility() {
            return driver_block_visibility;
        }

        public void setDriver_block_visibility(boolean driver_block_visibility) {
            this.driver_block_visibility = driver_block_visibility;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public String getCircular_image() {
            return circular_image;
        }

        public void setCircular_image(String circular_image) {
            this.circular_image = circular_image;
        }

        public String getHighlighted_text() {
            return highlighted_text;
        }

        public void setHighlighted_text(String highlighted_text) {
            this.highlighted_text = highlighted_text;
        }

        public String getHighlighted_small_text() {
            return highlighted_small_text;
        }

        public void setHighlighted_small_text(String highlighted_small_text) {
            this.highlighted_small_text = highlighted_small_text;
        }

        public String getValue_text() {
            return value_text;
        }

        public void setValue_text(String value_text) {
            this.value_text = value_text;
        }

        public boolean isValue_text_visibility() {
            return value_text_visibility;
        }

        public void setValue_text_visibility(boolean value_text_visibility) {
            this.value_text_visibility = value_text_visibility;
        }

        public String getValue_text_color() {
            return value_text_color;
        }

        public void setValue_text_color(String value_text_color) {
            this.value_text_color = value_text_color;
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

        public String getPick_marker_icon() {
            return pick_marker_icon;
        }

        public void setPick_marker_icon(String pick_marker_icon) {
            this.pick_marker_icon = pick_marker_icon;
        }

        public String getDrop_marker_icon() {
            return drop_marker_icon;
        }

        public void setDrop_marker_icon(String drop_marker_icon) {
            this.drop_marker_icon = drop_marker_icon;
        }

        public String getPloy_points() {
            return ploy_points;
        }

        public void setPloy_points(String ploy_points) {
            this.ploy_points = ploy_points;
        }


        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }
    }
}
