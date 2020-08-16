package com.example.ddareungi.api

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class StationServiceTest {

    private lateinit var service: BikeStationService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun init() {
        mockWebServer = MockWebServer()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(BikeStationService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getStations() {
        enqueueResponse("stationRealStatus.json")

        val serviceTestObserver = service.getBikeStations()
        serviceTestObserver
            .test()
            .assertNoErrors()
            .assertSubscribed()
            .assertValue { response ->
                val stationList = response.realtimeList
                stationList[0].stationName == "위트콤"
            }
            .assertValue { response ->
                val stationList = response.realtimeList
                stationList[0].getLatitude() == 0.0
            }
            .assertValue { response ->
                val stationList = response.realtimeList
                stationList[0].getLongitude() == 0.0
            }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse.setBody(source.readString(Charsets.UTF_8))
        )
    }
}