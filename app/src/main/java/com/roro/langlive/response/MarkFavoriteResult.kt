package com.roro.langlive.response

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.roro.langlive.model.MovieModel

class MarkFavoriteResult {
    //https://api.themoviedb.org/3/authentication/token/new?api_key=<Api_key>
    @SerializedName("status_code")
    @Expose
    var statusCode: Int? = 0

    @SerializedName("status_message")
    @Expose
    var statusMessage: String? = null


    override fun toString(): String {
        return "{$statusCode}"
    }

}