package com.aritana.cityclimate

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.aritana.cityclimate.adapter.MyRecyclerViewAdapter
import com.aritana.cityclimate.api.ApiService
import com.aritana.cityclimate.api.CreateRetrofit
import com.aritana.cityclimate.api.ResponseResult
import com.aritana.cityclimate.model.Coordinates
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = MyRecyclerViewAdapter(emptyList())
        recyclerView.adapter = adapter

        getUser(adapter)
    }

    private fun getUser(adapter: MyRecyclerViewAdapter) {
        val list: MutableList<ResponseResult> = mutableListOf()

        var service: ApiService = CreateRetrofit().retrofitService

        val cityCoordinatesList = mutableListOf<Coordinates>()
        for (city in Coordinates.values()) {
            cityCoordinatesList.add(city)
        }

        val response = cityCoordinatesList.map {
            service.getUserDetails(it.latitude, it.longitude)
        }

        response.forEach {
            it.enqueue(object : retrofit2.Callback<ResponseResult> {
                override fun onResponse(
                    call: Call<ResponseResult>,
                    response: Response<ResponseResult>
                ) {
                    if (response.body() != null) {
                        list.add(response.body()!!)
                        adapter.update(list)
                        Log.d(
                            "MainActivity-getUser",
                            "success when trying to get retrofit data , response  = ${response.body()}"
                        )
                    }
                    Log.d(
                        "MainActivity-getUser",
                        "fail in retrofit, response.body  = ${response.body()}"
                    )
                }
                override fun onFailure(call: Call<ResponseResult>, t: Throwable) {
                    Log.d(
                        "MainActivity-getUser",
                        "fail when trying to get retrofit data, call  = $call"
                    )
                }

            })
        }
    }
}