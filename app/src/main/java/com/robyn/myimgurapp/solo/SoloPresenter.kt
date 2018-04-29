package com.robyn.myimgurapp.solo

import android.util.Log
import com.robyn.myimgurapp.data.ImgurService
import com.robyn.myimgurapp.data.dataModel.ImageModel
import com.robyn.myimgurapp.data.response.ImageResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SoloPresenter(
    val view: SoloContract.View,
    imageHash: String
) :
    SoloContract.Presenter {

    var mCompositeDisposable: CompositeDisposable
    var mImageModel = ImageModel()
    var mImageHash = imageHash

    init {
        view.mPresenter = this
        mCompositeDisposable = CompositeDisposable()

        loadItems()
    }

    private fun loadItems() {

        val imgurApiService = ImgurService.retrofit.create(ImgurService::class.java)

        val observable = imgurApiService.image(mImageHash).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        observable.subscribe(object : Observer<ImageResponse> {

            override fun onNext(t: ImageResponse) {

                t.data?.apply {
                    mImageModel.image_id = image_link
                    mImageModel.image_link = image_link

                    image_link?.let { view.loadImageSolo(it) }
                }
            }

            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {
                mCompositeDisposable.add(d)
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, e.message)
                showConnectionErrorMsgOnScreen()
            }
        })
    }

    override fun showConnectionErrorMsgOnScreen() {
        view.showConnectionErrorMsg()
    }

    // invoke in activity onDestroy() to release
    override fun clearCompositeDisposable() {
        mCompositeDisposable.clear()
    }

    companion object {
        const val TAG = "SoloPresenter"
    }
}