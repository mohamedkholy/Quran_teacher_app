package com.alda3ia.ES.quranlearn.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["name","number"])
data class Sura(
    val name:String,
    val ayaCount:Int,
    val number:Int,
    val level:Int,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(ayaCount)
        parcel.writeInt(number)
        parcel.writeInt(level)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sura> {
        override fun createFromParcel(parcel: Parcel): Sura {
            return Sura(parcel)
        }

        override fun newArray(size: Int): Array<Sura?> {
            return arrayOfNulls(size)
        }
    }
}