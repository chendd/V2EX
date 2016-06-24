package com.chendd.v2ex.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Member implements Parcelable {
    public String id;
    public String username;
    public String tagline;
    @SerializedName("avatar_mini")
    public String avatarMini;
    @SerializedName("avatar_normal")
    public String avatarNormal;
    @SerializedName("avatar_large")
    public String avatarLarge;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.tagline);
        dest.writeString(this.avatarMini);
        dest.writeString(this.avatarNormal);
        dest.writeString(this.avatarLarge);
    }

    public Member() {
    }

    protected Member(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.tagline = in.readString();
        this.avatarMini = in.readString();
        this.avatarNormal = in.readString();
        this.avatarLarge = in.readString();
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel source) {
            return new Member(source);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };
}
