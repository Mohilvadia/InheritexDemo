package com.inheritex.demo.data

import androidx.room.Entity
import java.util.*

/// [NewsDataResponse] This data class is used for set api response
@Entity(tableName = "news")
data class NewsDataResponse (
    var status : String? = null,
    var totalResult : Int? =  null,
    var articles : ArrayList<Articles>? = null
)

/// [Articles] This data class is used for set api response
data class Articles(
    var source : SourceData? = null,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var content: String? = null,
)

/// [SourceData] This data class is used for set api response
data class SourceData(
    var id : String? = null,
    var name:  String? = null
)