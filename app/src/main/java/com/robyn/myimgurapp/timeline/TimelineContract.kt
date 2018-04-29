package com.robyn.myimgurapp.timeline

import com.robyn.myimgurapp.data.dataModel.GalleryModel

interface TimelineContract {

    interface Presenter {
        var showingViral: Boolean
        var showingSearched: Boolean

        fun getAdapter(): TimelineAdapter
        fun updateImagesToAdapter(galleries: List<GalleryModel>)

        fun showConnectionErrorMsgOnScreen()

        fun loadByCategory()
        fun refresh()
        fun search(queryText: String)

        fun clearItems() // when search or change category
        fun clearCompositeDisposable() // release observables
    }

    interface View {
        var mPresenter: TimelineContract.Presenter

        fun showConnectionErrorMsg()
        fun showRefreshing(isRefreshing: Boolean)
    }
}