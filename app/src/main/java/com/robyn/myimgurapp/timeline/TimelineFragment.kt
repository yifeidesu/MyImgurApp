package com.robyn.myimgurapp.timeline

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.*
import com.robyn.myimgurapp.R
import com.robyn.myimgurapp.utils.EndlessScrollListener
import com.robyn.myimgurapp.utils.showErrorMsg

class TimelineFragment :
    Fragment(), TimelineContract.View {

    override lateinit var mPresenter: TimelineContract.Presenter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipeLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.timeline_fg, container, false)

        setHasOptionsMenu(true)

        // Setup recyclerview and its layoutManager, scrollListener.
        mRecyclerView = view.findViewById(R.id.timeline_recycler_view)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val adapter = mPresenter.getAdapter()

        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = adapter

        mRecyclerView.addOnScrollListener(object :
            EndlessScrollListener(layoutManager, mPresenter) {
        })

        // set refresh layout
        mSwipeLayout = view.findViewById(R.id.timeline_swipe_refresh_layout)
        mSwipeLayout.setOnRefreshListener {
            mPresenter.refresh()
            //mSwipeLayout.isRefreshing = false
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater?.inflate(R.menu.timeline_menu, menu)

        val searchItem = menu.findItem(R.id.timeline_menu_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    mPresenter.clearItems()
                    mPresenter.search(it)
                    searchView.onActionViewCollapsed()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView.isSubmitButtonEnabled = true
        searchView.queryHint = "Search..."
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        var showViral = false

        return when (item?.itemId) {

            R.id.viral -> {
                showViral = true

                // if previously not showing this category, or showing search list, clear
                if (mPresenter.showingViral != showViral) mPresenter.clearItems()

                mPresenter.showingViral = showViral
                mPresenter.loadByCategory()

                return true
            }

            R.id.newest -> {
                showViral = false

                // if previously not showing this category, or showing search list, clear
                if (mPresenter.showingViral != showViral) mPresenter.clearItems()

                mPresenter.showingViral = showViral
                mPresenter.loadByCategory()

                return true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    // When connection failed or get 0 mData, show error msg.
    override fun showConnectionErrorMsg() {
        view?.let { showErrorMsg(it) }
    }

    override fun showRefreshing(isRefreshing: Boolean) {
        view?.findViewById<SwipeRefreshLayout>(R.id.timeline_swipe_refresh_layout)?.isRefreshing =
                isRefreshing
        //mSwipeLayout.isRefreshing = isRefreshing // causes widget not init error.
    }

    companion object {
        fun newInstance() = TimelineFragment()
    }
}