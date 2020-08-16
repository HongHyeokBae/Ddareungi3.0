package com.example.ddareungi.api

import com.example.ddareungi.data.BikeStationResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BikeStationService {

    @GET("app/station/getStationRealtimeStatus.do")
    fun getBikeStations(@Query ("stationGrpSeq") stationGrpSeq: String = "ALL"): Single<BikeStationResponse>
}