package com.ibnutriyardi.tmdb.ui.content

import android.view.MenuItem
import com.ibnutriyardi.tmdb.BuildConfig
import com.ibnutriyardi.tmdb.R
import com.ibnutriyardi.tmdb.base.BaseActivity
import com.ibnutriyardi.tmdb.model.ResponseContent
import com.ibnutriyardi.tmdb.util.loadImage
import com.ibnutriyardi.tmdb.util.subscribeState
import kotlinx.android.synthetic.main.activity_content_detail.*
import kotlinx.android.synthetic.main.activity_home.toolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContentDetailActivity : BaseActivity() {

    companion object {
        private const val CONTENT_TYPE = "CONTENT_TYPE"
        private const val CONTENT_ID = "CONTENT_ID"
        private const val CONTENT_TITLE = "CONTENT_TITLE"

        fun withData(type: String, id: Int, title: String): Array<Pair<String, *>> {
            return arrayOf(CONTENT_TYPE to type, CONTENT_ID to id, CONTENT_TITLE to title)
        }
    }

    private val contentViewModel by viewModel<ContentViewModel>()
    private val contentType by lazy {
        intent?.getStringExtra(CONTENT_TYPE) ?: ""
    }

    private val contentId by lazy {
        intent?.getIntExtra(CONTENT_ID, 0) ?: 0
    }

    private val contentTitle by lazy {
        intent?.getStringExtra(CONTENT_TITLE) ?: ""
    }

    override fun getLayoutResource(): Int = R.layout.activity_content_detail

    override fun initPage() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = contentTitle
        }
    }

    override fun observeData() {
        subscribeState(contentViewModel.state) {
            when (it) {
                is ContentViewModel.State.ShowDetails -> showDetails(it.content)
            }
        }

        contentViewModel.callEvent(ContentViewModel.Event.LoadDetails(contentType, contentId))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDetails(content: ResponseContent) {
        img_content?.loadImage("${BuildConfig.IMAGE_BASE_URL}${content.backdropPath}")
        tv_vote_average?.text = content.voteAverage?.toString() ?: ""
        tv_overview?.text = content.overview ?: ""
        tv_release_date?.text = content.releaseDate ?: ""
    }
}