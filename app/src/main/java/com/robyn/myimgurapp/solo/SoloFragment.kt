package com.robyn.myimgurapp.solo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.robyn.myimgurapp.R
import com.robyn.myimgurapp.utils.loadImage
import com.robyn.myimgurapp.utils.showErrorMsg

/**
 * Provide view for the detail Imgur image item.
 *
 */
class SoloFragment : Fragment(), SoloContract.View {

    override lateinit var mPresenter: SoloContract.Presenter
    private lateinit var mImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.solo_fg, container, false)
        setHasOptionsMenu(true)

        mImageView = view.findViewById(R.id.solo_image)

        return view
    }

    override fun loadImageSolo(imgUrlString: String) {

        context?.apply {
            loadImage(this, imgUrlString, mImageView)
        }
    }

    override fun showConnectionErrorMsg() {
        view?.let { showErrorMsg(it) }
    }

    companion object {
        fun newInstance() = SoloFragment()
    }
}
