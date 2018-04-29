package com.robyn.myimgurapp.solo

interface SoloContract {
    interface Presenter {
        fun showConnectionErrorMsgOnScreen()
        fun clearCompositeDisposable()
    }

    interface View {
        var mPresenter: SoloContract.Presenter
        fun loadImageSolo(imgUrlString: String)
        fun showConnectionErrorMsg()
    }
}