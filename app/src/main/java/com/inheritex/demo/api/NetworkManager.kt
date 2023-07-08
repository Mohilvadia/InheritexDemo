package com.inheritex.demo.api

import com.inheritex.demo.constant.ApiConstant
import com.inheritex.demo.data.NewsDataResponse
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import java.util.*

/// [NetworkManager] This class is used for api call using rxJava♦
open class NetworkManager(apiService: ApiService) {
    private var mApiService: ApiService? = apiService
    open fun getMainThread(): Scheduler? {
        return AndroidSchedulers.mainThread()
    }

    open fun getioThread(): Scheduler? {
        return Schedulers.io()
    }

    fun getCategories(responseListener: ResponseListner<NewsDataResponse>, query: String) {
        mApiService?.getNewsData(query, ApiConstant.API_KEY)
                ?.subscribeOn(getioThread())
                ?.observeOn(getMainThread())
                ?.subscribe(responseListener)
    }
}