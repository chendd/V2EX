package com.chendd.v2ex.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Reply implements Parcelable {
    public String id;
    public int thanks;
    public String content;
    @SerializedName("content_rendered")
    public String contentRendered;
    public Member member;
    //public Date created;
    //public Date lastModified;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.thanks);
        dest.writeString(this.content);
        dest.writeString(this.contentRendered);
        dest.writeParcelable(this.member, flags);
    }

    public Reply() {
    }

    protected Reply(Parcel in) {
        this.id = in.readString();
        this.thanks = in.readInt();
        this.content = in.readString();
        this.contentRendered = in.readString();
        this.member = in.readParcelable(Member.class.getClassLoader());
    }

    public static final Creator<Reply> CREATOR = new Creator<Reply>() {
        @Override
        public Reply createFromParcel(Parcel source) {
            return new Reply(source);
        }

        @Override
        public Reply[] newArray(int size) {
            return new Reply[size];
        }
    };
}
