package com.chendd.v2ex.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ReplyList extends ArrayList<Reply> implements Parcelable {


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ReplyList() {
    }

    protected ReplyList(Parcel in) {
    }

    public static final Creator<ReplyList> CREATOR = new Creator<ReplyList>() {
        @Override
        public ReplyList createFromParcel(Parcel source) {
            return new ReplyList(source);
        }

        @Override
        public ReplyList[] newArray(int size) {
            return new ReplyList[size];
        }
    };
}
