package com.inheritex.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.inheritex.demo.R
import com.inheritex.demo.data.Articles

/// [NewsAdapter] This class is used for set data in recycler view
class NewsAdapter(var listerner: ItemClickListener, var list: ArrayList<Articles>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.raw_news,parent,false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.tvTitle.text = list[position].title
        holder.tvDesc.text = list[position].description
        holder.tvContent.text = list[position].content

        holder.ivDelete.setOnClickListener {
            listerner.itemCLick(position, list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

   inner class NewsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var tvTitle : AppCompatTextView = itemView.findViewById(R.id.tvTitle)
        var tvDesc : AppCompatTextView = itemView.findViewById(R.id.tvDesc)
        var tvContent : AppCompatTextView = itemView.findViewById(R.id.tvContent)
        var ivDelete : AppCompatImageView = itemView.findViewById(R.id.ivDelete)
    }

    /// [ItemClickListener] This interface is used for get delete click in main screen and delete item
    interface ItemClickListener{
        fun itemCLick(position: Int, articles: Articles)
    }
}
