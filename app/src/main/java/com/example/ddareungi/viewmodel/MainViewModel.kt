package com.example.ddareungi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.ddareungi.BaseViewModel
import com.example.ddareungi.Event
import com.example.ddareungi.data.StationRe
import com.example.ddareungi.repository.StationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val stationRepository: StationRepository): BaseViewModel() {

    private val _bikeStations = MutableLiveData<Map<String, StationRe>>()
    val bikeStation: LiveData<List<StationRe>> = _bikeStations.map { it.values.toList() }

    val bookmarkedStations: LiveData<List<StationRe>> = bikeStation.map { bikeStations ->
        bikeStations.filter { it.bookmarked }
    }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    val bookmarkEmpty:LiveData<Boolean> = bookmarkedStations.map { it.isEmpty() }

    private val _navigateToMapFragEvent = MutableLiveData<Event<String>>()
    val navigateToMapFragEvent: LiveData<Event<String>> = _navigateToMapFragEvent

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    init {
        loadBikeStations()
    }

    private fun loadBikeStations() {
        stationRepository.getBikeStations()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ stations ->
                _bikeStations.value = stations
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }


    fun addBookmarkStation(stationId: String, stationName: String) {
        stationRepository.addBookmarkStation(stationId, stationName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                _bikeStations.value?.get(stationId)?.bookmarked = true
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }


    fun deleteBookmarkStation(stationId: String) {
        stationRepository.deleteBookmarkStation(stationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                _bikeStations.value?.get(stationId)?.bookmarked = false
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }


    fun deleteBookmarkStation(position: Int) {
        bookmarkedStations.value?.get(position)?.let { station ->
            deleteBookmarkStation(station.stationId)
        }
    }


    fun refresh() {
        loadBikeStations()
    }


    fun showSnackBarMessage(message: String) {
        _snackBarText.value = Event(message)
    }


    fun navigateToMapFrag(stationId: String) {
        _navigateToMapFragEvent.value = Event(stationId)
    }
}