package com.roro.langlive.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PopularBody {
    @SerializedName("media_type")
    @Expose
    var mediaType: String? = null

    @SerializedName("media_id")
    @Expose
    var mediaId: Int? = null

    @SerializedName("favorite")
    @Expose
    var favorite: Boolean? = null

    constructor(mediaType: String, mediaId: Int, favorite: Boolean) {
        this.mediaType = mediaType
        this.mediaId = mediaId
        this.favorite = favorite
    }
}