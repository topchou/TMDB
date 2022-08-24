package com.roro.langlive.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.roro.langlive.R
import com.roro.langlive.databinding.FragmentMovieDetailBinding
import com.roro.langlive.model.MovieModel
import com.roro.langlive.viewModel.MovieViewModel


class MovieDetailFragment() : Fragment() {

    lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = MovieViewModel.Factory(
            requireActivity().application,
            requireArguments().getInt(KEY_MOVIE_ID)
        )
        val model = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.movieViewModel = model
        binding.markFavoriteCallback = mMarkFavoriteClickCallback

        // ImageView: Using Glide Library
        Glide.with(this)
            .load(
                "https://image.tmdb.org/t/p/w500/"
                        + requireArguments().getString(KEY_POSTER_PATH)
            )
            .into(binding.previewImage)
        /*
         * Todo:
         * Model.movie.value.posterPath get null in beginning,
         * and need to wait long time to gain actual path.
         * I choice to use argument for keeping posterPath,
         * but I think it should have other better implement solution here.
         */
    }

    private val mMarkFavoriteClickCallback = object : MarkFavoriteClickCallback {
        override fun onClick(movie: MovieModel) {
            binding.movieViewModel?.markFavorite(movie)
            Log.d(MovieListFragment.TAG, "2movie isFavorite = ${movie.isFavorite}")
        }
    }

    companion object {
        private const val KEY_MOVIE_ID = "movie_id"
        private const val KEY_POSTER_PATH = "post_path"
        fun forMovie(movieId: Int, postPath: String): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            val args = Bundle()
            args.putInt(KEY_MOVIE_ID, movieId)
            args.putString(KEY_POSTER_PATH, postPath)
            fragment.arguments = args
            return fragment
        }
    }
}