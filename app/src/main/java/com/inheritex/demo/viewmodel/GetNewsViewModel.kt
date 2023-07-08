package com.inheritex.demo.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.inheritex.demo.api.ResponseListner
import com.inheritex.demo.data.Articles
import com.inheritex.demo.respository.NewsRepository

/// [GetNewsViewModel] this class is use for get data from repository and display in UI
class GetNewsViewModel (application: Application) : BaseViewModel(application){

    var newsLiveData=MutableLiveData<ArrayList<Articles>>()

    /// [getNewsData] This function is used for get data from database
    fun getNewsData(query : String,context : Context,lifeCycleOwner : LifecycleOwner){

        NewsRepository.getNewsRepository(getApplication())?.getNewsData(object : ResponseListner<ArrayList<Articles>>(){
            override fun onSuccess(response: ArrayList<Articles>?) {
                newsLiveData.value = response
            }

            override fun onFailure(error: Throwable?) {
                Toast.makeText(context,"something Went Wrong!",Toast.LENGTH_SHORT).show()
            }
        },query,context,lifeCycleOwner)
    }

    /// [deleteFromDB] This function is used for delete data from database
    fun deleteFromDB(context: Context,id :String) :Int?{
        return NewsRepository.getNewsRepository(getApplication())?.deleteFromDB(context,id)
    }
}