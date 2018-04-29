package com.robyn.myimgurapp.timeline

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.robyn.myimgurapp.R
import com.robyn.myimgurapp.data.dataModel.GalleryModel
import com.robyn.myimgurapp.solo.SoloActivity
import com.robyn.myimgurapp.utils.loadThumbnailImage

/**
 * timeline recyclerview's adapter
 */

class TimelineAdapter(var mAdapterGalleries: List<GalleryModel>) :
    RecyclerView.Adapter<TimelineAdapter.ImgurHolder>() {

    inner class ImgurHolder(
        inflater: LayoutInflater, parent: ViewGroup
    ) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.timeline_item, parent, false)),
        View.OnClickListener {

        private lateinit var mGalleryModel: GalleryModel

        init {
            itemView.setOnClickListener(this)
        }

        internal fun bind(imgurItem: GalleryModel) {
            mGalleryModel = imgurItem

            with(itemView) {

                // set caption
                val title = (mGalleryModel.title) ?: "No title"
                val points = "${mGalleryModel.points} Points \u25B2"
                val text = "$points $title"

                findViewById<TextView>(R.id.timeline_caption).setText(text)

                // set image
                val imageView = findViewById<ImageView>(R.id.timeline_item_image)

                context?.apply {
                    mGalleryModel.first_img_url?.let { loadThumbnailImage(this, it, imageView) }
                }
            }
        }

        // On click item, launch SoloActivity
        override fun onClick(v: View) {

            val ac = itemView.context as FragmentActivity
            val intent = Intent(itemView.context, SoloActivity::class.java)

            val imageId = mGalleryModel.first_img_id
            val caption = mGalleryModel.title

            intent.putExtra(SoloActivity.EXTRA_SOLO_IMGHASH, imageId)
            intent.putExtra(SoloActivity.EXTRA_SOLO_CAPTION, caption)

            ac.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgurHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImgurHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ImgurHolder, position: Int) {
        holder.bind(mAdapterGalleries[position])
    }

    override fun getItemCount(): Int {
        return mAdapterGalleries.size
    }
}