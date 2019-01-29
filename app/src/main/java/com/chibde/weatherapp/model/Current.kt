package com.chibde.weatherapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("temp_c")
    val tempC: Double?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(tempC)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Current> {
        override fun createFromParcel(parcel: Parcel): Current {
            return Current(parcel)
        }

        override fun newArray(size: Int): Array<Current?> {
            return arrayOfNulls(size)
        }
    }
}