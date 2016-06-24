package com.chendd.v2ex.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Node implements Parcelable {
    public String id;
    public String name;
    public String title;
    @SerializedName("title_alternative")
    public String titleAlternative;
    public String url;
    public int topics;
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
        dest.writeString(this.name);
        dest.writeString(this.title);
        dest.writeString(this.titleAlternative);
        dest.writeString(this.url);
        dest.writeInt(this.topics);
        dest.writeString(this.avatarMini);
        dest.writeString(this.avatarNormal);
        dest.writeString(this.avatarLarge);
    }

    public Node() {
    }

    protected Node(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.title = in.readString();
        this.titleAlternative = in.readString();
        this.url = in.readString();
        this.topics = in.readInt();
        this.avatarMini = in.readString();
        this.avatarNormal = in.readString();
        this.avatarLarge = in.readString();
    }

    public static final Creator<Node> CREATOR = new Creator<Node>() {
        @Override
        public Node createFromParcel(Parcel source) {
            return new Node(source);
        }

        @Override
        public Node[] newArray(int size) {
            return new Node[size];
        }
    };
}
