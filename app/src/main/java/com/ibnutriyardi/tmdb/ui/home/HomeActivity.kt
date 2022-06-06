package com.ibnutriyardi.tmdb.ui.home

import androidx.fragment.app.Fragment
import com.ibnutriyardi.tmdb.R
import com.ibnutriyardi.tmdb.base.BaseActivity
import com.ibnutriyardi.tmdb.util.onTabSelected
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity() {

    override fun getLayoutResource(): Int = R.layout.activity_home

    override fun initPage() {
        setSupportActionBar(toolbar)
        tab_layout?.onTabSelected {
            when (it.position) {
                0 -> changePage(MoviesFragment())
                1 -> changePage(TVShowsFragment())
            }
        }
    }

    override fun observeData() {
        changePage(MoviesFragment())
    }

    private fun changePage(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container_layout, fragment).commit()
    }
}