package com.ibnutriyardi.tmdb.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibnutriyardi.tmdb.R
import com.ibnutriyardi.tmdb.model.ResponseContent
import com.ibnutriyardi.tmdb.util.SpaceDecorator
import com.ibnutriyardi.tmdb.util.convertDpToPx
import kotlinx.android.synthetic.main.item_section.view.*

class SectionAdapter(private val onItemClicked: (ResponseContent) -> Unit) : RecyclerView.Adapter<SectionAdapter.ViewHolder>() {

    var sections: List<Pair<String, List<ResponseContent>>> = listOf()
        set(value) {
            field = value
            notifyItemInserted(0)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val section = sections[position]
        holder.bindData(section.first, section.second, onItemClicked)
    }

    override fun getItemCount(): Int = sections.count()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(title: String, contents: List<ResponseContent>, onItemClicked: (ResponseContent) -> Unit) {
            itemView.apply {
                val contentAdapter = ContentAdapter(contents, onItemClicked)
                txt_section_title?.text = title
                rv_content?.apply {
                    setHasFixedSize(true)
                    addItemDecoration(SpaceDecorator(context.convertDpToPx(8)))
                    adapter = contentAdapter
                }
            }
        }
    }
}