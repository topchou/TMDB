package com.roro.langlive.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.roro.langlive.R
import com.roro.langlive.databinding.MoviesItemBinding
import com.roro.langlive.model.MovieModel


class MoviesFragmentAdapter(private val mMovieClickCallback: MovieClickCallback?, private val mMarkFavoriteClickCallback: MarkFavoriteClickCallback?) :
    RecyclerView.Adapter<MoviesFragmentAdapter.MoviesListViewHolder>() {
    var list = ArrayList<MovieModel>()

    fun setList(list: List<MovieModel>) {
        this.list = ArrayList(list)
        Log.d(TAG, "setList size = ${this.list.size}")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        var binding: MoviesItemBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.movies_item,
                parent, false)

        //setCallback for click
        binding.callback = mMovieClickCallback
        binding.markFavoriteCallback = mMarkFavoriteClickCallback

        return MoviesListViewHolder(binding)


    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        val movie = list[position]
        holder.binding.movie = movie
        Log.d("TAG", "position = $position, movie title = ${movie.title}")
        holder.binding.title.text = movie.title
        holder.binding.movieOverView.text = movie.movieOverview
        holder.binding.releaseDate.text = movie.releaseDate


        // ImageView: Using Glide Library
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/"
                    + movie.posterPath)
            .into(holder.binding.previewImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MoviesListViewHolder(binding: MoviesItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding = binding
        /*
        var titleView = binding.title
        var picView = binding.previewImage
        var dateView = binding.releaseDate
        var favoriteBtn = binding.favoriteBtn
         */
    }

    companion object {
        private val TAG = "MoviesFragmentAdapter"
    }


}