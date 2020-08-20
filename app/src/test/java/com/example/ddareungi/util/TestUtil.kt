package com.example.ddareungi.util

import com.example.ddareungi.data.BookmarkStation
import com.example.ddareungi.data.StationRe

object TestUtil {

    fun createBookmarkStation(stationId: String, stationName: String) = BookmarkStation(
        stationId = stationId,
        stationName =  stationName
    )

    fun createBikeStationWithBookmark(stationId: String, stationName: String) = StationRe(
        stationId = stationId,
        stationName = stationName,
        bookmarked = true
    )

    fun createBikeStationWithNoBookmark(stationId: String, stationName: String) = StationRe(
        stationId = stationId,
        stationName = stationName,
        bookmarked = false
    )
}