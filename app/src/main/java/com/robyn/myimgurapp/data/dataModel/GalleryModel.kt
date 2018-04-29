package com.robyn.myimgurapp.data.dataModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Model object for imgur gallery.
 */
class GalleryModel {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("ups")
    var ups: Int = 0

    @SerializedName("downs")
    var downs: Int = 0

    @SerializedName("favorite_count")
    var favorite_count: Int = 0

//    @SerializedName("link")
//    var link: String? = null

    @SerializedName("points")
    var points: Int = 0

    @SerializedName("images")
    @Expose
    var images: List<ImageModel>? = null

    /**
     * [first_img_id] and [first_img_url] are not included in the galleryNewest json response.
     *
     * I add this vars here to conveniently access to
     * the link to the first image[ImageModel] of the galleryNewest[GalleryModel].
     *
     * Its value is assigned to [ImageModel.image_link] in presenter.
     *
     * @see ImageModel
     */
    var first_img_url: String? = null
    var first_img_id: String? = null
}
