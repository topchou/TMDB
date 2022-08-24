package com.roro.langlive.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.roro.langlive.BasicApp
import com.roro.langlive.R
import com.roro.langlive.db.DataRepository
import com.roro.langlive.model.MovieModel


class MainActivity : AppCompatActivity() {
    lateinit var mRepository: DataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRepository = (application as BasicApp).repository!!

        // Add Movie List Fragment when first create
        if (savedInstanceState == null) {
            val movieListFragment = MovieListFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, movieListFragment, MovieListFragment.TAG).commit()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.get_token -> {
                mRepository.requestToken(this)
            }

            R.id.make_session -> {
                mRepository.requestSession()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun show(movieId: Int, postPath: String) {
        Log.d("TAG", "movie id = $movieId")

        val movieDetailFragment = MovieDetailFragment.forMovie(movieId, postPath)
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("movie").replace(R.id.fragment_container, movieDetailFragment, null)
            .commit()


    }
}