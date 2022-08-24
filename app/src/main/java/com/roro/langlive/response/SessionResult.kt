package com.roro.langlive.response

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.roro.langlive.model.MovieModel

class SessionResult {
    //https://api.themoviedb.org/3/authentication/token/new?api_key=<Api_key>
    @SerializedName("session_id")
    @Expose
    var sessionId: String? = null

    override fun toString(): String {
        return "{session id = $sessionId}"
    }

}