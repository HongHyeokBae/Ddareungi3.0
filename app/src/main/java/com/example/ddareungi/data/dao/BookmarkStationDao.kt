package com.example.ddareungi.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ddareungi.data.BookmarkStation
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BookmarkStationDao {

    @Query("SELECT * FROM BookmarkStations")
    fun getAllStations(): List<BookmarkStation>

    @Query("SELECT * FROM BookmarkStations")
    fun getAllStationsRx(): Single<List<BookmarkStation>>

    @Query("SELECT * FROM BookmarkStations WHERE stationId = :stationId")
    fun getStationById(stationId: String): BookmarkStation?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStation(station: BookmarkStation): Completable

    @Query("DELETE FROM BookmarkStations WHERE stationId = :stationId")
    fun deleteStation(stationId: String): Completable
}