package com.example.trendingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.trendingdemo.adapter.TrendingAdapter
import com.example.trendingdemo.model.GifModel
import com.example.trendingdemo.service.ApiInterface
import com.example.trendingdemo.service.AppService
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var swipeRefresh: SwipeRefreshLayout

    /*
    We can use this variable to fetch more at the bottom
     */
//    private var lastIndex: Int = 0
    private var trendingArray = mutableListOf<GifModel>()

    private lateinit var trendingAdapter: TrendingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLayout()
    }

    override fun onResume() {
        super.onResume()

        getTrending()
    }

    // TODO: My Method

    private fun initLayout() {
        swipeRefresh = findViewById(R.id.swipe_refresh)
        swipeRefresh.setOnRefreshListener {
            getTrending()
        }

        trendingAdapter = TrendingAdapter(
            context = this,
            items = trendingArray
        )
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = trendingAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun getTrending() {
        swipeRefresh.isRefreshing = true
        val call = ApiInterface.create().getTrending(AppService.API_Key)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                swipeRefresh.isRefreshing = false
                if (response.code() == 200) {
                    val result = response.body()
                    if (result?.get("data") != null && result.get("data")!!.isJsonArray) {
                        val data = result.get("data").asJsonArray
                        trendingArray = mutableListOf()
                        for (datum in data) {
                            trendingArray.add(GifModel(obj = datum.asJsonObject))
                        }
                        trendingAdapter.setItems(trendingArray)
                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                swipeRefresh.isRefreshing = false
                Log.e("getTrending", "onFailure: ${t.localizedMessage}")
            }
        })
    }
}