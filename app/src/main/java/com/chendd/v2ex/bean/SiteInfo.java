package com.chendd.v2ex.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class SiteInfo implements Parcelable {

	public String title;
	public String slogan;
	public String description;
	public String domain;


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.title);
		dest.writeString(this.slogan);
		dest.writeString(this.description);
		dest.writeString(this.domain);
	}

	public SiteInfo() {
	}

	protected SiteInfo(Parcel in) {
		this.title = in.readString();
		this.slogan = in.readString();
		this.description = in.readString();
		this.domain = in.readString();
	}

	public static final Creator<SiteInfo> CREATOR = new Creator<SiteInfo>() {
		@Override
		public SiteInfo createFromParcel(Parcel source) {
			return new SiteInfo(source);
		}

		@Override
		public SiteInfo[] newArray(int size) {
			return new SiteInfo[size];
		}
	};
}
