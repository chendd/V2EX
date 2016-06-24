package com.chendd.v2ex.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Topic implements Parcelable {
    public String id;
    public String title;
    public String url;
    public String content;
    @SerializedName("content_rendered")
    public String contentRendered;
    public int replies;
    public Member member;
    public Node node;

    public long created;
    @SerializedName("last_modified")
    public long lastModified;
    @SerializedName("last_touched")
    public long lastTouched;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.content);
        dest.writeString(this.contentRendered);
        dest.writeInt(this.replies);
        dest.writeParcelable(this.member, flags);
        dest.writeParcelable(this.node, flags);
        dest.writeLong(this.created);
        dest.writeLong(this.lastModified);
        dest.writeLong(this.lastTouched);
    }

    public Topic() {
    }

    protected Topic(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.content = in.readString();
        this.contentRendered = in.readString();
        this.replies = in.readInt();
        this.member = in.readParcelable(Member.class.getClassLoader());
        this.node = in.readParcelable(Node.class.getClassLoader());
        this.created = in.readLong();
        this.lastModified = in.readLong();
        this.lastTouched = in.readLong();
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel source) {
            return new Topic(source);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };
}
