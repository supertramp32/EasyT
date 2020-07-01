package com.seshra.everestcab.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelGoogleApiStatus implements Parcelable {

    /**
     * status : REQUEST_DENIED
     */

    private String status;

    protected ModelGoogleApiStatus(Parcel in) {
        status = in.readString();
    }

    public static final Creator<ModelGoogleApiStatus> CREATOR = new Creator<ModelGoogleApiStatus>() {
        @Override
        public ModelGoogleApiStatus createFromParcel(Parcel in) {
            return new ModelGoogleApiStatus(in);
        }

        @Override
        public ModelGoogleApiStatus[] newArray(int size) {
            return new ModelGoogleApiStatus[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
    }
}
