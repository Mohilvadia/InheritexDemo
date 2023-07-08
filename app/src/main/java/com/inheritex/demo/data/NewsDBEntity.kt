package com.inheritex.demo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/// [NewsDBEntity] This data class is used for set data in database
@Entity(tableName = "news_table")
data class NewsDBEntity (
    @PrimaryKey(autoGenerate = true)
    var idKey: Int,
    var id : String? = null,
    var name:  String? = null,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var content: String? = null,
)