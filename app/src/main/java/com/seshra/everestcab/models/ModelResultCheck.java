package com.seshra.everestcab.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ModelResultCheck implements Parcelable
{
    @SerializedName("result")
    public String result;

    @SerializedName("message")
    public String message;

    protected ModelResultCheck(Parcel in) {
        result = in.readString();
        message = in.readString();
    }

    public static final Creator<ModelResultCheck> CREATOR = new Creator<ModelResultCheck>() {
        @Override
        public ModelResultCheck createFromParcel(Parcel in) {
            return new ModelResultCheck(in);
        }

        @Override
        public ModelResultCheck[] newArray(int size) {
            return new ModelResultCheck[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(result);
        dest.writeString(message);
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
}
