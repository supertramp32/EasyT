package com.seshra.everestcab.models;

import java.util.List;

public class ModelSpecificTripDetails {

    /**
     * result : 1
     * message : Ride Detail
     * bookable : true
     * data : {"holder_map_image":{"visibility":true,"data":{"map_image":"https:maps.googleapis.com/maps/api/staticmap?center=&maptype=roadmap&path=weight:10%7Cenc:}ullD{kguMoCIE??~@oD?M@?d@Kz@Wd@eAtAiAdAcAj@iGjBfApEjF~SBLZDZFhA\\|Dw@vJkBBZs@LBXeB^yPjDwDp@D\\\\xCZfBd@jDz@nE@N?Fi@~Ac@lAZjBL@nCpAn@\\P?`@Gf@ArAIlAQ?E&sensor=false"}},"cancel_reasons":[{"id":1,"reason":"Driver Refused to come"}],"holder_booking_description":{"visibility":true,"data":{"highlighted_left_text":"2018-08-22 06:41:16","highlighted_left_text_style":"BOLD","highlighted_left_text_color":"333333","small_left_text":"Normal Luxury","small_left_text_style":"NORMAL","small_left_text_color":"bbbbbb","highlighted_right_text":"200","highlighted_right_text_style":"NORMAL","highlighted_right_text_color":"333333","small_right_text":"Payment Done","small_right_text_style":"BOLD","small_right_text_color":"e74c3c"}},"holder_pickdrop_location":{"visibility":true,"data":{"pick_text_visibility":true,"pick_text":"C-5, S City Rd, South City II, Sector 49, Gurugram, Haryana 122018, India","drop_text_visibility":true,"drop_text":"Jai vihar Plot 240, Sector 48, Gurugram, Haryana 122004, India"}},"holder_metering":{"visibility":true,"data":{"text_one":"200","text_two":"0","text_three":"00:00:05"}},"holder_driver":{"visibility":true,"data":{"circular_image":"driver/OGodTPeyp4i9gUvtJZ0LqjWhkrkcYe1Xwa9IM04r.jpeg","highlighted_text":"Gautam","small_text":"gautam@seshra.com","rating_visibility":true,"rating":"4.75","rating_button_visibility":false,"rating_button_enable":false,"rating_button_text":"YOU RATE","rating_button_text_color":"333333","rating_button_text_style":"NORMAL"}},"holder_receipt":{"visibility":true,"data":[{"highlighted_text":"Bill Details","highlighted_text_color":"333333","highlighted_style":"BOLD","highlighted_visibility":true,"small_text":"eee","small_text_color":"333333","small_text_style":"NORMAL","small_text_visibility":false,"value_text":"ee","value_text_color":"333333","value_text_style":"NORMAL","value_textvisibility":false}]},"button_visibility":{"track":false,"cancel":false,"mail_invoice":true,"support":true,"coupon":false}}
     */

    private String result;
    private String message;
    private boolean bookable;
    private DataBeanXXXXXX data;

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

    public boolean isBookable() {
        return bookable;
    }

    public void setBookable(boolean bookable) {
        this.bookable = bookable;
    }

    public DataBeanXXXXXX getData() {
        return data;
    }

    public void setData(DataBeanXXXXXX data) {
        this.data = data;
    }

