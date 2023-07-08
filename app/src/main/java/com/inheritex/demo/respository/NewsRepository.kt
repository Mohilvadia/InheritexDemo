package com.inheritex.demo.respository

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.inheritex.demo.DemoApplication
import com.inheritex.demo.api.NetworkManager
import com.inheritex.demo.api.NetworkModule
import com.inheritex.demo.api.ResponseListner
import com.inheritex.demo.constant.ApiConstant
import com.inheritex.demo.data.Articles
import com.inheritex.demo.data.NewsDBEntity
import com.inheritex.demo.data.NewsDataResponse
import com.inheritex.demo.data.SourceData

var dataManager: NewsRepository? = null
var mNetworkManager: NetworkManager? = null

/// [NewsRepository] This class is used for get data from API or local database
open class NewsRepository {

    companion object {
        fun getNewsRepository(context: Context): NewsRepository? {
            if (mNetworkManager == null && dataManager == null) {
                mNetworkManager = NetworkManager(NetworkModule.providesNetworkService())
                dataManager = NewsRepository()
            }

            return dataManager
        }
    }

    /// [deleteFromDB] This function is used for delete data from DB
    fun deleteFromDB(context: Context,id: String) : Int{
       return DemoApplication.getNewsDB(context,ApiConstant.DB_NAME).newsDao().deleteByType(id)
    }

    /// [getNewsData] This function is used for get data from DB
    fun getNewsData(responseListenerCreate: ResponseListner<ArrayList<Articles>>,
                            query: String,context: Context,lifeCycleOwner : LifecycleOwner){

        DemoApplication.getNewsDB(context,ApiConstant.DB_NAME).newsDao().getAllNews().observe(lifeCycleOwner,{
            if (it.isNullOrEmpty()){
                mNetworkManager?.getCategories(object : ResponseListner<NewsDataResponse>(){
                    override fun onSuccess(response: NewsDataResponse?) {
                        responseListenerCreate.onSuccess(response?.articles)
                        val listOfDB : ArrayList<NewsDBEntity> = arrayListOf()
                        for (i in response?.articles?.indices!!){
                            val newsDBEntity = NewsDBEntity(i,
                                response.articles?.get(i)?.source?.id,
                                response.articles?.get(i)?.source?.name,
                                response.articles?.get(i)?.author,
                                response.articles?.get(i)?.title,
                                response.articles?.get(i)?.description,
                                response.articles?.get(i)?.url,
                                response.articles?.get(i)?.urlToImage,
                                response.articles?.get(i)?.publishedAt,
                                response.articles?.get(i)?.content)
                            listOfDB.add(newsDBEntity)
                        }
                        DemoApplication.getNewsDB(context,ApiConstant.DB_NAME).newsDao().insert(listOfDB.toList())
                    }

                    override fun onFailure(error: Throwable?) {
                        responseListenerCreate.onFailure(error)
                    }
                },query)
            } else{
                val listFromDB : ArrayList<Articles> = arrayListOf()
                for (i in it.indices){
                    val articles = Articles(
                        SourceData(it[i].id,
                            it[i].name),
                            it[i].author,
                            it[i].title,
                            it[i].description,
                            it[i].url,
                            it[i].urlToImage,
                            it[i].publishedAt,
                            it[i].content)
                        listFromDB.add(articles)
                }
                responseListenerCreate.onSuccess(listFromDB)
            }
        })
    }


}