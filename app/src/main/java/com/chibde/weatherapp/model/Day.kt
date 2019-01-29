package com.chibde.weatherapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("avgtemp_c")
    val avgtemp: Double?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(avgtemp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Day> {
        override fun createFromParcel(parcel: Parcel): Day {
            return Day(parcel)
        }

        override fun newArray(size: Int): Array<Day?> {
            return arrayOfNulls(size)
        }
    }
}