    public static class DataBeanXXXXXX {
        /**
         * holder_map_image : {"visibility":true,"data":{"map_image":"https:maps.googleapis.com/maps/api/staticmap?center=&maptype=roadmap&path=weight:10%7Cenc:}ullD{kguMoCIE??~@oD?M@?d@Kz@Wd@eAtAiAdAcAj@iGjBfApEjF~SBLZDZFhA\\|Dw@vJkBBZs@LBXeB^yPjDwDp@D\\\\xCZfBd@jDz@nE@N?Fi@~Ac@lAZjBL@nCpAn@\\P?`@Gf@ArAIlAQ?E&sensor=false"}}
         * cancel_reasons : [{"id":1,"reason":"Driver Refused to come"}]
         * holder_booking_description : {"visibility":true,"data":{"highlighted_left_text":"2018-08-22 06:41:16","highlighted_left_text_style":"BOLD","highlighted_left_text_color":"333333","small_left_text":"Normal Luxury","small_left_text_style":"NORMAL","small_left_text_color":"bbbbbb","highlighted_right_text":"200","highlighted_right_text_style":"NORMAL","highlighted_right_text_color":"333333","small_right_text":"Payment Done","small_right_text_style":"BOLD","small_right_text_color":"e74c3c"}}
         * holder_pickdrop_location : {"visibility":true,"data":{"pick_text_visibility":true,"pick_text":"C-5, S City Rd, South City II, Sector 49, Gurugram, Haryana 122018, India","drop_text_visibility":true,"drop_text":"Jai vihar Plot 240, Sector 48, Gurugram, Haryana 122004, India"}}
         * holder_metering : {"visibility":true,"data":{"text_one":"200","text_two":"0","text_three":"00:00:05"}}
         * holder_driver : {"visibility":true,"data":{"circular_image":"driver/OGodTPeyp4i9gUvtJZ0LqjWhkrkcYe1Xwa9IM04r.jpeg","highlighted_text":"Gautam","small_text":"gautam@seshra.com","rating_visibility":true,"rating":"4.75","rating_button_visibility":false,"rating_button_enable":false,"rating_button_text":"YOU RATE","rating_button_text_color":"333333","rating_button_text_style":"NORMAL"}}
         * holder_receipt : {"visibility":true,"data":[{"highlighted_text":"Bill Details","highlighted_text_color":"333333","highlighted_style":"BOLD","highlighted_visibility":true,"small_text":"eee","small_text_color":"333333","small_text_style":"NORMAL","small_text_visibility":false,"value_text":"ee","value_text_color":"333333","value_text_style":"NORMAL","value_textvisibility":false}]}
         * button_visibility : {"track":false,"cancel":false,"mail_invoice":true,"support":true,"coupon":false}
         */

        private HolderMapImageBean holder_map_image;
        private HolderBookingDescriptionBean holder_booking_description;
        private HolderPickdropLocationBean holder_pickdrop_location;
        private HolderMeteringBean holder_metering;
        private HolderDriverBean holder_driver;
        private HolderReceiptBean holder_receipt;
        private ButtonVisibilityBean button_visibility;
        private List<CancelReasonsBean> cancel_reasons;

        public HolderMapImageBean getHolder_map_image() {
            return holder_map_image;
        }

        public void setHolder_map_image(HolderMapImageBean holder_map_image) {
            this.holder_map_image = holder_map_image;
        }

        public HolderBookingDescriptionBean getHolder_booking_description() {
            return holder_booking_description;
        }

        public void setHolder_booking_description(HolderBookingDescriptionBean holder_booking_description) {
            this.holder_booking_description = holder_booking_description;
        }

        public HolderPickdropLocationBean getHolder_pickdrop_location() {
            return holder_pickdrop_location;
        }

        public void setHolder_pickdrop_location(HolderPickdropLocationBean holder_pickdrop_location) {
            this.holder_pickdrop_location = holder_pickdrop_location;
        }

        public HolderMeteringBean getHolder_metering() {
            return holder_metering;
        }

        public void setHolder_metering(HolderMeteringBean holder_metering) {
            this.holder_metering = holder_metering;
        }

        public HolderDriverBean getHolder_driver() {
            return holder_driver;
        }

        public void setHolder_driver(HolderDriverBean holder_driver) {
            this.holder_driver = holder_driver;
        }

        public HolderReceiptBean getHolder_receipt() {
            return holder_receipt;
        }

        public void setHolder_receipt(HolderReceiptBean holder_receipt) {
            this.holder_receipt = holder_receipt;
        }

        public ButtonVisibilityBean getButton_visibility() {
            return button_visibility;
        }

        public void setButton_visibility(ButtonVisibilityBean button_visibility) {
            this.button_visibility = button_visibility;
        }

        public List<CancelReasonsBean> getCancel_reasons() {
            return cancel_reasons;
        }

        public void setCancel_reasons(List<CancelReasonsBean> cancel_reasons) {
            this.cancel_reasons = cancel_reasons;
        }

