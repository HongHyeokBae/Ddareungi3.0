package com.example.ddareungi.api

import com.example.ddareungi.data.ParkResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ParkService {

    @GET("SearchParkInfoService/1/132/")
    fun getParkResult(): Single<ParkResponse>
}