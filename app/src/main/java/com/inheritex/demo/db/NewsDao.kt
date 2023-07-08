package com.inheritex.demo.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Query
import com.inheritex.demo.data.Articles
import com.inheritex.demo.data.NewsDBEntity

///[NewsDao] This interface is use for run news query
@Dao
interface NewsDao: BaseDao<NewsDBEntity> {

    @Query("delete from news_table where title=:title")
    fun deleteByType(title : String) : Int

    @Query("select * from news_table")
    fun getAllNews() : LiveData<List<NewsDBEntity>>
}