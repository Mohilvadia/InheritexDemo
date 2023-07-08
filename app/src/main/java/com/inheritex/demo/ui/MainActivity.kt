package com.inheritex.demo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.inheritex.demo.R
import com.inheritex.demo.adapter.NewsAdapter
import com.inheritex.demo.data.Articles
import com.inheritex.demo.viewmodel.GetNewsViewModel
import kotlinx.android.synthetic.main.activity_main.*

/// [MainActivity] This class is used for view UI
class MainActivity : AppCompatActivity(), NewsAdapter.ItemClickListener {

    private var newsAdapter: NewsAdapter ?= null
    private var newsList: ArrayList<Articles> = arrayListOf()
    private var searchNewsList: ArrayList<Articles> = arrayListOf()
    var viewModel : GetNewsViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[GetNewsViewModel::class.java]
        initView()
    }

    /// [initView] This function is used for init
    private fun initView() {
        getDataFromAPI()

        etSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchTitleInNews(text)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    /// [searchTitleInNews] This function is used for search data
    private fun searchTitleInNews(text: CharSequence?) {
        searchNewsList.clear()
        if(text.toString().isEmpty()) {
            newsAdapter = NewsAdapter(this, newsList)
            rvNews.adapter = newsAdapter
        }
        for(item in newsList) {
            if(item.title?.toLowerCase()!!.contains(text.toString().toLowerCase())) {
                searchNewsList.add(item)
            }
        }
        if(searchNewsList.isNotEmpty()) {
            newsAdapter = NewsAdapter(this, searchNewsList)
            rvNews.adapter = newsAdapter
        }
    }

    /// [getDataFromAPI] This function is used for get data from API
    private fun getDataFromAPI() {
        viewModel?.getNewsData("techcrunch",this,this)
        viewModel?.newsLiveData?.observe(this, {
            if (!it.isNullOrEmpty()){
                newsList = it
                rvNews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                newsAdapter = NewsAdapter(this, newsList)
                rvNews.adapter = newsAdapter
            }
        })
    }

    //// [itemCLick] This function is for delete item
    override fun itemCLick(position: Int, articles: Articles) {
        if(viewModel?.deleteFromDB(this,articles.title!!)!!>0){
            if (searchNewsList.size>0){
                searchNewsList.removeAt(position)
                newsAdapter = NewsAdapter(this, searchNewsList)
                rvNews.adapter = newsAdapter
            } else{
                getDataFromAPI()
            }
        } else{
            Toast.makeText(this,"something went Wrong!",Toast.LENGTH_SHORT).show()
        }
    }


}