package com.robyn.myimgurapp.timeline

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.robyn.myimgurapp.R
import com.robyn.myimgurapp.utils.replaceFragmentInActivity

class TimelineActivity : AppCompatActivity() {

    lateinit var mPresenter: TimelinePresenter
    private lateinit var mToolbar: android.support.v7.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timeline_ac)

        // Set up fragment, dataSource, and presenter

        val timelineFragment = supportFragmentManager.findFragmentById(R.id.timeline_container)
                as TimelineFragment? ?: TimelineFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.timeline_container)
        }

        mPresenter = TimelinePresenter(timelineFragment)

        // Setup toolbar
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)

        val sortDrawable = resources?.getDrawable(R.drawable.menu_sort)
        sortDrawable?.let { mToolbar.overflowIcon = it }
    }

    override fun onDestroy() {
        mPresenter.clearCompositeDisposable()
        super.onDestroy()
    }
}
