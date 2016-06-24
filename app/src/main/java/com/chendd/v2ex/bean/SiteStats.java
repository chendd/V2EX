package com.chendd.v2ex.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SiteStats implements Parcelable {
	@SerializedName("topic_max")
	public int topicMax;
	@SerializedName("member_max")
	public int memberMax;


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.topicMax);
		dest.writeInt(this.memberMax);
	}

	public SiteStats() {
	}

	protected SiteStats(Parcel in) {
		this.topicMax = in.readInt();
		this.memberMax = in.readInt();
	}

	public static final Creator<SiteStats> CREATOR = new Creator<SiteStats>() {
		@Override
		public SiteStats createFromParcel(Parcel source) {
			return new SiteStats(source);
		}

		@Override
		public SiteStats[] newArray(int size) {
			return new SiteStats[size];
		}
	};
}
