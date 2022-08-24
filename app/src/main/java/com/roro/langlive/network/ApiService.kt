package com.roro.langlive.network

import com.roro.langlive.model.PopularBody
import com.roro.langlive.response.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


const val BASE_URL = "https://api.themoviedb.org/"

/**
 * Use the Retrofit builder to build a retrofit object using a GsonConverter with our MovieModel
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


/**
 * Define API method.
 */
interface ApiService {
    @GET("3/movie/popular")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Call<PopularMoviesResult>

    @GET("3/authentication/token/new")
    fun getToken(@Query("api_key") apiKey: String): Call<TokenResult>

    @GET("authenticate/{token}/allow")
    fun approve(@Path("token") token: String): Call<AuthenticateResult>

    @POST("3/authentication/session/new")
    fun getSession(
        @Query("api_key") apiKey: String,
        @Query("request_token") token: String
    ): Call<SessionResult>

    @GET("3/account/{account_id}/favorite/movies")
    fun getFavoriteMovies(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?,
    ): Call<FavoriteMoviesResult>


    ///account/{account_id}/favorite
    //@POST("account/ted84523/favorite")
    @POST("3/account/ted84523/favorite")
    fun markAsFavorite(
        @Body bodyFavorite: PopularBody?,
        @Query("api_key") apiKey: String?,
        @Query("session_id") sessionId: String?,
    ): Call<MarkFavoriteResult?>?

}

object MovieApi {
    val retrofitService = retrofit.create(ApiService::class.java)!!
}