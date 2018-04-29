package com.robyn.myimgurapp.timeline

import android.util.Log
import com.robyn.myimgurapp.data.ImgurService
import com.robyn.myimgurapp.data.dataModel.GalleryModel
import com.robyn.myimgurapp.data.response.GalleryResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TimelinePresenter(val view: TimelineContract.View) :
    TimelineContract.Presenter {

    private val TAG = "TimelinePresenter"

    private val mService = ImgurService.service // datasource
    private var mNewestPage = 0
    private var mViralPage = 0
    override var showingViral = false // originally showing gallery/newest
    override var showingSearched = false

    var mCompositeDisposable: CompositeDisposable
    private var mAdapter: TimelineAdapter
    var mImgurItems: ArrayList<GalleryModel> = ArrayList()

    init {
        view.mPresenter = this
        mCompositeDisposable = CompositeDisposable()

        mAdapter = TimelineAdapter(mImgurItems)

        loadByCategory()
    }

    override fun getAdapter(): TimelineAdapter {
        return mAdapter
    }

    // Notify changes to update recyclerview
    override fun updateImagesToAdapter(galleries: List<GalleryModel>) {

        mAdapter.mAdapterGalleries = galleries
        mAdapter.notifyDataSetChanged()
    }

    // Show error msg when connection fails or response is null.
    override fun showConnectionErrorMsgOnScreen() {
        view.showConnectionErrorMsg()
    }

    // Clear disposable in hosting ac's onDestroy() to release.
    override fun clearCompositeDisposable() {
        mCompositeDisposable.clear()
    }

    // when search or change category
    override fun clearItems() {
        mImgurItems.clear()
    }

    // load items with the given service method
    private fun loadItems(action: ImgurService.() -> Observable<GalleryResponse>) {
        val observable = mService.action().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        observable.subscribe(object : Observer<GalleryResponse> {

            override fun onNext(t: GalleryResponse) {

                val responseImages = t.data

                responseImages?.let {

                    for (i in it.indices) {
                        val imageModel = GalleryModel()
                        with(it[i]) {

                            imageModel.id = id
                            imageModel.title = title

                            images?.let {
                                imageModel.first_img_url = it[0].image_link
                                imageModel.first_img_id = it[0].image_id
                            } ?: return@with // don't add it to list if no image.

                            imageModel.ups = ups
                            imageModel.downs = downs
                            imageModel.favorite_count = favorite_count
                            imageModel.points = points

                            mImgurItems.add(imageModel)
                        }
                    }
                } ?: showConnectionErrorMsgOnScreen() // if responseImages is null, show error msg.

                updateImagesToAdapter(mImgurItems)
            }

            override fun onComplete() {
                view.showRefreshing(false)
            }

            override fun onSubscribe(d: Disposable) {
                mCompositeDisposable.add(d)

                view.showRefreshing(true)
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, e.message)
                showConnectionErrorMsgOnScreen()
            }
        })
    }

    // load images with queryText as keyword
    override fun search(queryText: String) {

        showingSearched = true

        loadItems { mService.search(queryText) }

//        val observable = mService.search(queryText).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//
//        observable.subscribe(object : Observer<GalleryResponse> {
//
//            override fun onNext(t: GalleryResponse) {
//
//                val responseImages = t.data
//
//                responseImages?.let {
//
//                    for (i in it.indices) {
//                        val imageModel = GalleryModel()
//                        with(it[i]) {
//
//                            imageModel.id = id
//                            imageModel.title = title
//
//                            images?.let {
//                                imageModel.first_img_url = it[0].image_link
//                                imageModel.first_img_id = it[0].image_id
//                            } ?: return@with // don't add it to list if no image.
//
//                            imageModel.ups = ups
//                            imageModel.downs = downs
//                            imageModel.favorite_count = favorite_count
//                            imageModel.points = points
//
//                            mImgurItems.add(imageModel)
//                        }
//                    }
//                } ?: showConnectionErrorMsgOnScreen() // if responseImages is null, show error msg.
//
//                updateImagesToAdapter(mImgurItems)
//            }
//
//            override fun onComplete() {}
//
//            override fun onSubscribe(d: Disposable) {
//                mCompositeDisposable.add(d)
//            }
//
//            override fun onError(e: Throwable) {
//                Log.e(TAG, e.message)
//                showConnectionErrorMsgOnScreen()
//            }
//        })
    }

    // invoke when swipe to refresh
    override fun refresh() {
        if (showingSearched) clearItems()
        showingSearched = false

        if (showingViral) {
            loadViral(0)
        } else {
            loadNewest(0)
        }
    }

    // load item by category, invoke when choosing from overflow menu
    override fun loadByCategory() {
        if (showingSearched) clearItems()
        showingSearched = false

        if (showingViral) {
            loadViral(mViralPage)
            mViralPage++
        } else {
            loadNewest(mNewestPage)
            mNewestPage++
        }
    }

    private fun loadNewest(page: Int) {

        loadItems { mService.galleryNewest(page) }
    }

    private fun loadViral(page: Int) {

        loadItems { mService.galleryViral(page) }
    }
}