package com.ibnutriyardi.tmdb.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibnutriyardi.tmdb.BuildConfig
import com.ibnutriyardi.tmdb.R
import com.ibnutriyardi.tmdb.model.ResponseContent
import com.ibnutriyardi.tmdb.util.GlideApp
import kotlinx.android.synthetic.main.item_content.view.*

class ContentAdapter(private val contents: List<ResponseContent>, private val onCLicked: (ResponseContent) -> Unit) : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = contents[position]
        holder.bindData(content, onCLicked)
    }

    override fun getItemCount(): Int = contents.count()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(content: ResponseContent, onCLicked: (ResponseContent) -> Unit) {
            itemView.apply {
                GlideApp.with(context).load("${BuildConfig.IMAGE_BASE_URL}${content.backdropPath ?: ""}").into(img_content)
                setOnClickListener {
                    onCLicked.invoke(content)
                }
            }
        }
    }
}