package com.example.ddareungi.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Todo: will replace [BikeStation] with this class
 */
@Entity(tableName = "Bookmarks")
data class StationRe(
    @PrimaryKey
    @SerializedName("stationId")
    var stationId: String,

    @SerializedName("stationName")
    var stationName: String,

    @SerializedName("rackTotCnt")
    @Ignore
    var rackTotCnt: String = "0",

    @SerializedName("parkingBikeTotCnt")
    @Ignore
    var parkingBikeTotCnt: String = "0",

    @SerializedName("parkingQRBikeCnt")
    @Ignore
    var parkingQRBikeCnt: String = "0",

    @SerializedName("stationLatitude")
    @Ignore
    val stationLatitude: String = "0.0",

    @SerializedName("stationLongitude")
    @Ignore
    val stationLongitude: String = "0.0",

    var bookmarked:Boolean = false
) {
    constructor(): this("", "")

    fun getParkingBikeCnt(): Int = parkingBikeTotCnt.toInt() + parkingQRBikeCnt.toInt()
    fun getLatitude(): Double = stationLatitude.toDouble()
    fun getLongitude(): Double = stationLongitude.toDouble()
}

data class BikeStationResponse(
    @SerializedName("realtimeList")
    val realtimeList: List<StationRe>
)