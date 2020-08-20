package com.example.ddareungi

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {

    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        if(!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
        super.onCleared()
    }
}