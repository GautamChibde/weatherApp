package com.chibde.weatherapp.utils

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

fun Intent.putParcel(key: String = "parcel_key", parcel: Parcelable) {
    val bundle = Bundle()
    bundle.putParcelable(key, parcel)
    this.putExtra("parcel_bundle", bundle)
}

fun <T : Parcelable> Intent.getParcel(key: String = "parcel_key"): T? {
    return this.getBundleExtra("parcel_bundle")?.getParcelable(key)
}

fun String.getDayString(): String {
    val sdf = SimpleDateFormat("EEEE", Locale.ENGLISH)
    val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return try {
        val d = sdfDate.parse(this)
        sdf.format(d).capitalize()
    } catch (e: Exception) {
        "N/A"
    }
}