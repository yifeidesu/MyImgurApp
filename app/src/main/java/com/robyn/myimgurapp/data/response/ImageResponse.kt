package com.robyn.myimgurapp.data.response

import com.robyn.myimgurapp.data.dataModel.ImageModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Model object for gallery response
 *
 * [data] 's model object is [ImageModel]
 */
class ImageResponse {

    @SerializedName("data")
    @Expose
    var data: ImageModel? = null
}