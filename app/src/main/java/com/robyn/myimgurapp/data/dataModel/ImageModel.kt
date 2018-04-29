package com.robyn.myimgurapp.data.dataModel

import com.google.gson.annotations.SerializedName

/**
 * Model object for imgur image.
 */
class ImageModel {

    @SerializedName("link")
    var image_link: String? = null

    @SerializedName("id") // imageHash
    var image_id: String? = null
}