package com.inheritex.demo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable

/// [BaseViewModel] this class is use for base view model
open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val empty = MutableLiveData<Boolean>()
    val dataLoading = MutableLiveData<Boolean>()
    val toastMessage = MutableLiveData<String>()
    var compositeDisposable = CompositeDisposable()

}