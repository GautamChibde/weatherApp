package com.chibde.weatherapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("current")
    val current: Current?,
    @SerializedName("forecast")
    val forecast: Forecast?,
    @SerializedName("location")
    val location: Location?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Current::class.java.classLoader),
        parcel.readParcelable(Forecast::class.java.classLoader),
        parcel.readParcelable(Location::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(current, flags)
        parcel.writeParcelable(forecast, flags)
        parcel.writeParcelable(location, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherForecast> {
        override fun createFromParcel(parcel: Parcel): WeatherForecast {
            return WeatherForecast(parcel)
        }

        override fun newArray(size: Int): Array<WeatherForecast?> {
            return arrayOfNulls(size)
        }
    }
}