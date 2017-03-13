package com.placto.consumer;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 12/22/2016.
 */

class Customlist implements Parcelable {
    public String shop_id;
    public String verified;
    public String name;
    public String shop_name;
    public String rating;
    public String longitude;
    public String latitude;
    public String photo;

    public Customlist(JSONObject object) {
        try {
            this.shop_id = object.getString("shop_id");
            this.name = object.getString("name");
            this.shop_name = object.getString("shop_name");
            this.rating=object.getString("rating");
            this.longitude=object.getString("lat");
            this.latitude=object.getString("long");
            this.photo=object.getString("photo");
            this.verified = object.getString("verified");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<Customlist> fromJson(JSONArray jsonObjects) {
        ArrayList<Customlist> merchant = new ArrayList<Customlist>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                merchant.add(new Customlist(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return merchant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shop_id);
        dest.writeString(this.verified);
        dest.writeString(this.name);
        dest.writeString(this.shop_name);
        dest.writeString(this.rating);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeString(this.photo);
    }

    protected Customlist(Parcel in) {
        this.shop_id = in.readString();
        this.verified = in.readString();
        this.name = in.readString();
        this.shop_name = in.readString();
        this.rating = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.photo = in.readString();
    }

    public static final Parcelable.Creator<Customlist> CREATOR = new Parcelable.Creator<Customlist>() {
        @Override
        public Customlist createFromParcel(Parcel source) {
            return new Customlist(source);
        }

        @Override
        public Customlist[] newArray(int size) {
            return new Customlist[size];
        }
    };
}


