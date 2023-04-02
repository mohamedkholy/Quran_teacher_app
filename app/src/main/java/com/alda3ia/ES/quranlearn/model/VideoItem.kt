package com.alda3ia.ES.quranlearn.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoItem(
     @PrimaryKey
     val name: String,
     val url:String? = null,
     val aya:String ? = null,

):Parcelable {
     constructor(parcel: Parcel) : this(
          parcel.readString()!!,
          parcel.readString(),
          parcel.readString()
     ) {
     }

     override fun writeToParcel(parcel: Parcel, flags: Int) {
          parcel.writeString(name)
          parcel.writeString(url)
          parcel.writeString(aya)
     }

     override fun describeContents(): Int {
          return 0
     }

     companion object CREATOR : Parcelable.Creator<VideoItem> {
          override fun createFromParcel(parcel: Parcel): VideoItem {
               return VideoItem(parcel)
          }

          override fun newArray(size: Int): Array<VideoItem?> {
               return arrayOfNulls(size)
          }
     }
}