        public static class HolderMapImageBean {
            /**
             * visibility : true
             * data : {"map_image":"https:maps.googleapis.com/maps/api/staticmap?center=&maptype=roadmap&path=weight:10%7Cenc:}ullD{kguMoCIE??~@oD?M@?d@Kz@Wd@eAtAiAdAcAj@iGjBfApEjF~SBLZDZFhA\\|Dw@vJkBBZs@LBXeB^yPjDwDp@D\\\\xCZfBd@jDz@nE@N?Fi@~Ac@lAZjBL@nCpAn@\\P?`@Gf@ArAIlAQ?E&sensor=false"}
             */

            private boolean visibility;
            private DataBean data;

            public boolean isVisibility() {
                return visibility;
            }

            public void setVisibility(boolean visibility) {
                this.visibility = visibility;
            }

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * map_image : https:maps.googleapis.com/maps/api/staticmap?center=&maptype=roadmap&path=weight:10%7Cenc:}ullD{kguMoCIE??~@oD?M@?d@Kz@Wd@eAtAiAdAcAj@iGjBfApEjF~SBLZDZFhA\|Dw@vJkBBZs@LBXeB^yPjDwDp@D\\xCZfBd@jDz@nE@N?Fi@~Ac@lAZjBL@nCpAn@\P?`@Gf@ArAIlAQ?E&sensor=false
                 */

                private String map_image;

                public String getMap_image() {
                    return map_image;
                }

                public void setMap_image(String map_image) {
                    this.map_image = map_image;
                }
            }
        }

        public static class HolderBookingDescriptionBean {
            /**
             * visibility : true
             * data : {"highlighted_left_text":"2018-08-22 06:41:16",
             * "highlighted_left_text_style":"BOLD",
             * "highlighted_left_text_color":"333333","small_left_text":"Normal Luxury","small_left_text_style":"NORMAL","small_left_text_color":"bbbbbb","highlighted_right_text":"200","highlighted_right_text_style":"NORMAL","highlighted_right_text_color":"333333","small_right_text":"Payment Done","small_right_text_style":"BOLD","small_right_text_color":"e74c3c"}
             */

            private boolean visibility;
            private DataBeanX data;

            public boolean isVisibility() {
                return visibility;
            }

            public void setVisibility(boolean visibility) {
                this.visibility = visibility;
            }

            public DataBeanX getData() {
                return data;
            }

            public void setData(DataBeanX data) {
                this.data = data;
            }

            public static class DataBeanX {
                /**
                 * highlighted_left_text : 2018-08-22 06:41:16
                 * highlighted_left_text_style : BOLD
                 * highlighted_left_text_color : 333333
                 * small_left_text : Normal Luxury
                 * small_left_text_style : NORMAL
                 * small_left_text_color : bbbbbb
                 * highlighted_right_text : 200
                 * highlighted_right_text_style : NORMAL
                 * highlighted_right_text_color : 333333
                 * small_right_text : Payment Done
                 * small_right_text_style : BOLD
                 * small_right_text_color : e74c3c
                 */

                private String highlighted_left_text;
                private String highlighted_left_text_style;
                private String highlighted_left_text_color;
                private String small_left_text;
                private String small_left_text_style;
                private String small_left_text_color;
                private String highlighted_right_text;
                private String highlighted_right_text_style;
                private String highlighted_right_text_color;
                private String small_right_text;
                private String small_right_text_style;
                private String small_right_text_color;

                public String getHighlighted_left_text() {
                    return highlighted_left_text;
                }

                public void setHighlighted_left_text(String highlighted_left_text) {
                    this.highlighted_left_text = highlighted_left_text;
                }

                public String getHighlighted_left_text_style() {
                    return highlighted_left_text_style;
                }

                public void setHighlighted_left_text_style(String highlighted_left_text_style) {
                    this.highlighted_left_text_style = highlighted_left_text_style;
                }

                public String getHighlighted_left_text_color() {
                    return highlighted_left_text_color;
                }

                public void setHighlighted_left_text_color(String highlighted_left_text_color) {
                    this.highlighted_left_text_color = highlighted_left_text_color;
                }

                public String getSmall_left_text() {
                    return small_left_text;
                }

                public void setSmall_left_text(String small_left_text) {
                    this.small_left_text = small_left_text;
                }

                public String getSmall_left_text_style() {
                    return small_left_text_style;
                }

                public void setSmall_left_text_style(String small_left_text_style) {
                    this.small_left_text_style = small_left_text_style;
                }

                public String getSmall_left_text_color() {
                    return small_left_text_color;
                }

                public void setSmall_left_text_color(String small_left_text_color) {
                    this.small_left_text_color = small_left_text_color;
                }

                public String getHighlighted_right_text() {
                    return highlighted_right_text;
                }

                public void setHighlighted_right_text(String highlighted_right_text) {
                    this.highlighted_right_text = highlighted_right_text;
                }

                public String getHighlighted_right_text_style() {
                    return highlighted_right_text_style;
                }

                public void setHighlighted_right_text_style(String highlighted_right_text_style) {
                    this.highlighted_right_text_style = highlighted_right_text_style;
                }

                public String getHighlighted_right_text_color() {
                    return highlighted_right_text_color;
                }

                public void setHighlighted_right_text_color(String highlighted_right_text_color) {
                    this.highlighted_right_text_color = highlighted_right_text_color;
                }

                public String getSmall_right_text() {
                    return small_right_text;
                }

                public void setSmall_right_text(String small_right_text) {
                    this.small_right_text = small_right_text;
                }

                public String getSmall_right_text_style() {
                    return small_right_text_style;
                }

                public void setSmall_right_text_style(String small_right_text_style) {
                    this.small_right_text_style = small_right_text_style;
                }

                public String getSmall_right_text_color() {
                    return small_right_text_color;
                }

                public void setSmall_right_text_color(String small_right_text_color) {
                    this.small_right_text_color = small_right_text_color;
                }
            }
        }

        public static class HolderPickdropLocationBean {
            /**
             * visibility : true
             * data : {"pick_text_visibility":true,"pick_text":"C-5, S City Rd, South City II, Sector 49, Gurugram, Haryana 122018, India","drop_text_visibility":true,"drop_text":"Jai vihar Plot 240, Sector 48, Gurugram, Haryana 122004, India"}
             */

            private boolean visibility;
            private DataBeanXX data;

            public boolean isVisibility() {
                return visibility;
            }

            public void setVisibility(boolean visibility) {
                this.visibility = visibility;
            }

            public DataBeanXX getData() {
                return data;
            }

            public void setData(DataBeanXX data) {
                this.data = data;
            }

            public static class DataBeanXX {
                /**
                 * pick_text_visibility : true
                 * pick_text : C-5, S City Rd, South City II, Sector 49, Gurugram, Haryana 122018, India
                 * drop_text_visibility : true
                 * drop_text : Jai vihar Plot 240, Sector 48, Gurugram, Haryana 122004, India
                 */

                private boolean pick_text_visibility;
                private String pick_text;
                private boolean drop_text_visibility;
                private String drop_text;

                public boolean isPick_text_visibility() {
                    return pick_text_visibility;
                }

                public void setPick_text_visibility(boolean pick_text_visibility) {
                    this.pick_text_visibility = pick_text_visibility;
                }

                public String getPick_text() {
                    return pick_text;
                }

                public void setPick_text(String pick_text) {
                    this.pick_text = pick_text;
                }

                public boolean isDrop_text_visibility() {
                    return drop_text_visibility;
                }

                public void setDrop_text_visibility(boolean drop_text_visibility) {
                    this.drop_text_visibility = drop_text_visibility;
                }

                public String getDrop_text() {
                    return drop_text;
                }

                public void setDrop_text(String drop_text) {
                    this.drop_text = drop_text;
                }
            }
        }

        public static class HolderMeteringBean {
            /**
             * visibility : true
             * data : {"text_one":"200","text_two":"0","text_three":"00:00:05"}
             */

            private boolean visibility;
            private DataBeanXXX data;

            public boolean isVisibility() {
                return visibility;
            }

            public void setVisibility(boolean visibility) {
                this.visibility = visibility;
            }

            public DataBeanXXX getData() {
                return data;
            }

            public void setData(DataBeanXXX data) {
                this.data = data;
            }

            public static class DataBeanXXX {
                /**
                 * text_one : 200
                 * text_two : 0
                 * text_three : 00:00:05
                 */

                private String text_one;
                private String text_two;
                private String text_three;

                public String getText_one() {
                    return text_one;
                }

                public void setText_one(String text_one) {
                    this.text_one = text_one;
                }

                public String getText_two() {
                    return text_two;
                }

                public void setText_two(String text_two) {
                    this.text_two = text_two;
                }

                public String getText_three() {
                    return text_three;
                }

                public void setText_three(String text_three) {
                    this.text_three = text_three;
                }
            }
        }

        public static class HolderDriverBean {
            /**
             * visibility : true
             * data : {"circular_image":"driver/OGodTPeyp4i9gUvtJZ0LqjWhkrkcYe1Xwa9IM04r.jpeg","highlighted_text":"Gautam","small_text":"gautam@seshra.com","rating_visibility":true,"rating":"4.75","rating_button_visibility":false,"rating_button_enable":false,"rating_button_text":"YOU RATE","rating_button_text_color":"333333","rating_button_text_style":"NORMAL"}
             */

            private boolean visibility;
            private DataBeanXXXX data;

            public boolean isVisibility() {
                return visibility;
            }

            public void setVisibility(boolean visibility) {
                this.visibility = visibility;
            }

            public DataBeanXXXX getData() {
                return data;
            }

            public void setData(DataBeanXXXX data) {
                this.data = data;
            }

            public static class DataBeanXXXX {
                /**
                 * circular_image : driver/OGodTPeyp4i9gUvtJZ0LqjWhkrkcYe1Xwa9IM04r.jpeg
                 * highlighted_text : Gautam
                 * small_text : gautam@seshra.com
                 * rating_visibility : true
                 * rating : 4.75
                 * rating_button_visibility : false
                 * rating_button_enable : false
                 * rating_button_text : YOU RATE
                 * rating_button_text_color : 333333
                 * rating_button_text_style : NORMAL
                 */

                private String circular_image;
                private String highlighted_text;
                private String small_text;
                private boolean rating_visibility;
                private String rating;
                private boolean rating_button_visibility;
                private boolean rating_button_enable;
                private String rating_button_text;
                private String rating_button_text_color;
                private String rating_button_text_style;

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

                public String getSmall_text() {
                    return small_text;
                }

                public void setSmall_text(String small_text) {
                    this.small_text = small_text;
                }

                public boolean isRating_visibility() {
                    return rating_visibility;
                }

                public void setRating_visibility(boolean rating_visibility) {
                    this.rating_visibility = rating_visibility;
                }

                public String getRating() {
                    return rating;
                }

                public void setRating(String rating) {
                    this.rating = rating;
                }

                public boolean isRating_button_visibility() {
                    return rating_button_visibility;
                }

                public void setRating_button_visibility(boolean rating_button_visibility) {
                    this.rating_button_visibility = rating_button_visibility;
                }

                public boolean isRating_button_enable() {
                    return rating_button_enable;
                }

                public void setRating_button_enable(boolean rating_button_enable) {
                    this.rating_button_enable = rating_button_enable;
                }

                public String getRating_button_text() {
                    return rating_button_text;
                }

                public void setRating_button_text(String rating_button_text) {
                    this.rating_button_text = rating_button_text;
                }

                public String getRating_button_text_color() {
                    return rating_button_text_color;
                }

                public void setRating_button_text_color(String rating_button_text_color) {
                    this.rating_button_text_color = rating_button_text_color;
                }

                public String getRating_button_text_style() {
                    return rating_button_text_style;
                }

                public void setRating_button_text_style(String rating_button_text_style) {
                    this.rating_button_text_style = rating_button_text_style;
                }
            }
        }

        public static class HolderReceiptBean {
            /**
             * visibility : true
             * data : [{"highlighted_text":"Bill Details",
             * "highlighted_text_color":"333333","highlighted_style":"BOLD","highlighted_visibility":true,
             * "small_text":"eee","small_text_color":"333333","small_text_style":"NORMAL","small_text_visibility":false,
             * "value_text":"ee","value_text_color":"333333","value_text_style":"NORMAL","value_textvisibility":false}]
             */

            private boolean visibility;
            private List<DataBeanXXXXX> data;

            public boolean isVisibility() {
                return visibility;
            }

            public void setVisibility(boolean visibility) {
                this.visibility = visibility;
            }

            public List<DataBeanXXXXX> getData() {
                return data;
            }

            public void setData(List<DataBeanXXXXX> data) {
                this.data = data;
            }

            public static class DataBeanXXXXX {
                /**
                 * highlighted_text : Bill Details
                 * highlighted_text_color : 333333
                 * highlighted_style : BOLD
                 * highlighted_visibility : true
                 * small_text : eee
                 * small_text_color : 333333
                 * small_text_style : NORMAL
                 * small_text_visibility : false
                 * value_text : ee
                 * value_text_color : 333333
                 * value_text_style : NORMAL
                 * value_textvisibility : false
                 */

                private String highlighted_text;
                private String highlighted_text_color;
                private String highlighted_style;
                private boolean highlighted_visibility;
                private String small_text;
                private String small_text_color;
                private String small_text_style;
                private boolean small_text_visibility;
                private String value_text;
                private String value_text_color;
                private String value_text_style;
                private boolean value_textvisibility;

                public String getHighlighted_text() {
                    return highlighted_text;
                }

                public void setHighlighted_text(String highlighted_text) {
                    this.highlighted_text = highlighted_text;
                }

                public String getHighlighted_text_color() {
                    return highlighted_text_color;
                }

                public void setHighlighted_text_color(String highlighted_text_color) {
                    this.highlighted_text_color = highlighted_text_color;
                }

                public String getHighlighted_style() {
                    return highlighted_style;
                }

                public void setHighlighted_style(String highlighted_style) {
                    this.highlighted_style = highlighted_style;
                }

                public boolean isHighlighted_visibility() {
                    return highlighted_visibility;
                }

                public void setHighlighted_visibility(boolean highlighted_visibility) {
                    this.highlighted_visibility = highlighted_visibility;
                }

                public String getSmall_text() {
                    return small_text;
                }

                public void setSmall_text(String small_text) {
                    this.small_text = small_text;
                }

                public String getSmall_text_color() {
                    return small_text_color;
                }

                public void setSmall_text_color(String small_text_color) {
                    this.small_text_color = small_text_color;
                }

                public String getSmall_text_style() {
                    return small_text_style;
                }

                public void setSmall_text_style(String small_text_style) {
                    this.small_text_style = small_text_style;
                }

                public boolean isSmall_text_visibility() {
                    return small_text_visibility;
                }

                public void setSmall_text_visibility(boolean small_text_visibility) {
                    this.small_text_visibility = small_text_visibility;
                }

                public String getValue_text() {
                    return value_text;
                }

                public void setValue_text(String value_text) {
                    this.value_text = value_text;
                }

                public String getValue_text_color() {
                    return value_text_color;
                }

                public void setValue_text_color(String value_text_color) {
                    this.value_text_color = value_text_color;
                }

                public String getValue_text_style() {
                    return value_text_style;
                }

                public void setValue_text_style(String value_text_style) {
                    this.value_text_style = value_text_style;
                }

                public boolean isValue_textvisibility() {
                    return value_textvisibility;
                }

                public void setValue_textvisibility(boolean value_textvisibility) {
                    this.value_textvisibility = value_textvisibility;
                }
            }
        }

        public static class ButtonVisibilityBean {
            /**
             * track : false
             * cancel : false
             * mail_invoice : true
             * support : true
             * coupon : false
             */

            private boolean track;
            private boolean cancel;
            private boolean mail_invoice;
            private boolean support;
            private boolean coupon;

            public boolean isTrack() {
                return track;
            }

            public void setTrack(boolean track) {
                this.track = track;
            }

            public boolean isCancel() {
                return cancel;
            }

            public void setCancel(boolean cancel) {
                this.cancel = cancel;
            }

            public boolean isMail_invoice() {
                return mail_invoice;
            }

            public void setMail_invoice(boolean mail_invoice) {
                this.mail_invoice = mail_invoice;
            }

            public boolean isSupport() {
                return support;
            }

            public void setSupport(boolean support) {
                this.support = support;
            }

            public boolean isCoupon() {
                return coupon;
            }

            public void setCoupon(boolean coupon) {
                this.coupon = coupon;
            }
        }

        public static class CancelReasonsBean {
            /**
             * id : 1
             * reason : Driver Refused to come
             */

            private int id;
            private String reason;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }
        }
    }
}
