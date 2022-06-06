package com.ibnutriyardi.tmdb.ui.home

import com.google.android.material.snackbar.Snackbar
import com.ibnutriyardi.tmdb.R
import com.ibnutriyardi.tmdb.base.BaseFragment
import com.ibnutriyardi.tmdb.util.gone
import com.ibnutriyardi.tmdb.util.subscribeState
import kotlinx.android.synthetic.main.fragment_content.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoviesFragment : BaseFragment() {

    private val moviesViewModel by viewModel<MoviesViewModel>()
    private val bannerAdapter by lazy { BannerAdapter() }
    private val sectionAdapter by lazy {
        SectionAdapter {
            // TODO : Open content details
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_content

    override fun initPage() {
        moviesViewModel.callEvent(MoviesViewModel.Event.LoadContent)
    }

    override fun subscribeState() {
        subscribeState(moviesViewModel.state) {
            when (it) {
                is MoviesViewModel.State.HideBanner -> vp_banner?.gone()
                is MoviesViewModel.State.ShowBanner -> {
                    bannerAdapter.contents = it.nowPlayingMovies
                    vp_banner?.apply {
                        adapter = bannerAdapter
                    }
                }

                is MoviesViewModel.State.ShowSection -> {
                    sectionAdapter.sections = it.sections
                    rv_section?.apply {
                        setHasFixedSize(true)
                        adapter = sectionAdapter
                    }
                }

                is MoviesViewModel.State.ShowError -> Snackbar.make(container_layout, "Oops, somethings wrong", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}