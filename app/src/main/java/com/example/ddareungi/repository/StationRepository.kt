package com.example.ddareungi.repository

import com.example.ddareungi.api.BikeStationService
import com.example.ddareungi.data.StationRe
import com.example.ddareungi.data.dao.BookmarkDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StationRepository @Inject constructor(
    private val bikeStationService: BikeStationService,
    private val bookmarkStationDao: BookmarkDao) {

    private fun fetchRealtimeStatus(): Single<Map<String, StationRe>> {
        return bikeStationService.getBikeStations().map { response ->
            response.realtimeList.associateBy { it.stationId }
        }.onErrorReturn { emptyMap() }
    }

    fun addBookmarkStation(stationId: String, stationName: String): Completable {
        return bookmarkStationDao.insertStation(StationRe(stationId, stationName))
    }

    fun deleteBookmarkStation(stationId: String): Completable {
        return bookmarkStationDao.deleteStation(stationId)
    }

    private fun setBookmarkStatus(bikeStations: Map<String, StationRe>): Single<Map<String, StationRe>> {
        return bookmarkStationDao.getAllStations().onErrorReturn { emptyList() }
            .map { bookmarkStations ->
            bookmarkStations.forEach { bikeStations[it.stationId]?.bookmarked = true }
            bikeStations
        }
    }

    fun getBikeStations(): Single<Map<String, StationRe>> {
        return fetchRealtimeStatus().flatMap { bikeStations ->
            setBookmarkStatus(bikeStations)
        }
    }

    fun getBookmarkStations(): Single<Map<String, StationRe>> {
        return bookmarkStationDao.getAllStations().map { stations ->
            stations.associateBy{ it.stationId }
        }
    }
}