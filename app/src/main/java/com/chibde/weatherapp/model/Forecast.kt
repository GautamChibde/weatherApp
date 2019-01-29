package com.chibde.weatherapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val forecastDays: List<ForecastDay> = listOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(ForecastDay))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(forecastDays)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Forecast> {
        override fun createFromParcel(parcel: Parcel): Forecast {
            return Forecast(parcel)
        }

        override fun newArray(size: Int): Array<Forecast?> {
            return arrayOfNulls(size)
        }
    }
}