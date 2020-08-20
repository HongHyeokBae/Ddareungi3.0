package com.example.ddareungi.repository

import com.example.ddareungi.api.BikeStationService
import com.example.ddareungi.data.AppDatabase
import com.example.ddareungi.data.BikeStationResponse
import com.example.ddareungi.data.dao.BookmarkDao
import com.example.ddareungi.util.TestUtil
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StationRepositoryTest {

    @Mock
    lateinit var dao: BookmarkDao
    @Mock
    lateinit var service: BikeStationService
    @InjectMocks
    lateinit var repository: StationRepository


    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.bookmarkDao()).thenReturn(dao)
    }

    @Test
    fun fetchRealTimeStatus_and_getBookmark_success() {
        val stations = listOf(TestUtil.createBikeStationWithNoBookmark("ST-991", "AA"),
        TestUtil.createBikeStationWithNoBookmark("ST-331", "ㅇㅇㅇ"))
        val bookmarks = listOf(TestUtil.createBikeStationWithBookmark("ST-991", "AA"))

        `when`(service.getBikeStations()).thenReturn(Single.just(BikeStationResponse(stations)))
        `when`(dao.getAllStations()).thenReturn(Single.just(bookmarks))

        repository.getBikeStations()
            .test()
            .assertValue {
                it["ST-991"] == bookmarks[0]
            }
    }

    @Test
    fun fetchRealtimeFail_and_getBookmarkSuccess() {
        val bookmarks = listOf(TestUtil.createBikeStationWithBookmark("ST-991", "AA"))

        `when`(service.getBikeStations()).thenReturn(Single.error(Throwable("realtime failed")))
        `when`(dao.getAllStations()).thenReturn(Single.just(bookmarks))

        repository.getBikeStations()
            .test()
            .assertValue(emptyMap())
    }

    @Test
    fun fetchRealtimeSuccess_and_getBookmarkFail() {
        val stations = listOf(TestUtil.createBikeStationWithNoBookmark("ST-991", "AA"),
            TestUtil.createBikeStationWithNoBookmark("ST-331", "ㅇㅇㅇ"))

        `when`(service.getBikeStations()).thenReturn(Single.just(BikeStationResponse(stations)))
        `when`(dao.getAllStations()).thenReturn(Single.error(Throwable("")))

        repository.getBikeStations()
            .test()
            .assertValue(stations.associateBy { it.stationId })
    }

    @Test
    fun fetchRealtimeFail_and_getBookmarkFail() {
        `when`(service.getBikeStations()).thenReturn(Single.error(Throwable("realtime failed")))
        `when`(dao.getAllStations()).thenReturn(Single.error(Throwable("")))

        repository.getBikeStations()
            .test()
            .assertValue(emptyMap())
    }
}