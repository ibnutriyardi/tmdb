package com.ibnutriyardi.tmdb.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceDecorator(private val spacing: Int, private val withEdgeSpacing: Boolean = false) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val isLastItem = position == parent.adapter?.itemCount?.minus(1)
        when {
            position == 0 -> {
                outRect.left = if (withEdgeSpacing) spacing.times(2) else spacing
                outRect.right = spacing.div(2)
            }

            isLastItem -> {
                outRect.left = spacing.div(2)
                outRect.right = if (withEdgeSpacing) spacing.times(2) else spacing
            }

            else -> {
                outRect.left = spacing.div(2)
                outRect.right = spacing.div(2)
            }
        }

        outRect.top = spacing
        outRect.bottom = spacing
    }
}