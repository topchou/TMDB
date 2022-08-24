package com.roro.langlive.response

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.roro.langlive.model.MovieModel

class TokenResult {
    //https://api.themoviedb.org/3/authentication/token/new?api_key=<Api_key>
    @SerializedName("request_token")
    @Expose
    var token: String? = null

    @SerializedName("expires_at")
    @Expose
    var expiresTime: String? = null

    override fun toString(): String {
        return "{$token}"
    }

}