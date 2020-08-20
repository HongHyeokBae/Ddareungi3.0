package com.example.ddareungi.data

import com.google.gson.annotations.SerializedName

data class ParkResponse(
    @SerializedName("SearchParkInfoService")
    val info: Info
)

data class ParkRe(
    @SerializedName("P_IDX")
    val id: String,

    @SerializedName("P_PARK")
    val name: String,

    @SerializedName("G_LONGITUDE")
    val g_longitude: Double,

    @SerializedName("G_LATITUDE")
    val g_latitude: Double,

    @SerializedName("LONGITUDE")
    val longitude: Double,

    @SerializedName("LATITUDE")
    val latitude: Double,

    var dist: Float
)

data class Info(
    private val list_total_count: Int,
    val row: List<Park>
)