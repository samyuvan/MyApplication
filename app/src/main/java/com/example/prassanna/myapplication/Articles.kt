package com.example.prassanna.myapplication

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Articles(
		@SerializedName("author") var articleId: String? = null,
		@SerializedName("title") var title: String? = null,
		@SerializedName("description") var description: String? = null,
		@SerializedName("urlToImage") var thumb: String? = null,
		@SerializedName("url") var url: String? = null,
		@SerializedName("publishedAt") var publishedAt: String? = null,
		@SerializedName("content") var content: String? = null) : Parcelable {
	constructor(parcel: Parcel) : this(
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString())
	
	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(articleId)
		parcel.writeString(title)
		parcel.writeString(description)
		parcel.writeString(thumb)
		parcel.writeString(url)
		parcel.writeString(publishedAt)
		parcel.writeString(content)
	}
	
	override fun describeContents(): Int {
		return 0
	}
	
	companion object CREATOR : Parcelable.Creator<Articles> {
		override fun createFromParcel(parcel: Parcel): Articles {
			return Articles(parcel)
		}
		
		override fun newArray(size: Int): Array<Articles?> {
			return arrayOfNulls(size)
		}
	}
}