package com.aritana.cityclimate.api

import com.aritana.cityclimate.api.model.Main
import com.aritana.cityclimate.api.model.Sys
import com.aritana.cityclimate.api.model.Weather

data class ResponseResult(
    val weather: List<Weather>,
    val main: Main,
    val sys: Sys,
    val name: String
)
