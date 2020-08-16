package com.example

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        if(!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
        super.onCleared()
    }
}