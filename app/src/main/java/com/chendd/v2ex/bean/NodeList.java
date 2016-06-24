package com.chendd.v2ex.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NodeList extends ArrayList<Node> implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public NodeList() {
    }

    protected NodeList(Parcel in) {
    }

    public static final Creator<NodeList> CREATOR = new Creator<NodeList>() {
        @Override
        public NodeList createFromParcel(Parcel source) {
            return new NodeList(source);
        }

        @Override
        public NodeList[] newArray(int size) {
            return new NodeList[size];
        }
    };
}
