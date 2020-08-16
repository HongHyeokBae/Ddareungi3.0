package com.example.ddareungi.data

import com.google.gson.annotations.SerializedName

/**
 * Todo: will replace [BikeStation] with this class
 */
data class StationRe(
    @SerializedName("stationId")
    val stationId: String,

    @SerializedName("stationName")
    val stationName: String,

    @SerializedName("rackTotCnt")
    val rackTotCnt: String,

    @SerializedName("parkingBikeTotCnt")
    private var parkingBikeTotCnt: String,

    @SerializedName("parkingQRBikeCnt")
    private var parkingQRBikeCnt: String,

    @SerializedName("stationLatitude")
    private val stationLatitude: String = "0.0",

    @SerializedName("stationLongitude")
    private val stationLongitude: String = "0.0",

    var bookmarked:Boolean = false
) {
    fun getParkingBikeCnt(): Int = parkingBikeTotCnt.toInt() + parkingQRBikeCnt.toInt()
    fun getLatitude(): Double = stationLatitude.toDouble()
    fun getLongitude(): Double = stationLongitude.toDouble()
}

data class BikeStationResponse(
    @SerializedName("realtimeList")
    val realtimeList: List<StationRe>
)