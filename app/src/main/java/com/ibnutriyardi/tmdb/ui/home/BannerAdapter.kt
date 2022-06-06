package com.ibnutriyardi.tmdb.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager.widget.PagerAdapter
import com.ibnutriyardi.tmdb.BuildConfig
import com.ibnutriyardi.tmdb.R
import com.ibnutriyardi.tmdb.model.ResponseContent
import com.ibnutriyardi.tmdb.util.GlideApp

class BannerAdapter : PagerAdapter() {

    var contents: List<ResponseContent> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val content = contents[position]
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_banner, container, false)
        val imgBanner = view.findViewById<AppCompatImageView>(R.id.img_banner)
        val title = view.findViewById<AppCompatTextView>(R.id.txt_banner_title)
        GlideApp.with(view).load("${BuildConfig.IMAGE_BASE_URL}${content.backdropPath ?: ""}").into(imgBanner)
        title.text = content.title ?: ""
        container.addView(view)
        return view
    }

    override fun getCount(): Int = contents.count()

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return obj === view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}