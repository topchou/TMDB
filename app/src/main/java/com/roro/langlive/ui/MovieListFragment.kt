package com.roro.langlive.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.roro.langlive.R
import com.roro.langlive.databinding.FragmentMovieListBinding
import com.roro.langlive.model.MovieModel
import com.roro.langlive.viewModel.MovieListViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [MovieListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieListFragment : Fragment() {
    private var binding: FragmentMovieListBinding? = null
    private val viewModel: MovieListViewModel by lazy {
        ViewModelProvider(this)[MovieListViewModel::class.java]
    }

    private lateinit var moviesFragmentAdapter: MoviesFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        //Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        moviesFragmentAdapter =
            MoviesFragmentAdapter(mMovieClickCallback, mMarkFavoriteClickCallback)
        binding?.popularMovies?.adapter = moviesFragmentAdapter
        binding?.popularMovies?.layoutManager = LinearLayoutManager(context)

        subscribeUi(viewModel.getMovies())
        return binding?.root!!
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }


    private fun subscribeUi(liveData: LiveData<List<MovieModel>>) {
        liveData.observe(viewLifecycleOwner) { movies: List<MovieModel> ->
            moviesFragmentAdapter.setList(movies)
        }
    }

    private val mMovieClickCallback = object : MovieClickCallback {
        override fun onClick(movieId: Int, postPath:String) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (requireActivity() as MainActivity).show(movieId, postPath)
            }
        }
    }

    private val mMarkFavoriteClickCallback = object : MarkFavoriteClickCallback {
        override fun onClick(movie: MovieModel) {
            Log.d(TAG, "1movie isFavorite = ${movie.isFavorite}")
            viewModel.markFavorite(movie)
            Log.d(TAG, "2movie isFavorite = ${movie.isFavorite}")
        }
    }

    companion object {
        val TAG = "MovieListFragment"

    }
}