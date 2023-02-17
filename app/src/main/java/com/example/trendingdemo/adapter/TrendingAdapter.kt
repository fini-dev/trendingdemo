package com.example.trendingdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.trendingdemo.R
import com.example.trendingdemo.model.GifModel

class TrendingAdapter(
    private val context: Context,
    private var items: MutableList<GifModel>
): Adapter<TrendingAdapter.TrendingHolder>() {

    fun setItems(items: MutableList<GifModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false)
        return TrendingHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingHolder, position: Int) {
        val trending = items[position]

        holder.titleText.text = trending.title
        Glide.with(context)
            .asGif()
            .load(trending.gifUrl)
            .into(holder.imageView)
    }

    class TrendingHolder(view: View): ViewHolder(view) {
        var titleText: TextView
        var imageView: ImageView
        init {
            titleText = view.findViewById(R.id.tv_title)
            imageView = view.findViewById(R.id.iv_gif)
        }
    }

}