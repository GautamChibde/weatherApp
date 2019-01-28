package com.chibde.weatherapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chibde.weatherapp.R
import com.chibde.weatherapp.model.ForecastDay
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastAdapter(
    private val items: ArrayList<ForecastDay>
) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_forecast, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            item_forecast_tv_day.text = items[position].date ?: "N/A"
            item_forecast_tv_temp.text = items[position].day?.avgtemp.toString()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}