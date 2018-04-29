package com.robyn.myimgurapp.data.response

import com.robyn.myimgurapp.data.dataModel.GalleryModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *  Model object for gallery response
 *
 *  [data] contains a list of [GalleryModel]
 */
class GalleryResponse {

    @SerializedName("data")
    @Expose
    var data: List<GalleryModel>? = null
}