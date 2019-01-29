package com.chibde.weatherapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ForecastDay(
    @SerializedName("date")
    val date: String?,
    @SerializedName("day")
    val day: Day?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Day::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeParcelable(day, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ForecastDay> {
        override fun createFromParcel(parcel: Parcel): ForecastDay {
            return ForecastDay(parcel)
        }

        override fun newArray(size: Int): Array<ForecastDay?> {
            return arrayOfNulls(size)
        }
    }
}