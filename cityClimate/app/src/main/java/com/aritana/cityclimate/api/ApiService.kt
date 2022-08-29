package com.aritana.cityclimate.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_key = "bfd3fa49faa6fcf94351b9ee6b47842f"

interface ApiService {
    @GET("weather?units=metric&appid=bfd3fa49faa6fcf94351b9ee6b47842f")
    fun getUserDetails(@Query("lat") lat: String, @Query("lon") lon: String): Call<ResponseResult>
}