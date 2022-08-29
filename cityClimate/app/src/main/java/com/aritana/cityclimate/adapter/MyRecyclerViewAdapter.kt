package com.aritana.cityclimate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aritana.cityclimate.R
import com.aritana.cityclimate.api.ResponseResult
import com.aritana.cityclimate.model.DayOfTheWeek
import com.bumptech.glide.Glide
import java.util.Calendar
import kotlin.math.*

class MyRecyclerViewAdapter(private var itemList: List<ResponseResult>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder>() {

    lateinit var parentImage: ViewGroup

    fun update(itemsList: List<ResponseResult>) {
        this.itemList = itemsList
        notifyDataSetChanged()
    }

    inner class MyRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weatherTextView: TextView = view.findViewById(R.id.text_view_weather)
        val temperatureTextView: TextView = view.findViewById(R.id.text_view_temperature)
        val addressTextView: TextView = view.findViewById(R.id.text_view_address)
        val humidityTextView: TextView = view.findViewById(R.id.text_view_humidity)
        val dateNumberTextView: TextView = view.findViewById(R.id.text_view_date_number)
        val dateTextTextView: TextView = view.findViewById(R.id.text_view_date_text)
        val weatherIcon: ImageView = view.findViewById(R.id.image_view_weatherIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        parentImage = parent
        return MyRecyclerViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MyRecyclerViewHolder, position: Int) {
        val dayOfTheWeek= DayOfTheWeek
        val item = itemList[position]
        val weather = item.weather[0].main
        val iconCode = item.weather[0].icon
        val iconUrl = "https://openweathermap.org/img/w/${iconCode}.png"
        val country = item.sys.country
        val city = item.name
        val temperature = round(item.main.temp).toInt().toString()
        val humidity = round(item.main.humidity).toInt().toString()
        val dateNumber = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val dateText = dayOfTheWeek.today().name
        val context = holder.itemView.context

        holder.weatherTextView.text = weather
        holder.temperatureTextView.text = context.getString(R.string.temperature, temperature)
        holder.addressTextView.text = "$city, $country"
        holder.humidityTextView.text =context.getString(R.string.humidity, humidity.toString())
        holder.dateNumberTextView.text = dateNumber.toString()
        holder.dateTextTextView.text = dateText
        loadImage(parentImage, holder, iconUrl)
    }

    override fun getItemCount(): Int = itemList.size

    private fun loadImage(parent:ViewGroup, holder: MyRecyclerViewHolder, url:String){
        Glide.with(parent.context)
            .load( url)
            .into(holder.weatherIcon)
    }
}