package com.roro.langlive.model

import android.os.Parcelable
import android.os.Parcel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// Model Class for our movies
@Entity(tableName = "movies")
class MovieModel : Parcelable {
    @SerializedName("title")
    var title: String?

    @SerializedName("poster_path")
    var posterPath: String?

    @SerializedName("release_date")
    var releaseDate: String?

    @PrimaryKey
    @SerializedName("id")
    var movieId: Int

    @SerializedName("vote_average")
    var voteAverage: Float

    @SerializedName("overview")
    var movieOverview: String?

    @SerializedName("original_language")
    var originalLanguage: String?

    var isFavorite = false

    constructor(
        title: String?,
        posterPath: String?,
        releaseDate: String?,
        movieId: Int,
        voteAverage: Float,
        movieOverview: String?,
        originalLanguage: String?,
    ) {
        this.title = title
        this.posterPath = posterPath
        this.releaseDate = releaseDate
        this.movieId = movieId
        this.voteAverage = voteAverage
        this.movieOverview = movieOverview
        this.originalLanguage = originalLanguage
    }

    protected constructor(p: Parcel) {
        title = p.readString()
        posterPath = p.readString()
        releaseDate = p.readString()
        movieId = p.readInt()
        voteAverage = p.readFloat()
        movieOverview = p.readString()
        originalLanguage = p.readString()
        isFavorite = p.readBoolean()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(title)
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeInt(movieId)
        parcel.writeFloat(voteAverage)
        parcel.writeString(movieOverview)
        parcel.writeString(originalLanguage)
        parcel.writeBoolean(isFavorite)
    }

    override fun toString(): String {
        return "MovieModel{" +
                "title='" + title + '\'' +
                ", poster_path='" + posterPath + '\'' +
                ", release_date='" + releaseDate + '\'' +
                ", movie_id=" + movieId +
                ", vote_average=" + voteAverage +
                ", movie_overview='" + movieOverview + '\'' +
                ", original_language='" + originalLanguage + '\'' +
                '}'
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MovieModel?> = object : Parcelable.Creator<MovieModel?> {
            override fun createFromParcel(p: Parcel): MovieModel? {
                return MovieModel(p)
            }

            override fun newArray(size: Int): Array<MovieModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}