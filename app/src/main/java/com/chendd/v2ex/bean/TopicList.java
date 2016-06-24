package com.chendd.v2ex.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class TopicList extends ArrayList<Topic> implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public TopicList() {
    }

    protected TopicList(Parcel in) {
    }

    public static final Creator<TopicList> CREATOR = new Creator<TopicList>() {
        @Override
        public TopicList createFromParcel(Parcel source) {
            return new TopicList(source);
        }

        @Override
        public TopicList[] newArray(int size) {
            return new TopicList[size];
        }
    };
}
