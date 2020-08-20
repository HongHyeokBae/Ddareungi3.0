package com.example.ddareungi.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ddareungi.data.StationRe
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface BookmarkDao {

    @Query("SELECT * FROM Bookmarks")
    fun getAllStations(): Single<List<StationRe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStation(station: StationRe): Completable

    @Query("DELETE FROM Bookmarks WHERE stationId = :stationId")
    fun deleteStation(stationId: String): Completable
}